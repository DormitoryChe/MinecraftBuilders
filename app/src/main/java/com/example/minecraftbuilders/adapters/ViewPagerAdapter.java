package com.example.minecraftbuilders.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.minecraftbuilders.fragments.ListFragment;
import com.example.minecraftbuilders.models.SingletonBuildings;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String[] mCategories;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mCategories = SingletonBuildings.get().getCategories();
    }

    @Override
    public Fragment getItem(int position) {
        return ListFragment.getInstance(mCategories[position]);
    }

    @Override
    public int getCount() {
        return mCategories.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mCategories[position];
    }
}
