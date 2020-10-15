package com.example.weatherapp.Detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.DataRequest.FavoriteCityWeatherDTO;
import com.example.weatherapp.DataRequest.ScenePhotoVO;
import com.example.weatherapp.DataRequest.URLCollection;
import com.example.weatherapp.Detail.photoRecyclerAdapter;
import com.example.weatherapp.MyUtils.GsonUtils;
import com.example.weatherapp.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class photos_fragment extends Fragment {


    private URLCollection APIs = new URLCollection();
    private FavoriteCityWeatherDTO favoriteCityWeatherDTO;
    public static final String photos_fragmentTAG = "photos_fragmentTAG";
    private ArrayList<String> urlsList;
    private photoRecyclerAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView= (ViewGroup)inflater.inflate(R.layout.photos_fragment, container, false);

        String WeatherJsonString = (String) getArguments().getString("FavCityWethVO");
        urlsList = getArguments().getStringArrayList("urlslist");
        favoriteCityWeatherDTO = GsonUtils.getGsonParser().fromJson(WeatherJsonString, FavoriteCityWeatherDTO.class);
        // // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.photosTabRecyclerView);

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 3. create an adapter
        // loadPhoto();
        mAdapter = new photoRecyclerAdapter(this.getContext());
        mAdapter.addList(urlsList);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private void loadPhoto(){
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url = APIs.getSceneAPI();
        url+="city="+favoriteCityWeatherDTO.getCityName();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        convertdata(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(photos_fragmentTAG, error.getMessage(), error);
            }
        });


        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    private void convertdata(JSONObject response){
        String strByJson = "";
        try {
            strByJson= response.get("items").toString();
       }
       catch(JSONException e){
           Log.e(photos_fragmentTAG, "there is no items in custom search resumlt, please re-check! " );
       }

        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();


        urlsList = new ArrayList<>();

        //加强for循环遍历JsonArray
        for (JsonElement photoItem : jsonArray) {
            //使用GSON，直接转成Bean对象
            ScenePhotoVO userBean =  GsonUtils.getGsonParser().fromJson(photoItem, ScenePhotoVO.class);
            urlsList.add(userBean.getLink());

        }
        mAdapter.addList(urlsList);

    }

}
