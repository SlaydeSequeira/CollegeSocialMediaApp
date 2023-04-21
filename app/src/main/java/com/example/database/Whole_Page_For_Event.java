package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.database.Fragments.RegisterFragment;
import com.example.database.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class Whole_Page_For_Event extends AppCompatActivity {
    String a,b,c,d;
    TextView E,F,G;
    String username,counts;
    int count;

    int commentcount=0;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_page_for_event);
        a=getIntent().getStringExtra("A");
        b=getIntent().getStringExtra("B");
        c=getIntent().getStringExtra("C");
        d=getIntent().getStringExtra("D");
        count= getIntent().getIntExtra("E",0);
        counts = String.valueOf(count);
        Button button = findViewById(R.id.button);
        ImageView imageView = findViewById(R.id.imageinrecycler);
        E = findViewById(R.id.author);
        F = findViewById(R.id.textinrecycler);
        G = findViewById(R.id.description);
        E.setText(d);
        F.setText(a);
        G.setText(b);

        Glide.with(this)
                .load(c)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);

        FirebaseUser fuser;
        DatabaseReference reference;
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("MyUsers")
                .child(fuser.getUid());
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("comment");
        DatabaseReference myRef2= firebaseDatabase.getReference("comment").child(counts);
        DatabaseReference myRef3 = firebaseDatabase.getReference("comment").child(counts).child("cc");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                username=(user.getUsername()).toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Whole_Page_For_Event.this, "You are registered", Toast.LENGTH_SHORT).show();
                HashMap <String,Object> hashmap = new HashMap<>();
                hashmap.put(username,username);
                myRef2.updateChildren(hashmap);


                RegisterFragment registerFragment= new RegisterFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
                Bundle data = new Bundle();
                data.putString("a",counts);
                registerFragment.setArguments(data);
                fragmentTransaction.replace(R.id.register,registerFragment);
                fragmentTransaction.commit();

            }
        });
    }

}