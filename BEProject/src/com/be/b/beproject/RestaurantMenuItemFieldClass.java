package com.be.b.beproject;

public class RestaurantMenuItemFieldClass {
	String ItemName;
	String ItemPrice;
	String ItemDescription;
	public RestaurantMenuItemFieldClass(String itemName, String itemPrice,
			String itemDescription) {
		super();
		ItemName = itemName;
		ItemPrice = itemPrice;
		ItemDescription = itemDescription;
	}
	public String getItemName() {
		return ItemName;
	}
	public String getItemPrice() {
		return ItemPrice;
	}
	public String getItemDescription() {
		return ItemDescription;
	}
	
}
/*



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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantEditMenuFragment extends Fragment{

	Button addItemActivityButton;
	private String URL_ITEMS = "http://192.168.1.7/sampleserver/DisplayRestaurantMenuItems.php";
	TextView t1,t2,t3;
	String ItemName,ItemPrice,ItemDescription;
	String RestId;
	View v ;
	
	 private List<RestaurantMenuItemFieldClass> list=new ArrayList<RestaurantMenuItemFieldClass>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);

	    v = inflater.inflate(R.layout.restaurant_edit_menu, container, false);
	    addItemActivityButton = (Button) v.findViewById(R.id.addItemActivityButton);
	   
	   
	    RestId="3";
	    new AddNewPrediction().execute(RestId);
	    
	    	    	
	    addItemActivityButton.setOnClickListener(new View.OnClickListener() {
	    		
	        @Override
	        public void onClick(View v) {
	            // TODO Auto-generated method stub//Get content of TextView
	            Intent i = new Intent(getActivity(), RestaurantAddItemToMenu.class);
	            i.putExtra("RestId", ""+RestId);
	            startActivity(i);
	        }

	    });
	    	
	    //END OF +++ BUTTON

	    return v;
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
            Log.d("url: ", "> " + URL_ITEMS);
            String json = serviceClient.makeServiceCall(URL_ITEMS,ServiceHandler.POST,params);
            Log.i("json",json);
                       
            if (json != null) {
                try {
                	json=(json.substring(json.indexOf("</table></font>"), json.lastIndexOf("}") + 1));
					json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));	
					
                	Log.i("json modified",json);
					
                	JSONObject jObj=new JSONObject(json);
                	Log.i("object","success");
					JSONArray jArray=jObj.getJSONArray("MenuList");
                    Log.i("array",jArray.toString());
                                       
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject c = jArray.getJSONObject(i);
                        
                      
                        ItemName = c.getString("ItemName");
                        //Toast.makeText(getActivity(), ItemName, Toast.LENGTH_SHORT).show();
                        Log.i("matchId", ItemName);
                        
                         ItemPrice  = c.getString("ItemPrice");
                        Log.i("teamA", ItemPrice);
                        
                        ItemDescription = c.getString("ItemDescription");
                        Log.i("teamB", ItemDescription);
                        
                        
                        list.add(new RestaurantMenuItemFieldClass(ItemName, ItemPrice, ItemDescription));
                        
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
            ArrayAdapter<RestaurantMenuItemFieldClass> adapter=new MyListAdapter();
         
            ListView listView=(ListView) getActivity().findViewById(R.id.listMenu);
            listView.setAdapter(adapter);
            //Toast.makeText(getActivity(), ItemName, Toast.LENGTH_SHORT).show();
            
            //litVIew object is to be used for selecting or getting multiple select check boxes
            
        }
    }
	
	private class MyListAdapter extends ArrayAdapter<RestaurantMenuItemFieldClass>{

		public MyListAdapter() {
			super(getActivity(), R.layout.restaurant_menu_list_row);
			
		}
		  
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView=convertView;
			if(itemView==null)
			{
				itemView= getActivity().getLayoutInflater().inflate(R.layout.restaurant_menu_list_row, parent, false);
				
			}
			RestaurantMenuItemFieldClass obj=list.get(position);
			
			t1=(TextView) itemView.findViewById(R.id.itemNameTextView);
			t1.setText(obj.getItemName());
			t2=(TextView) itemView.findViewById(R.id.itemPriceTextView);
			t2.setText(obj.ItemPrice);
			t3=(TextView) itemView.findViewById(R.id.itemDescriptionTextView);
			t3.setText(obj.ItemDescription);
			
			return itemView;
			
		}
		
			
	  }
	
}



















*/