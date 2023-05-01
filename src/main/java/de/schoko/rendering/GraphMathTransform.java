package de.schoko.rendering;

/**
 * Default transform. Transforms coordinates to cartesian coordinate system.
 */
public class GraphMathTransform extends GraphTransform {
	public int convSX(double x) {
		return (int) (x * gtc.zoom + gtc.width / 2) - gtc.camX + gtc.drawXOffset;
	}
	
	public int convSY(double y) {
		return (int) (y * -gtc.zoom + gtc.height / 2) - gtc.camY + gtc.drawYOffset;
	}
	
	public double convBackFromSX(double x) {
		return ((x - gtc.drawXOffset + gtc.camX) - gtc.width / 2) / gtc.zoom;
	}
	
	public double convBackFromSY(double y) {
		return ((y - gtc.drawYOffset + gtc.camY) - gtc.height / 2) / -gtc.zoom;
	}

    public int convSW(double w) {
        return (int) (gtc.zoom * w);
    }
    
    public double convBackFromSW(double w) {
    	return gtc.zoom * w;
    }
    
    public int convSH(double h) {
        return (int) (gtc.zoom * h);
    }
    
    public double convBackFromSH(double h) {
    	return gtc.zoom * h;
    }
    
    /**
     * Converts degrees to screen radians
     * @param degrees
     * @return radians
     */
    public double convSA(double degrees) {
    	return Math.toRadians((degrees - 90));
    }
    
}
