/**
 * TP Chat - Mathilde MOTTAY
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * La classe LectureMessage permet au client de lire les messages qui arrivent du serveur. 
 */
public class LectureMessage extends Thread {

    /**
     * Fenêtre (interface client)
     */
    private Fenetre fenetre; 

    /**
     * Flux en lecture 
     */
    private BufferedReader fluxLecture; 


    /**
     * Constructeur LectureMessage. 
     * @param fenetre Fenêtre (interface client)
     * @param fluxLecture Flux en lecture 
     */
    public LectureMessage(Fenetre fenetre, BufferedReader fluxLecture){
        this.fenetre = fenetre; 
        this.fluxLecture = fluxLecture;
    }

    /**
     * Lit les messages sur le flux en lecture. 
     * Les messages peuvent correspondre à la liste des connectés ou à des messages envoyés par d'autres utilisateurs.
     */
    public void run(){
        String message; 

        while(!Thread.currentThread().isInterrupted()){
            try {
                message = fluxLecture.readLine(); 

                // Message correspondant à la liste des utilisateurs connectés 
                if(message.charAt(0) == '['){ 
                    // Vide la zone d'affichage des connectés 
                    fenetre.getZoneAffichageConnectes().setText(null); 

                    // Extrait les pseudos des utilisateurs et les met dans une liste 
                    message = message.substring(1,message.length()-1);
                    ArrayList<String> listeDesConnectes = new ArrayList<String>(Arrays.asList(message.split(", "))); 

                    // Affiche les pseudos des utilisateurs connectés dans la zone d'affichage dédiée à cet effet. 
                    for(String utilisateur :listeDesConnectes){
                        fenetre.ajouteTexte(fenetre.getZoneAffichageConnectes(),"<center><b>" + utilisateur + "</b></center>"); 
                    }
                }
                // Message "normal"
                else if (!message.isEmpty()){
                    // Ajoute le message dans la zone d'affichage de discussion
                    fenetre.ajouteTexte(fenetre.getZoneAffichageDiscussion(),message); 
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage()); 
            }
        }
    }
}