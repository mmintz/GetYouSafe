package cy.ucy.arm.example.MultiCare.Maps;

import java.util.Random;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.FeelingMode.ActivityDialogFeelingMode;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import cy.ucy.arm.example.MultiCare.xmlparser.ReadXML;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MapOptions extends Activity {

	AlertDialog.Builder builder;
	AlertDialog alert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		builder = new AlertDialog.Builder(MapOptions.this);

		// Get the layout inflater
		LayoutInflater inflater = this.getLayoutInflater();
		View row = inflater.inflate(R.layout.map_options, null);

		Button takeMeHome = (Button) row.findViewById(R.id.buttonTakeMeHome);
		Button routePoints = (Button) row.findViewById(R.id.buttonRoutePoints);
		Button routeAddresses = (Button) row
				.findViewById(R.id.buttonRouteAddresses);

		takeMeHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MapOptions.this, DrunkMapActivity.class);
				startActivity(i);
			}
		});

		routePoints.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MapOptions.this, MapActivity.class);
				startActivity(i);
			}
		});

		routeAddresses.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MapOptions.this,
						MapActivityRouteAddresses.class);
				startActivity(i);
			}
		});

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(row);
		builder.setTitle("Map Options")
				.setMessage("Select one of the following:")
				.setCancelable(false)
				.setPositiveButton("Dismiss",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						});

		alert = builder.create();
		alert.show();

	}

	@Override
	protected void onPause() {
		super.onPause();
		alert.dismiss();
	}
}
