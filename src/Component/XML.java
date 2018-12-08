package Component;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


public class XML {
	
	public static String XMLDir;
	public static String ZipDir;
	public static String imageDir;
	public static String IRkey;
	public static String IRversion;
	public static String WSkey;
	public static String WSversion;
	public static String WSurl;
	public static String textBackground;
	public static String buttonIcon;
	
	public static void InitSetting(){
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("./XML/sys_setting.xml"));
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

					if(childElement.getTagName().equals("ImageDir"))
						imageDir = text;
					else if(childElement.getTagName().equals("XMLDir"))
						XMLDir = text;
					else if(childElement.getTagName().equals("ZipDir"))
						ZipDir = text;
					else if(childElement.getTagName().equals("IRkey"))
						IRkey = text;
					else if(childElement.getTagName().equals("IRversion"))
						IRversion = text;
					else if(childElement.getTagName().equals("WSkey"))
						WSkey = text;
					else if(childElement.getTagName().equals("WSversion"))
						WSversion = text;
					else if(childElement.getTagName().equals("WSurl"))
						WSurl = text;
					else if(childElement.getTagName().equals("textBackground"))
						textBackground = text;
					else if(childElement.getTagName().equals("buttonIcon"))
						buttonIcon = text;
					
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) throws Exception {
		XML.InitSetting();
		
	}
}
