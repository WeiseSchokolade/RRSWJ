package de.schoko.rendering.hud;

import java.awt.Graphics2D;

import de.schoko.rendering.Image;

public class ImageDrawCall extends DrawCall {
	private double x, y;
	private Image image;
	private double scale;
	
	public ImageDrawCall(double x, double y, Image image, double scale) {
		this.x = x;
		this.y = y;
		this.image = image;
		this.scale = scale;
	}
	
	@Override
	public void call(Graphics2D g2D) {
		int imageWidth = (int) (image.getWidth() / scale);
		int imageHeight = (int) (image.getHeight() / scale);
		g2D.drawImage(image.getAWTImage(), (int) x, (int) y, imageWidth, imageHeight, null);
	}

}
