package com.example.duval.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

public class Change_city extends AppCompatActivity {
    ListView listCity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_city);

        listCity = (ListView)findViewById(R.id.listCity);

    }

    public void clickAddCity(View view){
        Intent intent = new Intent(Change_city.this, Add_city.class);
        startActivity(intent);
    }
}
