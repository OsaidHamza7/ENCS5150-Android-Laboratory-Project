package com.example.andriodlabproject;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String,
        String> {
    private WeakReference<Activity> weakActivity;
    public static boolean isConnectionSuccessful = true;
    private AsyncTaskCallback callback;

    public ConnectionAsyncTask(Activity activity, AsyncTaskCallback callback) {
        this.weakActivity = new WeakReference<>(activity);
        this.callback = callback;

    }
    @Override
    protected String doInBackground(String... params) {
        try {
            return HttpManager.getData(params[0]);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Activity activity = weakActivity.get();
        if (activity == null || activity.isFinishing()) return;

        if (s == null) {
            Toast.makeText(activity, "the connection is unsuccessful", Toast.LENGTH_LONG).show();
            callback.onTaskComplete(false);
        }
        else {
            Toast.makeText(activity, "the connection is successful", Toast.LENGTH_LONG).show();
            List<Car> cars = CarJsonParser.getObjectFromJson(s);
            callback.onTaskComplete(true);

            DataBaseHelper dataBaseHelper = ((MainActivity)activity).getDatabaseHelper();
            // insert all cars into the database
            for (Car car : cars) {
                // check if the car is already in the database
                Cursor cursor = dataBaseHelper.getCarByID(car.getID());
                if (cursor.getCount() > 0)
                    continue;

                dataBaseHelper.insertCar(car);
            }

            // get all cars from the database
            Cursor cursor = dataBaseHelper.getAllCars();
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
                addCarToCategory(car);
                HomeNormalCustomerActivity.allCars.add(car);

            }


        }
    }

    void addCarToCategory(Car car) {
        switch (car.getFactoryName()) {
            case "Chevrolet":
                HomeNormalCustomerActivity.chevroletCars.add(car);
                break;
            case "Jeep":
                HomeNormalCustomerActivity.jeepCars.add(car);
                break;
            case "Ford":
                HomeNormalCustomerActivity.fordCars.add(car);
                break;
            case "Dodge":
                HomeNormalCustomerActivity.dodgeCars.add(car);
                break;
            case "Lamborghini":
                HomeNormalCustomerActivity.lamborghiniCars.add(car);
                break;
            case "Tesla":
                HomeNormalCustomerActivity.teslaCars.add(car);
                break;
            case "Honda":
                HomeNormalCustomerActivity.hondaCars.add(car);
                break;
            case "Toyota":
                HomeNormalCustomerActivity.toyotaCars.add(car);
                break;
            case "Koenigsegg":
                HomeNormalCustomerActivity.koenigseggCars.add(car);
                break;

        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


}