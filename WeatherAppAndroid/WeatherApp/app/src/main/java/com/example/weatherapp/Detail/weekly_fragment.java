package com.example.weatherapp.Detail;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.DataRequest.DailyWeatherDTO;
import com.example.weatherapp.DataRequest.FavoriteCityWeatherDTO;
import com.example.weatherapp.MyUtils.GsonUtils;
import com.example.weatherapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class weekly_fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.weekly_fragment, container, false);
        String WeatherJsonString = (String) getArguments().getString("FavCityWethVO");
        FavoriteCityWeatherDTO favoriteCityWeatherDTO = GsonUtils.getGsonParser().fromJson(WeatherJsonString, FavoriteCityWeatherDTO.class);
        loadDatatoView(rootview, favoriteCityWeatherDTO);
        return rootview;
    }

    private void loadDatatoView(ViewGroup rootView, FavoriteCityWeatherDTO favoriteCityWeatherDTO) {
        TextView weeklySummaryText = rootView.findViewById(R.id.weekly_sum_textView);
        ImageView weeklyIconImageView = rootView.findViewById(R.id.weekly_imageview);

        weeklySummaryText.setText(favoriteCityWeatherDTO.getDailySummary());
        weeklyIconImageView.setImageResource(favoriteCityWeatherDTO.getDailyIcon());

        LineChart chart = (LineChart) rootView.findViewById(R.id.linechart);


        drawChart(chart, favoriteCityWeatherDTO);
    }

    /**
     * draw the chart
     *
     * @param chart
     * @param favoriteCityWeatherDTO
     */
    private void drawChart(LineChart chart, FavoriteCityWeatherDTO favoriteCityWeatherDTO) {

        ArrayList<Entry> MaxTemVals = new ArrayList<Entry>();
        ArrayList<Entry> MinTemvals = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        int indexCount = 0;
        for (DailyWeatherDTO dailyWeatherDTO : favoriteCityWeatherDTO.getDailyWeatherDTOList()) {
            Entry maxTemitem = new Entry(indexCount, (float)dailyWeatherDTO.getHighTemperature());
            MaxTemVals.add(maxTemitem);
            Entry minTemitem = new Entry(indexCount, (float)dailyWeatherDTO.getLowTemperature());
            MinTemvals.add(minTemitem);

            xVals.add(String.valueOf(indexCount));

            indexCount++;
        }

        LineDataSet maxTemDataSet = new LineDataSet(MaxTemVals, "Maximum Temperature");
        // setComp1.setAxisDependency(AxisDependency.LEFT);
        LineDataSet minTemDataSet = new LineDataSet(MinTemvals, "Minimum Temperature");
        // minTemDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        maxTemDataSet.setColor(ContextCompat.getColor(this.getContext(),R.color.max_tem_line_color));
        // maxTemDataSet.setColor(Color.WHITE);
        // minTemDataSet.setColor(getResources().getColor(R.color.min_tem_line_color));
        minTemDataSet.setColor(ContextCompat.getColor(this.getContext(),R.color.min_tem_line_color));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(maxTemDataSet);
        dataSets.add(minTemDataSet);

        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate(); // refresh

        Legend l = chart.getLegend();
        l.setFormSize(14f); // set the size of the legend forms/shapes
        l.setTextSize(14f); // set the size of the legend forms/shapes
        l.setTextColor(Color.WHITE);
        l.setXEntrySpace(20f);

        chart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        chart.getAxisRight().setTextColor(Color.WHITE); // left y-axis
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setDrawGridLines(false);

    }


}
