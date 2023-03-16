package com.be.b.beproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TempHome extends Activity {
	
	private SharedPreferences sharedPreferences;
	
	
	Button restaurantButton;
	Button customerButton;
    Button restaurantHomeButton;
    Button customerHomeButton;
    
    SQLiteDatabase customerOrderTable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_temp_home);
		//getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Add Review</font>"));
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().hide();
		
		sharedPreferences=getPreferences(Context.MODE_PRIVATE);
		if(sharedPreferences.getBoolean("first launch", true)){
			sharedPreferences.edit().putBoolean("first launch", false).commit();
			//Toast.makeText(TempHome.this, "inside if hahahhahahhahhahahhahaha", Toast.LENGTH_SHORT).show();
			try{
			customerOrderTable= openOrCreateDatabase("BEProjectTables", SQLiteDatabase.CREATE_IF_NECESSARY, null);
			String createQuery="CREATE TABLE placedorderstable (orderId integer primary key AUTOINCREMENT,orderName text,orderPrice text);";
			customerOrderTable.execSQL(createQuery);
			Toast.makeText(TempHome.this, "table creation successful", Toast.LENGTH_SHORT).show();
			}
			catch(Exception e){
				Toast.makeText(TempHome.this, "table creation error", Toast.LENGTH_SHORT).show();

			}
		}
		
		
		restaurantButton= (Button) findViewById(R.id.buttonRestaurantRegister);
		restaurantButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(TempHome.this, RestActivity.class);
				startActivity(i);
			}
		});
		
		
		
		restaurantHomeButton= (Button) findViewById(R.id.buttonRestaurantHome);
		restaurantHomeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(TempHome.this, RestaurantLogin.class);
				startActivity(i);
			}
		});
		
		
		
		customerButton= (Button) findViewById(R.id.buttonCustomerRegister);
		customerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(TempHome.this, CustActivity.class);
				startActivity(i);
			}
		});
		
		customerHomeButton= (Button) findViewById(R.id.buttonCustomerHome);
		customerHomeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(TempHome.this, CustomerLogin.class);
				startActivity(i);
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}
}
