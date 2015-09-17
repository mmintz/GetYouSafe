package cy.ucy.arm.example.MultiCare.MultiLocker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Calendar;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.UIclasses.ActivityDrunkNoAccess;
import cy.ucy.arm.example.MultiCare.actionbar.adapter.TitleNavigationAdapter;
import cy.ucy.arm.example.MultiCare.actionbar.model.SpinnerNavItem;
import cy.ucy.arm.example.MultiCare.utils.Utils;

import java.util.List;

import javax.xml.transform.TransformerException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.opengl.Visibility;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import android.app.ActionBar;

import android.app.SearchManager;

import android.widget.SearchView;

public class MainActivity extends Activity implements
		ActionBar.OnNavigationListener {

	 private AdView adView;

	  /** Your ad unit id. Replace with your actual ad unit id. */
	  private static final String AD_UNIT_ID = "ca-app-pub-9543282055174098/3822189367";
	ListView listView;
	ApplicationAdapter adapter;
	ArrayList<Apps> lists;
	ArrayList<Apps> saved_lists;
	ArrayList<Apps> locked_lists;
	Context context;
	ModifyXMLFile Applications;
	ModifyXMLFile Pass;
	TextView hint;
	boolean service_run=false;
	
	boolean button_pressed=false;
	
	fetchPackages fetch;
	int action_last_pos = 0;

	// action bar
	private ActionBar actionBar;

	// Title navigation Spinner data
	private ArrayList<SpinnerNavItem> navSpinner;

	// Navigation adapter
	private TitleNavigationAdapter adapter2;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main_2);
		
		 adView = (AdView)this.findViewById(R.id.adView);
		   
		    AdRequest adRequest = new AdRequest.Builder()
		        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		        .addTestDevice("ED120B0F8BBD7D050009BECC4C72C967")
		        .build();

		    // Start loading the ad in the background.
		    adView.loadAd(adRequest);
		
		//sendToService("yes", "check_run");
		context = this.getApplicationContext();

		listView = (ListView) findViewById(R.id.listView1);
		saved_lists = new ArrayList<Apps>();
		locked_lists = new ArrayList<Apps>();
		// listView.setBackgroundColor(Color.WHITE);
		hint=(TextView)findViewById(R.id.texthint);
		actionBar = getActionBar();

		// Hide the action bar title
		actionBar.setDisplayShowTitleEnabled(true);

		// Enabling Spinner dropdown navigation
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Spinner title navigation data
		navSpinner = new ArrayList<SpinnerNavItem>();
		navSpinner.add(new SpinnerNavItem("ALL Apps", R.drawable.lock));
		navSpinner.add(new SpinnerNavItem("Locked Apps", R.drawable.lock));
		// navSpinner.add(new SpinnerNavItem("UnLocked Apps", R.drawable.lock));

		// navSpinner.add(new SpinnerNavItem("Checkins",
		// R.drawable.ic_checkin));
		// navSpinner.add(new SpinnerNavItem("Latitude",
		// R.drawable.ic_latitude));

		// title drop down adapter
		adapter2 = new TitleNavigationAdapter(getApplicationContext(),
				navSpinner);

		// assigning the spinner navigation
		actionBar.setListNavigationCallbacks(adapter2, this);
		// :TODO hashmap
		lists = new ArrayList<Apps>();

		// receiver handlers
		final IntentFilter myFilter = new

		IntentFilter(MyService.ACTION_FROM_SERVICE);

		registerReceiver(mReceiver, myFilter);

		
		// fetchContactsDecide = true;

		final PackageManager pm = getPackageManager();
		// get a list of installed apps.
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);

		String app_info = "";

		String apps_filename = "xmlfile5.xml";
		String pass_filename = "passwdxml.xml";
		
		
		
		// Context context = getApplicationContext();
		// //////////////////////////////////////////////////////////////////////////

		apps_filename = check_xml(apps_filename);
		pass_filename = check_xml(pass_filename);
		//sendToService("yes", "check_run");
		try {
			Applications = new ModifyXMLFile(context, apps_filename);
			Pass = new ModifyXMLFile(context, pass_filename);

		} catch (Exception e) {

		}

		// Toast.makeText(context,"Your pass is "
		// +Pass.readPass(),Toast.LENGTH_SHORT).show();

		// Toast.makeText(context,"Your pass is "
		// +Pass.readPass(),Toast.LENGTH_SHORT).show();

		adapter = new ApplicationAdapter(this, R.layout.item, lists,
				Applications, this);

		listView.setAdapter(adapter);
		fetch = new fetchPackages();
		fetch.setMainActivity(MainActivity.this);
		fetch.execute();
		
		if(!checkRunningService())
		{
			listView.setVisibility(View.GONE);
			Log.e("checking","service false");
		}
		else{
			Log.e("checking","service true");
			button_pressed=true;
			hint.setVisibility(View.GONE);
			
		}
		// Log.e("list size = "," "+lists.size());

	}

	// checking each xml if is internal storage
	private String check_xml(String filename) {
		// TODO Auto-generated method stub

		// Check if the file already in internal storage
		String DestinationFile = context.getFilesDir().getPath()
				+ File.separator + filename;

		// If it does not exist in internal storage copy from assets folder
		if (!new File(DestinationFile).exists()) {
			try {
				// Toast.makeText(context,
				// "Creating copying file in Internal Storage",
				// Toast.LENGTH_SHORT).show();

				CopyFromAssetsToStorage(context, filename, DestinationFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		} else {
			// Toast.makeText(context, "File exists in Internal Storage",
			// Toast.LENGTH_SHORT).show();

		}

		return DestinationFile;
	}

	public void fetchpackages() {

		// //////////////////////////////////////////////////////////////////////////

		ArrayList<Apps> locked_already = new ArrayList<Apps>();

		locked_already = Applications.Execute();

		// Log.e("size ","locked "+locked_already.size());
		// Toast.makeText(context,"Locked apps "+locked_already.size()
		// ,Toast.LENGTH_LONG).show();
		// Toast.makeText(context,"Locked apps "+locked_already.size()
		// ,Toast.LENGTH_LONG).show();
		// Toast.makeText(context,"Locked apps "+locked_already.size()
		// ,Toast.LENGTH_LONG).show();

		ArrayList<InstalledAppData> array = new ArrayList<InstalledAppData>();

		array = importInstalledAppsData();

		// Toast.makeText(this,"installed "+ array.size(),
		// Toast.LENGTH_LONG).show();
		boolean locked = false;

		for (int i = 0; i < array.size(); i++) {

			Apps newapp;
			locked = false;
			int pos_lock = -1;
			for (int j = 0; j < locked_already.size(); j++) { // Log.e("pack",
																// " "+array.get(i).getPackageName());
				if (array.get(i).getPackageName()
						.equals(locked_already.get(j).getPackName())) {
					pos_lock = j;
					locked = true;
				}

				// Toast.makeText(context,array.get(i).getAppName(),
				// Toast.LENGTH_SHORT).show();

			}

			if (locked == true) {
				newapp = new Apps(array.get(i).getAppName(), "locked", array
						.get(i).getIcon(), R.drawable.lock, array.get(i)
						.getPackageName());
				newapp.setId(locked_already.get(pos_lock).getId());
			} else {
				newapp = new Apps(array.get(i).getAppName(), "unlocked", array
						.get(i).getIcon(), R.drawable.lock, array.get(i)
						.getPackageName());
				Integer ids = lists.size() + 1;
				newapp.setId(ids.toString());
			}
			lists.add(newapp);
		}

	}

	// dialog.dismiss();

	// Start the service
	public void startNewService() {
		// Toast.makeText(context, "calling", Toast.LENGTH_SHORT).show();
		startService(new Intent(this, MyService.class));
	}

	// Stop the service
	public void stopNewService() {

		stopService(new Intent(this, MyService.class));
	}

	public ArrayList<InstalledAppData> importInstalledAppsData() {
		ArrayList<InstalledAppData> appList = new ArrayList<InstalledAppData>();
		PackageManager pkgManager = getApplicationContext().getPackageManager();
		List<ApplicationInfo> packages = getInstalledApplicationsList(pkgManager);
		// String deviceId=Util.getDeviceId(getApplicationContext());
		String deviceId = "kwstis";
		// for (ApplicationInfo packageInfo : packages)
		String packageName = "s";// packageInfo.packageName;
		String appName = "a";
		String appFile = "s"; // packageInfo.sourceDir;
		// long installTime = new File(appFile).lastModified();
		long installTime = 100000;
		String status = "s";

		PackageManager pm = this.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		// ArrayList results = new ArrayList();
		List<ResolveInfo> list = pm.queryIntentActivities(intent,
				PackageManager.PERMISSION_GRANTED);
		for (ResolveInfo rInfo : list) {
			String str = rInfo.activityInfo.applicationInfo.loadLabel(pm)
					.toString() + "\n";

			appName = rInfo.activityInfo.applicationInfo.loadLabel(pm)
					.toString();
			Drawable icon = rInfo.activityInfo.applicationInfo.loadIcon(pm);

			packageName = rInfo.activityInfo.packageName;
			InstalledAppData data = new InstalledAppData(appName, status, icon,
					packageName);

			if (!packageName.equals("cy.ucy.arm.example.MultiCare"))
				appList.add(data);

			// Log.w("Installed Applications",
			// rInfo.activityInfo.applicationInfo.loadLabel(pm).toString());

		}

		return appList;
	}

	public List<ApplicationInfo> getInstalledApplicationsList(PackageManager pm) {
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		return packages;
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			// Log.e("epatithika","aaaaaaaaaa");
			Intent backtoHome = new Intent(Intent.ACTION_MAIN);
			backtoHome.addCategory(Intent.CATEGORY_HOME);
			backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(backtoHome);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		int itemId = item.getItemId();
		if (itemId == R.id.shut_down ) {
			if(button_pressed)
			{
				
				//startActivity(new Intent(this.context,StopServiceActivity.class));
				Intent i = new Intent(this, StopServiceMultiLocker.class);
				startActivityForResult(i, 1);
				
				//button_pressed=false;
				Log.e("if ","button true");
			}
			else{
				//service_run=false;
			Log.e("else ","button false");
			//sendToService("yes", "check_run");
			
			if(!checkRunningService())
			{startNewService();
			Toast.makeText(context, "Starting Service ", Toast.LENGTH_SHORT)
			.show();
			//button_pressed=true;
			button_pressed=true;
			hint.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			}
			
			
			
			

			adapter.notifyDataSetChanged();
			
			}
			return true;
		} else if (itemId == R.id.action_settings) {
			startActivity(new Intent(this.context, ChangePassWordActivity.class));
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}

		/*
		 * switch (item.getItemId()) { case R.id.shut_down:
		 * Toast.makeText(context,"Starting Service ",
		 * Toast.LENGTH_SHORT).show(); startNewService(); return true; case
		 * R.id.action_settings: startActivity(new
		 * Intent(this.context,ChangePassWordActivity.class)); default: return
		 * super.onOptionsItemSelected(item); }
		 */

	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String res=intent.getCharSequenceExtra("result").toString();
           if(res.equals("yes")){
        	//   service_run=true;
        //	Log.e("got it back","back");
//        	fetch = new fetchPackages();
//			fetch.setMainActivity(MainActivity.this);
//			fetch.execute();
        //	button_pressed=true;
        //	listView.setVisibility(View.VISIBLE);
        //	hint.setVisibility(View.GONE);
           }
           else{
        	   stopNewService();
   			finish();
           }
			
		}

	};

	void sendToService(CharSequence text, CharSequence check) {

		// Log.d("BroadcastActivity", "Sending message to service: " + text);

		final Intent intent = new Intent(MyService.ACTION_TO_SERVICE);
		intent.putExtra("case", "main");
		intent.putExtra("data", text);
		intent.putExtra("check", check);

		sendBroadcast(intent);

	}

	@Override
	public void onDestroy() {

		// stopService(new Intent(MyService.ACTION_STOP));

		unregisterReceiver(mReceiver);

		super.onDestroy();

	}

	public ApplicationAdapter getAdapter() {
		return adapter;
	}

	/**
	 * Actionbar navigation item select listener
	 * */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// Action to be taken after selecting a spinner item

		if (action_last_pos == itemPosition)
			return false;

		// locked apps
		if (itemPosition == 1 && action_last_pos == 0) {
			// argi lisi

			action_last_pos = 1;
			for (int i = 0; i < lists.size(); i++) {
				if (lists.get(i).getLocked().equals("unlocked")) {
					saved_lists.add(lists.get(i));
					// Log.e("unlocked"," "+lists.get(i).getPackName());
					// lists.remove(i);
				} else {
					locked_lists.add(lists.get(i));
				}

			}
			lists.clear();

			for (int j = 0; j < locked_lists.size(); j++) {
				lists.add(locked_lists.get(j));
			}

			adapter.notifyDataSetChanged();

		} else if (itemPosition == 0 && action_last_pos == 1) {
			action_last_pos = 0;
			for (int i = 0; i < saved_lists.size(); i++) {
				lists.add(saved_lists.get(i));

			}
			saved_lists.clear();
			locked_lists.clear();
			adapter.notifyDataSetChanged();
		} else if (itemPosition == 1 && action_last_pos == 2) {
			action_last_pos = 0;
			for (int i = 0; i < saved_lists.size(); i++) {
				lists.add(saved_lists.get(i));

			}
			saved_lists.clear();
			locked_lists.clear();
			adapter.notifyDataSetChanged();
		}

		else if (itemPosition == 0 && action_last_pos == 2) {
			action_last_pos = 0;
			// saved_lists.clear();
			for (int i = 0; i < locked_lists.size(); i++) {
				lists.add(locked_lists.get(i));

			}
			saved_lists.clear();
			locked_lists.clear();
			adapter.notifyDataSetChanged();
		} else if (itemPosition == 2 && action_last_pos == 1) {
			// exeis ti lista

			// lists.clear();
			// saved_lists.clear();
			for (int j = 0; j < lists.size(); j++) {
				if (lists.get(j).getLocked().equals("unlocked")) {
					saved_lists.add(lists.get(j));
					// Log.e("unlocked"," "+lists.get(j).getPackName());
					// lists.remove(i);
				}

			}
			lists.clear();
			for (int i = 0; i < saved_lists.size(); i++) {
				lists.add(saved_lists.get(i));
			}

			adapter.notifyDataSetChanged();
			// saved_lists.clear();

		} else if (itemPosition == 2 && action_last_pos == 0) {
			// na piaseis osa unlocked exei tr (meta to locked ) + ta dika sou
			locked_lists.clear();
			for (int i = 0; i < lists.size(); i++) {

				if (lists.get(i).getLocked().equals("unlocked")) {
					saved_lists.add(lists.get(i));
				} else {
					locked_lists.add(lists.get(i));
				}

			}

			lists.clear();
			for (int i = 0; i < saved_lists.size(); i++) {
				lists.add(saved_lists.get(i));
			}
			adapter.notifyDataSetChanged();

		}

		return false;
	}

	protected void onPause() {
		super.onPause();

	}

	protected void onStop() {
		super.onStop();

	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            String result=data.getStringExtra("result");
	            if(result.equals("true"))
	            {	button_pressed=false;
	            listView.setVisibility(View.GONE);
				hint.setVisibility(View.VISIBLE);
	            }
	            else{
	            	button_pressed=true;
	            }
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}//onActivityResult

	public boolean checkRunningService() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (RunningServiceInfo myService : activityManager
                .getRunningServices(Integer.MAX_VALUE)) {
            if ("cy.ucy.arm.example.MultiCare.MultiLocker.MyService".equals(myService.service
                    .getClassName())) {
                return true;
            }
        }
        return false;
    }
}
