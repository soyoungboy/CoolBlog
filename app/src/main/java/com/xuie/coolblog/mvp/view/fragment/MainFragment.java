package com.xuie.coolblog.mvp.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuie.coolblog.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    static final int TYPE_CSDN_WEB = 0;
    static final int TYPE_CSDN_JSOUP = 1;
    static final int TYPE_JOKE = 2;
    static final int TYPE_OTHER = 3;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog_main, container, false);
        ButterKnife.bind(this, view);

        viewpager.setOffscreenPageLimit(3);
        setupViewPager(viewpager);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.type_csdn_web));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.type_csdn_jsoup));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.type_joke));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.type_blog));
        tabLayout.setupWithViewPager(viewpager);
        return view;
    }

    private void setupViewPager(ViewPager viewpager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(BlogFragment.newInstance(TYPE_CSDN_WEB), getString(R.string.type_csdn_web));
        adapter.addFragment(BlogFragment.newInstance(TYPE_CSDN_JSOUP), getString(R.string.type_csdn_jsoup));
        adapter.addFragment(BlogFragment.newInstance(TYPE_JOKE), getString(R.string.type_joke));
        adapter.addFragment(BlogFragment.newInstance(TYPE_OTHER), getString(R.string.type_blog));
        viewpager.setAdapter(adapter);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        final List<Fragment> fragments = new ArrayList<>();
        final List<String> fragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
