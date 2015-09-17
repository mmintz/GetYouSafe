package cy.ucy.arm.example.MultiCare.MultiLocker;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import cy.ucy.arm.example.MultiCare.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StopServiceMultiLocker extends Activity implements View.OnClickListener {

	
	EditText passwd;
	TextView hint;
	ModifyXMLFile pass_xml;
	String current_pass;
	Context context;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	
		 setContentView(R.layout.stop_main);

		    //getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		 	
		 context=this.getApplicationContext();
		 String filename= check_xml("passwdxml.xml");
			
			
		 
		 
		 
			pass_xml=new ModifyXMLFile(context, filename);
			
			current_pass=pass_xml.readPass();
		 Button button = (Button) findViewById(R.id.submit2);
			button.setOnClickListener(this);
			//Button.setBackgroundColor(Color.BLUE);
			passwd = (EditText) findViewById(R.id.pass);
			
			hint=(TextView)findViewById(R.id.hint);
		 
			hint.setText("Please enter Password:");
	
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.submit2)
		{
			
			
			if(passwd.getText().toString().equals(current_pass))
			{
				
				
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result","true");
				setResult(RESULT_OK,returnIntent);
				sendToService();
				//finish();
				//finish();
			}
			else
			{	Toast.makeText(context,"Wrong Password", Toast.LENGTH_SHORT).show();
			Intent returnIntent = new Intent();
			returnIntent.putExtra("result","false");
			setResult(RESULT_OK,returnIntent);
			finish();
			}
		}
		
		
		
		
		
	}
	void sendToService() {

        //Log.d("BroadcastActivity", "Sending message to service: " + text);

        final Intent intent = new Intent(MyService.ACTION_TO_SERVICE);
        
        intent.putExtra("case", "stop");
        intent.putExtra("data", "");
        intent.putExtra("check", " ");
        

        sendBroadcast(intent);
        finish();

}
	//checking each xml if is internal storage
	private String check_xml(String filename) {
	// TODO Auto-generated method stub
	
		// Check if the file already in internal storage
		String DestinationFile = context.getFilesDir().getPath()
				+ File.separator + filename;
		
		
		
		

		// If it does not exist in internal storage copy from assets folder
		if (!new File(DestinationFile).exists()) {
			try {
				

				CopyFromAssetsToStorage(context, filename,
						DestinationFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}
		} else {
			
			
			
		}

		
		
		
		
		
return DestinationFile;
}



	/**
	 * Called once in the onCreate method.
	 * Gets the SourceFile from the assets folder and copies it to the DestinationFile in the internal storage 
	 * @param Context Context Interface to global information about the application environment.
	 * @param String SourceFile The SourceFile name to get from the assets folder
	 * @param String DestinationFile The DestinationFile name to be created in the internal storage
	 * @throws IOException 
	 */
	private void CopyFromAssetsToStorage(Context Context, String SourceFile,
			String DestinationFile) throws IOException {
		InputStream IS = Context.getAssets().open(SourceFile);
		OutputStream OS = new FileOutputStream(DestinationFile);
		CopyStream(IS, OS);
		OS.flush();
		OS.close();
		IS.close();
	}


	
	/**
	 * Copy bytes from an InputStream to an OutputStream
	 * @param InputStream Input The InputStream Object that refers to the SourceFile
	 * @param OutputStream Output The OutputStream Object that refers to the DestinationFile
	 * @throws IOException 
	 */
	private void CopyStream(InputStream Input, OutputStream Output)
			throws IOException {
		byte[] buffer = new byte[5120];
		int length = Input.read(buffer);
		while (length > 0) {
			Output.write(buffer, 0, length);
			length = Input.read(buffer);
		}
	}
	
	
}
