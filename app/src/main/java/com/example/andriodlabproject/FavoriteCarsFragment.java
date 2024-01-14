package com.example.andriodlabproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteCarsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteCarsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private CarAdapter adapter;

    public FavoriteCarsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteCarsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteCarsFragment newInstance(String param1, String param2) {
        FavoriteCarsFragment fragment = new FavoriteCarsFragment();
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
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        recyclerView = getActivity().findViewById(R.id.recycler_favorite_cars);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//
//
//        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.favCars);
//        recyclerView.setAdapter(adapter);
//    }
    @Override
    public void onResume(){
        super.onResume();
        recyclerView = getActivity().findViewById(R.id.recycler_favorite_cars);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // get the favorite cars from the database
        getFavoriteCars();

        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.favCars);
        recyclerView.setAdapter(adapter);
    }

    // function to get the favorite cars from the database
    public void getFavoriteCars(){
        HomeNormalCustomerActivity.favCars.clear();
        DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)getActivity()).getDatabaseHelper();
        String userEmail = User.currentUser.getString(3);

        // get the favorite cars from the database
        Cursor cursor = dataBaseHelper.getFavoritesWithCarInfoByEmail(userEmail);

        while (cursor.moveToNext()) {
            Car car = new Car();
            car.setID(cursor.getInt(2));
            car.setFactoryName(cursor.getString(4));
            car.setType(cursor.getString(5));
            car.setPrice(cursor.getString(6));
            car.setFuelType(cursor.getString(7));
            car.setTransmission(cursor.getString(8));
            car.setMileage(cursor.getString(9));
            car.setImgCar(cursor.getInt(10));
            car.setImgFavButton(R.drawable.ic_favorite);
            HomeNormalCustomerActivity.favCars.add(car);

        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_cars, container, false);
    }
}