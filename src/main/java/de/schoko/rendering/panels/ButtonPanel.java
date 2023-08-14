package de.schoko.rendering.panels;

import java.awt.Color;
import java.awt.Font;

import de.schoko.rendering.Graph;
import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Mouse;
import de.schoko.rendering.TextAlignment;

public class ButtonPanel extends Panel {
	private boolean applyTheme = true;
	
	private Mouse mouse;
	private int x, y;
	private int width, height;
	private boolean hovered;
	private boolean pressed;
	private boolean clicked;
	
	private String text;
	private Font font;
	private Color textColor;
	private Color backgroundColor;
	private Color unselectedColor, hoveredColor, pressedColor;
	private int horMargin, verMargin;
	private int outlineWidth;
	
	public ButtonPanel(String text, int x, int y) {
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void load() {
		mouse = getSystem().getContext().getMouse();
		if (applyTheme) {
			PanelTheme theme = getSystem().getTheme();
			font = theme.getDefaultFont();
			textColor = theme.getTextColor();
			backgroundColor = theme.getPanelBackgroundColor();
			unselectedColor = theme.getUnselectedColor();
			hoveredColor = theme.getHoveredColor();
			pressedColor = theme.getPressedColor();
			horMargin = theme.getHorMargin();
			verMargin = theme.getVerMargin();
			outlineWidth = theme.getOutlineWidth();
		}
		setFont(font);
	}
	
	@Override
	public void update() {
		hovered = (mouse.getScreenX() > x &&
				mouse.getScreenX() < x + width &&
				mouse.getScreenY() > y &&
				mouse.getScreenY() < y + height);
		
		clicked = false;
		pressed = false;
		if (hovered) {
			if (mouse.wasRecentlyPressed(Mouse.LEFT_BUTTON)) {
				clicked = true;
			}
			if (mouse.isPressed(Mouse.LEFT_BUTTON)) {
				pressed = true;
			}
		}
	}
	
	@Override
	public void draw(HUDGraph hud) {
		if (pressed) {
			hud.drawRect(x, y, width, height, pressedColor);
		} else {
			if (hovered) {
				hud.drawRect(x, y, width, height, hoveredColor);
			} else {
				hud.drawRect(x, y, width, height, unselectedColor);
			}
		}
		
		hud.drawRect(x + outlineWidth, y + outlineWidth, width - outlineWidth * 2, height - outlineWidth * 2, backgroundColor);
		hud.drawText(text, x + horMargin - 1, y + height * 0.9 - verMargin, textColor, font, TextAlignment.LEFT);
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setFont(Font font) {
		this.font = font;
		width = Graph.getStringWidth(text, font) + 2 * horMargin;
		height = font.getSize() + 2 * verMargin;
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	public boolean isHovered() {
		return hovered;
	}
	
	public boolean isClicked() {
		return clicked;
	}
}
