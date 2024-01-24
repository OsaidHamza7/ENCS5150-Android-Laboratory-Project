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
 * A simple {@link Fragment} subclass.//
 * Use the {@link SpecialOffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpecialOffersFragment extends Fragment {

    private ProgressBar progressBar;
    public static List<Car> allCars = new ArrayList<>();
    public static List<SpecialOffer> allSpecialOffers = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private CarAdapterSpecialOffers adapter;
    public SpecialOffersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpecialOffersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpecialOffersFragment newInstance(String param1, String param2) {
        SpecialOffersFragment fragment = new SpecialOffersFragment();
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
        progressBar = getActivity().findViewById(R.id.progressBar_specialOffers);
        recyclerView = getActivity().findViewById(R.id.recycler_special_offers);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getSpecialOffersFromDB();
                adapter = new CarAdapterSpecialOffers(getActivity(), allCars, allSpecialOffers);

                getActivity().runOnUiThread(() -> {
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                });
            }
        });
        thread.start();

    }

    // function to get all special offers from the database
    private void getSpecialOffersFromDB() {
        allCars.clear();
        allSpecialOffers.clear();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        Cursor cursor = dataBaseHelper.getAllSpecialOffersWithCarInfoNotReserved();


        while (cursor.moveToNext()) {
            Car car = new Car();
            car.setID(cursor.getInt(1));
            car.setFactoryName(cursor.getString(6));
            car.setType(cursor.getString(7));
            car.setPrice(cursor.getString(8));
            car.setFuelType(cursor.getString(9));
            car.setTransmission(cursor.getString(10));
            car.setMileage(cursor.getString(11));
            car.setImgCar(cursor.getInt(12));
            car.setDealerID(cursor.getInt(13));

            // check if the car in fav list
            boolean fav = dataBaseHelper.isFavorite(User.currentUser.getString(3), car.getID());
            if (fav)
                car.setImgFavButton(R.drawable.ic_favorite);
            else
                car.setImgFavButton(R.drawable.ic_favorite_border);


            //find the dealer name
            Cursor dealer = dataBaseHelper.getDealerByID(car.getDealerID());
            if (dealer.getCount() > 0) {
                dealer.moveToNext();
                car.setDealerName(dealer.getString(1));
            }

            allCars.add(car);

            // get special offer
            SpecialOffer specialOffer = new SpecialOffer();
            specialOffer.setID(cursor.getInt(0));
            specialOffer.setCarID(cursor.getInt(1));
            specialOffer.setStartDate(cursor.getString(2));
            specialOffer.setEndDate(cursor.getString(3));
            specialOffer.setDiscount(cursor.getString(4));
            allSpecialOffers.add(specialOffer);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_special_offers, container, false);
    }
}