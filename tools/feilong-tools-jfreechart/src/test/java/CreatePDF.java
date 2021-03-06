import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.feilong.tools.jfreechart.ChartUtil;

/**
 * A simple demonstration showing how to write a chart to PDF format using JFreeChart and iText.
 * <P>
 * You can download iText from http://www.lowagie.com/iText.
 */
public class CreatePDF{

	/**
	 * Creates a dataset, consisting of two series of monthly data. * *
	 * 
	 * @return the dataset.
	 */
	public static XYDataset createDataset(){
		TimeSeries s1 = new TimeSeries("L&G European Index Trust", Month.class);
		s1.add(new Month(2, 2001), 181.8);
		s1.add(new Month(3, 2001), 167.3);
		s1.add(new Month(4, 2001), 153.8);
		s1.add(new Month(5, 2001), 167.6);
		s1.add(new Month(6, 2001), 158.8);
		s1.add(new Month(7, 2001), 148.3);
		s1.add(new Month(8, 2001), 153.9);
		s1.add(new Month(9, 2001), 142.7);
		s1.add(new Month(10, 2001), 123.2);
		s1.add(new Month(11, 2001), 131.8);
		s1.add(new Month(12, 2001), 139.6);
		s1.add(new Month(1, 2002), 142.9);
		s1.add(new Month(2, 2002), 138.7);
		s1.add(new Month(3, 2002), 137.3);
		s1.add(new Month(4, 2002), 143.9);
		s1.add(new Month(5, 2002), 139.8);
		s1.add(new Month(6, 2002), 137.0);
		s1.add(new Month(7, 2002), 132.8);
		TimeSeries s2 = new TimeSeries("L&G UK Index Trust", Month.class);
		s2.add(new Month(2, 2001), 129.6);
		s2.add(new Month(3, 2001), 123.2);
		s2.add(new Month(4, 2001), 117.2);
		s2.add(new Month(5, 2001), 124.1);
		s2.add(new Month(6, 2001), 122.6);
		s2.add(new Month(7, 2001), 119.2);
		s2.add(new Month(8, 2001), 116.5);
		s2.add(new Month(9, 2001), 112.7);
		s2.add(new Month(10, 2001), 101.5);
		s2.add(new Month(11, 2001), 106.1);
		s2.add(new Month(12, 2001), 110.3);
		s2.add(new Month(1, 2002), 111.7);
		s2.add(new Month(2, 2002), 111.0);
		s2.add(new Month(3, 2002), 109.6);
		s2.add(new Month(4, 2002), 113.2);
		s2.add(new Month(5, 2002), 111.6);
		s2.add(new Month(6, 2002), 108.8);
		s2.add(new Month(7, 2002), 101.6);
		TimeSeries s3 = new TimeSeries("L&G UK Index Trust", Month.class);
		s3.add(new Month(2, 2001), 119.6);
		s3.add(new Month(3, 2001), 113.2);
		s3.add(new Month(4, 2001), 127.2);
		s3.add(new Month(5, 2001), 114.1);
		s3.add(new Month(6, 2001), 112.6);
		s3.add(new Month(7, 2001), 129.2);
		s3.add(new Month(8, 2001), 126.5);
		s3.add(new Month(9, 2001), 122.7);
		s3.add(new Month(10, 2001), 111.5);
		s3.add(new Month(11, 2001), 116.1);
		s3.add(new Month(12, 2001), 120.3);
		s3.add(new Month(1, 2002), 121.7);
		s3.add(new Month(2, 2002), 121.0);
		s3.add(new Month(3, 2002), 119.6);
		s3.add(new Month(4, 2002), 123.2);
		s3.add(new Month(5, 2002), 121.6);
		s3.add(new Month(6, 2002), 118.8);
		s3.add(new Month(7, 2002), 121.6);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);
		dataset.addSeries(s2);
		dataset.addSeries(s3);
		return dataset;
	}

	public static void main(String[] args){
		try{
			// create a chart...  
			XYDataset dataset = createDataset();
			JFreeChart chart = ChartFactory.createTimeSeriesChart("Legal & General Unit Trust Prices", "Date", "Price Per Unit", dataset, true, true, false);
			// some additional chart customisation here...  
			XYPlot plot = chart.getXYPlot();
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
			renderer.setShapesVisible(true);
			DateAxis axis = (DateAxis) plot.getDomainAxis();
			axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
			// write the chart to a PDF file...  
			File file = new File("E://jfreechart1.pdf");
			OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			ChartUtil.writeChartAsPDF(out, chart, 400, 300);
			out.close();
		}catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
}
