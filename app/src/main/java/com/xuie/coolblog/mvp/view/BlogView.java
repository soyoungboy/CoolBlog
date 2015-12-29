package com.xuie.coolblog.mvp.view;

import com.xuie.coolblog.bean.Blog;

import java.util.List;

/**
 * Created by xuie on 15-12-28.
 */
public interface BlogView {
    void addBlog(List<Blog> list);
    void addBlogFail();
}
