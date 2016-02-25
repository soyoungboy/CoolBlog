package com.xuie.coolblog.ui.fragment.blog;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.coolblog.R;
import com.xuie.coolblog.ui.activity.WebActivity;
import com.xuie.coolblog.ui.activity.detail.BlogDetailActivity;
import com.xuie.coolblog.ui.fragment.blog.mvp.BlogPresenter;
import com.xuie.coolblog.ui.fragment.blog.mvp.BlogPresenterImpl;
import com.xuie.coolblog.ui.fragment.blog.mvp.BlogView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogFragment extends Fragment implements BlogView, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = BlogFragment.class.getSimpleName();
    public static final String TYPE = "type";

    int type;
    LinearLayoutManager layoutManager;
    List<BlogBean> blogList;
    BlogAdapter adapter;
    BlogPresenter blogPresenter;

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    public static BlogFragment newInstance(int type) {
        Bundle args = new Bundle();
        BlogFragment fragment = new BlogFragment();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blogPresenter = new BlogPresenterImpl(this);
        type = getArguments().getInt(TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        ButterKnife.bind(this, view);

        swipeRefresh.setColorSchemeResources(
                R.color.primary,
                R.color.primary_dark,
                R.color.primary_light,
                R.color.accent);
        swipeRefresh.setOnRefreshListener(this);

        recycleView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recycleView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManager);

        recycleView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BlogAdapter(getActivity());
        adapter.setOnItemClickListener(onItemClickListener);
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(onScrollListener);
        onRefresh();
        return view;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && layoutManager.findLastVisibleItemPosition() + 1 == adapter.getItemCount()
                    && adapter.isShowFooter()) {
                //加载更多
                Log.d(TAG, type + " load more...");
//                blogPresenter.loadJoke(type, pageIndex + Urls.PA_ZE_SIZE);
                loadBlog();
            }
        }
    };

    private void loadBlog() {
        if (type == 0) {
            blogPresenter.loadCsdnBlog();
        } else if (type == 1) {
            blogPresenter.loadJokeBlog();
        } else if (type == 2) {
            blogPresenter.load9ThBlog();
        }
    }

    private BlogAdapter.OnItemClickListener onItemClickListener = new BlogAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            BlogBean blog = adapter.getItem(position);

            if (type == 0 || type == 2) {
                WebActivity.newIntent(blog.getLink());
            } else if (type == 1) {
                Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
                intent.putExtra("blog", blog);

                View transitionView = view.findViewById(R.id.img);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                transitionView, getString(R.string.transition_img));
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        }
    };


    @Override
    public void addBlog(List<BlogBean> list) {
        swipeRefresh.setRefreshing(false);
        adapter.isShowFooter(false);
        if (blogList == null)
            blogList = new ArrayList<>();
        blogList.addAll(list);
        adapter.setBlogList(blogList);
    }

    @Override
    public void addBlogFail() {
        swipeRefresh.setRefreshing(false);
        adapter.isShowFooter(false);
        adapter.notifyDataSetChanged();
        View view = getActivity() == null ? recycleView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        if (blogList != null) {
            blogList.clear();
        }
        loadBlog();
    }
}
