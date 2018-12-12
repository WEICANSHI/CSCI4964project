package P2P_mod;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import Component.XML;

public class Syslog {
	public static String id;
	public static String ip;
	public static int port;
	public static Map<String, Header> peers;
	
	public static void Init() {
		Syslog.Initsetting();
		Syslog.InitPeers();
	}
	
	public static void main(String args[]) {
		XML.InitSetting();
		Syslog.Init();
	}
	private static void InitPeers() {
		peers = new HashMap<String, Header>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("./" + XML.XMLDir + "/peers.xml"));
			Element rootElement = doc.getDocumentElement();
			// assert the setting is correct
			assert(rootElement.getTagName().equals("peers"));
			
			// loop through all the peers
			NodeList children = rootElement.getChildNodes();
			for(int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				String sid = null;
				String sip = null;
				int sport = -1;
				if(child instanceof Element) {
					Element childElement = (Element) child;
					NodeList gchildren = childElement.getChildNodes();
					for(int j = 0; j < gchildren.getLength(); j++) {
						Node gchild = gchildren.item(j);
						if(gchild instanceof Element) {
							Element gchildElement = (Element) gchild;
							Text textNode = (Text) gchildElement.getFirstChild();
							String text = textNode.getData().trim();
							System.out.println(text);
							if(gchildElement.getTagName().equals("id"))
								sid = text;
							else if(gchildElement.getTagName().equals("ip")) {
								sip = text;
							}else if(gchildElement.getTagName().equals("port")) {
								sport = Integer.parseInt(text.trim());
							}
						}
					}
				}
				// Exception but simply break
				if(sid == null || sip == null || sport == -1) {
					continue;
				}else {
					peers.put(sid, new Header(sip, sport));
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	private static void Initsetting() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("./" + XML.XMLDir + "/net_setting.xml"));
			Element rootElement = doc.getDocumentElement();
			// assert the setting is correct
			assert(rootElement.getTagName().equals("setting"));
			
			// loop through all the setting
			NodeList children = rootElement.getChildNodes();
			for(int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if(child instanceof Element) {
					Element childElement = (Element) child;
					Text textNode = (Text) childElement.getFirstChild();
					String text = textNode.getData().trim();
					if(childElement.getTagName().equals("id"))
						id = text;
					else if(childElement.getTagName().equals("ip")) {
						ip = Syslog.getIp();
						child.setTextContent(ip);
					}else if(childElement.getTagName().equals("port")) {
						port = Integer.parseInt(text.trim());
					}
				}
			}
			
			//write the content in xml file
			try {
				// transform in to XML file
				Transformer t = TransformerFactory.newInstance().newTransformer();
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
				t.transform(new DOMSource(doc), new StreamResult(new File("./" + XML.XMLDir + "/net_setting.xml")));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getIp() {
		String ip = "";
		try(final DatagramSocket socket = new DatagramSocket()){
			  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			  ip = socket.getLocalAddress().getHostAddress();
			  return ip;
		}catch(IOException e) {
			e.printStackTrace();
		}
		return ip;
	}
}

class Header{
	public String ip;
	public int port;
	public Header(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
}
