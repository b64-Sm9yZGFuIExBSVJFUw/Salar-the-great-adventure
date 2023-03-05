package personnage;
import java.io.FileNotFoundException;

import balles.EnnemiBalle;
import manipulation.Manipulation;
import niveau.Niveau;
import niveau.NiveauInterface;

/**
 * Classe d'un ennemi
 * 
 * @author Jordan LAIRES
 *
 */
public class Ennemi extends Personnage{
	
	/**
	 * Constructeur de l'ennemi qui peut renvoyer une exception si l'image de l'ennemi n'est pas trouvée
	 * @param x : Coordonnées de x
	 * @param y : Coordonnées de y
	 * @param w : Largeur de l'entité
	 * @param h : Hauteur de l'entité
	 * @param imageURL : Lien de l'image
	 * @param t : Type de sprite
	 * @param n : Le niveau où il doit être affiché
	 */
	public Ennemi(double x, double y, int w, int h, String imageURL, String t, Niveau n){
		super(x, y, w, h, imageURL, t, n); //Constructeur de personnage
	}

	@Override
	public void bougerBas(double diviseur) {
		setTranslateY(getTranslateY() + NiveauInterface.VITESSE_ENNEMI/diviseur);
		updateImagePos();
		
		if(dehors()) //S'il sort de l'écran
			meurs();
	}

	@Override
	public void bougerHaut(double diviseur) {
		setTranslateY(getTranslateY() - NiveauInterface.VITESSE_ENNEMI/diviseur);
		updateImagePos();
		
		if(dehors()) //S'il sort de l'écran
			meurs();
	}

	@Override
	public void bougerGauche(double diviseur) {
		setTranslateX(getTranslateX() - NiveauInterface.VITESSE_ENNEMI/diviseur);
		
		updateImagePos();
		
		if(dehors()) //S'il sort de l'écran
			meurs();
	}

	@Override
	public void bougerDroite(double diviseur) {
		setTranslateX(getTranslateX() + NiveauInterface.VITESSE_ENNEMI/diviseur);
		
		updateImagePos();
		
		if(dehors()) //S'il sort de l'écran
			meurs();
	}

	@Override
	public void tir(String type){
		//On crée une balle (Voir constructeur pour plus de détails)
		try {
			new EnnemiBalle(getTranslateX(), getTranslateY()+35, 20, 20, "Images/ennemi balle.png", type, getNiveau());
		} catch (FileNotFoundException e) {
			Manipulation.erreur("Le sprite de la balle d'un ennemi n'a pas été trouvé !", e);	
		}
	}

}