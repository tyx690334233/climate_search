package com.example.hw9;

public class data_current {

    private double temperature;
    private double humidity;
    private double windSpeed;
    private double visibility;
    private double pressure;
    private double ozone;
    private double precipitation;
    private String summary;
    private String img;

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

    public String getImg(){
        return img;
    }

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

    public void setImg(String img){
        this.img = img;
    }

}
