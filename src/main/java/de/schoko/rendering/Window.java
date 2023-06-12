package de.schoko.rendering;

import de.schoko.rendering.debugchanger.ChangeApplier;
import de.schoko.rendering.panels.PanelSystem;

public class Window {
	private String title;
	private DrawBasePanel drawBasePanel;
	private Renderer renderer;
	private SwingWindow swingWindow;
	private RendererSettings rendererSettings;
	private Context context;
	private Image windowIcon;
	private boolean open;
	
	/**
	 * Creates a new invisible Window with the given titel and renderer object.
	 * To make the window visible, use {@link Window#open()}
	 * @param renderer
	 * @param title
	 */
	public Window(Renderer renderer, String title) {
		this.renderer = renderer;
		this.title = title;
		this.rendererSettings = new RendererSettings(this);
	}
	
	/**
	 * Calls {@link Renderer#onLoad(Context)} and makes the window visible.
	 */
	public void open() {
		drawBasePanel = new DrawBasePanel(renderer, rendererSettings);
		
		// Sets up the context
		this.context = new Context(
				this,
				this.rendererSettings,
				this.drawBasePanel.getCamera(),
				new Keyboard(),
				new Mouse(drawBasePanel),
				new ImagePool(),
				null);
		// Adds the panel system afterwards as it requires the context
		this.context.setPanelSystem(new PanelSystem(context, drawBasePanel));
		
		// Loads the context into the renderer
		this.drawBasePanel.setContext(context);
		this.renderer.setContext(context);
		this.renderer.onLoad(context);
		
		// Opens the window and starts the rendering process
		swingWindow = new SwingWindow(title, drawBasePanel);
		if (rendererSettings.isMaximizedByDefault()) {
			swingWindow.setExtendedState(swingWindow.getExtendedState() | SwingWindow.MAXIMIZED_BOTH);
		}
		if (windowIcon != null) {
			swingWindow.setIconImage(windowIcon.getAWTImage());
		}
		open = true;
		rendererSettings.windowOpened();
	}

	public void openEditPanel(String initialText, ChangeApplier ca) {
		swingWindow.openEditPanel(initialText, ca);
	}
	
	public void closeEditPanel() {
		swingWindow.closeEditPanel();
	}
	
	public RendererSettings getSettings() {
		return rendererSettings;
	}
	
	public DrawBasePanel getPanel() {
		return drawBasePanel;
	}
	
	public SwingWindow getSwingWindow() {
		return swingWindow;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	/**
	 * @deprecated
	 * This method is deprecated in favor of {@link RendererSettings#setWindowIcon(Image)}
	 * @param image
	 */
	@Deprecated
	public void setWindowIcon(Image image) {
		if (swingWindow != null) {
			swingWindow.setIconImage(image.getAWTImage());
		} else {
			windowIcon = image;
		}
	}
}
