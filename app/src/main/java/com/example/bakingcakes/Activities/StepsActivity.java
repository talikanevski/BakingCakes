package com.example.bakingcakes.Activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.bakingcakes.R;

import java.util.Objects;

public class StepsActivity extends AppCompatActivity {
    public static final String CURRENT_STEP = "current step";
    public static final String CURRENT_STEP_NUMBER = "current step number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        //Providing Up navigation
        final Toolbar toolbar = findViewById(R.id.step_toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(DetailActivity.currentCake.getCakeName() + "Steps"); //TODO  doesn't work!!!
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
