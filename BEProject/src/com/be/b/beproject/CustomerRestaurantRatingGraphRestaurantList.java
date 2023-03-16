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
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CustomerRestaurantRatingGraphRestaurantList extends Activity implements TextWatcher{
	List<String> customerRestaurantNameList;
	List<String> customerRestaurantIdList;
	List<String> selectedRestaurantsRestIdList;
	String[] autocompleteRestaurantNameString;
	int textField1CheckFlag=0;
	int textField2CheckFlag=0;
	int textField3CheckFlag=0;
	int textField4CheckFlag=0;
	static ArrayList<CustomerRestaurantReviewCompareFieldClass> list=new ArrayList<CustomerRestaurantReviewCompareFieldClass>();
	private String URL_ITEMS= CustomerConfig.URL_ITEMS+"getAvgReviewData.php";

	AutoCompleteTextView customerRestaurant1AutoCompleteTextView,customerRestaurant2AutoCompleteTextView,customerRestaurant3AutoCompleteTextView,customerRestaurant4AutoCompleteTextView;
	Button customerRestaurantRatingCompareRatingButton;
	int NoOfSelectedRestaurants;
	int LoopCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_restaurant_rating_graph_restaurant_list);
		list.clear();
		customerRestaurantNameList=new ArrayList<String>();
		customerRestaurantIdList=new ArrayList<String>();
		selectedRestaurantsRestIdList=new ArrayList<String>();
		
		NoOfSelectedRestaurants=0;
		
		customerRestaurantRatingCompareRatingButton=(Button)findViewById(R.id.customerRestaurantRatingCompareRatingButton);
		
		customerRestaurant1AutoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.customerRestaurant1AutoCompleteTextView);
		customerRestaurant2AutoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.customerRestaurant2AutoCompleteTextView);
		customerRestaurant3AutoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.customerRestaurant3AutoCompleteTextView);
		customerRestaurant4AutoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.customerRestaurant4AutoCompleteTextView);
		
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
            Log.d("url: ", "> " + CustomerConfig.URL_ITEMS+"DisplayRestList.php");
            String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"DisplayRestList.php",ServiceHandler.GET);
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
                        
                        customerRestaurantIdList.add(""+c.getInt("RestId"));
                        customerRestaurantNameList.add(c.getString("RestName")+", "+c.getString("RestLocation"));
                        //Log.i("rest name and location",c.getString("RestName")+", "+c.getString("RestLocation"));
                        //Log.i("list content",customerRestaurantNameList.get(i));
                        
                        //list.add(new CustomerRestaurantFieldClass(RestId,RestName, RestOwnerName, RestEmail, RestLocation, RestPhoneNumber,AverageRating,RestSpeciality));
                        
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
            autocompleteRestaurantNameString= customerRestaurantNameList.toArray(new String[customerRestaurantNameList.size()]);
            customerRestaurant1AutoCompleteTextView.addTextChangedListener(CustomerRestaurantRatingGraphRestaurantList.this);
            customerRestaurant2AutoCompleteTextView.addTextChangedListener(CustomerRestaurantRatingGraphRestaurantList.this);
            customerRestaurant3AutoCompleteTextView.addTextChangedListener(CustomerRestaurantRatingGraphRestaurantList.this);
            customerRestaurant4AutoCompleteTextView.addTextChangedListener(CustomerRestaurantRatingGraphRestaurantList.this);
            
            customerRestaurant1AutoCompleteTextView.setAdapter(new ArrayAdapter<String>(CustomerRestaurantRatingGraphRestaurantList.this, android.R.layout.simple_list_item_1,autocompleteRestaurantNameString));
            customerRestaurant2AutoCompleteTextView.setAdapter(new ArrayAdapter<String>(CustomerRestaurantRatingGraphRestaurantList.this, android.R.layout.simple_list_item_1,autocompleteRestaurantNameString));
            customerRestaurant3AutoCompleteTextView.setAdapter(new ArrayAdapter<String>(CustomerRestaurantRatingGraphRestaurantList.this, android.R.layout.simple_list_item_1,autocompleteRestaurantNameString));
            customerRestaurant4AutoCompleteTextView.setAdapter(new ArrayAdapter<String>(CustomerRestaurantRatingGraphRestaurantList.this, android.R.layout.simple_list_item_1,autocompleteRestaurantNameString));
            
            customerRestaurantRatingCompareRatingButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					if(!customerRestaurant1AutoCompleteTextView.getText().toString().equals("") && !customerRestaurant2AutoCompleteTextView.getText().toString().equals("")){
						
						for(int i=0;i<autocompleteRestaurantNameString.length;i++){
							if(customerRestaurant1AutoCompleteTextView.getText().toString().equals(autocompleteRestaurantNameString[i])){
								textField1CheckFlag=1;
								
								selectedRestaurantsRestIdList.add(new String(customerRestaurantIdList.get(i)));
								NoOfSelectedRestaurants++;
								break;
							}
							
						}
						for(int i=0;i<autocompleteRestaurantNameString.length;i++){
							if(customerRestaurant2AutoCompleteTextView.getText().toString().equals(autocompleteRestaurantNameString[i])){
								textField2CheckFlag=1;
								selectedRestaurantsRestIdList.add(new String(customerRestaurantIdList.get(i)));
								NoOfSelectedRestaurants++;
								break;
							}
						}
						if(textField1CheckFlag==1 && textField2CheckFlag==1){
							
							
							if(!customerRestaurant3AutoCompleteTextView.getText().toString().equals("")){
								for(int i=0;i<autocompleteRestaurantNameString.length;i++){
									if(customerRestaurant3AutoCompleteTextView.getText().toString().equals(autocompleteRestaurantNameString[i])){
										textField3CheckFlag=1;
										
										selectedRestaurantsRestIdList.add(new String(customerRestaurantIdList.get(i)));
										NoOfSelectedRestaurants++;
										break;
									}
									
								}
								if(textField3CheckFlag==1){
									if(!customerRestaurant4AutoCompleteTextView.getText().toString().equals("")){
										for(int i=0;i<autocompleteRestaurantNameString.length;i++){
											if(customerRestaurant4AutoCompleteTextView.getText().toString().equals(autocompleteRestaurantNameString[i])){
												textField4CheckFlag=1;
												
												selectedRestaurantsRestIdList.add(new String(customerRestaurantIdList.get(i)));
												NoOfSelectedRestaurants++;
												break;
											}
											
										}
										if(textField4CheckFlag==1){
											//all 4
											//for(LoopCount=0;LoopCount<selectedRestaurantsRestIdList.size();LoopCount++){
												new AddReviewFieldsOfRestaurantToList().execute(selectedRestaurantsRestIdList.get(0));
											//}
											
										}
										else{
											Toast.makeText(CustomerRestaurantRatingGraphRestaurantList.this, "Please enter correct name of 4th restaurant!", Toast.LENGTH_SHORT).show();
										}
									}
									else{
										//only 3
										//for(LoopCount=0;LoopCount<selectedRestaurantsRestIdList.size();LoopCount++){
											new AddReviewFieldsOfRestaurantToList().execute(selectedRestaurantsRestIdList.get(0));
										//}
										
									}
								}
								else{
									Toast.makeText(CustomerRestaurantRatingGraphRestaurantList.this, "Please enter correct name of 3rd restaurant!", Toast.LENGTH_SHORT).show();
								}
							}
							else{
								//only two
								//for(LoopCount=0;LoopCount<selectedRestaurantsRestIdList.size();LoopCount++){
									//Log.i("loopcoount")
									new AddReviewFieldsOfRestaurantToList().execute(selectedRestaurantsRestIdList.get(0));
								//}
							
							}
							
						}
						else{
							Toast.makeText(CustomerRestaurantRatingGraphRestaurantList.this, "Please enter correct names of the first two restaurants!", Toast.LENGTH_SHORT).show();
						}
					}
					else{
						Toast.makeText(CustomerRestaurantRatingGraphRestaurantList.this, "Please enter the names of the first two restaurants at least!", Toast.LENGTH_SHORT).show();
					}
				}
			});
        }
    }

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
	
	
	private class AddReviewFieldsOfRestaurantToList extends AsyncTask<String, Void, Void> {
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
            protected Void doInBackground(String... arg) {
        	
        	//String RestId=arg[0];
        	String json[]=new String[selectedRestaurantsRestIdList.size()];
        	
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
        	
        	
            ServiceHandler serviceClient = new ServiceHandler();
            for(int i=0;i<selectedRestaurantsRestIdList.size();i++){
            	params.clear();
            	params.add(new BasicNameValuePair("RestId",selectedRestaurantsRestIdList.get(i)));
            //Log.d("url: ", "> " + URL_ITEMS);
            json[i] = serviceClient.makeServiceCall(URL_ITEMS,ServiceHandler.POST,params);
            
            }
            
            //Log.i("json",json[0]);
            //Log.i("json",json[1]);
                
            for(int i=0;i<selectedRestaurantsRestIdList.size();i++){
            if (json[i] != null) {
                try {
                	json[i]=(json[i].substring(json[i].indexOf("</table></font>"), json[i].lastIndexOf("}") + 1));
					json[i]=(json[i].substring(json[i].indexOf("{"), json[i].lastIndexOf("}") + 1));	
					
                	Log.i("json modified"+i,json[i]);
					
                	JSONObject jObj=new JSONObject(json[i]);
                	
                                       
                    
                       
                        
                        String AmbienceRating=jObj.getString("AmbienceRating");
                        String ServiceRating=jObj.getString("ServiceRating");
                        String FoodRating=jObj.getString("FoodRating");
                        String ValueForMoneyRating=jObj.getString("ValueForMoneyRating");
                        String RestName=jObj.getString("RestName");
                        
                        list.add(new CustomerRestaurantReviewCompareFieldClass(RestName,selectedRestaurantsRestIdList.get(i),AmbienceRating,FoodRating, ValueForMoneyRating,ServiceRating));
                        
                    }
               
                catch (JSONException e) {
                    Log.d("catch", "in the catch");
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
          
            }
            return null;
        }
	
        @Override
            protected void onPostExecute(Void result) {
            super.onPostExecute(result);
   	   
        	   Intent i = new Intent(CustomerRestaurantRatingGraphRestaurantList.this,CustomerRestaurantRatingBarGraph.class);
//        	   i.putExtra("list",list);
        	   //for(int j=0;j<list.size();j++){
        	   
        	   
        	   
        	   
        	   
        	   //}
        	   startActivity(i);
        	   finish();
        	   
        	   
        	   
        	   
           
            
        }
    }
}
