package com.example.dealershipinventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {
    EditText userName, userEmail, userPass, confirmPass;    //User data Edit Text fields
    Button syncAccount;         //
    TextView loginAct;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPass = findViewById(R.id.password);
        confirmPass = findViewById(R.id.passwordConfirm);

        syncAccount = findViewById(R.id.createAccount);
        loginAct = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar4);
        fAuth = FirebaseAuth.getInstance();


        getSupportActionBar().setTitle("Create New PostItNotes Account");

        //Enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loginAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });


        syncAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uUsername = userName.getText().toString();
                String uUserEmail = userEmail.getText().toString();
                String uUserPass = userPass.getText().toString();
                String uConfPass = confirmPass.getText().toString();

                if(uUserEmail.isEmpty() || uUsername.isEmpty() || uUserPass.isEmpty() || uConfPass.isEmpty())
                {
                    Toast.makeText(Register.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!uUserPass.equals(uConfPass))
                {
                    confirmPass.setError("Passwords Do Not Match.");
                }

                progressBar.setVisibility(View.VISIBLE);


                //Create a new credential for the user
                AuthCredential credential = EmailAuthProvider.getCredential(uUserEmail, uUserPass);

                //Merge Anonymous account with the new account
                fAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Register.this, "Notes are Synced.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        FirebaseUser usr = fAuth.getCurrentUser();

                        //Save the username of the user in the firebase auth user profile object
                        //by creating a user profile change request. Do this so it can be accessed
                        //from any other activity
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(uUsername)
                                .build();

                        usr.updateProfile(request);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        finish();



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed to Connect. Try Again.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }
        });




    }


    //Shows cross symbol on the top right corner
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Goes back to Main Activity when cross symbol clicked
        startActivity(new Intent(this, MainActivity.class));
        finish();

        return super.onOptionsItemSelected(item);
    }
}
