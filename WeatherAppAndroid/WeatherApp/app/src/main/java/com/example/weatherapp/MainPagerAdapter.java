package com.example.weatherapp;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.weatherapp.DataRequest.FavoriteCityWeatherDTO;
import com.example.weatherapp.Faviorite.FavoriteFragment;
import com.example.weatherapp.MyUtils.GsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainPagerAdapter extends FragmentStatePagerAdapter {

    //    private static final int NUM_PAGES = 5;
    private static List<FavoriteCityWeatherDTO> favWeatherList = new ArrayList<>();

    public static final String MainPagerAdapterTAG = "MainPagerAdapterTAG";

    private static MainPagerAdapter mainPagerAdapter;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //    单例模式
    public static MainPagerAdapter getInstance(FragmentManager fm) {

            mainPagerAdapter = new MainPagerAdapter(fm);


        return mainPagerAdapter;
    }
    public static MainPagerAdapter getInstance(FragmentManager fm, List<FavoriteCityWeatherDTO> list) {

        mainPagerAdapter = new MainPagerAdapter(fm);
        favWeatherList = list;


        return mainPagerAdapter;
    }

    public static MainPagerAdapter getInstance() {

        return mainPagerAdapter;
    }

    @Override
    public Fragment getItem(int position) {

//        set the position-th item to this new value
        Log.d(MainPagerAdapterTAG, "getItem: the position is :"+position);

        return FavoriteFragment.newInstance(position, favWeatherList.get(position));
    }

    @Override
    public int getCount() {
        return favWeatherList.size();
    }

    public void addNewFavourate(FavoriteCityWeatherDTO vo, boolean isCurrent) {

        if (isCurrent ){
            favWeatherList.add(0,vo);
        }
       else{
            favWeatherList.add(vo);
        }

        notifyDataSetChanged();
    }
    // public void addNewFavourate(Set<String> citySet) {
    //     FavoriteCityWeatherDTO favcityDto;
    //    for (String favcity : citySet){
    //        favcityDto = GsonUtils.getGsonParser().fromJson(favcity, FavoriteCityWeatherDTO.class);
    //        addNewFavourate(favcityDto, false);
    //    }
    // }


    //    return weather the item is remove successly from the list
    public void removeFavorateCity(int position) {
        FavoriteCityWeatherDTO item = favWeatherList.remove(position);
        notifyDataSetChanged();
    }
    public void removeFavorateCity(String cityname) {
        // FavoriteCityWeatherDTO item = favWeatherList.remove(position);
        int index = 0;
        for (FavoriteCityWeatherDTO item : favWeatherList){
            if (item.getCityName().equals(cityname)){
                favWeatherList.remove(index);
                notifyDataSetChanged();
                return;
            }
            index ++;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return mainPagerAdapter.POSITION_NONE;
    }

    /**
     *
     */

    public boolean isFaved(String cityname){
        for (FavoriteCityWeatherDTO item:favWeatherList){
            if (item.getCityName().equals(cityname)){
                return true;
            }
        }
        return false;
    }

}

