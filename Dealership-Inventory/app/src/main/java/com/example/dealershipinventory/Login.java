package com.example.dealershipinventory;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText userEmail, userPassword;       //Email and password fields
    Button loginButton;                     //login button
    TextView forgetLink, registerLink;      //forgot password and create new account links

    FirebaseAuth fAuth;                     //Entry point of the Firebase Authentication SDK
    FirebaseFirestore fStore;               //Cloud Document Database to store, sync, and query data
    FirebaseUser user;                      //User's profile information
    ProgressBar loading;                    //Loading icon

    String tempUserEmail, tempUserPassword; //Temporary email and password strings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Bind the views to variables
        userEmail = findViewById(R.id.email);
        userPassword = findViewById(R.id.lPassword);
        loginButton = findViewById(R.id.loginBtn);
        forgetLink = findViewById(R.id.forgotPasword);
        registerLink = findViewById(R.id.createAccount);
        loading = findViewById(R.id.progressBar3);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();


        //Set up Action Bar
        getSupportActionBar().setTitle("Login to Dealership"); //Set title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Set back button


//        showWarning();  //Warns user of possible loss of data before logging in

//        //OnClickListener for Create New Account link
//        registerLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //start Register Activity
//                startActivity(new Intent(getApplicationContext(), Register.class));
//                finish();
//            }
//        }); //End of OnClickListener for Create New Account link




        //OnClickListener for password reset link
        forgetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Initialize Email holder Edit Text
                final EditText resetMail = new EditText(v.getContext());

                //set up Alert Dialog
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password? ");
                passwordResetDialog.setMessage("Enter your Email to receive Reset link");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();   //Store user entered email

                        //Email password reset link and add OnSuccessListener / OnFailureListener
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error! Reset Link it Not Sent. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });//End of send password reset link
                    }   //End of onClick
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Login.this, "Reset Activity Canceled", Toast.LENGTH_SHORT).show();
                    }
                }); //End of setting up positive / negative buttons

                passwordResetDialog.create().show();
            }
        }); //End of OnClickListener for password reset link



        //OnClickListener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Initialize variables with user entered fields
                tempUserEmail = userEmail.getText().toString();
                tempUserPassword = userPassword.getText().toString();

                //If either of the fields are empty
                if(tempUserEmail.isEmpty() || tempUserPassword.isEmpty())
                {
                    //Toast an error and get out of this method
                    Toast.makeText(Login.this, "Fields Are Required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                loading.setVisibility(View.VISIBLE);    //Start upt he loading symbol

                //Sign in with Email and Password and add OnSuccessListener / OnFailureListener
                fAuth.signInWithEmailAndPassword(tempUserEmail, tempUserPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));     //Start Main Activity

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Login Failed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);   //Stop loading symbol
                    }
                }); //End of signing in with Email and Password

            }
        }); //End of OnClickListener for login button




    }   //END OF onCreate method


//    //Warns user of possible loss of data before logging in
//    private void showWarning() {
//        AlertDialog.Builder warning = new AlertDialog.Builder(this)
//                .setTitle("Are you sure?")
//                .setMessage("Logging in to Existing Account will delete all the temporary notes. Create New Account to Save them")
//                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startActivity(new Intent(getApplicationContext(), Register.class));
//                        finish();
//                    }
//                }).setNegativeButton("Login", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //do nothing
//                    }
//                }); //End of Alert Dialog
//
//        warning.show();
//    }


    //Shows cross symbol on top right corner
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Go back to Main Activity when cross symbol clicked
//        startActivity(new Intent(this, HomeActivity.class));
        finish();

        return super.onOptionsItemSelected(item);
    }
}
