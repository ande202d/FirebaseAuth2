package com.example.firebaseauth2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "lukind";
    private FirebaseAuth mAuth;
    EditText emailView;
    EditText passView;

    TextView messageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        messageView = findViewById(R.id.mainMessageView);
        emailView = findViewById(R.id.mainEditTextEmail);
        passView = findViewById(R.id.mainEditTextPassword);
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            messageView.setText("ingen er logget ind");
        }
        else {
            messageView.setText(currentUser.getEmail());
        }
    }

    public void Login(View view) {
        String email = emailView.getText().toString();
        String pass = passView.getText().toString();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(getBaseContext(), "Successfully logged in",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            messageView.setText("Signed in as: " + user.getEmail());
                            Intent intent = new Intent(getBaseContext(), UserActivity.class);
                            intent.putExtra("email", user.getEmail());
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            messageView.setText("Ugyldigt login");
                        }
                    }
                });
    }

    public void Registrer(View view) {
        String email = emailView.getText().toString();
        String pass = passView.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            messageView.setText("Signed in as: " + user.getEmail());
                            Intent intent = new Intent(getBaseContext(), UserActivity.class);
                            intent.putExtra("email", user.getEmail());
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            messageView.setText("failed");
                        }
                    }
                });
    }
}