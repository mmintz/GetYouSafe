package cy.ucy.arm.example.MultiCare.UIclasses;

import cy.ucy.arm.example.MultiCare.MultiLocker.MyService;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ActivityDrunkNoAccess extends Activity {

private static String taString = "ActivityDrunkNoAccess";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AlertDialog.Builder builder = new AlertDialog.Builder(
				ActivityDrunkNoAccess.this);
		builder.setTitle("Drunk Mode is ON")
				.setMessage("For your safety some applications are locked in order to prevent you from being exposed. To rollback to a previous state click on the Drunk Mode to disable this")
				.setCancelable(false)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {	
								finish();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	
}
