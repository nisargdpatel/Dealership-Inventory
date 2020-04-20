package com.example.dealershipinventory;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DealerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    RecyclerView carLists;         //List of Notes in Content Main

    FirebaseFirestore fStore;       //Cloud Document Database to store, sync, and query data
    FirestoreRecyclerAdapter<Car, CarViewHolder> carAdapter;     //Used to populate data in RecyclerView
    FirebaseUser user;              //User's profile information
    FirebaseAuth fAuth;             //Entry point of the Firebase Authentication SDK

    Spinner priceSpinner, conditionSpinner, yearSpinner, makeSpinner, colorSpinner, modelSpinner, mileageSpinner;
    Button searchButton;

    int upperPriceBound;
    int lowerPriceBound;
    int upperMileageBound;
    int lowerMileagebound;
    int upperYearBound;
    int lowerYearBound;


//    List<String> allMake = new ArrayList<>();
//    List<String> hondaMake = new ArrayList<>();
//    List<String> toyotaMake = new ArrayList<>();
//    List<String> nissanMake = new ArrayList<>();
//
//    List<String> allPrice = new ArrayList<>();
//    List<String> allCondition = new ArrayList<>();
//    List<String> allYear = new ArrayList<>();
//    List<String> allMileage = new ArrayList<>();
//    List<String> allColor = new ArrayList<>();
//    List<String> anyModel = new ArrayList<>();
//
//    ArrayAdapter<String> adapterMake;
//    ArrayAdapter<String> adapterHonda;
//    ArrayAdapter<String> adapterToyota;
//    ArrayAdapter<String> adapterNissan;
//    ArrayAdapter<String> adapterPrice;
//    ArrayAdapter<String> adapterCondition;
//    ArrayAdapter<String> adapterYear;
//    ArrayAdapter<String> adapterMileage;
//    ArrayAdapter<String> adapterColor;
//    ArrayAdapter<String> adapterAnyModel;


//    Adapter adapter;

    FloatingActionButton addCarFloat;




    View headerView;
    Query query;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
