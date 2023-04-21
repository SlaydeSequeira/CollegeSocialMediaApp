package com.example.database.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database.R;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ViewHolder> {
    private final Context context;
    String[] data;
    int c;
    public ReAdapter(Context context, String[] data, int c)
    {
        this.context = context;
        this.data = data;
        this.c =c;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView100;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView100 = itemView.findViewById(R.id.hello);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.text,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView100.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return c+1;
    }

}
