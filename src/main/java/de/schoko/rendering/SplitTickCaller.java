package de.schoko.rendering;

public class SplitTickCaller implements TickCaller {
	private double ups;
	private DrawBasePanel panel;
	private SplitRenderer splitRenderer;
	private boolean isRunning;
	
	protected SplitTickCaller(DrawBasePanel panel, SplitRenderer splitRenderer, RendererSettings rendererSettings) {
		this.panel = panel;
		this.splitRenderer = splitRenderer;
		this.ups = rendererSettings.getUPS();
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		// in nano seconds
		double timePerUpdate = 1_000_000_000 / ups;
		// how many times it needs to update
		double delta = 0;
		
		isRunning = true;
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / timePerUpdate;
			lastTime = now;
			
			while (delta >= 1) {
				splitRenderer.update();
				delta--;
			}
			panel.repaint();
		}
	}

	@Override
	public void renderCall(Graph g, double deltaTimeMS) {
		splitRenderer.frame(g);
	}
}
