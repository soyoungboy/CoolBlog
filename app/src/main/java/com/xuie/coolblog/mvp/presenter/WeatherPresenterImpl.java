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

    private WeatherView mWeatherView;
    private WeatherModel mWeatherModel;
    private Context mContext;

    public WeatherPresenterImpl() {
        this.mContext = App.getContext();
        mWeatherModel = new WeatherModelImpl();
    }

    @Override
    public void loadWeatherData() {
        if (mWeatherView == null)
            return;
        mWeatherView.showProgress();
        if (!ToolsUtil.isNetworkAvailable(mContext)) {
            mWeatherView.hideProgress();
            mWeatherView.showErrorToast("无网络连接");
            return;
        }

        WeatherModelImpl.LoadLocationListener listener = new WeatherModelImpl.LoadLocationListener() {
            @Override
            public void onSuccess(String cityName) {
                //定位成功，获取定位城市天气预报
                if (mWeatherView == null)
                    return;
                mWeatherView.setCity(cityName);
                mWeatherModel.loadWeatherData(cityName, WeatherPresenterImpl.this);
            }

            @Override
            public void onFailure(Exception e) {
                if (mWeatherView == null)
                    return;
                mWeatherView.showErrorToast("定位失败");
                mWeatherView.setCity("深圳");
                mWeatherModel.loadWeatherData("深圳", WeatherPresenterImpl.this);
            }
        };
        //获取定位信息
        mWeatherModel.loadLocation(mContext, listener);
    }

    @Override
    public void setView(WeatherView view) {
        mWeatherView = view;
    }

    @Override
    public void clearView() {
        mWeatherView = null;
    }

    @Override
    public void onSuccess(List<WeatherBean> list) {
        if (mWeatherView == null)
            return;
        if (list != null && list.size() > 0) {
            WeatherBean todayWeather = list.remove(0);
            mWeatherView.setToday(todayWeather.getDate());
            mWeatherView.setTemperature(todayWeather.getTemperature());
            mWeatherView.setWeather(todayWeather.getWeather());
            mWeatherView.setWind(todayWeather.getWind());
            mWeatherView.setWeatherImage(todayWeather.getImageRes());
        }
        mWeatherView.setWeatherData(list);
        mWeatherView.hideProgress();
        mWeatherView.showWeatherLayout();
    }

    @Override
    public void onFailure(Exception e) {
        if (mWeatherView == null)
            return;
        mWeatherView.hideProgress();
        mWeatherView.showErrorToast("获取天气数据失败");
    }

}
