package de.schoko.rendering;

public abstract class SplitRenderer extends Renderer {
	private boolean displayedWarning;
	
	@Override
	public void render(Graph g, double deltaTimeMS) {
		if (!displayedWarning) {
			displayedWarning = true;
			System.out.println("Warning: SplitRenderer#render(Graph, double) is being used, this will be changed in a future update");
		}
		update();
		frame(g);
	}
	
	public abstract void update();
	public abstract void frame(Graph g);
}
