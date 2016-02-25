package com.xuie.coolblog.ui.activity.detail.mvp;

/**
 * Created by xuie on 15-12-28.
 */
public interface BlogDetailModel {
    void loadBlogDetail(String docId, final BlogDetailModelImpl.OnLoadDetailListener listener);

    void loadCsdnJsoup(String link, final BlogDetailModelImpl.OnLoadDetailListener listener);

    void load9ThJsoup(String link, final BlogDetailModelImpl.OnLoadDetailListener listener);

}
