package cy.ucy.arm.example.MultiCare.MultiLocker;

import java.util.ArrayList;
import java.util.HashMap;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

/**
 * FetchContacts Asynchronous class. This class is an AsyncTask<Void, Void,
 * Void>. We use it to load from the phone's contacts provider all of the
 * contacts at the background of the UI thread. Until all of the contacts are
 * eventually loaded a progress bar is shown to the user in order to wait
 * 
 * @author Rafael
 * 
 */
public class fetchPackages extends AsyncTask<Void, Void, Void> {

	// ProgressDialog to prompt the user to wait
	private ProgressDialog progressDialog;
	// Reference of the mainActivity in order to have access to some of the
	// private objects of the main activity
	private MainActivity mainActivity;
	// This object is used in order to show to the user all of his contacts.
	// Each time we resolve a contact we add it to this array list
	//private ArrayList<PhoneContact> phoneContacts = new ArrayList<PhoneContact>();
	private HashMap<String, Boolean> existPhoneNumbers = new HashMap<String, Boolean>();

	/**
	 * This method is used to set the mainActivity Object
	 * 
	 * @param mainActivity
	 */
	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
		
	}

	@Override
	protected Void doInBackground(Void... args) {
		// TODO Auto-generated method stub
		mainActivity.fetchpackages();
		return null;
	}

	/**
	 * This method is being used each time we want to update the UI thread.
	 * Actually when we want to public the progress of our job
	 * 
	 * @param progress
	 */
	protected void onProgressUpdate(Void progress) {
	}

	/**
	 * This method is used at the end of the execution of the doInBackground
	 * method. Finalize our job!!
	 */
	protected void onPostExecute(Void result) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		mainActivity.getAdapter().notifyDataSetChanged();
		//mainActivity.listView.setVisibility(View.VISIBLE);
	}

	/**
	 * This method is used before the execution of the doInBackground method.
	 * Some things have to be done before the doInBackground process on the UI
	 * thread
	 */
	protected void onPreExecute() {
		//showProgressDialog("Please wait...",
			//	"Please be patient. Loading Applications...");
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
		progressDialog = new ProgressDialog(mainActivity);

		progressDialog.setTitle(title); // title

		progressDialog.setMessage(message); // message

		progressDialog.setCancelable(false);

		progressDialog.show();
	}

	

}
