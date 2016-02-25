package com.xuie.coolblog.ui.fragment.image;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.coolblog.R;
import com.xuie.coolblog.ui.fragment.image.mvp.ImagePresenter;
import com.xuie.coolblog.ui.fragment.image.mvp.ImagePresenterImpl;
import com.xuie.coolblog.ui.fragment.image.mvp.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment implements ImageView, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = ImageFragment.class.getSimpleName();

    LinearLayoutManager layoutManager;
    ImageAdapter adapter;
    List<ImageBean> beanList;
    ImagePresenter imagePresenter;

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePresenter = new ImagePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, view);
        swipeRefresh.setColorSchemeResources(
                R.color.primary,
                R.color.primary_dark,
                R.color.primary_light,
                R.color.accent);
        swipeRefresh.setOnRefreshListener(this);

        recycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManager);

        recycleView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ImageAdapter(getActivity().getApplicationContext());
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(mOnScrollListener);
        onRefresh();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && layoutManager.findLastVisibleItemPosition() + 1 == adapter.getItemCount()) {
                //加载更多
                Snackbar.make(getActivity().findViewById(R.id.drawer_layout),
                        getString(R.string.image_hit), Snackbar.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onRefresh() {
        if (beanList != null) {
            beanList.clear();
        }
        imagePresenter.loadImageList();
    }

    @Override
    public void addImages(List<ImageBean> list) {
        if (beanList == null) {
            beanList = new ArrayList<>();
        }
        beanList.addAll(list);
        adapter.setmDate(beanList);
    }

    @Override
    public void showLoadFailMsg() {
        View view = getActivity() == null ? recycleView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }
}

