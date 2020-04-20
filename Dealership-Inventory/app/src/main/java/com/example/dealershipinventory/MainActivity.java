package com.example.dealershipinventory;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    RecyclerView carLists;         //List of Notes in Content Main

    FirebaseFirestore fStore;       //Cloud Document Database to store, sync, and query data
    FirestoreRecyclerAdapter<Car, CarViewHolder> carAdapter;     //Used to populate data in RecyclerView
    FirebaseUser user;              //User's profile information
    FirebaseAuth fAuth;             //Entry point of the Firebase Authentication SDK

    Spinner priceSpinner, conditionSpinner, yearSpinner, makeSpinner, colorSpinner, modelSpinner, mileageSpinner;
    Button searchButton;


    FloatingActionButton addCarFloat;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    Toolbar toolbar;


    View headerView;
    Query query;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        priceSpinner = findViewById(R.id.priceSearch);
        conditionSpinner = findViewById(R.id.conditionSearch);
        yearSpinner = findViewById(R.id.yearSearch);
        makeSpinner = findViewById(R.id.makeSearch);
        colorSpinner = findViewById(R.id.colorSearch);
        modelSpinner = findViewById(R.id.modelSearch);
        mileageSpinner = findViewById(R.id.mileageSearch);
        searchButton = findViewById(R.id.searchButton);

        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);


        fAuth.signOut();
        user = fAuth.getCurrentUser();

        query = fStore.collection("cars").orderBy("make", Query.Direction.DESCENDING);

        final FirestoreRecyclerOptions<Car> allCars = new FirestoreRecyclerOptions.Builder<Car>()
                .setQuery(query, Car.class)
                .build();


        addCarFloat = findViewById(R.id.addCarFloat);
        carLists = findViewById(R.id.carList);


        final Intent data = getIntent();

        carAdapter = new FirestoreRecyclerAdapter<Car, CarViewHolder>(allCars) {


            @Override
            protected void onBindViewHolder(@NonNull CarViewHolder carViewHolder, int i, @NonNull final Car car) {
                carViewHolder.make.setText(car.getMake());
                carViewHolder.model.setText(car.getModel());
                carViewHolder.price.setText("$" + car.getPrice().toString());

                carViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), CarDetails.class);
                        i.putExtra("make", car.getMake());
                        i.putExtra("model", car.getModel());
                        i.putExtra("price", car.getPrice());
                        i.putExtra("condition", car.getColor());
                        i.putExtra("year", car.getYear());
                        i.putExtra("mileage", car.getMileage());
                        i.putExtra("color", car.getColor());
                        v.getContext().startActivity(i);
                    }
                });

//                }
            }

            @NonNull
            @Override
            public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
                return new CarViewHolder(view);
            }
        };

        carLists.setLayoutManager(new LinearLayoutManager(this));
        carLists.setAdapter(carAdapter);



        carLists = findViewById(R.id.carList);


        if (fAuth.getCurrentUser() == null) {
            addCarFloat.setVisibility(View.GONE);
        } else {
            addCarFloat.setVisibility(View.VISIBLE);
            addCarFloat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId())
        {
            default:
                Toast.makeText(this, "Coming Soon.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    //Holds all the view and data for single com.example.postitnotes.note card on Main Activity
    public class CarViewHolder extends RecyclerView.ViewHolder{
        TextView make, model, price;
        View view;
        CardView mCardView;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            make = itemView.findViewById(R.id.carMake);
            model = itemView.findViewById(R.id.carModel);
            price = itemView.findViewById(R.id.price);
            mCardView = itemView.findViewById(R.id.carCard);
            view = itemView;
        }
    }   //End of NoteViewHolder class


    @Override
    protected void onStart() {
        super.onStart();
        carAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (carAdapter != null) {
            carAdapter.stopListening();
        }
    }
}
