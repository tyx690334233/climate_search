package com.example.hw9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class act_detail extends AppCompatActivity {

    private RequestQueue mQueue;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    adapter_deatil pageAdapter;
    JSONObject jsonObject;
    String cityName;
    String [] linkArray;
    String temperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_lay);

        String sessionId = getIntent().getStringExtra("json");
        cityName = getIntent().getStringExtra("city");

        try {
            jsonObject = new JSONObject(sessionId);
            JSONObject currently = new JSONObject(jsonObject.getString("currently"));
            temperature = currently.getString("temperature");
            Double temTem= Double.parseDouble(temperature);
            int a = (int) Math.round(temTem);
            temperature = a +"";

        } catch (JSONException e) {
            e.printStackTrace();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(cityName);

        pageAdapter = new adapter_deatil(getSupportFragmentManager());
        pageAdapter.addFragment(new frag_tab_today(),"TODAY");
        pageAdapter.addFragment(new frag_tab_weekly(),"WEEKLY");
        pageAdapter.addFragment(new frag_tab_photos(), "PHOTOS");

        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.calendar_today);
        tabLayout.getTabAt(1).setIcon(R.drawable.trending_up);
        tabLayout.getTabAt(2).setIcon(R.drawable.google_photos);


        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                tab.getIcon().setColorFilter(getColor(R.color.textColor), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                tab.getIcon().setColorFilter(getColor(R.color.summary), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }

        });

        mQueue = Volley.newRequestQueue(this);
        getLink(cityName);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public JSONObject setData(){
        return jsonObject;
    }

    public String [] setLink(){
        return linkArray;
    }

    private void getLink(String cityName){
        String url = "https://www.googleapis.com/customsearch/v1?q=" + cityName + "&cx=011217509522821264725:imlsmqv57q2&num=8&searchType=image&key=AIzaSyCaSLbZLGkJsHQmRINCxTzhhDHpY4-et_Y";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                linkArray = new String[8];
                try {
                    JSONObject obj = response;
                    JSONArray data = obj.getJSONArray("items");
                    for(int i = 0 ; i < 8; i++){
                        String tem = data.getJSONObject(i).getString("link");
                        linkArray[i] = tem;
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String content = "Check Out " + cityName + "'s Weather! It is " + temperature + (char) 0x00B0 +"F! %23CSCI571 Weather Search";
        if(id == R.id.action_search){
            String url = "http://www.twitter.com/intent/tweet?text=" + content;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}

