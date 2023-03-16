package com.be.b.beproject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;

public class RestaurantHomeActivity extends Activity implements AnimationListener{
	
	ImageView RestaurantFoodOrder,RestaurantTableOrder,RestaurantEditMenu;
	Animation anim1;
	Bitmap myLogo;
	Drawable myDrawable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_home);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().hide();
		anim1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
		anim1.setAnimationListener(this);
		
		RestaurantFoodOrder=(ImageView) findViewById(R.id.restaurantFoodOrder);
		myDrawable = getResources().getDrawable(R.drawable.customer_food_order);
		 myLogo = ((BitmapDrawable) myDrawable).getBitmap();
		 myLogo=getRoundedCornerBitmap(myLogo,35);
		 RestaurantFoodOrder.setImageBitmap(myLogo);
		RestaurantFoodOrder.startAnimation(anim1);
		RestaurantFoodOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(RestaurantHomeActivity.this,RestaurantNotificationFragment.class);
				startActivity(i);
				
			}
		});
		
		RestaurantTableOrder=(ImageView) findViewById(R.id.restaurantTableOrder);
		myDrawable = getResources().getDrawable(R.drawable.customer_table_order1);
		 myLogo = ((BitmapDrawable) myDrawable).getBitmap();
		 myLogo=getRoundedCornerBitmap(myLogo,35);
		 RestaurantTableOrder.setImageBitmap(myLogo);
		RestaurantTableOrder.startAnimation(anim1);
		RestaurantTableOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(RestaurantHomeActivity.this,RestaurantTableBookedNotificationFragment.class);
				startActivity(i);
			}
		});
		RestaurantEditMenu=(ImageView) findViewById(R.id.restaurantEditMenu);
		myDrawable = getResources().getDrawable(R.drawable.restaurant_menu_card);
		 myLogo = ((BitmapDrawable) myDrawable).getBitmap();
		 myLogo=getRoundedCornerBitmap(myLogo,35);
		 RestaurantEditMenu.setImageBitmap(myLogo);
		RestaurantEditMenu.startAnimation(anim1);
		RestaurantEditMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(RestaurantHomeActivity.this,RestaurantEditMenuFragment.class);
				startActivity(i);
			}
		});
	}



	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
	
	

}