package com.banana.bananaweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by djd000 on 2018/11/30.
 */

public class AQI {
    @SerializedName("city")
    public AQICity aqiCity;


    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
/*
    "aqi": {
			"city": {
				"aqi": "150",
				"pm25": "115",
				"qlty": "轻度污染"
			}
		}
*/