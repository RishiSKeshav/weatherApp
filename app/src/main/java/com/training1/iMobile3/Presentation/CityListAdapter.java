package com.training1.iMobile3.Presentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.training1.iMobile3.Model.City;
import com.training1.iMobile3.R;

import java.util.ArrayList;

/**
 * Created by RishiS on 5/13/2016.
 */
public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ListViewHolder> {

    private ArrayList<City> cities;
    private Context context;

    public CityListAdapter(ArrayList<City> cities,Context context){
        this.cities=cities;
        this.context=context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_city_item, null);
        return  new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {

        holder.city.setText(cities.get(position).getCityName());
        holder.degree.setText(cities.get(position).getDegree());
        holder.time.setText(cities.get(position).getTime());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("city",cities.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        private TextView city;
        private TextView degree;
        private TextView time;
        private CardView card_view;

        public ListViewHolder(View itemView) {
            super(itemView);

            city = (TextView) itemView.findViewById(R.id.cityName);
            degree = (TextView) itemView.findViewById(R.id.degree);
            time =(TextView) itemView.findViewById(R.id.time);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
