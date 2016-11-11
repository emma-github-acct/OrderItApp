package com.example.emma.orderitapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }

    public void scanBuisnessQR() {
        /*
        this.answerList = new ArrayList<String>();

        try {

            InputStream iFile = currentContext.getResources().openRawResource( R.raw.possible_answers );
            Scanner scan = new Scanner( iFile );
            while ( scan.hasNextLine() ) {
                String stringInput = scan.nextLine();
                answerList.add( stringInput );

            }

        } catch ( IllegalStateException ise ){
            Toast.makeText( currentContext, R.string.input_error, Toast.LENGTH_LONG).show();

        } catch ( IllegalArgumentException ire ){
            Toast.makeText( currentContext, R.string.input_error, Toast.LENGTH_LONG).show();
        }

    }

    */
    }
}
