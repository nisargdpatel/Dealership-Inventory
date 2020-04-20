package com.example.dealershipinventory;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DealerActivity extends AppCompatActivity {

    RecyclerView carLists;         //List of Notes in Content Main

    FirebaseFirestore fStore;       //Cloud Document Database to store, sync, and query data
    FirestoreRecyclerAdapter<Car, CarViewHolder> carAdapter;     //Used to populate data in RecyclerView
    FirebaseUser user;              //User's profile information
    FirebaseAuth fAuth;             //Entry point of the Firebase Authentication SDK




//    List<String> make = new ArrayList<>();
//    List<String> model = new ArrayList<>();
//    List<String> price = new ArrayList<>();
//    List<String> condition = new ArrayList<>();
//    List<String> year = new ArrayList<>();
//    List<String> mileage = new ArrayList<>();
//    List<String> color = new ArrayList<>();

//    Adapter adapter;

    View headerView;
    Query query;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        fAuth.signInWithEmailAndPassword("nisargp@umich.edu", "123456");

        query = fStore.collection("cars").orderBy("make", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Car> allCars = new FirestoreRecyclerOptions.Builder<Car>()
                .setQuery(query, Car.class)
                .build();



        carLists = findViewById(R.id.carList);

//        make.add("Honda");
//        make.add("Toyota");
//        make.add("Audi");
//
//        model.add("CRV");
//        model.add("Sienna");
//        model.add("Q5");
//
//        price.add("$2000");
//        price.add("$7000");
//        price.add("$7000");
//
//        condition.add("Used");
//        condition.add("Used");
//        condition.add("Used");
//
//        year.add("2008");
//        year.add("2007");
//        year.add("2011");
//
//        mileage.add("210000");
//        mileage.add("135000");
//        mileage.add("200156");
//
//        color.add("Blue");
//        color.add("Blue");
//        color.add("Black");
//
//
//        adapter = new Adapter(make, model, price, condition, year, mileage, color);

        carAdapter = new FirestoreRecyclerAdapter<Car, CarViewHolder>(allCars) {
            @Override
            protected void onBindViewHolder(@NonNull CarViewHolder carViewHolder, int i, @NonNull final Car car) {
                carViewHolder.make.setText(car.getMake());
                carViewHolder.model.setText(car.getModel());
                carViewHolder.price.setText("$" + car.getPrice().toString());
//        final int code = getRandomColor();
//        holder.mCardView.setCardBackgroundColor(holder.view.getResources().getColor(code, null));

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

        FloatingActionButton fab = findViewById(R.id.addCarFloat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



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
