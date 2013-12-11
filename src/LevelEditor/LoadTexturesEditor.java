package LevelEditor;

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
public class LoadTexturesEditor {

	private Texture defaultTexture;

	private ArrayList<Texture> textureList = new ArrayList<Texture>();
	private ArrayList<String> textureListNames = new ArrayList<String>();

	/**
	 * Hier maak je alle textures aan en zet je ze in een arraylijst met
	 * textures. Daarnaast geef je de textures ook een naam en zet je deze in de
	 * texturenaamlijst. Hiermee kunnen we de goede texture bij de goede naam
	 * halen in de getter
	 */
	public LoadTexturesEditor() {
		defaultTexture = loadingTexture("default.png");
		addTextureToList(loadingTexture("wallEditor.png"), "wallEditor");
		addTextureToList(loadingTexture("floorEditor.png"), "floorEditor");
		addTextureToList(loadingTexture("doorEditor.png"), "doorEditor");
		addTextureToList(loadingTexture("guardianStepsBlue.png"),
				"guardianStepsBlue");
		addTextureToList(loadingTexture("guardianStepsRed.png"),
				"guardianStepsRed");
		addTextureToList(loadingTexture("cameraEditor.png"), "cameraEditor");
		addTextureToList(loadingTexture("spotEditor.png"), "spotEditor");
		addTextureToList(loadingTexture("startPos.png"), "startPos");
		addTextureToList(loadingTexture("endPos.png"), "endPos");
		addTextureToList(loadingTexture("addButton.png"), "addButton");
		addTextureToList(loadingTexture("removeButton.png"), "removeButton");
		addTextureToList(loadingTexture("removeLastPointGuardButton.png"),
				"removeLastPointGuardButton");
		addTextureToList(loadingTexture("showAllGuardsButton.png"),
				"showAllGuardsButton");
		addTextureToList(loadingTexture("setStartButton.png"), "setStartButton");
		addTextureToList(loadingTexture("setEndButton.png"), "setEndButton");
		addTextureToList(loadingTexture("setWidthButton.png"), "setWidthButton");
		addTextureToList(loadingTexture("saveButton.png"), "saveButton");
		addTextureToList(loadingTexture("loadButton.png"), "loadButton");
		addTextureToList(loadingTexture("guardianEditor.png"), "guardianEditor");
		addTextureToList(loadingTexture("keyEditor.png"), "keyEditor");
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
			ImageUtil.flipImageVertically(texture);
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
}
