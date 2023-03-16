package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;



public class RestaurantTableOrderProcess extends Activity{
	int flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		flag=0;
		
		
		Intent i=getIntent();
		String TableOrderId=i.getStringExtra("TableOrderId");
		String CustomerId=i.getStringExtra("CustomerId");
		String FLAG=i.getStringExtra("FLAG");
		new AddNewPrediction().execute(RestaurantConfig.RestId,TableOrderId,CustomerId,FLAG);
	
	
	
	
	
	
	
	
	
	}
	
	
	private class AddNewPrediction extends AsyncTask<String, Void, Void> {
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
            protected Void doInBackground(String... arg) {
        	String RestId=arg[0];
        	String TableOrderId=arg[1];
        	String CustomerId=arg[2];
        	String FLAG=arg[3];
        	
        	Log.d("url: ", "> " + RestId);
        	
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("RestaurantId", RestId));
	        params.add(new BasicNameValuePair("OrderId",TableOrderId));
	        params.add(new BasicNameValuePair("CustomerId",CustomerId));
	        params.add(new BasicNameValuePair("FLAG",FLAG));
            ServiceHandler serviceClient = new ServiceHandler();
            Log.d("url: ", "> " + RestaurantConfig.URL_ITEMS+"RestaurantTableOrderProcess.php");
            String json = serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"RestaurantTableOrderProcess.php",ServiceHandler.POST,params);
            Log.i("json",json);
                       
            if (json != null) {
                try {
                	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
    				json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
    				 Log.i("modified json ", "> " + json);
                    
    				 JSONObject jsonObj = new JSONObject(json);
    				 
                    int error = jsonObj.getInt("error");
                    // checking for error node in json
                    if (error==0) {
                        // new category created successfully
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
            	Toast.makeText(getApplicationContext(), "TAble order processed succcessfully",Toast.LENGTH_SHORT).show();
            }
            else{
            	Toast.makeText(getApplicationContext(), "problem confirming table order",Toast.LENGTH_SHORT).show();
            }
            Intent intent=new Intent(RestaurantTableOrderProcess.this,RestaurantTableBookedNotificationFragment.class);
            //RestaurantConfig.setTAB="2";
            startActivity(intent);
            finish();
        }
    }
	
	
	
}
