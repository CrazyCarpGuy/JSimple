package jsimple.object;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import jsimple.render.Triangle;
import jsimple.utility.StringUtils;
import jsimple.utility.Vector;

/* This class contains information about the triangles which compose a 3D model.
 * Written by Ryan Munn on 4/13/22
 */

public class Model {
	
	private GameObject parent;
	
	private ArrayList<Triangle> triangles;
	private boolean needsUpdate = false;
	private String shapeFile;
	private BufferedImage texture;
	private Vector scale;
	private Color color;
	
	
	public Model() {
		
		triangles = new ArrayList<Triangle>();
		
		this.setShape("models/cube.obj");
		this.setColor(Color.white);
		this.setScale(1, 1, 1);
		
	}
	
	public void setParent(GameObject gameObject) {
		this.parent = gameObject;
	}

	public void setShape(String shapeFile) {
		this.shapeFile = shapeFile;
		this.needsUpdate = true;
	}
	
	public void setColor(Color color) {
		this.color = color;
		this.texture = null;
		this.needsUpdate = true;
	}
	
	public void setColor(int r, int g, int b) {
		this.color = new Color(r, g, b);
		this.texture = null;
		this.needsUpdate = true;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setTexture(String textureFile) {
		try {
			texture = ImageIO.read(new File(textureFile));
			this.color = null;
			this.needsUpdate = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getTexture() {
		return this.texture;
	}
	
	public void setScale(Vector scale) {
		this.scale = scale;
		this.needsUpdate = true;
	}
	
	public void setScale (float x, float y, float z) {
		this.scale = new Vector(x, y, z);
		this.needsUpdate = true;
	}

	public void setXScale(float x) {
		this.scale.x = x;
	}
	
	public void setYScale(float y) {
		this.scale.y = y;
	}
	
	public void setZScale(float z) {
		this.scale.z = z;
	}
	
	public Vector getScale() {
		return this.scale;
	}
	
	public ArrayList<Triangle> getTriangles() {
		
		return triangles;
		
	}
	
	private void loadModelFromFile(String path) {
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
			
			ArrayList<Vector> vertices = new ArrayList<Vector>(), textures = new ArrayList<Vector>(), normals = new ArrayList<Vector>();
			String line = bufferedReader.readLine(); //Read the next line of the file
			String[] tokens;
			
			while (line != null) {
				
				tokens = line.split("\\s+");
				
				switch (tokens[0]) {
					
					case "v": //Add a vertex coordinate
						vertices.add(new Vector(Float.parseFloat(tokens[1]) * scale.x, Float.parseFloat(tokens[2]) * scale.y, Float.parseFloat(tokens[3]) * scale.z));
						break;
						
					case "vt": //Add a texture coordinate
						textures.add(new Vector(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), 1));
						break;
						
					case "vn": //Add a normal vector
						normals.add(new Vector(Float.parseFloat(tokens[1]) * scale.x, Float.parseFloat(tokens[2]) * scale.y, Float.parseFloat(tokens[3]) * scale.z));
						break;
						
					case "f": //Create a face
						
						Vector v1, v2, v3, uv1, uv2, uv3, normal;
						boolean hasNormal = StringUtils.hasNormal(tokens[1]), hasTexture = StringUtils.hasTexture(tokens[1]);
						
						for (int i = 3; i < tokens.length; i++) { // Iterate through all vertices on the face (may be 3 for triangle, 4 for quad, etc...)
							
							//Get the vertex coordinates for this triangle
							if (!hasNormal && !hasTexture) { //No forward slash delimiters
								//Parse the tokens directly as vertex coordinates
								v1 = vertices.get(Integer.parseInt(tokens[1]) - 1);
								v2 = vertices.get(Integer.parseInt(tokens[i - 1]) - 1);
								v3 = vertices.get(Integer.parseInt(tokens[i]) - 1);
							} else { 
								//Parse up to the first forward slash delimiter as vertex coordinates
								v1 = vertices.get(Integer.parseInt(tokens[1].substring(0, tokens[1].indexOf("/"))) - 1);
								v2 = vertices.get(Integer.parseInt(tokens[i - 1].substring(0, tokens[i - 1].indexOf("/"))) - 1);
								v3 = vertices.get(Integer.parseInt(tokens[i].substring(0, tokens[i].indexOf("/"))) - 1);
							}
							
							//Get the normal vector for this triangle
							if (hasNormal) { //Check if the normal vector is defined in the .obj file
								// Use the normal vector defined in the .obj file
								normal = normals.get(Integer.parseInt(tokens[1].substring(tokens[1].lastIndexOf("/") + 1)) - 1).getUnit();
							} else {
								// Calculate the normal vector via a cross product
								normal =  v2.subtract(v1).cross(v3.subtract(v1)).getUnit();
							} 

							//Get the texture coordinates for this triangle
							if (StringUtils.hasTexture(tokens[1])) { //Check if texture coordinates are defined
								
								uv1 = textures.get(Integer.parseInt(tokens[1].split("/")[1]) - 1);
								uv2 = textures.get(Integer.parseInt(tokens[i - 1].split("/")[1]) - 1);
								uv3 = textures.get(Integer.parseInt(tokens[i].split("/")[1]) - 1);
								triangles.add(new Triangle(v1, v2, v3, uv1, uv2, uv3, normal, this.parent));
							} else {
								//Add the triangle with the specified properties to the model
								triangles.add(new Triangle(v1, v2, v3, normal, this.parent));
							}
						
						}
						
						break;
					
				}
				
				//Read the next line of the file
				line = bufferedReader.readLine();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(float dt) {
		
		if (needsUpdate) {
			loadModelFromFile(shapeFile);
		}
		
		needsUpdate = false;
			
	}
}