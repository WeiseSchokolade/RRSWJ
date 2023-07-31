package de.schoko.rendering.hud;

import java.awt.Graphics2D;

import de.schoko.rendering.Image;

public class ImageDrawCall extends DrawCall {
	private double x, y;
	private java.awt.Image image;
	private int imageWidth;
	private int imageHeight;
	
	public ImageDrawCall(double x, double y, java.awt.Image image, double scale) {
		this.x = x;
		this.y = y;
		this.image = image;
		imageWidth = (int) (image.getWidth(null) / scale);
		imageHeight = (int) (image.getHeight(null) / scale);
	}
	
	public ImageDrawCall(double x, double y, Image image, double scale) {
		this.x = x;
		this.y = y;
		this.image = image.getBufferedImage();
		imageWidth = (int) (image.getWidth() / scale);
		imageHeight = (int) (image.getHeight() / scale);
	}
	
	@Override
	public void call(Graphics2D g2D) {
		g2D.drawImage(image, (int) x, (int) y, imageWidth, imageHeight, null);
	}

}
