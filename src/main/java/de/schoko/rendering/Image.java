package de.schoko.rendering;

public class Image {
	private String name;
	private java.awt.Image image;
	private int width, height;
	
	public Image(String name, java.awt.Image image) {
		this.name = name;
		this.image = image;
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
	}
	
	public String getName() {
		return name;
	}
	
	public java.awt.Image getAWTImage() {
		return image;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
