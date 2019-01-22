package com.example.bakingcakes.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bakingcakes.MainActivity;
import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.R;

import java.util.List;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.ViewHolder> {

    Context mContext;
    public List<Cake> cakeList;
    Cake currentCake;
    private final boolean mTwoPane;
    String currentCakePosterUrl; // cake image not from the given JSON
    String currentCakeName;

    //        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putString(DetailFragment.ARG_ITEM_ID, item.id);
//                    DetailFragment fragment = new DetailFragment();
//                    fragment.setArguments(arguments);
//                    mParentActivity.getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.item_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra(DetailFragment.ARG_ITEM_ID, item.id);
//
//                    context.startActivity(intent);
//                }
//            }
//        };

    public CakeAdapter(Context context,
                       List<Cake> cakes,
                       boolean twoPane) {
        this.cakeList = cakes;
        this.mContext = context;
        this.mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterImage;
        public TextView cakeName;
        public final View mView;


        ViewHolder(View view) {
            super(view);
            mView = view;
            posterImage = (ImageView) view.findViewById(R.id.cake_image);
            cakeName = (TextView) view.findViewById(R.id.cake_name);
        }

        void bind(final Cake currentCake) {
//            currentCakeName = currentCake.getCakeName();
//            cakeName.setText(currentCakeName);

//            currentCakePosterUrl = currentCake.getCakeImage();
            assert currentCake != null;
            //TODO
//                Picasso.with(posterImage.getContext())
//                        .load(currentCakePosterUrl)
//                        .into(posterImage);
//                posterImage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Context context = v.getContext();
//                        Intent intent = new Intent(context, DetailActivity.class);
//                        intent.putExtra(DetailActivity.CURRENT_MOVIE, currentMovie);
//                        context.startActivity(intent);
//                    }
//                });
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        currentCake = cakeList.get(position);
        holder.cakeName.setText(currentCake.getCakeName());
//        holder.bind(currentCake);
    }

    @Override
    public int getItemCount() {
        return cakeList != null ? cakeList.size() : 0;
    }
}


