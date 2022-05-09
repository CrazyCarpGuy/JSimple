package jsimple.core;

import java.util.HashMap;

import jsimple.object.GameObject;
import jsimple.render.*;

public class JSimple {

	public static final HashMap<String, GameObject> gameObjects = new HashMap<>();

    //core variables to track game state and window
    private static GState gameState;
    private static OutputWindow window;
    

    //reflects whether the screen is inverted
    private static boolean invert;

    //variables for brightness modification
    private static double brightness = 1.0;
    private static double brightBuffer = 1.0;

    //Update/render tick tracking
    private static int skipTime = 2000000, frameRateMax = 1000000000 / skipTime, totalTicks;
    private static final int maxSkip = 50;
    private static long nextTick = System.nanoTime();
    public static boolean renderComplete;
    
    //FPS calculator
    private static long lastTime, currentTime;
    public static int deltaFrame;
    public static float FPS, TPS, deltaTime; //TPS = ticks per second, amt of times the game state updates per second

    //Call JSimple.init() to initialize the engine
    public static void init() {
    	
        gameState = GState.LOAD;
        window = new OutputWindow();
        Camera.init();
    }
    
    //Call JSimple.start() to start the engine
    public static void start() {
    	
    	gameState = GState.RUNNING;
    	JSimple.update();
    	
    }
    
    //Add the GameObject to the list of game objects in the scene
    public static void addGameObject(GameObject gameObject) {
    	
    	gameObjects.put(gameObject.getName(), gameObject);
    	
    }

    public static void removeGameObject(GameObject o) {
        gameObjects.remove(o.getName());
    }

    //Getter and setter methods included for user convenience
    public static void switchState(GState s) { gameState = s; }
    public static GState getState() { return gameState; }
    public static double getBrightness() { return brightness; }
    public static void toggleResizeable() { window.setResizable(!window.isResizable()); }
    public static void setResizeable(boolean b) { window.setResizable(b); }
    public static void setSize(int w, int h) { window.setSize(w, h); }
    public static int getWidth() { return window.WIDTH; }
    public static int getHeight() { return window.HEIGHT; }
    public static double getX() { return window.getLocation().getX(); }
    public static double getY() { return window.getLocation().getY(); }
    public static void setMaxFrameRate(int f) {
        frameRateMax = f;
        skipTime = 1000000000 / frameRateMax;
    }

    //Update all triangles and repaint the screen
    public static void render() {
    	renderComplete = false;
    	for (GameObject gameObject : gameObjects.values()) {
    	
    		for (Triangle triangle : gameObject.model.getTriangles()) {
    			
    			triangle.render();
    			
    		}
    		
    	}
    	
        window.repaint();
    }

    //Update all game objects
    private static void update() {
        int loops;
        double interpolation;
        while(gameState == GState.RUNNING) {
            loops = 0;
            while (System.nanoTime() > nextTick && loops < maxSkip) {
                totalTicks++;
                currentTime = System.nanoTime();
                deltaTime = ((int)(currentTime - lastTime) * 1.0f)/1000000f;
                lastTime = currentTime;
                TPS = 1000000000f / deltaTime;
                callAllUpdates();
                nextTick += skipTime;
                loops++;
            }
            if(renderComplete) {
                interpolation = (System.nanoTime() + skipTime - nextTick) / (double) skipTime;
                deltaTime *= interpolation;
                callAllUpdates();
                JSimple.render();
                calculateFrameRate();
                totalTicks = 0;
            }
        }
    }

    private static void callAllUpdates() {
        Camera.update();
        for (GameObject go : gameObjects.values()) {
            go.update(deltaTime);
        }
    }

    /**
     * Sets the brightness of the screen to a percentage to the original. This function
     * uses a buffer value (determined by the current brightness) to ensure that
     * the default brightness is saved and retrievable. Additionally, this buffer ensures that all changes
     * to brightness are in relation to the original brightness.
     * @param b the new brightness, as a percentage of the default brightness
     */
    public static void setBrightness(double b) {
        brightness = b * brightBuffer;
        brightBuffer = 1.0 / brightness;
        window.modifyBrightness(brightness);
    }

    /**
     * Call to the window to invert all screen colors. The window then manually flips the RGB value of every visible pixel.
     */
    public static void invertColor() {
        window.invert();
        invert = true;
    }

    /**
     * Indicates whether the screen colors are inverted
     * @return a boolean determining whether the screen color is inverted
     */
    public static boolean isInverted() {
        return invert;
    }

    /**
     * Set the RGB color value of the specified pixel in the window.
     * Coordinates are expressed as traditional Cartesian coordinates, with an origin at the center of the frame
     * and positive x and y values representing right and up, respectively.
     * @param x The horizontal distance from the center of the screen to the pixel being operated on.
     * @param y The vertical distance from the center of the screen to the pixel being operated on.
     * @param RGB The desired RGB color value of the pixel, as an int
     */
    public static void setRGB(int x, int y, int RGB) {
        window.setRGB(x, y, RGB);
    }

    /**
     * Return the RGB color value of the specified pixel in the window.
     * Coordinates are expressed as traditional Cartesian coordinates, with an origin at the center of the frame
     * and positive x and y values representing right and up, respectively.
     * @param x The horizontal distance from the center of the screen to the pixel being operated on.
     * @param y The vertical distance from the center of the screen to the pixel being operated on.
     * @return the RGB color value of the pixel, as an int
     */
    public static int getRGB(int x, int y) {
    	return window.getRGB(x, y);	
    }

    /**
     *  Set the value of the window's depth buffer at the specified pixel.
     *  The depth buffer represents the distance to the closest JSimple object at a given pixel.
     *  Coordinates are expressed as traditional Cartesian coordinates, with an origin at the center of the frame
     *  and positive x and y values representing right and up, respectively.
     * @param x The horizontal distance from the center of the screen to the pixel being operated on.
     * @param y The vertical distance from the center of the screen to the pixel being operated on.
     * @param depth the desired value of the depth buffer at the specified pixel.
     */
    public static void setDepth(int x, int y, float depth) {
    	window.setDepth(x, y, depth);
    }

    /**
     * Calculates the value of the window's depth buffer at a given pixel.
     * The depth buffer represents the distance to the closest JSimple object at a given pixel.
     * Coordinates are expressed as traditional Cartesian coordinates, with an origin at the center of the frame
     * and positive x and y values representing right and up, respectively.
     * @param x The horizontal distance from the center of the screen to the pixel being operated on.
     * @param y The vertical distance from the center of the screen to the pixel being operated on.
     * @return the value of the window's depth buffer at the specified pixel.
     */
    public static float getDepth(int x, int y) { //calls the window to get the depth buffer value at a pixel
        return window.getDepth(x, y);
    }

    /**
     * Calculates the frame rate based on the time it takes the engine to render each frame. The time between each
     * frame is calculated in nanoseconds.
     */
    public static void calculateFrameRate() { //calculates the current frame rate in Frames Per Second (FPS)
        currentTime = System.nanoTime();
        deltaFrame = (int)(currentTime - lastTime);
        if (deltaFrame != 0) {
        	FPS = 1000000000.0f / deltaFrame;
        } else {
        	FPS = 1000000000;
        }
    }
}
