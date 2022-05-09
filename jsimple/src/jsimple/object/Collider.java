package jsimple.object;

import jsimple.core.JSimple;
import jsimple.utility.Vector;

/* This class detects collisions between game objects
 * Written by Ryan Munn on 4/13/22
 */

public class Collider {

	private GameObject parent;
	
	public GameObject collider;
	public boolean colliding;
	private Vector size;

	public Collider() {
		
		this.setSize(1, 1, 1);
		
	}
	
	public void setParent(GameObject gameObject) {
		this.parent = gameObject;
	}
	
	public void setSize(Vector size) {
		this.size = size;
	}
	
	public void setSize(float width, float height, float depth) {
		this.size = new Vector(width, height, depth);
	}
	
	public void setXSize(float x) {
		this.size.x = x;
	}
	
	public void setYSize(float y) {
		this.size.y = y;
	}
	
	public void setZSize(float z) {
		this.size.z = z;
	}
	
	public Vector getSize() {
		return this.size;
	}
	
	public void update(float dt) {
		
		colliding = false;
		
		for (GameObject gameObject : JSimple.gameObjects.values()) {
		
			if (!colliding && !gameObject.equals(this.parent)) {
				
				float dx = Math.abs(this.parent.transform.getPosition().x - gameObject.transform.getPosition().x),
					dy = Math.abs(this.parent.transform.getPosition().y - gameObject.transform.getPosition().y),
					dz = Math.abs(this.parent.transform.getPosition().z - gameObject.transform.getPosition().z);
				
				if (dx + dx < gameObject.collider.getSize().x + this.getSize().x 
						&& dy + dy < gameObject.collider.getSize().y + this.getSize().y
						&& dz + dz < gameObject.collider.getSize().z + this.getSize().z) {
					
					colliding = true;
					collider = gameObject;
					
				}
				
			}
			
		}
		
	}
	
}
