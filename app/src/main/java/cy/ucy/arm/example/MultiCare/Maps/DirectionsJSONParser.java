package cy.ucy.arm.example.MultiCare.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Class DirectionsJSONParser is a JSONParser for getting the latitude and longitude of the route points
 */
public class DirectionsJSONParser {

	private ArrayList<String> routeInstructions = new ArrayList<String>();
	
	/**
	 * @return the routeInstructions
	 */
	public ArrayList<String> getRouteInstructions() {
		return routeInstructions;
	}

	/**
	 * @param routeInstructions the routeInstructions to set
	 */
	public void setRouteInstructions(ArrayList<String> routeInstructions) {
		this.routeInstructions = routeInstructions;
	}
	
	/**
	 * @param JSONObject jObject The JSONObject received
	 * @return List<List<HashMap<String, String>>> routes  A list of lists containing latitude and longitude
	 */
	public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

		List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
		JSONArray jRoutes = null;
		JSONArray jLegs = null;
		JSONArray jSteps = null;

		try {

			jRoutes = jObject.getJSONArray("routes");

			/** Traversing all routes */
			for (int i = 0; i < jRoutes.length(); i++) {
				jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
				List path = new ArrayList<HashMap<String, String>>();

				/** Traversing all legs */
				for (int j = 0; j < jLegs.length(); j++) {
					jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

					/** Traversing all steps */
					for (int k = 0; k < jSteps.length(); k++) {
						String polyline = "";
						polyline = (String) ((JSONObject) ((JSONObject) jSteps
								.get(k)).get("polyline")).get("points");
						List<LatLng> list = decodePoly(polyline);

						/** Get the html instructions **/
						String html_instructions = jSteps.getJSONObject(k)
                                .getString("html_instructions");
												
						//Log.e("INSTRUCTIONS :", html_instructions);
						/** Add the html instructions to the ArrayList **/
						routeInstructions.add(html_instructions);
		
						/** Traversing all points */
						for (int l = 0; l < list.size(); l++) {
							HashMap<String, String> hm = new HashMap<String, String>();
							hm.put("lat",
									Double.toString(((LatLng) list.get(l)).latitude));
							hm.put("lng",
									Double.toString(((LatLng) list.get(l)).longitude));
							path.add(hm);
						}
					}
					routes.add(path);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
		return routes;
	}

	/**
	 * Method to decode polyline points
	 * decoding-polylines-from-google-maps-direction-api-with-java
	 * Polylines are set of latitude/longitude pairs
	 * */
	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}
		return poly;
	}
}
