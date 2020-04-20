package com.example.dealershipinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class add_car extends AppCompatActivity {

    /*MAKE SPINNER*/
    private ArrayAdapter<String> makeAdapter;
    private Spinner makeSpinner;
    private ArrayList<String> makeList;
    //-------------------------------------------------------------------------------------------------------------------------
    /*MODEL SPINNER*/
    private ArrayAdapter<String> modelAdapter;
    private Spinner modelSpinner;
    private ArrayList<String> hondaMake;
    private ArrayList<String> nissanMake;
    private ArrayList<String>  toyotaMake;
    private ArrayList<String> defaultMake;

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


    //To record user Choices
    String ChosenMake;
    String ChosenModel;
    String ChosenPrice;
    String ChosencarCondition;
    String ChosenYear;
    String ChosenColor;
    String ChosenMileage;

    //Error Feed
    TextView errorFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        makeSpinner = findViewById(R.id.makeSpinner);
        modelSpinner = findViewById(R.id.modelSpinner);
        conditionSpinner = findViewById(R.id.conditionSpinner);
        colorSpinner = findViewById(R.id.colorSpinner);
        submitButton = findViewById(R.id.addcarButton);
        priceEditText = findViewById(R.id.addPriceEdittext);
        yearBuiltEditText = findViewById(R.id.yearBuiltEdittext);
        mileageEditText = findViewById(R.id.addmileageEdittext);
        errorFeed = findViewById(R.id.errorFeed);


        makeList =  new ArrayList<String>();
        hondaMake = new ArrayList<String>();
        nissanMake = new ArrayList<String>();
        toyotaMake = new ArrayList<String>();
        defaultMake = new ArrayList<String>();
        conditionList = new ArrayList<String>();
        colorList = new ArrayList<String>();


        //Assigning Condition Data
        conditionList.add("New");
        conditionList.add("Used");

        //Assigning Color Data;
        colorList.add("BLUE");
        colorList.add("BLACK");
        colorList.add("WHITE");
        colorList.add("RED");
        colorList.add("GRAY");

        //Assigning Make data
       makeList.add("HONDA");
       makeList.add("TOYOTA");
       makeList.add("NISSAN");

       //Assigning Model data
        hondaMake.add("CIVIC");
        hondaMake.add("CRV");
        hondaMake.add("ACCORD");

        toyotaMake.add("SIENNA");
        toyotaMake.add("RAV4");
        toyotaMake.add("CAMRY");

        nissanMake.add("ALTIMA");
        nissanMake.add("SENTRA");
        nissanMake.add("ROGUE");

        defaultMake.add("NONE");
        //Passing in makeList Data
        fill_makeSpinner(makeList);
        fill_conditionSpinner(conditionList);
        fill_colorSpinner(colorList);
        //MAKE SPINNER SELECTED ITEM
        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChosenMake =  parent.getSelectedItem().toString();
                switch (ChosenMake)
                {
                    case "HONDA":
                        fill_modelSpinner(hondaMake);
                        Toast.makeText(getApplicationContext(), ChosenMake + " Selected", Toast.LENGTH_LONG).show();
                        break;
                    case "NISSAN":
                        fill_modelSpinner(nissanMake);
                        Toast.makeText(getApplicationContext(), ChosenMake + " Selected", Toast.LENGTH_LONG).show();
                        break;
                    case "TOYOTA":
                        fill_modelSpinner(toyotaMake);
                        Toast.makeText(getApplicationContext(), ChosenMake + " Selected", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        fill_modelSpinner(defaultMake);
                        Toast.makeText(getApplicationContext(), ChosenMake + " Selected", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //MAKE MODEL SELECTED ITEM
        modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChosenModel =  parent.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), ChosenModel + " Selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //MAKE CONDITION SELECTED ITEM
        conditionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChosencarCondition =  parent.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), ChosencarCondition + " Selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //MAKE COLOR SELECTED ITEM
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChosenColor =  parent.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), ChosenColor + " Selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //ONCLICK LISTENER FOR SUBMIT BUTTON
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the data from edittexts first, no need to validate or anything.. just get that numerical data...
                ChosenPrice = priceEditText.getText().toString();
                ChosenYear = yearBuiltEditText.getText().toString();
                ChosenMileage = mileageEditText.getText().toString();

                //convert them to string..
                if(ChosenModel.equals("NONE"))
                {
                    errorFeed.append("Error: You must choose a car make & a car model!\n");
                }
                else if(ChosenPrice.equals("0"))
                {
                    errorFeed.append("Error: You must choose a price greater than 0!\n");
                }
                else if(ChosenYear.equals("0"))
                {
                    errorFeed.append("Error: You must choose a year greater than 0!\n");
                }
                else
                {
                    errorFeed.setText("");
                    //DATABASE STUFF
                    //@NISARG
                    //Once again, the variables you want to add are:
                    // ChosenMake,
                    // ChosenModel,
                    // ChosenPrice,
                    // ChosenYear,
                    // ChosenMileage,
                    // ChosenColor,
                    // Chosen Condition
                    //LASTLY CHANGE INTENT TO THE MAIN RECYCLER VIEW ACTIVITY
                }
            }
        });

    }
    /*----------------------------------------------------------M E T H O D S------------------------------------------------------------------------------*/
    public void fill_makeSpinner(ArrayList<String> listOfMakes)
    {
        //Set up the spinner for make
        makeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfMakes);
        makeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        makeSpinner.setAdapter(makeAdapter);
    }
    public void fill_modelSpinner(ArrayList<String> listOfModels)
    {
        //Set up the spinner for model
        modelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfModels);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        modelSpinner.setAdapter(modelAdapter);
    }
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

}
