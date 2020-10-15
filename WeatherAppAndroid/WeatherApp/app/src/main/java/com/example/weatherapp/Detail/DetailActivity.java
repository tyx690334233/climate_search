package com.example.weatherapp.Detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.DataRequest.FavoriteCityWeatherDTO;
import com.example.weatherapp.DataRequest.ScenePhotoVO;
import com.example.weatherapp.DataRequest.URLCollection;
import com.example.weatherapp.MyUtils.GsonUtils;
import com.example.weatherapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String DetailActivityTAG = "DetailActivityTAG";
    private FavoriteCityWeatherDTO weatherDTO;
    private ConstraintLayout spinner;
    private URLCollection APIs = new URLCollection();
    private detailPagerAdapter detailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent getDetaildIntent = getIntent();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        String detailInfoText = getDetaildIntent.getStringExtra("weatherDetail");
        Log.d(DetailActivityTAG, "onCreate: detail" + detailInfoText);
        weatherDTO = GsonUtils.getGsonParser().fromJson(detailInfoText, FavoriteCityWeatherDTO.class);
        // TextView textView = findViewById(R.id.name_space);
        // textView.setText(detailInfoText);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.detail_tabs);
        loadPhoto();
        if (weatherDTO.getStateName().equals("")){
            getSupportActionBar().setTitle(weatherDTO.getCityName()+", "+weatherDTO.getCountryName());
        }
        else{
            getSupportActionBar().setTitle(weatherDTO.getCityName()+", "+weatherDTO.getStateName()+", "+weatherDTO.getCountryName());
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置adapter
        ViewPager viewPager = (ViewPager) findViewById(R.id.detail_slide_pager);

        detailAdapter = new detailPagerAdapter(getSupportFragmentManager(), 3);
        detailAdapter.setDTOText(detailInfoText);
        viewPager.setAdapter(detailAdapter);



        // tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);

                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryFont), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                tab.getIcon().setColorFilter(getResources().getColor(R.color.color_font_grey), PorterDuff.Mode.SRC_IN);
            }

        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detail_actionbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.twittershare) {
            // Toast.makeText(DetailActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            Intent tweetShare = new Intent(Intent.ACTION_VIEW);

            // String message = "The current temperature at " + weatherDTO.getCityName() + " is " + this.tempreture + ". The weather conditions are " + this.summary + "&hashtags=CSCI571WeatherSearch";;
            String message = "Check Out "+Uri.encode(weatherDTO.getCityName()+","+weatherDTO.getStateName()+","+weatherDTO.getCountryName())+"’s Weather! It is "+Math.round(weatherDTO.getTemperature())+"°F!" +
                    "&hashtags=CSCI571WeatherSearch";
            tweetShare.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + message));//where message is your string messageaa
            // Log.d(DetailActivityTAG, "twitter: "+message);
            startActivity(tweetShare);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }
    private void loadPhoto(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = APIs.getSceneAPI();
        url+="city="+Uri.encode(weatherDTO.getCityName());
        // Log.d(" strByJson", "url is: "+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        convertdata(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(DetailActivityTAG, error.getMessage(), error);
            }
        });


        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void convertdata(JSONObject response){
        if(response == null){
            return;
        }
        String strByJson = "";
        try {
            strByJson= response.get("items").toString();
            if(strByJson == null){
                return;
            }
        }
        catch(JSONException e){
            Log.e(DetailActivityTAG, "there is no items in custom search resumlt, please re-check! " );
        }

        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();


        ArrayList<String> urlsList = new ArrayList<>();

        //加强for循环遍历JsonArray
        for (JsonElement photoItem : jsonArray) {
            //使用GSON，直接转成Bean对象
            ScenePhotoVO userBean =  GsonUtils.getGsonParser().fromJson(photoItem, ScenePhotoVO.class);
            urlsList.add(userBean.getLink());

        }
        detailAdapter.setpicUrls(urlsList);
        spinner.setVisibility(View.GONE);

    }
}
