package menu;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.util.Optional;

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

public class MenuJouer extends Application {

	private static Pane affichage = new Pane();

	//Position en Y de la "premi�re ligne" et "deuxi�me ligne" des boutons pour les niveaux
	private static final int POSITION_Y_NIVEAUX_1_2_3 = 200;
	private static final int POSITION_Y_NIVEAUX_4_5_6 = 350;

	//Position en X des niveaux I et IV
	private static final int POSITION_X_BOUTON_NIV_1_4 = 50; 

	//Position en X des niveaux III et V
	private static final int POSITION_X_BOUTON_NIV_2_5 = 375; 

	//Position en X des niveaux III et VI
	private static final int POSITION_X_BOUTON_NIV_3_6 = 675; 
	private static final String IMAGE_LOCKED_LEVEL = "Images/Menu/Jouer/Locked.png";

	/**
	 * Cr�ation du menu
	 * 
	 * @return Renvoie l'objet o� l'on va tout ajouter pour qu'il affiche
	 * @author Wajdi
	 */
	private Pane createMenu(){  
		Main.plusPremiereFoisJouer();

		//R�solution du jeu
		affichage.setPrefSize(Menu.LARGEUR_ECRAN, Menu.HAUTEUR_ECRAN);

		//Image de fond et titre du jeu
		Manipulation.ajouterImage(affichage, "Fonds/bg_menu.gif",0,0,Menu.LARGEUR_ECRAN,Menu.HAUTEUR_ECRAN);

		//On renvoie pour l'affichage
		return affichage;
	}

	/**
	 * Le "MAIN" du menu
	 * 
	 * @author Wajdi
	 */
	@Override
	public void start(Stage jouer){
		Scene jouerScene;

		if(Main.premierAffichageJouer()) {
			//Cr�ation du menu
			jouerScene= new Scene(createMenu()); 

			//Le menu en lui-m�me (Doit �tre fait UNE fois)
			jouer.setScene(jouerScene); //La sc�ne principale = Le menu (on en est � o� en gros)

			//On doit l'init en une fois donc il est dans "Si premi�re fois qu'on affiche l'option"
			jouer.initStyle(StageStyle.UNDECORATED); //On ne peut pas fermer de nous m�me la fen�tre
		}

		jouer.setTitle("JOUER !"); //Le nom de la fen�tre
		jouer.getIcons().add(new Image("file:Images/Icone.png")); //On met l'ic�ne du jeu

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
			Manipulation.erreur("L'image de la fl�che retour n'a pas �t� trouv�e!",e);	
		} 

		//L'image qui appara�t dessus
		ImageView imgI = null;
		try {
			imgI = new ImageView(new Image(new FileInputStream("Images/Menu/Jouer/I.png")));
			imgI.setX(POSITION_X_BOUTON_NIV_1_4); //Position du bouton
			imgI.setY(POSITION_Y_NIVEAUX_1_2_3);
			imgI.setFitHeight(Menu.TAILLE_BOUTON); //Taille du bouton
			imgI.setFitWidth(Menu.TAILLE_BOUTON); 
			affichage.getChildren().add(imgI); //On ajoute
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image du niveau I n'a pas �t� trouv�e !", e);	
		} 

		//L'image qui appara�t dessus
		ImageView imgII = null;
		try {
			if(Main.niveauBloqu�(2))
				imgII = new ImageView(new Image(new FileInputStream(IMAGE_LOCKED_LEVEL))); //Cadenas
			else
				imgII = new ImageView(new Image(new FileInputStream("Images/Menu/Jouer/II.png")));
			imgII.setX(POSITION_X_BOUTON_NIV_2_5); //Position du bouton
			imgII.setY(POSITION_Y_NIVEAUX_1_2_3);
			imgII.setFitHeight(Menu.TAILLE_BOUTON); //Taille du bouton
			imgII.setFitWidth(Menu.TAILLE_BOUTON); 
			affichage.getChildren().add(imgII); //On ajoute
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image du niveau II n'a pas �t� trouv�e !", e);	
		} 

		//L'image qui appara�t dessus
		ImageView imgIII = null;
		try {
			imgIII = new ImageView(new Image(new FileInputStream(IMAGE_LOCKED_LEVEL)));
			imgIII.setX(POSITION_X_BOUTON_NIV_3_6); //Position du bouton
			imgIII.setY(POSITION_Y_NIVEAUX_1_2_3);
			imgIII.setFitHeight(Menu.TAILLE_BOUTON); //Taille du bouton
			imgIII.setFitWidth(Menu.TAILLE_BOUTON); 
			affichage.getChildren().add(imgIII); //On ajoute
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image du niveau III n'a pas �t� trouv�e !", e);	
		} 

		//L'image qui appara�t dessus
		ImageView imgIV = null;
		try {
			if(Main.niveauBloqu�(4))
				imgIV = new ImageView(new Image(new FileInputStream(IMAGE_LOCKED_LEVEL))); //Cadenas
			else
				imgIV = new ImageView(new Image(new FileInputStream("Images/Menu/Jouer/IV.png")));

			imgIV.setX(POSITION_X_BOUTON_NIV_1_4); //Position du bouton
			imgIV.setY(POSITION_Y_NIVEAUX_4_5_6);
			imgIV.setFitHeight(Menu.TAILLE_BOUTON); //Taille du bouton
			imgIV.setFitWidth(Menu.TAILLE_BOUTON); 
			affichage.getChildren().add(imgIV); //On ajoute
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image du niveau IV n'a pas �t� trouv�e !", e);	
		} 

		//L'image qui appara�t dessus
		ImageView imgV = null;
		try {
			if(Main.niveauBloqu�(5))
				imgV = new ImageView(new Image(new FileInputStream(IMAGE_LOCKED_LEVEL))); //Cadenas
			else
				imgV = new ImageView(new Image(new FileInputStream("Images/Menu/Jouer/V.png")));

			imgV.setX(POSITION_X_BOUTON_NIV_2_5); //Position du bouton
			imgV.setY(POSITION_Y_NIVEAUX_4_5_6);
			imgV.setFitHeight(Menu.TAILLE_BOUTON); //Taille du bouton
			imgV.setFitWidth(Menu.TAILLE_BOUTON); 
			affichage.getChildren().add(imgV); //On ajoute
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image du niveau V n'a pas �t� trouv�e !", e);	
		} 

		//L'image qui appara�t dessus
		ImageView imgVI = null;
		try {
			if(Main.niveauBloqu�(6))
				imgVI = new ImageView(new Image(new FileInputStream(IMAGE_LOCKED_LEVEL))); //Cadenas
			else
				imgVI = new ImageView(new Image(new FileInputStream("Images/Menu/Jouer/FINAL.png")));

			imgVI.setX(POSITION_X_BOUTON_NIV_3_6); //Position du bouton
			imgVI.setY(POSITION_Y_NIVEAUX_4_5_6);
			imgVI.setFitHeight(Menu.TAILLE_BOUTON); //Taille du bouton
			imgVI.setFitWidth(Menu.TAILLE_BOUTON); 
			affichage.getChildren().add(imgVI); //On ajoute
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image du niveau VI n'a pas �t� trouv�e !", e);	
		}

		jouer.show(); //On affiche le menu

		//Si le bouton est cliqu�
		retour.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				jouer.hide(); //On cache la sc�ne du menu
				try {
					Main.getMenuPrincipalStage().show();
				} catch (Exception e1) {
					Manipulation.erreur("Le menu principal n'a pas pu �tre charg� !", e1);
				}
			}
		});

		//Si clic sur l'image du retour [Bouton]... [Car sans �a, si on clique sur l'image, c'est pas consid�r� comme un clic du bouton en dessous]
		retImg.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				jouer.hide(); //On cache la sc�ne du menu
				try {
					Main.getMenuPrincipalStage().show();
				} catch (Exception e) {
					Manipulation.erreur("Le menu principal n'a pas pu �tre charg� !", e);
				}
			}

		});

		//Si clic sur l'image [bouton] du niveau I 
		imgI.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Alert alerte = new Alert(AlertType.CONFIRMATION);
				alerte.setTitle("NIVEAU I [COURSE POURSUITE]");
				alerte.setHeaderText("Salar se fait subitement attaquer alors qu'il �tait tranquillement chez lui...");
				alerte.setContentText("Lancer le niveau ?");

				Optional<ButtonType> result = alerte.showAndWait(); //On r�cup�re le r�sultat

				if (result.isPresent()) {
					if (result.get() == ButtonType.OK) { //Si OK
						jouer.close(); //On ferme la sc�ne du menu
						Main.mediaPlayer.stop(); //Plus de musique du menu
						try {
							Main.setEnTrainDeJouer(true);
							Main.instanceNiv1().start(new Stage()); //Cr�e le niveau I et le lance
						} catch (Exception e) {
							Manipulation.erreur("Le niveau 1 n'a pas pu �tre lanc� !", e);
						}
					} else { //Sinon
						//Ne se passe rien
					} 
				}

			}

		});

		//Si clic sur l'image [bouton] du niveau II 
		imgII.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(Main.niveauBloqu�(2)) {
					Alert info = new Alert(AlertType.ERROR);
					info.setTitle("Niveau II [???]");
					info.setHeaderText("???");
					info.setContentText("Niveau non d�bloqu� !");
					info.showAndWait(); //On l'affiche
				}else {
					Alert alerte = new Alert(AlertType.CONFIRMATION);
					alerte.setTitle("Niveau II [BARRIERE A LA LIBERTE]");
					alerte.setHeaderText("Salar se dirige vers le si�ge de l'organisation. Pour ce faire, il quitte l'�le. Mais la douane ne le laisse pas passer...");
					alerte.setContentText("Lancer le niveau ?");

					Optional<ButtonType> result = alerte.showAndWait(); //On r�cup�re le r�sultat

					if (result.isPresent()) {
						if (result.get() == ButtonType.OK) { //Si OK
							jouer.close(); //On ferme la sc�ne du menu
							Main.mediaPlayer.stop(); //Plus de musique du menu
							try {
								Main.setEnTrainDeJouer(true);
								Main.instanceNiv2().start(new Stage()); //Cr�e le niveau II et le lance
							} catch (Exception e) {
								Manipulation.erreur("Le niveau 2 n'a pas pu �tre lanc� !", e);
							}
						} else { //Sinon
							//Ne se passe rien
						} 
					}	
				}
			}

		});

		//Si clic sur l'image [bouton] du niveau III
		imgIII.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(Main.niveauBloqu�(3)) {
					Alert info = new Alert(AlertType.ERROR);
					info.setTitle("Niveau III [???]");
					info.setHeaderText("???");
					info.setContentText("Niveau non d�bloqu� !");
					info.showAndWait(); //On l'affiche
				}else {
					Alert info = new Alert(AlertType.INFORMATION);
					info.setTitle("Niveau III [TRAVERSEE MOUVEMENTEE]");
					info.setHeaderText("Salar a r�ussi a passer la fronti�re. Le voil� maintenant sur un bateau mais les robots l'ont rep�r� et le poursuivent...");
					info.setContentText("Niveau non commenc� !");
					
				}
			}
		});

		//Si clic sur l'image [bouton] du niveau IV
		imgIV.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(Main.niveauBloqu�(4)) {
					Alert info = new Alert(AlertType.ERROR);
					info.setTitle("Niveau IV [???]");
					info.setHeaderText("???");
					info.setContentText("Niveau non d�bloqu� !");
					info.showAndWait(); //On l'affiche
				}else {
					Alert alerte = new Alert(AlertType.CONFIRMATION);
					alerte.setTitle("NIVEAU IV [OPERATION EVASION]");
					alerte.setHeaderText("Emprissonn�, Salar se retrouve retrouve sans pouvoirs et essaye de se d�brouiller tant bien que mal.");
					alerte.setContentText("Lancer le niveau ?");

					Optional<ButtonType> result = alerte.showAndWait(); //On r�cup�re le r�sultat

					if (result.isPresent()) {
						if (result.get() == ButtonType.OK) { //Si OK
							jouer.close(); //On ferme la sc�ne du menu
							Main.mediaPlayer.stop(); //Plus de musique du menu
							try {
								Main.setEnTrainDeJouer(true);
								Main.instanceNiv4().start(new Stage()); //Cr�e le niveau IV et le lance
							} catch (Exception e) {
								Manipulation.erreur("Le niveau 4 n'a pas pu �tre lanc� !", e);
							}
						} else { //Sinon
							//Ne se passe rien
						} 
					}	
				}

			}
		});

		//Si clic sur l'image [bouton] du niveau V
		imgV.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(Main.niveauBloqu�(5)) {
					Alert info = new Alert(AlertType.ERROR);
					info.setTitle("Niveau V [???]");
					info.setHeaderText("???");
					info.setContentText("Niveau non d�bloqu� !");
					info.showAndWait(); //On l'affiche
				}else {
					Alert alerte = new Alert(AlertType.CONFIRMATION);
					alerte.setTitle("NIVEAU V [FACE A FACE]");
					alerte.setHeaderText("Salar entre dans le si�ge de l'organisation...");
					alerte.setContentText("Lancer le niveau ?");

					Optional<ButtonType> result = alerte.showAndWait(); //On r�cup�re le r�sultat

					if (result.isPresent()) {
						if (result.get() == ButtonType.OK) { //Si OK
							jouer.close(); //On ferme la sc�ne du menu
							Main.mediaPlayer.stop(); //Plus de musique du menu
							try {
								Main.setEnTrainDeJouer(true);
								Main.instanceNiv5().start(new Stage()); //Cr�e le niveau I et le lance
							} catch (Exception e) {
								Manipulation.erreur("Le niveau 5 n'a pas pu �tre lanc� !", e);
							}
						} else { //Sinon
							//Ne se passe rien
						} 
					}	
				}
			}

		});

		//Si clic sur l'image [bouton] du niveau VI
		imgVI.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(Main.niveauBloqu�(6)) {
					Alert info = new Alert(AlertType.ERROR);
					info.setTitle("Niveau FINAL [???]");
					info.setHeaderText("???");
					info.setContentText("Niveau non d�bloqu� !");
					info.showAndWait(); //On l'affiche
				}else {
					Alert alerte = new Alert(AlertType.CONFIRMATION);
					alerte.setTitle("NIVEAU FINAL [DERNIERE BATAILLE]");
					alerte.setHeaderText("Salar se retrouve en face du chef, Zerod.");
					alerte.setContentText("Lancer le niveau ?");

					Optional<ButtonType> result = alerte.showAndWait(); //On r�cup�re le r�sultat

					if (result.isPresent()) {
						if (result.get() == ButtonType.OK) { //Si OK
							jouer.close(); //On ferme la sc�ne du menu
							Main.mediaPlayer.stop(); //Plus de musique du menu
							try {
								Main.setEnTrainDeJouer(true);
								Main.instanceNivFinal().start(new Stage()); //Cr�e le niveau FINAL et le lance
							} catch (Exception e) {
								Manipulation.erreur("Le niveau 6 n'a pas pu �tre lanc� !", e);
							}
						} else { //Sinon
							//Ne se passe rien
						} 
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

}
