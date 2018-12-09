package UI;

import javax.swing.JFrame;

import Component.XML;

public class UI extends JFrame{
	private TextBox textbox = new TextBox();
	public UI() {
		//this.setSize(1000, 700);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setResizable(false);
		this.add(textbox);
		this.pack();
	}
	
	public void input() {
		textbox.input();
	}
	
	public static void main(String args[]) {
		XML.InitSetting();
		UI u = new UI();
		//u.input();
	}
}
