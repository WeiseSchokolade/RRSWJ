package de.schoko.rendering;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * @author WeiseSchokolade
 */
class ImgGetter {
	/**
	 * Gets an image from the classpath using {@link javax.swing.ImageIcon}. Used for loading images in JARs.
	 * 
	 * @param imgPath A resource path where the image is located.
	 * @return An image loaded from the classpath.
	 */
	public static BufferedImage getImageFromResources(String imgPath) {
		try {
			return ImageIO.read(ImgGetter.class.getClassLoader().getResourceAsStream(imgPath));
		} catch (IOException e) {
			System.out.println("Couldn't read image: " + imgPath);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets an image from the file system using {@link javax.swing.ImageIcon}. Used for loading images from the filesystem.
	 * 
	 * @param filepath A filepath where the image is located.
	 * @return An image loaded from the filepath.
	 */
	public static BufferedImage getImageFromFile(String filepath) {
		filepath = filepath.replaceAll("\\\\", "/");
		try {
			return ImageIO.read(new File(filepath));
		} catch (IOException e) {
			System.out.println("Couldn't read image: " + filepath);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets an image from the Internet using {@link javax.imageio.ImageIO#read(URL)}.
	 * Catches MalformedURLExceptions and IOExceptions occuring during the process.
	 * 
	 * @param imgUrl A url where the image is located.
	 * @return An image loaded from the web
	 */
	public static BufferedImage getImageFromWeb(String imgUrl) {
		URL url;
		try {
			url = new URL(imgUrl);
			return ImageIO.read(url);
		} catch (MalformedURLException e) {
			System.out.println("Couldn't read image: " + imgUrl);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Couldn't read image: " + imgUrl);
			e.printStackTrace();
		}
		return null;
	}
}
