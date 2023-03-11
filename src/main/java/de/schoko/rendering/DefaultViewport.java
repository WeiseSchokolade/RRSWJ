package de.schoko.rendering;

class DefaultViewport implements Viewport {
	private Panel panel;
	
	public DefaultViewport(Panel panel) {
		this.panel = panel;
	}

	@Override
	public int getDrawXOffset() {
		return 0;
	}

	@Override
	public int getDrawYOffset() {
		return 0;
	}
	
	@Override
	public int getWidth() {
		return panel.getWidth();
	}

	@Override
	public int getHeight() {
		return panel.getHeight();
	}
}
