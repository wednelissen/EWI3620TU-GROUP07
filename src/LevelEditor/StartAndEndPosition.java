package LevelEditor;

import java.awt.Point;

public class StartAndEndPosition {

	private Point start;
	private Point end;
	private boolean setStart = false;
	private boolean setEnd = false;

	/**
	 * maakt twee legen punten, 1 voor het begin punt en 1 voor het eind punt.
	 */
	public StartAndEndPosition() {
		start = new Point();
		end = new Point();
	}

	/**
	 * geeft start een punt en maakt 'setStart' true
	 * 
	 * @param a
	 *            Point
	 */
	public void setStart(Point a) {
		start = a;
		setStart = true;
	}

	/**
	 * geeft end een punt en maakt 'setEnd' true
	 * 
	 * @param a
	 *            Point
	 */
	public void setEnd(Point a) {
		end = a;
		setEnd = true;
	}

	/**
	 * 
	 * @return true indien er een start positie is geset.
	 */
	public boolean hasStart() {
		return setStart;
	}

	/**
	 * 
	 * @return true indien er een eind positie is geset.
	 */
	public boolean hasEnd() {
		return setEnd;
	}

	/**
	 * 
	 * @return het start punt.
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * 
	 * @return het eind punt.
	 */
	public Point getEnd() {
		return end;
	}

}
