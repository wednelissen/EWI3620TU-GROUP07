package test;

import static org.junit.Assert.assertEquals;

import javax.media.opengl.GLCanvas;

import org.junit.Test;

import MazeRunner.Fundamental.GameDriver;
import MazeRunner.Fundamental.MazeRunner;
import MazeRunner.Opponents.GuardCamera;

public class GuardCameraTest {

	private GLCanvas canvas;
	private MazeRunner mazerunner = new MazeRunner(canvas);
	GameDriver gamedriver = new GameDriver();

	GuardCamera testcamera = new GuardCamera(3, 2, 3, mazerunner);

	@Test
	public void testResetAlarm() {
		testcamera.resetAlarm();
		assertEquals(testcamera.alarm(), false);
	}

}
