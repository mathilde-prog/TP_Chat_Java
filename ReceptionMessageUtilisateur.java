/**
 * TP Chat - Mathilde MOTTAY
 */

import java.util.Scanner;

/**
 * La classe ReceptionMessageUtilisateur permet de gérer les messages envoyés par le client.   
 */
public class ReceptionMessageUtilisateur implements Runnable {

    /**
     * Serveur 
     */
    private Serveur serveur; 

    /**
     * Utilisateur 
     */
    private Utilisateur utilisateur; 

    /**
     * Constructeur ReceptionMessageClient. 
     * @param serveur Serveur 
     * @param utilisateur Utilisateur 
     */
    public ReceptionMessageUtilisateur (Serveur serveur, Utilisateur utilisateur){
        this.serveur = serveur; 
        this.utilisateur = utilisateur; 
        this.serveur.diffuseListeUtilisateurs();
    }

    /**
     * Récupère le message envoyé par l'utilisateur et le diffuse à tous les utilisateurs. 
     * Dans le cas d'un message privé, le message est seulement envoyé à l'utilisateur destinataire. 
     * Quand l'utilisateur se déconnecte, le serveur efface l'utilisateur et diffuse à tous les utilisateurs la nouvelle liste des connectés. 
     */
    public void run(){
        String message; 

        Scanner scan = new Scanner(utilisateur.getFluxEntree()); 
        while(scan.hasNextLine()){
            message = scan.nextLine(); 
            
            // SMILEYS - On remplace les smileys par l'image correspondante 
            message = message.replace("<3","<img src='https://wprock.fr/wp-content/themes/wprock-theme/img/emoji/joypixels/512/2764.png' width='20' height='20'>");
            message = message.replace(":)","<img src='https://wprock.fr/wp-content/themes/wprock-theme/img/emoji/joypixels/512/1f642.png' width='20' height='20'>");
            message = message.replace(";)","<img src='https://wprock.fr/wp-content/themes/wprock-theme/img/emoji/joypixels/512/1f609.png' width ='20' height='20'>"); 
            message = message.replace(":P","<img src='https://wprock.fr/wp-content/themes/wprock-theme/img/emoji/joypixels/512/1f61c.png' width='20' height='20'>"); 
            message = message.replace("XD","<img src='https://wprock.fr/wp-content/themes/wprock-theme/img/emoji/joypixels/512/1f602.png' width='20' height='20'>"); 
            message = message.replace(":(","<img src='https://wprock.fr/wp-content/themes/wprock-theme/img/emoji/joypixels/512/1f641.png' width='20' height='20'>"); 
            message = message.replace(":o","<img src='https://wprock.fr/wp-content/themes/wprock-theme/img/emoji/joypixels/512/1f62e.png' width='20' height='20'>"); 
            message = message.replace(":D","<img src='https://wprock.fr/wp-content/themes/wprock-theme/img/emoji/joypixels/512/1f600.png' width='20' height='20'>"); 
            message = message.replace(":3","<img src='https://wprock.fr/wp-content/themes/wprock-theme/img/emoji/joypixels/512/1f600.png' width='20' height='20'>"); 

            // Cas : message destiné à tous les utilisateurs 
            if(message.charAt(0) != '@'){
                serveur.diffuseMessageATousLesUtilisateurs(message,utilisateur);
            }
            // Cas : message privé 
            else {  
                 message = message.trim(); // Enlève les espaces de début et de fin 
                 int premierEspace = message.indexOf(" "); // Cherche l'indice du premier espace dans la chaine de caractères 
                 String pseudoDestinataire = message.substring(1,premierEspace); // Extrait le pseudo du destinataire 
                 String messageAEnvoyer = message.substring(premierEspace+1,message.length()); // Extrait le message à envoyer 
                 serveur.envoieMessagePrive(messageAEnvoyer,utilisateur,pseudoDestinataire); 
            }
        }

        // Efface l'utilisateur de la liste des connectés 
        serveur.effaceUtilisateur(utilisateur);

        // Diffuse la nouvelle liste à tous les utilisateurs 
        serveur.diffuseListeUtilisateurs();
        
        scan.close(); 
    }
}


