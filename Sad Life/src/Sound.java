import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Sound {
	private Long currentFrame;
	Clip clip;
	String status;
	AudioInputStream audioInputStream;
	String filePath = "src\\backgroundMusic.wav";
	
	public Sound() throws UnsupportedAudioFileException, IOException, LineUnavailableException  
	    { 
	        // create AudioInputStream object 
	        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
	          
	        // create clip reference 
	        clip = AudioSystem.getClip(); 
	          
	        // open audioInputStream to the clip 
	        clip.open(audioInputStream); 
	          
	        clip.loop(Clip.LOOP_CONTINUOUSLY); 
	    } 
	    
	public void volumeControl() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-Global.Volume); // Reduce volume by 10 decibels.
	}
	
	    // Method to play the audio 
	    public void play()  
	    { 
	        //start the clip 
	        clip.start(); 
	          
	        status = "play"; 
	    } 
	      
	    // Method to pause the audio 
	    public void pause()  
	    { 
	        if (status.equals("paused"))  
	        { 
	            System.out.println("audio is already paused"); 
	            return; 
	        } 
	        this.currentFrame =  
	        this.clip.getMicrosecondPosition(); 
	        clip.stop(); 
	        status = "paused"; 
	    } 
	      
	    // Method to resume the audio 
	    public void resumeAudio() throws UnsupportedAudioFileException, 
	                                IOException, LineUnavailableException  
	    { 
	        if (status.equals("play"))  
	        { 
	            System.out.println("Audio is already "+ 
	            "being played"); 
	            return; 
	        } 
	        clip.close(); 
	        resetAudioStream(); 
	        clip.setMicrosecondPosition(currentFrame); 
	        this.play(); 
	    } 
	      
	    // Method to restart the audio 
	    public void restart() throws IOException, LineUnavailableException, 
	                                            UnsupportedAudioFileException  
	    { 
	        clip.stop(); 
	        clip.close(); 
	        resetAudioStream(); 
	        currentFrame = 0L; 
	        clip.setMicrosecondPosition(0); 
	        this.play(); 
	    } 
	      
	    // Method to stop the audio 
	    public void stop() throws UnsupportedAudioFileException, 
	    IOException, LineUnavailableException  
	    { 
	        currentFrame = 0L; 
	        clip.stop(); 
	        clip.close(); 
	    } 
	      
	    // Method to jump over a specific part 
	    public void jump(long c) throws UnsupportedAudioFileException, IOException, 
	                                                        LineUnavailableException  
	    { 
	        if (c > 0 && c < clip.getMicrosecondLength())  
	        { 
	            clip.stop(); 
	            clip.close(); 
	            resetAudioStream(); 
	            currentFrame = c; 
	            clip.setMicrosecondPosition(c); 
	            this.play(); 
	        } 
	    } 
	      
	    // Method to reset audio stream 
	    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, 
	                                            LineUnavailableException  
	    { 
	        audioInputStream = AudioSystem.getAudioInputStream( 
	        new File(filePath).getAbsoluteFile()); 
	        clip.open(audioInputStream); 
	        clip.loop(Clip.LOOP_CONTINUOUSLY); 
	    } 
	  
} 

