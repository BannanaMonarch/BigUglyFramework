import java.applet.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Audio {
	ArrayList<AudioClip> aud = new ArrayList<AudioClip>(); //Growable array of sounds.
	String dir = ""; //Directory of sound files.
	boolean mute=false; //Should the sound be muted?
	
	/* Sets the directory of the sound files. */
	public void setDirectory(String gDir){
		dir=gDir;
		System.out.println("Set sounds directory to "+dir);
	}
	
	/* Adds a sound from the given sound file. */
	public void addSound(Applet applet, String url){
			AudioClip temp = applet.getAudioClip(applet.getCodeBase(),dir+url);
			if(!url.contains(".wav")){System.err.println("This is a "+url.substring(url.length()-4,url.length())+"! Unless it is disguised, try using a .wav and .au");}
			aud.add(temp);
	}
	
	/* Pulls a sound file from the Internet. Privileges may prevent this action. */
	public void addSoundFromWeb(Applet applet, String url){
		AudioClip temp = null;
		try {
			temp = applet.getAudioClip(new URL(applet.getCodeBase(),url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return; //stop the addition of nullified object.
		}
		if(!url.contains(".wav")){System.err.println("Unless it is disguised, try using a .wav and .au");}
		aud.add(temp);
}
	/* Plays the sound at the given index. */
	public void playSound(int index){
		if(!mute){
		aud.get(index).play();
		System.out.println("Playing "+aud.get(index));
		}
	}
	
	/* Loops the sound at the given index. */
	public void loopSound(int index){
		if(!mute){
		aud.get(index).loop();
		System.out.println("Looping "+aud.get(index));
		}
	}
	
	/* Stop sound from index. Usually only used when a sound is looping. */
	public void stopSound(int index){
		aud.get(index).stop();
		System.out.println("Stopping "+aud.get(index));
	}
	
	/* Stops all sounds and prevents new sounds from playing or looping. */
	public void mute(){
		for(AudioClip a: aud){a.stop();}
		mute=true;
	}
	
	/* Allows new sounds to play or loop */
	public void unmute(){mute=false;}
}
