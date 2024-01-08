package com.example.andriodlabproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarMenuFragment extends Fragment {

    public static TextView textView_favourite_alert;
    public static FragmentActivity activity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private CarAdapter adapter;
    public Button lastButtonPressed;
    public CarMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarMenuFragment newInstance(String param1, String param2) {
        CarMenuFragment fragment = new CarMenuFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // transtion animation for textview_favourite_alert
        textView_favourite_alert = getActivity().findViewById(R.id.textView_favouriteAlert);
        textView_favourite_alert.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.favourite_alert_initial));
        activity = getActivity();

    }
    @Override
    public void onResume(){
        super.onResume();
        recyclerView = getActivity().findViewById(R.id.recycler_car_menu);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.allCars);
        recyclerView.setAdapter(adapter);

        Button button_all =(Button) getActivity().findViewById(R.id.button_all);
        Button button_chevrolet =(Button) getActivity().findViewById(R.id.button_chevrolet);
        Button button_ford =(Button) getActivity().findViewById(R.id.button_ford);
        Button button_dodge =(Button) getActivity().findViewById(R.id.button_dodge);
        Button button_honda =(Button) getActivity().findViewById(R.id.button_honda);
        Button button_jeep =(Button) getActivity().findViewById(R.id.button_jeep);
        Button button_lamborghini =(Button) getActivity().findViewById(R.id.button_lamborghini);
        Button button_koenigsegg =(Button) getActivity().findViewById(R.id.button_koenigsegg);
        Button button_tesla =(Button) getActivity().findViewById(R.id.button_tesla);
        Button button_toyota =(Button) getActivity().findViewById(R.id.button_toyota);

        lastButtonPressed = button_all;

        button_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTextWhiteAndMakeBackgroundBlack(button_all);
                makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                lastButtonPressed=button_all;
                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.allCars);
                recyclerView.setAdapter(adapter);
            }
        });
        button_chevrolet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTextWhiteAndMakeBackgroundBlack(button_chevrolet);
                makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                lastButtonPressed=button_chevrolet;
                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.chevroletCars);
                recyclerView.setAdapter(adapter);
            }
        });

        button_ford.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTextWhiteAndMakeBackgroundBlack(button_ford);
                makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                lastButtonPressed=button_ford;
                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.fordCars);
                recyclerView.setAdapter(adapter);
            }
        });

        button_dodge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTextWhiteAndMakeBackgroundBlack(button_dodge);
                makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                lastButtonPressed=button_dodge;
                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.dodgeCars);
                recyclerView.setAdapter(adapter);
            }
        });

        button_honda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTextWhiteAndMakeBackgroundBlack(button_honda);
                makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                lastButtonPressed=button_honda;
                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.hondaCars);
                recyclerView.setAdapter(adapter);
            }
        });


        button_jeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTextWhiteAndMakeBackgroundBlack(button_jeep);
                makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                lastButtonPressed=button_jeep;
                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.jeepCars);
                recyclerView.setAdapter(adapter);
            }
        });

        button_lamborghini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTextWhiteAndMakeBackgroundBlack(button_lamborghini);
                makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                lastButtonPressed=button_lamborghini;
                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.lamborghiniCars);
                recyclerView.setAdapter(adapter);
            }
        });

        button_koenigsegg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTextWhiteAndMakeBackgroundBlack(button_koenigsegg);
                makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                lastButtonPressed=button_koenigsegg;
                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.koenigseggCars);
                recyclerView.setAdapter(adapter);
            }
        });


        button_tesla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTextWhiteAndMakeBackgroundBlack(button_tesla);
                makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                lastButtonPressed=button_tesla;
                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.teslaCars);
                recyclerView.setAdapter(adapter);
            }
        });

        button_toyota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeTextWhiteAndMakeBackgroundBlack(button_toyota);
                makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                lastButtonPressed=button_toyota;
                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.toyotaCars);
                recyclerView.setAdapter(adapter);
            }
        });



    }

    public void makeTextBlackMakeBackgroundWhite(Button button){
        button.setTextColor(Color.BLACK);
        button.setBackground(getResources().getDrawable(R.drawable.white_button));
    }
    public void makeTextWhiteAndMakeBackgroundBlack(Button button){
        button.setTextColor(Color.WHITE);
        button.setBackground(getResources().getDrawable(R.drawable.black_button));

    }

    public static void makeFavouriteAlertAnimation(String message){
        // transtion animation for textview_favourite_alert
        textView_favourite_alert.setText(message);
        textView_favourite_alert.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.favourite_alert_animation));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_menu, container, false);
    }
}