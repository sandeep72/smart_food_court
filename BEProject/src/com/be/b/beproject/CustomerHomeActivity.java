package com.be.b.beproject;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class CustomerHomeActivity extends Activity implements AnimationListener{
	
	ImageView CustomerFoodOrder,CustomerTableOrder;
	Animation anim1,anim2;
	Bitmap myLogo;
	Drawable myDrawable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_activity_home);
		//getActionBar().setTitle(Html.fromHtml("<font color='#ffe779'>Add Review</font>"));
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().hide();
		
		anim1= AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_in);
		
	//	anim2= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.flip);
		
		
		// set animation listener
		anim1.setAnimationListener(this);
	//	anim2.setAnimationListener(this);
		CustomerTableBookingFragment.TableWithFoodFlag=0;
		
		CustomerFoodOrder=(ImageView) findViewById(R.id.customerFoodOrder);
		CustomerFoodOrder.startAnimation(anim1);
		 myDrawable = getResources().getDrawable(R.drawable.customer_food_order);
		 myLogo = ((BitmapDrawable) myDrawable).getBitmap();
		 myLogo=getRoundedCornerBitmap(myLogo,35);
		CustomerFoodOrder.setImageBitmap(myLogo);
		
		
		CustomerFoodOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(CustomerHomeActivity.this,CustomerFoodOrderCategoryProcessClass.class);
				startActivity(i);
				
				
			}
		});
		CustomerTableOrder=(ImageView) findViewById(R.id.customerTableOrder);
		
		
		 myDrawable = getResources().getDrawable(R.drawable.customer_table_order1);
		 myLogo = ((BitmapDrawable) myDrawable).getBitmap();
		
		myLogo=getRoundedCornerBitmap(myLogo,35);
		CustomerTableOrder.setImageBitmap(myLogo);
		CustomerTableOrder.startAnimation(anim1);
		
		CustomerTableOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(CustomerHomeActivity.this,CustomerTableBookingFragment.class);
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
