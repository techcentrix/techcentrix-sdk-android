package com.techcentrix.sdk.demo_java.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.techcentrix.sdk.demo_java.R;

public class DemoActivity extends AppCompatActivity {

    public DemoActivity() {
        super(R.layout.activity_demo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(findViewById(R.id.actionBarToolbar));
    }
}
