package com.example.andriodlabproject;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private DataBaseHelper dataBaseHelper;

    private ProgressBar progressBar;
    private Button button_all;
    private Button button_chevrolet;
    private Button button_ford;
    private Button button_dodge;
    private Button button_honda;
    private Button button_jeep;
    private Button button_lamborghini;
    private Button button_koenigsegg;
    private  Button button_tesla;
    private Button button_toyota;
    private EditText searchField;
    private Button lastFuelTypeButtonPressed;
    private List<Button> factoryButtons = new ArrayList<>();

    private ImageButton OpenBottomSheet;

    private List<Car> filteredCars = new ArrayList<>();
    private boolean filterApplied = false;

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
            "Choose Range Milage",
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

    }

    @Override
    public void onResume(){
        super.onResume();
        // initialize the layout
        initializeLayout();

        showProgressBar(true);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);

        lastButtonPressed = button_all;

        // set the listener for the factory buttons
        setListenerForFactoryButtons();

        // set the listener for the filter button
        setListenerForFilterButton(bottomSheetDialog);

        // set the listener for the search field
        setListenerForSearchField();


        new Thread(() -> {

            // transition animation for textview_favourite_alert
            textView_favourite_alert.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.favourite_alert_initial));
            activity = getActivity();

            // get all cars from the database
            getAllCarNotSpecialAndNotReserved();

            // Update UI on the main thread
            getActivity().runOnUiThread(() -> {
                recyclerView = getActivity().findViewById(R.id.recycler_car_menu);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                checkFavoriteCarsList();

                adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.allCars);
                recyclerView.setAdapter(adapter);

                showProgressBar(false); // Hide progress bar
            });
        }).start();




    }

    // function to get all cars from the database
    public void getAllCarNotSpecialAndNotReserved(){
        HomeNormalCustomerActivity.allCars.clear();
        HomeNormalCustomerActivity.chevroletCars.clear();
        HomeNormalCustomerActivity.fordCars.clear();
        HomeNormalCustomerActivity.dodgeCars.clear();
        HomeNormalCustomerActivity.hondaCars.clear();
        HomeNormalCustomerActivity.jeepCars.clear();
        HomeNormalCustomerActivity.lamborghiniCars.clear();
        HomeNormalCustomerActivity.koenigseggCars.clear();
        HomeNormalCustomerActivity.teslaCars.clear();
        HomeNormalCustomerActivity.toyotaCars.clear();


        // get all cars not reserved and not on special offer.
        Cursor cursor = dataBaseHelper.getAllCarsNotOnSpecialOfferAndNotReserved();
        while (cursor.moveToNext()) {
            Car car = new Car();
            car.setID(cursor.getInt(0));
            car.setFactoryName(cursor.getString(1));
            car.setType(cursor.getString(2));
            car.setPrice(cursor.getString(3));
            car.setFuelType(cursor.getString(4));
            car.setTransmission(cursor.getString(5));
            car.setMileage(cursor.getString(6));
            car.setImgCar(cursor.getInt(7));
            car.setImgFavButton(R.drawable.ic_favorite_border);
            car.setDealerID(cursor.getInt(8));
            car.setRating(cursor.getDouble(9));
            car.setRatingCount(cursor.getInt(10));

            Cursor dealer = dataBaseHelper.getDealerByID(cursor.getInt(8));
            if (dealer.getCount() > 0) {
                dealer.moveToNext();
                car.setDealerName(dealer.getString(1));
            }

            HomeNormalCustomerActivity.allCars.add(car);

            if (car.getFactoryName().equals("Chevrolet")){
                HomeNormalCustomerActivity.chevroletCars.add(car);
            } else if (car.getFactoryName().equals("Ford")){
                HomeNormalCustomerActivity.fordCars.add(car);
            } else if (car.getFactoryName().equals("Dodge")){
                HomeNormalCustomerActivity.dodgeCars.add(car);
            } else if (car.getFactoryName().equals("Honda")){
                HomeNormalCustomerActivity.hondaCars.add(car);
            } else if (car.getFactoryName().equals("Jeep")){
                HomeNormalCustomerActivity.jeepCars.add(car);
            } else if (car.getFactoryName().equals("Lamborghini")){
                HomeNormalCustomerActivity.lamborghiniCars.add(car);
            } else if (car.getFactoryName().equals("Koenigsegg")){
                HomeNormalCustomerActivity.koenigseggCars.add(car);
            } else if (car.getFactoryName().equals("Tesla")){
                HomeNormalCustomerActivity.teslaCars.add(car);
            } else if (car.getFactoryName().equals("Toyota")){
                HomeNormalCustomerActivity.toyotaCars.add(car);
            }
        }
    }

    // function to search for a car by its type.
    public List<Car> searchForCarByType(String key, List<Car> cars){
        List<Car> searchedCars = new ArrayList<>();
        for (Car car : cars) {
            String carType = car.getType().toLowerCase().trim() + car.getFactoryName().toLowerCase().trim();
            String carType2 = car.getFactoryName().toLowerCase().trim() + car.getType().toLowerCase().trim();
            carType = carType.replaceAll(" ", "");
            carType2 = carType2.replaceAll(" ", "");
            String filteredKey = key.toLowerCase().trim().replaceAll(" ", "");
            if (carType.contains(filteredKey) || carType2.contains(filteredKey)) {
                searchedCars.add(car);
            }
        }
        return searchedCars;
    }

    // function to find the current array based on the last button pressed
    public List<Car> findCurrentArray(Button lastButtonPressed){
        List<Car> currentArray = new ArrayList<>();
        if (lastButtonPressed.getText().toString().equals("All")){
            currentArray = HomeNormalCustomerActivity.allCars;
        } else if (lastButtonPressed.getText().toString().equals("Chevrolet")){
            currentArray = HomeNormalCustomerActivity.chevroletCars;
        } else if (lastButtonPressed.getText().toString().equals("Ford")){
            currentArray = HomeNormalCustomerActivity.fordCars;
        } else if (lastButtonPressed.getText().toString().equals("Dodge")){
            currentArray = HomeNormalCustomerActivity.dodgeCars;
        } else if (lastButtonPressed.getText().toString().equals("Honda")){
            currentArray = HomeNormalCustomerActivity.hondaCars;
        } else if (lastButtonPressed.getText().toString().equals("Jeep")){
            currentArray = HomeNormalCustomerActivity.jeepCars;
        } else if (lastButtonPressed.getText().toString().equals("Lamborghini")){
            currentArray = HomeNormalCustomerActivity.lamborghiniCars;
        } else if (lastButtonPressed.getText().toString().equals("Koenigsegg")){
            currentArray = HomeNormalCustomerActivity.koenigseggCars;
        } else if (lastButtonPressed.getText().toString().equals("Tesla")){
            currentArray = HomeNormalCustomerActivity.teslaCars;
        } else if (lastButtonPressed.getText().toString().equals("Toyota")){
            currentArray = HomeNormalCustomerActivity.toyotaCars;
        }
        return currentArray;
    }

    // check the favorite cars list and change the favorite button image
    public void checkFavoriteCarsList(){
        for (Car car : HomeNormalCustomerActivity.allCars) {
            if (dataBaseHelper.isFavorite(User.currentUser.getString(3), car.getID())) {
                car.setImgFavButton(R.drawable.ic_favorite);
            } else {
                car.setImgFavButton(R.drawable.ic_favorite_border);
            }
        }

    }

    public void checkClickedButtons(List<Button> clickedButtons, Button button){
        if (clickedButtons.contains(button)) {
            makeTextBlackMakeBackgroundWhite(button);
            clickedButtons.remove(button);
        } else {
            makeTextWhiteAndMakeBackgroundBlack(button);
            clickedButtons.add(button);
        }
    }

    List<Car> applyFilter(List<Car> currentArray, List<Button> clickedButtons, Spinner spin, EditText priceFrom, EditText priceTo){
        List<Car> filteredCars = new ArrayList<>();
        List<String> filter = new ArrayList<>();

        for(int i=0;i<clickedButtons.size();i++){
            filter.add(clickedButtons.get(i).getText().toString().toLowerCase().trim());
        }
        int minMileage=0, maxMileage=0;
        if(!spin.getSelectedItem().toString().equals("Choose Range Milage")){

            if (spin.getSelectedItem().toString().equals("0")){
                minMileage = 0;
                maxMileage = 0;
            } else if (spin.getSelectedItem().toString().equals("+200,000")){
                minMileage = 200000;
                maxMileage = 999999999;
            } else {
                minMileage = Integer.parseInt(spin.getSelectedItem().toString().split("-")[0].replace(",", ""));
                maxMileage = Integer.parseInt(spin.getSelectedItem().toString().split("-")[1].replace(",", ""));
            }
            filter.add("mileAge");
        }
        for (Car car : currentArray) {
            String carTransmission = car.getTransmission().toLowerCase().trim();
            String carFuelType = car.getFuelType().toLowerCase().trim();
            int carMileage = Integer.parseInt(car.getMileage());
            if (!filter.contains("automatic") && !filter.contains("manual")){ // if the user didn't choose any transmission type
                carTransmission = "a";
                filter.add("a");
            }
            if (!filter.contains("diesel") && !filter.contains("petrol") && !filter.contains("electric") && !filter.contains("hybrid")){ // if the user didn't choose any fuel type
                carFuelType = "b";
                filter.add("b");
            }


            if (filter.contains(carTransmission) && filter.contains(carFuelType)) {
                if (filter.contains("mileAge")) {
                    if (carMileage >= minMileage && carMileage <= maxMileage) {
                        filteredCars.add(car);
                    }
                } else {
                    filteredCars.add(car);
                }
            }
        }

        // if the user didn't choose any filter
        if(filter.size()==0){
            // make a copy of the all cars list
            filteredCars = new ArrayList<>(currentArray);
        }

        if (!priceFrom.getText().toString().replace(" ", "").equals("") || !priceTo.getText().toString().replace(" ", "").equals("")) {
            // filter the cars based on the price range
            filteredCars = filterCarsBasedOnPriceRange(filteredCars, priceFrom, priceTo);
        }

        return filteredCars;
    }

    // function to filter the cars based on the price range
    public List<Car> filterCarsBasedOnPriceRange(List<Car> filteredCars, EditText priceFrom, EditText priceTo){
        List<Car> filteredCarsBasedOnPriceRange = new ArrayList<>();
        for (Car car : filteredCars) {
            // remove the $ sign from the price
            int carPrice = Integer.parseInt(car.getPrice().replace("$", ""));
            if (!priceFrom.getText().toString().replace(" ", "").equals("") && !priceTo.getText().toString().replace(" ", "").equals("")) {
                if (carPrice >= Integer.parseInt(priceFrom.getText().toString()) && carPrice <= Integer.parseInt(priceTo.getText().toString())) {
                    filteredCarsBasedOnPriceRange.add(car);
                }
            } else if (!priceFrom.getText().toString().replace(" ", "").equals("")) {
                if (carPrice >= Integer.parseInt(priceFrom.getText().toString())) {
                    filteredCarsBasedOnPriceRange.add(car);
                }
            } else if (!priceTo.getText().toString().replace(" ", "").equals("")) {
                if (carPrice <= Integer.parseInt(priceTo.getText().toString())) {
                    filteredCarsBasedOnPriceRange.add(car);
                }
            }
        }
        return filteredCarsBasedOnPriceRange;
    }

    private boolean checkPriceRangeValidity(EditText priceFrom, EditText priceTo){
        if (!priceFrom.getText().toString().replace(" ", "").equals("")) {
            if (!priceFrom.getText().toString().matches("[0-9]+")){
                priceFrom.setError("Please enter a numeric value");
                return false;
            }

            // check if the price is too high for integer
            if (priceFrom.getText().toString().length() > 10){
                priceFrom.setError("Invalid price");
                return false;
            }
        }

        if (!priceTo.getText().toString().replace(" ", "").equals("")) {
            if (!priceTo.getText().toString().matches("[0-9]+")){
                priceTo.setError("Please enter a numeric value");
                return false;
            }

            // check if the price is too high for integer
            if (priceTo.getText().toString().length() > 10){
                priceTo.setError("Invalid price");
                return false;
            }
        }


        // if both price from and price to are not empty
        if (!priceFrom.getText().toString().replace(" ", "").equals("") && !priceTo.getText().toString().replace(" ", "").equals("")) {
            // check if price from is greater than price to
            if (Integer.parseInt(priceFrom.getText().toString()) > Integer.parseInt(priceTo.getText().toString())) {
                priceFrom.setError("Price from must be less than price to");
                return false;
            }
        }
        return true;
    }

    // function to set the listener for the factory buttons
    private void setListenerForFactoryButtons(){

        for (Button button : factoryButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showProgressBar(true);
                    if (button.getText().toString().equals("All")){
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.allCars);
                        searchField.setHint("Search in all cars");
                    } else if (button.getText().toString().equals("Chevrolet")){
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.chevroletCars);
                        searchField.setHint("Search in Chevrolet cars");
                    } else if (button.getText().toString().equals("Ford")){
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.fordCars);
                        searchField.setHint("Search in Ford cars");
                    } else if (button.getText().toString().equals("Dodge")){
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.dodgeCars);
                        searchField.setHint("Search in Dodge cars");
                    } else if (button.getText().toString().equals("Honda")){
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.hondaCars);
                        searchField.setHint("Search in Honda cars");
                    } else if (button.getText().toString().equals("Jeep")){
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.jeepCars);
                        searchField.setHint("Search in Jeep cars");
                    } else if (button.getText().toString().equals("Lamborghini")){
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.lamborghiniCars);
                        searchField.setHint("Search in Lamborghini cars");
                    } else if (button.getText().toString().equals("Koenigsegg")){
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.koenigseggCars);
                        searchField.setHint("Search in Koenigsegg cars");
                    } else if (button.getText().toString().equals("Tesla")){
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.teslaCars);
                        searchField.setHint("Search in Tesla cars");
                    } else if (button.getText().toString().equals("Toyota")){
                        adapter = new CarAdapter(getActivity(),HomeNormalCustomerActivity.toyotaCars);
                        searchField.setHint("Search in Toyota cars");
                    }

                    new Thread(() -> {
                        // Update UI on the main thread
                        getActivity().runOnUiThread(() -> {
                            recyclerView.setAdapter(adapter);
                            filterApplied = false;
                            showProgressBar(false);
                        });
                    }).start();
                    if(lastButtonPressed.equals(button)){
                        return;
                    }
                    makeTextWhiteAndMakeBackgroundBlack(button);
                    makeTextBlackMakeBackgroundWhite(lastButtonPressed);
                    lastButtonPressed=button;
                }
            });
        }
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

    public void initializeLayout(){
        button_all =(Button) getActivity().findViewById(R.id.button_all);
        button_chevrolet =(Button) getActivity().findViewById(R.id.button_chevrolet);
        button_ford =(Button) getActivity().findViewById(R.id.button_ford);
        button_dodge =(Button) getActivity().findViewById(R.id.button_dodge);
        button_honda =(Button) getActivity().findViewById(R.id.button_honda);
        button_jeep =(Button) getActivity().findViewById(R.id.button_jeep);
        button_lamborghini =(Button) getActivity().findViewById(R.id.button_lamborghini);
        button_koenigsegg =(Button) getActivity().findViewById(R.id.button_koenigsegg);
        button_tesla =(Button) getActivity().findViewById(R.id.button_tesla);
        button_toyota =(Button) getActivity().findViewById(R.id.button_toyota);
        searchField = getActivity().findViewById(R.id.editText_search);
        OpenBottomSheet =(ImageButton) getActivity().findViewById(R.id.button_filter);
        textView_favourite_alert = getActivity().findViewById(R.id.textView_favouriteAlert);
        progressBar = getActivity().findViewById(R.id.progressBar_carMenu);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.BLACK));


        textView_favourite_alert.setText("Welcome to Car Menu");

        factoryButtons.add(button_all);
        factoryButtons.add(button_chevrolet);
        factoryButtons.add(button_ford);
        factoryButtons.add(button_dodge);
        factoryButtons.add(button_honda);
        factoryButtons.add(button_jeep);
        factoryButtons.add(button_lamborghini);
        factoryButtons.add(button_koenigsegg);
        factoryButtons.add(button_tesla);
        factoryButtons.add(button_toyota);

        dataBaseHelper = ((HomeNormalCustomerActivity)getActivity()).getDatabaseHelper();

    }

    // function to set the listener for the search field
    private void setListenerForSearchField(){
        searchField.setOnKeyListener((v, keyCode, event) -> {
            List<Car> cars;
            if (filterApplied){
                // if the user applied a filter (search in the filtered cars)
                cars = searchForCarByType(searchField.getText().toString(), filteredCars);
            } else {
                // if the user didn't apply a filter (search in the current array selected)
                cars = searchForCarByType(searchField.getText().toString(), findCurrentArray(lastButtonPressed));
            }
            adapter = new CarAdapter(getActivity(), cars);
            recyclerView.setAdapter(adapter);
            return false;
        });
    }

    private void setListenerForFilterButton(BottomSheetDialog bottomSheetDialog){
        // set the listener for the filter button
        OpenBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.modal_bottom_sheet,
                                (LinearLayout) getActivity().findViewById(R.id.modalBottomSheetContainer));
                List<Button> clickedButtons = new ArrayList<>();
                List<Button> fuelTypeButtons = new ArrayList<>();
                lastFuelTypeButtonPressed = null;

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
                Button btn_auto = bottomSheetView.findViewById(R.id.btn_automatic);
                Button btn_manual = bottomSheetView.findViewById(R.id.btn_manual);

                Button btn_diesel = bottomSheetView.findViewById(R.id.btn_diesel);
                Button btn_petrol = bottomSheetView.findViewById(R.id.btn_petrol);
                Button btn_electric = bottomSheetView.findViewById(R.id.btn_electric);
                Button btn_hybrid = bottomSheetView.findViewById(R.id.btn_hybrid);

                Button btn_apply = bottomSheetView.findViewById(R.id.btn_apply);
                Button btn_reset = bottomSheetView.findViewById(R.id.btn_reset);

                EditText priceFrom = bottomSheetView.findViewById(R.id.editTextNumber_priceFrom);
                EditText priceTo = bottomSheetView.findViewById(R.id.editTextNumber_priceTo);

                Spinner spin = bottomSheetView.findViewById(R.id.fromPrice);
                spin.setOnItemSelectedListener(CarMenuFragment.this);

                fuelTypeButtons.add(btn_diesel);
                fuelTypeButtons.add(btn_petrol);
                fuelTypeButtons.add(btn_electric);
                fuelTypeButtons.add(btn_hybrid);


                ArrayAdapter ad = new ArrayAdapter(getActivity(),
                        android.R.layout.simple_spinner_item, RangeMilages);

                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(ad);

                btn_apply.setOnClickListener(v1 -> {
                    ProgressBar pb = (ProgressBar) bottomSheetView.findViewById(R.id.progressBar);
                    prog(pb);

                    // check the validity of the price range
                    if (!checkPriceRangeValidity(priceFrom, priceTo)){
                        return;
                    }

                    // find the current array based on the last button pressed
                    List<Car> currentArray = findCurrentArray(lastButtonPressed);
                    filteredCars.clear();
                    filteredCars = applyFilter(currentArray, clickedButtons, spin, priceFrom, priceTo);
                    filterApplied = true;
                    searchField.setHint("Search in filtered cars");

                    adapter = new CarAdapter(getActivity(), filteredCars);
                    recyclerView.setAdapter(adapter);

                    bottomSheetDialog.dismiss();
                });

                for (Button button : fuelTypeButtons) {
                    button.setOnClickListener(v12 -> {
                        if(lastFuelTypeButtonPressed!=null && !lastFuelTypeButtonPressed.equals(button)){
                            checkClickedButtons(clickedButtons,lastFuelTypeButtonPressed);
                        }
                        checkClickedButtons(clickedButtons,button);
                        lastFuelTypeButtonPressed = button;
                    });
                }

                btn_auto.setOnClickListener(v12 -> {
                    if(clickedButtons.contains(btn_manual)){
                        makeTextBlackMakeBackgroundWhite(btn_manual);
                        clickedButtons.remove(btn_manual);
                    }
                    checkClickedButtons(clickedButtons,btn_auto);

                });


                btn_manual.setOnClickListener(v13 -> {
                    if(clickedButtons.contains(btn_auto)){
                        makeTextBlackMakeBackgroundWhite(btn_auto);
                        clickedButtons.remove(btn_auto);
                    }
                    checkClickedButtons(clickedButtons,btn_manual);
                });

                btn_reset.setOnClickListener(v18 -> {
                    for(int i=0;i<clickedButtons.size();i++){
                        makeTextBlackMakeBackgroundWhite(clickedButtons.get(i));
                    }
                    clickedButtons.clear();
                });
            }
        });
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}