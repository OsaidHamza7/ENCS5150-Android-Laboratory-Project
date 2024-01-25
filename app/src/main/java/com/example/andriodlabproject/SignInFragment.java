package com.example.andriodlabproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {
//
    private EditText editText_email;
    private EditText editText_password;
    private CheckBox checkBox_rememberMe;
    private SharedPrefManager sharedPrefManager;
    private ImageButton btn_visible_password;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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

        // check if the user checked the remember me checkbox
        String email = sharedPrefManager.readString("email", "noValue");

        if (!email.equals("noValue")){
            // if the user checked the remember me checkbox
            // set the email and password in the edit texts
            editText_email.setText(email);
            editText_password.setText("");
            checkBox_rememberMe.setChecked(true);
        } else {
            // if the user didn't check the remember me checkbox
            // set the email and password in the edit texts to empty strings
            editText_email.setText("");
            editText_password.setText("");
            checkBox_rememberMe.setChecked(false);
        }


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button_signIn = (Button) getActivity().findViewById(R.id.button_signIn);
        Button btnOpenSignUp = (Button) getActivity().findViewById(R.id.bottun_OpenSignUp);

        editText_email = (EditText) getActivity().findViewById(R.id.editText_email);
        editText_password = (EditText) getActivity().findViewById(R.id.editText_password);
        checkBox_rememberMe = (CheckBox) getActivity().findViewById(R.id.checkBox_rememberMe);
        btn_visible_password = (ImageButton) getActivity().findViewById(R.id.imgBtn_visible);
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());


        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editText_email.getText().toString();
                String password = editText_password.getText().toString();


                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                Cursor cursor = dataBaseHelper.getUserByEmail(email);

                // check if the email is found in the database
                if (cursor.getCount() == 0){
                    editText_email.setError("Email is not found");
                    return;
                } else {
                    // check if the password is correct
                    cursor.moveToFirst();
                    String passwordFromDB = cursor.getString(4);

                    if (User.checkPassword(password, passwordFromDB) == false){
                        editText_password.setError("Password is incorrect");
                        return;
                    }
                }

                User.currentUser = cursor;

                // check the remember me checkbox
                if (checkBox_rememberMe.isChecked()){
                    // if the user checked the remember me checkbox
                    // save the email and password in the shared preferences
                    sharedPrefManager.writeString("email", email);
                }
                else {
                    // if the user didn't check the remember me checkbox
                    // save the email and password in the shared preferences as empty strings
                    sharedPrefManager.writeString("email", "noValue");
                }


                if (cursor.getString(8).equals("Admin")){
                    // if the user is admin
                    ((SignInActivity)getActivity()).removeSignInFragment();
                    Intent intent = new Intent(getActivity(), HomeAdminActivity.class);
                    SignInFragment.this.startActivity(intent);

                }
                else{
                    // if the user is normal customer
                    ((SignInActivity)getActivity()).removeSignInFragment();
                    Intent intent = new Intent(getActivity(), HomeNormalCustomerActivity.class);
                    SignInFragment.this.startActivity(intent);
                    return;
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
        }
        );
        btnOpenSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();


            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        return view;
    }
}