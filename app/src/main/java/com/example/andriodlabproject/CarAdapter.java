package com.example.andriodlabproject;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<Car> carList;
    private List<User> userList;
    private LayoutInflater inflater;
    private final Context context;


    public CarAdapter(Context context, List<Car> carList) {
        this.inflater = LayoutInflater.from(context);
        this.carList = carList;
        this.context = context;
    }

    public CarAdapter(Context context, List<Car> carList, List<User> userList) {
        this.inflater = LayoutInflater.from(context);
        this.carList = carList;
        this.userList = userList;
        this.context = context;
    }


    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.car_item, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        Car currentCar = carList.get(position);
        DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)inflater.getContext()).getDatabaseHelper();
        holder.carName.setText(currentCar.getFactoryName()+" "+currentCar.getType());
        holder.carPrice.setText(currentCar.getPrice());
        holder.carImage.setImageResource(currentCar.getImgCar());
        holder.imgFav.setImageResource(currentCar.getImgFavButton());
        holder.reserve.setVisibility(currentCar.getVisibleReserveButton());
        holder.viewDate.setVisibility(currentCar.getVisibleDate());
        holder.viewDate.setText(currentCar.getDate());
        if (dataBaseHelper.isReserved(currentCar.getID())){
            holder.reserve.setText("Leave a Review");
        }
        else{
            holder.reserve.setText("Reserve");
        }
        if(context instanceof HomeAdminActivity){
            // remove the favorite button and space from the admin view
            holder.favLayout.removeView(holder.imgFav);
            holder.favLayout.removeView(holder.space_carItem);

            // remove the reserve button from the admin view
            holder.mainLayout.removeView(holder.reserve);
            holder.mainLayout.removeView(holder.carInfoLayout);

            User currentUser = userList.get(position);

            // split the date and time
            String[] dateTime = currentCar.getDate().split("T");
            String date = dateTime[0];
            String time = dateTime[1];

            TextView textView = new TextView(holder.itemView.getContext());
            TextView textView2 = new TextView(holder.itemView.getContext());
            String text = currentCar.getFactoryName()+" "+currentCar.getType()+"\n"+currentCar.getPrice() +"\n"+currentCar.getDealerName();
            String text2 = "Reserved by:\n"+currentUser.getFirstName()+" "+currentUser.getLastName()+"\n"+currentUser.getEmail()+"\n"+date+"\n"+time;

            LinearLayout linearLayout = new LinearLayout(holder.itemView.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(20, 20, 20, 20);


            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(15);
            textView.setText(text);

            textView2.setTextColor(Color.BLACK);
            textView2.setTextSize(15);
            textView2.setText(text2);

            linearLayout.addView(textView);
            linearLayout.addView(textView2);

            holder.favLayout.removeView(linearLayout);
            holder.favLayout.addView(linearLayout);

        }

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
        private LinearLayout mainLayout;
        private LinearLayout favLayout;
        private Space space_carItem;
        private LinearLayout carInfoLayout;
        public CarViewHolder(View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.imgCar);
            carName = itemView.findViewById(R.id.carType);
            carPrice = itemView.findViewById(R.id.carPrice);
            imgFav = itemView.findViewById(R.id.imgFav);
            reserve=itemView.findViewById(R.id.button_reserve);
            viewDate=itemView.findViewById(R.id.viewDate);
            mainLayout=itemView.findViewById(R.id.linear_carItem);
            favLayout=itemView.findViewById(R.id.favLayout);
            space_carItem=itemView.findViewById(R.id.space_carItem);
            carInfoLayout=itemView.findViewById(R.id.carInfoLayout);

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

                        }
                        else{
                            imgFav.setImageResource(R.drawable.ic_favorite);
                            CarMenuFragment.makeFavouriteAlertAnimation("Added to Favourites");
                            currentCar.setImgFavButton(R.drawable.ic_favorite);
                            // add the car to the favorite cars list in the database
                            addCarToFavorites(currentCar.getID());


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

                        StringBuilder details = addDetails(currentCar);

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
                    if(reserve.getText().toString().equals("Leave a Review")){
                        reviewCar();
                        return;
                    }

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Car currentCar = carList.get(position);

                        StringBuilder details = addDetails(currentCar);

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Car Details")
                                .setMessage(details.toString())
                                .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Car currentCar0 = new Car() ;
                                        currentCar0.setVisibleReserveButton(View.VISIBLE);
                                        currentCar0.setID(currentCar.getID());
                                        currentCar0.setType(currentCar.getType());
                                        currentCar0.setFactoryName(currentCar.getFactoryName()+" ");
                                        currentCar0.setImgCar(currentCar.getImgCar());
                                        currentCar0.setPrice(currentCar.getPrice());
                                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                        LocalDateTime now = LocalDateTime.now();
                                        currentCar0.setVisibleDate(View.VISIBLE);
                                        currentCar0.setDate(dtf.format(now));
                                        currentCar0.setDealerID(currentCar.getDealerID());
                                        currentCar0.setDealerName(currentCar.getDealerName());
                                        CarMenuFragment.makeFavouriteAlertAnimation("Car has been reserved successfully");
                                        notifyDataSetChanged();
                                        // add reservation to database
                                        addReservationToDatabase(currentCar, now);

                                        // remove the car from the list of all cars
                                        HomeNormalCustomerActivity.allCars.remove(currentCar);
                                        // remove the car from the factory list
                                        removeCarFromFactory(currentCar);
                                        // remove the car from the favorite cars list for all users
                                        removeCarFromAllUsersFavorites(currentCar);
                                        // remove the car from the list of favorite cars
                                        HomeNormalCustomerActivity.favCars.remove(currentCar);
                                        // add the car to the list of reserved cars
                                        HomeNormalCustomerActivity.reserveCars.add(currentCar);
                                        notifyDataSetChanged();
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

        // function to add the car details to the alert dialog
        public StringBuilder addDetails(Car currentCar){
            StringBuilder details = new StringBuilder();
            details.append("ID:").append(currentCar.getID()).append("\n");
            details.append("Type: ").append(currentCar.getType()).append("\n");
            details.append("Price: ").append(currentCar.getPrice()).append("\n");
            details.append("Fuel Type: ").append(currentCar.getFuelType()).append("\n");
            details.append("Mileage: ").append(currentCar.getMileage()).append("\n");
            details.append("Transmission Type: ").append(currentCar.getTransmission()).append("\n");
            details.append("Dealer ID: ").append(currentCar.getDealerID()).append("\n");
            details.append("Dealer Name: ").append(currentCar.getDealerName()).append("\n");
            return details;
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

        // function to remove a car from the favorite cars list for all users
        public void removeCarFromAllUsersFavorites(Car car){
            DataBaseHelper dataBaseHelper = ((HomeNormalCustomerActivity)inflater.getContext()).getDatabaseHelper();
            dataBaseHelper.deleteCarFromAllFavorites(car.getID());
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
    public void reviewCar(){
        View bottomSheetView = LayoutInflater.from(((HomeNormalCustomerActivity)inflater.getContext()))
                .inflate(R.layout.review_car_bottom_sheet,
                        (LinearLayout) ((HomeNormalCustomerActivity)inflater.getContext()).findViewById(R.id.modalBottomSheetContainer));
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(((HomeNormalCustomerActivity)inflater.getContext()), R.style.BottomSheetDialogTheme);
        Button btn_submit = bottomSheetView.findViewById(R.id.btn_submit);
        Button btn_cancel = bottomSheetView.findViewById(R.id.btn_cancel);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                Toast.makeText(context, "Review Submitted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        //
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

}
