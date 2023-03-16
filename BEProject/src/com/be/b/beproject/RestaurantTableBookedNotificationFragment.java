package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;



import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantTableBookedNotificationFragment extends Activity{
	//SQLiteDatabase dbHandler;
	//Cursor tableOrderHandlerCursor;
	//String[] values;
	View v;
	String RestId;
	private List<RestaurantTableOrderNotificationFieldClass> list=new ArrayList<RestaurantTableOrderNotificationFieldClass>();
	TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12;
	String TableOrderId,CustomerId;
	Button RestaurantTableHistoryButton;
	SwipeListView listView;
	Context context=this;
	TableLayout tl;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_table_booking_notification);
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Tables Booked ....</font>"));
		list.clear();
		RestId=RestaurantConfig.RestId;
		RestaurantTableHistoryButton=(Button) findViewById(R.id.RestaurantTableHistoryButton);
		RestaurantTableHistoryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getApplicationContext(),RestaurantCompleteTableOrderHistory.class);
				startActivity(i);
				
			}
		});
	
		 listView=(SwipeListView) findViewById(R.id.placedTableOrderNotificationListView);
		
		listView.setSwipeListViewListener(new BaseSwipeListViewListener(){
			
			@Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
                
             
                listView.openAnimate(position); //when you touch front view it will open
              
             
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
                
                listView.closeAnimate(position);//when you touch back view it will close
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
            	
            }
		
		
		
		
		
		
		});
		
		
		listView.setSwipeMode(SwipeListView.SWIPE_MODE_BOTH); // there are five swiping modes
        listView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_DISMISS); //there are four swipe actions 
        listView.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
        listView.setOffsetLeft(convertDpToPixel(0f)); // left side offset
        listView.setOffsetRight(convertDpToPixel(10f)); // right side offset
        listView.setAnimationTime(500); // Animation time
        listView.setSwipeOpenOnLongPress(true); 
				
		new AddNewPrediction().execute(RestId);
				
	}
	
	  public int convertDpToPixel(float dp) {
	        DisplayMetrics metrics = getResources().getDisplayMetrics();
	        float px = dp * (metrics.densityDpi / 160f);
	        return (int) px;
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
            Log.d("url: ", "> " + RestaurantConfig.URL_ITEMS+"ReadTableOrdersOfRestaurant.php");
            String json = serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"ReadTableOrdersOfRestaurant.php",ServiceHandler.POST,params);
            Log.i("json",json);
                       
            if (json != null) {
                try {
                	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
					json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));	
					
                	Log.i("json modified",json);
					
                	JSONObject jObj=new JSONObject(json);
                	Log.i("object","success");
					JSONArray jArray=jObj.getJSONArray("OrderList");
                    Log.i("array",jArray.toString());
                                       
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject c = jArray.getJSONObject(i);
                        
                       
                       String TableOrderId=c.getString("TableOrderId");
                       String CustomerName=c.getString("CustomerName");
                       String CustomerPhone=c.getString("CustomerPhone");
                       String NoOfSeats=c.getString("NoOfSeats");
                       String Time=c.getString("Time");
                       String Date=c.getString("Date"); 
                       String CustomerId=c.getString("CustomerId");
                       String ItemNames=c.getString("ItemNames");
                       String Amount=c.getString("Amount");
                       String TypeOfPayment=c.getString("TypeOfPayment");
                       list.add(new RestaurantTableOrderNotificationFieldClass(TableOrderId, CustomerName, CustomerPhone,NoOfSeats,Time,Date,CustomerId,"DUMMY",Amount,TypeOfPayment,ItemNames));
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
            ArrayAdapter<RestaurantTableOrderNotificationFieldClass> adapter=new MyListAdapter();
         
            
            listView.setAdapter(adapter);
            
        }
    }
	private class MyListAdapter extends ArrayAdapter<RestaurantTableOrderNotificationFieldClass>{
		
		public MyListAdapter() {
			super(RestaurantTableBookedNotificationFragment.this, R.layout.restaurant_table_order_notification_list_row,list);
			
		}
		  
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView=convertView;
			if(itemView==null)
			{
				itemView= getLayoutInflater().inflate(R.layout.restaurant_table_order_notification_list_row, parent, false);
				 
			}
			RestaurantTableOrderNotificationFieldClass obj=list.get(position);
			
			t1=(TextView) itemView.findViewById(R.id.tableOrderIdTextView);
			t1.setText(obj.getTableOrderId());
			t2=(TextView) itemView.findViewById(R.id.tableOrderCustomerNameTextView);
			t2.setText(obj.getCustomerName());
			t3=(TextView) itemView.findViewById(R.id.tableOrderCustomerPhoneTextView);
			t3.setText(obj.getCustomerPhone());
			t4=(TextView) itemView.findViewById(R.id.tableOrderNoOfSeatsTextView);
			t4.setText(obj.getNoOfSeats());
			t5=(TextView) itemView.findViewById(R.id.tableOrderTimeTextView);
			t5.setText(obj.getTime());
			t6=(TextView) itemView.findViewById(R.id.tableOrderDateTextView);
			t6.setText(obj.getDate());
			
			t7=(TextView) itemView.findViewById(R.id.tableOrderCustomerIdTextView);
			t7.setText(obj.getCustomerId());
			t8=(TextView) itemView.findViewById(R.id.tableOrderFlagTextView);
			t8.setVisibility(View.INVISIBLE);
			t9=(TextView) itemView.findViewById(R.id.textView2);
			t9.setVisibility(View.INVISIBLE);
			
			tl =(TableLayout) itemView.findViewById(R.id.main_table);
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
			
			
			
			
			
			
			t11=(TextView) itemView.findViewById(R.id.TotalAmount);
			t11.setText(obj.getAmount());
			t12=(TextView) itemView.findViewById(R.id.TypeOfPayment);
			t12.setText(obj.getTypeOfPayment());
			
			Button restaurantTableConfirmButton=(Button) itemView.findViewById(R.id.restaurantTableOrderConfirmButton);
			restaurantTableConfirmButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					
					LinearLayout rl =(LinearLayout)v.getParent();
					TextView tv=(TextView) rl.findViewById(R.id.tableOrderIdTextView);
					TableOrderId=tv.getText().toString();
					
					TextView custId=(TextView) rl.findViewById(R.id.tableOrderCustomerIdTextView);
					CustomerId=custId.getText().toString();
					
					
					final Dialog dialog =new Dialog(context);
					dialog.setContentView(R.layout.tableorderprocessdialog);
					
					dialog.setTitle("Table Order...");
					
//					number=(EditText) dialog.findViewById(R.id.editText1);
//					number.setText("1");
					
					Button confirm=(Button) dialog.findViewById(R.id.confirm);
					Button cancel=(Button) dialog.findViewById(R.id.cancel);
					
					
					dialog.show();
					confirm.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
						    
							Intent i = new Intent(RestaurantTableBookedNotificationFragment.this,RestaurantTableOrderProcess.class);
							i.putExtra("TableOrderId", TableOrderId);
							i.putExtra("CustomerId",CustomerId);
							i.putExtra("FLAG","1");
							//RestaurantConfig.OrderId=OrderId;
							Log.i("in fragment activity",TableOrderId);
							startActivity(i);
							list.clear();
							finish();
							
							
							
						}
					});
					
					
					
					
					
					cancel.setOnClickListener( new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent i = new Intent(RestaurantTableBookedNotificationFragment.this,RestaurantTableOrderProcess.class);
							i.putExtra("TableOrderId", TableOrderId);
							i.putExtra("CustomerId",CustomerId);
							i.putExtra("FLAG","0");
							//RestaurantConfig.OrderId=OrderId;
							Log.i("in fragment activity",TableOrderId);
							startActivity(i);
							list.clear();
							finish();
						}
					});
					
					
									
					
					
				}
			});
			
			
			
			return itemView;
		}
			
	  }
}
