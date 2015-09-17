package cy.ucy.arm.example.MultiCare.UIclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.FeelingMode.ActivityDialogFeelingMode;
import cy.ucy.arm.example.MultiCare.Maps.DrunkMapActivity;
import cy.ucy.arm.example.MultiCare.Maps.MapActivity;
import cy.ucy.arm.example.MultiCare.Maps.MapActivityRouteAddresses;
import cy.ucy.arm.example.MultiCare.Maps.SetHomeLocationMapActivity;
import cy.ucy.arm.example.MultiCare.MultiLocker.MyService;
import cy.ucy.arm.example.MultiCare.utils.Utils;

public class MainActivityDrunk extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_drunk);
		//case htc
		sendToService("com.android.htccontacts","add");
		sendToService("com.android.contacts","add");
		//case google
		sendToService("com.google.android.talk","add");
		sendToService("com.google.android.dialer","add");
		
		
		Button buttonQuotes = (Button) findViewById(R.id.textViewQuotes);
		buttonQuotes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(MainActivityDrunk.this,
						ActivityDialogFeelingMode.class));

			}
		});

		Button buttonTakeMeHome = (Button) findViewById(R.id.textViewTakeMeHome);
		buttonTakeMeHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(MainActivityDrunk.this,
						DrunkMapActivity.class));

			}
		});

		/*Button buttonRouteTwoPoints = (Button) findViewById(R.id.textViewRouteBetweenTwoPoints);
		buttonRouteTwoPoints.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivityDrunk.this,
						MapActivity.class));

			}
		});*/
		/*Button buttonRouteTwoAddresses = (Button) findViewById(R.id.textViewRouteAddresses);
		buttonRouteTwoAddresses.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivityDrunk.this,
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
				startActivity(new Intent(MainActivityDrunk.this,
						DrunkLoadFavoriteContactsXML.class));

			}
		});

		Button buttonDisableDrunkMode = (Button) findViewById(R.id.buttonDrunkMode);
		buttonDisableDrunkMode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new
				// Intent(MainActivity.this,cy.arm.example.MultiCare.locker2.MainActivity.class));
				startActivity(new Intent(MainActivityDrunk.this,
						ActivityActionsDrunkEnable.class));
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_activity_main_drunk, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_action_quotes:
			startActivity(new Intent(this, ActivityDialogFeelingMode.class));
			return true;
		case R.id.menu_action_drunk_favorite_contacts:
			Intent loadDrunk = new Intent(this,
					DrunkLoadFavoriteContactsXML.class);
			startActivity(loadDrunk);
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
			startActivity(new Intent(this, MapActivityRouteAddresses.class));
			return true;*/
		case R.id.menu_action_drunk_mode:
			// Toast.makeText(getApplicationContext(), "Hello",
			// Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, ActivityActionsDrunkEnable.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
	@Override
	protected void onResume() {
		super.onPause();
		if (!Utils.getAnswer(getFilesDir())) {
			Intent i = new Intent(this,MainActivityNotDrunk.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
	}
	void sendToService(CharSequence text,CharSequence check) {

		
		
        Log.e("BroadcastActivity", "Sending message to service: " + text);

        final Intent intent = new Intent(MyService.ACTION_TO_SERVICE);
        intent.putExtra("case", "main");
        intent.putExtra("data", text);
        intent.putExtra("check", check);

        sendBroadcast(intent);

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
