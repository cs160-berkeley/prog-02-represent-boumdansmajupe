package com.example.tiffanielo.weartest;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "io27C40swOCvFrCZIuCbdB78u";
    private static final String TWITTER_SECRET = "MAammgS69oHLc5LZJY1GFekxkd7Gnnhci4CGj5OlgZt4B9Azly";

    public final static String EXTRA_MESSAGE = "com.example.tiffanielo.weartest.MESSAGE";
    private GoogleApiClient mGoogleApiClient;

    Location mLastLocation;
    EditText mLatitudeText;
    EditText mLongitudeText;
    String mLatitude;
    String mLongitude;


    //int ZipOrLocSelection;
    RadioButton UseCurrLoc;
    RadioGroup ZipOrLocGroup;
    Button GetReps;
    EditText UserInputZC;
    String ZipCodeStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
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
        //Added from Step 3
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)  // used for data layer API
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


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
                UserInputZC = (EditText) findViewById(R.id.UserInputEnterZip);
                ZipCodeStr = UserInputZC.getText().toString();
//                System.out.println("beforeAsyncExecute");
//                new DownloadWebpageTask().execute();
//                System.out.println("afterAsyncExecute");
                //int ZipCode = Integer.parseInt(ZipCodeStr);
                if (RadButtTxt.equals("USE CURRENT LOCATION")) {
                    Res = true;
                } else {
                    Res = false;
                }
                if (Res) {
                    //WHEN GETREPS IS CLICKED THIS HAPPENS IF USE CURR LOC IS ALSO CHECKED
                    //next(GetReps);
                    System.out.println("RESSTUFF");
//                    GetReps.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatch.class);
//                            System.out.println("B4");
//                            sendIntent.putExtra("RepName", "Paul Ryan");
//                            System.out.println("After111");
//                            startService(sendIntent);
//                        }
//                    });
                    System.out.println("BEFORE NEXTGETREPS");
                    next(GetReps);
                    System.out.println("AFTERNEXTGETREPS");


                } else {
                    next(GetReps);
//                    GetReps.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent sendIntent = new Intent(getBaseContext(), PhoneToWatch.class);
//                            System.out.println("B4");
//                            sendIntent.putExtra("RepName", "Paul Ryan");
//                            System.out.println("After000");
//                            startService(sendIntent);
//                        }
//                    });
                }
            }
        });
    }
    public void next(View view) {
            Intent intent = new Intent(this, CongressionalView.class);
//            EditText editText = (EditText) findViewById(R.id.UserInputEnterZip);
//            String message = editText.getText().toString();
            UserInputZC = (EditText) findViewById(R.id.UserInputEnterZip);
            String ZipCodeStr = UserInputZC.getText().toString();
            int ZipCode = Integer.parseInt(ZipCodeStr);
            if(ZipCodeStr.length() > 0 ) {
                intent.putExtra("ZCFromMain", ZipCodeStr);
                System.out.println("Nextwascalled");
                startActivity(intent);
            }
        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatch.class);
        System.out.println("B4");
        sendIntent.putExtra("RepName", "Paul Ryan");
        System.out.println("After222");
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
    //After this line added from Step 3
    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitude = String.valueOf(mLastLocation.getLatitude());
            mLongitude = String.valueOf(mLastLocation.getLongitude());
            System.out.printf("Latitude + %s + Longitude + %s", mLatitude, mLongitude);
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {}
//
//    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
//    // URL string and uses it to create an HttpUrlConnection. Once the connection
//    // has been established, the AsyncTask downloads the contents of the webpage as
//    // an InputStream. Finally, the InputStream is converted into a string, which is
//    // displayed in the UI by the AsyncTask's onPostExecute method.
//    public class DownloadWebpageTask extends AsyncTask<Void, Void, JSONObject> {
//        private final String API_URL = "http://congress.api.sunlightfoundation.com/legislators/locate?";
//        private final String API_KEY = "997ac79ce0c840b99af580620af6fc00";
//        String zipcode;
//        EditText UserInputEnterZip;
////        @Override
////        protected String doInBackground(String... urls) {
////
////            // params comes from the execute() call: params[0] is the url.
////            try {
////                return downloadUrl(urls[0]);
////            } catch (IOException e) {
////                return "Unable to retrieve web page. URL may be invalid.";
////            }
////        }
//        @Override
//        protected JSONObject doInBackground(Void... urls) {
//            //String email = emailText.getText().toString();
//            //UserInputEnterZip = (EditText) findViewById(R.id.UserInputEnterZip);
//            //zipcode = UserInputEnterZip.getText().toString();
//            System.out.println("GOT TO ASYNC: got to do inbackground");
//            zipcode = ZipCodeStr;
//            System.out.printf("ZIPCODE:: + %s", zipcode);
//            // Do some validation here
//
//            try {
//                //FROM CODE ON INTERNET... FOR REFERENCE
//                //URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
//                //congress.api.sunlightfoundation.com/legislators/locate?zip=94555&apikey=997ac79ce0c840b99af580620af6fc00
//
//                URL url = new URL(API_URL + "zip=" + zipcode + "&apikey=" + API_KEY);
//                System.out.printf("URL: + %s", url);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                try {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        //stringBuilder.append(line).append("\n");
//                        stringBuilder.append(line);
//                    }
//                    bufferedReader.close();
//                    String res = stringBuilder.toString();
//                    System.out.printf("RES STRING: %s", res);
//                    JSONObject jObject  = new JSONObject(res); // json
////                    System.out.println("CreatedJsonObj");
////                    JSONObject data = jObject.getJSONObject("results"); // get data object
//////                    JSONArray array = data.getJSONArray("bioguide_id");
//////                    System.out.printf("array + %s", array);
////                    String RepName = data.getString("first_name"); // get the name from data.
////                    System.out.printf("repname: + %s", RepName);
//                    return jObject;
//                    //return stringBuilder.toString();
//
//                }
//                finally{
//                    urlConnection.disconnect();
//                }
//            }
//            catch(Exception e) {
//                Log.e("ERROR", e.getMessage(), e);
//                return null;
//            }
//        }
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(JSONObject result) {
//            TextView RepNameView = (TextView) findViewById(R.id.RepNameView);
//            RepNameView.setText("Penis");
//            if (result == null) {
//                System.out.println("Errors result was null");
//                return;
//            }
//            try {
//                //JSONObject jObject  = new JSONObject(result); // json
//                System.out.println("GOTHERETOPOSTEX");
//                JSONObject jObject = result;
//                JSONArray data = jObject.getJSONArray("results"); // get data object
//                //JsonElement elem = data.getJSONe(0);
//
//                String RepName = data.getString(0); // get the name from data.
//                System.out.printf("repname:"  + "%s" + "\n", RepName);
//
//                //for (int i = 0; i <= 3; i ++) {
//                    JSONObject firstentry = data.getJSONObject(0);
//                    String Representative = firstentry.getString("first_name") + " " + firstentry.getString("last_name");
//                    System.out.printf("REPRESENTATIVE:" + "%s" + "\n", Representative);
//                    //TextView RepNameView = (TextView) findViewById(R.id.RepNameView);
//                    //RepNameView.setText("Penis");
//
//                //}
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("onpostexecute");
//            //System.out.printf("AsyncResult: %s", result);
//            //textView.setText(result);
//        }
//    }
//
//
}
