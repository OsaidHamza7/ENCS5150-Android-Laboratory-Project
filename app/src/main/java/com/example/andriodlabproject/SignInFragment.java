package com.example.andriodlabproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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

    public void onResume() {
        super.onResume();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button_signIn = (Button) getActivity().findViewById(R.id.button_signIn);
        Button btnOpenSignUp = (Button) getActivity().findViewById(R.id.bottun_OpenSignUp);

        EditText editText_email = (EditText) getActivity().findViewById(R.id.editText_email);
        EditText editText_password = (EditText) getActivity().findViewById(R.id.editText_password);
        CheckBox checkBox_rememberMe = (CheckBox) getActivity().findViewById(R.id.checkBox_rememberMe);

        // check if the user checked the remember me checkbox
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        String email = sharedPrefManager.readString("email", "noValue");
        String password = sharedPrefManager.readString("password", "noValue");
        if (!email.equals("noValue") && !password.equals("noValue")){
            // if the user checked the remember me checkbox
            // set the email and password in the edit texts
            editText_email.setText(email);
            editText_password.setText(password);
            checkBox_rememberMe.setChecked(true);
        } else {
            // if the user didn't check the remember me checkbox
            // set the email and password in the edit texts to empty strings
            editText_email.setText("");
            editText_password.setText("");
            checkBox_rememberMe.setChecked(false);
        }

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
                // check the remember me checkbox
                if (checkBox_rememberMe.isChecked()){
                    // if the user checked the remember me checkbox
                    // save the email and password in the shared preferences
                    SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getActivity());
                    sharedPrefManager.writeString("email", email);
                    sharedPrefManager.writeString("password", password);
                }
                else {
                    // if the user didn't check the remember me checkbox
                    // save the email and password in the shared preferences as empty strings
                    SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getActivity());
                    sharedPrefManager.writeString("email", "noValue");
                    sharedPrefManager.writeString("password", "noValue");
                }


                if (cursor.getString(8).equals("Admin")){
                    // if the user is admin
                    /*
                        TODO: open the admin activity
                     */

                } else{
                    // if the user is normal customer
                    ((SignInActivity)getActivity()).removeSignInFragment();
                    Intent intent = new Intent(getActivity(), HomeNormalCustomerActivity.class);
                    SignInFragment.this.startActivity(intent);
                    return;
                }




            }
        });


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