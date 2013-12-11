package Sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sound {



public static final AudioClip howYaDoin = Applet.newAudioClip(Sound.class.getResource("/how_ya_doin_x.wav"));
public static final AudioClip murphyLaugh = Applet.newAudioClip(Sound.class.getResource("/murphy_laugh.wav"));
	
	public static void main(String[] args){
		URL url = Sound.class.getResource("/murphy_laugh.wav");
//		System.out.println(url);
		howYaDoin.play();
		//murphyLaugh.loop();
		
	}
}
