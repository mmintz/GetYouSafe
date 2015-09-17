package cy.ucy.arm.example.MultiCare.xmlparser;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.DownloadManager.Query;

import cy.ucy.arm.example.MultiCare.DataClasses.PhoneContact;
import cy.ucy.arm.example.MultiCare.DataClasses.Quote;

public class ReadXML {

	private String filename;
	private static String friend = "friend";
	private static String _id = "id";
	private static String name = "name";
	private static String telNumber = "telNumber";
	private static String quote = "quote";
	private static String id = "id";
	private static String text = "text";
	private File myDir;

	public File getMyDir() {
		return myDir;
	}

	public void setMyDir(File myDir) {
		this.myDir = myDir;
	}

	private String getFilename() {
		return filename;
	}

	private void setFilename(String filename) {
		this.filename = filename;
	}

	public ReadXML(String filename) {
		this.setFilename(filename);
	}

	public ArrayList<PhoneContact> getData() {
		try {
			ArrayList<PhoneContact> friends = new ArrayList<PhoneContact>();

			File fXmlFile = new File(myDir + this.getFilename());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(friend);

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String idE = eElement.getAttribute(_id);
					String nameElement = eElement.getElementsByTagName(name)
							.item(0).getTextContent();
					String telElement = eElement
							.getElementsByTagName(telNumber).item(0)
							.getTextContent();
					PhoneContact t = new PhoneContact(idE, nameElement,
							telElement);
					friends.add(t);
				}
			}
			return friends;
		} catch (Exception e) {
			System.out.println("No file found");
		}
		return null;
	}

	public ArrayList<Quote> getQuoteData() {
		try {
			ArrayList<Quote> friends = new ArrayList<Quote>();

			File fXmlFile = new File(myDir + this.getFilename());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(quote);

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String idE = eElement.getAttribute(_id);
					String textRead = eElement.getElementsByTagName(text)
							.item(0).getTextContent();
					
					Quote t = new Quote(Integer.parseInt(idE), textRead);
					friends.add(t);
				}
			}
			return friends;
		} catch (Exception e) {
			System.out.println("No file found");
		}
		return null;
	}
	
	
}
