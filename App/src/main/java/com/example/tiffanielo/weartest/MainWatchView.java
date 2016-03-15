package com.example.tiffanielo.weartest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;


public class MainWatchView extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "tiffanielo";
    private static final String TWITTER_SECRET = "@1Firebolt";


    private TextView mTextView;
    private ImageButton RepOneBtn;
    private Button voteViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.round_activity_main_watch_view);

        RepOneBtn = (ImageButton) findViewById(R.id.RepOneBtn);
        System.out.println("RepOneBtn" + RepOneBtn.toString());
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
        if (RepOneBtn != null) {
            RepOneBtn.setOnClickListener(new View.OnClickListener() {
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
