package com.xuie.coolblog.ui.activity.detail.mvp;

import com.xuie.coolblog.ui.activity.detail.BlogDetailBean;

public class BlogDetailPresenterImpl implements BlogDetailPresenter, BlogDetailModelImpl.OnLoadDetailListener {
    private BlogDetailView detailView;
    private BlogDetailModel blogModel;

    public BlogDetailPresenterImpl(BlogDetailView detailView) {
        this.detailView = detailView;
        this.blogModel = new BlogDetailModelImpl();
    }

    @Override
    public void loadDetail(String docId) {
        detailView.showProgress();
        blogModel.loadBlogDetail(docId, this);
    }

    @Override
    public void loadLink(String link) {
        detailView.showProgress();
        blogModel.loadCsdnJsoup(link, this);
    }

    @Override
    public void loadCool(String link) {
        detailView.showProgress();
        blogModel.load9ThJsoup(link, this);
    }

    @Override
    public void onSuccess(BlogDetailBean jokeDetailBean) {
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
