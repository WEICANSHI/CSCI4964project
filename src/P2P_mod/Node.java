package P2P_mod;

import java.util.Iterator;
import java.util.Map;

public class Node {
	private Reciever reciever;
	private Requester requester;
	
	public Node() {
		Syslog.Init();
		reciever = new Reciever(Syslog.port);
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
