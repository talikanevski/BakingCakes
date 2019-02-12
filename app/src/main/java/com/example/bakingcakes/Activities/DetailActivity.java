package com.example.bakingcakes.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingcakes.Adapters.IngredientAdapter;
import com.example.bakingcakes.Adapters.StepsAdapter;
import com.example.bakingcakes.Fragments.DetailsFragment;
import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.Models.Ingredient;
import com.example.bakingcakes.Models.Step;
import com.example.bakingcakes.R;
import com.example.bakingcakes.databinding.ActivityDetailBinding;

import android.content.SharedPreferences;
import android.appwidget.AppWidgetManager;
import android.content.Context;

import java.util.List;
import java.util.Objects;

import static com.example.bakingcakes.Activities.StepsActivity.CURRENT_STEP_NUMBER;

public class DetailActivity extends AppCompatActivity {

    public static final String CURRENT_CAKE = "current cake";
    public static final String IMAGE = "image";
    public static Cake currentCake;
    private static byte[] byteArray;
    public static SharedPreferences recentCake;
    private ActivityDetailBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        currentCake = Objects.requireNonNull(intent.getExtras()).getParcelable(CURRENT_CAKE);
        byteArray = getIntent().getByteArrayExtra(IMAGE);
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
}
