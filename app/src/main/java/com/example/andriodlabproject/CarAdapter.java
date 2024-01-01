package com.example.andriodlabproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;
    private LayoutInflater inflater;
    private List<Car> carsFavorites;

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
        holder.carType.setText(currentCar.getType());
        holder.carPrice.setText(currentCar.getPrice());
        holder.carImage.setImageResource(currentCar.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {
        private ImageView carImage;
        private TextView carType;
        private TextView carPrice;
        private ImageButton imgFav;
        public CarViewHolder(View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.imgCar);
            carType = itemView.findViewById(R.id.carType);
            carPrice = itemView.findViewById(R.id.carPrice);
            imgFav = itemView.findViewById(R.id.imgFav);

            imgFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle the click event for imgFav
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        imgFav.setImageResource(R.drawable.ic_favorite); // Set your favorite icon here
//                        car currentCar = carList.get(position);
//                        carsFavorites.add(currentCar);

                    }
                }
            });

        }


    }
}
