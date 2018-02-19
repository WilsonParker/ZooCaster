package com.graction.developer.zoocaster.UI.Layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.graction.developer.zoocaster.R;

import java.util.ArrayList;

/**
 * Created by Hare on 2018-01-23.
 */

public abstract class HareArrayView2<V extends HareArrayView2.ItemView, M extends HareArrayView2.ItemViewModel> extends LinearLayout {

    private ArrayList<V> children;
    private static ItemView clickedItemView;
    private final int[] DefAttribute = R.styleable.hare_array_view;

    // Attribute
    private static boolean isMultiSelected;
    private static int ItemResId, orientation;

    private int[] defStyle = {};

    public HareArrayView2(Context context) {
        super(context);
    }

    public HareArrayView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttrs(attrs);
    }

    public HareArrayView2(Context context, @Nullable AttributeSet attrs, int styleAttr) {
        super(context, attrs, styleAttr);
        setAttrs(attrs, styleAttr);
    }

    public HareArrayView2(Context context, @Nullable AttributeSet attrs, int[] style) {
        super(context, attrs);
        setAttrs(attrs, style);
    }

    public HareArrayView2(Context context, @Nullable AttributeSet attrs, int styleAttr, int[] style) {
        super(context, attrs, styleAttr);
        setAttrs(attrs, styleAttr, style);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, DefAttribute);
        initTypeArray(typedArray);
    }

    private void setAttrs(AttributeSet attrs, int style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, DefAttribute, style, 0);
        initTypeArray(typedArray);
    }

    private void setAttrs(AttributeSet attrs, int[] style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, style);
        initTypeArray(typedArray);
    }

    private void setAttrs(AttributeSet attrs, int defStyle, int[] style) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, style, defStyle, 0);
        initTypeArray(typedArray);
    }

    private void initTypeArray(TypedArray typedArray) {
        setDefTypeArray(typedArray);
        setTypeArray(typedArray);
    }

    private void setDefTypeArray(TypedArray typedArray) {
        isMultiSelected = typedArray.getBoolean(R.styleable.hare_array_view_isMultiSelected, false);
        orientation = typedArray.getInteger(R.styleable.hare_array_view_orientation, LinearLayout.HORIZONTAL);
        ItemResId = typedArray.getResourceId(R.styleable.hare_array_view_item_layout, R.layout.item_array_view);
        typedArray.recycle();
    }

    abstract void setTypeArray(TypedArray typedArray);

    // Need To Override when change the ItemView
    public abstract V createItemView(Context context, ViewGroup viewGroup, int resId);

    public View bindView(Context context, ArrayList<M> items) {
        children = new ArrayList<>();
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(orientation);
        root.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(root);

        for (M item : items) {
            V itemVIew = createItemView(context, this, ItemResId);
            root.addView(itemVIew.toBind(item));
            children.add(itemVIew);
        }
        return root;
    }

    public void setDefStyle(int[] defStyle) {
        this.defStyle = defStyle;
    }

    public void setMultiSelected(boolean multiSelected) {
        isMultiSelected = multiSelected;
    }

    public void setItemResId(int itemResId) {
        ItemResId = itemResId;
    }

    public HareArrayView2 getInstance() {
        return this;
    }

    public static abstract class ItemView {
        private View view;
        private ItemViewModel item;

        public ItemView(Context context, ViewGroup viewGroup, int resId) {
            view = createView(context, viewGroup, resId);
        }

        public abstract View setClickListener();

        public abstract View createView(Context context, ViewGroup viewGroup, int resId);

        public abstract void onFirstClick(ItemViewModel model);

        public abstract void onSecondClick(ItemViewModel model);

        public void setView(View view) {
            this.view = view;
        }

        public View toBind(ItemViewModel item) {
            this.item = item;
            if (orientation == LinearLayout.VERTICAL)
                view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f));
            else
                view.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
            setClickListener().setOnClickListener(view -> setState(item.isClicked()));
            return view;
        }

        /*
         * First : setState(true)
         * Second : setState(false)
         */
        private void setState(boolean clicked) {
            item.setClicked(!clicked);
            if (clicked)
                onSecondClick(item);
            else
                onFirstClick(item);

            if (!isMultiSelected) {
                if (clickedItemView != null)
                    clickedItemView.setState(false);
                clickedItemView = this;
            }
        }
    }

    public static class ItemViewModel {
        private boolean isClicked;

        public ItemViewModel() {
        }

        public boolean isClicked() {
            return isClicked;
        }

        public void setClicked(boolean clicked) {
            isClicked = clicked;
        }

        public void setClicked() {
            isClicked = !isClicked;
        }
    }

}