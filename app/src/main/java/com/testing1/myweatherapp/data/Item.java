package com.testing1.myweatherapp.data;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by USer on 18/5/2016.
 */
public class Item implements JSONPopulator
{
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }


    @Override
    public void populate(JSONObject data)
    {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
