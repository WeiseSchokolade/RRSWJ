package de.schoko.rendering;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Camera implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
	private double x;
	private double y;
	private double mouseX;
	private double mouseY;
	private double zoom = 50;
	/*
	 * Whether the camera can be moved
	 */
	private boolean movable;
	/*
	 * Whether the camera should be movable after the camera path was stopped
	 */
	private boolean originallyMovable;
	/*
	 * Whether the camera is currently being moved
	 */
	private boolean movingCam;
	private CameraPath cameraPath;
	private Viewport viewport;
	
	/**
	 * Creates a camera with a default zoom of 50
	 * @param x Initial x
	 * @param y Initial y
	 */
	public Camera(double x, double y) {
		this.x = x;
		this.y = y;
		this.movable = false;
		this.originallyMovable = false;
	}
	
	/**
	 * @param x Initial x
	 * @param y Initial y
	 * @param zoom Initial zoom
	 */
	public Camera(double x, double y, double zoom) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
		this.movable = false;
		this.originallyMovable = false;
	}

	/**
	 * @param x Initial x
	 * @param y Initial y
	 * @param zoom Initial zoom
	 * @param viewport Viewport of the camera
	 */
	public Camera(double x, double y, double zoom, Viewport viewport) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
		this.viewport = viewport;
		this.movable = false;
		this.originallyMovable = false;
	}
	/**
	 * Adds movement functionality to camera using the panel
	 * @param x Initial x
	 * @param y Initial y
	 * @param zoom Initial zoom
	 * @param viewport Viewport of the camera
	 * @param panel Panel this camera is added to
	 */
	public Camera(double x, double y, double zoom, Viewport viewport, Panel panel) {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
		this.viewport = viewport;
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.addMouseWheelListener(this);
		panel.addKeyListener(this);
		this.movable = true;
		this.originallyMovable = true;
	}
	
	public void update(double deltaTimeMS) {
		if (cameraPath != null) {
			CameraPathPoint point = cameraPath.getPoint(this, deltaTimeMS);
			this.x = point.x;
			this.y = point.y;
			if (point.zoom > 0) {
				this.zoom = point.zoom;
			}
		}
	}
	
	public void setCameraPath(CameraPath cameraPath) {
		if (cameraPath == null) {
			this.movable = originallyMovable;
			this.cameraPath = null;
		} else {
			this.movable = false;
			this.cameraPath = cameraPath;
		}
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZoom() {
		return zoom;
	}
	
	public Viewport getViewport() {
		return viewport;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	
	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (movingCam && movable) {
            this.x -= (e.getX() - mouseX) / zoom;
            this.y -= -(e.getY() - mouseY) / zoom;
        }
        this.mouseX = e.getX();
        this.mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!movable) return;
		if (e.getButton() == MouseEvent.BUTTON3) {
            movingCam = true;
        }
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
            movingCam = false;
        }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (!movable) return;
		if (e.getWheelRotation() > 0) {
            zoom *= 0.95;
        } else {
            zoom /= 0.95;
        }
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!movable) return;
		if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            zoom /= 0.95;
        }
        if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            zoom *= 0.95;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
