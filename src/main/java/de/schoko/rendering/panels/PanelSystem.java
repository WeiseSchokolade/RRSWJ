package de.schoko.rendering.panels;

import java.awt.Color;
import java.util.ArrayList;

import de.schoko.rendering.Context;
import de.schoko.rendering.DrawBasePanel;
import de.schoko.rendering.HUDGraph;

public class PanelSystem {
	private ArrayList<Panel> panels;
	private Context context;
	private PanelTheme theme;
	
	public PanelSystem(Context context, DrawBasePanel drawBasePanel) {
		panels = new ArrayList<>();
		this.context = context;
		theme = new PanelTheme(
				drawBasePanel.getFont(),
				Color.GRAY,
				Color.LIGHT_GRAY,
				Color.WHITE,
				Color.GRAY,
				Color.WHITE,
				Color.CYAN,
				5,
				5,
				2);
	}
	
	public void draw(HUDGraph hud) {
		for (int i = 0; i < panels.size(); i++) {
			panels.get(i).draw(hud);
		}
	}
	
	public void update() {
		for (int i = 0; i < panels.size(); i++) {
			Panel panel = panels.get(i);
			panel.update();
			if (panel.isRemoved()) {
				panels.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Adds the panel to the system, sets its system to this system and calls the load method
	 * @param panel The panel to add to the PanelSystem.
	 * @return The parsed panel
	 */
	public <T extends Panel> T add(T panel) {
		panel.setSystem(this);
		panels.add(panel);
		panel.load();
		return panel;
	}
	
	public void remove(Panel panel) {
		panels.remove(panel);
	}

	public void clear() {
		panels.clear();
	}

	public Context getContext() {
		return context;
	}
	
	public PanelTheme getTheme() {
		return theme;
	}
}
