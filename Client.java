/**
 * TP Chat - Mathilde MOTTAY
 */

import java.net.*;
import java.io.*;

/**
 * La classe Client représente un client. 
 */
public class Client {
    
    /**
     * Socket serveur 
     */
    private Socket serveur; 

    /**
     * Flux en écriture   
     */
    private PrintWriter fluxEcriture; 

    /**
     * Flux en lecture  
     */
    private BufferedReader fluxLecture;  

    /**
     * Thread pour la lecture des messages
     */
    private Thread lectureMessage;

    /**
     * Fenêtre (interface client)
     */
    private static Fenetre fenetre; 

    /**
     * Programme principal pour lancer un client
     * @param args
     */
    public static void main (String[] args){
        fenetre = new Fenetre(new Client()); 
    }

    /**
     * Connecte le client au serveur (procédure de connexion)
     * @param nom Nom du client
     * @param ip Adresse IP 
     * @param port Numéro de port  
     * @throws IOException
     */
    public void connexion(String nom, String ip, int port) throws IOException {
        serveur = new Socket(ip,port);                        
        fluxLecture = new BufferedReader(new InputStreamReader(serveur.getInputStream())); 
        fluxEcriture = new PrintWriter(serveur.getOutputStream(),true); 
        fluxEcriture.println(nom); // Le client envoie son nom (pseudo) au serveur. 
        lectureMessage = new LectureMessage(fenetre, fluxLecture);
        lectureMessage.start(); 
    }
    
    /**
     * Déconnecte le client du serveur (procédure de déconnexion)
     */
    public void deconnexion(){
        lectureMessage.interrupt(); 
        fluxEcriture.close(); 
    }

    /**
     * Permet au client d'envoyer un message 
     */
    public void envoieMessage () {
        try{
            // Récupère le contenu de la zone d'affichage message 
            String message = fenetre.getZoneAffichageMessage().getText().trim(); 
            if(message.equals("")){
                return; 
            }

            // Ecrit le message au serveur 
            fluxEcriture.println(message); 

            // Vide la zone d'affichage message 
            fenetre.getZoneAffichageMessage().setText(null);
        }
        catch (Exception e){
            System.out.println(e.getMessage()); 
        }
    }   
}