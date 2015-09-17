package cy.ucy.arm.example.MultiCare.UIclasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.DataClasses.PhoneContact;
import cy.ucy.arm.example.MultiCare.Dialogs.InsertFavoriteFriendDialog;
import cy.ucy.arm.example.MultiCare.GetLonLat.GetLonLat;

import cy.ucy.arm.example.MultiCare.adapters.DrunkFavoriteContactArrayAdapter;
import cy.ucy.arm.example.MultiCare.adapters.FavoriteContactArrayAdapter;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import cy.ucy.arm.example.MultiCare.xmlparser.ModifyXML;
import cy.ucy.arm.example.MultiCare.xmlparser.ReadXML;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class DrunkLoadFavoriteContactsXML extends Activity {

	private ListView loadFavoriteContacts;
	private ImageView buttonPolice;
	private ImageView buttonAmbulance;
	private Button buttonSendAll;
	private ProgressDialog progressDialog;
	private ArrayList<PhoneContact> dataFavoriteContacts;
	private DrunkFavoriteContactArrayAdapter adapter;
	private LocationManager locationManager;
	private String latituteField;
	private String longitudeField;
	private String provider;
	private double longitude;
	private double latitude;
	private String countryName = "";
	List<Address> addresses = null;
	Geocoder gcd;
	private Map<String, String> mapPolice;
	private Map<String, String> mapAmbulance;

	private View.OnClickListener listenerPolice = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/**
			 * create an intent Intent.ACTION_DIAL.. With this intent every app
			 * that has this intent-filter is a candidate to respond this intent
			 * (message). If we have multiple apps that can respond to this
			 * intent a list will be shown to us in order to choose to which app
			 * will the intent be delivered
			 */
			Intent intent = new Intent(Intent.ACTION_CALL);
			
			String countryCode = mapPolice.get(countryName);
			String callPolice = "tel://";
			
			if(countryCode != null && !countryCode.isEmpty())
			{
				callPolice += countryCode;
				intent.setData(Uri.parse(callPolice));
				startActivity(intent);
			}
			else
			{
				Toast.makeText(getApplicationContext(),"Emergency number not found. Opening phone dialer...", Toast.LENGTH_LONG).show();
				Intent intentDial = new Intent(Intent.ACTION_DIAL);
				startActivity(intentDial);
			}
		}
	};

	private View.OnClickListener listenerAmbulance = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/**
			 * create an intent Intent.ACTION_DIAL.. With this intent every app
			 * that has this intent-filter is a candidate to respond this intent
			 * (message). If we have multiple apps that can respond to this
			 * intent a list will be shown to us in order to choose to which app
			 * will the intent be delivered
			 */
			Intent intent = new Intent(Intent.ACTION_CALL);
			
			String countryCode = mapAmbulance.get(countryName);
			String callAmbulance = "tel://";
			
			if(countryCode != null && !countryCode.isEmpty())
			{
				callAmbulance += countryCode;
				intent.setData(Uri.parse(callAmbulance));
				startActivity(intent);
			}
			else
			{
				Toast.makeText(getApplicationContext(),"Emergency number not found. Opening phone dialer...", Toast.LENGTH_LONG).show();
				Intent intentDial = new Intent(Intent.ACTION_DIAL);
				startActivity(intentDial);
			}
		}
	};

	private View.OnClickListener listenerSendAll = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			AlertDialog.Builder builder = new AlertDialog.Builder(DrunkLoadFavoriteContactsXML.this);
			builder.setTitle("Confirm Sending...")
					.setMessage(
							"Send to all favorite friends a message with your location?")
					.setCancelable(false)
					.setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					})
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									sendMessage();
									Toast.makeText(getApplicationContext(),
											"Message successfully sent to all favorite contacts!",
											Toast.LENGTH_SHORT).show();
										
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
			
			

		}
	};

	/**
	 * This method is used for creating a new Progress Dialog
	 * 
	 *
	 *            message
	 */
	private void showProgressDialog(String title, String message) {
		progressDialog = new ProgressDialog(this);

		progressDialog.setTitle(title); // title

		progressDialog.setMessage(message); // message

		progressDialog.setCancelable(false);

		progressDialog.show();
	}

	public ArrayList<PhoneContact> getDataFavoriteContacts() {
		return dataFavoriteContacts;
	}

	public void setDataFavoriteContacts(
			ArrayList<PhoneContact> dataFavoriteContacts) {
		this.dataFavoriteContacts = dataFavoriteContacts;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_favorite_contacts_drunk);
		loadFavoriteContacts = (ListView) findViewById(R.id.listViewLoadFavoriteContacts);
		fetchDataFromXML();
		adapter = new DrunkFavoriteContactArrayAdapter(this,
				R.layout.listview_item_row_favorite_drunk, dataFavoriteContacts);
		adapter.setFavoritesActivity(this);
		buttonPolice = (ImageView) findViewById(R.id.imageViewPolice);
		buttonAmbulance = (ImageView) findViewById(R.id.imageViewAmbulance);
		buttonSendAll = (Button) findViewById(R.id.buttonSendAll);
		loadFavoriteContacts.setAdapter(adapter);
		buttonAmbulance.setOnClickListener(listenerAmbulance);
		buttonPolice.setOnClickListener(listenerPolice);
		buttonSendAll.setOnClickListener(listenerSendAll);
		
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location;
		try{
		location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}catch(Exception e)
		{
			location=null;
		}
		// Initialize the location fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
			latitude = Double.valueOf(latituteField);
			longitude = Double.valueOf(longitudeField);
		} else {
			latituteField = "Location not available";
			longitudeField = "Location not available";
		}
		System.out.println("lat: " + latitude + " long: " + longitude);
		mapPolice = new HashMap<String, String>();
		mapAmbulance = new HashMap<String, String>();
		
		//
		createPoliceMap(mapPolice);
		createAmbulanceMap(mapAmbulance);
	
		//System.out.println("Initialized of latitude: " + latitude + " and longitude: " + longitude);
	
gcd = new Geocoder(DrunkLoadFavoriteContactsXML.this, Locale.getDefault());
		
		
		ParserTask parserTask = new ParserTask();

		// Invokes the thread for parsing the JSON data
		parserTask.execute("start");
		
	}
	
	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<Address>> {
		// ProgressDialog to prompt the user to wait
		private ProgressDialog progressDialog;

		/**
		 * This method is used before the execution of the doInBackground method.
		 * Some things have to be done before the doInBackground process on the UI
		 * thread
		 */
		protected void onPreExecute() {
			
			showProgressDialog("Please wait...",
					"Please be patient. Discovering Emergency Contacts in your location...");
		}
		
		// Parsing the data in non-ui thread
		@Override
		protected List<Address> doInBackground(String ...message) {

			
			try {
				addresses = gcd.getFromLocation(latitude, longitude, 1);	
			} catch (Exception e) {
				e.printStackTrace();
				addresses=null;
			}
			return addresses;
		}
		
		
		private void showProgressDialog(String title, String message) {
			progressDialog = new ProgressDialog(DrunkLoadFavoriteContactsXML.this);

			progressDialog.setTitle(title); // title

			progressDialog.setMessage(message); // message

			progressDialog.setCancelable(false);

			progressDialog.show();

		}
		
		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<Address> result) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			
			update_country();
			// MarkerOptions markerOptions = new MarkerOptions();

		
		}

		
	}
	private void update_country() {
		// TODO Auto-generated method stub
		if(addresses!=null)
		    if (addresses.size() > 0)
		    {  
		    	countryName=addresses.get(0).getCountryName();
		    }
	}




	private void sendMessage() {
		GetLonLat get = new GetLonLat(this);
		get.prepareLonLat();

		for (int i = 0; i < dataFavoriteContacts.size(); i++) {

			String toSend = Utils.appName + Utils.version + Utils.dearFriend
					+ dataFavoriteContacts.get(i).getContactName() + ",\n"
					+ Utils.mainBody;
			String myName = " ";
			String lon = "lon: " + get.getLon() + "\n";
			String lat = "lat: " + get.getLat() + "\n";
			String finString = toSend + lon + lat;
			SmsManager smsManager = SmsManager.getDefault();
			String number;
			if (dataFavoriteContacts.get(i).getPhoneNumber().contains("+357"))
				number = dataFavoriteContacts.get(i).getPhoneNumber();
			else
				number = dataFavoriteContacts.get(i).getPhoneNumber();
			smsManager.sendTextMessage(number, null, finString, null, null);

			Toast.makeText(getApplicationContext(), "Message Successfully Sent", Toast.LENGTH_SHORT).show();
		}

	}

	public DrunkFavoriteContactArrayAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(DrunkFavoriteContactArrayAdapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * This method is being called each time we return from a child Activity
	 * which we are his parent. This method takes 3 arguments. The resultCode
	 * which in our example must me RESULT_OK a requestCode which we must check
	 * which one of the 1-3 is in order to find out which of the 3 button was
	 * originally pressed and which of the 3 TextViews is going to change its
	 * value. If the resultCode == RESULT_OK and the requestCode is one of the
	 * 1,2,3 then we check if the Intent has the returnValue as a key. If it
	 * does we take it's value and if it's not an empty string we change the
	 * text of the respective TextView and we make a Toast that show form which
	 * activity we have returned
	 * 
	 *
	 *            data
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check if the result code is ok
		// and which of the requested code we have received (we now know which
		// button has called the NotifyBackActivity )
		if (resultCode == RESULT_OK && requestCode == 0) {
			// if the intent has a returnValue key means is a valid intent that
			// has came from our NotifyBackActivity
			ArrayList<PhoneContact> getContactsToImport = data
					.getParcelableArrayListExtra("dataToAdd");
			ModifyXML modifier = new ModifyXML(Utils.outputFolder
					+ Utils.filenameFavoriteContacts);
			modifier.setMyDir(getFilesDir());
			for (int i = 0; i < getContactsToImport.size(); i++) {
				modifier.addElement(getContactsToImport.get(i));
				dataFavoriteContacts.add(getContactsToImport.get(i));
				adapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * This method is used in order to fetch the existing data from the xml
	 */
	private void fetchDataFromXML() {
		ReadXML xmlRead = new ReadXML(Utils.outputFolder
				+ Utils.filenameFavoriteContacts);
		xmlRead.setMyDir(this.getFilesDir());
		// get the existing data
		this.dataFavoriteContacts = xmlRead.getData();

		// this.modifyXML = new ModifyXML(this.getOutputFolder()
		// + this.getOutputFilename());
		// this.modifyXML.setMyDir(myDir);
	}
	
	// onLocationChanged update values of latituteField and longitudeField 
	public void onLocationChanged(Location location) {
		double lat = (double) (location.getLatitude());
		double lng = (double) (location.getLongitude());
		latituteField = String.valueOf(lat);
		longitudeField = String.valueOf(lng);
		latitude = Double.valueOf(latituteField);
		longitude = Double.valueOf(longitudeField);
	}
	
	public Map<String, String> createPoliceMap(Map<String, String> mapPolice){
	
		mapPolice.put("Algeria", "17");
		mapPolice.put("Angola", "110");
		mapPolice.put("Botswana", "911");
		mapPolice.put("Cameroon", "112");
		mapPolice.put("Chad", "17");
		mapPolice.put("Djibouti", "17");
		mapPolice.put("Egypt", "112");
		mapPolice.put("Ghana", "999");
		mapPolice.put("Mali", "17");
		mapPolice.put("Mauritius", "112");
		mapPolice.put("Morocco", "19");
		mapPolice.put("Nigeria", "112");
		mapPolice.put("Rwanda", "112");
		mapPolice.put("Sierra Leone", "019");
		mapPolice.put("Somalia", "888");
		mapPolice.put("South Africa", "10111");
		mapPolice.put("Sudan", "999");
		mapPolice.put("Tunisia", "197");
		mapPolice.put("Uganda", "999");
		mapPolice.put("Zambia", "999");
		mapPolice.put("Zimbabwe", "999");
		mapPolice.put("Afghanistan", "119");
		mapPolice.put("Bahrain", "999");
		mapPolice.put("Bangladesh", "999");
		mapPolice.put("Burma", "999");
		mapPolice.put("Cambodia", "117");
		mapPolice.put("People's Republic of China", "110");
		mapPolice.put("China", "110");
		mapPolice.put("East Timor", "112");
		mapPolice.put("Hong Kong", "999");
		mapPolice.put("India", "100");
		mapPolice.put("Indonesia", "110");
		mapPolice.put("Iran", "110");
		mapPolice.put("Israel", "100");
		mapPolice.put("Japan", "110");
		mapPolice.put("Jordan", "112");
		mapPolice.put("Kazakhstan", "112");
		mapPolice.put("Democratic People's Republic of Korea", "119");
		mapPolice.put("Republic of Korea", "112");
		mapPolice.put("Korea", "112");
		mapPolice.put("North Korea", "112");
		mapPolice.put("South Korea", "112");
		mapPolice.put("Kuwait", "112");
		mapPolice.put("Lebanon", "112");
		mapPolice.put("Macau", "999");
		mapPolice.put("Maldives", "999");
		mapPolice.put("Malaysia", "999");
		mapPolice.put("Mongolia", "100");
		mapPolice.put("Nepal", "100");
		mapPolice.put("Oman", "999");
		mapPolice.put("Pakistan", "15");
		mapPolice.put("Philippines", "117");
		mapPolice.put("Qatar", "999");
		mapPolice.put("Saudi Arabia", "999");
		mapPolice.put("Singapore", "999");
		mapPolice.put("Sri Lanka", "118");
		mapPolice.put("Syria", "112");
		mapPolice.put("Republic of China (Taiwan)", "110");
		mapPolice.put("Taiwan", "110");
		mapPolice.put("Tajikistan", "112");
		mapPolice.put("Thailand", "191");
		mapPolice.put("United Arab Emirates", "999");
		mapPolice.put("Vietnam", "113");
		mapPolice.put("Albania", "129");
		mapPolice.put("Andorra", "112");
		mapPolice.put("Armenia", "102");
		mapPolice.put("Austria", "112");
		mapPolice.put("Azerbaijan", "112");
		mapPolice.put("Belarus", "102");
		mapPolice.put("Belgium", "112");
		mapPolice.put("Bosnia and Herzegovina", "112");
		mapPolice.put("Bosnia", "112");
		mapPolice.put("Bulgaria", "112");
		mapPolice.put("Croatia", "112");
		mapPolice.put("Cyprus", "199");
		mapPolice.put("Czech Republic", "112");
		mapPolice.put("Denmark", "112");
		mapPolice.put("Estonia", "112");
		mapPolice.put("Faroe Islands", "112");
		mapPolice.put("Finland", "112");
		mapPolice.put("France", "112");
		mapPolice.put("Georgia", "112");
		mapPolice.put("Germany", "112");
		mapPolice.put("Gibraltar", "112");
		mapPolice.put("Greece", "112");
		mapPolice.put("Greenland", "112");
		mapPolice.put("Hungary", "112");
		mapPolice.put("Iceland", "112");
		mapPolice.put("Republic of Ireland", "112");
		mapPolice.put("Ireland", "112");
		mapPolice.put("Italy", "112");
		mapPolice.put("Kosovo", "112");
		mapPolice.put("Latvia", "112");
		mapPolice.put("Lithuania", "112");
		mapPolice.put("Luxembourg", "112");
		mapPolice.put("Republic of Macedonia", "112");
		mapPolice.put("Macedonia", "112");
		mapPolice.put("Malta", "112");
		mapPolice.put("Moldova", "112");
		mapPolice.put("Monaco", "112");
		mapPolice.put("Montenegro", "112");
		mapPolice.put("Netherlands", "112");
		mapPolice.put("Norway", "112");
		mapPolice.put("Poland", "112");
		mapPolice.put("Portugal", "112");
		mapPolice.put("Romania", "112");
		mapPolice.put("Russia", "112");
		mapPolice.put("San Marino", "113");
		mapPolice.put("Serbia", "112");
		mapPolice.put("Slovakia", "112");
		mapPolice.put("Slovenia", "112");
		mapPolice.put("Spain", "112");
		mapPolice.put("Sweden", "112");
		mapPolice.put("Switzerland", "112");
		mapPolice.put("Turkey", "112");
		mapPolice.put("Ukraine", "112");
		mapPolice.put("United Kingdom", "112");
		mapPolice.put("Vatican City", "113");
		mapPolice.put("Vatican", "113");
		mapPolice.put("Australia", "000");
		mapPolice.put("Fiji", "917");
		mapPolice.put("New Zealand", "111");
		mapPolice.put("Solomon Islands", "999");
		mapPolice.put("Vanuatu", "112");
		mapPolice.put("Canada", "911");
		mapPolice.put("Mexico", "066");
		mapPolice.put("Saint Pierre and Miquelon", "17");
		mapPolice.put("United States of America", "911");
		mapPolice.put("United States", "911");
		mapPolice.put("America", "911");
		mapPolice.put("Barbados", "211");
		mapPolice.put("The Bahamas", "911");
		mapPolice.put("Bahamas", "911");
		mapPolice.put("Cayman Islands", "911");
		mapPolice.put("Costa Rica", "911");
		mapPolice.put("Dominican Republic", "911");
		mapPolice.put("Guatemala", "110");
		mapPolice.put("El Salvador", "911");
		mapPolice.put("Haiti", "114");
		mapPolice.put("Honduras", "199");
		mapPolice.put("Jamaica", "119");
		mapPolice.put("Nicaragua", "911");
		mapPolice.put("Panama", "911");
		mapPolice.put("Trinidad and Tobago", "999");
		mapPolice.put("Argentina", "101");
		mapPolice.put("Bolivia", "110");
		mapPolice.put("Brazil", "190");
		mapPolice.put("Chile", "133");
		mapPolice.put("Colombia", "112");
		mapPolice.put("Ecuador", "911");
		mapPolice.put("French Guiana", "112");
		mapPolice.put("Guyana", "911");
		mapPolice.put("Paraguay", "911");
		mapPolice.put("Peru", "105");
		mapPolice.put("Suriname", "112");
		mapPolice.put("Uruguay", "911");
		mapPolice.put("Venezuela", "911");

		return mapPolice;
	}
	
	public Map<String, String> createAmbulanceMap(Map<String, String> mapAmbulance){

		mapAmbulance.put("Algeria", "14");
		mapAmbulance.put("Angola", "118");
		mapAmbulance.put("Botswana", "911");
		mapAmbulance.put("Cameroon", "112");
		mapAmbulance.put("Chad", "22511237");
		mapAmbulance.put("Djibouti", "351351");
		mapAmbulance.put("Egypt", "112");
		mapAmbulance.put("Ghana", "999");
		mapAmbulance.put("Mali", "15");
		mapAmbulance.put("Mauritius", "114");
		mapAmbulance.put("Morocco", "15");
		mapAmbulance.put("Nigeria", "112");
		mapAmbulance.put("Rwanda", "112");
		mapAmbulance.put("Sierra Leone", "999");
		mapAmbulance.put("Somalia", "999");
		mapAmbulance.put("South Africa", "10177");
		mapAmbulance.put("Sudan", "999");
		mapAmbulance.put("Tunisia", "190");
		mapAmbulance.put("Uganda", "999");
		mapAmbulance.put("Zambia", "991");
		mapAmbulance.put("Zimbabwe", "999");
		mapAmbulance.put("Afghanistan", "102");
		mapAmbulance.put("Bahrain", "999");
		mapAmbulance.put("Bangladesh", "199");
		mapAmbulance.put("Burma", "999");
		mapAmbulance.put("Cambodia", "119");
		mapAmbulance.put("People's Republic of China", "120");
		mapAmbulance.put("China", "120");
		mapAmbulance.put("East Timor", "112");
		mapAmbulance.put("Hong Kong", "999");
		mapAmbulance.put("India", "102");
		mapAmbulance.put("Indonesia", "118");
		mapAmbulance.put("Iran", "115");
		mapAmbulance.put("Israel", "101");
		mapAmbulance.put("Japan", "119");
		mapAmbulance.put("Jordan", "112");
		mapAmbulance.put("Kazakhstan", "112");
		mapAmbulance.put("Democratic People's Republic of Korea", "119");
		mapAmbulance.put("Republic of Korea", "119");
		mapAmbulance.put("Korea", "119");
		mapAmbulance.put("North Korea", "119");
		mapAmbulance.put("South Korea", "119");
		mapAmbulance.put("Kuwait", "112");
		mapAmbulance.put("Lebanon", "140");
		mapAmbulance.put("Macau", "999");
		mapAmbulance.put("Maldives", "999");
		mapAmbulance.put("Malaysia", "999");
		mapAmbulance.put("Mongolia", "100");
		mapAmbulance.put("Nepal", "102");
		mapAmbulance.put("Oman", "999");
		mapAmbulance.put("Pakistan", "115");
		mapAmbulance.put("Philippines", "117");
		mapAmbulance.put("Qatar", "999");
		mapAmbulance.put("Saudi Arabia", "997");
		mapAmbulance.put("Singapore", "995");
		mapAmbulance.put("Sri Lanka", "110");
		mapAmbulance.put("Syria", "110");
		mapAmbulance.put("Republic of China (Taiwan)", "119");
		mapAmbulance.put("Taiwan", "119");
		mapAmbulance.put("Tajikistan", "112");
		mapAmbulance.put("Thailand", "1669");
		mapAmbulance.put("United Arab Emirates", "999");
		mapAmbulance.put("Vietnam", "115");
		mapAmbulance.put("Albania", "112");
		mapAmbulance.put("Andorra", "112");
		mapAmbulance.put("Armenia", "103");
		mapAmbulance.put("Austria", "112");
		mapAmbulance.put("Azerbaijan", "112");
		mapAmbulance.put("Belarus", "103");
		mapAmbulance.put("Belgium", "112");
		mapAmbulance.put("Bosnia and Herzegovina", "112");
		mapAmbulance.put("Bosnia", "112");
		mapAmbulance.put("Bulgaria", "112");
		mapAmbulance.put("Croatia", "112");
		mapAmbulance.put("Cyprus", "199");
		mapAmbulance.put("Czech Republic", "112");
		mapAmbulance.put("Denmark", "112");
		mapAmbulance.put("Estonia", "112");
		mapAmbulance.put("Faroe Islands", "112");
		mapAmbulance.put("Finland", "112");
		mapAmbulance.put("France", "112");
		mapAmbulance.put("Georgia", "112");
		mapAmbulance.put("Germany", "112");
		mapAmbulance.put("Gibraltar", "999");
		mapAmbulance.put("Greece", "112");
		mapAmbulance.put("Greenland", "112");
		mapAmbulance.put("Hungary", "112");
		mapAmbulance.put("Iceland", "112");
		mapAmbulance.put("Republic of Ireland", "112");
		mapAmbulance.put("Ireland", "112");
		mapAmbulance.put("Italy", "112");
		mapAmbulance.put("Kosovo", "112");
		mapAmbulance.put("Latvia", "112");
		mapAmbulance.put("Lithuania", "112");
		mapAmbulance.put("Luxembourg", "112");
		mapAmbulance.put("Republic of Macedonia", "112");
		mapAmbulance.put("Macedonia", "112");
		mapAmbulance.put("Malta", "112");
		mapAmbulance.put("Moldova", "903");
		mapAmbulance.put("Monaco", "112");
		mapAmbulance.put("Montenegro", "112");
		mapAmbulance.put("Netherlands", "112");
		mapAmbulance.put("Norway", "113");
		mapAmbulance.put("Poland", "112");
		mapAmbulance.put("Portugal", "112");
		mapAmbulance.put("Romania", "112");
		mapAmbulance.put("Russia", "112");
		mapAmbulance.put("San Marino", "118");
		mapAmbulance.put("Serbia", "112");
		mapAmbulance.put("Slovakia", "112");
		mapAmbulance.put("Slovenia", "112");
		mapAmbulance.put("Spain", "112");
		mapAmbulance.put("Sweden", "112");
		mapAmbulance.put("Switzerland", "112");
		mapAmbulance.put("Turkey", "112");
		mapAmbulance.put("Ukraine", "112");
		mapAmbulance.put("United Kingdom", "112");
		mapAmbulance.put("Vatican City", "118");
		mapAmbulance.put("Vatican", "118");
		mapAmbulance.put("Australia", "000");
		mapAmbulance.put("Fiji", "911");
		mapAmbulance.put("New Zealand", "111");
		mapAmbulance.put("Solomon Islands", "999");
		mapAmbulance.put("Vanuatu", "112");
		mapAmbulance.put("Canada", "911");
		mapAmbulance.put("Mexico", "065");
		mapAmbulance.put("Saint Pierre and Miquelon", "15");
		mapAmbulance.put("United States of America", "911");
		mapAmbulance.put("United States", "911");
		mapAmbulance.put("America", "911");
		mapAmbulance.put("Barbados", "511");
		mapAmbulance.put("The Bahamas", "911");
		mapAmbulance.put("Bahamas", "911");
		mapAmbulance.put("Cayman Islands", "911");
		mapAmbulance.put("Costa Rica", "911");
		mapAmbulance.put("Dominican Republic", "911");
		mapAmbulance.put("Guatemala", "120");
		mapAmbulance.put("El Salvador", "911");
		mapAmbulance.put("Haiti", "118");
		mapAmbulance.put("Honduras", "195");
		mapAmbulance.put("Jamaica", "110");
		mapAmbulance.put("Nicaragua", "911");
		mapAmbulance.put("Panama", "911");
		mapAmbulance.put("Trinidad and Tobago", "990");
		mapAmbulance.put("Argentina", "107");
		mapAmbulance.put("Bolivia", "118");
		mapAmbulance.put("Brazil", "192");
		mapAmbulance.put("Chile", "131");
		mapAmbulance.put("Colombia", "112");
		mapAmbulance.put("Ecuador", "911");
		mapAmbulance.put("French Guiana", "112");
		mapAmbulance.put("Guyana", "913");
		mapAmbulance.put("Paraguay", "911");
		mapAmbulance.put("Peru", "117");
		mapAmbulance.put("Suriname", "112");
		mapAmbulance.put("Uruguay", "911");
		mapAmbulance.put("Venezuela", "911");
		return mapAmbulance;
	}

}
