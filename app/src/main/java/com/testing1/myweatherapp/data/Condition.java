package com.testing1.myweatherapp.data;

import org.json.JSONObject;

/**
 * Created by USer on 18/5/2016.
 */
public class Condition implements JSONPopulator
{
    private int code;
    private int temperature;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void populate(JSONObject jsonObject)
    {
        code = jsonObject.optInt("code");
        temperature = jsonObject.optInt("temp");
        description = jsonObject.optString("text");
    }
}
