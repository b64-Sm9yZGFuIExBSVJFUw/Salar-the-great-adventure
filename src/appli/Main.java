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
 * Cette classe sert en r�alit� � faire le pont entre les menus
 */
public class Main extends Application {
	private static Difficult� diff = Difficult�.NORMAL; //Par d�faut
	private static Integer vies = Menu.NORMAL; //Par d�faut (Nombre de vies)
	private static boolean son = true; //Son activ� ?
	
	private static Application menuPrincipal = new MenuPrincipal(); //Le menu principal [L'application]
	private static Stage menuPrincipalStage = new Stage(); //L'affichage, la sc�ne du menu principal
	private static Application menuOptions = new MenuOptions(); //Le menu des options [L'application]
	private static Stage menuOptionsStage = new Stage(); //L'affichage, la sc�ne du menu des options
	private static Application menuJouer = new MenuJouer(); //Le menu jouer [L'application]
	private static Stage menuJouerStage = new Stage(); //L'affichage, la sc�ne du "jouer"
	private static boolean premierAffichageOptions = true;
	private static boolean premierAffichageJouer = true;
	private static boolean enTrainDeJouer = false; //True si le joueur a cliqu� sur un niveau a jouer
	private static ArrayList<Integer> niveaux = new ArrayList<Integer>();

	// Manipulations pour charger le son du menu principal
	private static Media musique = new Media(new File("Sons/menu.mp3").toURI().toString());
	public static MediaPlayer mediaPlayer = new MediaPlayer(musique);

	/**
	 * Cr�ation du niveau 1
	 * 
	 * @return Le niveau 1 cr�e
	 * @author Jordan
	 */
	public static Application instanceNiv1() {
		return new Niveau1();
	}

	/**
	 * Cr�ation du niveau 2
	 * 
	 * @return Le niveau 2 cr�e
	 * @author Nathan
	 */
	public static Application instanceNiv2() {
		return new Niveau2();
	}

	/**
	 * Cr�ation du niveau 4
	 * 
	 * @return Le niveau 4 cr�e
	 * @author Ephra�m
	 */
	public static Application instanceNiv4() {
		return new Niveau4();
	}
	
	/**
	 * Cr�ation du niveau 5
	 * 
	 * @return Le niveau 5 cr�e
	 * @author Jordan
	 */
	public static Application instanceNiv5() {
		return new Niveau5();
	}
	
	/**
	 * Cr�ation du niveau final
	 * 
	 * @return Le niveau final cr�e
	 * @author Jordan
	 */
	public static Application instanceNivFinal() {
		return new NiveauFinal();
	}
	
	/**
	 * Cr�ation du combat contre Salar
	 * 
	 * @return Le contre Salar cr�e
	 * @author Jordan
	 */
	public static Application instanceVsSalar() {
		return new vsSalar();
	}
	
	/**
	 * Cr�ation du combat secret
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
	 * @return Le menu des options qui permet au joueur de s�lectionner la
	 *         difficult� du niveau
	 * @author Wajdi
	 */
	public static Application instanceOptions() {
		//Si c'est la premi�re fois qu'on affiche le menu des options, sinon comme il est d�j� cr�e, on le renvoie directement
		if (premierAffichageOptions)
			menuOptions = new MenuOptions(); // On le cr�e
		
		return menuOptions;
	}

	/**
	 * Renvoie l'application du menu "jouer"
	 * @author Wajdi
	 */
	public static Application instanceJouer() {
		if(premierAffichageJouer)
			menuJouer = new MenuJouer(); //On le cr�e
		
		return menuJouer;
	} 
	
	/**
	 * Le joueur a perdu, on retourne l'instance de l'application Game Over
	 * 
	 * @author Ihsane
	 */
	public static void gameOver() {
		//Cr�ation de l'instance
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
	 * Lorsque ce n'est plus la premi�re fois que les options sont affich�es
	 * 
	 * @author Wajdi
	 */
	public static void plusPremiereFoisOptions() {
		premierAffichageOptions = false;
	}
	
	/**
	 * Lorsque ce n'est plus la premi�re fois que le menu "jouer" est affich�
	 * 
	 * @author Wajdi
	 */
	public static void plusPremiereFoisJouer() {
		premierAffichageJouer = false;
	}

	/**
	 * Permet de savoir si le menu des options est affich� pour la premi�re fois
	 * 
	 * @author Wajdi
	 */
	public static boolean premierAffichageOptions() {
		return premierAffichageOptions;
	}
	
	/**
	 * Permet de savoir si le menu "jouer" est affich� pour la premi�re fois
	 * 
	 * @author Wajdi
	 */
	public static boolean premierAffichageJouer() {
		return premierAffichageJouer;
	}

	/**
	 * Getter nombre de vies
	 * 
	 * @return Le nombre de vies li� � la difficult� choisi
	 * @author Jordan
	 */
	public static Integer getVies() {
		return vies;
	}

	/**
	 * Change le nombre de vies (Difficult�)
	 * 
	 * @param [val]:
	 *            La nouvelle valeur (1,2,4 ou 8)
	 * @author Jordan
	 */
	public static void setVies(Integer val) {
		vies = val;
	}

	/**
	 * Incr�menter le nombre de vies
	 * 
	 * @author Jordan
	 */
	public static void vieUp() {
		vies++;
	}

	/**
	 * D�cr�menter le nombre de vies
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
	 * Lorsque le joueur a cliqu� sur le bouton du son.
	 * S'il �tait activ� = D�sactiv� et vice versa
	 * 
	 * @param [imageDuSon]: L'image du son dans le menu principal pour pouvoir modifier l'image (D�sactiv� ou pas [une croix])
	 * @author Wajdi
	 */
	public static void sonClic(ImageView imageDuSon) {
		son = !son; //Inverse
		
		//Mise � jour de l'image dans le menu
		if(!son) {
			try {
				imageDuSon.setImage(new Image(new FileInputStream("Images/Menu/sonD�sactiv�.png")));
			} catch (FileNotFoundException e1) {
				System.out.println("L'Image du son d�sactiv� n'a pas �t� trouv�e !");
				e1.printStackTrace();
			}
		}else {
			try {
				imageDuSon.setImage(new Image(new FileInputStream("Images/Menu/sonActiv�.png")));
			} catch (FileNotFoundException e1) {
				System.out.println("L'Image du son activ� n'a pas �t� trouv�e !");
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * D�sactive le son (Pour le gameover)
	 * 
	 * @author Ihsane
	 */
	public static void d�sacSon() {
		son = false;
	}

	/**
	 * Getter menu principal
	 * 
	 * @return La sc�ne du menu principal
	 * @author Wajdi
	 */
	public static Stage getMenuPrincipalStage() {
		return menuPrincipalStage;
	}

	/**
	 * Renvoie la sc�ne du menu des options
	 * 
	 * @return La sc�ne du menu des options
	 * @author Wajdi
	 */
	public static Stage getMenuOptionsStage() {
		return menuOptionsStage;
	}
	
	/**
	 * Renvoie la sc�ne du menu "jouer"
	 * 
	 * @return La sc�ne du menu "jouer"
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
		//Si le son est activ�
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
	 * Renvoie la difficult�
	 * 
	 * @author Jordan
	 */
	public static Difficult� getDifficult�() {
		return diff;
	}
	
	/**
	 * Change la difficult�
	 * @param [d]: La difficult� a mettre
	 * 
	 * @author Jordan
	 */
	public static void setDifficult�(Difficult� d) {
		diff = d;
	}
	
	/**
	 * Renvoie si le niveau est bloqu�
	 * @param [niveau]: Le num�ro
	 * 
	 * @author Ihsane
	 */
	public static boolean niveauBloqu�(Integer niveau) {
		//Renvoie true si le niveau n'est pas dans la sauvegarde charg�e (Tableau niveau)
		return !niveaux.contains(niveau);
	}
	
	/**
	 * A la fin d'un niveau on sauvegarde car le joueur a d�bloqu� le suivant
	 * 
	 * @param niveau: Le niveau d�bloqu�
	 * 
	 * @author Ihsane
	 * @throws IOException 
	 */
	public static void sauvegarde(Integer niveau) throws IOException {
		niveaux.add(niveau); //Tableau contenant les niveaux, on ajoute le niveau d�bloqu�
		
		FileWriter fileWriter = new FileWriter("Sauvegarde.salar", true); //True pour ne pas �craser l'�criture pr�c�dente
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

			//On r�cup�re
			String st = br.readLine();
			br.close();

			//Si il y a... alors le niveau est d�bloqu�
			if(st.contains("2")) niveaux.add(2); //Niveau II d�bloqu�
			if(st.contains("3")) niveaux.add(3); //Niveau III d�bloqu�
			if(st.contains("4")) niveaux.add(4); //Niveau IV d�bloqu�
			if(st.contains("5")) niveaux.add(5); //Niveau V d�bloqu�
			if(st.contains("6")) niveaux.add(6); //Niveau VI (FINAL) d�bloqu�
			if(st.contains("7")) niveaux.add(7); //Niveau VS. SALAR d�bloqu�
			if(st.contains("8")) niveaux.add(8); //Le dialogue de d�but contre Salar a d�j� �t� lu
			if(st.contains("9")) niveaux.add(9); //Fin du jeu, le g�n�rique va s'afficher au d�but du jeu
			if(st.contains("0")) niveaux.add(0); //Fin r�elle du jeu, le g�n�rique a d�j� �t� affich� du d�but du jeu
			if(st.contains("1")) Niveau.setDebug(true); //Mode DEBUG activ� (Le joueur ne pas �tre touch�/mourir)
		}catch(FileNotFoundException f) {
			//Ne fait rien c'est pas grave, c'est juste qu'il n'existe pas de sauvegarde
		}
	}
	
	/**
	 * Remet le jeu � 0
	 * 
	 * @author Ihsane
	 */
	public static void remiseA0() {
		//Tous les niveaux redeviennent bloqu�s
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
		//Appel de la m�thode start
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		charger(); //On charge la sauvegarde 
		menuPrincipal.start(menuPrincipalStage); //On lance le menu principal
	}

}