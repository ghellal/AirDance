 import java.io.*;

 import javax.sound.sampled.AudioSystem;
 import javax.sound.sampled.AudioInputStream;
 import javax.sound.sampled.Clip;
 import javax.sound.sampled.DataLine;
       


public class Sound_wav extends Thread {
    
    int t1 = -1;
    int t2 = -1;

    private boolean isPlaying = false;
    private Clip player = null;

    public Sound_wav(String path) throws Exception {

        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
        DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
        player = (Clip) AudioSystem.getLine(info);
        player.open(ais);
    }
    public Sound_wav(String path, int t1, int t2) throws Exception {

        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
        DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
        player = (Clip) AudioSystem.getLine(info);
        player.open(ais);

        this.t1 = t1;
        this.t2 = t2;
    }
               
    public void play_music() throws Exception {
        if (player != null) {
            isPlaying = true;
            player.start();
            }
        }
               
    public void play_music(int begin,int end) throws Exception {
        if (player != null) {
            isPlaying = true;
            player.setFramePosition(t1);
            player.start();
            //player.setLoopPoints(t1, t2);
            //player.loop(1);
            }
        }
               
    public void run() {
        try {
            if ((t1 != -1) && (t2 != -1)) {
                play_music(t1,t2);

                while (player.isActive()) {
                    if (player.getFramePosition() > t2) {
                        player.stop();
                        System.out.println("stop " + player.getFramePosition());
                    }
                }

            } else {
                play_music();
            }
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
} 