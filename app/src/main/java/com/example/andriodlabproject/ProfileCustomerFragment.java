package com.example.andriodlabproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import kotlin.Unit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileCustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileCustomerFragment extends Fragment {

    private TextView textView_email;
    private Cursor currentUser;
    private EditText editText_firstName;
    private EditText editText_lastName;
    private EditText editText_password;
    private EditText editText_confirmPassword;
    private EditText editText_country;
    private EditText editText_city;
    private EditText editText_phoneNumber;
    private TextView textView_countryCode;
    private ImageView imageView_countryFlag;
    private Button button_save;
    private  boolean changePass = false;
    private ImageView profileImage;
    private ImageView changeProfileImage;


    DataBaseHelper dataBaseHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileCustomerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileCustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileCustomerFragment newInstance(String param1, String param2) {
        ProfileCustomerFragment fragment = new ProfileCustomerFragment();
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
    public void onResume(){
        super.onResume();

        // get the current user
        currentUser = User.currentUser;

        // assign the user info to Text fields
        assignInfoToInputFields(currentUser);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView_email = getActivity().findViewById(R.id.textView_email);
        editText_password = getActivity().findViewById(R.id.editText_editPassword);
        editText_confirmPassword = getActivity().findViewById(R.id.editText_editConfirmPassword);
        editText_firstName = getActivity().findViewById(R.id.editText_editFirstName);
        editText_lastName = getActivity().findViewById(R.id.editText_editLastName);
        editText_country = getActivity().findViewById(R.id.editText_editCountry);
        editText_city = getActivity().findViewById(R.id.editText_editCity);
        editText_phoneNumber = getActivity().findViewById(R.id.editText_editPhoneNumber);
        imageView_countryFlag = getActivity().findViewById(R.id.imageView_editCountryFlag);
        textView_countryCode = getActivity().findViewById(R.id.textView_editCountryCode);
        button_save = getActivity().findViewById(R.id.button_saveEdit);
        profileImage = getActivity().findViewById(R.id.imageView_profileImage);
        changeProfileImage = getActivity().findViewById(R.id.imageView_changeProfileImage);


        // access the database
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());

        // set the on click listener for the save button
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the user info from the input fields
                String firstName = editText_firstName.getText().toString();
                String lastName = editText_lastName.getText().toString();
                String country = editText_country.getText().toString();
                String city = editText_city.getText().toString();
                String phoneNumber = editText_phoneNumber.getText().toString();
                String gender = currentUser.getString(2);
                String permission = currentUser.getString(8);
                String password = editText_password.getText().toString();
                String confirmPassword = editText_confirmPassword.getText().toString();
                String email = currentUser.getString(3);

                User user = new User(firstName,lastName, gender, email, password, country, city, phoneNumber, permission,null);

                // check if all input fields are filled and the password and confirm password are the same
                if (!checkInputFields(user, confirmPassword)){
                    changePass = false;
                    return;
                }

                if (changePass){
                    // hash the password then update it in the database
                    dataBaseHelper.updateUserPassword(email, User.hashPassword(password));

                }

                // update the user info in the database ( First name, Last name, Phone)
                dataBaseHelper.updateUserInfo(user);

                // update the current user info
                currentUser = dataBaseHelper.getUserByEmail(email);
                currentUser.moveToFirst();
                User.currentUser = currentUser;

                // assign the user info to Text fields
                assignInfoToInputFields(currentUser);

                Toast.makeText(getActivity(), "Profile Updated successfully", Toast.LENGTH_SHORT).show();


                //clear the passwords fields
                editText_password.setText("");
                editText_confirmPassword.setText("");
                changePass = false;

            }
        });

        // listener for the change profile image button
        changeProfileImage.setOnClickListener(e->{
            ImagePicker.with(this)
                    .crop()
                    .compress(1024)         // Final image size will be less than 1 MB (Optional)
                    .maxResultSize(1080, 1080)  // Final image resolution will be less than 1080 x 1080 (Optional)
                    .createIntent(a->{
                         startForProfileImageResult.launch(a);
                        return Unit.INSTANCE;
                    });

        });




    }

    // method to get the result of the image picker
    private ActivityResultLauncher<Intent> startForProfileImageResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            Intent data = result.getData();
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                if (data != null && data.getData() != null) {
                                    Uri fileUri = data.getData();
                                    profileImage.setImageURI(fileUri); // Update your ImageView
                                }
                            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                                Toast.makeText(getActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



    // function to check if all input fields are filled and the password and confirm password are the same
    public boolean checkInputFields(User user, String confirmPassword){
        boolean isValid = true;

        if (user.getFirstName().replace(" ", "").isEmpty()){
            editText_firstName.setError("Please enter your first name");
            isValid = false;
        }
        if (user.getLastName().replace(" ", "").isEmpty()){
            editText_lastName.setError("Please enter your last name");
            isValid = false;
        }
        if (user.getPhoneNumber().replace(" ", "").isEmpty()){
            editText_phoneNumber.setError("Please enter your phone number");
            isValid = false;
            // check if the the phone number contain only numbers
        } else if(!user.getPhoneNumber().matches("[0-9]+")){
            editText_phoneNumber.setError("Please enter");
        }
        // if the user want to change the password
        if (!user.getPassword().replace(" ","").isEmpty()){
            if (user.getPassword().length() < 5) {
                editText_password.setError("Password must contain at least 5 characters");
                isValid = false;
            } else if (!user.getPassword().matches(".*[a-zA-Z]+.*")) {
                editText_password.setError("Password must contain at least 1 character");
                isValid = false;
            } else if (!user.getPassword().matches(".*[0-9]+.*")) {
                editText_password.setError("Password must contain at least 1 number");
                isValid = false;
            } else if (!user.getPassword().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+.*")) {
                editText_password.setError("Password must contain at least 1 special character");
                isValid = false;
            }

            // check if the confirm password match the password
            if (editText_confirmPassword.getText().toString().isEmpty()){
                editText_confirmPassword.setError("Please confirm your password");
                isValid = false;
            } else if (!confirmPassword.equals(user.getPassword())){
                editText_confirmPassword.setError("Passwords doesn't match");
                isValid = false;
            }
            changePass = true;
        }

        return isValid;
    }

    // fucntion to assign info to input fields
    public void assignInfoToInputFields(Cursor currentUser){
        textView_email.setText(currentUser.getString(3));
        editText_firstName.setText(currentUser.getString(0));
        editText_lastName.setText(currentUser.getString(1));
        editText_country.setText(currentUser.getString(5));
        editText_city.setText(currentUser.getString(6));
        editText_phoneNumber.setText(currentUser.getString(7));
        imageView_countryFlag.setImageResource(getFlagResource(currentUser.getString(5)));

        if (currentUser.getString(2).equals("Male")){
            //change the enddrawable icon in the first name
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.baseline_male_35);
            editText_firstName.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

        } else {
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.baseline_female_35);
            editText_firstName.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

        }

    }

    // function to check return the flag resource according to the country and assign the country code
    private int getFlagResource(String country) {
        if (country.equals("Palestine")){
            textView_countryCode.setText("+970");
            return R.drawable.palestine;
        } else if (country.equals("Jordan")){
            textView_countryCode.setText("+962");
            return R.drawable.jordan;
        } else if (country.equals("Yemen")){
            textView_countryCode.setText("+967");
            return R.drawable.yemen;
        } else if (country.equals("Lebanon")){
            textView_countryCode.setText("+961");
            return R.drawable.lebanon;
        } else if (country.equals("Syria")){
            textView_countryCode.setText("+963");
            return R.drawable.syria;
        }
        return 0;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_customer, container, false);
    }
}