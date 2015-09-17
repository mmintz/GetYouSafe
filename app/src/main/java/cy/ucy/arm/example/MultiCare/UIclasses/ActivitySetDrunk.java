package cy.ucy.arm.example.MultiCare.UIclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.Dialogs.TimePickerFragmentStart;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ActivitySetDrunk extends FragmentActivity {

	private String tag = "ActivitySetDrunk";
	TimePickerFragmentStart pickTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pickTime = new TimePickerFragmentStart();
		pickTime.setContext(this);
		pickTime.setCancelable(false);
		pickTime.show(getSupportFragmentManager(), "");
		
		
	}

}
