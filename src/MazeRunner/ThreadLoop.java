package MazeRunner;

public class ThreadLoop extends Thread {
	public boolean visible = true;

	public static void main(String[] args) {
		ThreadLoop T = new ThreadLoop();
		T.start();
	}

	public void run() {
		try {
			Thread.sleep(2000);

//			System.out.println(visible + " -> " + !visible);
			visible = !visible;

			run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
