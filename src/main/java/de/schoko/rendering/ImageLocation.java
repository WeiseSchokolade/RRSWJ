package de.schoko.rendering;

import java.awt.image.BufferedImage;

public enum ImageLocation {
	JAR((String path) -> {
		return ImgGetter.getImageFromResources(path);
	}),
	FILE((String path) -> {
		return ImgGetter.getImageFromFile(path);
	}),
	WEB((String path) -> {
		return ImgGetter.getImageFromWeb(path);
	});
	
	private ImageGetter imageGetter;
	
	ImageLocation(ImageGetter imageGetter) {
		this.imageGetter = imageGetter;
	}
	
	public BufferedImage getImage(String path) {
		return imageGetter.getImage(path);
	}
	
	private interface ImageGetter {
		BufferedImage getImage(String path);
	}
}
