package com.example.bakingcakes.Adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.bakingcakes.Activities.DetailActivity;
import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.ViewHolder> {

    private Context mContext;
    private final List<Cake> cakeList;
    private Bitmap bitmap; // cake image NOT from the given JSON

    public CakeAdapter(Context context,
                       List<Cake> cakes) {
        this.cakeList = cakes;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.cake_item_list_content, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView posterImage;
        final TextView cakeName;
        final LinearLayout linearLayout;


        ViewHolder(View view) {
            super(view);
            posterImage = view.findViewById(R.id.image);
            cakeName = view.findViewById(R.id.cake_name);
            linearLayout = view.findViewById(R.id.linear_layout);
        }

        void bind(final Cake currentCake) {

            bitmap = currentCake.getCakeImage();

            posterImage.setImageBitmap(bitmap);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.CURRENT_CAKE, currentCake);

                    //passing Bitmap
                    bitmap = currentCake.getCakeImage();
                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byte[] byteArray = bStream.toByteArray();
                    intent.putExtra(DetailActivity.IMAGE, byteArray);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Cake currentCake = cakeList.get(position);
        holder.cakeName.setText(currentCake.getCakeName());
        holder.bind(currentCake);
    }

    @Override
    public int getItemCount() {
        return cakeList != null ? cakeList.size() : 0;
    }
}


