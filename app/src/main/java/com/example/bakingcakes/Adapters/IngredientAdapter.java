package com.example.bakingcakes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bakingcakes.DetailActivity;
import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.Models.Ingredient;
import com.example.bakingcakes.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    Context mContext;
    public List<Ingredient> ingredientList;
    Ingredient currentIngredient;

    public IngredientAdapter(Context context,
                             List<Ingredient> ingredients) {
        this.ingredientList = ingredients;
        this.mContext = context;//TODO add  boolean mTwoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.ingredient_item_list_content, parent, false);
        return new ViewHolder(view);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView quantityTv;
        public TextView measureTv;
        public TextView ingredientTv;
        public final View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            quantityTv = (TextView) view.findViewById(R.id.quantity_tv);
            measureTv = (TextView) view.findViewById(R.id.measure_tv);
            ingredientTv = (TextView) view.findViewById(R.id.ingredient_tv);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        currentIngredient = ingredientList.get(position);
        holder.quantityTv.setText(currentIngredient.getIngredientQuantity());
        holder.measureTv.setText(currentIngredient.getIngredientMeasure());
        holder.ingredientTv.setText(currentIngredient.getIngredientName());
    }

    @Override
    public int getItemCount() {
        return ingredientList != null ? ingredientList.size() : 0;
    }
}


