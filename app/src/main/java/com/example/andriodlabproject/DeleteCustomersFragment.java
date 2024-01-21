package com.example.andriodlabproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteCustomersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteCustomersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static TextView textView_delete_customer_alert;
    public static FragmentActivity activity;
    private String mParam1;
    private String mParam2;
    private EditText editText_emailCustomer;
    private Button button_deleteCustomerById;

    public DeleteCustomersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteCustomersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteCustomersFragment newInstance(String param1, String param2) {
        DeleteCustomersFragment fragment = new DeleteCustomersFragment();
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
        textView_delete_customer_alert.setText("Delete Customer By Email");
        textView_delete_customer_alert = getActivity().findViewById(R.id.textView_deleteCustomerAlert);
        textView_delete_customer_alert.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.favourite_alert_initial));
        activity = getActivity();

        editText_emailCustomer = getActivity().findViewById(R.id.editText_emailCustomer);
        button_deleteCustomerById = getActivity().findViewById(R.id.button_deleteCustomerById);
        button_deleteCustomerById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText_emailCustomer.getText().toString();
                if (email.equals("")) {
                    Toast.makeText(getActivity(), "Please enter an email", Toast.LENGTH_SHORT).show();
                } else {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                    Cursor cursor = dataBaseHelper.getUserByEmail(email);
                    // check if the email is found in the database
                    if (cursor.getCount() == 0) {
                        Toast.makeText(getActivity(), "Email is not found", Toast.LENGTH_SHORT).show();
                    } else {
                        //delete the customer
                        dataBaseHelper.deleteUserByEmail(email);
                        makeDeleteCustomerAlertAnimation("customer deleted successfully");
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_customers, container, false);
    }

    public static void makeDeleteCustomerAlertAnimation(String message){
        // transtion animation for textview_favourite_alert
        textView_delete_customer_alert.setText(message);
        textView_delete_customer_alert.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.favourite_alert_animation));

    }

}