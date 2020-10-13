package com.example.hw9;

import java.util.List;

public class data_daily_trans {

    List<data_list_structure> list;
    public int icon;
    public String summary;

    public void setList(List<data_list_structure> list){
        this.list = list;
    }

    public void setIcon(String icon){
        switch (icon){
            case "clear-day": this.icon = R.drawable.weather_sunny;break;
            case "clear-night": this.icon = R.drawable.weather_night;break;
            case "rain": this.icon = R.drawable.weather_rainy_white;break;
            case "sleet": this.icon = R.drawable.weather_snowy_rainy_white;break;
            case "snow": this.icon = R.drawable.weather_snowy_white;break;
            case "wind": this.icon = R.drawable.weather_windy_icon_white;break;
            case "fog": this.icon = R.drawable.weather_fog_white;break;
            case "cloudy": this.icon = R.drawable.weather_cloudy;break;
            case "partly-cloudy-night": this.icon = R.drawable.weather_night_partly_cloudy;break;
            case "partly-cloudy-day": this.icon = R.drawable.weather_partly_cloudy;break;
            default: this.icon = R.drawable.weather_sunny;
        }
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public List<data_list_structure> getList() {
        return list;
    }

    public int getIcon() {
        return icon;
    }

    public String getSummary() {
        return summary;
    }
}
