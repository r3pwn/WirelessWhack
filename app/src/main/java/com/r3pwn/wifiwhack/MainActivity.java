package com.r3pwn.wifiwhack;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    boolean isButtonAngry;
    Button angrybtn;
    String[] sessionID = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TextView sessiontv = (TextView) findViewById(R.id.sessionTV);
        final EditText sessionet = (EditText) findViewById(R.id.sessionET);
        final Button sessionbtn = (Button) findViewById(R.id.sessionBtn);
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.main);
        angrybtn = (Button) findViewById(R.id.whackbtn);

        final android.support.v7.app.ActionBar ab = getSupportActionBar();

        sessionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionID[0] = sessionet.getText().toString();
                sessiontv.setVisibility(GONE);
                sessionbtn.setVisibility(GONE);
                sessionet.setVisibility(GONE);
                ab.hide();
                angrybtn.setVisibility(View.VISIBLE);
                angrybtn.setBackgroundColor(Color.WHITE);
                rl.setPadding(0, 0, 0, 0);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(sessionet.getWindowToken(), 0);
                AngryTimer at = new AngryTimer();
                at.sendEmptyMessage(AngryTimer.NEEDS_LESS_ANGRY); // idk why this is being stupid
            }
        });
    }

    private class SubmitScore extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String[] sessID)
        {
            // this is the hardcoded URL of my Pi, you'll need to change this for your server
            String url = url = "http://192.168.10.1/add_to_score.php?sessid=" + sessID[0];
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // we have recieved a response
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // there was an error
                }
            });
            queue.add(stringRequest);
            return null;
        }
    }


    private class WhiteTapPenalizeScore extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String[] sessID)
        {
            // this is the hardcoded URL of my Pi, you'll need to change this for your server
            String url = url = "http://192.168.10.1/remove_from_score.php?sessid=" + sessID[0];
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // we have recieved a response
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // there was an error
                }
            });
            queue.add(stringRequest);
            return null;
        }
    }

    private class RedMissTapPenalizeScore extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String[] sessID)
        {
            // this is the hardcoded URL of my Pi, you'll need to change this for your server
            String url = url = "http://192.168.10.1/remove_from_score_rm.php?sessid=" + sessID[0];
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // we have recieved a response
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // there was an error
                }
            });
            queue.add(stringRequest);
            return null;
        }
    }

    public class AngryTimer extends Handler
    {
        public static final int NEEDS_LESS_ANGRY = 0; // red needing to turn white
        public static final int NEEDS_MORE_ANGRY = 1; // white needing to turn red
        boolean isFirstRun = true;

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case NEEDS_LESS_ANGRY:
                    Random rand = new Random();
                    int seconds = rand.nextInt(3) + 2;
                    sendEmptyMessageDelayed(NEEDS_MORE_ANGRY, seconds * 1000); // convert seconds to milliseconds by multiplying 1000
                    if (!isFirstRun){
                        angrybtn.setBackgroundColor(Color.WHITE);
                        isButtonAngry = false;
                        angrybtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new WhiteTapPenalizeScore().execute(sessionID);
                            }
                        });
                    }
                    isFirstRun = false;
                    break;
                case NEEDS_MORE_ANGRY:
                    Random rand2 = new Random();
                    int seconds2 = rand2.nextInt(3) + 2;
                    sendEmptyMessageDelayed(NEEDS_LESS_ANGRY, seconds2 * 1000);
                    if (!isFirstRun){
                        angrybtn.setBackgroundColor(Color.RED);
                        isButtonAngry = true;
                        angrybtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new SubmitScore().execute(sessionID);
                                view.setBackgroundColor(Color.WHITE);
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new WhiteTapPenalizeScore().execute(sessionID);
                                    }
                                });
                            }
                        });
                    }
                    isFirstRun = false;
                    break;
                default:
                    removeMessages(NEEDS_LESS_ANGRY);
                    removeMessages(NEEDS_MORE_ANGRY);
                    break;
            }
        }
    }
}
