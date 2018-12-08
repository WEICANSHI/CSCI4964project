package API;

import Component.XML;


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
	public String sessionId = "ad210506-9afc-437e-8558-824f32b11ebd";
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
	
	public String createSession() {
		Assistant service = new Assistant(version, options);
		CreateSessionOptions options = new CreateSessionOptions.Builder(assid).build();
		SessionResponse response = service.createSession(options).execute();
		System.out.println(response);
		return response.toString();
	}
	
	
	public String sendMessage(String mes) {
		MessageInput input = new MessageInput.Builder()
		  .messageType("text")
		  .text(mes)
		  .build();

		MessageOptions options = new MessageOptions.Builder(assid, sessionId)
		  .input(input)
		  .build();

		MessageResponse response = assistant.message(options).execute();

		System.out.println(response);
		return null;
	}
	
	public static void main(String args[]) {
		XML.InitSetting();
		WatsonConnector wc = new WatsonConnector();
		//wc.createSession();
		wc.sendMessage("I am cold");
	}
}
