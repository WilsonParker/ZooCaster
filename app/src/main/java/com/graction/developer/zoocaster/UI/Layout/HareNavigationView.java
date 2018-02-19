package com.graction.developer.zoocaster.UI.Layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Hare on 2017-08-02.
 * Update by Hare on 2018-01-17.
 */

public class HareNavigationView extends LinearLayout {
    private ArrayList<NavigationItemView> children;
    private NavigationItemView clickedItemView;
    private NavigationItemViewBindImage navigationItemViewBindImage;

    private int[] Style_NavigationView = {};

    public HareNavigationView(Context context) {
        super(context);
    }

    public HareNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
    }

    public HareNavigationView(Context context, @Nullable AttributeSet attrs, int styleAttr) {
        super(context, attrs, styleAttr);
        getAttrs(attrs, styleAttr);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, Style_NavigationView);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, Style_NavigationView, style, 0);
        setTypeArray(typedArray);
    }


    // Not yet implements
    private void setTypeArray(TypedArray typedArray) {
        // itemId = typedArray.getResourceId(R.styleable.custom_navigation_view_item_layout, Layout_ItemCustomNavigationVIew);
        // typedArray.recycle();
    }


    public View bindItemView(Context context, ArrayList<NavigationItem> items, NavigationItemViewBindImage navigationItemViewBindImage) {
        this.navigationItemViewBindImage = navigationItemViewBindImage;
        children = new ArrayList<>();
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.HORIZONTAL);
        root.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(root);

        for (NavigationItem item : items) {
            NavigationItemView itemVIew = new NavigationItemView(context, this);
            root.addView(itemVIew.toBind(item));
            children.add(itemVIew);
        }
        return root;
    }

    public void actionItem(int position) {
        children.get(position).actionClickEvent();
    }

    public void setStyle_NavigationView(int[] style_NavigationView) {
        Style_NavigationView = style_NavigationView;
    }

    public void setItemSelect(int position) {
        NavigationItemView navigationItemView = children.get(position);
        navigationItemView.setState(true);
        clickedItemView.setState(false);
        clickedItemView = navigationItemView;
    }


    public class NavigationItemView {
        private ImageView icon;
        private NavigationItem item;

        public NavigationItemView(Context context, ViewGroup viewGroup) {
            icon = new ImageView(context);
            icon.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
        }

        public View toBind(NavigationItem item) {
            this.item = item;
            navigationItemViewBindImage.bindImage(icon, item.getDefaultImage());
            icon.setOnClickListener(view -> actionClickEvent());
            return icon;
        }

        private void actionClickEvent() {
            if (!item.isClicked()) {
                item.getOnClickListener().onClick();
                if (item.isChangeable()) {
                    setState(true);
                    if (clickedItemView != null)
                        clickedItemView.setState(false);
                    clickedItemView = this;
                }
            }
        }

        private void setState(boolean clicked) {
            item.setClicked(clicked);
            if (clicked)
                navigationItemViewBindImage.bindImage(icon, item.getClickImage());
            else
                navigationItemViewBindImage.bindImage(icon, item.getDefaultImage());
        }
    }

    public interface NavigationItemViewBindImage {
        void bindImage(ImageView imageView, int resId);
    }

    public class NavigationItem {
        private int clickImage, defaultImage;
        private NavigationOnClickListener onClickListener;
        private boolean isClicked, changeable = true;

        public NavigationItem(int clickImage, int defaultImage, NavigationOnClickListener onClickListener) {
            this.clickImage = clickImage;
            this.defaultImage = defaultImage;
            this.onClickListener = onClickListener;
        }

        public NavigationItem(int clickImage, int defaultImage, NavigationOnClickListener onClickListener, boolean changeable) {
            this.clickImage = clickImage;
            this.defaultImage = defaultImage;
            this.onClickListener = onClickListener;
            this.changeable = changeable;
        }

        public int getClickImage() {
            return clickImage;
        }

        public void setClickImage(int clickImage) {
            this.clickImage = clickImage;
        }

        public int getDefaultImage() {
            return defaultImage;
        }

        public void setDefaultImage(int defaultImage) {
            this.defaultImage = defaultImage;
        }

        public NavigationOnClickListener getOnClickListener() {
            return onClickListener;
        }

        public void setOnClickListener(NavigationOnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public boolean isClicked() {
            return isClicked;
        }

        public void setClicked(boolean clicked) {
            isClicked = clicked;
        }

        public boolean isChangeable() {
            return changeable;
        }

        public void setChangeable(boolean changeable) {
            this.changeable = changeable;
        }
    }

    public interface NavigationOnClickListener {
        void onClick();
    }
}
