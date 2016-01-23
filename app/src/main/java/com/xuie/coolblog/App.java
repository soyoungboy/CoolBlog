package com.xuie.coolblog;

import android.app.Application;

/**
 * Created by xuie on 16-1-23.
 */
public class App extends Application {
    static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static App getContext() {
        return context;
    }
}
