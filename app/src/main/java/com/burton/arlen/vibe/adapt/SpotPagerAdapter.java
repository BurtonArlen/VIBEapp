package com.burton.arlen.vibe.adapt;

/**
 * Created by arlen on 7/15/16.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.burton.arlen.vibe.model.Spot;
import com.burton.arlen.vibe.ui.SpotDetailFragment;

import java.util.ArrayList;

public class SpotPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Spot> mSpots;

    public SpotPagerAdapter(FragmentManager fm, ArrayList<Spot> spots) {
        super(fm);
        mSpots = spots;
    }

    @Override
    public Fragment getItem(int position) {
        return SpotDetailFragment.newInstance(mSpots.get(position));
    }

    @Override
    public int getCount() {
        return mSpots.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mSpots.get(position).getName();
    }
}
