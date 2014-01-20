package MazeRunner.Opponents;

/**
 * Deze klasse wordt gebruikt om de camera uit en aan te zetten.
 * 
 * @author Stijn
 * 
 */

public class ThreadLoop extends Thread {
	public boolean visible = true;
	private long alarmTime = (long) (400);
	private boolean offset = true;
	private long offsetSleepTime = (long) (Math.random() * 1000);
	private boolean fullRandom = true;
	private final Object lockObj = new Object();

	public static void main(String[] args) {
		ThreadLoop T = new ThreadLoop();
		T.start();
	}

	public void setAlarmOn(){
		synchronized(lockObj){
			fullRandom = false;
			lockObj.notify();
		}
	}
	
	public void setAlarmOff(){
		synchronized(lockObj){
			fullRandom = true;
			lockObj.notify();
		}
	}
	
	public void stopSleeping(){
		//Thread.interrupted();
		Thread.currentThread().interrupt();
	}

	public void run() {
		while(true){
			synchronized(lockObj){
				try {
					if (offset) {
						lockObj.wait(offsetSleepTime);
						//Thread.sleep();
						offset = false;
					}
					
					if(fullRandom){
						lockObj.wait((long)(Math.random() * 8000));
						//Thread.sleep((long)(Math.random() * 8000));
					}
					else{
						lockObj.wait(alarmTime);
						//Thread.sleep(alarmTime);
					}
					
					// System.out.println(visible + " -> " + !visible);
					visible = !visible;
					}
						
					//run();
				  catch (InterruptedException e) {
					e.printStackTrace();
				  }
			}
		}
	}
}
