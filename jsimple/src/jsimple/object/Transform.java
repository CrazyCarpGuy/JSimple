package jsimple.object;

/* This class contains information about the position and rotation of a game object.
 * Written by Ryan Munn on 4/13/22
 */

import jsimple.utility.Vector;

public class Transform {

	private GameObject parent;
	private Vector position, linearVelocity, linearAcceleration, rotation, angularVelocity, angularAcceleration;
	
	
	public Transform() {
		
		this.setPosition(0, 0, 0);
		this.setLinearVelocity(0, 0, 0);
		this.setLinearAcceleration(0, 0, 0);
		
		this.setRotation(0, 0, 0);
		this.setAngularVelocity(0, 0, 0);
		this.setAngularAcceleration(0, 0, 0);
		
	}
	
	public void setParent(GameObject gameObject) {
		this.parent = gameObject;	
	}
	
	public void setPosition(Vector position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y, float z) {
		this.position = new Vector(x, y, z);
	}
	
	public void setXPosition(float x) {
		this.position.x = x;
	}
	
	public void setYPosition(float y) {
		this.position.y = y;
	}
	
	public void setZPosition(float z) {
		this.position.z = z;
	}
	
	public Vector getPosition() {
		return this.position;
	}
	
	public void setLinearVelocity(Vector linearVelocity) {
		this.linearVelocity = linearVelocity;
	}
	
	public void setLinearVelocity(float x, float y, float z) {
		this.linearVelocity = new Vector(x, y, z);
	}
	
	public void setLinearXVelocity(float x) {
		this.linearVelocity.x = x;
	}
	
	public void setLinearYVelocity(float y) {
		this.linearVelocity.y = y;
	}
	
	public void setLinearZVelocity(float z) {
		this.linearVelocity.z = z;
	}
	
	public Vector getLinearVelocity() {
		return this.linearVelocity;
	}
	
	public void setLinearAcceleration(Vector linearAcceleration) {
		this.linearAcceleration = linearAcceleration;
	}
	
	public void setLinearAcceleration(float x, float y, float z) {
		this.linearAcceleration = new Vector(x, y, z);
	}
	
	public void setLinearXAcceleration(float x) {
		this.linearAcceleration.x = x;
	}
	
	public void setLinearYAcceleration(float y) {
		this.linearAcceleration.y = y;
	}
	
	public void setLinearZAcceleration(float z) {
		this.linearAcceleration.z = z;
	}
	
	public Vector getLinearAcceleration() {
		return this.linearAcceleration;
	}
	
	public void setRotation(Vector rotation) {
		this.rotation = rotation;
	}
	
	public void setRotation(float x, float y, float z) {
		this.rotation = new Vector(x, y, z);
	}
	
	public void setXRotation(float x) {
		this.rotation.x = x;
	}
	
	public void setYRotation(float y) {
		this.rotation.y = y;
	}
	
	public void setZRotation(float z) {
		this.rotation.z = z;
	}
	
	public Vector getRotation() {
		return this.rotation;
	}
	
	public void setAngularVelocity(Vector angularVelocity) {
		this.angularVelocity = angularVelocity;
	}
	
	public void setAngularVelocity(float x, float y, float z) {
		this.angularVelocity = new Vector(x, y, z);
	}
	
	public void setAngularXVelocity(float x) {
		this.angularVelocity.x = x;
	}
	
	public void setAngularYVelocity(float y) {
		this.angularVelocity.y = y;
	}
	
	public void setAngularZVelocity(float z) {
		this.angularVelocity.z = z;
	}
	
	public Vector getAngularVelocity() {
		return this.angularVelocity;
	}
	
	public void setAngularAcceleration(Vector angularAcceleration) {
		this.angularAcceleration = angularAcceleration;
	}
	
	public void setAngularAcceleration(float x, float y, float z) {
		this.angularAcceleration = new Vector(x, y, z);
	}
	
	public void setAngularXAcceleration(float x) {
		this.angularAcceleration.x = x;
	}
	
	public void setAngularYAcceleration(float y) {
		this.angularAcceleration.y = y;
	}
	
	public void setAngularZAcceleration(float z) {
		this.angularAcceleration.z = z;
	}
	
	public Vector getAngularAcceleration() {
		return this.angularAcceleration;
	}
	
	public void update(float dt) {
		
		linearVelocity = linearVelocity.add(linearAcceleration.multiply(1 / 100.0f));
		position = position.add(linearVelocity.multiply(1 / 100.0f));
		
		angularVelocity = angularVelocity.add(angularAcceleration.multiply(1 / 100.0f));
		rotation = rotation.add(angularVelocity.multiply(1 / 100.0f));
		
	}

}
