package com.example.database;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.database.adapters.RecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment1 extends Fragment {
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    Button floating;
    String temp, temp2, temp3, temp4, temp5;

    String[] Titles = new String[100];
    String[] Description = new String[100];
    String[] Image = new String[100];
    String[] Author = new String[100];
    int count;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        floating = view.findViewById(R.id.btn);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("message");
        DatabaseReference myRef2 = database.getReference("count");
        DatabaseReference myRef3 = database.getReference("description");
        DatabaseReference myRef4 = database.getReference("url");
        DatabaseReference myRef5 = database.getReference("author");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temp = String.valueOf(snapshot.getValue());
                count = Integer.parseInt(temp);
                myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (int i = 0; i < count; i++) {
                            temp2 = String.valueOf(i);
                            Titles[i] = String.valueOf(snapshot.child(temp2).getValue());
                        }
                        myRef5.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(int i=0;i< count;i++)
                                {
                                    temp5 = String.valueOf(i);
                                    Author[i] = String.valueOf(snapshot.child(temp5).getValue());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        myRef4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (int i = 0; i < count; i++) {
                                    temp4 = String.valueOf(i);
                                    Image[i] = String.valueOf(snapshot.child(temp4).getValue());
                                }
                                newView(Titles, Image);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                myRef3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (int i = 0; i < count; i++) {
                            temp3 = String.valueOf(i);
                            Description[i] = String.valueOf(snapshot.child(temp3).getValue());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAct();
            }
        });



        return view;
    }

    private void openAct() {
        Intent intent = new Intent(getActivity(), new_post.class);
        startActivity(intent);
    }

    private void newView(String[] titles, String[] image) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerAdapter(getActivity(), Titles, count, Image, Description,Author);
        recyclerView.setAdapter(adapter);
    }

}