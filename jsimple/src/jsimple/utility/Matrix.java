package jsimple.utility;

/* This class represents a 4x4 matrix.
 * Written by Ryan Munn on 2/4/22.
 * Reviewed by Ben on 2/4/22.
 */

public class Matrix {
	
	public float[][] matrix = new float[4][4];
	public static final Matrix IDENTITY = new Matrix(1, 0, 0, 0, //Identity matrix stored as a final for easy access
													 0, 1, 0, 0,
													 0, 0, 1, 0,
													 0, 0, 0, 1); 
	public static final Matrix PROJECTION = new Matrix(400, 0,   0, 0, //Projection matrix stored as final for easy access
			  										   0,   400, 0, 0, 
			  										   0,   0,   1, 0,
			  										   0,   0,   1, 0);
	
	public Matrix() {
		
		this.setEqualTo(IDENTITY);
		
	}
	
	public Matrix(float m00, float m01, float m02, float m03,
				   float m10, float m11, float m12, float m13,
				   float m20, float m21, float m22, float m23,
				   float m30, float m31, float m32, float m33) {
		
		this.setValue(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
		
	}
	
	public Matrix(Matrix matrix) {
		
		this.setEqualTo(matrix);
		
	}
	
	//Set the value of each element of this matrix.
	public void setValue(float m00, float m01, float m02, float m03,
			   float m10, float m11, float m12, float m13,
			   float m20, float m21, float m22, float m23,
			   float m30, float m31, float m32, float m33) {
	
		this.matrix[0][0] = m00;
		this.matrix[0][1] = m01;
		this.matrix[0][2] = m02;
		this.matrix[0][3] = m03;
	
		this.matrix[1][0] = m10;
		this.matrix[1][1] = m11;
		this.matrix[1][2] = m12;
		this.matrix[1][3] = m13;
	
		this.matrix[2][0] = m20;
		this.matrix[2][1] = m21;
		this.matrix[2][2] = m22;
		this.matrix[2][3] = m23;
	
		this.matrix[3][0] = m30;
		this.matrix[3][1] = m31;
		this.matrix[3][2] = m32;
		this.matrix[3][3] = m33;
	
	}
	
	//Set each element of this matrix equal to the corresponding element of m.
	public void setEqualTo(Matrix m) {
		
		this.setValue(m.matrix[0][0], m.matrix[0][1], m.matrix[0][2], m.matrix[0][3], 
					  m.matrix[1][0], m.matrix[1][1], m.matrix[1][2], m.matrix[1][3], 
					  m.matrix[2][0], m.matrix[2][1], m.matrix[2][2], m.matrix[2][3], 
					  m.matrix[3][0], m.matrix[3][1], m.matrix[3][2], m.matrix[3][3]);
		
	}
	
	//Return a new Matrix equal to the product between this and matrix2.
	public Matrix multiply(Matrix matrix2) {
		
		Matrix matrix1 = new Matrix();
		matrix1.setEqualTo(this);
		
		for (int r = 0; r < 4; r++) {
			
			for (int c = 0; c < 4; c++) {
				
				matrix1.matrix[r][c] = this.matrix[r][0] * matrix2.matrix[0][c] + this.matrix[r][1] * matrix2.matrix[1][c] 
									 + this.matrix[r][2] * matrix2.matrix[2][c] + this.matrix[r][3] * matrix2.matrix[3][c];

			}
			
		}
		
		return matrix1;

	}
	
	//Return a new Matrix for translating a given vector by dx in the x direction, dy in the y direction, and dz in the z direction.
	public static Matrix getTranslation(float dx, float dy, float dz) {
		
		Matrix matrix1 = new Matrix();
		matrix1.matrix[0][3] = dx;
		matrix1.matrix[1][3] = dy;
		matrix1.matrix[2][3] = dz;
		
		return matrix1;
		
	}
	
	//Return a new Matrix for translating a given vector by xTheta radians along the x-axis, and yTheta radians along the y-axis.
	public static Matrix getRotation(float xTheta, float yTheta, float zTheta) {
		
		float sinX = (float)Math.sin(xTheta),
			  cosX = (float)Math.cos(xTheta),
			  sinY = (float)Math.sin(yTheta),
			  cosY = (float)Math.cos(yTheta),
			  sinZ = (float)Math.sin(zTheta),
			  cosZ = (float)Math.cos(zTheta);
		
		Matrix rX = new Matrix(), rY = new Matrix(), rZ = new Matrix();
		
		rX.matrix[1][1] = cosX;
		rX.matrix[1][2] = -sinX;
		rX.matrix[2][1] = sinX;
		rX.matrix[2][2] = cosX;
		
		rY.matrix[0][0] = cosY;
		rY.matrix[0][2] = sinY;
		rY.matrix[2][0] = -sinY;
		rY.matrix[2][2] = cosY;
		
		rZ.matrix[0][0] = cosZ;
		rZ.matrix[0][1] = -sinZ;
		rZ.matrix[1][0] = sinZ;
		rZ.matrix[1][1] = cosZ;
		
		return rX.multiply(rY.multiply(rZ));
		
	}
	
}