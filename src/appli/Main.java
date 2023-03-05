package appli;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import menu.Menu;
import menu.MenuJouer;
import menu.MenuOptions;
import menu.MenuPrincipal;
import niveau.GameOver;
import niveau.Niveau;
import niveau.Niveau1;
import niveau.Niveau2;
import niveau.Niveau4;
import niveau.Niveau5;
import niveau.NiveauFinal;
import niveau.Secret;
import niveau.vsSalar;

/**
 * Cette classe sert en réalité à faire le pont entre les menus
 */
public class Main extends Application {
	private static Difficulté diff = Difficulté.NORMAL; //Par défaut
	private static Integer vies = Menu.NORMAL; //Par défaut (Nombre de vies)
	private static boolean son = true; //Son activé ?
	
	private static Application menuPrincipal = new MenuPrincipal(); //Le menu principal [L'application]
	private static Stage menuPrincipalStage = new Stage(); //L'affichage, la scène du menu principal
	private static Application menuOptions = new MenuOptions(); //Le menu des options [L'application]
	private static Stage menuOptionsStage = new Stage(); //L'affichage, la scène du menu des options
	private static Application menuJouer = new MenuJouer(); //Le menu jouer [L'application]
	private static Stage menuJouerStage = new Stage(); //L'affichage, la scène du "jouer"
	private static boolean premierAffichageOptions = true;
	private static boolean premierAffichageJouer = true;
	private static boolean enTrainDeJouer = false; //True si le joueur a cliqué sur un niveau a jouer
	private static ArrayList<Integer> niveaux = new ArrayList<Integer>();

	// Manipulations pour charger le son du menu principal
	private static Media musique = new Media(new File("Sons/menu.mp3").toURI().toString());
	public static MediaPlayer mediaPlayer = new MediaPlayer(musique);

	/**
	 * Création du niveau 1
	 * 
	 * @return Le niveau 1 crée
	 * @author Jordan
	 */
	public static Application instanceNiv1() {
		return new Niveau1();
	}

	/**
	 * Création du niveau 2
	 * 
	 * @return Le niveau 2 crée
	 * @author Nathan
	 */
	public static Application instanceNiv2() {
		return new Niveau2();
	}

	/**
	 * Création du niveau 4
	 * 
	 * @return Le niveau 4 crée
	 * @author Ephraïm
	 */
	public static Application instanceNiv4() {
		return new Niveau4();
	}
	
	/**
	 * Création du niveau 5
	 * 
	 * @return Le niveau 5 crée
	 * @author Jordan
	 */
	public static Application instanceNiv5() {
		return new Niveau5();
	}
	
	/**
	 * Création du niveau final
	 * 
	 * @return Le niveau final crée
	 * @author Jordan
	 */
	public static Application instanceNivFinal() {
		return new NiveauFinal();
	}
	
	/**
	 * Création du combat contre Salar
	 * 
	 * @return Le contre Salar crée
	 * @author Jordan
	 */
	public static Application instanceVsSalar() {
		return new vsSalar();
	}
	
	/**
	 * Création du combat secret
	 * 
	 * @return Le niveau secret
	 * @author Jordan
	 */
	public static Application instanceSecret() {
		return new Secret();
	}

	/**
	 * Renvoie l'application du menu principal
	 * 
	 * @author Wajdi
	 */
	public static Application instanceMenu() {
		return menuPrincipal;
	}

	/**
	 * Renvoie l'application du menu des options
	 * 
	 * @return Le menu des options qui permet au joueur de sélectionner la
	 *         difficulté du niveau
	 * @author Wajdi
	 */
	public static Application instanceOptions() {
		//Si c'est la première fois qu'on affiche le menu des options, sinon comme il est déjà crée, on le renvoie directement
		if (premierAffichageOptions)
			menuOptions = new MenuOptions(); // On le crée
		
		return menuOptions;
	}

	/**
	 * Renvoie l'application du menu "jouer"
	 * @author Wajdi
	 */
	public static Application instanceJouer() {
		if(premierAffichageJouer)
			menuJouer = new MenuJouer(); //On le crée
		
		return menuJouer;
	} 
	
	/**
	 * Le joueur a perdu, on retourne l'instance de l'application Game Over
	 * 
	 * @author Ihsane
	 */
	public static void gameOver() {
		//Création de l'instance
		GameOver go = new GameOver();

		//Lancement de l'instance
		try {
			go.start(new Stage());
		} catch (Exception e) {
			System.out.println("Erreur lors du lancement de l'application Game Over");
			e.printStackTrace();
		}
	}
	
	/**
	 * Lorsque ce n'est plus la première fois que les options sont affichées
	 * 
	 * @author Wajdi
	 */
	public static void plusPremiereFoisOptions() {
		premierAffichageOptions = false;
	}
	
	/**
	 * Lorsque ce n'est plus la première fois que le menu "jouer" est affiché
	 * 
	 * @author Wajdi
	 */
	public static void plusPremiereFoisJouer() {
		premierAffichageJouer = false;
	}

	/**
	 * Permet de savoir si le menu des options est affiché pour la première fois
	 * 
	 * @author Wajdi
	 */
	public static boolean premierAffichageOptions() {
		return premierAffichageOptions;
	}
	
	/**
	 * Permet de savoir si le menu "jouer" est affiché pour la première fois
	 * 
	 * @author Wajdi
	 */
	public static boolean premierAffichageJouer() {
		return premierAffichageJouer;
	}

	/**
	 * Getter nombre de vies
	 * 
	 * @return Le nombre de vies lié à la difficulté choisi
	 * @author Jordan
	 */
	public static Integer getVies() {
		return vies;
	}

	/**
	 * Change le nombre de vies (Difficulté)
	 * 
	 * @param [val]:
	 *            La nouvelle valeur (1,2,4 ou 8)
	 * @author Jordan
	 */
	public static void setVies(Integer val) {
		vies = val;
	}

	/**
	 * Incrémenter le nombre de vies
	 * 
	 * @author Jordan
	 */
	public static void vieUp() {
		vies++;
	}

	/**
	 * Décrémenter le nombre de vies
	 * 
	 * @author Jordan
	 */
	public static void vieDown() {
		vies--;
	}

	/**
	 * Getter son
	 * 
	 * @author Wajdi
	 */
	public static boolean son() {
		return son;
	}

	/**
	 * Lorsque le joueur a cliqué sur le bouton du son.
	 * S'il était activé = Désactivé et vice versa
	 * 
	 * @param [imageDuSon]: L'image du son dans le menu principal pour pouvoir modifier l'image (Désactivé ou pas [une croix])
	 * @author Wajdi
	 */
	public static void sonClic(ImageView imageDuSon) {
		son = !son; //Inverse
		
		//Mise à jour de l'image dans le menu
		if(!son) {
			try {
				imageDuSon.setImage(new Image(new FileInputStream("Images/Menu/sonDésactivé.png")));
			} catch (FileNotFoundException e1) {
				System.out.println("L'Image du son désactivé n'a pas été trouvée !");
				e1.printStackTrace();
			}
		}else {
			try {
				imageDuSon.setImage(new Image(new FileInputStream("Images/Menu/sonActivé.png")));
			} catch (FileNotFoundException e1) {
				System.out.println("L'Image du son activé n'a pas été trouvée !");
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Désactive le son (Pour le gameover)
	 * 
	 * @author Ihsane
	 */
	public static void désacSon() {
		son = false;
	}

	/**
	 * Getter menu principal
	 * 
	 * @return La scène du menu principal
	 * @author Wajdi
	 */
	public static Stage getMenuPrincipalStage() {
		return menuPrincipalStage;
	}

	/**
	 * Renvoie la scène du menu des options
	 * 
	 * @return La scène du menu des options
	 * @author Wajdi
	 */
	public static Stage getMenuOptionsStage() {
		return menuOptionsStage;
	}
	
	/**
	 * Renvoie la scène du menu "jouer"
	 * 
	 * @return La scène du menu "jouer"
	 * @author Wajdi
	 */
	public static Stage getMenuJouerStage() {
		return menuJouerStage;
	}
	
	/**
	 * Permet de jouer le son du menu principal du jeu.
	 * 
	 * @author Wajdi
	 */
	public static void jouerSon(String mp3) {
		//Si le son est activé
		if (son) {
			MediaPlayer sfx = new MediaPlayer(new Media(new File(mp3).toURI().toString())); //On charge l'audio
			sfx.play(); //On le joue
		}
	}
	
	/**
	 * Renvoie si le joueur est en train de jouer
	 * 
	 * @author Jordan
	 */
	public static boolean enTrainDeJouer() {
		return enTrainDeJouer;
	}
	
	/**
	 * Change la valeur de la variable qui dit si le joueur joue
	 * @param [val]: La nouvelle valeur
	 * @author Jordan
	 */
	public static void setEnTrainDeJouer(boolean val) {
		enTrainDeJouer = val;
	}
	
	/**
	 * Renvoie la difficulté
	 * 
	 * @author Jordan
	 */
	public static Difficulté getDifficulté() {
		return diff;
	}
	
	/**
	 * Change la difficulté
	 * @param [d]: La difficulté a mettre
	 * 
	 * @author Jordan
	 */
	public static void setDifficulté(Difficulté d) {
		diff = d;
	}
	
	/**
	 * Renvoie si le niveau est bloqué
	 * @param [niveau]: Le numéro
	 * 
	 * @author Ihsane
	 */
	public static boolean niveauBloqué(Integer niveau) {
		//Renvoie true si le niveau n'est pas dans la sauvegarde chargée (Tableau niveau)
		return !niveaux.contains(niveau);
	}
	
	/**
	 * A la fin d'un niveau on sauvegarde car le joueur a débloqué le suivant
	 * 
	 * @param niveau: Le niveau débloqué
	 * 
	 * @author Ihsane
	 * @throws IOException 
	 */
	public static void sauvegarde(Integer niveau) throws IOException {
		niveaux.add(niveau); //Tableau contenant les niveaux, on ajoute le niveau débloqué
		
		FileWriter fileWriter = new FileWriter("Sauvegarde.salar", true); //True pour ne pas écraser l'écriture précédente
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.print(niveau); //On ajoute le niveau 
	    printWriter.close();
	}
	
	/**
	 * Charge la sauvegarde (Lancement du jeu)
	 * 
	 * @author Ihsane
	 * @throws IOException 
	 */
	public static void charger() throws IOException {
		//On lit
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("Sauvegarde.salar"))); 

			//On récupère
			String st = br.readLine();
			br.close();

			//Si il y a... alors le niveau est débloqué
			if(st.contains("2")) niveaux.add(2); //Niveau II débloqué
			if(st.contains("3")) niveaux.add(3); //Niveau III débloqué
			if(st.contains("4")) niveaux.add(4); //Niveau IV débloqué
			if(st.contains("5")) niveaux.add(5); //Niveau V débloqué
			if(st.contains("6")) niveaux.add(6); //Niveau VI (FINAL) débloqué
			if(st.contains("7")) niveaux.add(7); //Niveau VS. SALAR débloqué
			if(st.contains("8")) niveaux.add(8); //Le dialogue de début contre Salar a déjà été lu
			if(st.contains("9")) niveaux.add(9); //Fin du jeu, le générique va s'afficher au début du jeu
			if(st.contains("0")) niveaux.add(0); //Fin réelle du jeu, le générique a déjà été affiché du début du jeu
			if(st.contains("1")) Niveau.setDebug(true); //Mode DEBUG activé (Le joueur ne pas être touché/mourir)
		}catch(FileNotFoundException f) {
			//Ne fait rien c'est pas grave, c'est juste qu'il n'existe pas de sauvegarde
		}
	}
	
	/**
	 * Remet le jeu à 0
	 * 
	 * @author Ihsane
	 */
	public static void remiseA0() {
		//Tous les niveaux redeviennent bloqués
		for(int i = 0; i<niveaux.size(); ++i)
			niveaux.remove(i);
		
		//Suppression du fichier de sauvegarde
		File sauvegarde = new File("Sauvegarde.salar");
		sauvegarde.delete();
	}
	
	/**
	 * Permet de lancer le jeu
	 * 
	 * @author Wajdi
	 */
	public static void main(String[] args) throws Exception {
		//Appel de la méthode start
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		charger(); //On charge la sauvegarde 
		menuPrincipal.start(menuPrincipalStage); //On lance le menu principal
	}

}