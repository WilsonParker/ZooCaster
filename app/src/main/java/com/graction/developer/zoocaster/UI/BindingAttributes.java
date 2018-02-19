package com.graction.developer.zoocaster.UI;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Graction06 on 2018-01-17.
 */

public class BindingAttributes {

    @BindingAdapter("ImageResource")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @BindingAdapter("ImageResource")
    public static void setImageResource(ImageView imageView, Drawable drawable){
        imageView.setImageDrawable(drawable);
    }

    @BindingAdapter("Typeface")
    public static void setTypeface(TextView textView, int index){
        FontManager.getInstance().setFont(textView, index);
    }

    @BindingAdapter("Typeface")
    public static void setTypeface(TextView textView, String font){
        FontManager.getInstance().setFont(textView, font);
    }
}
