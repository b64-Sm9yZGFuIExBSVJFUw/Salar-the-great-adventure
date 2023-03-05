package menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import appli.Difficulté;
import appli.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import manipulation.Manipulation;

/**
 * Le menu des options
 */
public class MenuOptions extends Application{
	private static Pane affichage = new Pane();
	
	//Bouton difficultés
	private static final int POSITION_X_FACILE = 50; //Positions en X du bouton du mode facile
	private static final int POSITION_X_NORMAL = 250; 
	private static final int POSITION_X_DIFFICILE = 450;
	private static final int POSITION_X_FRAGILE = 650; 
	private static final int POSITION_Y_BOUTON_DIFFICULTE = 400; //Positions en Y des boutons pour choisir la difficultée
	
	//Bouton du secret
	private static final int POSITION_X_SECRET= 405; 
	private static final int POSITION_Y_SECRET= 220;
	
	/**
	 * Création du menu
	 * 
	 * @return Renvoie l'objet où l'on va tout ajouter pour qu'il affiche
	 * @author Wajdi
	 */
	private Pane createMenu() throws FileNotFoundException {  
		//Résolution du jeu
		Main.plusPremiereFoisOptions();
		affichage.setPrefSize(Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN);

		//Image de fond et titre du jeu
		Manipulation.ajouterImage(affichage, "Fonds/bg_menu.gif",0,0,Menu.LARGEUR_ECRAN,Menu.HAUTEUR_ECRAN);
		Manipulation.ajouterImage(affichage, "Images/TITRE.png",20,20,554,140);

		//On renvoie pour l'affichage
		return affichage;
	}
	
	/**
	 * Le "MAIN" du menu
	 * 
	 * @author Wajdi
	 */
	@Override
	public void start(Stage options) throws Exception {
		Scene optionsScene;
		
		if(Main.premierAffichageOptions()) {
			//Création du menu
			optionsScene = new Scene(createMenu()); 

			//Le menu en lui-même (Doit être fait UNE fois)
			options.setScene(optionsScene); //La scène principale = Le menu (on en est à où en gros)
			
			//On doit l'init en une fois donc il est dans "Si première fois qu'on affiche l'option"
			options.initStyle(StageStyle.UNDECORATED); //On ne peut pas fermer de nous même la fenêtre
		}
		
		options.setTitle("OPTIONS"); //Le nom de la fenêtre
		options.getIcons().add(new Image("file:Images/Icone.png")); //On met l'icône du jeu
		
		//Accéder aux options
		Button retour = new Button();
		retour.setTranslateX(Menu.POSITION_X_BOUTON_RETOUR_OPTIONS);
		retour.setTranslateY(Menu.POSITION_Y_BOUTON_RETOUR_SON_OPTIONS);
		retour.setPrefWidth(Menu.TAILLE_BOUTON);
		retour.setPrefHeight(Menu.TAILLE_BOUTON);
		Manipulation.ajouterJeu(affichage, retour);

		//L'image qui apparaît dessus du retour
		ImageView retImg = null;
		try {
			retImg = new ImageView(new Image(new FileInputStream("Images/Menu/retour.png")));
			retImg.setX(Menu.POSITION_X_BOUTON_RETOUR_OPTIONS); //Position du bouton
			retImg.setY(Menu.POSITION_Y_BOUTON_RETOUR_SON_OPTIONS);
			retImg.setFitHeight(Menu.TAILLE_BOUTON); //Taille du bouton
			retImg.setFitWidth(Menu.TAILLE_BOUTON); 
			affichage.getChildren().add(retImg); //On ajoute
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image de la flèche retour n'a pas été trouvée!", e);	
		} 
		

		Button facile = new Button("Mode FACILE (8 VIES)");
		facile.setTranslateX(POSITION_X_FACILE);
		facile.setTranslateY(POSITION_Y_BOUTON_DIFFICULTE);
		Manipulation.ajouterJeu(affichage, facile);
		
		Button normal = new Button("Mode NORMAL (4 VIES)");
		normal.setTranslateX(POSITION_X_NORMAL);
		normal.setTranslateY(POSITION_Y_BOUTON_DIFFICULTE);
		Manipulation.ajouterJeu(affichage, normal);

		Button difficile = new Button("Mode DIFFICILE (2 VIES)");
		difficile.setTranslateX(POSITION_X_DIFFICILE);
		difficile.setTranslateY(POSITION_Y_BOUTON_DIFFICULTE);
		Manipulation.ajouterJeu(affichage, difficile);

		Button fragile = new Button("FRAGILE COMME DU VERRE");
		fragile.setTranslateX(POSITION_X_FRAGILE);
		fragile.setTranslateY(POSITION_Y_BOUTON_DIFFICULTE);
		Manipulation.ajouterJeu(affichage, fragile);
		
			// ================ SECRET (CONTRE JORDAN) ================ \\
		File tmpDir = new File("./secret"); //Il faut créer un fichier appelé "secret" sans extension dans le même dossier que le .exe

		if(tmpDir.exists()) {
			Button secret = new Button("? ? ?");
			secret.setTranslateX(POSITION_X_SECRET);
			secret.setTranslateY(POSITION_Y_SECRET);
			Manipulation.ajouterJeu(affichage, secret);
			
			//Si le bouton est cliqué
			secret.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					Alert alerte = new Alert(AlertType.CONFIRMATION);
					alerte.setTitle("Niveau SECRET [THE GREAT ONE]");
					alerte.setHeaderText("Vous souhaitez donc défier le créateur du jeu... Êtes-vous sûr ?");
					alerte.setContentText("Lancer le niveau ?");

					Optional<ButtonType> result = alerte.showAndWait(); //On récupère le résultat
					
					if (result.isPresent()) {
						if (result.get() == ButtonType.OK) { //Si OK
							Main.mediaPlayer.stop(); //Fin de la musique du menu
							options.hide(); //On cache la scène du menu
							try {
								Main.setEnTrainDeJouer(true);
								Main.instanceSecret().start(new Stage()); //Crée le niveau secret et le lance
							} catch (Exception e1) {
								Manipulation.erreur("Le niveau secret n'a pas pu être chargé !", e1);
							}
						} else { //Sinon
							//Ne se passe rien
						} 
					}	
					
				}
			});
		}
		
		options.show(); //On affiche le menu

		//Si le bouton est cliqué
		retour.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				options.hide(); //On cache la scène du menu
				try {
					Main.getMenuPrincipalStage().show();
				} catch (Exception e2) {
					Manipulation.erreur("Le menu principal n'a pas pu être chargé !", e2);
				}
			}
		});
		
		//Si clic sur l'image du retour [Bouton]... [Car sans ça, si on clique sur l'image, c'est pas considéré comme un clic du bouton en dessous]
		retImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				options.hide(); //On cache la scène du menu
				try {
					Main.getMenuPrincipalStage().show();
				} catch (Exception e) {
					Manipulation.erreur("Le menu principal n'a pas pu être chargé !", e);
				}
			}

		});

		//Clics sur boutons de difficultés
		facile.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Main.setVies(Menu.FACILE); //Main fait le lien entre le menu et le niveau 1
				Main.setDifficulté(Difficulté.FACILE);
			}
		});

		normal.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Main.setVies(Menu.NORMAL);
				Main.setDifficulté(Difficulté.NORMAL);
			}
		});

		difficile.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Main.setVies(Menu.DIFFICILE);
				Main.setDifficulté(Difficulté.DIFFICILE);
			}
		});

		fragile.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Main.setVies(Menu.FRAGILE_COMME_DU_VERRE);
				Main.setDifficulté(Difficulté.FRAGILE_COMME_DU_VERRE);
			}
		});
	}

	/**
	 * Le véritable main mais on lance juste l'application en fait
	 * 
	 * @author Wajdi
	 */
	public static void main(String[] args) {
		launch(args); //On lance l'application! (Equivalent du RUN en Thread)
	}
}
