package de.schoko.rendering.panels;

import java.awt.Color;
import java.awt.Font;

import de.schoko.rendering.HUDGraph;
import de.schoko.rendering.Mouse;

public class CheckButtonPanel extends Panel {
	private boolean applyTheme = true;

	private Mouse mouse;
	private boolean checked;
	private String label;
	private int x, y;
	private int width, height;
	
	private int outlineWidth;
	private int outlineWidthx2;
	private int outlineWidthx4;
	private Color checkedColor;
	private Color backgroundColor;
	private Color textColor;
	private Font labelFont;
	private int labelX, labelY;
	private int horMargin;
	
	public CheckButtonPanel(String label, boolean checked, int x, int y, int width, int height) {
		this.label = label;
		this.checked = checked;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void load() {
		if (applyTheme) {
			mouse = getSystem().getContext().getMouse();
			PanelTheme theme = getSystem().getTheme();
			checkedColor = theme.getCheckedColor();
			backgroundColor = theme.getPanelBackgroundColor();
			textColor = theme.getTextColor();
			labelFont = theme.getDefaultFont();
			outlineWidth = theme.getOutlineWidth();
			outlineWidthx2 = outlineWidth * 2;
			outlineWidthx4 = outlineWidthx2 * 2;
			horMargin = theme.getHorMargin();
			setX(x);
			setY(y);
		}
	}

	@Override
	public void update() {
		if (mouse.getScreenX() > x &&
			mouse.getScreenX() < x + width &&
			mouse.getScreenY() > y &&
			mouse.getScreenY() < y + height) {
			if (mouse.wasRecentlyPressed(Mouse.LEFT_BUTTON)) {
				checked = !checked;
			}
		}
	}
	
	@Override
	public void draw(HUDGraph hud) {
		hud.drawRect(x, y, width, height, backgroundColor);
		hud.drawRect(x + outlineWidth, y + outlineWidth, width - outlineWidthx2, height - outlineWidthx2, (checked) ? checkedColor : backgroundColor);
		hud.drawRect(x + outlineWidthx2, y + outlineWidthx2, width - outlineWidthx4, height - outlineWidthx4, backgroundColor);
		hud.drawText(label, labelX, labelY, textColor, labelFont);
	}
	
	public void setX(int x) {
		this.x = x;
		labelX = x + width + horMargin;
	}
	
	public void setY(int y) {
		this.y = y;
		labelY = (int) (y + labelFont.getSize() * 0.85);
	}
}
