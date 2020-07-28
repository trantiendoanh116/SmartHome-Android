package com.iot.smarthome.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.iot.smarthome.fragments.AutomaticFragment;
import com.iot.smarthome.fragments.DeviceFragment;
import com.iot.smarthome.fragments.SensorFragment;

public class MyAdapter extends FragmentPagerAdapter {
    private int totalTabs;

    public MyAdapter(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DeviceFragment();
            case 1:
                return new SensorFragment();
            case 2:
                return new AutomaticFragment();
            default:
                return new DeviceFragment();
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
