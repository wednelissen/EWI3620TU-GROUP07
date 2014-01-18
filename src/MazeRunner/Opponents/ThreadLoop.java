package MazeRunner.Opponents;

/**
 * Deze klasse wordt gebruikt om de camera uit en aan te zetten.
 * 
 * @author Stijn
 * 
 */

public class ThreadLoop extends Thread {
	public boolean visible = true;
	private long sleepTime = (long) (Math.random() * 8000);
	private boolean offset = true;
	private long offsetSleepTime = (long) (Math.random() * 1000);

	public static void main(String[] args) {
		ThreadLoop T = new ThreadLoop();
		T.start();
	}

	public void setSleepTime(long time) {
		sleepTime = time;
	}

	public void run() {
		try {
			if (offset) {
				Thread.sleep(offsetSleepTime);
				offset = false;
			}

			Thread.sleep((long) (Math.random() * 8000));

			// System.out.println(visible + " -> " + !visible);
			visible = !visible;

			run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
