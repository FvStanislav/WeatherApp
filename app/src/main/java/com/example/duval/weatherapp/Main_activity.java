package com.example.duval.weatherapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.duval.weatherapp.Common.Common;
import com.example.duval.weatherapp.Helper.Helper;
import com.example.duval.weatherapp.Model.Coord;
import com.example.duval.weatherapp.Model.Main;
import com.example.duval.weatherapp.Model.OpenWeatherMap;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;


import java.lang.reflect.Type;



public class Main_activity extends AppCompatActivity implements LocationListener {
    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius;
    ImageView imageView;

    LocationManager locationManager;
    String provider;
    static double lat, lng;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lat = 43.12;
        lng = 37.61;



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        txtCity = (TextView) findViewById(R.id.txtCity);
        txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtCelsius = (TextView) findViewById(R.id.txtCelsius);
        imageView = (ImageView) findViewById(R.id.ImageView);







        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        Location location = locationManager.getLastKnownLocation(provider);
        if(location == null){
            Log.e("TAG", "No location");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        new GetWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void clickMoreInfo(View view) {
        Intent intent = new Intent(Main_activity.this, More_info.class);
        startActivity(intent);
    }

    public void clickChangeCity(View view) {
        Intent intent = new Intent(Main_activity.this, Change_city.class);
        startActivity(intent);
    }

    private class GetWeather extends AsyncTask<String, Void, String>{
        ProgressDialog pd = new ProgressDialog(Main_activity. this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String stream = null;
            String urlString = params[0];

            Helper http = new Helper();
            stream = http.getHttpData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.contains("Error: Not found city")){
                pd.dismiss();
                return;
            }

            ProgressDialog pd = new ProgressDialog(Main_activity. this);
            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s, mType);
            pd.dismiss();

            txtCity.setText(String.format("%s, %s", openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
            txtLastUpdate.setText(String.format("LastUpdate, %s", Common.getDateNow()));
            txtDescription.setText(String.format("%s", openWeatherMap.getWeather().get(0).getDescription()));
            txtHumidity.setText(String.format("%s", openWeatherMap.getMain().getHumidity()));
            txtTime.setText(String.format("%s/%s", Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()),  Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
            txtCelsius.setText(String.format("%, 2f, °C", openWeatherMap.getMain().getTemp()));
            Picasso.with(Main_activity.this).load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon())).into(imageView);



        }
    }


}
