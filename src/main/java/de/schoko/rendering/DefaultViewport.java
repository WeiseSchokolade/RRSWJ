package de.schoko.rendering;

public class DefaultViewport implements Viewport {
	private DrawBasePanel drawBasePanel;
	
	public DefaultViewport(DrawBasePanel drawBasePanel) {
		this.drawBasePanel = drawBasePanel;
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
		return drawBasePanel.getWidth();
	}

	@Override
	public int getHeight() {
		return drawBasePanel.getHeight();
	}
}
