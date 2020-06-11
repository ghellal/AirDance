import java.io.*;
import java.net.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import static javax.swing.SwingConstants.*;


 
class PanneauInterieur
extends JPanel 
implements ActionListener {


    JPanel PaneConvertisseur;


    int nbSon = 10;
    JButton boutonChoisir[] = new JButton[nbSon];
    JTextField chemin[] = new JTextField[nbSon];

    JFileChooser chooser;

    //JButton boutonServeur;

    PanneauInterieur() {


        PaneConvertisseur = new JPanel();

        PaneConvertisseur.setLayout(new GridLayout(nbSon,3,10,5));

        for (int i=0; i<nbSon; i++) {
            
            boutonChoisir[i] = new JButton("Parcourir");

            chemin[i] = new JTextField("");

            PaneConvertisseur.add(new JLabel("Musique " + (i+1), CENTER));
            PaneConvertisseur.add(chemin[i]);
            PaneConvertisseur.add(boutonChoisir[i]);

            boutonChoisir[i].addActionListener(this);
        }
       
        add(PaneConvertisseur);

        chooser = new JFileChooser();
        chooser.setDialogTitle("Choisir un fichier son");
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("WAVE and MP3", "wav", "mp3");
        chooser.addChoosableFileFilter(filter);

    }

    public void actionPerformed (ActionEvent e) {
        for (int i=0; i<nbSon; i++) {
            if (boutonChoisir[i] == e.getSource()) {

                int returnValue = chooser.showOpenDialog(this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    System.out.println(chooser.getSelectedFile().getPath());
                    chemin[i].setText(chooser.getSelectedFile().getPath());
                }   
            }
        }
    }

    public int getNbSon(){
        return nbSon;
    }

    public String getPath(int i){
        return chemin[i].getText();
    }
}

public class AirDance {
 
    public static void main(String[] args) {
        if (args.length < 1) return;
        

        JFrame maFenetre = new JFrame("AirDance"); 
        PanneauInterieur panneau = new PanneauInterieur();
        maFenetre.setContentPane(panneau);
        maFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int tailleX = 390;
        int tailleY = 350;

        maFenetre.setPreferredSize(new Dimension(tailleX, tailleY));

        //maFenetre.requestFocus();

        Dimension dimEcran = Toolkit.getDefaultToolkit().getScreenSize();
        maFenetre.setLocation(new Point( (dimEcran.width - tailleX) / 2,(dimEcran.height - tailleY) / 2) );
        maFenetre.pack();
        maFenetre.setVisible(true);    

        int port = Integer.parseInt(args[0]);   

        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
 
                new ServerThread(socket, panneau).start();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } 

    }
}