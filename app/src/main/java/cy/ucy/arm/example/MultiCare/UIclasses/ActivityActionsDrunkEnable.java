package cy.ucy.arm.example.MultiCare.UIclasses;

import java.util.Random;


import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.R.string;
import cy.ucy.arm.example.MultiCare.MultiLocker.StopServiceActivity;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityActionsDrunkEnable extends Activity {

	private static String taString = "ActivityActionsDrunkEnable";
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	private AlertDialog.Builder proceedBuilder;
	private AlertDialog alertProceed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		builder = new AlertDialog.Builder(ActivityActionsDrunkEnable.this);

		// Get the layout inflater
		LayoutInflater inflater = this.getLayoutInflater();
		View row = inflater
				.inflate(R.layout.activity_action_drunk_enable, null);

		final Button buttonDisableLocker = (Button) row
				.findViewById(R.id.buttonDisableLocker);
		final Button buttonDisableDrunk = (Button) row
				.findViewById(R.id.buttonDisableDrunk);
		final Button buttonDisableBoth = (Button) row
				.findViewById(R.id.buttonDisableBoth);

		buttonDisableDrunk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Utils.saveToFileDrunk(ActivityActionsDrunkEnable.this,
						getFilesDir(), Utils.NOT_DRUNK);
				Utils.makeABeep(ActivityActionsDrunkEnable.this.getApplicationContext());
				
				finish();
			}
		});

		buttonDisableLocker.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ActivityActionsDrunkEnable.this,
						StopServiceActivity.class));
				finish();
			}
		});

		buttonDisableBoth.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Utils.saveToFileDrunk(ActivityActionsDrunkEnable.this,
						getFilesDir(), Utils.NOT_DRUNK);
				Utils.makeABeep(ActivityActionsDrunkEnable.this);
				startActivity(new Intent(ActivityActionsDrunkEnable.this,
						StopServiceActivity.class));
				finish();
			}
		});

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(row);
		builder.setTitle("Decide what to disable")
				.setMessage(
						"**If you choose to disable MultiLocker and you give a wrong password, MultiLocker will remain active**")
				.setCancelable(false)
				.setPositiveButton("Dismiss",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								alert.dismiss();
								finish();
							}
						});

		
		
		/*ImageView im;
		im = (ImageView)findViewById(R.id.imageCaptcha);
		Captcha c = new TextCaptcha(300, 100, 5, cy.ucy.arm.example.MultiCare.UIclasses.TextCaptcha.TextOptions.NUMBERS_AND_LETTERS);
		im.setImageBitmap(c.image);
		im.setLayoutParams(new LinearLayout.LayoutParams(c.getWidth() *2, c.getHeight() *2));
		*/
		
	
		Intent i = new Intent(this, Manager.class);
		startActivityForResult(i, 1);


		
		
		
		

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (alert != null && alert.isShowing())
			alert.dismiss();
		if (alertProceed != null && alertProceed.isShowing())
			alertProceed.dismiss();
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            String result=data.getStringExtra("result");
	            if(result.equals("ok"))
	            	{
	            	alert = builder.create();
	            	alert.show();
	            	}
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}//onActi

}
