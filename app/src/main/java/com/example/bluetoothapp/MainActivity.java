package com.example.bluetoothapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bluetoothapp.databinding.ActivityMainBinding;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
     private BluetoothManager bluetoothManager;
     private BluetoothAdapter bluetoothAdapter;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button enableDisableBleBtn = findViewById(R.id.enableDisableBleBtn);
        Button findDevices = findViewById(R.id.findDevices);

        findDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanForDevices();
            }
        });

        enableDisableBleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableDisableBle();
            }
        });


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        this.bluetoothManager = getSystemService(BluetoothManager.class);
        this.bluetoothAdapter = bluetoothManager.getAdapter();

    }



    private void enableDisableBle(){
        String[] permissions = {Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_ADVERTISE, Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        int requestCode = 100;

        requestPermissions(permissions, requestCode);

        if(!this.bluetoothAdapter.isEnabled() && (checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN )== PackageManager.PERMISSION_GRANTED)){
            Log.d("message2","try to turn on the bluetooth");
            Intent enableBleIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBleIntent);
        }

    }

    private void scanForDevices(){
        try{
            Set<BluetoothDevice> pairedDevices = this.bluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0){
                for(BluetoothDevice device : pairedDevices){
                    String deviceName = device.getName();
                    String deviceAdress = device.getAddress();

                    Log.d("device", deviceName);
                }
            }

        }catch(SecurityException e){
                Log.d("error", "Security exception");
        }

    }

}