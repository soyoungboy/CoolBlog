package com.xuie.coolblog.ui.fragment.image.mvp;

import com.xuie.coolblog.ui.fragment.image.ImageBean;

import java.util.List;

public interface ImageView {
    void addImages(List<ImageBean> list);

    void showLoadFailMsg();

    void showProgress();

    void hideProgress();

}
