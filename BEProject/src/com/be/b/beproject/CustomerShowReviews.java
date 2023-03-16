package com.be.b.beproject;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;










import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class CustomerShowReviews extends Activity {
	//private String URL_ITEMS = "http://192.168.1.2/sampleserver/getdata.php";
	private String URL_ITEMS= CustomerConfig.URL_ITEMS+"getdata.php";
	TextView t1,t2,t4;
	TextView t5,t6,t7,t8;
	
	//t3
	RatingBar ratingBarOfIndividualCustomer;
	String Text,Rating,Date,UserId;
	private List<ReviewFields> list=new ArrayList<ReviewFields>();
	Button AddReviewButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_show_reviews);
		getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>User Reviews</font>"));
	
	new AddNewPrediction().execute();
	AddReviewButton=(Button) findViewById(R.id.addReviewButton);
	AddReviewButton.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i=new Intent(CustomerShowReviews.this,CustomerAddReview.class);
			startActivity(i);
			finish();
			
		}
	});
	
	
	}

		
	private class AddNewPrediction extends AsyncTask<Void, Void, Void> {
        @Override
            protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
            protected Void doInBackground(Void... arg) {
        	
        	
        	
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
        	params.add(new BasicNameValuePair("RestId",CustomerConfig.RestId));
        	
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
					JSONArray jArray=jObj.getJSONArray("Review");
                    Log.i("array",jArray.toString());
                                       
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject c = jArray.getJSONObject(i);
                        
                        Text = c.getString("Text");
                        Log.i("matchId", Text);
                        
                         UserId = c.getString("userId");
                        Log.i("teamA", UserId);
                        
                         Date = c.getString("Date");
                        Log.i("teamB", Date);
                        
                         Rating = c.getString("Rating");
                        Log.i("teamB", Rating);
                        
                        String AmbienceRating=c.getString("AmbienceRating");
                        String ServiceRating=c.getString("ServiceRating");
                        String FoodRating=c.getString("FoodRating");
                        String ValueForMoneyRating=c.getString("ValueForMoneyRating");
                        
                        list.add(new ReviewFields(Text, Rating, Date, UserId,AmbienceRating,ServiceRating,FoodRating,ValueForMoneyRating));
                        
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
            ArrayAdapter<ReviewFields> adapter=new MyListAdapter();
            ListView listView=(ListView) findViewById(R.id.listOfReviewsListView);
            listView.setAdapter(adapter);
        }
    }
    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.rest, menu);
        return true;
    }
    
    private class MyListAdapter extends ArrayAdapter<ReviewFields>{

    	public MyListAdapter() {
    		super(CustomerShowReviews.this, R.layout.customer_review_row,list);
    		
    	}
	  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView=convertView;
		if(itemView==null)
		{
			itemView=getLayoutInflater().inflate(R.layout.customer_review_row, parent, false);
		}
		ReviewFields obj=list.get(position);
		
		t1=(TextView) itemView.findViewById(R.id.textOfReviewTextView);
		t1.setText(obj.getText());
		t2=(TextView) itemView.findViewById(R.id.dateOfReviewtextView);
		t2.setText(obj.getDate());
		ratingBarOfIndividualCustomer=(RatingBar) itemView.findViewById(R.id.ratingBarOfIndividualCustomer);
		ratingBarOfIndividualCustomer.setRating(Float.parseFloat(obj.getRating()));
		ratingBarOfIndividualCustomer.setEnabled(false);
		t4=(TextView) itemView.findViewById(R.id.nameOfReviewtextView);
		t4.setText(obj.getUserId());
		t5=(TextView)itemView.findViewById(R.id.customerAmbienceTextView);
		t5.setText(obj.getAmbienceRating());
		t6=(TextView)itemView.findViewById(R.id.customerServiceTextView);
		t6.setText(obj.getServiceRating());
		t7=(TextView)itemView.findViewById(R.id.customerFoodTextView);
		t7.setText(obj.getFoodRating());
		t8=(TextView)itemView.findViewById(R.id.customerValueForMoneyTextView);
		t8.setText(obj.getValueForMoneyRating());
		return itemView;
		
	}
	
	
	
	
  }
    
}

