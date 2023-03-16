package com.be.b.beproject;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class RestaurantAdapterclass extends FragmentPagerAdapter {
	static int saawan=0;
	public RestaurantAdapterclass(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int index) {
	
		
		for(int i=0;i<RestaurantEditMenuFragment.CategoryName.size();i++){
			if(i==index){
				saawan=i;
				return new RestaurantViewAndEditMenuFragment();
			}
		
		}
		
		
		
		
	
		return null;
	}

	@Override
	public int getCount() 
	{
		return RestaurantEditMenuFragment.CategoryName.size();
	}
	
	
}
