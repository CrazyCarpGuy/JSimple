package jsimple.scripts;

import jsimple.object.GameObject;
import jsimple.object.Script;
import jsimple.utility.Vector;

import java.util.HashMap;

public class Orbiter implements Script {
    
	/**
     * The center of the orbit.
     */
    public Vector center;
    public GameObject reference;
    private HashMap<GameObject, Float> radius;

    /**
     * Initializes the Orbiter.
     * @param c the desired point for the center of the orbit
     */
    public Orbiter(Vector c) {
        radius = new HashMap<>();
        center = c;
        reference = null;
    }

    /**
     * Initializes the orbiter, with a given GameObject as a center
     * @param o the GameObject that the central point of the orbit should map to.
     */
    public Orbiter(GameObject o) {
        this(o.transform.getPosition());
        reference = o;
    }

    @Override
    /**
     * {@inheritDoc}
     * Adds the target object to a list of all objects using this Orbiter, along with its initial distance from the center.
     * @param o the GameObject that the script is being applied to
     */
	public void executeInit(GameObject o) {
        radius.put(o, o.transform.getPosition().subtract(center).getMagnitude());
    }
    
    /**
     * {@inheritDoc}
     * Calculates the centripetal acceleration of the target object.
     * @param o the GameObject that the script is being applied to
     */
    public void executeUpdate(GameObject o){
        Vector rad = o.transform.getPosition().subtract(center);
        float r = radius.get(o);
        float acc = Math.round((float)(Math.pow(o.transform.getLinearVelocity().getMagnitude(),2))/(Math.round(r*100)/100f)*100)/100f; //find magnitude of acceleration
        o.transform.setLinearAcceleration(rad.getUnit().invert().multiply(acc)); //point acceleration towards orbital center
        if(reference != null && reference.transform.getLinearVelocity() != Vector.ZERO) {
            o.transform.setPosition(o.transform.getPosition().add(reference.transform.getLinearVelocity().multiply(1 / 100.0f)));
            center = reference.transform.getPosition();
        }
    }

    public Vector calculateRadius(Vector pos) {
        return pos.subtract(center);
    }
}
