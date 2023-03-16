package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantNotificationFragment extends Activity{
	//private String URL_ITEMS = "http://192.168.1.7/sampleserver/ReadOrders.php";
	//private String URL_ITEMS = "http://192.168.1.4/sampleserver/ReadOrders.php";
	
	String[] values;
	String RestId;
	TextView t2,t3,t4,t5,t6,t7,t8;
	View v;
	private List<RestaurantNotificationFieldClass> list=new ArrayList<RestaurantNotificationFieldClass>();
	TableLayout tl;
	Button showOrderHistoryButton;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_notification);
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Food Ordered Page</font>"));
		showOrderHistoryButton=(Button)findViewById(R.id.showOrderHistoryButton);
		showOrderHistoryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(RestaurantNotificationFragment.this,RestaurantCompleteOrderHistory.class);
				startActivity(i);
				
				//getActivity().finish();
			}
		});
		
	 
		RestId=RestaurantConfig.RestId;
		new AddNewPrediction().execute(RestId);
		
	}
	
	private class AddNewPrediction extends AsyncTask<String, Void, Void> {
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
            protected Void doInBackground(String... arg) {
        	String RestId=arg[0];
        	Log.d("url: ", "> " + RestId);
        	
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("RestaurantId", RestId));
        	
        	
            ServiceHandler serviceClient = new ServiceHandler();
            Log.d("url: ", "> " + RestaurantConfig.URL_ITEMS+"ReadOrders.php");
            String json = serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"ReadOrders.php",ServiceHandler.POST,params);
            Log.i("json",json);
                       
            if (json != null) {
                try {
                	json=(json.substring(json.indexOf("</table></font>"), json.lastIndexOf("}") + 1));
					json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));	
					
                	Log.i("json modified",json);
					
                	JSONObject jObj=new JSONObject(json);
                	Log.i("object","success");
					JSONArray jArray=jObj.getJSONArray("OrderList");
                    Log.i("array",jArray.toString());
                                       
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject c = jArray.getJSONObject(i);
                        
                      
                       String  ItemNames = c.getString("ItemNames");
                       String Amount  = c.getString("Amount");
                       String CustomerNo = c.getString("CustomerNo");
                       String CustomerName = c.getString("CustomerName");
                       String OrderId=c.getString("OrderId");
                       String OrderDate=c.getString("OrderDate");
                       String OrderTime=c.getString("OrderTime");
                       String TypeOfPayment=c.getString("TypeOfPayment");
                       list.add(new RestaurantNotificationFieldClass(ItemNames, Amount, CustomerNo,CustomerName,OrderId,OrderDate,OrderTime,TypeOfPayment));
                     }
                }
                catch (JSONException e) {
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
            ArrayAdapter<RestaurantNotificationFieldClass> adapter=new MyListAdapter();
         
            ListView listView=(ListView)findViewById(R.id.placedOrdersNotificationListView);
            listView.setAdapter(adapter);
            
        }
    }
	
	private class MyListAdapter extends ArrayAdapter<RestaurantNotificationFieldClass>{

		public MyListAdapter() {
			super(RestaurantNotificationFragment.this, R.layout.restaurant_notification_list_row,list);
			
		}
		  
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView=convertView;
			if(itemView==null)
			{
				itemView= getLayoutInflater().inflate(R.layout.restaurant_notification_list_row, parent, false);
				
			}
			RestaurantNotificationFieldClass obj=list.get(position);
			
			 tl = (TableLayout) itemView.findViewById(R.id.main_table);
			 tl.removeAllViews();
			TableRow tr_head = new TableRow(getApplicationContext());
			tr_head.setId(10);
			tr_head.setBackgroundColor(Color.GRAY);
			tr_head.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			
	//adding the column headers
			TextView label_Name = new TextView(getApplicationContext());
			label_Name.setId(20);
			label_Name.setText("ItemNames");
			label_Name.setTextColor(Color.WHITE);
			label_Name.setPadding(0, 5, 5, 5);
	        tr_head.addView(label_Name);
	        
	        TextView label_Price = new TextView(getApplicationContext());
	        label_Price.setId(21);// define id that must be unique
	        label_Price.setText("Price"); // set the text for the header 
	        label_Price.setTextColor(Color.WHITE); // set the color
	        label_Price.setPadding(0, 5, 5, 5); // set the padding (if required)
	        tr_head.addView(label_Price); // add the column to the table row here		
			
	        
	        TextView label_Quantity = new TextView(getApplicationContext());
	        label_Quantity.setId(22);
	        label_Quantity.setText("Quantity");
	        label_Quantity.setTextColor(Color.WHITE);
	        label_Quantity.setPadding(0, 5, 5, 5);
	        tr_head.addView(label_Quantity);
	        
	        TextView label_Total = new TextView(getApplicationContext());
	        label_Total.setId(23);
	        label_Total.setText("Total");
	        label_Total.setTextColor(Color.WHITE);
	        label_Total.setPadding(0, 5, 5, 5);
	        tr_head.addView(label_Total);
	        
	        tl.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			
	        StringTokenizer stringTokenizer = new StringTokenizer(obj.getItemNames().replace("\n","").toString(), "|");
	        int count=0;
	        while (stringTokenizer.hasMoreElements()) {
	        
	        	TableRow tr = new TableRow(getApplicationContext());
	            tr.setBackgroundColor(Color.WHITE);
	           tr.setId(100+count);
	           tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

	           //Create two columns to add as table data
	            // Create a TextView to add date
	           TextView Name = new TextView(getApplicationContext());
	           Name.setId(200+count); 
	           Name.setText(stringTokenizer.nextElement().toString());
	           Name.setPadding(2, 0, 5, 0);
	           Name.setTextColor(Color.BLACK);
	           tr.addView(Name);
	           TextView Price = new TextView(getApplicationContext());
	           Price.setId(200+count);
	           Price.setText(stringTokenizer.nextElement().toString());
	           Price.setTextColor(Color.BLACK);
	           tr.addView(Price);

	           
	           TextView Quantity = new TextView(getApplicationContext());
	           Quantity.setId(300+count);
	           Quantity.setText(stringTokenizer.nextElement().toString());
	           Quantity.setTextColor(Color.BLACK);
	           tr.addView(Quantity);
	           
	           TextView Total = new TextView(getApplicationContext());
	           Total.setId(400+count);
	           Total.setText(stringTokenizer.nextElement().toString());
	           Total.setTextColor(Color.BLACK);
	           tr.addView(Total);
	           
	           // finally add this to the table row
	           tl.addView(tr, new TableLayout.LayoutParams(
	           	                    LayoutParams.FILL_PARENT,
	           	                    LayoutParams.WRAP_CONTENT));
	        
	        count++;
	        }
	        
	        
	        
			t2=(TextView) itemView.findViewById(R.id.amounttextView);
			t2.setText(obj.getAmount());
			t3=(TextView) itemView.findViewById(R.id.customerNotextView);
			t3.setText(obj.getCustomerNo());
			t4=(TextView) itemView.findViewById(R.id.customerNametextView);
			t4.setText(obj.getCustomerName());
			t5=(TextView) itemView.findViewById(R.id.orderId);
			t5.setText(obj.getOrderId());
			t6=(TextView) itemView.findViewById(R.id.orderDateTextView);
			t6.setText(obj.getOrderDate());
			t7=(TextView) itemView.findViewById(R.id.orderTimeTextView);
			t7.setText(obj.getOrderTime());
			t8=(TextView) itemView.findViewById(R.id.orderTypeOfPaymentTextView);
			t8.setText(obj.getTypeOfPayment());
			Button restaurantOrderDeleteButton=(Button) itemView.findViewById(R.id.restaurantOrderDeleteButton);
			restaurantOrderDeleteButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					RelativeLayout rl =(RelativeLayout)v.getParent();
					TextView tv=(TextView) rl.findViewById(R.id.orderId);
					String OrderId=tv.getText().toString();
					Intent i = new Intent(RestaurantNotificationFragment.this,RestaurantDeleteOrderProcess.class);
					i.putExtra("OrderId", OrderId);
					//RestaurantConfig.OrderId=OrderId;
					Log.i("in fragment activity",OrderId);
					startActivity(i);
					list.clear();
					finish();
				}
			});
			
			
			return itemView;
		}
			
	  }
	
	

	
}
