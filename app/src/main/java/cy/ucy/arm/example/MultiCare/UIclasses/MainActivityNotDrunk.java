package cy.ucy.arm.example.MultiCare.UIclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.FeelingMode.ActivityDialogFeelingMode;
import cy.ucy.arm.example.MultiCare.Maps.DrunkMapActivity;
import cy.ucy.arm.example.MultiCare.Maps.MapActivity;
import cy.ucy.arm.example.MultiCare.Maps.MapActivityRouteAddresses;
import cy.ucy.arm.example.MultiCare.Maps.SetHomeLocationMapActivity;
import cy.ucy.arm.example.MultiCare.MultiLocker.ChangePassWordActivityFirstTime;
import cy.ucy.arm.example.MultiCare.MultiLocker.MyService;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
public class MainActivityNotDrunk extends Activity {

	public static final String CUSTOM_INTENT = "BROADCAST_TEST.PING";

	
	 private AdView adView;

	  /** Your ad unit id. Replace with your actual ad unit id. */
	  private static final String AD_UNIT_ID = "ca-app-pub-9543282055174098/3822189367";
	  
	
	// static final String[] MOBILE_OS = new String[] { "Android", "iOS",
	// "Windows", "Blackberry" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_not_drunk);
		
		//adds
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		double ratio = ((float) (width))/300.0;
		int height = (int)(ratio*50);
		
		 // Create an ad.
		 adView = (AdView)this.findViewById(R.id.adView);
	    //adView = new AdView(this);
	    //adView.setAdSize(AdSize.BANNER);
	    //adView.setAdUnitId(AD_UNIT_ID);
	    //adView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT,height));
	  
	    // Add the AdView to the view hierarchy. The view will have no size until the ad is loaded.
	    // This code assumes you have a LinearLayout with attribute android:id="@+id/linear_layout"
	    // in your activity_main.xml.
	    //LinearLayout layout = (LinearLayout) findViewById(R.id.layoutadd);
	    //layout.addView(adView);

	    // Create an ad request. Check logcat output for the hashed device ID to get test ads on a
	    // physical device.
	    AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .addTestDevice("ED120B0F8BBD7D050009BECC4C72C967")
	        .build();

	    // Start loading the ad in the background.
	    adView.loadAd(adRequest);
		
		
		
		///
		
		// prepei na ginei uncomment gia na valei toulaxiston mia fora to home
		// location

		String filenameHome = Utils.homeLocationFile;
		File myFileHome = new File(getFilesDir() + filenameHome);

		if (!myFileHome.exists()) {
			//setAlarmFeelignMode();
			Intent changePasswordFirstTime = new Intent(MainActivityNotDrunk.this,ChangePassWordActivityFirstTime.class);
			startActivity(changePasswordFirstTime);
			
			Intent setHome = new Intent(MainActivityNotDrunk.this,
					SetHomeLocationMapActivity.class);
			startActivity(setHome);
		}

		Button locker = (Button) findViewById(R.id.textViewLocker);
		locker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(
						MainActivityNotDrunk.this,
						cy.ucy.arm.example.MultiCare.MultiLocker.MainActivity.class));
			}
		});

		Button buttonQuotes = (Button) findViewById(R.id.textViewQuotes);
		buttonQuotes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(MainActivityNotDrunk.this,
						ActivityDialogFeelingMode.class));

			}
		});

		Button buttonSetHomeLocation = (Button) findViewById(R.id.textViewSetHomeLocation);
		buttonSetHomeLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(MainActivityNotDrunk.this,
						SetHomeLocationMapActivity.class));

			}
		});

		Button buttonTakeMeHome = (Button) findViewById(R.id.textViewTakeMeHome);
		buttonTakeMeHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(MainActivityNotDrunk.this,
						DrunkMapActivity.class));

			}
		});

		/*Button buttonRouteTwoPoints = (Button) findViewById(R.id.textViewRouteBetweenTwoPoints);
		buttonRouteTwoPoints.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(MainActivityNotDrunk.this,
						MapActivity.class));

			}
		});*/
		/*Button buttonRouteTwoAddresses = (Button) findViewById(R.id.textViewRouteAddresses);
		buttonRouteTwoAddresses.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(MainActivityNotDrunk.this,
						MapActivityRouteAddresses.class));

			}
		});*/
		Button buttonEmergencyContacts = (Button) findViewById(R.id.textViewEmergencyContacts);
		buttonEmergencyContacts.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(MainActivityNotDrunk.this,
						DrunkLoadFavoriteContactsXML.class));

			}
		});
		Button buttonFavoriteContacts = (Button) findViewById(R.id.textViewFavoriteContacts);
		buttonFavoriteContacts.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(MainActivityNotDrunk.this,
						LoadFavoriteContactsXML.class));

			}
		});
		Button buttonEnableDrunkMode = (Button) findViewById(R.id.buttonDrunkMode);
		buttonEnableDrunkMode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				
				Intent in=new Intent(MainActivityNotDrunk.this,
						ActivitySetDrunk.class);
				
				
				startActivity(in);
			}
		});

	}

	private void setAlarmFeelignMode() {
		Intent intentFeeling = new Intent(CUSTOM_INTENT);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2222222,
				intentFeeling, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		// alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
		// + (1 * 1000), pendingIntent);
		Calendar time = Calendar.getInstance();
		time.set(Calendar.HOUR_OF_DAY, 9);
		time.set(Calendar.MINUTE, 0);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				time.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				pendingIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_activity_main_no_drunk, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_action_multilocker:
			startActivity(new Intent(MainActivityNotDrunk.this,
					cy.ucy.arm.example.MultiCare.MultiLocker.MainActivity.class));
			return true;
		case R.id.menu_action_quotes:
			startActivity(new Intent(MainActivityNotDrunk.this,
					ActivityDialogFeelingMode.class));
			return true;
		case R.id.menu_action_drunk_favorite_contacts:
			Intent loadDrunk = new Intent(this,
					DrunkLoadFavoriteContactsXML.class);
			startActivity(loadDrunk);
			return true;
		case R.id.menu_action_load_favorite_contacts:
			Intent loadFavorite = new Intent(this,
					LoadFavoriteContactsXML.class);
			startActivity(loadFavorite);
			return true;
		case R.id.menu_action_set_home_location:
			// Toast.makeText(getApplicationContext(), "Hello",
			// Toast.LENGTH_SHORT).show();
			Intent loadMap = new Intent(this, SetHomeLocationMapActivity.class);
			startActivity(loadMap);
			return true;
		case R.id.menu_action_take_me_home:
			// Toast.makeText(getApplicationContext(), "Hello",
			// Toast.LENGTH_SHORT).show();
			Intent takeMeHome = new Intent(this, DrunkMapActivity.class);
			startActivity(takeMeHome);
			return true;
		/*case R.id.menu_action_directions_two_points:
			Intent twoPoints = new Intent(this, MapActivity.class);
			startActivity(twoPoints);
			return true;
		case R.id.menu_action_directions_two_address:
			startActivity(new Intent(MainActivityNotDrunk.this,
					MapActivityRouteAddresses.class));
			return true;*/
		case R.id.menu_action_drunk_mode:
			// Toast.makeText(getApplicationContext(), "Hello",
			// Toast.LENGTH_SHORT).show();
			startActivity(new Intent(MainActivityNotDrunk.this,
					ActivitySetDrunk.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onResume() {
		super.onPause();
		if (Utils.getAnswer(getFilesDir())) {
			Intent i = new Intent(this,MainActivityDrunk.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        moveTaskToBack(true);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
