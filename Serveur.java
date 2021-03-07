/**
 * TP Chat - Mathilde MOTTAY
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * La classe Serveur représente le serveur.  
 */
public class Serveur {

    /**
     * Numéro du port local sur lequel les connexions sont attendues du serveur 
     */
    private int port; 

    /**
     * Liste des utilisateurs connectés au serveur 
     */
    private List<Utilisateur> listeDesConnectes; 

    /**
     * ServerSocket côté serveur pour attendre les appels du ou des clients 
     */
    private ServerSocket serveur; 


    /**
     * Programme principal pour lancer le serveur (port 64000)
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException{
        new Serveur(64000).lanceToi();
    }

    /**
     * Constructeur Serveur. 
     * @param port Numéro du port local sur lequel les connexions sont attendues du serveur  
     */
    public Serveur(int port){
        this.port = port; 
        listeDesConnectes = new ArrayList<Utilisateur>(); 
    }

    /**
     * Lance le serveur 
     * @throws IOException
     */
    public void lanceToi() throws IOException {

        // Crée un socket de serveur en attente sur le port passé en paramètre
        serveur = new ServerSocket(port) {
            protected void finalize() throws IOException {
                close(); // Ferme le socket du serveur 
            }
        }; 

        System.out.println("Ouverture du port " + port); 

        while(true){
            // Attend la demande de connexion d'un client, et renvoie un socket une fois la connexion établie. 
            Socket client = serveur.accept();

            // Récupère le pseudo du client  
            Scanner scan = new Scanner(client.getInputStream());
            String pseudo = scan.nextLine(); 

            Utilisateur nouvelUtilisateur = new Utilisateur(client,pseudo);
            // Ajout du nouvel utilisateur à la liste des utilisateurs connectés 
            listeDesConnectes.add(nouvelUtilisateur); 
            // Le nouvel utilisateur reçoit un message de bienvenue 
            nouvelUtilisateur.getFluxSortie().println("<b> Bienvenue " + nouvelUtilisateur + " !</b>"); 
            
            // Lancement du thread pour permettre la réception des messages du nouvel utilisateur 
            new Thread(new ReceptionMessageUtilisateur(this,nouvelUtilisateur)).start(); 
        }
    }

    /**
     * Efface l'utilisateur passé en paramètre de la liste des utilisateurs connectés 
     * @param utilisateur Utilisateur à effacer 
     */
    public void effaceUtilisateur(Utilisateur utilisateur){
        listeDesConnectes.remove(utilisateur); 
    }

    /**
     * Diffuse un message à tous les utilisateurs connectés 
     * @param message Message à diffuser 
     * @param utilisateurEnvoi Utilisateur qui envoie le message 
     */
    public void diffuseMessageATousLesUtilisateurs(String message, Utilisateur utilisateurEnvoi){
        for(Utilisateur client :listeDesConnectes){
            client.getFluxSortie().println("<span> " + utilisateurEnvoi + " : "+ message + "</span>"); 
        }
    }

    /**
     * Envoie un message privé à un utilisateur 
     * @param message Message à envoyer 
     * @param utilisateurEnvoi Utilisateur qui envoie le message 
     * @param pseudoDestinataire Pseudo de l'utilisateur à qui le message privé est adressé 
     */
    public void envoieMessagePrive(String message, Utilisateur utilisateurEnvoi, String pseudoDestinataire){       
        for(Utilisateur client: listeDesConnectes){
            if(client.getPseudo().equals(pseudoDestinataire)){
                utilisateurEnvoi.getFluxSortie().println("<span> Vous à " + client + " : " + message + "</span>"); 
                client.getFluxSortie().println("<span>" + utilisateurEnvoi + " à vous : " + message + "</span>"); 
            }
        }
    }

    /**
     * Diffuse la liste des utilisateurs connectés à tous les utilisateurs 
     */
    public void diffuseListeUtilisateurs(){
        for(Utilisateur client: listeDesConnectes){
            client.getFluxSortie().println(listeDesConnectes); 
        }
    }
}

