package com.example.hw9;

public class data_current_trans {

    private double temperature;
    private double humidity;
    private double windSpeed;
    private double visibility;
    private double pressure;
    private double ozone;
    private double precipitation;
    private String summary;
    private int icon;


    public void setTemperature(double temperature){
        this.temperature = temperature;
    }

    public void setHumidity(double humidity){
        this.humidity = humidity;
    }

    public void setWindSpeed(double windSpeed){
        this.windSpeed = windSpeed;
    }

    public void setVisibility(double visibility){
        this.visibility = visibility;
    }

    public void setPressure(double pressure){
        this.pressure = pressure;
    }

    public void setOzone(double ozone){
        this.ozone = ozone;
    }

    public void setPrecipitation(double precipitation){
        this.precipitation = precipitation;
    }

    public void setSummary(String summary){
        this.summary = summary;
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

    public double getTemperature(){
        return temperature;
    }

    public double getHumidity(){
        return humidity;
    }

    public double getWindSpeed(){
        return windSpeed;
    }

    public double getVisibility(){
        return visibility;
    }

    public double getPressure(){
        return pressure;
    }

    public double getOzone(){
        return ozone;
    }

    public double getPrecipitation(){
        return precipitation;
    }

    public String getSummary(){
        return summary;
    }

    public int getIcon(){
        return icon;
    }


}
