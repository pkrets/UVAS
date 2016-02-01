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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements BluetoothAdapter.LeScanCallback {

    private static boolean RUN_ONCE = true;

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private MyPagerAdapter mAdapter;

    private ArrayList<String> recData = new ArrayList<String>();
    private int nbytes=0;
    private int fileSize = -1;

    private static final String TAG = "BluetoothGattActivity";

    private static final String DEVICE_NAME_1 = "UVA-1";
    private static final String DEVICE_NAME_2 = "UVA-2";
    private static final String DEVICE_NAME_3 = "UVA-3";

        private static final UUID SERVICE = UUID.fromString("00001120-2222-2111-1aad-22faa544a3dd");

        private static final UUID ALL_READ = UUID.fromString("00001143-2222-2111-1aad-22faa544a3dd");
        private static final UUID ALL_WRITE = UUID.fromString("00001144-2222-2111-1aad-22faa544a3dd");

        private static final UUID GPS_READ = UUID.fromString("00001145-2222-2111-1aad-22faa544a3dd");

        // UUID for the BTLE client characteristic which is necessary for notifications.
        public static UUID CLIENT_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

        private BluetoothAdapter mBluetoothAdapter;
        private SparseArray<BluetoothDevice> mDevices;

        private BluetoothGatt mConnectedGatt;

        BluetoothDevice connectDevice;

        private String mTemperature, mPressão, mHumAr, mLuminosidade, mHumSolo;

        private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setProgressBarIndeterminate(true);

     /*   SharedPreferences prefs2 = getSharedPreferences("GPS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = prefs2.edit();
        editor2.putBoolean("isConfigured_2", false);
        editor2.putString("GpsLat2", "0");
        editor2.putString("GpsLng2", "0");
        editor2.commit();
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
        Boolean log = prefs.getBoolean("isLogged", false);

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
                                finish();
                                System.exit(0);
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
                        finish();
                        System.exit(0);
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
            for (int i=0; i < mDevices.size(); i++) {
                BluetoothDevice device = mDevices.valueAt(i);
                String deviceFound = "\t\t\t\t * " + device.getName();
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
                    Log.d(TAG, "Sending SD values");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(ALL_WRITE);
                    characteristic.setValue(new byte[]{(byte) 0xE1});
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
            BluetoothGattCharacteristic characteristic = null;
            switch (mState) {
                case 0:
                    if (DEVICE_NAME_1.equals(connectDevice.getName())) {
                        SharedPreferences prefs = getSharedPreferences("GPS", Context.MODE_PRIVATE);
                        boolean GpsConfig = prefs.getBoolean("isConfigured_1", false);
                            Log.i("GPS CONFIGURED 1", String.valueOf(GpsConfig));
                        if(!GpsConfig) {
                            characteristic = gatt.getService(SERVICE)
                                    .getCharacteristic(GPS_READ);
                        }
                    }
                    else if(DEVICE_NAME_2.equals(connectDevice.getName())) {
                        SharedPreferences prefs = getSharedPreferences("GPS", Context.MODE_PRIVATE);
                        boolean GpsConfig = prefs.getBoolean("isConfigured_2", false);
                            Log.i("GPS CONFIGURED 2", String.valueOf(GpsConfig));
                        //if(!GpsConfig) {
                            characteristic = gatt.getService(SERVICE)
                                    .getCharacteristic(GPS_READ);
                        //}
                    }
                    else if(DEVICE_NAME_3.equals(connectDevice.getName())) {
                        SharedPreferences prefs = getSharedPreferences("GPS", Context.MODE_PRIVATE);
                        boolean GpsConfig = prefs.getBoolean("isConfigured_3", false);
                            Log.i("GPS CONFIGURED 3", String.valueOf(GpsConfig));
                        if(!GpsConfig) {
                            characteristic = gatt.getService(SERVICE)
                                    .getCharacteristic(GPS_READ);
                        }
                    }
                    break;
                case 1:
                    Log.d(TAG, "Reading SD");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(ALL_READ);
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
                    Log.d(TAG, "Set notify GPS cal");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(GPS_READ);
                    break;
                case 1:
                    Log.d(TAG, "Set notify SD cal");
                    characteristic = gatt.getService(SERVICE)
                            .getCharacteristic(ALL_READ);
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
            if (GPS_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_GPS, characteristic));
            }
            if (ALL_READ.equals(characteristic.getUuid())) {
                mHandler.sendMessage(Message.obtain(null, MSG_ALL, characteristic));
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




    private void updateSDValues(BluetoothGattCharacteristic characteristic) {

        byte[] tmp = new byte[20];

        tmp = characteristic.getValue();
        String aux = new String(tmp);
        Log.i(TAG, "SD Caract: " + aux);

        if (nbytes == 20){
            fileSize = Integer.parseInt(aux);
            Log.i(TAG, "file size: " + fileSize);
        }
        else if(nbytes < fileSize)
            recData.add(aux);

        nbytes += tmp.length;

        Log.i(TAG, "nbytes: " + nbytes);

        if(nbytes >= fileSize)
            readSD();

        Log.i(TAG, "recData size: " + recData.size());
    }

    // GET GPS (LAT, LNG) FROM SD CARD
    private void updateGPSValues(BluetoothGattCharacteristic characteristic) {

        byte tmp[];
        byte lat[] = new byte[4];
        byte lng[] = new byte[4];

        tmp = characteristic.getValue();
        lat = Arrays.copyOfRange(tmp, 0, 4);
        lng = Arrays.copyOfRange(tmp, 4, 8);

        Log.i(TAG, "GPS Caract: " + Arrays.toString(tmp));

        if (DEVICE_NAME_1.equals(connectDevice.getName())) {
            SharedPreferences prefs = getSharedPreferences("GPS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isConfigured_1", true);
            editor.commit();

            int Lat = bytearray2int(lat);
            double finalLat = ((double) (int) Lat)/10000000;
            String sLat = String.valueOf(finalLat);

            int Lng = bytearray2int(lng);
            double finalLng = ((double) (int) Lng)/10000000;
            String sLng = String.valueOf(finalLng);

            SharedPreferences prefs2 = getSharedPreferences("GPS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();
            editor2.putString("GpsLat1", sLat);
            editor2.putString("GpsLng1", sLng);
            editor2.commit();

                Log.i(TAG, "GPS Lat 1: " + sLat);
                Log.i(TAG, "GPS Long 1: " + sLng);
        }
        else if (DEVICE_NAME_2.equals(connectDevice.getName())) {
            SharedPreferences prefs = getSharedPreferences("GPS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isConfigured_2", true);
            editor.commit();

            int Lat = bytearray2int(lat);
            double finalLat = ((double) (int) Lat)/10000000;
            String sLat = String.valueOf(finalLat);

            int Lng = bytearray2int(lng);
            double finalLng = ((double) (int) Lng)/10000000;
            String sLng = String.valueOf(finalLng);

            SharedPreferences prefs2 = getSharedPreferences("GPS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();
            editor2.putString("GpsLat2", sLat);
            editor2.putString("GpsLng2", sLng);
            editor2.commit();

                Log.i(TAG, "GPS Lat 2: " + sLat);
                Log.i(TAG, "GPS Long 2: " + sLng);
        }
        else if (DEVICE_NAME_3.equals(connectDevice.getName())) {
            SharedPreferences prefs = getSharedPreferences("GPS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isConfigured_3", true);
            editor.commit();

            int Lat = bytearray2int(lat);
            double finalLat = ((double) (int) Lat)/10000000;
            String sLat = String.valueOf(finalLat);

            int Lng = bytearray2int(lng);
            double finalLng = ((double) (int) Lng)/10000000;
            String sLng = String.valueOf(finalLng);

            SharedPreferences prefs2 = getSharedPreferences("GPS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = prefs2.edit();
            editor2.putString("GpsLat3", sLat);
            editor2.putString("GpsLng3", sLng);
            editor2.commit();

                Log.i(TAG, "GPS Lat 3: " + sLat);
                Log.i(TAG, "GPS Long 3: " + sLng);
        }
    }



    private void readSD() {
        String allData = "";
        String[] data;


        for (int i = 0; i < recData.size(); i++) {
            allData += recData.get(i);
        }

        data = allData.split("\n");

        Log.i(TAG, "All Data " + allData);


        for (int i = 0; i < data.length; i++) {
            Log.i(TAG, "Data " + (i+1)  + ": " + data[i]);
        }

        //mandaBD(data);

        //meteZona1(data[i].split(",")[1] )
        // id => data[i].getChar(0)
    }

    public static int bytearray2int(byte[] b) {
        ByteBuffer buf = ByteBuffer.wrap(b);
        return buf.getInt();
    }













    // Populate DB: Historico tables (NOT USED)
        private void populateHistoricoDb()
        {
            if(mTemperature != null && mLuminosidade != null && mHumSolo != null && mHumAr != null && mPressão != null) {
                String data = "12-12-2015, 18h34m";     // Manually inserted for now

                BackgroundDbTask backgroundDbTask = new BackgroundDbTask(this);
                backgroundDbTask.execute("add_info_1", mTemperature, mLuminosidade, mHumSolo, mHumAr, mPressão, data);
            }
        }

}

