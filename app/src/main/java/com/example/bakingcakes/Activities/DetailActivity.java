package com.example.bakingcakes.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
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

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailActivity.class.getName();

    public static final String CURRENT_CAKE = "current cake";
    private View recyclerView;
    private IngredientAdapter adapter;
    public Cake currentCake;
    TextView servings;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView stepsRecyclerView;
    Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Providing Up navigation
        final Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.ingredients_item_list);
        assert recyclerView != null;

        Intent intent = getIntent();
        currentCake = intent.getParcelableExtra(CURRENT_CAKE);

        servings = findViewById(R.id.servings);
        servings.setText("Yield: " + currentCake.getServings() + " servings");
        currentCake.getCakeIngredients();

        ingredientsRecyclerView = findViewById(R.id.ingredients_item_list);
        assert recyclerView != null;

        List<Ingredient> ingredients = currentCake.getCakeIngredients();
        ingredientsRecyclerView = findViewById(R.id.ingredients_item_list);
        assert recyclerView != null;
        setupRecyclerViewForIngredients((RecyclerView) ingredientsRecyclerView, ingredients);

        List<Step> steps = currentCake.getSteps();
        stepsRecyclerView = findViewById(R.id.steps_item_list);
        assert recyclerView != null;
        setupRecyclerViewForSteps((RecyclerView) stepsRecyclerView, steps);
//
//        FloatingActionButton play = findViewById(R.id.playFab);
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        // Setup FAB to share the ingredients of the current cake
        FloatingActionButton fabShare = findViewById(R.id.share_fab);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                }
                i.putExtra(Intent.EXTRA_SUBJECT, currentCake.getCakeName());
                i.putExtra(Intent.EXTRA_TEXT, currentCake.getCakeIngredients().toArray()); //TODO doesn't work - improve
                startActivity(Intent.createChooser(i, getString(R.string.share_text_for_chooser) + currentCake.getCakeName()));
            }
        });
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(currentCake.getCakeName());
        loadBackdrop();

    }

    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        bitmap = currentCake.getCakeImage();
        assert currentCake != null;
        imageView.setImageBitmap(bitmap);
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
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
