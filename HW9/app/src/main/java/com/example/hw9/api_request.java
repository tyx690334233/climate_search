package com.example.hw9;

public class api_request {


    public static String current_url = "http://ip-api.com/json";
    public static String auto_url = "http://yuxintiancloudnodejs.us-east-2.elasticbeanstalk.com/find_similiar?";
    public static String weather_url = "http://yuxintiancloudnodejs.us-east-2.elasticbeanstalk.com/current_cloud?";
    public static String photo_url = "http://weather-app.us-west-1.elasticbeanstalk.com/get_photos?";


    public static String getIpUrl(){
        return current_url;
    }

    public static String getAutoCompleteUrl(){
        return auto_url;
    }

    public static String getWeatherUrl(){
        return weather_url;
    }

    public static String getPhotoUrl(){
        return photo_url;
    }
}
