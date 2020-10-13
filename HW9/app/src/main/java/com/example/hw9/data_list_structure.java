package com.example.hw9;

public class data_list_structure {

    public int time;
    public int icon_img;
    public String icon;
    public double temperHight;
    public double temperLow;

    public data_list_structure(){

    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public void setIcon_img(String icon_img) {
        switch (icon){
            case "clear-day": this.icon_img = R.drawable.weather_sunny;break;
            case "clear-night": this.icon_img = R.drawable.weather_night;break;
            case "rain": this.icon_img = R.drawable.weather_rainy_white;break;
            case "sleet": this.icon_img = R.drawable.weather_snowy_rainy_white;break;
            case "snow": this.icon_img = R.drawable.weather_snowy_white;break;
            case "wind": this.icon_img = R.drawable.weather_windy_icon_white;break;
            case "fog": this.icon_img = R.drawable.weather_fog_white;break;
            case "cloudy": this.icon_img = R.drawable.weather_cloudy;break;
            case "partly-cloudy-night": this.icon_img = R.drawable.weather_night_partly_cloudy;break;
            case "partly-cloudy-day": this.icon_img = R.drawable.weather_partly_cloudy;break;
            default: this.icon_img = R.drawable.weather_sunny;
        }
    }

    public void setTemperHight(double temperHight) {
        this.temperHight = Math.round(temperHight);
    }

    public void setTemperLow(double temperLow) {
        this.temperLow = temperLow;
    }

    public double getTemperHight() {
        return temperHight;
    }

    public double getTemperLow() {
        return temperLow;
    }

    public int getTime() {
        return time;
    }

    public int getIcon_img() {
        return icon_img;
    }

    public String getIcon() {
        return icon;
    }

    public data_list_structure(int time, String icon, double temperHight, double temperLow){
        this.time = time;
        this.icon = icon;
        this.temperHight = temperHight;
        this.temperLow = temperLow;
    }
}
