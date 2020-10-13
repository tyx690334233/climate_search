package com.example.hw9;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class frag_tab_today extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public frag_tab_today() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment today_detail.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_tab_today newInstance(String param1, String param2) {
        frag_tab_today fragment = new frag_tab_today();
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
        act_detail details = (act_detail)getActivity();
        JSONObject jsonObject = details.setData();
        View view = inflater.inflate(R.layout.frag_tab_today, container, false);


        try {
            JSONObject currently = new JSONObject(jsonObject.getString("currently"));
            String humidity = currently.getString("humidity");
            Double temHum = Double.parseDouble(humidity) * 100;
            int b = (int) Math.round(temHum);
            humidity = b + "%";
            TextView humidityTextView = (TextView) view.findViewById(R.id.val_humi);
            humidityTextView.setText(humidity);

            TextView windSpeedTextView = (TextView) view.findViewById(R.id.val_wind);
            String windSpeed = currently.getString("windSpeed");
            windSpeedTextView.setText(windSpeed + " mph");

            TextView visibilityTextView = (TextView) view.findViewById(R.id.val_vis);
            String visibility = currently.getString("visibility");
            visibilityTextView.setText(visibility +" km");


            TextView pressureTextView = (TextView) view.findViewById(R.id.val_pres);
            String pressure = currently.getString("pressure");
            pressureTextView.setText(pressure +" mb");


            TextView precipitationTextView = (TextView) view.findViewById(R.id.val_preci);
            String precipIntensity = currently.getString("precipIntensity");
            precipitationTextView.setText(precipIntensity +" mmph");

            String temperature = currently.getString("temperature");
            Double temTem= Double.parseDouble(temperature);
            int a = (int) Math.round(temTem);
            temperature = a +"" + (char) 0x00B0 +"F";
            TextView temperatureTextView = (TextView) view.findViewById(R.id.val_temper);
            temperatureTextView.setText(temperature);

            TextView cloudCoverTextView = (TextView) view.findViewById(R.id.val_cloud);
            String cloudCover = currently.getString("cloudCover");
            cloudCoverTextView.setText(cloudCover +" km");

            TextView ozoneTextView = (TextView) view.findViewById(R.id.val_ozo);
            String ozone = currently.getString("ozone");
            ozoneTextView.setText(ozone +" DU");

            String icon = currently.getString("icon");
            ImageView summaryImageView = (ImageView) view.findViewById(R.id.icon_name);
            setIcon(icon,summaryImageView);

            String iconValue = icon.replace("-"," ");
            TextView iconTextView = (TextView) view.findViewById(R.id.val_weather);
            iconTextView.setText(iconValue);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        setHasOptionsMenu(true);
        return view;
    }


    @Override
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

