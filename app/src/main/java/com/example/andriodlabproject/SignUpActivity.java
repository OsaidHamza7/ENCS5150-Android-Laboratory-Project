package com.example.andriodlabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner countrySpinner;
    private Spinner citySpinner;
    private String [] cities = null;
    private ImageView imageViewCountryFlag;
    private TextView textViewCountryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button button_registerSignIn = (Button) findViewById(R.id.button_registerSignIn);
        ImageButton button_backToMain = (ImageButton) findViewById(R.id.button_registerBackToMain);

        // set the listener for the sign in button
        button_registerSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the sign in activity
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                SignUpActivity.this.startActivity(intent);
                finish();
            }
        });

        // set the listener for the back button
        button_backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open the main activity
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                SignUpActivity.this.startActivity(intent);
                finish();
            }
        });

        // set the gender spinner
        String[] genders = {"Male", "Female"};
        final Spinner genderSpinner =(Spinner) findViewById(R.id.spinner_registerGender);
        ArrayAdapter<String> objGenderArr = new
                ArrayAdapter<>(this,android.R.layout.simple_spinner_item, genders);
        genderSpinner.setAdapter(objGenderArr);

        imageViewCountryFlag = (ImageView) findViewById(R.id.imageView_registerCountryFlag);
        textViewCountryCode = (TextView) findViewById(R.id.textView_CountryCode);

        // set the country spinner
        String [] countries = {"Palestine", "Jordan", "Yemen", "Lebanon", "Syria"};
        countrySpinner =(Spinner) findViewById(R.id.spinner_registerCountry);
        ArrayAdapter<String> objCountryArr = new
                ArrayAdapter<>(this,android.R.layout.simple_spinner_item, countries);
        countrySpinner.setAdapter(objCountryArr);

        // set the city spinner
        citySpinner =(Spinner) findViewById(R.id.spinner_registerCity);
        // set the listener for the country spinner to change the cities when the country is changed
        countrySpinner.setOnItemSelectedListener(this);

        // make the spinner select nothing at the beginning and show the hint


    }

    /**
     * This method is called when an item in the country spinner is selected
     * @param parent the adapter view where the selection happened
     * @param view the view within the adapter view that was clicked
     * @param pos the position of the view in the adapter
     * @param id the row id of the item that is selected
     * function : change the cities in the city spinner and change the country code and flag
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (pos == 0) { // Palestine
            // add cities of Palestine and set the country code and flag
            cities = new String[]{"Gaza", "Tulkarm", "Ramallah", "Hebron", "Jerusalem", "Jenin", "Nablus", "Qalqilya", "Tubas", "Salfit", "Bethlehem", "Jericho"};
            imageViewCountryFlag.setImageResource(R.drawable.palestine);
            textViewCountryCode.setText("+970");
        }
        else if (pos == 1) { // Jordan
            // add cities of Jordan and set the country code and flag
            cities = new String[]{"Amman", "Irbid", "Zarqa", "Aqaba", "Salt", "Madaba", "Karak", "Ma'an", "Tafilah", "Mafraq", "Jerash", "Ajloun"};
            imageViewCountryFlag.setImageResource(R.drawable.jordan);
            textViewCountryCode.setText("+962");
        }
        else if (pos == 2) { // Yemen
            // add cities of Yemen and set the country code and flag
            cities = new String[]{"Sana'a", "Aden", "Taiz", "Al Hudaydah", "Ibb", "Dhamar", "Al Mukalla", "Zinjibar", "Sayyan", "Ash Shihr", "Sahar", "Hajjah"};
            imageViewCountryFlag.setImageResource(R.drawable.yemen);
            textViewCountryCode.setText("+967");
        }
        else if (pos == 3) { // Lebanon
            // add cities of Lebanon and set the country code and flag
            cities = new String[]{"Beirut", "Tripoli", "Sidon", "Tyre", "Jounieh", "Zahle", "Byblos", "Baalbek", "Batroun", "Aley", "Nabatieh", "Jbeil"};
            imageViewCountryFlag.setImageResource(R.drawable.lebanon);
            textViewCountryCode.setText("+961");
        }
        else if (pos == 4) { // Syria
            // add cities of Syria
            cities = new String[]{"Damascus", "Aleppo", "Homs", "Hama", "Latakia", "Deir ez-Zor", "Ar-Raqqah", "Al-Hasakah", "Qamishli", "Idlib", "Daraa", "As-Suwayda"};
            imageViewCountryFlag.setImageResource(R.drawable.syria);
            textViewCountryCode.setText("+963");

        }
        ArrayAdapter<String> objCityArr = new
                ArrayAdapter<>(this,android.R.layout.simple_spinner_item, cities);
        citySpinner.setAdapter(objCityArr);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}