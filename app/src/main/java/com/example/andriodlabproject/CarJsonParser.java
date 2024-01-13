package com.example.andriodlabproject;


import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CarJsonParser {
    public static List<Car> CARS;
    static Random random = new Random();
    static List<String> fuelTypes = Arrays.asList("Petrol", "Diesel", "Electric", "Hybrid");
    static List<String> transmissionTypes = Arrays.asList("Automatic", "Manual");


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
                CAR.setType(TYPE);
                int randomPrice = 100000 + random.nextInt(200001); // Random value between 100,000 and 300,000
                int randomOffer = 10 + random.nextInt(71);
                CAR.setOffer(randomOffer+"%");
                CAR.setPrice("$"+String.valueOf(randomPrice));
                String randomFuelType = fuelTypes.get(random.nextInt(fuelTypes.size()));
                CAR.setFuelType(randomFuelType);
                int randomMileage = random.nextInt(200001); // Random value between 0 and 200,000
                CAR.setMileage(String.valueOf(randomMileage));
                String randomTransmissionType = transmissionTypes.get(random.nextInt(transmissionTypes.size()));
                CAR.setTransmission(randomTransmissionType);
                CAR.setImgFavButton(R.drawable.ic_favorite_border);

                // check the factory name and set the image of the car
                for (Map.Entry<String, List<String>> entry : cars.entrySet()) {
                    if (entry.getValue().contains(TYPE)) {
                        CAR.setFactoryName(entry.getKey());
                        switch (entry.getKey()) {
                            case "Chevrolet":
                                CAR.setImgCar(R.drawable.pngegg);
                                CAR.setFactoryName("Chevrolet");
                                break;
                            case "Jeep":
                                CAR.setImgCar(R.drawable.pngwing);
                                CAR.setFactoryName("Jeep");
                                break;
                            case "Ford":
                                CAR.setImgCar(R.drawable.pngwingg);
                                CAR.setFactoryName("Ford");
                                break;
                            case "Dodge":
                                CAR.setImgCar(R.drawable.pngwinggg);
                                CAR.setFactoryName("Dodge");
                                break;
                            case "Lamborghini":
                                CAR.setImgCar(R.drawable.pngegg);
                                CAR.setFactoryName("Lamborghini");
                                break;
                            case "Tesla":
                                CAR.setImgCar(R.drawable.pngwingg);
                                CAR.setFactoryName("Tesla");
                                break;
                            case "Honda":
                                CAR.setImgCar(R.drawable.pngwinggg);
                                CAR.setFactoryName("Honda");
                                break;
                            case "Toyota":
                                CAR.setImgCar(R.drawable.pngegg);
                                CAR.setFactoryName("Toyota");
                                break;
                            case "Koenigsegg":
                                CAR.setImgCar(R.drawable.pngwingg);
                                CAR.setFactoryName("Koenigsegg");
                                break;
                        }
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
