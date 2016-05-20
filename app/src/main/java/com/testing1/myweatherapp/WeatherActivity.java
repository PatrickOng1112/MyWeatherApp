package com.testing1.myweatherapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.testing1.myweatherapp.data.Channel;
import com.testing1.myweatherapp.service.MyWeatherAppWidgetConfigureActivity;
import com.testing1.myweatherapp.service.WeatherServiceCallBack;
import com.testing1.myweatherapp.service.YahooWeatherService;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallBack {

    ImageView weatherimageView;
    TextView tvTemperature, tvCondition, tvLocation;
    YahooWeatherService yahooWeatherService;
    ProgressDialog progressDialog;

    EditText etSearch;
    Button btSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherimageView = (ImageView) findViewById(R.id.weatherIconImageView);
        tvTemperature = (TextView) findViewById(R.id.temperature);
        tvCondition = (TextView) findViewById(R.id.condition);
        tvLocation = (TextView) findViewById(R.id.location);

        etSearch = (EditText) findViewById(R.id.et_search);
        btSearch = (Button) findViewById(R.id.bt_search);

        yahooWeatherService = new YahooWeatherService(WeatherActivity.this);
        yahooWeatherService.refreshWeather("Kuala Lumpur, Malaysia", "c");

        progressDialog = new ProgressDialog(WeatherActivity.this);
        progressDialog.setMessage("Loading Weather, Please Wait...");
        progressDialog.show();
    }

    public void widgetClick(View v)
    {
        Intent i = new Intent(WeatherActivity.this, MyWeatherAppWidgetConfigureActivity.class);
        startActivity(i);
    }



    @Override
    public void serviceSuccess(Channel channel) {
        progressDialog.dismiss();

        int resourceID = getResources().getIdentifier
                ("drawable/icon_" + channel.getItem().getCondition().getCode(), null, getPackageName());
        Drawable weatherIcon = getResources().getDrawable(resourceID);
        weatherimageView.setImageDrawable(weatherIcon);

        tvTemperature.setText(channel.getItem().getCondition().getTemperature() + "\u00B0 " + channel.getUnits().getTemperature());
        String city = channel.getLocation().getCity();
        String country = channel.getLocation().getCountry();
        tvLocation.setText(city + ", " + country);
        tvCondition.setText(channel.getItem().getCondition().getDescription());
    }

    @Override
    public void serviceFailure(Exception exception)
    {
        progressDialog.hide();
        Toast.makeText(WeatherActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuTemp:
                Toast.makeText(WeatherActivity.this, "Menu Temp", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuClose:
                Toast.makeText(WeatherActivity.this, "Menu Temp", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v)
    {
        final EditText locationStringInput = (EditText)findViewById(R.id.et_search);
        String etSearch = locationStringInput.getText().toString();
        if (etSearch.isEmpty())
        {

            Toast.makeText(getApplicationContext(), "Enter Place", Toast.LENGTH_SHORT).show();
        }
        else
        {
            yahooWeatherService = new YahooWeatherService(WeatherActivity.this);
            yahooWeatherService.refreshWeather(etSearch, "f");

            /*progressDialog = new ProgressDialog(WeatherActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();*/
        }
    }

}
