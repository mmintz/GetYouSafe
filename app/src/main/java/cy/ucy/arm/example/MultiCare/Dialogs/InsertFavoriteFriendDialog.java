package cy.ucy.arm.example.MultiCare.Dialogs;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.DataClasses.PhoneContact;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Rafael
 * 
 */
public class InsertFavoriteFriendDialog extends DialogFragment {

	private LoadFavoriteContactsXML favoriteActivity;

	public LoadFavoriteContactsXML getFavoriteActivity() {
		return favoriteActivity;
	}

	public void setFavoriteActivity(LoadFavoriteContactsXML favoriteActivity) {
		this.favoriteActivity = favoriteActivity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInsanceState) {
		// create a dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.insert_dialog_title);
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View row = inflater.inflate(R.layout.insert_friend_in_need, null);

		final EditText editInsertName = (EditText) row
				.findViewById(R.id.editTextInsertName);
		final EditText editInsertPhoneNumber = (EditText) row
				.findViewById(R.id.editTextInsertPhoneNumber);

		editInsertPhoneNumber
				.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(row)
				// Add action buttons
				.setPositiveButton(R.string.answer_ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								if (editInsertName.getText().toString().length()!=0  && editInsertPhoneNumber.getText().toString().length()!=0
										) {
									Toast.makeText(favoriteActivity, "Friend Loaded Successfully", Toast.LENGTH_SHORT).show();
									PhoneContact contact = new PhoneContact("-1", editInsertName.getText().toString(), editInsertPhoneNumber.getText().toString());
									ModifyXML modifier = new ModifyXML(Utils.outputFolder
											+ Utils.filenameFavoriteContacts);
									modifier.setMyDir(favoriteActivity.getFilesDir());
									modifier.addElement(contact);
									favoriteActivity.getDataFavoriteContacts().add(contact);
									favoriteActivity.getAdapter().notifyDataSetChanged();
								}
								else
									Toast.makeText(favoriteActivity, "Please insert Correct Values", Toast.LENGTH_SHORT).show();
								clearFocus(editInsertName);
								clearFocus(editInsertPhoneNumber);
							}
						})
				.setNegativeButton(R.string.answer_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// cancel
								clearFocus(editInsertName);
								clearFocus(editInsertPhoneNumber);
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
