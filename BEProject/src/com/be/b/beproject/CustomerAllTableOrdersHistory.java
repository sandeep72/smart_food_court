package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CustomerAllTableOrdersHistory extends Activity{
	
	private List<CustomerTableOrderHistoryFieldClass> list = new ArrayList<CustomerTableOrderHistoryFieldClass>();
	String CustId;
	TextView t1,t2,t3,t4,t5,t6,t7,t8;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_all_table_order_history);
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Table Order History</font>"));
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
	        params.add(new BasicNameValuePair("CustomerId",CustId));
        	
        	
            ServiceHandler serviceClient = new ServiceHandler();
            Log.d("url: ", "> " + CustomerConfig.URL_ITEMS+"ReadCustomerTableOrdersForAllRestaurantsHistory.php");
            String json = serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"ReadCustomerTableOrdersForAllRestaurantsHistory.php",ServiceHandler.POST,params);
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
                        
                      
                       String TableOrderId = c.getString("TableOrderId");
                       String NoOfSeats  = c.getString("NoOfSeats");
                       String Time = c.getString("Time");
                       String Date = c.getString("Date");
                       String BasePayment=c.getString("BasePayment");
                       String RestaurantName=c.getString("RestaurantName");
                       String RestaurantId=c.getString("RestaurantId");
                       String Flag=c.getString("Flag");
                       list.add(new CustomerTableOrderHistoryFieldClass(TableOrderId,NoOfSeats, Time, Date, RestaurantId,RestaurantName, BasePayment,Flag));
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
			ArrayAdapter<CustomerTableOrderHistoryFieldClass> adapter=new MyListAdapter();
			
			ListView listView=(ListView) findViewById(R.id.customerAllRestaurantTableOrdersListView);
			listView.setAdapter(adapter);
		}
	}

	private class MyListAdapter extends ArrayAdapter<CustomerTableOrderHistoryFieldClass>{
		public MyListAdapter() {
			super(CustomerAllTableOrdersHistory.this, R.layout.customer_table_orders_list_row,list);
			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView=convertView;
			if(itemView==null)
			{
				itemView= getLayoutInflater().inflate(R.layout.customer_table_orders_list_row, parent, false);
				
			}
			CustomerTableOrderHistoryFieldClass obj=list.get(position);
			
			t1=(TextView) itemView.findViewById(R.id.customerTableOrderIdTextView);
			t1.setText(obj.getTableOrderId());
			t2=(TextView) itemView.findViewById(R.id.customerTableOrderNoOfSeatsTextView);
			t2.setText(obj.getNoOfSeats());
			t3=(TextView) itemView.findViewById(R.id.customerTableOrderTimeTextView);
			t3.setText(obj.getTime());
			t4=(TextView) itemView.findViewById(R.id.customerTableOrderDateTextView);
			t4.setText(obj.getDate());
			t5=(TextView) itemView.findViewById(R.id.customerTableOrderRestaurantIdTextView);
			t5.setText(obj.getRestaurantId());
			t6=(TextView) itemView.findViewById(R.id.customerTableOrderRestaurantNameTextView);
			t6.setText(obj.getRestaurantName());
			t7=(TextView) itemView.findViewById(R.id.customerTableOrderBasePaymentTextView);
			t7.setText(obj.getBasePayment());
			t8=(TextView) itemView.findViewById(R.id.customerTableOrderFlag);
			//t8.setText(obj.getBasePayment());
			if(obj.getFlag().equals("0")){
				t8.setText("PENDING");
			}else{
				if(obj.getFlag().equals("1")){
					t8.setText("Confirmed");
			}else{
				t8.setText("Cancelled");
			}
			}
			//t5.setVisibility(View.INVISIBLE);
			
	
			
			return itemView;
		}
	}
}
