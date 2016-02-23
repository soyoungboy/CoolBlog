package com.xuie.coolblog.mvp.presenter;

import com.xuie.coolblog.bean.ImageBean;
import com.xuie.coolblog.mvp.model.ImageModel;
import com.xuie.coolblog.mvp.model.ImageModelImpl;
import com.xuie.coolblog.mvp.view.ImageView;

import java.util.List;

public class ImagePresenterImpl implements ImagePresenter, ImageModelImpl.OnLoadImageListListener {
    ImageModel imageModel;
    ImageView imageView;

    public ImagePresenterImpl(ImageView imageView) {
        this.imageView = imageView;
        this.imageModel = new ImageModelImpl();
    }

    @Override
    public void loadImageList() {
        imageView.showProgress();
        imageModel.loadImageList(this);
    }

    @Override
    public void onSuccess(List<ImageBean> list) {
        if (imageView == null)
            return;
        imageView.addImages(list);
        imageView.hideProgress();
    }

    @Override
    public void onFailure(Exception e) {
        if (imageView == null)
            return;
        imageView.hideProgress();
        imageView.showLoadFailMsg();
    }
}
