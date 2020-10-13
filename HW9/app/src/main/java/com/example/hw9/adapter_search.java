package com.example.hw9;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;

public class adapter_search extends FragmentPagerAdapter {

    static ArrayList<Fragment> mFragmentList;
    static ArrayList<String> mFragmentTitleList;
    public static final String detail_adapterTAG = "detail_adapterTAG";


    adapter_search(FragmentManager fm){

        super(fm);
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
        Log.d(detail_adapterTAG, "adapter_deatil: ");
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public static void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public CharSequence getPageTitle(int position){
        return mFragmentTitleList.get(position);

    }

}
