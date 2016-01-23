package com.xuie.coolblog.mvp.view.fragment;


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
import com.xuie.coolblog.adapter.BlogAdapter;
import com.xuie.coolblog.bean.Blog;
import com.xuie.coolblog.mvp.presenter.BlogPresenter;
import com.xuie.coolblog.mvp.presenter.BlogPresenterImpl;
import com.xuie.coolblog.mvp.view.BlogView;
import com.xuie.coolblog.mvp.view.activity.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogFragment extends Fragment implements BlogView, SwipeRefreshLayout.OnRefreshListener {
    final static String TAG = BlogFragment.class.getSimpleName();
    int type;
    LinearLayoutManager layoutManager;
    List<Blog> blogList;
    BlogAdapter adapter;
    BlogPresenter presenter;

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;

    public static BlogFragment newInstance(int type) {
        Bundle args = new Bundle();
        BlogFragment fragment = new BlogFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BlogPresenterImpl();
        type = getArguments().getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        ButterKnife.bind(this, view);

        presenter.setView(this);
        swipeRefreshWidget.setColorSchemeResources(R.color.primary, R.color.primary_dark,
                R.color.primary_light, R.color.accent);
        swipeRefreshWidget.setOnRefreshListener(this);

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
        int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == adapter.getItemCount()
                    && adapter.isShowFooter()) {
                //加载更多
                Log.d(TAG, type + " load more...");
//                presenter.loadJoke(type, pageIndex + Urls.PA_ZE_SIZE);
                presenter.loadBlog(type);
            }
        }
    };

    private BlogAdapter.OnItemClickListener onItemClickListener = new BlogAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Blog blog = adapter.getItem(position);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("blog", blog);

            View transitionView = view.findViewById(R.id.img);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transitionView, getString(R.string.transition_img));
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };


    @Override
    public void addBlog(List<Blog> list) {
        swipeRefreshWidget.setRefreshing(false);
        adapter.isShowFooter(false);
        if (blogList == null)
            blogList = new ArrayList<>();
        blogList.addAll(list);
        adapter.setBlogList(blogList);
    }

    @Override
    public void addBlogFail() {
        swipeRefreshWidget.setRefreshing(false);
        adapter.isShowFooter(false);
        adapter.notifyDataSetChanged();
        View view = getActivity() == null ? recycleView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        presenter.clearView();
    }

    @Override
    public void onRefresh() {
        if (blogList != null) {
            blogList.clear();
        }
        presenter.loadBlog(type);
    }
}
