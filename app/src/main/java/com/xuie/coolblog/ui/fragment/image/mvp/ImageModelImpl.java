package com.xuie.coolblog.ui.fragment.image.mvp;

import com.xuie.coolblog.ui.fragment.image.ImageBean;
import com.xuie.coolblog.common.Urls;
import com.xuie.coolblog.utils.ImageJsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class ImageModelImpl implements ImageModel {

    /**
     * 获取图片列表
     */
    @Override
    public void loadImageList(final OnLoadImageListListener listener) {
        String url = Urls.IMAGES_URL;

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(String response) {
                List<ImageBean> imageBeanList = ImageJsonUtil.readJsonImageBeans(response);
                listener.onSuccess(imageBeanList);
            }
        });
    }

    public interface OnLoadImageListListener {
        void onSuccess(List<ImageBean> list);

        void onFailure(Exception e);
    }
}
