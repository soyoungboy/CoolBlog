package com.xuie.coolblog.mvp.presenter;

import com.xuie.coolblog.bean.JokeDetailBean;
import com.xuie.coolblog.mvp.model.BlogModel;
import com.xuie.coolblog.mvp.model.BlogModelImpl;
import com.xuie.coolblog.mvp.view.DetailView;

public class JokeDetailPresenterImpl implements JokeDetailPresenter, BlogModelImpl.OnLoadDetailListener {
    private DetailView detailView;
    private BlogModel blogModel;

    public JokeDetailPresenterImpl(DetailView detailView) {
        this.detailView = detailView;
        this.blogModel = new BlogModelImpl();
    }

    @Override
    public void loadDetail(String docId) {
        detailView.showProgress();
        blogModel.loadDetail(docId, this);
    }

    @Override
    public void loadLink(String link) {
        detailView.showProgress();
        blogModel.loadLink(link, this);
    }

    @Override
    public void loadCool(String link) {
        detailView.showProgress();
        blogModel.loadCool(link, this);
    }

    @Override
    public void onSuccess(JokeDetailBean jokeDetailBean) {
        if (detailView == null)
            return;
        if (jokeDetailBean != null) {
            detailView.showDetailContent(jokeDetailBean.getBody());
        }
        detailView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        if (detailView == null)
            return;
        detailView.hideProgress();
    }
}
