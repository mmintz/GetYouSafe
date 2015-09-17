/**
 * 
 */
package cy.ucy.arm.example.MultiCare.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import cy.ucy.arm.example.MultiCare.R;
import cy.ucy.arm.example.MultiCare.UIclasses.DisableDrunkMode;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * @author Rafael
 * 
 */
public class Utils {
	public static String filenameFavoriteContacts_1 = "my_friends.xml";
	public static String filenameFavoriteContacts = "/my_friends.xml";
	public static String filenameQuotes_1 = "quotes.xml";
	public static String filenameQuotes = "/quotes.xml";
	public static String outputFolder = "/Favorite_Contacts";
	public static String appName = " *MULTICARE*\n";
	public static String version = "Version 1.0\n";
	public static String confFile = "conf.txt";
	public static String dearFriend = "Dear friend ";
	public static String folderHomeLocation = "/homeLocation";
	public static String mainBody = "I am in danger!\nPlease come and find me at this location:";
	public static String end = "****************";
	public static String homeLocationFile = "/homeLocation";
	public static String SATTELITE = "Sattelite View";
	public static String ROADMAP = "RoadMap View";
	public static String HYBRID = "Hybrid View";
	public static String TERRAIN = "Terrain View";
	public static String contacts_package = "com.android.contacts";
	public static String messages_package = "com.android.mms";
	public static String email_package = "com.android.email";
	public static String fileSeperator = "/";
	public static String DRUNK = "DRUNK";
	public static String NOT_DRUNK = "NOT_DRUNK";
	public static long timeToUnlock = 1;

	// public static long timeToUnlock = 18000000
	public static void saveToFileDrunk(Activity activity, File getFilesDir,
			String what) {
		try {
			String filename = Utils.confFile;
			File myFile = new File(getFilesDir + filename);
			if (myFile.exists())
				myFile.delete();
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.write(what);
			myOutWriter.close();
			fOut.close();
			// Toast.makeText(activity, "Done saving file -->" + what,
			// Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean getAnswer(File getFilesDir) {
		String filename = Utils.confFile;
		File myFile = new File(getFilesDir + filename);
		String drunk = null;
		if (myFile.exists()) {
			FileInputStream fIn;

			try {
				fIn = new FileInputStream(myFile);
				BufferedReader myReader = new BufferedReader(
						new InputStreamReader(fIn));
				drunk = myReader.readLine();
				myReader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (drunk.equals(Utils.DRUNK))
			return true;
		else
			return false;

	}

	public static void makeABeep(Context c) {
		MediaPlayer mp;
		mp = MediaPlayer.create(c, R.raw.beep);
		mp.start();
		mp.reset();
		mp =  MediaPlayer.create(c, R.raw.beep);
		mp.start();
		
	}

}
