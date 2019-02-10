package com.example.bakingcakes.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bakingcakes.Activities.DetailActivity;
import com.example.bakingcakes.Activities.StepsActivity;

import com.example.bakingcakes.Models.Step;
import com.example.bakingcakes.R;

import java.util.List;

import static com.example.bakingcakes.Activities.StepsActivity.CURRENT_STEP_NUMBER;

public class StepsAdapter extends Adapter<StepsAdapter.ViewHolder> {

    private Context mContext;
    private final List<Step> stepList;

    public StepsAdapter(Context context,
                        List<Step> steps) {
        this.stepList = steps;
        this.mContext = context;//TODO add boolean mTwoPane;
    }

    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.steps_item_list_content, parent, false);
        return new StepsAdapter.ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView shortDescription;
        final RelativeLayout listItem;

        ViewHolder(View view) {
            super(view);
            shortDescription = view.findViewById(R.id.tv_step_number_and_short_description);
            listItem = view.findViewById(R.id.step_list_item);
        }

        void bind(final Step currentStep) {
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, StepsActivity.class);
                    intent.putExtra(StepsActivity.CURRENT_STEP, currentStep);
                    intent.putExtra(CURRENT_STEP_NUMBER, currentStep.getStepId());
                    intent.putExtra(DetailActivity.CURRENT_CAKE, DetailActivity.currentCake);

                    context.startActivity(intent);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final StepsAdapter.ViewHolder holder, int position) {
        Step currentStep = stepList.get(position);
        String description = currentStep.getStepShortDescription();
        if (position == 0) {
            holder.shortDescription.setText(description);
        } else {
            holder.shortDescription.setText("Step " + currentStep.getStepId() + ": " + description);
        }
        holder.bind(currentStep);
    }

    @Override
    public int getItemCount() {
        return stepList != null ? stepList.size() : 0;
    }
}