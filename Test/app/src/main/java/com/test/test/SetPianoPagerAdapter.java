package com.test.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SetPianoPagerAdapter extends FragmentStatePagerAdapter {
    private int mNoOfTabs;

    public SetPianoPagerAdapter(FragmentManager fm, int mNoOfTabs) {
        super(fm);
        this.mNoOfTabs = mNoOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new SetPianoOctaveActivity();
            case 1:
                return new SetPianoPositionActivity();
            case 2:
                return new SetPianoHorizontalActivity();
            case 3:
                return new SetPianoVerticalActivity();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}