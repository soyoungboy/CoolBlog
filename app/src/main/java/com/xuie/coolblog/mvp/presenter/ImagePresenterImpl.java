package com.xuie.coolblog.mvp.presenter;

import com.xuie.coolblog.bean.ImageBean;
import com.xuie.coolblog.mvp.model.ImageModel;
import com.xuie.coolblog.mvp.model.ImageModelImpl;
import com.xuie.coolblog.mvp.view.ImageView;

import java.util.List;

public class ImagePresenterImpl implements ImagePresenter, ImageModelImpl.OnLoadImageListListener {

    private ImageModel mImageModel;
    private ImageView mImageView;

    public ImagePresenterImpl() {
        this.mImageModel = new ImageModelImpl();
    }

    @Override
    public void loadImageList() {
        mImageView.showProgress();
        mImageModel.loadImageList(this);
    }

    @Override
    public void setView(ImageView view) {
        mImageView = view;
    }

    @Override
    public void clearView() {
        mImageView = null;
    }

    @Override
    public void onSuccess(List<ImageBean> list) {
        if (mImageView == null)
            return;
        mImageView.addImages(list);
        mImageView.hideProgress();
    }

    @Override
    public void onFailure(Exception e) {
        if (mImageView == null)
            return;
        mImageView.hideProgress();
        mImageView.showLoadFailMsg();
    }
}
