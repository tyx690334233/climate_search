package com.example.weatherapp.Search;

import androidx.appcompat.app.AppCompatActivity;

// import android.app.FragmentManager;
// import android.app.FragmentTransaction;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.CallAPI.CallWeatherAPI;
import com.example.weatherapp.DataRequest.FavoriteCityWeatherDTO;
import com.example.weatherapp.DataRequest.URLCollection;
import com.example.weatherapp.Faviorite.FavoriteFragment;
import com.example.weatherapp.MainPagerAdapter;
import com.example.weatherapp.MyUtils.GsonUtils;
import com.example.weatherapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    private CallWeatherAPI getWeather;
    private FavoriteCityWeatherDTO dto = new FavoriteCityWeatherDTO();
    public static final String SearchActivityTAG = "SearchActivityTAG";
    private URLCollection APIs = new URLCollection();
    private ConstraintLayout spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //make a new object
        getWeather = new CallWeatherAPI(dto);

        Intent getSearchIntent = getIntent();

        final String location = getSearchIntent.getStringExtra("searchCity");
        boolean isfaved = ifStored(location);

        getSupportActionBar().setTitle(location);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getLocationInfo(location);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        final FloatingActionButton fab = findViewById(R.id.fab);
        if(isfaved){
            //delete fav city
            fab.setImageResource(R.drawable.map_marker_minus);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SearchActivity.this, location + " was rermoved from favorites", Toast.LENGTH_LONG).show();
                    SharedPreferences sharelist = getSharedPreferences("FavcityList",MODE_PRIVATE);
                    Map<String,?> cityMap = sharelist.getAll();
                    String city = dto.getCityName();
                    cityMap.remove(city);
                    SharedPreferences.Editor editor= sharelist.edit();
                    editor.remove(city);
                    editor.apply();
                    // editor.commit();

                    MainPagerAdapter mainPagerAdapter = MainPagerAdapter.getInstance();
                    mainPagerAdapter.removeFavorateCity(city);
                    fab.setImageResource(R.drawable.map_marker_plus);
                }
            });
        }
        else {
            //add to fav
            fab.setImageResource(R.drawable.map_marker_plus);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SearchActivity.this, location + " was added to favorites", Toast.LENGTH_LONG).show();

                    SharedPreferences sharelist = getSharedPreferences("FavcityList", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharelist.edit();
                    editor.putString(dto.getCityName(), GsonUtils.getGsonParser().toJson(dto));

                    editor.apply();
                    Log.d(SearchActivityTAG, "onClick: and save the favourite safely");
                    MainPagerAdapter mainPagerAdapter = MainPagerAdapter.getInstance();
                    mainPagerAdapter.addNewFavourate(dto,false);
                    fab.setImageResource(R.drawable.map_marker_minus);
                }
            });
        }

    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }

    private void getLocationInfo(String locationString) {
        String[] locationArray = locationString.split(",");
        if (locationArray.length < 2) {
            Log.e(SearchActivityTAG, "getLocationInfo: location info is not sufficient");
        }
        String city = locationArray[0].trim();
        String state ="";
        String country;
        if (locationArray.length == 2){
             country = locationArray[1].trim();
        } else if (locationArray.length == 3) {
             state = locationArray[1].trim();
             country = locationArray[2].trim();
        }
       else{
             state = locationArray[1].trim();
             country = locationArray[2].trim();
        }
        dto.setCityName(city);
        dto.setStateName(state);
        dto.setCountryName(country);
        RequestQueue queue = Volley.newRequestQueue(this);

        String getLocationUrl = APIs.getLocationAPI();

        getLocationUrl += Uri.parse("city=" + city + "&state=" + state);
        Log.d(SearchActivityTAG, "getLocationUrl is: " + getLocationUrl);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getLocationUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.d(locationTAG, "getWeatherURL result is: "+response.toString());
                        setLatLon(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(SearchActivityTAG, error.getMessage(), error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }

    private void setLatLon(JSONObject respeonse){
        try {
            JSONObject loca = respeonse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            double searchLat = loca.getDouble("lat");
            // double searchLon = loca.getDouble("lon");
            double searchLon = loca.getDouble("lng");
            getWeather.getWeatherDatafromLatLon(searchLat, searchLon, this, dto, new CallWeatherAPI.VolleyCallback() {
                @Override
                public void onSuccess(FavoriteCityWeatherDTO weatherDTO) {
                    Log.d(SearchActivityTAG, "onSuccess: ");
                    // dto = weatherDTO;

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.search_fragment, FavoriteFragment.newInstance(0,dto));
                    ft.commit();
                    spinner.setVisibility(View.GONE);
                }
            });
            // spinner.setVisibility(View.GONE);
        }
        catch(JSONException e){
            Log.e(SearchActivityTAG, "setLatLon: there is no effecive information to parse,please recheck again",e);
        }
    }

    // @Override
    // public void onSuccess(FavoriteCityWeatherDTO weatherDTO) {
    //     Log.d(SearchActivityTAG, "onSuccess: ");
    // }
    private boolean ifStored(String location){
        SharedPreferences sharelist = getSharedPreferences("FavcityList", MODE_PRIVATE);
        // Map<String,?> citySet = sharelist.getAll();
        String city = location.split(",")[0].trim();
        // FavoriteCityWeatherDTO test;
        // for (Map.Entry<String,?> item: citySet.entrySet()){
        //     if (item.getKey().equals(city)){
        //         return true;
        //     }
        // }
        return MainPagerAdapter.getInstance().isFaved(city);
        // return false;
    }
}
