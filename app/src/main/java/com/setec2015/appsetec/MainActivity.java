package com.setec2015.appsetec;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;


public class MainActivity extends AppCompatActivity implements BluetoothAdapter.LeScanCallback {

    private static boolean RUN_ONCE = true;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private MyPagerAdapter mAdapter;

    private static final String TAG = "BluetoothGattActivity";

    private static final String DEVICE_NAME = "SETEC-15/16";

        private static final UUID SERVICE = UUID.fromString("00001120-2222-2111-1aad-22faa544a3dd");
        private static final UUID TEMP_READ = UUID.fromString("00001133-2222-2111-1aad-22faa544a3dd");
        private static final UUID TEMP_WRITE = UUID.fromString("00001134-2222-2111-1aad-22faa544a3dd");

        private static final UUID PRESSAO_READ = UUID.fromString("00001135-2222-2111-1aad-22faa544a3dd");
        private static final UUID PRESSAO_WRITE = UUID.fromString("00001136-2222-2111-1aad-22faa544a3dd");

        private static final UUID HUM_READ = UUID.fromString("00001137-2222-2111-1aad-22faa544a3dd");
        private static final UUID HUM_WRITE = UUID.fromString("00001138-2222-2111-1aad-22faa544a3dd");

        private static final UUID LUM_READ = UUID.fromString("00001139-2222-2111-1aad-22faa544a3dd");
        private static final UUID LUM_WRITE = UUID.fromString("00001140-2222-2111-1aad-22faa544a3dd");

        private static final UUID HSOLO_READ = UUID.fromString("00001141-2222-2111-1aad-22faa544a3dd");
        private static final UUID HSOLO_WRITE = UUID.fromString("00001142-2222-2111-1aad-22faa544a3dd");

        // UUID for the BTLE client characteristic which is necessary for notifications.
        public static UUID CLIENT_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

        private BluetoothAdapter mBluetoothAdapter;
        private SparseArray<BluetoothDevice> mDevices;

        private BluetoothGatt mConnectedGatt;

        private String mTemperature, mPressão, mHumAr, mLuminosidade, mHumSolo;

        private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProgressBarIndeterminate(true);

        try {
            Class.forName("android.os.AsyncTask");
            Log.i("ASYNCTASK ERROR LOGIN", "android.os.AsyncTask found.");
        } catch (ClassNotFoundException e) {
            Log.i("ASYNCTASK ERROR LOGIN", "Class android.os.AsyncTask not found!!!");
            e.printStackTrace();
        }


        mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_action);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        /*
         * Bluetooth in Android 4.3 is accessed via the BluetoothManager, rather than
         * the old static BluetoothAdapter.getInstance()
         */
        BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = manager.getAdapter();

        mDevices = new SparseArray<BluetoothDevice>();

        /*
         * A progress dialog will be needed while the connection process is taking place.
         */
        mProgress = new ProgressDialog(this);
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(false);

        SharedPreferences prefs = this.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        Boolean log = prefs.getBoolean("isLogged", false);

        /*
         * When user is LOGGED, the Local DB is deleted and updated with the Online DB
         */
        if(log)
        {
            Log.i("USER LOGGED", String.valueOf(log));
            /*
             * We first need to enforce that an Internet connection is existent, and ask the
             * user to enable one if they have not done so.
             */
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()) {
                updateOnlineDb();
            }
            else {
                Intent enableInternetIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(enableInternetIntent);
            }
        }
    }

    private void updateOnlineDb() {
        if(RUN_ONCE) {
            RUN_ONCE = false;

            // Local DB - get all rows from all tables (pandlet1_table, pandlet2_table, pandlet3_table)
                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("get_new_1");

            //updateLocalDb();
        }
    }

    private void updateLocalDb() {

            // Local DB - delete all local tables (pandlet1_table, pandlet2_table, pandlet3_table)
                BackgroundDbTask backgroundDbTask_A = new BackgroundDbTask(this);
                backgroundDbTask_A.execute("delete_info_1");

                BackgroundDbTask backgroundDbTask_B = new BackgroundDbTask(this);
                backgroundDbTask_B.execute("delete_info_2");

                BackgroundDbTask backgroundDbTask_C = new BackgroundDbTask(this);
                backgroundDbTask_C.execute("delete_info_3");

            // Online DB - get new data from all online tables (pandlet1_values, pandlet2_values, pandlet3_values)
                BackgroundOnlineDbTask backgroundOnlineDbTask_A = new BackgroundOnlineDbTask(this);
                backgroundOnlineDbTask_A.execute("get_info_1");

                BackgroundOnlineDbTask backgroundOnlineDbTask_B = new BackgroundOnlineDbTask(this);
                backgroundOnlineDbTask_B.execute("get_info_2");

                BackgroundOnlineDbTask backgroundOnlineDbTask_C = new BackgroundOnlineDbTask(this);
                backgroundOnlineDbTask_C.execute("get_info_3");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Make sure dialog is hidden
        mProgress.dismiss();
        //Cancel any scans in progress
        mHandler.removeCallbacks(mStopRunnable);
        mHandler.removeCallbacks(mStartRunnable);
        mBluetoothAdapter.stopLeScan(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Disconnect from any active tag connection
        if (mConnectedGatt != null) {
            mConnectedGatt.disconnect();
            mConnectedGatt = null;
        }
        RUN_ONCE = false;
    }


////////////////   Fragments (Tabs)   ////////////////

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns the Fragment at a given position
        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            if (i == 0) {
                fragment = new tabSensores();
            }
            if (i == 1) {
                fragment = new tabAlertas();
            }
            if (i == 2) {
                fragment = new tabHistorico();
            }
            return fragment;
        }

        // Returns the total number of pages or Fragments
        @Override
        public int getCount() {
            return 3;
        }

        // Show the name of each Tab in the Toolbar
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Sensores";
            }
            if (position == 1) {
                return "Alertas";
            }
            if (position == 2) {
                return "Histórico";
            }
            return null;
        }



    }


////////////////   Menu Actions   ////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Add any device elements we've discovered to the overflow menu
        /*for (int i=0; i < mDevices.size(); i++) {
            BluetoothDevice device = mDevices.valueAt(i);
            menu.add(0, mDevices.keyAt(i), 0, device.getName());
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Button -- BLE SCAN
            case R.id.action_scan:
                /*
                 * We need to enforce that Bluetooth is first enabled, and ask the
                 * user to enable it if they have not done so.
                 */
                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    //Bluetooth is disabled
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                    //finish();
                }
                mDevices.clear();
                startScan(); // look for BLE devices
                break;

            // Button -- Settings
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;

            // Button -- Logout
            case R.id.action_logout:
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Terminar sessão")
                        .setIcon(R.mipmap.ic_logout)
                        .setMessage("Deseja terminar a sessão?")
                        .setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(((Dialog) dialog).getContext(), StartActivity.class));

                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Terminar sessão")
                .setIcon(R.mipmap.ic_logout)
                .setMessage("Deseja terminar a sessão?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(((Dialog) dialog).getContext(), StartActivity.class));
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }


////////////////   BLE   ////////////////

    private Runnable mStopRunnable = new Runnable() {
        @Override
        public void run() {
            stopScan();
        }
    };

    private Runnable mStartRunnable = new Runnable() {
        @Override
        public void run() {
            startScan();
        }
    };

    private void startScan() {
        mBluetoothAdapter.startLeScan(this);
        setProgressBarIndeterminateVisibility(true);

        mHandler.postDelayed(mStopRunnable, 2500); // Time of scan = 2.5 s
    }

    private void stopScan() {
        mBluetoothAdapter.stopLeScan(this);
        setProgressBarIndeterminateVisibility(false);
        ConnectToDeviceDialog(); // after scanning
    }

    /* Connect to the device */
    private void ConnectToDeviceDialog() {

        if(mDevices.size() == 0) {
            Toast.makeText(this, "Nenhum dispositivo BLE encontrado...", Toast.LENGTH_LONG).show();
        }
        else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Escolha o dispositivo BLE desejado:");

            ListView deviceList = new ListView(this);
            for (int i=0; i < mDevices.size(); i++) {
                BluetoothDevice device = mDevices.valueAt(i);
                String deviceFound = "\t\t\t\t\t" + device.getName();
                ArrayAdapter<String> deviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[] {deviceFound});
                deviceList.setAdapter(deviceAdapter);
            }
            builder.setView(deviceList);
            final Dialog dialog = builder.create();
            dialog.show();

            deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int i = position;
                    //Obtain the discovered device to connect with
                    BluetoothDevice device = mDevices.valueAt(i);
                        Log.i(TAG, "Connecting to " + device.getName());
                    /*
                     * Make a connection with the device using the special LE-specific
                     * connectGatt() method, passing in a callback for GATT events
                     */
                    if(!device.equals(null)) {
                        mConnectedGatt = device.connectGatt(getApplicationContext(), false, mGattCallback);
                        //Display progress UI
                        mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "A estabelecer ligação com " + device.getName() + "..."));
                        dialog.dismiss();
                     }

                }

            });
        }
    }


    /* BluetoothAdapter.LeScanCallback */
    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        Log.i(TAG, "New LE Device: " + device.getName() + " @ " + rssi);
        /*
         * We are looking for SensorTag devices only, so validate the name
         * that each device reports before adding it to our collection
         */
        if (DEVICE_NAME.equals(device.getName())) {
            mDevices.put(device.hashCode(), device);
            //Update the overflow menu
            invalidateOptionsMenu();
        }
    }

    /*
    * In this callback, we've created a bit of a state machine to enforce that only
    * one characteristic be read or written at a time until all of our sensors
    * are enabled and we are registered to get notifications.
    */
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        /* State Machine Tracking */
        private int mState = 0;

        private void reset() { mState = 0; }

        private void advance() { mState++; }

        /*
         * Send an enable command to each sensor by writing a configuration
         * characteristic.  This is specific to the SensorTag to keep power
         * low by disabling sensors you aren't using.
         */
        private void enableNextSensor(BluetoothGatt gatt) {
            BluetoothGattCharacteristic characteristic;
            switch (mState) {
                case 0:
                    Log.d(TAG, "Enabling Temperature");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(TEMP_WRITE);
                    characteristic.setValue(new byte[] {(byte) 0xE1});
                    break;
                case 1:
                    Log.d(TAG, "Enabling Pressure");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(PRESSAO_WRITE);
                    characteristic.setValue(new byte[] {(byte) 0xE1});
                    break;
                case 2:
                    Log.d(TAG, "Enabling Humidity");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(HUM_WRITE);
                    characteristic.setValue(new byte[] {(byte) 0xE1});
                    break;
                case 3:
                    Log.d(TAG, "Enabling Luminosity");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(LUM_WRITE);
                    characteristic.setValue(new byte[] {(byte) 0xE1});
                    break;
                case 4:
                    Log.d(TAG, "Enabling Moisture");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(HSOLO_WRITE);
                    characteristic.setValue(new byte[] {(byte) 0xE1});
                    break;
                default:
                    mHandler.sendEmptyMessage(MSG_DISMISS);
                    Log.i(TAG, "All Sensors Enabled");
                    return;
            }

            gatt.writeCharacteristic(characteristic);
        }

        /*
         * Read the data characteristic's value for each sensor explicitly
         */
        private void readNextSensor(BluetoothGatt gatt) {
            BluetoothGattCharacteristic characteristic;
            switch (mState) {
                case 0:
                    Log.d(TAG, "Reading Temperature");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(TEMP_READ);
                    break;
                case 1:
                    Log.d(TAG, "Reading Pressure");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(PRESSAO_READ);
                    break;
                case 2:
                    Log.d(TAG, "Reading Humidity");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(HUM_READ);
                    break;
                case 3:
                    Log.d(TAG, "Reading Luminosity");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(LUM_READ);
                    break;
                case 4:
                    Log.d(TAG, "Reading Moisture");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(HSOLO_READ);
                    break;
                default:
                    mHandler.sendEmptyMessage(MSG_DISMISS);
                    Log.i(TAG, "All Sensors Enabled");
                    return;
            }

            gatt.readCharacteristic(characteristic);
        }

        /*
         * Enable notification of changes on the data characteristic for each sensor
         * by writing the ENABLE_NOTIFICATION_VALUE flag to that characteristic's
         * configuration descriptor.
         */
        private void setNotifyNextSensor(BluetoothGatt gatt) {
            BluetoothGattCharacteristic characteristic;
            switch (mState) {
                case 0:
                    Log.d(TAG, "Set notify temperature cal");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(TEMP_READ);
                    break;
                case 1:
                    Log.d(TAG, "Set notify Pressure cal");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(PRESSAO_READ);
                    break;
                case 2:
                    Log.d(TAG, "Set notify Humidity cal");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(HUM_READ);
                    break;
                case 3:
                    Log.d(TAG, "Set notify Luminosity cal");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(LUM_READ);
                    break;
                case 4:
                    Log.d(TAG, "Set notify Moisture cal");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(HSOLO_READ);
                    break;
                default:
                    mHandler.sendEmptyMessage(MSG_DISMISS);
                    Log.i(TAG, "All Sensors Enabled");
                    return;
            }

            //Enable local notifications
            gatt.setCharacteristicNotification(characteristic, true);
            //Enabled remote notifications
            BluetoothGattDescriptor desc = characteristic.getDescriptor(CLIENT_UUID);
            desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(desc);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.d(TAG, "Connection State Change: "+status+" -> "+connectionState(newState));
            if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {
                /*
                 * Once successfully connected, we must next discover all the services on the
                 * device before we can read and write their characteristics.
                 */
                gatt.discoverServices();
                mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Discovering Services..."));
            } else if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_DISCONNECTED) {
                /*
                 * If at any point we disconnect, send a message to clear the weather values
                 * out of the UI
                 */
                mHandler.sendEmptyMessage(MSG_CLEAR);
            } else if (status != BluetoothGatt.GATT_SUCCESS) {
                /*
                 * If there is a failure at any stage, simply disconnect
                 */
                gatt.disconnect();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.d(TAG, "Services Discovered: "+status);
            mHandler.sendMessage(Message.obtain(null, MSG_PROGRESS, "Enabling Sensors..."));
            /*
             * With services discovered, we are going to reset our state machine and start
             * working through the sensors we need to enable
             */
            reset();
            enableNextSensor(gatt);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            //For each read, pass the data up to the UI thread to update the display
            if (TEMP_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_TEMP, characteristic));
            }
            if (PRESSAO_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_PR, characteristic));
            }
            if (HUM_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_HUM, characteristic));
            }
            if (LUM_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_LUM, characteristic));
            }
            if (HSOLO_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_HSOLO, characteristic));
            }


            //After reading the initial value, next we enable notifications
            setNotifyNextSensor(gatt);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            //After writing the enable flag, next we read the initial value
            readNextSensor(gatt);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            /*
             * After notifications are enabled, all updates from the device on characteristic
             * value changes will be posted here.  Similar to read, we hand these up to the
             * UI thread to update the display.
             */
            if (TEMP_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_TEMP, characteristic));
            }
            if (PRESSAO_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_PR, characteristic));
            }
            if (HUM_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_HUM, characteristic));
            }
            if (LUM_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_LUM, characteristic));
            }
            if (HSOLO_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_HSOLO, characteristic));
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            //Once notifications are enabled, we move to the next sensor and start over with enable
            advance();
            enableNextSensor(gatt);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            Log.d(TAG, "Remote RSSI: "+rssi);
        }

        private String connectionState(int status) {
            switch (status) {
                case BluetoothProfile.STATE_CONNECTED:
                    return "Connected";
                case BluetoothProfile.STATE_DISCONNECTED:
                    return "Disconnected";
                case BluetoothProfile.STATE_CONNECTING:
                    return "Connecting";
                case BluetoothProfile.STATE_DISCONNECTING:
                    return "Disconnecting";
                default:
                    return String.valueOf(status);
            }
        }
    };

    /*
     * We have a Handler to process event results on the main thread
     */
    private static final int MSG_TEMP = 101;
    private static final int MSG_PR = 102;
    private static final int MSG_HUM = 103;
    private static final int MSG_LUM = 104;
    private static final int MSG_HSOLO = 105;

    private static final int MSG_PROGRESS = 201;
    private static final int MSG_DISMISS = 202;
    private static final int MSG_CLEAR = 301;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            BluetoothGattCharacteristic characteristic;
            switch (msg.what) {
                case MSG_TEMP:
                    characteristic = (BluetoothGattCharacteristic) msg.obj;
                    if (characteristic.getValue() == null) {
                        Log.w(TAG, "Error obtaining temperature value");
                        return;
                    }
                    updateTempValues(characteristic);
                    break;
                case MSG_PR:
                    characteristic = (BluetoothGattCharacteristic) msg.obj;
                    if (characteristic.getValue() == null) {
                        Log.w(TAG, "Error obtaining pressure value");
                        return;
                    }
                    updatePresValues(characteristic);
                    break;
                case MSG_HUM:
                    characteristic = (BluetoothGattCharacteristic) msg.obj;
                    if (characteristic.getValue() == null) {
                        Log.w(TAG, "Error obtaining humidity value");
                        return;
                    }
                    updateHumValues(characteristic);
                    break;
                case MSG_LUM:
                    characteristic = (BluetoothGattCharacteristic) msg.obj;
                    if (characteristic.getValue() == null) {
                        Log.w(TAG, "Error obtaining luminosity value");
                        return;
                    }
                    updateLumValues(characteristic);
                    break;
                case MSG_HSOLO:
                    characteristic = (BluetoothGattCharacteristic) msg.obj;
                    if (characteristic.getValue() == null) {
                        Log.w(TAG, "Error obtaining moisture value");
                        return;
                    }
                    updateHumSoloValues(characteristic);
                    break;
                case MSG_PROGRESS:
                    mProgress.setMessage((String) msg.obj);
                    if (!mProgress.isShowing()) {
                        mProgress.show();
                    }
                    break;
                case MSG_DISMISS:
                    mProgress.hide();
                    break;
                case MSG_CLEAR:
                    mTemperature = "null";
                    mPressão = "null";
                    mHumAr = "null";
                    mLuminosidade = "null";
                    mHumSolo = "null";
                    break;
            }
            populateHistoricoDb();
        }
    };


    /* Methods to extract sensor data and update the sensors' last known value */

        private void updateTempValues(BluetoothGattCharacteristic characteristic) {
            double temp = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT32, 0);
            mTemperature = String.valueOf((String.format("%.2f", temp / 100)));
                Log.i(TAG, "Temp Caract: " + mTemperature);
                //Toast.makeText(this, "Temp Caract: " + mTemperature, Toast.LENGTH_LONG).show();
            getMyTempUI();
            //alertaTemperatura();

        }

        private void updatePresValues(BluetoothGattCharacteristic characteristic) {
            double pluv = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT32, 0);
            mPressão = String.valueOf((String.format("%.2f", pluv/100)));
                Log.i(TAG, "Press Caract: " + mPressão);
                //Toast.makeText(this, "Press Caract: " + mPressão, Toast.LENGTH_SHORT).show();

            getMyPres();
        }

        private void updateHumValues(BluetoothGattCharacteristic characteristic) {
            double hum = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 0);
            mHumAr = String.valueOf((String.format("%.2f", hum/1000)));
                Log.i(TAG, "HumAr Caract: " + mHumAr);
                //Toast.makeText(this, "HumAr Caract: " + mHumAr, Toast.LENGTH_SHORT).show();

            getMyHumAr();
        }

        private void updateLumValues(BluetoothGattCharacteristic characteristic) {
            double lum = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT32, 0);
            mLuminosidade = String.valueOf((String.format("%.2f", lum)));
                Log.i(TAG, "Lum Caract: " + mLuminosidade);
                //Toast.makeText(this, "Lum Caract: " + mLuminosidade, Toast.LENGTH_SHORT).show();

            getMyLum();
        }

        private void updateHumSoloValues(BluetoothGattCharacteristic characteristic) {
            double hum = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 0);
            mHumSolo = String.valueOf((String.format("%.2f", hum/100)));
                Log.i(TAG, "HumSolo Caract: " + mHumSolo);
                //Toast.makeText(this, "Hum Solo Caract: " + mHumSolo, Toast.LENGTH_SHORT).show();

            getMyHumSolo();
        }

        // Send data to display in tab Sensores (currently not used)
        public String getMyTempUI() {
            return mTemperature;
        }
        public String getMyPres() {
            return mPressão;
        }
        public String getMyHumAr() {
            return mHumAr;
        }
        public String getMyLum() {
            return mLuminosidade;
        }
        public String getMyHumSolo() {
            return mHumSolo;
        }

        // Populate DB: Historico tables
        private void populateHistoricoDb()
        {
            if(mTemperature != null && mLuminosidade != null && mHumSolo != null && mHumAr != null && mPressão != null) {
                String data = "12-12-2015, 18h34m";     // Manually inserted for now

                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_1", mTemperature, mLuminosidade, mHumSolo, mHumAr, mPressão, data);
            }
        }


}

