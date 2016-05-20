package com.testing1.myweatherapp.data;

import org.json.JSONObject;

/**
 * Created by USer on 18/5/2016.
 */
public class Channel implements  JSONPopulator
{
    private Item item;
    private Units units;
    private Location location;


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void populate(JSONObject jsonObject)
    {
        units = new Units();
        units.populate(jsonObject.optJSONObject("units"));

        item = new Item();
        item.populate(jsonObject.optJSONObject("item"));

        location = new Location();
        location.populate(jsonObject.optJSONObject("location"));

    }
}
