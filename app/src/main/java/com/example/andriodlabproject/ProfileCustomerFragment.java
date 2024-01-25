package com.example.andriodlabproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

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
    private ImageButton btn_visible_password;
    private ImageButton btn_visible_confirm_password;
    private Button button_save;
    private  boolean changePass = false;
    private ImageView profileImage;
    private ImageView changeProfileImage;
    private boolean isImageChanged = false;


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
    public void onResume() {
        super.onResume();
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
        btn_visible_password = (ImageButton) getActivity().findViewById(R.id.imgBtn_makeVisible);
        btn_visible_confirm_password = (ImageButton) getActivity().findViewById(R.id.imgBtn_makeVisible2);

        // get the current user
        currentUser = User.currentUser;
        // assign the user info to Text fields
        assignInfoToInputFields(currentUser);

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

                User user = new User(firstName,lastName, gender, email, password, country, city, phoneNumber, permission, null, -1);

                // check if all input fields are filled and the password and confirm password are the same.
                if (!checkInputFields(user, confirmPassword)){
                    changePass = false;
                    return;
                }
                StringBuilder details = new StringBuilder();
                details.append("Are you sure you want to save the changes?\n\n");

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirm Changes")
                        .setMessage(details.toString())
                        .setNegativeButton("discard changes",null)
                        .setPositiveButton("save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                // Inside your onClickListener for the save button
                                if (isImageChanged && profileImage.getDrawable() != null) {
                                    BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();
                                    Bitmap bitmap = drawable.getBitmap();
                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                                    byte[] data = outputStream.toByteArray();
                                    dataBaseHelper.updateUserProfilePicture(email, data);

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
                                //update nav header with the new (first name and last name)
                                currentUser=User.currentUser;
                                if (currentUser.getString(8).equals("User")){
                                    View headerView = HomeNormalCustomerActivity.navigationView.getHeaderView(0);
                                    TextView navUsername= (TextView) headerView.findViewById(R.id.view_name);
                                    TextView navEmail= (TextView) headerView.findViewById(R.id.view_email);
                                    navUsername.setText(currentUser.getString(0) +" " +currentUser.getString(1));
                                    navEmail.setText(User.currentUser.getString(3));
                                } else {
                                    View headerView = HomeAdminActivity.navigationView.getHeaderView(0);
                                    TextView navUsername= (TextView) headerView.findViewById(R.id.view_name);
                                    TextView navEmail= (TextView) headerView.findViewById(R.id.view_email);
                                    navUsername.setText(currentUser.getString(0) +" " +currentUser.getString(1));
                                    navEmail.setText(User.currentUser.getString(3));
                                }

                            }
                        })
                        .create()
                        .show();



            }
        });

        // listener for the change profile image button
        changeProfileImage.setOnClickListener(e->{
            ImagePicker.with(this)
                    .crop(1f, 1f)  // Set aspect ratio to 1:1 for square crop
                    .cropSquare()  // Alternatively, some libraries provide a shortcut method for square crop
                    .compress(1024)         // Final image size will be less than 1 MB
                    .maxResultSize(1080, 1080)  // Final image resolution
                    .createIntent(a->{
                        startForProfileImageResult.launch(a);
                        return Unit.INSTANCE;
                    });

        });
        editText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                btn_visible_password.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText_password.getText().toString().isEmpty()){
                    btn_visible_password.setVisibility(View.INVISIBLE);
                } else {
                    btn_visible_password.setVisibility(View.VISIBLE);
                }
            }
        });
        editText_confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                btn_visible_confirm_password.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText_confirmPassword.getText().toString().isEmpty()){
                    btn_visible_confirm_password.setVisibility(View.INVISIBLE);
                } else {
                    btn_visible_confirm_password.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_visible_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText_password.getTransformationMethod() == null){
                        editText_password.setTransformationMethod(new PasswordTransformationMethod());
                        btn_visible_password.setImageResource(R.drawable.ic_visible);
                    } else {
                        editText_password.setTransformationMethod(null);
                        btn_visible_password.setImageResource(R.drawable.ic_invisible);
                    }
                }
            });
        btn_visible_confirm_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText_confirmPassword.getTransformationMethod() == null){
                        editText_confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                        btn_visible_confirm_password.setImageResource(R.drawable.ic_visible);
                    } else {
                        editText_confirmPassword.setTransformationMethod(null);
                        btn_visible_confirm_password.setImageResource(R.drawable.ic_invisible);
                    }
                }
            });
    }



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
                btn_visible_password.setVisibility(View.INVISIBLE);
                isValid = false;
            } else if (!user.getPassword().matches(".*[a-zA-Z]+.*")) {
                editText_password.setError("Password must contain at least 1 character");
                btn_visible_password.setVisibility(View.INVISIBLE);
                isValid = false;
            } else if (!user.getPassword().matches(".*[0-9]+.*")) {
                editText_password.setError("Password must contain at least 1 number");
                btn_visible_password.setVisibility(View.INVISIBLE);
                isValid = false;
            } else if (!user.getPassword().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+.*")) {
                editText_password.setError("Password must contain at least 1 special character");
                btn_visible_password.setVisibility(View.INVISIBLE);
                isValid = false;
            }

            // check if the confirm password match the password
            if (editText_confirmPassword.getText().toString().isEmpty()){
                editText_confirmPassword.setError("Please confirm your password");
                btn_visible_confirm_password.setVisibility(View.INVISIBLE);
                isValid = false;
            } else if (!confirmPassword.equals(user.getPassword())){
                editText_confirmPassword.setError("Passwords doesn't match");
                btn_visible_confirm_password.setVisibility(View.INVISIBLE);
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

        // assign the profile image
        if (currentUser.getBlob(9) != null){
            byte[] blobImage = currentUser.getBlob(9);
            Bitmap bitmap = BitmapFactory.decodeByteArray(blobImage,0,blobImage.length);
            profileImage.setImageBitmap(bitmap);

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

                                    profileImage.setImageURI(fileUri); // Update  ImageView
                                    isImageChanged = true;
                                }
                            } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                                Toast.makeText(getActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_customer, container, false);
    }
}