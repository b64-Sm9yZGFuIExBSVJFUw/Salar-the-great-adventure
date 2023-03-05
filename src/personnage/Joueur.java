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
	private boolean focus = false; //Est-t-il en mode focus ? (Bouge plus lentement pour �tre plus pr�cis)
	private boolean estMAJ = false; //Le joueur qui �tait en cooldown et qui ne l'est plus: �a a d�j� �t� mis � jour ?
	private int puissance = 1;
	private static final String PATH_JOUEUR_BALLE_MAX = "Images/joueur balle max.png";
	private static final String PATH_JOUEUR_BALLE = "Images/joueur balle.png";

	/**
	 * Constructeur du joueur qui peut renvoyer une exception si l'image du joueur n'est pas trouv�e
	 * @param x :  Coordonn�es de x
	 * @param y : Coordonn�es de y
	 * @param w: Largeur de l'entit�
	 * @param h: Hauteur de l'entit� 
	 * @param t: Type de sprite
	 * @param imageURL: Lien de l'image
	 * @param n: Le niveau o� il doit �tre affich�
	 * @author Jordan
	 */
	public Joueur(double x, double y, int w, int h, String t, String imageURL, Niveau n){
		super(x, y, w, h, imageURL, t, n); //Constructeur du personnage
	}

	/**
	 * Met le joueur en mode focus (Bouge plus lentement pour �tre plus pr�cis)
	 *        Peut renvoyer une exception si l'image du joueur en mode focus n'est pas trouv�e
	 * @author Jordan
	 */
	public void focus(){
		focus = true; //Il passe en true du coup

		Sprite j = (Sprite)this;

		if(getType().equals("joueur") && !j.getNiveau().enTrainDeTirer())//Pour ne pas afficher aussi la grazebox
				try {
					getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur_focus.png"))); //L'image du joueur change (Eclairs verts)
				} catch (FileNotFoundException e) {
					Manipulation.erreur("Le sprite du joueur en focus n'a pas pu �tre trouv� !", e);	
				} 
		}

	/**
	 * Passe le joueur en mode focus en mode normal et renvoie une exception pour la m�me raison qu'avant
	 * @author Jordan
	 */
	public void finFocus(){
		focus = false;

		if(getType().equals("joueur")) { //Pour ne pas afficher aussi la grazebox
			if(!getNiveau().isCooldownTouch�()) {
				try {
					getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur.png")));
				} catch (FileNotFoundException e) {
					Manipulation.erreur("Le sprite du joueur n'a pas pu �tre trouv� !",e);

				}
			}else {//Toujours en cooldown
				try {
					getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur_touch�.png")));
				} catch (FileNotFoundException e) {
					Manipulation.erreur( "Le sprite du joueur en reg�n�ration n'a pas pu �tre trouv� !", e);	
				} 
			}
		}
	}

	/**
	 * Renvoie si le sprite du joueur a d�j� �t� mis � jour
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
			updateImagePos(); //Mise � jour de la position de l'image
		}
		else {
			if(Niveau.isVsSalar()) vitesse=NiveauInterface.VITESSE_JOUEUR_VS_SALAR; //Contre Salar, le joueur va plus vite

			setTranslateX(getTranslateX() - vitesse/diviseur);
			updateImagePos();
		}

		/*
		 * Afin d'�viter que le joueur "sorte" du niveau (d�cor), lorsque qu'il va se d�placer,
		 * on va le replacer en arri�re et �a � l'infini tant qu'il tentera de sortir du d�cor
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
			//Afin d'�viter que le joueur sorte du d�cor
			if(getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 45 && !focus)
				setTranslateX(getTranslateX() - vitesse/diviseur);
			else if(getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 45 && focus)
				setTranslateX(getTranslateX() - (vitesse/3)/diviseur);
		}else { //...Alors le joueur peut aller plus loin
			//Afin d'�viter que le joueur sorte du d�cor
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

		//Afin d'�viter que le joueur sorte du d�cor
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

		//Afin d'�viter que le joueur sorte du d�cor
		if(getTranslateY() >= NiveauInterface.HAUTEUR_ECRAN-29 && !focus)
			setTranslateY(getTranslateY() - vitesse/diviseur);
		else if(getTranslateY() >= NiveauInterface.HAUTEUR_ECRAN-29 && focus)
			setTranslateY(getTranslateY() - (vitesse/3)/diviseur);
	}

	@Override
	public void tir(String type){	
		if(!Niveau.isVsSalar()) Main.jouerSon("Sons/tir.mp3"); //On joue le son ! (Le son du tir ne se joue pas si on est contre Salar)

		Sprite j = (Sprite)this; //Pour utiliser cooldownTouch�()

		//Selon la puissance...
		try {
			switch(puissance) {
			case 1: //Une seule balle, les mouvements des balles gauche/droites sont trait�s dans le niveau (Update)
				if(!Niveau.isVsSalar()) //Pas contre Salar (ce n'est pas le m�me sprite ni la m�me taille)
					new JoueurBalle(getTranslateX() + 15, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet", getNiveau()); //Normale (Tout droit)
				else //Contre Salar, la balle est corrompue
					new JoueurBalle(getTranslateX() + 15, getTranslateY(), 25, 25,"Images/Niveau/balle.gif", "joueur bullet", getNiveau()); //Normale (Tout droit) mais corrompue
				break; 

			case 2: //Deux balles c�te � c�te
				new JoueurBalle(getTranslateX() + 5, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet", getNiveau()); //Normale (Tout droit)
				new JoueurBalle(getTranslateX() + 15, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet", getNiveau());//Normale (Tout droit)
				break;

			case 3: //Deux balles c�te � c�te et une qui va vers la gauche et une autre vers la droite 
				new JoueurBalle(getTranslateX() + 5, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet", getNiveau()); //Normale (Tout droit)
				new JoueurBalle(getTranslateX() + 15, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet", getNiveau()); //Normale (Tout droit)
				new JoueurBalle(getTranslateX() - 5, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet g", getNiveau()); //G = Gauche
				new JoueurBalle(getTranslateX() + 25, getTranslateY(), 10, 10,PATH_JOUEUR_BALLE, "joueur bullet d", getNiveau()); //D = Droite
				break;

				//Default: Forc�ment 4 (Puissance max)
			default: //Deux balles c�te � c�te et deux qui vont vers la gauche et deux autres vers la droite [Des deux c�t�s, une balle ira plus loin vers la gauche/droite]
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
			else if (j.getNiveau().isCooldownTouch�())
				getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur_touch�_tir.png"))); //Sprite quand il tire en reg�n�ration
			else 
				if(!Niveau.isVsSalar()) getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur_tir.png"))); //Sprite quand il tire (Contre Salar il n'a pas le m�me sprite on ne l'affiche pas)
		} 
		catch (FileNotFoundException e) { //Si image de la balle non trouv�e
			Manipulation.erreur("Un sprite n'a pas �t� trouv� !", e);	
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

		puissance = 1; //Il retourne � la puissance initiale
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
