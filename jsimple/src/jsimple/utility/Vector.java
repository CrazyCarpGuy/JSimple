package jsimple.utility;

/* This class represents a vector with four components.
 * Written by Ryan Munn on 2/4/22.
 * Reviewed by Ben on 2/4/22
 */

public class Vector {
	
	/* Declare vector components.
	 * (x, y, z) represents a given point in 3D space.
	 * (scaledX, scaledY) represents the 2D projection of (x, y, z), scaled by a factor of 1/z.
	 * w represents a component necessary for 4x4 matrix transformation. Not directly modifiable since it's only used for vector normalization.
	 */
	public float x, y, scaledX, scaledY, z, w = 1;

	/**
	 * A vector with magnitude zero.
	 */
	public static final Vector ZERO = new Vector(0, 0, 0);
	
	public Vector(float x, float y, float z) {
		
		this.setValue(x, y, z);

	}

	public Vector(double x, double y, double z) {
		this((float) x, (float) y, (float) z);
	}

	//Set the value of each component of this vector.
	public void setValue(float x, float y, float z) {
			
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.scaledX = this.x / this.z;
		this.scaledY = this.y / this.z;
		
	}
	
	//Set each component of this vector equal to the corresponding component of v.
	public void setEqualTo(Vector v) {
		
		this.setValue(v.x, v.y, v.z);
		
	}

	/**
	 * Checks if this vector has the same direction as another vector by comparing unit vectors.
	 * @param v the second vector to check equality
	 * @return Whether the two Vectors share a direction, as a boolean
	 */
	public boolean dirEquals(Vector v) {
		if(this.getUnit().equals(v.getUnit())) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if this Vector is equal to another Vector.
	 * @param v the other Vector to check equality.
	 * @return a boolean representing whether the two Vectors are equal.
	 */
	public boolean equals(Vector v) {
		if(v.x == x && v.y == y && v.z == z) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if this Vector has equal magnitude to another Vector.
	 * @param v the other Vector to check equality.
	 * @return a boolean representation of whether this Vector has equal magnitude to another Vector.
	 */
	public boolean mEquals(Vector v) {
		if(getMagnitude() == v.getMagnitude()) {
			return true;
		}
		return false;
	}
	
	//Return a new Vector with each component equal to the sum of this and v's respective components. Leaves this vector and v unchanged. 
	public Vector add(Vector v) {
		
		return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
		
	}
	
	//Return a new Vector with each component equal to the difference between this and v's respective components. Leaves this vector and v unchanged.
	public Vector subtract(Vector v) {
		
		return new Vector(this.x - v.x, this.y - v.y, this.z - v.z);
		
	}
	
	//Returns a float equal to the scalar dot product between this and v. Leaves this vector and v unchanged.
	public float dot(Vector v) {
		
		return this.x * v.x + this.y * v.y + this.z * v.z;
		
	}
	
	//Returns a new Vector equal to the vector cross product between this and v. Leaves this vector and v unchanged.
	public Vector cross(Vector v) {
		
		return new Vector(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
		
	}
	
	//Returns a new Vector equal to the scalar multiplication of this by the specified factor. Leaves this vector unchanged.
	public Vector multiply(float factor) {

		return new Vector(this.x * factor, this.y * factor, this.z * factor);

	}
	
	//Returns a new Vector equal to the scalar division of this by the specified factor. Leaves this vector unchanged.
	public Vector divide(float factor) {
		//Ensure no division by zero occurs
		if (factor != 0) {
			return new Vector(this.x / factor, this.y / factor, this.z / factor);
		} else {
			return this;
		}
		
	}
	
	//Returns a float equal to the magnitude of this vector. Leaves this vector unchanged.
	public float getMagnitude() {
		
		return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
				
	}
	
	//Returns a new Vector with magnitude 1 in the direction of this vector. Leaves this vector unchanged.
	public Vector getUnit() {
		
			return this.divide(this.getMagnitude());
		
	}

	//Returns a new Vector with magnitude identical to and direction opposite this vector. Leaves this vector unchanged.
	public Vector invert() {

		return this.multiply(-1);

	}

	//Returns a new Vector equal to this vector linearly transformed by the matrix m. Leaves this vector unchanged.
	public Vector transform(Matrix m) {
		
		Vector v = new Vector(this.x * m.matrix[0][0] + this.y * m.matrix[0][1] + this.z * m.matrix[0][2] + this.w * m.matrix[0][3],
							  this.x * m.matrix[1][0] + this.y * m.matrix[1][1] + this.z * m.matrix[1][2] + this.w * m.matrix[1][3],
							  this.x * m.matrix[2][0] + this.y * m.matrix[2][1] + this.z * m.matrix[2][2] + this.w * m.matrix[2][3]);
		v.w = this.x * m.matrix[3][0] + this.y * m.matrix[3][1] + this.z * m.matrix[3][2] + this.w * m.matrix[3][3];

		return v;
		
	}
	
	//Returns whether or not this Vector lies on the inside of the given plane.
	public boolean isInsidePlane(Vector point, Vector normal) {
		
		return normal.dot(this) - normal.dot(point) > 0; 
		
	}
	
	//Returns a Vector representing the coordinates of the intersection point between two vector points and a specified plane.
	public Vector linePlaneIntersection(Vector v, Vector point, Vector normal) {
		
		Vector direction = v.subtract(this);
		
		//Find parametric t value for which line equation satisfies plane equation
		float t = -(normal.dot(this) - normal.dot(point)) 
				/ (normal.dot(direction));

		return new Vector(this.x + direction.x * t, this.y + direction.y * t, this.z + direction.z * t);
		
	}

	public float getAngle(Vector v) {
		double mag = dot(v)/(getMagnitude()*v.getMagnitude());
		return (float)Math.acos(mag);
	}

	@Override
	public String toString() {
		return "<" + x + ", " + y + "," + z + ">";
	}
}
