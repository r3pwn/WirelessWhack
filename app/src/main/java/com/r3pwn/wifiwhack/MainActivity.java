package com.r3pwn.wifiwhack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView sessiontv = (TextView) findViewById(R.id.sessionTV);
        EditText sessionet = (EditText) findViewById(R.id.sessionET);
        Button session = (Button) findViewById(R.id.sessionBtn);
    }
}
