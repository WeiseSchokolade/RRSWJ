package de.schoko.rendering;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener, CoordProvider {
	public static final int LEFT_BUTTON = 0;
	public static final int MIDDLE_BUTTON = 1;
	public static final int RIGHT_BUTTON = 2;
	
	private int x, y;
	private double mouseWheelClicks, recentMouseWheelClicks;
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
		recentMouseWheelClicks = mouseWheelClicks;
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
	
	/**
	 * @return The change of rotation of the mouse wheel since the last frame
	 * @see Mouse#getMouseWheelClicks()
	 */
	public double getDeltaMouseWheelClicks() {
		return mouseWheelClicks - recentMouseWheelClicks;
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
		int id = 3;
		if (SwingUtilities.isLeftMouseButton(e)) {
			id = 0;
		} else if (SwingUtilities.isMiddleMouseButton(e)) {
			id = 1;
		} else if (SwingUtilities.isRightMouseButton(e)) {
			id = 2;
		}
		pressed[id] = true;
		recentlyPressed[id] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int id = 3;
		if (SwingUtilities.isLeftMouseButton(e)) {
			id = 0;
		} else if (SwingUtilities.isMiddleMouseButton(e)) {
			id = 1;
		} else if (SwingUtilities.isRightMouseButton(e)) {
			id = 2;
		}
		pressed[id] = false;
		recentlyReleased[id] = true;
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
