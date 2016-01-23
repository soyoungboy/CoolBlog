package com.xuie.coolblog.mvp.view.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xuie.coolblog.R;
import com.xuie.coolblog.bean.WeatherBean;
import com.xuie.coolblog.mvp.presenter.WeatherPresenter;
import com.xuie.coolblog.mvp.presenter.WeatherPresenterImpl;
import com.xuie.coolblog.mvp.view.WeatherView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements WeatherView {
    WeatherPresenter mWeatherPresenter;

    @Bind(R.id.city)
    TextView city;
    @Bind(R.id.today)
    TextView today;
    @Bind(R.id.weatherImage)
    ImageView weatherImage;
    @Bind(R.id.weatherTemp)
    TextView weatherTemp;
    @Bind(R.id.wind)
    TextView wind;
    @Bind(R.id.weather)
    TextView weather;
    @Bind(R.id.weather_content)
    LinearLayout weatherContent;
    @Bind(R.id.weather_layout)
    LinearLayout weatherLayout;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.root_layout)
    FrameLayout rootLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherPresenter = new WeatherPresenterImpl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        mWeatherPresenter.setView(this);
        mWeatherPresenter.loadWeatherData();
        return view;
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showWeatherLayout() {
        weatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCity(String city) {
        this.city.setText(city);
    }

    @Override
    public void setToday(String data) {
        today.setText(data);
    }

    @Override
    public void setTemperature(String temperature) {
        weatherTemp.setText(temperature);
    }

    @Override
    public void setWind(String wind) {
        this.wind.setText(wind);
    }

    @Override
    public void setWeather(String weather) {
        this.weather.setText(weather);
    }

    @Override
    public void setWeatherImage(int res) {
        weatherImage.setImageResource(res);
    }

    @Override
    public void setWeatherData(List<WeatherBean> lists) {
        List<View> adapterList = new ArrayList<View>();
        for (WeatherBean weatherBean : lists) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_weather, null, false);
            TextView dateTV = (TextView) view.findViewById(R.id.date);
            ImageView todayWeatherImage = (ImageView) view.findViewById(R.id.weatherImage);
            TextView todayTemperatureTV = (TextView) view.findViewById(R.id.weatherTemp);
            TextView todayWindTV = (TextView) view.findViewById(R.id.wind);
            TextView todayWeatherTV = (TextView) view.findViewById(R.id.weather);

            dateTV.setText(weatherBean.getWeek());
            todayTemperatureTV.setText(weatherBean.getTemperature());
            todayWindTV.setText(weatherBean.getWind());
            todayWeatherTV.setText(weatherBean.getWeather());
            todayWeatherImage.setImageResource(weatherBean.getImageRes());
            weatherContent.addView(view);
            adapterList.add(view);
        }
    }

    @Override
    public void showErrorToast(String msg) {
        Snackbar.make(getActivity().findViewById(R.id.drawer_layout), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mWeatherPresenter.clearView();
    }
}

