package com.training1.iMobile3.Presentation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.training1.iMobile3.Model.City;
import com.training1.iMobile3.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private View mView;
    private City mCity;
    private TextView mCityName;
    private TextView mDegree;
    private TextView mWeather;
    private TextView mFeelsLike;
    private TextView mHumidity;
    private TextView mWindDirection;
    private TextView mWindSpeed;

    private CardView mCardView;
    private TextView mWeatherH;
    private TextView mFeelsLikeH;
    private TextView mHumidityH;
    private TextView mWindDirectionH;
    private TextView mWindSpeedH;
    private TextView mNowH;


    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DetailFragment", "Reached");

        mCity =(City) getActivity().getIntent().getExtras().get("city");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_detail,container,false);

        initilisation(mView);

        return mView;
    }

    private void initilisation(View view){

        mCityName =(TextView) view.findViewById(R.id.city);
        mDegree = (TextView) view.findViewById(R.id.degree);
        mWeather =(TextView) view.findViewById(R.id.weather);
        mFeelsLike =(TextView) view.findViewById(R.id.feelsLike);
        mHumidity =(TextView) view.findViewById(R.id.humidity);
        mWindDirection =(TextView) view.findViewById(R.id.windDirection);
        mWindSpeed =(TextView) view.findViewById(R.id.windspeed);

        mCardView = (CardView) view.findViewById(R.id.card_view);
        mNowH=(TextView)view.findViewById(R.id.now);
        mWeatherH =(TextView) view.findViewById(R.id.weatherH);
        mFeelsLikeH =(TextView) view.findViewById(R.id.feelsLikeH);
        mHumidityH =(TextView) view.findViewById(R.id.humidityH);
        mWindDirectionH =(TextView) view.findViewById(R.id.windDirectionH);
        mWindSpeedH =(TextView) view.findViewById(R.id.windspeedH);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mCity!=null) {

            mCardView.setVisibility(View.VISIBLE);
            mWeatherH.setVisibility(View.VISIBLE);
            mFeelsLikeH.setVisibility(View.VISIBLE);
            mHumidityH.setVisibility(View.VISIBLE);
            mWindDirectionH.setVisibility(View.VISIBLE);
            mWindSpeedH.setVisibility(View.VISIBLE);
            mNowH.setVisibility(View.VISIBLE);

            mCityName.setText(mCity.getCityName());
            mDegree.setText(mCity.getDegree());
            mWeather.setText(mCity.getWeather());
            mFeelsLike.setText(mCity.getFeelsLike());
            mHumidity.setText(mCity.getHumidity());
            mWindDirection.setText(mCity.getWindDirection());
            mWindSpeed.setText(mCity.getWindSpeed());
        }
    }
}
