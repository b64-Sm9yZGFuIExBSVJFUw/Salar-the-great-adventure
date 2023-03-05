package menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

import appli.Main;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import manipulation.Manipulation;

/**
 * Le menu principal
 */
public class MenuPrincipal extends Application{
	private static Pane affichage = new Pane(); //Pour le menu
	private Stage menu; //Le menu du jeu
	private AnimationTimer timer; //Le timer
	private long start = 0; //Le temps o� la cin�matique a commenc�e (Pour pouvoir retrouver le temps �coul�)

	//Cin�matique de d�but
	private static final int FIN_CINEMATIQUE = 12700;
	private static final int FIN_CINEMATIQUE2 = 12750;

	//G�n�rique de fin
	private static final int FIN_GENERIQUE = 69000;
	private static final int FIN_GENERIQUE2 = 69050;

	//Boutons Jouer, remise � z�ro et quitter
	private static final double POSITION_X_BOUTONS = Menu.LARGEUR_ECRAN/2;
	private static final double POSITION_Y_JOUER = 180;
	private static final double POSITION_Y_REMISE_ZERO = 285;
	private static final double POSITION_Y_QUITTER = 405;
	private static final double LARGEUR_BOUTONS = 400;
	
	private boolean creditsAffich�s = false; //Le g�n�rique est affich� ?

	/**
	 * Cr�ation du menu
	 * @return Renvoie l'objet o� l'on va tout ajouter pour qu'il affiche
	 * @author Wajdi & Ihsane
	 */
	private Pane createMenu() throws FileNotFoundException {  
		//R�solution du jeu
		affichage.setPrefSize(Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN);

		timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				try {
					if(!creditsAffich�s) //Si les cr�dits sont affich�s, on ne raffiche pas le menu
						//Si �a fait 12.7 secondes que la cin�matique a commenc�e (12.7 secondes = Drop de la musique, fin de la cin�matique)
						if((System.currentTimeMillis()-start) > FIN_CINEMATIQUE && (System.currentTimeMillis()-start) < FIN_CINEMATIQUE2) afficherMenu(menu);

					//Si un niveau a �t� lanc� (On v�rifie constamment)
					if(Main.enTrainDeJouer()) menu.close();
				} catch (FileNotFoundException e) {
					System.err.println("Une image dans le menu principal est introuvable [Apr�s cin�matique]");
				}
			}
		};

		//Cin�matique et chargement du fond du menu principal
		Manipulation.ajouterImage(affichage, Menu.PATH_BACKGROUND_MENU,0,0,Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN); //On charge d�j� le fond du menu
		Manipulation.ajouterImage(affichage, Menu.PATH_CINEMATIQUE_DEBUT, 0, 0, Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN); //La cin�matique

		Main.mediaPlayer.play(); //On joue la musique
		timer.start(); //On lance le timer
		start = System.currentTimeMillis(); //On r�cup�re a quel moment a commenc� le timer

		return affichage;
	}

	/**
	 * @brief Le "MAIN" du menu
	 * @author Wajdi
	 */
	@Override
	public void start(Stage menu) throws Exception {
		Scene menuScene = null; 
		
		if(Main.niveauBloqu�(9) || !Main.niveauBloqu�(0)) //Si le jeu n'est pas termin� ou termin� on affiche pas les credits au d�but
			menuScene = new Scene(createMenu()); //Cr�ation du menu
		else //Si �tat 9 mais pas le 0, on viens de finir le jeu donc g�n�rique d�s le d�but
			menuScene = new Scene(g�n�rique());

		//Le menu en lui-m�me
		menu.setScene(menuScene); //La sc�ne principale = Le menu (on en est � o� en gros)
		menu.initStyle(StageStyle.UNDECORATED); //On ne peut pas fermer de nous m�me la fen�tre
		menu.setTitle("MENU"); //Le nom de la fen�tre
		menu.getIcons().add(new Image(new FileInputStream(Menu.PATH_ICONE))); //On met l'ic�ne du jeu
		this.menu = menu;

		menu.show();
	}

	/**
	 * Apr�s la cin�matique, affiche le menu principal
	 * @param [menu]: Le menu, pour ajouter les boutons etc, pour le manipuler
	 * @author Wajdi
	 */
	private void afficherMenu(Stage menu) throws FileNotFoundException {
		affichage.getChildren().remove(1); //On retire le gif de l'intro

		//Affichage du titre et du personnage
		Manipulation.ajouterImage(affichage, Menu.PATH_TITRE ,Menu.POSITION_TITRE,Menu.POSITION_TITRE,Menu.LARGEUR_TITRE,Menu.HAUTEUR_TITRE);
		Manipulation.ajouterImage(affichage, Menu.PATH_SALAR, 0, Menu.POSITION_Y_SALAR, Menu.LARGEUR_SALAR,Menu.HAUTEUR_SALAR);

		//Le bouton "NOUVELLE PARTIE"
		Button jouer = new Button("JOUER"); //Cr�ation du bouton 
		jouer.setTranslateX(POSITION_X_BOUTONS); //On met les coordonn�es du bouton au milieu de l'�cran
		jouer.setTranslateY(POSITION_Y_JOUER); //Pareil en hauteur (Y)
		jouer.setPrefWidth(LARGEUR_BOUTONS); //Largeur
		Manipulation.ajouterJeu(affichage, jouer);

		//Le bouton "CHARGER PARTIE"
		Button remise0 = new Button("REMISE A ZERO");
		remise0.setTranslateX(POSITION_X_BOUTONS);
		remise0.setTranslateY(POSITION_Y_REMISE_ZERO); 		
		remise0.setPrefWidth(LARGEUR_BOUTONS); 
		Manipulation.ajouterJeu(affichage, remise0);

		//Le bouton "QUITTER" 
		Button quitter = new Button("QUITTER");
		quitter.setTranslateX(POSITION_X_BOUTONS);
		quitter.setTranslateY(POSITION_Y_QUITTER); 
		quitter.setPrefWidth(LARGEUR_BOUTONS); 
		Manipulation.ajouterJeu(affichage, quitter);

		//Si le jeu est "termin�"
		if(!Main.niveauBloqu�(7)) {
			//Le bouton de la fin du jeu
			String nomBouton = "F I N";

			if(!Main.niveauBloqu�(9)) //Si le jeu est r�ellement termin� �a sera le bouton pour les credits (Sinon "F I N" contre Salar, le r�el dernier niveau)
				nomBouton = "Credits";

			Button fin = new Button(nomBouton);
			fin.setTranslateX(Menu.POSITION_X_FIN);
			fin.setTranslateY(Menu.POSITION_Y_FIN); 
			fin.setPrefWidth(Menu.TAILLE_BOUTON*2); 
			fin.setPrefHeight(Menu.TAILLE_BOUTON);
			Manipulation.ajouterJeu(affichage, fin);

			//On clique sur le bouton
			fin.setOnAction(new EventHandler<ActionEvent>() {
				@Override 
				public void handle(ActionEvent e) {
					if(Main.niveauBloqu�(0)) { 	//Si on clique sur "F I N" 
						Alert alerte = new Alert(AlertType.CONFIRMATION);
						alerte.setTitle("FIN");
						alerte.setHeaderText("Zerod vaincu, le monde se retrouve en paix...");
						alerte.setContentText("Lancer la fin ?");

						Optional<ButtonType> result = alerte.showAndWait(); //On r�cup�re le r�sultat

						if (result.isPresent()) {
							if (result.get() == ButtonType.OK) { //Si OK
								Main.mediaPlayer.stop(); //Plus de musique du menu
								menu.close();
								try {
									Main.setEnTrainDeJouer(true);
									Main.instanceVsSalar().start(new Stage()); //Cr�e le niveau et le lance
								} catch (Exception e1) {
									Manipulation.erreur("Le menu \"jouer\" n'a pas pu �tre charg� !", e1);
								}
							} else { //Sinon
								//Ne se passe rien
							} 
						}	
					}else { //Si on clique sur "Credits" 
						Alert alerte = new Alert(AlertType.CONFIRMATION);
						alerte.setTitle("CREDITS");
						alerte.setHeaderText("F�licitations encore une fois � vous !");
						alerte.setContentText("Afficher les credits du jeu ?");

						Optional<ButtonType> result = alerte.showAndWait(); //On r�cup�re le r�sultat

						if (result.isPresent()) {
							if (result.get() == ButtonType.OK) { //Si OK
								Main.mediaPlayer.stop(); //Plus de musique du menu
								try {
									Scene gen = new Scene(g�n�rique());
									menu.setScene(gen);
								} catch (FileNotFoundException e1) {
									Manipulation.erreur("Erreur du lancement du g�n�rique! (Image/Son non trouv�!)", e1);
								}
							} else { //Sinon
								//Ne se passe rien
							} 
						}	
					}
				}
			});
		}

		//Le bouton pour mettre le son ou pas
		Button son = new Button();
		son.setTranslateX(Menu.POSITION_X_IMAGE_SON);
		son.setTranslateY(Menu.POSITION_Y_BOUTON_RETOUR_SON_OPTIONS);
		son.setPrefWidth(Menu.TAILLE_BOUTON);
		son.setPrefHeight(Menu.TAILLE_BOUTON);
		Manipulation.ajouterJeu(affichage, son);

		//L'image qui appara�t dessus
		ImageView sonImg = new ImageView(new Image(new FileInputStream("Images/Menu/sonActiv�.png"))); 
		sonImg.setX(Menu.POSITION_X_IMAGE_SON); //Position du bouton
		sonImg.setY(Menu.POSITION_Y_BOUTON_RETOUR_SON_OPTIONS);
		sonImg.setFitHeight(Menu.TAILLE_BOUTON); //Taille du bouton
		sonImg.setFitWidth(Menu.TAILLE_BOUTON); 
		affichage.getChildren().add(sonImg); //On ajoute

		//Acc�der aux options
		Button options = new Button();
		options.setTranslateX(Menu.POSITION_X_BOUTON_RETOUR_OPTIONS);
		options.setTranslateY(Menu.POSITION_Y_BOUTON_RETOUR_SON_OPTIONS);
		options.setPrefWidth(Menu.TAILLE_BOUTON);
		options.setPrefHeight(Menu.TAILLE_BOUTON);
		Manipulation.ajouterJeu(affichage, options);

		//L'image qui appara�t dessus
		ImageView optImg = new ImageView(new Image(new FileInputStream("Images/Menu/options.png"))); 
		optImg.setX(Menu.POSITION_X_BOUTON_RETOUR_OPTIONS); //Position du bouton
		optImg.setY(Menu.POSITION_Y_BOUTON_RETOUR_SON_OPTIONS);
		optImg.setFitHeight(Menu.TAILLE_BOUTON); //Taille du bouton
		optImg.setFitWidth(Menu.TAILLE_BOUTON); 
		affichage.getChildren().add(optImg); //On ajoute

		menu.show(); //On affiche le menu

		//Si clic sur bouton son
		son.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Main.sonClic(sonImg); //Changement 
			}
		});

		//Si clic sur l'image du son [Bouton]... [Car sans �a, si on clique sur l'image, c'est pas consid�r� comme un clic du bouton en dessous]
		sonImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Main.sonClic(sonImg); //M�me chose que si on, cliquait sur le bouton
			}

		});

		options.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				menu.hide();
				try {
					Main.instanceOptions().start(Main.getMenuOptionsStage());
				} catch (Exception e1) {
					Manipulation.erreur("Le menu des options n'a pas pu �tre charg� !", e1);
				}
			}
		});

		//Si clic sur l'image du son [Bouton]... [Car sans �a, si on clique sur l'image, c'est pas consid�r� comme un clic du bouton en dessous]
		optImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				menu.hide();
				try {
					Main.instanceOptions().start(Main.getMenuOptionsStage());
				} catch (Exception e1) {
					Manipulation.erreur("Le menu des options n'a pas pu �tre charg� !", e1);
				}
			}

		});

		//Si on clique sur "ARCADE"
		jouer.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				menu.hide();
				try {
					Main.instanceJouer().start(Main.getMenuJouerStage());
				} catch (Exception e1) {
					Manipulation.erreur("Le menu \"jouer\" n'a pas pu �tre charg� !", e1);
				}
			}
		});

		//Si on clique sur "QUITTER"
		quitter.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) {
				Alert alerte = new Alert(AlertType.CONFIRMATION);
				alerte.setTitle("Confirmation");
				alerte.setHeaderText("�tes-vous s�r de quitter ?");
				alerte.setContentText("Vous allez nous manquer...");

				Optional<ButtonType> result = alerte.showAndWait(); //On r�cup�re le r�sultat

				if (result.isPresent()) {
					if (result.get() == ButtonType.OK) { //Si OK
						System.exit(0); //Le jeu se ferme
					} else { //Sinon
						//Ne se passe rien
					} 
				}

			}
		});

		//Si on clique sur "REMISE A ZERO"
		remise0.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent e) { 
				Alert alerte = new Alert(AlertType.CONFIRMATION);
				alerte.setTitle("Confirmation");
				alerte.setHeaderText("�tes-vous s�r de tout recommencer ???");
				alerte.setContentText("Cette op�ration est irr�versible.");

				Optional<ButtonType> result = alerte.showAndWait(); //On r�cup�re le r�sultat

				if (result.isPresent()) {
					if (result.get() == ButtonType.OK) { //Si OK
						Main.remiseA0(); //Remise � 0

						//On informe
						alerte = new Alert(AlertType.ERROR);
						alerte.setTitle("Succ�s");
						alerte.setHeaderText("Le jeu est reparti � z�ro !");
						alerte.setContentText("Bienvenue dans une nouvelle partie...");

						alerte.showAndWait(); //On l'affiche
					} else { //Sinon
						//Ne se passe rien
					} 
				}

			}
		});
	}

	/**
	 * Le v�ritable main mais on lance juste l'application en fait
	 * 
	 * @author Wajdi
	 */
	public static void main(String[] args) {
		launch(args); //On lance l'application! (Equivalent du RUN en Thread)
	}

	/**
	 * G�n�rique de fin qui s'affiche au d�but
	 * @return Renvoie l'objet o� l'on va tout ajouter pour qu'il affiche
	 * @author Wajdi & Ihsane
	 */
	private Pane g�n�rique() throws FileNotFoundException {  
		//R�solution du jeu
		affichage.setPrefSize(Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN);
		creditsAffich�s = true; //Les g�n�riques sont affich�s

		timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				//Si �a fait 12.7 secondes que la cin�matique a commenc�e (12.7 secondes = Drop de la musique, fin de la cin�matique)
				if((System.currentTimeMillis()-start) > FIN_GENERIQUE && (System.currentTimeMillis()-start) < FIN_GENERIQUE2) {
					try {
						if(Main.niveauBloqu�(0)) //Si on est � l'�tat 9 (Apr�s avoir battu Salar)
							Main.sauvegarde(0); //Cela n'arrive qu'une fois, l'�tat 0 ne le permet plus 
					} catch (IOException e) {
						Manipulation.erreur("Erreur lors de la sauvegarde de l'�tat 0 !", e);
					} 
					menu.close(); //Fin du jeu
				}

				//Si un niveau a �t� lanc� (On v�rifie constamment)
				if(Main.enTrainDeJouer()) menu.close();
			}                                               
		};

		//Cin�matique et chargement du fond du menu principal
		Manipulation.ajouterImage(affichage, Menu.PATH_BACKGROUND_MENU,0,0,Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN); //On charge d�j� le fond du menu
		Manipulation.ajouterImage(affichage, Menu.PATH_GENERIQUE, 0, 0, Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN); //La cin�matique

		//Musique du g�n�rique de fin
		Main.mediaPlayer = new MediaPlayer(new Media(new File("Sons/credits.mp3").toURI().toString()));
		Main.mediaPlayer.play(); //On joue la musique
		timer.start(); //On lance le timer
		start = System.currentTimeMillis(); //On r�cup�re a quel moment a commenc� le timer
		

		return affichage;
	}
}
