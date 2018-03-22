package com.graction.developer.zoocaster.UI.Item;

import com.androidplot.Plot;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

/**
 * Created by JeongTaehyun on 2018-02-13.
 */

/*
 * Graph Animation
 */
public class AnimatedSeries implements XYSeries, Runnable {

    private final Plot plot;
    private final XYSeries series;
    private int step = 0;
    private int stepCount = 25;
    private float factor = 0;

    public AnimatedSeries(Plot plot, XYSeries series) {
        this.plot = plot;
        this.series = series;
    }

    public AnimatedSeries(Plot plot, XYSeries series, int stepCount) {
        this(plot, series);
        this.stepCount = stepCount;
    }

    @Override
    public void run() {
        try {
            while (step < stepCount) {
                factor = step / (float) stepCount;
                Thread.sleep(50);
                plot.redraw();
                step++;
            }
            factor = 1;
            plot.redraw();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return series.size();
    }

    @Override
    public Number getX(int index) {
        return index;
    }

    @Override
    public Number getY(int index) {
        return (int) series.getY(index).doubleValue() * factor;
    }

    @Override
    public String getTitle() {
        return series.getTitle();
    }
}
