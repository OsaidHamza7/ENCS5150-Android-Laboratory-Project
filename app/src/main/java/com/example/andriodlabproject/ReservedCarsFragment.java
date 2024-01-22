package com.example.andriodlabproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Console;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservedCarsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservedCarsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private CarAdapter adapter;
    private ProgressBar progressBar;

    public ReservedCarsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservedCarsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservedCarsFragment newInstance(String param1, String param2) {
        ReservedCarsFragment fragment = new ReservedCarsFragment();
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
        progressBar = getActivity().findViewById(R.id.progressBar_reserved);
        recyclerView = getActivity().findViewById(R.id.recycler_reserved_cars);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        progressBar.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getReservedCars();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.reserveCars);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        thread.start();

    }

    // function to get the reserved cars from the database
    public void getReservedCars(){
        // get the database helper
        DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)getActivity()).getDatabaseHelper();
        // get the user email
        String userEmail = User.currentUser.getString(3);
        // get the reserved cars from the database
        Cursor cursor = dataBaseHelper.getReservationWithCarInfoByEmail(userEmail);

        // clear the reserved cars list
        HomeNormalCustomerActivity.reserveCars.clear();

        // add the reserved cars to the reserved cars list
        while (cursor.moveToNext()){
            Car car = new Car();
            car.setID(cursor.getInt(2));
            car.setDate(cursor.getString(3));
            car.setFactoryName(cursor.getString(5));
            car.setType(cursor.getString(6));
            car.setPrice(cursor.getString(7));
            car.setFuelType(cursor.getString(8));
            car.setTransmission(cursor.getString(9));
            car.setMileage(cursor.getString(10));
            car.setImgCar(cursor.getInt(11));
            car.setVisibleDate(View.VISIBLE);
            car.setVisibleReserveButton(View.INVISIBLE);

            // get the dealer name and id from the database
            Cursor dealer = dataBaseHelper.getDealerByID(cursor.getInt(12));
            if (dealer.getCount() > 0) {
                dealer.moveToNext();
                car.setDealerName(dealer.getString(1));
                car.setDealerID(dealer.getInt(0));
            }

            HomeNormalCustomerActivity.reserveCars.add(car);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reserved_cars, container, false);
    }
}