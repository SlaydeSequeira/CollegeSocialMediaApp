package com.example.database.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.database.Buy;
import com.example.database.R;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {
    private final Context context;
    private final String[] title;
    private final int count;
    private final String[] cost;
    private final String[] image;
    private final String[] image2;
    private final String[] image1;
    private final String[] cost2;
    private final String[] cost1;
    private final String[] title2;
    private final String[] title1;

    public MarketAdapter(Context context, String[] title, String[] cost, int count, String[] image) {
        this.image = image;
        this.cost = cost;
        this.context = context;
        int numItems = (count + 1) / 2;
        title1 = new String[numItems];
        cost1 = new String[numItems];
        image1 = new String[numItems];
        title2 = new String[numItems];
        cost2 = new String[numItems];
        image2 = new String[numItems];
        for(int i = 0; i < count; i++) {
            if(i % 2 == 0) {
                title1[i / 2] = title[i];
                cost1[i / 2] = cost[i];
                image1[i / 2] = image[i];
            }
            else {
                title2[i / 2] = title[i];
                cost2[i / 2] = cost[i];
                image2[i / 2] = image[i];
            }
        }
        this.title = title;
        this.count = count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sell_stuff, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView1.setText(title1[position]);
        holder.textView2.setText(title2[position]);
        Glide.with(context)
                .load(image1[position])
                .into(holder.imageView1);
        Glide.with(context)
                .load(image2[position])
                .into(holder.imageView2);

        // Set the cost text for left item
        holder.textView3.setText("Rs " + cost1[position]);

        // Set the cost text for right item, or make it blank if it is null
        if (cost2[position] != null) {
            holder.textView4.setText("Rs " + cost2[position]);
        } else {
            holder.textView4.setText("");
        }
        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(), Buy.class);
                intent.putExtra("a",title1[position]);
                intent.putExtra("b",cost1[position]);
                intent.putExtra("c",image1[position]);
                String pos= String.valueOf(2*position);
                intent.putExtra("d",pos);
                view.getContext().startActivity(intent);
            }
        });
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(),Buy.class);
                intent.putExtra("a",title2[position]);
                intent.putExtra("b",cost2[position]);
                intent.putExtra("c",image2[position]);
                String pos= String.valueOf(2*position+1);
                intent.putExtra("d",pos);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (count + 1) / 2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        ImageView imageView1;
        ImageView imageView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.text1);
            textView2 = itemView.findViewById(R.id.text2);
            textView3 = itemView.findViewById(R.id.cost1);
            textView4 = itemView.findViewById(R.id.cost2);
            imageView1 = itemView.findViewById(R.id.image1);
            imageView2 = itemView.findViewById(R.id.image2);
        }
    }
}
