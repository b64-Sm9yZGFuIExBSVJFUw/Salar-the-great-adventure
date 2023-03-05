package balles;

import java.io.FileNotFoundException;

import niveau.Niveau;
import sprite.Sprite;

/**
 * Une balle
 * 
 * @author Jordan LAIRES
 */
public class Balle extends Sprite {

	/**
	 * Constructeur d'une balle
	 * @param x Position en x
	 * @param y Position en y
	 * @param w Largeur
	 * @param h Hauteur
	 * @param imageURL Image de la balle
	 * @param t Type de balle
	 * @param n Dans quel niveau
	 * @throws FileNotFoundException
	 * @author Jordan
	 */
	Balle(double x, double y, int w, int h, String imageURL, String t, Niveau n) throws FileNotFoundException {
		super(x, y, w, h, imageURL, t, n);
	}

	//Ces m�thodes se sp�cialiseront plus tard 
	public void bougerBas(double divis�) {
	}

	public void bougerHaut(double divis�) {
	}

	public void bougerGauche(double divis�) {
	}

	public void bougerDroite(double divis�) {
	}

	public void tir(String type) {
	}

}
