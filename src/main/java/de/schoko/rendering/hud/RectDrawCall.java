package de.schoko.rendering.hud;

import java.awt.Color;
import java.awt.Graphics2D;

public class RectDrawCall extends DrawCall {
	private int x, y;
	private int width, height;
	private Color fillColor;
	
	public RectDrawCall(double x, double y, double width, double height, Color fillColor) {
		this.x = (int) x;
		this.y = (int) y;
		this.width = (int) width;
		this.height = (int) height;
		this.fillColor = fillColor;
	}

	@Override
	public void call(Graphics2D g2D) {
		g2D.setColor(fillColor);
		g2D.fillRect(x, y, width, height);
	}
}
