package de.schoko.rendering.panels;

import java.awt.Color;
import java.awt.Font;

public class PanelTheme {
	private Font defaultFont;
	private Color unselectedColor, hoveredColor, pressedColor;
	private Color panelBackgroundColor;
	private Color textColor;
	private Color checkedColor;
	private int horMargin, verMargin;
	private int outlineWidth;
	
	public PanelTheme(PanelTheme parentTheme) {
		defaultFont = parentTheme.defaultFont;
		unselectedColor = parentTheme.unselectedColor;
		hoveredColor = parentTheme.hoveredColor;
		pressedColor = parentTheme.pressedColor;
		panelBackgroundColor = parentTheme.panelBackgroundColor;
		textColor = parentTheme.textColor;
		checkedColor = parentTheme.checkedColor;
		horMargin = parentTheme.horMargin;
		verMargin = parentTheme.verMargin;
		outlineWidth = parentTheme.outlineWidth;
	}
	
	public PanelTheme(
			Font defaultFont,
			Color unselectedColor,
			Color hoveredColor,
			Color pressedColor,
			Color panelBackgroundColor,
			Color textColor,
			Color checkedColor,
			int verMagin,
			int horMargin,
			int outlineWidth) {
		this.defaultFont = defaultFont;
		this.unselectedColor = unselectedColor;
		this.hoveredColor = hoveredColor;
		this.pressedColor = pressedColor;
		this.panelBackgroundColor = panelBackgroundColor;
		this.textColor = textColor;
		this.checkedColor = checkedColor;
		this.horMargin = horMargin;
		this.verMargin = verMagin;
		this.outlineWidth = outlineWidth;
	}
	
	public Font getDefaultFont() {
		return defaultFont;
	}
	
	public Color getUnselectedColor() {
		return unselectedColor;
	}
	
	public Color getHoveredColor() {
		return hoveredColor;
	}
	
	public Color getPressedColor() {
		return pressedColor;
	}
	
	public Color getPanelBackgroundColor() {
		return panelBackgroundColor;
	}
	
	public Color getTextColor() {
		return textColor;
	}
	
	public Color getCheckedColor() {
		return checkedColor;
	}
	
	public int getHorMargin() {
		return horMargin;
	}
	
	public int getVerMargin() {
		return verMargin;
	}
	
	public int getOutlineWidth() {
		return outlineWidth;
	}
	
	public void setDefaultFont(Font defaultFont) {
		this.defaultFont = defaultFont;
	}
	
	public void setHoveredColor(Color hoveredColor) {
		this.hoveredColor = hoveredColor;
	}
	
	public void setOutlineWidth(int outlineColor) {
		this.outlineWidth = outlineColor;
	}
	
	public void setPanelBackgroundColor(Color panelBackgroundColor) {
		this.panelBackgroundColor = panelBackgroundColor;
	}
	
	public void setPressedColor(Color pressedColor) {
		this.pressedColor = pressedColor;
	}
	
	public void setUnselectedColor(Color unselectedColor) {
		this.unselectedColor = unselectedColor;
	}
	
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	public void setCheckedColor(Color checkedColor) {
		this.checkedColor = checkedColor;
	}
	
	public void setHorMargin(int horMargin) {
		this.horMargin = horMargin;
	}
	
	public void setVerMargin(int verMargin) {
		this.verMargin = verMargin;
	}
}
