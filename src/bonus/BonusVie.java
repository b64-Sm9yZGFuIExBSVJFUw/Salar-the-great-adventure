package bonus;

import java.io.File;
import java.io.FileNotFoundException;

import appli.Main;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import manipulation.Manipulation;
import niveau.Niveau;

/**
 * Bonus de vie qui donne une vie au joueur
 * 
 * @author Jordan LAIRES
 *
 */
public class BonusVie extends Bonus{
	private Niveau n;
	//Son qui se joue lorsqu'il gagne une vie
	private static Media son = new Media(new File("Sons/vieUp.mp3").toURI().toString());
	
	public BonusVie(double x, double y, double w, double h, Niveau n)
			throws FileNotFoundException {
		super(x, y, w, h, "Images/Bonus/BonusVie.png", n);
		this.n = n;
	}

	@Override
	public void pris(){
		//Le son se joue
		if (Main.son()) new MediaPlayer(son).play();
		
		Main.vieUp(); //+1 vie
		Manipulation.updateScore(n); //Met à jour le nb de vies affichées à l'écran
		meurs();
	}

}
