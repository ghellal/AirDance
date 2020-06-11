 import javazoom.jl.player.advanced.*;
 import java.io.*;


public class Sound_mp3 extends Thread {
    
    int t1 = -1;
    int t2 = -1;

    private boolean isPlaying = false;
    private AdvancedPlayer player = null;

    public Sound_mp3(String path) throws Exception {
        InputStream in = (InputStream)new BufferedInputStream(new FileInputStream(new File(path)));
        player = new AdvancedPlayer(in);
    }
    public Sound_mp3(String path, int t1, int t2) throws Exception {
        InputStream in = (InputStream)new BufferedInputStream(new FileInputStream(new File(path)));
        player = new AdvancedPlayer(in);

        this.t1 = t1;
        this.t2 = t2;
    }
               
    public Sound_mp3(String path,PlaybackListener listener) throws Exception {
        InputStream in = (InputStream)new BufferedInputStream(new FileInputStream(new File(path)));
        player = new AdvancedPlayer(in);
        player.setPlayBackListener(listener);
     }
               
    public void play_music() throws Exception {
        if (player != null) {
            isPlaying = true;
            player.play();
            }
        }
               
    public void play_music(int begin,int end) throws Exception {
        if (player != null) {
        	isPlaying = true;
            player.play(begin,end);
        	}
        }
               
    public void stop_music() throws Exception {
        if (player != null) {
            isPlaying = false;
            player.stop();
        }
    }
               
    public boolean isPlaying() {
        return isPlaying;
    }



    public void run() {
        try {
            if ((t1 != -1) && (t2 != -1)) {
                play_music(t1,t2);
            } else {
                play_music();
            }
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
} 