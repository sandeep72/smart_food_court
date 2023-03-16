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
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RestaurantDeleteOrderProcess extends Activity{
	int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		flag=0;
		Intent i=getIntent();
		String OrderId=i.getStringExtra("OrderId");
		new AddNewPrediction().execute(RestaurantConfig.RestId,OrderId);
	}
	
	
	private class AddNewPrediction extends AsyncTask<String, Void, Void> {
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
            protected Void doInBackground(String... arg) {
        	String RestId=arg[0];
        	String OrderId=arg[1];
        	Log.d("url: ", "> " + RestId);
        	
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("RestaurantId", RestId));
	        params.add(new BasicNameValuePair("OrderId",OrderId));
        	
            ServiceHandler serviceClient = new ServiceHandler();
            Log.d("url: ", "> " + RestaurantConfig.URL_ITEMS+"DeleteOrders.php");
            String json = serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"DeleteOrders.php",ServiceHandler.POST,params);
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
            	Toast.makeText(getApplicationContext(), "order deleted succcessfully",Toast.LENGTH_SHORT).show();
            }
            else{
            	Toast.makeText(getApplicationContext(), "problem deleting order",Toast.LENGTH_SHORT).show();
            }
            Intent intent=new Intent(RestaurantDeleteOrderProcess.this,RestaurantNotificationFragment.class);
            //RestaurantConfig.setTAB="2";
            startActivity(intent);
            finish();
            
        }
    }
	
	
	
	
	
	
}
