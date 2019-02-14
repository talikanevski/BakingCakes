package com.example.bakingcakes.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.bakingcakes.Fragments.DetailsFragment;
import com.example.bakingcakes.Fragments.StepFragment;
import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.R;
import com.example.bakingcakes.databinding.ActivityDetailBinding;

import android.content.SharedPreferences;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity implements DetailsFragment.OnStepClickListener{

    public static final String CURRENT_CAKE = "current cake";
    public static final String IMAGE = "image";
    public static Cake currentCake;
    public static SharedPreferences recentCake;
    private ActivityDetailBinding binding;
    private boolean mTwoPane;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        currentCake = Objects.requireNonNull(intent.getExtras()).getParcelable(CURRENT_CAKE);
        byte[] byteArray = getIntent().getByteArrayExtra(IMAGE);
        mTwoPane = findViewById(R.id.tablet_details_fragment) != null;

        setContentView(R.layout.activity_detail);

    }

    @Override //Providing Up navigation
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //it was my first option
//                NavUtils.navigateUpFromSameTask(this);

                // this is a second option
                //from here: https://stackoverflow.com/questions/6554317/savedinstancestate-is-always-null
//                Intent intent = NavUtils.getParentActivityIntent(this);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                NavUtils.navigateUpTo(this, intent);

                //3rd option
//                finish();

                //last option
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepSelected(int position) {
        if (mTwoPane) {
            StepFragment stepFragment =
                    (StepFragment) getSupportFragmentManager().findFragmentById(R.id.tablet_details_fragment);
            Objects.requireNonNull(stepFragment).setUp(position);
        }
    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putParcelable(CURRENT_CAKE, currentCake);
//        outState.putByteArray(IMAGE, byteArray);
//        super.onSaveInstanceState(outState);
//    }
}
