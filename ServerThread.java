
import java.io.*;
import java.net.*;
 

public class ServerThread extends Thread {
    private Socket socket;

    PanneauInterieur panneau;
 
    String path;

    String text;

    int indiceMusique = -1;
    int start = -1;
    int end = -1;

    String format;

    public ServerThread(Socket socket, PanneauInterieur panneau) {
        this.socket = socket;
        this.panneau = panneau;
    }
 
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
 
 
            
 
            do {
                text = reader.readLine();

                System.out.println("Pos -m :" + text.indexOf("-m"));

                System.out.println("Pos -start :" + text.indexOf("-start"));

                System.out.println("Pos -end :" + text.indexOf("-end"));

                //System.out.println("aza   azaz ".replaceAll("\\s+",""));


                indiceMusique = Integer.parseInt((text.substring(text.indexOf("-m")+2, text.indexOf("-start"))).replaceAll("\\s+",""));

                System.out.println("Musique :" + indiceMusique);

                start = Integer.parseInt((text.substring(text.indexOf("-start")+6, text.indexOf("-end"))).replaceAll("\\s+",""));

                System.out.println("Start :" + start);

                end = Integer.parseInt((text.substring(text.indexOf("-end")+4, text.length())).replaceAll("\\s+",""));

                System.out.println("End :" + end);


                path = panneau.getPath(indiceMusique);

                System.out.println("path :" + path);

                format = path.substring(path.indexOf("."), path.length());

                System.out.println("format :" + format);

                if (format.equals(".wav")) {

                    writer.println("Server: " + "wave");

                    try {
                        //new Sound_wav(panneau.getPath(Integer.parseInt(indiceMusique))).start();
                        //new Sound_wav(panneau.getPath(Integer.parseInt(indiceMusique)),0,100).start();
                        /*new Sound_wav(panneau.getPath(Integer.parseInt(indiceMusique)),5100000, 6100000).start();
                        Thread.sleep(30);
                        new Sound_wav(panneau.getPath(Integer.parseInt(indiceMusique)),5000000, 6100000).start();
                        Thread.sleep(30);
                        new Sound_wav(panneau.getPath(Integer.parseInt(indiceMusique)),5000000, 6100000).start();*/

                        new Sound_wav(panneau.getPath(indiceMusique),start*1000,end*1000).start();
                    }   
                    catch (Exception e) {
                        System.out.println("Exception: " + e);
                    }

                } else if (format.equals(".mp3")) {

                    writer.println("Server: " + "mp3");

                    try {
                        //new Sound_mp3(panneau.getPath(Integer.parseInt(text))).start();
                        //new Sound_mp3(panneau.getPath(Integer.parseInt(text)),0,100).start();
                        /*new Sound_mp3(panneau.getPath(Integer.parseInt(indiceMusique)),00, 6100000).start();
                        Thread.sleep(30);
                        new Sound_mp3(panneau.getPath(Integer.parseInt(indiceMusique)),5000000, 6100000).start();
                        Thread.sleep(30);
                        new Sound_mp3(panneau.getPath(Integer.parseInt(indiceMusique)),5000000, 6100000).start();*/

                        new Sound_mp3(panneau.getPath(indiceMusique),start,end).start();
                    }   
                    catch (Exception e) {
                        System.out.println("Exception: " + e);
                    }
                }
                
                writer.println("Server: " + path.substring(path.indexOf("."), path.length()));
 
            } while (!text.equals("bye"));
 
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}