package com.graction.developer.zoocaster.Fragment;

import android.databinding.DataBindingUtil;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.Item.AnimatedSeries;
import com.graction.developer.zoocaster.UI.Layout.CustomPointLabelFormatter;
import com.graction.developer.zoocaster.databinding.FragmentForecast5dayBinding;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

/**
 * Created by JeongTaehyun
 */

/*
 * 주간 날씨 그래프 테스트 중
 */

public class Test2Fragment extends BaseFragment {
    private FragmentForecast5dayBinding binding;

    public static Fragment getInstance() {
        Fragment fragment = new Test2Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast5day, null, false);
        return binding.getRoot();
    }

    @Override
    protected void init(View view) {
        binding.setActivity(this);
        initXYPlot(binding.xyplot);
    }

    private void initXYPlot(XYPlot plot) {
        // create a couple arrays of y-values to plot:
        final Number[] domainLabels = {1, 2, 3, 6, 7, 8, 9, 10, 13, 14};
        Number[] series1Numbers = {0, 1, 8, 5, 2, 7, 10, 0};
        Number[] series2Numbers = {0, 0, 0, 0, 0, 0, 0, 0};

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(getContext(), R.xml.line_point_formatter_with_labels);

        LineAndPointFormatter series2Format =
                new LineAndPointFormatter(getContext(), R.xml.line_point_formatter_with_labels_2);

        // add an "dash" effect to the series2 line:
        series2Format.getLinePaint().setPathEffect(new DashPathEffect(new float[]{

                // always use DP when specifying pixel sizes, to keep things consistent across devices:
                PixelUtils.dpToPix(20),
                PixelUtils.dpToPix(15)}, 0));

        series1Format.setPointLabelFormatter(new CustomPointLabelFormatter());
        series2Format.setPointLabelFormatter(new CustomPointLabelFormatter());

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        series2Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
        plot.addSeries(series2, series2Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domainLabels[i]);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });

        plot.setRangeBoundaries(1, 30, BoundaryMode.FIXED);

    }

    private void initXYPlotWithAnimation(XYPlot plot) {
        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {-1, 1, 8, 5, 2, 7, 10};
        Number[] series2Numbers = {0, 0, 0, 0, 0, 0, 0};

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series

        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
//        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.setPointLabelFormatter(new CustomPointLabelFormatter());
        series1Format.configure(getContext(),
                R.xml.line_point_formatter_with_labels);

        AnimatedSeries as1 = new AnimatedSeries(plot, series1);
        AnimatedSeries as2 = new AnimatedSeries(plot, series2);

        // add a new series' to the xyplot:
        plot.addSeries(as1, series1Format);

        // same as above:
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new CustomPointLabelFormatter());
        series2Format.configure(getContext(),
                R.xml.line_point_formatter_with_labels_2);
        plot.addSeries(as2, series2Format);

        plot.setRangeBoundaries(1, 30, BoundaryMode.FIXED);
        new Thread(as1).start();
        new Thread(as2).start();
    }

    public void reLoad() {
//        initXYPlot(binding.xyplot);
        initXYPlotWithAnimation(binding.xyplot);
    }
}
