package com.xuie.coolblog.mvp.presenter;

import com.xuie.coolblog.mvp.view.DetailView;

public interface JokeDetailPresenter {

    void loadDetail(String docId);

    void loadLink(String link);

    void loadCool(String link);

    void setView(DetailView view);

    void clearView();
}
