package com.example.weatherapp.DataRequest;

public class URLCollection {
    // String ip = "http://10.26.107.118";
    // String ip = "http://nodejsapp-env.muzexzf9jz.us-east-2.elasticbeanstalk.com";
    String ip ="http://NodejsApp-env.muzexzf9jz.us-east-2.elasticbeanstalk.com";

    /**
     * local Server static aqi
     */
    // private String curlocationAPI = "http://ip-api.com/json";
    // private String detailAPI = ":3000/apis//get_detail?lat=37.8267&lng=-122.4233&time=1572570337";
    // private String imageAPI = ":3000/apis/get_image?state=California";
    // private String locationAPI = ":3000/apis/get_location?street=1246 W 30th St&city=Los Angeles&state=CA";
    // private String weatherAPI = ":3000/apis/get_weather?lat=37.8267&lng=-122.4233";
    // private String autoCompeteAPI = ":3000/apis/get_autocomplete?input=Los";
    // private String sceneAPI = ":3000/apis/get_scenery_photo?city=Losangeles";

    /**
     * local Server dynamic aqi
     */
    // private String curlocationAPI = "http://ip-api.com/json";
    // private String detailAPI = ":8081/get_detail?";
    // private String imageAPI = ":8081/get_image?";
    // private String locationAPI = ":8081/get_location?";
    // private String weatherAPI = ":8081/get_weather?";
    // private String autoCompeteAPI = ":8081/get_autocomplete?";
    // private String sceneAPI = ":8081/get_scenery_photo?";

    /**
     * remote aws Server static api
     */
    // private String curlocationAPI = "http://ip-api.com/json";
    // private String detailAPI = "/get_detail?lat=37.8267&lng=-122.4233&time=1572570337";
    // private String imageAPI = "/get_image?state=California";
    // private String locationAPI = "/get_location?street=1246 W 30th St&city=Los Angeles&state=CA";
    // private String weatherAPI = "/get_weather?lat=37.8267&lng=-122.4233";
    // private String autoCompeteAPI = "/get_autocomplete?input=Los";
    // private String sceneAPI = "/get_scenery_photo?city=Losangeles";

    /**
     * remote aws Server dynamic api
     */
    private String curlocationAPI = "http://ip-api.com/json";
    private String detailAPI = "/get_detail?";
    private String imageAPI = "/get_image?";
    private String locationAPI = "/get_location?";
    private String weatherAPI = "/get_weather?";
    private String autoCompeteAPI = "/get_autocomplete?";
    private String sceneAPI = "/get_scenery_photo?";

    public String getCurlocationAPI() {
        return curlocationAPI;
    }

    public String getDetailAPI() {
        return ip + detailAPI;
    }

    public String getImageAPI() {
        return ip + imageAPI;
    }

    public String getLocationAPI() {
        return ip + locationAPI;
    }

    public String getWeatherAPI() {
        return ip + weatherAPI;
    }

    public String getAutoCompeteAPI() {
        return ip + autoCompeteAPI;
    }

    public String getSceneAPI() {
        return ip + sceneAPI;
    }
}
