package com.testing1.myweatherapp.service;

import com.testing1.myweatherapp.data.Channel;

/**
 * Created by USer on 18/5/2016.
 */
public interface WeatherServiceCallBack
{
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
