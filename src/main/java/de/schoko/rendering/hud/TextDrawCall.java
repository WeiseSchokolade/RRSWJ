package de.schoko.rendering.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import de.schoko.rendering.Graph;
import de.schoko.rendering.TextAlignment;

public class TextDrawCall extends DrawCall {
	private String text;
	private double x, y;
	private Color color;
	private Font font;
	private TextAlignment textAlignment;
	
	public TextDrawCall(String text, double x, double y, Color color, Font font, TextAlignment textAlignment) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = color;
		this.font = font;
		this.textAlignment = textAlignment;
	}
	
	@Override
	public void call(Graphics2D g2D) {
		g2D.setColor(color);
		g2D.setFont(font);
		if (textAlignment == TextAlignment.LEFT) {
			g2D.drawString(text, (float) x, (float) y);
		} else if (textAlignment == TextAlignment.CENTER) {
			double stringWidth = Graph.getStringWidth(text, font);
			g2D.drawString(text, (float) (x - stringWidth / 2.0), (float) y);
		} else {
			double stringWidth = Graph.getStringWidth(text, font);
			g2D.drawString(text, (float) (x - stringWidth), (float) y);
		}
	}

}
