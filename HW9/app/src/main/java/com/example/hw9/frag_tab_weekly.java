package com.example.hw9;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frag_tab_weekly.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frag_tab_weekly#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_tab_weekly extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LineChart lineChart;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public frag_tab_weekly() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment week_detail.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_tab_weekly newInstance(String param1, String param2) {
        frag_tab_weekly fragment = new frag_tab_weekly();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        act_detail details = (act_detail)getActivity();
        JSONObject jsonObject = details.setData();
        View view = inflater.inflate(R.layout.frag_tab_weekly, container, false);
        ArrayList<Entry> high = new ArrayList<>();
        ArrayList<Entry> low = new ArrayList<>();
        try {
            JSONObject daily = new JSONObject(jsonObject.getString("daily"));
            String summary = daily.getString("summary");
            TextView summaryTextView = (TextView) view.findViewById(R.id.val_icon);
            summaryTextView.setText(summary);

            String icon = daily.getString("icon");
            ImageView summaryImageView = (ImageView) view.findViewById(R.id.icon_name);
            setIcon(icon,summaryImageView);

            JSONArray data = daily.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                String temperatureHigh = data.getJSONObject(i).getString("temperatureHigh");
                String temperatureLow = data.getJSONObject(i).getString("temperatureLow");
                double temhigh = Double.valueOf(temperatureHigh);
                double temlow = Double.valueOf(temperatureLow);
                int c = (int) Math.round(temhigh);
                int d = (int) Math.round(temlow);
                high.add(new Entry(i,c));
                low.add(new Entry(i,d));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        lineChart = (LineChart) view.findViewById(R.id.chart);
        LineDataSet lineDataSet1 = new LineDataSet(high,"Maximum Temperature");
        LineDataSet lineDataSet2 = new LineDataSet(low,"Minimum Temperature");
        lineDataSet2.setColor(Color.parseColor("#b980f7"));
        lineDataSet1.setColor(Color.parseColor("#f6a902"));
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet1);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setTextColor(Color.WHITE);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setDrawAxisLine(false);
        LineData data = new LineData(dataSets);
        Legend legend = lineChart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setFormSize(20f);
        legend.setTextSize(15f);
        lineChart.setData(data);
        lineChart.invalidate();
        return view;

    }


    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.twitter,menu);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setIcon(String icon, ImageView img){
        if(icon.equals("clear-day")){
            img.setImageResource(R.drawable.weather_sunny);
        }else if(icon.equals("Clear-night")){
            img.setImageResource(R.drawable.weather_night);
        }else if(icon.equals("rain")){
            img.setImageResource(R.drawable.weather_rainy);
        }else if(icon.equals("sleet")){
            img.setImageResource(R.drawable.weather_snowy_rainy);
        }else if(icon.equals("snow")){
            img.setImageResource(R.drawable.weather_snowy);
        }else if(icon.equals("wind")){
            img.setImageResource(R.drawable.weather_windy_variant);
        }else if(icon.equals("fog")){
            img.setImageResource(R.drawable.weather_fog);
        }else if(icon.equals("cloudy")){
            img.setImageResource(R.drawable.weather_cloudy);
        }else if(icon.equals("partly-cloudy-night")){
            img.setImageResource(R.drawable.weather_night_partly_cloudy);
        }else if(icon.equals("partly-cloudy-day")){
            img.setImageResource(R.drawable.weather_partly_cloudy);
        }
    }
}


//    public void generateChart(LineChart chart){
//        temp1 = new ArrayList<>();
//        temp2 = new ArrayList<>();
//        for(int i = 0; i <= 7; i++){
//            Entry point = new Entry(i, 25);
//            temp1.add(point);
//        }
//        for(int i = 0; i <= 7; i++){
//            Entry point = new Entry(i, 14);
//            temp2.add(point);
//        }
//        LineDataSet set1 = new LineDataSet(temp1, "Minimum Temperature");
//        LineDataSet set2 = new LineDataSet(temp2,"Maximum Temperature");
//        set1.setColor(Color.parseColor("#BC83FC"));
//        set2.setColor(Color.parseColor("#F9AC1A"));
//        ArrayList<ILineDataSet> list = new ArrayList<>();
//        list.add(set1);
//        list.add(set2);
//
//        LineData data = new LineData(list);
//        chart.setData(data);
//        XAxis x = chart.getXAxis();
//        x.setTextColor(Color.parseColor("#ffffff"));
//        chart.getAxisLeft().setTextColor(Color.parseColor("#ffffff"));
//        chart.getAxisRight().setTextColor(Color.parseColor("#ffffff"));
//        Legend lgd = chart.getLegend();
//        lgd.setTextSize(13f);
//        lgd.setFormSize(13f);
//        x.setDrawGridLines(false);
//        lgd.setTextColor(Color.parseColor("#ffffff"));
//        chart.invalidate();
//
//    }





