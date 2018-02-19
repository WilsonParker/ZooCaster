package com.graction.developer.zoocaster.UI.Layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.databinding.ItemArrayViewBinding;

/**
 * Created by Graction06 on 2018-01-23.
 */

public class CustomArrayView extends HareArrayView2<CustomArrayView.CustomItemView, CustomArrayView.CustomItemViewModel> {
    private WeekClickListener weekClickListener;

    public CustomArrayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomArrayView(Context context, @Nullable AttributeSet attrs, WeekClickListener weekClickListener) {
        super(context, attrs);
        this.weekClickListener = weekClickListener;
    }

    public void setWeekClickListener(WeekClickListener weekClickListener) {
        this.weekClickListener = weekClickListener;
    }

    @Override
    void setTypeArray(TypedArray typedArray) {

    }

    @Override
    public CustomItemView createItemView(Context context, ViewGroup viewGroup, int resId) {
        return new CustomItemView(context, viewGroup, resId);
    }

    public class CustomItemView extends HareArrayView2.ItemView {
        private ItemArrayViewBinding itemArrayBinding;
        private Context context;

        public CustomItemView(Context context, ViewGroup viewGroup, int resId) {
            super(context, viewGroup, resId);
            this.context = context;
        }

        @Override
        public View setClickListener() {
            return itemArrayBinding.text;
        }

        @Override
        public View createView(Context context, ViewGroup viewGroup, int resId) {
            itemArrayBinding = DataBindingUtil.inflate(LayoutInflater.from(context), resId, viewGroup, false);
            return itemArrayBinding.getRoot();
        }

        @Override
        public void onFirstClick(ItemViewModel model) {
            itemArrayBinding.text.setTextColor(ContextCompat.getColor(context, R.color.item_array_view_text_first));
            itemArrayBinding.icon.setImageResource(R.drawable.ellipse_1);
            weekClickListener.setWeekArray(((CustomItemViewModel) model).getIndex(), WeekClickListener.click);
        }

        @Override
        public void onSecondClick(ItemViewModel model) {
            itemArrayBinding.text.setTextColor(ContextCompat.getColor(context, R.color.item_array_view_text_second));
            itemArrayBinding.icon.setImageResource(R.drawable.ellipse_7);
            weekClickListener.setWeekArray(((CustomItemViewModel) model).getIndex(), WeekClickListener.reset);
        }

        @Override
        public View toBind(ItemViewModel item) {
            super.toBind(item);
            itemArrayBinding.setItemModel((CustomItemViewModel) item);
            itemArrayBinding.executePendingBindings();
            if(item.isClicked())
                onFirstClick(item);
            else
                onSecondClick(item);
            return itemArrayBinding.getRoot();
        }
    }

    public class CustomItemViewModel extends HareArrayView2.ItemViewModel {
        private String text;
        private int index;

        public CustomItemViewModel(String text, int index) {
            this.text = text;
            this.index = index;
        }

        public CustomItemViewModel(String text, int index, boolean isClicked) {
            this(text, index);
            super.setClicked(isClicked);
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public interface WeekClickListener {
        int click = 1, reset = 0;

        void setWeekArray(int index, int value);
    }
}
