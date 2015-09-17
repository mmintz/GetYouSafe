package cy.ucy.arm.example.MultiCare.Maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

public class MapActivity extends FragmentActivity implements LocationListener {

	private GoogleMap map;
	private RadioButton rbDriving;
	private RadioButton rbBiCycling;
	private RadioButton rbWalking;
	private RadioGroup rgModes;
	private ArrayList<LatLng> markerPoints;
	int mMode = 0;
	final int MODE_DRIVING = 0;
	final int MODE_BICYCLING = 1;
	final int MODE_WALKING = 2;
	private Spinner spinnerSelection;
	private ArrayList<String> routeInstructions = new ArrayList<String>();
	private LocationManager locationManager;
	private String provider;
	private String latituteField;
	private String longitudeField;
	private Button getDirections;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		// Initialize the location fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			latituteField = "Location not available";
			longitudeField = "Location not available";
		}

		// Getting reference to getDirections
		getDirections = (Button) findViewById(R.id.getDirections);

		// OnClick of getDirections button
		View.OnClickListener directionsListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Create a new Intent and startActivity DirectionsActivity which will show the route instructions on a ListView
				Intent intent = new Intent(MapActivity.this,
						DirectionsActivity.class);
				intent.putStringArrayListExtra("directions", routeInstructions);
				startActivity(intent);
			}
		};

		getDirections.setOnClickListener(directionsListener);

		// Getting reference to rb_driving
		rbDriving = (RadioButton) findViewById(R.id.rb_driving);

		// Getting reference to rb_walking
		rbWalking = (RadioButton) findViewById(R.id.rb_walking);

		// Getting Reference to rg_modes
		rgModes = (RadioGroup) findViewById(R.id.rg_modes);

		rgModes.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				getDirections.setVisibility(View.GONE);

				// Checks, whether start and end locations are captured
				if (markerPoints.size() >= 2) {
					LatLng origin = markerPoints.get(0);
					LatLng dest = markerPoints.get(1);

					// Getting URL to the Google Directions API
					String url = getDirectionsUrl(origin, dest);

					DownloadTask downloadTask = new DownloadTask();

					// Check for Network Connection
					NetworkConnection network = new NetworkConnection(
							MapActivity.this);
					if (network.haveNetworkConnection())
						// Start downloading json data from Google Directions
						// API
						downloadTask.execute(url);
					else
						createDialog();
				}
			}
		});

		// Initializing
		markerPoints = new ArrayList<LatLng>();

		// Getting reference to SupportMapFragment of the activity_main
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);

		// Getting Map for the SupportMapFragment
		map = fm.getMap();

		// Enable MyLocation Button in the Map
		map.setMyLocationEnabled(true);
		
		// Get reference for the Spinner
		spinnerSelection = (Spinner) findViewById(R.id.spinnerSelection);
		spinnerSelection.setSelection(1);
		
		spinnerSelection
		.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView adapter, View v,
					int i, long lng) {

				String selecteditem = adapter.getItemAtPosition(i).toString();
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
		
		// Setting onclick event listener for the map
		map.setOnMapClickListener(new OnMapClickListener() {

			public void onMapClick(LatLng point) {

				// Already two locations
				if (markerPoints.size() > 1) {
					markerPoints.clear();
					map.clear();
				}

				// Adding new item to the ArrayList
				markerPoints.add(point);

				// Draws Start and Stop markers on the Google Map
				drawStartStopMarkers();

				// Checks, whether start and end locations are captured
				if (markerPoints.size() >= 2) {
					LatLng origin = markerPoints.get(0);
					LatLng dest = markerPoints.get(1);

					// Getting URL to the Google Directions API
					String url = getDirectionsUrl(origin, dest);

					DownloadTask downloadTask = new DownloadTask();

					// Start downloading json data from Google Directions API
					
					NetworkConnection network = new NetworkConnection(
							MapActivity.this);
					if (network.haveNetworkConnection())
						// Start downloading json data from Google Directions
						// API
						downloadTask.execute(url);
					else
						createDialog();
				}
			}
		});
	}

	// ///

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

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

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
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
			} else if (i == 1) {
				options.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			}

			// Add new marker to the Google Map Android API V2
			map.addMarker(options);
		}
	}

	/**
	* Creates the directions URL string for the request
	* @param LatLng origin The LatLng object which contains the origin longitude and latitude
	* @param LatLng dest The LatLng object which contains the dest longitude and latitude
	* @return String url The url which will be used for the request
	*/
	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Travelling Mode
		String mode = "mode=driving";

		if (rbDriving.isChecked()) {
			mode = "mode=driving";
			mMode = 0;
		} else if (rbWalking.isChecked()) {
			mode = "mode=walking";
			mMode = 2;
		}

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor + "&"
				+ mode;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	/** 
	* A method to download json data from url 
	* @param String strUrl The url which will be used to download data
	* @return String data The downloaded data  
	*/
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
			
		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
		// ProgressDialog to prompt the user to wait
		private ProgressDialog progressDialog;

		/**
		 * This method is used before the execution of the doInBackground method.
		 * Some things have to be done before the doInBackground process on the UI
		 * thread
		 */
		protected void onPreExecute() {
			getDirections.setClickable(false);
			showProgressDialog("Please wait...",
					"Please be patient. Loading your directions...");
		}
		
		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
				routeInstructions = parser.getRouteInstructions();

				for (int i = 0; i < routeInstructions.size(); i++)
					System.out.println("Instruction " + i + " is: "
							+ routeInstructions.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}
		/**
		 * This method is used for creating a new Progress Dialog
		 * 
		 * @param String
		 *            title
		 * @param String
		 *            message
		 */
		private void showProgressDialog(String title, String message) {
			progressDialog = new ProgressDialog(MapActivity.this);

			progressDialog.setTitle(title); // title

			progressDialog.setMessage(message); // message

			progressDialog.setCancelable(false);

			progressDialog.show();

		}
		
		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			// MarkerOptions markerOptions = new MarkerOptions();

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(7);

				// Changing the color polyline according to the mode
				if (mMode == MODE_DRIVING)
					lineOptions.color(Color.RED);
				else if (mMode == MODE_WALKING)
					lineOptions.color(Color.BLUE);
			}

			if (result.size() < 1) {
				Toast.makeText(getBaseContext(), "No Points",
						Toast.LENGTH_SHORT).show();
				return;
			}

			// Drawing polyline in the Google Map for the i-th route
			map.addPolyline(lineOptions);
			getDirections.setVisibility(View.VISIBLE);
			getDirections.setClickable(true);
		}
	}

	// Creates a Dialog for Network Connection.
	// If Internet Settings is clicked then the Phone Settings for the Internet is opened
	public void createDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				MapActivity.this);
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

							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
