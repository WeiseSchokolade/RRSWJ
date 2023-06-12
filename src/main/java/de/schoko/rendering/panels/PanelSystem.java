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
				Color.LIGHT_GRAY,
				Color.WHITE,
				Color.CYAN,
				5,
				5,
				2);
	}
	
	public void draw(HUDGraph hud) {
		for (int i = 0; i < panels.size(); i++) {
			Panel panel = panels.get(i);
			panel.draw(hud);
		}
	}
	
	public void add(Panel panel) {
		panel.setSystem(this);
		panels.add(panel);
		panel.load();
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
