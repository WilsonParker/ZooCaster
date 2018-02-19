package com.graction.developer.zoocaster.Adapter;

import android.support.v4.app.Fragment;

import com.lsjwzh.widget.recyclerviewpager.TabLayoutSupport;

import java.util.ArrayList;

/**
 * Created by Graction06 on 2018-01-18.
 */

public class ViewPagerTabAdapter implements TabLayoutSupport.ViewPagerTabLayoutAdapter {
    private ArrayList<TabItem> items;
    private OnTabSelected onTabSelected;

    public ViewPagerTabAdapter(OnTabSelected onTabSelected) {
        items = new ArrayList<>();
        this.onTabSelected = onTabSelected;
    }

    public ViewPagerTabAdapter(ArrayList<TabItem> items) {
        this.items = items;
    }

    public ViewPagerTabAdapter(ArrayList<TabItem> items, OnTabSelected onTabSelected) {
        this.items = items;
        this.onTabSelected = onTabSelected;
    }

    public void addItem(TabItem tabItem) {
        this.items.add(tabItem);
    }

    public Fragment getItem(int position) {
        TabItem item = items.get(position);
        if (onTabSelected != null)
            onTabSelected.onSelected(position, item);
        return item.getFragment();
    }

    @Override
    public String getPageTitle(int i) {
        return null;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class TabItem {
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
}
