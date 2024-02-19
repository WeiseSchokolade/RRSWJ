package de.schoko.rendering.panels;

import de.schoko.rendering.HUDGraph;

public abstract class Panel {
	private PanelSystem system;
	private boolean removed;
	
	/**
	 * Optionally overriden method for loading. When 
	 * this method is called, the panel system can be
	 * accessed using {@link Panel#getSystem()} which
	 * doesn't work in the constructor.
	 */
	public void load() {
		
	}
	public abstract void draw(HUDGraph hud);

	public abstract void update();
	
	public void remove() {
		this.removed = true;
	}
	
	public PanelSystem getSystem() {
		return system;
	}
	
	protected final void setSystem(PanelSystem system) {
		this.system = system;
	}
	
	public boolean isRemoved() {
		return removed;
	}
}
