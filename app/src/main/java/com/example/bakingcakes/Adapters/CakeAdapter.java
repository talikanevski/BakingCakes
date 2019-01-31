package com.example.bakingcakes.Adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bakingcakes.Activities.DetailActivity;
import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.ViewHolder> {

    Context mContext;
    public List<Cake> cakeList;
    Cake currentCake;
    private final boolean mTwoPane;
    Bitmap bitmap; // cake image NOT from the given JSON

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
                .inflate(R.layout.cake_item_list_content, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterImage;
        public TextView cakeName;
        public final View mView;


        ViewHolder(View view) {
            super(view);
            mView = view;
            posterImage = (ImageView) view.findViewById(R.id.image);
            cakeName = (TextView) view.findViewById(R.id.cake_name);
        }

        void bind(final Cake currentCake) {

            bitmap = currentCake.getCakeImage();
            assert currentCake != null;

            posterImage.setImageBitmap(bitmap);

            posterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.CURRENT_CAKE, (Parcelable) currentCake);
                    //passing Bitmap
                    bitmap = currentCake.getCakeImage();

                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byte[] byteArray = bStream.toByteArray();
                    intent.putExtra("image", byteArray);

                    context.startActivity(intent);
                }
            });
            cakeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.CURRENT_CAKE, (Parcelable) currentCake);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        currentCake = cakeList.get(position);
        holder.cakeName.setText(currentCake.getCakeName());
        holder.bind(currentCake);
    }

    @Override
    public int getItemCount() {
        return cakeList != null ? cakeList.size() : 0;
    }
}


