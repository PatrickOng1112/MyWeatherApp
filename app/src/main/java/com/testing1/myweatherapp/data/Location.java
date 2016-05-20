package com.testing1.myweatherapp.data;

import org.json.JSONObject;

/**
 * Created by USer on 18/5/2016.
 */
public class Location implements JSONPopulator
{
    private String city;
    private String country;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public void populate(JSONObject data)
    {
        city = data.optString("city");
        country = data.optString("country");
    }
}
