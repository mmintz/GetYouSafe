package cy.ucy.arm.example.MultiCare.Widget;

import cy.ucy.arm.example.MultiCare.MultiLocker.MyService;
import cy.ucy.arm.example.MultiCare.UIclasses.ActivityActionsDrunkEnable;
import cy.ucy.arm.example.MultiCare.UIclasses.ActivitySetDrunk;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WidgetDrunkMode extends Activity {

	private static String taString = "WidgetDrunkMode";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Utils.getAnswer(this.getFilesDir())) {
			startActivity(new Intent(WidgetDrunkMode.this, ActivityActionsDrunkEnable.class));
		}
		else {
			startActivity(new Intent(WidgetDrunkMode.this, ActivitySetDrunk.class));
		}

	}

}
