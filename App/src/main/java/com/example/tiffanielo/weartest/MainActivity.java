package com.example.tiffanielo.weartest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.tiffanielo.weartest.MESSAGE";

    //int ZipOrLocSelection;
    RadioButton UseCurrLoc;
    RadioGroup ZipOrLocGroup;
    Button GetReps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //ZipOrLocCalls if GetReps button is clicked.
        ZipOrLoc();


    }

    public void ZipOrLoc() {
        ZipOrLocGroup = (RadioGroup) findViewById(R.id.ZipOrLoc);
        GetReps = (Button) findViewById(R.id.ClickZipOrLoc);

        GetReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = ZipOrLocGroup.getCheckedRadioButtonId();
                UseCurrLoc = (RadioButton) findViewById(selectedId);
                boolean Res = true;
                String RadButtTxt = UseCurrLoc.getText().toString();
                if (RadButtTxt.equals("USE CURRENT LOCATION")) {
                    Res = true;
                } else {
                    Res = false;
                }
                if (Res) {
                    //WHEN GETREPS IS CLICKED THIS HAPPENS IF USE CURR LOC IS ALSO CHECKED
                    //next(GetReps);
                    System.out.println("RESSTUFF");
                    GetReps.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatch.class);
                            System.out.println("B4");
                            sendIntent.putExtra("RepName", "Paul Ryan");
                            System.out.println("After");
                            startService(sendIntent);
                        }
                    });
                    System.out.println("BEFORE NEXTGETREPS");
                    next(GetReps);
                    System.out.println("AFTERNEXTGETREPS");



                } else {
                    next(GetReps);
                    GetReps.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatch.class);
                            System.out.println("B4");
                            sendIntent.putExtra("RepName", "Paul Ryan");
                            System.out.println("After");
                            startService(sendIntent);
                        }
                    });
                }
            }
        });
    }
    public void next(View view) {
            Intent intent = new Intent(this, CongressionalView.class);
            EditText editText = (EditText) findViewById(R.id.UserInputEnterZip);
            String message = editText.getText().toString();
            if(message.length() > 0 ) {
                intent.putExtra(EXTRA_MESSAGE, message);
                System.out.println("Nextwascalled");
                startActivity(intent);
            }
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatch.class);
        System.out.println("B4");
        sendIntent.putExtra("RepName", "Paul Ryan");
        System.out.println("After");
        startService(sendIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
