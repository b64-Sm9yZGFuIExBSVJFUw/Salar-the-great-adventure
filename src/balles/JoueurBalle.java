package balles;
import java.io.FileNotFoundException;

import niveau.Niveau;
import niveau.NiveauInterface;

/**
 * Balle du joueur
 * 
 * @author Jordan LAIRES
 *
 */
public class JoueurBalle extends Balle{
	
	/**
	 * @brief Construction d'une balle tiré par le boss
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param imageURL
	 * @param t
	 * @param n
	 * @throws FileNotFoundException
	 * @author Jordan
	 */
	public JoueurBalle(double x, double y, int w, int h, String imageURL, String t, Niveau n) throws FileNotFoundException {
		super(x, y, w, h, imageURL, t, n);
	}
	
	public void bougerHaut(double divisé){
			setTranslateY(getTranslateY() - NiveauInterface.VITESSE_BALLE_JOUEUR/divisé);
			updateImagePos();
			
			if(dehors()) //Si elle sort de l'écran
				meurs();
	}
	
	public void bougerGauche(double divisé) {
		setTranslateX(getTranslateX() - NiveauInterface.VITESSE_BALLE_JOUEUR/divisé);
		updateImagePos();
		
		if(dehors()) //Si elle sort de l'écran
			meurs();
	}
	
	public void bougerDroite(double divisé) {
		setTranslateX(getTranslateX() + NiveauInterface.VITESSE_BALLE_JOUEUR/divisé);
		updateImagePos();
		
		if(dehors()) //Si elle sort de l'écran
			meurs();
	}
}
