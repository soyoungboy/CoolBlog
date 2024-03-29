package com.xuie.coolblog.ui.activity.main.mvp;

import com.xuie.coolblog.R;

public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.nav_blog:
            default:
                mMainView.switch2Blog();
                break;
            case R.id.nav_image:
                mMainView.switch2Images();
                break;
            case R.id.nav_weather:
                mMainView.switch2Weather();
                break;
            case R.id.nav_about:
                mMainView.switch2About();
                break;
        }
    }
}
