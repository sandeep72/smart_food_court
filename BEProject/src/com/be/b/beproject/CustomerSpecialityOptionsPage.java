package com.be.b.beproject;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CustomerSpecialityOptionsPage extends Activity{
	
	
	ListView SpecialityElementsListView;
	Button goToCompleteListOfRestaurantsButton;
	Button showNearbyRestaurantsButton;
	Button customerAfterLoginReviewComparisonButton;
	String SpecialtiyName;
	private List<String> list=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_speciality_buttons);
		
		customerAfterLoginReviewComparisonButton=(Button)findViewById(R.id.customerAfterLoginReviewComparisonButton);
		customerAfterLoginReviewComparisonButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(CustomerSpecialityOptionsPage.this,CustomerRestaurantRatingGraphRestaurantList.class);
				startActivity(i);
			}
		});
		
		showNearbyRestaurantsButton=(Button)findViewById(R.id.showNearbyRestaurantsButton);
		showNearbyRestaurantsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants+near+me");
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
				mapIntent.setPackage("com.google.android.apps.maps");
				startActivity(mapIntent);
			}
		});
		
		goToCompleteListOfRestaurantsButton=(Button)findViewById(R.id.goToCompleteListOfRestaurantsButton);
		goToCompleteListOfRestaurantsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(CustomerSpecialityOptionsPage.this,CustomerListOfRestaurants.class);
				startActivity(i);
			}
		});
		SpecialityElementsListView=(ListView) findViewById(R.id.specialityButtonsListView);
		   SpecialityElementsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

   			@Override
   			public void onItemClick(AdapterView<?> parent, View view,
   					int position, long id) {
   				// TODO Auto-generated method stub
   				Intent i = new Intent(CustomerSpecialityOptionsPage.this,CustomerSpecializedListOfRestaurants.class);
   				i.putExtra("SelectedSpeciality", SpecialityElementsListView.getItemAtPosition(position).toString());
   				Log.i("value of selected speciality", SpecialityElementsListView.getItemAtPosition(position).toString());
   				startActivity(i);
   			}
   			
   		});
   		
		
		new AddNewPrediction().execute();
	}
	
	private class AddNewPrediction extends AsyncTask<Void, Void, Void> {
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
            protected Void doInBackground(Void... arg) {
            ServiceHandler serviceClient = new ServiceHandler();
            Log.d("url: ", "> " + CustomerConfig.URL_ITEMS+"getSpecialityList.php");
            String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"getSpecialityList.php",ServiceHandler.GET);
            Log.i("json",json);
                       
            if (json != null) {
                try {
                	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
					json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));	
					
                	Log.i("json modified",json);
					
                	JSONObject jObj=new JSONObject(json);
                	Log.i("object","success");
					JSONArray jArray=jObj.getJSONArray("SpecialityList");
                    Log.i("array",jArray.toString());
                                       
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject c = jArray.getJSONObject(i);
                        /*
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
                        */
                        SpecialtiyName=c.getString("SpecialityName");
                        list.add(SpecialtiyName);
                        
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
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list.toArray(new String[list.size()]));
         
            
            SpecialityElementsListView.setAdapter(adapter);
            
            //litVIew object is to be used for selecting or getting multiple select check boxes
            
         
            
        }
    }
}
