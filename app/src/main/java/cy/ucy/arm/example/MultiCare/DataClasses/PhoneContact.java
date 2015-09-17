package cy.ucy.arm.example.MultiCare.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class PhoneContact implements Parcelable {

	private String _id;
	private String contactName ;
	private String phoneNumber ; 
	
	
	public String getContactName() {
		return contactName;
	}


	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public PhoneContact (String _id, String contactName ,  String phoneNumber) {
		this._id = _id;
		this.contactName = contactName;
		this.phoneNumber = phoneNumber;
	}
	
	public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(_id);
		parcel.writeString(contactName);
		parcel.writeString(phoneNumber);
	}

	public static final Parcelable.Creator<PhoneContact> CREATOR = new Parcelable.Creator<PhoneContact>() {

		@Override
		public PhoneContact createFromParcel(Parcel source) {
			// Must read values in the same order as they were placed in
			String _id = source.readString();
			String contactName = source.readString();
			String phoneNumber = source.readString();
			PhoneContact myPhoneContact = new PhoneContact(_id, contactName,phoneNumber);
			return myPhoneContact;
		}

		@Override
		public PhoneContact[] newArray(int size) {
			return new PhoneContact[size];
		}

	};
	
	
}
