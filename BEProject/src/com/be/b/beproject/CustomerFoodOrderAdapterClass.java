package com.be.b.beproject;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomerFoodOrderAdapterClass extends FragmentPagerAdapter {
	static int saawan=0;
	public CustomerFoodOrderAdapterClass(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int index) {
	
		
		for(int i=0;i<CustomerFoodOrderCategoryProcessClass.CategoryName.size();i++){
			if(i==index){
				saawan=i;
				return new CustomerFoodOrderPlacementFragment();
			}
		
		}
		
		
		
		
	/*	switch (index)
		{
		case 0:		//Salad
			saawan=0;
			System.out.println("third saawan"+index+"saawan value is"+saawan);
			
			return new VegFrag();
		case 1:
			//Salad
			saawan=1;
			System.out.println("third saawan"+index+"saawan value is"+saawan);
			return new VegFrag();
		case 2:
			//Salad
			saawan=2;
			System.out.println("third saawan"+index+"saawan value is"+saawan);
			return new VegFrag();
		case 3:
			//Salad
			saawan=3;
			System.out.println("third saawan"+index+"saawan value is"+saawan);
			return new VegFrag();
		case 4:
			//Salad
			saawan=4;
			System.out.println("third saawan"+index+"saawan value is"+saawan);
			return new VegFrag();
		case 5:
			//Salad
			saawan=5;
			System.out.println("third saawan"+index+"saawan value is"+saawan);
			return new VegFrag();
		case 6:
			//Salad
			saawan=6;
			System.out.println("third saawan"+index+"saawan value is"+saawan);
			return new VegFrag();
		case 7:
			//Salad
			saawan=7;
			System.out.println("third saawan"+index+"saawan value is"+saawan);
			return new VegFrag();
		case 8:
			//Salad
			saawan=8;
			System.out.println("third saawan"+index+"saawan value is"+saawan);
			return new VegFrag();
		
		}*/
		return null;
	}

	@Override
	public int getCount() 
	{
		return CustomerFoodOrderCategoryProcessClass.CategoryName.size();
	}
	
	
}
