package com.xuie.coolblog.ui.fragment;


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
import com.xuie.coolblog.ui.fragment.blog.BlogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    public static final int TYPE_WEB = 0;
    public static final int TYPE_JOKE = 1;
    public static final int TYPE_9TH = 2;

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
        tabLayout.addTab(tabLayout.newTab().setText(R.string.type_joke));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.type_blog));
        tabLayout.setupWithViewPager(viewpager);
        return view;
    }

    private void setupViewPager(ViewPager viewpager) {
        //Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(BlogFragment.newInstance(TYPE_WEB), getString(R.string.type_csdn_web));
        adapter.addFragment(BlogFragment.newInstance(TYPE_JOKE), getString(R.string.type_joke));
        adapter.addFragment(BlogFragment.newInstance(TYPE_9TH), getString(R.string.type_blog));
        viewpager.setAdapter(adapter);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
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
