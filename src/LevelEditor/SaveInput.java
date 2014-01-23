package LevelEditor;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SaveInput {
	private MapMenu map;
	private String[] floorPlan;

	private PlacedItemsMenu placedItems;
	private String[] guardsPlan;
	private String[] keysPlan;

	private StartAndEndPosition StartAndEnd;
	private String StartPosition;
	private String EndPosition;

	private SpotList spotList;
	private String[] spots;

	private CameraList cameraList;
	private String[] cameras;

	private ControlCenterList controlCenterList;
	private String[] controlCenters;

	/**
	 * alle objecten meegeven waarmee je een level maakt. deze worden vertaald
	 * naar strings en vervolgens opgeslagen naar een tekstbestand met de naam
	 * van de ingegeven param 'LevelName'
	 * 
	 * @param tempMap
	 * @param tempPlacedItems
	 * @param tempStartAndEnd
	 * @param tempSpotList
	 * @param tempCameraList
	 * @param LevelName
	 */
	public SaveInput(MapMenu tempMap, PlacedItemsMenu tempPlacedItems,
			StartAndEndPosition tempStartAndEnd, SpotList tempSpotList,
			CameraList tempCameraList, ControlCenterList tempControlCenterList,
			String LevelName) {
		map = tempMap;
		placedItems = tempPlacedItems;
		StartAndEnd = tempStartAndEnd;
		spotList = tempSpotList;
		cameraList = tempCameraList;
		controlCenterList = tempControlCenterList;

		floorPlan = new String[map.getHeight()];
		guardsPlan = new String[placedItems.guardSize()];
		keysPlan = new String[placedItems.keySize()];
		StartPosition = new String();
		EndPosition = new String();
		spots = new String[spotList.size()];
		cameras = new String[cameraList.size()];
		controlCenters = new String[controlCenterList.size()];

		this.floorPlanMaze(); // de map wordt vertaald naar enen en nullen
		this.GuardsPlan(); // de bewakers worden weggeschreven
		this.KeysPlan(); // de keys worden weggeschreven
		this.StartAndEndPosition(); // begin en eindpositie worden naar string
									// vertaald.
		this.SpotsPlan(); // spotjes worden naar string vertaald
		this.CamerasPlan(); // camera's worden naar string vertaald
		this.ControlCenterPlan(); // Control Centers worden naar een string
									// vertaald
		if (StartAndEnd.hasStart() && StartAndEnd.hasEnd()) {
			this.write(LevelName); // de map wordt weggeschereven naar een
									// bestand.
		} else
			System.out.println("er zijn nog geen begin en eindpunt geset.");
	}

	/**
	 * zorgt dat iedere BuildingBlock wordt nagelopen en zorgt dat een vloer een
	 * 0 wordt. een muur een 1 wordt en een deur een 2 wordt.
	 */
	public void floorPlanMaze() {
		int TotalBuildingBlockX = map.getWidth();
		int TotalBuildingBlockY = map.getHeight();
		BuildingBlock[][] BuildingBlocks = map.getAllBuildingBlocks();

		for (int j = 0; j < TotalBuildingBlockY; j++) {
			floorPlan[j] = "";
			for (int i = 0; i < TotalBuildingBlockX; i++) {
				if (BuildingBlocks[i][j].getFloor()) {
					floorPlan[j] = floorPlan[j] + "0";
				} else if (BuildingBlocks[i][j].getWall()) {
					floorPlan[j] = floorPlan[j] + "1";
				} else if (BuildingBlocks[i][j].getDoor()) {
					floorPlan[j] = floorPlan[j] + "2";
				} else
					System.out
							.println("Er is geen wall,floor of door geset??? niet mogelijk");

			}
		}
	}

	/**
	 * de Start en Eind Positie worden naar String vertaald als een Punt bv:
	 * x,y;
	 */
	public void StartAndEndPosition() {
		Point start = StartAndEnd.getStart();
		Point end = StartAndEnd.getEnd();

		StartPosition = StartPosition + (int) start.getX() + ","
				+ (int) start.getY() + ";";
		EndPosition = EndPosition + (int) end.getX() + "," + (int) end.getY()
				+ ";";

		// testen van de output
		System.out.println("start: " + StartPosition + " eind: " + EndPosition);
	}

	/**
	 * iedere Guardian die is opgeslagen wordt de route vertaald naar String met
	 * scheiding van semicolum tussen de punten.
	 */
	public void GuardsPlan() {
		ArrayList<Guardian> guards = placedItems.getAllGuards();
		for (int i = 0; i < placedItems.guardSize(); i++) {
			Guardian g = guards.get(i);
			String temp = "";
			for (int j = 0; j < g.routeSize(); j++) {
				Point p = g.getRoute(j);
				int a = (int) p.getX();
				int b = (int) p.getY();
				temp = temp + a + "," + b + ";";
			}
			guardsPlan[i] = temp;

		}

		// hier wordt de output getest.
		System.out.println("Guards routes");
		for (int i = 0; i < guardsPlan.length; i++) {
			System.out.println(guardsPlan[i]);
		}
	}

	/**
	 * alle opgeslagen Key worden vertaald naar string. eerst wordt de positie
	 * van de sleutel vertaald en daar achter komt de positie van de Deur.
	 */
	public void KeysPlan() {
		ArrayList<Key> keys = placedItems.getAllKeys();
		for (int i = 0; i < placedItems.keySize(); i++) {
			Key k = keys.get(i);
			String temp = "";
			// punt waar de sleutel ligt
			Point p = k.getKey();
			int a = (int) p.getX();
			int b = (int) p.getY();
			temp = temp + a + "," + b + ";";

			// punt waar de door zich bevind
			Point q = k.getDoor();
			int c = (int) q.getX();
			int d = (int) q.getY();
			temp = temp + c + "," + d + ";";

			keysPlan[i] = temp;
		}

		// hier wordt de output getest.
		System.out.println("Keys met deuren");
		for (int i = 0; i < keysPlan.length; i++) {
			System.out.println(keysPlan[i]);
		}
	}

	/**
	 * zet alle spots om naar punten zoals x,y;
	 */
	public void SpotsPlan() {
		int i = 0;
		for (Spot s : spotList.getSpots()) {
			Point a = s.getPosition();
			int x = (int) a.getX();
			int y = (int) a.getY();
			spots[i] = x + "," + y + ";";
			i++;
		}

		// DEBUG VOOR DE SPOTS
		for (int j = 0; j < spots.length; j++) {
			System.out.println(spots[j]);
		}

	}

	/**
	 * zet alle camera's om naar punten zoals x,y;
	 */
	public void CamerasPlan() {
		int i = 0;
		for (Camera s : cameraList.getCameras()) {
			Point a = s.getPosition();
			int x = (int) a.getX();
			int y = (int) a.getY();
			cameras[i] = x + "," + y + ";";
			i++;
		}

		// DEBUG VOOR DE CAMERA'S
		for (int j = 0; j < cameras.length; j++) {
			System.out.println(cameras[j]);
		}

	}

	/**
	 * zet alle Control Centers om naar punten zoals x,y;
	 */
	public void ControlCenterPlan() {
		int i = 0;
		for (ControlCenterEditor s : controlCenterList.getControlCenters()) {
			Point a = s.getPosition();
			int x = (int) a.getX();
			int y = (int) a.getY();
			controlCenters[i] = x + "," + y + ";";
			i++;
		}

		// DEBUG VOOR DE ControlCenters
		for (int j = 0; j < controlCenters.length; j++) {
			System.out.println(controlCenters[j]);
		}

	}

	/**
	 * hier worden alle bovenstaande gecreerde Strings weggeschreven naar een
	 * bestand met de naam 'name'. de eerste regel wordt een ID weggeschreven.
	 * bij het laden wordt gecontrolleerd of deze gelijk is aan de hier
	 * weggeschreven ID zodat het programma weet dat het om een geldig level
	 * editor bestand gaat.
	 * 
	 * @param name
	 *            naam van het bestand dat wordt opgeslagen
	 */
	public void write(String name) {
		try {
			// build files
			// File NNOut = new File(NNOutS);
			File outputF = new File(name);

			// make scanner
			// Scanner sc = new Scanner(NNOut);

			// make file and printwriter
			FileWriter fw = new FileWriter(outputF);
			PrintWriter pw = new PrintWriter(fw);

			// build solution
			// geeft een ID om ter controllen dat het een bestand is dat kan
			// ingeladen worden.
			pw.println("a5ir783n!f78gds3b?54sdfg>sdfg549fd#sh");

			// de Maze hoogte en breedte wordt weggescheven
			pw.println(map.getWidth());
			pw.println(map.getHeight());
			// de maze word weggeschreven
			for (int i = 0; i < map.getHeight(); i++) {
				pw.println(floorPlan[i]);
			}
			pw.println("Begin punt:");
			pw.println(StartPosition);
			pw.println("Eind punt:");
			pw.println(EndPosition);

			pw.println("spots:");
			pw.println(spots.length);
			for (int i = 0; i < spots.length; i++) {
				pw.println(spots[i]);
			}

			pw.println("cameras:");
			pw.println(cameras.length);
			for (int i = 0; i < cameras.length; i++) {
				pw.println(cameras[i]);
			}

			pw.println("controlCenter:");
			pw.println(controlCenters.length);
			for (int i = 0; i < controlCenters.length; i++) {
				pw.println(controlCenters[i]);
			}

			pw.println("Guards:");
			pw.println(guardsPlan.length);
			for (int i = 0; i < guardsPlan.length; i++) {
				pw.println(guardsPlan[i]);
			}

			pw.println("Keys:");
			pw.println(keysPlan.length);
			for (int i = 0; i < keysPlan.length; i++) {
				pw.println(keysPlan[i]);
			}

			// close all
			pw.close();
			fw.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
