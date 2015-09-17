package cy.ucy.arm.example.MultiCare.Loaders;

import java.util.ArrayList;
import java.util.HashMap;

import cy.ucy.arm.example.MultiCare.DataClasses.PhoneContact;
import cy.ucy.arm.example.MultiCare.UIclasses.LoadContactsActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * FetchContacts Asynchronous class. This class is an AsyncTask<Void, Void,
 * Void>. We use it to load from the phone's contacts provider all of the
 * contacts at the background of the UI thread. Until all of the contacts are
 * eventually loaded a progress bar is shown to the user in order to wait
 * 
 * @author Rafael
 * 
 */
public class FetchContacts extends AsyncTask<Void, Void, Void> {

	// ProgressDialog to prompt the user to wait
	private ProgressDialog progressDialog;
	// Reference of the mainActivity in order to have access to some of the
	// private objects of the main activity
	private LoadContactsActivity mainActivity;
	// This object is used in order to show to the user all of his contacts.
	// Each time we resolve a contact we add it to this array list
	private ArrayList<PhoneContact> phoneContacts = new ArrayList<PhoneContact>();
	private HashMap<String, Boolean> existPhoneNumbers = new HashMap<String, Boolean>();

	/**
	 * This method is used to set the mainActivity Object
	 * 
	 * @param mainActivity
	 */
	public void setMainActivity(LoadContactsActivity mainActivity) {
		this.mainActivity = mainActivity;
		this.phoneContacts = mainActivity.getDataPhoneContacts();
	}

	@Override
	protected Void doInBackground(Void... args) {
		// TODO Auto-generated method stub
		fetchContacts();
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
		mainActivity.getAllContactsAdapter().notifyDataSetChanged();
	}

	/**
	 * This method is used before the execution of the doInBackground method.
	 * Some things have to be done before the doInBackground process on the UI
	 * thread
	 */
	protected void onPreExecute() {
		showProgressDialog("Please wait...",
				"Please be patient. Loading your contacts...");
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

	/**
	 * This method is used in order to fetch the contacts from the android
	 * device
	 */
	public void fetchContacts() {

		String phoneNumber = null;
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

		ContentResolver contentResolver = mainActivity.getContentResolver();

		Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null,
				DISPLAY_NAME);
		int find_out = 0;
		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {

				String contact_id = cursor
						.getString(cursor.getColumnIndex(_ID));
				String name = cursor.getString(cursor
						.getColumnIndex(DISPLAY_NAME));

				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(HAS_PHONE_NUMBER)));

				if (hasPhoneNumber > 0) {

					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(
							PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?",
							new String[] { contact_id }, null);

					find_out++;

					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor
								.getColumnIndex(NUMBER));

						// output.append("\n Phone number:" + phoneNumber);
						if (!existPhoneNumbers.containsKey(phoneNumber)) {
							existPhoneNumbers.put(phoneNumber, true);
							phoneContacts.add(new PhoneContact("-1" ,name,
									phoneNumber));
						}
						// break;
					}

					phoneCursor.close();

					// // Query and loop for every email of the contact
					// Cursor emailCursor =
					// contentResolver.query(EmailCONTENT_URI, null,
					// EmailCONTACT_ID+ " = ?", new String[] { contact_id },
					// null);
					//
					// while (emailCursor.moveToNext()) {
					//
					// email =
					// emailCursor.getString(emailCursor.getColumnIndex(DATA));
					//
					// output.append("\nEmail:" + email);
					//
					//
					// }
					//
					// emailCursor.close();
				}
			}

		}

		Log.e("TOTAL:", find_out + "");
	}

}
