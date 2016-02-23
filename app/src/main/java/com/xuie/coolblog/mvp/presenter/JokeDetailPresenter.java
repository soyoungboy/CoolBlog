package com.xuie.coolblog.mvp.presenter;

public interface JokeDetailPresenter {

    void loadDetail(String docId);

    void loadLink(String link);

    void loadCool(String link);
}
