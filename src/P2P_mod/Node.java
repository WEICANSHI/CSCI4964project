package P2P_mod;

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
	
	public void addPeer(String id, String ip, int port) {
		
	}
	
	public void joinNetwork() {
		
	}
	
	public static void main(String args[]) {
		Node node = new Node();
		//node.sendMessage();
	}
}
