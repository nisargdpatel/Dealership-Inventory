package com.example.dealershipinventory;

import android.os.AsyncTask;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class AddVehicles extends AsyncTask<Map<String, Object>, Void, String> {


    @Override
    protected String doInBackground(Map<String, Object>... maps) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        DocumentReference docref = fStore.collection("cars").document();
        Map<String, Object> car = maps[0];
        docref.set(car);

        return "Car Added";
    }
}
