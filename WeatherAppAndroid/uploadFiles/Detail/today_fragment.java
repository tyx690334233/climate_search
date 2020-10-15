package com.example.weatherapp.Detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.DataRequest.FavoriteCityWeatherDTO;
import com.example.weatherapp.MyUtils.GsonUtils;
import com.example.weatherapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link today_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link today_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class today_fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.today_fragment, container, false);
        String WeatherJsonString = (String) getArguments().getString("FavCityWethVO");
        FavoriteCityWeatherDTO favoriteCityWeatherDTO = GsonUtils.getGsonParser().fromJson(WeatherJsonString, FavoriteCityWeatherDTO.class);
        loadDatatoView(rootview, favoriteCityWeatherDTO);
        return rootview;

    }

    private void loadDatatoView(ViewGroup rootView, FavoriteCityWeatherDTO favoriteCityWeatherDTO) {
        //    get element
        TextView windspeedText = rootView.findViewById(R.id.windspeedcardvalue);
        TextView pressureText = rootView.findViewById(R.id.pressurecardvalue);
        TextView precipitationText = rootView.findViewById(R.id.precipitationcardvalue);
        TextView tempratureText = rootView.findViewById(R.id.tempraturecardvalue);
        TextView humidityText = rootView.findViewById(R.id.humiditycardvalue);
        TextView visibilityText = rootView.findViewById(R.id.visibilitycardvalue);
        TextView cloudcloverText = rootView.findViewById(R.id.cloudcovercardvalue);
        TextView ozoneText = rootView.findViewById(R.id.ozonecardvalue);


        TextView todaySummaryText = rootView.findViewById(R.id.todaycardvalue);
        ImageView todayIconImageView = rootView.findViewById(R.id.todayimage);

        //    put element

        // windspeedText.setText(String.valueOf(favoriteCityWeatherDTO.getWindSpeed()).substring(0,5)+"mph");
        windspeedText.setText(String.format("%.2f", favoriteCityWeatherDTO.getWindSpeed()) + " mph");
        pressureText.setText(String.format("%.2f", favoriteCityWeatherDTO.getPressure()) + " mb");
        precipitationText.setText(String.format("%.2f", favoriteCityWeatherDTO.getPrecipitation()) + " mmph");
        tempratureText.setText(Math.round(favoriteCityWeatherDTO.getTemperature()) + "℉");
        humidityText.setText(Math.round(favoriteCityWeatherDTO.getHumidity() * 100) + " %");
        visibilityText.setText(String.format("%.2f", favoriteCityWeatherDTO.getVisablility()) + " km");
        cloudcloverText.setText(Math.round(favoriteCityWeatherDTO.getCloudCover()) + " %");
        ozoneText.setText(String.format("%.2f", favoriteCityWeatherDTO.getOzone()) + " DU");

        todaySummaryText.setText(convertSumm(favoriteCityWeatherDTO.getRawSummaryIcon()));
        todayIconImageView.setImageResource(favoriteCityWeatherDTO.getSummaryIcon());

    }

    private String convertSumm(String input) {
        String res = "";
        switch (input) {
            case "partly-cloudy-day":
                res = "cloudy day";
                break;
            case "partly-cloudy-night":
                res = "cloudy night";
                break;
            default:
                res = input;

        }
        res.replace("-"," ");
        return res;

    }
}

