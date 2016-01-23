package com.xuie.coolblog.mvp.presenter;

import com.xuie.coolblog.mvp.view.ImageView;

public interface ImagePresenter {
    void loadImageList();
    void setView(ImageView view);
    void clearView();
}
