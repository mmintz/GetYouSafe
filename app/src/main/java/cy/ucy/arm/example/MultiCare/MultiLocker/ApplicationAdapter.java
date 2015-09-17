package cy.ucy.arm.example.MultiCare.MultiLocker;



import java.util.List;



import cy.ucy.arm.example.MultiCare.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;





public class ApplicationAdapter extends ArrayAdapter<Apps> {

	
	ModifyXMLFile xml;
	 LayoutInflater mInflater;
	    
	// ViewHolder holder;// holder = null;

	//Context context;
	int resource;
	List<Apps> apps;
	ListView m=null;
	MainActivity activity_handler ;

	
	public ApplicationAdapter(Context context, int resource, List<Apps> objects,ModifyXMLFile mk,MainActivity main) {
		super(context, resource,objects);
		this.resource = resource;
		//this.context = context;
		//this.act=m;
		activity_handler=main;
		apps=objects;
		xml=mk;
		//Log.e("pamen","constructor");
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get a View that displays the data at the specified position in the data
	 * set.
	 * 
	 * @param int position The position in data set
	 * @param View
	 *            convertView The View that displays the data
	 * @param ViewGroup
	 *            parent The ViewGroup parent
	 * @return View The returned View
	 */
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View usersView= convertView;
		ViewHolder holder= new ViewHolder();
		
		// get item returns to the adapter the item at position
		
		
		final int pos=position;
		
		// If View is null, create a new LinearLayout based on the resource id
		if (usersView == null) {
			//usersView = mInflater.inflate(R.layout.item, parent, false);
			 
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
			usersView  = li.inflate(R.layout.item, parent, false);
		
			holder.nameView= (TextView) usersView.findViewById(R.id.name);
			holder.lockedView = (TextView) usersView.findViewById(R.id.locked);
			holder.image=(ImageView) usersView.findViewById(R.id.image1);
			holder.button=(cy.ucy.arm.example.MultiCare.MultiLocker.ExtendedButton) usersView.findViewById(R.id.imageButton1);
			holder.button.setRes(R.id.locked);
			//Log.e("general position"," "+position);
			//holder.button.setPos(position);
			//TODO:REMEBER LIST me ta locked tha theleis elegxous
			holder.button.setAlpha(0.6f);
			holder.button.setOnClickListener(mBuyButtonClickListener);
			usersView.setTag(holder);
		} else {
			holder = (ViewHolder)(usersView.getTag());
			//usersView = new RelativeLayout(getContext());
			//String inflater = Context.LAYOUT_INFLATER_SERVICE;
			//LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
			//		inflater);
			//vi.inflate(this.resource, usersView, true);
		}
		
		
		Apps app=apps.get(position);
	
		if(app!=null)
		{
		//Apps item = getItem(position);
		String name = app.getName();
		String locked=app.getLocked();
		Drawable icon=app.getIcon();
		String pack=app.getPackName();
		
		
		int res=app.getrec();
		//ExtendedButton button=app.ge
			holder.nameView.setText(name);
			holder.lockedView.setText(locked);
			holder.image.setImageDrawable(icon);
		
			
			if(locked.equals("unlocked") )//&& !holder.button.isClicked())
			{
				
				//Log.e("ime mesa sto ! clicked ","yeah");
				holder.button.setPos(pos);
				holder.button.setAlpha(0.23f);
				
				//holder.button.setBackgroundResource(R.drawable.ic_launcher);
				
			}
			else if(locked.equals("locked"))
			{
				
				//Log.e("ime mesa sto = clicked ","yeah");
				//holder.button.setBackgroundResource(R.drawable.lock);
				holder.button.setPos(pos);
				holder.button.setAlpha(1f);
			
			}
		}
		
	
		  
		
		
		return usersView;
	}
	public class ViewHolder
	{
	    
		ImageView image;
	    TextView lockedView;
	    TextView nameView;
	    cy.ucy.arm.example.MultiCare.MultiLocker.ExtendedButton button;
	}
	
	
	
	private OnClickListener mBuyButtonClickListener = new OnClickListener() {
	    @SuppressLint("NewApi")
		@Override
	    public void onClick(View v) {
	       
	    	cy.ucy.arm.example.MultiCare.MultiLocker.ExtendedButton b=(cy.ucy.arm.example.MultiCare.MultiLocker.ExtendedButton)v;
	    	//Log.e("patithike ","pos "+b.getPos());
	    	b.setClicked(true,R.drawable.ic_launcher);
	    	apps.get(b.getPos()).setrec(R.drawable.ic_launcher);
	    	
	    	
	    	//if its unlocked lock it
	    	if(apps.get(b.getPos()).getLocked().equals("unlocked"))
	    	{
	    		apps.get(b.getPos()).setLocked("locked");
	    		Apps add_app=new Apps(apps.get(b.getPos()).getPackName());
	    		add_app.setId(apps.get(b.getPos()).getId());
	    		xml.AddToXMLFile((add_app));
	    	
	    		activity_handler.sendToService(add_app.getPackName(),"add");
	    		Toast.makeText(getContext(), "Locked " +apps.get(b.getPos()).getName(), Toast.LENGTH_SHORT).show();
	    	
	    	//apps.get(b.getPos()).g
	    	//b.setBackgroundResource(R.drawable.ic_launcher);
	    	b.setAlpha(1f);
	    	}
	    	else
	    	{		
	    		apps.get(b.getPos()).setLocked("unlocked");
	    	Apps del_app=new Apps(apps.get(b.getPos()).getPackName());
	    	del_app.setId(apps.get(b.getPos()).getId());
	    	xml.DeleteFromXMLFile(del_app);
	    	//b.setBackgroundResource(R.drawable.lock);
	    	b.setAlpha(0.23f);
	    	Toast.makeText(getContext(), "Unlocked " +apps.get(b.getPos()).getName(), Toast.LENGTH_SHORT).show();
	    	activity_handler.sendToService(del_app.getPackName(),"remove");
	    	
	    	
	    	}
	    	//b.setBackgroundResource(R.drawable.ic_launcher);
	    	
	    	notifyDataSetChanged();
	        }
	    
	};
	

	
	   
	
}
