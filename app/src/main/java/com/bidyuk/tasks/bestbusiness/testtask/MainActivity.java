package com.bidyuk.tasks.bestbusiness.testtask;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bidyuk.tasks.bestbusiness.testtask.fragments.NewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private MyFragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fragmentList = new ArrayList<>();
        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentPagerAdapter);

        if (getIntent().getIntExtra(getResources().getString(R.string.saved_tag), 0) > 0)
            startActivityFromIntent();
        else
            createFragment(NewFragment.newInstance(1));
    }

    @Override
    protected void onResume() {
        super.onResume();
        int fragmentNumber = getIntent()
                .getIntExtra(getResources().getString(R.string.saved_tag), 0);
        if (fragmentNumber > 0)
            viewPager.setCurrentItem(fragmentNumber - 1);
    }

    private void startActivityFromIntent() {
        int fragmentNumber = getIntent()
                .getIntExtra(getResources().getString(R.string.saved_tag), 0);
        int fragmentCount = getIntent()
                .getIntExtra(getResources().getString(R.string.saved_count_tag), 0);
        for (int i = 0; i < fragmentCount; i++) {
            createFragment(NewFragment.newInstance(i+1));
            viewPager.setCurrentItem(fragmentNumber - 1);
        }
    }

    public void createFragment(@NonNull Fragment fragment) {
        fragmentList.add(fragment);
        fragmentPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(fragmentList.size());
    }

    public void removeFragment(@NonNull Fragment fragment) {
        int currentItem = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem - 1);
        fragmentList.remove(currentItem);
        fragmentPagerAdapter.notifyDataSetChanged();
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        public MyFragmentPagerAdapter(FragmentManager fm, @NonNull List<Fragment> fragmentList) {
            super(fm);
            fragments = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public List<Fragment> getFragments() {
            return fragments;
        }
    }
}
