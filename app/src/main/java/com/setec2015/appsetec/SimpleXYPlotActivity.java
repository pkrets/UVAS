package com.setec2015.appsetec;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;

/**
 * Created by Krets on 27/10/2015.
 */
public class SimpleXYPlotActivity extends Activity {
    private XYPlot plot;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // fun little snippet that prevents users from taking screenshots
        // on ICS+ devices :-)
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
        //        WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.simple_xy_plot_example);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {42, 55, 59, 34, 38, 41};
        Number[] series2Numbers = {38, 51, 56, 32, 35, 38};
        Number[] series3Numbers = {45, 49, 51, 33, 32, 37};

    // ZONA 1
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Zona 1");                             // Set the display title of the series

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.BLACK, null, null);

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);

    // ZONA 2
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Zona 2");

        LineAndPointFormatter series2Format = new LineAndPointFormatter(Color.BLUE, Color.BLACK, null, null);

        plot.addSeries(series2, series2Format);

    // ZONA 3
        XYSeries series3 = new SimpleXYSeries(Arrays.asList(series3Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Zona 3");

        LineAndPointFormatter series3Format = new LineAndPointFormatter(Color.GREEN, Color.BLACK, null, null);

        plot.addSeries(series3, series3Format);



        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);

    }
}

