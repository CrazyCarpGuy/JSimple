package jsimple.core;

import java.awt.image.BufferedImage;


import java.util.Arrays;
import javax.swing.JFrame;
import jsimple.render.Triangle;
import java.awt.Color;
import java.awt.Graphics;

/* This class manages the display window.
 * Written by Ed Araujo on 2/7/22.
 * Reviewed by Ryan Munn on 2/8/22.
 */

public class OutputWindow extends JFrame {

	public final int WIDTH = 801, HEIGHT = 801;
	private BufferedImage screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
	private float[][] depthBuffer = new float[WIDTH][HEIGHT];
	
    public OutputWindow(){
    	
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addKeyListener(new Input());
		this.addMouseListener(new Input());

    }
    
    /* Set the RGB color value of the specified pixel.
     * Coordinates are expressed as traditional Cartesian coordinates, with an origin at the center of the frame
	 * and positive x and y values representing right and up, respectively.
	 */
    public void setRGB(int x, int y, int RGB) {
    	
    	/* Cartesian coordinates must be converted to screen coordinates,
    	 * since Java graphics libraries place the origin at the top left corner of the screen
    	 * and treat positive y values as down from the origin.
    	 */
    	int screenX = (WIDTH - 1) / 2 + (int)x, screenY = (HEIGHT - 1) / 2 - (int)y;
    	//Only update the pixel if the converted are within the bounds of the window.
    	if (screenX >= 0 && screenX < WIDTH && screenY >= 0 && screenY < HEIGHT) {
    		screen.setRGB(screenX, screenY, RGB);
    	}
    	
    }
    
    /* Return the RGB color value of the specified pixel. 
     * Coordinates are expressed as traditional Cartesian coordinates, with an origin at the center of the frame
	 * and positive x and y values representing right and up, respectively.
	 */
    public int getRGB(int x, int y) {
    	
    	/* Cartesian coordinates must be converted to screen coordinates,
    	 * since Java graphics libraries place the origin at the top left corner of the screen
    	 * and treat positive y values as down from the origin.
    	 */
    	int screenX = (WIDTH - 1) / 2 + (int)x, screenY = (HEIGHT - 1) / 2 - (int)y;
    	if (screenX >= 0 && screenX < WIDTH && screenY >= 0 && screenY < HEIGHT) {
    		return screen.getRGB(screenX, screenY);
    	} else {
    		return 0;
    	}
    	
    }
    
    /* Set the value of the depth buffer at the specified pixel, representing the distance to the closest jsimple.object at a given pixel.
     * Coordinates are expressed as traditional Cartesian coordinates, with an origin at the center of the frame
	 * and positive x and y values representing right and up, respectively.
	 */
    public void setDepth(int x, int y, float depth) {
    	
    	/* Cartesian coordinates must be converted to screen coordinates,
    	 * since Java graphics libraries place the origin at the top left corner of the screen
    	 * and treat positive y values as down from the origin.
    	 */
    	int screenX = (WIDTH - 1) / 2 + (int)x, screenY = (HEIGHT - 1) / 2 - (int)y;
    	if (screenX >= 0 && screenX < WIDTH && screenY >= 0 && screenY < HEIGHT) {
    		depthBuffer[screenX][screenY] = depth;
    	}
    	
    }
    
    /* Return the value of the depth buffer at the specified pixel, representing the distance to the closest jsimple.object at a given pixel.
     * Coordinates are expressed as traditional Cartesian coordinates, with an origin at the center of the frame
	 * and positive x and y values representing right and up, respectively.
	 */
    public float getDepth(int x, int y) {
    	
    	/* Cartesian coordinates must be converted to screen coordinates,
    	 * since Java graphics libraries place the origin at the top left corner of the screen
    	 * and treat positive y values as down from the origin.
    	 */
    	int screenX = (WIDTH - 1) / 2 + (int)x, screenY = (HEIGHT - 1) / 2 - (int)y;
    	if (screenX >= 0 && screenX < WIDTH && screenY >= 0 && screenY < HEIGHT) {
    		return depthBuffer[screenX][screenY];
    	} else {
    		return Float.MAX_VALUE;
    	}
    	
    }
    
    //Redraw the screen and clear it for next time
    public void paint(Graphics g) {
    		
    	g.drawImage(screen, 0, 0, null);
    	g = screen.getGraphics();
    	
    	//Reset the screen to a blank black screen for the next frame
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, WIDTH, HEIGHT);
    	
    	//Reset the depth buffer
    	for (int r = 0; r < HEIGHT; r++) {
    		Arrays.fill(depthBuffer[r], Float.MAX_VALUE);
    	}

		JSimple.renderComplete = true;

    }

    /*
	Iterates through all pixels and modifies their brightness by calling scalarRGB()
	*/
	public void modifyBrightness(double b) {
		for(int x = 0; x < screen.getWidth(); x++) {
			for(int y = 0; y < screen.getHeight(); y++) {
				screen.setRGB(x, y, scalarRGB(screen.getRGB(x, y), b));
			}
		}
		//TODO: Fix with new gameObject system
		//for(Triangle t : Triangle.triangles) {
		//	t.scalarColor(b);
		//}
	}


	/*
	Iterates through all pixels and inverts their brightness by calling invertRGB()
	*/
	public void invert() {
		for(int x  = 0; x < screen.getWidth(); x++) {
			for(int y = 0; y < screen.getHeight(); y++) {
				screen.setRGB(x, y, invertRGB(screen.getRGB(x, y)));
			}
		}
		//TODO: Fix with new gameObject system
		//for(Triangle t : Triangle.triangles) {
		//	t.invertColor();
		//}
	}

	/*
	Converts RGB values to a color, multiplies components by a scalar value, and returns color as an RGB value
	*/
	private int scalarRGB(int rgb, double scalar) {
		Color c =  new Color(rgb, false);
		int r = Math.min((int) (scalar * c.getRed()), 255);
		int g = Math.min((int) (scalar * c.getGreen()), 255);
		int b = Math.min((int) (scalar * c.getBlue()), 255);
		c = new Color(r, g, b);
		return c.getRGB();
	}

	/*
	Converts RGB values to a color, inverts individual components of color, and returns color as an RGB value
	*/
	private int invertRGB(int rgb) {
		Color c =  new Color(rgb, true);
		int r = 255 - c.getRed();
		int g = 255 - c.getGreen();
		int b = 255 - c.getBlue();
		int a = c.getAlpha();
		c = new Color(r, g, b, a);
		return c.getRGB();
	}
	
}