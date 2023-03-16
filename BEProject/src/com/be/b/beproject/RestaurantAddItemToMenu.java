package com.be.b.beproject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;





import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class RestaurantAddItemToMenu extends Activity{
	Button	addItem,getImage;
	ImageView imageView;
	EditText itemName,itemPrice,itemDescription;
	Spinner itemCategory;
	String ItemName,ItemPrice,ItemCategory,ItemDescription;
	int flag;
	Context context=this;
	String RestId;
	Intent intent;
	private String upLoadServerUri = null;
	private String imagepath=null;
	Bitmap bmp=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_add_item__to_menu);
		
		getImage=(Button) findViewById(R.id.getImage);
		imageView=(ImageView) findViewById(R.id.imageView1);
		InputStream ip=getResources().openRawResource(R.drawable.no_image_available);
		bmp=BitmapFactory.decodeStream(ip);
		
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Add New Menu</font>"));
		flag=0;
		itemName=(EditText)findViewById(R.id.itemName);
		itemPrice=(EditText)findViewById(R.id.itemPrice);
		itemCategory=(Spinner)findViewById(R.id.categorySpinner);
	
		itemDescription=(EditText)findViewById(R.id.itemDescription);
		
		ArrayList<String> foodTypes=RestaurantEditMenuFragment.CategoryName;
		String[] categoryString = foodTypes.toArray(new String[foodTypes.size()]);
		
		//itemCategory= (Spinner) findViewById(R.id.card_spinner);
		ArrayAdapter<String> categoryAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categoryString);
		categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		itemCategory.setAdapter(categoryAdapter);
		
		
		RestId=RestaurantConfig.RestId;
		
		addItem= (Button)findViewById(R.id.addItemToMenuButton);
		addItem.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try{
				//restaurantTable= openOrCreateDatabase("BEProjectTables", SQLiteDatabase.CREATE_IF_NECESSARY, null);
				//ContentValues contentValues=new ContentValues();
				//contentValues.put("itemName", itemName.getText().toString());
				//contentValues.put("itemPrice", Integer.parseInt(itemPrice.getText().toString()));
				//contentValues.put("itemCategory", itemCategory.getText().toString());
				//contentValues.put("itemDescription", itemDescription.getText().toString());
				//restaurantTable.insertOrThrow("menuitems", null, contentValues);
				
					ItemName=itemName.getText().toString();
					ItemPrice=itemPrice.getText().toString();
					ItemCategory=itemCategory.getSelectedItem().toString();
					ItemDescription=itemDescription.getText().toString();
				if(!ItemName.equals("") && !ItemPrice.equals("")  && !ItemCategory.equals("")  && !ItemDescription.equals("")){	
					new AddNewPrediction().execute(ItemName,ItemPrice,ItemCategory,ItemDescription);
				}else{
					Toast.makeText(getApplicationContext(), "please fill all the fields ",Toast.LENGTH_SHORT).show();
				}
														
				//Toast.makeText(RestaurantAddItemToMenu.this, "insertion successful", Toast.LENGTH_SHORT).show();
				//Intent i = new Intent(RestaurantAddItemToMenu.this,RestaurantHomeActivity.class);
				//startActivity(i);
					
				}
				catch(Exception e){
					Toast.makeText(RestaurantAddItemToMenu.this, "error inserting data", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		
		getImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Dialog dialog =new Dialog(context);
				dialog.setContentView(R.layout.get_image_from_dialog);
				
				dialog.setTitle("Table Order...");
				

				Button fromGallery=(Button) dialog.findViewById(R.id.button1);
				Button captureImage=(Button) dialog.findViewById(R.id.button2);
								
				dialog.show();
				fromGallery.setOnClickListener(new View.OnClickListener() {
				@Override
					public void onClick(View v) {
					    dialog.dismiss();
						Intent intent = new Intent();
			            intent.setType("image/*");
			            intent.setAction(Intent.ACTION_GET_CONTENT);
			            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
								
						
					}
				});
				
				
				captureImage.setOnClickListener( new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						 startActivityForResult(intent,2);
						 dialog.dismiss();
					}
				});
				
				
				
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(RestaurantAddItemToMenu.this,RestaurantEditMenuFragment.class);
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
	    	String itemName,itemPrice,itemCategory,itemDescription;
	        
	    	itemName=arg[0];
			itemPrice=arg[1];
			itemCategory=arg[2];
			itemDescription=arg[3];
						 
			Log.i("name: ", "> " + itemName);
			//image to string conversion
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
		    byte [] byte_arr = stream.toByteArray();
		    String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
			
			
			
			
	        // Preparing post params
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("ItemName", itemName));
	        params.add(new BasicNameValuePair("ItemPrice", itemPrice));
	        params.add(new BasicNameValuePair("ItemCategory", itemCategory));
	        params.add(new BasicNameValuePair("ItemDescription", itemDescription));
	        params.add(new BasicNameValuePair("RestId",RestId));
	        params.add(new BasicNameValuePair("Image",image_str));
	        ServiceHandler serviceClient = new ServiceHandler();

	        String json = serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"addMenu.php",
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
	        	Intent i = new Intent(RestaurantAddItemToMenu.this,RestaurantEditMenuFragment.class);
				startActivity(i);
				finish();
	        }else{
	        	Toast.makeText(RestaurantAddItemToMenu.this, "error inserting data", Toast.LENGTH_SHORT).show();
	        }
	    }
	}
	
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	         
	        if (requestCode == 1 && resultCode == RESULT_OK) {
	            //Bitmap photo = (Bitmap) data.getData().getPath(); 
	        	BitmapFactory.Options options = null;
				options = new BitmapFactory.Options();
				options.inSampleSize = 3;
	        	
	            Uri selectedImageUri = data.getData();
	            imagepath = getPath(selectedImageUri);
	            bmp=BitmapFactory.decodeFile(imagepath,options);
	            imageView.setImageBitmap(bmp);
	        }
	        if (requestCode == 2 && resultCode == RESULT_OK) {
	           Bundle extra=data.getExtras();
	           bmp=(Bitmap) extra.get("data");
	           imageView.setImageBitmap(bmp);
	             
	        }
	    }
	 public String getPath(Uri uri) {
      String[] projection = { MediaStore.Images.Media.DATA };
      Cursor cursor = managedQuery(uri, projection, null, null, null);
      int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      cursor.moveToFirst();
      return cursor.getString(column_index);
  }
	
	
	
	
	
	
	
	}


