package jsimple.scripts;

import jsimple.object.GameObject;
import jsimple.object.Script;
import jsimple.utility.Vector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Oscillator class controls an object oscillating around a specific point in space along a line. This class provides functionality to control an obect rot
 * @author Benjamin Boardman
 * @since Version 1.0
 */
public class Oscillator implements Script {

    /**
     *A vector representing the central point of oscillation.
     */
    private Vector center;
    /**
     * A HashMap to store all Objects that use or have used this instance of the script, as well as their initial/maximum velocity.
     */
    private HashMap<GameObject, Float> maxVelocity;
    /**
     * The maximum distance an oscillating object should be from the center.
     */
    private float distance;

    /**
     * Initializes an Oscillator with a given center point and a maximum distance d from the center.
     * @param c the focal point of an Oscillator, represented as a vector.
     * @param d the maximum distance from the focal point that an Oscillating object may be.
     */
    public Oscillator(Vector c, float d) {
        center = c;
        distance = d;
        maxVelocity = new HashMap<>();
    }

    @Override
    /**
     *{@inheritDoc}
     * @param o the GameObject that the script is being applied to
     */
	public void executeInit(GameObject o) {
        maxVelocity.put(o, o.transform.getLinearVelocity().getMagnitude()/2f);
    }
	
    @Override
    /**
     *{@inheritDoc}
     * Oscillates an object around a certain point. This point is defined as the center field of this Oscillator. This object travels a distance d away from the center on either side.
     * When the distance between the object and the center is more than 80% of the oscillator distance, the object will accelerate towards the center  at the rate of 40% of its maximum velocity.
     * @param o the GameObject that the script is being applied to
     */
    public void executeUpdate(GameObject o) {
        Vector toCenter = o.transform.getPosition().subtract(center);
        if(Math.abs(toCenter.getMagnitude()) >= distance*0.8f && (o.transform.getLinearVelocity().getMagnitude() <= (maxVelocity.get(o) * 4f) + 0.1f || !o.transform.getLinearVelocity().dirEquals(toCenter))) {
            float accelerationMagnitude = maxVelocity.get(o);
            o.transform.setLinearAcceleration(o.transform.getPosition().subtract(center).getUnit().invert().multiply(accelerationMagnitude));
        }
        else {
            o.transform.setLinearAcceleration(0, 0, 0);
        }
    }
}
