package de.schoko.rendering;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import de.schoko.rendering.shapes.Shape;

public class Graph {
	private static final int POINT_RADIUS = 2;
	private static final float DEBUG_FONT_SIZE = 14f;
	private static final float NOTIFICATION_FONT_SIZE = 25f;
	
	private Panel panel;
	private Graphics2D g2D;
	private ArrayList<String> debugStrings;
	private HUDGraph hud;
	private Viewport viewport;
	private GraphTransform transform;
	
	public Graph(Panel panel, Graphics gEntered, Camera camera, RendererSettings rendererSettings, GraphTransform transform) {
		this.panel = panel;
		this.g2D = (Graphics2D) gEntered;
		this.viewport = camera.getViewport();
		this.transform = transform;
		this.transform.setGTC(new GraphTransformContext(
				viewport.getDrawXOffset(),
				viewport.getDrawYOffset(),
				(int) (camera.getX() * camera.getZoom()),
				(int) -(camera.getY() * camera.getZoom()),
				viewport.getWidth(),
				viewport.getHeight(),
				camera.getZoom()));
		this.hud = new HUDGraph(g2D, panel.getWidth(), panel.getHeight());
		g2D.setColor(rendererSettings.getBackgroundColor());
		g2D.fillRect(viewport.getDrawXOffset(), viewport.getDrawYOffset(), viewport.getWidth(), viewport.getHeight());
		g2D.setStroke(new BasicStroke((float) camera.getZoom() / 10));
		
		if (rendererSettings.isRenderingCoordinateSystem()) {
			drawLine(0, 10, 0, -10, Color.BLUE);
			drawLine(-10, 0, 10, 0, Color.RED);
			for (int i = -10; i <= 10; i++) {
				if (i == 0) continue;
				drawLine(i, 0.1, i, -0.1, Color.RED);
			}
			for (int i = -10; i <= 10; i++) {
				if (i == 0) continue;
				drawLine(-0.1, i, 0.1, i, Color.BLUE);
			}
		}
		debugStrings = new ArrayList<>();
	}
	
	public static Color getColor(int r, int g, int b) {
		return new Color(r, g, b);
	}
	
	public static Color getColor(int r, int g, int b, int a) {
		return new Color(r, g, b, a);
	}
	
	/**
	 * @param text The string to be measured
	 * @param font The font this text is to be written in
	 * @return The width of the entered string in pixels
	 */
	public static int getStringWidth(String text, Font font) {
		Canvas canvas = new Canvas();
		return canvas.getFontMetrics(font).stringWidth(text);
	}
	
	/**
	 * This method cannot be static because it uses the underlying {@link Graphics2D#getFontMetrics()} method
	 * @param text The string to be measured
	 * @return The width of the entered string in pixels
	 */
	public int getStringWidth(String text) {
		return g2D.getFontMetrics(g2D.getFont()).stringWidth(text);
	}
	
	public void finalize() {
		hud.draw();
		
		// Draw Notifications
		g2D.setColor(Color.BLACK);
		g2D.setFont(new Font("Segoe UI", Font.PLAIN, (int) NOTIFICATION_FONT_SIZE));
		double boxOffset = 5;
		double boxMargin = 4;
		
		// The higher the offset the lower the oldest message
		List<Notification> notifications = panel.getNotifications();
		double notificationHeight = NOTIFICATION_FONT_SIZE + boxMargin * 2;
		double offset = notifications.size() * (notificationHeight + boxOffset);
		for (int i = 0; i < notifications.size(); i++) {
			Notification n = notifications.get(i);
			int stringWidth = g2D.getFontMetrics().stringWidth(n.getMessage());
			g2D.setColor(new Color(0.1f, 0.1f, 0.1f, 0.1f));
			g2D.fillRect((int) (hud.getWidth() / 2 - stringWidth / 2 - boxMargin), (int) (offset - NOTIFICATION_FONT_SIZE - boxMargin), (int) (stringWidth + boxMargin * 2), (int) (notificationHeight + boxMargin));
			g2D.setColor(Color.BLACK);
			g2D.drawString(n.getMessage(), (int) (hud.getWidth() / 2 - stringWidth / 2), (int) offset);
			offset -= notificationHeight + boxOffset;
		}
		
		g2D.setColor(Color.BLUE);
		g2D.setFont(g2D.getFont().deriveFont(DEBUG_FONT_SIZE));
		for (int i = 0; i < debugStrings.size(); i++) {
			g2D.drawString(debugStrings.get(i), 1, i * (DEBUG_FONT_SIZE + 2) + DEBUG_FONT_SIZE);
		}
	}
	
	public void draw(Shape... shapes) {
		for (int i = 0; i < shapes.length; i++) {
			shapes[i].render(this);
		}
	}
	
	public void drawString(String s, double x, double y) {
		g2D.setColor(Color.BLACK);
		g2D.drawString(s, convSX(x), convSY(y));
	}
	
	public void drawString(String s, double x, double y, Color c) {
		g2D.setColor(c);
		g2D.drawString(s, convSX(x), convSY(y));
	}
	
	public void drawString(String s, double x, double y, Color c, Font font) {
		Font previousFont = g2D.getFont();
		g2D.setFont(font);
		g2D.setColor(c);
		g2D.drawString(s, convSX(x), convSY(y));
		g2D.setFont(previousFont);
	}
	
	public void drawString(String s, double x, double y, Color c, Font font, TextAlignment textAlignment) {
		Font previousFont = g2D.getFont();
		g2D.setFont(font);
		g2D.setColor(c);
		double stringWidth = getStringWidth(s, font);
		if (textAlignment == TextAlignment.LEFT) {
			g2D.drawString(s, convSX(x), convSY(y));
		} else if (textAlignment == TextAlignment.CENTER) {
			g2D.drawString(s, (int) (convSX(x) - stringWidth / 2), convSY(y));
		} else {
			g2D.drawString(s, (int) (convSX(x) - stringWidth), convSY(y));
		}
		g2D.setFont(previousFont);
	}
	
	/**
	 * Calls {@link Graph#drawPoint(double, double, Color)} with the color black
	 */
	public void drawPoint(double x, double y) {
		drawPoint(x, y, Color.BLACK);
	}
	
	/**
	 * Similar to {@link Graph#drawCircle(double, double, Color, double)} but will always use a radius of 0.2
	 */
	public void drawPoint(double x, double y, Color c) {
		g2D.setColor(c);
		g2D.fillArc(convSX(x) - POINT_RADIUS, convSY(y) + POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2, 0, 360);
	}
	
	/**
	 * Draws a circle
	 * @param x coordinate of center
	 * @param y coordinate of center
	 * @param c Color of circle
	 * @param radius Radius of circle
	 */
	public void drawCircle(double x, double y, Color c, double radius) {
		g2D.setColor(c);
		g2D.drawArc(convSX(x - radius), convSY(y + radius), convSW(radius * 2), convSH(radius * 2), 0, 360);
	}
	
	/**
	 * Fills a circle
	 * @param x coordinate of center
	 * @param y coordinate of center
	 * @param c Color of circle
	 * @param radius Radius of circle
	*/
	public void fillCircle(double x, double y, Color c, double radius) {
		g2D.setColor(c);
		g2D.fillArc(convSX(x - radius), convSY(y + radius), convSW(radius * 2), convSH(radius * 2), 0, 360);
	}
	
	/**
	 * Draws a line between the points (x0, y0) and (x1, y1) with the color Black
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	public void drawLine(double x0, double y0, double x1, double y1) {
		drawLine(x0, y0, x1, y1, Color.BLACK);
	}
	
	/**
	 * Draws a line between the points (x0, y0) and (x1, y1) with the given color
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @param c Color of the line
	 */
	public void drawLine(double x0, double y0, double x1, double y1, Color c) {
		g2D.setColor(c);
		g2D.drawLine(convSX(x0), convSY(y0), convSX(x1), convSY(y1));
	}

	/**
	 * Draws a line between the points (x0, y0) and (x1, y1) with the given color and the stroke width in pixels
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @param c Color of the line
	 * @param strokeWidth Width of line in pixels.
	 */
	public void drawLine(double x0, double y0, double x1, double y1, Color c, float strokeWidth) {
		Stroke prevStroke = g2D.getStroke();
		g2D.setStroke(new BasicStroke(strokeWidth));
		g2D.setColor(c);
		g2D.drawLine(convSX(x0), convSY(y0), convSX(x1), convSY(y1));
		g2D.setStroke(prevStroke);
	}
	
	public void drawRect(double x0, double y0, double x1, double y1) {
		drawRect(x0, y0, x1, y1, Color.BLACK);
	}
	
	public void drawRect(double x0, double y0, double x1, double y1, Color c) {
		g2D.setColor(c);
		double ax0 = x0;
		double ax1 = x1;
		double ay0 = y0;
		double ay1 = y1;
		if (x0 > x1) {
			ax0 = x1;
			ax1 = x0;
		}
		if (y0 > y1) {
			ay0 = y1;
			ay1 = y0;
		}
		g2D.drawRect(convSX(ax0), convSY(ay1), convSW(ax1 - ax0), convSH(ay1 - ay0));
	}
	
	public void fillRect(double x0, double y0, double x1, double y1) {
		fillRect(x0, y0, x1, y1, Color.BLACK);
	}
	
	public void fillRect(double x0, double y0, double x1, double y1, Color c) {
		g2D.setColor(c);
		double ax0 = x0;
		double ax1 = x1;
		double ay0 = y0;
		double ay1 = y1;
		if (x0 > x1) {
			ax0 = x1;
			ax1 = x0;
		}
		if (y0 > y1) {
			ay0 = y1;
			ay1 = y0;
		}
		g2D.fillRect(convSX(ax0), convSY(ay1), convSW(ax1 - ax0), convSH(ay1 - ay0));
	}

	public void fillRotatedRect(double x, double y, double width, double height, double degrees, Color c) {
		double drawnWidth = convSW(width);
		double drawnHeight = convSH(height);
		int areaSize = (int) (drawnWidth > drawnHeight ? drawnWidth * 2 : drawnHeight * 2);
		Graphics2D area = (Graphics2D) g2D.create(convSX(x) - (int) drawnWidth, convSY(y) - (int) drawnHeight, (int) areaSize, (int) areaSize);
		double angle = convSA(-degrees);
		area.rotate(angle, areaSize / 2, areaSize / 2);
		area.setColor(c);
		area.fillRect((int) (drawnWidth / 2), (int) (drawnHeight / 2), (int) drawnWidth, (int) drawnHeight);
		area.dispose();
	}

	public void drawImage(Image image, double x, double y, double scale) {
		int imgWidth = (int) convSW(image.getWidth() / scale);
		int imgHeight = (int) convSH(image.getHeight() / scale);
		g2D.drawImage(image.getAWTImage(), convSX(x) - imgWidth / 2, convSY(y) - imgHeight / 2, imgWidth, imgHeight, null);
	}
	
	public void drawImage(java.awt.Image image, double x, double y, double scale) {
		int imgWidth = (int) convSW(image.getWidth(null) / scale);
		int imgHeight = (int) convSH(image.getHeight(null) / scale);
		g2D.drawImage(image, convSX(x) - imgWidth / 2, convSY(y) - imgHeight / 2, imgWidth, imgHeight, null);
	}

	public void drawRotatedImage(Image image, double x, double y, double scale, double degrees) {
		double imgWidth = convSW(image.getWidth() / scale);
		double imgHeight = convSH(image.getHeight() / scale);
		int areaSize = (int) (imgWidth > imgHeight ? imgWidth * 2 : imgHeight * 2);
		Graphics2D area = (Graphics2D) g2D.create(convSX(x) - (int) imgWidth, convSY(y) - (int) imgHeight, (int) areaSize, (int) areaSize);
		double angle = convSA(degrees);
		area.rotate(angle, imgWidth, imgHeight);
		area.drawImage(image.getAWTImage(), (int) (imgWidth / 2), (int) (imgHeight / 2), (int) imgWidth, (int) imgHeight, null);
		area.dispose();
	}
	
	public void drawRotatedImage(java.awt.Image image, double x, double y, double scale, double degrees) {
		double imgWidth = convSW(image.getWidth(null) / scale);
		double imgHeight = convSH(image.getHeight(null) / scale);
		int areaSize = (int) (imgWidth > imgHeight ? imgWidth * 2 : imgHeight * 2);
		Graphics2D area = (Graphics2D) g2D.create(convSX(x) - (int) imgWidth, convSY(y) - (int) imgHeight, (int) areaSize, (int) areaSize);
		double angle = convSA(degrees);
		area.rotate(angle, imgWidth, imgHeight);
		area.drawImage(image, (int) (imgWidth / 2), (int) (imgHeight / 2), (int) imgWidth, (int) imgHeight, null);
		area.dispose();
	}
	
	/**
	 * Adds a string to the current frame which will be drawn in blue in the top left corner.
	 * @param s
	 */
	public void addDebugString(String s) {
		this.debugStrings.add(s);
	}
	
	/**
	 * Adds a notification once.
	 * @param message The message
	 * @param time The time the notification is displayed
	 */
	public void addNotification(String message, double time) {
		panel.addNotification(new Notification(message, time));
	}
	
	public int convSX(double x) {
		return transform.convSX(x);
	}
	
	public int convSY(double y) {
		return transform.convSY(y);
	}
	
	public double convBackFromSX(double x) {
		return transform.convBackFromSX(x);
	}
	
	public double convBackFromSY(double y) {
		return transform.convBackFromSY(y);
	}

    public int convSW(double w) {
    	return transform.convSW(w);
    }
    
    public double convBackFromSW(double w) {
    	return transform.convBackFromSW(w);
    }
    
    public int convSH(double h) {
    	return transform.convSH(h);
    }
    
    public double convBackFromSH(double h) {
    	return transform.convBackFromSH(h);
    }
    
    /**
     * Converts degrees to screen radians
     * @param degrees
     * @return radians
     */
    public double convSA(double degrees) {
    	return Math.toRadians((degrees - 90));
    }
    
    public HUDGraph getHUD() {
    	return hud;
    }
    
    public Viewport getViewport() {
    	return viewport;
    }
    
    public Graphics2D getAWTGraphics() {
    	return g2D;
    }
}
