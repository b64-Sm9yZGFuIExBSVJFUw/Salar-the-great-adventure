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
	 * Constructeur d'une balle tir�e par le BOSS
	 * @param x : Coordonn�es de x
	 * @param y : Coordonn�es de y
	 * @param imageURL : Le sprite de la balle
	 * @param t : Type de balle
	 * @param b : Issue de quel boss
	 * @param n : Le niveau o� il doit �tre affich�
	 * @author Jordan
	 */
	public BossBalle(double x, double y, String imageURL, String t, Niveau n) throws FileNotFoundException {
		super(x, y, NiveauInterface.TAILLE_BALLE_BOSS, NiveauInterface.TAILLE_BALLE_BOSS, imageURL, t, n);
	}


	@Override
	public void bougerBas(double divis�) {
		//Bouge en Y
		setTranslateY(getTranslateY() +  NiveauInterface.VITESSE_BALLE_BOSS/divis�);	
		updateImagePos(); //Met � jour la position de la balle

		if(dehors()) //Si elle sort de l'�cran
			meurs();
	}

	public void bougerHaut(double divis�) {
		setTranslateY(getTranslateY() - NiveauInterface.VITESSE_BALLE_BOSS/divis�);
		updateImagePos();

		if(dehors()) //Si elle sort de l'�cran
			meurs();	
	}

	public void bougerGauche(double divis�) {
		setTranslateX(getTranslateX() - NiveauInterface.VITESSE_BALLE_BOSS/divis�);
		updateImagePos();

		if(dehors()) //Si elle sort de l'�cran
			meurs();
	}

	public void bougerDroite(double divis�) {
		setTranslateX(getTranslateX() + NiveauInterface.VITESSE_BALLE_BOSS/divis�);
		updateImagePos();

		if(dehors()) //Si elle sort de l'�cran
			meurs();
	}

}