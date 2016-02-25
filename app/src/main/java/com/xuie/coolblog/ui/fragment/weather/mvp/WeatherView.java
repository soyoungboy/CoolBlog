package com.xuie.coolblog.ui.fragment.weather.mvp;

import com.xuie.coolblog.ui.fragment.weather.WeatherBean;

import java.util.List;

public interface WeatherView {

    void showProgress();

    void hideProgress();

    void showWeatherLayout();

    void setCity(String city);

    void setToday(String data);

    void setTemperature(String temperature);

    void setWind(String wind);

    void setWeather(String weather);

    void setWeatherImage(int res);

    void setWeatherData(List<WeatherBean> lists);

    void showErrorToast(String msg);
}
