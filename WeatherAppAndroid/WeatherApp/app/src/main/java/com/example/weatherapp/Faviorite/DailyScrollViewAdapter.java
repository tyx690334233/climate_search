package com.example.weatherapp.Faviorite;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.DataRequest.DailyInfo;
import com.example.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class DailyScrollViewAdapter extends BaseAdapter {
    private List<DailyInfo> dailyInfoList;
    private Context mContext;

    public DailyScrollViewAdapter(List<DailyInfo> dailyInfoList, Context mcontext) {
        this.dailyInfoList = dailyInfoList;
        this.mContext = mcontext;
    }

    @Override
    public int getCount(){
        return dailyInfoList.size();
    }

    @Override
    public Object getItem(int position) {
         createFragment(position);
         return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_row,parent,false);
        TextView dailyData = convertView.findViewById(R.id.dailyDate);
        ImageView img_icon = (ImageView) convertView.findViewById(R.id.dailyWeather);
        TextView minTempretTxt = (TextView) convertView.findViewById(R.id.minTempret);
        TextView maxTempretTxt = (TextView) convertView.findViewById(R.id.maxTempret);
//        Log.d("dataTransfer", "getView: "+dataformat.format(dailyInfoList.get(position).getDailyDate()));
        dailyData.setText(dailyInfoList.get(position).getDailyDate());
        img_icon.setBackgroundResource(dailyInfoList.get(position).getWeather());
        minTempretTxt.setText(dailyInfoList.get(position).getMinTempret().toString());
        maxTempretTxt.setText(dailyInfoList.get(position).getMaxTempret().toString());
        return convertView;
    }
    private void createFragment(int pos) {

        Log.d("fragment", "createFragment: "+pos);

    }
}
