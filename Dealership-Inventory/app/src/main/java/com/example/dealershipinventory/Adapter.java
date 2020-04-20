package com.example.dealershipinventory;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> make;
    List<String> model;
    List<String> price;
    List<String> condition;
    List<String> year;
    List<String> mileage;
    List<String> color;

    public Adapter(List<String> make, List<String> model, List<String> price, List<String> condition, List<String> year, List<String> mileage, List<String> color)
    {
        this.make = make;
        this.model = model;
        this.price = price;
        this.condition = condition;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.make.setText(make.get(position));
        holder.model.setText(model.get(position));
        holder.price.setText(price.get(position));
//        final int code = getRandomColor();
//        holder.mCardView.setCardBackgroundColor(holder.view.getResources().getColor(code, null));
        
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CarDetails.class);
                i.putExtra("make", make.get(position));
                i.putExtra("model", model.get(position));
                i.putExtra("price", price.get(position));
                i.putExtra("condition", condition.get(position));
                i.putExtra("year", year.get(position));
                i.putExtra("mileage", mileage.get(position));
                i.putExtra("color", color.get(position));
                v.getContext().startActivity(i);
            }
        });
    }

//    private int getRandomColor() {
//        List<Integer> colorCode = new ArrayList<>();
//        colorCode.add(R.color.blue);
//        colorCode.add(R.color.yellow);
//        colorCode.add(R.color.skyblue);
//        colorCode.add(R.color.lightPurple);
//        colorCode.add(R.color.lightGreen);
//        colorCode.add(R.color.gray);
//        colorCode.add(R.color.pink);
//        colorCode.add(R.color.red);
//        colorCode.add(R.color.greenlight);
//        colorCode.add(R.color.notgreen);
//
//        Random randomColor = new Random();
//        int number = randomColor.nextInt(colorCode.size());
//        return colorCode.get(number);
//
//    }

    @Override
    public int getItemCount() {
        return make.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView make, model, price;
        View view;

        CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            make = itemView.findViewById(R.id.carMake);
            model = itemView.findViewById(R.id.carModel);
            price = itemView.findViewById(R.id.price);
            mCardView = itemView.findViewById(R.id.carCard);
            view = itemView;
        }
    }
}
