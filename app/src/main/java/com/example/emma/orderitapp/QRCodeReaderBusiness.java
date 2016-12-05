package com.example.emma.orderitapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Hannah on 11/16/16.
 */

public class QRCodeReaderBusiness extends Activity {

    private Barcode barcode;
    private BarcodeDetector barcodeDetector;
    //public CameraSource cameraSource;
    private FileWriter fileWriter;
    private File file;
    private SurfaceView cameraView;
    private SparseArray<Barcode> qrCodes;
    private OrderDatabase od;
    private Business businessObject;
    FileOutputStream fos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.barcode_read);

        od = new OrderDatabase(this);

        cameraView = (SurfaceView) findViewById(R.id.camera_view);

        createCamera();
    }

    private void createCamera() {

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024).build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(QRCodeReaderBusiness.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(null, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
               }
                else {
                    ActivityCompat.requestPermissions(QRCodeReaderBusiness.this, new String[]{Manifest.permission.CAMERA}, 0);

                    try {
                        Log.w("CAMERA SOURCE", "Camera started");
                        cameraSource.start(surfaceHolder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release () {
            }

            @Override
            public void receiveDetections (Detector.Detections<Barcode> barcodeDetections) {

                qrCodes = barcodeDetections.getDetectedItems();
                Log.i("DETECTIONS", "Receiving detections");
                Log.i("CODE ARRAY SIZE", String.valueOf(qrCodes.size()));

                if (qrCodes.size() != 0) {

                    final ObjectMapper mapper = new ObjectMapper();
                    //final OrderTest orderTest = new OrderTest();

                    try {
                        Log.i("TRY: ", "In try 1");
                        final String jsonString = qrCodes.valueAt(0).displayValue;
                        Log.i("TRY: ", "In try 2");
                        businessObject = mapper.readValue(jsonString, Business.class);
                        Log.i("TRY: ", "In try 3");

                        startWelcome(null);

                    } catch (IOException ioe) {
                        ioe.getMessage();
                    }

                }

            }
        });
    }

    public void startWelcome(View v) {
        Intent i = new Intent( getApplicationContext(), WelcomeActivity.class );
        i.putExtra( "business", businessObject );
        startActivity(i);
    }

    public void startCheckout(View v) {
        Intent i = new Intent( getApplicationContext(), CheckoutActivity.class );
        i.putExtra( "business", businessObject );
        startActivity(i);
    }

    public void startMain(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }




}
