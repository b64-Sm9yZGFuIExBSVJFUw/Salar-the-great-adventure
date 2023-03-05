package personnage;

import java.io.FileNotFoundException;

import balles.BossBalle;
import manipulation.Manipulation;
import niveau.Niveau;

/**
 * Le boss final (Niveau 6, Zerod)
 * 
 * @author Jordan LAIRES
 *
 */
public class BOSSFINAL extends BOSS{

	public BOSSFINAL(double x, double y, int w, int h, String t, double vie, String imageURL, BarreDeVie bdv,
			Niveau n) {
		super(x, y, w, h, t, vie, imageURL, bdv, n);
	}

	@Override
	public void tir(String type) {
		try {
			new BossBalle(getTranslateX() + 25, getTranslateY()+35, "Images/NiveauFinal/balle2.png", type, getNiveau());
		} catch (FileNotFoundException e) {
			Manipulation.erreur("Le sprite de la balle du boss n'a pas été trouvé !", e);	
		}
	}
}
