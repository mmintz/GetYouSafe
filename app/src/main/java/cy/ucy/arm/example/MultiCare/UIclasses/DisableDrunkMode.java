package cy.ucy.arm.example.MultiCare.UIclasses;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.Dialogs.TimePickerFragmentEnd;
import cy.ucy.arm.example.MultiCare.Dialogs.TimePickerFragmentStart;
import cy.ucy.arm.example.MultiCare.MultiLocker.MyService;
import cy.ucy.arm.example.MultiCare.MultiLocker.StopServiceActivity;
import cy.ucy.arm.example.MultiCare.Widget.NotifyDrunkUser;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.KeyguardManager.KeyguardLock;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.widget.RemoteViews;

public class DisableDrunkMode extends Activity {

	private static String taString = "DisableDrunkMode";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		//Log.e(taString, "empika stin  disable drunk mode");
		
		if (Utils.getAnswer(getFilesDir())) {
			
			Intent intent2 = new Intent(this, DrunkModeOFF.class);
         	intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
	        PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent2, 0);
	
	        //This constructor is deprecated. Use Notification.Builder instead
	        Notification notice = new Notification(R.drawable.ic_launcher, "Drunk Mode is OFF", System.currentTimeMillis());
	
	        //This method is deprecated. Use Notification.Builder instead.
	        notice.setLatestEventInfo(this, "Drunk Mode is OFF", "Drunk Mode was disabled successfully", pendIntent);
	
	        notice.flags |= Notification.FLAG_NO_CLEAR;
	        notice.flags |= Notification.FLAG_AUTO_CANCEL;
			notice.vibrate = new long[]{100, 200, 100, 500}; 
	      
			Utils.saveToFileDrunk(this, this.getFilesDir(), Utils.NOT_DRUNK);
			Log.e(taString, "save");

			final Intent intent = new Intent(MyService.ACTION_TO_SERVICE);

			MediaPlayer mp;
			mp = MediaPlayer.create(DisableDrunkMode.this,
					R.raw.beep);
			mp.start();
			
			intent.putExtra("case", "stop");
			intent.putExtra("data", "");
			intent.putExtra("check", " ");
			AlarmManager alarmManager1 = (AlarmManager) this
					.getSystemService(getApplicationContext().ALARM_SERVICE);
			alarmManager1.cancel(TimePickerFragmentStart.on);
			alarmManager1.cancel(TimePickerFragmentEnd.off);
	
			sendBroadcast(intent);
			
			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify(0, notice);
			
			/*AlertDialog.Builder builder = new AlertDialog.Builder(
					DisableDrunkMode.this);
			builder.setTitle("Drunk Mode is OFF")
					.setMessage(
							"Drunk Mode was disabled successfully. You now have full phone's functionalities. We hope that we have helped you")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									finish();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();*/
			finish();
		}
		else
			finish();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
