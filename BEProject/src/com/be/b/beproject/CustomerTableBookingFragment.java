package com.be.b.beproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CustomerTableBookingFragment extends Activity{
	View v;
	Button customerBookTableButton;
	TimePicker timePicker;
	EditText numberOfSeats;
	TextView tv;
	SQLiteDatabase queryHandler;
	String RestId;
	String TableOrderDate;
	String dateForDateSpinner[];
	Spinner tableOrderDateSpinner;
	int flag;
	static String TableOrderId;
	String time;
	static int TableWithFoodFlag=0;
	Button customerAllTableOrdersListButton;
	Context context=this;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.customer_table_booking);
		
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Book Table</font>"));
		flag=0;
		customerAllTableOrdersListButton=(Button)findViewById(R.id.customerAllTableOrdersListButton);
		customerAllTableOrdersListButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(CustomerTableBookingFragment.this,CustomerAllTableOrdersHistory.class);
				startActivity(i);
			}
		});
		
		customerBookTableButton = (Button) findViewById(R.id.customerBookTableButton);
		timePicker=(TimePicker)findViewById(R.id.tableOrderTimingtTimePicker);
		numberOfSeats=(EditText)findViewById(R.id.noOfSeatsEditText);
		timePicker.setIs24HourView(true);
		tv=(TextView)findViewById(R.id.noOfSeatsTextView);
		
		dateForDateSpinner=new String[3];
		TableOrderDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		dateForDateSpinner[0]=new SimpleDateFormat("MMM dd").format(new Date());;
		Log.i("plus one",dateForDateSpinner[0]);
		SimpleDateFormat frmt=new SimpleDateFormat("MMM dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(TableOrderDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 1);  // number of days to add
		dateForDateSpinner[1] = frmt.format(c.getTime());  // dt is now the new date
		Log.i("plus one",dateForDateSpinner[1]);
		 c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(TableOrderDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 2);  // number of days to add
		dateForDateSpinner[2] = frmt.format(c.getTime());  // dt is now the new date
		Log.i("plus one",dateForDateSpinner[2]);
		
		tableOrderDateSpinner=(Spinner)findViewById(R.id.customerTableOrderDateSpinner);
		ArrayAdapter<String> DateAdapter=new  ArrayAdapter<String>(CustomerTableBookingFragment.this, android.R.layout.simple_spinner_item,dateForDateSpinner);
		DateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tableOrderDateSpinner.setAdapter(DateAdapter);
		
		
		
		
		
		RestId=CustomerConfig.RestId;
		customerBookTableButton.setOnClickListener(new View.OnClickListener() {
		@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				time="";
			
				time=timePicker.getCurrentHour().toString();
				time+=":"+timePicker.getCurrentMinute().toString()+":00";
				
				String TempYear = new SimpleDateFormat("yyyy").format(new Date());
				
				
				TableOrderDate=tableOrderDateSpinner.getSelectedItem().toString();
				
				TableOrderDate=TempYear+" "+TableOrderDate;
				if(!(numberOfSeats.getText().toString().equals("")) ){
					int val=Integer.parseInt(numberOfSeats.getText().toString());
					if(val<11 && val>0){
						final Dialog dialog =new Dialog(context);
						dialog.setContentView(R.layout.save_card_detail_dialog);
						dialog.setTitle("Place order for food?");
						
						TextView text=(TextView) dialog.findViewById(R.id.textView1);
						text.setText(" ");
						Button ok=(Button) dialog.findViewById(R.id.button1);
						Button cancel=(Button) dialog.findViewById(R.id.button2);

						dialog.show();
						ok.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								TableWithFoodFlag=1;
								new AddNewPrediction().execute(RestId,time);
								dialog.dismiss();
							}
							
						});
			    		
						cancel.setOnClickListener(new View.OnClickListener() {
						@Override
							public void onClick(View v) {
							new AddNewPrediction().execute(RestId,time);
							dialog.dismiss();
							}
						});
			    	
					}else{
						Toast.makeText(CustomerTableBookingFragment.this, "Please enter valid no. of guests", Toast.LENGTH_SHORT).show();
					}
					
				}
				else{
					Toast.makeText(CustomerTableBookingFragment.this, "Please enter correct values", Toast.LENGTH_SHORT).show();
				}
			}
		});
	
	}
	
	private class AddNewPrediction extends AsyncTask<String, Void, Void> {
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
            protected Void doInBackground(String... arg) {
        	String RestId=arg[0];
        	String time=arg[1];
        	
        	Log.d("url: ", "> " + RestId);
        	
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("RestId", RestId));
	        params.add(new BasicNameValuePair("TableOrderNoOfSeats", numberOfSeats.getText().toString()));
	        params.add(new BasicNameValuePair("TableOrderTime",time));
	        params.add(new BasicNameValuePair("TableOrderDate", TableOrderDate));
	        params.add(new BasicNameValuePair("CustomerName",CustomerConfig.CustomerName));
	        params.add(new BasicNameValuePair("CustomerPhone",CustomerConfig.CustPhoneNo));
	        params.add(new BasicNameValuePair("CustomerId",CustomerConfig.CustId));
	        params.add(new BasicNameValuePair("BasePayment","100"));
	        params.add(new BasicNameValuePair("RestaurantName",CustomerConfig.RestName));
	        
	        
            ServiceHandler serviceClient = new ServiceHandler();
            Log.d("url: ", "> " + CustomerConfig.URL_ITEMS+"PlaceTableOrder.php");
            String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"PlaceTableOrder.php",ServiceHandler.POST,params);
            Log.i("json",json);
                       
            if (json != null) {
                try {
                	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
					json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));	
					
                	Log.i("json modified",json);
                	JSONObject jsonObj = new JSONObject(json);
   				 
                    int error = jsonObj.getInt("error");
                    // checking for error node in json
                    if (error==0) {
                        // new category created successfully
                    	flag=1;
                    	TableOrderId=jsonObj.getString("TableOrderId");
                    } else if(error==2){
                    	flag=2;
                    }else {
                        Log.e("LOGIN Error: ",
                                "> " + jsonObj.getString("message"));
                    }
                	
           
      
       	 }
       catch (Exception e) {
           Log.d("catch", "in the catch");
           e.printStackTrace();
       }
   } else {
       Log.e("JSON Data", "Didn't receive any data from server!");
   }
 
            return null;
        }
	
        @Override
        protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(flag==1){
        Toast.makeText(CustomerTableBookingFragment.this, "Table order sent for processing. Thank you!   Order Id : "+TableOrderId, Toast.LENGTH_SHORT).show();
        if(TableWithFoodFlag==1){
        	Intent i = new Intent(CustomerTableBookingFragment.this,CustomerFoodOrderCategoryProcessClass.class);
            startActivity(i);
            finish();
        }else{
        Intent i = new Intent(CustomerTableBookingFragment.this,CustomerListOfRestaurants.class);
        startActivity(i);
        finish();
        	}
        }else if(flag==2){
        	Toast.makeText(CustomerTableBookingFragment.this, "restaurant not accepting Table orders!", Toast.LENGTH_SHORT).show();
        }
        else{
        	Toast.makeText(CustomerTableBookingFragment.this, "unable to place order for table", Toast.LENGTH_SHORT).show();
        }
    }
}
}
