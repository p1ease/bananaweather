package com.banana.bananaweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by djd000 on 2018/11/30.
 */

public class Suggestion {

    @SerializedName("comf")
    public Comf comf;

    @SerializedName("cw")
    public Cw cw;

    @SerializedName("sport")
    public Sport sport;

    public class Comf{
        public String txt;
    }
    public class Cw{
        public String txt;
    }
    public class Sport{
        public String txt;
    }
}
/*
	"suggestion": {
			"comf": {
				"type": "comf",
				"brf": "舒适",
				"txt": "白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"
			},
			"sport": {
				"type": "sport",
				"brf": "适宜",
				"txt": "天气较好，赶快投身大自然参与户外运动，尽情感受运动的快乐吧。"
			},
			"cw": {
				"type": "cw",
				"brf": "不宜",
				"txt": "不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"
			}
		}
 */