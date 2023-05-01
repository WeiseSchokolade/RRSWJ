package de.schoko.rendering;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Panel extends JPanel {
	private static final long serialVersionUID = -2898023849852442454L;
	private boolean instanciated = false;
    private double lastRun;

	private Camera camera;
	private Renderer renderer;
	private RendererSettings rendererSettings;
	private Context context;
	private GraphTransform graphTransform;
	
	private List<Notification> notifications;
    
	public Panel(Renderer renderer, RendererSettings rendererSettings) {
		this.setPreferredSize(new Dimension(500, 500));
		this.setFocusable(true);
		this.renderer = renderer;
		this.rendererSettings = rendererSettings;
		if (rendererSettings.isAutoCam()) {
			camera = new Camera(0, 0, 50, new DefaultViewport(this), this);
		} else {
			camera = new Camera(0, 0, 50, new DefaultViewport(this));
		}
		this.graphTransform = rendererSettings.getGraphTransform();
		this.notifications = new ArrayList<>();
		if (rendererSettings.isDisplayingStartedNotification()) {
			notifications.add(new Notification("Started", 5));
		}
		lastRun = System.currentTimeMillis();
	}
	
	@Override
	public void paint(Graphics g) {
		if (!instanciated) {
			this.requestFocusInWindow();
			instanciated = true;
		}
		
		double deltaTime = System.currentTimeMillis() - lastRun;
		lastRun = System.currentTimeMillis();
		
		for (int i = 0; i < notifications.size(); i++) {
			Notification n = notifications.get(i);
			n.setTimeLeft(n.getTimeLeft() - deltaTime);
		}
		notifications.removeIf((Notification n) -> {
			return n.getTimeLeft() < 0;
		});
		
		if (renderer != null) {
			Graph graph = new Graph(this, g, camera, rendererSettings, graphTransform);
			context.setLastGraph(graph);
			
			try {
				camera.update(deltaTime);
				renderer.render(graph, deltaTime);
			} catch (Exception e) {
				if (rendererSettings.isCrashingOnException()) {
					throw e;
				}
				e.printStackTrace();
			}
			graph.finalize();
			context.getMouse().update();
			context.getKeyboard().update();
		}
		if (rendererSettings.getFPSCap() > 0) {
        	try {
            	Thread.sleep(rendererSettings.getFPSCap());
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        		assert false : "Couldn't cap FPS";
        	}
		}
        this.repaint();
	}
	
	public void addNotification(Notification n) {
		this.notifications.add(n);
	}
	
	public List<Notification> getNotifications() {
		return this.notifications;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public void setGraphTransform(GraphTransform graphTransform) {
		this.graphTransform = graphTransform;
	}
}
