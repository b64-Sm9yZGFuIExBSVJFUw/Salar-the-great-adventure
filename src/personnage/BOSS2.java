
package personnage;

import java.io.FileNotFoundException;

import balles.BossBalle;
import balles.Balle_Rebondissante;
import manipulation.Manipulation;
import niveau.Niveau;

/**
 * Le deuxième boss
 * 
 * @author Jordan LAIRES
 *
 */
public class BOSS2 extends BOSS{

	private static final String PATH_BOSS_BALLE_NIVEAU2 = "Images/Niveau2/boss balle.png";

	public BOSS2(double x, double y, int w, int h, String t, double vie, String imageURL, BarreDeVie bdv, Niveau n)
			throws FileNotFoundException {
		super(x, y, w, h, t, vie, imageURL, bdv, n);
	}

	@Override
	public void tir(String type) {
		try {
			//S'il tire une balle rebondissante...
			if(type.contains("balle rebondissante")) {
				if(type.equals("balle rebondissante g")) //Si c'est une balle rebondissante qui va a gauche
					new Balle_Rebondissante(getTranslateX() + 25, getTranslateY()+35, PATH_BOSS_BALLE_NIVEAU2, type, getNiveau(), true); //Va vers gauche
				else //Sinon "d" (Vers la droite)
					new Balle_Rebondissante(getTranslateX() + 25, getTranslateY()+35, PATH_BOSS_BALLE_NIVEAU2, type, getNiveau(), false); //Va vers gauche
			}else { //Si c'est une balle normale
				new BossBalle(getTranslateX() + 25, getTranslateY()+35, PATH_BOSS_BALLE_NIVEAU2, type, getNiveau());
			}
			
		} catch (FileNotFoundException e) {
			Manipulation.erreur("Le sprite de la balle du boss n'a pas été trouvé !", e);	
		}
	}

}
