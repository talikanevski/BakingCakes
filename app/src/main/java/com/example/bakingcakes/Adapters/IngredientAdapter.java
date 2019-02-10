package com.example.bakingcakes.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingcakes.Activities.DetailActivity;
import com.example.bakingcakes.Models.Ingredient;
import com.example.bakingcakes.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private Context mContext;
    private final List<Ingredient> ingredientList;
    private Ingredient currentIngredient;
    private Double quantity;
    private String newMeasure;
    public static String ingredientsForWidget = "Ingredients:";

    public IngredientAdapter(Context context,
                             List<Ingredient> ingredients) {
        this.ingredientList = ingredients;
        this.mContext = context;//TODO add boolean mTwoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.ingredient_item_list_content, parent, false);
        return new ViewHolder(view);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView quantityTv;
        final TextView measureTv;
        final TextView ingredientTv;

        ViewHolder(View view) {
            super(view);
            quantityTv = view.findViewById(R.id.quantity_tv);
            measureTv = view.findViewById(R.id.measure_tv);
            ingredientTv = view.findViewById(R.id.ingredient_tv);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        currentIngredient = ingredientList.get(position);
        quantity = currentIngredient.getIngredientQuantity();
//        String newQuantity = quantityPolish(quantity);
//        holder.quantityTv.setText(newQuantity + " ");
        holder.quantityTv.setText(quantity + " ");
        String measure = currentIngredient.getIngredientMeasure();
        newMeasure = measurePolish(measure, quantity);
        holder.measureTv.setText(newMeasure + " ");
        holder.ingredientTv.setText(currentIngredient.getIngredientName());
        saveIngredientsToOneString();
    }

    //Since RecyclerView is not supported for app widgets,
    // I have to format ingredients for widget differently then I did in RecyclerViewForIngredients
    // Also, I use this String to share ingredients via fab
    private void saveIngredientsToOneString() {
        // save details to SharedPreferences for the widget to use
        ingredientsForWidget = ingredientsForWidget + "\n"+ quantity + " " + newMeasure + " " + currentIngredient.getIngredientName();
        DetailActivity.recentCake.edit().putString(mContext.getString(R.string.widget_ingredients), ingredientsForWidget).apply();
    }

    @Override
    public int getItemCount() {
        return ingredientList != null ? ingredientList.size() : 0;
    }

    //    public String quantityPolish (Double quantity){
//        String polishedQuantity = null;
//        if (quantity >1.0){
//            String s = quantity.toString();
//            String[] split = s.split(".");
//            polishedQuantity = split[0];
//        }
//        return polishedQuantity;
//    }
    private String measurePolish(String measure, Double quantity) {
        String polishedMeasure;

        switch (measure) {
            case "G":
                if (quantity > 1.0) {
                    polishedMeasure = "grams";
                } else {
                    polishedMeasure = "gram";
                }
                return polishedMeasure;
            case "UNIT":
                polishedMeasure = "";
                return polishedMeasure;
            case "TBLSP":
                if (quantity > 1.0) {
                    polishedMeasure = "tablespoons";
                } else {
                    polishedMeasure = "tablespoon";
                }
                return polishedMeasure;
            case "TSP":
                if (quantity > 1.0) {
                    polishedMeasure = "teaspoons";
                } else {
                    polishedMeasure = "tablespoon";
                }
                return polishedMeasure;
            case "CUP":
                if (quantity > 1.0) {
                    polishedMeasure = "cups";
                } else {
                    polishedMeasure = "cup";
                }
                return polishedMeasure;
            case "K":
                if (quantity > 1.0) {
                    polishedMeasure = "kilograms";
                } else {
                    polishedMeasure = "kilogramme";
                }
                return polishedMeasure;
            case "OZ":
                if (quantity > 1.0) {
                    polishedMeasure = "ounces";
                } else {
                    polishedMeasure = "ounce";
                }
                return polishedMeasure;
            default:
                return measure;
        }
    }
}