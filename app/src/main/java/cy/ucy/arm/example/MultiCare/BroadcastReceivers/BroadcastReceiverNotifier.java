package cy.ucy.arm.example.MultiCare.BroadcastReceivers;

import java.util.ArrayList;
import java.util.Random;

import cy.ucy.arm.example.MultiCare.DataClasses.Quote;
import cy.ucy.arm.example.MultiCare.FeelingMode.ActivityDialogFeelingMode;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import cy.ucy.arm.example.MultiCare.xmlparser.ReadXML;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * This class extends the <b>BroadcastReceiver</b> and is being used as a
 * receiver in the app's manifest We declared that this class will respond to
 * BROADCAST_TEST.PING intents. So when we retrieve these intents the onReceive
 * method is called in order to do sth when we get this intent
 * 
 * @author Rafael
 * 
 */
public class BroadcastReceiverNotifier extends BroadcastReceiver {

	public static final String tag = "BroadcastReceiverNotifier";

	/**
	 * This method is being called when we receive a BROADCAST_TEST.PING intent.
	 * When we receive this intent we simply make a Toast to tell that the
	 * intent was received
	 * 
	 * @param Context
	 *            context
	 * @param Intent
	 *            intentI
	 */
	@Override
	public void onReceive(final Context context, Intent intentI) {
		// TODO Auto-generated method stub

		Intent i = new Intent(context, ActivityDialogFeelingMode.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

}
