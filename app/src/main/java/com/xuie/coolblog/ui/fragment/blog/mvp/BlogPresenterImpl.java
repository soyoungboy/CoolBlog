package com.xuie.coolblog.ui.fragment.blog.mvp;

import com.xuie.coolblog.ui.fragment.blog.BlogBean;
import com.xuie.coolblog.common.Urls;

import java.util.List;

/**
 * Created by xuie on 15-12-28.
 */
public class BlogPresenterImpl implements BlogPresenter, BlogModeImpl.OnListener {
    BlogView blogView;
    BlogModeImpl blogModel;

    public BlogPresenterImpl(BlogView blogView) {
        this.blogView = blogView;
        this.blogModel = new BlogModeImpl();
    }

    @Override
    public void loadCsdnBlog() {
        blogModel.loadCsdn(Urls.CSDN_CSDN_LIST, this);
    }

    @Override
    public void loadJokeBlog() {
        blogModel.loadJoke(Urls.JOKE_LIST, this);
    }

    @Override
    public void load9ThBlog() {
        blogModel.load9Th(Urls.BLOG_9TH_LIST, this);
    }

    @Override
    public void onSuccess(List<BlogBean> list) {
        if (blogView != null)
            blogView.addBlog(list);
    }

    @Override
    public void onFailure(Exception e) {
        if (blogView != null)
            blogView.addBlogFail();
    }
}
