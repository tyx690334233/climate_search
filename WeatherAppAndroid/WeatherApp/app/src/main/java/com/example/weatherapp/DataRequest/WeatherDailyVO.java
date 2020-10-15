package com.example.weatherapp.DataRequest;

import java.util.List;

public class WeatherDailyVO {
    private String summary;
    private String icon;
    private List<WeatherDailyDataVO> data;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<WeatherDailyDataVO> getData() {
        return data;
    }

    public void setData(List<WeatherDailyDataVO> data) {
        this.data = data;
    }
}
