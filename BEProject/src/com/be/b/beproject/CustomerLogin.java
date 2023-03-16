package com.be.b.beproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;




import com.google.android.gms.gcm.GoogleCloudMessaging;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerLogin extends Activity{
	//private String URL_ITEMS = "http://192.168.1.4/sampleserver/CustLogin.php";
	Button customerLoginButton;
	EditText customerUsername,customerPassword;
	String CustomerEmail,CustomerPassword,ID,CustomerName,CustPhoneNo;
	int flag;
	SharedPreferences pref ;
	Editor editor ;
	String regId;
	Context context;
	GoogleCloudMessaging gcm;
	private GestureDetector gestureDetector;
	TextView customerButton;
	ImageView customToastImageView;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.customer_login);
	getActionBar().setDisplayShowTitleEnabled(false);
	getActionBar().hide();
	gestureDetector = new GestureDetector(new SwipeGestureDetector());
	
	Toast.makeText(getApplicationContext(), "Swipe or Double Tap for Restaurant Page", Toast.LENGTH_SHORT).show();
//	//Toast.makeText(getApplicationContext(), "Swipe or Double Tap for Restaurant Page", Toast.LENGTH_LONG).show();
//	LayoutInflater inflater = getLayoutInflater();
//	View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup) findViewById(R.id.toast_layout_root));
//
//	TextView text = (TextView) layout.findViewById(R.id.text);
//	text.setText("Swipe or Double Tap for Restaurant Page");
//	customToastImageView=(ImageView)layout.findViewById(R.id.customToastImageView);
//	customToastImageView.setImageResource(R.drawable.restaurant_login);
//
//	Toast toast = new Toast(getApplicationContext());
//	toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//	toast.setDuration(Toast.LENGTH_LONG);
//	toast.setView(layout);
//	toast.show();
	
	customerUsername=(EditText) findViewById(R.id.customerUsernameEditText);
	customerPassword=(EditText)findViewById(R.id.customerPasswordEditText);
	
	context = getApplicationContext();
	pref = getApplicationContext().getSharedPreferences("CustomerOrderPref", MODE_PRIVATE); 
	editor = pref.edit();
	
	if(!pref.getBoolean("first_time_login",true)){
		//editor.putString("CardNo",CardNo.getText().toString());
		customerUsername.setText(pref.getString("username",null));
	}
	
	customerButton= (TextView) findViewById(R.id.buttonCustomerRegister);
	customerButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i= new Intent(CustomerLogin.this, CustActivity.class);
			startActivity(i);
		}
	});
	
	
	
	customerLoginButton=(Button)findViewById(R.id.customerLoginButton);
	customerLoginButton.setOnClickListener(new View.OnClickListener() {
	@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			flag=0;
			if((!customerUsername.getText().toString().equals("")) && (!customerPassword.getText().toString().equals("")) ){
				
				CustomerEmail=customerUsername.getText().toString();
				CustomerPassword=customerPassword.getText().toString();
			
				//register with gcm for reg id
				
				
				
				new AddNewPrediction().execute(CustomerEmail,CustomerPassword);
			}
			else{
				
				Toast.makeText(CustomerLogin.this, "please enter both fields!", Toast.LENGTH_SHORT).show();
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
    	String CustEmail=arg[0];
    	String CustPassword=arg[1];
    	Log.d("url: ", "> " + CustPassword);
    	
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CustomerPassword", CustPassword));
        params.add(new BasicNameValuePair("CustomerEmail", CustEmail));
    	
        ServiceHandler serviceClient = new ServiceHandler();
        Log.i("url: ", "> " + CustomerConfig.URL_ITEMS);
        String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"CustLogin.php",ServiceHandler.POST,params);
        
        Log.i("Create Prediction Request: ", "> " + json);

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
                	ID=jsonObj.getString("Id");
                	Log.i("Rest ID ", "> " + ID);
                    CustomerName=jsonObj.getString("CustomerName");
                    Log.i("Name :",CustomerName);
                    
                    CustPhoneNo=jsonObj.getString("CustPhoneNo");
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
        	
          //   Intent i = new Intent(CustomerLogin.this,CustomerSpecialityOptionsPage.class);
          // Bundle bundle = new Bundle();
          // bundle.putString("RestId",ID);
          // i.putExtras(bundle);
            editor.putString("username",customerUsername.getText().toString());
			editor.putBoolean("first_time_login", false);
			editor.commit();
            CustomerConfig.CustId=ID;
            CustomerConfig.CustomerName=CustomerName;
            CustomerConfig.CustPhoneNo=CustPhoneNo;
        	Log.i("  IN POSTEXECUTE  ","   i.e:SUCCESS   ");
        //	customerUsername.setText("");
        	customerPassword.setText("");
		 //   startActivity(i);
        	
      //after successfull login get registered with GCM Cloud to receive notification  	
        	
        	regId=registerGCM() ;
        	
        	
        }
        else{
        	Toast.makeText(CustomerLogin.this, "Wrong Credentials!", Toast.LENGTH_SHORT).show();
        }
        
    }
}

//register with gcm for registeratoin Id

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
				parameters.add(new BasicNameValuePair("CustomerId", CustomerConfig.CustId));
		    	
		        ServiceHandler serviceClient = new ServiceHandler();
		        Log.i("url: ", "> " + CustomerConfig.URL_ITEMS);
		        String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"CustomerGCMRegId.php",ServiceHandler.POST,parameters);
		        
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
			
			Intent i = new Intent(CustomerLogin.this,CustomerSpecialityOptionsPage.class);
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
	  Intent i=new Intent(CustomerLogin.this,RestaurantLogin.class);
	  startActivity(i);
	  finish();
  }

  private void onRightSwipe() {
    // Do something
	  Intent i=new Intent(CustomerLogin.this,RestaurantLogin.class);
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
		Intent i=new Intent(CustomerLogin.this,RestaurantLogin.class);
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
   CustomerLogin.this.onLeftSwipe();

// Right swipe
} else if (-diff > SWIPE_MIN_DISTANCE
&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	CustomerLogin.this.onRightSwipe();
}
} catch (Exception e) {
Log.e("YourActivity", "Error on gestures");
}
return false;
}
}






}



