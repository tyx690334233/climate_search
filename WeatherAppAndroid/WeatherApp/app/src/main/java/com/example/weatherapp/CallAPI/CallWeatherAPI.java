package com.example.weatherapp.CallAPI;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.DataRequest.DailyWeatherDTO;
import com.example.weatherapp.DataRequest.FavoriteCityWeatherDTO;
import com.example.weatherapp.DataRequest.URLCollection;
import com.example.weatherapp.DataRequest.WeatherCurrentlyVO;
import com.example.weatherapp.DataRequest.WeatherDailyDataVO;
import com.example.weatherapp.DataRequest.WeatherDailyVO;
import com.example.weatherapp.MyUtils.GsonUtils;
import com.example.weatherapp.R;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is for enclosure the functions to get weather and load data to favorite DTO
 */
public class CallWeatherAPI {

    public static final String CallWeatherAPITAG = "CallWeatherAPITAG";

    private FavoriteCityWeatherDTO weatherDTO;
    private URLCollection APIs = new URLCollection();

    public CallWeatherAPI(FavoriteCityWeatherDTO weatherDTO) {
        this.weatherDTO = weatherDTO;
    }


    public void getWeatherDatafromLatLon(double lat, double lon, Context mcontext, FavoriteCityWeatherDTO dto, final VolleyCallback callback) {

        RequestQueue queue = Volley.newRequestQueue(mcontext);

        String getWeatherUrl = APIs.getWeatherAPI();
        // weatherDTO = new FavoriteCityWeatherDTO();
        weatherDTO = dto;
        getWeatherUrl += "lat=" + String.valueOf(lat) + "&lng=" + String.valueOf(lon);
        weatherDTO.setLatitude(lat);
        weatherDTO.setLongitude(lon);
        Log.d(CallWeatherAPITAG, "getWeatherURL is: " + getWeatherUrl);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getWeatherUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d(locationTAG, "getWeatherURL result is: "+response.toString());
                        weatherDTO = setCurrentData(response);
                        callback.onSuccess(weatherDTO);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(CallWeatherAPITAG, error.getMessage(), error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }

    public FavoriteCityWeatherDTO setCurrentData(JSONObject response) {
        try {
            //            temText.setText(response.get("currently").toString());
            Log.d(CallWeatherAPITAG, "getWeatherURL result is:: " + response.get("currently").toString());

            WeatherCurrentlyVO currentlyVO = GsonUtils.getGsonParser().fromJson(response.get("currently").toString(), WeatherCurrentlyVO.class);
            WeatherDailyVO dailyVO = GsonUtils.getGsonParser().fromJson(response.get("daily").toString(), WeatherDailyVO.class);
            String timezone = response.get("timezone").toString();
            // temText.setText(String.valueOf(vo.getTemperature()));
            // summaryText.setText(String.valueOf(vo.getSummary()));
            return  loadDataToWeatherDTO(currentlyVO, dailyVO, timezone, weatherDTO);
            //put the current weather item to the pager adapter
        } catch (JsonParseException e) {
            Log.e(CallWeatherAPITAG, "GSON parseE ERROR: " + e);
        } catch (NullPointerException e) {
            Log.e(CallWeatherAPITAG, "cannot get the main pager adapter ");
        } catch (Exception e) {
            Log.d(CallWeatherAPITAG, "get error in casting :" + e);
        }
        return null;

    }

    private FavoriteCityWeatherDTO loadDataToWeatherDTO(WeatherCurrentlyVO currentlyVO, WeatherDailyVO dailyVO, String timezone, FavoriteCityWeatherDTO dto) {

        dto.setTimeZone(timezone);
        //put currentlyVO infos
        dto.setSummaryIcon(convertIcon(currentlyVO.getIcon()));
        dto.setRawSummaryIcon(currentlyVO.getIcon());
        dto.setTemperature(Math.round(currentlyVO.getTemperature()));
        dto.setCurrentlySummary(currentlyVO.getSummary());
        dto.setHumidity(currentlyVO.getHumidity());
        dto.setWindSpeed((float) currentlyVO.getWindSpeed());
        dto.setVisablility((float) currentlyVO.getVisibility());
        dto.setPressure((float) currentlyVO.getPressure());
        dto.setCurrentlySummary(currentlyVO.getSummary());
        dto.setPrecipitation((float) currentlyVO.getPrecipIntensity());
        dto.setCloudCover((float) currentlyVO.getCloudCover());
        dto.setOzone((float) currentlyVO.getOzone());
        // put icon
        //put dailyvo infos
        dto.setDailySummary(dailyVO.getSummary());
        dto.setDailyIcon(convertIcon(dailyVO.getIcon()));
        List<DailyWeatherDTO> dailyWeatherDTOList = new ArrayList<>();
        DailyWeatherDTO dailyItem;
        for (WeatherDailyDataVO vo : dailyVO.getData()) {
            dailyItem = new DailyWeatherDTO();
            dailyItem.setUnixtime(vo.getTime());
            dailyItem.setIcon(convertIcon(vo.getIcon()));
            dailyItem.setHighTemperature(vo.getTemperatureHigh());
            dailyItem.setLowTemperature(vo.getTemperatureLow());
            //put one element to list
            dailyWeatherDTOList.add(dailyItem);
        }
        // put list to dto
        dto.setDailyWeatherDTOList(dailyWeatherDTOList);

        //return dto
        return dto;
    }

    //according the icontxt, return conspounding icon img
    private int convertIcon(String iconText) {
        Log.d(CallWeatherAPITAG, "the icontext is: " + iconText);
        int res;
        iconText = iconText.toLowerCase();
        switch (iconText) {
            case "clear-day":
                res = R.drawable.summary_weather_sunny;
                break;
            case "clear-night":
                res = R.drawable.summary_weather_night;
                break;
            case "rain":
                res = R.drawable.summary_weather_rainy;
                break;
            case "sleet":
                res = R.drawable.summary_weather_snowy_rainy;
                break;
            case "snow":
                res = R.drawable.summary_weather_snowy;
                break;
            case "wind":
                res = R.drawable.summary_weather_windy_variant;
                break;
            case "fog":
                res = R.drawable.summary_weather_fog;
                break;
            case "cloudy":
                res = R.drawable.summary_weather_cloudy;
                break;
            case "partly-cloudy-night":
                res = R.drawable.summary_weather_night_partly_cloudy;
                break;
            case "partly-cloudy-day":
                res = R.drawable.summary_weather_partly_cloudy;
                break;
            default:
                res = R.drawable.summary_weather_sunny;
        }
        return res;
    }

    public interface VolleyCallback {
        void onSuccess(FavoriteCityWeatherDTO weatherDTO);

    }
}
