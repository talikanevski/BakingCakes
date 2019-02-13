package com.example.bakingcakes.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bakingcakes.Adapters.IngredientAdapter;
import com.example.bakingcakes.Adapters.StepsAdapter;
import com.example.bakingcakes.Models.Ingredient;
import com.example.bakingcakes.Models.Step;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import java.util.List;
import java.util.Objects;

public class DetailsFragment extends Fragment {
    private static final String TAG = DetailsFragment.class.getSimpleName();

    private static final String CURRENT_CAKE = "current cake";
    private static final String IMAGE = "image";
    private static Cake currentCake;
    private static byte[] byteArray;
    public static SharedPreferences recentCake;

    // Define a new interface OnStepClickListener that triggers a callback in the host activity
    public static OnStepClickListener mStepCallback;

    // OnStepClickListener interface, calls a method in the host activity named onStepSelected
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mStepCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + getString(R.string.must_implement_onstepclicklistener));
        }
    }

    public DetailsFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        //Providing Up navigation
        final Toolbar toolbar = rootView.findViewById(R.id.detail_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        final ImageView imageView = rootView.findViewById(R.id.backdrop);
        if (savedInstanceState == null) {
            Intent intent = Objects.requireNonNull(getActivity()).getIntent();

            if (intent.getExtras() != null) {
                currentCake = intent.getExtras().getParcelable(CURRENT_CAKE);

                //retrieve the Bitmap from the intent
//                Bitmap bmp;
                byteArray = getActivity().getIntent().getByteArrayExtra(IMAGE);
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                assert currentCake != null;
                imageView.setImageBitmap(bmp);
            }

        } else {
            currentCake = savedInstanceState.getParcelable(CURRENT_CAKE);
            //retrieve the Bitmap
//            Bitmap bmp;
            byteArray = savedInstanceState.getByteArray(IMAGE);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, Objects.requireNonNull(byteArray).length);
            assert currentCake != null;
            imageView.setImageBitmap(bmp);
        }

        TextView servings = rootView.findViewById(R.id.servings);
        servings.setText("Yield: " + currentCake.getServings() + getString(R.string._servings));

        List<Ingredient> ingredients = currentCake.getCakeIngredients();
        RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.ingredients_item_list);
        setupRecyclerViewForIngredients(ingredientsRecyclerView, ingredients);

        List<Step> steps = currentCake.getSteps();
        RecyclerView stepsRecyclerView = rootView.findViewById(R.id.steps_item_list);
            setupRecyclerViewForSteps(stepsRecyclerView, steps);

        // save selected recipe details to SharedPreferences for the widget to use
        recentCake =  getContext().getSharedPreferences(getString(R.string.pref_file_name),
                        Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = recentCake.edit();
        editor.putString(getString(R.string.cake_name_key), currentCake.getCakeName());
        editor.apply();

        // Setup FAB to share the ingredients of the current cake
        FloatingActionButton fabShare = rootView.findViewById(R.id.share_fab);
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
        CollapsingToolbarLayout collapsingToolbar = rootView.findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(currentCake.getCakeName());
        return rootView;
    }

    public void widgetIntent() {
        // and let the widget know there is a new recentCake to display
        Intent widgetIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        ((AppCompatActivity)getActivity()).sendBroadcast(widgetIntent);
    }

    private void setupRecyclerViewForIngredients(@NonNull RecyclerView
                                                         recyclerView, List<Ingredient> ingredients) {
        recyclerView.setAdapter(new IngredientAdapter(getContext(), ingredients));
    }

    private void setupRecyclerViewForSteps(@NonNull RecyclerView
                                                   recyclerView, List<Step> steps) {
        recyclerView.setAdapter(new StepsAdapter(getContext(), steps));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_CAKE, currentCake);
        outState.putByteArray(IMAGE, byteArray);
        widgetIntent();
        super.onSaveInstanceState(outState);
    }
}