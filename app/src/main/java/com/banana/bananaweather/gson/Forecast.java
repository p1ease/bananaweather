package com.banana.bananaweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by djd000 on 2018/11/30.
 */

public class Forecast {
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature{
        public String max;
        public String min;
    }

    public class More{
        public String txt_d;
    }
}
/*
	"daily_forecast": [{
			"date": "2018-11-30",
			"cond": {
				"txt_d": "多云"
			},
			"tmp": {
				"max": "17",
				"min": "9"
			}
		}, {
			"date": "2018-12-01",
			"cond": {
				"txt_d": "小雨"
			},
			"tmp": {
				"max": "15",
				"min": "9"
			}
		}, {
			"date": "2018-12-02",
			"cond": {
				"txt_d": "小雨"
			},
			"tmp": {
				"max": "16",
				"min": "9"
			}
		}],
		"aqi": {
			"city": {
				"aqi": "150",
				"pm25": "115",
				"qlty": "轻度污染"
			}
		},
 */