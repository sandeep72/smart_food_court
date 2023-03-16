package com.be.b.beproject;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class RestActivity extends Activity {
	String EMAIL_REGEX ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	Button ProceedToPayment;
	EditText Rest_OwnerName,Rest_PhoneNumber,Rest_Email,Rest_RestName,Rest_Location,Rest_Password,Rest_ConfirmPassword,Rest_Speciality;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rest);
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Sign Up</font>"));
		
		Rest_Password=(EditText) findViewById(R.id.restaurantSignupPasswordEditText);
		Rest_ConfirmPassword=(EditText) findViewById(R.id.restaurantSignUpConfirmPasswordEditText);
		Rest_OwnerName=(EditText) findViewById(R.id.rest_ownername);
		Rest_PhoneNumber=(EditText) findViewById(R.id.rest_phonenumber);
		Rest_Email=(EditText) findViewById(R.id.rest_email);
		Rest_RestName=(EditText) findViewById(R.id.rest_restaurantname);
		Rest_Location=(EditText) findViewById(R.id.rest_locationname);
		Rest_Speciality=(EditText)findViewById(R.id.rest_speciality);
		
		ProceedToPayment=(Button)findViewById(R.id.buttonPaymentProceed);
		ProceedToPayment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!Rest_OwnerName.getText().toString().equals("")  &&   !Rest_PhoneNumber.getText().toString().equals("")   && !Rest_Email.getText().toString().equals("")   &&  !Rest_Location.getText().toString().equals("")    &&  !Rest_Password.getText().toString().equals("")  &&   !Rest_RestName.getText().toString().equals("") && !Rest_Speciality.getText().toString().equals("")){
										
					if(Rest_Password.getText().toString().equals(Rest_ConfirmPassword.getText().toString())   &&  !Rest_Password.getText().toString().equals("")){
						
						if(Rest_OwnerName.getText().toString().matches("[A-Z]([a-z]+|\\s[a-z]+)")){	
							if(Rest_PhoneNumber.getText().toString().length()==10){
									if(Rest_Email.getText().toString().matches(EMAIL_REGEX)){
				Intent intent= new Intent(RestActivity.this, RestaurantPayments.class);
				
				intent.putExtra("Rest_OwnerName", Rest_OwnerName.getText().toString());
				intent.putExtra("Rest_PhoneNumber",Rest_PhoneNumber.getText().toString());
				intent.putExtra("Rest_Email",Rest_Email.getText().toString());
				intent.putExtra("Rest_RestName",Rest_RestName.getText().toString());
				intent.putExtra("Rest_Location",Rest_Location.getText().toString());
				intent.putExtra("Rest_Password",Rest_Password.getText().toString());
				intent.putExtra("Rest_Speciality", Rest_Speciality.getText().toString());
				
				startActivity(intent);
				}else{
					Toast.makeText(RestActivity.this, "enter a valid email address",Toast.LENGTH_SHORT).show();
				}
				}else{
					Toast.makeText(RestActivity.this, "enter a valid 10 digit mobile number",Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(RestActivity.this, "Owner Name should not contain digits",Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(RestActivity.this, "reenter the password correctly",Toast.LENGTH_SHORT).show();
		}
		}else{
			Toast.makeText(RestActivity.this, "Plese!! fill all the fields",Toast.LENGTH_SHORT).show();
		}
	}
	});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rest, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
