package com.example.emma.orderitapp;

/**
 * Controller to display, confirm and email the Take Out Order.
 *
 */

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private Customer customer;
    private Business businessObject;
    private Order orderObject;
    
    private LayoutManager layoutManager;
    private String orderData; //Text display and email body of the order.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.businessObject= (Business) intent.getSerializableExtra("business");
        this.orderObject= (Order) intent.getSerializableExtra("order");
        
        layoutManager = new LayoutManager();
        setContentView( layoutManager.getCheckoutLayout( businessObject.getName() ));

        customer = new Customer(this);
        boolean inputSettings = confirmCustomerSettingsInput( customer );
        if (!inputSettings){
            Toast.makeText(this, R.string.settings_error, Toast.LENGTH_LONG).show();
        }


        TextView orderDisplay = (TextView) findViewById(R.id.order_items);
        orderData = "Restaurant: " + businessObject.getName() + "  " + businessObject.getEmail() + "\n\n";
        orderData += "Customer: " + customer.getName() + " " + customer.getEmail() +"\n\n";
        orderData += "Item    Price    Quantity \n";

        ArrayList<MenuItem> o = orderObject.getOrder();
        for (MenuItem i: o){
            orderData += i.getName() + "  ";
            orderData += i.getPrice() + "  ";
            orderData += i.getQuantity() + "\n";
        }
        orderData += "Total " + orderObject.getTotal() + "\n";
        orderDisplay.setText( orderData);
    }

    private boolean confirmCustomerSettingsInput( Customer customer ){
        String name = getResources().getString(R.string.default_name);
        String email = getResources().getString(R.string.default_email);
        String phoneNumber = getResources().getString(R.string.default_phone_number);
        String address = getResources().getString(R.string.default_address);

        boolean validName = !(customer.getName().equals(name));
        boolean validEmail = !(customer.getEmail().equals(email));
        boolean validPhoneNumber = !(customer.getPhone().equals(phoneNumber));
        boolean validAddress = !(customer.getAddress().equals(address));

        return ( validName && validEmail && validPhoneNumber && validAddress );

    }

    /**
     * Send order by email
     * Threaded. Starts Receipt activity on result code.
     * @param v
     */

    public void sendOrder(View v) {

        OrderDatabase orderDatabaseObject = new OrderDatabase(this);
        orderDatabaseObject.insert(orderObject.getDate(), businessObject.getName(), orderObject.getTotal());

        new Thread(new Runnable() {
            public void run() {

                String[] addresses = {"sengle64@gmail.com"}; //{restaurant.getEmail(), customer.getEmail()}
                String subject = "TakeOut Order";
                String body = orderData;

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(emailIntent, 1);
                }
            }
        }).start();

        Toast.makeText( this, "Sending Order, pick up in 30 minutes", Toast.LENGTH_LONG).show();
    }


    /**
     * Start Receipt activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    /**
     * The code below handles menus
     */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_checkout).setVisible(false);
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
            case R.id.menu_start:
                startMain(null);
                return true;
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
        Intent i = new Intent( getApplicationContext(), SettingsActivity.class );
        i.putExtra( "business", businessObject );
        i.putExtra( "order", orderObject );
        startActivity(i);
    }

    public void startHistory(View v) {
        Intent i = new Intent( getApplicationContext(), OrderHistoryActivity.class );
        i.putExtra( "business", businessObject );
        i.putExtra( "order", orderObject );
        startActivity(i);
    }

    public void startScan(View v) {
        Intent i = new Intent( getApplicationContext(), QRCodeReaderRestaurant.class );
        i.putExtra( "business", businessObject );
        i.putExtra( "order", orderObject );
        startActivity(i);
    }

    public void startMain(View v) {
        confirmAppRestart();
    }
// End menu code

    private void confirmAppRestart(){
        final Dialog restartConfirmation = new Dialog(CheckoutActivity.this);
        restartConfirmation.setContentView(R.layout.confirm_restart);
        restartConfirmation.setTitle("Restarting App");
        Button confirm = (Button) restartConfirmation.findViewById(R.id.restart_confirm);
        Button cancel = (Button) restartConfirmation.findViewById(R.id.restart_cancel);

        restartConfirmation.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getApplicationContext(), MainActivity.class );
                startActivity(i);
                restartConfirmation.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartConfirmation.dismiss();
            }
        });
    }



}

