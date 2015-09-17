package cy.ucy.arm.example.MultiCare.Widget;

import cy.ucy.arm.example.MultiCare.MultiLocker.MainActivity;
import cy.ucy.arm.example.MultiCare.UIclasses.ActivityDrunkNoAccess;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class ActivityDecideDrunk  extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Utils.getAnswer(getFilesDir())) {
			Intent i = new Intent(this,ActivityDrunkNoAccess.class);
			startActivity(i);
		}
		else {
			Intent i = new Intent(this,MainActivity.class);
			startActivity(i);
		}
	}
	

}
