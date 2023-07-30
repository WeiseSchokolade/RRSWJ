package de.schoko.rendering;

public interface TickCaller extends Runnable {
	public void renderCall(Graph g, double deltaTimeMS);
}
