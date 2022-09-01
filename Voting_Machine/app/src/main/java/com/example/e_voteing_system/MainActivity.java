package com.example.e_voteing_system;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.net.ssl.SSLContext;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    Handler h;
    final int RECIEVE_MESSAGE = 1;
    BluetoothSocket btSocket = null;
    String address = null;
    public static String Fingerstring="Fingerid";
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final StringBuilder sb = new StringBuilder();

    Timer timer;
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent newint = getIntent();
        address = newint.getStringExtra(Bluetoothact.EXTRA_ADDRESS);

        new ConnectBT().execute();
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,voter_data.class);
                int fingerid=Getdetected_Finger();
                intent.putExtra("Fingerstring",fingerid);
                System.out.println(fingerid);
                startActivity(intent);
                finish();
            }
        },5000);/*
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* try{
                    if(fingerid==10){
                        tv.setText("done");
                        Toast.makeText(MainActivity.this, "Done bro", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(MainActivity.this, "f8ck", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
                               }
*/

    }
    private class ConnectBT extends AsyncTask<Void, Void, Void> // UI thread
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(MainActivity.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.


                        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                        btSocket.connect();
                    }               }
            } catch (IOException e) {
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
    public int Getdetected_Finger() {
        int tu=0;
        String s=null;
        InputStream inputStream;
        if (btSocket != null) {
            int r=0;
            InputStream tmp = null;
            try {
                tmp = btSocket.getInputStream();
            } catch (IOException t) {
            }

            inputStream = tmp;
            while (true) {
                try {
                    byte buffer = (byte) inputStream.read();
                       s = new String(String.valueOf(buffer));
                     r = Integer.parseInt(s);

                    break;
                } catch (IOException e) {
                    break;
                }
            }
            tu=r;
        }
        int tf=tu;
        return tf;
    }
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    void getdata(){
        /*  db.collection("Result").document("1").
                        get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        boolean statues=documentSnapshot.getBoolean("finished");
                        if(statues){
                            Intent intent=new Intent(MainActivity.this,RESULT.class);
                            startActivity(intent);
                        }
                        else{
                            //read finger
                            Intent intent=new Intent(MainActivity.this,loading_voter.class);
                            intent.putExtra("Fingerprint",finger);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Failure");
                    }
                });

 */
    }


}