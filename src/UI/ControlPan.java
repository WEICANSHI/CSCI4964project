package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ControlPan extends JPanel implements MouseListener{
	public UI ref;
	public String warning;
	public JButton connect = new JButton("connect");
	public JButton test01 = new JButton("test voice");
	public JButton test02 = new JButton("test img");
	
	public ControlPan(UI ref) {
		warning = null;
		this.ref = ref;
		this.add(connect);
		this.add(test01);
		this.add(test02);
		test01.addMouseListener(this);
		test02.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if(warning == null) {
			g2.setColor(Color.GREEN);
			g2.fill3DRect(this.getWidth() / 15, this.getHeight() / 10, 
					this.getWidth() - this.getWidth() * 2 / 15, this.getHeight() / 2, true);
		}else {
			g2.setColor(Color.RED);
			g2.fill3DRect(this.getWidth() / 15, this.getHeight() / 10, 
					this.getWidth() - this.getWidth() * 2 / 15, this.getHeight() / 2, true);
			g2.setColor(Color.GREEN);
			Font font = new Font("Tahoma", Font.PLAIN, 30);
			g2.setFont(font);
			g2.drawString(warning, this.getWidth() * 2 / 5, this.getHeight() / 4);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == test01) {
			JFileChooser chooser = new JFileChooser("./py/");
			chooser.setMultiSelectionEnabled(true);
			chooser.showOpenDialog(null);
			File file = chooser.getSelectedFile();
			if(file != null) {
				if(file.getName().contains(".wav")) {
					ref.input();
				}
			}
		}
		if(e.getSource() == test02) {
			ref.getMessage("dangerous! Speed down!");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	} 
}
