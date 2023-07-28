package de.schoko.rendering;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
	public static final int LEFT_BUTTON = MouseEvent.BUTTON1;
	public static final int RIGHT_BUTTON = MouseEvent.BUTTON2;
	public static final int MIDDLE_BUTTON = MouseEvent.BUTTON3;
	
	private int x, y;
	private double mouseWheelClicks;
	private boolean[] pressed = new boolean[MouseInfo.getNumberOfButtons() + 1];
	private boolean[] recentlyPressed = new boolean[pressed.length];
	private boolean[] recentlyReleased = new boolean[pressed.length];
	private boolean inPanel = false;
	private Context context;
	
	public Mouse(DrawBasePanel drawBasePanel) {
		drawBasePanel.addMouseListener(this);
		drawBasePanel.addMouseMotionListener(this);
		drawBasePanel.addMouseWheelListener(this);
	}
	
	/**
	 * Marks all recently pressed keys as not pressed
	 */
	protected void update() {
		for (int i = 0; i < recentlyPressed.length; i++) {
			recentlyPressed[i] = false;
			recentlyReleased[i] = false;
		}
	}
	
	/**
	 * @return The x position of the mouse in the coordinate system
	 */
	public double getX() {
		return context.getLastGraph().convBackFromSX(x);
	}

	/**
	 * @return The y position of the mouse in the coordinate system
	 */
	public double getY() {
		return context.getLastGraph().convBackFromSY(y);
	}
	
	/**
	 * @return The x position of the mouse in the actual frame
	 */
	public double getScreenX() {
		return x;
	}

	/**
	 * @return The y position of the mouse in the actual frame
	 */
	public double getScreenY() {
		return y;
	}
	
	/**
	 * @return The rotation of the mouse wheel.
	 * @see MouseWheelEvent#getUnitsToScroll()
	 */
	public double getMouseWheelClicks() {
		return mouseWheelClicks;
	}
	
	public boolean isPressed(int buttonNumber) {
		return pressed[buttonNumber];
	}

	/**
	 * Checks whether the given button was pressed since the last frame.
	 * @param keyCode The button to be checked
	 * @return Whether the button was recently pressed
	 */
	public boolean wasRecentlyPressed(int keyCode) {
		return recentlyPressed[keyCode];
	}
	
	/**
	 * Checks whether the given button was released since the last frame.
	 * @param keyCode The button to be checked
	 * @return Whether the button was recently released
	 */
	public boolean wasRecentlyReleased(int keyCode) {
		return recentlyReleased[keyCode];
	}
	
	public boolean isInPanel() {
		return inPanel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed[e.getButton()] = true;
		recentlyPressed[e.getButton()] = true;
		recentlyReleased[e.getButton()] = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed[e.getButton()] = false;
		recentlyPressed[e.getButton()] = false;
		recentlyReleased[e.getButton()] = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		inPanel = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		inPanel = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelClicks += e.getUnitsToScroll();
	}
}
