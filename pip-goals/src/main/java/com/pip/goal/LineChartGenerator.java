package com.pip.goal;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Component;

@Component
public class LineChartGenerator {
	public void generateLineChart() {
		try {
			System.out.println("Line Chart image creation started...");

			DefaultCategoryDataset dataset = createDataset_DailyTrend();

			/* Step -2:Define the JFreeChart object to create bar chart */

			JFreeChart lineChart = ChartFactory.createLineChart("Daily Trend", "Plan", "", dataset,
					PlotOrientation.VERTICAL, true, true, false);

			CategoryPlot plot = lineChart.getCategoryPlot();

			// Create the Y-Axis as percentage
			lineChart.getCategoryPlot().getRangeAxis().setLowerBound(0);
			lineChart.getCategoryPlot().getRangeAxis().setUpperBound(1);
			NumberAxis xAxis2 = (NumberAxis) lineChart.getCategoryPlot().getRangeAxis();
			xAxis2.setNumberFormatOverride(NumberFormat.getPercentInstance());

			DecimalFormat labelFormat = new DecimalFormat("##0.0%");
			labelFormat.setMultiplier(100);

			// Apply label format on Self Served (%),HIPAA Authenticated (%)
			plot.getRenderer().setSeriesItemLabelGenerator(0,
					new StandardCategoryItemLabelGenerator("{2}", labelFormat));
			plot.getRenderer().setSeriesItemLabelGenerator(1,
					new StandardCategoryItemLabelGenerator("{2}", labelFormat));
			plot.getRenderer().setSeriesItemLabelsVisible(0, true);
			plot.getRenderer().setBaseItemLabelsVisible(true);
			plot.getRenderer().setBaseSeriesVisible(true);
			lineChart.getCategoryPlot().setRenderer(plot.getRenderer());

			/* Step -3: Write the output as PNG file with bar chart information */
			int width = 640; /* Width of the image */
			int height = 480; /* Height of the image */
			File BarChart = new File("C:\\siva\\PIP\\pip-goals\\src\\main\\resources\\LineChart_dailyTrend.jpeg");

			// Save the file to current root folder
			ChartUtilities.saveChartAsJPEG(BarChart, lineChart, width, height);
			System.out.println("Image created successfully...");
		} catch (Exception i) {
			System.out.println(i);
		}
	}

	public DefaultCategoryDataset createDataset_DailyTrend() {
		/* Step - 1: Define the data for the bar chart */
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(0.3775, "Self Served (%)", "MAPD");
		dataset.addValue(0.3511, "HIPAA Authenticated (%)", "MAPD");
		dataset.addValue(0.4502, "Self Served (%)", "PDP");
		dataset.addValue(0.6368, "HIPAA Authenticated (%)", "PDP");
		dataset.addValue(0.4778, "Self Served (%)", "TEXAS");
		dataset.addValue(0.4500, "HIPAA Authenticated (%)", "TEXAS");
		dataset.addValue(0.4522, "Self Served (%)", "IL CAID");
		dataset.addValue(0.5807, "HIPAA Authenticated (%)", "IL CAID");
		dataset.addValue(0.4542, "Self Served (%)", "MMAI");
		dataset.addValue(0.5358, "HIPAA Authenticated (%)", "MMAI");
		dataset.addValue(0.3982, "Self Served (%)", "JHHC");
		dataset.addValue(0.5858, "HIPAA Authenticated (%)", "JHHC");
		dataset.addValue(0.4361, "Self Served (%)", "ULTIMATE");
		dataset.addValue(0.5639, "HIPAA Authenticated (%)", "ULTIMATE");
		return dataset;
	}
}
