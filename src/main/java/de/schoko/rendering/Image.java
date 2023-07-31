package de.schoko.rendering;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Image {
	private String name;
	private BufferedImage bufferedImage;
	private int width, height;
	
	public Image(String name, BufferedImage image) {
		this.name = name;
		this.bufferedImage = image;
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
	}
	
	private Image(String name, BufferedImage image, int width, int height) {
		this.name = name;
		this.bufferedImage = image;
		this.width = width;
		this.height = height;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Flips the current image horizontally.
	 * @return The flipped image
	 */
	public Image flipHorizontally() {
		Image copy = new Image(name, this.bufferedImage, width, height);
		AffineTransform txTransform = AffineTransform.getScaleInstance(-1, 1);
		txTransform.translate(-width, 0);
		AffineTransformOp op = new AffineTransformOp(txTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		copy.bufferedImage = op.filter(copy.bufferedImage, null);
		return copy;
	}

	/**
	 * Flips the current image vertically.
	 * @return The flipped image
	 */
	public Image flipVertically() {
		Image copy = new Image(name, this.bufferedImage, width, height);
		AffineTransform txTransform = AffineTransform.getScaleInstance(1, -1);
		txTransform.translate(0, -height);
		AffineTransformOp op = new AffineTransformOp(txTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		copy.bufferedImage = op.filter(copy.bufferedImage, null);
		return copy;
	}
	
	/**
	 * @deprecated This name is a little misleading
	 * @return The generated image
	 */
	@Deprecated
	public BufferedImage getAWTImage() {
		return bufferedImage;
	}
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
