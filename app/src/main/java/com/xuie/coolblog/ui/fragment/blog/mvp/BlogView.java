package com.xuie.coolblog.ui.fragment.blog.mvp;

import com.xuie.coolblog.ui.fragment.blog.BlogBean;

import java.util.List;

public interface BlogView {
    void addBlog(List<BlogBean> list);

    void addBlogFail();
}
