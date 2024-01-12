package com.example.andriodlabproject;


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
                for (Map.Entry<String, List<String>> entry : cars.entrySet()) {

                    if (entry.getKey().equals("Chevrolet") && entry.getValue().contains(TYPE)){
                        CAR.setImgCar(R.drawable.pngegg);
                        CAR.setFactoryName("Chevrolet");
                        HomeNormalCustomerActivity.chevroletCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Ford") && entry.getValue().contains(TYPE)){
                        CAR.setImgCar(R.drawable.pngwingg);
                        CAR.setFactoryName("Ford");
                        HomeNormalCustomerActivity.fordCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Jeep") && entry.getValue().contains(TYPE)){
                        CAR.setImgCar(R.drawable.pngwing);
                        CAR.setFactoryName("Jeep");
                        HomeNormalCustomerActivity.jeepCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Dodge") && entry.getValue().contains(TYPE)){
                        CAR.setImgCar(R.drawable.pngwinggg);
                        CAR.setFactoryName("Dodge");
                        HomeNormalCustomerActivity.dodgeCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Lamborghini") && entry.getValue().contains(TYPE)){
                        CAR.setImgCar(R.drawable.pngegg);
                        CAR.setFactoryName("Lamborghini");
                        HomeNormalCustomerActivity.lamborghiniCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Tesla") && entry.getValue().contains(TYPE)){
                        CAR.setImgCar(R.drawable.pngwingg);
                        CAR.setFactoryName("Tesla");
                        HomeNormalCustomerActivity.teslaCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Honda") && entry.getValue().contains(TYPE)){
                        CAR.setImgCar(R.drawable.pngwing);
                        CAR.setFactoryName("Honda");
                        HomeNormalCustomerActivity.hondaCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Toyota") && entry.getValue().contains(TYPE)){
                        CAR.setImgCar(R.drawable.pngwinggg);
                        CAR.setFactoryName("Toyota");
                        HomeNormalCustomerActivity.toyotaCars.add(CAR);
                        break;
                    }
                    if (entry.getKey().equals("Koenigsegg") && entry.getValue().contains(TYPE)){
                        CAR.setImgCar(R.drawable.pngegg);
                        CAR.setFactoryName("Koenigsegg");
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
