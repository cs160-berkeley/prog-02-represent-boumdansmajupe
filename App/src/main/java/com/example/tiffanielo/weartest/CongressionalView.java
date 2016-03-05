package com.example.tiffanielo.weartest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Intent;

public class CongressionalView extends AppCompatActivity {
    ImageButton RepNumberOne;
    ImageButton RepNumberTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congressional_view);
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

        RepNumberOne = (ImageButton) findViewById(R.id.BtnRepNumberOne);
        RepNumberTwo = (ImageButton) findViewById(R.id.BtnRepNumberTwo);

        RepNumberOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(CongressionalView.this, DetailedView.class);
                startActivity(sendIntent);
            }
        });


    }

}
