package com.android.devthien.nationinfo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.devthien.nationinfo.apis.ApiServices;
import com.android.devthien.nationinfo.core.Constant;
import com.android.devthien.nationinfo.core.Loading;
import com.android.devthien.nationinfo.databinding.ActivityMainBinding;
import com.android.devthien.nationinfo.models.NationModel;
import com.android.devthien.nationinfo.models.ResponseModel;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private AdapterListView adapterListView;
    private ActivityMainBinding binding;
    private Loading loading;

    private List<NationModel> nationModelList;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        nationModelList = new ArrayList<>();
        loading = new Loading(MainActivity.this);
        listView = findViewById(R.id.list_item_image);
        adapterListView = new AdapterListView(nationModelList, MainActivity.this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDetail(i);
            }
        });
        listView.setAdapter(adapterListView);
        getApi();
    }
    private void showDetail(int i) {
        Intent intent = new Intent(MainActivity.this, NationViewActivity.class);
//        String nation = gson.toJson(nationModelList.get(i));
        intent.putExtra("nation", nationModelList.get(i));
        startActivity(intent);
    }

    private void convertNationListTofull(){
        for (NationModel s: nationModelList) {
            s.setUrlFlag(Constant.API_FLAG_URL+s.getCountryCode().toLowerCase(Locale.ROOT)+".gif");
            s.setUrlMap(Constant.API_MAP_URL+s.getCountryCode()+".png");
        }
    }

    private void getApi(){
        loading.startLoading();
        //link api: countryInfoJSON?formatted=true&lang=it&username=thienv29&style=full&fbclid=IwAR0-M-iwVsSj0OZwULO9428GiGzxgAoZGb0oyiQLiFFLpjRtoNC0LR7jloI
        ApiServices.apiServices.getListNation("true","it","thienv29","full","IwAR0-M-iwVsSj0OZwULO9428GiGzxgAoZGb0oyiQLiFFLpjRtoNC0LR7jloI").enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel nationModels = response.body();
                nationModelList = nationModels.geonames;
                convertNationListTofull();
                adapterListView.updateReceiptsList(nationModelList);

                Log.d(TAG, "onResponse: "+gson.toJson(nationModelList));
                Toast.makeText(MainActivity.this, "call api success", Toast.LENGTH_SHORT).show();
                loading.dismissDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "call api error", Toast.LENGTH_LONG).show();
                loading.dismissDialog();
            }
        });
    }

}