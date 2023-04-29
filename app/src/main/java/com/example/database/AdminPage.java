package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminPage extends AppCompatActivity {

    EditText editText;
    Button button;
    String a;
    String temp;
    int count;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        editText=findViewById(R.id.edit);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                a=editText.getText().toString();
                FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference("Notice");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(flag==1) {
                            temp = String.valueOf(snapshot.child("nc").getValue());
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put(temp, a);
                            myRef.updateChildren(hashMap);
                            flag = 0;
                            count = Integer.parseInt(temp);
                            count++;
                            temp = String.valueOf(count);
                            myRef.child("nc").setValue(temp);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}