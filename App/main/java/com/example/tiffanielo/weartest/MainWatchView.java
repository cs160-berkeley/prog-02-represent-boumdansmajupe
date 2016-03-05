package com.example.tiffanielo.weartest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainWatchView extends Activity {

    private TextView mTextView;
    private ImageButton mFeedBtn;
    private Button voteViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_main_watch_view);

        mFeedBtn = (ImageButton) findViewById(R.id.RepOneBtn);
        voteViewBtn = (Button) findViewById(R.id.voteBtn);
        voteViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(MainWatchView.this, DetailedView.class);
                startActivity(sendIntent);
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String catName = extras.getString("RepName");
            System.out.println("repname okay");
            //mFeedBtn.setText("Feed " + catName);
        }
        System.out.println("BeforeRepOneBtnClicked");
        if (mFeedBtn != null) {
            mFeedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("InOnClick");
                    Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                    startService(sendIntent);
                    System.out.println("AfterIntentSent");
                }
            });
        }
    }
}
