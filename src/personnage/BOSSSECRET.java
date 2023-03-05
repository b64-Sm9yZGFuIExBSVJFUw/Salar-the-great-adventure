package personnage;

import java.io.FileNotFoundException;


import balles.BossBalle;
import manipulation.Manipulation;
import niveau.Niveau;

/**
 * Boss secret (Moi même)
 * 
 * @author Jordan LAIRES
 *
 */
public class BOSSSECRET extends BOSS{

	public BOSSSECRET(double x, double y, int w, int h, String t, double vie, String imageURL, BarreDeVie bdv,
			Niveau n) {
		super(x, y, w, h, t, vie, imageURL, bdv, n);
	}

	@Override
	public void tir(String type) {
		try {
				new BossBalle(getTranslateX() + 25, getTranslateY()+35, "Images/Secret/balle.gif", type, getNiveau());	
		} catch (FileNotFoundException e) {
			Manipulation.erreur("Le sprite de la balle du boss secret n'a pas été trouvé !", e);	
		}
	}

}
