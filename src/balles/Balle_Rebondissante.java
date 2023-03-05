package balles;

import java.io.FileNotFoundException;
import niveau.Niveau;
import niveau.NiveauInterface;

/**
 * Balle qui rebondit quand elle touche les bords gauche/droite (va dans l'autre sens) [LE BOSS1]
 * 
 * @author Jordan LAIRES
 */
public class Balle_Rebondissante extends BossBalle {
	private boolean versGauche; //La balle va vers la gauche ?
	private static final int SORTIE_DU_DECOR = NiveauInterface.LARGEUR_DECOR - 30; //A partir de combien la balle sort du décor
	
	public Balle_Rebondissante(double x, double y, String imageURL, String t, Niveau n, boolean sensGauche)
			throws FileNotFoundException {
		super(x, y, imageURL, t, n);
		versGauche = sensGauche;
	}
	
	public void bouger(double divisé) {
		//Si elle touche les "murs" du décor
		if(getTranslateX() < 0 || getTranslateX() > SORTIE_DU_DECOR)
			versGauche = !versGauche; //Elle change de sens
		
		//Si elle va vers la gauche...
		if(versGauche)
			bougerGauche(divisé); //...elle va a gauche
		else
			bougerDroite(divisé);
	}
}
