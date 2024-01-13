package com.example.andriodlabproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarMenuFragment extends Fragment implements AdapterView.OnItemSelectedListener{

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
    String[] RangeMilages = {
            "0","1-999","1,000-9,999","10,000-19,999","20,000-29,999",
            "30,000-39,999","40,000-49,999","50,000-59,999","60,000-69,999",
            "70,000-79,999", "80,000-89,999","90,000-99,999", "100,000-109,999",
            "110,000-119,999","120,000-129,999", "130,000-139,999","140,000-149,999",
            "150,000-159,999","160,000-169,999", "170,000-179,999", "180,000-189,999",
            "190,000-199,999","+200,000",
    };
    int counter = 0;
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
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);

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

        ImageButton OpenBottomSheet =(ImageButton) getActivity().findViewById(R.id.button_filter);

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

        OpenBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.modal_bottom_sheet,
                                (LinearLayout) getActivity().findViewById(R.id.modalBottomSheetContainer));
                List<Button> clickedButtons = new ArrayList<>();

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
                Button btn_auto = bottomSheetView.findViewById(R.id.btn_automatic);
                Button btn_manual = bottomSheetView.findViewById(R.id.btn_manual);

                Button btn_diesel = bottomSheetView.findViewById(R.id.btn_diesel);
                Button btn_petrol = bottomSheetView.findViewById(R.id.btn_petrol);
                Button btn_electric = bottomSheetView.findViewById(R.id.btn_electric);
                Button btn_hybrid = bottomSheetView.findViewById(R.id.btn_hybrid);

                Button btn_applay = bottomSheetView.findViewById(R.id.btn_applay);
                Button btn_reset = bottomSheetView.findViewById(R.id.btn_reset);

                btn_applay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProgressBar pb = (ProgressBar) bottomSheetView.findViewById(R.id.progressBar);
                        prog(pb);
                    }

                });


                Spinner spin = bottomSheetView.findViewById(R.id.fromPrice);
                spin.setOnItemSelectedListener(CarMenuFragment.this);


                ArrayAdapter ad = new ArrayAdapter(getActivity(),
                        android.R.layout.simple_spinner_item, RangeMilages);

                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(ad);

                btn_auto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeTextWhiteAndMakeBackgroundBlack(btn_auto);
                        clickedButtons.add(btn_auto);
                    }
                });

                btn_diesel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeTextWhiteAndMakeBackgroundBlack(btn_diesel);
                        clickedButtons.add(btn_diesel);
                    }
                });

                btn_manual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeTextWhiteAndMakeBackgroundBlack(btn_manual);
                        clickedButtons.add(btn_manual);
                    }
                });

                btn_petrol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeTextWhiteAndMakeBackgroundBlack(btn_petrol);
                        clickedButtons.add(btn_petrol);
                    }
                });

                btn_electric.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeTextWhiteAndMakeBackgroundBlack(btn_electric);
                        clickedButtons.add(btn_electric);
                    }
                });

                btn_hybrid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeTextWhiteAndMakeBackgroundBlack(btn_hybrid);
                        clickedButtons.add(btn_hybrid);
                    }
                });

                btn_reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       for(int i=0;i<clickedButtons.size();i++){
                           makeTextBlackMakeBackgroundWhite(clickedButtons.get(i));
                       }
                        clickedButtons.clear();
                    }
                });
            }
        });


        // transtion animation for textview_favourite_alert
        textView_favourite_alert = getActivity().findViewById(R.id.textView_favouriteAlert);
        textView_favourite_alert.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.favourite_alert_initial));
        activity = getActivity();
        //adapter.notifyDataSetChanged();

    }



    @Override
    public void onResume(){
        super.onResume();
        recyclerView = getActivity().findViewById(R.id.recycler_car_menu);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.allCars);
        recyclerView.setAdapter(adapter);

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
    public void prog(ProgressBar pb) {
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter);

                if (counter == 100)
                    t.cancel();

            }
        };
        t.schedule(tt, 0, 50);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}