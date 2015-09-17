package cy.ucy.arm.example.MultiCare.MultiLocker;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import cy.ucy.arm.example.MultiCare.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LockedActivity extends Activity implements View.OnClickListener{

	String name;
	TextView passwd;
	ActivityManager manager ;
	ModifyXMLFile pass_xml;
	String current_pass;
	Context context;
	Button b1;
	Button b2;
	Button b3;
	Button b4;
	Button b5;
	Button b6;
	Button b7;
	Button b8;
	Button b9;
	String pass_entered="";
	
	
	
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
			                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
			//Toast.makeText(this, "on Create", Toast.LENGTH_SHORT).show();
			
			setContentView(R.layout.update_locked);
			context=this.getApplicationContext();
			
			String filename= check_xml("passwdxml.xml");
			
			
			pass_xml=new ModifyXMLFile(context, filename);
			
			current_pass=pass_xml.readPass();
			
			
			
			
			Intent intent = getIntent();
			//receiver handlers
			final IntentFilter myFilter = new

					IntentFilter(MyService.ACTION_FROM_SERVICE);

					registerReceiver(mReceiver, myFilter);
					
					
					b1=(Button)findViewById(R.id.number1);
					b2=(Button)findViewById(R.id.number2);
					b3=(Button)findViewById(R.id.number3);
					b4=(Button)findViewById(R.id.number4);
					b5=(Button)findViewById(R.id.number5);
					b6=(Button)findViewById(R.id.number6);
					b7=(Button)findViewById(R.id.number7);
					b8=(Button)findViewById(R.id.number8);
					b9=(Button)findViewById(R.id.number9);
					
						b1.setOnClickListener(this);
						b2.setOnClickListener(this);
						b3.setOnClickListener(this);
						b4.setOnClickListener(this);
						b5.setOnClickListener(this);
						b6.setOnClickListener(this);
						b7.setOnClickListener(this);
						b8.setOnClickListener(this);
						b9.setOnClickListener(this);
						
						
						
//					passwd = (EditText) findViewById(R.id.passw);
//			
//			
//					passwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//		            @Override
//		            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//		            	
//		            	
//		            	
//		            	
//		            	
//		            	
//		                boolean handled = false;
//		                if (actionId == EditorInfo.IME_ACTION_DONE) {
//		                    if (v.getText().length() != 0) {
//		                    	if(passwd.getText().toString().equals(current_pass))
//		                			{
//		                    		InputMethodManager imm = (InputMethodManager) LockedActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//	    		                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//		                				sendToService(name);
//		    		                    
//		                				finish();
//		                				//finish();
//		                			}
//		                			else{
//		                				InputMethodManager imm = (InputMethodManager) LockedActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//		    		                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//		                				Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
//		                				 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
//		            	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
//		            	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		            	    	         startActivity(backtoHome);
//		                				finish();
//		                			}
//		                			
//		                			
//		                		
//		                    }
//
//		                    handled = true;
//		                }
//		                return handled;
//		            }
//		        });
			
			
			
			
			
			
			
			
			
			manager = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
			String name = intent.getStringExtra("app");
			int ps = 0;
			int ps2= intent.getIntExtra("pid",ps);
			
			//Log.e("trying to kill", " "+name);
			if(ps2==0)
			{	//android.os.Process.killProcess(android.os.Process.myPid());
			}
			//android.os.Process.killProcess(ps2);
		}

	@Override
	@SuppressWarnings("deprecation")
	public void onResume() {
	    super.onResume();  // Always call the superclass method first
	  // Toast.makeText(this, "on resume", Toast.LENGTH_SHORT).show();
	    
	    Intent intent = getIntent();
		
		//manager= (ActivityManager)LockedActivity.this.getSystemService(ACTIVITY_SERVICE);
		name = intent.getStringExtra("app");
		int ps = 0;
		int ps2= intent.getIntExtra("pid",ps);
		
		
		
		//manager.restartPackage(name);
		
		
	
		 //android.os.Process.killProcess(ps2);
	  
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
	    	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	    	 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	         startActivity(backtoHome);
	    	         finish();
	    	        return true;
	    	    }
	    	    return super.onKeyDown(keyCode, event);
	    	
	}

	
	
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
	
	
	@Override
	protected void onPause ()
	{super.onPause();
		
		finish();
		
	}
	protected void onStop ()
	{super.onStop();
	
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override

        public void onReceive(Context context, Intent intent) {

                    //final TextView responseFromService = (TextView) findViewById(R.id.responseFromService);
        		//Toast.makeText(context,intent.getCharSequenceExtra("data") , Toast.LENGTH_SHORT).show();
                   // responseFromService.setText(intent.getCharSequenceExtra("data"));

        }

	};
	
	
	void sendToService(CharSequence text) {

       // Log.d("BroadcastActivity", "Sending message to service: " + text);

        final Intent intent = new Intent(MyService.ACTION_TO_SERVICE);
        
        intent.putExtra("case", "locked");
        intent.putExtra("data", text);
        intent.putExtra("check", " ");
        

        sendBroadcast(intent);

}
	
	@Override
	public void onDestroy() {
		
        //stopService(new Intent(MyService.ACTION_STOP));

        unregisterReceiver(mReceiver);

        super.onDestroy();

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
		// TODO Auto-generated method stub
		switch(v.getId()){

	      case R.id.number1:
	    	 // v.setBackgroundColor(Color.GRAY); // Choose whichever color
	    	  b1.setAlpha(0.25f);
	          new Handler().postDelayed(new Runnable() {

	              public void run() {
	            	  b1.setAlpha(0.99f);
	                  
	                  pass_entered+="1";
	    	    	  if(pass_entered.length()==current_pass.length() )
	    	    	  {
	    	    		  if(pass_entered.equals(current_pass))
	    	    		  {
	    	    			  sendToService(name);
	    	                    

	    	    			 finish();
	    	    			  
	    	    		  }
	    	    		  else
	    	    		  {
	    	    			  Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
	         				 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
	     	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
	     	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     	    	         startActivity(backtoHome);
	         				finish();
	    	    		  }
	    	    		  
	    	    		  
	    	    		  
	    	    	  }
	    	    	  //b1.setAlpha(0.25f);
	    	    	  
	                  // Button Click Code Here
	              }

	          }, 100L);
	          
	    	  //Toast.makeText(context, "epatithike 1 "+current_pass+" "+pass_entered,Toast.LENGTH_SHORT).show();
	          break;
	      case R.id.number2: 
	    	  b2.setAlpha(0.25f);
	          new Handler().postDelayed(new Runnable() {

	              public void run() {
	            	  b2.setAlpha(0.99f);
	                  
	                  pass_entered+="2";
	    	    	  if(pass_entered.length()==current_pass.length() )
	    	    	  {
	    	    		  if(pass_entered.equals(current_pass))
	    	    		  {
	    	    			  sendToService(name);
	    	                    

	    	    			 finish();
	    	    			  
	    	    		  }
	    	    		  else
	    	    		  {
	    	    			  Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
	         				 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
	     	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
	     	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     	    	         startActivity(backtoHome);
	         				finish();
	    	    		  }
	    	    		  
	    	    		  
	    	    		  
	    	    	  }
	    	    	  //b1.setAlpha(0.25f);
	    	    	  
	                  // Button Click Code Here
	              }

	          }, 100L);
	          
	    	  //Toast.makeText(context, "epatithike 1 "+current_pass+" "+pass_entered,Toast.LENGTH_SHORT).show();
	          break;
	      case R.id.number3: 
	    	  b3.setAlpha(0.25f);
	          new Handler().postDelayed(new Runnable() {

	              public void run() {
	            	  b3.setAlpha(0.99f);
	                  
	                  pass_entered+="3";
	    	    	  if(pass_entered.length()==current_pass.length() )
	    	    	  {
	    	    		  if(pass_entered.equals(current_pass))
	    	    		  {
	    	    			  sendToService(name);
	    	                    

	    	    			 finish();
	    	    			  
	    	    		  }
	    	    		  else
	    	    		  {
	    	    			  Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
	         				 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
	     	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
	     	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     	    	         startActivity(backtoHome);
	         				finish();
	    	    		  }
	    	    		  
	    	    		  
	    	    		  
	    	    	  }
	    	    	  //b1.setAlpha(0.25f);
	    	    	  
	                  // Button Click Code Here
	              }

	          }, 100L);
	          
	    	  //Toast.makeText(context, "epatithike 1 "+current_pass+" "+pass_entered,Toast.LENGTH_SHORT).show();
	          break;
	      case R.id.number4: 
	    	  b4.setAlpha(0.25f);
	          new Handler().postDelayed(new Runnable() {

	              public void run() {
	            	  b4.setAlpha(0.99f);
	                  
	                  pass_entered+="4";
	    	    	  if(pass_entered.length()==current_pass.length() )
	    	    	  {
	    	    		  if(pass_entered.equals(current_pass))
	    	    		  {
	    	    			  sendToService(name);
	    	                    

	    	    			 finish();
	    	    			  
	    	    		  }
	    	    		  else
	    	    		  {
	    	    			  Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
	         				 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
	     	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
	     	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     	    	         startActivity(backtoHome);
	         				finish();
	    	    		  }
	    	    		  
	    	    		  
	    	    		  
	    	    	  }
	    	    	  //b1.setAlpha(0.25f);
	    	    	  
	                  // Button Click Code Here
	              }

	          }, 100L);
	          
	    	  //Toast.makeText(context, "epatithike 1 "+current_pass+" "+pass_entered,Toast.LENGTH_SHORT).show();
	          break;
	      case R.id.number5: 
	    	  b5.setAlpha(0.25f);
	          new Handler().postDelayed(new Runnable() {

	              public void run() {
	            	  b5.setAlpha(0.99f);
	                  
	                  pass_entered+="5";
	    	    	  if(pass_entered.length()==current_pass.length() )
	    	    	  {
	    	    		  if(pass_entered.equals(current_pass))
	    	    		  {
	    	    			  sendToService(name);
	    	                    

	    	    			 finish();
	    	    			  
	    	    		  }
	    	    		  else
	    	    		  {
	    	    			  Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
	         				 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
	     	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
	     	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     	    	         startActivity(backtoHome);
	         				finish();
	    	    		  }
	    	    		  
	    	    		  
	    	    		  
	    	    	  }
	    	    	  //b1.setAlpha(0.25f);
	    	    	  
	                  // Button Click Code Here
	              }

	          }, 100L);
	          
	    	  //Toast.makeText(context, "epatithike 1 "+current_pass+" "+pass_entered,Toast.LENGTH_SHORT).show();
	          break;
	      case R.id.number6: 
	    	  b6.setAlpha(0.25f);
	          new Handler().postDelayed(new Runnable() {

	              public void run() {
	            	  b6.setAlpha(0.99f);
	                  
	                  pass_entered+="6";
	    	    	  if(pass_entered.length()==current_pass.length() )
	    	    	  {
	    	    		  if(pass_entered.equals(current_pass))
	    	    		  {
	    	    			  sendToService(name);
	    	                    

	    	    			 finish();
	    	    			  
	    	    		  }
	    	    		  else
	    	    		  {
	    	    			  Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
	         				 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
	     	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
	     	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     	    	         startActivity(backtoHome);
	         				finish();
	    	    		  }
	    	    		  
	    	    		  
	    	    		  
	    	    	  }
	    	    	  //b1.setAlpha(0.25f);
	    	    	  
	                  // Button Click Code Here
	              }

	          }, 100L);
	          
	    	  //Toast.makeText(context, "epatithike 1 "+current_pass+" "+pass_entered,Toast.LENGTH_SHORT).show();
	          break;
	      case R.id.number7: 
	    	  b7.setAlpha(0.25f);
	          new Handler().postDelayed(new Runnable() {

	              public void run() {
	            	  b7.setAlpha(0.99f);
	                  
	                  pass_entered+="7";
	    	    	  if(pass_entered.length()==current_pass.length() )
	    	    	  {
	    	    		  if(pass_entered.equals(current_pass))
	    	    		  {
	    	    			  sendToService(name);
	    	                    

	    	    			 finish();
	    	    			  
	    	    		  }
	    	    		  else
	    	    		  {
	    	    			  Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
	         				 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
	     	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
	     	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     	    	         startActivity(backtoHome);
	         				finish();
	    	    		  }
	    	    		  
	    	    		  
	    	    		  
	    	    	  }
	    	    	  //b1.setAlpha(0.25f);
	    	    	  
	                  // Button Click Code Here
	              }

	          }, 100L);
	          
	    	  //Toast.makeText(context, "epatithike 1 "+current_pass+" "+pass_entered,Toast.LENGTH_SHORT).show();
	          break;
	      case R.id.number8: 
	    	  b8.setAlpha(0.25f);
	          new Handler().postDelayed(new Runnable() {

	              public void run() {
	            	  b8.setAlpha(0.99f);
	                  
	                  pass_entered+="8";
	    	    	  if(pass_entered.length()==current_pass.length() )
	    	    	  {
	    	    		  if(pass_entered.equals(current_pass))
	    	    		  {
	    	    			  sendToService(name);
	    	                    

	    	    			 finish();
	    	    			  
	    	    		  }
	    	    		  else
	    	    		  {
	    	    			  Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
	         				 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
	     	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
	     	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     	    	         startActivity(backtoHome);
	         				finish();
	    	    		  }
	    	    		  
	    	    		  
	    	    		  
	    	    	  }
	    	    	  //b1.setAlpha(0.25f);
	    	    	  
	                  // Button Click Code Here
	              }

	          }, 100L);
	          
	    	  //Toast.makeText(context, "epatithike 1 "+current_pass+" "+pass_entered,Toast.LENGTH_SHORT).show();
	          break;
		
	      case R.id.number9: 
	    	  b9.setAlpha(0.25f);
	          new Handler().postDelayed(new Runnable() {

	              public void run() {
	            	  b9.setAlpha(0.99f);
	                  
	                  pass_entered+="9";
	    	    	  if(pass_entered.length()==current_pass.length() )
	    	    	  {
	    	    		  if(pass_entered.equals(current_pass))
	    	    		  {
	    	    			  sendToService(name);
	    	                    

	    	    			 finish();
	    	    			  
	    	    		  }
	    	    		  else
	    	    		  {
	    	    			  Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
	         				 Intent backtoHome = new Intent(Intent.ACTION_MAIN);
	     	    	         backtoHome.addCategory(Intent.CATEGORY_HOME);
	     	    	         backtoHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	     	    	         startActivity(backtoHome);
	         				finish();
	    	    		  }
	    	    		  
	    	    		  
	    	    		  
	    	    	  }
	    	    	  //b1.setAlpha(0.25f);
	    	    	  
	                  // Button Click Code Here
	              }

	          }, 100L);
	          
	    	  //Toast.makeText(context, "epatithike 1 "+current_pass+" "+pass_entered,Toast.LENGTH_SHORT).show();
	          break;
		
	    	  
	    
	    	  
		}
		
		
		
		
	}


}
