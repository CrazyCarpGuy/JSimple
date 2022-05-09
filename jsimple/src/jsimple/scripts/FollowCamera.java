package jsimple.scripts;

import jsimple.core.Input;
import jsimple.object.GameObject;
import jsimple.object.Script;
import jsimple.render.Camera;
import jsimple.utility.Vector;

public class FollowCamera implements Script {
    public GameObject target;
    public Vector offset;
    public boolean followRotation;

    public FollowCamera(GameObject o, Vector d, boolean rotation) {
        target = o;
        offset = d;
        followRotation = rotation;
    }

    @Override
    public void executeInit(GameObject o) {
    }

    @Override
    public void executeUpdate(GameObject o) {
        Vector distFromTarget = Vector.ZERO;
        Camera.setPosition(target.transform.getPosition().add(offset));

        if(!followRotation) {
            if (Input.upPressed) {
                Camera.angularSpeed.x = 0.002f;
            } else if (Input.downPressed) {
                Camera.angularSpeed.x = -0.002f;
            } else {
                Camera.angularSpeed.x = 0;
            }
            if (Input.rightPressed) {
                Camera.angularSpeed.y = -0.002f;
            } else if (Input.leftPressed) {
                Camera.angularSpeed.y = 0.002f;
            } else {
                Camera.angularSpeed.y = 0;
            }
        }
        else {
            setFollowRotation();
        }
    }

    public void setFollowRotation() {
        Vector direction = Camera.rotation.subtract(target.transform.getPosition()).invert();
        double x = (Math.atan(direction.z / direction.y) - Math.PI), y = 0, z = (Math.atan(direction.x / direction.y))-(Math.atan(direction.z / direction.x));
        Vector angle  = new Vector(x, y, z);
        if(direction.z > 0 && direction.x == 0) {
            angle.z -= (float)Math.PI / 2;
        }
        else if(direction.z < 0 && direction.x == 0) {
            angle.z += (float)Math.PI / 2;
        }
        if (direction.z > 0) {
            angle.x  += Math.PI;
        }
        if(direction.y > 0) {
            angle.z = (float)(Math.PI + angle.z);
        }
        if(direction.z > 0) {
            angle.z = (float)Math.PI - angle.z;
        }
        Camera.rotation = angle;
    }
}
