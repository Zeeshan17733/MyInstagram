package com.example.mygram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText lemail,lPassword;
    Button login;
    TextView registerLink,forget;
    ProgressBar progress;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lemail=(EditText)findViewById(R.id.email1);
        lPassword=(EditText)findViewById(R.id.password1);
        login=(Button)findViewById(R.id.loginBtn);
        auth=FirebaseAuth.getInstance();
        registerLink=(TextView)findViewById(R.id.register);
        forget=(TextView)findViewById(R.id.forget);
        progress=(ProgressBar)findViewById(R.id.progressBar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=lemail.getText().toString().trim();
                String password=lPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    lemail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    lPassword.setError("Password is required");
                    return;
                }
                if(password.length()<6){
                    lPassword.setError("Password should be more than or equal to 6 characters");
                    return;
                }
                progress.setVisibility(View.VISIBLE);
                //authenticatation

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"logged in successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Login.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),forgetPassword.class));
            }
        });
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}