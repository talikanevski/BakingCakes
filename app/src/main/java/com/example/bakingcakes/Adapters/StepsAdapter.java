package com.example.bakingcakes.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.bakingcakes.DetailActivity;
import com.example.bakingcakes.Models.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter {
    public StepsAdapter(DetailActivity detailActivity, ArrayList<Step> steps) {
    }

    public StepsAdapter(DetailActivity detailActivity, List<Step> steps) {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
