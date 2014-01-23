package LevelEditor;

import java.awt.Point;

public class Key extends Window {

	private Point keyPosition;
	private Point doorPosition;
	private boolean setKey;
	private boolean setDoor;

	/**
	 * Hier wordt een key gemaakt maar de positie die wordt opgegeven is nog
	 * niet belangrijk deze wordt geautomatiseerd goed geset doordat de functie
	 * updatePositie wordt aangeroepen, door de classe PlacedItems.
	 * 
	 * @param sizes
	 *            een array van float met 4 coordinaten. linksboven,
	 *            rechtsboven, breedte, hoogte.
	 * @param screenWidthFrame
	 * @param screenHeightFrame
	 */
	public Key(float[] sizes, int screenWidthFrame, int screenHeightFrame) {
		super(sizes, screenWidthFrame, screenHeightFrame);
		keyPosition = new Point();
		doorPosition = new Point();
		setKey = false;
		setDoor = false;
	}

	/**
	 * Hier wordt de werkelijke positie van een Key geset, wanneer hij in het
	 * placedItems menu is geplaatst.
	 * 
	 * @param sizes
	 *            Een floatarray waar de positie en grootte van de knop in
	 *            staat.
	 * @param screenWidthFrame
	 *            De breedte van het scherm
	 * @param screenHeightFrame
	 *            De hoogte van het scherm
	 */
	public void updatePosition(float[] sizes, int screenWidthFrame,
			int screenHeightFrame) {
		if (sizes.length == 4) {

			originalSizes = sizes;
			float x_linksOnder = sizes[0];
			float y_rechtsOnder = sizes[1];
			float buttonSizeX = sizes[2];
			float buttonSizeY = sizes[3];

			screenWidth = screenWidthFrame;
			screenHeight = screenHeightFrame;

			x = x_linksOnder / 800 * screenWidth;
			y = (600 - y_rechtsOnder) / 600 * screenHeight;

			sizeX = buttonSizeX / 800 * screenWidth;
			sizeY = buttonSizeY / 600 * screenHeight;
		} else {
			System.out.println("geen geldige lengte voor input");
		}

	}

	/**
	 * Set de positie waar de sleutel staat en set 'setKey' op true
	 * 
	 * @param a
	 *            Het coöordinaat (x, z) waar de key moet worden geset
	 */
	public void setKey(Point a) {
		keyPosition = a;
		setKey = true;
	}

	/**
	 * Verwijderd de positie waar de sleutel staat en set 'setKey' op false
	 */
	public void removeKey() {
		keyPosition = null;
		setKey = false;
	}

	/**
	 * Geeft de positie van de sleutel terug indien deze is geset, returnt
	 * anders null
	 * 
	 * @return Een punt van de positie van de key (x, z)
	 */
	public Point getKey() {
		if (setKey) {
			return keyPosition;
		} else
			return null;
	}

	/**
	 * Een get functie die kijkt of er een sleutel ligt op de positie
	 * 
	 * @return De boolean setKey
	 */
	public boolean hasPosition() {
		return setKey;
	}

	/**
	 * Set de positie van de deur die bij de sleutel hoort en maakt 'setDoor'
	 * true
	 * 
	 * @param a
	 *            Het coöordinaat (x, z) waar de deur moet worden geset
	 */
	public void setDoor(Point a) {
		doorPosition = a;
		setDoor = true;
	}

	/**
	 * Verwijderd de positie van de deur die bij de sleutel hoort en maakt
	 * 'setDoor' false De positie van de deur wordt null
	 */
	public void removeDoor() {
		doorPosition = null;
		setDoor = false;
	}

	/**
	 * De get functie die het punt terug geeft van de deur op een bepaald punt
	 * 
	 * @return Het coöordinaat (x, z) van de deur anders null
	 */
	public Point getDoor() {
		if (setDoor) {
			return doorPosition;
		} else
			return null;
	}

	/**
	 * Return true indien de deur positie is geset
	 * 
	 * @return Een boolean als de positie is geset
	 */
	boolean hasDoor() {
		return setDoor;
	}

}
