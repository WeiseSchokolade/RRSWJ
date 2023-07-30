package de.schoko.rendering;

import java.awt.Color;

public class RendererSettings {
	private Window window;
	private boolean autoCam;
	private Color backgroundColor;
	private boolean crashOnException;
	private boolean displayStartedNotification;
	private long fpsCap = 20;
	private int ups;
	private GraphTransform graphTransform;
	private boolean maximizedByDefault;
	private boolean renderCoordinateSystem;
	private Image windowIcon;
	private boolean windowResizable;
	private int windowWidth = -1, windowHeight = -1;
	
	protected RendererSettings(Window window) {
		this.window = window;
		autoCam = true;
		backgroundColor = Color.LIGHT_GRAY;
		crashOnException = true;
		displayStartedNotification = true;
		graphTransform = new GraphMathTransform();
		fpsCap = 1;
		renderCoordinateSystem = true;
		windowResizable = true;
	}
	
	/**
	 * Applies window settings like windowIcon and windowResizable
	 * if they were set before the window opened.
	 */
	protected void windowOpened() {
		if (windowIcon != null) {
			window.getSwingWindow().setIconImage(windowIcon.getAWTImage());
		}
		window.getSwingWindow().setResizable(windowResizable);
		if (windowWidth != -1) {
			window.getSwingWindow().setSize(windowWidth, windowHeight);
		}
	}
	
	public boolean isRenderingCoordinateSystem() {
		return renderCoordinateSystem;
	}
	
	/**
	 * @param renderCoordinateSystem Whether a coordinate system for math is rendered
	 */
	public void setRenderCoordinateSystem(boolean renderCoordinateSystem) {
		this.renderCoordinateSystem = renderCoordinateSystem;
	}

	public boolean isAutoCam() {
		return autoCam;
	}
	
	/**
	 * @param autoCam Whether the user can move the camera by default
	 */
	public void setAutoCam(boolean autoCam) {
		this.autoCam = autoCam;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * Sets the background of the window to a {@link java.awt.Color}
	 * @param backgroundColor The color
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	/**
	 * Sets the background of the window to a color in rgb color space.
	 * @param r Red Color Amount (Value between 0 and 255)
	 * @param g Green Color Amount (Value between 0 and 255)
	 * @param b Blue Color Amount (Value between 0 and 255)
	 */
	public void setBackgroundColor(int r, int g, int b) {
		backgroundColor = Graph.getColor(r, g, b);
	}
	
	public boolean isCrashingOnException() {
		return crashOnException;
	}
	
	/**
	 * Sets whether the program should crash if an exception occures
	 * @param crashOnException
	 */
	public void setCrashOnException(boolean crashOnException) {
		this.crashOnException = crashOnException;
	}
	
	public boolean isDisplayingStartedNotification() {
		return displayStartedNotification;
	}
	
	/**
	 * Displays a Notification "Started" after starting when true. Defaults to true.
	 * @param displayStartedNotification Whether to display the "Started" Notification.
	 */
	public void setDisplayStartedNotification(boolean displayStartedNotification) {
		this.displayStartedNotification = displayStartedNotification;
	}
	
	public GraphTransform getGraphTransform() {
		return graphTransform;
	}
	
	/**
	 * Changes how to transform application x/y coordinates to screen x/y coordinates
	 * @param graphTransform
	 */
	public void setGraphTransform(GraphTransform graphTransform) {
		this.graphTransform = graphTransform;
		if (window.isOpen()) {
			window.getPanel().setGraphTransform(graphTransform);
		}
	}
	
	public long getFPSCap() {
		return fpsCap;
	}
	
	/**
	 * Sets how long RRSWJ should wait before drawing the next frame in milliseconds.
	 * Only applies to simple renderers.
	 * Defaults to 1 millisecond.
	 * @param fpsCap
	 */
	public void setFPSCap(long fpsCap) {
		this.fpsCap = fpsCap;
	}

	public int getUPS() {
		return ups;
	}
	/**
	 * Sets how many updates there should be in a second.
	 * Only applies to split renderers
	 * Defaults to 20 (= 50 milliseconds per update).
	 * Can only be used before Window is opened.
	 * @return
	 */
	public void setUPS(int ups) {
		this.ups = ups;
	}
	
	public boolean isMaximizedByDefault() {
		return maximizedByDefault;
	}
	
	/**
	 * Maximizes the window after it opens. Defaults to false
	 * @param maximizedByDefault Whether to maximize the window.
	 */
	public void setMaximizedByDefault(boolean maximizedByDefault) {
		this.maximizedByDefault = maximizedByDefault;
	}
	
	/**
	 * Sets the window's icon
	 * @param image The new icon
	 */
	public void setWindowIcon(Image image) {
		if (window.isOpen()) {
			window.getSwingWindow().setIconImage(image.getAWTImage());
		} else {
			this.windowIcon = image;
		}
	}
	
	public boolean isWindowResizable() {
		return windowResizable;
	}
	
	/**
	 * Sets whether the window can be resized;
	 * @param windowResizable Whether the window can be resized;
	 */
	public void setWindowResizable(boolean windowResizable) {
		this.windowResizable = windowResizable;
		if (window.isOpen()) {
			window.getSwingWindow().setResizable(windowResizable);
		}
	}
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	
	/**
	 * Sets the size of the window
	 * @param windowWidth The window's width
	 * @param windowHeight The window's height
	 */
	public void setWindowSize(int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		if (window.isOpen()) {
			window.getSwingWindow().setSize(windowWidth, windowHeight);
		}
	}
}
