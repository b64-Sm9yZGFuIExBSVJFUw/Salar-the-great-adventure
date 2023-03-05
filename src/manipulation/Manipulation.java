package manipulation;
import java.io.File;

import java.io.FileInputStream;


import java.io.FileNotFoundException;
import java.util.ArrayList;

import appli.Main;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import niveau.Niveau;
import niveau.NiveauInterface;
import sprite.ImageSprite;
import sprite.Sprite;
import texte.Texte;

/**
 * Pour manipuler les éléments d'un niveau
 * (Sert à simplifier la création d'un niveau en "cachant" le réel code en JavaFX derrière les fonctions)
 * 
 * @author Jordan LAIRES
 */
public class Manipulation {
	//Son qui se joue quand le joueur est touché
	private static Media son = new Media(new File("Sons/touché.mp3").toURI().toString()); 
	
	//Variables d'affichage du score
	private static final int POSITION_X_SCORE = 610; //Position en X du score
	private static final int POSITION_Y_SCORE = 300; //Position en Y du score
	private static final int POSITION_Y_GRAZE = 500; //Position en Y du graze
	
	//Variables d'affichage des vies
	private static final int COULEUR_VIE_ROUGE = 1; //A combien de vies la couleur du nombre devient rouge  ?
	private static final int COULEUR_VIE_ORANGE = 5; //Et orange ?
	private static final int POSITION_X_VIES = 675; //Position en X du nb de vies
	private static final int POSITION_Y_VIES = 137; //Position en Y du nb de vies
	
	//Variables qui gèrent le temps
	private static final double ECART_COURT = 0.015;
	private static final double ECART_LONG = 0.02;
	
	// ================================================== GERER LES ELEMENTS AFFICHES ================================================== \\
	/**
	 * Ajoute une image à l'écran
	 * @param [root]: L'endroit où on va l'afficher
	 * @param [imageURL]: Lien vers l'image à afficher
	 * @param [x]: Coordonnée x
	 * @param [y]: Coordonnée y
	 * @param [w]: Largeur de l'image
	 * @param [h]: Hauteur de l'image
	 * @author Jordan
	 */
	public static void ajouterImage(Pane root, String imageURL, double x, double y, double w, double h){
		ImageView img;
		
		try {
			img = new ImageView(new Image(new FileInputStream(imageURL)));
			img.setX(x); 
			img.setY(y);
			img.setFitHeight(h); //Change la taille
			img.setFitWidth(w); 
			root.getChildren().add(img);
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image " + imageURL + " est introuvable !", e);
		} 
	}
	
	/**
	 * Ajoute un texte à l'écran
	 * @param [x]: Coordonnée x
	 * @param [y]: Coordonnée y
	 * @param [texte]: Le texte a afficher
	 * @param [policeURL]: Le lien vers la police à utiliser
	 * @param [taille]: La taille
	 * @param [couleur]: Couleur du texte
	 */
	public static void ajouterTexte(Niveau niv, double x, double y, String texte, String policeURL, int taille, String couleur){
		Text t = new Text(x,y,texte);
		
		try {
			t.setFont(Font.loadFont(new FileInputStream(policeURL), taille)); //Charge la police et la change
		} catch (FileNotFoundException e) {
			Manipulation.erreur("La police " + policeURL + " n'a pas été trouvée! ", e);
		} 
		
		t.setFill(Paint.valueOf(couleur)); //Couleur de la police
		niv.getRoot().getChildren().add(t); //Ajout dans le niveau
	}

	/**
	 * Affiche un objet dans le décor
	 * @param [affichage]: L'objet d'affichage de la classe
	 * @param [n]: L'objet (Node: Classe très globale, qui englobe Sprite et d'autres)
	 * @author Jordan
	 */
	public static void ajouterJeu(Pane affichage, Node n) {
		affichage.getChildren().add(n);
	}
	
	/**
	 * Enlève de l'affichage l'objet en paramètre
	 * @param[n]: L'objet à "désafficher"
	 * @author Jordan
	 */
	public static void supprimer(Niveau niv, Object n) {
		niv.getRoot().getChildren().remove(n);
	}
	
	/**
	 * Efface le texte du dialogue pour en afficher un autre en réaffichant la zone de texte
	 * @author Jordan
	 */
	public static void effacerDialogue(Niveau niv) {
		//Ajout d'une image par dessus le texte pour "effacer"
		ajouterImage(niv.getRoot(), "Images/Dialogue.png", 0, 650, 585, 150);
	}
	
	
	/**
	 * Efface tout ce qui est affiché dans le niveau
	 * @param [niv]: Le niveau
	 * 
	 * @author Jordan
	 */
	public static void toutEffacer(Niveau niv) {
		//On enlève toutes les images
		Manipulation.images(niv.getRoot()).stream().forEach(e -> {
			niv.getRoot().getChildren().remove(e);
		});
		
		//Puis tous les textes
		Manipulation.textes(niv.getRoot()).stream().forEach(e -> {
			niv.getRoot().getChildren().remove(e);
		});
	}
	
	// ================================================== ENVOYER DES SPRITES ================================================== \\
	/**
	 * Renvoie la liste des sprites affichés pour pouvoir les mettre à jour d'un coup
	 * 		   Exemple: Tous ceux qui sont morts
	 * 
	 * @param[root]: L'objet qui contient tous les sprites du niveau où l'on a appelé cette méthode
	 * @author Jordan
	 */
	public static ArrayList<Sprite> sprites(Pane root){
		ArrayList<Sprite> res = new ArrayList<>();
		
		//Pour chaque objet affiché, si c'est un sprite....
		root.getChildren().forEach(n -> {if (n instanceof Sprite) {
			res.add((Sprite)n);//...On l'ajoute à la liste
		}});
		
		return res;
	}
	
	/**
	 * @brief Renvoie la liste des images de sprites affichées pour pouvoir les mettre à jour d'un coup
	 *         Exemple: Toutes celles qui sont mortes 
	 *   
	 * @param[root]: L'objet qui contient tous les sprites du niveau où l'on a appelé cette méthode
	 * @author Jordan
	 */
	public static ArrayList<ImageSprite> images(Pane root){
		ArrayList<ImageSprite> res = new ArrayList<>();
		
		//Pour chaque objet affiché, si c'est une image d'un sprite....
		root.getChildren().forEach(n -> {if (n instanceof ImageSprite) {
			res.add((ImageSprite)n);//...On l'ajoute à la liste
		}});
		
		return res;
	}
	
	/**
	 * Renvoie la liste des images de sprites affichées pour pouvoir les mettre à jour d'un coup
	 *         Exemple: Toutes celles qui sont mortes 
	 * 
	 * @param[root]: L'objet qui contient tous les sprites du niveau où l'on a appelé cette méthode
	 * @author Jordan
	 */
	public static ArrayList<Texte> textes(Pane root){
		ArrayList<Texte> t = new ArrayList<>();
		
		//Pour chaque objet affiché, si c'est une image d'un sprite....
		root.getChildren().forEach(n -> {if (n instanceof Texte) {
				t.add((Texte)n);
		}});
		
		return t;
	}
	
	
	// ================================================== GERER LE SCORE/GRAZE ================================================== \\
	/**
	 * Monte le score de 10 quand on touche un ennemi
	 * @param [n]: Niveau où l'on appelle cette méthode, (Où la méthode va s'effectuer)
	 * @author Jordan
	 */
	public static void scoreUp(Niveau n){
		n.addScore(NiveauInterface.NBPOINTS_ENNEMI_TOUCHE); //Augmentation du score
		updateScore(n); //Mise à jour de l'affichage
	}
	
	/**
	 * Monte le graze de 1 et score de 20 quand on frôle une balle/un ennemi
	 * @param [n]: Niveau où l'on appelle cette méthode, (Où la méthode va s'effectuer)
	 * @author Jordan
	 */
	public static void grazeUp(Niveau n){
		n.addGraze(); //Ajout +1 de graze
		n.addScore(NiveauInterface.NBPOINTS_GRAZE); //Augmentation du score 
		Manipulation.updateScore(n); //Mise à jour de l'affichage
	}

	/**
	 * Met à jour le score
	 * @param [n]: Niveau où l'on appelle cette méthode, (Où la méthode va s'effectuer)
	 * @author Jordan
	 */
	public static void updateScore(Niveau n){
		ArrayList<Text> textes = new ArrayList<>(); //Liste de tous les textes affichés
		
		//Pour chaque objet affiché, si c'est un texte....
		n.getRoot().getChildren().forEach(chose -> {if (chose instanceof Text) {
			if(!(chose instanceof Texte)) //Si c'est un texte spécial (Titre niveau, nomBOSS, etc.) on l'ajoute pas [On supprimera pas]
				textes.add((Text)chose);//...On l'ajoute à la liste
		}});
			
		//On retire tout les textes (Anciens score & graze)
		textes.stream().forEach(e -> {
			n.getRoot().getChildren().remove(e);
		});

		Manipulation.ajouterTexte(n, POSITION_X_SCORE,POSITION_Y_SCORE, Niveau.getScore().toString(), "Polices/SCORE.ttf", 30, "CYAN"); //Ajout de la nouvelle valeur du score
		Manipulation.ajouterTexte(n, POSITION_X_SCORE,POSITION_Y_GRAZE, Niveau.getGraze().toString(), "Polices/SCORE.ttf", 30, "CYAN"); //Et du graze
		
		//Pour la vie, couleur initiale (En mode facile)
		String couleur = "LIME"; //Vert clair
		
		if(Main.getVies() < COULEUR_VIE_ORANGE && Main.getVies() > COULEUR_VIE_ROUGE) //Si moitié de sa vie c'est en orange
			couleur = "ORANGE";
		else if(Main.getVies() == COULEUR_VIE_ROUGE)
				couleur = "RED"; //Si reste une, c'est en rouge
		
		//Mise à jour du nombre de vies 
		Manipulation.ajouterTexte(n, POSITION_X_VIES,POSITION_Y_VIES, Main.getVies().toString(), "Polices/SCORE.ttf", 50, couleur);
	}
	
	
	// ================================================== GERER LES COLLISIONS ================================================== \\
	/**
	 * Renvoie si oui ou non s1 touche s2 au niveau des coordonnées
	 * @param [s1]: Sprite qui touche
	 * @param [s2]: L'autre sprite
	 * @author Jordan
	 */
	public static boolean touche(Sprite s1, Sprite s2) {
		return s1.getBoundsInParent().intersects((s2.getBoundsInParent()));
	}

	/**
	 * Lorsque le joueur se fait toucher par ce qui a été entré en paramètre
	 * @param [n]: Niveau où l'on appelle cette méthode, (Où la méthode va s'effectuer)
	 * @param [ceQuiATouché]: L'objet qui a touché le joueur
	 * @param [ennemi]: Est-ce un ennemi ? False si c'était une balle
	 * @author Jordan
	 */
	public static void CollisionJoueur(Niveau n, Sprite ceQuiATouché, boolean ennemi){
		if(!n.isCooldownTouché()) {
			n.perdVie(); //Il perd une vie
			
			updateScore(n); //On met à jour le nombres de vies
			
			n.getJoueur().meurs(); //Le joueur "meurt"

			if(!ennemi) //Un ennemi ne meurt pas quand le joueur le touche
				ceQuiATouché.meurs(); //Donc si c'est une balle elle disparaît
			
			//Pour le cooldown de la regénération du joueur
			n.setCooldownTouché(true);
			n.setTpsDébTouché(Niveau.getTempsTotal());
			
			//On affiche l'image du joueur touché
			try {
				n.getJoueur().getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur_touché.png")));
			} catch (FileNotFoundException e) {
				Manipulation.erreur("Le sprite du joueur en regénération est introuvable!", e);
			}
		
			//Le son du joueur touché se joue
			if(Main.son()) new MediaPlayer(son).play();
		}
	}
	
	/**
	 * Renvoie si le temps est égal à la valeur entrée
	 * @param [tps]: Le temps où l'on va vérifier s'il est égal à une valeur
	 * @param [val]: A quelle valeur on vérifie que temps est égal à ?
	 * @param [court]: L'opération faite après l'appel de cette méthode est-t-elle rapide ?
	 * 				   Si c'est court et on met false, le programme peut le faire en double
	 * @author Jordan
	 */
	public static boolean siTpsA(double tps, double val, boolean court) {
		double ecart;
		if (court) 
			ecart = ECART_COURT;
		else
			ecart = ECART_LONG;
			
		//Le temps d'une frame explique le +0.015
		return (tps > val && tps < (val+ecart));
	}
	
	/**
	 * Doit être appelé constamment dans un "tps" entre 0 et 2 secondes
	 * Il va renvoyer true toutes les 0.5ms
	 * 
	 * @param [tps]: Le temps a vérifier
	 * @author Jordan
	 */
	public static boolean deuxFoisParSecondes(double tps) {
		return (Manipulation.siTpsA(tps, 0.48, true) || Manipulation.siTpsA(tps, 0.98, true) || Manipulation.siTpsA(tps, 1.48, true) || Manipulation.siTpsA(tps, 1.98, true));
	}
	
	/**
	 * Doit être appelé constamment dans un "tps" entre 0 et 2 secondes
	 * Il va renvoyer true toutes les 0.25ms
	 * 
	 * @param [tps]: Le temps a vérifier
	 * @author Jordan
	 */
	public static boolean quatreFoisParSecondes(double tps) {
		return (Manipulation.siTpsA(tps, 0.25, true) || Manipulation.siTpsA(tps, 0.5, true) || Manipulation.siTpsA(tps, 0.75, true) || Manipulation.siTpsA(tps, 1, true) ||
				Manipulation.siTpsA(tps, 1.25, true) || Manipulation.siTpsA(tps, 1.5, true) || Manipulation.siTpsA(tps, 1.75, true) || Manipulation.siTpsA(tps, 1.98, true));
	}
	
	/**
	 * Doit être appelé constamment dans un "tps" entre 0 et 2 secondes
	 * Il va renvoyer true toutes les 0.125ms
	 * 
	 * @param [tps]: Le temps a vérifier
	 * @author Jordan
	 */
	public static boolean huitFoisParSecondes(double tps) {
		return (Manipulation.siTpsA(tps, 0.125, true) || Manipulation.siTpsA(tps, 0.25, true) || Manipulation.siTpsA(tps, 0.375, true) || Manipulation.siTpsA(tps, 0.5, true) ||
				Manipulation.siTpsA(tps, 0.625, true) || Manipulation.siTpsA(tps, 0.75, true) || Manipulation.siTpsA(tps, 0.825, true) || Manipulation.siTpsA(tps, 1, true) ||
				Manipulation.siTpsA(tps, 1.125, true) || Manipulation.siTpsA(tps, 1.25, true) || Manipulation.siTpsA(tps, 1.375, true) || Manipulation.siTpsA(tps, 1.5, true) ||
				Manipulation.siTpsA(tps, 1.625, true) || Manipulation.siTpsA(tps, 1.75, true) || Manipulation.siTpsA(tps, 1.825, true) || Manipulation.siTpsA(tps, 1.98, true));
	}
	
	/**
	 * Affiche un message d'erreur
	 * @param[titre]: Le nom de l'erreur
	 * @param[e]: L'exception 
	 * @author Jordan
	 */
	public static void erreur(String titre, Exception e) {
		System.out.println(titre); //Affichage de l'erreur
		e.printStackTrace(); //Affichage de la pile d'exécution
	}
}
