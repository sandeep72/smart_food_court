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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CustomerCompleteOrderHistory extends Activity{

	String[] values;
	String CustId;
	TextView t1,t2,t3,t4,t5,t6,t7;
	View v;
	TableLayout tl;
	private List<CustomerOrderHistoryFieldClass> list = new ArrayList<CustomerOrderHistoryFieldClass>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_complete_order_history);
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Food Order History</font>"));
		CustId=CustomerConfig.CustId;
		new AddNewPrediction().execute(CustId);
	}
	private class AddNewPrediction extends AsyncTask<String, Void, Void> {
		
		@Override
        protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub
			String CustId=arg[0];
        	Log.d("url: ", "> " + CustId);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("CustomerId", CustId));
        	
        	
            ServiceHandler serviceClient = new ServiceHandler();
            Log.d("url: ", "> " + CustomerConfig.URL_ITEMS+"ReadCustomerOrdersForAllRestaurantsHistory.php");
            String json = serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"ReadCustomerOrdersForAllRestaurantsHistory.php",ServiceHandler.POST,params);
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
                       String RestName = c.getString("RestName");
                       String OrderDate = c.getString("OrderDate");
                       String OrderId=c.getString("OrderId");
                       String OrderTime=c.getString("OrderTime");
                       String TypeOfPayment=c.getString("TypeOfPayment");
                       list.add(new CustomerOrderHistoryFieldClass(ItemNames, Amount, OrderDate,RestName,OrderId,OrderTime,TypeOfPayment));
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
			ArrayAdapter<CustomerOrderHistoryFieldClass> adapter=new MyListAdapter();
			
			ListView listView=(ListView) findViewById(R.id.customerAllRestaurantOrdersListView);
			listView.setAdapter(adapter);
		}
	}

	private class MyListAdapter extends ArrayAdapter<CustomerOrderHistoryFieldClass>{
		public MyListAdapter() {
			super(CustomerCompleteOrderHistory.this, R.layout.customer_orders_list_row,list);
			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView=convertView;
			if(itemView==null)
			{
				itemView= getLayoutInflater().inflate(R.layout.customer_orders_list_row, parent, false);
				
			}
			CustomerOrderHistoryFieldClass obj=list.get(position);
			
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
			t2=(TextView) itemView.findViewById(R.id.customerTotalAmountTextView);
			t2.setText(obj.getAmount());
			t3=(TextView) itemView.findViewById(R.id.customerRestaurantNameTextView);
			t3.setText(obj.getRestName());
			t4=(TextView) itemView.findViewById(R.id.customerOrderDateTextView);
			t4.setText(obj.getOrderDate());
			t5=(TextView) itemView.findViewById(R.id.customersOrderIdTextView);
			t5.setText(obj.getOrderId());
			t6=(TextView) itemView.findViewById(R.id.customerOrderTimeTextView);
			t6.setText(obj.getOrderTime());
			t7=(TextView) itemView.findViewById(R.id.customerTypeOfPaymentTextView);
			t7.setText(obj.getTypeOfPayment());
			//t5.setVisibility(View.INVISIBLE);
			
	
			
			return itemView;
		}
	}
}