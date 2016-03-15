package com.example.tiffanielo.weartest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class CongressionalView extends AppCompatActivity {
    ImageButton RepNumberOne;
    ImageButton RepNumberTwo;
    final String TWITTER_KEY = "io27C40swOCvFrCZIuCbdB78u";
    final String TWITTER_SECRET = "MAammgS69oHLc5LZJY1GFekxkd7Gnnhci4CGj5OlgZt4B9Azly";
    String ZipCodeStr;
    String TwitterId;
    private TwitterLoginButton loginButton;
    ArrayList<String> TwitterIDS;
    ArrayList<TextView> TwitterFeeds = new ArrayList<>();
    TextView CurrFeed;
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
        System.out.println("Intentgetting");
        Intent intent = getIntent();
        ZipCodeStr = intent.getStringExtra("ZCFromMain");
        System.out.printf("ZIPCODESTRFROMINTENTEXTRA: %s", ZipCodeStr);

        System.out.println("beforeAsyncExecute");
        new DownloadWebpageTask().execute();
        System.out.println("afterAsyncExecute");


        RepNumberOne = (ImageButton) findViewById(R.id.BtnRepNumberOne);
        RepNumberTwo = (ImageButton) findViewById(R.id.BtnRepNumberTwo);

        RepNumberOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(CongressionalView.this, DetailedView.class);
                startActivity(sendIntent);
            }
        });

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
                    @Override
                    public void success(Result<AppSession> appSessionResult) {
                        AppSession session = appSessionResult.data;
                        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
                        TextView RepOneTFeed = (TextView) findViewById(R.id.RepOneTFeed);
                        TextView RepTwoTFeed = (TextView) findViewById(R.id.RepTwoTFeed);

                        TwitterFeeds.add(RepOneTFeed);
                        TwitterFeeds.add(RepTwoTFeed);

                        //for (int i = 0; i <= 1; i++) {
                            //CurrFeed = TwitterFeeds.get(i);
                            twitterApiClient.getStatusesService().userTimeline(null, TwitterId, 1, null, null, false, false, false, true, new Callback<List<Tweet>>() {
                                @Override
                                public void success(Result<List<Tweet>> listResult) {
                                    for (Tweet tweet : listResult.data) {
                                        Log.d("fabricstuff", "result: " + tweet.text + "  " + tweet.createdAt);
                                        TextView RepOneTFeed = (TextView) findViewById(R.id.RepOneTFeed);
                                        RepOneTFeed.setText(tweet.text);
                                        //CurrFeed.setText(tweet.text);
                                    }
                                }

                                @Override
                                public void failure(TwitterException e) {
                                    e.printStackTrace();
                                }
                            });
                            System.out.println("Before2ndcall");
                            Twitter.getApiClient().getAccountService().verifyCredentials(true, false, new Callback<User>() {
                                @Override
                                public void success(Result<User> userResult) {
                                    User user = userResult.data;
                                    String twitterImage = user.profileImageUrl;
                                    System.out.printf("TIMG: %s\n", twitterImage);
                                    //RepNumberOne.setImageBitmap(twitterImage);
                                    new DownLoadImageTask(RepNumberOne).execute(twitterImage);
                                }

                                @Override
                                public void failure(TwitterException e) {
                                    e.printStackTrace();
                                }
                            });

                        }
                   // }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                    }
                });

            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });



//        // TODO: Use a more specific parent
//        final ViewGroup parentView = (ViewGroup) getWindow().getDecorView().getRootView();
//        // TODO: Base this Tweet ID on some data from elsewhere in your app
//        long tweetId = 631879971628183552L;
//        TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
//            @Override
//            public void success(Result<Tweet> result) {
//                TweetView tweetView = new TweetView(CongressionalView.this, result.data);
//                parentView.addView(tweetView);
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//                Log.d("TwitterKit", "Load Tweet failure", exception);
//            }
//        });

//        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
//// Can also use Twitter directly: Twitter.getApiClient()
//        StatusesService statusesService = twitterApiClient.getStatusesService();
//        statusesService.show(524971209851543553L, null, null, null, new Callback<Tweet>() {
//            @Override
//            public void success(Result<Tweet> result) {
//                //Do something with result, which provides a Tweet inside of result.data
//            }
//
//            public void failure(TwitterException exception) {
//                //Do something on failure
//            }
//        });
            //FROM HERE
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));
//
//        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
//            @Override
//            public void success(Result<AppSession> appSessionResult) {
//                AppSession session = appSessionResult.data;
//                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
//                twitterApiClient.getStatusesService().userTimeline(null, TwitterId, 1, null, null, false, false, false, true, new Callback<List<Tweet>>() {
//                    @Override
//                    public void success(Result<List<Tweet>> listResult) {
//                        for (Tweet tweet : listResult.data) {
//                            Log.d("fabricstuff", "result: " + tweet.text + "  " + tweet.createdAt);
//                            TextView RepOneTFeed = (TextView) findViewById(R.id.RepOneTFeed);
//                            RepOneTFeed.setText(tweet.text);
//                        }
//                    }
//
//                    @Override
//                    public void failure(TwitterException e) {
//                        e.printStackTrace();
//                    }
//                });
//                System.out.println("Before2ndcall");
//                twitterApiClient.getAccountService().verifyCredentials(true, false, new Callback<User>() {
//                    @Override
//                    public void success(Result<User> userResult) {
//                        User user = userResult.data;
//                        String twitterImage = user.profileImageUrl;
//                        System.out.printf("TIMG: %s\n", twitterImage);
//                    }
//
//                    @Override
//                    public void failure(TwitterException e) {
//                        e.printStackTrace();
//                    }
//                });
//
//            }
//
//            @Override
//            public void failure(TwitterException e) {
//                e.printStackTrace();
//            }
//        });
//        System.out.println("BeforenewTwitterSession");
//        //TwitterAuthConfig authConfig2 = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));
//
//        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
//            @Override
//            public void success(Result<AppSession> appSessionResult) {
//                AppSession session = appSessionResult.data;
//                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
//                twitterApiClient.getAccountService().verifyCredentials(true, false, new Callback<User>() {
//                    @Override
//                    public void success(Result<User> userResult) {
//                        User user = userResult.data;
//                        String twitterImage = user.profileImageUrl;
//                        System.out.printf("TIMG: %s\n", twitterImage);
//                    }
//
//                    @Override
//                    public void failure(TwitterException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//
//            @Override
//            public void failure(TwitterException e) {
//                e.printStackTrace();
//            }
//        });
        System.out.println("AfterTwitterSession2");



    }
    //FROM TWITTER LOGIN BUTTON CODE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    public class DownloadWebpageTask extends AsyncTask<Void, Void, JSONObject> {
        private final String API_URL = "http://congress.api.sunlightfoundation.com/legislators/locate?";
        private final String API_KEY = "997ac79ce0c840b99af580620af6fc00";
        String zipcode;
        EditText UserInputEnterZip;
        //        @Override
//        protected String doInBackground(String... urls) {
//
//            // params comes from the execute() call: params[0] is the url.
//            try {
//                return downloadUrl(urls[0]);
//            } catch (IOException e) {
//                return "Unable to retrieve web page. URL may be invalid.";
//            }
//        }
        @Override
        protected JSONObject doInBackground(Void... urls) {
            //String email = emailText.getText().toString();
            //UserInputEnterZip = (EditText) findViewById(R.id.UserInputEnterZip);
            //zipcode = UserInputEnterZip.getText().toString();
            System.out.println("GOT TO ASYNC: got to do inbackground");
            zipcode = ZipCodeStr;
            System.out.printf("ZIPCODE:: + %s", zipcode);
            // Do some validation here

            try {
                //FROM CODE ON INTERNET... FOR REFERENCE
                //URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
                //congress.api.sunlightfoundation.com/legislators/locate?zip=94555&apikey=997ac79ce0c840b99af580620af6fc00

                URL url = new URL(API_URL + "zip=" + zipcode + "&apikey=" + API_KEY);
                System.out.printf("URL: + %s", url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        //stringBuilder.append(line).append("\n");
                        stringBuilder.append(line);
                    }
                    bufferedReader.close();
                    String res = stringBuilder.toString();
                    System.out.printf("RES STRING: %s", res);
                    JSONObject jObject  = new JSONObject(res); // json
//                    System.out.println("CreatedJsonObj");
//                    JSONObject data = jObject.getJSONObject("results"); // get data object
////                    JSONArray array = data.getJSONArray("bioguide_id");
////                    System.out.printf("array + %s", array);
//                    String RepName = data.getString("first_name"); // get the name from data.
//                    System.out.printf("repname: + %s", RepName);
                    return jObject;
                    //return stringBuilder.toString();

                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(JSONObject result) {
            if (result == null) {
                System.out.println("Errors result was null");
                return;
            }
            try {
                //JSONObject jObject  = new JSONObject(result); // json
                System.out.println("GOTHERETOPOSTEX");
                JSONObject jObject = result;
                JSONArray data = jObject.getJSONArray("results"); // get data object

                String RepName = data.getString(0); // get the name from data.
                System.out.printf("repname:" + "%s" + "\n", RepName);
                ImageView FirstRepColor = (ImageView) findViewById(R.id.FirstRepColor);
                ImageView SecondRepColor = (ImageView) findViewById(R.id.SecondRepColor);
                ArrayList<ImageView> ButtonsUsed = new ArrayList<>();
                ButtonsUsed.add(FirstRepColor);
                ButtonsUsed.add(SecondRepColor);

                for (int i = 0; i <= 1; i ++) {
                    JSONObject firstentry = data.getJSONObject(i);
                    String Representative = firstentry.getString("first_name") + " " + firstentry.getString("last_name");
                    System.out.printf("REPRESENTATIVE:" + "%s" + "\n", Representative);
                    String Party = firstentry.getString("party");
                    System.out.printf("Party: %s \n", Party);
                    String WebSiteLink = firstentry.getString("website");
                    String EmailLink = firstentry.getString("oc_email");
                    String TermStart = firstentry.getString("term_start");
                    String TermEnd = firstentry.getString("term_end");
                    TwitterId = firstentry.getString("twitter_id");
                    System.out.printf("TwitterID: %s", TwitterId);
                    //TwitterIDS.set(0,TwitterId);


                    if (Party.equals("D")) {
                        ButtonsUsed.get(i).setBackgroundColor(Color.parseColor("#c42153e8"));
                    } else if (Party.equals("R")) {
                        ButtonsUsed.get(i).setBackgroundColor(Color.parseColor("#b9f61d1d"));

                    } else {
                        ButtonsUsed.get(i).setBackgroundColor(Color.parseColor("#b4b4b4"));
                    }


                    //TextView RepNameView = (TextView) findViewById(R.id.RepNameView);
                    //RepNameView.setText("Penis");

                    //}
//                    String WebSiteLink = firstentry.getString("website");
//                    String EmailLink = firstentry.getString("oc_email");
//                    String TermStart = firstentry.getString("term_start");
//                    String TermEnd = firstentry.getString("term_end");
//                    TwitterId = firstentry.getString("twitter_id");
//                    System.out.printf("TwitterID: %s", TwitterId);
                    if (TwitterId != null) {
                        //TwitterIDS.add(TwitterId);
//                        TwitterIDS.set(0,TwitterId);
                    }
                    System.out.printf("TID: %s \n EmailLink: %s \n", TwitterId, EmailLink);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println("onpostexecute");
            //System.out.printf("AsyncResult: %s", result);
            //textView.setText(result);
        }
    }
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap>{
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }




}
