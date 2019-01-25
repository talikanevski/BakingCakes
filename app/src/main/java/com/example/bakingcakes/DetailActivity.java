package com.example.bakingcakes;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakingcakes.Adapters.IngredientAdapter;
import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.Models.Ingredient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailActivity.class.getName();

    public static final String CURRENT_CAKE = "current cake";
    private View recyclerView;
    private IngredientAdapter adapter;
    public Cake currentCake;
    TextView cakeName;
    ImageView cakeImage;
    TextView servings;
    private RecyclerView ingredientList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        recyclerView = findViewById(R.id.ingredients_item_list);
        assert recyclerView != null;
//        setupRecyclerView((RecyclerView) recyclerView);

        Intent intent = getIntent();
        currentCake = intent.getParcelableExtra(CURRENT_CAKE);

        cakeName = findViewById(R.id.cake_name);
        cakeName.setText(currentCake.getCakeName());
        servings = findViewById(R.id.servings);
        servings.setText("Yield: " + currentCake.getServings() + " servings");
        currentCake.getCakeIngredients();

        ingredientList = findViewById(R.id.ingredients_item_list);
        assert recyclerView != null;
        updateRecyclerView((RecyclerView) ingredientList, currentCake.getCakeIngredients());
//
//        List<Ingredient> ingredients = null;
//        try {
//            ingredients = Utils.extractIngredientsFromJson((JSONArray) currentCake.getCakeIngredients());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        ingredientsRecyclerView = findViewById(R.id.ingredients_item_list);
//        assert recyclerView != null;
//        updateRecyclerView((RecyclerView) ingredientsRecyclerView, ingredients);


//        cakeImage = findViewById(R.id.cake_image);
//        Picasso.get().
//                load(currentCake.getCakeImage()) //TODO I still have no images for the cakes
//                .into(cakeImage);

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
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new IngredientAdapter(this, new ArrayList<Ingredient>()));
    }

    private void updateRecyclerView(@NonNull RecyclerView recyclerView, List<Ingredient> ingredients) {
         recyclerView.setAdapter(new IngredientAdapter(this, ingredients));
    }

}
