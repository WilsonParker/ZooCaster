package com.graction.developer.zoocaster.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.graction.developer.zoocaster.R;

import java.util.ArrayList;

/**
 * Created by Graction06 on 2018-01-18.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<TabItem> items;
    private OnTabSelected onTabSelected;
    private SetTabItem setTabItem;
    private static boolean isFirst;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
    }

    public FragmentAdapter(FragmentManager fm, OnTabSelected onTabSelected) {
        super(fm);
        this.onTabSelected = onTabSelected;
        items = new ArrayList<>();
    }

    public FragmentAdapter(FragmentManager fm, ArrayList<TabItem> items) {
        super(fm);
        setItems(items);
    }

    public FragmentAdapter(FragmentManager fm, ArrayList<TabItem> items, OnTabSelected onTabSelected) {
        super(fm);
        this.onTabSelected = onTabSelected;
        setItems(items);
    }

    public void setOnTabSelected(OnTabSelected onTabSelected) {
        this.onTabSelected = onTabSelected;
    }

    public void setSetTabItem(SetTabItem setTabItem) {
        this.setTabItem = setTabItem;
    }

    public void setItems(ArrayList<TabItem> items) {
        this.items = items;
        if (setTabItem != null)
            for (int i = 0; i < items.size(); i++)
                setTabItem.setTabItem(i, items.get(i));
    }

    public void addItem(TabItem tabItem) {
        this.items.add(tabItem);
    }

    @Override
    public Fragment getItem(int position) {
        TabItem item = items.get(position);
        if (onTabSelected != null)
            onTabSelected.onSelected(position, item);
        return item.getFragment();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        /*if (!isFirst) {
            for (int i = 0; i < items.size(); i++)
                setTabItem.setTabItem(i, items.get(i));
            Log.i("FragmentAdapter", "finishUpdate(ViewGroup container) size : " + items.size());
            isFirst = true;
        }*/
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    public View getDefaultView(Context context, int resId) {
        ImageView view = new ImageView(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setImageResource(resId);
        return view;
    }

    // ImageView id must be icon
    public View getView(Context context, int layout, int resId) {
        View view = LayoutInflater.from(context).inflate(layout, null);
        ((ImageView) view.findViewById(R.id.icon)).setImageResource(resId);
        return view;
    }

    public static class TabItem {
        private Fragment fragment;
        private int resIcon;

        public TabItem(Fragment fragment, int resIcon) {
            this.fragment = fragment;
            this.resIcon = resIcon;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }

        public int getResIcon() {
            return resIcon;
        }

        public void setResIcon(int resIcon) {
            this.resIcon = resIcon;
        }
    }

    public interface OnTabSelected {
        void onSelected(int index, TabItem item);
    }

    public interface SetTabItem {
        void setTabItem(int index, TabItem item);
    }
}
