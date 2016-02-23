package com.xuie.coolblog.mvp.view;

import com.xuie.coolblog.bean.Blog;

import java.util.List;

public interface BlogView {
    void addBlog(List<Blog> list);

    void addBlogFail();
}
