package com.example.hw9;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class act_main extends AppCompatActivity implements frag_weather_view.OnFragmentInteractionListener{


    static TextView summaryTextView;
    static TextView temperatureTextView;
    static TextView cityTextView;
    static TextView humidityTextView;
    static TextView windSpeedTextView;
    static TextView visibilityTextView;
    static TextView pressureTextView;
    static ImageView mainSummaryImgView;

    public static final String act_mainTAG = "act_mainTAG";

    private RequestQueue mQueue;
    private RequestQueue linkQueue;
    private RequestQueue locationQueue;
    Double lat;
    Double lng;
    String cityName;
    JSONObject jsonObject;
    static ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
//
//        fab.hide();

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



        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }

        summaryTextView = (TextView) findViewById(R.id.frag_fav_current_summary);

        temperatureTextView = (TextView) findViewById(R.id.frag_fav_current_temper);

        cityTextView = (TextView) findViewById(R.id.frag_fav_current_location);

        humidityTextView = (TextView) findViewById(R.id.frag_fav_bar_humi);

        windSpeedTextView = (TextView) findViewById(R.id.frag_fav_bar_wind);

        pressureTextView = (TextView) findViewById(R.id.frag_fav_bar_pre);

        visibilityTextView = (TextView) findViewById(R.id.frag_fav_bar_vis);

        mainSummaryImgView = (ImageView) findViewById(R.id.frag_fav_current_weather);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (location == null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                }
            }
        }


        lat = location.getLatitude();

        lng = location.getLongitude();

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cityName = addresses.get(0).getLocality();

        cityTextView.setText(cityName);
        mQueue = Volley.newRequestQueue(this);
        linkQueue = Volley.newRequestQueue(this);
        locationQueue = Volley.newRequestQueue(this);
        setCurrentDetail(lat,lng);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        ArrayList<String> itemArrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,
                R.layout.auto_complete_list, itemArrayList);
        searchAutoComplete.setAdapter(adapter);
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String searchString=(String)parent.getItemAtPosition(position);
                searchAutoComplete.setText(""+searchString);
            }
        });
        SearchManager searchManager =
                (SearchManager) getSystemService(this.SEARCH_SERVICE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        getLatlng(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if(newText.length() > 1){
                            autoComplete(newText, searchAutoComplete);
                        }
                        return false;
                    }
                }
        );
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void setCurrentDetail(Double lat, Double lng){
        String url = "http://homework-env.mukfmcx8k2.us-east-2.elasticbeanstalk.com/location?lat=" + String.valueOf(lat) + "&lng=" + String.valueOf(lng);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = response;
                    JSONObject currently = new JSONObject(obj.getString("currently"));

                    jsonObject = response;

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

                    JSONObject daily = new JSONObject(obj.getString("daily"));

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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
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

    private void autoComplete(String input, final SearchView.SearchAutoComplete searchAutoComplete){
        arrayList = new ArrayList<>();
        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + input +"&types=(cities)&language=en&key=AIzaSyB0bc0KCx8W3PQLyIr9rgipSSVcW8wOTCI";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("predictions");
                    for(int i = 0; i < jsonArray.length(); i++){
                        String tem = jsonArray.getJSONObject(i).getString("description");
                        arrayList.add(tem);
                    }
                    adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.auto_complete_list, new ArrayList<>(arrayList));
                    searchAutoComplete.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        linkQueue.add(request);
    }

    private void getLatlng(final String location){
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=AIzaSyB0bc0KCx8W3PQLyIr9rgipSSVcW8wOTCI";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = response;

                    JSONArray result = obj.getJSONArray("results");

                    JSONObject geometry = result.getJSONObject(0).getJSONObject("geometry");

                    JSONObject locationObj = geometry.getJSONObject("location");

                    String lat = locationObj.getString("lat");

                    String lng = locationObj.getString("lng");

                    String url = "http://homework-env.mukfmcx8k2.us-east-2.elasticbeanstalk.com/location?lat=" + String.valueOf(lat) + "&lng=" + String.valueOf(lng);

                    JsonObjectRequest requestFore = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject obj = response;
                                JSONObject currently = new JSONObject(obj.getString("currently"));
                                Intent intent=new Intent(getApplicationContext(),act_search.class);
                                intent.putExtra("city",location);
                                intent.putExtra("json",obj.toString());
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    mQueue.add(requestFore);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        locationQueue.add(request);
    }

}
