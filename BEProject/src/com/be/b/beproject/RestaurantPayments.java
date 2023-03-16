package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RestaurantPayments extends Activity {
	
	//private String URL_NEW_PREDICTION = "http://192.168.1.7/sampleserver/addRestaurant.php";
	//private String URL_NEW_PREDICTION = "http://192.168.1.4/sampleserver/addRestaurant.php";
	Button restaurantRegisterButton;
	//SQLiteDatabase restaurantTable;
	String Rest_OwnerName,Rest_PhoneNumber,Rest_Email,Rest_RestName,Rest_Location,Rest_Password,Rest_Speciality;
	Intent i;
	int flag;
	SharedPreferences pref ;
	Editor editor ;
	EditText  cardno,cvvno,pinno;
	Context context=this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rest_payment);
		flag=0;
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Sign Up</font>"));
		pref = getApplicationContext().getSharedPreferences("RestaurantOrderPref", MODE_PRIVATE); 
		editor = pref.edit();
		
		cardno=(EditText) findViewById(R.id.reg_cardno);
		cvvno=(EditText) findViewById(R.id.reg_cvvnumber);
		pinno=(EditText) findViewById(R.id.reg_pinNo);
		
		if(!pref.getBoolean("first_time_order",true)){
			//editor.putString("CardNo",CardNo.getText().toString());
			cardno.setText(pref.getString("CardNo",null));
		}
		
		Spinner monthSpinner;
		
		monthSpinner= (Spinner) findViewById(R.id.month_spinner);
		ArrayAdapter<CharSequence> monthAdapter= ArrayAdapter.createFromResource(this, R.array.month_array, android.R.layout.simple_spinner_item);
		monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		monthSpinner.setAdapter(monthAdapter);
		
		Spinner cardSpinner;
		
		cardSpinner= (Spinner) findViewById(R.id.card_spinner);
		ArrayAdapter<CharSequence> cardAdapter= ArrayAdapter.createFromResource(this, R.array.card_array, android.R.layout.simple_spinner_item);
		cardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cardSpinner.setAdapter(cardAdapter);
		
		Log.i("card value default", " "+cardSpinner.getSelectedItem().toString());
		
		Spinner yearSpinner;
		
		yearSpinner= (Spinner) findViewById(R.id.year_spinner);
		ArrayAdapter<CharSequence> yearAdapter= ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
		yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		yearSpinner.setAdapter(yearAdapter);
		
		i=getIntent();
		Rest_OwnerName=i.getStringExtra("Rest_OwnerName");
		Rest_PhoneNumber=i.getStringExtra("Rest_PhoneNumber");
		Rest_Email=i.getStringExtra("Rest_Email");
		Rest_Location=i.getStringExtra("Rest_Location");
		Rest_RestName=i.getStringExtra("Rest_RestName");
		Rest_Password=i.getStringExtra("Rest_Password");
		Rest_Speciality=i.getStringExtra("Rest_Speciality");
		
		restaurantRegisterButton=(Button)findViewById(R.id.RestaurantRegisterButton);
		restaurantRegisterButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(cardno.getText().toString().length()==16)
				{
				    if(cvvno.getText().toString().length()==3)
				    {
				    	if(pinno.getText().toString().length()==4)
				    	{	
				    		final Dialog dialog =new Dialog(context);
							dialog.setContentView(R.layout.save_card_detail_dialog);
							
							dialog.setTitle("Save Details...");
							
//							number=(EditText) dialog.findViewById(R.id.editText1);
//							number.setText("1");
							
							Button ok=(Button) dialog.findViewById(R.id.button1);
							Button cancel=(Button) dialog.findViewById(R.id.button2);
//							Button ok=(Button) dialog.findViewById(R.id.button3);
							
							dialog.show();
							ok.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									editor.putString("CardNo",cardno.getText().toString());
									editor.putBoolean("first_time_order", false);
									editor.commit();
									new AddNewRestaurantInTable().execute(Rest_OwnerName,Rest_PhoneNumber,Rest_Email,Rest_RestName,Rest_Location,Rest_Password,Rest_Speciality);
									dialog.dismiss();
								}
							});
				    		
							cancel.setOnClickListener(new View.OnClickListener() {
								
								@Override
								public void onClick(View v) {
									new AddNewRestaurantInTable().execute(Rest_OwnerName,Rest_PhoneNumber,Rest_Email,Rest_RestName,Rest_Location,Rest_Password,Rest_Speciality);
									dialog.dismiss();
								}
							});
				
							
				    	}else{
				    		Toast.makeText(RestaurantPayments.this, "enter 4 digit pin ", Toast.LENGTH_SHORT).show();
				    	}
				    }else{
				    	Toast.makeText(RestaurantPayments.this, "enter a valid 3 digit cvv number", Toast.LENGTH_SHORT).show();
				    }
				}else{
					Toast.makeText(RestaurantPayments.this, "enter a valid 16 digit card number", Toast.LENGTH_SHORT).show();
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


private class AddNewRestaurantInTable extends AsyncTask<String, Void, Void> {
	 
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
  
    }

    @Override
    protected Void doInBackground(String... arg) {
        // TODO Auto-generated method stub
    	String Rest_OwnerName,Rest_PhoneNumber,Rest_Email,Rest_RestName,Rest_Location,Rest_Password,Rest_Speciality;
        
    	Rest_OwnerName=arg[0];
		Rest_PhoneNumber=arg[1];
		Rest_Email=arg[2];
		Rest_Location=arg[4];
		Rest_RestName=arg[3];
		Rest_Password=arg[5];
		Rest_Speciality=arg[6];
		Log.i("name: ", "> " + Rest_OwnerName);
		
        // Preparing post params
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("RestaurantOwnerName", Rest_OwnerName));
        params.add(new BasicNameValuePair("RestaurantPhoneNumber", Rest_PhoneNumber));
        params.add(new BasicNameValuePair("RestaurantLocation", Rest_Location));
        params.add(new BasicNameValuePair("RestaurantEmail", Rest_Email));
        params.add(new BasicNameValuePair("RestaurantRestaurantName",Rest_RestName));
        params.add(new BasicNameValuePair("RestaurantPassword",Rest_Password));
        params.add(new BasicNameValuePair("RestaurantSpeciality", Rest_Speciality));
        ServiceHandler serviceClient = new ServiceHandler();

        String json = serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"addRestaurant.php",
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
	                	
	                    
	                }else if(error==2){
	                	flag=2;
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
        	Intent intent = new Intent(RestaurantPayments.this,RestaurantLogin.class);
			flag=0;
			startActivity(intent);
        }else if(flag==2){
        	Toast.makeText(RestaurantPayments.this,"Email id already exists",Toast.LENGTH_SHORT).show();
        	flag=0;
        }      
        else{
        	Toast.makeText(RestaurantPayments.this,"unable to register",Toast.LENGTH_SHORT).show();
        	flag=0;
        }
    }
}
}
