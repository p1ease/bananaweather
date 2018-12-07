package com.banana.bananaweather.until;

import android.text.TextUtils;
import android.util.Log;

import com.banana.bananaweather.db.City;
import com.banana.bananaweather.db.County;
import com.banana.bananaweather.db.Province;
import com.banana.bananaweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by djd000 on 2018/11/20.
 */

public class Utility {
    public static String TAG = "djd + Utility";
    public static boolean handleProvinceResponse(String response)
    {
        Log.i(TAG,response);
        if(!TextUtils.isEmpty(response)) {
            try{
                JSONArray allProvinces = new JSONArray(response);
                for(int i = 0;i < allProvinces.length();i++) {
                    JSONObject jsonObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(jsonObject.getString("name"));
                    province.setProvinceCode(jsonObject.getInt("id"));
                    province.save();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean handleCityResponse(String response,int provinceId){
        Log.i(TAG,response);
        try{
            if(!TextUtils.isEmpty(response)){
                JSONArray jsonArray = new JSONArray(response);
                for(int i = 0;i < jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(jsonObject.getInt("id"));
                    city.setCityName(jsonObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                    Log.i(TAG,city.getCityName()+" save successful");
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean handleCountyResponse(String response,int cityId){
        try{
            Log.i(TAG,response);
            if(!TextUtils.isEmpty(response)){
                JSONArray jsonArray = new JSONArray(response);
                for(int i = 0;i < jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    County county = new County();
                    county.setCityId(cityId);
                    county.setCountyName(jsonObject.getString("name"));
                    county.setId(jsonObject.getInt("id"));
                    county.setWeatherId(jsonObject.getString("weather_id"));
                    county.save();
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return false;
    }

    public static Weather handleWeatherResponse(String response){
        try{
            Log.i(TAG,"response + "+response);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
