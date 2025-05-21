package Utils;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class MP3Player implements Runnable {
    private String fileName;
    private boolean loop = true;
    private Thread thread;
    private Player player;

    public MP3Player(String fileName) {
        this.fileName = fileName;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        loop = false;
        if(player!=null){
            player.close();
        }
        if(thread!=null&&thread.isAlive()){
            thread.interrupt();
        }

    }

    @Override
    public void run() {
        do {
            try {
                InputStream is = getClass().getResourceAsStream("/sounds/" + fileName);
                if (is == null) {
                    System.err.println("Archivo MP3 no encontrado: " + fileName);
                    return;
                }
                BufferedInputStream bis = new BufferedInputStream(is);
                player = new Player(bis);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        } while (loop);
    }
}
