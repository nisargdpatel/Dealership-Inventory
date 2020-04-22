package com.example.dealershipinventory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class edit_car extends AppCompatActivity {

    //-------------------------------------------------------------------------------------------------------------------------
    /*CONDITION SPINNER */
    private ArrayAdapter<String>  conditionAdapter;
    private Spinner conditionSpinner;
    private ArrayList<String> conditionList;
    //-------------------------------------------------------------------------------------------------------------------------
    /*COLOR SPINNER */
    private ArrayAdapter<String>  colorAdapter;
    private Spinner colorSpinner;
    private ArrayList<String> colorList;

    //SUBMIT BUTTON
    private Button submitButton;

    //EDITTEXTS FOR FETCHING USER INPUTS
    private EditText priceEditText;
    private EditText yearBuiltEditText;
    private EditText mileageEditText;

    //RESTRICTED MODIFICATIONS TEXTVIEWS
    private TextView carMakeTextView;
    private TextView carModelTextView;


    //To get OLD values from the database
    String oldMake;
    String oldModel;
    String oldPrice;
    String oldcarCondition;
    String oldYear;
    String oldColor;
    String oldMileage;

    //To record user Choices
    String newMake;
    String newModel;
    String newPrice;
    String newcarCondition;
    String newYear;
    String newColor;
    String newMileage;

    //Error Feed
    TextView errorFeed;

    DocumentReference docref;
    FirebaseFirestore fStore;
    FirebaseUser user;

    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        carMakeTextView = findViewById(R.id.edit_makeTextView);
        carModelTextView = findViewById(R.id.edit_makeModel);
        conditionSpinner = findViewById(R.id.edit_conditionSpinner);
        colorSpinner = findViewById(R.id.edit_colorSpinner);
        submitButton = findViewById(R.id.edit_carButton);
        priceEditText = findViewById(R.id.edit_PriceEdittext);
        yearBuiltEditText = findViewById(R.id.edit_yearBuiltEdittext);
        mileageEditText = findViewById(R.id.edit_mileageEdittext);
        errorFeed = findViewById(R.id.edit_errorFeed);

        conditionList = new ArrayList<String>();
        colorList = new ArrayList<String>();

        //Assigning Condition Data
        conditionList.add("NEW");
        conditionList.add("USED");

        //Assigning Color Data;
        colorList.add("BLUE");
        colorList.add("BLACK");
        colorList.add("WHITE");
        colorList.add("RED");
        colorList.add("GRAY");

        fStore = fStore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        data = getIntent();


        //Passing in makeList Data
        fill_conditionSpinner(conditionList);
        fill_colorSpinner(colorList);

        //PASS THE OLD DATA(FROM DATABASE) INTO THIS METHOD AND IT WILL TAKE CARE OF ALL WIDGETS
        fill_OldData(data.getStringExtra("make"), data.getStringExtra("model"), data.getStringExtra("price"), data.getStringExtra("condition"), data.getStringExtra("year"), data.getStringExtra("color"), data.getStringExtra("mileage"));

        //UPDATE WIDGETS AS PER OLD DATA
        carMakeTextView.setText(oldMake);
        carModelTextView.setText(oldModel);
        priceEditText.setText(oldPrice);
        conditionSpinner.setSelection(conditionAdapter.getPosition(oldcarCondition));
        yearBuiltEditText.setText(oldYear);
        colorSpinner.setSelection(colorAdapter.getPosition(oldColor));
        mileageEditText.setText(oldMileage);



        //MAKE CONDITION SELECTED ITEM
        conditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newcarCondition =  parent.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), newcarCondition + " Selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //MAKE COLOR SELECTED ITEM
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newColor =  parent.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), newColor + " Selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the data from edittexts first, no need to validate or anything.. just get that numerical data...
                newPrice = priceEditText.getText().toString();
                newYear = yearBuiltEditText.getText().toString();
                newMileage = mileageEditText.getText().toString();

                docref = fStore.collection("cars").document(data.getStringExtra("carId"));
                Toast.makeText(getApplicationContext(), "DocId: " + data.getStringExtra("carId"), Toast.LENGTH_SHORT).show();
                if(newPrice.equals("0"))
                {
                    errorFeed.append("Error: You must choose a price greater than 0!\n");
                }
                else if(newYear.equals("0"))
                {
                    errorFeed.append("Error: You must choose a year greater than 0!\n");
                }
                else
                {
                    errorFeed.setText("");
                    //DATABASE STUFF

                    Map<String, Object> car = new HashMap<>();

                    car.put("make", data.getStringExtra("make"));
                    car.put("model", data.getStringExtra("model"));
                    car.put("price", newPrice);
                    car.put("year", newYear);
                    car.put("mileage", newMileage);
                    car.put("color", newColor);
                    car.put("condition", newcarCondition);

                    docref.update(car).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(edit_car.this, "Car updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(edit_car.this, "Error. Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

    /*----------------------------------------------------------M E T H O D S------------------------------------------------------------------------------*/
    public void fill_conditionSpinner(ArrayList<String> listOfconditions)
    {
        //Set up the spinner for condition
        conditionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfconditions);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        conditionSpinner.setAdapter(conditionAdapter);
    }
    public void fill_colorSpinner(ArrayList<String> listOfcolors)
    {
        //Set up the spinner for color
        colorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfcolors);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        colorSpinner.setAdapter(colorAdapter);
    }
    //NISARG, look into the following method
    public void fill_OldData(String carMake, String carModel, String carPrice, String carCondition, String carYear, String carColor, String carMileage)
    {
        oldMake = carMake;
        oldModel = carModel;
        oldPrice = carPrice;
        oldcarCondition = carCondition;
        oldYear = carYear;
        oldColor = carColor;
        oldMileage = carMileage;
    }
}
