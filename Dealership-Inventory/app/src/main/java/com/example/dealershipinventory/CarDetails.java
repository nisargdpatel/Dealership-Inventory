package com.example.dealershipinventory;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class CarDetails extends AppCompatActivity {

    Intent data;
    FloatingActionButton editButton;
    FirebaseAuth fAuth;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editButton = findViewById(R.id.editCarFloat);
        fAuth = FirebaseAuth.getInstance();
        data = getIntent();


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
        } else {
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Intent i = new Intent(view.getContext(), EditCar.class);
//                i.putExtra("title", data.getStringExtra("title"));
//                i.putExtra("content", data.getStringExtra("content"));
//                i.putExtra("noteId", data.getStringExtra("noteId"));
//                startActivity(i);
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
