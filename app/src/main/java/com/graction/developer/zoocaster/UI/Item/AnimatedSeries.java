package com.graction.developer.zoocaster.UI.Item;

import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

/**
 * Created by Graction06 on 2018-02-13.
 */

public class AnimatedSeries implements XYSeries,Runnable {

    private final XYPlot plot;
    private final XYSeries series;
    private int step = 0;
    private int stepCount = 25;
    private float factor = 0;

    public AnimatedSeries(XYPlot plot, XYSeries series) {
        this.plot = plot;
        this.series = series;
    }

    public AnimatedSeries(XYPlot plot, XYSeries series, int stepCount) {
        this(plot, series);
        this.stepCount = stepCount;
    }

    @Override
    public void run() {
        try {
            while (step < stepCount) {
                factor = step / (float) stepCount;
                Thread.sleep(50);
//                    Thread.sleep(50);
                plot.redraw();
                step++;
            }
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
        return series.getY(index).doubleValue() * factor;
    }

    @Override
    public String getTitle() {
        return series.getTitle();
    }
}
