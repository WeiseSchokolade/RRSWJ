package de.schoko.rendering.hud;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class LineDrawCall extends DrawCall {
	protected static float lastRecordedStrokeWidth;
	private int x0, y0;
	private int x1, y1;
	private Color color;
	private float width;

	public LineDrawCall(double x0, double y0, double x1, double y1, Color color) {
		this.x0 = (int) x0;
		this.y0 = (int) y0;
		this.x1 = (int) x1;
		this.y1 = (int) y1;
		this.color = color;
		this.width = -1;
	}
	public LineDrawCall(double x0, double y0, double x1, double y1, Color color, float width) {
		this.x0 = (int) x0;
		this.y0 = (int) y0;
		this.x1 = (int) x1;
		this.y1 = (int) y1;
		this.color = color;
		this.width = width;
	}
	
	@Override
	public void call(Graphics2D g2D) {
		g2D.setColor(color);
		if (width != -1) {
			if (width != lastRecordedStrokeWidth) {
				g2D.setStroke(new BasicStroke(width));
				lastRecordedStrokeWidth = width;
			}
		}
		g2D.drawLine(x0, y0, x1, y1);
	}

}
