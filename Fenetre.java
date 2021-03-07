/**
 * TP Chat - Mathilde MOTTAY
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.*;

/**
 * La classe Fenêtre représente la fenêtre (interface client). 
 */
public class Fenetre extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Bouton de connexion 
     */
    final JButton boutonConnexion;

    /**
     * Bouton Envoyer  
     */
    final JButton boutonEnvoyer; 

    /**
     * Zone saisie texte pour le nom 
     */
    final JTextField zoneTexteNom; 

    /**
     * Zone saisie texte pour l'IP
     */
    final JTextField zoneTexteIP;

    /**
     * Zone saisie texte pour le port 
     */
    final JTextField zoneTextePort; 

    /**
     * Zone affichage discussion 
     */
    final JTextPane zoneAffichageDiscussion; 

    /**
     * Zone affichage message 
     */
    final JTextPane zoneAffichageMessage; 

    /**
     * Zone affichage connectés 
     */
    final JTextPane zoneAffichageConnectes;  

    /**
     * Etiquette des connectés 
     */
    final JLabel etiquetteConnectes; 

    /**
     * Etiquette discussion 
     */
    final JLabel etiquetteDiscussion;

    /**
     * Etiquette message 
     */
    final JLabel etiquetteMessage;

    /**
     * Scrollpane connectés 
     */
    final JScrollPane scrollPaneConnectes; 

    /**
     * Scrollpane discussion 
     */
    final JScrollPane scrollPaneDiscussion; 

    /**
     * Client 
     */
    final Client client; 

    /**
     * Constructeur Fenetre.
     * @param client Client 
     */
    public Fenetre(Client client ){
        this.client = client; 
        
        this.setTitle("TP Chat Java");
        this.setSize(600,600); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false); // Fenêtre non-redimensionnable 

        ////////////////// MISE EN FORME DE LA FENETRE ///////////////////

        // Container principal 
        Container mainContainer = this.getContentPane(); 
        mainContainer.setLayout(new BorderLayout(8,6)); 
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        /**
         * TOP PANEL (north)
         */

        // Top Panel --> Etiquettes et zones de texte Nom, IP, Port + bouton Connexion
        JPanel topPanel = new JPanel(); 
        topPanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3)); 
        topPanel.setLayout(new BorderLayout(10,8));

        // Le Top Panel est décomposé en 2 lignes. 
        
        // LIGNE 1 --> étiquette et zone de texte du nom + bouton Connexion 
        JPanel ligne1Panel = new JPanel(); 
        ligne1Panel.setLayout(new BorderLayout(10,8)); 
        
        // Bouton Connexion 
        boutonConnexion = new JButton("Connexion");
        boutonConnexion.setPreferredSize(new Dimension(205,20));
        boutonConnexion.setEnabled(false);
        ligne1Panel.add(boutonConnexion,BorderLayout.EAST); 

        // Panel qui inclut étiquette et zone de texte du nom 
        JPanel nomPanel = new JPanel(); 
        nomPanel.setLayout(new BorderLayout(10,8));
            
        // Etiquette du nom 
        JLabel etiquetteNom = new JLabel("Nom"); 
        etiquetteNom.setPreferredSize(new Dimension(30,10));
        nomPanel.add(etiquetteNom, BorderLayout.WEST); 

        // Zone de texte nom 
        zoneTexteNom = new JTextField ();
        zoneTexteNom.setPreferredSize(new Dimension(250,30));
        nomPanel.add(zoneTexteNom, BorderLayout.EAST); 
        
        ligne1Panel.add(nomPanel,BorderLayout.WEST); 
        topPanel.add(ligne1Panel,BorderLayout.NORTH); 

        // LIGNE 2 --> Etiquettes et zones de texte IP + Port 
        JPanel ligne2Panel = new JPanel(); 
        ligne2Panel.setLayout(new BorderLayout(15,8)); 

        // Panel qui inclut étiquette et zone de texte IP   
        JPanel IPPanel = new JPanel(); 
        IPPanel.setLayout(new BorderLayout(10,8));

        // Etiquette IP
        JLabel etiquetteIP = new JLabel("IP"); 
        etiquetteIP.setPreferredSize(new Dimension(30,10));
        IPPanel.add(etiquetteIP, BorderLayout.WEST); 

        // Zone de texte IP 
        zoneTexteIP = new JTextField();
        zoneTexteIP.setPreferredSize(new Dimension(250,30));
        IPPanel.add(zoneTexteIP, BorderLayout.EAST); 

        ligne2Panel.add(IPPanel,BorderLayout.WEST);  

        // Panel qui inclut étiquette et zone de texte Port   
        JPanel PortPanel = new JPanel(); 
        PortPanel.setLayout(new BorderLayout(10,8));

        // Etiquette Port 
        JLabel etiquettePort = new JLabel("Port"); 
        etiquettePort.setPreferredSize(new Dimension(30,10));
        PortPanel.add(etiquettePort, BorderLayout.WEST); 

        // Zone de texte Port 
        zoneTextePort = new JTextField(); 
        zoneTextePort.setPreferredSize(new Dimension(5,30));
        PortPanel.add(zoneTextePort, BorderLayout.CENTER); 

        ligne2Panel.add(PortPanel,BorderLayout.CENTER);  
        topPanel.add(ligne2Panel,BorderLayout.SOUTH); 

        mainContainer.add(topPanel,BorderLayout.NORTH); 

        /**
         * CONNECTES PANEL (west) : étiquette et zone d'affichage Connectés 
         */
        JPanel connectesPanel = new JPanel(); 
        connectesPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        connectesPanel.setLayout(new BorderLayout(10,8));

        // Etiquette Connectés 
        etiquetteConnectes = new JLabel("             Connectés");
        etiquetteConnectes.setPreferredSize(new Dimension(100,10));
        connectesPanel.add(etiquetteConnectes,BorderLayout.NORTH); 

        // Zone d'affichage Connectés 
        zoneAffichageConnectes = new JTextPane();
        zoneAffichageConnectes.setContentType("text/html"); 
        zoneAffichageConnectes.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true); 
        zoneAffichageConnectes.setText(null);
        zoneAffichageConnectes.setEditable(false);
        zoneAffichageConnectes.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.GRAY));
        zoneAffichageConnectes.setPreferredSize(new Dimension(150,30)); 
        scrollPaneConnectes = new JScrollPane(zoneAffichageConnectes,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        connectesPanel.add(scrollPaneConnectes, BorderLayout.CENTER); 
        mainContainer.add(connectesPanel,BorderLayout.WEST); 

        /**
         * CENTER PANEL : Discussion, Message & Bouton envoyer 
         */
        JPanel centerPanel = new JPanel(); 
        centerPanel.setLayout(new BorderLayout(10,8)); 
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Panel discussion : étiquette + zone affichage discussion
        JPanel discussionPanel = new JPanel(); 
        discussionPanel.setLayout(new BorderLayout(10,8));
        discussionPanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));

        // Etiquette discussion 
        etiquetteDiscussion = new JLabel("                                           Discussion");
        etiquetteDiscussion.setPreferredSize(new Dimension(100,10));
        discussionPanel.add(etiquetteDiscussion, BorderLayout.NORTH); 

        // Zone affichage discussion 
        zoneAffichageDiscussion = new JTextPane();
        zoneAffichageDiscussion.setEditable(false);  
        zoneAffichageDiscussion.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.GRAY));
        zoneAffichageDiscussion.setPreferredSize(new Dimension(150,20));
        zoneAffichageDiscussion.setContentType("text/html"); 
        zoneAffichageDiscussion.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true); 
        scrollPaneDiscussion = new JScrollPane(zoneAffichageDiscussion,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        discussionPanel.add(scrollPaneDiscussion, BorderLayout.CENTER); 

        // Panel messageEnvoyer : étiquette et zone affichage Message + bouton Envoyer 
        JPanel messageEnvoyerPanel = new JPanel(); 
        messageEnvoyerPanel.setLayout(new BorderLayout(10,4));
        messageEnvoyerPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

        // Panel qui inclut étiquette et zone affichage Message 
        JPanel messagePanel = new JPanel(); 
        messagePanel.setLayout(new BorderLayout(10,8)); 
        messagePanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

        // Etiquette message 
        etiquetteMessage = new JLabel("Message");
        etiquetteMessage.setPreferredSize(new Dimension(150,24));
        messagePanel.add(etiquetteMessage, BorderLayout.NORTH); 

        // Zone affichage message 
        zoneAffichageMessage = new JTextPane(); 
        zoneAffichageMessage.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.GRAY));
        zoneAffichageMessage.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true); 
        zoneAffichageMessage.setPreferredSize(new Dimension(200,80)); 
        zoneAffichageMessage.setEnabled(false);
        messagePanel.add(zoneAffichageMessage, BorderLayout.CENTER); 

        messageEnvoyerPanel.add(messagePanel,BorderLayout.CENTER); 

        // Panel : bouton envoyer 
        JPanel boutonEnvoyerPanel = new JPanel(); 
        boutonEnvoyerPanel.setLayout(new BorderLayout(10,8)); 
        boutonEnvoyerPanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

        // Bouton Envoyer 
        boutonEnvoyer = new JButton("Envoyer"); 
        boutonEnvoyer.setPreferredSize(new Dimension(50,30));
        boutonEnvoyerPanel.add(boutonEnvoyer, BorderLayout.CENTER); 
        messageEnvoyerPanel.add(boutonEnvoyerPanel, BorderLayout.SOUTH); 
        
        centerPanel.add(discussionPanel, BorderLayout.CENTER); 
        centerPanel.add(messageEnvoyerPanel, BorderLayout.SOUTH); 
        mainContainer.add(centerPanel,BorderLayout.CENTER); 

        affichageAvantConnexion(); 
        this.setVisible(true);      

        ////////////////// FIN MISE EN FORME DE LA FENETRE ///////////////////

        /**
         * Si on clique sur le bouton Envoyer, le message est envoyé.  
         */
        boutonEnvoyer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
               client.envoieMessage(); 
            }
        });     

        /**
         * Procédure de connexion / déconnexion si clic sur le bouton Connexion
         */
        boutonConnexion.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent evt){
                try {
                    // Procédure de connexion 
                    if(boutonConnexion.getText().equals("Connexion")){
                        
                        affichageAprèsConnexion(); // Actualisation de l'affichage (mode connexion)
                        
                        // Récupération du nom, ip et numéro de port 
                        String nom = zoneTexteNom.getText(); 
                        String ip = zoneTexteIP.getText(); 
                        int port = Integer.parseInt(zoneTextePort.getText()); 
                        
                        client.connexion(nom,ip,port); 
                    }
                    // Procédure de déconnexion 
                    else if (boutonConnexion.getText().equals("Déconnexion")){  
                        affichageAvantConnexion(); // Actualisation de l'affichage (mode déconnexion)
                        client.deconnexion(); 
                    }
                }
                catch(Exception e){
                   JOptionPane.showMessageDialog(mainContainer,"Connexion impossible au serveur");
                }
            }
        }); 

        zoneTexteNom.getDocument().addDocumentListener(new TextListener(zoneTexteNom, zoneTextePort, zoneTexteIP, boutonConnexion));
        zoneTexteIP.getDocument().addDocumentListener(new TextListener(zoneTexteNom, zoneTextePort, zoneTexteIP, boutonConnexion));
        zoneTextePort.getDocument().addDocumentListener(new TextListener(zoneTexteNom, zoneTextePort, zoneTexteIP, boutonConnexion));
    }

    
    /**
     * Permet de gérer l'état du bouton connexion en fonction de la saisie des champs.
     * Le bouton connexion devient actif quand les champs nom, ip et port sont remplis. 
     */
    private class TextListener implements DocumentListener {

        private JTextField nom;     // Zone de texte correspondant au nom
        private JTextField ip;      // Zone de texte correspondant à l'adresse ip
        private JTextField port;    // Zone de texte correspondant au numéro de port 
        private JButton bouton;     // Correspond au bouton connexion/déconnexion
        
        /**
         * Constructeur TextListener.
         * @param nom Zone de texte correspondant au nom
         * @param ip Zone de texte correspondant à l'adresse ip
         * @param port Zone de texte correspondant au numéro du port
         * @param bouton Bouton  de connexion/ déconnexion
         */
        public TextListener (JTextField nom, JTextField ip, JTextField port, JButton bouton){
            this.nom = nom; 
            this.ip = ip; 
            this.port = port; 
            this.bouton = bouton; 
        }

        public void changedUpdate(DocumentEvent e) {
            actualisation(); 
        }

        public void removeUpdate(DocumentEvent e){
            actualisation();
        }

        public void insertUpdate(DocumentEvent e){
            actualisation();
        }

        /**
         * Actualise l'état actif/désactif du bouton connexion/déconnexion selon si les zones de texte nom, ip et port sont tous remplis ou non. 
         */
        private void actualisation(){
            if(!nom.getText().trim().equals("") && !ip.getText().trim().equals("") && !port.getText().trim().equals("")){
                bouton.setEnabled(true);
            }
            else {
                bouton.setEnabled(false);
            } 
        }
    }

    /**
     * Ajoute du texte à la zone d'affichage de texte passée en paramètre 
     * @param textPane Zone d'affichage de texte 
     * @param message Texte à ajouter 
     */
    public void ajouteTexte(JTextPane textPane, String texte){
        HTMLDocument document = (HTMLDocument)textPane.getDocument(); 
        HTMLEditorKit editorKit = (HTMLEditorKit)textPane.getEditorKit(); 

        try {
            editorKit.insertHTML(document,document.getLength(),texte,0,0,null); 
            textPane.setCaretPosition(document.getLength()); 
        }
        catch(Exception e) {
            e.printStackTrace(); 
        }          
    }

    /**
     * Affichage avant la connexion au serveur
     */
    public void affichageAvantConnexion(){
        boutonConnexion.setText("Connexion");
        etiquetteConnectes.setVisible(false);
        zoneAffichageConnectes.setVisible(false); 
        zoneAffichageConnectes.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        scrollPaneConnectes.setVisible(false); 
        etiquetteDiscussion.setVisible(false); 
        zoneAffichageDiscussion.setVisible(false);
        zoneAffichageDiscussion.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        scrollPaneDiscussion.setVisible(false);
        zoneAffichageMessage.setVisible(false); 
        etiquetteMessage.setVisible(false);
        boutonEnvoyer.setVisible(false); 
        zoneAffichageMessage.setText(null);
        zoneAffichageDiscussion.setText(null);
        zoneAffichageConnectes.setText(null);
        zoneTexteNom.setText(null);
        zoneTexteIP.setText(null); 
        zoneTextePort.setText(null); 
        zoneTexteNom.setEditable(true); 
        zoneTexteIP.setEditable(true); 
        zoneTextePort.setEditable(true); 
    }

    /**
     * Affichage après la connexion au serveur
     */
    public void affichageAprèsConnexion(){
        boutonConnexion.setText("Déconnexion"); 
        etiquetteConnectes.setVisible(true);
        zoneAffichageConnectes.setVisible(true); 
        zoneAffichageConnectes.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.GRAY));
        scrollPaneConnectes.setVisible(true); 
        etiquetteDiscussion.setVisible(true);
        zoneAffichageDiscussion.setVisible(true);
        zoneAffichageDiscussion.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.GRAY));
        scrollPaneDiscussion.setVisible(true); 
        etiquetteMessage.setVisible(true);
        zoneAffichageMessage.setVisible(true);
        zoneAffichageMessage.setEnabled(true);  
        boutonEnvoyer.setVisible(true); 
        zoneTexteNom.setEditable(false); 
        zoneTexteIP.setEditable(false); 
        zoneTextePort.setEditable(false); 
    }

    /**
     * Retourne la zone d'affichage des messages de la fenêtre 
     * @return Zone d'affichage des messages 
     */
    public JTextPane getZoneAffichageMessage(){
        return zoneAffichageMessage; 
    }

    /**
     * Retourne la zone d'affichage des connectés de la fenêtre
     * @return Zone d'affichage des connectés 
     */
    public JTextPane getZoneAffichageConnectes(){
        return zoneAffichageConnectes; 
    }

    /**
     * Retourne la zone d'affichage de discussion de la fenêtre 
     * @return Zone d'affichage de la discussion 
     */
    public JTextPane getZoneAffichageDiscussion(){
        return zoneAffichageDiscussion; 
    }
}
