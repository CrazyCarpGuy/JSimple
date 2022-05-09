package jsimple.scripts;

import jsimple.object.GameObject;
import jsimple.object.Script;
import jsimple.render.Camera;
import jsimple.utility.Vector;

public class Pursuer implements Script {
    /**
     * The target object to be pursued.
     */
    public GameObject target;
    public Vector distToTarget;

    public boolean followRotation;

    /**
     * Initializes the pursuit script with a target.
     * @param o The target to be followed.
     */
    public Pursuer(GameObject o, boolean r) {
        target = o;
        followRotation = r;
    }

    @Override
	public void executeInit(GameObject o) {}
    
    /**
     * {@inheritDoc}
     * Sets the follower object's velocity to point towards the target.
     * @param o the GameObject that the script is being applied to
     */
    public void executeUpdate(GameObject o) {
        distToTarget = target.transform.getPosition().subtract(o.transform.getPosition());
        float speed = o.transform.getLinearVelocity().getMagnitude();
        Vector targetDirection = distToTarget.getUnit().multiply(speed);
        o.transform.setLinearVelocity(targetDirection);
        if(followRotation) {
            setFollowRotation(o);
        }
    }

    public void setFollowRotation(GameObject o) {
        Vector direction = distToTarget.getUnit();
        float xAngle = direction.getAngle(new Vector(1,0, 0));
        float yAngle = direction.getAngle(new Vector(0,1, 0));
        float zAngle = direction.getAngle(new Vector(0,0, 1));
        o.transform.setRotation(new Vector(xAngle, yAngle, zAngle).invert());
        System.out.println(o.transform.getRotation().divide((float)Math.PI));
    }
}
