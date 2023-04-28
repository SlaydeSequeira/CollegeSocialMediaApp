package com.example.database;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.database.adapters.RecyclerAdapter1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NoticeFragment extends Fragment {
    RecyclerAdapter1 adapter;
    RecyclerView recyclerView;
    String count,temp;
    String Feed[]= new String[100];
    String User[]= new String[100];
    int c;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notice, container, false);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef= firebaseDatabase.getReference("Notice");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count=String.valueOf(snapshot.child("nc").getValue());
                c=Integer.parseInt(count);
                for (int i = 0; i < c  ; i++) {
                    temp = String.valueOf(i);
                    Feed[i] = String.valueOf(snapshot.child(temp).getValue());
                    User[i] = "The Principal";
                    newView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
    private void newView() {
        RecyclerView recyclerView = getView().findViewById(R.id.test);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerAdapter1(getActivity(), Feed, User, c);
        recyclerView.setAdapter(adapter);
    }

}