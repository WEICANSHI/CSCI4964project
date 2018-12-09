package Component;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Parser {
	/**
	 * Read the image by the file name provided
	 * @param fileName: the image file name
	 * @return the image read
	 * @throws RuntimeException if imag can't load
	 */
	public static Image readImage(String fileName) {
		BufferedImage img = null;	// image buffer
		Image image = null; // Image init as null
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			throw new RuntimeException("Image Load Fail");
		}
		//get the imageIcon
		ImageIcon icon = new ImageIcon(img);
		image = icon.getImage();
		return image;
	}
}
