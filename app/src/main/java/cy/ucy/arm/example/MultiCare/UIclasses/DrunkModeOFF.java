package cy.ucy.arm.example.MultiCare.UIclasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class DrunkModeOFF extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		showAlert(this);
	}
	
	public void showAlert(Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(
				context);
		builder.setTitle("Drunk Mode is OFF")
				.setMessage(
						"Drunk Mode was disabled successfully. You now have full phone's functionalities. We hope that we have helped you")
				.setCancelable(false)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								
								finish();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
}
