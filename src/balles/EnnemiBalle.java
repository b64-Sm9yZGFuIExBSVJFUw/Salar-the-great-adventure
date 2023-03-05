package balles;
import java.io.FileNotFoundException;



import niveau.Niveau;
import niveau.NiveauInterface;

/**
 * Balle d'un ennemi
 * 
 * @author Jordan LAIRES
 *
 */
public class EnnemiBalle extends Balle{


	/**
	 * @brief Construscteur d'une balle tirée par un ennemi
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
	public EnnemiBalle(double x, double y, int w, int h, String imageURL, String t, Niveau n) throws FileNotFoundException {
		super(x, y, w, h, imageURL, t, n);
	}

	@Override
	public void bougerBas(double divisé) {
		setTranslateY(getTranslateY() + NiveauInterface.VITESSE_BALLE_ENNEMIE/divisé);
		updateImagePos();

		if(dehors()) //Si elle sort de l'écran
			meurs();
	}

	@Override
	public void bougerGauche(double divisé) {
		setTranslateX(getTranslateX() - NiveauInterface.VITESSE_BALLE_ENNEMIE/divisé);
		updateImagePos();

		if(dehors()) //Si elle sort de l'écran
			meurs();
	}

	@Override
	public void bougerDroite(double divisé) {
		setTranslateX(getTranslateX() + NiveauInterface.VITESSE_BALLE_ENNEMIE/divisé);
		updateImagePos();

		if(dehors()) //Si elle sort de l'écran
			meurs();
	}

	@Override
	public void bougerHaut(double divisé) {
		setTranslateY(getTranslateY() - NiveauInterface.VITESSE_BALLE_ENNEMIE/divisé);
		updateImagePos();

		if(dehors()) //Si elle sort de l'écran
			meurs();
	}
}
