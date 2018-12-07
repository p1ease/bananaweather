package com.banana.bananaweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by djd000 on 2018/11/30.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;
    public class Update{

        @SerializedName("loc")
        public String updateTime;
    }

}
/*
"basic": {
			"cid": "CN101270401",
			"location": "绵阳",
			"parent_city": "绵阳",
			"admin_area": "四川",
			"cnty": "中国",
			"lat": "31.46401978",
			"lon": "104.74172211",
			"tz": "+8.00",
			"city": "绵阳",
			"id": "CN101270401",
			"update": {
				"loc": "2018-11-30 14:45",
				"utc": "2018-11-30 06:45"
			}
*/