package com.example.kolot.hackathon.viewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kolot.hackathon.R;

public class SimplePageAdapter extends PagerAdapter {
    private Context mContext;

    public SimplePageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
// инициализируем экран ViewPager'а в соответствии с позицией
        ViewGroup group = (ViewGroup) inflater.inflate(R.layout.hardcoded_instructions_layout, viewGroup, false);
        viewGroup.addView(group);

        return group;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int position, Object view) {
        viewGroup.removeView((View) view);
    }

    @Override
    public int getCount() {
// кличество элементов в адаптере соответствует
// количеству значений в enum классе
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}