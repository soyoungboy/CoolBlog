package com.xuie.coolblog.ui.activity.detail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.umeng.analytics.MobclickAgent;
import com.xuie.coolblog.R;
import com.xuie.coolblog.ui.fragment.blog.BlogBean;
import com.xuie.coolblog.ui.activity.detail.mvp.BlogDetailPresenter;
import com.xuie.coolblog.ui.activity.detail.mvp.BlogDetailPresenterImpl;
import com.xuie.coolblog.ui.activity.detail.mvp.BlogDetailView;
import com.xuie.coolblog.utils.ImageLoaderUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class BlogDetailActivity extends SwipeBackActivity implements BlogDetailView {

    BlogBean blog;
    BlogDetailPresenter detailPresenter;

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.htContent)
    HtmlTextView htContent;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        blog = (BlogBean) getIntent().getSerializableExtra("blog");
        collapsingToolbar.setTitle(blog.getTitle());
        if (blog.getImgSrc() != null) {
            ImageLoaderUtil.display(getApplicationContext(), image, blog.getImgSrc());
        } else {
            image.setImageResource(blog.getImgId());
        }

        detailPresenter = new BlogDetailPresenterImpl(this);
        if (blog.getDocId() != null) {
            detailPresenter.loadDetail(blog.getDocId());
        } else if (blog.getDate() != null) {
            detailPresenter.loadLink(blog.getLink());
        } else {
            detailPresenter.loadCool(blog.getLink());
        }
    }

    @Override
    public void showDetailContent(String detailContent) {
        htContent.setHtmlFromString(detailContent, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
