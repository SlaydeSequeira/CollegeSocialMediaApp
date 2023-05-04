package com.example.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.database.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class additem extends AppCompatActivity {

    String temp,temp1,temp2,temp10;
    EditText editText,editText2,editText3;
    Button button,button1;
    String a,b,c;
    int flag=0,temporary;
    ImageView imageView;
    String username;

    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    Button btn,but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        Button btn = findViewById(R.id.buttonPanel);
        Button btn1= findViewById(R.id.submit_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                EditText e = findViewById(R.id.edit);
                EditText f = findViewById(R.id.edit2);
                a=e.getText().toString();
                b=f.getText().toString();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference("market");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        while (flag==1) {
                            temp10 = String.valueOf(snapshot.child("count").getValue());
                            temporary = Integer.parseInt(temp10);
                            uploadImage(temporary);
                            HashMap<String, Object> hashMap1 = new HashMap<>();
                            hashMap1.put(temp10, a);
                            myRef.child("Title").updateChildren(hashMap1);
                            HashMap<String, Object> hashMap2 = new HashMap<>();
                            hashMap2.put(temp10, b);
                            myRef.child("cost").updateChildren(hashMap2);
                            flag=0;
                            HashMap<String, Object> hashMap3 = new HashMap<>();
                            temporary=temporary+1;
                            temp10=String.valueOf(temporary);
                            hashMap3.put("count", temp10);
                            myRef.updateChildren(hashMap3);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    private void uploadImage(int count) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName+".jpg");
        try {
            storageReference.putFile(imageUri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            try {
                                if (task.isSuccessful()) {
                                    imageView = findViewById(R.id.image_view);
                                    imageView.setImageURI(imageUri);
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String a= uri.toString();
                                            Toast.makeText(additem.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference myRef4 = database.getReference("market").child("image");

                                            c=String.valueOf(count);
                                            HashMap<String, Object> hashMap4 = new HashMap<>();
                                            hashMap4.put(c , a);
                                            myRef4.updateChildren(hashMap4);

                                            // Clear the imageUri variable
                                            imageUri = null;
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(additem.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(additem.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                // handle exception
                            } finally {
                                // Dismiss the progress dialog
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            // handle exception
        }
    }



    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){
            imageView = findViewById(R.id.image_view);
            imageUri = data.getData();
            imageView.setImageURI(imageUri);


        }
    }
}