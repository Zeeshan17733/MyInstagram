package com.example.mygram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    EditText name,email,phone;
    Button save,cancel;
    FirebaseFirestore store;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name=(EditText)findViewById(R.id.editName);
        email=(EditText)findViewById(R.id.editEmail);
        phone=(EditText)findViewById(R.id.editPhone);
        save=(Button)findViewById(R.id.save);
        cancel=(Button)findViewById(R.id.cancel);
        auth=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();
        Intent d=getIntent();
        name.setText(d.getStringExtra("name"));
        email.setText(d.getStringExtra("email"));
        phone.setText(d.getStringExtra("phone"));
        user=auth.getCurrentUser();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()||email.getText().toString().isEmpty()||phone.getText().toString().isEmpty()){
                    Toast.makeText(EditProfile.this,"One or many fields are empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                final String mail=email.getText().toString();
                user.updateEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference doc=store.collection("users").document(user.getUid());
                        Map<String,Object> edited=new HashMap<>();
                        edited.put("Email",mail);
                        edited.put("Full Name",name.getText().toString());
                        edited.put("Phone Number",phone.getText().toString());
                        doc.update(edited);
                        Toast.makeText(EditProfile.this,"Email is changed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}