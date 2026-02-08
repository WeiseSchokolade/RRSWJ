package de.schoko.rendering;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;

import de.schoko.rendering.panels.PanelSystem;
import de.schoko.rendering.shapes.Shape;

public class Graph {
	private static final int POINT_RADIUS = 2;
	private static final float DEBUG_FONT_SIZE = 14f;
	
	private Graphics2D g2D;
	private ArrayList<String> debugStrings;
	private HUDGraph hud;
	private Viewport viewport;
	private GraphTransform transform;
	private PanelSystem panelSystem;
	private Camera camera;
	private RendererSettings rendererSettings;
	
	public Graph(Graphics gEntered,
			Camera camera,
			RendererSettings rendererSettings) {
		this.g2D = (Graphics2D) gEntered;
		this.viewport = camera.getViewport();
		this.transform = rendererSettings.getGraphTransform();
		this.camera = camera;
		this.rendererSettings = rendererSettings;
		this.transform.setGTC(new GraphTransformContext(
				viewport.getDrawXOffset(),
				viewport.getDrawYOffset(),
				(int) (camera.getX() * camera.getZoom()),
				(int) -(camera.getY() * camera.getZoom()),
				viewport.getWidth(),
				viewport.getHeight(),
				camera.getZoom()));
		debugStrings = new ArrayList<>();
		
		g2D.setColor(rendererSettings.getBackgroundColor());
		g2D.setStroke(new BasicStroke((float) camera.getZoom() / 10));
	}
	
	public Graph(DrawBasePanel drawBasePanel,
				Graphics gEntered,
				Camera camera,
				RendererSettings rendererSettings,
				GraphTransform transform,
				PanelSystem panelSystem) {
		this.g2D = (Graphics2D) gEntered;
		this.viewport = camera.getViewport();
		this.transform = transform;
		this.camera = camera;
		this.rendererSettings = rendererSettings;
		this.panelSystem = panelSystem;
		this.transform.setGTC(new GraphTransformContext(
				viewport.getDrawXOffset(),
				viewport.getDrawYOffset(),
				(int) (camera.getX() * camera.getZoom()),
				(int) -(camera.getY() * camera.getZoom()),
				viewport.getWidth(),
				viewport.getHeight(),
				camera.getZoom()));
		this.hud = new HUDGraph(g2D, drawBasePanel.getWidth(), drawBasePanel.getHeight());
		g2D.setColor(rendererSettings.getBackgroundColor());
		g2D.fillRect(viewport.getDrawXOffset(), viewport.getDrawYOffset(), viewport.getWidth(), viewport.getHeight());
		g2D.setStroke(new BasicStroke((float) camera.getZoom() / 10));
		
		if (rendererSettings.isRenderingCoordinateSystem()) {
			drawCoordinateSystem();
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
		hud.call();
		panelSystem.draw(hud);
		hud.call();
		
		g2D.setColor(Color.BLUE);
		g2D.setFont(g2D.getFont().deriveFont(DEBUG_FONT_SIZE));
		for (int i = 0; i < debugStrings.size(); i++) {
			g2D.drawString(debugStrings.get(i), 1, i * (DEBUG_FONT_SIZE + 2) + DEBUG_FONT_SIZE);
		}
	}
	
	public void drawCoordinateSystem() {
		drawCoordinateSystem(0, 0);
	}

	public void drawCoordinateSystem(double x, double y) {
		drawLine(x, x + 10, y, y - 10, Color.BLUE);
		drawLine(x - 10, x, y + 10, y, Color.RED);
		for (int i = -10; i <= 10; i++) {
			if (i == 0) continue;
			drawLine(x + i, y + 0.1, x + i, y - 0.1, Color.RED);
		}
		for (int i = -10; i <= 10; i++) {
			if (i == 0) continue;
			drawLine(x - 0.1, y + i, x + 0.1, y + i, Color.BLUE);
		}
	}
	
	public void draw(Shape... shapes) {
		for (int i = 0; i < shapes.length; i++) {
			shapes[i].render(this);
		}
	}
	
	public void drawString(String s, double x, double y) {
		drawString(s, x, y, Color.BLACK);
	}

	public void drawString(String s, CoordProvider coord) {
		drawString(s, coord.getX(), coord.getY(), Color.BLACK);
	}
	
	public void drawString(String s, double x, double y, Color c) {
		g2D.setColor(c);
		g2D.drawString(s, convSX(x), convSY(y));
	}

	public void drawString(String s, CoordProvider coord, Color c) {
		drawString(s, coord.getX(), coord.getY(), c);
	}
	
	public void drawString(String s, double x, double y, Color c, Font font) {
		Font previousFont = g2D.getFont();
		g2D.setFont(font);
		g2D.setColor(c);
		g2D.drawString(s, convSX(x), convSY(y));
		g2D.setFont(previousFont);
	}
	
	public void drawString(String s, CoordProvider coord, Color c, Font font) {
		drawString(s, coord.getX(), coord.getY(), c, font);
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

	public void drawString(String s, CoordProvider coord, Color c, Font font, TextAlignment textAlignment) {
		drawString(s, coord.getX(), coord.getY(), c, font, textAlignment);
	}
	
	/**
	 * Calls {@link Graph#drawPoint(double, double, Color)} with the color black
	 */
	public void drawPoint(double x, double y) {
		drawPoint(x, y, Color.BLACK);
	}

	public void drawPoint(CoordProvider coord) {
		drawPoint(coord.getX(), coord.getY());
	}
	
	/**
	 * Similar to {@link Graph#drawCircle(double, double, Color, double)} but will always use a radius of 0.2
	 */
	public void drawPoint(double x, double y, Color c) {
		g2D.setColor(c);
		g2D.fillArc(convSX(x) - POINT_RADIUS, convSY(y) + POINT_RADIUS, POINT_RADIUS * 2, POINT_RADIUS * 2, 0, 360);
	}
	
	public void drawPoint(CoordProvider coord, Color c) {
		drawPoint(coord.getX(), coord.getY(), c);
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
	
	public void drawCircle(CoordProvider coord, Color c, double radius) {
		drawCircle(coord.getX(), coord.getY(), c, radius);
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
	
	public void fillCircle(CoordProvider coord, Color c, double radius) {
		fillCircle(coord.getX(), coord.getY(), c, radius);
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

	public void drawLine(CoordProvider coord1, CoordProvider coord2) {
		drawLine(coord1.getX(), coord1.getY(), coord2.getX(), coord2.getY(), Color.BLACK);
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
	
	public void drawLine(CoordProvider coord1, CoordProvider coord2, Color c) {
		drawLine(coord1.getX(), coord1.getY(), coord2.getX(), coord2.getY(), c);
	}

	/**
	 * Draws a line between the points (x0, y0) and (x1, y1) with the given color and the stroke width in application line width.
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @param c Color of the line
	 * @param strokeWidth Width of line in application line width.
	 */
	public void drawLine(double x0, double y0, double x1, double y1, Color c, double strokeWidth) {
		Stroke prevStroke = g2D.getStroke();
		g2D.setStroke(new BasicStroke(convSLW(strokeWidth)));
		g2D.setColor(c);
		g2D.drawLine(convSX(x0), convSY(y0), convSX(x1), convSY(y1));
		g2D.setStroke(prevStroke);
	}

	public void drawLine(CoordProvider coord1, CoordProvider coord2, Color c, double strokeWidth) {
		drawLine(coord1.getX(), coord1.getY(), coord2.getX(), coord2.getY(), c, strokeWidth);
	}

	public void drawRect(double x, double y, double width, double height) {
		drawRect(x, y, width, height, Color.BLACK);
	}

	public void drawRect(CoordProvider coord, double width, double height) {
		drawRect(coord.getX(), coord.getY(), width, height);
	}
	
	public void drawRect(double x, double y, double width, double height, Color c) {
		g2D.setColor(c);
		g2D.drawRect(convSX(x), convSY(y + height), convSW(width), convSH(height));
	}

	public void drawRect(CoordProvider coord, double width, double height, Color c) {
		drawRect(coord.getX(), coord.getY(), width, height, c);
	}
	
	public void fillRect(double x, double y, double width, double height) {
		fillRect(x, y, width, height, Color.BLACK);
	}

	public void fillRect(CoordProvider coord, double width, double height) {
		fillRect(coord.getX(), coord.getY(), width, height);
	}
	
	/**
	 * @deprecated Depracted because spelling mistake; Use {@link Graph#fillRect(CoordProvider, double, double)} instead
	 */
	@Deprecated
	public void filllRect(CoordProvider coord, double width, double height) {
		drawRect(coord.getX(), coord.getY(), width, height);
	}
	
	public void fillRect(double x, double y, double width, double height, Color c) {
		g2D.setColor(c);
		g2D.fillRect(convSX(x), convSY(y + height), convSW(width), convSH(height));
	}

	public void fillRect(CoordProvider coord, double width, double height, Color c) {
		fillRect(coord.getX(), coord.getY(), width, height, c);
	}

	public void drawRotatedRect(double x, double y, double width, double height, double degrees, Color c) {
		if (degrees == 0) {
			g2D.setColor(c);
			g2D.drawRect(convSX(x), convSY(y + height), convSW(width), convSH(height));
		} else {
			double drawnWidth = convSW(width);
			double drawnHeight = convSH(height);
			Graphics2D area = (Graphics2D) g2D.create(0, 0, viewport.getWidth(), viewport.getHeight());
			area.translate(0, -drawnHeight);
			area.rotate(Math.toRadians(degrees), convSX(x) + drawnWidth * 0.5, convSY(y) + drawnHeight * 0.5);
			area.setColor(c);
			area.drawRect(convSX(x), convSY(y), (int) drawnWidth, (int) drawnHeight);
			area.dispose();
		}
	}
	
	public void drawRotatedRect(CoordProvider coord, double width, double height, double degrees, Color c) {
		drawRotatedRect(coord.getX(), coord.getY(), width, height, degrees, c);
	}
	
	public void fillRotatedRect(double x, double y, double width, double height, double degrees, Color c) {
		if (degrees == 0) {
			g2D.setColor(c);
			g2D.fillRect(convSX(x), convSY(y + height), convSW(width), convSH(height));
		} else {
			double drawnWidth = convSW(width);
			double drawnHeight = convSH(height);
			Graphics2D area = (Graphics2D) g2D.create(0, 0, viewport.getWidth(), viewport.getHeight());
			area.translate(0, -drawnHeight);
			area.rotate(Math.toRadians(degrees), convSX(x) + drawnWidth * 0.5, convSY(y) + drawnHeight * 0.5);
			area.setColor(c);
			area.fillRect(convSX(x), convSY(y), (int) drawnWidth, (int) drawnHeight);
			area.dispose();
		}
	}

	public void fillRotatedRect(CoordProvider coord, double width, double height, double degrees, Color c) {
		fillRotatedRect(coord.getX(), coord.getY(), width, height, degrees, c);
	}

	public void drawRotatedCenteredRect(double x, double y, double width, double height, double degrees, Color c) {
		double halfWidth = width * 0.5;
		double halfHeight = height * 0.5;
		if (degrees == 0) {
			g2D.setColor(c);
			g2D.drawRect(convSX(x - halfWidth), convSY(y + halfHeight), convSW(width), convSH(height));
		} else {
			double drawnWidth = convSW(width);
			double drawnHeight = convSH(height);
			Graphics2D area = (Graphics2D) g2D.create(0, 0, viewport.getWidth(), viewport.getHeight());
			area.translate(0, -drawnHeight);
			area.rotate(Math.toRadians(degrees), convSX(x - halfWidth) + drawnWidth * 0.5, convSY(y - halfHeight) + drawnHeight * 0.5);
			area.setColor(c);
			area.drawRect(convSX(x - halfWidth), convSY(y - halfHeight), (int) drawnWidth, (int) drawnHeight);
			area.dispose();
		}
	}
	
	public void drawRotatedCenteredRect(CoordProvider coord, double width, double height, double degrees, Color c) {
		drawRotatedCenteredRect(coord.getX(), coord.getY(), width, height, degrees, c);
	}
	
	public void fillRotatedCenteredRect(double x, double y, double width, double height, double degrees, Color c) {
		double halfWidth = width * 0.5;
		double halfHeight = height * 0.5;
		if (degrees == 0) {
			g2D.setColor(c);
			g2D.fillRect(convSX(x - halfWidth), convSY(y + halfHeight), convSW(width), convSH(height));
		} else {
			double drawnWidth = convSW(width);
			double drawnHeight = convSH(height);
			Graphics2D area = (Graphics2D) g2D.create(0, 0, viewport.getWidth(), viewport.getHeight());
			area.translate(0, -drawnHeight);
			area.rotate(Math.toRadians(degrees), convSX(x - halfWidth) + drawnWidth * 0.5, convSY(y - halfHeight) + drawnHeight * 0.5);
			area.setColor(c);
			area.fillRect(convSX(x - halfWidth), convSY(y - halfHeight), (int) drawnWidth, (int) drawnHeight);
			area.dispose();
		}
	}
	
	public void fillRotatedCenteredRect(CoordProvider coord, double width, double height, double degrees, Color c) {
		fillRotatedCenteredRect(coord.getX(), coord.getY(), width, height, degrees, c);
	}
	
	public void drawImage(Image image, double x, double y, double scale) {
		int imgWidth = (int) convSW(image.getWidth() / scale);
		int imgHeight = (int) convSH(image.getHeight() / scale);
		g2D.drawImage(image.getBufferedImage(), convSX(x) - imgWidth / 2, convSY(y) - imgHeight / 2, imgWidth, imgHeight, null);
	}
	
	public void drawImage(Image image, CoordProvider coord, double scale) {
		drawImage(image, coord.getX(), coord.getY(), scale);
	}
	
	public void drawImage(java.awt.Image image, double x, double y, double scale) {
		int imgWidth = (int) convSW(image.getWidth(null) / scale);
		int imgHeight = (int) convSH(image.getHeight(null) / scale);
		g2D.drawImage(image, convSX(x) - imgWidth / 2, convSY(y) - imgHeight / 2, imgWidth, imgHeight, null);
	}

	public void drawImage(java.awt.Image image, CoordProvider coord, double scale) {
		drawImage(image, coord.getX(), coord.getY(), scale);
	}
	
	public void drawRotatedImage(Image image, double x, double y, double scale, double degrees) {
		double imgWidth = convSW(image.getWidth() / scale);
		double imgHeight = convSH(image.getHeight() / scale);
		int areaSize = (int) (imgWidth > imgHeight ? imgWidth * 2 : imgHeight * 2);
		Graphics2D area = (Graphics2D) g2D.create(convSX(x) - (int) imgWidth, convSY(y) - (int) imgHeight, (int) areaSize, (int) areaSize);
		double angle = convSA(degrees);
		area.rotate(angle, imgWidth, imgHeight);
		area.drawImage(image.getBufferedImage(), (int) (imgWidth / 2), (int) (imgHeight / 2), (int) imgWidth, (int) imgHeight, null);
		area.dispose();
	}

	public void drawRotatedImage(Image image, CoordProvider coord, double scale) {
		drawImage(image, coord.getX(), coord.getY(), scale);
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

	public void drawRotatedImage(java.awt.Image image, CoordProvider coord, double scale) {
		drawImage(image, coord.getX(), coord.getY(), scale);
	}
	
	/**
	 * Adds a string to the current frame which will be drawn in blue in the top left corner.
	 * @param s
	 */
	public void addDebugString(String s) {
		this.debugStrings.add(s);
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
    
    public float convSLW(double lineWidth) {
    	return transform.convSLW(lineWidth);
    }
    
    public HUDGraph getHUD() {
    	return hud;
    }
    
    public Camera getCamera() {
		return camera;
	}
    
    public RendererSettings getRendererSettings() {
		return rendererSettings;
	}
    
    public Viewport getViewport() {
    	return viewport;
    }
    
    public Graphics2D getAWTGraphics() {
    	return g2D;
    }
}
