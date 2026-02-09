package de.schoko.rendering;

import java.awt.Color;
import java.awt.Font;

public interface OverloadedGraph {
	void drawCoordinateSystem(double x, double y);
	
	default void drawCoordinateSystem(CoordProvider pos) {
		drawCoordinateSystem(pos.getX(), pos.getY());
	}
	
	default void drawCoordinateSystem() {
		drawCoordinateSystem(0, 0);
	}

	
	
	void drawString(String string, double x, double y, Color color);
	
	default void drawString(String string, double x, double y) {
		drawString(string, x, y, Color.BLACK);
	}
	
	default void drawString(String string, CoordProvider pos) {
		drawString(string, pos.getX(), pos.getY(), Color.BLACK);
	}
	
	default void drawString(String string, CoordProvider pos, Color color) {
		drawString(string, pos.getX(), pos.getY(), color);
	}

	
	void drawString(String string, double x, double y, Color color, Font font);
	
	default void drawString(String string, double x, double y, Font font) {
		drawString(string, x, y, Color.BLACK, font);
	}
	
	default void drawString(String string, CoordProvider pos, Font font) {
		drawString(string, pos.getX(), pos.getY(), Color.BLACK, font);
	}
	
	default void drawString(String string, CoordProvider pos, Color color, Font font) {
		drawString(string, pos.getX(), pos.getY(), color, font);
	}
	
	
	void drawString(String string, double x, double y, Color color, Font font, TextAlignment textAlignment);

	default void drawString(String string, double x, double y, Font font, TextAlignment textAlignment) {
		drawString(string, x, y, Color.BLACK, font, textAlignment);
	}
	
	default void drawString(String string, CoordProvider pos, Font font, TextAlignment textAlignment) {
		drawString(string, pos.getX(), pos.getY(), Color.BLACK, font, textAlignment);
	}
	
	default void drawString(String string, CoordProvider pos, Color color, Font font, TextAlignment textAlignment) {
		drawString(string, pos.getX(), pos.getY(), color, font, textAlignment);
	}
	
	
	void drawPoint(double x, double y, Color color);

	/**
	 * Draws a black point
	 */
	default void drawPoint(double x, double y) {
		drawPoint(x, y, Color.BLACK);
	}

	/**
	 * Draws a black point
	 */
	default void drawPoint(CoordProvider pos) {
		drawPoint(pos.getX(), pos.getY(), Color.BLACK);
	}
	
	default void drawPoint(CoordProvider pos, Color color) {
		drawPoint(pos.getX(), pos.getY(), color);
	}
	
	
	/**
	 * Draws a line between the points (x0, y0) and (x1, y1) with the given color
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @param color Color of the line
	 */
	public void drawLine(double x0, double y0, double x1, double y1, Color color);
	
	/**
	 * Draws a black line between the points (x0, y0) and (x1, y1)
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	default void drawLine(double x0, double y0, double x1, double y1) {
		drawLine(x0, y0, x1, y1, Color.BLACK);
	}
	
	default void drawLine(CoordProvider pos0, CoordProvider pos1) {
		drawLine(pos0.getX(), pos0.getY(), pos1.getX(), pos1.getY(), Color.BLACK);
	}

	default void drawLine(CoordProvider pos0, CoordProvider pos1, Color color) {
		drawLine(pos0.getX(), pos0.getY(), pos1.getX(), pos1.getY(), color);
	}
	
	/**
	 * Draws a line between the points (x0, y0) and (x1, y1) with the given color and the stroke width in application line width.
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @param color Color of the line
	 * @param strokeWidth Width of line in application line width.
	 */
	public void drawLine(double x0, double y0, double x1, double y1, Color color, double strokeWidth);

	default void drawLine(CoordProvider coord1, CoordProvider coord2, Color color, double strokeWidth) {
		drawLine(coord1.getX(), coord1.getY(), coord2.getX(), coord2.getY(), color, strokeWidth);
	}
	
	
	
	/**
	 * Draws a circle
	 * @param x coordinate of center
	 * @param y coordinate of center
	 * @param color Color of the circle
	 * @param radius Radius of the circle
	 */
	public void drawCircle(double x, double y, Color color, double radius);
	
	default void drawCircle(CoordProvider pos, Color color, double radius) {
		drawCircle(pos.getX(), pos.getY(), color, radius);
	}
	
	/**
	 * Draws a filled circle
	 * @param x coordinate of center
	 * @param y coordinate of center
	 * @param color Color of the circle
	 * @param radius Radius of the circle
	*/
	public void fillCircle(double x, double y, Color color, double radius);

	default void fillCircle(CoordProvider pos, Color color, double radius) {
		fillCircle(pos.getX(), pos.getY(), color, radius);
	}
	
	
	void drawRect(double x, double y, double width, double height, Color color);

	default void drawRect(double x, double y, double width, double height) {
		drawRect(x, y, width, height, Color.BLACK);
	}
	
	default void drawRect(CoordProvider pos, double width, double height) {
		drawRect(pos.getX(), pos.getY(), width, height, Color.BLACK);
	}
	
	default void drawRect(CoordProvider pos, double width, double height, Color color) {
		drawRect(pos.getX(), pos.getY(), width, height, color);
	}

	
	void fillRect(double x, double y, double width, double height, Color color);

	default void fillRect(double x, double y, double width, double height) {
		fillRect(x, y, width, height, Color.BLACK);
	}
	
	default void fillRect(CoordProvider pos, double width, double height) {
		fillRect(pos.getX(), pos.getY(), width, height, Color.BLACK);
	}
	
	default void fillRect(CoordProvider pos, double width, double height, Color color) {
		fillRect(pos.getX(), pos.getY(), width, height, color);
	}
	
	
	
	void drawPivottedRotatedRect(double x, double y, double pivotX, double pivotY, double width, double height, double degrees, Color color);
	
	default void drawPivottedRotatedRect(CoordProvider pos, CoordProvider localPivot, double width, double height, double degrees, Color color) {
		drawPivottedRotatedRect(pos.getX(), pos.getY(), localPivot.getX(), localPivot.getY(), width, height, degrees, color);
	}
	
	default void drawRotatedRect(double x, double y, double width, double height, double degrees, Color color) {
		drawPivottedRotatedRect(x, y, width * 0.5, height * 0.5, width, height, degrees, color);
	}
	
	default void drawRotatedRect(CoordProvider pos, double width, double height, double degrees, Color color) {
		drawPivottedRotatedRect(pos.getX(), pos.getY(), width * 0.5, height * 0.5, width, height, degrees, color);
	}
	
	default void drawRotatedCenteredRect(double x, double y, double width, double height, double degrees, Color color) {
		drawPivottedRotatedRect(x - width * 0.5, y - height * 0.5, width * 0.5, height * 0.5, width, height, degrees, color);
	}
	
	default void drawRotatedCenteredRect(CoordProvider pos, double width, double height, double degrees, Color color) {
		drawPivottedRotatedRect(pos.getX() - width * 0.5, pos.getY() - height * 0.5, width * 0.5, height * 0.5, width, height, degrees, color);
	}
	

	
	void fillPivottedRotatedRect(double x, double y, double pivotX, double pivotY, double width, double height, double degrees, Color color);
	
	default void fillPivottedRotatedRect(CoordProvider pos, CoordProvider localPivot, double width, double height, double degrees, Color color) {
		fillPivottedRotatedRect(pos.getX(), pos.getY(), localPivot.getX(), localPivot.getY(), width, height, degrees, color);
	}
	
	default void fillRotatedRect(double x, double y, double width, double height, double degrees, Color color) {
		fillPivottedRotatedRect(x, y, width * 0.5, height * 0.5, width, height, degrees, color);
	}
	
	default void fillRotatedRect(CoordProvider pos, double width, double height, double degrees, Color color) {
		fillPivottedRotatedRect(pos.getX(), pos.getY(), width * 0.5, height * 0.5, width, height, degrees, color);
	}
	
	default void fillRotatedCenteredRect(double x, double y, double width, double height, double degrees, Color color) {
		fillPivottedRotatedRect(x - width * 0.5, y - height * 0.5, width * 0.5, height * 0.5, width, height, degrees, color);
	}
	
	default void fillRotatedCenteredRect(CoordProvider pos, double width, double height, double degrees, Color color) {
		fillPivottedRotatedRect(pos.getX() - width * 0.5, pos.getY() - height * 0.5, width * 0.5, height * 0.5, width, height, degrees, color);
	}
	
	
	

	void drawImage(Image image, double x, double y, double scale);

	default void drawImage(Image image, CoordProvider pos, double scale) {
		drawImage(image, pos.getX(), pos.getY(), scale);
	}

	
	void drawImage(java.awt.Image image, double x, double y, double scale);

	default void drawImage(java.awt.Image image, CoordProvider pos, double scale) {
		drawImage(image, pos.getX(), pos.getY(), scale);
	}
}
