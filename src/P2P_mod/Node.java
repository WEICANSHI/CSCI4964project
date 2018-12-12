package P2P_mod;

import java.util.Iterator;
import java.util.Map;

import UI.UI;

public class Node {
	private Reciever reciever;
	private Requester requester;
	private UI ref;
	
	public Node(UI ref) {
		Syslog.Init();
		this.ref = ref;
		reciever = new Reciever(Syslog.port, ref);
		new Thread(reciever).start();
	}
	
	public void sendMessage(String id, String message) {
		String ip = Syslog.peers.get(id).ip;
		int port = Syslog.peers.get(id).port;
		requester = new Requester(ip, port);
		requester.sendMessage(message);
	}
	
	public void sendAll(String message) {
		Iterator<Map.Entry<String, Header>> itr = Syslog.peers.entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<String, Header> tmp = itr.next();
			this.sendMessage(tmp.getKey(), message);
		}
	}
	
	public void addPeer(String id, String ip, int port) {
		
	}
	
	public void joinNetwork() {
		
	}
	
}
