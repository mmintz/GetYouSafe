package cy.ucy.arm.example.MultiCare.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.DataClasses.PhoneContact;
import cy.ucy.arm.example.MultiCare.Dialogs.SendMessageFavoriteFriendDrunkDialog;
import cy.ucy.arm.example.MultiCare.GetLonLat.GetLonLat;
import cy.ucy.arm.example.MultiCare.UIclasses.DrunkLoadFavoriteContactsXML;
import cy.ucy.arm.example.MultiCare.UIclasses.LoadFavoriteContactsXML;
import cy.ucy.arm.example.MultiCare.utils.Utils;

public class DrunkFavoriteContactArrayAdapter extends
		ArrayAdapter<PhoneContact> {

	private String tagString = "FavoriteContactArrayAdapter";
	private Context context;
	private int layoutResourceId;
	private ArrayList<PhoneContact> data;
	private DrunkLoadFavoriteContactsXML favoritesActivity;

	public DrunkLoadFavoriteContactsXML getFavoritesActivity() {
		return favoritesActivity;
	}

	public void setFavoritesActivity(
			DrunkLoadFavoriteContactsXML favoritesActivity) {
		this.favoritesActivity = favoritesActivity;
	}

	/**
	 * class ToDoHolder This class is used to hold the ids of an item of the
	 * listview!!!
	 * 
	 * @author Rafael
	 * 
	 */
	public static class FavoritePhoneContactHolder {
		TextView textPhoneContactName;
		TextView textPhoneContactNumber;
		ImageView callImage;
		ImageView sendSMSimage;
	}

	/**
	 * Constructor of the ToDoArrayAdapter
	 * 
	 * @param context
	 * @param layoutResourceId
	 * @param data
	 */
	public DrunkFavoriteContactArrayAdapter(Context context,
			int layoutResourceId, ArrayList<PhoneContact> data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final FavoritePhoneContactHolder holder;
		final PhoneContact obj = data.get(position);

		// get all the ids only once!! and store it in the holder class. then
		// each time a new row will be created the ids will exist in the holder
		// object
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new FavoritePhoneContactHolder();
			holder.textPhoneContactName = (TextView) row
					.findViewById(R.id.textContactName);
			holder.textPhoneContactNumber = (TextView) row
					.findViewById(R.id.textContactPhoneNumber);
			holder.callImage = (ImageView) row.findViewById(R.id.imageViewCall);
			holder.sendSMSimage = (ImageView) row
					.findViewById(R.id.imageViewSendSms);

			row.setTag(holder);
		} else {

			holder = (FavoritePhoneContactHolder) row.getTag();
		}

		holder.textPhoneContactName.setText(obj.getContactName());
		holder.textPhoneContactNumber.setText("\t" + obj.getPhoneNumber());

		holder.callImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Toast.makeText(context, "CALL!", Toast.LENGTH_SHORT).show();
				/**
				 * create an intent Intent.ACTION_DIAL.. With this intent every
				 * app that has this intent-filter is a candidate to respond
				 * this intent (message). If we have multiple apps that can
				 * respond to this intent a list will be shown to us in order to
				 * choose to which app will the intent be delivered
				 */
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + obj.getPhoneNumber()));
				// start activity (is going to show us a list as was described
				// above) or start immediately the default app
				favoritesActivity.startActivity(intent);
			}
		});

		holder.sendSMSimage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Toast.makeText(context, "SMS!", Toast.LENGTH_SHORT).show();
				GetLonLat get = new GetLonLat(favoritesActivity);
				get.prepareLonLat();
				String toSend = Utils.appName + Utils.version
						+ Utils.dearFriend + obj.getContactName() + ",\n"
						+ Utils.mainBody;
				String myName = " ";
				String lon = "lon: " + get.getLon() + "\n";
				String lat = "lat: " + get.getLat() + "\n";
				String finString = toSend + lon + lat;
				SmsManager smsManager = SmsManager.getDefault();
				String number;
				if (obj.getPhoneNumber().contains("+357"))
					number = obj.getPhoneNumber();
				else
					number = "+357" + obj.getPhoneNumber();
				smsManager.sendTextMessage(number, null, finString, null, null);
				Toast.makeText(context, "Message Successfully Sent", Toast.LENGTH_SHORT).show();


			}
		});

		row.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "Long click:" + obj.getContactName(), Toast.LENGTH_SHORT).show();
				SendMessageFavoriteFriendDrunkDialog dialog = new SendMessageFavoriteFriendDrunkDialog();
				dialog.setFavoriteActivity(favoritesActivity, obj.getContactName(),obj.getPhoneNumber());
				dialog.show(favoritesActivity.getFragmentManager(), "");
				return false;
			}
		});
		
		
		return row;

	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public int getLayoutResourceId() {
		return layoutResourceId;
	}

	public void setLayoutResourceId(int layoutResourceId) {
		this.layoutResourceId = layoutResourceId;
	}

	public ArrayList<PhoneContact> getData() {
		return data;
	}

	public void setData(ArrayList<PhoneContact> data) {
		this.data = data;
	}

}
