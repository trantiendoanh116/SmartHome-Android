package com.iot.smarthome.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.github.nkzawa.socketio.client.Socket;

public class MyAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    private Socket mSocket;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs, Socket socket) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.mSocket = socket;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                Floor1Fragment floor1Fragment = new Floor1Fragment(mSocket);
//                return floor1Fragment;
//            case 1:
//                Floor2Fragment floor2Fragment = new Floor2Fragment();
//                return floor2Fragment;
//            case 2:
//                Floor3Fragment floor3Fragment = new Floor3Fragment();
//                return floor3Fragment;
//            default:
//                return null;
//        }
        return null;
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
