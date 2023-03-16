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
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustActivity extends Activity {
	Button CustomerRegister;
	int flag;
	//private String URL_NEW_PREDICTION = "http://192.168.1.7/sampleserver/addCustomer.php";
	//private String URL_NEW_PREDICTION = "http://192.168.1.4/sampleserver/addCustomer.php";
	String CustomerName,CustomerPhoneNo,CustomerEmail,CustomerPassword,CustomerConfirmPassword;
	String EMAIL_REGEX ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	EditText customerNameEditText,customerPhoneNoEditText,CustomerEditTtext,customerPasswordEditText,customerConfirmPasswordEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cust);
		flag=0;
		
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>SIGN UP</font>"));
		
		
		customerNameEditText=(EditText)findViewById(R.id.reg_fullname);
		customerPhoneNoEditText= (EditText)findViewById(R.id.reg_custphonenumber);
		CustomerEditTtext=(EditText) findViewById(R.id.reg_custemail);
		customerPasswordEditText=(EditText) findViewById(R.id.customerSignupPasswordEditText);
		customerConfirmPasswordEditText=(EditText)findViewById(R.id.customerSignUpConfirmPasswordEditText);
		
		CustomerRegister=(Button)findViewById(R.id.buttonCustomerRegister);
		CustomerRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				CustomerName=customerNameEditText.getText().toString();
				CustomerPhoneNo=customerPhoneNoEditText.getText().toString();
				CustomerEmail=CustomerEditTtext.getText().toString();
				CustomerPassword=customerPasswordEditText.getText().toString();
				CustomerConfirmPassword=customerConfirmPasswordEditText.getText().toString();
				//CustomerName.trim();	
				//CustomerPhoneNo.trim();
				//CustomerEmail.trim();
				if((!CustomerName.equals("")) && (!CustomerPhoneNo.equals(""))  && (!CustomerEmail.equals("")) && (!CustomerPassword.equals("")) && (!CustomerConfirmPassword.equals(""))){
				
					if(CustomerPassword.equals(CustomerConfirmPassword)){
						if(CustomerPhoneNo.length()==10){
					    	if(CustomerName.matches("[A-Z]([a-z]+|\\s[a-z]+)")){
					    		if(CustomerEmail.matches(EMAIL_REGEX)){
					    					new AddNewCustomerInTable().execute(CustomerName,CustomerPhoneNo,CustomerEmail,CustomerPassword);
					    		}else{
					    			Toast.makeText(getApplicationContext(), "enter a valid email address", Toast.LENGTH_SHORT).show();
					    		}
					    	}
					    	else{
					    		Toast.makeText(getApplicationContext(), "DO not enter digit in Name", Toast.LENGTH_SHORT).show();
					    	}
					    	
					    	}
					    else{
					    	Toast.makeText(getApplicationContext(), "please enter 10 digit mobile no.", Toast.LENGTH_SHORT).show();
					    }}
					else{
						Toast.makeText(CustActivity.this,"please reenter password correctly" , Toast.LENGTH_LONG).show();
					}
				}
				else{
					
					Toast.makeText(CustActivity.this,"please fill all the fieids" , Toast.LENGTH_LONG).show();
					
				}
				
			}
		});
	}

private class AddNewCustomerInTable extends AsyncTask<String, Void, Void> {
	 
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
  
    }

    @Override
    protected Void doInBackground(String... arg) {
        // TODO Auto-generated method stub
    	String CustomerName,CustomerPhoneNo,CustomerEmailId,CustomerPassword;
        
    	CustomerName=arg[0];
		CustomerPhoneNo=arg[1];
		CustomerEmailId=arg[2];
		 CustomerPassword=arg[3];
		Log.i("name: ", "> " + CustomerName+CustomerPhoneNo+CustomerEmailId);
		
        // Preparing post params
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CustomerName", CustomerName));
        params.add(new BasicNameValuePair("CustomerPhoneNo",CustomerPhoneNo));
        params.add(new BasicNameValuePair("CustomerEmail",CustomerEmailId));
        params.add(new BasicNameValuePair("CustomerPassword",CustomerPassword));
        ServiceHandler serviceClient = new ServiceHandler();

        String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"addCustomer.php",
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
	                } 
	                else {
	                    Log.e("LOGIN Error: ","> " + jsonObj.getString("message"));
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
        Toast.makeText(CustActivity.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
        Intent i= new Intent(CustActivity.this, CustomerLogin.class);
		startActivity(i);
		finish();
		flag=0;
        }else if(flag==2){
        	Toast.makeText(CustActivity.this,"Email id already exists",Toast.LENGTH_SHORT).show();
        	flag=0;
        }else{
        	Toast.makeText(CustActivity.this, "Unable to register!", Toast.LENGTH_SHORT).show();
        	flag=0;
        }
    }
}
}