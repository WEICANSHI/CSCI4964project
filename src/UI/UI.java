package UI;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import Component.XML;
import P2P_mod.Node;

public class UI extends JFrame{
	private TextBox textbox;
	private ControlPan control;
	private JSplitPane splitPane;
	private Node node;
	
	public UI() {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		node = new Node();
		control = new ControlPan(this);
		textbox = new TextBox();
		splitPane = new JSplitPane(SwingConstants.VERTICAL, textbox, null);
		splitPane.setOneTouchExpandable(true);
		this.add(splitPane);
		splitPane.setRightComponent(control);
		this.pack();
	}
	
	public void input() {
		textbox.input();
	}
	
	public void getMessage(String msg) {
		if(msg.contains("dangerous")) {
			System.out.println("sending message");
			node.sendAll(msg);
		}
	}
	
	public static void main(String args[]) {
		XML.InitSetting();
		new UI();
	}
}
