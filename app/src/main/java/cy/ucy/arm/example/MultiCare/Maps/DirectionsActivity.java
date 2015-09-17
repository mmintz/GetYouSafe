package cy.ucy.arm.example.MultiCare.Maps;

import java.util.ArrayList;
import cy.ucy.arm.example.MultiCare.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class DirectionsActivity extends Activity {

	private ArrayList<String> routeInstructions = new ArrayList<String>();
	
	private ListView directionsListView;
	
	private DirectionsAdapter directionsAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directions);
		
		// Get references for the UI widgets
		directionsListView = (ListView)findViewById(R.id.directionsListView);
		
		// Get the resource ID for each item on the ListView
		int resID = R.layout.directions_item;
		
		// Get the ArrayList containing the route directions Strings
		routeInstructions = getIntent().getExtras().getStringArrayList("directions");
		
		// Create a new DirectionsAdapter passing it the context, the resource layout id and the ArrayList with the route directions Strings
		directionsAdapter = new DirectionsAdapter(getApplicationContext(), resID, routeInstructions);
		
		// Set the Adapter to the ListView
		directionsListView.setAdapter(directionsAdapter);
		
		// Notify the Adapter about the data changed
		directionsAdapter.notifyDataSetChanged();
		
		//for (int i = 0; i < routeInstructions.size(); i++) {
		//	System.out.println("DirectionsActivity: Instruction " + i + " is: "
		//			+ routeInstructions.get(i));
		//}
		
		
	}
}
