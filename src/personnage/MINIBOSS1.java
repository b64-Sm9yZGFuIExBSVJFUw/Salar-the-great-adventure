package personnage;

import java.io.FileNotFoundException;

import balles.BossBalle;
import manipulation.Manipulation;
import niveau.Niveau;

/**
 * Premier mini-boss
 * 
 * @author Jordan LAIRES
 *
 */
public class MINIBOSS1 extends BOSS {

	public MINIBOSS1(double x, double y, int w, int h, String t, double vie, String imageURL, BarreDeVie bdv,
			Niveau n) {
		super(x, y, w, h, t, vie, imageURL, bdv, n);
	}

	public void tir(String type){
		try {
			new BossBalle(getTranslateX() + 25, getTranslateY()+35, "Images/ennemi balle.png", type, getNiveau());
		} catch (FileNotFoundException e) {
			Manipulation.erreur("Le sprite de la balle du miniboss n'a pas été trouvé !", e);	
		}
	}

}
