package com.example.hw9;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class adapter_deatil extends FragmentPagerAdapter {

    ArrayList<Fragment> mFragmentList;
    ArrayList<String> mFragmentTitleList;

    adapter_deatil(FragmentManager fmng){
        super(fmng);
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();

    }

    public Fragment getItem(int position){
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
