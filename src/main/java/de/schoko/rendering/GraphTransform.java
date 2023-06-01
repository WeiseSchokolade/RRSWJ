package de.schoko.rendering;

/**
 * Specifies how to convert the inputted x/y coordinate to a x/y coordinate for rendering.
 * Use gtc() or the gtc attribute to access information such as width, height etc.
 */
public abstract class GraphTransform {
	protected GraphTransformContext gtc;
	
	/**
	 * Converts application coordinate to screen coordinate
	 * @param y The application coordinate
	 * @return y coordinate on screen
	 */
	public abstract int convSX(double x);
	
	/**
	 * Converts application coordinate to screen coordinate
	 * @param y The application coordinate
	 * @return y coordinate on screen
	 */
	public abstract int convSY(double y);
	
	/**
	 * Converts screen coordinate to application coordinate
	 * @param x Coordinate on screen
	 * @return x application coordinate
	 */
	public abstract double convBackFromSX(double x);
	
	/**
	 * Converts screen coordinate to application coordinate
	 * @param y Coordinate on screen
	 * @return y application coordinate
	 */
	public abstract double convBackFromSY(double y);
	
	/**
	 * Converts application width to screen width
	 * @param w Application Width
	 * @return Screen Width
	 */
    public abstract int convSW(double w);

	/**
	 * Converts screen width to application width
	 * @param w Screen Width
	 * @return Application Width
	 */
    public abstract double convBackFromSW(double w);

	/**
	 * Converts application height to screen height
	 * @param w Application Height
	 * @return Screen Height
	 */
    public abstract int convSH(double h);

	/**
	 * Converts screen height to application height
	 * @param w Screen Height
	 * @return Application Height
	 */
    public abstract double convBackFromSH(double h);
    
    /**
     * Converts application degrees to screen radians
     * @param degrees
     * @return radians
     */
    public abstract double convSA(double degrees);
    
    /**
     * Converts application line width to screen line width
     * @param lineWidth
     * @return
     */
    public abstract float convSLW(double lineWidth);
    
    protected void setGTC(GraphTransformContext gtc) {
    	this.gtc = gtc;
    }
    
    /**
     * Returns the current Graph Transform Context.
     */
    public GraphTransformContext gtc() {
    	return gtc;
    }
}
