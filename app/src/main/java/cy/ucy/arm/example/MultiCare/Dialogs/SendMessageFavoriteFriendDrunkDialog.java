package cy.ucy.arm.example.MultiCare.Dialogs;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.DataClasses.PhoneContact;
import cy.ucy.arm.example.MultiCare.UIclasses.DrunkLoadFavoriteContactsXML;
import cy.ucy.arm.example.MultiCare.UIclasses.LoadFavoriteContactsXML;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import cy.ucy.arm.example.MultiCare.xmlparser.ModifyXML;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Rafael
 * 
 */
public class SendMessageFavoriteFriendDrunkDialog extends DialogFragment {

	private DrunkLoadFavoriteContactsXML favoriteActivity;
	private LoadFavoriteContactsXML load;
	private String tel;
	private String name;

	public DrunkLoadFavoriteContactsXML getFavoriteActivity() {
		return favoriteActivity;
	}

	public void setFavoriteActivity(
			DrunkLoadFavoriteContactsXML favoriteActivity, String name,
			String tel) {
		this.favoriteActivity = favoriteActivity;
		this.tel = tel;
		this.name = name;
	}

	public void setActivity(LoadFavoriteContactsXML favoriteActivity,
			String name, String tel) {
		this.load = favoriteActivity;
		this.tel = tel;
		this.name = name;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInsanceState) {
		// create a dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Send Message to friend");
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View row = inflater.inflate(R.layout.dialog_send_friend_message_drunk,
				null);

		final TextView textInsertName = (TextView) row
				.findViewById(R.id.textViewFriendName);
		final TextView textInsertPhoneNumber = (TextView) row
				.findViewById(R.id.textViewFriendPhoneNumber);
		final EditText editInsertMessage = (EditText) row
				.findViewById(R.id.editTextInsertMessage);

		textInsertName.setText(name);
		textInsertPhoneNumber.setText(tel);

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(row)
				// Add action buttons
				.setPositiveButton(R.string.answer_ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								if (editInsertMessage.getText().toString()
										.length() != 0) {

									SmsManager smsManager = SmsManager
											.getDefault();
									String number;
									if (tel.contains("+357"))
										number = tel;
									else
										number = tel;

									smsManager.sendTextMessage(number, null,
											editInsertMessage.getText()
													.toString(), null, null);
									clearFocus(editInsertMessage);
									if (load != null)
										Toast.makeText(
												load.getApplicationContext(),
												"Message successfully sent!",
												Toast.LENGTH_SHORT).show();
									else
										Toast.makeText(
												favoriteActivity
														.getApplicationContext(),
												"Message successfully sent!",
												Toast.LENGTH_SHORT).show();
								}
							}
						})
				.setNegativeButton(R.string.answer_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// cancel
								clearFocus(editInsertMessage);
							}
						});
		return builder.create();
	}

	public void clearFocus(EditText insertToDo) {
		insertToDo.clearFocus();
		insertToDo.setCursorVisible(false);
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(insertToDo.getWindowToken(), 0);
	}

}
