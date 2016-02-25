package com.xuie.coolblog.ui.fragment.blog.mvp;

/**
 * Created by xuie on 16-2-25.
 */
public interface BlogMode {
    void loadCsdn(String url, final BlogModeImpl.OnListener listener);

    void loadJoke(String url, final BlogModeImpl.OnListener listener);

    void load9Th(String url, final BlogModeImpl.OnListener listener);
}
