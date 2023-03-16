package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RestaurantAddCategoryToMenu extends Activity {

	Button	addCategory;
	EditText categoryName;
	
	String CategoryName;
	int flag;
	String RestId;
	Intent intent;
	//private String URL_NEW_PREDICTION = "http://192.168.1.7/sampleserver/addMenu.php";
	//private String URL_NEW_PREDICTION = "http://192.168.1.4/sampleserver/addMenu.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_add_category_to_menu);
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Add New Category</font>"));
		flag=0;
		categoryName=(EditText)findViewById(R.id.categoryNameEditText);
		
		
		RestId=RestaurantConfig.RestId;
		
		addCategory= (Button)findViewById(R.id.addNewCategory);
		addCategory.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try{
				
				if(!categoryName.getText().toString().equals("") ){	
					
					CategoryName=categoryName.getText().toString();
					new AddNewPrediction().execute(CategoryName);
				}else{
					Toast.makeText(getApplicationContext(), "please fill all the fields ",Toast.LENGTH_SHORT).show();
				}
														
				//Toast.makeText(RestaurantAddItemToMenu.this, "insertion successful", Toast.LENGTH_SHORT).show();
				//Intent i = new Intent(RestaurantAddItemToMenu.this,RestaurantHomeActivity.class);
				//startActivity(i);
					
				}
				catch(Exception e){
					Toast.makeText(RestaurantAddCategoryToMenu.this, "error inserting data", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//finish();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(RestaurantAddCategoryToMenu.this,RestaurantEditMenuFragment.class);
		startActivity(i);
	}
	
	
	
	
	private class AddNewPrediction extends AsyncTask<String, Void, Void> {
		 
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	  
	    }

	    @Override
	    protected Void doInBackground(String... arg) {
	        // TODO Auto-generated method stub
	    	String categoryName;
	        
	    	categoryName=arg[0];
			
						 
			Log.i("name: ", "> " + categoryName);
			
	        // Preparing post params
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("CategoryName", categoryName));
	        
	        params.add(new BasicNameValuePair("RestId",RestId));

	        ServiceHandler serviceClient = new ServiceHandler();

	        String json = serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"addCategory.php",
	                ServiceHandler.POST, params);

	        Log.d("Create Prediction Request: ", "> " + json);

	        if (json != null) {
	            try {
	            	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
					json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
					 Log.i("modified json ", "> " + json);
					 
					 JSONObject jsonObj = new JSONObject(json);
					 
		                int error = jsonObj.getInt("error");
					 
		                if (error==0) {
		                    // new user created successfully
		                	flag=1;
		                	
		                    
		                } else {
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
	        	Intent i = new Intent(RestaurantAddCategoryToMenu.this,RestaurantEditMenuFragment.class);
				startActivity(i);
				finish();
	        }else{
	        	Toast.makeText(RestaurantAddCategoryToMenu.this, "error inserting data", Toast.LENGTH_SHORT).show();
	        }
	    }
	}
	
	
	
	
}
