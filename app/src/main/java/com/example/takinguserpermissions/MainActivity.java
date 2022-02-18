package com.example.takinguserpermissions;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.metrics.Event;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String  TAG = MainActivity.class.getSimpleName();

    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private boolean isCameraPermissionGranted = false;
    private boolean isStoragePermissionGranted = false;
    private boolean isMessagePermissionGranted = false;
    private boolean isCallPermissionGranted = false;

    private static final int CAMERA_PERMISSION_CODE = 112;
    private static final int STORAGE_PERMISSION_CODE = 113;
    private static final int MESSAGE_PERMISSION_CODE = 115;
    private static final int CALL_PERMISSION_CODE = 116;
    Button btnCamera, btnStorage, btnMessage, btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ifAllPermissionsAreGrantedOpenAnotherActivity();

        mPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                if(result.get(Manifest.permission.CAMERA) != null){
                    isCameraPermissionGranted = result.get(Manifest.permission.CAMERA);
                }

                if(result.get(Manifest.permission.READ_EXTERNAL_STORAGE) != null){
                    isStoragePermissionGranted = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

                if(result.get(Manifest.permission.SEND_SMS) != null){
                    isMessagePermissionGranted = result.get(Manifest.permission.SEND_SMS);
                }

                if(result.get(Manifest.permission.CALL_PHONE) != null){
                    isCallPermissionGranted = result.get(Manifest.permission.CALL_PHONE);
                    System.out.print(isCallPermissionGranted);
                }
            }
        });

        requestPermission();

        ifAllPermissionsAreGrantedOpenAnotherActivity();

        btnCamera = findViewById(R.id.btnCamera);
        btnStorage = findViewById(R.id.btnStorage);
        btnMessage = findViewById(R.id.btnMsg);
        btnCall = findViewById(R.id.btnCall);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
                System.out.println("Testing Camera****************************"+isCameraPermissionGranted);
                ifAllPermissionsAreGrantedOpenAnotherActivity();
            }
        });

        btnStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                System.out.println("Testing Storage****************************"+isStoragePermissionGranted);
                ifAllPermissionsAreGrantedOpenAnotherActivity();
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.CALL_PHONE, CALL_PERMISSION_CODE);
                System.out.println("Testing Call****************************"+isCallPermissionGranted);
                ifAllPermissionsAreGrantedOpenAnotherActivity();
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.SEND_SMS, MESSAGE_PERMISSION_CODE);
                System.out.println("Testing Message****************************"+isMessagePermissionGranted);
                ifAllPermissionsAreGrantedOpenAnotherActivity();
            }
        });
    }


    //we make global function to take or check permission

    public void checkPermission(String permission,int requestCode){
        //checking if permission granted or not
        if(ContextCompat.checkSelfPermission(MainActivity.this, permission)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        }
        else{
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            ifAllPermissionsAreGrantedOpenAnotherActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d(TAG, "inside onRequestPermissionsResult Request code:- "+requestCode);



        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                btnCamera.setText("Camera Permission Granted");
                Toast.makeText(MainActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
                ifAllPermissionsAreGrantedOpenAnotherActivity();
            }
            else{
                Toast.makeText(MainActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                btnStorage.setText("Storage Permission Granted");
                Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
                ifAllPermissionsAreGrantedOpenAnotherActivity();
            }
            else{
                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == MESSAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                btnMessage.setText("Message Permission Granted");
                Toast.makeText(MainActivity.this, "Message Permission Granted", Toast.LENGTH_SHORT).show();
                ifAllPermissionsAreGrantedOpenAnotherActivity();
            }
            else{
                Toast.makeText(MainActivity.this, "Message Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == CALL_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                btnCall.setText("Call Permission Granted");
                Toast.makeText(MainActivity.this, "Call Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this, "Call Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            //nothing to do
            ifAllPermissionsAreGrantedOpenAnotherActivity();
        }


        Log.d(TAG,"camera:- "+isCameraPermissionGranted + " Storage:- " + isStoragePermissionGranted + " Message:- " + isMessagePermissionGranted + " Call:- " + isCallPermissionGranted);


        if(isCameraPermissionGranted && isStoragePermissionGranted && isMessagePermissionGranted && isCallPermissionGranted){
            ifAllPermissionsAreGrantedOpenAnotherActivity();
        }


    }

    private void requestPermission(){
        isCameraPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        isStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        isMessagePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
        isCallPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;

        List<String> permissionRequest = new ArrayList<String>();

        if(!isCameraPermissionGranted){
            permissionRequest.add(Manifest.permission.CAMERA);
        }

        if(!isStoragePermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if(!isMessagePermissionGranted){
            permissionRequest.add(Manifest.permission.SEND_SMS);
        }

        if(!isCallPermissionGranted){
            permissionRequest.add(Manifest.permission.CALL_PHONE);
        }

        if(!permissionRequest.isEmpty()){
            mPermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
        }
    }

    public void ifAllPermissionsAreGrantedOpenAnotherActivity() {

        Log.d(TAG,"camera:- "+isCameraPermissionGranted + " Storage:- " + isStoragePermissionGranted + " Message:- " + isMessagePermissionGranted + " Call:- " + isCallPermissionGranted);

        if(isCameraPermissionGranted && isStoragePermissionGranted && isMessagePermissionGranted && isCallPermissionGranted){
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            Log.d(TAG, "Testing ");
        }
        else{
            //stay in the current activity only
        }


    }

}