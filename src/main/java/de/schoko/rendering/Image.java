package de.schoko.rendering;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Image {
	private String name;
	private BufferedImage bufferedImage;
	private int width, height;
	
	public Image(String path, ImageLocation location) {
		this.name = path;
		this.bufferedImage = location.getImage(path);
		this.width = bufferedImage.getWidth(null);
		this.height = bufferedImage.getHeight(null);
	}
	
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
	
	private Image copy(boolean copyBufferedImage) {
		Image copy = new Image(this.name, bufferedImage, width, height);
		if (!copyBufferedImage) {
			ColorModel model = copy.bufferedImage.getColorModel();
			WritableRaster raster = copy.bufferedImage.copyData(null);
			boolean alphaPremultiplied = model.isAlphaPremultiplied();
			copy.bufferedImage = new BufferedImage(model, raster, alphaPremultiplied, null);
		}
		return copy;
	}
	
	/**
	 * Flips the current image horizontally.
	 * @return The flipped image
	 */
	public Image flipHorizontally() {
		Image copy = copy(true);
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
		Image copy = copy(true);
		AffineTransform txTransform = AffineTransform.getScaleInstance(1, -1);
		txTransform.translate(0, -height);
		AffineTransformOp op = new AffineTransformOp(txTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		copy.bufferedImage = op.filter(copy.bufferedImage, null);
		return copy;
	}
	
	/**
	 * @return A copy of the image rotated clockwise
	 */
	public Image turnClockwise() {
		Image copy = copy(true);
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.PI * 0.5, copy.width * 0.5, copy.height * 0.5);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		copy.bufferedImage = op.filter(copy.bufferedImage, null);
		return copy;
	}

	/**
	 * @return A copy of the image rotated counter clockwise
	 */
	public Image turnCounterClockwise() {
		Image copy = copy(true);
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.PI * -0.5, copy.width * 0.5, copy.height * 0.5);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
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
