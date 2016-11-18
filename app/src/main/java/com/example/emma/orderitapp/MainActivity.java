package com.example.emma.orderitapp;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
    private business business;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showTitle();



        // Set business info
        business = new business(this);
        business.setType("Restaurant");
        business.setName("Java Cafe");
        business.setPhone("908-456-8888");
        business.setAddress("123 Holt Ave Winter Park, FL");
        business.setEmail("JavaCafe@gmail.com");

    }


    public void scanBusinessQR(View view) {
        //instantiateBusinessInfo();

        business.setType("Restaurant");
        business.setName("Java Cafe");
        business.setPhone("908-456-8888");
        business.setAddress("123 Holt Ave Winter Park, FL");
        business.setEmail("JavaCafe@gmail.com");

        // Where
        loadWelcomePage(null);
    }

    private void loadWelcomePage(View view) {
        Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
        i.putExtra("Type", business.getType());
        i.putExtra("Name", business.getName());
        i.putExtra("Phone", business.getPhone());
        i.putExtra("Address", business.getAddress());
        i.putExtra("Email", business.getEmail());
        startActivity(i);
    }

    private void showTitle() {
        // Create ShapeDrawable title circle
        ShapeDrawable titleCircle = new ShapeDrawable(new OvalShape());
        titleCircle.setIntrinsicHeight(300);
        titleCircle.setIntrinsicWidth(900);

        // Create Paint for MAGIC 8 BALL
        Paint ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int blue = getResources().getColor(R.color.blue);
        int darkBlue = getResources().getColor(R.color.darkBlue);
        RadialGradient radGrad = new RadialGradient(450, 150, 100, blue, darkBlue, Shader.TileMode.MIRROR);
        ballPaint.setShader(radGrad);
        titleCircle.getPaint().set(ballPaint);

        // Set to ImageView
        ImageView tc = (ImageView) findViewById(R.id.titleCircle);
        tc.setImageDrawable(titleCircle);

        // Shake Title
        shakeTitle();
    }


    // Shake Title Circle
    private void shakeTitle() {
        performImageAnimation(R.anim.shake, R.id.titleCircle);
    }

    private void performImageAnimation(int animationResourceID, int viewID) {
        Animation an = AnimationUtils.loadAnimation(this, animationResourceID);
        an.setAnimationListener(new TweenAnimationListener());
        ImageView image = (ImageView) findViewById(viewID);
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
            case R.id.menu_checkout:
                startCheckout(null);
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

    public void startCheckout(View v) {
        startActivity(new Intent(getApplicationContext(), Checkout.class));
    }

    public void startHistory(View v) {
        startActivity(new Intent(getApplicationContext(), OrderHistory.class));
    }

    public void startScan(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
// End menu code


}
