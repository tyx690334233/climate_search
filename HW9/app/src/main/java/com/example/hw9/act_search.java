package com.example.hw9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class act_search extends AppCompatActivity {

    String cityName;
    Toolbar toolbar;
    JSONObject jsonObject;
    static TextView summaryTextView;
    static TextView temperatureTextView;
    static TextView cityTextView;
    static TextView humidityTextView;
    static TextView windSpeedTextView;
    static TextView visibilityTextView;
    static TextView pressureTextView;
    static ImageView mainSummaryImgView;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_lay);

        CardView cardView=findViewById(R.id.frag_summary);
        cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),act_detail.class);
                intent.putExtra("json", jsonObject.toString());
                intent.putExtra("city",cityName);
                startActivity(intent);
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    String msg = cityName + " was added to favorites";
                    fab.setImageResource(R.drawable.map_marker_minus);
                    Toast.makeText(act_search.this, msg, LENGTH_SHORT).show();
                    flag = false;
                }else{
                    String msg = cityName + " was removed from favorites";
                    fab.setImageResource(R.drawable.map_marker_plus);
                    Toast.makeText(act_search.this, msg, LENGTH_SHORT).show();
                    flag = true;
                }
            }
        });

        String sessionId = getIntent().getStringExtra("city");
        cityName = getIntent().getStringExtra("city");
        summaryTextView = (TextView) findViewById(R.id.frag_fav_current_summary);

        temperatureTextView = (TextView) findViewById(R.id.frag_fav_current_temper);

        cityTextView = (TextView) findViewById(R.id.frag_fav_current_location);

        humidityTextView = (TextView) findViewById(R.id.frag_fav_bar_humi);

        windSpeedTextView = (TextView) findViewById(R.id.frag_fav_bar_wind);

        pressureTextView = (TextView) findViewById(R.id.frag_fav_bar_pre);

        visibilityTextView = (TextView) findViewById(R.id.frag_fav_bar_vis);

        mainSummaryImgView = (ImageView) findViewById(R.id.frag_fav_current_weather);

        cityTextView.setText(cityName);
        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("json"));
            JSONObject currently = new JSONObject(jsonObject.getString("currently"));
            String summary = currently.getString("summary");
            summaryTextView.setText(summary);

            String temperature = currently.getString("temperature");
            Double temTem= Double.parseDouble(temperature);
            int a = (int) Math.round(temTem);
            temperature = a +"" + (char) 0x00B0 +"F";
            temperatureTextView.setText(temperature);

            String humidity = currently.getString("humidity");
            Double temHum = Double.parseDouble(humidity) * 100;
            int b = (int) Math.round(temHum);
            humidity = b + "%";
            humidityTextView.setText(humidity);

            String windSpeed = currently.getString("windSpeed");
            windSpeedTextView.setText(windSpeed + " mph");


            String visibility = currently.getString("visibility");
            visibilityTextView.setText(visibility +" km");



            String pressure = currently.getString("pressure");
            pressureTextView.setText(pressure +" mb");

            String mainSummary = currently.getString("icon");
            setIcon(mainSummary,mainSummaryImgView);

            JSONObject daily = new JSONObject(jsonObject.getString("daily"));

            JSONArray data = daily.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                String tem = data.getJSONObject(i).getString("time");
                String date = getDate(tem);
                String temperatureHigh = data.getJSONObject(i).getString("temperatureHigh");
                String temperatureLow = data.getJSONObject(i).getString("temperatureLow");
                double temhigh = Double.valueOf(temperatureHigh);
                double temlow = Double.valueOf(temperatureLow);
                int c = (int) Math.round(temhigh);
                int d = (int) Math.round(temlow);
                temperatureHigh = c +"";
                temperatureLow = d +"";
                String icon = data.getJSONObject(i).getString("icon");
                setContent(date, temperatureLow,temperatureHigh,icon,i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolbar = (Toolbar) findViewById(R.id.searchToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(cityName);

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setContent(String date, String temperatureLow, String temperatureHigh, String icon, int index) {
        if(index == 0){
            TextView first = (TextView) findViewById(R.id.frag_fav_list_row1_date);
            TextView firstHigh = (TextView) findViewById(R.id.frag_fav_list_row1_high);
            TextView firstLow = (TextView) findViewById(R.id.frag_fav_list_row1_low);
            ImageView firstIcon = (ImageView) findViewById(R.id.frag_fav_list_row1_weather);
            first.setText(date);
            firstHigh.setText(temperatureHigh);
            firstLow.setText(temperatureLow);
            setIcon(icon, firstIcon);
        }else if(index == 1){
            TextView second = (TextView) findViewById(R.id.frag_fav_list_row2_date);
            TextView secondHigh = (TextView) findViewById(R.id.frag_fav_list_row2_high);
            TextView secondLow = (TextView) findViewById(R.id.frag_fav_list_row2_low);
            ImageView secondIcon = (ImageView) findViewById(R.id.frag_fav_list_row2_weather);
            second.setText(date);
            secondHigh.setText(temperatureHigh);
            secondLow.setText(temperatureLow);
            setIcon(icon, secondIcon);
        }else if(index == 2){
            TextView third = (TextView) findViewById(R.id.frag_fav_list_row3_date);
            TextView thirdHigh = (TextView) findViewById(R.id.frag_fav_list_row3_high);
            TextView thirdLow = (TextView) findViewById(R.id.frag_fav_list_row3_low);
            ImageView thirdIcon = (ImageView) findViewById(R.id.frag_fav_list_row3_weather);
            third.setText(date);
            thirdHigh.setText(temperatureHigh);
            thirdLow.setText(temperatureLow);
            setIcon(icon, thirdIcon);
        }else if(index == 3){
            TextView fourth = (TextView) findViewById(R.id.frag_fav_list_row4_date);
            TextView fourthHigh = (TextView) findViewById(R.id.frag_fav_list_row4_high);
            TextView fourthLow = (TextView) findViewById(R.id.frag_fav_list_row4_low);
            ImageView fourthIcon = (ImageView) findViewById(R.id.frag_fav_list_row4_weather);
            fourth.setText(date);
            fourthHigh.setText(temperatureHigh);
            fourthLow.setText(temperatureLow);
            setIcon(icon, fourthIcon);
        }else if(index == 4){
            TextView fifth = (TextView) findViewById(R.id.frag_fav_list_row5_date);
            TextView fifthHigh = (TextView) findViewById(R.id.frag_fav_list_row5_high);
            TextView fifthLow = (TextView) findViewById(R.id.frag_fav_list_row5_low);
            ImageView fifthIcon = (ImageView) findViewById(R.id.frag_fav_list_row5_weather);
            fifth.setText(date);
            fifthHigh.setText(temperatureHigh);
            fifthLow.setText(temperatureLow);
            setIcon(icon, fifthIcon);
        }else if(index == 5){
            TextView sixth = (TextView) findViewById(R.id.frag_fav_list_row6_date);
            TextView sixthHigh = (TextView) findViewById(R.id.frag_fav_list_row6_high);
            TextView sixthLow = (TextView) findViewById(R.id.frag_fav_list_row6_low);
            ImageView sixthIcon = (ImageView) findViewById(R.id.frag_fav_list_row6_weather);
            sixth.setText(date);
            sixthHigh.setText(temperatureHigh);
            sixthLow.setText(temperatureLow);
            setIcon(icon, sixthIcon);
        }else if(index == 6){
            TextView seventh = (TextView) findViewById(R.id.frag_fav_list_row7_date);
            TextView seventhHigh = (TextView) findViewById(R.id.frag_fav_list_row7_high);
            TextView seventhLow = (TextView) findViewById(R.id.frag_fav_list_row7_low);
            ImageView seventhIcon = (ImageView) findViewById(R.id.frag_fav_list_row7_weather);
            seventh.setText(date);
            seventhHigh.setText(temperatureHigh);
            seventhLow.setText(temperatureLow);
            setIcon(icon, seventhIcon);
        }else if(index == 7){
            TextView eighth = (TextView) findViewById(R.id.frag_fav_list_row8_date);
            TextView eighthHigh = (TextView) findViewById(R.id.frag_fav_list_row8_high);
            TextView eighthLow = (TextView) findViewById(R.id.frag_fav_list_row8_low);
            ImageView eightIcon = (ImageView) findViewById(R.id.frag_fav_list_row8_weather);
            eighth.setText(date);
            eighthHigh.setText(temperatureHigh);
            eighthLow.setText(temperatureLow);
            setIcon(icon, eightIcon);
        }
    }

    private String getDate(String stamp){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = formatter.format(new Date(Long.parseLong(stamp)*1000));
        return dateString;
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
