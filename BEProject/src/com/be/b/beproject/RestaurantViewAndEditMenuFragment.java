package com.be.b.beproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.be.b.beproject.CustomerFoodOrderPlacementFragment.CustomListViewAdapter;






import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RestaurantViewAndEditMenuFragment extends Fragment implements OnItemClickListener{
	int sanvai=RestaurantAdapterclass.saawan;
	List<CustomerFoodOrderPlacementItemFieldClass> list = new ArrayList<CustomerFoodOrderPlacementItemFieldClass>();
	TextView t1,t2,t3;
	EditText number;
	int flag;
	ImageView imageView;
	List SelectedItemName,SelectedItemQuantity,SelectedItemPrice;
	Context context=getActivity();
	Dialog dialog;
	CustomerFoodOrderPlacementItemFieldClass clickToast;
	EditText nameEditText,priceEditText,descriptionEditText;
	ImageLoader imgLoader ;
	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		
		
		List<String> ItemName=new ArrayList<String>();
		List<String> ItemPrice=new ArrayList<String>();
		List<String> ItemDescription=new ArrayList<String>();
		List<String> ItemId=new ArrayList<String>();
		List<String> ItemImageURL=new ArrayList<String>();
		list.clear();
		flag=0;
		imgLoader = new ImageLoader(getActivity());
		
//		HashMap<String,List<String>> listData =RestaurantEditMenuFragment.listDataItemName;
//		List<String> my2=new ArrayList<String>();
		if(RestaurantEditMenuFragment.listDataItemName.size()!=0){
		ItemName=RestaurantEditMenuFragment.listDataItemName.get(""+sanvai);
		String itemName[]=ItemName.toArray(new String[ItemName.size()]);
		
		ItemPrice=RestaurantEditMenuFragment.listDataPrice.get(""+sanvai);
		String itemPrice[]=ItemPrice.toArray(new String[ItemPrice.size()]);
		
		ItemDescription=RestaurantEditMenuFragment.listDataDescription.get(""+sanvai);
		String itemDescription[]=ItemDescription.toArray(new String[ItemDescription.size()]);
		
		ItemId=RestaurantEditMenuFragment.listDataId.get(""+sanvai);
		String itemId[]=ItemId.toArray(new String[ItemId.size()]);
		
		ItemImageURL=RestaurantEditMenuFragment.listImage.get(""+sanvai);
		String itemImageURL[]=ItemImageURL.toArray(new String[ItemImageURL.size()]);
		
		for(int i=0;i<itemName.length;i++){
			list.add(new CustomerFoodOrderPlacementItemFieldClass(itemName[i], itemPrice[i], itemDescription[i],itemId[i],itemImageURL[i]));
			
		}
}	
		
		View view = inflater.inflate(R.layout.customerfoodorderplacementfragment, container, false);
		
		ListView listView = (ListView)view.findViewById(R.id.listView1);
		ArrayAdapter<CustomerFoodOrderPlacementItemFieldClass> adapter = new CustomListViewAdapter();
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//edit menu code goes here
				dialog =new Dialog(getActivity());
				dialog.setContentView(R.layout.edit_menu_dialog);
				
				dialog.setTitle("Edit Menu Dialog");
				
				
				
				
				Button save=(Button) dialog.findViewById(R.id.saveButton);
				Button delete=(Button) dialog.findViewById(R.id.deleteButton);
				Button cancel=(Button) dialog.findViewById(R.id.cancelButton);
				
				nameEditText=(EditText) dialog.findViewById(R.id.nameEditText);
				priceEditText=(EditText) dialog.findViewById(R.id.priceEditText);
				descriptionEditText=(EditText) dialog.findViewById(R.id.descriptionEditText);

				
				
				clickToast=list.get(position);
				
				nameEditText.setText(clickToast.getItemName());
				priceEditText.setText(clickToast.getItemPrice());
				descriptionEditText.setText(clickToast.getItemDescription());
				
				
				dialog.show();
				save.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						new AddNewPrediction().execute("0",nameEditText.getText().toString(),priceEditText.getText().toString(),descriptionEditText.getText().toString(),clickToast.getId());
						
					   dialog.dismiss();
					}
				});
				
				
				
				
				
				delete.setOnClickListener( new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						new AddNewPrediction().execute("1",clickToast.getId());
						
						   dialog.dismiss();

					}
				});
				
				
				cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						   dialog.dismiss();

						
					}
				});
				
				
	}


});
	return view;

}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
	
	class CustomListViewAdapter extends ArrayAdapter<CustomerFoodOrderPlacementItemFieldClass> {
		public CustomListViewAdapter() {
			super(getActivity(), R.layout.row_viewmenu,list);
		}
		  
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView=convertView;
			if(itemView==null)
			{
				itemView=getActivity().getLayoutInflater().inflate(R.layout.row_viewmenu, parent, false);
				
			}
			CustomerFoodOrderPlacementItemFieldClass obj=list.get(position);
			
			t1=(TextView) itemView.findViewById(R.id.customerMenuItemNameTextView);
			t1.setText(obj.getItemName());
			//Log.i("name ",obj.getName());
			t2=(TextView) itemView.findViewById(R.id.customerMenuItemPriceTextView);
			t2.setText(obj.getItemPrice());
			t3=(TextView) itemView.findViewById(R.id.customerMenuItemDescriptionTextView);
			t3.setText(obj.getItemDescription());
			//image for menu items
			imageView=(ImageView) itemView.findViewById(R.id.imageView1);
		
			
			String image_url = RestaurantConfig.URL_ITEMS+"/"+obj.getImageURL();
			Log.i("image path",image_url);
			
			imgLoader.DisplayImage(image_url, imageView);
			
			//Bitmap bitmap = DownloadImage(image_url);
		       // ImageView img = (ImageView) findViewById(R.id.img);
		       // imageView.setImageBitmap(bitmap);
			
			return itemView;
			
		}
		
	    
	}
	
	
	
	private class AddNewPrediction extends AsyncTask<String, Void, Void> {
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
        }
        
        @Override
            protected Void doInBackground(String... arg) {
        	String flagvar=arg[0];
        	String ItemName,ItemPrice,ItemDescription,ItemId;
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
        	ServiceHandler serviceClient = new ServiceHandler();
        	String json=null;
        	
        	
        	if(flagvar.equals("0")){
        	//save edited menu code goes here
        	ItemName=arg[1];
        	ItemPrice=arg[2];
        	ItemDescription=arg[3];
        	ItemId=arg[4];
        	
        	params.add(new BasicNameValuePair("RestId", RestaurantConfig.RestId));
        	params.add(new BasicNameValuePair("ItemName",ItemName));	
        	params.add(new BasicNameValuePair("ItemPrice",ItemPrice));	
        	params.add(new BasicNameValuePair("ItemDescription",ItemDescription)); 
        	params.add(new BasicNameValuePair("ItemId",ItemId));	
        	json= serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"editmenuitem.php",ServiceHandler.POST,params);
        	
        	
        	}
        	if(flagvar.equals("1")){
        	//delete item from menu card code goes here
        		ItemId=arg[1];	
        		params.add(new BasicNameValuePair("ItemId",ItemId));	
        		params.add(new BasicNameValuePair("RestId", RestaurantConfig.RestId));
        		json= serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"deletemenuitem.php",ServiceHandler.POST,params);
        		
        	}
        
            Log.i("json",json);
                       
            if (json != null) {
                try {
                	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
					json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));	
					
					
					
                	Log.i("json modified",json);
                	
                	JSONObject jsonObj=new JSONObject(json);
                	
                	int error = jsonObj.getInt("error");
                    // checking for error node in json
                    if (error==0) {
                        // new category created successfully
                    	flag=1;
                    	
                    	Log.i("SUCCESSFULL LOGIN ",
                                "> " + jsonObj.getString("message"));
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
               Intent i = new Intent(getActivity(),RestaurantEditMenuFragment.class);
              
    		   startActivity(i);
            }
            else{
            	Toast.makeText(getActivity(), "error updating data", Toast.LENGTH_SHORT).show();
            }
            
        }
    }
     


}//end of parent class
