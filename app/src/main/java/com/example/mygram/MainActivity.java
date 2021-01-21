package com.example.mygram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {
    TextView name,email,number;
    ImageView profileph;
    Button newDiary,viewAllDiaries,editProfile;
    FirebaseAuth auth;
    FirebaseFirestore store;
    StorageReference reference;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(TextView)findViewById(R.id.displayName);
        email=(TextView)findViewById(R.id.displayEmail);
        number=(TextView)findViewById(R.id.displayNumber);
        profileph=(ImageView)findViewById(R.id.imageView);
        newDiary=(Button)findViewById(R.id.dNewPost);
        viewAllDiaries=(Button)findViewById(R.id.dViewAll);
        editProfile=(Button)findViewById(R.id.dEditProfile);
        auth=FirebaseAuth.getInstance();
        reference= FirebaseStorage.getInstance().getReference();
        store=FirebaseFirestore.getInstance();
        userId=auth.getCurrentUser().getUid();


        DocumentReference documentReference=store.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
            name.setText(value.getString("Full Name"));
            email.setText(value.getString("Email"));
            number.setText(value.getString("Phone Number"));
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),EditProfile.class);
                i.putExtra("name",name.getText().toString());
                i.putExtra("email",email.getText().toString());
                i.putExtra("phone",number.getText().toString());
                startActivity(i);
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}