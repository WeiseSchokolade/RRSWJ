package de.schoko.rendering;

public class SplitTickCaller implements TickCaller {
	private DrawBasePanel panel;
	private SplitRenderer splitRenderer;
	private boolean isRunning;
	private RendererSettings rendererSettings;

	protected SplitTickCaller(DrawBasePanel panel, SplitRenderer splitRenderer, RendererSettings rendererSettings) {
		this.panel = panel;
		this.splitRenderer = splitRenderer;
		this.rendererSettings = rendererSettings;
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		// in nano seconds
		double timePerUpdate = 1_000_000_000 / (double) rendererSettings.getUPS();
		// how many times it needs to update
		double delta = 0;

		isRunning = true;
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / timePerUpdate;
			lastTime = now;

			while (delta >= 1) {
				splitRenderer.update();
				splitRenderer.getContext().getPanelSystem().update();
				splitRenderer.getContext().getMouse().update();
				splitRenderer.getContext().getKeyboard().update();
				delta--;
			}
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
		splitRenderer.frame(g);
	}
}