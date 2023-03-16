package com.be.b.beproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.gcm.GoogleCloudMessaging;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantLogin extends Activity{
	//private String URL_ITEMS = "http://192.168.1.4/sampleserver/RestLogin.php";
	Button restaurantLoginButton;
	EditText restaurantUsername,restaurantPassword;
	String RestEmailId,RestPassword;
	String ID;
	SharedPreferences pref ;
	Editor editor ;
	int flag;
	TextView restaurantButton;
	String regId;
	Context context;
	GoogleCloudMessaging gcm;
	private GestureDetector gestureDetector;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.restaurant_login);
	gestureDetector = new GestureDetector(new SwipeGestureDetector());
	
	getActionBar().setDisplayShowTitleEnabled(false);
	getActionBar().hide();
	
	Toast.makeText(getApplicationContext(), "Swipe or Double Tap for Customer Page", Toast.LENGTH_SHORT).show();
	
	pref = getApplicationContext().getSharedPreferences("RestaurantOrderPref", MODE_PRIVATE); 
	editor = pref.edit();
	
	context = getApplicationContext();
	
	restaurantUsername=(EditText)findViewById(R.id.restaurantUsernameEditText);
	restaurantPassword=(EditText)findViewById(R.id.restaurantPasswordEditText);
	
	if(!pref.getBoolean("first_time_login",true)){
		//editor.putString("CardNo",CardNo.getText().toString());
		restaurantUsername.setText(pref.getString("username",null));
	}
	
	restaurantButton= (TextView) findViewById(R.id.buttonRestaurantRegister);
	restaurantButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(RestaurantLogin.this, RestActivity.class);
			startActivity(i);
		}
	});
	
	
	
	
	
	restaurantLoginButton=(Button)findViewById(R.id.RestaurantLoginButton);
	restaurantLoginButton.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			flag=0;
			if(!(restaurantUsername.getText().toString().trim().equals("")) && !(restaurantPassword.getText().toString().trim().equals("")) ){
				
				//call the asyn class here and pass the credentials Set flag t either 1 or zero depending upon the credential verification 
				//and then call the next activity passing the id of the restaurant. and get going
				
				RestEmailId=restaurantUsername.getText().toString();
				RestPassword=restaurantPassword.getText().toString();
				
				
				
				new AddNewPrediction().execute(RestEmailId,RestPassword);
					
				
			}
			else{
				Toast.makeText(RestaurantLogin.this, "please enter your credentials !", Toast.LENGTH_SHORT).show();
			}
		}
	});
}
	
	


private class AddNewPrediction extends AsyncTask<String, Void, Void> {
    @Override
        protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
        protected Void doInBackground(String... arg) {
    	String RestEmail=arg[0];
    	String RestPassword=arg[1];
    	Log.d("url: ", "> " + RestPassword);
    	
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("RestaurantPassword", RestPassword));
        params.add(new BasicNameValuePair("RestaurantEmail", RestEmail));
    	
        ServiceHandler serviceClient = new ServiceHandler();
        Log.i("url: ", "> " + RestaurantConfig.URL_ITEMS+"RestLogin.php");
        String json = serviceClient.makeServiceCall(RestaurantConfig.URL_ITEMS+"RestLogin.php",ServiceHandler.POST,params);
        
        Log.i("Create Prediction Request: ", "> " + json);

        if (json != null) {
            try {
            	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
				json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
				 Log.i("modofied json ", "> " + json);
                
				 JSONObject jsonObj = new JSONObject(json);
				 
                int error = jsonObj.getInt("error");
                // checking for error node in json
                if (error==0) {
                    // new category created successfully
                	flag=1;
                	ID=jsonObj.getString("Id");
                	Log.i("Rest ID ", "> " + ID);
                    
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
          // Intent i = new Intent(RestaurantLogin.this,RestaurantHomeActivity.class);
          // Bundle bundle = new Bundle();
          // bundle.putString("RestId",ID);
          // i.putExtras(bundle);
            editor.putString("username",restaurantUsername.getText().toString());
			editor.putBoolean("first_time_login", false);
			editor.commit();
           
           RestaurantConfig.RestId=ID;
           
           
          // RestaurantConfig.setTAB="1";
        	Log.i("  IN POSTEXECUTE  ","   i.e:SUCCESS   ");
        
        	//	restaurantUsername.setText("");
        	restaurantPassword.setText("");
		 //  startActivity(i);
        	
        	regId=registerGCM() ;
        }
        else{
        	Toast.makeText(RestaurantLogin.this, "Wrong Credentials!", Toast.LENGTH_SHORT).show();
        	restaurantPassword.setText("");
        }
        
    }
}











public String registerGCM() {

	gcm = GoogleCloudMessaging.getInstance(this);
//	regId = getRegistrationId(context);

//	if (TextUtils.isEmpty(regId)) {

		registerInBackground();

		Log.d("RegisterActivity",
				"registerGCM - successfully registered with GCM server - regId: "
						+ regId);
//	} else {
//		Toast.makeText(getApplicationContext(),
//				"RegId already available. RegId: " + regId,
//				Toast.LENGTH_LONG).show();
//	}
	return regId;
}



//@SuppressLint("NewApi")
//private String getRegistrationId(Context context) {
//	final SharedPreferences prefs = getSharedPreferences(
//			MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
//	String registrationId = prefs.getString(REG_ID, "");
//	if (registrationId.isEmpty()) {
//		Log.i(TAG, "Registration not found.");
//		return "";
//	}
//	int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
//	int currentVersion = getAppVersion(context);
//	if (registeredVersion != currentVersion) {
//		Log.i(TAG, "App version changed.");
//		return "";
//	}
//	return registrationId;
//}


private void registerInBackground() {
	new AsyncTask<Void, Void, String>() {
		@Override
		protected String doInBackground(Void... params) {
			String msg = "";
			try {
				if (gcm == null) {
					gcm = GoogleCloudMessaging.getInstance(context);
				}
				regId = gcm.register(CustomerConfig.GOOGLE_PROJECT_ID);
				Log.d("RegisterActivity", "registerInBackground - regId: "
						+ regId);
				msg = "Device registered, registration ID=" + regId;
				
				
				List<NameValuePair> parameters = new ArrayList<NameValuePair>();
				parameters.add(new BasicNameValuePair("GCMRegId",regId ));
				parameters.add(new BasicNameValuePair("RestaurantId", RestaurantConfig.RestId));
		    	
		        ServiceHandler serviceClient = new ServiceHandler();
		        Log.i("url: ", "> " + CustomerConfig.URL_ITEMS);
		        String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"RestaurantGCMRegId.php",ServiceHandler.POST,parameters);
		        
		        Log.i("Create Prediction Request: ", "> " + json);
				
				
				
//				storeRegistrationId(context, regId);
			} catch (IOException ex) {
				msg = "Error :" + ex.getMessage();
				Log.d("RegisterActivity", "Error: " + msg);
			}
			Log.d("RegisterActivity", "AsyncTask completed: " + msg);
			return msg;
		}

		@Override
		protected void onPostExecute(String msg) {
//			Toast.makeText(getApplicationContext(),
//					"Registered with GCM Server." + msg, Toast.LENGTH_LONG)
//					.show();
			
			Intent i = new Intent(RestaurantLogin.this,RestaurantHomeActivity.class);
			startActivity(i);
			
		}
	}.execute(null, null, null);
}





@Override
  public boolean onTouchEvent(MotionEvent event) {
    if (gestureDetector.onTouchEvent(event)) {
      return true;
    }
    return super.onTouchEvent(event);
  }

  private void onLeftSwipe() {
    // Do something
	  Intent i=new Intent(RestaurantLogin.this,CustomerLogin.class);
	  startActivity(i);
	  finish();
  }

  private void onRightSwipe() {
    // Do something
	  Intent i=new Intent(RestaurantLogin.this,CustomerLogin.class);
	  startActivity(i);
	  finish();
  }
  
  private class SwipeGestureDetector extends SimpleOnGestureListener {
//Swipe properties, you can change it to make the swipe 
//longer or shorter and speed
private static final int SWIPE_MIN_DISTANCE = 120;
private static final int SWIPE_MAX_OFF_PATH = 200;
private static final int SWIPE_THRESHOLD_VELOCITY = 200;

@Override
public boolean onDoubleTap(MotionEvent e) {
	// TODO Auto-generated method stub
	Intent i=new Intent(RestaurantLogin.this,CustomerLogin.class);
	startActivity(i);
	finish();
	return super.onDoubleTap(e);
}

@Override
public boolean onFling(MotionEvent e1, MotionEvent e2,
                 float velocityX, float velocityY) {
try {
float diffAbs = Math.abs(e1.getY() - e2.getY());
float diff = e1.getX() - e2.getX();

if (diffAbs > SWIPE_MAX_OFF_PATH)
  return false;

// Left swipe
if (diff > SWIPE_MIN_DISTANCE
&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
   RestaurantLogin.this.onLeftSwipe();

// Right swipe
} else if (-diff > SWIPE_MIN_DISTANCE
&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	RestaurantLogin.this.onRightSwipe();
}
} catch (Exception e) {
Log.e("YourActivity", "Error on gestures");
}
return false;
}
}









}
