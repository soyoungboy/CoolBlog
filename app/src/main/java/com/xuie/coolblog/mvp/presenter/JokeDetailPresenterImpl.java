package com.xuie.coolblog.mvp.presenter;

import com.xuie.coolblog.bean.JokeDetailBean;
import com.xuie.coolblog.mvp.model.BlogModel;
import com.xuie.coolblog.mvp.model.BlogModelImpl;
import com.xuie.coolblog.mvp.view.DetailView;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 2015/12/21
 */
public class JokeDetailPresenterImpl implements JokeDetailPresenter, BlogModelImpl.OnLoadDetailListener {
    private DetailView view;
    private BlogModel model;

    public JokeDetailPresenterImpl(DetailView view) {
        this.view = view;
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
    public void onSuccess(JokeDetailBean jokeDetailBean) {
        if(jokeDetailBean != null) {
            view.showDetailContent(jokeDetailBean.getBody());
        }
        view.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        view.hideProgress();
    }
}
