package com.example.bakingcakes.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingcakes.Adapters.IngredientAdapter;
import com.example.bakingcakes.Adapters.StepsAdapter;
import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.Models.Ingredient;
import com.example.bakingcakes.Models.Step;
import com.example.bakingcakes.R;
import com.example.bakingcakes.Utils;

import android.content.SharedPreferences;
import android.appwidget.AppWidgetManager;
import android.content.Context;

import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    public static final String CURRENT_CAKE = "current cake";
    public static final String IMAGE = "image";
    public static Cake currentCake;
    private static byte[] byteArray;
    public static SharedPreferences recentCake;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Providing Up navigation
        final Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        final ImageView imageView = findViewById(R.id.backdrop);
        if (savedInstanceState == null) {
            Intent intent = getIntent();

            if (intent.getExtras() != null) {
                currentCake = intent.getExtras().getParcelable(CURRENT_CAKE);

                //retrieve the Bitmap from the intent
                Bitmap bmp;
                byteArray = getIntent().getByteArrayExtra(IMAGE);
                bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                assert currentCake != null;
                imageView.setImageBitmap(bmp);
            }

        } else {
            currentCake = savedInstanceState.getParcelable(CURRENT_CAKE);
            //retrieve the Bitmap
            Bitmap bmp;
            byteArray = savedInstanceState.getByteArray(IMAGE);
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, Objects.requireNonNull(byteArray).length);
            assert currentCake != null;
            imageView.setImageBitmap(bmp);
        }

        TextView servings = findViewById(R.id.servings);
        servings.setText(getString(R.string.yeild) + currentCake.getServings() + getString(R.string._servings));

        List<Ingredient> ingredients = currentCake.getCakeIngredients();
        RecyclerView ingredientsRecyclerView = findViewById(R.id.ingredients_item_list);
        setupRecyclerViewForIngredients(ingredientsRecyclerView, ingredients);

        List<Step> steps = currentCake.getSteps();
        RecyclerView stepsRecyclerView = findViewById(R.id.steps_item_list);
        setupRecyclerViewForSteps(stepsRecyclerView, steps);

        // save selected recipe details to SharedPreferences for the widget to use
        recentCake = getSharedPreferences(getString(R.string.pref_file_name), Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = recentCake.edit();
        editor.putString(getString(R.string.cake_name_key), currentCake.getCakeName());
        editor.apply();

        // Setup FAB to share the ingredients of the current cake
        FloatingActionButton fabShare = findViewById(R.id.share_fab);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                }
                i.putExtra(Intent.EXTRA_SUBJECT, currentCake.getCakeName());
                i.putExtra(Intent.EXTRA_TEXT, currentCake.getCakeName()
                        + ".\n" + IngredientAdapter.ingredientsForWidget);
                startActivity(Intent.createChooser(i, "Ingredients for " + currentCake.getCakeName()));
            }
        });
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(currentCake.getCakeName());
    }

    private void widgetIntent() {
        // and let the widget know there is a new recentCake to display
        Intent widgetIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(widgetIntent);
    }

    private void setupRecyclerViewForIngredients(@NonNull RecyclerView recyclerView, List<Ingredient> ingredients) {
        recyclerView.setAdapter(new IngredientAdapter(this, ingredients));
    }

    private void setupRecyclerViewForSteps(@NonNull RecyclerView recyclerView, List<Step> steps) {
        recyclerView.setAdapter(new StepsAdapter(this, steps));
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_CAKE, currentCake);
        outState.putByteArray(IMAGE, byteArray);
        widgetIntent();
        super.onSaveInstanceState(outState);
    }
}
