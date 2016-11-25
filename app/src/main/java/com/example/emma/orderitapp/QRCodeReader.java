package com.example.emma.orderitapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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

//import com.google.android.gms.vision.Detector;

/**
 * Created by Hannah on 11/11/16.
 */
public class QRCodeReader extends Activity {
    private Barcode barcode;
    private BarcodeDetector barcodeDetector;
    //public CameraSource cameraSource;
    private FileWriter fileWriter;
    private File file;
    private SurfaceView cameraView;
    private SparseArray<Barcode> qrCodes;
    FileOutputStream fos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.barcode_read);

        cameraView = (SurfaceView) findViewById(R.id.camera_view);

        file = new File("OrderFile.txt");

        try {
            fileWriter = new FileWriter(file, true);
            Log.i("TRY", "In try");
        }catch (IOException ioe) {
            ioe.getMessage();
        }

        createCamera();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void createCamera() {

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024).build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                if (ActivityCompat.checkSelfPermission(QRCodeReader.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.w("PERMISSIONS", "Permission request");

                    ActivityCompat.requestPermissions(QRCodeReader.this, new String[]{Manifest.permission.CAMERA}, 0);
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

                        ObjectMapper mapper = new ObjectMapper();
                        //final OrderTest orderTest = new OrderTest();

                        try {
                            String jsonString = qrCodes.valueAt(0).displayValue;

                            final OrderTest orderTest = mapper.readValue(jsonString, OrderTest.class);
                            final TextView codeStuff = (TextView)findViewById(R.id.code_info);

                            codeStuff.post(new Runnable() {
                                @Override
                                public void run() {
                                    codeStuff.setText(orderTest.toString());
                                }
                            });

                        } catch (IOException ioe) {
                            ioe.getMessage();
                        }


                        /*
                        out.println(qrCodes.valueAt(0).displayValue);
                        Log.i("SCAN", "Scanned!!!");
                        Intent intent = new Intent();
                        intent.putExtra("code", qrCodes.valueAt(0));
                        setResult(CommonStatusCodes.SUCCESS, intent);
                        */


                        /*
                        try {
                            fos = openFileOutput("OrderFile", Context.MODE_APPEND);
                            fos.write("Hi there".getBytes());
                            Log.i("WRITING TO FILE", "Wrote to file");
                            fos.flush();
                            fos.close();
                        }catch (IOException ioe) {
                            Log.i("FILE WRITING", "Caught IOException:  " + ioe.getMessage());
                        }
                        */

                        /*
                        //Looper.prepare();
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    out.write(qrCodes.valueAt(0).displayValue);
                                    Log.i("WRITING TO FILE", "Wrote to file");
                                }catch (IOException ioe) {
                                    ioe.getMessage();
                                    Log.i("FILE WRITING", "Caught IOException");
                                }
                                Log.d("SCAN", "Scanned!!");
                            }
                        };
                        */
                    }

            }
        });

    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.

    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, 9001);
            dlg.show();
        }

        if (cameraSource != null) {
            try {
                cameraSource.start();
            } catch (IOException e) {
                Log.e("CAMERA SOURCE", "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }
     */
}
