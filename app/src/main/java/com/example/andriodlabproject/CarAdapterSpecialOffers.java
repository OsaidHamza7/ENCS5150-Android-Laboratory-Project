package com.example.andriodlabproject;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CarAdapterSpecialOffers extends RecyclerView.Adapter<CarAdapterSpecialOffers.CarViewHolder> {

    private List<Car> carSpecialOffers;
    private List<SpecialOffer> specialOffers;
    private LayoutInflater inflater;
    private DataBaseHelper dataBaseHelper;

    public CarAdapterSpecialOffers(Context context, List<Car> carList, List<SpecialOffer> specialOffers) {
        this.inflater = LayoutInflater.from(context);
        this.carSpecialOffers = carList;
        this.specialOffers = specialOffers;
    }


    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.car_special_offer, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        Car currentCar = carSpecialOffers.get(position);
        SpecialOffer currentSpecialOffer = specialOffers.get(position);

        holder.carImage.setImageResource(currentCar.getImgCar());
        holder.imgFav.setImageResource(currentCar.getImgFavButton());

        holder.carName.setText(currentCar.getFactoryName()+" "+currentCar.getType());
        // set the old price and add strike through it
        holder.oldPrice.setText(currentCar.getPrice());
        holder.oldPrice.setPaintFlags(holder.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.discount.setText(currentSpecialOffer.getDiscount());
        holder.endDate.setText(currentSpecialOffer.getEndDate());

        String newPrice = holder.getNewPrice(currentCar, currentSpecialOffer);
        holder.newPrice.setText("$" + newPrice);




    }

    @Override
    public int getItemCount() {
        return carSpecialOffers.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {
        private ImageView carImage;
        private TextView carName;
        private ImageButton imgFav;
        private Button reserve;
        private final TextView discount;
        private TextView endDate;
        private TextView oldPrice;
        private TextView newPrice;


        public CarViewHolder(View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.imgCarView);
            carName = itemView.findViewById(R.id.textView_name);
            imgFav = itemView.findViewById(R.id.imgFavView);
            reserve=itemView.findViewById(R.id.button_reserve);
            discount=itemView.findViewById(R.id.discount);

            endDate=itemView.findViewById(R.id.textView_endDate);
            oldPrice=itemView.findViewById(R.id.textView_oldPrice);
            newPrice=itemView.findViewById(R.id.text_newPrice);

            imgFav.setOnClickListener(view -> {
                // Handle the click event for imgFav
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Car currentCar = carSpecialOffers.get(position);
                    if (currentCar.getImgFavButton()==R.drawable.ic_favorite) {
                        imgFav.setImageResource(R.drawable.ic_favorite_border);
                        currentCar.setImgFavButton(R.drawable.ic_favorite_border);
                        // remove the car from the favorite cars list in the database
                        removeCarFromFavorites(currentCar.getID());

                    }
                    else{
                        imgFav.setImageResource(R.drawable.ic_favorite);
                        currentCar.setImgFavButton(R.drawable.ic_favorite);
                        // add the car to the favorite cars list in the database
                        addCarToFavorites(currentCar.getID());

                    }
                }
                notifyDataSetChanged();
            });

            carImage.setOnClickListener(view -> {
                // Handle the click event for imgFav
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Car currentCar = carSpecialOffers.get(position);

                    StringBuilder details = addDetails(currentCar, specialOffers.get(position));

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Car Details")
                            .setMessage(details.toString())
                            .setNegativeButton("CANCEL", null)
                            .create()
                            .show();


                }
            });


            reserve.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Car currentCar = carSpecialOffers.get(position);

                    StringBuilder details = addDetails(currentCar, specialOffers.get(position));

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Car Details")
                            .setMessage(details.toString())
                            .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Car currentCar0 = new Car() ;

                                    String newPrice = getNewPrice(currentCar, specialOffers.get(position));

                                    currentCar0.setVisibleReserveButton(View.INVISIBLE);
                                    currentCar0.setID(currentCar.getID());
                                    currentCar0.setType(currentCar.getType());
                                    currentCar0.setFactoryName(currentCar.getFactoryName()+" ");
                                    currentCar0.setImgCar(currentCar.getImgCar());
                                    currentCar0.setDealerID(currentCar.getDealerID());
                                    currentCar0.setDealerName(currentCar.getDealerName());
                                    currentCar0.setPrice("$"+newPrice);
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                    LocalDateTime now = LocalDateTime.now();
                                    currentCar0.setVisibleDate(View.VISIBLE);
                                    currentCar0.setDate(dtf.format(now));
                                    currentCar0.setIsOnOffer(true);

                                    notifyDataSetChanged();
                                    // add reservation to database
                                    addReservationToDatabase(currentCar0, now);

                                    // remove the car from the list of all cars
                                    HomeNormalCustomerActivity.allCars.remove(currentCar);
                                    // remove the car from the factory list
                                    removeCarFromFactory(currentCar);
                                    // remove the car from the favorite cars list for all users
                                    removeCarFromAllUsersFavorites(currentCar);
                                    // remove the car from the list of favorite cars
                                    HomeNormalCustomerActivity.favCars.remove(currentCar);
                                    // add the car to the list of reserved cars
                                    HomeNormalCustomerActivity.reserveCars.add(currentCar0);
                                    Toast.makeText(view.getContext(), "The car is reserved successfully", Toast.LENGTH_SHORT).show();

                                    // remove the car from the special offers list
                                    removeCarFromSpecialOffers(currentCar, specialOffers.get(position));
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("CANCEL", null)
                            .create()
                            .show();

                }
                notifyDataSetChanged();

            });





        }

        // function to remove the car from the special offers list and database
        public void removeCarFromSpecialOffers(Car car, SpecialOffer specialOffer){
            DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)inflater.getContext()).getDatabaseHelper();
            dataBaseHelper.deleteSpecialOfferByID(specialOffer.getID());
            SpecialOffersFragment.allSpecialOffers.remove(specialOffer);
            SpecialOffersFragment.allCars.remove(car);
        }


        // function to remove the car from the favorite cars list in the database
        public void removeCarFromFavorites(int carID){
            DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)inflater.getContext()).getDatabaseHelper();
            if (dataBaseHelper.isFavorite(User.currentUser.getString(3),carID)){
                dataBaseHelper.deleteFavorite(User.currentUser.getString(3), carID);
            }

        }

        // function to remove a car from the favorite cars list for all users
        public void removeCarFromAllUsersFavorites(Car car){
            DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)inflater.getContext()).getDatabaseHelper();
            dataBaseHelper.deleteCarFromAllFavorites(car.getID());
        }

        // function to add the car to the favorite cars list in the database.
        public void addCarToFavorites(int carID){

            DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)inflater.getContext()).getDatabaseHelper();
            if (!dataBaseHelper.isFavorite(User.currentUser.getString(3),carID)){
                dataBaseHelper.insertFavorite(User.currentUser.getString(3), carID);
            }

        }

        // function to add the reservation to the database
        public void addReservationToDatabase(Car car, LocalDateTime now) {
            DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)inflater.getContext()).getDatabaseHelper();
            Reservation reservation = new Reservation();
            reservation.setUserEmail(User.currentUser.getString(3));
            reservation.setCarID(car.getID());
            reservation.setReservationDate(now.toString());
            reservation.setReservationTime(now.toString());
            dataBaseHelper.insertReservation(reservation);

            // set the price of the car in the database to the new price
            dataBaseHelper.updateCarPrice(car.getID(), car.getPrice());

        }

        // function to add the car details to the alert dialog
        public StringBuilder addDetails(Car currentCar, SpecialOffer specialOffer){
            StringBuilder details = new StringBuilder();
            details.append("ID:").append(currentCar.getID()).append("\n");
            details.append("Factory Name: ").append(currentCar.getFactoryName()).append("\n");
            details.append("Type: ").append(currentCar.getType()).append("\n");

            details.append("Fuel Type: ").append(currentCar.getFuelType()).append("\n");
            details.append("Mileage: ").append(currentCar.getMileage()).append("\n");
            details.append("Transmission Type: ").append(currentCar.getTransmission()).append("\n");
            details.append("Car Rating: ").append(String.format("%.1f", currentCar.getRating())).append("(").append(currentCar.getRatingCount()).append(")").append("\n");
            details.append("Dealer ID: ").append(currentCar.getDealerID()).append("\n");
            details.append("Dealer Name: ").append(currentCar.getDealerName()).append("\n");

            String newPrice = getNewPrice(currentCar, specialOffer);

            details.append("Price: ").append("$").append(newPrice).append("\n");

            return details;
        }

        public String getNewPrice(Car currentCar, SpecialOffer specialOffer){
            // remove $ from price
            String price = currentCar.getPrice();
            price = price.substring(1, price.length());
            int priceInt = Integer.parseInt(price);
            // remove % from discount
            String discount = specialOffer.getDiscount();
            discount = discount.substring(0, discount.length() - 1);
            int discountInt = Integer.parseInt(discount);
            // calculate new price
            int newPriceInt = priceInt - (priceInt * discountInt / 100);
            String newPrice = Integer.toString(newPriceInt);
            return newPrice;
        }


        // function to remove the car from the factory list
        public void removeCarFromFactory(Car car){
            switch (car.getFactoryName()) {
                case "Chevrolet":
                    HomeNormalCustomerActivity.chevroletCars.remove(car);
                    break;
                case "Jeep":
                    HomeNormalCustomerActivity.jeepCars.remove(car);
                    break;
                case "Ford":
                    HomeNormalCustomerActivity.fordCars.remove(car);
                    break;
                case "Dodge":
                    HomeNormalCustomerActivity.dodgeCars.remove(car);
                    break;
                case "Lamborghini":
                    HomeNormalCustomerActivity.lamborghiniCars.remove(car);
                    break;
                case "Tesla":
                    HomeNormalCustomerActivity.teslaCars.remove(car);
                    break;
                case "Honda":
                    HomeNormalCustomerActivity.hondaCars.remove(car);
                    break;
                case "Toyota":
                    HomeNormalCustomerActivity.toyotaCars.remove(car);
                    break;
                case "Koenigsegg":
                    HomeNormalCustomerActivity.koenigseggCars.remove(car);
                    break;
            }
        }

    }
}
