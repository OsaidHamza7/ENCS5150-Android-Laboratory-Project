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
 * Use the {@link ViewAllReservesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAllReservesFragment extends Fragment {

    private RecyclerView recyclerView;
    private CarAdapter adapter;
    private DataBaseHelper dataBaseHelper;

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
        recyclerView = getActivity().findViewById(R.id.recycler_all_reserved_cars);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.allCars);
        recyclerView.setAdapter(adapter);


        dataBaseHelper = new DataBaseHelper(getActivity());
        // get all reservations from the database

    }

    private void getAllReservation(){
        Cursor cursor = dataBaseHelper.getAllReservationsWithCarInfoAndUserInfo();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_all_reserves, container, false);
    }
}