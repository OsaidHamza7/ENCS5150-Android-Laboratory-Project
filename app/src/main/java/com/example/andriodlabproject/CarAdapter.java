package com.example.andriodlabproject;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> carList;
    private LayoutInflater inflater;

    public CarAdapter(Context context, List<Car> carList) {
        this.inflater = LayoutInflater.from(context);
        this.carList = carList;
    }


    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        Car currentCar = carList.get(position);
        holder.carName.setText(currentCar.getFactoryName()+" "+currentCar.getType());
        holder.carPrice.setText(currentCar.getPrice());
        holder.carImage.setImageResource(currentCar.getImgCar());
        holder.imgFav.setImageResource(currentCar.getImgFavButton());
        holder.reserve.setVisibility(currentCar.getVisibleReserveButton());
        holder.viewDate.setVisibility(currentCar.getVisibleDate());
        holder.viewDate.setText(currentCar.getDate());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {
        private ImageView carImage;
        private TextView carName;
        private TextView carPrice;
        private TextView viewDate;
        private ImageButton imgFav;
        private Button reserve;
        public CarViewHolder(View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.imgCar);
            carName = itemView.findViewById(R.id.carType);
            carPrice = itemView.findViewById(R.id.carPrice);
            imgFav = itemView.findViewById(R.id.imgFav);
            reserve=itemView.findViewById(R.id.button_reserve);
            viewDate=itemView.findViewById(R.id.viewDate);
            imgFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle the click event for imgFav
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Car currentCar = carList.get(position);
                        if (currentCar.getImgFavButton()==R.drawable.ic_favorite) {
                            imgFav.setImageResource(R.drawable.ic_favorite_border);
                            CarMenuFragment.makeFavouriteAlertAnimation("Removed from Favourites");
                            currentCar.setImgFavButton(R.drawable.ic_favorite_border);
                            // remove the car from the favorite cars list in the database
                            removeCarFromFavorites(currentCar.getID());
                            if(HomeNormalCustomerActivity.favCars.contains(currentCar)) {
                                HomeNormalCustomerActivity.favCars.remove(currentCar);
                            }
                        }
                        else{
                            imgFav.setImageResource(R.drawable.ic_favorite);
                            CarMenuFragment.makeFavouriteAlertAnimation("Added to Favourites");
                            currentCar.setImgFavButton(R.drawable.ic_favorite);
                            // add the car to the favorite cars list in the database
                            addCarToFavorites(currentCar.getID());

                            if(!HomeNormalCustomerActivity.favCars.contains(currentCar)) {
                                HomeNormalCustomerActivity.favCars.add(currentCar);
                            }

                        }
                    }
                    notifyDataSetChanged();
                }
            });

            carImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle the click event for imgFav
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                     Car currentCar = carList.get(position);

                        StringBuilder details = new StringBuilder();
//                        details.append(currentCar.getImageResourceId()).append("\n");
                        details.append("ID: ").append(currentCar.getID()).append("\n");
                        details.append("Car Type: ").append(currentCar.getType()).append("\n");
                        details.append("Price: ").append(currentCar.getPrice()).append("\n");
                        details.append("Fuel Type: ").append(currentCar.getFuelType()).append("\n");
                        details.append("Mileage: ").append(currentCar.getMileage()).append("\n");
                        details.append("Transmission Type: ").append(currentCar.getTransmission()).append("\n");

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Car Details")
                                .setMessage(details.toString())
                                .setNegativeButton("CANCEL", null)
                                .create()
                                .show();


                    }
                }
            });

            reserve.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Car currentCar = carList.get(position);

                        StringBuilder details = new StringBuilder();
                        details.append("ID:").append(currentCar.getID()).append("\n");
                        details.append("Type: ").append(currentCar.getType()).append("\n");
                        details.append("Price: ").append(currentCar.getPrice()).append("\n");
                        details.append("Fuel Type: ").append(currentCar.getFuelType()).append("\n");
                        details.append("Mileage: ").append(currentCar.getMileage()).append("\n");
                        details.append("Transmission Type: ").append(currentCar.getTransmission()).append("\n");

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Car Details")
                                .setMessage(details.toString())
                                .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Car currentCar0 = new Car() ;

                                        currentCar0.setVisibleReserveButton(View.INVISIBLE);
                                        currentCar0.setID(currentCar.getID());
                                        currentCar0.setType(currentCar.getType());
                                        currentCar0.setFactoryName(currentCar.getFactoryName()+" ");
                                        currentCar0.setImgCar(currentCar.getImgCar());
                                        currentCar0.setPrice(currentCar.getPrice());
                                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                        LocalDateTime now = LocalDateTime.now();
                                        currentCar0.setVisibleDate(View.VISIBLE);
                                        currentCar0.setDate(dtf.format(now));
                                        CarMenuFragment.makeFavouriteAlertAnimation("Car has been reserved successfully");
                                        notifyDataSetChanged();
                                        // add reservation to database
                                        addReservationToDatabase(currentCar0, now);

                                        HomeNormalCustomerActivity.reserveCars.add(currentCar0);
                                    }
                                })
                                .setNegativeButton("CANCEL", null)
                                .create()
                                .show();

                    }
                    notifyDataSetChanged();

                }


            });

        }

        public void addReservationToDatabase(Car car, LocalDateTime now) {
            DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)inflater.getContext()).getDatabaseHelper();
            Reservation reservation = new Reservation();
            reservation.setUserEmail(User.currentUser.getString(3));
            reservation.setCarID(car.getID());
            reservation.setReservationDate(now.toString());
            reservation.setReservationTime(now.toString());
            dataBaseHelper.insertReservation(reservation);

        }

        // function to add the car to the favorite cars list in the database
        public void addCarToFavorites(int carID){

            DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)inflater.getContext()).getDatabaseHelper();
            if (!dataBaseHelper.isFavorite(User.currentUser.getString(3),carID)){
                dataBaseHelper.insertFavorite(User.currentUser.getString(3), carID);
            }

        }

        // function to remove the car from the favorite cars list in the database
        public void removeCarFromFavorites(int carID){

            DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)inflater.getContext()).getDatabaseHelper();
            if (dataBaseHelper.isFavorite(User.currentUser.getString(3),carID)){
                dataBaseHelper.deleteFavorite(User.currentUser.getString(3), carID);
            }

        }


    }


}
