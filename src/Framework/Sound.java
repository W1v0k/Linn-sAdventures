package Framework;

import javax.sound.sampled.*;

public class Sound {

    private Clip clip;

    public Sound(String path){
        try{

            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(path));

            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                                        baseFormat.getSampleRate(), 16,
                                                        baseFormat.getChannels(),
                                                        baseFormat.getChannels() * 2,
                                                        baseFormat.getSampleRate(),
                                                false);

            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);

            clip = AudioSystem.getClip();
            clip.open(dais);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        if(clip == null) return;
        stop();
        clip.setFramePosition(0);   //always start playing from the start of the sound
        clip.start();
    }

    public void play(int timesToRepeat){
        if(clip == null) return;
        stop();
        //For volume
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-25.0f);
        clip.setFramePosition(0);
        clip.loop(timesToRepeat);
    }

    public void play(String typeSound){
        if(clip == null) return;
        stop();
        if(typeSound.toLowerCase() == "coin"){
            //For volume
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-25.0f);
        }else if(typeSound.toLowerCase() == "attack"){
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
        }else if(typeSound.toLowerCase() == "level"){
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
        }
        clip.setFramePosition(0);
        clip.start();
    }

    public void close(){
        stop();
        clip.close();
    }

    public void stop(){
        //if(clip.isRunning())
        clip.stop();
    }

}
