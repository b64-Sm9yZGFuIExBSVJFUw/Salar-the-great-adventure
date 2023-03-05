package bonus;

import java.io.File;
import java.io.FileNotFoundException;

import appli.Main;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import niveau.Niveau;

/**
 * Bonus de puissance: Augmente la puissance des balles du joueur
 * 
 * @author Jordan LAIRES
 *
 */
public class BonusPuissance extends Bonus {
	private Niveau n;	
	
	//Le son qui se joue lorsque le joueur prend le bonus
	private static Media son = new Media(new File("Sons/powerUp.mp3").toURI().toString());

	public BonusPuissance(double x, double y, double w, double h, Niveau n)
			throws FileNotFoundException {
		super(x, y, w, h, "Images/Bonus/BonusPuissance.png", n);
		this.n = n;
	}

	@Override
	public void pris() {
		//Le son du bonus se joue
		if (Main.son()) new MediaPlayer(son).play();
		
		n.powerUp(); //Il gagne en puissance
		meurs(); //Le bonus disparaît
	}

}
