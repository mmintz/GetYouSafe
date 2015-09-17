package cy.ucy.arm.example.MultiCare.FeelingMode;

import java.util.ArrayList;
import java.util.Random;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.DataClasses.Quote;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import cy.ucy.arm.example.MultiCare.xmlparser.ReadXML;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDialogFeelingMode extends Activity {

	private String tag = "ActivityDialogFeelingMode";
	private static boolean got = false;
	private static ArrayList<Quote> quotes;
	private boolean music = false;
	private MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (got == false) {
			got = true;
			ReadXML readXML = new ReadXML(Utils.outputFolder + "/"
					+ Utils.filenameQuotes_1);
			readXML.setMyDir(getFilesDir());
			quotes = readXML.getQuoteData();
			Log.e(tag, "Size:" + quotes.size());
		}

		int number = (new Random().nextInt(quotes.size()));

		AlertDialog.Builder builder = new AlertDialog.Builder(
				ActivityDialogFeelingMode.this);

		// Get the layout inflater
		LayoutInflater inflater = this.getLayoutInflater();
		View row = inflater.inflate(R.layout.play_relaxing_music, null);

		final TextView quote = (TextView) row.findViewById(R.id.textViewQuote);

		final ImageView imageMusic = (ImageView) row
				.findViewById(R.id.imageViewMusic);
		final ImageView imageRefresh = (ImageView) row
				.findViewById(R.id.imageViewRefresh);
		quote.setText(quotes.get(number).getQuote());

		imageRefresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int number = (new Random().nextInt(quotes.size()));
				quote.setText(quotes.get(number).getQuote());
			}
		});

		imageMusic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (music == true) {
					imageMusic.setImageResource(R.drawable.music_off);
					if (mp != null)
						mp.stop();
					music = false;
				} else if (music == false) {
					imageMusic.setImageResource(R.drawable.music_on);
					
					int number = (new Random().nextInt(3));
					
					switch (number) {
					case 0:
						mp = MediaPlayer.create(ActivityDialogFeelingMode.this,
								R.raw.relax_music_1);
						break;
					case 1:
						mp = MediaPlayer.create(ActivityDialogFeelingMode.this,
								R.raw.relax_music_2);
						break;
					case 2:
						mp = MediaPlayer.create(ActivityDialogFeelingMode.this,
								R.raw.relax_music_3);
						break;
					default:
						break;
					}
					
					
					
					
					mp.start();
					mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
						
						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub
							mp.start();
							
						}
					});
					music = true;
				}
			}
		});

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(row);

		builder.setTitle("Quote of the Day!")
				// .setMessage(
				// quotes.get(number).getQuote())
				.setCancelable(false)
				.setPositiveButton("Dismiss",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Toast.makeText(getApplicationContext(),
										"Have a nice day!", Toast.LENGTH_SHORT)
										.show();
								if (mp != null)
									mp.stop();
								finish();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();

	}
}
