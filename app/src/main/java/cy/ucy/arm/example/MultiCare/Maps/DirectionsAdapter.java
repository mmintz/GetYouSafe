package cy.ucy.arm.example.MultiCare.Maps;

import java.util.List;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import cy.ucy.arm.example.MultiCare.R;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * DirectionsAdapter extends the ArrayAdapter<String>
 * In this class is implemented the View getView method that creates the appropriate View and returns it to the ListView
 */
public class DirectionsAdapter extends ArrayAdapter<String>{

	int resource;

	public DirectionsAdapter(Context _context, 
                             int _resource, 
                             List<String> _items) {
		super(_context, _resource, _items);
		resource = _resource;
	}
	
	/**
	 * Get a View that displays the data at the specified position in the data set.
	 * @param int position The position in the data set
	 * @param View convertView The View that displays the data
	 * @param ViewGroup parent The ViewGroup parent
	 * @return View The View that is returned
	 */
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	   
		 LinearLayout usersView;
	    
		 // getItem returns to the adapter the item at position
		 String item = getItem(position);
	
		 // Get the String value of the item at position 
		 String direction = item.toString();
	
	    
		 // If the View is null, I create a new LinearLayout based on the resource id
		 if (convertView == null) {
			 usersView = new LinearLayout(getContext());
			 String inflater = Context.LAYOUT_INFLATER_SERVICE;
			 LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
			 vi.inflate(resource, usersView, true);
		 } 
		 else {
			 usersView = (LinearLayout) convertView;
		 }
	
		 TextView directionsView = (TextView)usersView.findViewById(R.id.directionsText);
		 TextView routeNumbersView = (TextView)usersView.findViewById(R.id.routeNumbering);
		 
		 // Set values to TextViews the String got from item at position
		 directionsView.setText(Html.fromHtml(direction));
		
		 // position + 1 because the first item is the zero item
		 routeNumbersView.setText(String.valueOf(position+1));
		 
		 // return the View
		 return usersView;
	 }
}
