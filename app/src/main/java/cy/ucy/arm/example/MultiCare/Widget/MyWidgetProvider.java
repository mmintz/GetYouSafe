package cy.ucy.arm.example.MultiCare.Widget;

import java.util.Random;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.Maps.DrunkMapActivity;
import cy.ucy.arm.example.MultiCare.Maps.MapOptions;
import cy.ucy.arm.example.MultiCare.MultiLocker.MainActivity;
import cy.ucy.arm.example.MultiCare.UIclasses.DrunkLoadFavoriteContactsXML;
import cy.ucy.arm.example.MultiCare.utils.Utils;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

	private static final String ACTION_CLICK = "ACTION_CLICK";
	public static final String CUSTOM_INTENT = "BROADCAST_TEST.PING";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				MyWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			// create some random data

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);
			// Set the text
			
			// Register an onClickListener
			Intent startApp = new Intent(context, cy.ucy.arm.example.MultiCare.UIclasses.MainActivity.class);
			Intent intentEnableLockingService= new Intent(context, ActivityDecideDrunk.class);
			Intent intentTakeMeHomeIntent = new Intent(context, DrunkMapActivity.class);
			Intent intentFeeling = new Intent(CUSTOM_INTENT);
			Intent intentLoadFriendList= new Intent(context, DrunkLoadFavoriteContactsXML.class);
			Intent intentSetDrunk= new Intent(context, WidgetDrunkMode.class);

			//Intent intentFeeling = new Intent(context, ActivityDialogFeelingMode.class);
			
			
			// intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			// intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
			// appWidgetIds);

//			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			 PendingIntent pendingStartActivity = PendingIntent.getActivity(context, 0, startApp, 0);
			 PendingIntent pendingIntentLockingService = PendingIntent.getActivity(context, 0, intentEnableLockingService, 0);
			 PendingIntent pendingIntentTakeMeHomeIntent = PendingIntent.getActivity(context, 0, intentTakeMeHomeIntent, 0);
			 PendingIntent pendingIntentFeeling = PendingIntent.getBroadcast(context, 2222222, intentFeeling, 0);
			 PendingIntent pendingIntentLoadFriendList = PendingIntent.getActivity(context, 0, intentLoadFriendList, 0);
			 PendingIntent pendingIntentSetDrunk = PendingIntent.getActivity(context, 0, intentSetDrunk, 0);

			// PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intentFeeling, 0);
			remoteViews.setOnClickPendingIntent(R.id.textViewMuticareLabel, pendingStartActivity);
			remoteViews.setOnClickPendingIntent(R.id.imageEnableLockingService, pendingIntentLockingService);
			remoteViews.setOnClickPendingIntent(R.id.imageQuotes, pendingIntentFeeling);
			remoteViews.setOnClickPendingIntent(R.id.imageTakeMeHome, pendingIntentTakeMeHomeIntent);
			remoteViews.setOnClickPendingIntent(R.id.imageFriendList, pendingIntentLoadFriendList);
			remoteViews.setOnClickPendingIntent(R.id.imageDrunkMode, pendingIntentSetDrunk);

			
			
			
			
			
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
}