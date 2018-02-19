package com.graction.developer.zoocaster.UI.Layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.graction.developer.zoocaster.R;

import java.util.ArrayList;

/**
 * Created by Hare on 2017-08-02.
 * Update by Hare on 2018-01-17.
 */

public abstract class HareArrayView extends LinearLayout {
    private ArrayList<ItemView> children;
    private ItemView clickedItemView;
    private final int[] DefAttribute = R.styleable.hare_array_view;

    // Attribute
    private boolean isMultiSelected;
    private int ItemResId, orientation;

    private int[] defStyle = {};

    public HareArrayView(Context context) {
        super(context);
    }

    public HareArrayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttrs(attrs);
    }

    public HareArrayView(Context context, @Nullable AttributeSet attrs, int styleAttr) {
        super(context, attrs, styleAttr);
        setAttrs(attrs, styleAttr);
    }

    public HareArrayView(Context context, @Nullable AttributeSet attrs, int[] style) {
        super(context, attrs);
        setAttrs(attrs, style);
    }

    public HareArrayView(Context context, @Nullable AttributeSet attrs, int styleAttr, int[] style) {
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
    public ItemView createItemVIew(Context context, ViewGroup viewGroup, int resId) {
        return new ItemView(context, viewGroup, resId);
    }

    public View bindView(Context context, ArrayList<ItemViewModel> items) {
        children = new ArrayList<>();
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(orientation);
        root.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(root);

        for (ItemViewModel item : items) {
            ItemView itemVIew = createItemVIew(context, this, ItemResId);
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

    public class ItemView {
        private View view;
        private ItemViewModel item;
        private OnItemViewClickListener firstClick, secondClick;

        public ItemView(Context context, ViewGroup viewGroup, int resId) {
            view = createView(context, viewGroup, resId);
            init();
        }

        public View createView(Context context, ViewGroup viewGroup, int resId) {
            return LayoutInflater.from(context).inflate(resId, viewGroup, false);
        }

        protected void init() {

        }

        public View toBind(ItemViewModel item) {
            this.item = item;
            if (orientation == LinearLayout.VERTICAL)
                view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f));
            else
                view.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
            view.setOnClickListener(view -> setState(item.isClicked()));
            return view;
        }

        public void setFirstClick(OnItemViewClickListener firstClick) {
            this.firstClick = firstClick;
        }

        public void setSecondClick(OnItemViewClickListener secondClick) {
            this.secondClick = secondClick;
        }

        public void setView(View view) {
            this.view = view;
        }

        /*
         * First : setState(true)
         * Second : setState(false)
         */
        private void setState(boolean clicked) {
            item.setClicked(!clicked);
            if (clicked)
                secondClick.onClick(view, item);
            else
                firstClick.onClick(view, item);

            if (!isMultiSelected) {
                if (clickedItemView != null)
                    clickedItemView.setState(false);
                clickedItemView = this;
            }
        }
    }

    public class ItemViewModel {
        private boolean isClicked;

        public ItemViewModel() {
        }

        public boolean isClicked() {
            return isClicked;
        }

        public void setClicked(boolean clicked) {
            isClicked = clicked;
        }
    }

    public interface OnItemViewClickListener {
        void onClick(View view, ItemViewModel itemViewModel);
    }
}