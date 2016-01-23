package com.xuie.coolblog.mvp.view.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.xuie.coolblog.R;
import com.xuie.coolblog.bean.Blog;
import com.xuie.coolblog.mvp.presenter.JokeDetailPresenter;
import com.xuie.coolblog.mvp.presenter.JokeDetailPresenterImpl;
import com.xuie.coolblog.mvp.view.DetailView;
import com.xuie.coolblog.utils.ImageLoaderUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class DetailActivity extends SwipeBackActivity implements DetailView {

    Blog blog;
    JokeDetailPresenter mJokeDetailPresenter;

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
        blog = (Blog) getIntent().getSerializableExtra("blog");
        collapsingToolbar.setTitle(blog.getTitle());
        if (blog.getImgSrc() != null) {
            ImageLoaderUtils.display(getApplicationContext(), image, blog.getImgSrc());
        } else {
            image.setImageResource(blog.getImgId());
        }

        mJokeDetailPresenter = new JokeDetailPresenterImpl();
        mJokeDetailPresenter.setView(this);
        if (blog.getDocid() != null) {
            mJokeDetailPresenter.loadDetail(blog.getDocid());
        } else if (blog.getDate() != null) {
            mJokeDetailPresenter.loadLink(blog.getLink());
        } else {
            mJokeDetailPresenter.loadCool(blog.getLink());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mJokeDetailPresenter.clearView();
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
}
