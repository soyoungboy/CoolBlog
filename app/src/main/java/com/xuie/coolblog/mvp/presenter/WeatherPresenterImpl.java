package com.xuie.coolblog.mvp.presenter;

import android.content.Context;

import com.xuie.coolblog.App;
import com.xuie.coolblog.bean.WeatherBean;
import com.xuie.coolblog.mvp.model.WeatherModel;
import com.xuie.coolblog.mvp.model.WeatherModelImpl;
import com.xuie.coolblog.mvp.view.WeatherView;
import com.xuie.coolblog.utils.ToolsUtil;

import java.util.List;

public class WeatherPresenterImpl implements WeatherPresenter, WeatherModelImpl.LoadWeatherListener {
    WeatherView weatherView;
    WeatherModel weatherModel;
    Context context;

    public WeatherPresenterImpl(WeatherView weatherView) {
        this.weatherView = weatherView;
        this.weatherModel = new WeatherModelImpl();
        this.context = App.getContext();
    }

    @Override
    public void loadWeatherData() {
        if (weatherView == null)
            return;
        weatherView.showProgress();
        if (!ToolsUtil.isNetworkAvailable(context)) {
            weatherView.hideProgress();
            weatherView.showErrorToast("无网络连接");
            return;
        }

        WeatherModelImpl.LoadLocationListener listener = new WeatherModelImpl.LoadLocationListener() {
            @Override
            public void onSuccess(String cityName) {
                //定位成功，获取定位城市天气预报
                if (weatherView == null)
                    return;
                weatherView.setCity(cityName);
                weatherModel.loadWeatherData(cityName, WeatherPresenterImpl.this);
            }

            @Override
            public void onFailure(Exception e) {
                if (weatherView == null)
                    return;
                weatherView.showErrorToast("定位失败");
                weatherView.setCity("深圳");
                weatherModel.loadWeatherData("深圳", WeatherPresenterImpl.this);
            }
        };
        //获取定位信息
        weatherModel.loadLocation(context, listener);
    }

    @Override
    public void onSuccess(List<WeatherBean> list) {
        if (weatherView == null)
            return;
        if (list != null && list.size() > 0) {
            WeatherBean todayWeather = list.remove(0);
            weatherView.setToday(todayWeather.getDate());
            weatherView.setTemperature(todayWeather.getTemperature());
            weatherView.setWeather(todayWeather.getWeather());
            weatherView.setWind(todayWeather.getWind());
            weatherView.setWeatherImage(todayWeather.getImageRes());
        }
        weatherView.setWeatherData(list);
        weatherView.hideProgress();
        weatherView.showWeatherLayout();
    }

    @Override
    public void onFailure(Exception e) {
        if (weatherView == null)
            return;
        weatherView.hideProgress();
        weatherView.showErrorToast("获取天气数据失败");
    }

}
