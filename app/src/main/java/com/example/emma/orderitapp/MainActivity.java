package com.example.emma.orderitapp;

import android.content.Intent;
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
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    private String businessType;
    private SharedPreferences prefs;
    private Business business;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showTitle();
    }

    public void scanBusinessQR(View view) {
        startActivity(new Intent(this, QRCodeReaderBusiness.class));
    }

    private void showTitle() {
        // Create ShapeDrawable title circle
        ShapeDrawable titleCircle = new ShapeDrawable(new RectShape());
        titleCircle.setIntrinsicHeight(1400);
        titleCircle.setIntrinsicWidth(900);

        // Create Paint for MAGIC 8 BALL
        Paint ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int blue = getResources().getColor(R.color.blue);
        int darkBlue = getResources().getColor(R.color.darkBlue);
        RadialGradient radGrad = new RadialGradient(450, 700, 100, blue, darkBlue, Shader.TileMode.MIRROR);
        ballPaint.setShader(radGrad);
        titleCircle.getPaint().set(ballPaint);

        // Set to ImageView
        ImageView tc = (ImageView) findViewById(R.id.titleCircle);
        tc.setImageDrawable(titleCircle);

        // Shake Button
        shakeButton();
    }


    // Shake Title Circle
    private void shakeButton() {
        performImageAnimation(R.anim.shake, R.id.LaunchButton);
    }

    private void performImageAnimation(int animationResourceID, int viewID) {
        Animation an = AnimationUtils.loadAnimation(this, animationResourceID);
        an.setAnimationListener(new TweenAnimationListener());
        Button image = (Button) findViewById(viewID);
        image.startAnimation(an);
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
            final Button scanButton = (Button) findViewById(R.id.LaunchButton);
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
