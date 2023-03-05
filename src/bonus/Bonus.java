package bonus;
import java.io.FileNotFoundException;

import niveau.Niveau;
import niveau.NiveauInterface;
import sprite.Sprite;

/**
 * @brief Les bonus (Score, Puissance, Vie)
 * 
 * @author Jordan LAIRES
 */

public abstract class Bonus extends Sprite{
	
	/**
	 * Constructeur de la classe abtraite des diff�rents bonus possibles durant une partie
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param imageURL
	 * @param n
	 * @throws FileNotFoundException
	 * @author Jordan
	 */
	public Bonus(double x, double y, double w, double h, String imageURL, Niveau n)
			throws FileNotFoundException {
		super(x, y, w, h, imageURL, "bonus", n);
	}

	/**
	 * Le joueur a pris le bonus
	 */
	public abstract void pris();
	
	/**
	 * Le bonus tombe vers le bas
	 */
	public void bougerBas(double divis�) {
		setTranslateY(getTranslateY() + (NiveauInterface.VITESSE_BONUS/divis�));
		updateImagePos();
		
		if(dehors())
			meurs();
	}
	
	//Le bonus tombera toujours vers le bas et ne tire pas: Inutile de coder ces fonctions.
	public void bougerHaut(double divis�) {}
	
	public void bougerGauche(double divis�) {}
	
	public void bougerDroite(double divis�) {}
	
	public void tir(String type){}
}
