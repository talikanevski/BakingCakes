package com.example.bakingcakes.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bakingcakes.R;

public class StepsActivity extends AppCompatActivity {
    public static final String CURRENT_STEP = "current step";
    public static final String CURRENT_STEP_NUMBER = "current step number";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
    }
}
