/**
 * TP Chat - Mathilde MOTTAY
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * La classe Utilisateur représente un utilisateur.  
 */
public class Utilisateur {

    /**
     * Numéro Utilisateur (permet de générer automatiquement l'identifiant de l'utilisateur)
     */
    private static int numUtilisateur = 0;

    /**
     * Identifiant de l'utilisateur (permet d'affecter une couleur à l'utilisateur)
     */
    private int identifiant; 

    /**
     * Flux de sortie 
     */
    private PrintStream fluxSortie; 

    /**
     * Flux d'entrée 
     */
    private InputStream fluxEntree; 

    /**
     * Pseudo de l'utilisateur 
     */
    private String pseudo; 

    /**
     * Couleur de l'utilisateur 
     */
    private String couleur; 

    /**
     * Tableau de couleurs (chaines de caractères des codes HEX)
     */
    public static String[] couleurs = {
        "#ee9b23", // orange
        "#ee3823", // rouge
        "#6c3483", // violet
        "#3498db", // bleu clair
        "#f1c40f", // jaune
        "#7b7d7d", // gris 
        "#e948d3", // rose
        "#108158", // vert
        "#104081", // bleu marine
    }; 

    /**
     * Constructeur Utilisateur. 
     * @param client Socket client 
     * @param pseudo Pseudo de l'utilisateur
     * @exception IOException
     */
    public Utilisateur(Socket client, String pseudo) throws IOException {
        fluxSortie = new PrintStream(client.getOutputStream()); 
        fluxEntree = client.getInputStream(); 
        this.pseudo = pseudo; 
        identifiant = numUtilisateur++; 
        couleur = couleurs[identifiant%(couleurs.length)]; // On prend à chaque fois la couleur suivante 
    }

    /**
     * Retourne le pseudo de l'utilisateur 
     * @return Pseudo de l'utilisateur 
     */
    public String getPseudo(){
        return pseudo; 
    }
    
    /**
     * Retourne la couleur de l'utilisateur 
     * @return Couleur de l'utilisateur 
     */
    public String getCouleur(){
        return couleur; 
    }

    /**
     * Retourne le flux de sortie 
     * @return Flux de sortie 
     */
    public PrintStream getFluxSortie(){
        return fluxSortie; 
    }

    /**
     * Retourne le flux d'entrée
     * @return Flux d'entrée 
     */
    public InputStream getFluxEntree(){
        return fluxEntree; 
    }

    /**
     * Affichage de l'utilisateur (pseudo en couleur)
     */
    public String toString(){
        return "<span style='color:" + getCouleur() + "'>" + getPseudo() + "</span>"; 
    }
}

