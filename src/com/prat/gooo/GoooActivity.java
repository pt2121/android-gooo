package com.prat.gooo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GoooActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onTestButtonClick(View v) {
    	Intent intent = new Intent(this, AnimationTest.class);
        startActivity(intent);
    }
}