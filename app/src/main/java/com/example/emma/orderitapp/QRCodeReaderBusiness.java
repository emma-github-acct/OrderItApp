package com.example.emma.orderitapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

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
                    Log.w("PERMISSIONS", "Permission request");

                    ActivityCompat.requestPermissions(QRCodeReaderBusiness.this, new String[]{Manifest.permission.CAMERA}, 0);
                }
                try {
                    Log.w("CAMERA SOURCE", "Camera started");
                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                cameraSource.release();
                try{
                    startCameraSource();
                } catch (SecurityException se) {
                    Log.e("CAMERA PERMISSIONS", se.getMessage());
                }
                */
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
                        final Business business = mapper.readValue(jsonString, Business.class);
                        Log.i("TRY: ", "In try 3");
                        Log.i("ITEM: ", business.toString());

                        Intent i = new Intent( getApplicationContext(), WelcomeActivity.class );
                        i.putExtra("Type", business.getType());
                        i.putExtra("Name", business.getName());
                        i.putExtra("Phone", business.getPhone());
                        i.putExtra("Address", business.getAddress());
                        i.putExtra("Email", business.getEmail());
                        startActivity(i);


                    } catch (IOException ioe) {
                        ioe.getMessage();
                    }

                }

            }
        });


    }
}
