package com.be.b.beproject;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CustomerBill extends Activity{
Bundle bundle;
String appendedOrderList;
int sum;
Button okButton;
String orderId;
TextView orderIdTextView,totalPriceTextView;
TableLayout t1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customerbill);
		sum=0;
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Receipt ..</font>"));
		orderIdTextView=(TextView) findViewById(R.id.orderIdTextView);
		totalPriceTextView=(TextView) findViewById(R.id.totalPriceTextView);
		TableLayout tl = (TableLayout) findViewById(R.id.main_table);
		
		bundle=this.getIntent().getExtras();
		String[] selectedItemNames =bundle.getStringArray("selectedItemNames");
		String[] selectedItemPrices =bundle.getStringArray("selectedItemPrices");
		String[] selectedItemQuantity =bundle.getStringArray("selectedItemQuantity");
		if(CustomerTableBookingFragment.TableWithFoodFlag==0){
		orderId=bundle.getString("OrderId");
		}else{
			orderId=CustomerTableBookingFragment.TableOrderId;
			
		}
			
		
		String[] appendedList=new String[selectedItemNames.length];
		int i;
		int val;
		appendedOrderList=new String();
		Log.i("restid in confirmation", " "+selectedItemNames.length);
		
		for(i=0;i<selectedItemNames.length;i++){
			val=(Integer.parseInt(selectedItemPrices[i])*(Integer.parseInt(selectedItemQuantity[i])));
			appendedList[i]=selectedItemNames[i]+"                   "+selectedItemPrices[i]+"          "+selectedItemQuantity[i]+"          "+val;
			
			sum=sum+val;
			if(i==0){
			appendedOrderList=" "+appendedList[i];
			}
			else{
				appendedOrderList=appendedOrderList+"\n"+appendedList[i];
			}
				
		}
		
		totalPriceTextView.setText("Rs    :"+sum);
		orderIdTextView.setText(orderId);
		
		
		
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
//		       String Names = selectedItemNames[count];// get the first variable
//		       String Price=selectedItemPrices[count];// get the second variable
		       
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		ListView customerSelectedOrderList=(ListView) findViewById(R.id.customerSelectedOrderListView);
//		ArrayAdapter<String> adapter=new ArrayAdapter<String>(CustomerBill.this, android.R.layout.simple_list_item_1,appendedList);
//    	customerSelectedOrderList.setAdapter(adapter);
    	
    	okButton=(Button) findViewById(R.id.okButton);
    	okButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent i=new Intent(CustomerBill.this,CustomerHomeActivity.class);
//				startActivity(i);
				finish();
			
			}
		});
    	
    	
    	
    	
		
	}
	
}
