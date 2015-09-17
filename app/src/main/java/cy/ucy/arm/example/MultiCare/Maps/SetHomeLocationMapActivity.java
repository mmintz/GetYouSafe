package cy.ucy.arm.example.MultiCare.Maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.NetworkInfo.NetworkConnection;
import cy.ucy.arm.example.MultiCare.utils.Utils;

import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.R.bool;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

public class SetHomeLocationMapActivity extends FragmentActivity implements
		LocationListener {

	public static Boolean firstTime = false;
	private Boolean select = false;
	private GoogleMap map;
	private ArrayList<LatLng> markerPoints;
	private int mMode = 0;
	final int MODE_DRIVING = 0;
	final int MODE_BICYCLING = 1;
	final int MODE_WALKING = 2;
	private Spinner spinnerSelection;
	private ArrayList<String> routeInstructions = new ArrayList<String>();
	private LocationManager locationManager;
	private String provider;
	private String latituteField;
	private String longitudeField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_home_location);

		// Initializing
		markerPoints = new ArrayList<LatLng>();
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		// Getting reference to SupportMapFragment of the activity_main
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);

		// Getting Map for the SupportMapFragment
		map = fm.getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		// Get reference for the Spinner
		spinnerSelection = (Spinner) findViewById(R.id.spinnerSelection);
		spinnerSelection.setSelection(1);
		
		spinnerSelection
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView adapter, View v,
							int i, long lng) {

						String selecteditem = adapter.getItemAtPosition(i)
								.toString();
						// set appropriate Map Type based on the spinner selection
						if (selecteditem.equals(Utils.SATTELITE))
							map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
						else if (selecteditem.equals(Utils.ROADMAP))
							map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
						else if (selecteditem.equals(Utils.HYBRID))
							map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
						else if (selecteditem.equals(Utils.TERRAIN))
							map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {

					}
				});

		// Enable MyLocation Button in the Map
		map.setMyLocationEnabled(true);
		
		// Get reference for the saveHomeLocation button
		Button save = (Button) findViewById(R.id.set_home_location);

		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				select = true;
				onBackPressed();
			}
		});

		// Setting onclick event listener for the map
		map.setOnMapClickListener(new OnMapClickListener() {

			public void onMapClick(LatLng point) {

				// Already two locations
				if (markerPoints.size() > 0) {
					markerPoints.clear();
					map.clear();
				}

				// Adding new item to the ArrayList
				markerPoints.add(point);

				// Draws Start and Stop markers on the Google Map
				drawStartStopMarkers();

				
			}
		});

		// Initialize the location fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
			markerPoints.add(new LatLng(Double.valueOf(latituteField), Double
					.valueOf(longitudeField)));
			drawStartStopMarkers();
		} else {
			latituteField = "Location not available";
			longitudeField = "Location not available";
		}
		createPopUp();
		
	}

	private void saveToFile() {

		// prepei na mpei internal

		try {
			String filename = Utils.homeLocationFile;
			File myFile = new File(getFilesDir() + filename);
			if (myFile.exists())
				myFile.delete();
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.write(markerPoints.get(0).latitude + "\n"
					+ markerPoints.get(0).longitude + "\n");
			System.out.println("Saving....: " + markerPoints.get(0).latitude + "\n"
					+ markerPoints.get(0).longitude + "\n");
			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(),
					"Done saving file for your Home Location ",
					Toast.LENGTH_SHORT).show();
			
			if (firstTime == false) {
				firstTime = true;
				finish();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createPopUp() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Hint...")
				.setMessage(
						"Click on the map to select your Home Location. It will be used in case of emergency (e.g Get home directions when you feel dizzy). If nothing selected then your current location is used as your Home Location. You can change your Home Location by pressing\n->Set Home Location from the home screen")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Check for Network Connection
						NetworkConnection network = new NetworkConnection(
								SetHomeLocationMapActivity.this);
						if (!network.haveNetworkConnection())
							createDialog();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1,
				(LocationListener) this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates((LocationListener) this);
	}

	// onLocationChanged update values of latituteField and longitudeField 
	public void onLocationChanged(Location location) {
		double lat = (double) (location.getLatitude());
		double lng = (double) (location.getLongitude());
		latituteField = String.valueOf(lat);
		longitudeField = String.valueOf(lng);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(Double.parseDouble(latituteField), Double
						.parseDouble(longitudeField)), 14.0f));// where 14.0 is
																// the zoom
																// level
																
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	public void onProviderEnabled(String provider) {

	}

	public void onProviderDisabled(String provider) {
		
	}

	// Drawing Start and Stop locations
	private void drawStartStopMarkers() {

		for (int i = 0; i < markerPoints.size(); i++) {

			// Creating MarkerOptions
			MarkerOptions options = new MarkerOptions();

			// Setting the position of the marker
			options.position(markerPoints.get(i));

			//For the start location, the color of marker is GREEN and for the
			// end location, the color of marker is RED.
			
			if (i == 0) {
				options.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
			}

			// Add new marker to the Google Map Android API V2
			map.addMarker(options);
		}
	}

	/**
	 * Called when the activity has detected the user's press of the back key.
	 * Prompts the user to confirm the exit of the application
	 * 
	 * @Override onBackPressed() in Activity
	 */
	public void onBackPressed() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Save Home Location?")
				.setMessage("Your Home location will be the Selected Location")
				.setCancelable(false)
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						if(firstTime==true)
						{
							finish();
							
						
						}
					}
				})
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								saveToFile();
								Toast.makeText(getApplicationContext(),
										"Your current location was saved!",
										Toast.LENGTH_SHORT).show();
								finish();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	// Creates a Dialog for Network Connection.
		// If Internet Settings is clicked then the Phone Settings for the Internet is opened
		public void createDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					SetHomeLocationMapActivity.this);
			builder.setTitle("No Internet Connection")
					.setMessage("Enable Internet Connection?")
					.setCancelable(true)
					.setPositiveButton("Internet Settings",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									startActivity(new Intent(
											android.provider.Settings.ACTION_WIRELESS_SETTINGS));
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									finish();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}

}
