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
import android.os.AsyncTask;
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
import android.text.Html;
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

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements BluetoothAdapter.LeScanCallback {

    private static boolean RUN_ONCE = true;
    Boolean log;

    //ProgressDialog progressBle;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private MyPagerAdapter mAdapter;

    private static int flag = 0;

    private ArrayList<String> recData = new ArrayList<String>();
    private int nbytes=0;
    private int fileSize = -1;

    private boolean move = false;
    private int battery = 0;

    private static final String TAG = "BluetoothGattActivity";

    private static final String DEVICE_NAME_1 = "UVA-1";
    private static final String DEVICE_NAME_2 = "UVA-2";
    private static final String DEVICE_NAME_3 = "UVA-3";

        private static final UUID SERVICE = UUID.fromString("00001120-2222-2111-1aad-22faa544a3dd");

        private static final UUID ALL_READ = UUID.fromString("00001143-2222-2111-1aad-22faa544a3dd");
        private static final UUID ALL_WRITE = UUID.fromString("00001144-2222-2111-1aad-22faa544a3dd");

        private static final UUID GPS_READ = UUID.fromString("00001145-2222-2111-1aad-22faa544a3dd");

        private static final UUID ALT_READ = UUID.fromString("00001147-2222-2111-1aad-22faa544a3dd");
        private static final UUID ALT_WRITE = UUID.fromString("00001148-2222-2111-1aad-22faa544a3dd");

        // UUID for the BTLE client characteristic which is necessary for notifications.
        public static UUID CLIENT_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

        private BluetoothAdapter mBluetoothAdapter;
        private SparseArray<BluetoothDevice> mDevices;

        private BluetoothGatt mConnectedGatt;

        BluetoothDevice connectDevice;

        String alertaDbTask, id, temp, lum, humS, humA, pluv, time;
        Boolean alertasOn, boxTemp, boxLum, boxHum, boxPluv, boxOutros, boxPraga1, boxPraga2, boxPraga3, boxPraga4;


        private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProgressBarIndeterminate(true);

/*
        SharedPreferences prefs2 = getSharedPreferences("GPS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = prefs2.edit();

        editor2.putString("GpsLat1", null);
        editor2.putString("GpsLng1", null);
        editor2.putString("GpsLat2", null);
        editor2.putString("GpsLng2", null);
        editor2.putString("GpsLat3", null);
        editor2.putString("GpsLng3", null);
        editor2.commit();


        SharedPreferences prefs3 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = prefs3.edit();
        editor3.putBoolean("isAlerta_1", false);
        editor3.putBoolean("isAlerta_2", false);
        editor3.putBoolean("isAlerta_3", false);
        editor3.commit();
*/
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
        getSupportActionBar().setIcon(R.mipmap.app_logo);

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
        log = prefs.getBoolean("isLogged", false);

        /*
         * When user is LOGGED, the Local DB is deleted and updated with the Online DB
         */
        if(log && RUN_ONCE)
        {
            RUN_ONCE = false;
            /*
             * We first need to enforce that an Internet connection is existent, and ask the
             * user to enable one if they have not done so.
             */
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()) {
                // Online DB - get new data from all online tables (pandlet1_values, pandlet2_values, pandlet3_values)
                    BackgroundOnlineDbTask backgroundOnlineDbTaskA = new BackgroundOnlineDbTask(this);
                    backgroundOnlineDbTaskA.execute("get_info_1");

                    BackgroundOnlineDbTask backgroundOnlineDbTaskB = new BackgroundOnlineDbTask(this);
                    backgroundOnlineDbTaskB.execute("get_info_2");

                    BackgroundOnlineDbTask backgroundOnlineDbTaskC = new BackgroundOnlineDbTask(this);
                    backgroundOnlineDbTaskC.execute("get_info_3");

                Toast.makeText(this, "Os registos do Histórico foram atualizados!", Toast.LENGTH_LONG).show();
            }
            else {
                Intent enableInternetIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(enableInternetIntent);
            }
        }
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
        MenuItem ble = menu.findItem(R.id.action_scan);
        if(log) {
            ble.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem ble = menu.findItem(R.id.action_scan);
        if(log) {
            ble.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
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
                        .setTitle("Sair de UVAS")
                        .setIcon(R.mipmap.ic_logout)
                        .setMessage("Deseja sair da aplicação?")
                        .setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", true);
                                startActivity(intent);
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
                .setTitle("Sair de UVAS")
                .setIcon(R.mipmap.ic_logout)
                .setMessage("Deseja sair da aplicação?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this, StartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
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
            builder.setIcon(R.mipmap.ic_ble);

            ListView deviceList = new ListView(this);
            String[] devicesFound = new String[3];
                Log.i("mDEVICE", String.valueOf(mDevices.size()));
            for (int i=0; i < mDevices.size(); i++) {
                BluetoothDevice device = mDevices.valueAt(i);
                devicesFound[i] = "\t\t\t\t - " + device.getName();
               // String deviceFound = "\t\t\t\t - " + device.getName();
               // ArrayAdapter<String> deviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[] {deviceFound});
               // deviceList.setAdapter(deviceAdapter);
            }

            ArrayAdapter<String> deviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, devicesFound);
            deviceList.setAdapter(deviceAdapter);
            builder.setView(deviceList);

            final Dialog dialog = builder.create();
            dialog.show();

            deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int i = position;
                    //Obtain the discovered device to connect with
                    BluetoothDevice device = mDevices.valueAt(i);
                    connectDevice = device;
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
        if (DEVICE_NAME_1.equals(device.getName())) {
            mDevices.put(device.hashCode(), device);
            //Update the overflow menu
            invalidateOptionsMenu();
        }
        if (DEVICE_NAME_2.equals(device.getName())) {
            mDevices.put(device.hashCode(), device);
            //Update the overflow menu
            invalidateOptionsMenu();
        }
        if (DEVICE_NAME_3.equals(device.getName())) {
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
                    Log.d(TAG, "Sending GPS values");
                    readNextSensor(gatt);
                    return;
                case 2:
                    Log.d(TAG, "Sending SD values");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(ALL_WRITE);
                    characteristic.setValue(new byte[]{(byte) 0xE1});
                    break;
                case 1:
                    Log.d(TAG, "Sending Alerta values");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(ALT_WRITE);
                    characteristic.setValue(new byte[]{(byte) 0xE1});
                    break;
                default:
                    mHandler.sendEmptyMessage(MSG_DISMISS);
                    Log.i(TAG, "All Sensors Enabled WRITE");
                    return;
            }

            gatt.writeCharacteristic(characteristic);
        }

        /*
         * Read the data characteristic's value for each sensor explicitly
         */
        private void readNextSensor(BluetoothGatt gatt) {
            BluetoothGattCharacteristic characteristic = null;
            switch (mState) {
                case 0:
                    Log.d(TAG, "Reading GPS");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(GPS_READ);
                    break;
                case 2:
                    Log.d(TAG, "Reading SD");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(ALL_READ);
                    break;
                case 1:
                    Log.d(TAG, "Reading Alertas");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(ALT_READ);
                    break;
                default:
                    mHandler.sendEmptyMessage(MSG_DISMISS);
                    Log.i(TAG, "All Sensors Enabled READ");
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
                    Log.d(TAG, "Set notify GPS cal");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(GPS_READ);
                    break;
                case 2:
                    Log.d(TAG, "Set notify SD cal");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(ALL_READ);
                    break;
                case 1:
                    Log.d(TAG, "Set notify Alerta cal");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(ALT_READ);
                    break;
                default:
                    mHandler.sendEmptyMessage(MSG_DISMISS);
                    Log.i(TAG, "All Sensors Enabled NOTIFY");
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
            Log.d(TAG, "Connection State Change: " + status + " -> " + connectionState(newState));
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
            if (GPS_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_GPS, characteristic));
            }
            if (ALL_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_ALL, characteristic));
            }
            if (ALT_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_ALT, characteristic));
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
            if (GPS_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_GPS, characteristic));
            }
            if (ALL_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_ALL, characteristic));
            }
            if (ALT_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_ALT, characteristic));
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
    private static final int MSG_ALL = 101;
    private static final int MSG_GPS = 102;
    private static final int MSG_ALT = 103;

    private static final int MSG_PROGRESS = 201;
    private static final int MSG_DISMISS = 202;
    private static final int MSG_CLEAR = 301;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            BluetoothGattCharacteristic characteristic;
            switch (msg.what) {
                case MSG_ALL:
                    characteristic = (BluetoothGattCharacteristic) msg.obj;
                    if (characteristic.getValue() == null) {
                        Log.w(TAG, "Error obtaining SD values");
                        return;
                    }
                    updateSDValues(characteristic);
                    break;
                case MSG_GPS:
                    characteristic = (BluetoothGattCharacteristic) msg.obj;
                    if (characteristic.getValue() == null) {
                        Log.w(TAG, "Error obtaining GPS values");
                        return;
                    }
                    updateGPSValues(characteristic);
                    break;
                case MSG_ALT:
                    characteristic = (BluetoothGattCharacteristic) msg.obj;
                    if (characteristic.getValue() == null) {
                        Log.w(TAG, "Error obtaining Alertas");
                        return;
                    }
                    updateALTValues(characteristic);
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
                    break;
            }
        }
    };

    // Get external ALERTAS (pandlet)
    private void updateALTValues(BluetoothGattCharacteristic characteristic){
        byte[] tmp = new byte[1];

        int val;
        tmp = characteristic.getValue();

        val = (int) tmp[0];
        Log.w(TAG, "Val Total " + val);
        if( val < 0 ) {
            move = true;
            val += 128;
        }
        Log.w(TAG, "Val bat " + val);
        battery = val;

        if(move) {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Alerta de movimento")
                    .setIcon(R.mipmap.ic_aviso)
                    .setMessage("Foram detetados movimentos inesperados no dispositivo: " + connectDevice.getName() +
                            "\nDiriga-se ao local, e em caso de problemas, por favor contacte o suporte técnico.")
                    .setNegativeButton("Ok", null)
                    .show();
        }
        // for testing purposes (70%)
        if(battery < 70) {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Alerta de bateria")
                    .setIcon(R.mipmap.ic_aviso)
                    .setMessage("A bateria do dispositivo " +connectDevice.getName()+ "está fraca." +
                            "\nPor favor contacte o suporte técnico.")
                    .setNegativeButton("Ok", null)
                    .show();
        }
    }



    // Get GPS (Lat, Lng) from SD Card
    private void updateGPSValues(BluetoothGattCharacteristic characteristic) {

        byte tmp[];
        byte lat[] = new byte[4];
        byte lng[] = new byte[4];

        tmp = characteristic.getValue();
        lat = Arrays.copyOfRange(tmp, 0, 4);
        lng = Arrays.copyOfRange(tmp, 4, 8);

        Log.i(TAG, "GPS Caract: " + Arrays.toString(tmp));

        if (DEVICE_NAME_1.equals(connectDevice.getName())) {

            int Lat = bytearray2int(lat);
            double finalLat = ((double) (int) Lat)/10000;
            String sLat = String.valueOf(finalLat);

            int Lng = bytearray2int(lng);
            double finalLng = ((double) (int) Lng)/10000;
            String sLng = String.valueOf(finalLng);

            SharedPreferences prefs2 = getSharedPreferences("GPS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();
            editor2.putString("GpsLat1", sLat);
            editor2.putString("GpsLng1", sLng);
            editor2.commit();

                Toast.makeText(this, "Coordenadas GPS associadas à Zona 1 com sucesso!", Toast.LENGTH_SHORT).show();

            Log.i(TAG, "GPS Lat 1: " + sLat);
            Log.i(TAG, "GPS Long 1: " + sLng);
        }
        else if (DEVICE_NAME_2.equals(connectDevice.getName())) {

            int Lat = bytearray2int(lat);
            double finalLat = ((double) (int) Lat)/10000;
            String sLat = String.valueOf(finalLat);

            int Lng = bytearray2int(lng);
            double finalLng = ((double) (int) Lng)/10000;
            String sLng = String.valueOf(finalLng);

            SharedPreferences prefs2 = getSharedPreferences("GPS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();
            editor2.putString("GpsLat2", sLat);
            editor2.putString("GpsLng2", sLng);
            editor2.commit();

                Toast.makeText(this, "Coordenadas GPS associadas à Zona 2 com sucesso!", Toast.LENGTH_SHORT).show();

            Log.i(TAG, "GPS Lat 2: " + sLat);
            Log.i(TAG, "GPS Long 2: " + sLng);
        }
        else if (DEVICE_NAME_3.equals(connectDevice.getName())) {

            int Lat = bytearray2int(lat);
            double finalLat = ((double) (int) Lat)/10000;
            String sLat = String.valueOf(finalLat);

            int Lng = bytearray2int(lng);
            double finalLng = ((double) (int) Lng)/10000;
            String sLng = String.valueOf(finalLng);

            SharedPreferences prefs2 = getSharedPreferences("GPS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();
            editor2.putString("GpsLat3", sLat);
            editor2.putString("GpsLng3", sLng);
            editor2.commit();

                Toast.makeText(this, "Coordenadas GPS associadas à Zona 3 com sucesso!", Toast.LENGTH_SHORT).show();

            Log.i(TAG, "GPS Lat 3: " + sLat);
            Log.i(TAG, "GPS Long 3: " + sLng);
        }
    }


    private void updateSDValues(BluetoothGattCharacteristic characteristic) {

        //progressBle = new ProgressDialog(this);
        //progressBle.setMessage("Por favor aguarde, a receber dados ...");
        //progressBle.show();

        byte[] tmp = new byte[20];
        String aux = "";
        tmp = characteristic.getValue();

        if(nbytes+20 > fileSize && flag > 1 ) {
            if(nbytes < fileSize) {
                int dif = nbytes + 20 - fileSize;
                byte[] tmp2 = new byte[20 - dif];
                for(int i=0; i < (20-dif); i++){
                    tmp2[i] = tmp[i];
                }
                aux = new String(tmp2);
            }
        }
        else{
            aux = new String(tmp);
        }
        //Log.i(TAG, "SD Caract: " + aux);
        if (nbytes == 0 && flag > 0){
            fileSize = Integer.parseInt(aux);
            Log.i(TAG, "file size: " + fileSize);
        }
        else if(nbytes < fileSize)
            recData.add(aux);
        if(flag > 0)
            nbytes += aux.length();
        //Log.i(TAG, "nbytes: " + nbytes);
        if(nbytes >= fileSize && flag != 0)
            readSD();
        flag++;
    }

    private void readSD() {
        String allData = "";
        String[] data;

        for (int i = 0; i < recData.size(); i++) {
            allData += recData.get(i);
        }

        allData = allData.substring(1);
        data = allData.split("\n");

        Log.i(TAG, "All Data " + allData);

        for (int i = 0; i < data.length; i++) {
            Log.i(TAG, "Data " + (i + 1) + ": " + data[i]);

            id = data[i].substring(0, 1);
            Log.i("SD CARD", "ID = " +id);

            int t = ((Integer.parseInt(data[i].substring(11, 16)) / 100)) - 9; //
            temp = String.valueOf(t);
            Log.i("SD CARD", "Temp = " +temp);

            int l = (Integer.parseInt(data[i].substring(16, 22)));
            lum = String.valueOf(l);
            Log.i("SD CARD", "Lum = " +lum);

            int hS = ((Integer.parseInt(data[i].substring(22, 25))) /10);
            humS = String.valueOf(hS);
            Log.i("SD CARD", "Hum Solo = " +humS);

            int hA = ((Integer.parseInt(data[i].substring(25, 28))) /10);
            humA = String.valueOf(hA);
            Log.i("SD CARD", "Hum Ar = " +humA);

            String pluvBool = data[i].substring(28, 29);
            if(pluvBool.matches("1")) {
                pluv = "sim";
            } else if(pluvBool.matches("0")) {
                pluv = "não";
            }
            Log.i("SD CARD", "Pluv = " +pluv);

            long ts = Long.valueOf(data[i].substring(1, 11))*1000; // it needs to be in milliseconds
            Date df = new java.util.Date(ts);
            time = new SimpleDateFormat("dd/MM/yyyy, HH:mm").format(df);
            Log.i("SD CARD", "Timestamp = " +time);

        // Write data retrieved from SD into Local BD
            if(id.matches("1")) {
                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_1", temp, lum, humS, humA, pluv, time);

                // ALERTAS
                SharedPreferences prefs = getSharedPreferences("DataSettingsState", Context.MODE_PRIVATE);
                alertasOn = prefs.getBoolean("alertasOn", false);
                boxTemp = prefs.getBoolean("boxTemp", false);
                boxLum = prefs.getBoolean("boxLum", false);
                boxHum = prefs.getBoolean("boxHum", false);
                boxPluv = prefs.getBoolean("boxPluv", false);
                //boxOutros = prefs.getBoolean("boxOutros", false);
                //boxPraga1 = prefs.getBoolean("boxPraga1", false);
                //boxPraga2 = prefs.getBoolean("boxPraga2", false);
                //boxPraga3 = prefs.getBoolean("boxPraga3", false);
                //boxPraga4 = prefs.getBoolean("boxPraga4", false);
                if(alertasOn) {
                    alertaDbTask = "add_alerta_1";
                    if(boxTemp) { alertaTemperatura(); }
                    if(boxLum) { alertaLuminosidade(); }
                    if(boxHum) { alertaHumidadeSolo(); alertaHumidadeAr(); }
                    if(boxPluv) { alertaPluviosidade(); }
                    //if(boxOutros) { alertaOutros(); }
                    //if(boxPraga1) { alertaPraga1(); }
                    //if(boxPraga2) { alertaPraga2(); }
                    //if(boxPraga3) { alertaPraga3(); }
                    //if(boxPraga4) { alertaPraga4(); }
                }
            }
            else if(id.matches("2")) {
                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_2", temp, lum, humS, humA, pluv, time);

                // ALERTAS
                SharedPreferences prefs = getSharedPreferences("DataSettingsState", Context.MODE_PRIVATE);
                alertasOn = prefs.getBoolean("alertasOn", false);
                boxTemp = prefs.getBoolean("boxTemp", false);
                boxLum = prefs.getBoolean("boxLum", false);
                boxHum = prefs.getBoolean("boxHum", false);
                boxPluv = prefs.getBoolean("boxPluv", false);
                //boxOutros = prefs.getBoolean("boxOutros", false);
                //boxPraga1 = prefs.getBoolean("boxPraga1", false);
                //boxPraga2 = prefs.getBoolean("boxPraga2", false);
                //boxPraga3 = prefs.getBoolean("boxPraga3", false);
                //boxPraga4 = prefs.getBoolean("boxPraga4", false);
                if(alertasOn) {
                    alertaDbTask = "add_alerta_2";
                    if(boxTemp) { alertaTemperatura(); }
                    if(boxLum) { alertaLuminosidade(); }
                    if(boxHum) { alertaHumidadeSolo(); alertaHumidadeAr(); }
                    if(boxPluv) { alertaPluviosidade(); }
                    //if(boxOutros) { alertaOutros(); }
                    //if(boxPraga1) { alertaPraga1(); }
                    //if(boxPraga2) { alertaPraga2(); }
                    //if(boxPraga3) { alertaPraga3(); }
                    //if(boxPraga4) { alertaPraga4(); }
                }
            }
            else if(id.matches("3")) {
                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_3", temp, lum, humS, humA, pluv, time);

                // ALERTAS
                SharedPreferences prefs = getSharedPreferences("DataSettingsState", Context.MODE_PRIVATE);
                alertasOn = prefs.getBoolean("alertasOn", false);
                boxTemp = prefs.getBoolean("boxTemp", false);
                boxLum = prefs.getBoolean("boxLum", false);
                boxHum = prefs.getBoolean("boxHum", false);
                boxPluv = prefs.getBoolean("boxPluv", false);
                //boxOutros = prefs.getBoolean("boxOutros", false);
                //boxPraga1 = prefs.getBoolean("boxPraga1", false);
                //boxPraga2 = prefs.getBoolean("boxPraga2", false);
                //boxPraga3 = prefs.getBoolean("boxPraga3", false);
                //boxPraga4 = prefs.getBoolean("boxPraga4", false);
                if(alertasOn) {
                    alertaDbTask = "add_alerta_3";
                    if(boxTemp) { alertaTemperatura(); }
                    if(boxLum) { alertaLuminosidade(); }
                    if(boxHum) { alertaHumidadeSolo(); alertaHumidadeAr(); }
                    if(boxPluv) { alertaPluviosidade(); }
                    //if(boxOutros) { alertaOutros(); }
                    //if(boxPraga1) { alertaPraga1(); }
                    //if(boxPraga2) { alertaPraga2(); }
                    //if(boxPraga3) { alertaPraga3(); }
                    //if(boxPraga4) { alertaPraga4(); }
                }
            }
        }

        //progressBle.dismiss();

        SharedPreferences prefs = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
        Boolean alerta1 = prefs.getBoolean("isAlerta_1", false);
        Boolean alerta2 = prefs.getBoolean("isAlerta_2", false);
        Boolean alerta3 = prefs.getBoolean("isAlerta_3", false);

        String zonaAlerta = "";

        if(alerta1){
            zonaAlerta += "\n\t\t Zona 1";
        }
        if(alerta2){
            zonaAlerta += "\n\t\t Zona 2";
        }
        if(alerta3){
            zonaAlerta += "\n\t\t Zona 3";
        }

        if(alerta1 || alerta2 || alerta3) {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Alerta")
                    .setIcon(R.mipmap.ic_aviso)
                    .setMessage("Foram emitidos alertas na(s) Zona(s): " + zonaAlerta)
                    .setNegativeButton("Ok", null)
                    .show();
        }

        SharedPreferences prefs3 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = prefs3.edit();
        editor3.putBoolean("isAlerta_1", false);
        editor3.putBoolean("isAlerta_2", false);
        editor3.putBoolean("isAlerta_3", false);
        editor3.commit();
    }


    ////////////////   ALERTAS   ////////////////

    private void alertaTemperatura() {
        SharedPreferences prefs = getSharedPreferences("DataTemperatura", Context.MODE_PRIVATE);
        String minTemp = prefs.getString("minTemperatura", "0");
        double min_temp = Double.parseDouble(minTemp);
        String maxTemp = prefs.getString("maxTemperatura", "0");
        double max_temp = Double.parseDouble(maxTemp);

        double TMP;
        if(temp != null && !temp.isEmpty()) {
            TMP = Double.parseDouble(temp);
        }else {
            TMP = 0.0;
        }

        if(TMP != 0.0) {
            String type = "Temperatura";
            String alert1 = "Valor recebido é inferior ao valor mínimo desejado:  " +minTemp+ " ºC";
            String alert2 = "Valor recebido é superior ao valor máximo desejado:  " +maxTemp+ " ºC";
            String value = temp + " ºC";
            String date = time;

            if (TMP < min_temp) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);

                SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();

                if(id.matches("1")) {
                    editor2.putBoolean("isAlerta_1", true);
                    editor2.commit();
                } else if(id.matches("2")) {
                    editor2.putBoolean("isAlerta_2", true);
                    editor2.commit();
                } else if(id.matches("3")) {
                    editor2.putBoolean("isAlerta_3", true);
                    editor2.commit();
                }
            }
            else if (TMP > max_temp) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert2, value, date);

                SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();

                if(id.matches("1")) {
                    editor2.putBoolean("isAlerta_1", true);
                    editor2.commit();
                } else if(id.matches("2")) {
                    editor2.putBoolean("isAlerta_2", true);
                    editor2.commit();
                } else if(id.matches("3")) {
                    editor2.putBoolean("isAlerta_3", true);
                    editor2.commit();
                }
            }
        }
    }

    private void alertaLuminosidade() {
        SharedPreferences prefs = getSharedPreferences("DataLuminosidade", Context.MODE_PRIVATE);
        String minLum = prefs.getString("minLuminosidade", "0");
        double min_lum = Double.parseDouble(minLum);
        String maxLum = prefs.getString("maxLuminosidade", "0");
        double max_lum = Double.parseDouble(maxLum);

        double LUM;
        if(lum != null && !lum.isEmpty()) {
            LUM = Double.parseDouble(lum);
        }else {
            LUM = 0.0;
        }

        if(LUM != 0.0) {
            String type = "Luminosidade";
            String alert1 = "Valor recebido é inferior ao valor mínimo desejado:  " +minLum+ " lux";
            String alert2 = "Valor recebido é superior ao valor máximo desejado:  " +maxLum+ " lux";
            String value = lum + " lux";
            String date = time;

            if (LUM < min_lum) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);

                SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();

                if(id.matches("1")) {
                    editor2.putBoolean("isAlerta_1", true);
                    editor2.commit();
                } else if(id.matches("2")) {
                    editor2.putBoolean("isAlerta_2", true);
                    editor2.commit();
                } else if(id.matches("3")) {
                    editor2.putBoolean("isAlerta_3", true);
                    editor2.commit();
                }
            } else if (LUM > max_lum) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert2, value, date);

                SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();

                if(id.matches("1")) {
                    editor2.putBoolean("isAlerta_1", true);
                    editor2.commit();
                } else if(id.matches("2")) {
                    editor2.putBoolean("isAlerta_2", true);
                    editor2.commit();
                } else if(id.matches("3")) {
                    editor2.putBoolean("isAlerta_3", true);
                    editor2.commit();
                }
            }
        }
    }

    private void alertaHumidadeSolo() {
        SharedPreferences prefs = getSharedPreferences("DataHumidade", Context.MODE_PRIVATE);
        String minHumSolo = prefs.getString("minHumidadeSolo", "0");
        double min_humSolo = Double.parseDouble(minHumSolo);
        String maxHumSolo = prefs.getString("maxHumidadeSolo", "0");
        double max_humSolo = Double.parseDouble(maxHumSolo);

        double HSOLO;
        if(humS != null && !humS.isEmpty()) {
            HSOLO = Double.parseDouble(humS);
        }else {
            HSOLO = 0.0;
        }

        if(HSOLO != 0.0) {
            String type = "Humidade (solo)";
            String alert1 = "Valor recebido é inferior ao valor mínimo desejado:  " +minHumSolo+ " %";
            String alert2 = "Valor recebido é superior ao valor máximo desejado:  " +maxHumSolo+ " %";
            String value = humS + " %";
            String date = time;

            if (HSOLO < min_humSolo) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);

                SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();

                if(id.matches("1")) {
                    editor2.putBoolean("isAlerta_1", true);
                    editor2.commit();
                } else if(id.matches("2")) {
                    editor2.putBoolean("isAlerta_2", true);
                    editor2.commit();
                } else if(id.matches("3")) {
                    editor2.putBoolean("isAlerta_3", true);
                    editor2.commit();
                }
            } else if (HSOLO > max_humSolo) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert2, value, date);

                SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();

                if(id.matches("1")) {
                    editor2.putBoolean("isAlerta_1", true);
                    editor2.commit();
                } else if(id.matches("2")) {
                    editor2.putBoolean("isAlerta_2", true);
                    editor2.commit();
                } else if(id.matches("3")) {
                    editor2.putBoolean("isAlerta_3", true);
                    editor2.commit();
                }
            }
        }
    }

    private void alertaHumidadeAr() {
        SharedPreferences prefs = getSharedPreferences("DataHumidade", Context.MODE_PRIVATE);
        String minHumAr = prefs.getString("minHumidadeAr", "0");
        double min_humAr = Double.parseDouble(minHumAr);
        String maxHumAr = prefs.getString("maxHumidadeAr", "0");
        double max_humAr = Double.parseDouble(maxHumAr);

        double HAR;
        if(humA != null && !humA.isEmpty()) {
            HAR = Double.parseDouble(humA);
        }else {
            HAR = 0.0;
        }

        if(HAR != 0.0) {
            String type = "Humidade (ar)";
            String alert1 = "Valor recebido é inferior ao valor mínimo desejado:  " +minHumAr+ " %";
            String alert2 = "Valor recebido é superior ao valor máximo desejado:  " +maxHumAr+ " %";
            String value = humA + " %";
            String date = time;

            if (HAR < min_humAr) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);

                SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();

                if(id.matches("1")) {
                    editor2.putBoolean("isAlerta_1", true);
                    editor2.commit();
                } else if(id.matches("2")) {
                    editor2.putBoolean("isAlerta_2", true);
                    editor2.commit();
                } else if(id.matches("3")) {
                    editor2.putBoolean("isAlerta_3", true);
                    editor2.commit();
                }
            } else if (HAR > max_humAr) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert2, value, date);

                SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();

                if(id.matches("1")) {
                    editor2.putBoolean("isAlerta_1", true);
                    editor2.commit();
                } else if(id.matches("2")) {
                    editor2.putBoolean("isAlerta_2", true);
                    editor2.commit();
                } else if(id.matches("3")) {
                    editor2.putBoolean("isAlerta_3", true);
                    editor2.commit();
                }
            }
        }
    }

    private void alertaPluviosidade() {
        SharedPreferences prefs = getSharedPreferences("DataPluviosidade", Context.MODE_PRIVATE);
        String maxHorasPluv = prefs.getString("maxHorasPluviosidade", "0");
        double max_horasPluv = Double.parseDouble(maxHorasPluv);

        double PLUV;
        if(pluv != null && !pluv.isEmpty()) {
            PLUV = Double.parseDouble(pluv);
        }else {
            PLUV = 0.0;
        }

        if(PLUV != 0.0) {
            String type = "Pluviosidade";
            String alert1 = "O número de horas de ocorrência de pluviosidade foi superior ao máximo desejado:  " +maxHorasPluv+ " horas";
            String value = pluv + " horas";
            String date = time;

            if (PLUV > max_horasPluv) {
                BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
                backgroundDbTask2.execute(alertaDbTask, type, alert1, value, date);

                SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = prefs2.edit();

                if(id.matches("1")) {
                    editor2.putBoolean("isAlerta_1", true);
                    editor2.commit();
                } else if(id.matches("2")) {
                    editor2.putBoolean("isAlerta_2", true);
                    editor2.commit();
                } else if(id.matches("3")) {
                    editor2.putBoolean("isAlerta_3", true);
                    editor2.commit();
                }
            }
        }
    }

    private void alertaOutros() {

    }

    private void alertaPraga1() {
        double TEMP, HUM_A, LUM, PLUV;
        if((temp != null && !temp.isEmpty()) || (humA != null && !humA.isEmpty())
                || (lum != null && !lum.isEmpty()) || (pluv != null && !pluv.isEmpty())) {
            TEMP = Double.parseDouble(temp); HUM_A = Double.parseDouble(humA);
            LUM = Double.parseDouble(lum); PLUV = Double.parseDouble(pluv);
        }else {
            TEMP = 0.0; HUM_A = 0.0; LUM = 0.0; PLUV = 0.0;
        }

        // Conditions favorable for Praga 1 (Míldio)
        if(TEMP > 10.0 && HUM_A > 95.0 && LUM == 0.0 && PLUV > 0)        // LUM ?? PLUV ??
        {
            String type = "Doença";
            String alert = "Foram reunidas as condições necessárias para a existência de 'Míldio' nesta zona.";
            String value = "n/a";
            String date = time;

            BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
            backgroundDbTask2.execute(alertaDbTask, type, alert, value, date);

            SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();

            if(id.matches("1")) {
                editor2.putBoolean("isAlerta_1", true);
                editor2.commit();
            } else if(id.matches("2")) {
                editor2.putBoolean("isAlerta_2", true);
                editor2.commit();
            } else if(id.matches("3")) {
                editor2.putBoolean("isAlerta_3", true);
                editor2.commit();
            }
        }
    }

    private void alertaPraga2() {
        double TEMP, HUM_A, PLUV;
        if((temp != null && !temp.isEmpty()) || (humA != null && !humA.isEmpty())
                || (pluv != null && !pluv.isEmpty())) {
            TEMP = Double.parseDouble(temp); HUM_A = Double.parseDouble(humA); PLUV = Double.parseDouble(pluv);
        }else {
            TEMP = 0.0; HUM_A = 0.0; PLUV = 0.0;
        }

        // Conditions favorable for Praga 2 (Oídio da Videira)
        if(TEMP >= 24.0 && TEMP <= 26.0 && HUM_A > 90.0 && PLUV != 0.0)                 // PLUV ??
        {
            String type = "Doença";
            String alert = "Foram reunidas as condições necessárias para a existência de 'Oídio da Videira' nesta zona.";
            String value = "n/a";
            String date = time;

            BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
            backgroundDbTask2.execute(alertaDbTask, type, alert, value, date);

            SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();

            if(id.matches("1")) {
                editor2.putBoolean("isAlerta_1", true);
                editor2.commit();
            } else if(id.matches("2")) {
                editor2.putBoolean("isAlerta_2", true);
                editor2.commit();
            } else if(id.matches("3")) {
                editor2.putBoolean("isAlerta_3", true);
                editor2.commit();
            }

        }
    }

    private void alertaPraga3() {
        double TEMP, HUM_A;
        if((temp != null && !temp.isEmpty()) || (humA != null && !humA.isEmpty())) {
            TEMP = Double.parseDouble(temp); HUM_A = Double.parseDouble(humA);
        }else {
            TEMP = 0.0; HUM_A = 0.0;
        }

        // Conditions favorable for Praga 2 (Oídio da Videira)
        if(TEMP >= 15.0 && TEMP <= 25.0 && HUM_A > 95.0)                 // PLUV ??
        {
            String type = "Doença";
            String alert = "Foram reunidas as condições necessárias para a existência de 'Podridão Cinzenta' nesta zona.";
            String value = "n/a";
            String date = time;

            BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
            backgroundDbTask2.execute(alertaDbTask, type, alert, value, date);

            SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();

            if(id.matches("1")) {
                editor2.putBoolean("isAlerta_1", true);
                editor2.commit();
            } else if(id.matches("2")) {
                editor2.putBoolean("isAlerta_2", true);
                editor2.commit();
            } else if(id.matches("3")) {
                editor2.putBoolean("isAlerta_3", true);
                editor2.commit();
            }
        }
    }

    private void alertaPraga4() {
        double TEMP, HUM_S, PLUV;
        if((temp != null && !temp.isEmpty()) || (humS != null && !humS.isEmpty())
                || (pluv != null && !pluv.isEmpty())) {
            TEMP = Double.parseDouble(temp); HUM_S = Double.parseDouble(humS); PLUV = Double.parseDouble(pluv);
        }else {
            TEMP = 0.0; HUM_S = 0.0; PLUV = 0.0;
        }

        // Conditions favorable for Praga 2 (Oídio da Videira)
        if(TEMP < 37.0 && HUM_S > 95.0 && PLUV > 0.0)                               // PLUV ??
        {
            String type = "Doença";
            String alert = "Foram reunidas as condições necessárias para a existência de 'Escoriose da Videira' nesta zona.";
            String value = "n/a";
            String date = time;

            BackgroundDbTask2 backgroundDbTask2 = new BackgroundDbTask2(this);
            backgroundDbTask2.execute(alertaDbTask, type, alert, value, date);

            SharedPreferences prefs2 = getSharedPreferences("AlertasDialog", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();

            if(id.matches("1")) {
                editor2.putBoolean("isAlerta_1", true);
                editor2.commit();
            } else if(id.matches("2")) {
                editor2.putBoolean("isAlerta_2", true);
                editor2.commit();
            } else if(id.matches("3")) {
                editor2.putBoolean("isAlerta_3", true);
                editor2.commit();
            }
        }
    }




    // Byte array --> integer
    public static int bytearray2int(byte[] b) {
        ByteBuffer buf = ByteBuffer.wrap(b);
        return buf.getInt();
    }


}

