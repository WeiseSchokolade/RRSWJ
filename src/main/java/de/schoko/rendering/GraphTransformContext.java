package de.schoko.rendering;

public class GraphTransformContext {
	public final int drawXOffset, drawYOffset;
	public final int camX, camY;
	public final int width, height;
	public final double zoom;
	
	protected GraphTransformContext(int drawXOffset, int drawYOffset, int camX, int camY, int width, int height, double zoom) {
		this.drawXOffset = drawXOffset;
		this.drawYOffset = drawYOffset;
		this.camX = camX;
		this.camY = camY;
		this.width = width;
		this.height = height;
		this.zoom = zoom;
	}
}
