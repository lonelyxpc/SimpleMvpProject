package com.lonelyxpc.mvp.module.MainActivity.adapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomePageFragmentAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmentManager;

    private List<String> titles;
    private List<Fragment> fragments;

    public HomePageFragmentAdapter(@NonNull FragmentManager fm, List<String> titles,List<Fragment> fragments) {
        super(fm);
        this.fragmentManager = fm;
        this.titles = titles;
        this.fragments =fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
