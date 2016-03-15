package com.example.tiffanielo.weartest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailedView extends AppCompatActivity {
    TextView RepNameView;
    TextView PositionInput;
    TextView TermEndInput;
    TextView BillOneInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);
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
        RepNameView = (TextView) findViewById(R.id.RepNameView);
        PositionInput = (TextView) findViewById(R.id.PositionInput);
        TermEndInput = (TextView) findViewById(R.id.TermEndInput);
        BillOneInput = (TextView) findViewById(R.id.BillOneInput);
        basedOnRepChangeInfo();
    }

    public void basedOnRepChangeInfo() {
        System.out.println("BasedOnRepChangeInfoWasCalled");

    }

}
