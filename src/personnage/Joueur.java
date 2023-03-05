package personnage;

import balles.JoueurBalle;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import appli.Main;
import javafx.scene.image.Image;
import manipulation.Manipulation;
import niveau.Niveau;
import niveau.NiveauInterface;
import sprite.Sprite;

/**
 * Le joueur
 * 
 * @author Jordan LAIRES
 *
 */
public class Joueur extends Personnage{
	private double vitesse = NiveauInterface.VITESSE_JOUEUR; //Vitesse du joueur
	private boolean focus = false; //Est-t-il en mode focus ? (Bouge plus lentement pour être plus précis)
	private boolean estMAJ = false; //Le joueur qui était en cooldown et qui ne l'est plus: ça a déjà été mis à jour ?
	private int puissance = 1;
	private static final String PATH_JOUEUR_BALLE_MAX = "Images/joueur balle max.png";
	private static final String PATH_JOUEUR_BALLE = "Images/joueur balle.png";

	/**
	 * Constructeur du joueur qui peut renvoyer une exception si l'image du joueur n'est pas trouvée
	 * @param x :  Coordonnées de x
	 * @param y : Coordonnées de y
	 * @param w: Largeur de l'entité
	 * @param h: Hauteur de l'entité 
	 * @param t: Type de sprite
	 * @param imageURL: Lien de l'image
	 * @param n: Le niveau où il doit être affiché
	 * @author Jordan
	 */
	public Joueur(double x, double y, int w, int h, String t, String imageURL, Niveau n){
		super(x, y, w, h, imageURL, t, n); //Constructeur du personnage
	}

	/**
	 * Met le joueur en mode focus (Bouge plus lentement pour être plus précis)
	 *        Peut renvoyer une exception si l'image du joueur en mode focus n'est pas trouvée
	 * @author Jordan
	 */
	public void focus(){
		focus = true; //Il passe en true du coup

		Sprite j = (Sprite)this;

		if(getType().equals("joueur") && !j.getNiveau().enTrainDeTirer())//Pour ne pas afficher aussi la grazebox
				try {
					getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur_focus.png"))); //L'image du joueur change (Eclairs verts)
				} catch (FileNotFoundException e) {
					Manipulation.erreur("Le sprite du joueur en focus n'a pas pu être trouvé !", e);	
				} 
		}

	/**
	 * Passe le joueur en mode focus en mode normal et renvoie une exception pour la même raison qu'avant
	 * @author Jordan
	 */
	public void finFocus(){
		focus = false;

		if(getType().equals("joueur")) { //Pour ne pas afficher aussi la grazebox
			if(!getNiveau().isCooldownTouché()) {
				try {
					getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur.png")));
				} catch (FileNotFoundException e) {
					Manipulation.erreur("Le sprite du joueur n'a pas pu être trouvé !",e);

				}
			}else {//Toujours en cooldown
				try {
					getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur_touché.png")));
				} catch (FileNotFoundException e) {
					Manipulation.erreur( "Le sprite du joueur en regénération n'a pas pu être trouvé !", e);	
				} 
			}
		}
	}

	/**
	 * Renvoie si le sprite du joueur a déjà été mis à jour
	 * @author Jordan
	 */
	public boolean aEteMAJ() {
		return estMAJ;
	}

	/**
	 * Change la valeur de MAJ
	 * @param maj: Nouvelle valeur
	 * @author Jordan
	 */
	public void misAJour(boolean maj) {
		estMAJ = maj;
	}

	@Override
	public void bougerGauche(double diviseur) {
		if(focus) { //Si en mode focus il va bouger plus lentement
			setTranslateX(getTranslateX() - (vitesse/3)/diviseur); //Modification de la position en X
			updateImagePos(); //Mise à jour de la position de l'image
		}
		else {
			if(Niveau.isVsSalar()) vitesse=NiveauInterface.VITESSE_JOUEUR_VS_SALAR; //Contre Salar, le joueur va plus vite

			setTranslateX(getTranslateX() - vitesse/diviseur);
			updateImagePos();
		}

		/*
		 * Afin d'éviter que le joueur "sorte" du niveau (décor), lorsque qu'il va se déplacer,
		 * on va le replacer en arrière et ça à l'infini tant qu'il tentera de sortir du décor
		 */
		if(getTranslateX() <= 4 && !focus) 
			setTranslateX(getTranslateX() + vitesse/diviseur);
		else if(getTranslateX() <= 4 && focus)
			setTranslateX(getTranslateX() + (vitesse/3)/diviseur);
	}

	@Override
	public void bougerDroite(double diviseur) {
		if(focus) {
			setTranslateX(getTranslateX() + (vitesse/3)/diviseur);
			updateImagePos();
		}
		else {
			if(Niveau.isVsSalar()) vitesse=NiveauInterface.VITESSE_JOUEUR_VS_SALAR; //Contre Salar, le joueur va plus vite

			setTranslateX(getTranslateX() + (vitesse)/diviseur);
			updateImagePos();
		}

		if(!Niveau.isVsSalar()) { //Contre Salar le niveau est plus grand...
			//Afin d'éviter que le joueur sorte du décor
			if(getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 45 && !focus)
				setTranslateX(getTranslateX() - vitesse/diviseur);
			else if(getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 45 && focus)
				setTranslateX(getTranslateX() - (vitesse/3)/diviseur);
		}else { //...Alors le joueur peut aller plus loin
			//Afin d'éviter que le joueur sorte du décor
			if(getTranslateX() >= NiveauInterface.LARGEUR_ECRAN - 45 && !focus)
				setTranslateX(getTranslateX() - vitesse/diviseur);
		}
	}

	@Override
	public void bougerHaut(double diviseur) {
		if(focus) {
			setTranslateY(getTranslateY() - (vitesse/3)/diviseur);
			updateImagePos();
		}
		else {
			if(Niveau.isVsSalar()) vitesse=NiveauInterface.VITESSE_JOUEUR_VS_SALAR; //Contre Salar, le joueur va plus vite

			setTranslateY(getTranslateY() - vitesse/diviseur);
			updateImagePos();
		}

		//Afin d'éviter que le joueur sorte du décor
		if(getTranslateY() <= 0 && !focus)
			setTranslateY(getTranslateY() + vitesse/diviseur);
		else if(getTranslateY() <= 0 && focus)
			setTranslateY(getTranslateY() + (vitesse/3)/diviseur);

	}

	@Override
	public void bougerBas(double diviseur) {
		if(focus) {
			setTranslateY(getTranslateY() + (vitesse/3));    
			updateImagePos();
		}
		else {
			if(Niveau.isVsSalar()) vitesse=NiveauInterface.VITESSE_JOUEUR_VS_SALAR; //Contre Salar, le joueur va plus vite

			setTranslateY(getTranslateY() + vitesse/diviseur);
			updateImagePos();
		}

		//Afin d'éviter que le joueur sorte du décor
		if(getTranslateY() >= NiveauInterface.HAUTEUR_ECRAN-29 && !focus)
			setTranslateY(getTranslateY() - vitesse/diviseur);
		else if(getTranslateY() >= NiveauInterface.HAUTEUR_ECRAN-29 && focus)
			setTranslateY(getTranslateY() - (vitesse/3)/diviseur);
	}

	@Override
	public void tir(String type){	
		if(!Niveau.isVsSalar()) Main.jouerSon("Sons/tir.mp3"); //On joue le son ! (Le son du tir ne se joue pas si on est contre Salar)

		Sprite j = (Sprite)this; //Pour utiliser cooldownTouché()

		//Selon la puissance...
		try {
			switch(puissance) {
			case 1: //Une seule balle, les mouvements des balles gauche/droites sont traités dans le niveau (Update)
				if(!Niveau.isVsSalar()) //Pas contre Salar (ce n'est pas le même sprite ni la même taille)
					new JoueurBalle(getTranslateX() + 15, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet", getNiveau()); //Normale (Tout droit)
				else //Contre Salar, la balle est corrompue
					new JoueurBalle(getTranslateX() + 15, getTranslateY(), 25, 25,"Images/Niveau/balle.gif", "joueur bullet", getNiveau()); //Normale (Tout droit) mais corrompue
				break; 

			case 2: //Deux balles côte à côte
				new JoueurBalle(getTranslateX() + 5, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet", getNiveau()); //Normale (Tout droit)
				new JoueurBalle(getTranslateX() + 15, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet", getNiveau());//Normale (Tout droit)
				break;

			case 3: //Deux balles côte à côte et une qui va vers la gauche et une autre vers la droite 
				new JoueurBalle(getTranslateX() + 5, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet", getNiveau()); //Normale (Tout droit)
				new JoueurBalle(getTranslateX() + 15, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet", getNiveau()); //Normale (Tout droit)
				new JoueurBalle(getTranslateX() - 5, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet g", getNiveau()); //G = Gauche
				new JoueurBalle(getTranslateX() + 25, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet d", getNiveau()); //D = Droite
				break;

				//Default: Forcément 4 (Puissance max)
			default: //Deux balles côte à côte et deux qui vont vers la gauche et deux autres vers la droite [Des deux côtés, une balle ira plus loin vers la gauche/droite]
				new JoueurBalle(getTranslateX() + 5, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE_MAX, "joueur bullet", getNiveau()); //Normale (Toute droit)
				new JoueurBalle(getTranslateX() + 15, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE_MAX, "joueur bullet", getNiveau()); //Normale (Tout droit)
				new JoueurBalle(getTranslateX() - 5, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE_MAX, "joueur bullet g", getNiveau()); //G = Gauche
				new JoueurBalle(getTranslateX() + 25, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE_MAX, "joueur bullet d", getNiveau()); //D = Droite
				new JoueurBalle(getTranslateX() - 15, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE_MAX, "joueur bullet gg", getNiveau()); //G = Gauche mais + loin
				new JoueurBalle(getTranslateX() + 35, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE_MAX, "joueur bullet dd", getNiveau()); //D = Droite mais + loin
				break;
			}


			if(focus)
				getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur_focus_tir.png"))); //Sprite quand il tire en focus
			else if (j.getNiveau().isCooldownTouché())
				getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur_touché_tir.png"))); //Sprite quand il tire en regénération
			else 
				if(!Niveau.isVsSalar()) getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur_tir.png"))); //Sprite quand il tire (Contre Salar il n'a pas le même sprite on ne l'affiche pas)
		} 
		catch (FileNotFoundException e) { //Si image de la balle non trouvée
			Manipulation.erreur("Un sprite n'a pas été trouvé !", e);	
		}
	}

	/**
	 * Augmente la puissance
	 * @author Jordan
	 */
	public void powerUp() {
		if(puissance < NiveauInterface.PUISSANCE_MAX)
			puissance++;
	}

	@Override
	public void meurs() {
		super.meurs();

		puissance = 1; //Il retourne à la puissance initiale
	}

	/**
	 * Retourne la puissance
	 * 
	 * @author Jordan
	 */
	public int getPuissance() {
		return puissance;
	}
}
