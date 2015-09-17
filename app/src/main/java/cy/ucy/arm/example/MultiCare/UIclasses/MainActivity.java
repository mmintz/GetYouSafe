package cy.ucy.arm.example.MultiCare.UIclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.BroadcastReceivers.BroadcastReceiverNotifier;
import cy.ucy.arm.example.MultiCare.Maps.DrunkMapActivity;
import cy.ucy.arm.example.MultiCare.Maps.MapActivity;
import cy.ucy.arm.example.MultiCare.Maps.SetHomeLocationMapActivity;
import cy.ucy.arm.example.MultiCare.MultiLocker.MyService;
import cy.ucy.arm.example.MultiCare.adapters.ImageAdapter;
import cy.ucy.arm.example.MultiCare.utils.Utils;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private File myInteralStorge;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		
		myInteralStorge = getFilesDir();
		prepareSDCard();
		String filenameDrunk = Utils.confFile;
		File myFileDrunk = new File(getFilesDir() + filenameDrunk);
		
		if (!myFileDrunk.exists()) {
			Utils.saveToFileDrunk(this, getFilesDir(), Utils.NOT_DRUNK);
		}

		// drunk
		if (Utils.getAnswer(getFilesDir())) {
			Intent i = new Intent(MainActivity.this, MainActivityDrunk.class);
			startActivity(i);
			finish();
		} else {
			Intent i = new Intent(MainActivity.this, MainActivityNotDrunk.class);
			startActivity(i);
			finish();
		}

	}

	/**
	 * Check if the files or folders already exist. If something does not exist
	 * we created it
	 */
	private void prepareSDCard() {
		String folderPath = Utils.outputFolder;
		String filePath = Utils.outputFolder + Utils.filenameFavoriteContacts;
		File fileFolder = new File(this.myInteralStorge + folderPath);
		File fileXML = new File(this.myInteralStorge + filePath);
		if (!fileFolder.exists()) {
			if (fileFolder.mkdirs())
//				Toast.makeText(
//						getApplicationContext(),
//						"Folder " + Utils.outputFolder
//								+ " created successfully", Toast.LENGTH_SHORT)
//						.show();
			copyAssets(Utils.filenameFavoriteContacts_1);
			copyAssets(Utils.filenameQuotes_1);
		} else if (!fileXML.exists()) {
			copyAssets(Utils.filenameFavoriteContacts_1);
			copyAssets(Utils.filenameQuotes_1);
//			Toast.makeText(
//					getApplicationContext(),
//					"File " + Utils.filenameFavoriteContacts
//							+ " created successfully", Toast.LENGTH_SHORT)
//					.show();
		}

	}

	/**
	 * This method is used to copy the xml file from the assets t the internal
	 * storage of the app
	 */
	private void copyAssets(String filename) {
		AssetManager assetManager = getAssets();
		InputStream in = null;
		OutputStream out = null;
		try {
			in = assetManager.open(filename);
			File outFile = new File(this.myInteralStorge + Utils.outputFolder
					+ "/" + filename);
			out = new FileOutputStream(outFile);
			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (IOException e) {
			Log.e("tag", "Failed to copy asset file: " + filename, e);
		}
	}

	/**
	 * This method is used to copy the file
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	// ===================================================================================================
	// getters and setters of the objects
	// ===================================================================================================

	public File getMyInteralStorge() {
		return myInteralStorge;
	}

	public void setMyInteralStorge(File myInteralStorge) {
		this.myInteralStorge = myInteralStorge;
	}

}
