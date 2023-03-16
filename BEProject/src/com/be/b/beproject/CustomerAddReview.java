package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;





import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerAddReview extends Activity {
	//private String URL_NEW_PREDICTION = "http://192.168.1.2/sampleserver/addReview.php";
	Button button;
	String ReviewText,AmbienceRating,ServiceRating,FoodRating,ValueForMoneyRating,TableId;
	EditText text;
	//ratingEditText
	RatingBar ambienceRating,serviceRating,foodRating,valueForMoneyRating;
	String CustomerId,CustomerName;
	int flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_add_review);
		
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Add Review</font>"));
		
		Log.i("in on create", "success");
		button=(Button) findViewById(R.id.button1);
		ambienceRating=(RatingBar) findViewById(R.id.ambienceRating);
		serviceRating=(RatingBar) findViewById(R.id.serviceRating);
		foodRating=(RatingBar) findViewById(R.id.foodRating);
		valueForMoneyRating=(RatingBar) findViewById(R.id.valueForMoneyRating);
		
		text=(EditText) findViewById(R.id.editText1);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		flag=0;
	try{	 
		TableId=CustomerConfig.RestId;
		ReviewText=text.getText().toString();
		
		CustomerName=CustomerConfig.CustomerName;
		
		CustomerId=CustomerConfig.CustId;
		AmbienceRating=String.valueOf(ambienceRating.getRating());
		ServiceRating=String.valueOf(serviceRating.getRating());
		FoodRating=String.valueOf(foodRating.getRating());
		ValueForMoneyRating=String.valueOf(valueForMoneyRating.getRating());
		if(!ReviewText.equals("") && !(Float.parseFloat(FoodRating)==0)   &&  !(Float.parseFloat(ServiceRating)==0)   &&  !(Float.parseFloat(AmbienceRating)==0)   &&   !(Float.parseFloat(ValueForMoneyRating)==0)){
		
		new AddNewPrediction().execute(ReviewText,AmbienceRating,ServiceRating,FoodRating,ValueForMoneyRating,TableId,CustomerId,CustomerName);
		}else{
			Toast.makeText(CustomerAddReview.this, "please provide review text and rating", Toast.LENGTH_SHORT).show();
		}
											
	
		
	}
	catch(Exception e){
		Toast.makeText(CustomerAddReview.this, "error inserting data", Toast.LENGTH_SHORT).show();
	}
	
}
});
}

@Override
protected void onPause() {
// TODO Auto-generated method stub
super.onPause();
finish();
}

private class AddNewPrediction extends AsyncTask<String, Void, Void> {

@Override
protected void onPreExecute() {
super.onPreExecute();

}

@Override
protected Void doInBackground(String... arg) {
// TODO Auto-generated method stub
String ReviewText,AmbienceRating,ServiceRating,FoodRating,ValueForMoneyRating,TableId,CustomerName,CustomerId;


ReviewText=arg[0];
AmbienceRating=arg[1];
ServiceRating=arg[2];
FoodRating=arg[3];
ValueForMoneyRating=arg[4];
TableId=arg[5];	
CustomerId=arg[6];
CustomerName=arg[7];
Log.i("name: ", "> " + ReviewText);

// Preparing post params
List<NameValuePair> params = new ArrayList<NameValuePair>();
params.add(new BasicNameValuePair("ReviewText", ReviewText));
params.add(new BasicNameValuePair("CustomerId", CustomerId));
params.add(new BasicNameValuePair("AmbienceRating", AmbienceRating));
params.add(new BasicNameValuePair("ServiceRating",ServiceRating));
params.add(new BasicNameValuePair("FoodRating",FoodRating));
params.add(new BasicNameValuePair("ValueForMoneyRating",ValueForMoneyRating));
params.add(new BasicNameValuePair("TableId", TableId));
params.add(new BasicNameValuePair("CustomerName",CustomerName));

ServiceHandler serviceClient = new ServiceHandler();

String json = serviceClient.makeServiceCall(CustomerConfig.URL_ITEMS+"addReview.php",
        ServiceHandler.POST, params);

Log.d("Create Prediction Request: ", "> " + json);

if (json != null) {
    try {
    	json=(json.substring(json.lastIndexOf("</table></font>"), json.lastIndexOf("}") + 1));
		json=(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
		 Log.i("modified json ", "> " + json);
        
		 JSONObject jsonObj = new JSONObject(json);
		 
        int error = jsonObj.getInt("error");
        
        if (error==0){
           flag=1;
            Log.i("review added successfully ",
                    "> " + jsonObj.getString("message"));
           
           
        } else {
            Log.e("Add Review Error: ",
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


	Intent i=new Intent(CustomerAddReview.this,CustomerShowReviews.class);
	//CustomerConfig.RestId=null;
	if(flag==1){
	Toast.makeText(CustomerAddReview.this, "review added successfully", Toast.LENGTH_SHORT).show();
	}
	else{
		Toast.makeText(CustomerAddReview.this, "oops!!!   error adding review", Toast.LENGTH_SHORT).show();

	}
	startActivity(i);
	
}
}
}

