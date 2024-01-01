package com.example.andriodlabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static String permission = "User";
    private Button button_signup;
    private EditText editText_firstName;
    private EditText editText_lastName;
    private EditText editText_email;
    private EditText editText_password;
    private EditText editText_confirmPassword;
    private EditText editText_phoneNumber;

    private Spinner countrySpinner;
    private Spinner citySpinner;
    private Spinner genderSpinner;
    private String[] cities = null;
    private ImageView imageViewCountryFlag;
    private TextView textViewCountryCode;
    private boolean firstCountry = true;
    private boolean firstGender = true;
    private boolean firstCity = true;
    private int previousCountry = 1;
    private int previousCity = 1;

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
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                SignUpActivity.this.startActivity(intent);
                finish();
            }
        });

        // set the gender spinner
        String[] genders = {"Gender", "Male", "Female"};
        genderSpinner = (Spinner) findViewById(R.id.spinner_registerGender);
        ArrayAdapter<String> objGenderArr = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        genderSpinner.setAdapter(objGenderArr);
        genderSpinner.setOnItemSelectedListener(this);

        imageViewCountryFlag = (ImageView) findViewById(R.id.imageView_registerCountryFlag);
        textViewCountryCode = (TextView) findViewById(R.id.textView_CountryCode);

        // set the country spinner
        String[] countries = {"Country", "Palestine", "Jordan", "Yemen", "Lebanon", "Syria"};
        countrySpinner = (Spinner) findViewById(R.id.spinner_registerCountry);
        ArrayAdapter<String> objCountryArr = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        countrySpinner.setAdapter(objCountryArr);

        // set the city spinner
        citySpinner = (Spinner) findViewById(R.id.spinner_registerCity);
        cities = new String[]{"City"};
        ArrayAdapter<String> objCityArr = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        citySpinner.setAdapter(objCityArr);

        // set the listener for the country spinner to change the cities when the country is changed
        countrySpinner.setOnItemSelectedListener(this);
        // set the listener for the city spinner
        citySpinner.setOnItemSelectedListener(this);


        button_signup = (Button) findViewById(R.id.button_registerSignUp);
        editText_firstName = (EditText) findViewById(R.id.editText_registerFirstName);
        editText_lastName = (EditText) findViewById(R.id.editText_registerLastName);
        editText_email = (EditText) findViewById(R.id.editText_registerEmail);
        editText_password = (EditText) findViewById(R.id.editText_registerPassword);
        editText_confirmPassword = (EditText) findViewById(R.id.editText_registerConfirmPassword);
        editText_phoneNumber = (EditText) findViewById(R.id.editText_registerPhoneNumber);

//        editText_email.setOnKeyListener((view, i, keyEvent) -> {
//            // check if the email is valid
//            if (!editText_email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
//
//                editText_email.setError("Please enter a valid email");
//            } else {
//                // show the email icon
//                //imageView_emailIcon.setVisibility(View.VISIBLE);
//            }
//            return false;
//        });
//
//        editText_password.setOnKeyListener((view, i, keyEvent) -> {
//            // check if passowrd contain at least 5 characters and (1 character and 1 number and 1 special character)
//            if (editText_password.getText().toString().length() < 5){
//                editText_password.setError("Password must contain at least 5 characters");
//            } else if (!editText_password.getText().toString().matches(".*[a-zA-Z]+.*")){
//                editText_password.setError("Password must contain at least 1 character");
//            } else if (!editText_password.getText().toString().matches(".*[0-9]+.*")){
//                editText_password.setError("Password must contain at least 1 number");
//            } else if (!editText_password.getText().toString().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+.*")){
//                editText_password.setError("Password must contain at least 1 special character");
//            } else {
//
//            }
//            return false;
//        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = editText_firstName.getText().toString();
                String lastName = editText_lastName.getText().toString();
                String email = editText_email.getText().toString();
                String password = editText_password.getText().toString();
                String confirmPassword = editText_confirmPassword.getText().toString();
                String phoneNumber = editText_phoneNumber.getText().toString();
                String gender = genderSpinner.getSelectedItem().toString();
                String country = countrySpinner.getSelectedItem().toString();
                String city = citySpinner.getSelectedItem().toString();

                // check if the user entered all the required data and if it is valid
                boolean isValid = isUserDataValid(firstName, lastName, email, password, confirmPassword, phoneNumber);

                // hash the password
                password = User.hashPassword(password);

                // if the data is valid or not
                if (!isValid){
                    return;
                }
                else{
                    User user = new User(firstName, lastName, gender, email, password, country, city, phoneNumber, permission, null);
                    // add the user to the database
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(SignUpActivity.this);

                    // check if the user is added successfully
                    boolean isInserted = dataBaseHelper.insertUser(user);
                    if (isInserted){
                        Toast.makeText(SignUpActivity.this, "User Registered successfully", Toast.LENGTH_SHORT).show();
                        // open the sign in activity
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        SignUpActivity.this.startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "User not added", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


    }



    // this method is used to check if the user entered all the required data and if it is valid
    private boolean isUserDataValid(String firstName, String lastName, String email,
                                    String password, String confirmPassword, String phoneNumber) {

        boolean isValid = true;
        // check if the user entered all the required data
        if (firstName.replaceAll(" ", "").isEmpty()) {
            editText_firstName.setError("Please enter your first name");
            isValid = false;
        }
        if (lastName.replaceAll(" ", "").isEmpty()) {
            editText_lastName.setError("Please enter your last name");
            isValid = false;
        }
        if (email.replaceAll(" ", "").isEmpty()) {
            editText_email.setError("Please enter your email");
            isValid = false;
        } // cehck if the email is valid
        else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            editText_email.setError("Please enter a valid email");
            isValid = false;
        }

        // check if passowrd contain at least 5 characters and (1 character and 1 number and 1 special character)
        if (password.replaceAll(" ", "").isEmpty()) {
            editText_password.setError("Please enter your password");
            isValid = false;
        } else if (password.length() < 5) {
            editText_password.setError("Password must contain at least 5 characters");
            isValid = false;
        } else if (!password.matches(".*[a-zA-Z]+.*")) {
            editText_password.setError("Password must contain at least 1 character");
            isValid = false;
        } else if (!password.matches(".*[0-9]+.*")) {
            editText_password.setError("Password must contain at least 1 number");
            isValid = false;
        } else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+.*")) {
            editText_password.setError("Password must contain at least 1 special character");
            isValid = false;
        }
        // check if the user confirmed the password and if it matches the password
        if (confirmPassword.replaceAll(" ", "").isEmpty()) {
            editText_confirmPassword.setError("Please confirm your password");
            isValid = false;
        } else if (!confirmPassword.equals(password)) {
            editText_confirmPassword.setError("Password doesn't match");
            isValid = false;
        }

        if (phoneNumber.replaceAll(" ", "").isEmpty()) {
            editText_phoneNumber.setError("Please enter your phone number");
            isValid = false;
        }

        // check if the user selected all the required data for the spinners
        if (genderSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(SignUpActivity.this, "Please select the Gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (countrySpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(SignUpActivity.this, "Please select a Country", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (citySpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(SignUpActivity.this, "Please select a City", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;

    }







    /**
     * This method is called when an item in the country spinner is selected
     * @param parent the adapter view where the selection happened
     * @param view the view within the adapter view that was clicked
     * @param pos the position of the view in the adapter
     * @param id the row id of the item that is selected
     * function : change the cities in the city spinner when the country is changed
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // if this for the country spinner
        if (parent.getId()==R.id.spinner_registerCountry){

            if (firstCountry) {
                // change the color of the hint
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey));
                firstCountry = false;
                return;
            } else{
                if (pos == 0) {
                    // show toast to select a country
                    countrySpinner.setSelection(previousCountry);
                    Toast.makeText(this, "Please select a country", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pos == 1) { // Palestine
                    // add cities of Palestine and set the country code and flag
                    cities = new String[]{"City", "Gaza", "Tulkarm", "Ramallah", "Hebron", "Jerusalem", "Jenin", "Nablus", "Qalqilya", "Tubas", "Salfit", "Bethlehem", "Jericho"};
                    imageViewCountryFlag.setImageResource(R.drawable.palestine);
                    textViewCountryCode.setText("+970");
                }
                else if (pos == 2) { // Jordan
                    // add cities of Jordan and set the country code and flag
                    cities = new String[]{"City", "Amman", "Irbid", "Zarqa", "Aqaba", "Salt", "Madaba", "Karak", "Ma'an", "Tafilah", "Mafraq", "Jerash", "Ajloun"};
                    imageViewCountryFlag.setImageResource(R.drawable.jordan);
                    textViewCountryCode.setText("+962");
                }
                else if (pos == 3) { // Yemen
                    // add cities of Yemen and set the country code and flag
                    cities = new String[]{"City", "Sana'a", "Aden", "Taiz", "Al Hudaydah", "Ibb", "Dhamar", "Al Mukalla", "Zinjibar", "Sayyan", "Ash Shihr", "Sahar", "Hajjah"};
                    imageViewCountryFlag.setImageResource(R.drawable.yemen);
                    textViewCountryCode.setText("+967");
                }
                else if (pos == 4) { // Lebanon
                    // add cities of Lebanon and set the country code and flag
                    cities = new String[]{"City", "Beirut", "Tripoli", "Sidon", "Tyre", "Jounieh", "Zahle", "Byblos", "Baalbek", "Batroun", "Aley", "Nabatieh", "Jbeil"};
                    imageViewCountryFlag.setImageResource(R.drawable.lebanon);
                    textViewCountryCode.setText("+961");
                }
                else if (pos == 5) { // Syria
                    // add cities of Syria
                    cities = new String[]{"City", "Damascus", "Aleppo", "Homs", "Hama", "Latakia", "Deir ez-Zor", "Ar-Raqqah", "Al-Hasakah", "Qamishli", "Idlib", "Daraa", "As-Suwayda"};
                    imageViewCountryFlag.setImageResource(R.drawable.syria);
                    textViewCountryCode.setText("+963");

                }
                ArrayAdapter<String> objCityArr = new
                        ArrayAdapter<>(this,android.R.layout.simple_spinner_item, cities);
                citySpinner.setAdapter(objCityArr);
                previousCountry = pos;
                firstCity = true;


            }

        }
        // if this for the city spinner
        else if (parent.getId()==R.id.spinner_registerCity) {
            if (firstCity) {
                // change the color of the hint
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey));
                firstCity = false;
                return;
            } else {
                if (pos == 0) {
                    // show toast to select a city
                    Toast.makeText(this, "Please select a city", Toast.LENGTH_SHORT).show();
                    citySpinner.setSelection(previousCity);
                    return;
                }
            }
            previousCity = pos;

        }
        // if this for the gender spinner
        else if (parent.getId() == R.id.spinner_registerGender){
            if (firstGender){
                // change the color of the hint
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey));
                firstGender = false;

            } else {
                if (pos == 0){
                    // show toast to select Gender
                    Toast.makeText(this, "Please select a Gender", Toast.LENGTH_SHORT).show();
                    genderSpinner.setSelection(1);
                }
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}