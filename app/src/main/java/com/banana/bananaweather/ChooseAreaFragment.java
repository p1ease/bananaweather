package com.banana.bananaweather;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.bananaweather.db.City;
import com.banana.bananaweather.db.County;
import com.banana.bananaweather.db.Province;
import com.banana.bananaweather.until.HttpUtil;
import com.banana.bananaweather.until.Utility;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by djd000 on 2018/11/21.
 */

public class ChooseAreaFragment extends Fragment {
    public static final String TAG = "djd + ChooseAreaFragment";
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    public ProgressDialog progressDialog;
    private TextView titleText;
    private Button backbutton;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> datalist = new ArrayList<>();
    private List<Province> provinceList ;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectProvince;
    private City selectCity;
    private County selectCounty;
    private int currentLevel;



    //当activity要得到fragment的layout时，调用此方法，fragment在其中创建自己的layout(界面)。
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG,"djd +   onCreateView");
        View view = inflater.inflate(R.layout.choose_area,container,false);//!!!
        titleText = (TextView) view.findViewById(R.id.title_text);
        backbutton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,datalist);
        listView.setAdapter(arrayAdapter);
        return view;
    }

    //当activity的onCreated()方法返回后调用此方法
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,"djd +   onActivityCreated");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentLevel == LEVEL_PROVINCE){
                    selectProvince = provinceList.get(i);
                    queryCitys();
                }else if(currentLevel == LEVEL_CITY){
                    selectCity = cityList.get(i);
                    queryCounty();
                }
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel == LEVEL_COUNTY){
                    queryCitys();
                }else if(currentLevel == LEVEL_CITY){
                    queryProvince();
                }
            }
        });
        queryProvince();
    }

    private void queryProvince() {
        Log.i(TAG,"djd +  queryProvince");
        titleText.setText("中国");
        backbutton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if(provinceList.size()>0)
        {
            datalist.clear();
            for(Province province:provinceList)
            {
                datalist.add(province.getProvinceName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }else{
            String address = "https://guolin.tech/api/china";
            Log.i(TAG,address);
            queryFromServer(address,"province");
        }
    }

    private void queryCitys(){
        Log.i(TAG,"djd +  queryCity");
        titleText.setText(selectProvince.getProvinceName());
        backbutton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid = ?",String.valueOf(selectProvince.getId())).find(City.class);
        if (cityList.size()>0){
            datalist.clear();
            for(City city : cityList){
                datalist.add(city.getCityName());
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        }else{
            int provinceCode = selectProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            Log.i(TAG,address);
            queryFromServer(address,"city");
        }
    }

    private void queryCounty(){
        Log.i(TAG,"djd +  queryCounty");
        titleText.setText(selectCity.getCityName());
        backbutton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?",String.valueOf(selectCity.getId())).find(County.class);
        if(countyList.size()>0){
            Log.i(TAG,"size > 0 ");
          datalist.clear();
          for(County county :countyList){
              datalist.add(county.getCountyName());
          }
          arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }else{
            int provinceCode = selectProvince.getProvinceCode();
            int cityCode = selectCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" +cityCode;
            Log.i(TAG,address);
            queryFromServer(address,"county");
        }


    }

    private void queryFromServer(String address,final String type) {
        showProgressDialog();
        Log.i(TAG,"djd +  queryFromServer");
        HttpUtil.sendOkHttpRequeset(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,"djd +  failed");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"djd +  Response");
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(responseText, selectProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountyResponse(responseText, selectCity.getId());
                }
                Log.i(TAG,type+"  "+result);
                if (!result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvince();
                            } else if ("city".equals(type)) {
                                queryCitys();
                            } else if ("county".equals(type)) {
                                queryCounty();
                            }
                        }
                    });
                }
            }
        });

    }


    private void showProgressDialog(){
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}