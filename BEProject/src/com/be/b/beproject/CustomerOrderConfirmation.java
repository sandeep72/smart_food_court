package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;







import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class CustomerOrderConfirmation extends Activity{
	
	int sum=0;
	int flag;
	Bundle bundle;
	String OrderId;
	int dialogFlag;
	TextView totalPriceTextView;
	Button placeFoodOrderButton;
	TextView customerPayAmountTextView;
	TableLayout t1;

	
	String appendedOrderList;
	String RestId;
	//SQLiteDatabase customerOrderTable;
	//String appendedOrderList;
	//String RestId;
	//EditText CardNo;
	Spinner cardSpinner;
	String typeOfPayment;
	SharedPreferences pref ;
	Editor editor ;
	String[] selectedItemNames,selectedItemPrices,selectedItemQuantity;
	EditText CardNo,CvvNo,PinNo;
	Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_order_confirmation);
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Order Confirmation</font>"));
		sum=0;
		flag=0;
		dialogFlag=0;
		pref = getApplicationContext().getSharedPreferences("CustomerOrderPref", MODE_PRIVATE); 
		editor = pref.edit();
		CardNo=(EditText) findViewById(R.id.customer_cardno);
		CvvNo=(EditText) findViewById(R.id.customer_cvvnumber);
		PinNo=(EditText) findViewById(R.id.customer_pinNo);
		TableLayout tl = (TableLayout) findViewById(R.id.main_table);
		
		if(!pref.getBoolean("first_time_order",true)){
			//editor.putString("CardNo",CardNo.getText().toString());
			CardNo.setText(pref.getString("CardNo",null));
		}
		
		Spinner monthSpinner;
		
		monthSpinner= (Spinner) findViewById(R.id.month_spinner);
		ArrayAdapter<CharSequence> monthAdapter= ArrayAdapter.createFromResource(this, R.array.month_array, android.R.layout.simple_spinner_item);
		monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		monthSpinner.setAdapter(monthAdapter);
		
		
		
		
		cardSpinner= (Spinner) findViewById(R.id.card_spinner);
		ArrayAdapter<CharSequence> cardAdapter= ArrayAdapter.createFromResource(this, R.array.card_array, android.R.layout.simple_spinner_item);
		cardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cardSpinner.setAdapter(cardAdapter);
		
		
		Spinner yearSpinner;
		
		yearSpinner= (Spinner) findViewById(R.id.year_spinner);
		ArrayAdapter<CharSequence> yearAdapter= ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
		yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		yearSpinner.setAdapter(yearAdapter);
		
		customerPayAmountTextView=(TextView) findViewById(R.id.customer_payAmount);
		
		
		bundle=this.getIntent().getExtras();
		 selectedItemNames =bundle.getStringArray("selectedItemNames");
		 selectedItemPrices =bundle.getStringArray("selectedItemPrices");
		 selectedItemQuantity =bundle.getStringArray("selectedItemQuantity");
		RestId=CustomerConfig.RestId;
		Log.i("restid in confirmation", RestId);
		
		
		String[] appendedList=new String[selectedItemNames.length];
		int i;
		int val;
		appendedOrderList=new String();
		Log.i("restid in confirmation", " "+selectedItemNames.length);
		
		for(i=0;i<selectedItemNames.length;i++){
			val=(Integer.parseInt(selectedItemPrices[i])*(Integer.parseInt(selectedItemQuantity[i])));
			appendedList[i]=String.format("%s|%s|%s|%d|",selectedItemNames[i].trim(),selectedItemPrices[i].trim(),selectedItemQuantity[i].trim(),val);
			
			Log.i("order: ",appendedList[i]);
			sum=sum+val;
			if(i==0){
			appendedOrderList=""+appendedList[i];
			}
			else{
				appendedOrderList=appendedOrderList+"\n"+appendedList[i];
			}
				
		}
		
//Table layout code goes here
		TableRow tr_head = new TableRow(this);
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.GRAY);
		tr_head.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		
//adding the column headers
		TextView label_Name = new TextView(this);
		label_Name.setId(20);
		label_Name.setText("ItemNames");
		label_Name.setTextColor(Color.WHITE);
		label_Name.setPadding(0, 5, 5, 5);
        tr_head.addView(label_Name);
        
        TextView label_Price = new TextView(this);
        label_Price.setId(21);// define id that must be unique
        label_Price.setText("Price"); // set the text for the header 
        label_Price.setTextColor(Color.WHITE); // set the color
        label_Price.setPadding(0, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_Price); // add the column to the table row here		
		
        
        TextView label_Quantity = new TextView(this);
        label_Quantity.setId(22);
        label_Quantity.setText("Quantity");
        label_Quantity.setTextColor(Color.WHITE);
        label_Quantity.setPadding(0, 5, 5, 5);
        tr_head.addView(label_Quantity);
        
        TextView label_Total = new TextView(this);
        label_Total.setId(23);
        label_Total.setText("Total");
        label_Total.setTextColor(Color.WHITE);
        label_Total.setPadding(0, 5, 5, 5);
        tr_head.addView(label_Total);
        
        
        
        
        tl.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		
        Integer count=0;
        while (count<selectedItemNames.length) {
//       String Names = selectedItemNames[count];// get the first variable
//       String Price=selectedItemPrices[count];// get the second variable
       
       // Create the table row
       TableRow tr = new TableRow(this);
        tr.setBackgroundColor(Color.WHITE);
       tr.setId(100+count);
       tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

       //Create two columns to add as table data
        // Create a TextView to add date
       TextView Name = new TextView(this);
       Name.setId(200+count); 
       Name.setText(selectedItemNames[count]);
       Name.setPadding(2, 0, 5, 0);
       Name.setTextColor(Color.BLACK);
       tr.addView(Name);
       TextView Price = new TextView(this);
       Price.setId(200+count);
       Price.setText(selectedItemPrices[count]);
       Price.setTextColor(Color.BLACK);
       tr.addView(Price);

       
       TextView Quantity = new TextView(this);
       Quantity.setId(300+count);
       Quantity.setText(selectedItemQuantity[count]);
       Quantity.setTextColor(Color.BLACK);
       tr.addView(Quantity);
       
       TextView Total = new TextView(this);
       Total.setId(400+count);
       Total.setText(""+(Integer.parseInt(selectedItemPrices[count])*Integer.parseInt(selectedItemQuantity[count])));
       Total.setTextColor(Color.BLACK);
       tr.addView(Total);
       
       // finally add this to the table row
       tl.addView(tr, new TableLayout.LayoutParams(
       	                    LayoutParams.FILL_PARENT,
       	                    LayoutParams.WRAP_CONTENT));
       		       count++;
       		    }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//	ListView customerSelectedOrderList=(ListView) findViewById(R.id.customerSelectedOrderListView);
//		ArrayAdapter<String> adapter=new ArrayAdapter<String>(CustomerOrderConfirmation.this, android.R.layout.simple_list_item_1,appendedList);
//    	customerSelectedOrderList.setAdapter(adapter);
    	
    	totalPriceTextView=(TextView)findViewById(R.id.totalPriceTextView);
    	totalPriceTextView.setText(""+sum);
    	
    	customerPayAmountTextView.setText(""+sum);
    	
    	placeFoodOrderButton= (Button) findViewById(R.id.placeFoodOrderButton);
    	placeFoodOrderButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try{
					
					Log.i("appended list ",appendedOrderList);	
					typeOfPayment=cardSpinner.getSelectedItem().toString();
					if(CardNo.getText().toString().length()==16)
					{
						Log.i("appended list ","card veri");
					    if(CvvNo.getText().toString().length()==3)
					    {
					    	Log.i("appended list ","cvv verify");
					    	if(PinNo.getText().toString().length()==4)
					    	{	 String tempCard=pref.getString("CardNo",null);
					    		if(!(CardNo.getText().toString().equals(tempCard))){
					    		final Dialog dialog =new Dialog(context);
								dialog.setContentView(R.layout.save_card_detail_dialog);
								
								dialog.setTitle("Save Details...");
								
//								number=(EditText) dialog.findViewById(R.id.editText1);
//								number.setText("1");
								
								Button ok=(Button) dialog.findViewById(R.id.button1);
								Button cancel=(Button) dialog.findViewById(R.id.button2);
//								Button ok=(Button) dialog.findViewById(R.id.button3);
								
								dialog.show();
								ok.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										
										editor.putString("CardNo",CardNo.getText().toString());
										editor.putBoolean("first_time_order", false);
										editor.commit();
										
										new AddNewPrediction().execute(appendedOrderList,""+sum,typeOfPayment);
										dialog.dismiss();
									}
								});
					    		
								cancel.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										new AddNewPrediction().execute(appendedOrderList,""+sum,typeOfPayment);
										dialog.dismiss();
									}
								});
					    	}else{
					    		new AddNewPrediction().execute(appendedOrderList,""+sum,typeOfPayment);
					    	}
					    		
					    	}else{
					    		Toast.makeText(CustomerOrderConfirmation.this, "enter 4 digit pin ", Toast.LENGTH_SHORT).show();
					    	}
					    }else{
					    	Toast.makeText(CustomerOrderConfirmation.this, "enter a valid 3 digit cvv number", Toast.LENGTH_SHORT).show();
					    }
					}else{
						Toast.makeText(CustomerOrderConfirmation.this, "enter a valid 16 digit card number", Toast.LENGTH_SHORT).show();
					}
					}
				
					catch(Exception e){
						Toast.makeText(CustomerOrderConfirmation.this, "error inserting data", Toast.LENGTH_SHORT).show();
					}
					
				}
			});
		}
		
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			finish();
		}
		
		@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			//Intent i = new Intent(RestaurantAddItemToMenu.this,RestaurantHomeActivity.class);
			//startActivity(i);
		}
		
		
		
		
		private class AddNewPrediction extends AsyncTask<String, Void, Void> {
			 
		    @Override
		    protected void onPreExecute() {
		        super.onPreExecute();
		  
		    }

		    @Override
		    protected Void doInBackground(String... arg) {
		        // TODO Auto-generated method stub
		    	String itemName,itemPrice,CustomerNo,CustomerName,typeOfPayment;
		        
		    	itemName=arg[0];
				itemPrice=arg[1];
				typeOfPayment=arg[2];
				CustomerNo=CustomerConfig.CustPhoneNo;
				CustomerName=CustomerConfig.CustomerName;
						 
				Log.i("name: ", "> " + itemName+"    "+itemPrice+"   "+CustomerName+"   "+CustomerNo+"    "+typeOfPayment);
				//Log.i(itemName,itemPrice);
		        // Preparing post params
		        List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("ItemNames", itemName));
		        params.add(new BasicNameValuePair("Amount", itemPrice));
		        params.add(new BasicNameValuePair("CustomerNo",CustomerNo));
		        params.add(new BasicNameValuePair("RestId",RestId));
		        params.add(new BasicNameValuePair("CustomerName",CustomerName));
		        params.add(new BasicNameValuePair("CustomerId",CustomerConfig.CustId));
		        params.add(new BasicNameValuePair("RestName",CustomerConfig.RestName));
		        params.add(new BasicNameValuePair("TypeOfPayment", typeOfPayment));
		        ServiceHandler serviceClient = new ServiceHandler();

		        String json =null;
		        if(CustomerTableBookingFragment.TableWithFoodFlag==1){
		        	params.add(new BasicNameValuePair("TableOrderId", CustomerTableBookingFragment.TableOrderId));
		        json= serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"PlaceOrderWithTableOrder.php",
		                ServiceHandler.POST, params);
		        }else
		        {
		        	json= serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"PlaceOrder.php",
			                ServiceHandler.POST, params);
		        	
		        }
		        Log.d("Create Prediction Request: ", "> " + json);

		        if (json != null) {
		            try {
		            	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
						json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
						 Log.i("modified json ", "> " + json);
						 
						 JSONObject jsonObj = new JSONObject(json);
						 
			                int error = jsonObj.getInt("error");
						 
			                if (error==0) {
			                    // new category created successfully
			                	flag=1;
			                	if(CustomerTableBookingFragment.TableWithFoodFlag==0){
			                	OrderId=jsonObj.getString("Id");
			                	Log.i("Rest ID ", "> " + OrderId);
			                	}
			                    
			                }else if(error==2){ 
			                	flag=2;
			                }	else {
			                
			                    Log.e("LOGIN Error: ",
			                            "> " + jsonObj.getString("message"));
			                }

		            } catch (JSONException e) {
		                e.printStackTrace();
		            }

		        } else {
		            Log.e("JSON Data", "JSON data error!");
		        }
		        return null;
		    }

		    @Override
		    protected void onPostExecute(Void result) {
		        super.onPostExecute(result);
		        if(flag==1){
		        	Bundle b=new Bundle();
					b.putStringArray("selectedItemNames", selectedItemNames);
					b.putStringArray("selectedItemPrices", selectedItemPrices);
					b.putStringArray("selectedItemQuantity", selectedItemQuantity);
					if(CustomerTableBookingFragment.TableWithFoodFlag==0){
					b.putString("OrderId",OrderId);
					}
					Intent i = new Intent(CustomerOrderConfirmation.this,CustomerBill.class);
			        Toast.makeText(CustomerOrderConfirmation.this, "ORDER ID!"+OrderId, Toast.LENGTH_SHORT).show();
			        CvvNo.setText("");
			        PinNo.setText("");
			        i.putExtras(b);
			        
			        startActivity(i);
			        }else if(flag==2){
			        	Toast.makeText(CustomerOrderConfirmation.this, "restaurant not accepting orders!", Toast.LENGTH_SHORT).show();
			        }
			        else{
			        	Toast.makeText(CustomerOrderConfirmation.this, "unable to palce order!", Toast.LENGTH_SHORT).show();
			        }
		    }
		}
		
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			
		}
		}
