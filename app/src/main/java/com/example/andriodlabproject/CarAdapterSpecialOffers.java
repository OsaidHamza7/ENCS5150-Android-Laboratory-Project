package com.example.andriodlabproject;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarAdapterSpecialOffers extends RecyclerView.Adapter<CarAdapterSpecialOffers.CarViewHolder> {

    private List<Car> carSpecialOffers;
    private LayoutInflater inflater;

    public CarAdapterSpecialOffers(Context context, List<Car> carList) {
        this.inflater = LayoutInflater.from(context);
        this.carSpecialOffers = carList;
    }


    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.car_special_offer, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        Car currentCar = carSpecialOffers.get(position);

        //holder.carName.setText(currentCar.getFactoryName()+currentCar.getType());
        holder.carImage.setImageResource(currentCar.getImgCar());
        holder.imgFav.setImageResource(currentCar.getImgFavButton());
        //holder.reserve.setVisibility(currentCar.getVisibleReserveButton());
        holder.offer.setText(currentCar.getOffer());


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
        private TextView offer;

        public CarViewHolder(View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.imgCarView);
            //carName = itemView.findViewById(R.id.carType);
            imgFav = itemView.findViewById(R.id.imgFavView);
           // reserve=itemView.findViewById(R.id.buttonReserve);
            offer=itemView.findViewById(R.id.offer);



        }


    }
}
