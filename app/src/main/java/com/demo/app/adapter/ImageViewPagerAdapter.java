package com.demo.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.demo.app.view.ImageFragment;

import java.util.List;

/**
 * Created by LXG on 2016/12/14.
 */

public class ImageViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final String IMAGE_URL = "image";

    List<String> mDatas;


    public ImageViewPagerAdapter(FragmentManager fm, List data) {
        super(fm);
        this.mDatas = data;
    }

    @Override
    public Fragment getItem(int position) {
        String url = mDatas.get(position);
        Fragment fragment = ImageFragment.newInstance(url);
        return fragment;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }
}
