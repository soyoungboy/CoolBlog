package com.xuie.coolblog.mvp.presenter;

import com.xuie.coolblog.mvp.view.BlogView;

/**
 * Created by xuie on 15-12-28.
 */
public interface BlogPresenter {
    void loadBlog(int type);
    void setView(BlogView view);
    void clearView();
}
