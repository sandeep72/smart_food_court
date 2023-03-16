package com.be.b.beproject;


import java.util.ArrayList;
import java.util.HashMap;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CustomerFoodOrderCategoryProcessClass extends Activity {
ArrayList<String> CategoryId,Id,ItemCategoryId,ItemName,Price,Description,ImageURL;
static	HashMap<String,List<String>> listDataItemName = new HashMap<String, List<String>>();
static	HashMap<String,List<String>> listDataPrice = new HashMap<String, List<String>>();
static	HashMap<String,List<String>> listDataDescription = new HashMap<String, List<String>>();
static	HashMap<String,List<String>> listImage = new HashMap<String, List<String>>();
	static ArrayList<String> CategoryName;
	Button button; 
	int categoryLength;
	
//	String URL_ITEMS="http://192.168.43.238/sampleserver/getcategory.php"; 
//	String URL_ITEMS1="http://192.168.43.238/sampleserver/getmenuitems.php";
	//ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customerfoodordercategoryprocessclass);
		//list=(ListView) findViewById(R.id.categorylist);
	/*	button=(Button) findViewById(R.id.button1);
		
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent i =new Intent(CustomerFoodOrderCategoryProcessClass.this,TabHomePAge.class);
			     startActivity(i);				
			}
		});
		*/
		new AddNewPrediction().execute();
		
	}

	
	
	
	private class AddNewPrediction extends AsyncTask<Void, Void, Void> {
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
            protected Void doInBackground(Void... arg) {
        	
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("RestId", CustomerConfig.RestId));
            ServiceHandler serviceClient = new ServiceHandler();
        //    Log.d("url: ", "> "+ URL_ITEMS);
            
            String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"getcategory.php",ServiceHandler.POST,params);
            String json1 = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"getmenuitems.php",ServiceHandler.POST,params);
            
            
            
            
            
            Log.i("json",json);
                       
            if (json != null) {
                try {
                	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
					json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));	
					
					json1=(json1.substring(json1.lastIndexOf("</table></font>"), json1.lastIndexOf("}") + 1));
					json1=(json1.substring(json1.indexOf("{"), json1.lastIndexOf("}") + 1));	
					
					
                	Log.i("json modified",json);
                	Log.i("json modified",json1);
                	
                	JSONObject jObj=new JSONObject(json);
                	JSONArray jArray=jObj.getJSONArray("categoryList");
                   
                
               JSONObject c ;
               CategoryId=new ArrayList<String>();
               CategoryName=new ArrayList<String>();
               
               categoryLength=jArray.length();
               
               
               for (int i = 0; i < jArray.length(); i++) {
                 c= jArray.getJSONObject(i);
                 CategoryId.add(c.getString("CategoryId"));
                 Log.i("category id",CategoryId.get(i));
                 CategoryName.add(c.getString("CategoryName"));
                 Log.i("category name",CategoryName.get(i));
               }
               
               jObj=new JSONObject(json1);
               jArray=jObj.getJSONArray("menulist");
               
               ItemCategoryId=new ArrayList<String>();
               Id=new ArrayList<String>();
               Description=new ArrayList<String>();
               Price=new ArrayList<String>();
               ItemName=new ArrayList<String>();
               ImageURL=new ArrayList<String>();
               
               for (int i = 0; i < jArray.length(); i++) {
                   c= jArray.getJSONObject(i);
                   Id.add(c.getString("Id"));
                   Log.i("Id :",Id.get(i));
                   ItemCategoryId.add(c.getString("CategoryId"));
                   Log.i("ItemCategoryId",ItemCategoryId.get(i));
                   Description.add(c.getString("ItemDescription"));
                   Log.i("Description",Description.get(i));
                   ItemName.add(c.getString("ItemName"));
                   Log.i("ItemName",ItemName.get(i));
                   Price.add(c.getString("ItemPrice"));
                   Log.i("Price :",Price.get(i));
                   Log.i("Price :",Price.get(i));
                   ImageURL.add(c.getString("ImageURL"));
                   Log.i("image path back ",c.getString("ImageURL"));
                }
               
               Log.i("size :" ," "+CategoryId.size());
               Log.i("size :" ," "+Id.size());
               int k=0;
               
               for(int j=0;j<CategoryId.size();j++){
               	   List<String> TempItemName=new ArrayList<String>();
            	   List<String> TempPrice = new ArrayList<String>();
            	   List<String> TempDescription = new ArrayList<String>();
            	   List<String> TempImageURL = new ArrayList<String>();
            	   for(int i=0;i<Id.size();i++){
            		   if(CategoryId.get(j).equals(ItemCategoryId.get(i))){
                           Log.i("equal  :" ," "+Id.get(i)+"     "+CategoryId.get(j));
            			TempItemName.add(ItemName.get(i));
            			TempPrice.add(Price.get(i));
            			TempDescription.add(Description.get(i));
            			TempImageURL.add(ImageURL.get(i));
            		   }
            		  k++;
            	   }
            	   
            	   listDataItemName.put(""+j,TempItemName);
            	   listDataPrice.put(""+j, TempPrice);
            	   listDataDescription.put(""+j,TempDescription);
            	   listImage.put(""+j,TempImageURL);
            	   
               }
               Log.i("loop count :" ," "+k);           
      
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
       
        Log.i("length :"," "+categoryLength);
        Log.i("length :"," "+listDataItemName.size());
        for(int i=0;i<categoryLength;i++){
        	List<String> TItemName=new ArrayList<String>();
        	TItemName=listDataItemName.get(""+i);
        	
        	for(int j=0;j<TItemName.size();j++)
        	{	
        		Log.i("element   "+i,TItemName.get(j));
        	}
        }
      
        Intent i =new Intent(CustomerFoodOrderCategoryProcessClass.this,CustomerFoodOrderTabPage.class);
	     startActivity(i);
        
        }
     
}
	
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			finish();
	}

}
