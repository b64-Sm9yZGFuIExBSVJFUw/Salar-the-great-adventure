package niveau;

import java.io.File;





import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import bonus.*;
import appli.Difficulté;
import appli.Main;
import balles.BossBalle;
import balles.Balle_Rebondissante;
import balles.EnnemiBalle;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import manipulation.Manipulation;
import menu.Menu;
import personnage.BOSS;
import personnage.BOSS1;
import personnage.BarreDeVie;
import personnage.Ennemi;
import personnage.Joueur;
import personnage.MINIBOSS1;
import texte.Texte;

/**
 * Le niveau I
 */
public class Niveau1 extends Niveau{	
	//===================== TIMING DU NIVEAU EN SECONDES ===================\\
	private static final double APPARITION_NOM_NIVEAU = 0.8; //Le nom du niveau s'affiche
	private static final int NOM_NIVEAU_EN_APPARITION = 2; //Augmentation de son opacité
	private static final int NOM_NIVEAU_EN_DISPARITION = 4; //Descente de son opacité
	private static final int NOM_NIVEAU_EN_DISPARITION2 = 7; //Pareil mais plus vite
	private static final int FIN_NOM_NIVEAU = 9; //Il disparaît
	private static final int APPARITION_1ERS_ENNEMIS = 11; //Premiers ennemis (1ère horde)
	private static final double APPARITION_2EMES_ENNEMIS = 21.75; //Deuxièmes ennemis (2ème horde)
	private static final int APPARITION_MINIBOSS = 32; // Le mini-boss apparaît
	private static final double PEUT_ATTAQUER_MINIBOSS = 34.5; //Le mini-boss attaque
	private static final int APPARITION_1HORDE_AVEC_MINIBOSS = 45; //La première horde apparaît après que le mini-boss ait apparu
	private static final double APPARITION_1_ET_2HORDE = 55.5; //La deuxième horde apparaît après que le mini-boss ait apparu
	private static final double APPARITION_3HORDES_AVANT_MINIBOSS2 = 66.5; //La troisième horde en même dans que la 1 et 2 (Première apparition de la 3ème)
	private static final int APPARITION_MINIBOSS2 = 77; //Les 2 mini-boss apparaîssent
	private static final int APPARITION_3HORDES = 110; //Les trois hordes d'un coup après l'apparition des 2 mini-boss
	private static final double APPARITION_BALLES = 130; //Les balles qui tombent aléatoirement 
	private static final double APPARITION_DERNIERS_MINIBOSS = 153;
	private static final int TEMPS_MINIBOSS_DESCENTE = 2; //Le temps que prennent les mini-boss a descendre pour après attaquer


	//===================== TIMING DU BOSS EN SECONDES ===================\\
	private static final double BALLES_ALEATOIRES_HAUT = 6; //Balles descendent du haut aléatoirement
	private static final double FIN_BALLES_ALEATOIRES_HAUT = 16; //Fin de l'attaque
	private static final double BALLES_TOUTES_DIRECTIONS = 18; //Le boss tire des balles dans toutes les directions
	private static final double BALLES_REBONDISSANTES = 30; //Les balles rebondissent contre les "murs du décor"
	private static final double FIN_BALLES_REBONDISSANTES = 40; //Fin de l'attaque
	private static final double BALLES_ALEATOIRES_COTES = 43; //Balles aléatoires sortant des côtés
	private static final double SPIRALE = 58; //Le boss tire en spirale comme les miniboss
	private static final double MUR_BALLES = 76; //Un mur de balle qui tombe
	private static final double FIN_MUR_BALLES = 95; //Fin de l'attaque
	private static final double TUNNEL = 101; //Un tunnel "de balle" sortant du boss

	private static final int VIE_MINIBOSS = 565;
	private static double DEGAT_BALLE_JOUEUR_VS_MINIBOSS = 4.8;
	private static double DEGAT_BALLE_JOUEUR_VS_BOSS = 1;

	//Il faut combien de points pour avoir le rang ?
	private static final int RANG_SALAR = 2692000;
	private static final int RANG_S = 2000000;
	private static final int RANG_A = 1500000;
	private static final int RANG_B = 1000000;

	// ================================== Manipulations pour charger le son  ==================================
	private static Media son = new Media(new File("Sons/niveau1/niveau1.mp3").toURI().toString());
	private static MediaPlayer mediaPlayer = new MediaPlayer(son);

	// ================================== Variables qui gèrent les miniboss ==================================
	private static BOSS miniBOSS = null; //Le mini-boss
	private static BOSS miniBOSS2 = null; //2ème
	private static BOSS miniBOSS3 = null; //3ème 
	private static BOSS miniBOSS4 = null; //4ème
	private static boolean miniBossVersDroite = false; //Va-t-il vers la droite ?
	private static boolean miniBossVersDroite2 = true; //Va-t-il vers la droite ?
	private static final double POSITION_MINIBOSS_MILIEU = (NiveauInterface.LARGEUR_DECOR/2)-70; //Position en X où le miniboss est au milieu

	// ================================== Variables qui gèrent le boss ==================================
	private static BOSS1 boss = null; //Le BOSS
	private static boolean vsBOSS = false; //Actuellement contre un boss ?
	private static boolean bossBattu = false; //BOSS battu ?
	private static boolean vsMINIBOSS = false; //Actuellement contre plusieurs miniboss en même tps ?
	private boolean bossVersDroite = false; //Le boss bouge-t-il vers la droite ? (Pour gérer son mouvement)
	private boolean bossVersBas = false; //Vers le bas ?
	private static String nomBOSS = "COLONEL X14570";
	private double tpsDébutBoss = 0; //A quel moment le combat contre le boss commence ?
	private double tpsDescenteBoss = -9999; //A quel moment le boss descend ? (Petite "cinématique" quand il apparaît) [-9999] pour pas que le boss apparaisse au début du niveau (Voir code)
	private static final double POSITION_BOSS_MILIEU = (NiveauInterface.LARGEUR_DECOR/2)-75; //Position en X où le boss est au milieu
	
	// ================================== Chemins des images ==================================
	private static final String PATH_BACKGROUND_NIVEAU = "Fonds/Niveau_I/bg_niveau1.gif";
	private static final String PATH_MINIBOSS = "Images/Niveau1/miniboss.png";
	private static final String PATH_BOSS = "Images/Niveau1/boss.png";

	@Override
	public void start(Stage NIV1) throws Exception {
		Scene niveau_I = new Scene(createNiveau(NIV1)); //Création du niveau

		NIV1.setScene(niveau_I); //La scène du niveau devient la scène principale
		NIV1.setTitle("NIVEAU I"); //Nom de la fenêtre
		NIV1.setResizable(false); //On ne peut pas redimensionner la fenêtre

		//On remet l'icône du jeu comme elle a changée car c'est une autre fênetre
		NIV1.getIcons().add(new Image(new FileInputStream(Menu.PATH_ICONE)));

		Manipulation.updateScore(this); //Met à jour le score pour le début du niveau

		NIV1.show(); //Affichage du niveau

		/*On gère les boutons tapés (Contrôles du jeu) 
		 *[Quand on APPUIE sur la touche]
		 */
		niveau_I.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(!isEnDialogue()) { //Quand c'est en dialogue, le joueur ne joue pas
					switch (event.getCode()) { //Récupération de quelle touche appuyée
					case UP:    setBougeHaut(true); break;
					case DOWN:  setBougeBas(true); break;
					case LEFT:  setBougeGauche(true); break;
					case RIGHT: setBougeDroite(true); break;
					case SPACE: if(isPeutTirer()) setTiré(true); break;
					case SHIFT: setFocus(true); break;
					default:
						break;
					}
				}if(isPeutQuitter()) { //A la fin du niveau
					switch(event.getCode()) {
					case SPACE:
						NIV1.close();
						mediaPlayer.stop();
						
						//Niveau II, III, IV, V débloqués en attendant que le II, III, IV soient finis on peut jouer au Vème
						try {
							Main.sauvegarde(2);
							Main.sauvegarde(3);
							Main.sauvegarde(4);
							Main.sauvegarde(5);
						} catch (IOException e) {
							System.err.println("Erreur lors de la sauvegarde de l'état 2!");
						}
						
						Alert alerte = new Alert(AlertType.INFORMATION); 
						alerte.setTitle("NIVEAU I TERMINE !"); //Le titre de la fênetre
						alerte.setHeaderText("Vous avez réussi le niveau I ! Niveau II débloqué !"); //Au dessus du texte
						alerte.setContentText("Sauvegarde réussie. Veuillez redémarrer le jeu."); //Le texte

						alerte.showAndWait(); //On la montre et elle est modale (On peut rien faire tant qu'elle n'est pas fermée
						System.exit(0);
						break;

					default:
						break;
					}
				}
			}
		});

		//[Quand on RELACHE la touche]
		niveau_I.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(!isEnDialogue()) { //Quand c'est en dialogue, le joueur ne joue pas
					switch (event.getCode()) { //Récupération de quelle touche appuyée
					case UP:    setBougeHaut(false); break;
					case DOWN:  setBougeBas(false); break;
					case LEFT:  setBougeGauche(false); break;
					case RIGHT: setBougeDroite(false); break;
					case SPACE: setTiré(false); break;
					case SHIFT:
						setFocus(false); 
						Joueur g = (Joueur)getGrazeBox();
						joueur.finFocus();
						g.finFocus();
						break;
					default:
						break;
					}
				}else {
					switch(event.getCode()) {
					case SPACE:
						try {
							setNbEspaceAppuyés(getNbEspaceAppuyés()+1);
							dialogue();
						} catch (FileNotFoundException e) {
							//Fenetre dialogue
						}
						break;
					default:
						break;
					}
				}	
			}
		});
	}

	/**
	 * Création du niveau
	 * 
	 * @param [st]: Le stage du niveau pour pouvoir le fermer quand le joueur meurs
	 * @return Renvoie l'objet où l'on va tout ajouter pour qu'il affiche
	 * @author Jordan
	 */
	public Parent createNiveau(Stage niveau) throws IOException { 
		//Lancement de la musique du niveau
		if (Main.son()) mediaPlayer.play();

		getRoot().setPrefSize(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); //Met à jour les dimensions du jeu
		Manipulation.ajouterImage(getRoot(),PATH_BACKGROUND_NIVEAU, -15, 0, NiveauInterface.LARGEUR_DECOR,NiveauInterface.HAUTEUR_ECRAN+25); //Image de fond
		Manipulation.ajouterImage(getRoot(),NiveauInterface.PATH_SCORE, NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25,  NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

		//Le joueur et sa grazebox
		Niveau.grazeBox =  new Joueur(295,695,NiveauInterface.LARGEUR_JOUEUR+10,NiveauInterface.HAUTEUR_JOUEUR+10, "grazebox", "Images/Transparent.png", this);
		joueur = new Joueur(300,700,NiveauInterface.LARGEUR_JOUEUR, NiveauInterface.HAUTEUR_JOUEUR , "joueur", "Images/Joueur/joueur.png", this);

		AnimationTimer timer = new AnimationTimer() { //Le timer qui va gérer l'animation, etc.

			/*
			 * @brief Lié au timer
			 *        Va gérer tout ce que doit faire le joueur quand il appuie sur les touches
			 */
			public void handle(long arg0) {
				if (isBougeHaut()) { //Il a appuyé sur la flèche du HAUT
					joueur.bougerHaut(1); 
					getGrazeBox().bougerHaut(1); //Sa hitbox va le suivre de partout
				}
				if (isBougeBas()) { //Flèche du BAS 
					joueur.bougerBas(1); 
					getGrazeBox().bougerBas(1);
				}
				if (isBougeGauche()) { //GAUCHE
					joueur.bougerGauche(1);
					getGrazeBox().bougerGauche(1);
				}
				if (isBougeDroite()) { //DROITE
					joueur.bougerDroite(1); 
					getGrazeBox().bougerDroite(1);
				}

				//Le mode focus
				if (isFocus()) { //La touche SHIFT
					Joueur g = (Joueur)getGrazeBox();
					joueur.focus(); //Il passe en mode focus
					g.focus();
				} 

				//Si le joueur tire et que le  temps d'attente après le tir d'une balle est fini
				if (isTiré() && !isCooldownTir()) {
					joueur.tir("joueur bullet"); //Il tire
					setTpsDébTir(getTempsTotal()); //Le temps du début est enregistré pour calculer le cooldown
					setCooldownTir(true); //Il passe en cooldown
				}

				//Joueur a perdu
				if(Main.getVies() == 0) {
					if(!isLockGameOver()) { //L'action doit se faire 1 fois
						setLockGameOver(true);
						niveau.close(); //Fermeture fenêtre
						mediaPlayer.stop(); //Fin de la musique du Niveau 1
						Main.gameOver(); 
					}
				}

				try {
					update();
				} catch (FileNotFoundException e) {
					Manipulation.erreur("Un(e) sprite/image n'a pas pu être trouvée !", e);
				}
			}

		}; //Fin de la méthode handle

		//On lance le timer
		timer.start();

		//On retourne donc l'objet qui va nous permettre d'afficher des choses
		return getRoot();
	}

	public void update() throws FileNotFoundException {
		//Le temps d'une frame en JavaFX (On le met ici pour pouvoir manipuler les objets selon le temps)
		setTemps(getTemps() + NiveauInterface.TEMPS_1_FRAME);
		setTempsTotal(getTempsTotal() + NiveauInterface.TEMPS_1_FRAME);

		//Si ça fait 100 millisecondes que le joueur a isTiré()
		if(getTempsTotal() - getTpsDébTir() > NiveauInterface.COOLDOWN_TIR) setCooldownTir(false);

		//Pour mieux calculer le temps
		if(getTemps() > 2)
			setTemps(0);

		//Si cooldown écoulé
		if (getTempsTotal() - getTpsDébTouché() > NiveauInterface.COOLDOWN_REGENERATION) {
			setCooldownTouché(false);
			joueur.misAJour(false); //A mettre a jour
		}

		//Si le cooldown est fini, et que le sprite n'a pas été mis à jour et que il n'est pas en focus et qu'il ne tire pas (Sinon il se met constamment en pose normale)
		if(!isCooldownTouché() && !joueur.aEteMAJ() && !isFocus() && !isTiré()) { 
			joueur.getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur.png")));
			joueur.misAJour(true);
		}

		//Pour chacun des sprite présent sur le jeu on va les mettre à jour
		Manipulation.sprites(getRoot()).forEach(s -> {   
			switch(s.getType()) {
			case "joueur":
				if(!Niveau.isDebug()) { //Si pas en mode debug, le joueur n'est pas invincible
					//Si collision avec un ennemi
					Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().contains("ennemi") || e.getType().contains("boss") || e.getType().contains("balle")).forEach(ennemi -> {
						if (Manipulation.touche(s, ennemi)){
							Manipulation.CollisionJoueur(this, s, false);
						}

						if(Manipulation.touche(getGrazeBox(), ennemi)) {
							if(!isCooldownTouché()) Manipulation.grazeUp(this);
						}
					});
				}

				//Si collision bonus
				Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().contains("bonus")).forEach(bonus -> {
					if (Manipulation.touche(s, bonus)){
						Bonus b = (Bonus)bonus;
						b.pris();
					}
				});

				break;

			case "bonus": //Bonus
				s.bougerBas(2); //Descend vers le bas
				break;

			case "ennemi1": //Ennemi 1ère horde
				s.bougerDroite(1);

				if(Math.random() > 0.99)
					s.tir("ennemi balle1");
				break;

			case "ennemi2g": //Ennemi 2ème horde colonne de gauche
				s.bougerBas(1);

				if(Math.random() > 0.999)
					s.tir("ennemi balle2g");
				break;

			case "ennemi2d": //Ennemi 2ème horde colonne de droite
				s.bougerBas(1);

				if(Math.random() > 0.999)
					s.tir("ennemi balle2d");
				break;

			case "ennemi3": //Ennemi 3ème horde
				s.bougerGauche(1);

				if(Math.random() > 0.99)
					s.tir("ennemi balle3");
				break;

			case "ennemi balle1": //Balle d'ennemi de 1ère horde
				if(!vsMINIBOSS)
					s.bougerBas(4); //...elle se dirige vers le bas
				else
					s.bougerBas(8); //Quand on est contre le boss en même temps les balles ralentissent
				break;

			case "ennemi balle2g": //Balle d'ennemi de 2ème horde, colonne de gauche
				if(!vsMINIBOSS)
					s.bougerDroite(8);
				else
					s.bougerDroite(14);
				break;

			case "ennemi balle2d": //Balle d'ennemi de 2ème horde, colonne de droite
				if(!vsMINIBOSS)
					s.bougerGauche(8);
				else
					s.bougerGauche(14);
				break;

			case "ennemi balle3": //Balle d'ennemi de 3ème horde
				if(!vsMINIBOSS)
					s.bougerBas(4);
				else
					s.bougerBas(8); 
				break;

			case "balle": //Balle aléatoire [Après les 2 miniboss et les 3 hordes {130 secondes}]
				s.bougerBas(4);
				break;

			case "miniboss":
				//Si contre miniboss OU dernier miniboss
				if((getTempsTotal() > APPARITION_MINIBOSS + TEMPS_MINIBOSS_DESCENTE && vsMINIBOSS && getTempsTotal() < APPARITION_BALLES) || getTempsTotal() > APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) { //Dès qu'il arrête de descendre

					//Si 1er miniboss non tué OU Lors de la 2ème apparition il n'est tjr pas mort
					if((getTempsTotal() > APPARITION_3HORDES_AVANT_MINIBOSS2 && getTempsTotal() < APPARITION_MINIBOSS2) || (getTempsTotal() > APPARITION_3HORDES + 5 && getTempsTotal() < APPARITION_3HORDES + 10)) {
						s.bougerHaut(0.125); //il s'enfuit
					}
					else {

						//Il flotte
						if((getTemps()> 0 && getTemps() < 0.5) || (getTemps() > 1 && getTemps() < 1.5))
							s.bougerHaut(3);
						else
							s.bougerBas(3);

						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120)
							miniBossVersDroite = !miniBossVersDroite;

						if(miniBossVersDroite)
							s.bougerDroite(1);
						else
							s.bougerGauche(1);

						//Chaque 0.125 secondes il dire chacune des balles dans l'ordre
						try {
							miniBOSSTir((BOSS)s);
						}catch(FileNotFoundException e) {
							Manipulation.erreur("Le sprite de la balle du miniboss n'a pas été trouvé !", e);
						}
					}
				}
				break;

			case "miniboss2":
				if((getTempsTotal() > APPARITION_MINIBOSS2 + TEMPS_MINIBOSS_DESCENTE && getTempsTotal() < APPARITION_BALLES) || getTempsTotal() > APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) { //Dès qu'il arrête de descendre

					//Si pas mort alors que les 3 hordes sont là depuis 5 secondes
					if(getTempsTotal() > APPARITION_3HORDES + 5 && getTempsTotal() < APPARITION_3HORDES + 10) {
						s.bougerHaut(0.125); //S'enfuit
					}else {

						//Si derniers miniboss
						if(getTempsTotal() > APPARITION_DERNIERS_MINIBOSS)
							miniBossVersDroite2 = miniBossVersDroite; //ils vont tous dans le même sens

						//Il flotte
						if((getTemps() > 0 && getTemps() < 0.5) || (getTemps() > 1 && getTemps() < 1.5))
							s.bougerHaut(3);
						else
							s.bougerBas(3);

						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120)
							miniBossVersDroite2 = !miniBossVersDroite2;

						if(miniBossVersDroite2)
							s.bougerDroite(1);
						else
							s.bougerGauche(1);

						//Chaque 0.125 secondes il dire chacune des balles dans l'ordre
						try {
							miniBOSSTir((BOSS)s);
						}catch(FileNotFoundException e) {
							Manipulation.erreur("Le sprite de la balle du miniboss n'a pas été trouvé !",e);
						}
					}
				}
				break;

			case "miniboss3":
				if(getTempsTotal() > APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) { //Dès qu'il arrête de descendre

					//Il flotte
					if((getTemps() > 0 && getTemps() < 0.5) || (getTemps() > 1 && getTemps() < 1.5))
						s.bougerHaut(3);
					else
						s.bougerBas(3);

					if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120) {
						miniBossVersDroite = !miniBossVersDroite; //Même X que le miniboss1
					}

					if(miniBossVersDroite)
						s.bougerDroite(0.5);
					else
						s.bougerGauche(0.5);

					//Chaque 0.125 secondes il dire chacune des balles dans l'ordre
					try {
						miniBOSSTir((BOSS)s);
					}catch(FileNotFoundException e) {
						Manipulation.erreur("Le sprite de la balle du miniboss n'a pas été trouvé !", e);
					}
				}
				break;

			case "miniboss4":
				if(getTempsTotal() > APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) { //Dès qu'il arrête de descendre

					//Il flotte
					if((getTemps() > 0 && getTemps() < 0.5) || (getTemps() > 1 && getTemps() < 1.5))
						s.bougerHaut(3);
					else
						s.bougerBas(3);

					if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120) {
						miniBossVersDroite = !miniBossVersDroite; //Même X que le miniboss1
					}

					if(miniBossVersDroite)
						s.bougerDroite(0.5);
					else
						s.bougerGauche(0.5);

					//Chaque 0.125 secondes il dire chacune des balles dans l'ordre
					try {
						miniBOSSTir((BOSS)s);
					}catch(FileNotFoundException e) {
						Manipulation.erreur("Le sprite de la balle du miniboss n'a pas été trouvé !",e);
					}
				}
				break;

				//N: Nord, O: ouest...
			case "balle N":
				//Si c'est pas le boss OU le boss mais n'attaque pas en spirale: Les balles sont plus lentes (Car le boss aussi fait cette attaque)
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<FIN_BALLES_REBONDISSANTES)) 
					s.bougerHaut(0.25); 
				else
					s.bougerHaut(0.125);
				break;


			case "balle NO":
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<FIN_BALLES_REBONDISSANTES)) {
					s.bougerHaut(0.25);
					s.bougerDroite(0.25);
				}else {
					s.bougerHaut(0.125);
					s.bougerDroite(0.125);
				}

				break;

			case "balle O":
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<FIN_BALLES_REBONDISSANTES))
					s.bougerDroite(0.25);
				else
					s.bougerDroite(0.125);
				break;

			case "balle OS":
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<FIN_BALLES_REBONDISSANTES)) {
					s.bougerDroite(0.25);
					s.bougerBas(0.25);
				}else {
					s.bougerDroite(0.125);
					s.bougerBas(0.125);
				}
				break;

			case "balle S":
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<FIN_BALLES_REBONDISSANTES))
					s.bougerBas(0.25);
				else
					s.bougerBas(0.125);
				break;

			case "balle SE":
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<FIN_BALLES_REBONDISSANTES)) {
					s.bougerBas(0.25);
					s.bougerGauche(0.25);
				}else {
					s.bougerBas(0.125);
					s.bougerGauche(0.125);
				}
				break;

			case "balle E":
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<FIN_BALLES_REBONDISSANTES))
					s.bougerGauche(0.25);
				else
					s.bougerGauche(0.125);
				break;

			case "balle NE":
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<FIN_BALLES_REBONDISSANTES)) {
					s.bougerHaut(0.25);
					s.bougerGauche(0.25);
				}else {
					s.bougerHaut(0.125);
					s.bougerGauche(0.125);
				}

				break;

			case "boss":
				if(vsBOSS) {
					//Il flotte
					if((getTemps() > 0 && getTemps() < 0.5) || (getTemps() > 1 && getTemps() < 1.5))
						s.bougerHaut(1);
					else
						s.bougerBas(1);


					//Attaque étoile et Balles aléatoires + spirale
					if(((getTempsTotal() - tpsDébutBoss)>BALLES_TOUTES_DIRECTIONS && (getTempsTotal() - tpsDébutBoss)<BALLES_REBONDISSANTES) || (((getTempsTotal() - tpsDébutBoss)>SPIRALE && (getTempsTotal() - tpsDébutBoss)<MUR_BALLES))){
						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120) 
							bossVersDroite = !bossVersDroite; //Même X que le miniboss1

						if(bossVersDroite)
							s.bougerDroite(0.25);
						else
							s.bougerGauche(0.25);
						//Place le boss au milieu pour prochaine attaque (Balles rebondissantes)
					} else if (Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, BALLES_REBONDISSANTES, false)) { 
						s.setTranslateX(POSITION_BOSS_MILIEU);
						s.updateImagePos(); //Mise à jour de la position de l'image
					} else if ((getTempsTotal() - tpsDébutBoss)>BALLES_REBONDISSANTES && (getTempsTotal() - tpsDébutBoss)<BALLES_ALEATOIRES_COTES) { //Attaque balles rebondies
						if(s.getTranslateY() <= 50 || s.getTranslateY() >= 250)
							bossVersBas = !bossVersBas;

						if(bossVersBas)
							s.bougerBas(0.5);
						else
							s.bougerHaut(0.5);
					}else if (Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, SPIRALE, false)) { //Replace le boss vers le haut pour prochaine attaque (Spirale)
						//On le remet à sa place
						s.setTranslateY(50);
						s.updateImagePos();
					}else if (Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, 76.2, false)) { //Place le boss au milieu pour prochaine attaque (Mur de balles)
						s.setTranslateX(POSITION_BOSS_MILIEU);
						s.updateImagePos(); //Mise à jour de la position de l'image
					}else if (Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, 100.8, false)) { //Place le boss au milieu pour prochaine attaque (Mur de balles)
						s.setTranslateY(50);
						s.updateImagePos(); //Mise à jour de la position de l'image
					}else if((getTempsTotal() - tpsDébutBoss)>TUNNEL) { //Attaque tunnel
						//Si contre un mur
						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120) 
							bossVersDroite = !bossVersDroite; 

						if(bossVersDroite)
							s.bougerDroite(0.25);
						else
							s.bougerGauche(0.25);

						//Aléatoire toute les 0.5 secondes
						if(Manipulation.huitFoisParSecondes(getTemps())){
							if(Math.random() >= 0.5)
								bossVersDroite = false;
							else
								bossVersDroite = true;
						}
					}

					try {
						if((getTempsTotal() - tpsDébutBoss)<TUNNEL) { //Si c'est pas le tunnel
							bossTir();
						}else {
							if(Manipulation.huitFoisParSecondes(getTemps())) //Si c'est le tunnel
								bossTir();
						}

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;

			case "boss balle descente": 
				if((getTempsTotal() - tpsDébutBoss)<75) //Au début quand les balles descendent aléatoirement
					s.bougerBas(2);
				else
					s.bougerBas(0.5); //Mur balles
				break;

			case "balle rebondissante g": //Les balles qui rebondissent qui vont de base vers la gauche
				Balle_Rebondissante balle = (Balle_Rebondissante)s; //Pour bouger()
				balle.bouger(0.375); 
				balle.bougerBas(0.5);
				break;

			case "balle rebondissante d": //Pareil mais de base vers la droite
				Balle_Rebondissante ball = (Balle_Rebondissante)s; //Pour bouger()
				ball.bouger(0.375);
				ball.bougerBas(0.5);
				break;

			case "boss balle random g": //Balles qui apparaissent aléatoirement de gauche
				s.bougerDroite(0.375);
				break;

			case "boss balle random d":
				s.bougerGauche(0.375);
				break;

			case "balle tunnel": //Tunnel
				s.bougerBas(0.375);
				break;
			}

			//Si c'est une balle du joueur [Pas un switch car en switch on ne peut pas faire de .contains]
			if(s.getType().contains("joueur bullet")) {
				s.bougerHaut(1);
				if(s.getType().equals("joueur bullet gg")) s.bougerGauche(5); //Pour la puissance max
				if(s.getType().equals("joueur bullet g")) s.bougerGauche(10); //Pour la puissance 3
				if(s.getType().equals("joueur bullet d")) s.bougerDroite(10); //Pour la puissance 3
				if(s.getType().equals("joueur bullet dd")) s.bougerDroite(5); //Pour la puissance max
				//Si collision avec un ennemi
				Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().contains("ennemi") && !e.getType().contains("balle")).forEach(ennemi -> {
					if (Manipulation.touche(s, ennemi)){
						//Bonus Point tombe
						try {
							new BonusPoints(ennemi.getTranslateX() + 5, ennemi.getTranslateY() + 7, 15, 15, this);

							if(Math.random() > 0.985) //1.5% de chance
								new BonusPuissance(ennemi.getTranslateX()+10, ennemi.getTranslateY() + 7, 15, 15, this);

						} catch (FileNotFoundException e) {
							Manipulation.erreur("Le sprite du bonus de points n'a pas été trouvé !", e);
						} 

						ennemi.meurs(); //L'ennemi meurt
						s.meurs(); //La balle du joueur aussi
						Manipulation.scoreUp(this);
					}
				});

				//34s = On peut attaquer le boss
				if((getTempsTotal() > PEUT_ATTAQUER_MINIBOSS && getTempsTotal() < APPARITION_MINIBOSS2 - 2) ||(getTempsTotal() > APPARITION_MINIBOSS2 + TEMPS_MINIBOSS_DESCENTE && getTempsTotal() < APPARITION_DERNIERS_MINIBOSS) || (getTempsTotal() > APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) ) {
					//Si la balle touche le miniboss
					Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("miniboss")).forEach(miniboss -> {
						if (Manipulation.touche(s, miniboss)){
							s.meurs();
							miniBOSS.perdreVie(DEGAT_BALLE_JOUEUR_VS_MINIBOSS); //Il perd de la vie !
							Manipulation.scoreUp(this);

							//Mort du miniboss
							if(miniBOSS.getVieBossActuelle() <= 0) {

								if(getTempsTotal() > APPARITION_MINIBOSS2) { //Si miniboss II
									if(miniBOSS2.getVieBossActuelle() <= 0) vsMINIBOSS = false; //Si les deux boss sont morts, vsMINIBOSS = false;
								}else { //Si y'a qu'un boss
									vsMINIBOSS = false;
								}

								if(Math.random() > 0.75)
									try {
										new BonusVie(miniboss.getTranslateX()+10, miniboss.getTranslateY() + 7, 25, 25, this);
									} catch (FileNotFoundException e) {
										Manipulation.erreur("Le sprite du bonus de vie n'a pas pu être trouvé !", e);
									}

							}
						}	
					});

					//Dès qu'on peut attaquer (Finis de descendre)
					if((getTempsTotal() > APPARITION_MINIBOSS2  + TEMPS_MINIBOSS_DESCENTE && getTempsTotal() < APPARITION_DERNIERS_MINIBOSS) || (getTempsTotal() > APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE)) {
						Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("miniboss2")).forEach(miniboss -> {
							if (Manipulation.touche(s, miniboss)){
								s.meurs();
								miniBOSS2.perdreVie(DEGAT_BALLE_JOUEUR_VS_MINIBOSS); //Il perd de la vie !
								Manipulation.scoreUp(this);

								//Mort du miniboss
								if(miniBOSS2.getVieBossActuelle() <= 0) {
									if(miniBOSS.getVieBossActuelle() <= 0) vsMINIBOSS = false; //Si les deux boss sont morts

									if(Math.random() > 0.75)
										try {
											new BonusVie(miniboss.getTranslateX()+10, miniboss.getTranslateY() + 7, 25, 25, this);
										} catch (FileNotFoundException e) {
											Manipulation.erreur("Le sprite du bonus de vie n'a pas pu être trouvé !", e);
										}
								}
							}	
						});
					}

					if(getTempsTotal() > APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) {
						Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("miniboss3")).forEach(miniboss -> {
							if (Manipulation.touche(s, miniboss)){
								s.meurs();
								miniBOSS3.perdreVie(DEGAT_BALLE_JOUEUR_VS_MINIBOSS); //Il perd de la vie !
								Manipulation.scoreUp(this);

								//Mort du miniboss
								if(miniBOSS3.getVieBossActuelle() <= 0) {

									if(Math.random() > 0.75)
										try {
											new BonusVie(miniboss.getTranslateX()+10, miniboss.getTranslateY() + 7, 25, 25, this);
										} catch (FileNotFoundException e) {
											Manipulation.erreur("Le sprite du bonus de vie n'a pas pu être trouvé !", e);
										}
								}
							}	
						});

						Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("miniboss4")).forEach(miniboss -> {
							if (Manipulation.touche(s, miniboss)){
								s.meurs();
								miniBOSS4.perdreVie(DEGAT_BALLE_JOUEUR_VS_MINIBOSS); //Il perd de la vie !
								Manipulation.scoreUp(this);

								//Mort du miniboss
								if(miniBOSS4.getVieBossActuelle() <= 0) {

									if(Math.random() > 0.75)
										try {
											new BonusVie(miniboss.getTranslateX()+10, miniboss.getTranslateY() + 7, 25, 25, this);
										} catch (FileNotFoundException e) {
											Manipulation.erreur("Le sprite du bonus de vie n'a pas pu être trouvé !", e);
										}
								}
							}	
						});

						//Le boss
						if(vsBOSS) {
							//Si la balle touche le boss
							Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("boss")).forEach(bosss -> {
								if (Manipulation.touche(s, bosss)){
									s.meurs();
									boss.perdreVie(DEGAT_BALLE_JOUEUR_VS_BOSS); //Il perd de la vie !
									Manipulation.scoreUp(this);
								}

								//Mort du boss
								if(boss.getVieBossActuelle() <= 0) {
									bossBattu = true; //Mort
									setEnDialogue(true); //Dialogue de fin
									try {
										dialogue();
									} catch (FileNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							});


						}
					}
				}
			}});

		niveau();

		if(isNiveauTerminé()) fin();
	}

	/**
	 * Fait apparaître la première horde d'ennemis
	 * 
	 * @author Jordan
	 */
	private void premiereHorde() throws FileNotFoundException {
		//Toutes les 0.5 secondes un ennemi apparaît
		if(Manipulation.deuxFoisParSecondes(getTemps())) {
			new Ennemi(-10, 30, 40, 40, "Images/Niveau1/ennemi.png", "ennemi1", this);
		}
	}

	/**
	 * Fait apparaître la deuxième horde d'ennemis
	 * 
	 * @author Jordan
	 */
	private void deuxiemeHorde() throws FileNotFoundException {
		//Toutes les 0.5 secondes 2 ennemis apparaîssent, un à gauche et un à droite
		if(Manipulation.deuxFoisParSecondes(getTemps())) {
			new Ennemi(20, -10, 40, 40, "Images/Niveau1/ennemi.png", "ennemi2g", this); //2g car 2ème horde et est à gauche
			new Ennemi(NiveauInterface.LARGEUR_DECOR - 60, -10, 40, 40, "Images/Niveau1/ennemi.png", "ennemi2d", this);
		}
	}

	/**
	 * Fait apparaître la troisième horde d'ennemis
	 * 
	 * @author Jordan
	 */
	private void troisiemeHorde() throws FileNotFoundException{
		//Toutes les 0.5 secondes un ennemi apparaît
		if(Manipulation.deuxFoisParSecondes(getTemps())) {
			new Ennemi(NiveauInterface.LARGEUR_DECOR - 60, NiveauInterface.HAUTEUR_ECRAN-30, 40, 40, "Images/Niveau1/ennemi.png", "ennemi3", this);
		}
	}

	/**
	 * Fait apparaître les balles qui tombent aléatoirement (Vers la fin du niveau)
	 * 
	 * @author Jordan
	 */
	private void balles() throws FileNotFoundException{
		if(Math.random() > 0.95)
			new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/ennemi balle.png", "balle", this); //X aléatoire
	}

	public void texteNiveau() throws FileNotFoundException {
		//On ne fait pas Manipulation.ajouterTexte car la il a un effet d'opacité
		Texte niv = new Texte("NIVEAU I - COURSE POURSUITE", "niv1");
		niv.setX(-10);
		niv.setY(NiveauInterface.HAUTEUR_ECRAN/2);
		niv.setFont(Font.loadFont(new FileInputStream("Polices/NIVEAU.ttf"), 30)); //Charge la police et la change
		niv.setFill(Paint.valueOf("RED")); //Couleur
		niv.setOpacity(0); //Il va apparaître
		Manipulation.ajouterJeu(getRoot(), niv);
	}

	@Override
	public void dialogue() throws FileNotFoundException {
		super.dialogue();
		//Dialogue avant le boss
		if(!bossBattu) {
			switch(getNbEspaceAppuyés()) {
			case 0:
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/Niveau1/Dialogue/boss.png", NiveauInterface.LARGEUR_DECOR-420, 200, 410, 580);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tous les Totsuzens ont ete extermines.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1: 
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Il ne reste que toi !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Bande d'enfoires, vous allez le payer !!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;
			}

			if(getNbEspaceAppuyés() > 2) { //Fin du dialogue
				setEnDialogue(false);
				boss();	 
				setNbEspaceAppuyés(0); //Remise à 0 pour le dialogue de fin
			}
		}else {
			switch(getNbEspaceAppuyés()) {
			case 0:
				//Toutes les balles ennemies meurent
				Manipulation.sprites(root).stream().filter(e -> e.getType().contains("balle")).forEach(balle -> {
					balle.meurs();
				});

				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/Niveau1/Dialogue/boss_cassé.png", NiveauInterface.LARGEUR_DECOR-390, 100, 410, 680);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Qui vous a envoye ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1: 
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Le chef tout puissant... Il...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "IL VOUS AURA TOT OU TARD !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Hahaha. Ou est-t-il ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 4:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "....", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 5:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tsss, il s'est eteint.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;
			}

			if(getNbEspaceAppuyés() > 5) { //Fin du dialogue
				setEnDialogue(false);
				setNiveauTerminé(true);
				setTpsDébScore(getTempsTotal());
			}
		}
	}

	public void boss() throws FileNotFoundException {
		//Pour effacer tout ce qui est dialogue
		Manipulation.toutEffacer(this);
		Manipulation.ajouterImage(getRoot(), "Fonds/NIVEAU_I/bg_boss.gif", 0, 0, NiveauInterface.LARGEUR_DECOR, NiveauInterface.LARGEUR_ECRAN);
		Manipulation.ajouterImage(getRoot(),"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

		//On réaffiche les personnages
		Manipulation.ajouterJeu(getRoot(), joueur.getImg());
		Manipulation.ajouterJeu(getRoot(), boss.getImg());

		//On enlève puis remet la barre de vie 
		Manipulation.supprimer(this, boss.getBarreVie());
		Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

		Manipulation.updateScore(this); //On remet le score

		mediaPlayer.stop(); //Fin de la musique du niveau 1

		//Musique du boss
		mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveau1/boss.mp3").toURI().toString()));
		mediaPlayer.play();
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //Répète a l'infini

		vsBOSS = true;
		tpsDébutBoss = getTempsTotal(); //On enregistre le temps de début du combat

		//On ne fait pas Manipulation.ajouterTexte car on doit récupérer ce nom pour pas qu'il soit supprimé lors de updateScore()
		Texte niv = new Texte(nomBOSS, "nomboss");
		niv.setX(NiveauInterface.X_NOM_BOSS);
		niv.setY(NiveauInterface.Y_NOM_BOSS);
		niv.setFont(Font.loadFont(new FileInputStream("Polices/BOSS.ttf"), NiveauInterface.X_TEXTE_DIALOGUE)); //Charge la police et la change
		niv.setFill(Paint.valueOf("BLACK")); //Couleur
		Manipulation.ajouterJeu(getRoot(), niv);
	}

	public void miniBOSSTir(BOSS b) throws FileNotFoundException {
		if(getTempsTotal() < APPARITION_DERNIERS_MINIBOSS) { //Si c'est pas les derniers miniboss
			if(Manipulation.siTpsA(getTemps(), 0.125, false) || Manipulation.siTpsA(getTemps(), 1.125, false)) {
				if(b.getType().equals("miniboss"))
					b.tir("balle N");
				else
					b.tir("balle S");
			}
			if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 1.25, false)) {
				if(b.getType().equals("miniboss"))
					b.tir("balle NO");
				else
					b.tir("balle SE");
			}
			if(Manipulation.siTpsA(getTemps(), 0.375, false) || Manipulation.siTpsA(getTemps(), 1.375, false)) {
				if(b.getType().equals("miniboss"))
					b.tir("balle O");
				else
					b.tir("balle E");
			}
			if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false)) {
				if(b.getType().equals("miniboss"))
					b.tir("balle OS");
				else
					b.tir("balle NE");
			}
			if(Manipulation.siTpsA(getTemps(), 0.625, false) || Manipulation.siTpsA(getTemps(), 1.625, false)) {
				if(b.getType().equals("miniboss"))
					b.tir("balle S");
				else
					b.tir("balle N");
			}
			if(Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.75, false)) {
				if(b.getType().equals("miniboss"))
					b.tir("balle SE");
				else
					b.tir("balle NO");
			}
			if(Manipulation.siTpsA(getTemps(), 0.875, false) || Manipulation.siTpsA(getTemps(), 1.875, false)) {
				if(b.getType().equals("miniboss"))
					b.tir("balle E");
				else
					b.tir("balle O");
			}
			if(Manipulation.siTpsA(getTemps(), 0.999, false) || Manipulation.siTpsA(getTemps(), 1.999, false)) {
				if(b.getType().equals("miniboss"))
					b.tir("balle NE");
				else
					b.tir("balle OS");
			}
		} else { //SI DERNIER MINIBOSS
			if(Manipulation.siTpsA(getTemps(), 0.66, false))
				b.tir("balle OS");
			if(Manipulation.siTpsA(getTemps(), 1.32, false))
				b.tir("balle S");
			if(Manipulation.siTpsA(getTemps(), 1.98, false))
				b.tir("balle SE");	
		}
	}

	@Override
	public void bossTir() throws FileNotFoundException {
		// =================================== BALLES ALEATOIRES QUI TOMBENT ==================================== \\
		if((getTempsTotal() - tpsDébutBoss) > BALLES_ALEATOIRES_HAUT && (getTempsTotal() - tpsDébutBoss) < FIN_BALLES_ALEATOIRES_HAUT) {
			if(Math.random() > 0.95) 
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/niveau1/boss balle.png", "boss balle descente", this); //X aléatoire
		} 
		// =================================== BALLES TOUTES DIRECTIONS ==================================== \\
		else if((getTempsTotal()-tpsDébutBoss)>BALLES_TOUTES_DIRECTIONS && (getTempsTotal()-tpsDébutBoss)<BALLES_REBONDISSANTES) { 
			if(Manipulation.deuxFoisParSecondes(getTemps())) {
				boss.tir("balle N");
				boss.tir("balle NE");
				boss.tir("balle E");
				boss.tir("balle SE");
				boss.tir("balle S");
				boss.tir("balle OS");
				boss.tir("balle O");
				boss.tir("balle NO");
			}
		}
		// =================================== BALLES REBONDISSANTES ==================================== \\
		else if((getTempsTotal()-tpsDébutBoss)>BALLES_REBONDISSANTES && (getTempsTotal()-tpsDébutBoss)<FIN_BALLES_REBONDISSANTES) { 
			if(Manipulation.deuxFoisParSecondes(getTemps())) {
				boss.tir("balle rebondissante g");
				boss.tir("balle rebondissante d");
			}
		} 
		// =================================== BALLES ALEATOIRES A GAUCHE & DROITE ==================================== \\
		else if((getTempsTotal() - tpsDébutBoss) > BALLES_ALEATOIRES_COTES && (getTempsTotal() - tpsDébutBoss) < MUR_BALLES) {
			//Part de la droite
			if(Math.random() > 0.975)
				new BossBalle(NiveauInterface.LARGEUR_DECOR-35, Math.random() * NiveauInterface.LARGEUR_ECRAN, "Images/niveau1/boss balle.png", "boss balle random d", this); //X aléatoire

			//Part de la gauche
			if(Math.random() > 0.975)
				new BossBalle(5, Math.random() * NiveauInterface.LARGEUR_ECRAN, "Images/niveau1/boss balle.png", "boss balle random g", this); //X aléatoire

			// =================================== PAREIL + LA SPIRALE DES MINIBOSS EN + RAPIDE  ==================================== \\
			if((getTempsTotal()-tpsDébutBoss)>SPIRALE) { 
				if(Manipulation.siTpsA(getTemps(), 0.125, false) || Manipulation.siTpsA(getTemps(), 1.125, false))
					boss.tir("balle N");
				if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 1.25, false))
					boss.tir("balle NO");
				if(Manipulation.siTpsA(getTemps(), 0.375, false) || Manipulation.siTpsA(getTemps(), 1.375, false))
					boss.tir("balle O");
				if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false))
					boss.tir("balle OS");
				if(Manipulation.siTpsA(getTemps(), 0.625, false) || Manipulation.siTpsA(getTemps(), 1.625, false))
					boss.tir("balle S");
				if(Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.75, false))
					boss.tir("balle SE");
				if(Manipulation.siTpsA(getTemps(), 0.875, false) || Manipulation.siTpsA(getTemps(), 1.875, false))
					boss.tir("balle E");
				if(Manipulation.siTpsA(getTemps(), 0.999, false) || Manipulation.siTpsA(getTemps(), 1.999, false))
					boss.tir("balle NE");
			}
		}
		// =================================== MUR DE BALLES ==================================== \\
		else if((getTempsTotal() - tpsDébutBoss) > MUR_BALLES && (getTempsTotal() - tpsDébutBoss) < FIN_MUR_BALLES) {
			//2 fois par secondes
			if(Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
				//Pas le même écart sinon ça sera toujours le même mur avec les mêmes trous 
				int ecartBalles = (int) (Math.random() * 100)+100; //+100 évite le ecartBalles = 0

				//Création de la ligne de balles
				for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles)
					new BossBalle(i, -10, "Images/niveau1/boss balle.png", "boss balle descente", this); //X aléatoire
			}
		}
		// =================================== TUNNEL DE BALLES ==================================== \\
		else if((getTempsTotal() - tpsDébutBoss) > TUNNEL) {
			//L'écart entre les balles en largeur afin que cela ait une apparance de mur
			int ecartMur = NiveauInterface.TAILLE_BALLE_BOSS; 

			//Mettre cette limite sert à faire en sorte que pour le tunnel, le mur de droite ne soit pas sur le tableau
			int limiteBalle = NiveauInterface.LARGEUR_DECOR - 192;

			double toutAGauche = boss.getTranslateX()-100; //La balle la plus à gauche du BOSS
			double toutADroite;

			//Si le boss n'est pas proche du tableau qui va faire spawn des balles sur le tableau..
			if(boss.getTranslateX() <= limiteBalle)
				toutADroite = boss.getTranslateX()+170; //..tout se passe normal
			else
				toutADroite = NiveauInterface.LARGEUR_DECOR-35; //Sinon la limite c'est la fin du décor

			//Création des balles tout à gauche et droite
			new BossBalle(toutAGauche, boss.getTranslateY()+120, "Images/niveau1/boss balle.png", "balle tunnel", this);

			if(boss.getTranslateX() <= limiteBalle) //Si le boss pas trop proche du tableau
				new BossBalle(toutADroite, boss.getTranslateY()+120, "Images/niveau1/boss balle.png", "balle tunnel", this);

			//Création d'un mur de balles du milieu à la toute gauche [1er tunnel gauche]
			for(int i=0;i<toutAGauche;i+=ecartMur) {
				new BossBalle(i, boss.getTranslateY()+120, "Images/niveau1/boss balle.png", "balle tunnel", this);
			}

			//De même pour le mur de balles du milieu à la toute droite [2ème tunnel gauche] si le boss n'est pas proche du tableau
			if(boss.getTranslateX() <= limiteBalle) {
				for(int i=NiveauInterface.LARGEUR_DECOR-35; i>toutADroite;i-=ecartMur) {
					new BossBalle(i, boss.getTranslateY()+120,  "Images/niveau1/boss balle.png", "balle tunnel", this);
				}
			}

		}
	}

	public void niveau() throws FileNotFoundException {
		// ============================ LE TEXTE DU DEBUT ============================= \\
		//0.8 secondes après le début du niveau on affiche le nom du niveau
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_NOM_NIVEAU, true))
			texteNiveau();

		//De l'affichage à la disparition
		if(getTempsTotal() > APPARITION_NOM_NIVEAU && getTempsTotal() < FIN_NOM_NIVEAU) {
			Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("niv1")).forEach(texte -> {

				if (getTempsTotal() > APPARITION_NOM_NIVEAU && getTempsTotal() < NOM_NIVEAU_EN_APPARITION) //Début à 2 seconde il monte en opacité [2 à 4 il stagne au max]
					texte.setOpacity(getTempsTotal()/2);
				else if (getTempsTotal() > NOM_NIVEAU_EN_DISPARITION && getTempsTotal() < NOM_NIVEAU_EN_DISPARITION2) //4s à 7s il redescend en opacité
					texte.setOpacity(4/(getTempsTotal()*1.25));
				else if (getTempsTotal() > NOM_NIVEAU_EN_DISPARITION2 && getTempsTotal() < FIN_NOM_NIVEAU) //7s à 9s il redescend encore plus
					texte.setOpacity(4/(getTempsTotal()*3.5));

				texte.updatePos(this, texte.getTranslateX() + 0.25, NiveauInterface.HAUTEUR_ECRAN/4);
			});
		}

		//Après son mouvement on le supprime
		if(Manipulation.siTpsA(getTempsTotal(), FIN_NOM_NIVEAU, false)) {
			Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("niv1")).forEach(texte -> {
				Manipulation.supprimer(this, texte);
			});
		}

		// ======================= PREMIERE HORDE D'ENNEMIS ==================== \\
		if(getTempsTotal() > APPARITION_1ERS_ENNEMIS && getTempsTotal() < 20){
			premiereHorde();
		}

		// ======================= DEUXIEME HORDE D'ENNEMIS ==================== \\
		if(getTempsTotal() > APPARITION_2EMES_ENNEMIS && getTempsTotal() < 30) {
			deuxiemeHorde();
		}

		// ======================================= MINI BOSS =================================== \\
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_MINIBOSS, false)){ //La méthode siTpsA est trop rapide pour invoquer 1 boss
			//Le mini-boss apparaît
			miniBOSS = new MINIBOSS1(POSITION_MINIBOSS_MILIEU, -10, 100, 100, "miniboss", VIE_MINIBOSS, PATH_MINIBOSS, new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this), this);
			vsMINIBOSS = true;
		}

		//Il descend
		if(getTempsTotal() > APPARITION_MINIBOSS && getTempsTotal() < APPARITION_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) {
			miniBOSS.bougerBas(0.25);
		}

		// ===================================== MINI BOSS + 1ER HORDE ============================== \\
		if(getTempsTotal() > APPARITION_1HORDE_AVEC_MINIBOSS && getTempsTotal() < APPARITION_1_ET_2HORDE) {
			premiereHorde();
		}

		// ===================================== 1ER HORDE + 2EME HORDE ============================== \\
		if(getTempsTotal() > APPARITION_1_ET_2HORDE && getTempsTotal() < APPARITION_3HORDES_AVANT_MINIBOSS2) {
			premiereHorde();
			deuxiemeHorde();
		}

		// ===================================== LES 3 HORDES ============================== \\
		if(getTempsTotal() > APPARITION_3HORDES_AVANT_MINIBOSS2 && getTempsTotal() < APPARITION_MINIBOSS2 - 0.5) {
			premiereHorde();
			deuxiemeHorde();
			troisiemeHorde();
			if(getTempsTotal() > APPARITION_3HORDES_AVANT_MINIBOSS2 + 3) vsMINIBOSS = false;
		}

		// ======================================= MINI BOSS II =================================== \\
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_MINIBOSS2, false)){
			//Le mini-boss apparaît
			miniBOSS = new MINIBOSS1(100, -10, 100, 100, "miniboss", VIE_MINIBOSS, PATH_MINIBOSS, new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this) ,this);
			miniBOSS2 = new MINIBOSS1(NiveauInterface.LARGEUR_DECOR - 120, -10, 100, 100, "miniboss2", VIE_MINIBOSS, PATH_MINIBOSS,new BarreDeVie(10,85,VIE_MINIBOSS,10, "vie", this) , this);

		}

		//Il descend
		if(getTempsTotal() > APPARITION_MINIBOSS2 && getTempsTotal() < APPARITION_MINIBOSS2 + TEMPS_MINIBOSS_DESCENTE) {
			miniBOSS.bougerBas(0.25);
			miniBOSS2.bougerBas(0.25);
		}

		//On commence à l'attaquer
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_MINIBOSS2 + TEMPS_MINIBOSS_DESCENTE, true))
			vsMINIBOSS = true;


		if(getTempsTotal() > APPARITION_3HORDES && getTempsTotal() < APPARITION_3HORDES + 18.5) {
			premiereHorde();
			deuxiemeHorde();
			troisiemeHorde();
		}

		if(getTempsTotal() > APPARITION_BALLES && getTempsTotal() < APPARITION_DERNIERS_MINIBOSS - 2) {
			//Apparition aléatoire de balles
			balles();
		}

		// ======================================= DERNIERS MINIBOSS =================================== \\
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_DERNIERS_MINIBOSS, false)){
			//Toutes les balles ennemies meurent
			Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().contains("balle")).forEach(balle -> {
				balle.meurs();
			});

			//On supprime tout ce qui a été affiché (Pour enlever l'ancien background)
			Manipulation.toutEffacer(this);

			//Changement de l'image de fond
			Manipulation.ajouterImage(getRoot(),"Fonds/Niveau_I/bg_boss.gif", -15, 0, NiveauInterface.LARGEUR_DECOR, NiveauInterface.HAUTEUR_ECRAN+50);

			//Réapparition du joueur (Car juste l'image avait été supprimée
			Manipulation.ajouterJeu(getRoot(), joueur.getImg()); 

			//Le mini-boss apparaît
			miniBOSS = new MINIBOSS1(25, -10, 100, 100, "miniboss", VIE_MINIBOSS, PATH_MINIBOSS, new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this) ,this);
			miniBOSS2 = new MINIBOSS1(175, -10, 100, 100, "miniboss2", VIE_MINIBOSS, PATH_MINIBOSS,new BarreDeVie(10,85,VIE_MINIBOSS,10, "vie", this) , this);
			miniBOSS3 = new MINIBOSS1(325, -10, 100, 100, "miniboss3", VIE_MINIBOSS, PATH_MINIBOSS, new BarreDeVie(10,130,VIE_MINIBOSS,10, "vie", this) ,this);
			miniBOSS4 = new MINIBOSS1(475, -10, 100, 100, "miniboss4", VIE_MINIBOSS, PATH_MINIBOSS,new BarreDeVie(10,175,VIE_MINIBOSS,10, "vie", this) , this);
		}

		//Il descend
		if(getTempsTotal() > APPARITION_DERNIERS_MINIBOSS && getTempsTotal() < APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) {
			miniBOSS.bougerBas(0.25);
			miniBOSS2.bougerBas(0.25);
			miniBOSS3.bougerBas(0.25);
			miniBOSS4.bougerBas(0.25);
		}

		//On commence à l'attaquer
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE, false))
			vsMINIBOSS = true;

		// =============================================================== BOSS =========================================================== \\

		//Si les 4 derniers miniboss meurent
		if((getTempsTotal() > APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) && !isLockDialogue()) {
			if(miniBOSS.getVieBossActuelle() <= 0 && miniBOSS2.getVieBossActuelle() <= 0 && miniBOSS3.getVieBossActuelle() <= 0 && miniBOSS4.getVieBossActuelle() <= 0) {

				//Le boss apparaît
				boss = new BOSS1((NiveauInterface.LARGEUR_DECOR)/2-75, -100, 100, 100, "boss", VIE_MINIBOSS, PATH_BOSS, new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this) ,this);
				setLockDialogue(true); //Pour le lancer une fois 
				tpsDescenteBoss = getTempsTotal();
			}
		}

		//L'animation de sa descente
		if((getTempsTotal() - tpsDescenteBoss) < 2)
			boss.bougerBas(0.25);

		//Le dialogue commence
		if(Manipulation.siTpsA((getTempsTotal() - tpsDescenteBoss), 2, false)) {
			setEnDialogue(true); //Il est en dialogue, il ne joue pas
			dialogue(); //DIALOGUE AVANT LE BOSS
		}
	}

	/**
	 * La fin du niveau, le calcul du score final
	 * 
	 * @author Jordan
	 */
	public void fin() {
		super.fin();

		//Pour le calcul final...
		int difficulté = 0;

		switch(Main.getDifficulté()) {
		case FACILE:
			difficulté = 2;
			break;

		case NORMAL:
			difficulté = 4;
			break;

		case DIFFICILE:
			difficulté = 6;
			break;

		case FRAGILE_COMME_DU_VERRE:
			difficulté = 20;
			break;
		}

		//Le score final
		double scoreFinal = (getScore() * (double)(Main.getVies()/2)+1 * joueur.getPuissance()) * difficulté;

		//Le rang final
		String rang = "";
		if(scoreFinal > RANG_SALAR) 
			rang = "SALAR";
		else if(scoreFinal > RANG_S) 
			rang = "S";
		else if(scoreFinal >RANG_A) 
			rang = "A";
		else if(scoreFinal > RANG_B) 
			rang = "B";
		else
			rang = "C";

		if(Manipulation.siTpsA((getTempsTotal()-getTpsDébScore()), 2, false)){
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_SCORE, "SCORE -- " + getScore(), "Polices/SCORE.ttf", 40, "YELLOW");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsDébScore()), 4, false)){
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_VIES, "VIES RESTANTES -- " + Main.getVies(), "Polices/SCORE.ttf", 40, "YELLOW");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsDébScore()), 6, false)){
			if(Main.getDifficulté() == Difficulté.FRAGILE_COMME_DU_VERRE)
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_DIFFICULTE, "DIFFICULTE -- MAX", "Polices/SCORE.ttf", 40, "RED");
			else
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_DIFFICULTE, "DIFFICULTE -- " + Main.getDifficulté().toString(), "Polices/SCORE.ttf", 40, "YELLOW");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsDébScore()), 8, false)){
			if(joueur.getPuissance() < NiveauInterface.PUISSANCE_MAX)
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_PUISSANCE, "PUISSANCE -- " + joueur.getPuissance(), "Polices/SCORE.ttf", 40, "YELLOW");
			else
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_PUISSANCE, "PUISSANCE -- MAX", "Polices/SCORE.ttf", 40, "RED");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsDébScore()), 12, false)){
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_SCORE_FINAL, "SCORE FINAL -- " + scoreFinal, "Polices/SCORE.ttf", 40, "RED");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsDébScore()), 13, false)){
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_RANG, "RANG -- " + rang, "Polices/SCORE.ttf", 60, "PURPLE");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsDébScore()), 16, false)){
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS+10, NiveauInterface.POSITION_Y_STATS_QUITTER, "APPUYEZ SUR ESPACE POUR QUITTER", "Polices/SCORE.ttf", 30, "ORANGE");
			setPeutQuitter(true);
		}
	}
}
