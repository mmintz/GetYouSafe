package cy.ucy.arm.example.MultiCare.adapters;

import java.util.ArrayList;
import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.DataClasses.PhoneContact;
import cy.ucy.arm.example.MultiCare.UIclasses.LoadContactsActivity;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PhoneContactArrayAdapter extends ArrayAdapter<PhoneContact> {

	private String tagString = "PhoneContactArrayAdapter";
	private Context context;
	private int layoutResourceId;
	private ArrayList<PhoneContact> data;
	private ArrayList<PhoneContact> selectedContacts = new ArrayList<PhoneContact>();
	private FragmentManager manager;
	private LoadContactsActivity mainActivity;
	private String filename;

	public LoadContactsActivity getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(LoadContactsActivity mainActivity) {
		this.mainActivity = mainActivity;
		this.mainActivity.setDataToImport(selectedContacts);
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public FragmentManager getManager() {
		return manager;
	}

	public void setManager(FragmentManager manager) {
		this.manager = manager;
	}


	/**
	 * class ToDoHolder This class is used to hold the ids of an item of the
	 * listview!!!
	 * 
	 * @author Rafael
	 * 
	 */
	public static class PhoneContactHolder {
		TextView textPhoneContactName;
		TextView textPhoneContactNumber;
		CheckBox checkSelected;
	}

	/**
	 * Constructor of the ToDoArrayAdapter
	 * 
	 * @param context
	 * @param layoutResourceId
	 * @param data
	 */
	public PhoneContactArrayAdapter(Context context, int layoutResourceId,
			ArrayList<PhoneContact> data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final PhoneContactHolder holder;
		final PhoneContact obj = data.get(position);

		// get all the ids only once!! and store it in the holder class. then
		// each time a new row will be created the ids will exist in the holder
		// object
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new PhoneContactHolder();

			holder.textPhoneContactName = (TextView) row
					.findViewById(R.id.textContactName);
			holder.textPhoneContactNumber = (TextView) row
					.findViewById(R.id.textContactPhoneNumber);
			holder.checkSelected = (CheckBox) row
					.findViewById(R.id.checkBoxToSelect);
			row.setTag(holder);
		} else {

			holder = (PhoneContactHolder) row.getTag();
		}

		holder.textPhoneContactName.setText(obj.getContactName());
		holder.textPhoneContactNumber.setText("\t" + obj.getPhoneNumber());

		row.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holder.checkSelected.isChecked()) {
					holder.checkSelected.setChecked(false);
				} else {
					holder.checkSelected.setChecked(true);
				}
				Log.e(tagString, "Hello!");
				
			}
		});

		holder.checkSelected
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						// IF THE CHECK BUTTON IS CHECKED THE TODO WILL BE
						// PLACED IN THE dataToBeDeleted in order to be deleted
						// in the multiple deleted option
						if (buttonView.isChecked()) {
							selectedContacts.add(obj);
						} else {
							selectedContacts.remove(obj);
						}

					}
				});

		return row;

	}
}
