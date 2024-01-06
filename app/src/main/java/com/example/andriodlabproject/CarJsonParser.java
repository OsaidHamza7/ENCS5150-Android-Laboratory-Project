package com.example.andriodlabproject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarJsonParser {
    public static List<Car> CARS;

    public static List<Car> getObjectFromJson(String json) {
        Map<String, List<String>> cars = new HashMap<>();

        cars.put("Chevrolet", Arrays.asList("Volt","Alpine"));
        cars.put("Jeep", Arrays.asList("Grand Cherokee", "Wrangler"));
        cars.put("Ford", Arrays.asList("Fiesta", "Mustang", "Fiesta", "Expedition"));
        cars.put("Dodge", Arrays.asList("Grand Caravan"));
        cars.put("Lamborghini", Arrays.asList("Aventador", "Mercielago", "Countach"));
        cars.put("Tesla", Arrays.asList("Model 3", "Model Y", "Model S", "Roadster"));
        cars.put("Honda", Arrays.asList("Accord", "Civic", "Element"));
        cars.put("Toyota", Arrays.asList("Prius"));
        cars.put("Koenigsegg", Arrays.asList("1"));

        //To print the map, you can iterate over it
        try {
            JSONArray jsonArray = new JSONArray(json);
            CARS = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Car CAR = new Car();
                String TYPE =jsonObject.getString("type");
                CAR.setID(jsonObject.getInt("id"));
                CAR.setPrice("$974,000");
                CAR.setImageFavResourceId(R.drawable.ic_favorite_border);
                for (Map.Entry<String, List<String>> entry : cars.entrySet()) {

                    if (entry.getKey().equals("Chevrolet") && entry.getValue().contains(TYPE)){
                        CAR.setImageResourceId(R.drawable.pngegg);
                        CAR.setType("Chevrolet"+" "+TYPE);
                        HomeNormalCustomerActivity.chevroletCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Ford") && entry.getValue().contains(TYPE)){
                        CAR.setImageResourceId(R.drawable.pngwingg);
                        CAR.setType("Ford"+" "+TYPE);
                        HomeNormalCustomerActivity.fordCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Jeep") && entry.getValue().contains(TYPE)){
                        CAR.setImageResourceId(R.drawable.pngwing);
                        CAR.setType("Jeep"+" "+TYPE);
                        HomeNormalCustomerActivity.jeepCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Dodge") && entry.getValue().contains(TYPE)){
                        CAR.setImageResourceId(R.drawable.pngwinggg);
                        CAR.setType("Dodge"+" "+TYPE);
                        HomeNormalCustomerActivity.dodgeCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Lamborghini") && entry.getValue().contains(TYPE)){
                        CAR.setImageResourceId(R.drawable.pngegg);
                        CAR.setType("Lamborghini"+" "+TYPE);
                        HomeNormalCustomerActivity.lamborghiniCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Tesla") && entry.getValue().contains(TYPE)){
                        CAR.setImageResourceId(R.drawable.pngwingg);
                        CAR.setType("Tesla"+" "+TYPE);
                        HomeNormalCustomerActivity.teslaCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Honda") && entry.getValue().contains(TYPE)){
                        CAR.setImageResourceId(R.drawable.pngwing);
                        CAR.setType("Honda"+" "+TYPE);
                        HomeNormalCustomerActivity.hondaCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Toyota") && entry.getValue().contains(TYPE)){
                        CAR.setImageResourceId(R.drawable.pngwinggg);
                        CAR.setType("Toyota"+" "+TYPE);
                        HomeNormalCustomerActivity.toyotaCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Koenigsegg") && entry.getValue().contains(TYPE)){
                        CAR.setImageResourceId(R.drawable.pngegg);
                        CAR.setType("Koenigsegg"+" "+TYPE);
                        HomeNormalCustomerActivity.koenigseggCars.add(CAR);
                        break;
                    }


                }

                CARS.add(CAR);

            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return CARS;
    }


}
