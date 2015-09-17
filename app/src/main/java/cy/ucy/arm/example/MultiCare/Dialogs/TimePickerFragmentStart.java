package cy.ucy.arm.example.MultiCare.Dialogs;

import java.util.Calendar;

import cy.ucy.arm.example.MultiCare.MultiLocker.MyService;
import cy.ucy.arm.example.MultiCare.UIclasses.ActivitySetDrunk;
import cy.ucy.arm.example.MultiCare.UIclasses.DisableDrunkMode;
import cy.ucy.arm.example.MultiCare.UIclasses.MainActivity;
import cy.ucy.arm.example.MultiCare.Widget.NotifyDrunkUser;
import cy.ucy.arm.example.MultiCare.utils.Utils;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimePickerFragmentStart extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	private ActivitySetDrunk context;
	public static PendingIntent on;

	public ActivitySetDrunk getContext() {
		return context;
	}

	public void setContext(ActivitySetDrunk context) {
		this.context = context;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		TimePickerDialog t = new TimePickerDialog(getActivity(), this, hour,
				minute, DateFormat.is24HourFormat(getActivity()));
		t.setTitle("Drunk Mode");
		t.setMessage("Set Time to Activate Drunk Mode");
		t.setCancelable(false);

		t.setCanceledOnTouchOutside(false);

		t.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (which == DialogInterface.BUTTON_NEGATIVE) {
							context.finish();
						}
					}
				});

		return t;
	}

	@Override
	public void onTimeSet(TimePicker arg0, int hourOfDay, int minute) {
		// TODO Auto-generated method stub

		Calendar check = Calendar.getInstance();
		int hourToCheck = check.get(Calendar.HOUR_OF_DAY);
		int minuteToCheck = check.get(Calendar.MINUTE);

		if ((hourOfDay >= hourToCheck && minute > minuteToCheck)|| hourOfDay >=hourToCheck) {

			/*Toast.makeText(context, "Your alarm will be set shortly",
					Toast.LENGTH_SHORT).show();*/

			Intent intentDrunk = new Intent(context, NotifyDrunkUser.class);
			PendingIntent pendingIntentLoadDrunkMode = PendingIntent
					.getActivity(context, 0, intentDrunk, 0);
			on = pendingIntentLoadDrunkMode;
			Calendar timeStart = Calendar.getInstance();
			timeStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
			timeStart.set(Calendar.MINUTE, minute);
			
			
			TimePickerFragmentEnd pickTime = new TimePickerFragmentEnd();
			pickTime.setOnIntent(timeStart);
			pickTime.setContext(context);
			pickTime.setCancelable(false);
			pickTime.show(this.getFragmentManager(), "");
			this.dismiss();
			
		} else {
			Toast.makeText(context, "Please Select a Valid Time",
					Toast.LENGTH_SHORT).show();
			context.finish();
			this.dismiss();
		}
	}

}