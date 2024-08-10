package de.schoko.rendering;

public interface CoordAcceptor {
	public void setX(double x);
	public void setY(double y);
	
	public default void set(CoordProvider coordProvider) {
		setX(coordProvider.getX());
		setY(coordProvider.getY());
	}
}
