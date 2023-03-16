package com.be.b.beproject;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMNotificationIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString()," "," "," ");
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString()," "," "," ");
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {

				for (int i = 0; i < 3; i++) {
					Log.i(TAG,
							"Working... " + (i + 1) + "/5 @ "
									+ SystemClock.elapsedRealtime());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}

				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

				sendNotification(""+extras.getString("message"),""+extras.getString("title"),""+extras.getString("subtitle"),""+extras.getString("ordertype"));
				Log.i(TAG, "Received: " + extras.toString());
				Log.i(TAG, "ID  : " + extras.getString("title"));
				
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String message,String title,String subtitle,String orderType) {
		Log.d(TAG, "Preparing to send notification...: " + message);
		mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent contentIntent=null;
		if(orderType.equals("food")){
		
				contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, RestaurantNotificationFragment.class), 0);
		}else if(orderType.equals("table")){
    		contentIntent = PendingIntent.getActivity(this, 0,
			new Intent(this, RestaurantTableBookedNotificationFragment.class), 0);
	
		}
		else if(orderType.equals("tableStatus")){
			contentIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, CustomerAllTableOrdersHistory.class), 0);
			
		}
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(title)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(subtitle))
				.setContentText(subtitle);
		mBuilder.setDefaults(Notification.DEFAULT_ALL);
		
		mBuilder.setAutoCancel(true);
		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		Log.d(TAG, "Notification sent successfully.");
	}
}