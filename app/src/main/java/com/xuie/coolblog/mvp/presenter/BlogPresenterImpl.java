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
    BlogView blogView;
    BlogModel blogModel;

    public BlogPresenterImpl(BlogView blogView) {
        this.blogView = blogView;
        this.blogModel = new BlogModelImpl();
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
                break;
            case 1:
                url = Urls.JOKE_LIST;
                break;
            case 2:
                url = Urls.XUIE_BLOG_LIST;
                break;
        }
        blogModel.load(url, type, this);
    }

    @Override
    public void onSuccess(List<Blog> list) {
        if (blogView != null)
            blogView.addBlog(list);
    }

    @Override
    public void onFailure(Exception e) {
        if (blogView != null)
            blogView.addBlogFail();
    }
}
