package com.example.weatherapp.DataRequest;

import java.util.List;

//this is for the data transfer in background
public class FavoriteCityWeatherDTO {
    private double latitude;
    private double longitude;
    private int summaryIcon;
    private String rawSummaryIcon;
    private String currentlySummary;
    private String dailySummary;
    private int dailyIcon;
    private float temperature;

    private String cityName;
    private String stateName;
    private String CountryName;
    private float humidity;
    private float windSpeed;
    private float Visablility;
    private float pressure;
    private float precipitation;
    private float cloudCover;
    private float ozone;
    private String timeZone;

    private List<DailyWeatherDTO> dailyWeatherDTOList;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getSummaryIcon() {
        return summaryIcon;
    }

    public void setSummaryIcon(int summaryIcon) {
        this.summaryIcon = summaryIcon;
    }

    public String getRawSummaryIcon() {
        return rawSummaryIcon;
    }

    public void setRawSummaryIcon(String rawSummaryIcon) {
        this.rawSummaryIcon = rawSummaryIcon;
    }

    public String getCurrentlySummary() {
        return currentlySummary;
    }

    public void setCurrentlySummary(String currentlySummary) {
        this.currentlySummary = currentlySummary;
    }

    public String getDailySummary() {
        return dailySummary;
    }

    public void setDailySummary(String dailySummary) {
        this.dailySummary = dailySummary;
    }

    public int getDailyIcon() {
        return dailyIcon;
    }

    public void setDailyIcon(int dailyIcon) {
        this.dailyIcon = dailyIcon;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getVisablility() {
        return Visablility;
    }

    public void setVisablility(float visablility) {
        Visablility = visablility;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public float getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(float cloudCover) {
        this.cloudCover = cloudCover;
    }

    public float getOzone() {
        return ozone;
    }

    public void setOzone(float ozone) {
        this.ozone = ozone;
    }

    public List<DailyWeatherDTO> getDailyWeatherDTOList() {
        return dailyWeatherDTOList;
    }

    public void setDailyWeatherDTOList(List<DailyWeatherDTO> dailyWeatherDTOList) {
        this.dailyWeatherDTOList = dailyWeatherDTOList;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
