package com.example.dealershipinventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivty extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner priceSpinner, conditionSpinner, yearSpinner, makeSpinner, colorSpinner, modelSpinner, mileageSpinner;
    Button searchButton;

    List<String> allMake = new ArrayList<>();
    List<String> hondaMake = new ArrayList<>();
    List<String> toyotaMake = new ArrayList<>();
    List<String> nissanMake = new ArrayList<>();

    List<String> allPrice = new ArrayList<>();
    List<String> allCondition = new ArrayList<>();
    List<String> allYear = new ArrayList<>();
    List<String> allMileage = new ArrayList<>();
    List<String> allColor = new ArrayList<>();
    List<String> anyModel = new ArrayList<>();

    ArrayAdapter<String> adapterMake;
    ArrayAdapter<String> adapterHonda;
    ArrayAdapter<String> adapterToyota;
    ArrayAdapter<String> adapterNissan;
    ArrayAdapter<String> adapterPrice;
    ArrayAdapter<String> adapterCondition;
    ArrayAdapter<String> adapterYear;
    ArrayAdapter<String> adapterMileage;
    ArrayAdapter<String> adapterColor;
    ArrayAdapter<String> adapterAnyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search);

        allMake.add("ANY");
        allMake.add("HONDA");
        allMake.add("TOYOTA");
        allMake.add("NISSAN");

        anyModel.add("ANY");

        hondaMake.add("ANY");
        hondaMake.add("CIVIC");
        hondaMake.add("CRV");
        hondaMake.add("ACCORD");

        toyotaMake.add("ANY");
        toyotaMake.add("SIENNA");
        toyotaMake.add("RAV4");
        toyotaMake.add("CAMRY");

        nissanMake.add("ANY");
        nissanMake.add("ALTIMA");
        nissanMake.add("SENTRA");
        nissanMake.add("ROGUE");

        allPrice.add("ANY");
        allPrice.add("< $10,000");
        allPrice.add("$10,000 - $20,000");
        allPrice.add("> $20,000");

        allCondition.add("ANY");
        allCondition.add("New");
        allCondition.add("Used");

        allYear.add("ANY");
        allYear.add("< 2005");
        allYear.add("2005 - 2010");
        allYear.add("2010 - 2015");
        allYear.add("> 2015");

        allMileage.add("ANY");
        allMileage.add("< 20,000");
        allMileage.add("20,000 - 40,000");
        allMileage.add("40,000 - 60,000");
        allMileage.add("60,000 - 80,000");
        allMileage.add("80,000 - 100,000");
        allMileage.add("> 100,000");

        allColor.add("ANY");
        allColor.add("BLUE");
        allColor.add("BLACK");
        allColor.add("WHITE");
        allColor.add("RED");
        allColor.add("GRAY");


        priceSpinner = findViewById(R.id.priceSearch);
        conditionSpinner = findViewById(R.id.conditionSearch);
        yearSpinner = findViewById(R.id.yearSearch);
        makeSpinner = findViewById(R.id.makeSearch);
        colorSpinner = findViewById(R.id.colorSearch);
        modelSpinner = findViewById(R.id.modelSearch);
        mileageSpinner = findViewById(R.id.mileageSearch);
        searchButton = findViewById(R.id.searchButton);

        adapterMake = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allMake);
        adapterHonda = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hondaMake);
        adapterToyota = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toyotaMake);
        adapterNissan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nissanMake);
        adapterPrice = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allPrice);
        adapterCondition = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allCondition);
        adapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allYear);
        adapterMileage = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allMileage);
        adapterColor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allColor);
        adapterAnyModel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allColor);

        adapterMake.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterHonda.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterToyota.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterNissan.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterPrice.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterCondition.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterYear.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterMileage.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        adapterColor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        makeSpinner.setAdapter(adapterMake);
        makeSpinner.setOnItemSelectedListener(this);

        modelSpinner.setAdapter(adapterHonda);
        priceSpinner.setAdapter(adapterPrice);
        conditionSpinner.setAdapter(adapterCondition);
        yearSpinner.setAdapter(adapterYear);
        mileageSpinner.setAdapter(adapterMileage);
        colorSpinner.setAdapter(adapterColor);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DealerActivity.class);
                i.putExtra("make", makeSpinner.getSelectedItem().toString());
                i.putExtra("model", modelSpinner.getSelectedItem().toString());
                i.putExtra("price", priceSpinner.getSelectedItem().toString());
                i.putExtra("condition", conditionSpinner.getSelectedItem().toString());
                i.putExtra("year", yearSpinner.getSelectedItem().toString());
                i.putExtra("mileage", mileageSpinner.getSelectedItem().toString());
                i.putExtra("color", colorSpinner.getSelectedItem().toString());
                v.getContext().startActivity(i);
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(makeSpinner.getSelectedItem().toString() == "TOYOTA")
        {
            modelSpinner.setAdapter(adapterToyota);
        }
        else if (makeSpinner.getSelectedItem().toString() == "HONDA")
        {
            modelSpinner.setAdapter(adapterHonda);
        }
        else if (makeSpinner.getSelectedItem().toString() == "NISSAN")
        {
            modelSpinner.setAdapter(adapterNissan);
        }
        else if (makeSpinner.getSelectedItem().toString() == "ANY")
        {
            modelSpinner.setAdapter(adapterAnyModel);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
