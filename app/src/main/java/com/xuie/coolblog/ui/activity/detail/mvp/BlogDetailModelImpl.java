package com.xuie.coolblog.ui.activity.detail.mvp;

import com.xuie.coolblog.ui.fragment.blog.BlogBean;
import com.xuie.coolblog.common.Urls;
import com.xuie.coolblog.ui.activity.detail.BlogDetailBean;
import com.xuie.coolblog.utils.JokeJsonUtil;
import com.xuie.coolblog.utils.JsoupUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by xuie on 15-12-28.
 */
public class BlogDetailModelImpl implements BlogDetailModel {
    static final String TAG = BlogDetailModelImpl.class.getSimpleName();

    public interface OnLoadDetailListener {
        void onSuccess(BlogDetailBean jokeDetailBean);

        void onFailure(String msg, Exception e);
    }

    @Override
    public void loadBlogDetail(final String docId, final OnLoadDetailListener listener) {
        String url = getBlogDetailUrl(docId);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure("load joke detail info failure.", e);
            }

            @Override
            public void onResponse(String response) {
                BlogDetailBean jokeDetailBean = JokeJsonUtil.readJsonDetailBeans(response, docId);
                listener.onSuccess(jokeDetailBean);
            }
        });
    }

    @Override
    public void loadCsdnJsoup(String link, final OnLoadDetailListener listener) {
        OkHttpUtils.get().url(link).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure("load csdn detail info failure.", e);
            }

            @Override
            public void onResponse(String response) {
                List<BlogBean> blogList = JsoupUtil.blogCsdnContent(response);
                if (blogList.size() > 0) {
                    BlogDetailBean jokeDetailBean = new BlogDetailBean();
                    for (BlogBean blog : blogList) {
                        jokeDetailBean.setBody(jokeDetailBean.getBody() + blog.getContent());
                    }
                    listener.onSuccess(jokeDetailBean);
                }
            }
        });
    }

    @Override
    public void load9ThJsoup(String link, final OnLoadDetailListener listener) {
        OkHttpUtils.get().url(link).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure("load 9Th detail info failure.", e);
            }

            @Override
            public void onResponse(String response) {
                List<BlogBean> blogList = JsoupUtil.blog9ThContent(response);

                if (blogList.size() > 0) {
                    BlogDetailBean jokeDetailBean = new BlogDetailBean();
                    for (BlogBean blog : blogList) {
                        jokeDetailBean.setBody(jokeDetailBean.getBody() + blog.getContent());
                    }
                    listener.onSuccess(jokeDetailBean);
                }
            }
        });

    }

    private String getBlogDetailUrl(String docId) {
        return Urls.NEW_DETAIL + docId + Urls.END_DETAIL_URL;
    }

}
