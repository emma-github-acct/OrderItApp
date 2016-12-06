package com.example.emma.orderitapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 10);
        }
        showTitle();
    }

    // Request permissions at runtime from Marshmallow and Nougat devices
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Camera Access Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Camera Access Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void scanBusinessQR(View view) {
        startActivity(new Intent(this, QRCodeReaderBusiness.class));
    }

    private void showTitle() {
        // Create ShapeDrawable
        ShapeDrawable titleCircle = new ShapeDrawable(new RectShape());
        titleCircle.setIntrinsicHeight(1400);
        titleCircle.setIntrinsicWidth(900);

        // Create Paint
        Paint ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int blue = getResources().getColor(R.color.blue);
        int darkBlue = getResources().getColor(R.color.darkBlue);
        RadialGradient radGrad = new RadialGradient(450, 700, 100, blue, darkBlue, Shader.TileMode.MIRROR);
        ballPaint.setShader(radGrad);
        titleCircle.getPaint().set(ballPaint);

        // Set to ImageView
        ImageView tc = (ImageView) findViewById(R.id.backdrop);
        tc.setImageDrawable(titleCircle);

        // Shake Button
        shakeButton();
        shakeTitle();
    }

    private void shakeButton() {
        performImageAnimation(R.anim.shake, R.id.launch_button);
    }

    private void shakeTitle() {
        performTextAnimation(R.anim.shake, R.id.welcome_text);
    }

    private void performImageAnimation(int animationResourceID, int viewID) {
        Animation an = AnimationUtils.loadAnimation(this, animationResourceID);
        an.setAnimationListener(new TweenAnimationListener());
        Button image = (Button) findViewById(viewID);
        image.startAnimation(an);
    }

    private void performTextAnimation(int animationResourceID, int viewID) {
        Animation an = AnimationUtils.loadAnimation(this, animationResourceID);
        an.setAnimationListener(new TweenAnimationListener());
        TextView textview = (TextView) findViewById(viewID);
        textview.startAnimation(an);
    }

    class TweenAnimationListener implements Animation.AnimationListener {
        public void onAnimationStart(Animation animation) {
            // Disable all buttons while animation is running
            enableButtons(false);
        }

        public void onAnimationEnd(Animation animation) {
            // Enable all buttons once animation is over
            long duration = animation.getDuration();
            enableButtons(true);
        }

        public void onAnimationRepeat(Animation animation) {
            // what to do when animation loops
        }

        private void enableButtons(boolean enabledState) {
            final Button scanButton = (Button) findViewById(R.id.launch_button);
            scanButton.setEnabled(enabledState);

        }
    } // end listener class


    /**
     * The code below handles menus
     */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_start).setVisible(false);
        menu.findItem(R.id.menu_scan).setVisible(false);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; adds items to the action bar
        // if it is present
        getMenuInflater().inflate(
                R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                startScan(null);
                return true;
            case R.id.menu_history:
                startHistory(null);
                return true;
            case R.id.menu_settings:
                startSettings(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Below are the handlers that start new activities
     *
     * @param v
     */
    public void startSettings(View v) {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }

    public void startHistory(View v) {
        startActivity(new Intent(getApplicationContext(), OrderHistoryActivity.class));
    }

    public void startScan(View v) {
        startActivity(new Intent(getApplicationContext(), QRCodeReaderRestaurant.class));
    }

// End menu code


}
