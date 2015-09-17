package cy.ucy.arm.example.MultiCare.UIclasses;

import java.util.ArrayList;
import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.DataClasses.PhoneContact;
import cy.ucy.arm.example.MultiCare.Loaders.FetchContacts;
import cy.ucy.arm.example.MultiCare.adapters.PhoneContactArrayAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This is the MainActivity class for our app.
 * 
 * @author Rafael
 * 
 */
public class LoadContactsActivity extends Activity {

	// This ArrayList is used in order to get all the contact of the android
	// device in order to present to the user all of his contact and give him
	// the opportunity to choose which of them are going to be in his favorite
	// friend list.
	private ArrayList<PhoneContact> dataPhoneContacts = new ArrayList<PhoneContact>();
	private PhoneContactArrayAdapter allContactsAdapter ; 
	private ListView allContactsListView;
	private boolean fetchContactsDecide = false;
	private ArrayList<PhoneContact> dataToImport = new ArrayList<PhoneContact>();  


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_contacts);
		Button fetchContacts = (Button) findViewById(R.id.button_fetch_contacts);
		Button importContacts = (Button) findViewById(R.id.button_import_contacts);
		
		// get the listview
		allContactsListView = (ListView) findViewById(R.id.listViewShowContacts);
		int resID = R.layout.listview_item_row_phone_contacts;
		allContactsAdapter = new PhoneContactArrayAdapter(this, resID,
				dataPhoneContacts);
		allContactsListView.setAdapter(allContactsAdapter);
		allContactsAdapter.setManager(null);
		allContactsAdapter.setMainActivity(this);
		allContactsAdapter.setFilename("");
		allContactsListView.setAdapter(allContactsAdapter);

		fetchContacts.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!fetchContactsDecide) {
					FetchContacts fetch = new FetchContacts();
					fetch.setMainActivity(LoadContactsActivity.this);
					fetchContactsDecide = true;
					fetch.execute();
				}
				else {
					Toast.makeText(getApplicationContext(), "Contacts already fetched!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		importContacts.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent returnBack = new Intent();
	                returnBack.putParcelableArrayListExtra("dataToAdd",  dataToImport);
	                setResult(RESULT_OK, returnBack);	
	                finish();
			}
		});
	}

	
	/**
	 * Called when the activity has detected the user's press of the back key.
	 * Prompts the user to confirm the exit of the application
	 * @Override onBackPressed() in Activity
	 */
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
		       .setCancelable(false)
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       })
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                Intent returnBack = new Intent();
		                returnBack.putParcelableArrayListExtra("dataToAdd",  dataToImport);
		                setResult(RESULT_OK, returnBack);
		                finish();
		           }
		       })
		       ;
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/**
	 * This method is used to get all the phone contacts in a single ArrayList.
	 * Later this ArrayList is going to be used as a dataset of an ArrayAdapter
	 * to present to the user all of his contacts
	 * 
	 * @return ArrayList<PhoneContact>
	 */
	public ArrayList<PhoneContact> getDataPhoneContacts() {
		return dataPhoneContacts;
	}

	public void setDataPhoneContacts(ArrayList<PhoneContact> dataPhoneContacts) {
		this.dataPhoneContacts = dataPhoneContacts;
	}

	public PhoneContactArrayAdapter getAllContactsAdapter() {
		return allContactsAdapter;
	}

	public void setAllContactsAdapter(
			PhoneContactArrayAdapter allContactsAdapter) {
		this.allContactsAdapter = allContactsAdapter;
	}

	public ListView getAllContactsListView() {
		return allContactsListView;
	}

	public void setAllContactsListView(ListView allContactsListView) {
		this.allContactsListView = allContactsListView;
	}
	public ArrayList<PhoneContact> getDataToImport() {
		return dataToImport;
	}

	public void setDataToImport(ArrayList<PhoneContact> dataToImport) {
		this.dataToImport = dataToImport;
	}

	
}