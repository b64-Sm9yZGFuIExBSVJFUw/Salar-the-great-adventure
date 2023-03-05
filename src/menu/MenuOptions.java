package menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import appli.Difficult�;
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
	
	//Bouton difficult�s
	private static final int POSITION_X_FACILE = 50; //Positions en X du bouton du mode facile
	private static final int POSITION_X_NORMAL = 250; 
	private static final int POSITION_X_DIFFICILE = 450;
	private static final int POSITION_X_FRAGILE = 650; 
	private static final int POSITION_Y_BOUTON_DIFFICULTE = 400; //Positions en Y des boutons pour choisir la difficult�e
	
	//Bouton du secret
	private static final int POSITION_X_SECRET= 405; 
	private static final int POSITION_Y_SECRET= 220;
	
	/**
	 * Cr�ation du menu
	 * 
	 * @return Renvoie l'objet o� l'on va tout ajouter pour qu'il affiche
	 * @author Wajdi
	 */
	private Pane createMenu() throws FileNotFoundException {  
		//R�solution du jeu
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
			//Cr�ation du menu
			optionsScene = new Scene(createMenu()); 

			//Le menu en lui-m�me (Doit �tre fait UNE fois)
			options.setScene(optionsScene); //La sc�ne principale = Le menu (on en est � o� en gros)
			
			//On doit l'init en une fois donc il est dans "Si premi�re fois qu'on affiche l'option"
			options.initStyle(StageStyle.UNDECORATED); //On ne peut pas fermer de nous m�me la fen�tre
		}
		
		options.setTitle("OPTIONS"); //Le nom de la fen�tre
		options.getIcons().add(new Image("file:Images/Icone.png")); //On met l'ic�ne du jeu
		
		//Acc�der aux options
		Button retour = new Button();
		retour.setTranslateX(Menu.POSITION_X_BOUTON_RETOUR_OPTIONS);
		retour.setTranslateY(Menu.POSITION_Y_BOUTON_RETOUR_SON_OPTIONS);
		retour.setPrefWidth(Menu.TAILLE_BOUTON);
		retour.setPrefHeight(Menu.TAILLE_BOUTON);
		Manipulation.ajouterJeu(affichage, retour);

		//L'image qui appara�t dessus du retour
		ImageView retImg = null;
		try {
			retImg = new ImageView(new Image(new FileInputStream("Images/Menu/retour.png")));
			retImg.setX(Menu.POSITION_X_BOUTON_RETOUR_OPTIONS); //Position du bouton
			retImg.setY(Menu.POSITION_Y_BOUTON_RETOUR_SON_OPTIONS);
			retImg.setFitHeight(Menu.TAILLE_BOUTON); //Taille du bouton
			retImg.setFitWidth(Menu.TAILLE_BOUTON); 
			affichage.getChildren().add(retImg); //On ajoute
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image de la fl�che retour n'a pas �t� trouv�e!", e);	
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
		File tmpDir = new File("./secret"); //Il faut cr�er un fichier appel� "secret" sans extension dans le m�me dossier que le .exe

		if(tmpDir.exists()) {
			Button secret = new Button("? ? ?");
			secret.setTranslateX(POSITION_X_SECRET);
			secret.setTranslateY(POSITION_Y_SECRET);
			Manipulation.ajouterJeu(affichage, secret);
			
			//Si le bouton est cliqu�
			secret.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					Alert alerte = new Alert(AlertType.CONFIRMATION);
					alerte.setTitle("Niveau SECRET [THE GREAT ONE]");
					alerte.setHeaderText("Vous souhaitez donc d�fier le cr�ateur du jeu... �tes-vous s�r ?");
					alerte.setContentText("Lancer le niveau ?");

					Optional<ButtonType> result = alerte.showAndWait(); //On r�cup�re le r�sultat
					
					if (result.isPresent()) {
						if (result.get() == ButtonType.OK) { //Si OK
							Main.mediaPlayer.stop(); //Fin de la musique du menu
							options.hide(); //On cache la sc�ne du menu
							try {
								Main.setEnTrainDeJouer(true);
								Main.instanceSecret().start(new Stage()); //Cr�e le niveau secret et le lance
							} catch (Exception e1) {
								Manipulation.erreur("Le niveau secret n'a pas pu �tre charg� !", e1);
							}
						} else { //Sinon
							//Ne se passe rien
						} 
					}	
					
				}
			});
		}
		
		options.show(); //On affiche le menu

		//Si le bouton est cliqu�
		retour.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				options.hide(); //On cache la sc�ne du menu
				try {
					Main.getMenuPrincipalStage().show();
				} catch (Exception e2) {
					Manipulation.erreur("Le menu principal n'a pas pu �tre charg� !", e2);
				}
			}
		});
		
		//Si clic sur l'image du retour [Bouton]... [Car sans �a, si on clique sur l'image, c'est pas consid�r� comme un clic du bouton en dessous]
		retImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				options.hide(); //On cache la sc�ne du menu
				try {
					Main.getMenuPrincipalStage().show();
				} catch (Exception e) {
					Manipulation.erreur("Le menu principal n'a pas pu �tre charg� !", e);
				}
			}

		});

		//Clics sur boutons de difficult�s
		facile.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Main.setVies(Menu.FACILE); //Main fait le lien entre le menu et le niveau 1
				Main.setDifficult�(Difficult�.FACILE);
			}
		});

		normal.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Main.setVies(Menu.NORMAL);
				Main.setDifficult�(Difficult�.NORMAL);
			}
		});

		difficile.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Main.setVies(Menu.DIFFICILE);
				Main.setDifficult�(Difficult�.DIFFICILE);
			}
		});

		fragile.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Main.setVies(Menu.FRAGILE_COMME_DU_VERRE);
				Main.setDifficult�(Difficult�.FRAGILE_COMME_DU_VERRE);
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
}
