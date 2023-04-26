package com.example.database.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.database.HomePage;
import com.example.database.R;
import com.example.database.adapters.MarketAdapter;
import com.example.database.adapters.RecyclerAdapter;
import com.example.database.additem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MarketplaceFragment extends Fragment {

    RecyclerView recyclerView;
    MarketAdapter adapter;
    String temp;
    String Title[]=new String[100];
    String Image[]= new String[100];
    String Cost[]= new String[100];
    int count;
    String c;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_marketplace, container, false);
        recyclerView = view.findViewById(R.id.recyclerViews);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("market");
        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), additem.class);
                startActivity(i);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                c= String.valueOf(snapshot.child("count").getValue());
                count=Integer.parseInt(c);
                for (int i = 0; i < count; i++) {
                    temp = String.valueOf(i);
                    Title[i] = String.valueOf(snapshot.child("Title").child(temp).getValue());
                }
                for (int i=0;i< count;i++){
                    temp = String.valueOf(i);
                    Cost[i]= String.valueOf(snapshot.child("cost").child(temp).getValue());
                }
                for (int i=0;i< count;i++)
                {
                    temp = String.valueOf(i);
                    Image[i]= String.valueOf(snapshot.child("image").child(temp).getValue());
                }
                adapter = new MarketAdapter(getActivity(),Title,Cost,count,Image);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return  view;
    }

}