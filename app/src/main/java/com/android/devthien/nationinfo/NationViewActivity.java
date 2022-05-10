package com.android.devthien.nationinfo;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.devthien.nationinfo.models.NationModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NationViewActivity extends AppCompatActivity {
    Gson gson = new Gson();
    ImageView imageMap;
    ImageView imageFlag;
    TextView txtCountryName;
    TextView txtCapitalName;
    TextView txtContinent;
    TextView txtPopulation;
    TextView txtArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nation_view);
        imageMap = findViewById(R.id.nationMap);
        imageFlag = findViewById(R.id.nationFlag);
        txtCountryName = findViewById(R.id.countryName);
        txtCapitalName = findViewById(R.id.capitalName);
        txtContinent = findViewById(R.id.continent);
        txtPopulation = findViewById(R.id.population);
        txtArea = findViewById(R.id.areaKm);

        setData();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void setData(){
        NumberFormat dfPopular = new DecimalFormat("###,000");
        NumberFormat  dfKm = new DecimalFormat("#,###,###,###.### km²");
        Intent intent = getIntent();
        NationModel nationModel = (NationModel) intent.getSerializableExtra("nation");
        Log.d(TAG, "onCreate: "+gson.toJson(nationModel));
        Picasso.with(NationViewActivity.this).load(nationModel.getUrlFlag()).into(imageFlag);
        Picasso.with(NationViewActivity.this).load(nationModel.getUrlMap()).into(imageMap);
        txtCountryName.setText(nationModel.getCountryName());
        txtCapitalName.setText(nationModel.getCapital());
        txtContinent.setText(nationModel.getContinentName());
        txtPopulation.setText(dfPopular.format(Integer.parseInt(nationModel.getPopulation())));
        txtArea.setText(dfKm.format(Double.parseDouble(nationModel.getAreaInSqKm())));
    }
}