package com.example.emma.orderitapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.*;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

//import com.google.android.gms.vision.Detector;

/**
 * Created by Hannah on 11/11/16.
 */
public class QRCodeReaderRestaurant extends Activity {
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
                if (ActivityCompat.checkSelfPermission(QRCodeReaderRestaurant.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Log.w("PERMISSIONS", "Permission request");

                    ActivityCompat.requestPermissions(QRCodeReaderRestaurant.this, new String[]{Manifest.permission.CAMERA}, 0);
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
                        final MenuItem item = mapper.readValue(jsonString, MenuItem.class);
                        Log.i("TRY: ", "In try 3");
                        final TextView test = (TextView) findViewById(R.id.code_info);
                        Log.i("TRY: ", "In try 4");
                        Log.i("ITEM: ", item.toString());


                        Handler mHandler = new Handler (Looper.getMainLooper());
                        test.post(new Runnable() {
                            @Override
                            public void run() {
                                cameraSource.stop();

                                Log.i("RUNNABLE: ", "In run()");
                                final Dialog orderConfirmation = new Dialog(QRCodeReaderRestaurant.this);
                                orderConfirmation.setContentView(R.layout.confirm_order_dialog);
                                orderConfirmation.setTitle("Your Order:");

                                final NumberPicker quantity
                                        = (NumberPicker) orderConfirmation.findViewById(R.id.picker);
                                quantity.setMaxValue(10);
                                quantity.setMinValue(0);

                                final TextView orderContent
                                        = (TextView) orderConfirmation.findViewById(R.id.orderContent);
                                orderContent.setText(item.toString());

                                Button confirm
                                        = (Button) orderConfirmation.findViewById(R.id.confirm);
                                Button cancel
                                        = (Button) orderConfirmation.findViewById(R.id.cancel);

                                orderConfirmation.show();

                                test.setText(item.toString());

                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        item.setQuantity(String.valueOf(quantity.getValue()));
                                        od.insert("today", "restaurant", item.getName(), item.getQuantity(), item.getPrice());
                                        orderConfirmation.dismiss();
                                        try {
                                            cameraSource.start(cameraView.getHolder());
                                        } catch (IOException ioe) {
                                            ioe.getMessage();
                                        }                                    }
                                });
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        orderConfirmation.dismiss();
                                        try {
                                            cameraSource.start(cameraView.getHolder());
                                        } catch (IOException ioe) {
                                            ioe.getMessage();
                                        }
                                    }
                                });

                            }
                        });

                    } catch (IOException ioe) {
                        ioe.getMessage();
                    }

                }

            }
        });


    }


}
