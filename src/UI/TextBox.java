package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Component.XML;
import Component.Parser;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class TextBox extends JPanel {
	private Image background;
	private TextBox self = this;
	private JTextField typefile;
	private JButton send;
	private List<Message> messages;
	private int sY = 50;
	
	public TextBox() {
		this.SetComponent();
		this.addListener();
		messages = new ArrayList<Message>();
		background = Parser.readImage(XML.imageDir + XML.textBackground);
		this.setPreferredSize(new Dimension(500, 700));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		typefile.setBounds(this.getWidth() / 13, this.getHeight() * 9 / 10, this.getWidth() * 9 / 13,
				this.getHeight() / 15);
		send.setBounds(this.getWidth() / 20 + this.getWidth() * 9 / 12, this.getHeight() * 9 / 10,
				this.getHeight() / 10, this.getHeight() / 15);
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		for(int i = 0; i < messages.size(); i ++) {
			drawBubble(g, messages.get(i).text, messages.get(i).y, messages.get(i).direction);
		}
	}
	
	public int getTextHeight(String text) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		Font font = new Font("Tahoma", Font.PLAIN, 20);
		int textheight = (int)(font.getStringBounds(text, frc).getHeight());
		return textheight;
	}
	
	public void drawBubble(Graphics g, String text, int sY, int direction) {
		Graphics2D g2 = (Graphics2D) g;
		int head = this.getWidth() / 15;
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		Font font = new Font("Tahoma", Font.PLAIN, 20);
		int textwidth = (int)(font.getStringBounds(text, frc).getWidth());
		int textheight = (int)(font.getStringBounds(text, frc).getHeight());
		g2.setFont(font);
		if(direction == 0) {
			g2.setColor(Color.GREEN);
			g2.fillRoundRect(this.getWidth() - head - textwidth - textwidth / text.length(), 
							sY - textheight, textwidth + 2 * textwidth / text.length(), textheight + textheight/2, 20, 20);
			g2.setColor(Color.BLACK);
			g2.drawString(text, this.getWidth() - head - textwidth, sY);
		}else {
			g2.setColor(Color.BLUE);
			g2.fillRoundRect(head - textwidth / text.length(), 
							sY - textheight, textwidth + 2 * textwidth / text.length(), textheight + textheight/2, 20, 20);
			g2.setColor(Color.BLACK);
			g2.drawString(text, head, sY);
		}
	}
	
	private void addListener() {
		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == send) {
					Message message = new Message(sY, 0, typefile.getText());
					messages.add(message);
					sY += getTextHeight(typefile.getText()) * 2;
					typefile.setText("");
					self.repaint();
				}
			}
		});
	}
	/**
	 * @refference https://java-swing-tips.blogspot.com/2012/03/rounded-border-for-jtextfield.html
	 */
	private void SetComponent() {
		send = new JButton("send");
		typefile = new JTextField(20) {
			@Override
			protected void paintComponent(Graphics g) {
				if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setPaint(getBackground());
					g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
					g2.dispose();
				}
				super.paintComponent(g);
			}

			@Override
			public void updateUI() {
				super.updateUI();
				setOpaque(false);
				setBorder(new RoundedCornerBorder());
			}
		};
		this.add(typefile);
		this.add(send);
	}
}

/**
 * @author https://java-swing-tips.blogspot.com/2012/03/rounded-border-for-jtextfield.html
 */
@SuppressWarnings("serial")
class RoundedCornerBorder extends AbstractBorder {
	private static final Color ALPHA_ZERO = new Color(0x0, true);

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Shape border = getBorderShape(x, y, width - 1, height - 1);
		g2.setPaint(ALPHA_ZERO);
		Area corner = new Area(new Rectangle2D.Double(x, y, width, height));
		corner.subtract(new Area(border));
		g2.fill(corner);
		g2.setPaint(Color.GRAY);
		g2.draw(border);
		g2.dispose();
	}

	public Shape getBorderShape(int x, int y, int w, int h) {
		int r = h; // h / 2;
		return new RoundRectangle2D.Double(x, y, w, h, r, r);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(4, 8, 4, 8);
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.set(4, 8, 4, 8);
		return insets;
	}
}

class Message{
	public int y;
	public int direction;
	public String text;
	public Message(int y, int direction, String text) {
		this.y = y;
		this.direction = direction;
		this.text = text;
	}
}
