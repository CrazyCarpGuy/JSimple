package jsimple.render;


import java.awt.Color;
import java.util.ArrayList;

import jsimple.core.JSimple;
import jsimple.object.GameObject;
import jsimple.utility.*;

/* This class contains information about a triangle in 3D space.
 * Written by Ryan Munn on 2/5/22.
 */

public class Triangle {
	
	/* Declare fixed and transformed vertex position vectors.
	 * Fixed vertices represent the original, absolute position of a vertex before any external movement, scaling, rotation, and projection.
	 * Transformed vertices represent the new position of a vertex after all external movement, scaling, rotation, and projection.
	 */
	
	private Vector fixedVertex1, fixedVertex2, fixedVertex3, transformedVertex1, transformedVertex2, transformedVertex3, 
				   fixedNormal, transformedNormal, uv1, uv2, uv3;
	
	private GameObject parent;
	private boolean isWorld;
	
	/*
    public void setColor(Color c) {
    	
        color = c;
        //Checks if colors are inverted and applies brightness
        if(JSimple.isInverted()) {
            invertColor();
        }
        scalarColor(JSimple.getBrightness());

    }
    
	//Scale the color of this triangle by a given factor
    public void scalarColor(double scalar) {
        int r = Math.min((int) (scalar * color.getRed()), 255);
        int g = Math.min((int) (scalar * color.getGreen()), 255);
        int b = Math.min((int) (scalar * color.getBlue()), 255);
        color = new Color(r, g, b);
    }

    //Inverts the color of this triangle 
    public void invertColor() {
        int r = 255 - color.getRed();
        int g = 255 - color.getGreen();
        int b = 255 - color.getBlue();
        int a = color.getAlpha();
        color = new Color(r, g, b, a);
    }
	*/
	
	public Triangle(Vector v1, Vector v2, Vector v3, Vector normal, GameObject gameObject) {
		
		this.fixedVertex1 = v1;
		this.fixedVertex2 = v2;
		this.fixedVertex3 = v3;
		
		this.fixedNormal = normal;
		this.parent = gameObject;
		
		this.isWorld = true;

	}
	
	public Triangle(Vector v1, Vector v2, Vector v3, Vector normal, GameObject gameObject, boolean isWorld) {
		
		this.fixedVertex1 = v1;
		this.fixedVertex2 = v2;
		this.fixedVertex3 = v3;
		
		this.fixedNormal = normal;
		this.parent = gameObject;
		
		//Specifies whether this triangle is part of the world and must therefore be clipped.
		this.isWorld = isWorld;
			
	}
	
	public Triangle(Vector v1, Vector v2, Vector v3, Vector uv1, Vector uv2, Vector uv3, Vector normal, GameObject gameObject) {
		
		this.fixedVertex1 = v1;
		this.fixedVertex2 = v2;
		this.fixedVertex3 = v3;
		
		this.uv1 = uv1;
		this.uv2 = uv2;
		this.uv3 = uv3;
		
		this.fixedNormal = normal;
		this.parent = gameObject;
		
		this.isWorld = true;

	}
	
	public Triangle(Vector v1, Vector v2, Vector v3, Vector uv1, Vector uv2, Vector uv3, Vector normal, GameObject gameObject, boolean isWorld) {
		
		this.fixedVertex1 = v1;
		this.fixedVertex2 = v2;
		this.fixedVertex3 = v3;
		
		this.uv1 = uv1;
		this.uv2 = uv2;
		this.uv3 = uv3;
		
		this.fixedNormal = normal;
		this.parent = gameObject;
		
		//Specifies whether this triangle is part of the world and must therefore be clipped.
		this.isWorld = isWorld;
			
	}
	
	//Clip all triangles to the view frustrum and update the image data for each.
	public void render() {
		
		if(parent.isVisible) {
			
			for (Triangle t : this.getClipped()) { //Iterate through each clipped triangle

				if (t.transformedNormal != null && t.transformedNormal.dot(Camera.forward) <= 0.707) { //Ensure triangle is (at least somewhat) facing toward the camera

					t.draw();

				}

			}
			
		}
		
	}
	
	// Return an ArrayList of triangles resulting from clipping this Triangle along all view frustrum planes.
	private ArrayList<Triangle> getClipped() {
		
		ArrayList<Triangle> unclippedTris = new ArrayList<>(),
							clippedTris = new ArrayList<>();
		
		this.transform();
		
		//Clip the triangle along the plane z = 0 (the near plane to the camera - prevents divide by zero errors!).
		clippedTris.addAll(this.clipAgainstPlane(new Vector(0, 0, 1), new Vector(0, 0, 1))); //Clip against near z plane
		
		//Clip the triangle along the plane which defines the right edge of the view frustrum.
		unclippedTris.addAll(clippedTris);
		clippedTris.clear();
		for (Triangle tri : unclippedTris) {
			clippedTris.addAll(tri.clipAgainstPlane(new Vector(1, 0, 0), new Vector(-1, 0, JSimple.getWidth() / 2)));
		}
		
		//Clip the triangle along the plane which defines the left edge of the view frustrum.
		unclippedTris.clear();
		unclippedTris.addAll(clippedTris);
		clippedTris.clear();
		for (Triangle tri : unclippedTris) {
			clippedTris.addAll(tri.clipAgainstPlane(new Vector(-1, 0, 0), new Vector(1, 0, JSimple.getWidth() / 2)));
		}
		
		//Clip the triangle along the plane which defines the top edge of the view frustrum.
		unclippedTris.clear();
		unclippedTris.addAll(clippedTris);
		clippedTris.clear();
		for (Triangle tri : unclippedTris) {
			clippedTris.addAll(tri.clipAgainstPlane(new Vector(0, 1, 0), new Vector(0, -1, JSimple.getHeight() / 2)));
		}
		
		//Clip the triangle along the plane which defines the bottom edge of the view frustrum.
		unclippedTris.clear();
		unclippedTris.addAll(clippedTris);
		clippedTris.clear();
		for (Triangle tri : unclippedTris) {
			clippedTris.addAll(tri.clipAgainstPlane(new Vector(0, -1, 0), new Vector(0, 1, JSimple.getHeight() / 2)));
		}
		
		return clippedTris;
	}

	/* Return an ArrayList of triangles resulting from clipping this Triangle along the specified plane.
	 * The ArrayList contains 0 triangles if all 3 points of this Triangle lie outside the specified plane.
	 * The ArrayList contains 2 triangle if exactly 2 points of this Triangle lie inside the specified plane.
	 * The ArrayList contains 1 triangle otherwise.
	 */
	private ArrayList<Triangle> clipAgainstPlane(Vector point, Vector normal) {
		
		int numInside = 0;
		boolean v1Inside = false, v2Inside = false;
		
		Triangle tri1, tri2;
		ArrayList<Triangle> clippedTris = new ArrayList<>();
		
		point = point.transform(Matrix.PROJECTION);
	
		//Count the number of points inside the plane, and determine which they are.
		if (this.transformedVertex1.isInsidePlane(point, normal)) {numInside++; v1Inside = true;}
		if (this.transformedVertex2.isInsidePlane(point, normal)) {numInside++; v2Inside = true;}
		if (this.transformedVertex3.isInsidePlane(point, normal)) {numInside++;}
		
		if (numInside == 1) {

			//ArrayList should contain one triangle composed of the vertex inside the plane and both intersection points.
			if (v1Inside) {
				
				tri1 = new Triangle(transformedVertex1, transformedVertex1.linePlaneIntersection(transformedVertex2, point, normal), 
						transformedVertex1.linePlaneIntersection(transformedVertex3, point, normal), this.fixedNormal, this.parent, false);
				
			} else if (v2Inside) {

				tri1 = new Triangle(transformedVertex2, transformedVertex2.linePlaneIntersection(transformedVertex1, point, normal), 
						transformedVertex2.linePlaneIntersection(transformedVertex3, point, normal), this.fixedNormal, this.parent, false);
				
			} else {
				
				tri1 = new Triangle(transformedVertex3, transformedVertex3.linePlaneIntersection(transformedVertex1, point, normal), 
						transformedVertex3.linePlaneIntersection(transformedVertex2, point, normal), this.fixedNormal, this.parent, false);
				
			}
			
			//Sort the triangle's vertices and add it to the ArrayList.
			tri1.sort();
			clippedTris.add(tri1);
			
		}
		else if (numInside == 2) {
			
			Vector intersect1, intersect2;
			
			//ArrayList should contain two triangles which form a quad between the two vertices inside the plane and both intersection points.
			if (!v1Inside) {
				
				intersect1 = transformedVertex1.linePlaneIntersection(transformedVertex2, point, normal);
				intersect2 = transformedVertex1.linePlaneIntersection(transformedVertex3, point, normal);

				tri1 = new Triangle(transformedVertex2, intersect1, intersect2, this.fixedNormal, this.parent, false);
				tri2 = new Triangle(transformedVertex2, transformedVertex3, intersect2, this.fixedNormal, this.parent, false);
						
			} else if (!v2Inside) {

				intersect1 = transformedVertex2.linePlaneIntersection(transformedVertex1, point, normal);
				intersect2 = transformedVertex2.linePlaneIntersection(transformedVertex3, point, normal);
				
				tri1 = new Triangle(transformedVertex1, intersect1, intersect2, this.fixedNormal, this.parent, false);
				tri2 = new Triangle(transformedVertex1, transformedVertex3, intersect2, this.fixedNormal, this.parent, false);
				
			} else {
				
				intersect1 = transformedVertex3.linePlaneIntersection(transformedVertex1, point, normal);
				intersect2 = transformedVertex3.linePlaneIntersection(transformedVertex2, point, normal);

				tri1 = new Triangle(transformedVertex1, intersect1, intersect2, this.fixedNormal, this.parent, false);
				tri2 = new Triangle(transformedVertex1, transformedVertex2, intersect2, this.fixedNormal, this.parent, false);
				
			}
			
			//Sort the triangles' vertices and add them to the ArrayList.
			tri1.sort();
			clippedTris.add(tri1);
			
			tri2.sort();
			clippedTris.add(tri2);

		}
		else if (numInside == 3) {
			
			//The triangle falls entirely within the specified plane and doesn't need to be clipped. Sort its vertices and add it to the ArrayList.
			this.sort();
			clippedTris.add(this);
			
		}
		
		return clippedTris;
		
	}
	
	//Update this clipped triangle's image data.
	private void draw() {
		
		if (transformedVertex1.scaledY == transformedVertex2.scaledY) { //Triangle has a flat top.
			//Ensure second vector argument is left of third vector argument.
			if (transformedVertex1.scaledX < transformedVertex2.scaledX) { //transformedVertex1 is left of transformedVertex2
				this.drawFlatTop(transformedVertex1, transformedVertex2, transformedVertex3);
			}
			else { //transformedVertex2 is left of transformedVertex1.
				this.drawFlatTop(transformedVertex2, transformedVertex1, transformedVertex3);
			}
		}
		else if (transformedVertex2.scaledY == transformedVertex3.scaledY) { //Triangle has a flat bottom.
			//Ensure second vector argument is left of third vector argument.
			if (transformedVertex2.scaledX < transformedVertex3.scaledX) { //transformedVertex2 is left of transformedVertex3.
				this.drawFlatBottom(transformedVertex1, transformedVertex2, transformedVertex3);
			}
			else { //transformedVertex3 is left of transformedVertex2.
				this.drawFlatBottom(transformedVertex1, transformedVertex3, transformedVertex2);
			}
		}
		else { //Triangle is irregular, with neither a flat top nor flat bottom; must be split.
			//Calculate a fourth vertex which will split the triangle horizontally along the line with transformedVertex2.
			float scaledX4 = transformedVertex1.scaledX + (transformedVertex2.scaledY - transformedVertex1.scaledY) / (transformedVertex3.scaledY - transformedVertex1.scaledY) * (transformedVertex3.scaledX - transformedVertex1.scaledX),
				  scaledY4 = transformedVertex2.scaledY,
				  z4 = calculateDepth(scaledX4, scaledY4, transformedVertex1, transformedVertex2, transformedVertex3);
			Vector transformedVertex4 = new Vector(scaledX4 * z4, scaledY4 * z4, z4);
			//Ensure second vector argument is left of third vector argument.
			if (transformedVertex2.scaledX < transformedVertex4.scaledX) { //transformedVertex2 is left of transformedVertex4.
				this.drawFlatBottom(transformedVertex1, transformedVertex2, transformedVertex4);	
				this.drawFlatTop(transformedVertex2, transformedVertex4, transformedVertex3);
			}
			else { //transformedVertex4 is left of transformedVertex2.
				this.drawFlatBottom(transformedVertex1, transformedVertex4, transformedVertex2);
				this.drawFlatTop(transformedVertex4, transformedVertex2, transformedVertex3);
			}
		}

	}
	
	//Perform all world and projection transformations of each vertex and the normal vector.
	private void transform() {
		
		Matrix translationMatrix = Matrix.getTranslation(parent.transform.getPosition().x, parent.transform.getPosition().y, parent.transform.getPosition().z),
				rotationMatrix = Matrix.getRotation(parent.transform.getRotation().x, parent.transform.getRotation().y, parent.transform.getRotation().z),
				transformMatrix = Matrix.PROJECTION.multiply(Camera.worldMatrix.multiply(translationMatrix.multiply(rotationMatrix)));
		
		transformedVertex1 = fixedVertex1.transform(transformMatrix);
		transformedVertex2 = fixedVertex2.transform(transformMatrix);
		transformedVertex3 = fixedVertex3.transform(transformMatrix);
		
		transformedNormal = fixedNormal.transform(rotationMatrix);
		
	}
	
	/* Sort the transformed vertices such that:
	 * transformedVertex1 represents the vertex highest on the screen,
	 * transformedVertex2 represents the middle vertex on the screen,
	 * transformedVertex3 represents the vertex lowest on the screen.
	 */
	private void sort() {
		
		Vector tempVertex1, tempVertex2, tempVertex3;
		
		if (isWorld) { //World triangles can be sorted by transformed coordinates
			tempVertex1 = transformedVertex1;
			tempVertex2 = transformedVertex2;
			tempVertex3 = transformedVertex3;
		} else { //Render triangles aren't transformed, must be sorted by fixed coordinates
			tempVertex1 = fixedVertex1;
			tempVertex2 = fixedVertex2;
			tempVertex3 = fixedVertex3;
		}
		
		if (tempVertex1.scaledY >= tempVertex2.scaledY && tempVertex1.scaledY >= tempVertex3.scaledY) { //tempVertex1 is (at least tied for) highest on the screen. 
			transformedVertex1 = tempVertex1;
			if (tempVertex2.scaledY >= tempVertex3.scaledY) { //tempVertex2 is (at least tied for) second highest on the screen, tempVertex3 is lowest.
				transformedVertex2 = tempVertex2;
				transformedVertex3 = tempVertex3;
			}
			else { //tempVertex3 is second highest on the screen, tempVertex2 is lowest.
				transformedVertex2 = tempVertex3;
				transformedVertex3 = tempVertex2;
			}
		}
		else if (tempVertex2.scaledY >= tempVertex1.scaledY && tempVertex2.scaledY >= tempVertex3.scaledY) { //tempVertex2 is (at least tied for) highest on the screen.
			transformedVertex1 = tempVertex2;
			if (tempVertex1.scaledY >= tempVertex3.scaledY) { //tempVertex1 is (at least tied for) second highest on the screen, tempVertex3 is lowest.
				transformedVertex2 = tempVertex1;
				transformedVertex3 = tempVertex3;
			}
			else { //tempVertex3 is second highest on the screen, tempVertex1 is lowest.
				transformedVertex2 = tempVertex3;
				transformedVertex3 = tempVertex1;
			}
		}
		else { //tempVertex3 is (at least tied for) highest on the screen.
			transformedVertex1 = tempVertex3;
			if (tempVertex1.scaledY >= tempVertex2.scaledY) { //tempVertex1 is (at least tied for) second highest on the screen, tempVertex2 is lowest.
				transformedVertex2 = tempVertex1;
				transformedVertex3 = tempVertex2;
			}
			else { //tempVertex2 is second highest on the screen, tempVertex1 is lowest.
				transformedVertex2 = tempVertex2;
				transformedVertex3 = tempVertex1;
			}
		}
	}
	
	//Temporarily defined using crude shadow lighting.
	private int getRGB(int screenX, int screenY) {
		
		//Determine lighting shadow factor
		float factor = (1 - this.transformedNormal.dot(Camera.forward)) / 2f;
		Color color;
		
		if (this.parent.model.getColor() != null) {
			
			color = this.parent.model.getColor();
			
		} else {

			Vector uvcoords = this.getUV(screenX, screenY, transformedVertex1, transformedVertex2, transformedVertex3, uv1, uv2, uv3);
			
			int width = this.parent.model.getTexture().getWidth(), height = this.parent.model.getTexture().getHeight(), 
					x = (int)(uvcoords.x * width), y = (int)((1 - uvcoords.y) * height);
			
			if (x < 0) {
				x = 0;
			} else if (x >= width) {
				x = width - 1;
			}
			if (y < 0) {
				y = 0;
			} else if (y >= height) {
				y = height - 1;
			}
			color = new Color(this.parent.model.getTexture().getRGB(x, y));

		}
		
		return (new Color(color.getRed() * factor / 255, color.getGreen() * factor / 255, color.getBlue() * factor / 255)).getRGB();
	}
	
	/* VISUAL REPRESENTATION
	 * 			v1
	 * 
	 * 		v2		v3
	 */
	/**
	 *  Fill a triangle with the bottom vertices at the same on-screen y value and the top vertex at different y
	 * @param v1 A vector representation of the top vertex of the triangle
	 * @param v2 A vector representation of the bottom-left vertex of the triangle
	 * @param v3 A vector representation of the bottom-right vertex of the triangle
	 */
	private void drawFlatBottom(Vector v1, Vector v2, Vector v3) {
		
		int leftX, rightX;
		//Calculate change in x per step in y of left and right edges of triangle.
		float leftSlope = (v2.scaledX - v1.scaledX) / (v2.scaledY - v1.scaledY), 
			  rightSlope = (v3.scaledX - v1.scaledX) / (v3.scaledY - v1.scaledY),
			  depth;
		
		for (int y = Math.round(v1.scaledY - 1); y >= Math.round(v3.scaledY); y--) { //Fill the triangle in horizontal strips from top to bottom.
			//Calculate horizontal intersection points with edges of the triangle.
			leftX = (int)Math.ceil(v1.scaledX + leftSlope * (y - v1.scaledY + 0.5) - 0.5);
			rightX = (int)Math.floor(v1.scaledX + rightSlope * (y - v1.scaledY + 0.5) - 0.5);
			for (int x = leftX; x <= rightX; x++) { //Fill the horizontal line between the intersection points.
				//Calculate the distance to the pixel at (x, y) on the triangle.
				depth = calculateDepth(x + 0.5f, y + 0.5f, v1, v2, v3);
				if (depth < JSimple.getDepth(x, y)) { //Draw the new pixel at (x, y) since it's closer to the camera than the existing pixel.
					JSimple.setDepth(x, y, depth);
					JSimple.setRGB(x, y, this.getRGB(x, y));
				}
			}	
		}
	}


	/* VISUAL REPRESENTATION
	 *  v1	 	 v2
	 * 
	 * 		v3
	 */
	/**
	 *  Fill a triangle with the top vertices at the same on-screen y value and the bottom vertex at different y
	 * @param v1 A vector representation of the top-left vertex of the triangle
	 * @param v2 A vector representation of the top-right vertex of the triangle
	 * @param v3 A vector representation of the bottom vertex of the triangle
	 */
	private void drawFlatTop(Vector v1, Vector v2, Vector v3) {
		
		int leftX, rightX;
		//Calculate change in x per step in y of left and right edges of triangle.
		float leftSlope = (v3.scaledX - v1.scaledX) / (v3.scaledY - v1.scaledY), 
			  rightSlope = (v3.scaledX - v2.scaledX) / (v3.scaledY - v2.scaledY),
			  depth;
		
		for (int y = Math.round(v1.scaledY - 1); y >= Math.round(v3.scaledY); y--) { //Fill the triangle in horizontal strips from top to bottom.
			//Calculate horizontal intersection points with edges of the triangle.
			leftX = (int)Math.ceil(v1.scaledX + leftSlope * (y - v1.scaledY + 0.5) - 0.5);
			rightX = (int)Math.floor(v2.scaledX + rightSlope * (y - v2.scaledY + 0.5) - 0.5);
			for (int x = leftX; x <= rightX; x++) { //Fill the horizontal line between the intersection points.
				depth = calculateDepth(x + 0.5f, y + 0.5f, v1, v2, v3);
				if (depth < JSimple.getDepth(x, y)) { //Draw the new pixel at (x, y) since it's closer to the camera than the existing pixel.
					JSimple.setDepth(x, y, depth);
					JSimple.setRGB(x, y, this.getRGB(x, y));
				}
			}	
		}
	}

	//Determine the z coordinate at (x, y) on the triangle defined by v1, v2, and v3.
	private float calculateDepth(float x, float y, Vector v1, Vector v2, Vector v3) {
		
		float weight1;

		if (x == v1.scaledX && x == v2.scaledX) { //Point lies along vertical edge of v1 and v2.
			weight1 = Math.abs((y - v2.scaledY) / (v1.scaledY - v2.scaledY));
			return v1.z * weight1 + v2.z * (1 - weight1); 
		}
		else if (x == v1.scaledX && x == v3.scaledX) { //Point lies along vertical edge of v1 and v3.
			weight1 = Math.abs((y - v3.scaledY) / (v1.scaledY - v3.scaledY));
			return v1.z * weight1 + v3.z * (1 - weight1); 
		}
		else if (x == v2.scaledX && x == v3.scaledX) { //Point lies along vertical edge of v2 and v3.
			weight1 = Math.abs((y - v3.scaledY) / (v2.scaledY - v3.scaledY));
			return v2.z * weight1 + v3.z * (1 - weight1); 
		}
		else if (y == v1.scaledY && y == v2.scaledY){ //Point lies along top horizontal edge of v1 and v2.
			weight1 = Math.abs((v2.scaledX - x) / (v2.scaledX - v1.scaledX));
			return v1.z * weight1 + v2.z * (1 - weight1);
		}
		else if (y == v2.scaledY && y == v3.scaledY) { //Point lies along bottom horizontal edge of v2 and v3.
			weight1 = Math.abs((v3.scaledX - x) / (v3.scaledX - v2.scaledX));
			return v2.z * weight1 + v3.z * (1 - weight1);
		}
		else { //Check if point lies along a diagonal edge
			float m = (v1.scaledY - v3.scaledY) / (v1.scaledX - v3.scaledX),
				  b = v1.scaledY - m * v1.scaledX;
			if (y == m * x + b) { //Point lies along diagonal edge of v1 and v3.
					weight1 = Math.abs((v3.scaledX - x) / (v3.scaledX - v1.scaledX));
					return v1.z * weight1 + v3.z * (1 - weight1);
			}
			else {
				m = (v1.scaledY - v2.scaledY) / (v1.scaledX - v2.scaledX);
			    b = v1.scaledY - m * v1.scaledX;
				if (y == m * x + b) { //Point lies along diagonal edge of v1 and v2.
					weight1 = Math.abs((v2.scaledX - x) / (v2.scaledX - v1.scaledX));
					return v1.z * weight1 + v2.z * (1 - weight1);
				} else {
					m = (v2.scaledY - v3.scaledY) / (v2.scaledX - v3.scaledX);
					b = v2.scaledY - m * v2.scaledX;
					if (y == m * x + b) { //Point lies along diagonal edge of v2 and v3.
						weight1 = Math.abs((v3.scaledX - x) / (v3.scaledX - v2.scaledX));
						return v2.z * weight1 + v3.z * (1 - weight1);
					} else { //Point lies fully within the triangle.
						float dx1 = Math.abs(x - v1.scaledX),
							  dy1 = Math.abs(y - v1.scaledY),
							  dx2 = Math.abs(x - v2.scaledX),
							  dy2 = Math.abs(y - v2.scaledY),
							  dx3 = Math.abs(x - v3.scaledX),
							  dy3 = Math.abs(y - v3.scaledY),
							  weight2 = Math.abs(dy1 * dx3 - dx1 * dy3),
							  weight3 = Math.abs(dy1 * dx2 - dx1 * dy2);
						weight1 = Math.abs(dy2 * dx3 - dx2 * dy3);
					    float total = weight1 + weight2 + weight3;
						return 1.0f / (weight1 / v1.z / total + weight2 / v2.z / total + weight3 / v3.z / total);
					}
				}
			}
		}
	}

	//Determine the z coordinate at (x, y) on the triangle defined by v1, v2, and v3.
	private Vector getUV(float x, float y, Vector v1, Vector v2, Vector v3, Vector uv1, Vector uv2, Vector uv3) {
		
		float weight1;
		
		if (x == v1.scaledX && x == v2.scaledX) { //Point lies along vertical edge of v1 and v2.
			weight1 = Math.abs((y - v2.scaledY) / (v1.scaledY - v2.scaledY));
			//return v1.z * weight1 + v2.z * (1 - weight1); 
			return uv1.multiply(weight1).add(uv2.multiply(1 - weight1));
		}
		else if (x == v1.scaledX && x == v3.scaledX) { //Point lies along vertical edge of v1 and v3.
			weight1 = Math.abs((y - v3.scaledY) / (v1.scaledY - v3.scaledY));
			//return v1.z * weight1 + v3.z * (1 - weight1); 
			return uv1.multiply(weight1).add(uv3.multiply(1 - weight1));
		}
		else if (x == v2.scaledX && x == v3.scaledX) { //Point lies along vertical edge of v2 and v3.
			weight1 = Math.abs((y - v3.scaledY) / (v2.scaledY - v3.scaledY));
			//return v2.z * weight1 + v3.z * (1 - weight1); 
			return uv2.multiply(weight1).add(uv3.multiply(1 - weight1));
		}
		else if (y == v1.scaledY && y == v2.scaledY){ //Point lies along top horizontal edge of v1 and v2.
			weight1 = Math.abs((v2.scaledX - x) / (v2.scaledX - v1.scaledX));
			//return v1.z * weight1 + v2.z * (1 - weight1);
			return uv1.multiply(weight1).add(uv2.multiply(1 - weight1));
		}
		else if (y == v2.scaledY && y == v3.scaledY) { //Point lies along bottom horizontal edge of v2 and v3.
			weight1 = Math.abs((v3.scaledX - x) / (v3.scaledX - v2.scaledX));
			//return v2.z * weight1 + v3.z * (1 - weight1);
			return uv2.multiply(weight1).add(uv3.multiply(1 - weight1));
		}
		else { //Check if point lies along a diagonal edge
			float m = (v1.scaledY - v3.scaledY) / (v1.scaledX - v3.scaledX),
				  b = v1.scaledY - m * v1.scaledX;
			if (y == m * x + b) { //Point lies along diagonal edge of v1 and v3.
					weight1 = Math.abs((v3.scaledX - x) / (v3.scaledX - v1.scaledX));
					//return v1.z * weight1 + v3.z * (1 - weight1);
					return uv1.multiply(weight1).add(uv3.multiply(1 - weight1));
			}
			else {
				m = (v1.scaledY - v2.scaledY) / (v1.scaledX - v2.scaledX);
			    b = v1.scaledY - m * v1.scaledX;
				if (y == m * x + b) { //Point lies along diagonal edge of v1 and v2.
					weight1 = Math.abs((v2.scaledX - x) / (v2.scaledX - v1.scaledX));
					//return v1.z * weight1 + v2.z * (1 - weight1);
					return uv1.multiply(weight1).add(uv2.multiply(1 - weight1));
				} else {
					m = (v2.scaledY - v3.scaledY) / (v2.scaledX - v3.scaledX);
					b = v2.scaledY - m * v2.scaledX;
					if (y == m * x + b) { //Point lies along diagonal edge of v2 and v3.
						weight1 = Math.abs((v3.scaledX - x) / (v3.scaledX - v2.scaledX));
						//return v2.z * weight1 + v3.z * (1 - weight1);
						return uv2.multiply(weight1).add(uv3.multiply(1 - weight1));
					} else { //Point lies fully within the triangle.
						float dx1 = Math.abs(x - v1.scaledX),
							  dy1 = Math.abs(y - v1.scaledY),
							  dx2 = Math.abs(x - v2.scaledX),
							  dy2 = Math.abs(y - v2.scaledY),
							  dx3 = Math.abs(x - v3.scaledX),
							  dy3 = Math.abs(y - v3.scaledY),
							  weight2 = Math.abs(dy1 * dx3 - dx1 * dy3),
							  weight3 = Math.abs(dy1 * dx2 - dx1 * dy2);
						weight1 = Math.abs(dy2 * dx3 - dx2 * dy3);
					    float total = weight1 + weight2 + weight3;
						return new Vector(1.0f / (weight1 / uv1.x / total + weight2 / uv2.x / total + weight3 / uv3.x / total), 
									      1.0f / (weight1 / uv1.y / total + weight2 / uv2.y / total + weight3 / uv3.y / total), 1);
					}
				}
			}
		}
	}
	
}
