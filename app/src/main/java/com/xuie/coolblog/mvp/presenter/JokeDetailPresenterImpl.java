package com.xuie.coolblog.mvp.presenter;

import com.xuie.coolblog.bean.JokeDetailBean;
import com.xuie.coolblog.mvp.model.BlogModel;
import com.xuie.coolblog.mvp.model.BlogModelImpl;
import com.xuie.coolblog.mvp.view.DetailView;

public class JokeDetailPresenterImpl implements JokeDetailPresenter, BlogModelImpl.OnLoadDetailListener {
    private DetailView view;
    private BlogModel model;

    public JokeDetailPresenterImpl() {
        model = new BlogModelImpl();
    }

    @Override
    public void loadDetail(final String docId) {
        view.showProgress();
        model.loadDetail(docId, this);
    }

    @Override
    public void loadLink(String link) {
        view.showProgress();
        model.loadLink(link, this);
    }

    @Override
    public void loadCool(String link) {
        view.showProgress();
        model.loadCool(link, this);
    }

    @Override
    public void setView(DetailView view) {
        this.view = view;
    }

    @Override
    public void clearView() {
        view = null;
    }

    @Override
    public void onSuccess(JokeDetailBean jokeDetailBean) {
        if (view == null)
            return;
        if (jokeDetailBean != null) {
            view.showDetailContent(jokeDetailBean.getBody());
        }
        view.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        if (view == null)
            return;
        view.hideProgress();
    }
}
