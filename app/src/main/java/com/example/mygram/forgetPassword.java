package com.example.mygram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {
    EditText resetPassword;
    Button reset;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        reset=(Button)findViewById(R.id.resetBtn);
        resetPassword=(EditText)findViewById(R.id.emailReset);
        auth=FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=resetPassword.getText().toString().trim();
                if(userEmail.equals(""))
                {
                    resetPassword.setError("Please provide Email ");
                }
                else
                {
                    auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(forgetPassword.this,"Reset Link Sent to Email",Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(forgetPassword.this,Login.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(forgetPassword.this,"Error sending link check your Email!",Toast.LENGTH_SHORT).show();
                            }
                        }
                });
                }
            }
    });
}
}