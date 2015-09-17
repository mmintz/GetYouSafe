package cy.ucy.arm.example.MultiCare.Widget;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.Dialogs.TimePickerFragmentStart;
import cy.ucy.arm.example.MultiCare.FeelingMode.ActivityDialogFeelingMode;
import cy.ucy.arm.example.MultiCare.MultiLocker.MyService;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class NotifyDrunkUser extends Activity {

	private static String taString = "NotifyDrunkUser";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Utils.saveToFileDrunk(this, this.getFilesDir(),Utils.DRUNK);
//		AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
//		manager.updateAppWidget(new ComponentName(this, MyWidgetProvider.class), new RemoteViews(this.getPackageName(), R.layout.widget_layout));
		Log.e(taString, "save");
		
		startService(new Intent(NotifyDrunkUser.this, MyService.class));

		Utils.makeABeep(NotifyDrunkUser.this);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				NotifyDrunkUser.this);
		builder.setTitle("Drunk Mode is ON")
				.setMessage("For your safety some applications are locked in order to prevent you from being exposed. To rollback to a previous state click on the Disable Drunk Mode.")
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
