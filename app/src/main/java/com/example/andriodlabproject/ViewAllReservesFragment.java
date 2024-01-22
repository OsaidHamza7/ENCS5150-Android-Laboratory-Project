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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewAllReservesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAllReservesFragment extends Fragment {
//
    private RecyclerView recyclerView;
    private CarAdapter adapter;
    private DataBaseHelper dataBaseHelper;
    private ProgressBar progressBar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewAllReservesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewAllReservesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewAllReservesFragment newInstance(String param1, String param2) {
        ViewAllReservesFragment fragment = new ViewAllReservesFragment();
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
        dataBaseHelper = new DataBaseHelper(getActivity());

        progressBar = getActivity().findViewById(R.id.progressBar3);
        recyclerView = getActivity().findViewById(R.id.recycler_all_reserved_cars);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        List <Car> allCarsReserved = new ArrayList<>();
        List <User> allUsersReserved = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // get all reservations from the database
                getAllReservation(allCarsReserved, allUsersReserved);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        adapter = new CarAdapter(getActivity(), allCarsReserved, allUsersReserved);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);

                    }
                });
            }
        });
        thread.start();




    }

    private void getAllReservation(List<Car> allCars, List<User> allUsers) {
        int dealerID = User.currentUser.getInt(10);
        Cursor cursor = null;
        if (dealerID == -1){ // global admin
            cursor = dataBaseHelper.getAllReservationsWithCarInfoAndUserInfo();
        } else { // dealer admin
            cursor = dataBaseHelper.getAllReservationsWithCarInfoAndUserInfoByDealerID(dealerID);
        }
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
            car.setDealerID(cursor.getInt(12));

            Cursor tempDealer = dataBaseHelper.getDealerByID(cursor.getInt(12));
            tempDealer.moveToNext();
            car.setDealerName(tempDealer.getString(1));

            User user = new User();
            user.setEmail(cursor.getString(1));
            user.setFirstName(cursor.getString(13));
            user.setLastName(cursor.getString(14));
            user.setGender(cursor.getString(15));
            user.setCountry(cursor.getString(18));
            user.setCity(cursor.getString(19));
            user.setPhoneNumber(cursor.getString(20));





            allCars.add(car);
            allUsers.add(user);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_all_reserves, container, false);
    }
}