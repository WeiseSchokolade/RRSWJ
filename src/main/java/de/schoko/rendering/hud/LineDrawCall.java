package de.schoko.rendering.hud;

import java.awt.Color;
import java.awt.Graphics2D;

public class LineDrawCall extends DrawCall {
	private int x0, y0;
	private int x1, y1;
	private Color color;
	
	public LineDrawCall(double x0, double y0, double x1, double y1, Color color) {
		this.x0 = (int) x0;
		this.y0 = (int) y0;
		this.x1 = (int) x1;
		this.y1 = (int) y1;
		this.color = color;
	}
	
	@Override
	public void call(Graphics2D g2D) {
		g2D.setColor(color);
		g2D.drawLine(x0, y0, x1, y1);
	}

}
