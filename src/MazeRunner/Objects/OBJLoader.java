package MazeRunner.Objects;

import java.io.*;
import java.util.ArrayList;



public class OBJLoader {

	public static Model loadModel(String fileName) {
		Model m = new Model();
		try {
		BufferedReader reader = new BufferedReader(new FileReader(new File(
				fileName)));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("v ")) {
				// Splits the line into 3 floats which will represent the
				// vertices of the part of the model
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				float z = Float.valueOf(line.split(" ")[3]);
				m.addVertice(x, y, z);
			} else if (line.startsWith("vn ")) {
				// Splits the line into 3 floats which will represent the
				// normals of the part of the model
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				float z = Float.valueOf(line.split(" ")[3]);
				m.addNormal(x, y, z);
			} else if (line.startsWith("vt ")) {
				// Splits the line into 2 floats which will represent the
				// texture coords of the part of the model
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				m.addTexCoords(x, y);
			} else if (line.startsWith("f ")) {
				// De plek in de arraylijsten waar de vertex wordt aangegeven
				ArrayList<Integer> vertexIndices = new ArrayList<Integer>();
				int x1 = Integer.valueOf(line.split(" ")[1].split("/")[0]);
				vertexIndices.add(x1);
				int y1 = Integer.valueOf(line.split(" ")[2].split("/")[0]);
				vertexIndices.add(y1);
				int z1 = Integer.valueOf(line.split(" ")[3].split("/")[0]);
				vertexIndices.add(z1);

				// De plek in de arraylijsten waar de texture wordt aangegeven
				ArrayList<Integer> textureIndices = new ArrayList<Integer>();
				int x2 = Integer.valueOf(line.split(" ")[1].split("/")[1]);
				textureIndices.add(x2);
				int y2 = Integer.valueOf(line.split(" ")[2].split("/")[1]);
				textureIndices.add(y2);
				int z2 = Integer.valueOf(line.split(" ")[3].split("/")[1]);
				textureIndices.add(z2);

				// De plek in de arraylijsten waar de normals wordt aangegeven
				ArrayList<Integer> normalIndices = new ArrayList<Integer>();
				int x3 = Integer.valueOf(line.split(" ")[1].split("/")[2]);
				normalIndices.add(x3);
				int y3 = Integer.valueOf(line.split(" ")[2].split("/")[2]);
				normalIndices.add(y3);
				int z3 = Integer.valueOf(line.split(" ")[3].split("/")[2]);
				normalIndices.add(z3);

				m.addFace(new Face(vertexIndices, textureIndices, normalIndices));
			}
		}
		reader.close();
		
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Loading model failed");
			m = null;
		}
		return m;	
	}
}
