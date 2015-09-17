package cy.ucy.arm.example.MultiCare.MultiLocker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.TransformerException;
import cy.ucy.arm.example.MultiCare.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassWordActivity extends Activity implements View.OnClickListener {

	
	EditText oldPass;
	EditText newPass;
	EditText confirmPass;
	ModifyXMLFile pass_xml;
	Context context;
	String current_pass;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	
		 setContentView(R.layout.change_pass);

		    //getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		 	
		 context=this.getApplicationContext();
		 
		String filename= check_xml("passwdxml.xml");
		
		
		pass_xml=new ModifyXMLFile(context, filename);
		
		current_pass=pass_xml.readPass();
		 
		 Button button = (Button) findViewById(R.id.submit3);
			button.setOnClickListener(this);
			//Button.setBackgroundColor(Color.BLUE);
			oldPass = (EditText) findViewById(R.id.old);
			newPass=(EditText) findViewById(R.id.new1);
			confirmPass=(EditText) findViewById(R.id.conf);
			
	
	
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
	
	
	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.submit3)
		{
			
			//Toast.makeText(context, "epatttt",Toast.LENGTH_SHORT).show();
		
			if(oldPass.getText().toString().equals(current_pass))
			{
				String newpass=newPass.getText().toString();
				String confpass=confirmPass.getText().toString();
				if(newpass.equals(confpass))
				{
					
					Toast.makeText(context,"Password succesfully changed",Toast.LENGTH_SHORT).show();
					try {
						
						pass_xml.changePass(newpass);
						finish();
					} catch (TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				else if(!(newpass.equals(confpass)))
				{
					newPass.setHint("Enter New Password");
					newPass.setHintTextColor(Color.RED);
					newPass.setText("");
					
					confirmPass.setHint("Confirm New Password");
					confirmPass.setHintTextColor(Color.RED);
					
					confirmPass.setText("");
					confirmPass.requestFocus();
					newPass.requestFocus();
					oldPass.requestFocus();
					
				}
				//finish();
			}
			else{
				oldPass.setHint("Enter Current Password again");
				oldPass.setHintTextColor(Color.RED);
				oldPass.setText("");
				newPass.setText("");
				newPass.setHint("Enter New Password");
				newPass.setHintTextColor(Color.RED);
				confirmPass.setHint("Confirm New Password");
				confirmPass.setHintTextColor(Color.RED);
				confirmPass.setText("");
				confirmPass.requestFocus();
				newPass.requestFocus();
				oldPass.requestFocus();
				
			}
			
			
		}
		
		
	}

}
