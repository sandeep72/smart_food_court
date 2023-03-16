package com.be.b.beproject;

import java.util.ArrayList;
import java.util.List;









import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class CustomerFoodOrderTabPage extends FragmentActivity implements ActionBar.TabListener{
	
	private ViewPager viewPager;
	private CustomerFoodOrderAdapterClass mAdapter;
	private ActionBar actionBar;
	private ProgressDialog dialog;
	public static int saawankumar=1;
	public static String RestroName;
	static Button notifCount;
	static int mNotifCount = 0,loginCounter;    
	static List<String> SelectedItemName,SelectedItemQuantity,SelectedItemPrice;
	View count;
	
	
	
	
	
	
	ArrayList<String> foodTypes=CustomerFoodOrderCategoryProcessClass.CategoryName;
	String[] tabs = foodTypes.toArray(new String[foodTypes.size()]);
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customerfoodordertabpage);
		
		SelectedItemName=new ArrayList<String>();
		SelectedItemPrice=new ArrayList<String>();
		SelectedItemQuantity=new ArrayList<String>();
		
		
		actionBar = getActionBar();
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		
		mAdapter = new CustomerFoodOrderAdapterClass(getSupportFragmentManager());
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		viewPager.setAdapter(mAdapter);
	
		
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
			
			System.out.println("for loops tabs name"+tab_name);
			
		}
		
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
				saawankumar=position+1;
				System.out.println("position is"+position);
				System.out.println("position +1 saawankumar"+saawankumar);
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.customeraddtocart, menu);

		
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.apps:
			
			if(SelectedItemName.size()!=0){
				String Name[]=SelectedItemName.toArray(new String[SelectedItemName.size()]);


				String Price[]=SelectedItemPrice.toArray(new String[SelectedItemPrice.size()]);
				String Quantity[]=SelectedItemQuantity.toArray(new String[SelectedItemQuantity.size()]);
				for(int i=0;i<Name.length;i++){
					Log.i("name",Name[i]+" "+Price[i]+"  "+Quantity[i]);
					}
				Bundle b=new Bundle();
				b.putStringArray("selectedItemNames", Name);
				b.putStringArray("selectedItemPrices", Price);
				b.putStringArray("selectedItemQuantity", Quantity);
			
			
			Intent bill=new Intent(getApplicationContext(),CustomerOrderConfirmation.class);
			bill.putExtras(b);
			startActivity(bill);
			return true;
			}else{
				
					
			}
			default:
			return super.onOptionsItemSelected(item);
		}
	}


}