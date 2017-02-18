package com.r3pwn.wifiwhack;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final String[] sessionID = new String[1];

        final TextView sessiontv = (TextView) findViewById(R.id.sessionTV);
        final EditText sessionet = (EditText) findViewById(R.id.sessionET);
        final Button sessionbtn = (Button) findViewById(R.id.sessionBtn);

        sessionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionID[0] = sessionet.getText().toString();
                sessiontv.setVisibility(GONE);
                sessionbtn.setVisibility(GONE);
                sessionet.setVisibility(GONE);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(sessionet.getWindowToken(), 0);
                new SubmitScore().execute(sessionID);
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
}
