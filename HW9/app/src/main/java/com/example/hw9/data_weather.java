package com.example.hw9;

public class data_weather {

    public double lat;
    public double lng;
    public String city;
    public String state;
    public String country;
    public String countryCode;

    public data_current current;
    public data_current_trans current_trans;
    public data_daily daily;
    public data_daily_trans daily_trans;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode(){
        return countryCode;
    }


    public data_current getCurrent() {
        return current;
    }

    public data_current_trans getCurrent_trans() {
        return current_trans;
    }

    public data_daily getDaily() {
        return daily;
    }

    public data_daily_trans getDaily_trans() {
        return daily_trans;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCurrent(data_current current) {
        this.current = current;
    }

    public void setCurrent_trans(data_current_trans current_trans) {
        this.current_trans = current_trans;
    }

    public void setDaily(data_daily daily) {
        this.daily = daily;
    }

    public void setDaily_trans(data_daily_trans daily_trans) {
        this.daily_trans = daily_trans;
    }
}
