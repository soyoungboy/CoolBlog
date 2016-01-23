package com.xuie.coolblog.mvp.view;

import com.xuie.coolblog.bean.ImageBean;

import java.util.List;

public interface ImageView {
    void addImages(List<ImageBean> list);

    void showLoadFailMsg();

    void showProgress();

    void hideProgress();

}
