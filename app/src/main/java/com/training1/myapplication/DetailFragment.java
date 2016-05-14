package com.training1.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.training1.myapplication.Model.City;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private View view;
    private City city;
    private TextView cityName;
    private TextView degree;
    private TextView weather;
    private TextView feelsLike;
    private TextView humidity;
    private TextView windDirection;
    private TextView windSpeed;


    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DetailFragment", "Reached");

        city =(City) getActivity().getIntent().getExtras().get("city");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_detail,container,false);
        cityName =(TextView) view.findViewById(R.id.city);
        degree = (TextView) view.findViewById(R.id.degree);
        weather =(TextView) view.findViewById(R.id.weather);
        feelsLike =(TextView) view.findViewById(R.id.feelsLike);
        humidity =(TextView) view.findViewById(R.id.humidity);
        windDirection =(TextView) view.findViewById(R.id.windDirection);
        windSpeed =(TextView) view.findViewById(R.id.windspeed);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cityName.setText(city.getCityName());
        degree.setText(city.getDegree());
        weather.setText(city.getWeather());
        feelsLike.setText(city.getFeelsLike());
        humidity.setText(city.getHumidity());
        windDirection.setText(city.getWindDirection());
        windSpeed.setText(city.getWindSpeed());
    }
}
