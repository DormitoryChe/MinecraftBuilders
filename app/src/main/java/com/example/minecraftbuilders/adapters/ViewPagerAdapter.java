package com.example.minecraftbuilders.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.minecraftbuilders.ListFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final String[] CATEGORIES = {"Best", "Adventure", "Creation", "Minigame", "Parkour", "Other"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ListFragment.getInstance(CATEGORIES[position]);
    }

    @Override
    public int getCount() {
        return CATEGORIES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return CATEGORIES[position];
    }
}
