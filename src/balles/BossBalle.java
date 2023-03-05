package balles;
import java.io.FileNotFoundException;


import niveau.Niveau;
import niveau.NiveauInterface;

/**
 * Balle d'un BOSS
 * 
 * @author Jordan LAIRES
 */
public class BossBalle extends Balle{

	/**
	 * Constructeur d'une balle tirée par le BOSS
	 * @param x : Coordonnées de x
	 * @param y : Coordonnées de y
	 * @param imageURL : Le sprite de la balle
	 * @param t : Type de balle
	 * @param b : Issue de quel boss
	 * @param n : Le niveau où il doit être affiché
	 * @author Jordan
	 */
	public BossBalle(double x, double y, String imageURL, String t, Niveau n) throws FileNotFoundException {
		super(x, y, NiveauInterface.TAILLE_BALLE_BOSS, NiveauInterface.TAILLE_BALLE_BOSS, imageURL, t, n);
	}


	@Override
	public void bougerBas(double divisé) {
		//Bouge en Y
		setTranslateY(getTranslateY() +  NiveauInterface.VITESSE_BALLE_BOSS/divisé);	
		updateImagePos(); //Met à jour la position de la balle

		if(dehors()) //Si elle sort de l'écran
			meurs();
	}

	public void bougerHaut(double divisé) {
		setTranslateY(getTranslateY() - NiveauInterface.VITESSE_BALLE_BOSS/divisé);
		updateImagePos();

		if(dehors()) //Si elle sort de l'écran
			meurs();	
	}

	public void bougerGauche(double divisé) {
		setTranslateX(getTranslateX() - NiveauInterface.VITESSE_BALLE_BOSS/divisé);
		updateImagePos();

		if(dehors()) //Si elle sort de l'écran
			meurs();
	}

	public void bougerDroite(double divisé) {
		setTranslateX(getTranslateX() + NiveauInterface.VITESSE_BALLE_BOSS/divisé);
		updateImagePos();

		if(dehors()) //Si elle sort de l'écran
			meurs();
	}

}