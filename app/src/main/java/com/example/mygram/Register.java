package com.example.mygram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText rName,rEmail,rPassword,rPhone;
    Button register;
    TextView rLogin;
    FirebaseAuth auth;
    FirebaseFirestore store;
    ProgressBar progress;
    String userId;
    String password,fullName,email,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rName=(EditText)findViewById(R.id.name);
        rEmail=(EditText)findViewById(R.id.email);
        rPassword=(EditText)findViewById(R.id.password);
        rPhone=(EditText)findViewById(R.id.phone);
        register=(Button)findViewById(R.id.registerBtn);
        rLogin=(TextView)findViewById(R.id.login);
        progress=(ProgressBar)findViewById(R.id.progressBar1);
        auth=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        // register user
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password=rPassword.getText().toString().trim();
                email=rEmail.getText().toString().trim();
                fullName=rName.getText().toString();
                phone=rPhone.getText().toString();
                if(TextUtils.isEmpty(email)){
                    rEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    rPassword.setError("Password is required");
                    return;
                }
                if(password.length()<6){
                    rPassword.setError("Password should be more than or equal to 6 characters");
                    return;
                }

                progress.setVisibility(View.VISIBLE);
                //saving data in fire base
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"user Created",Toast.LENGTH_SHORT).show();
                            userId=auth.getCurrentUser().getUid();
                            DocumentReference documentReference=store.collection("users").document(userId);
                            Map<String,Object> user=new HashMap<>();
                            user.put("Full Name",fullName);
                            user.put("Email",email);
                            user.put("Phone Number",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                   
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Register.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });




        rLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}