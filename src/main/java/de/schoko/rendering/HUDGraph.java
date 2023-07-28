package de.schoko.rendering;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import de.schoko.rendering.hud.BarDrawCall;
import de.schoko.rendering.hud.CircleDrawCall;
import de.schoko.rendering.hud.DrawCall;
import de.schoko.rendering.hud.ImageDrawCall;
import de.schoko.rendering.hud.LineDrawCall;
import de.schoko.rendering.hud.RectDrawCall;
import de.schoko.rendering.hud.TextDrawCall;

public class HUDGraph {
	private Graphics2D g2D;
	private double width, height;
	private ArrayList<DrawCall> drawCalls;
	
	public HUDGraph(Graphics2D gEntered, double width, double height) {
		g2D = gEntered;
		this.width = width;
		this.height = height;
		drawCalls = new ArrayList<>();
	}
	
	/**
	 * Applies all recent draw calls
	 */
	public void call() {
		for (int i = 0; i < drawCalls.size(); i++) {
			drawCalls.get(i).call(g2D);
		}
		drawCalls.clear();
	}
	
	public void draw(DrawCall drawCall, DrawCall... drawCalls) {
		this.drawCalls.add(drawCall);
		for (int i = 0; i < drawCalls.length; i++) {
			this.drawCalls.add(drawCalls[i]);
		}
	}
	
	public void drawText(String s, double x, double y, Color color, Font font) {
		drawText(s, x, y, color, font, TextAlignment.LEFT);
	}
	
	public void drawText(String s, double x, double y, Color color, Font font, TextAlignment textAlignment) {
		drawCalls.add(new TextDrawCall(s, x, y, color, font, textAlignment));
	}
	
	public void drawRect(double x, double y, double width, double height, Color fillColor) {
		drawCalls.add(new RectDrawCall(x, y, width, height, fillColor));
	}
	
	public void drawBar(double x, double y, double width, double height, double perc, Color innerColor, Color outerColor) {
		drawCalls.add(new BarDrawCall(x, y, width, height, perc, innerColor, outerColor));
	}

	/**
	 * Draws java.awt.Image
	 * @see java.awt.Image
	 */
	public void drawImage(double x, double y, java.awt.Image image, double scale) {
		drawCalls.add(new ImageDrawCall(x, y, image, scale));
	}
	
	/**
	 * Draws de.schoko.rendering.Image
	 * @see de.schoko.rendering.Image
	 */
	public void drawImage(double x, double y, Image image, double scale) {
		drawCalls.add(new ImageDrawCall(x, y, image, scale));
	}
	
	public void drawCircle(double x, double y, double radius, Color color) {
		drawCalls.add(new CircleDrawCall(x, y, radius, color));
	}
	
	public void drawLine(double x0, double y0, double x1, double y1, Color color) {
		drawCalls.add(new LineDrawCall(x0, y0, x1, y1, color));
	}

	public void drawLine(double x0, double y0, double x1, double y1, Color color, float width) {
		drawCalls.add(new LineDrawCall(x0, y0, x1, y1, color, width));
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
}
