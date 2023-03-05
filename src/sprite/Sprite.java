package sprite;
import java.io.FileInputStream;




import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import manipulation.Manipulation;
import niveau.Niveau;
import niveau.NiveauInterface;

/**
 * Un sprite.
 * 
 * @author Jordan LAIRES
 *
 */
public abstract class Sprite extends Rectangle{
	private String type;
	private Image sprite; //L'image en elle-même
	private ImageSprite img; //L'image SUR le sprite
	private Niveau n;
	
	/***
	 *  Constructeur d'un sprite
	 * @param x: Coordonnées de x
	 * @param y: Coordonnées de y
	 * @param w: Largeur de l'entité
	 * @param h: Hauteur de l'entité
	 * @param imageURL: Lien de l'image
	 * @param t: Type de sprite
	 * @param n: Le niveau où il doit être affiché
	 * @author Jordan
	 */
	public Sprite(double x, double y, double w, double h, String imageURL, String t, Niveau n){
		super(w,h); //Dessine un rectangle de dimension w,h
		setFill(Paint.valueOf("TRANSPARENT")); //Par défaut le sprite est transparent
		setTranslateX(x); //On le place dans les coordonnées données
		setTranslateY(y);
		type = t;
		this.n = n;
		Manipulation.ajouterJeu(n.getRoot(), this); //On ajoute au jeu
		
		try {
			sprite = new Image(new FileInputStream(imageURL));
		} catch (FileNotFoundException e) {
			Manipulation.erreur("Le sprite n'a pas été trouvé !", e);
		}  
		
		img = new ImageSprite(sprite, n); 
		img.relocate(x, y); //Replace l'image aux coordonnées x et y [Par défaut 0, 0]
		img.setFitWidth(w);
		img.setFitHeight(h); 
		Manipulation.ajouterJeu(n.getRoot(), img); //On ajoute l'image
	}
	
	/**
	 *  "Tue" le sprite
	 *  @author Jordan
	 */
	public void meurs() {
		if(type.equals("joueur")) { //Si c'est le joueur
	
		}else {
			Manipulation.supprimer(n, this);
			img.meurs(); //Tue l'image avec lui		
		}
	}
	
	/**
	 *  Renvoie si oui ou non le sprite sort du décor
	 *  @author Jordan
	 */
	public boolean dehors() {
		boolean parLaGauche = false; //S'il sort vers la gauche du décor
		boolean parLaDroite = false;
		
		parLaGauche = (getTranslateX() < -20);
		
		if(!Niveau.isVsSalar()) { //Contre Salar, la fenêtre de jeu est plus grande...
			//Si c'est une petite balle (Toutes sauf celles du boss du niveau VI [Dans une attaque: Balles aléatoires de partout])
			if((type.contains("joueur bullet") || type.contains("balle") || type.contains("rebondissante")) && getWidth() < 30) 
				parLaDroite = (getTranslateX() > NiveauInterface.LARGEUR_DECOR - 25); 
			else if(getWidth() == 50) //Si c'est un grand sprite (Le seul: Grosses flammes du boss du niveau VI
				parLaDroite = (getTranslateX() > NiveauInterface.LARGEUR_DECOR - 60);
			else //Si l'ennemi meurt comme la balle, comme il est plus large on le voit survoler le score
				parLaDroite = (getTranslateX() > NiveauInterface.LARGEUR_DECOR - 55);
		}else { //...Donc les sprites peuvent aller plus loin
			parLaDroite = (getTranslateX() > NiveauInterface.LARGEUR_ECRAN + 50);
		}
		
		boolean parLeHaut = (getTranslateY() < -200);
		boolean parLeBas = (getTranslateY() > NiveauInterface.HAUTEUR_ECRAN + 50);
		
		//Si un des 4 est vrai, il sort du décor donc il renvoie True
		return parLaGauche || parLaDroite || parLeHaut || parLeBas;
	}
	
	/**
	 *  Retourne le type de sprite
	 *  @author Jordan
	 */
	public String getType() {
		return type;
	}
	
	/**
	 *  Retourne le niveau où le sprite est affiché
	 *  @author Jordan
	 */
	public Niveau getNiveau() {
		return n;
	}
	
	/**
	 *  Changer le type du sprite
	 * @param t: Le type
	 * @author Jordan
	 */
	public void setType(String t) {
		type = t;
	}
	
	/**
	 *  Met à jour la position de l'image
	 *  @author Jordan
	 */
	public void updateImagePos() {
		//Réaffiche l'image aux coordonnées
		img.relocate(getTranslateX(), getTranslateY());
	}
	
	/**
	 *  Renvoie l'image du sprite
	 *  @author Jordan
	 */
	public ImageSprite getImg() {
		return img;
	}
	
	/**
	 *  Change l'image du sprite
	 * @param imageURL: Lien de la nouvelle image
	 */
	public void setImg(String imageURL) throws FileNotFoundException {
		try {
			sprite = new Image(new FileInputStream(imageURL));
		} catch (FileNotFoundException e) {
			Manipulation.erreur("Le sprite n'a pas été trouvé !", e);
			
		}  
		img = new ImageSprite(sprite, n); 
		img.relocate(getTranslateX(), getTranslateY()); //Replace l'image aux coordonnées x et y [Par défaut 0, 0]
		img.setFitWidth(getWidth());
		img.setFitHeight(getHeight()); 
		Manipulation.ajouterJeu(n.getRoot(), img); //On ajoute l'image dans le jeu
	}
	
	//Les mouvements
	public abstract void bougerBas(double diviseur);
	public abstract void bougerHaut(double diviseur);
	public abstract void bougerGauche(double diviseur);
	public abstract void bougerDroite(double diviseur);
	
	/**
	 *  Tir du personnage
	 * @param type: Type de balle pour après la retrouver
	 * @author Jordan
	 */
	public abstract void tir(String type);
}
