package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;






import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerOrderPlacementFragment extends Activity{
	
	//SQLiteDatabase restaurantTable;
	//Cursor itemHandlerCursor;
	View v;
	String[] allItemNames,selectedItemNames,allItemPrices,selectedItemPrices;
	String concatenatedMenuString;
	ListView customerRestaurantMenuListView;
	String RestId;
	TextView t1,t2;
	private List<menufields> list=new ArrayList<menufields>();
	
	ArrayList<String> ItemName,ItemPrice,ItemQuantity; 
	
	menufields clickToast;
	final Context context = this;
	EditText number;
	Button PlaceOrder;
	Button proceedToOrderPaymentButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_place_order);
		
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Menu</font>"));
		ItemName=new ArrayList<String>();
		ItemPrice=new ArrayList<String>();
		ItemQuantity=new ArrayList<String>();
		RestId= CustomerConfig.RestId;
		
	    new AddNewPrediction().execute(RestId);
	    registerClick();
	    
	    
	    proceedToOrderPaymentButton=(Button) findViewById(R.id.proceedToOrderPaymentButton);
		proceedToOrderPaymentButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(ItemName.size()!=0){
					
					String Name[]=ItemName.toArray(new String[ItemName.size()]);
					String Price[]=ItemPrice.toArray(new String[ItemPrice.size()]);
					String Quantity[]=ItemQuantity.toArray(new String[ItemQuantity.size()]);
					for(int i=0;i<Name.length;i++){
						Log.i("name",Name[i]+" "+Price[i]+"  "+Quantity[i]);
						}
					Bundle b=new Bundle();
					b.putStringArray("selectedItemNames", Name);
					b.putStringArray("selectedItemPrices", Price);
					b.putStringArray("selectedItemQuantity", Quantity);
					//b.putString("RestId", RestId);
					Intent intent = new Intent(CustomerOrderPlacementFragment.this,CustomerOrderConfirmation.class);
					intent.putExtras(b);
					startActivity(intent);
					
					
					}
					else{
						Toast.makeText(CustomerOrderPlacementFragment.this,"no items selected",Toast.LENGTH_SHORT).show();
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
        	Log.d("url: ", "> " + RestId);
        	
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("RestaurantId", RestId));
        	
        	
            ServiceHandler serviceClient = new ServiceHandler();
            Log.d("url: ", "> " + CustomerConfig.URL_ITEMS+"DisplayRestaurantMenuItems.php");
            String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"DisplayRestaurantMenuItems.php",ServiceHandler.POST,params);
            Log.i("json",json);
                       
            if (json != null) {
                try {
                	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
					json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));	
					
                	Log.i("json modified",json);
					
                	JSONObject jObj=new JSONObject(json);
                	Log.i("object","success");
					JSONArray jArray=jObj.getJSONArray("MenuList");
                   Log.i("array",jArray.toString());
                
               JSONObject c ;
               for (int i = 0; i < jArray.length(); i++) {
                 c= jArray.getJSONObject(i);
              
              String  ItemNames = c.getString("ItemName");
              Log.i("name ",ItemNames);
              String Amount  = c.getString("ItemPrice");
              list.add(new menufields(ItemNames,Amount));
             
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
        ArrayAdapter<menufields> adapter=new MyListAdapter();
     
        ListView listView=(ListView) findViewById(R.id.customerRestaurantMenuListView);
        listView.setAdapter(adapter);
        //Toast.makeText(getActivity(), ItemName, Toast.LENGTH_SHORT).show();
        
        //litVIew object is to be used for selecting or getting multiple select check boxes
        
    }
}

private class MyListAdapter extends ArrayAdapter<menufields>{

	public MyListAdapter() {
		super(CustomerOrderPlacementFragment.this, R.layout.row_viewmenu,list);
		
	}
	  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView=convertView;
		if(itemView==null)
		{
			itemView=getLayoutInflater().inflate(R.layout.row_viewmenu, parent, false);
			
		}
		menufields obj=list.get(position);
		
		t1=(TextView) itemView.findViewById(R.id.customerMenuItemNameTextView);
		t1.setText(obj.getName());
		Log.i("name ",obj.getName());
		t2=(TextView) itemView.findViewById(R.id.customerMenuItemPriceTextView);
		t2.setText(obj.getPrice());
		
		return itemView;
		
	}
	
		
  }


private void registerClick() {
	Log.i("in the orderplacefragment ","list item click");
	ListView listViewObj=(ListView) findViewById(R.id.customerRestaurantMenuListView);
	listViewObj.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			
			
			Log.i("in the orderplacefragment ","list item click");
			clickToast=list.get(position);
			
				
			final Dialog dialog =new Dialog(context);
			dialog.setContentView(R.layout.quantity);
			
			dialog.setTitle("Select Quantity...");
			
			number=(EditText) dialog.findViewById(R.id.editText1);
			number.setText("1");
			
			Button add=(Button) dialog.findViewById(R.id.button1);
			Button minus=(Button) dialog.findViewById(R.id.button2);
			Button ok=(Button) dialog.findViewById(R.id.button3);
			
			dialog.show();
			ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				    Log.i("value",number.getText().toString());
				    int val=Integer.parseInt(number.getText().toString());
				    if(val>0 && !(number.getText().toString().trim().equals(""))){
				    ItemName.add(clickToast.getName());
				    Log.i("add",clickToast.getName());
				    ItemPrice.add(clickToast.getPrice());
				    ItemQuantity.add(number.getText().toString());
					dialog.dismiss();
				}
				}
			});
			
			
			
			
			
			add.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String qtyStr=number.getText().toString();
					int cnt=Integer.parseInt(qtyStr.trim());
					int sum=cnt+1;
					if(sum<=9)
					{
						number.setText("0"+sum);
					}
					else
					{
						number.setText(""+sum);
					}
				}
			});
			
			
			minus.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					String qtyStr=number.getText().toString();
					int cnt=Integer.parseInt(qtyStr.trim());
					if(cnt>01)
					{
						int sum=cnt-1;
						if(sum<=9)
						{
							number.setText("0"+sum);
						}
						else
						{
							number.setText(""+sum);
						}
					}
				}
			});
}
});
	
	} 

}