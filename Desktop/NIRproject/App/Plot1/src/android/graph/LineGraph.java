package android.graph;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
//import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class LineGraph {
	
private GraphicalView view;
	
	private TimeSeries dataset = new TimeSeries("Rain Fall"); 
	//private TimeSeries dataset1 = new TimeSeries("Rain Fall"); 
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	
	private XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRe
	
	/*public Intent getIntent(Context context)
	{
		double y [] = {1.0, 2.3 ,3.9};
		double x [] = {0.25,0.5,0.75};
		TimeSeries series = new TimeSeries("Line1"); 
		for( int i = 0; i < x.length; i++)
		{
			series.add(x[i], y[i]);
		}
		
		// Our second data
		int[] x2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
		int[] y2 =  { 145, 123, 111, 100, 89, 77, 57, 45, 34, 30}; // y values!
		TimeSeries series2 = new TimeSeries("Line2"); 
		for( int i = 0; i < x2.length; i++)
		{
			series2.add(x2[i], y2[i]);
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		dataset.addSeries(series2);
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
		XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
		XYSeriesRenderer renderer2 = new XYSeriesRenderer(); // This will be used to customize line 2
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.addSeriesRenderer(renderer2);
		mRenderer.setChartTitle("Test1");
		mRenderer.setAxesColor(Color.GREEN);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLabelsColor(Color.BLUE);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.BLUE);
		mRenderer.setMarginsColor(Color.RED);
		// Customization time for line 1!
				renderer.setColor(Color.RED);
				renderer.setPointStyle(PointStyle.SQUARE);
				renderer.setFillPoints(true);
				// Customization time for line 2!
				renderer2.setColor(Color.YELLOW);
				renderer2.setPointStyle(PointStyle.DIAMOND);
				renderer2.setFillPoints(true);
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Line Graph Title");
		
		return intent;
		
	}*/
	
	public LineGraph()
	{
		// Add single dataset to multiple dataset
		mDataset.addSeries(dataset);
	//	mDataset.addSeries(dataset1);
		// Customization time for line 1!
		renderer.setColor(Color.DKGRAY);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		
		
		// Enable Zoom
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setChartTitle("Plot random data Test ");
		mRenderer.setXTitle("Day #");
		mRenderer.setYTitle("CM in Rainfall");
		mRenderer.setYLabelsAlign(Align.RIGHT);
		mRenderer.setYLabelsAngle(10);
		mRenderer.setLabelsTextSize(14);
		// Add single renderer to multiple renderer
		mRenderer.addSeriesRenderer(renderer);	
	}

	public GraphicalView getView(Context context) 
	{
		view =  ChartFactory.getLineChartView(context, mDataset, mRenderer);
		return view;
	}
	
	public void addNewPoints(Point p)
	{
		dataset.add(p.getX(), p.getY());
	//	dataset1.add(p.getX(),p.getY());
	}
}
