package MazeRunner;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.sun.opengl.util.ImageUtil;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

/**
 * Deze class maakt alle textures aan en slaat deze op.
 * 
 * @author Huib
 * 
 */
public class LoadTexturesMaze {

	private Texture defaultTexture;

	private ArrayList<Texture> textureList = new ArrayList<Texture>();
	private ArrayList<String> textureListNames = new ArrayList<String>();
	
	private ArrayList<Model> modelList = new ArrayList<Model>();
	private ArrayList<String> modelListNames = new ArrayList<String>();

	/**
	 * Hier maak je alle textures aan en zet je ze in een arraylijst met
	 * textures. Daarnaast geef je de textures ook een naam en zet je deze in de
	 * texturenaamlijst. Hiermee kunnen we de goede texture bij de goede naam
	 * halen in de getter
	 */
	public LoadTexturesMaze() {
		loadTextures();
		loadModels();
	}
	
	public void loadTextures () {
		defaultTexture = loadingTexture("default.png");
		addTextureToList(loadingTexture("wallTexture.png"), "wallTexture");
		addTextureToList(loadingTexture("floorTexture.png"), "floorTexture");
		addTextureToList(loadingTexture("doorMazerunner.png"), "doorTexture");
		addTextureToList(loadingTexture("MazerunnerGuardTexture.tga"), "doorBottomTexture");
//		addTextureToList(loadingTexture("doorMazerunBottom.png"), "doorBottomTexture");
//		addTextureToList(loadingTexture("MazerunnerGuardTexture.tga"), "guardTexture");
	}
	
	public void loadModels () {
//		addModelToList(OBJLoader.loadModel("src/modelGuard.obj", getTexture("guardTexture")), "modelGuard");
	}

	/**
	 * Laad een plaatje in een bufferedImage reader and schrijft deze dan uit
	 * naar een Texture
	 * 
	 * @param fileName
	 * @return
	 */
	public Texture loadingTexture(String fileName) {
		// Nieuwe texture aanmaken
		Texture tempTexture;

		try {
			// Texture ophalen vanuit een image
			URL textureURL;
			textureURL = getClass().getClassLoader().getResource(fileName);
			// read file into BufferedImage
			BufferedImage texture = ImageIO.read(textureURL);
//			ImageUtil.flipImageVertically(texture);
			tempTexture = TextureIO.newTexture(texture, true);
		} catch (IOException e) {
			// Wanneer de source niet gevonden is
			System.out.println("De file " + fileName + " is niet gevonden");
			e.printStackTrace();
			tempTexture = defaultTexture;
		} catch (IllegalArgumentException k) {
			System.out
					.println(fileName + " is null, default texture ingeladen");
			tempTexture = defaultTexture;
		}
		return tempTexture;
	}

	/**
	 * Een getter voor de Textures. Deze is gelinkt aan een String "TextureNaam"
	 * die hierboven wordt opgegeven. Hij zoekt door de twee arraylijsten om de
	 * goede texture te vinden.
	 * 
	 * @param textureName
	 * @return
	 */
	public Texture getTexture(String textureName) {
		for (int i = 0; i < textureListNames.size(); i++) {
			if (textureListNames.get(i).equals(textureName)) {
				if (!(textureList.get(i) == null)) {
					return textureList.get(i);
				}
			}
		}
		return defaultTexture;
	}
	
	public Model getModel(String modelName) {
		for (int i = 0; i < modelListNames.size(); i++) {
			if (modelListNames.get(i).equals(modelName)) {
				if (!(modelList.get(i) == null)) {
					return modelList.get(i);
				}
			}
		}
		return null;
	}

	/**
	 * Een methode om de texture op een simpele manier toe te voegen aan de
	 * arraylijsten. Anders werd dit 3/4 regels per inladen van een texture. Nu
	 * is het maar één regel
	 * 
	 * @param myTexture
	 * @param textureName
	 */
	public void addTextureToList(Texture myTexture, String textureName) {
		textureList.add(myTexture);
		textureListNames.add(textureName);
	}
	
	public void addModelToList(Model myModel, String modelName) {
		modelList.add(myModel);
		modelListNames.add(modelName);
	}
}
