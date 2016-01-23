package com.xuie.coolblog.mvp.presenter;

import com.xuie.coolblog.bean.Blog;
import com.xuie.coolblog.common.Urls;
import com.xuie.coolblog.mvp.model.BlogModel;
import com.xuie.coolblog.mvp.model.BlogModelImpl;
import com.xuie.coolblog.mvp.view.BlogView;

import java.util.List;

/**
 * Created by xuie on 15-12-28.
 */
public class BlogPresenterImpl implements BlogPresenter, BlogModelImpl.OnListener {
    BlogModel model;
    BlogView view;

    @Override
    public void setView(BlogView view) {
        this.view = view;
    }

    @Override
    public void clearView() {
        this.view = null;
    }

    public BlogPresenterImpl() {
        this.model = new BlogModelImpl();
    }

    @Override
    public void loadBlog(int type) {
        load(type);
    }

    private void load(int type) {
        String url;
        switch (type) {
            case 0:
            default:
                url = Urls.CSDN_HOME_LIST;
                model.load(url, this);
                return;
            case 1:
                url = Urls.CSDN_ANDROID_LIST;
                break;
            case 2:
                url = Urls.JOKE_LIST;
                break;
            case 3:
                url = Urls.XUIE_BLOG_LIST;
        }
        model.load(url, type, this);
    }

    @Override
    public void onSuccess(List<Blog> list) {
        if (view != null)
            view.addBlog(list);
    }

    @Override
    public void onFailure(Exception e) {
        if (view != null)
            view.addBlogFail();
    }
}
