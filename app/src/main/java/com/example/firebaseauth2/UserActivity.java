package com.example.firebaseauth2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Intent intent;
    TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mAuth = FirebaseAuth.getInstance();
        intent = getIntent();
        messageView = findViewById(R.id.userTextViewMessage);
        //messageView.setText(intent.getStringExtra("email"));
        messageView.setText(mAuth.getCurrentUser().getEmail());
    }

    public void Logout(View view) {
        mAuth.signOut();
        finish();
    }
}