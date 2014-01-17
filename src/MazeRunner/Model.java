package MazeRunner;

import java.util.ArrayList;

import javax.media.opengl.GL;
import com.sun.opengl.util.texture.Texture;

public class Model {
	private ArrayList<ArrayList<Float>> vertices = new ArrayList<ArrayList<Float>>();
	private ArrayList<ArrayList<Float>> normals = new ArrayList<ArrayList<Float>>();
	private ArrayList<ArrayList<Float>> textures = new ArrayList<ArrayList<Float>>();
	private ArrayList<Face> faces = new ArrayList<Face>();

	private Texture myTexture;

	public void addVertice(float x, float y, float z) {
		ArrayList<Float> verticeCoords = new ArrayList<Float>();
		verticeCoords.add(x);
		verticeCoords.add(y);
		verticeCoords.add(z);
		vertices.add(verticeCoords);
	}

	public void addNormal(float x, float y, float z) {
		ArrayList<Float> normalCoords = new ArrayList<Float>();
		normalCoords.add(x);
		normalCoords.add(y);
		normalCoords.add(z);
		normals.add(normalCoords);
	}

	public void addTexCoords(float x, float y) {
		ArrayList<Float> textureCoords = new ArrayList<Float>();
		textureCoords.add(x);
		textureCoords.add(y);
		textures.add(textureCoords);
	}

	public void addFace(Face face) {
		faces.add(face);
	}

	public void draw(GL gl) {
		gl.glBegin(GL.GL_TRIANGLES);
		for (Face face : faces) {
			if (myTexture != null) {
				myTexture.enable();
				myTexture.bind();
			}
			ArrayList<Integer> vertexIndices = face.getVertexIndices();
			ArrayList<Integer> normalIndices = face.getNormalIndices();
			ArrayList<Integer> textureIndices = face.getTextureIndices();
			gl.glNormal3f(normals.get((normalIndices.get(0)) - 1).get(0),
					normals.get((normalIndices.get(0)) - 1).get(1), normals
							.get((normalIndices.get(0)) - 1).get(2));
			gl.glTexCoord2d(textures.get((textureIndices.get(0)) - 1).get(0),
					textures.get((textureIndices.get(0)) - 1).get(1));
			gl.glVertex3d(vertices.get((vertexIndices.get(0)) - 1).get(0),
					vertices.get((vertexIndices.get(0)) - 1).get(1), vertices
							.get((vertexIndices.get(0)) - 1).get(2));

			gl.glNormal3f(normals.get((normalIndices.get(1)) - 1).get(0),
					normals.get((normalIndices.get(1)) - 1).get(1), normals
							.get((normalIndices.get(1)) - 1).get(2));
			gl.glTexCoord2d(textures.get((textureIndices.get(1)) - 1).get(0),
					textures.get((textureIndices.get(1)) - 1).get(1));
			gl.glVertex3d(vertices.get((vertexIndices.get(1)) - 1).get(0),
					vertices.get((vertexIndices.get(1)) - 1).get(1), vertices
							.get((vertexIndices.get(1)) - 1).get(2));

			gl.glNormal3f(normals.get((normalIndices.get(2)) - 1).get(0),
					normals.get((normalIndices.get(2)) - 1).get(1), normals
							.get((normalIndices.get(2)) - 1).get(2));
			gl.glTexCoord2d(textures.get((textureIndices.get(2)) - 1).get(0),
					textures.get((textureIndices.get(2)) - 1).get(1));
			gl.glVertex3d(vertices.get((vertexIndices.get(2)) - 1).get(0),
					vertices.get((vertexIndices.get(2)) - 1).get(1), vertices
							.get((vertexIndices.get(2)) - 1).get(2));
		}
		gl.glEnd();
		if (myTexture != null) {
			myTexture.disable();
		}
	}
	
	public void setTexture (Texture myTexture) {
		myTexture = this.myTexture;
	}
}
