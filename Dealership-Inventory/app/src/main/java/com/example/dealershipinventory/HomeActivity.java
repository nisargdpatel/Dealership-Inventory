package com.example.dealershipinventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    Button dealerButton;
    Button customerButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        dealerButton = findViewById(R.id.dealerButton);
        customerButton = findViewById(R.id.customerButton);

        dealerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() == null)
                {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

            }
        });

        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }


}
