package com.xuie.coolblog.ui.activity.detail.mvp;

public interface BlogDetailPresenter {

    void loadDetail(String docId);

    void loadLink(String link);

    void loadCool(String link);
}
