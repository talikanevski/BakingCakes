package com.example.bakingcakes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bakingcakes.Activities.DetailActivity;
import com.example.bakingcakes.Activities.StepsActivity;
import com.example.bakingcakes.Fragments.StepFragment;
import com.example.bakingcakes.Models.Ingredient;
import com.example.bakingcakes.Models.Step;
import com.example.bakingcakes.R;

import java.util.List;

import static com.example.bakingcakes.Activities.StepsActivity.CURRENT_STEP_NUMBER;

public class StepsAdapter extends Adapter<StepsAdapter.ViewHolder> {

    Context mContext;
    public List<Step> stepList;
    Step currentStep;

    public StepsAdapter(Context context,
                        List<Step> steps) {
        this.stepList = steps;
        this.mContext = context;//TODO add boolean mTwoPane;
    }

    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.steps_item_list_content, parent, false);
        return new StepsAdapter.ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView shortDescription;
        public RelativeLayout listItem;

        public final View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            shortDescription = (TextView) view.findViewById(R.id.tv_step_number_and_short_description);
            listItem = (RelativeLayout) view.findViewById(R.id.step_list_item);
        }

        void bind(final Step currentStep) {
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, StepsActivity.class);
                    intent.putExtra(StepsActivity.CURRENT_STEP, (Parcelable) currentStep);
                    intent.putExtra(CURRENT_STEP_NUMBER, currentStep.getStepId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(final StepsAdapter.ViewHolder holder, int position) {
        currentStep = stepList.get(position);
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