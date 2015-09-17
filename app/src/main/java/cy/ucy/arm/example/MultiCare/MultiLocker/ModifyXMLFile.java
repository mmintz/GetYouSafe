package cy.ucy.arm.example.MultiCare.MultiLocker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ModifyXMLFile {

	private Context context;
	private String filename;

	public ModifyXMLFile(Context context, String filename) {

		this.filename = filename;
		this.context = context;
	}
	
	public String readPass(){
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		Document doc = null;
		try {
			//Log.e("filename"," "+filename);
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(filename));
			

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//Log.e("exception", "builting");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//Log.e("exception", "parce");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Log.e("exception", "xml exception");
			e.printStackTrace();
		}
		
		
		
		
		doc.getDocumentElement().normalize();
		
	//String pass=((NodeList) doc.getElementById("0")).item(0)
		//	.getTextContent();
		Node application = doc.getFirstChild();
		 
		
		
		NodeList nList = doc.getElementsByTagName("password");
		
		
		Node nNode = nList.item(0);
		
		
		Element eElement = (Element) nNode;

		//String id = eElement.getAttribute("id");
	
		String name = eElement.getTextContent();
		
		// Get the staff element , it may not working if tag has spaces, or
		// whatever weird characters in front...it's better to use
		// getElementsByTagName() to get it directly.
		// Node staff = company.getFirstChild();
 
		// Get the staff element by tag name directly
		
 
		//Log.e("pass","pass "+name);
		
		
		// Create a NodeList which contains all the nodes of the XML file
		
		
		return name;
	}
	
	public int changePass(String newPass) throws TransformerException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		Document doc = null;
		
		try {
			//Log.e("filename"," "+filename);
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(filename));
			

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//Log.e("exception", "builting");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//Log.e("exception", "parce");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Log.e("exception", "fellas");
			e.printStackTrace();
		}
		
		
		
		doc.getDocumentElement().normalize();
		
			//getting the root

		NodeList nList = doc.getElementsByTagName("password");
		
		
		Node nNode = nList.item(0);
		
		
		Element eElement = (Element) nNode;

		//String id = eElement.getAttribute("id");
	
		String name = eElement.getTextContent();
		//Log.e("pas old ","id "+name);
		eElement.setTextContent(newPass);
		name = eElement.getTextContent();
		
		//Log.e("pas new ","id "+name);
		// Get the staff element , it may not working if tag has spaces, or
		// whatever weird characters in front...it's better to use
		// getElementsByTagName() to get it directly.
		// Node staff = company.getFirstChild();
 
		// Get the staff element by tag name directly
		
 
			
			// Create a NodeList which contains all the nodes of the XML file
			
			
			
		
		// Create a NodeList which contains all the nodes of the XML file
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filename));
		transformer.transform(source, result);
		
		return 1;
	}

	public ArrayList<Apps> Execute() {

		// Create a new ArrayList<Teacher>
		ArrayList<Apps> Locked_apps = new ArrayList<Apps>();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		Document doc = null;
		try {
			
			File f = new File (filename) ;
//			if (!f.exists()) 
//				//Log.e("stay","bo");
//			
//			
			
		//	Log.e("filename"," "+filename);
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(filename));
			

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//Log.e("exception", "builting");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//Log.e("exception", "parce");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Log.e("exception", "fellas");
			e.printStackTrace();
		}
		if(doc==null)
		{
			//Log.e("felllas","null");
		}
		
		doc.getDocumentElement().normalize();
		

		// Create a NodeList which contains all the nodes of the XML file
		NodeList nList = doc.getElementsByTagName("Locked");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				//String id = eElement.getAttribute("id");
				try{
				String name = eElement.getElementsByTagName("Name").item(0)
						.getTextContent();
				
				//Log.e("names "," "+name);
				//String number = eElement.getElementsByTagName("Telephone")
				//		.item(0).getTextContent();
				//String ofice = eElement.getElementsByTagName("Office").item(0)
				//		.getTextContent();
				Apps app=new Apps(name);
				app.setId(eElement.getAttribute("id"));
				
				Locked_apps.add(app);
				}catch(Exception e)
				{
					//Log.e("reading xml","no name tag");
				}

			}
		}
		return Locked_apps;

	}

	/**
	 * Adds a new teacher tag and saves to XML file
	 * 
	 * @param Teacher
	 *            newUser The Teacher Object that will be added to the XML file
	 */
	public void AddToXMLFile(Apps app_to_lock) {
		try {
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			Node Application = doc.getFirstChild();
			 
			// Get the staff element , it may not working if tag has spaces, or
			// whatever weird characters in front...it's better to use
			// getElementsByTagName() to get it directly.
			// Node staff = company.getFirstChild();
	 
			
			Element lock=doc.createElement("Locked");
			Application.appendChild(lock);
			
			// Create id attribute and set id value
						//Attr id = doc.createAttribute("id");
						//id.setValue(app_to_lock.getId());
						//lock.setAttributeNode(id);
						
						
						Element name = doc.createElement("Name");
						name.appendChild(doc.createTextNode(app_to_lock.getPackName()));
						lock.appendChild(name);
			// Create name element, set value and append child to the parent
			// element
			
			//Log.e("adding to xml name "+app_to_lock.getPackName()," "+app_to_lock.getPackName());
			// Create telephone element, set value and append child to the
			// parent element
						// Write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes the teacher tag and saves changes to the XML file
	 * 
	 * @param Teacher
	 *            user The Teacher Object that will be deleted from the XML file
	 */
	public void DeleteFromXMLFile(Apps app_unlocked) {
		try {
			File fXmlFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			
			NodeList nList = doc.getElementsByTagName("Locked");

			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					// If the name is found in XML file, then the Element is
					// removed from XML file
					if(eElement.getChildNodes().item(0).getTextContent().equals(app_unlocked.getPackName()))
							{
						eElement.getParentNode().removeChild(eElement);
							}
					
					//Log.e("name text ?"," "+eElement.getChildNodes().item(0).getTextContent());
					//if (eElement.getAttribute("id").equals(app_unlocked.getId())) {
					//	eElement.getParentNode().removeChild(eElement);
					//}
				}
			}


			// Write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);
		} catch (Exception e) {
			//Log.e("Exception","in delete");
			e.printStackTrace();
		}
	}

	
}
