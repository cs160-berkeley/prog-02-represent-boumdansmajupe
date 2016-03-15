package com.example.tiffanielo.weartest;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;


public class WatchListener extends WearableListenerService {
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
    private static final String REP_ONE = "/Paul Ryan";
    private static final String REP_TWO = "/Evan Low";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        if( messageEvent.getPath().equalsIgnoreCase( REP_ONE ) ) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, MainWatchView.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("RepName", "Paul Ryan");
            Log.d("T", "about to start watch MainActivity with RepName: Paul Ryan");
            System.out.println("Got to PhoneService");
            startActivity(intent);
        } else if (messageEvent.getPath().equalsIgnoreCase( REP_TWO )) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, MainWatchView.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("RepName", "Evan Low");
            Log.d("T", "about to start watch MainActivity with RepName: Evan Low");
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }


}
