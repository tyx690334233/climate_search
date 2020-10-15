package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.DataRequest.AutoCompleteVO;
import com.example.weatherapp.DataRequest.DailyWeatherDTO;
import com.example.weatherapp.DataRequest.FavoriteCityWeatherDTO;
import com.example.weatherapp.DataRequest.URLCollection;
import com.example.weatherapp.DataRequest.WeatherCurrentlyVO;
import com.example.weatherapp.DataRequest.WeatherDailyDataVO;
import com.example.weatherapp.DataRequest.WeatherDailyVO;
import com.example.weatherapp.Faviorite.AutoCompleteAdapter;
import com.example.weatherapp.MyUtils.GsonUtils;
import com.example.weatherapp.Search.SearchActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mEntries = new ArrayList<>();
    private MainPagerAdapter mAdapter;
    private ViewPager mPager;
    private URLCollection APIs = new URLCollection();
    private AutoCompleteAdapter autoCompleteAdapter;
    public static final String locationTAG = "locationTAG";
    private FavoriteCityWeatherDTO currentItem = new FavoriteCityWeatherDTO();
    private ConstraintLayout spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        try {
            Thread.sleep(1000);
            getCurLoction();
        } catch (Exception e) {

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        // mAdapter = new MainPagerAdapter(this.getSupportFragmentManager());
        List<FavoriteCityWeatherDTO> list = getFavCitylist();
        mAdapter = MainPagerAdapter.getInstance(this.getSupportFragmentManager(), list);

        mPager.setAdapter(mAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mPager, true);
        spinner = findViewById(R.id.progressBar);
        // spinner.setVisibility(View.GONE);
        // getCurLoction();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    // Menu icons are inflated just as they were with actionbar
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                }
        );

        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        searchAutoComplete.setBackgroundColor(ContextCompat.getColor(this, R.color.color_background_grey));
        searchAutoComplete.setTextColor(Color.WHITE);

        searchAutoComplete.setDropDownBackgroundResource(R.color.colorPrimaryFont);

        // Create a new ArrayAdapter and add data to search auto complete object.
        final List<String> list = new ArrayList<>();
        autoCompleteAdapter = new AutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        autoCompleteAdapter.setData(list);
        searchAutoComplete.setThreshold(1);
        searchAutoComplete.setAdapter(autoCompleteAdapter);

        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString = (String) adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);
                // Toast.makeText(MainActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });

        // Below event is triggered when submit search query.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast.makeText(MainActivity.this, "you submit " + query, Toast.LENGTH_LONG).show();
                Intent searchIndent = new Intent();
                //SoilsenerActivity.class为想要跳转的Activity
                searchIndent.setClass(getApplicationContext(), SearchActivity.class);
                //                put some information
                // detailIntent.putExtra("firstKeyName", "you are a shit!");
                searchIndent.putExtra("searchCity", query);
                startActivity(searchIndent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Toast.makeText(MainActivity.this, "you change " + newText, Toast.LENGTH_LONG).show();
                callAutoComApi(newText, list);
                return false;
            }
        });


        return true;


    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private void addNewItem() {
        mEntries.add("new item");
        mAdapter.notifyDataSetChanged();
    }

    private void removeCurrentItem() {
        int position = mPager.getCurrentItem();
        mEntries.remove(position);
        mAdapter.notifyDataSetChanged();
    }


    public String getTextForPosition(int position) {
        return mEntries.get(position);
    }

    public int getCount() {
        return mEntries.size();
    }


    public void getCurLoction() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = APIs.getCurlocationAPI();
        Log.d(locationTAG, "get local api:" + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getWeatherData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(locationTAG, error.getMessage(), error);
            }
        });


        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    //    load the lat&lon data to get the weather infomation and city and state and country
    public void getWeatherData(JSONObject response) {
        String curCity;
        String curState;
        String curCountry;
        double curlat;
        double curlon;
        RequestQueue queue = Volley.newRequestQueue(this);
        String getWeatherUrl = APIs.getWeatherAPI();
        try {
            curCity = String.valueOf(response.get("city"));
            curState = String.valueOf(response.get("region"));
            curCountry = String.valueOf(response.get("countryCode"));
            curlat = (double) response.get("lat");
            curlon = (double) response.get("lon");
            getWeatherUrl += "lat=" + String.valueOf(curlat) + "&lng=" + String.valueOf(curlon);
            currentItem.setCityName(curCity);
            currentItem.setCountryName(curCountry);
            currentItem.setStateName(curState);
            currentItem.setLatitude(curlat);
            currentItem.setLongitude(curlon);
            Log.d(locationTAG, "getWeatherURL is: " + getWeatherUrl);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getWeatherUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Log.d(locationTAG, "getWeatherURL result is: "+response.toString());
                            setCurrentData(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(locationTAG, error.getMessage(), error);
                }
            });

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);

        } catch (JSONException e) {
            Log.e(locationTAG, "there is no data about lat and lon");
        }
    }

    public void setCurrentData(JSONObject response) {
        try {
            //            temText.setText(response.get("currently").toString());
            Log.d(locationTAG, "getWeatherURL result is:: " + response.get("currently").toString());

            WeatherCurrentlyVO currentlyVO = GsonUtils.getGsonParser().fromJson(response.get("currently").toString(), WeatherCurrentlyVO.class);
            WeatherDailyVO dailyVO = GsonUtils.getGsonParser().fromJson(response.get("daily").toString(), WeatherDailyVO.class);
            String timezone = response.get("timezone").toString();
            Log.d("timezone", "setCurrentData: "+timezone);
            // temText.setText(String.valueOf(vo.getTemperature()));
            // summaryText.setText(String.valueOf(vo.getSummary()));
            currentItem = loadDataToWeatherDTO(currentlyVO, dailyVO, timezone,currentItem);
            //put the current weather item to the pager adapter
            mAdapter.addNewFavourate(currentItem, true);
            spinner.setVisibility(View.GONE);
            // mAdapter.notifyDataSetChanged();
        } catch (JsonParseException e) {
            Log.e(locationTAG, "GSON parseE ERROR: " + e);
        } catch (NullPointerException e) {
            Log.e(locationTAG, "cannot get the main pager adapter ");
        } catch (Exception e) {
            Log.d(locationTAG, "get error in casting :" + e);
        }


    }

    private FavoriteCityWeatherDTO loadDataToWeatherDTO(WeatherCurrentlyVO currentlyVO, WeatherDailyVO dailyVO, String timezone, FavoriteCityWeatherDTO dto) {
        // Log.d(locationTAG, "WeatherCurrentlyVO is : "+currentlyVO.toString());
        // Log.d(locationTAG, "WeatherDailyVO is : "+dailyVO.toString());
        // Log.d(locationTAG, "FavoriteCityWeatherDTO is : "+dto.toString());

        dto.setTimeZone(timezone);
        //put currentlyVO infos
        dto.setSummaryIcon(convertIcon(currentlyVO.getIcon()));
        dto.setRawSummaryIcon(currentlyVO.getIcon());
        dto.setTemperature(Math.round(currentlyVO.getTemperature()));
        dto.setCurrentlySummary(currentlyVO.getSummary());
        dto.setHumidity(currentlyVO.getHumidity());
        dto.setWindSpeed((float) currentlyVO.getWindSpeed());
        dto.setVisablility((float) currentlyVO.getVisibility());
        dto.setPressure((float) currentlyVO.getPressure());
        dto.setCurrentlySummary(currentlyVO.getSummary());
        dto.setPrecipitation((float) currentlyVO.getPrecipIntensity());
        dto.setCloudCover((float) currentlyVO.getCloudCover());
        dto.setOzone((float) currentlyVO.getOzone());
        // put icon
        //put dailyvo infos
        dto.setDailySummary(dailyVO.getSummary());
        dto.setDailyIcon(convertIcon(dailyVO.getIcon()));
        List<DailyWeatherDTO> dailyWeatherDTOList = new ArrayList<>();
        DailyWeatherDTO dailyItem;
        for (WeatherDailyDataVO vo : dailyVO.getData()) {
            dailyItem = new DailyWeatherDTO();
            dailyItem.setUnixtime(vo.getTime());
            dailyItem.setIcon(convertIcon(vo.getIcon()));
            dailyItem.setHighTemperature(vo.getTemperatureHigh());
            dailyItem.setLowTemperature(vo.getTemperatureLow());
            //put one element to list
            dailyWeatherDTOList.add(dailyItem);
        }
        // put list to dto
        dto.setDailyWeatherDTOList(dailyWeatherDTOList);

        //return dto
        return dto;
    }

    //according the icontxt, return conspounding icon img
    private int convertIcon(String iconText) {
        Log.d(locationTAG, "the icontext is: " + iconText);
        int res;
        iconText = iconText.toLowerCase();
        switch (iconText) {
            case "clear-day":
                res = R.drawable.summary_weather_sunny;
                break;
            case "clear-night":
                res = R.drawable.summary_weather_night;
                break;
            case "rain":
                res = R.drawable.summary_weather_rainy;
                break;
            case "sleet":
                res = R.drawable.summary_weather_snowy_rainy;
                break;
            case "snow":
                res = R.drawable.summary_weather_snowy;
                break;
            case "wind":
                res = R.drawable.summary_weather_windy_variant;
                break;
            case "fog":
                res = R.drawable.summary_weather_fog;
                break;
            case "cloudy":
                res = R.drawable.summary_weather_cloudy;
                break;
            case "partly-cloudy-night":
                res = R.drawable.summary_weather_night_partly_cloudy;
                break;
            case "partly-cloudy-day":
                res = R.drawable.summary_weather_partly_cloudy;
                break;
            default:
                res = R.drawable.summary_weather_sunny;
        }
        return res;
    }

    private void callAutoComApi(String text, final List<String> list) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = APIs.getAutoCompeteAPI();
        url += "input=" + text;
        Log.d(locationTAG, "get auto complete api:" + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadAutoCompleteData(response, list);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(locationTAG, error.getMessage(), error);
            }
        });


        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void loadAutoCompleteData(JSONObject response, List<String> list) {
        try {
            list.clear();
            JsonArray jsonArray = new JsonParser().parse(response.get("predictions").toString()).getAsJsonArray();
            //将JSON的String 转成一个JsonArray对象

            for (JsonElement photoItem : jsonArray) {
                //使用GSON，直接转成Bean对象
                AutoCompleteVO userBean = GsonUtils.getGsonParser().fromJson(photoItem, AutoCompleteVO.class);
                list.add(userBean.getDescription());
                Log.d(locationTAG, "loadAutoCompleteData: " + list.toString());

            }
            autoCompleteAdapter.additem(list);
        } catch (JSONException e) {
            Log.e(locationTAG, "no element in json: ");
        }
    }

    private List<FavoriteCityWeatherDTO> getFavCitylist() {
        SharedPreferences sharelist = getSharedPreferences("FavcityList", MODE_PRIVATE);


        Map<String,?> cityMap = sharelist.getAll();
        if (cityMap == null || cityMap.size() == 0) {
            Log.d(locationTAG, "you are going to have the list: ");
        }
        Set<String> cityset = new HashSet<>();
        List<FavoriteCityWeatherDTO> reslist = new ArrayList<>();
        for (Map.Entry<String, ?> item : cityMap.entrySet()){

            FavoriteCityWeatherDTO favcityDto = GsonUtils.getGsonParser().fromJson(item.getValue().toString(), FavoriteCityWeatherDTO.class);
            // mAdapter.addNewFavourate(favcityDto, false);
            reslist.add(favcityDto);
        }
        return reslist;

        // mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharelist = getSharedPreferences("FavcityList", MODE_PRIVATE);
        sharelist.edit().remove("FavcityList").commit();

    }
}
