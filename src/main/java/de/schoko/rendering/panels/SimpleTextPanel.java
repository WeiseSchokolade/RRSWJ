package de.schoko.rendering.panels;

import java.awt.Color;
import java.awt.Font;

import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.TextAlignment;

public class SimpleTextPanel extends Panel {
	private boolean applyTheme = true;
	private Font font;
	private Color textColor;
	private String text;
	private int x, y;
	
	public SimpleTextPanel(String text, int x, int y) {
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void load() {
		if (applyTheme) {
			PanelTheme theme = getSystem().getTheme();
			font = theme.getDefaultFont();
			textColor = theme.getTextColor();
		}
	}
	
	@Override
	public void draw(HUDGraph hud) {
		hud.drawText(text, x, y, textColor, font, TextAlignment.LEFT);
	}

}
