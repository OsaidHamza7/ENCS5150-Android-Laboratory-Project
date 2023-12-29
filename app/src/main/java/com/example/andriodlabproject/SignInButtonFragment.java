package com.example.andriodlabproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInButtonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInButtonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInButtonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInButtonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInButtonFragment newInstance(String param1, String param2) {
        SignInButtonFragment fragment = new SignInButtonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnOpenSignIn = (Button) getActivity().findViewById(R.id.btnOpenSignIn);
        Button btnOpenSignUp = (Button) getActivity().findViewById(R.id.btnOpenSignUp);
        btnOpenSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SignInActivity)getActivity()).addSignInFragment();
            }
        });

        // open the sign up activity
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
        return inflater.inflate(R.layout.fragment_sign_in_button, container, false);
    }
}