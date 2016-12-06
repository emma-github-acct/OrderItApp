package com.example.emma.orderitapp;

/**
 * Controller to display, confirm and email the Take Out Order.
 */

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private Customer customer;
    private Business businessObject;
    private Order orderObject;
    private LinearLayout businessLayout;
    private LinearLayout orderLayout;
    private LinearLayout customerLayout;
    private LinearLayout.LayoutParams params;
    private int COLUMN_COUNT = 1;
    private int windowWidth;
    private String TOTAL = "Total: ";
    private ArrayList<String> businessAttributes;
    private ArrayList<String> customerAttributes;
    private ArrayList<String> orderAttributes;

    private LayoutManager layoutManager;
    private String orderData; //email body of the order

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.businessObject = (Business) intent.getSerializableExtra("business");
        this.orderObject = (Order) intent.getSerializableExtra("order");
        this.customer = new Customer(this);

        layoutManager = new LayoutManager();
        setContentView(layoutManager.getCheckoutLayout(businessObject.getName()));

        loadOrderDataForEmail();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupLayout();
    }

    private void assignAttributes() {
        this.businessAttributes = new ArrayList<String>();
        this.orderAttributes = new ArrayList<String>();
        this.customerAttributes = new ArrayList<String>();

        // Business
        this.businessAttributes.add(businessObject.getName());
        this.businessAttributes.add(businessObject.getPhone());
        this.businessAttributes.add(businessObject.getAddress());

        // Order
        ArrayList<MenuItem> o = orderObject.getOrder();
        for (MenuItem i : o) {
            String orderEntry = i.getName() + "  " + i.getPrice() + "  " + i.getQuantity();
            this.orderAttributes.add( orderEntry );
        }
        this.orderAttributes.add(TOTAL + orderObject.getTotal());

        // Customer
        this.customerAttributes.add(customer.getName());
        this.customerAttributes.add(customer.getEmail());
        this.customerAttributes.add(customer.getPhone());
    }

    //  Set Up General Layout
    private void setupLayout() {
        assignAttributes();

        businessLayout = (LinearLayout) findViewById( R.id.business_info_view );
        customerLayout = (LinearLayout) findViewById( R.id.customer_info_view );
        orderLayout = (LinearLayout) findViewById( R.id.order_info_view );

        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );

        // Get width of screen
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int paddingLeft = businessLayout.getPaddingLeft();
        int paddingRight = businessLayout.getPaddingRight();
        windowWidth = size.x - paddingLeft - paddingRight;

        // Programmatically Show Info
        setupCheckoutInfo(businessAttributes, businessLayout);
        setupCheckoutInfo(orderAttributes, orderLayout);
        setupCheckoutInfo(customerAttributes, customerLayout);
    }


    private void setupCheckoutInfo( ArrayList<String> attr, LinearLayout layout){
        layout.removeAllViews();
        GridLayout infoGrid;
        infoGrid = new GridLayout( this );
        infoGrid.setPadding( 10, 10, 10, 10 );
        infoGrid.setColumnCount( COLUMN_COUNT );
        infoGrid.setRowCount( attr.size() );

        try {
            for (int i = 0; i < attr.size(); i++) {

                TextView t = new TextView( this );
                t.setText( attr.get( i ));
                t.setTextAppearance( layoutManager.getSubTextStyle( businessObject.getName() ));
                infoGrid.addView( t, windowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            infoGrid.setLayoutParams( params );
            layout.addView( infoGrid );

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    public void loadOrderDataForEmail() {
        this.orderData = "";
        orderData = "Restaurant: " + businessObject.getName() +  "\n\n";
        orderData += "Customer: " + customer.getName() + " " + customer.getEmail() + "\n\n";
        orderData += "Item    Price    Quantity \n";

        ArrayList<MenuItem> o = orderObject.getOrder();
        for (MenuItem i : o) {
            orderData += i.getName() + "  ";
            orderData += i.getPrice() + "  ";
            orderData += i.getQuantity() + "\n";
        }
        orderData += "Total " + orderObject.getTotal() + "\n";
        if (orderObject.getTotal().equals("$0.00")){
            Toast.makeText(this, "Your order is empty!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean confirmCustomerSettingsInput(Customer customer) {
        String name = getResources().getString(R.string.default_name);
        String email = getResources().getString(R.string.default_email);
        String phoneNumber = getResources().getString(R.string.default_phone_number);
        String address = getResources().getString(R.string.default_address);

        boolean validName = !(customer.getName().equals(name));
        boolean validEmail = !(customer.getEmail().equals(email));
        boolean validPhoneNumber = !(customer.getPhone().equals(phoneNumber));
        boolean validAddress = !(customer.getAddress().equals(address));

        return (validName && validEmail && validPhoneNumber && validAddress);
    }

    /**
     * Send order by email
     * Threaded. Starts Receipt activity on result code.
     *
     * @param v
     */

    public void sendOrder(View v) {
        boolean inputSettings = confirmCustomerSettingsInput(customer);

        // If no Costumer info from settings
        if (!inputSettings) {
            Toast.makeText(this, R.string.settings_error, Toast.LENGTH_LONG).show();
            startSettings(null);
        }
        // Else send order
        else {
            OrderDatabase orderDatabaseObject = new OrderDatabase(this);
            orderDatabaseObject.insert(orderObject.getDate(), businessObject.getName(), orderObject.getTotal());

            String[] addresses = {businessObject.getEmail(), customer.getEmail()};
            String subject = "TakeOut Order";
            String body = orderData;

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
            emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);

            try {
                startActivityForResult(emailIntent, 1);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, R.string.noEmailError, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(this, R.string.order_sent, Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

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
        Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
        i.putExtra("business", businessObject);
        i.putExtra("order", orderObject);
        startActivity(i);
    }

    public void startHistory(View v) {
        Intent i = new Intent(getApplicationContext(), OrderHistoryActivity.class);
        i.putExtra("business", businessObject);
        i.putExtra("order", orderObject);
        startActivity(i);
    }

    public void startScan(View v) {
        Intent i = new Intent(getApplicationContext(), QRCodeReaderRestaurant.class);
        i.putExtra("business", businessObject);
        i.putExtra("order", orderObject);
        startActivity(i);
    }

    public void startMain(View v) {
        confirmAppRestart();
    }
// End menu code

    private void confirmAppRestart() {
        final Dialog restartConfirmation = new Dialog(CheckoutActivity.this);
        restartConfirmation.setContentView(R.layout.confirm_restart);
        restartConfirmation.setTitle("Restarting App");
        Button confirm = (Button) restartConfirmation.findViewById(R.id.restart_confirm);
        Button cancel = (Button) restartConfirmation.findViewById(R.id.restart_cancel);

        restartConfirmation.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
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

