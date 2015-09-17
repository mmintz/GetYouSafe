package cy.ucy.arm.example.MultiCare.MultiLocker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	Timer timer;
	ActivityManager activitymanager;
	String active = "";
	ArrayList<String> app_array;
	ModifyXMLFile xml;
	Boolean killed = false;
String last_packet="";

	public static final String ACTION_START = "cy.ucy.arm.example.MultiCare.MultiLocker.broadcast.action.START";

	public static final String ACTION_STOP = "cy.ucy.arm.example.MultiCare.MultiLocker.broadcast.action.STOP";

	public static final String ACTION_TO_SERVICE = "cy.ucy.arm.example.MultiCare.MultiLocker.broadcast.action.TO_SERVICE";

	public static final String ACTION_FROM_SERVICE = "cy.ucy.arm.example.MultiCare.MultiLocker.broadcast.action.FROM_SERVICE";

	public MyService() {

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {

		String apps_filename = "xmlfile5.xml";
		String pass_filename = "passwdxml.xml";

		// Context context = getApplicationContext();
		// //////////////////////////////////////////////////////////////////////////

		apps_filename = check_xml(apps_filename);
		pass_filename = check_xml(pass_filename);

		// Toast.makeText(this, "The new Service was Created",
		// Toast.LENGTH_LONG).show();
		app_array = new ArrayList<String>();

		String DestinationFile = getApplicationContext().getFilesDir()
				.getPath() + File.separator + "xmlfile5.xml";

		// ArrayList<Apps> application=xml.

		xml = new ModifyXMLFile(getApplicationContext(), DestinationFile);

		ArrayList<Apps> xml_files = xml.Execute();

		for (int i = 0; i < xml_files.size(); i++) {
			app_array.add(xml_files.get(i).getPackName());
		}

		if (!app_array.contains(Utils.contacts_package)) {
			app_array.add(Utils.contacts_package);
			xml.AddToXMLFile(new Apps(Utils.contacts_package));
		}

		if (!app_array.contains(Utils.messages_package)) {
			app_array.add(Utils.messages_package);
			xml.AddToXMLFile(new Apps(Utils.messages_package));
		}

		if (!app_array.contains(Utils.email_package)) {
			app_array.add(Utils.email_package);
			xml.AddToXMLFile(new Apps(Utils.email_package));
		}

		final IntentFilter myFilter = new IntentFilter(ACTION_TO_SERVICE);

		registerReceiver(mReceiver, myFilter);

		int myID = 1234;

		// android.os.Debug.waitForDebugger();

	}

	// checking each xml if is internal storage
	private String check_xml(String filename) {
		// TODO Auto-generated method stub

		// Check if the file already in internal storage

		String DestinationFile = getApplicationContext().getFilesDir()
				.getPath() + File.separator + filename;

		// If it does not exist in internal storage copy from assets folder
		if (!new File(DestinationFile).exists()) {
			try {
				// Toast.makeText(getApplicationContext(),
				// "Creating copying file in Internal Storage",
				// Toast.LENGTH_SHORT).show();

				CopyFromAssetsToStorage(getApplicationContext(), filename,
						DestinationFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		} else {
			// Toast.makeText(getApplicationContext(),
			// "File exists in Internal Storage",
			// Toast.LENGTH_SHORT).show();

		}

		return DestinationFile;
	}

	/**
	 * Called once in the onCreate method. Gets the SourceFile from the assets
	 * folder and copies it to the DestinationFile in the internal storage
	 * 
	 * @param Context
	 *            Context Interface to global information about the application
	 *            environment.
	 * @param String
	 *            SourceFile The SourceFile name to get from the assets folder
	 * @param String
	 *            DestinationFile The DestinationFile name to be created in the
	 *            internal storage
	 * @throws IOException
	 */
	private void CopyFromAssetsToStorage(Context Context, String SourceFile,
			String DestinationFile) throws IOException {
		InputStream IS = Context.getAssets().open(SourceFile);
		OutputStream OS = new FileOutputStream(DestinationFile);
		CopyStream(IS, OS);
		OS.flush();
		OS.close();
		IS.close();
	}

	/**
	 * Copy bytes from an InputStream to an OutputStream
	 * 
	 * @param InputStream
	 *            Input The InputStream Object that refers to the SourceFile
	 * @param OutputStream
	 *            Output The OutputStream Object that refers to the
	 *            DestinationFile
	 * @throws IOException
	 */
	private void CopyStream(InputStream Input, OutputStream Output)
			throws IOException {
		byte[] buffer = new byte[5120];
		int length = Input.read(buffer);
		while (length > 0) {
			Output.write(buffer, 0, length);
			length = Input.read(buffer);
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// For time consuming an long tasks you can launch a new thread here...
		Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();

	}

	void sendToActivity(CharSequence text) {

		Intent intent = new Intent(MyService.ACTION_FROM_SERVICE);
		sendBroadcast(intent);

		// sendBroadcast(intent,
		// "com.sprc.sample.broadcast.Manifest.permission.ALLOW");

	}

	@SuppressLint("NewApi")
	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		final Handler handler = new Handler();
		// int numberOfTasks = 1;

		int myID = 1234;
		// The intent to launch when the user clicks the expanded notification
		Intent intent2 = new Intent(this, StopServiceActivity.class);
		intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent2,
				0);

		// This constructor is deprecated. Use Notification.Builder instead
		Notification notice = new Notification(R.drawable.lock,
				"MultiLocker Started", System.currentTimeMillis());

		// This method is deprecated. Use Notification.Builder instead.
		notice.setLatestEventInfo(this, "MultiLocker Service",
				"Currently running", pendIntent);

		notice.flags |= Notification.FLAG_NO_CLEAR;

		// Toast.makeText(getApplicationContext(), "Start command",
		// Toast.LENGTH_SHORT).show();
		startForeground(1, notice);

		// Intent intent2 = new Intent(this, StopServiceActivity.class);
		// PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent2,
		// 0);

		// build notification
		// the addAction re-use the same intent to keep the example short
		// Notification n = new Notification.Builder(this)
		// .setContentTitle("New mail from " + "test@gmail.com")
		// .setContentText("Subject")
		// .setSmallIcon(R.drawable.lock)
		// .setContentIntent(pIntent)
		// .setAutoCancel(true)
		// .build();
		//
		//
		// NotificationManager notificationManager =
		// (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//
		// n.flags |= Notification.FLAG_AUTO_CANCEL;
		// // notificationManager.notify(0, n);
		// startForeground(1, n);

		final ActivityManager manager = (ActivityManager) MyService.this
				.getSystemService(ACTIVITY_SERVICE);
		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				if (killed)
					timer.cancel();

				// ArrayList<RecentTaskInfo> apps = (ArrayList<RecentTaskInfo>)
				// manager.getRecentTasks(10,
				// ActivityManager.RECENT_WITH_EXCLUDED);

				RunningTaskInfo foregroundTaskInfo = manager.getRunningTasks(1)
						.get(0);

				ComponentName c = foregroundTaskInfo.topActivity;
				c.getPackageName();

				// startActivity(c);

				String foregroundTaskPackageName = foregroundTaskInfo.topActivity
						.getPackageName();
				PackageManager pm = MyService.this.getPackageManager();
				PackageInfo foregroundAppPackageInfo;
				try {
					foregroundAppPackageInfo = pm.getPackageInfo(
							foregroundTaskPackageName, 0);
					String foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo
							.loadLabel(pm).toString();

				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Log.e("lunched ", " " + foregroundTaskPackageName+" active "+active);

				boolean check = false;
				// Log.e("array size()"," "+app_array.size());

				// :TODO HASHMAP
				// if(!foregroundTaskPackageName.equals("com.example.locker2"))
				// manager.killBackgroundProcesses("com.example.locker2");
				for (int i = 0; i < app_array.size(); i++) {

					String packet = app_array.get(i);
					// Log.e("in array"," "+foregroundTaskPackageName);

					// :TODO IF THE PACKAGE CHANGE
					Log.e("noobas","name: " + foregroundTaskPackageName);
					if (foregroundTaskPackageName.equals(packet)) {

						// TODO:asychronously so u can kill the other app
						if (!killed) {
							
							if(active.equals(""))
							{
								last_packet=foregroundTaskPackageName;
							Intent intent = new Intent(getApplicationContext(),
									LockedActivity.class);
							intent.setAction(Intent.ACTION_VIEW);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
							intent.putExtra("app", foregroundTaskPackageName);

							intent.putExtra("pid", 2);

							try {
								startActivity(intent);
							} catch (Exception e) {

							}
							}

						} else {
							timer.cancel();
							stopSelf();
						}

					}

				}

				String test = (String) foregroundTaskPackageName;
				// Log.e("tha kopsw"," "+test);
				if (test.contains(".")) {// Log.e("exei telies ","yea");
				}
				String[] parts = test.split("\\.");
			
				// Log.e("parts "," "+parts.length);

				if(!foregroundTaskPackageName.equals("cy.ucy.arm.example.MultiCare"))
				if (parts.length > 0 && parts[parts.length - 1].equals("launcher")
						|| (!foregroundTaskPackageName.equals(active))) {

					if (!active.equals("")) {
						app_array.add(active);
						active = "";
					}

				}

			}// end of run

		}, 0, 500);

		return Service.START_STICKY;
		// return startid;

	}

	private boolean check_unlocked(String foregroundTaskPackageName) {
		// TODO Auto-generated method stub

		if (active.equals(foregroundTaskPackageName)) {
			return true;
		}
		return false;
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String pack_name = intent.getCharSequenceExtra("data").toString();
			String cases = intent.getCharSequenceExtra("case").toString();

			if (cases.equals("main")) {

				if (intent.getCharSequenceExtra("check").toString()
						.equals("remove")) {
					// hashmap !
					for (int i = 0; i < app_array.size(); i++) {
						if (app_array.get(i).equals(pack_name)) {
							app_array.remove(i);
							break;

						}
					}

				} else if (intent.getCharSequenceExtra("check").toString()
						.equals("check_run")) {
					Log.e("got it ! ", "got it");
					Intent intent2 = new Intent(MyService.ACTION_FROM_SERVICE);
					intent2.putExtra("result", "yes");
					sendBroadcast(intent2);

				} else {
					// Toast.makeText(context,"Adding "+pack_name,Toast.LENGTH_SHORT).show();
					app_array.add(pack_name);

				}

			}

			// message sent from locked activity
			else if (cases.equals("locked")) {

				active = pack_name;
				// remove it from array temporarilly
				for (int i = 0; i < app_array.size(); i++) {
					if (app_array.get(i).equals(active)) {
						app_array.remove(i);
						break;

					}
				}

			} else if (cases.equals("stop")) {
				// Log.e("ime dame ","yeah");
				stopForeground(true);

				killed = true;
				stopSelf();

			}

			// unlocked=intent.getCharSequenceExtra("data").toString();

			// sendToActivity("Who's there? You wrote: " +

			// intent.getCharSequenceExtra("data"));

		}

	};

	@Override
	public void onDestroy() {

		super.onDestroy();

		unregisterReceiver(mReceiver);
		/*
		 * Intent in = new Intent(); in.setAction("YouWillNeverKillMe");
		 * sendBroadcast(in);
		 */
		// Log.e("destroeye", "onDestroy()...");
	}

}