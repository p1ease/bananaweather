package com.banana.bananaweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by djd000 on 2018/11/30.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More{

        @SerializedName("txt")
        public  String info;

    }
}
/*
"now": {
			"cloud": "59",
			"cond_code": "100",
			"cond_txt": "晴",
			"fl": "12",
			"hum": "79",
			"pcpn": "0.0",
			"pres": "1022",
			"tmp": "12",
			"vis": "3",
			"wind_deg": "267",
			"wind_dir": "西风",
			"wind_sc": "1",
			"wind_spd": "2",
			"cond": {
				"code": "100",
				"txt": "晴"
			}
		}
 */