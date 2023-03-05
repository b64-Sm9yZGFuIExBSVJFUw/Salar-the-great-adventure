package personnage;

import java.io.FileNotFoundException;

import balles.BossBalle;
import manipulation.Manipulation;
import niveau.Niveau;

/**
 * Le boss SALAR au vrai dernier niveau
 * 
 * @author Jordan LAIRES
 */
public class Salar extends BOSS{

	public Salar(double x, double y, int w, int h, String t, double vie, String imageURL, BarreDeVie bdv, Niveau n) {
		super(x, y, w, h, t, vie, imageURL, bdv, n);
	}

	@Override
	public void tir(String type) {
		try {
			//Il tire les balles du joueur (Quand vous étiez Salar)
			new BossBalle(getTranslateX() + 25, getTranslateY()+35, "Images/joueur balle.png", type, getNiveau());
		} catch (FileNotFoundException e) {
			Manipulation.erreur("Le sprite de la balle de Salar en boss n'a pas été trouvé !", e);	
		}
	}
}
