package com.testing1.myweatherapp.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.testing1.myweatherapp.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by USer on 18/5/2016.
 */
public class YahooWeatherService
{
    private WeatherServiceCallBack weatherServiceCallBack;
    private String location;
    private Exception error;

    public YahooWeatherService(WeatherServiceCallBack weatherServiceCallBack)
    {
        this.weatherServiceCallBack = weatherServiceCallBack;
    }

    public String getLocation()
    {
        return location;
    }

    public void refreshWeather(String l, final String type)
    {
        this.location = l;
        new AsyncTask<String, Void, String>()
        {
            @Override
            protected String doInBackground(String... params)
            {
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='"+type+"'", params[0]);
                String endPoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
                try
                {
                    URL url = new URL(endPoint);
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                    {
                        result.append(line);
                    }
                    return result.toString();
                }
                catch (MalformedURLException e)
                {
                    error = e;
                    return null;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s)
            {
                if (s == null && error != null)
                {
                    weatherServiceCallBack.serviceFailure(error);
                    return;
                }
                try
                {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject queryResults = jsonObject.optJSONObject("query");
                    int count = queryResults.optInt("count");
                    if (count == 0)
                    {
                        weatherServiceCallBack.serviceFailure(new LocationWeatherException("No Weather data found for " + location));
                    }
                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));
                    weatherServiceCallBack.serviceSuccess(channel);
                }
                catch (JSONException e)
                {
                    weatherServiceCallBack.serviceFailure(error);
                }
            }
        }.execute(location);
    }

    public class LocationWeatherException extends  Exception
    {
        public LocationWeatherException(String detailMessage)
        {
            super(detailMessage);
        }
    }
}
