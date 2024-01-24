package com.example.andriodlabproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public String permission = "User";
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private String[] genders = {"Gender", "Male", "Female"};
    private String[] countries = {"Country", "Palestine", "Jordan", "Yemen", "Lebanon", "Syria"};

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // initialize the layout
        initializeLayout();

        if(getActivity() instanceof SignUpActivity){
            button_signup.setText("Sign Up");
            permission = "User";
        }
        else if(getActivity() instanceof HomeAdminActivity){
            button_signup.setText("Add Admin");
            permission = "Admin";
        }

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = editText_firstName.getText().toString();
                String lastName = editText_lastName.getText().toString();
                String email = editText_email.getText().toString().toLowerCase();
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
                    // convert first letter of first name and last name to upper case and the rest to lower case.
                    firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
                    lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();

                    int dealerID = -1;
                    if(User.currentUser != null){

                        dealerID = User.currentUser.getInt(10);

                    }

                    User user = new User(firstName, lastName, gender, email, password, country, city, phoneNumber, permission, null, dealerID);
                    // add the user to the database
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());

                    // check if the user is added successfully
                    boolean isInserted = dataBaseHelper.insertUser(user);
                    if (isInserted){
                        if (permission.equals("User")){
                            Toast.makeText(getActivity(), "User Registered successfully", Toast.LENGTH_SHORT).show();
                            // open the sign in activity
                            Intent intent = new Intent(getActivity(), SignInActivity.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        } else{
                            Toast.makeText(getActivity(), "Admin Added successfully", Toast.LENGTH_SHORT).show();
                            // open the sign in activity again to clear the fields
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new SignUpFragment()).commit();
                        }

                    } else {
                        editText_email.setError("Email is already Exist");
                        Toast.makeText(getActivity(), "Email is already Exist", Toast.LENGTH_SHORT).show();
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
        } // check if the email is valid
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
            editText_confirmPassword.setError("Passwords doesn't match");
            isValid = false;
        }

        if (phoneNumber.replaceAll(" ", "").isEmpty()) {
            editText_phoneNumber.setError("Please enter your phone number");
            isValid = false;
            // check if all the phone number digits are numbers
        } else if (!phoneNumber.matches("[0-9]+")) {
            editText_phoneNumber.setError("Please enter a valid phone number");
            isValid = false;
        }

        // check if the user selected all the required data for the spinners
        if (genderSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Please select the Gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (countrySpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Please select a Country", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (citySpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Please select a City", Toast.LENGTH_SHORT).show();
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
                // change the color of the remaining options to black
                for (int i = 1; i < parent.getChildCount(); i++) {
                    ((TextView) parent.getChildAt(i)).setTextColor(getResources().getColor(R.color.black));
                }
                firstCountry = false;
                return;
            } else{
                if (pos == 0) {
                    // show toast to select a country
                    countrySpinner.setSelection(previousCountry);
                    Toast.makeText(getActivity(), "Please select a country", Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<>(getActivity(), R.layout.spinner_layout, cities);
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
                // change the color of the remaining options to black
                for (int i = 1; i < parent.getChildCount(); i++) {
                    ((TextView) parent.getChildAt(i)).setTextColor(getResources().getColor(R.color.black));
                }
                firstCity = false;
                return;
            } else {
                if (pos == 0) {
                    // show toast to select a city
                    Toast.makeText(getActivity(), "Please select a city", Toast.LENGTH_SHORT).show();
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
                // change the color of the remaining options to black
                for (int i = 1; i < parent.getChildCount(); i++) {
                    ((TextView) parent.getChildAt(i)).setTextColor(getResources().getColor(R.color.black));
                }
                firstGender = false;

            } else {
                if (pos == 0){
                    // show toast to select Gender
                    Toast.makeText(getActivity(), "Please select a Gender", Toast.LENGTH_SHORT).show();
                    genderSpinner.setSelection(1);
                }
            }
        }

    }

    private void initializeLayout(){
        button_signup = (Button) getActivity().findViewById(R.id.button_registerSignUp);
        editText_firstName = (EditText) getActivity().findViewById(R.id.editText_registerFirstName);
        editText_lastName = (EditText) getActivity().findViewById(R.id.editText_registerLastName);
        editText_email = (EditText) getActivity().findViewById(R.id.editText_registerEmail);
        editText_password = (EditText) getActivity().findViewById(R.id.editText_registerPassword);
        editText_confirmPassword = (EditText) getActivity().findViewById(R.id.editText_registerConfirmPassword);
        editText_phoneNumber = (EditText) getActivity().findViewById(R.id.editText_editPhoneNumber);
        genderSpinner = (Spinner) getActivity().findViewById(R.id.spinner_registerGender);
        genderSpinner = (Spinner) getActivity().findViewById(R.id.spinner_registerGender);
        imageViewCountryFlag = (ImageView) getActivity().findViewById(R.id.imageView_editCountryFlag);
        textViewCountryCode = (TextView) getActivity().findViewById(R.id.textView_editCountryCode);
        citySpinner = (Spinner) getActivity().findViewById(R.id.spinner_registerCity);

        // set the gender spinner
        ArrayAdapter<String> objGenderArr = new
                ArrayAdapter<>(getActivity(), R.layout.spinner_layout, genders);
        genderSpinner.setAdapter(objGenderArr);
        genderSpinner.setOnItemSelectedListener(this);

        // set the country spinner
        countrySpinner = (Spinner) getActivity().findViewById(R.id.spinner_registerCountry);
        ArrayAdapter<String> objCountryArr = new
                ArrayAdapter<>(getActivity(), R.layout.spinner_layout, countries);
        countrySpinner.setAdapter(objCountryArr);

        // set the city spinner
        cities = new String[]{"City"};
        ArrayAdapter<String> objCityArr = new
                ArrayAdapter<>(getActivity(), R.layout.spinner_layout, cities);
        citySpinner.setAdapter(objCityArr);

        // set the listener for the country spinner to change the cities when the country is changed
        countrySpinner.setOnItemSelectedListener(this);
        // set the listener for the city spinner
        citySpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}