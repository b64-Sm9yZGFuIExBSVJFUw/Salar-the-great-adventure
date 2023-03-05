package niveau;

import java.io.File;

import appli.Main;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import manipulation.Manipulation;
import menu.Menu;

/**
 * L'application Game Over
 * 
 * @author Jordan LAIRES
 *
 */
public class GameOver extends Application {
	private static Pane affichage = new Pane();
	
	//Manipulations pour charger le son du game over
	private Media musique = new Media(new File("Sons/gameOver.mp3").toURI().toString()); //La musique qui se joue
	private MediaPlayer mediaPlayer = new MediaPlayer(musique); //Le mediaPlayer qui va jouer la musique
	
	private AnimationTimer timer;
	private double start; //A combien de secondes le timer s'est lancé ?
	
	//Pour gérer le gif du game over (Pour pas qu'il se fasse à l'infini
	private static final int FIN_FONDU = 2200;
	private static final int PEUT_QUITTER = 4000;
	
	
	/**
	 * Création du menu
	 * 
	 * @return Renvoie l'objet où l'on va tout ajouter pour qu'il affiche
	 * @author Ihsane
	 */
	private Pane createMenu(){  
		//Résolution du jeu
		affichage.setPrefSize(Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN);

		//Image de fond et titre de l'application
		if(Main.son()) mediaPlayer.play();
		Manipulation.ajouterImage(affichage, "Images/Game Over/Game over.gif", 0, 0, Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN);
		
		//Ce qui gère l'affichage du gif du game over
		timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				//2 seconde = Fin du fondu, le game over est totalement visible
				if((System.currentTimeMillis()-start) > FIN_FONDU) afficher();
				
				//4 secondes = Il peut quitter.
				if((System.currentTimeMillis()-start) > PEUT_QUITTER) peutQuitter();
			}
		};
		
		timer.start(); //On lance le timer
		start = System.currentTimeMillis(); //On récupère a quel moment a commencé le timer
		
	    //On renvoie pour l'affichage
		return affichage;
	}
	
	@Override
	public void start(Stage gameover) throws Exception {
		Scene scene = new Scene(createMenu()); //Création du menu
		
		//Désactive le son du niveau I s'il était activé [Car on entend en arrière-plan le joueur qui se fait toucher]
		if(Main.son()) Main.désacSon(); 
		
		//Le game over en lui-même
		gameover.setScene(scene); //La scène du game over
		gameover.initStyle(StageStyle.UNDECORATED); //On ne peut pas fermer de nous même la fenêtre
		gameover.setTitle("GAME OVER"); //Le nom de la fenêtre
		gameover.getIcons().add(new Image("file:Images/Icone.png")); //On met l'icône du jeu
		gameover.show();
		
		//Si "espace" appuyée
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				switch (event.getCode()) { //Récupération de quelle touche appuyée
				case SPACE: System.exit(0);
				default: break; 
				}
			}
		});
	}
	
	/**
	 * Fin du gif, on affiche l'image du Game Over
	 * 
	 * @author Ihsane
	 */
	public void afficher() {
		Manipulation.ajouterImage(affichage, "Images/Game Over/Game over.png", 0, 0, Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN);
	}
	
	/**
	 * On change l'image, où on dit que le joueur peut quitter (Au bout de 4 secondes)
	 * 
	 * @author Ihsane
	 */
	public void peutQuitter() {
		timer.stop(); //Plus besoin du timer
		Manipulation.ajouterImage(affichage, "Images/Game Over/Game over (Quitter).gif", 0, 0, Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN);
	}
}
