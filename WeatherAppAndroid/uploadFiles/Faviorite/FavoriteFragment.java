package com.example.weatherapp.Faviorite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.ListView;


import com.example.weatherapp.DataRequest.DailyInfo;
import com.example.weatherapp.DataRequest.DailyWeatherDTO;
import com.example.weatherapp.DataRequest.FavoriteCityWeatherDTO;
import com.example.weatherapp.Detail.DailyScrollViewAdapter;
import com.example.weatherapp.Detail.DetailActivity;
import com.example.weatherapp.MainPagerAdapter;
import com.example.weatherapp.MyUtils.GsonUtils;
import com.example.weatherapp.OverwriteClass.MyListView;
import com.example.weatherapp.R;
import com.example.weatherapp.Search.SearchActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteFragment} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    public static final String FavoriteFragmentTAG = "FavoriteFragmentTAG";

    //    daily scroll view
    private List<DailyInfo> dailyInfos = new ArrayList<>();
    private Context mcontext;
    private DailyScrollViewAdapter dailyAdapter = null;
    private MyListView dailyListView;
    private CardView DetailedCard;

    int mNum;

    //    default constructor
    public FavoriteFragment() {
    }

    //
    public FavoriteFragment(int position) {
        //        means this the current page
        if (position == 0) {

        }
    }

    static public FavoriteFragment newInstance(int num, FavoriteCityWeatherDTO vo) {
        FavoriteFragment f = new FavoriteFragment();
        Bundle args = new Bundle();
        String voJsonString = GsonUtils.getGsonParser().toJson(vo);
        args.putString("FavCityWethVO", voJsonString);
        args.putString("cityname", vo.getCityName() + "," + vo.getStateName() + "," + vo.getCountryName());
        if (num == 0) {
            args.putBoolean("isCurrent", true);
        } else {
            args.putBoolean("isCurrent", false);
        }
        args.putInt("num", num);
        f.setArguments(args);
        //if it is not current lovation, add float button

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        // Log.d(FavoriteFragmentTAG, "onCreate: " + mNum);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // boolean iscurrent = getArguments().getBoolean("isCurrent");
        boolean iscurrent = getArguments() != null ? getArguments().getBoolean("isCurrent") : false;
        final String thisCity = getArguments() != null ? getArguments().getString("cityname") : "";
        ViewGroup rootView;
        // final String WeatherJsonString = (String) getArguments().getString("FavCityWethVO");
        final String WeatherJsonString = getArguments() != null ? (String) getArguments().getString("FavCityWethVO") : "";
        FavoriteCityWeatherDTO favoriteCityWeatherDTO = GsonUtils.getGsonParser().fromJson(WeatherJsonString, FavoriteCityWeatherDTO.class);
        if (iscurrent) {
            rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_favrate, container, false);
        } else {
            rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_favrate_delete, container, false);
            FloatingActionButton fab = rootView.findViewById(R.id.delete_fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), thisCity + " was removed from favorites", Toast.LENGTH_LONG).show();
                    SharedPreferences sharelist = getActivity().getSharedPreferences("FavcityList", MODE_PRIVATE);
                    Map<String, ?> cityMap = sharelist.getAll();
                    String city = thisCity.split(",")[0].trim();
                    cityMap.remove(city);
                    SharedPreferences.Editor editor = sharelist.edit();
                    editor.remove(city);
                    editor.apply();
                    // editor.commit();
                    Log.d(FavoriteFragmentTAG, "onClick: and delete the favourite safely");
                    MainPagerAdapter mainPagerAdapter = MainPagerAdapter.getInstance();
                    mainPagerAdapter.removeFavorateCity(city);

                }
            });
        }


        try {
            //        Bundle args = getArguments();


            //绑定该LinearLayout的ID
            DetailedCard = rootView.findViewById(R.id.card1);
            //设置监听
            DetailedCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(FavoriteFragmentTAG, "click: ");
                    Intent detailIntent = new Intent();
                    //SoilsenerActivity.class为想要跳转的Activity
                    detailIntent.setClass(getActivity(), DetailActivity.class);
                    //                put some information
                    // detailIntent.putExtra("firstKeyName", "you are a shit!");
                    detailIntent.putExtra("weatherDetail", WeatherJsonString);
                    startActivity(detailIntent);
                }
            });

            TextView test = rootView.findViewById(R.id.Location);
            test.setText(favoriteCityWeatherDTO.getCityName());
            //load the data to view group
            loadDataToView(rootView, favoriteCityWeatherDTO);


        } catch (NullPointerException ex) {
            Log.d(FavoriteFragmentTAG, "didnot get the actual favourite VO!");
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(FavoriteFragmentTAG, "onPause: ");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(FavoriteFragmentTAG, "onstop: ");
    }

    private void loadDataToView(ViewGroup rootView, FavoriteCityWeatherDTO favoriteCityWeatherDTO) {

        //put first card
        ImageView weatherView = rootView.findViewById(R.id.imageView);
        TextView tempreText = rootView.findViewById(R.id.Temperature);
        TextView summaryText = rootView.findViewById(R.id.summary);
        TextView locationText = rootView.findViewById(R.id.Location);

        weatherView.setImageResource(favoriteCityWeatherDTO.getSummaryIcon());
        tempreText.setText(String.valueOf(Math.round(favoriteCityWeatherDTO.getTemperature())) + "℉");
        summaryText.setText(favoriteCityWeatherDTO.getCurrentlySummary());
        if (favoriteCityWeatherDTO.getStateName().length() == 0) {
            locationText.setText(favoriteCityWeatherDTO.getCityName() + "," + favoriteCityWeatherDTO.getCountryName());
        } else {
            locationText.setText(favoriteCityWeatherDTO.getCityName() + "," + favoriteCityWeatherDTO.getStateName() + "," + favoriteCityWeatherDTO.getCountryName());
        }

        //put 2nd card
        //find item
        TextView humidityText = rootView.findViewById(R.id.humidityValue);
        TextView windSpeedText = rootView.findViewById(R.id.windSpeedValue);
        TextView visibilityText = rootView.findViewById(R.id.visibilityValue);
        TextView pressureText = rootView.findViewById(R.id.pressureValue);
        //set item
        humidityText.setText(Math.round(favoriteCityWeatherDTO.getHumidity() * 100) + "%");
        windSpeedText.setText(String.format("%.2f", favoriteCityWeatherDTO.getWindSpeed()) + " mph");
        visibilityText.setText(String.format("%.2f", favoriteCityWeatherDTO.getVisablility()) + " km");
        pressureText.setText(String.format("%.2f", favoriteCityWeatherDTO.getPressure()) + " mb");

        //put third card
        dailyListView = rootView.findViewById(R.id.dailyList);
        for (DailyWeatherDTO dailyItem : favoriteCityWeatherDTO.getDailyWeatherDTOList()) {

            DailyInfo infoItem = new DailyInfo();
            infoItem.setDailyDate(new Date(dailyItem.getUnixtime() * 1000));
            infoItem.setWeather(dailyItem.getIcon());
            infoItem.setMaxTempret((int) dailyItem.getHighTemperature());
            infoItem.setMinTempret((int) dailyItem.getLowTemperature());
            dailyInfos.add(infoItem);
        }

        // add daily info to scroll list
        dailyAdapter = new DailyScrollViewAdapter(dailyInfos, getActivity());
        dailyListView.setAdapter(dailyAdapter);
    }

}
