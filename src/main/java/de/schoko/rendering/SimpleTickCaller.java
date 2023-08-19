package de.schoko.rendering;

public class SimpleTickCaller implements TickCaller {
	private DrawBasePanel panel;
	private Renderer renderer;
	private boolean isRunning;
	private RendererSettings rendererSettings;
	
	public SimpleTickCaller(DrawBasePanel drawBasePanel, Renderer renderer, RendererSettings rendererSettings) {
		this.panel = drawBasePanel;
		this.renderer = renderer;
		this.rendererSettings = rendererSettings;
	}
	
	@Override
	public void run() {
		isRunning = true;
		while (isRunning) {
			panel.repaint();
			
			if (rendererSettings.getFPSCap() > 0) {
	        	try {
	            	Thread.sleep(rendererSettings.getFPSCap());
	        	} catch (InterruptedException e) {
	        		e.printStackTrace();
	        		assert false : "Couldn't cap FPS";
	        	}
			}
		}
	}

	@Override
	public void renderCall(Graph g, double deltaTimeMS) {
		renderer.render(g, deltaTimeMS);
		renderer.getContext().getPanelSystem().update();
		renderer.getContext().getMouse().update();
		renderer.getContext().getKeyboard().update();
	}
}
