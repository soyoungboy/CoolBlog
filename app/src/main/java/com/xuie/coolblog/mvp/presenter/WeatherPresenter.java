package com.xuie.coolblog.mvp.presenter;

import com.xuie.coolblog.mvp.view.WeatherView;

public interface WeatherPresenter {

    void loadWeatherData();

    void setView(WeatherView view);

    void clearView();

}
