package bonus;

import java.io.FileNotFoundException;

import niveau.Niveau;
import niveau.NiveauInterface;

/**
 * @brief Le bonus Point qui donne du score (50 points)
 * 
 * @author Jordan LAIRES
 */
public class BonusPoints extends Bonus {
	private Niveau n; //Pour permettre la montée du score

	public BonusPoints(double x, double y, double w, double h, Niveau n) throws FileNotFoundException {
		super(x, y, w, h, "Images/Bonus/BonusPoints.png", n);
		this.n = n;
	}

	/**
	 * Lorsque le joueur prend le bonus
	 */
	@Override
	public void pris() {
		n.addScore(NiveauInterface.BONUSPOINTS); //Il gagne du score
		meurs(); //Et le bonus disparaît
	}

}