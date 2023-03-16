package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerSpecializedListOfRestaurants extends Activity{
	TextView t1,t2,t3,t4,t5,t6;
	String RestName,RestOwnerName,RestEmail,RestLocation,RestPhoneNumber,AverageRating,RestSpeciality;
	int RestId;
	RatingBar averageRating;
	ListView listView;
	private List<CustomerRestaurantFieldClass> list=new ArrayList<CustomerRestaurantFieldClass>();
	String SelectedSpeciality;
	Button showNearbyRestaurantsButton;
	Button showCustomerOrderHistoryButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_list_of_restaurants);
	
		SelectedSpeciality=getIntent().getStringExtra("SelectedSpeciality");
		
		
	//GetSelected=(Button) findViewById(R.id.cus);
//		showNearbyRestaurantsButton=(Button)findViewById(R.id.showNearbyRestaurantsButton);
//		showNearbyRestaurantsButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants+near+me");
//				Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//				mapIntent.setPackage("com.google.android.apps.maps");
//				startActivity(mapIntent);
//			}
//		});
//		
		showCustomerOrderHistoryButton=(Button) findViewById(R.id.showCustomerOrderHistoryButton);
		showCustomerOrderHistoryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(CustomerSpecializedListOfRestaurants.this,CustomerCompleteOrderHistory.class);
				startActivity(i);
			}
		});
		
		new AddNewPrediction().execute();
	    registerClickCallback();
	
	
	}

	private class AddNewPrediction extends AsyncTask<Void, Void, Void> {
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
            protected Void doInBackground(Void... arg) {
        	
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("SelectedSpeciality", SelectedSpeciality));
            Log.i("here ss value",SelectedSpeciality);
            ServiceHandler serviceClient = new ServiceHandler();
            Log.d("url: ", "> " + CustomerConfig.URL_ITEMS+"DisplaySpecializedRestList.php");
            String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"DisplaySpecializedRestList.php",ServiceHandler.POST,params);
            Log.i("json",json);
                       
            if (json != null) {
                try {
                	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
					json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));	
					
                	Log.i("json modified",json);
					
                	JSONObject jObj=new JSONObject(json);
                	Log.i("object","success");
					JSONArray jArray=jObj.getJSONArray("RestList");
                    Log.i("array",jArray.toString());
                                       
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject c = jArray.getJSONObject(i);
                        
                        RestId = c.getInt("RestId");
                        AverageRating=
                        		c.getString("AverageRating");
                        RestName = c.getString("RestName");
                        Log.i("matchId", RestName);
                        
                         RestEmail  = c.getString("RestEmail");
                        Log.i("teamA", RestEmail);
                        
                         RestOwnerName = c.getString("RestOwnerName");
                        Log.i("teamB", RestOwnerName);
                        
                         RestPhoneNumber = c.getString("RestPhoneNumber");
                        Log.i("teamB", RestPhoneNumber);
                        
                        RestLocation = c.getString("RestLocation");
                        Log.i("teamB", RestLocation);
                        
                        RestSpeciality = c.getString("RestSpeciality");
                        list.add(new CustomerRestaurantFieldClass(RestId,RestName, RestOwnerName, RestEmail, RestLocation, RestPhoneNumber,AverageRating,RestSpeciality));
                        
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
            ArrayAdapter<CustomerRestaurantFieldClass> adapter=new MyListAdapter();
         
             listView=(ListView) findViewById(R.id.customerListOfRestaurantsListView);
            listView.setAdapter(adapter);
            
            //litVIew object is to be used for selecting or getting multiple select check boxes
            
        }
    }
    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rest, menu);
        return true;
    }
    
  private class MyListAdapter extends ArrayAdapter<CustomerRestaurantFieldClass>{
	  int pos;
	public MyListAdapter() {
		super(CustomerSpecializedListOfRestaurants.this, R.layout.customer_restaurant_list_row,list);
		
	}
	  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView=convertView;
		if(itemView==null)
		{
			itemView=getLayoutInflater().inflate(R.layout.customer_restaurant_list_row, parent, false);
		}
		CustomerRestaurantFieldClass obj=list.get(position);
		pos=position;
		t1=(TextView) itemView.findViewById(R.id.customerRestaurantListRestaurantNameTextView);
		t1.setText(obj.getRestName());
		
		t2=(TextView) itemView.findViewById(R.id.customerRestaurantEmailTextView);
		t2.setText(obj.getRestEmail());
		t2.setVisibility(View.INVISIBLE);
		t3=(TextView) itemView.findViewById(R.id.customerRestaurantOwnerTextView);
		t3.setText(obj.getRestOwnerName());
		t3.setVisibility(View.INVISIBLE);
		t4=(TextView) itemView.findViewById(R.id.customerRestaurantPhoneNumberTextView);
		t4.setText(obj.getRestPhoneNumber());
		t4.setVisibility(View.INVISIBLE);
		t5=(TextView) itemView.findViewById(R.id.customerRestaurantLocationTextView);
		t5.setText(obj.getRestLocation());
		
		t6=(TextView) itemView.findViewById(R.id.customerRestaurantIdTextView);
		t6.setText(""+obj.getRestId());
		t6.setVisibility(View.INVISIBLE);
		
		averageRating=(RatingBar) itemView.findViewById(R.id.averageRating);
		averageRating.setRating(Float.parseFloat(obj.getAverageRating()));
		averageRating.setEnabled(false);
		averageRating.setFocusable(false);
		
		ImageView button1=(ImageView) itemView.findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout rl = (LinearLayout)v.getParent().getParent();
	            TextView tv = (TextView)rl.findViewById(R.id.customerRestaurantPhoneNumberTextView);
	            String number = tv.getText().toString().trim();
	            Intent callIntent=new Intent(Intent.ACTION_DIAL);
	            callIntent.setData(Uri.parse("tel:"+number));
	            startActivity(callIntent);
	            //Toast.makeText(CustomerListOfRestaurants.this, text, Toast.LENGTH_SHORT).show(); 
				
			}
		});
		
		
		
		ImageView button3=(ImageView) itemView.findViewById(R.id.button3);
		button3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//int position;	
				LinearLayout rl = (LinearLayout)v.getParent().getParent();
            TextView tv = (TextView)rl.findViewById(R.id.customerRestaurantIdTextView);
            String posString = tv.getText().toString().trim();
				
				CustomerConfig.RestId=posString;
				Intent i= new Intent(CustomerSpecializedListOfRestaurants.this,CustomerShowReviews.class);
				Log.i("id  :", CustomerConfig.RestId);
				//Toast.makeText(CustomerListOfRestaurants.this, ""+CustomerConfig.RestId, Toast.LENGTH_SHORT).show();
				startActivity(i);
			
			}
		});
		
		ImageView button2=(ImageView) itemView.findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//int position;	
				LinearLayout rl = (LinearLayout)v.getParent().getParent();
            TextView t1 = (TextView)rl.findViewById(R.id.customerRestaurantListRestaurantNameTextView);
            TextView t5 = (TextView)rl.findViewById(R.id.customerRestaurantLocationTextView);
            
				
		    	
		        String restaurantLocationString=t1.getText().toString()+", "+t5.getText().toString();
		        Toast.makeText(CustomerSpecializedListOfRestaurants.this,restaurantLocationString,Toast.LENGTH_SHORT).show();
		        restaurantLocationString=restaurantLocationString.replace(' ','+');
		        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+restaurantLocationString);
		        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
		        mapIntent.setPackage("com.google.android.apps.maps");
		        startActivity(mapIntent);
			}
		});
		
		
		
		
		
		return itemView;
		
	}
	
		
  }
  private void registerClickCallback() {
		ListView listViewObj=(ListView) findViewById(R.id.customerListOfRestaurantsListView);
		listViewObj.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CustomerRestaurantFieldClass clickToast=list.get(position);
				//String text="  "+clickToast.getRestName()+"is located at "+clickToast.getRestLocation()+" its contact no. is "+clickToast.getRestPhoneNumber();
				//Toast.makeText(CustomerListOfRestaurants.this, text, Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(CustomerSpecializedListOfRestaurants.this,CustomerHomeActivity.class);
				//i.putExtra("RestId",""+clickToast.getRestId());
				CustomerConfig.RestId=""+clickToast.getRestId();
				CustomerConfig.RestName=""+clickToast.getRestName();
				Toast.makeText(CustomerSpecializedListOfRestaurants.this, ""+clickToast.getRestId(), Toast.LENGTH_SHORT).show();

				startActivity(i);
				
				
			}
			
			
			
			
			
		});
		
	} 
}