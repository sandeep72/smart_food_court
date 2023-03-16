package com.be.b.beproject;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
//import android.widget.TextView;

public class CustomerRestaurantRatingBarGraph extends Activity{
//TextView text;
String[] RestName=new String[5];
double [][] RestRating;
int totalRest,counter;
String[] mParam={"Ambience","Service","Food","ValueForMoney"};
int[] x={1,2,3,4};
ArrayList<CustomerRestaurantReviewCompareFieldClass> list;
CustomerRestaurantReviewCompareFieldClass obj;


//Button asdfButton;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.customer_restaurant_rating_bargraph);
	//text=(TextView) findViewById(R.id.textView1);
	
	openChart();
//	asdfButton=(Button)findViewById(R.id.asdfbutton);
//	asdfButton.setOnClickListener(new View.OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			openChart();
//		}
//	});
	
	
}
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.rest, menu);
	return true;
}


	protected void openChart(){
		counter=0;
		//list=(ArrayList<CustomerRestaurantReviewCompareFieldClass>)getIntent().getSerializableExtra("list");
		// String temp="";
		 totalRest=CustomerRestaurantRatingGraphRestaurantList.list.size();
		 
		 RestRating=new double[totalRest][];
		 
		 for(int i=0;i<totalRest;i++){
			 RestRating[i]=new double[4];
		 }
		 
		 Log.i("total rest",""+totalRest);
		 
		 for(int i=0;i<totalRest;i++){
			 obj=CustomerRestaurantRatingGraphRestaurantList.list.get(i);
			 //temp=temp+obj.getRestName()+"   "+obj.getAmbienceRating()+"   "+obj.getFoodRating()+"    "+obj.getServiceRating()+"    "+obj.getValueForMoneyRating();
			RestName[counter]=obj.getRestName();
			RestRating[counter][0]=Double.parseDouble(obj.getAmbienceRating());
			RestRating[counter][2]=Double.parseDouble(obj.getFoodRating());
			RestRating[counter][1]=Double.parseDouble(obj.getServiceRating());
			RestRating[counter][3]=Double.parseDouble(obj.getValueForMoneyRating());
			
			
			Log.i("Rest name",RestName[counter]);
			Log.i("Ambience Rating",""+RestRating[counter][0]);
			Log.i("Food Rating",""+RestRating[counter][2]);
			Log.i("Service Rating",""+RestRating[counter][1]);
			Log.i("Value For Money Rating",""+RestRating[counter][3]);
			counter++;
		 }
		 //text.setText(temp);
		XYSeries seriesArray[]= new XYSeries[totalRest];
		for(int i=0;i<totalRest;i++){
			seriesArray[i]=new XYSeries("Reviews of Restaurant "+RestName[i]);
		}
		
		Log.i("total rest",""+totalRest);
		Log.i("x length",""+x.length);
		for(int j=0;j<totalRest;j++){
		for(int i=0;i<x.length;i++)
		{
			//series.add(x[i], RestA[i]);
			//series1.add(x[i],RestB[i]);
			seriesArray[j].add(x[i], RestRating[j][i]);
			
		}
		}
		XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();
		
		for(int i=0;i<totalRest;i++){
			dataset.addSeries(seriesArray[i]);
		}
		
		
		XYSeriesRenderer renderer[]= new XYSeriesRenderer[totalRest];
		for(int i=0;i<totalRest;i++){
			renderer[i]=new XYSeriesRenderer();
			//renderer[i].setColor(Color.BLACK);
			renderer[i].setPointStyle(PointStyle.CIRCLE);
			renderer[i].setFillPoints(true);
			renderer[i].setLineWidth(1);
			//renderer[i].setFillBelowLine(true);
			renderer[i].setDisplayChartValues(true);
			if(i==0)
				renderer[i].setColor(Color.argb(150, 218,165,32));
			else if(i==1)
				renderer[i].setColor(Color.argb(150, 0,255,255));
			else if(i==2)
				renderer[i].setColor(Color.argb(150, 154,205,50));
			else 
				renderer[i].setColor(Color.argb(150, 255,0,255));
				
		}
		
		
		
		XYMultipleSeriesRenderer multirenderer=new XYMultipleSeriesRenderer();
		multirenderer.setXLabels(0);
		multirenderer.setChartTitle("Comparison Chart");
		multirenderer.setXTitle("Reviews for restaurants");
		
		multirenderer.setYTitle("Performance Rating");
		multirenderer.setZoomButtonsVisible(true);
		//multirenderer.setBarSpacing(2);
		//multirenderer.setBackgroundColor(Color.BLACK);
	
		for(int i=0;i<x.length;i++)
		{
			multirenderer.addXTextLabel(i+1,mParam[i]);
			multirenderer.setBarSpacing(0.05);
		}
		for(int i=0;i<totalRest;i++){
			multirenderer.addSeriesRenderer(renderer[i]);
		}
		Intent intent=ChartFactory.getBarChartIntent(getBaseContext(), dataset, multirenderer,null);
		//Intent intent=ChartFactory.getLineChartIntent(getBaseContext(), dataset,multirenderer);
		startActivity(intent);
		finish();
	}
	
	@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent i=new Intent(CustomerRestaurantRatingBarGraph.this,CustomerRestaurantRatingGraphRestaurantList.class);
			startActivity(i);
			finish();
		}
}
/*
 * 
 */
