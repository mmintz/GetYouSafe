package cy.ucy.arm.example.MultiCare.xmlparser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cy.ucy.arm.example.MultiCare.DataClasses.PhoneContact;

public class ModifyXML {

	private String filename;
	private static String friend = "friend";
	private static String _id = "id";
	private static String name = "name";
	private static String telNumber = "telNumber";

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

	public ModifyXML(String filename) {
		this.setFilename(filename);
	}

	public void addElement(PhoneContact element) {
		try {
			File fXmlFile = new File(myDir + this.getFilename());
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fXmlFile);

			// get how many friends i have in the xml
			NodeList list = doc.getElementsByTagName(ModifyXML.friend);
			int find = list.getLength();
			int howmany;
			if (find == 0)
				howmany = 0;
			else
				howmany = Integer.valueOf(list.item(list.getLength() - 1)
						.getAttributes().getNamedItem(_id).getNodeValue()) + 1;

			element.set_id("" + howmany);

			// Get the root element
			Node friendArray = doc.getDocumentElement();
			Element MyFriend = doc.createElement(ModifyXML.friend);
			Element nameElemenet = doc.createElement(ModifyXML.name);
			Element telElemenet = doc.createElement(ModifyXML.telNumber);

			MyFriend.setAttribute(_id, "" + howmany);
			nameElemenet.appendChild(doc.createTextNode(element
					.getContactName()));
			telElemenet
					.appendChild(doc.createTextNode(element.getPhoneNumber()));

			MyFriend.appendChild(nameElemenet);
			MyFriend.appendChild(telElemenet);

			friendArray.appendChild(MyFriend);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(myDir
					+ this.getFilename()));
			transformer.transform(source, result);

			System.out.println("Done");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

	public void deleteElement(PhoneContact element) {
		try {
			File fXmlFile = new File(myDir + this.getFilename());
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fXmlFile);

			// Get the root element
			Node friendArray = doc.getDocumentElement();
			int until = friendArray.getChildNodes().getLength();
			// Get the root element that is going to be modified
			NodeList MyFriendArray = doc.getElementsByTagName(ModifyXML.friend);

			Node nNode = null;
			int temp;
			for (temp = 0; temp < MyFriendArray.getLength(); temp++) {
				nNode = MyFriendArray.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					NamedNodeMap attr = nNode.getAttributes();
					Node nodeAttr = attr.getNamedItem(_id);
					if (nodeAttr.getNodeValue().equals(element.get_id()))
						break;
				}
			}
			if (temp != MyFriendArray.getLength())
				friendArray.removeChild(nNode);
			else
				System.out.println("deleted");

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(myDir
					+ this.getFilename()));
			transformer.transform(source, result);

			System.out.println("Done");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

	public void modifyElement(PhoneContact element) {
		try {
			File fXmlFile = new File(myDir + this.getFilename());
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fXmlFile);

			// Get the root element
			Node friendArray = doc.getDocumentElement();
			int until = friendArray.getChildNodes().getLength();
			// Get the root element that is going to be modified
			NodeList MyFriendArray = doc.getElementsByTagName(ModifyXML.friend);

			Node nNode = null;
			int temp;
			for (temp = 0; temp < MyFriendArray.getLength(); temp++) {
				nNode = MyFriendArray.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					NamedNodeMap attr = nNode.getAttributes();
					Node nodeAttr = attr.getNamedItem(_id);
					if (nodeAttr.getNodeValue().equals(element.get_id()))
						break;
				}
			}

			NodeList list = nNode.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {
				Node toTest = list.item(i);
				if (toTest.getNodeName().equals(ModifyXML.name))
					toTest.setTextContent(element.getContactName());
				if (toTest.getNodeName().equals(ModifyXML.telNumber))
					toTest.setTextContent(element.getPhoneNumber());
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(myDir + this.getFilename()));
			transformer.transform(source, result);
			System.out.println("Done");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

}
