package com.example.dealershipinventory;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CarDetails extends AppCompatActivity {

    Intent data;
    FloatingActionButton editButton;
    FloatingActionButton deleteButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    Toolbar toolbar;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

//        drawerLayout = findViewById(R.id.drawer);
//        nav_view = findViewById(R.id.nav_view);
//        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


//        drawerLayout.addDrawerListener(toggle);
//        toggle.setDrawerIndicatorEnabled(true);
//        toggle.syncState();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editButton = findViewById(R.id.editCarFloat);
        fAuth = FirebaseAuth.getInstance();
        data = getIntent();
        Toast.makeText(this, "DocID: " + data.getStringExtra("carId"), Toast.LENGTH_SHORT).show();
        fStore = FirebaseFirestore.getInstance();

        deleteButton = findViewById(R.id.deleteCarFloat);

        TextView price = findViewById(R.id.price);
        TextView condition = findViewById(R.id.condition);
        TextView year = findViewById(R.id.year);
        TextView color = findViewById(R.id.color);
        TextView mileage = findViewById(R.id.mileage);
        TextView make = findViewById(R.id.make);
        TextView model = findViewById(R.id.model);
//        content.setMovementMethod(new ScrollingMovementMethod());

//        content.setText(data.getStringExtra("content"));
        price.setText(data.getStringExtra("price"));
        condition.setText(data.getStringExtra("condition"));
        year.setText(data.getStringExtra("year"));
        color.setText(data.getStringExtra("color"));
        mileage.setText(data.getStringExtra("mileage"));
        make.setText(data.getStringExtra("make"));
        model.setText(data.getStringExtra("model"));
//        title.setText(data.getStringExtra("title"));
//        content.setBackgroundColor(getResources().getColor(data.getIntExtra("code", 0), null));


        if (fAuth.getCurrentUser() == null)
        {
            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        } else {
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent i = new Intent(view.getContext(), edit_car.class);
                i.putExtra("make", data.getStringExtra("make"));
                i.putExtra("model", data.getStringExtra("model"));
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("year", data.getStringExtra("year"));
                i.putExtra("mileage", data.getStringExtra("mileage"));
                i.putExtra("condition", data.getStringExtra("condition"));
                i.putExtra("color", data.getStringExtra("color"));
                i.putExtra("carId", data.getStringExtra("carId"));
                startActivity(i);
                }
            });

            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentReference docRef = fStore.collection("cars").document(data.getStringExtra("carId"));
                    
                    docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CarDetails.this, "Car deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CarDetails.this, "Error in Deleting Car", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.close_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.close)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
