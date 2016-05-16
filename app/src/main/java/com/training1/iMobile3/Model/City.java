package com.training1.iMobile3.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RishiS on 5/8/2016.
 */
public class City implements Parcelable {

    private String cityName="";
    private String degree="";
    private String time="";
    private String weather="";
    private String feelsLike="";
    private String humidity="";
    private String windSpeed="";
    private String windDirection="";
    private String imageUrl="";

    public City(){

    }

    public City(Parcel in){
        readFromParcel(in);
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityName);
        dest.writeString(degree);
        dest.writeString(time);
        dest.writeString(weather);
        dest.writeString(feelsLike);
        dest.writeString(windSpeed);
        dest.writeString(windDirection);
        dest.writeString(humidity);
        dest.writeString(imageUrl);
    }
    private void readFromParcel(Parcel in) {

        cityName = in.readString();
        degree = in.readString();
        time = in.readString();
        weather = in.readString();
        feelsLike= in.readString();
        windSpeed= in.readString();
        windDirection= in.readString();
        humidity= in.readString();
        imageUrl = in.readString();
    }
}
