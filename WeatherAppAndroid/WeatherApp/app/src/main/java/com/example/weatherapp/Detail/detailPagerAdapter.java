package com.example.weatherapp.Detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class detailPagerAdapter extends FragmentPagerAdapter {
    private int num;
    private HashMap<Integer, Fragment> mFragmentHashMap = new HashMap<>();
    private String FavWeatherDTOText;
    private ArrayList<String> urls;
    public detailPagerAdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @Override
    public Fragment getItem(int position) {

        return createFragment(position);
    }

    @Override
    public int getCount() {
        return num;
    }

    public void setDTOText(String dtoText) {
        FavWeatherDTOText = dtoText;
    }

    private Fragment createFragment(int pos) {
        Fragment fragment = mFragmentHashMap.get(pos);

        Bundle args = new Bundle();
        String voJsonString = FavWeatherDTOText;

        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = new today_fragment();
                    args.putString("FavCityWethVO", voJsonString);
                    fragment.setArguments(args);
                    break;
                case 1:
                    fragment = new weekly_fragment();
                    args.putString("FavCityWethVO", voJsonString);
                    fragment.setArguments(args);
                    break;
                case 2:
                    fragment = new photos_fragment();
                    args.putString("FavCityWethVO", voJsonString);
                    args.putStringArrayList("urlslist",urls);
                    fragment.setArguments(args);
                    break;
            }
            mFragmentHashMap.put(pos, fragment);
        }
        return fragment;
    }

    public void setpicUrls(ArrayList<String> res){
        this.urls = res;
    }
}
