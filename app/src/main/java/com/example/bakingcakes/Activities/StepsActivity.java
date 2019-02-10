package com.example.bakingcakes.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bakingcakes.R;

import java.util.Objects;

public class StepsActivity extends AppCompatActivity {
    public static final String CURRENT_STEP = "current step";
    public static final String CURRENT_STEP_NUMBER = "current step number";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        
        //Providing Up navigation
        final Toolbar toolbar = findViewById(R.id.step_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(DetailActivity.currentCake.getCakeName() + "Steps");

        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(DetailActivity.currentCake.getCakeName() + " Steps");
    }
    @Override //Providing Up navigation
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
