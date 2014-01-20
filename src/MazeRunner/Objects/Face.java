package MazeRunner.Objects;

import java.util.ArrayList;

/**
 * An extra class to store all the faces of the model. This will help to keep
 * overview. It creates a face with 3 arraylists. One with the coords of the
 * right vertex, one with normals and one with the right texturecoords.
 * 
 * @author Huib
 * 
 */
public class Face {
	private ArrayList<Integer> vertexIndices = new ArrayList<Integer>();
	private ArrayList<Integer> normalIndices = new ArrayList<Integer>();
	private ArrayList<Integer> textureIndices = new ArrayList<Integer>();

	public Face(ArrayList<Integer> vertex, ArrayList<Integer> texture,
			ArrayList<Integer> normal) {
		this.vertexIndices = vertex;
		this.normalIndices = normal;
		this.textureIndices = texture;
	}

	public ArrayList<Integer> getVertexIndices() {
		return this.vertexIndices;
	}

	public ArrayList<Integer> getNormalIndices() {
		return this.normalIndices;
	}

	public ArrayList<Integer> getTextureIndices() {
		return this.textureIndices;
	}

}
