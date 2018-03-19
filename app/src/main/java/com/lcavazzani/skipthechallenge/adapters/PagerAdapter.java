package com.lcavazzani.skipthechallenge.adapters;

/**
 * Created by leonardoCavazzani on 3/18/18.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lcavazzani.skipthechallenge.fragments.ProductsFragment;
import com.lcavazzani.skipthechallenge.fragments.StoreFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new StoreFragment();
            case 1:
                return new ProductsFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}