package API;

import Component.XML;
import java.util.Scanner;

import com.ibm.watson.developer_cloud.assistant.v2.Assistant;
import com.ibm.watson.developer_cloud.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageInput;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageResponse;
import com.ibm.watson.developer_cloud.assistant.v2.model.SessionResponse;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

public class WatsonConnector {
	public String apikey;
	public String version;
	public String url;
	public Assistant assistant;
	public String sessionId;
	public String assid = "1fcfeee1-dd9f-460f-9a7e-236065bf5cd9";
	public IamOptions options;
	
	public WatsonConnector(){
		apikey = XML.WSkey;
		version = XML.WSversion;
		url = XML.WSurl;
		options = new IamOptions.Builder()
			    .apiKey(apikey)
			    .build();
		assistant = new Assistant(version, options);
		assistant.setEndPoint(url);
		System.out.println("success");
	}
	
	public static WatsonConnector Connect() {
		WatsonConnector wc = new WatsonConnector();
		String ret = wc.createSession();
		WatsonConnector.parseSession(wc, ret);
		System.out.println(wc.sessionId);
		return wc;
	}
	
	public String createSession() {
		Assistant service = new Assistant(version, options);
		CreateSessionOptions options = new CreateSessionOptions.Builder(assid).build();
		SessionResponse response = service.createSession(options).execute();
		System.out.println(response);
		return response.toString();
	}
	
	
	public String sendMessage(String mes, int counter) {
		if(counter >= 2) return null;
		try {
			MessageInput input = new MessageInput.Builder()
					  .messageType("text")
					  .text(mes)
					  .build();

					MessageOptions options = new MessageOptions.Builder(assid, sessionId)
					  .input(input)
					  .build();

					MessageResponse response = assistant.message(options).execute();

					String ret = response.toString();
					int text_ = ret.indexOf("\"text\":") + "\"text\":".length();
					ret = ret.substring(text_);
					int strquto = ret.indexOf("\"");
					ret = ret.substring(strquto + 1);
					int end = ret.indexOf("\"");
					ret = ret.substring(0, end);
					ret = ret.replace(" ", "_");
					return ret;
		}catch(Exception e) {
			System.out.println("session fail");
			counter += 1;
			String ret = this.createSession();
			WatsonConnector.parseSession(this, ret);
			this.sendMessage(ret, counter);
		}
		return null;
	}

	
	public static void parseSession(WatsonConnector wc, String ret) {
		int id = ret.indexOf(':');
		ret = ret.substring(id + 1);
		int aqut = ret.indexOf('\"');
		ret = ret.substring(aqut + 1);
		int end = ret.indexOf('\"');
		wc.sessionId = ret.substring(0, end);
	}

	public static void main(String args[]) {
		XML.InitSetting();
		WatsonConnector wc = WatsonConnector.Connect();
		
		
		while(true) {
			System.out.println("type: ");
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			String mess = scan.next();
			String ret = wc.sendMessage(mess, 0);
			PyRun.textToSpeach(ret);
		}
		
	}
}
