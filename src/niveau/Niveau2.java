package niveau;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

import appli.Main;
import balles.Balle_Rebondissante;
import bonus.Bonus;
import bonus.BonusPoints;
import bonus.BonusPuissance;
import bonus.BonusVie;
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
import personnage.BOSS;
import personnage.BOSS2;
import personnage.BarreDeVie;
import personnage.Ennemi;
import personnage.Joueur;
import personnage.MINIBOSS1;
import texte.Texte;

public class Niveau2 extends Niveau{
	private static Media son = new Media(new File("Sons/niveau2/musique.mp3").toURI().toString());
	private static MediaPlayer mediaPlayer = new MediaPlayer(son);
	private static final int APPARITION_NOM_NIVEAU = 5;
	private static final int NOM_NIVEAU_EN_APPARITION = 6; //Augmentation de son opacité
	private static final int NOM_NIVEAU_EN_DISPARITION = 8; //Descente de son opacité
	private static final int NOM_NIVEAU_EN_DISPARITION2 = 9; //Pareil mais plus vite
	private static final int FIN_NOM_NIVEAU = 10; //Il disparaît
	private static final int APPARITION_1ERS_ENNEMIS = 14; //Premiers ennemis (1ère horde)
	private static final double APPARITION_2EMES_ENNEMIS = 39; //Deuxièmes ennemis (2ème horde)
	private static final int APPARITION_MINIBOSS = 37; // Le mini-boss apparaît
	private static final double PEUT_ATTAQUER_MINIBOSS =39 ; //Le mini-boss attaque
	private static final double APPARITION_3HORDES_AVANT_MINIBOSS2 = 66.5; //La troisième horde en même dans que la 1 et 2 (Première apparition de la 3ème)
	private static final int APPARITION_MINIBOSS2 = 77; //Les 2 mini-boss apparaîssent
	private static final int APPARITION_3HORDES = 75; //Les trois hordes d'un coup après l'apparition des 2 mini-boss
	private static final double APPARITION_BALLES = 130; //Les balles qui tombent aléatoirement
	private static final double APPARITION_DERNIERS_MINIBOSS = 153;
	private static final double APPARITION_DERNIER_HORDE = 100;
	private static final int TEMPS_MINIBOSS_DESCENTE = 2; //Le temps que prennent les mini-boss a descendre pour après attaquer
	// ================================== Variables qui gèrent les miniboss ==================================
	private static BOSS miniBOSS = null; //Le mini-boss

	private static boolean miniBossVersDroite = false; //Va-t-il vers la droite ?
	private static boolean miniBossVersDroite2 = true; //Va-t-il vers la droite ?
	private static boolean vsMINIBOSS = false; //Actuellement contre plusieurs miniboss en même tps ?

	private static final int VIE_MINIBOSS = 565;
	private static double DEGAT_BALLE_JOUEUR_VS_MINIBOSS = 4.8;
	// ================================== Variables qui gèrent le boss ==================================
	private static BOSS2 boss = null; //Le BOSS
	private static boolean vsBOSS = false; //Actuellement contre un boss ?
	private static boolean bossBattu = false; //BOSS battu ?
	private boolean bossVersDroite = false; //Le boss bouge-t-il vers la droite ? (Pour gérer son mouvement)
	private boolean bossVersBas = false; //Vers le bas ?
	private static String nomBOSS = "FRONTIERATOR";
	private double tpsDébutBoss = 999; //A quel moment le combat contre le boss commence ?
	private double tpsDescenteBoss = 2; //A quel moment le boss descend ? (Petite "cinématique" quand il apparaît) [-9999] pour pas que le boss apparaisse au début du niveau (Voir code)
	private static double DEGAT_BALLE_JOUEUR_VS_BOSS = 1;
	private boolean lockBoss = false;

	private static double PREMIERE_SALVE = 4;
	@Override
	public void fin() {
		// TODO Auto-generated method stub

	}

	@Override
	public Parent createNiveau(Stage niveau) throws IOException {
		if (Main.son()) mediaPlayer.play();

		root.setPrefSize(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); //Met à jour les dimensions du jeu
		Manipulation.ajouterImage(root,"Fonds/NIVEAU_II/FOND.gif", -15, 0, NiveauInterface.LARGEUR_DECOR, NiveauInterface.HAUTEUR_ECRAN+25); //Image de fond
		Manipulation.ajouterImage(root,"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

		//Le joueur et sa grazebox
		grazeBox = new Joueur(295,695,NiveauInterface.LARGEUR_JOUEUR,NiveauInterface.HAUTEUR_JOUEUR, "grazebox", "Images/Transparent.png", this);
		joueur = new Joueur(300,700,NiveauInterface.LARGEUR_GRAZEBOX,NiveauInterface.HAUTEUR_GRAZEBOX, "joueur", "Images/Joueur/joueur.png", this);

		AnimationTimer timer = new AnimationTimer() { //Le timer qui va gérer l'animation, etc.

			/*
			 * @brief Lié au timer
			 *        Va gérer tout ce que doit faire le joueur quand il appuie sur les touches
			 */
			public void handle(long arg0) {
				if (isBougeHaut()) { //Il a appuyé sur la flèche du HAUT
					joueur.bougerHaut(1);
					grazeBox.bougerHaut(1); //Sa hitbox va le suivre de partout
				}
				if (isBougeBas()) { //Flèche du BAS
					joueur.bougerBas(1);
					grazeBox.bougerBas(1);
				}
				if (isBougeGauche()) { //GAUCHE
					joueur.bougerGauche(1);
					grazeBox.bougerGauche(1);
				}
				if (isBougeDroite()) { //DROITE
					joueur.bougerDroite(1);
					grazeBox.bougerDroite(1);
				}

				//Le mode focus
				if (isFocus()) { //La touche SHIFT
					Joueur g = (Joueur)grazeBox;
					joueur.focus(); //Il passe en mode focus
					g.focus();
				}

				//Si le joueur tire et que le  getTemps() d'attente après le tir d'une balle est fini
				if (isTiré() && !isCooldownTir()) {
					joueur.tir("joueur bullet"); //Il tire
					setTpsDébTir(getTempsTotal());  //Le temps du début est enregistré pour calculer le cooldown
					setCooldownTir(true); //Il passe en cooldown
				}

				//Joueur a perdu
				if(Main.getVies() == 0) {
					if(!isLockGameOver()) { //L'action doit se faire 1 fois
						setLockGameOver(true);
						niveau.close(); //Fermeture fenêtre

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
		return root;
	}
	@Override
	public void start(Stage NIV2) throws Exception {
		Scene niveau_II = new Scene(createNiveau(NIV2)); //Création du niveau

		NIV2.setScene(niveau_II); //La scène du niveau devient la scène principale
		NIV2.setTitle("NIVEAU II"); //Nom de la fenêtre
		NIV2.setResizable(false); //On ne peut pas redimensionner la fenêtre

		//On remet l'icône du jeu comme elle a changée car c'est une autre fênetre
		NIV2.getIcons().add(new Image("file:Images/Icone.png"));

		Manipulation.updateScore(this); //Met à jour le score pour le début du niveau

		NIV2.show(); //Affichage du niveau

		/*On gère les boutons tapés (Contrôles du jeu)
		 *[Quand on APPUIE sur la touche]
		 */
		niveau_II.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(!isEnDialogue()) { //Quand c'est en dialogue, le joueur ne joue pas
					switch (event.getCode()) { //Récupération de quelle touche appuyée
					case UP:    setBougeHaut(true); break;
					case DOWN:  setBougeBas(true); break;
					case LEFT:  setBougeGauche(true); break;
					case RIGHT: setBougeDroite(true); break;
					case SPACE: setTiré(true); break;
					case SHIFT: setFocus(true); break;
					default:
						break;
					}
				}if(isPeutQuitter()) { //A la fin du niveau
					switch(event.getCode()) {
					case SPACE:
						NIV2.close();

						Alert alerte = new Alert(AlertType.INFORMATION);
						alerte.setTitle("NIVEAU II TERMINE !"); //Le titre de la fênetre
						alerte.setHeaderText("Vous avez réussi le niveau II ! Niveau III débloqué !"); //Au dessus du texte
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
		niveau_II.setOnKeyReleased(new EventHandler<KeyEvent>() {
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


	@Override
	public void boss() throws FileNotFoundException {



		mediaPlayer.stop(); //Fin de la musique du niveau 1

		//Musique du boss
		//mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveau2/boss.mp3").toURI().toString()));
		//  mediaPlayer.play();
		//mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //Répète a l'infinis
		
		boss = new BOSS2((NiveauInterface.LARGEUR_DECOR)/2-75, -100, 100, 100, "boss", VIE_MINIBOSS, "Images/Niveau2/robot.png", new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this) ,this);

		vsBOSS = true;
		tpsDébutBoss = getTempsTotal(); //On enregistre le temps de début du combat
 
		//On ne fait pas Manipulation.ajouterTexte car on doit récupérer ce nom pour pas qu'il soit supprimé lors de updateScore()
		Texte niv = new Texte(nomBOSS, "nomboss");
		niv.setX(NiveauInterface.X_NOM_BOSS);
		niv.setY(NiveauInterface.Y_NOM_BOSS);
		niv.setFont(Font.loadFont(new FileInputStream("Polices/BOSS.ttf"), NiveauInterface.X_TEXTE_DIALOGUE)); //Charge la police et la change
		niv.setFill(Paint.valueOf("RED")); //Couleur
		Manipulation.ajouterJeu(root, niv);
	}

	@Override
	public void dialogue() throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void texteNiveau() throws FileNotFoundException {
		// TODO Auto-generated method stub
		Texte niv = new Texte("NIVEAU II - BARRIERE A LA LIBERTE", "niv2");
		niv.setX(-10);
		niv.setY(NiveauInterface.HAUTEUR_ECRAN/2);
		niv.setFont(Font.loadFont(new FileInputStream("Polices/NIVEAU.ttf"), 30)); //Charge la police et la change
		niv.setFill(Paint.valueOf("RED")); //Couleur
		niv.setOpacity(0); //Il va apparaître
		Manipulation.ajouterJeu(getRoot(), niv);
	}

	@Override
	public void niveau() throws FileNotFoundException {
		//Affiche le nom du niveau & Le fond change
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_NOM_NIVEAU, true))
			texteNiveau();

		//De l'affichage à la disparition
		if(getTempsTotal() > APPARITION_NOM_NIVEAU && getTempsTotal() < FIN_NOM_NIVEAU) {
			Manipulation.textes(root).stream().filter(e -> e.getID().equals("niv2")).forEach(texte -> {

				if (getTempsTotal() > APPARITION_NOM_NIVEAU && getTempsTotal() < NOM_NIVEAU_EN_APPARITION)
					texte.setOpacity(getTempsTotal()/2);
				else if (getTempsTotal() > NOM_NIVEAU_EN_DISPARITION && getTempsTotal() < NOM_NIVEAU_EN_DISPARITION2)
					texte.setOpacity(4/(getTempsTotal()*1.25));
				else if (getTempsTotal() > NOM_NIVEAU_EN_DISPARITION2 && getTempsTotal() < FIN_NOM_NIVEAU)
					texte.setOpacity(4/(getTempsTotal()*3.5));

				texte.updatePos(this, texte.getTranslateX() + 0.25, NiveauInterface.HAUTEUR_ECRAN/4);
			});
		}

		//Après son mouvement on le supprime
		if(Manipulation.siTpsA(getTempsTotal(), FIN_NOM_NIVEAU, false)) {
			Manipulation.textes(root).stream().filter(e -> e.getID().equals("niv2")).forEach(texte -> {
				Manipulation.supprimer(this, texte);
			});
		}

		// ======================= PREMIERE HORDE D'ENNEMIS ==================== \\
		if(getTempsTotal() > APPARITION_1ERS_ENNEMIS && getTempsTotal() < 25){
			premiereHorde();
		}
		// ======================= DEUXIEME HORDE D'ENNEMIS ==================== \\
		if(getTempsTotal() > APPARITION_2EMES_ENNEMIS && getTempsTotal() < 59) {
			deuxiemeHorde();
		}

		// ======================================= MINI BOSS =================================== \\
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_MINIBOSS, false)){ //La méthode siTpsA est trop rapide pour invoquer 1 boss
			//Le mini-boss apparaît
			miniBOSS = new MINIBOSS1((NiveauInterface.LARGEUR_DECOR/2)-70, -10, 100, 100, "miniboss", VIE_MINIBOSS, "Images/Niveau1/miniboss.png", new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this), this);
			vsMINIBOSS = true;
		}

		//Il descend
		if(getTempsTotal() > APPARITION_MINIBOSS && getTempsTotal() < APPARITION_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) {
			miniBOSS.bougerBas(0.25);
		}
		if(getTempsTotal() > APPARITION_3HORDES && getTempsTotal() < 100) {
			troisiemeHorde();
		}
		if(getTempsTotal() > APPARITION_DERNIER_HORDE && getTempsTotal() < 140) {
			quatriemeVage();
		}

		if(!lockBoss){
			boss();
			tpsDescenteBoss = getTempsTotal();
			lockBoss = true;
		}

		//L'animation de sa descente
		if((getTempsTotal() - tpsDescenteBoss) < 2)
			boss.bougerBas(0.25);

		}

		@Override
		public void miniBOSSTir(BOSS b) throws FileNotFoundException {
			// TODO Auto-generated method stub
			if(Math.random() > 0.99) {
				if(Math.random() > 0.5) {
					if(Math.random() > 0.5)
						b.tir("balle 1");
					else b.tir("balle 2");
				}
				else {
					b.tir("balle 3");
				}

			}
		}

		@Override
		public void bossTir() throws FileNotFoundException {
			System.out.println("a");
			// TODO Auto-generated method stub
			if(((int)getTempsTotal() - (int)tpsDébutBoss) % 3   == 0   ) {
				int c = (int)(Math.random()*(10-0+1)+0);
				switch(c) {
				case 0:boss.tir("raffale une");
				break;
				case 1:boss.tir("raffale deux");
				break;
				case 2:
					boss.tir("balle SO");
					boss.tir("balle S");
					boss.tir("balle N");
					boss.tir("balle NO");
					boss.tir("balle S");
					boss.tir("balle E");
					boss.tir("balle NE");

					break;
				case 3:
					boss.tir("balle SO");
					boss.tir("balle S");
					boss.tir("balle N");
					boss.tir("balle NO");
					boss.tir("balle S");
					boss.tir("balle E");
					boss.tir("balle SE");
					break;
				case 4:

					boss.tir("balle SO");
					boss.tir("balle S");
					boss.tir("balle N");
					boss.tir("balle NO");
					boss.tir("balle S");
					boss.tir("balle NE");
					boss.tir("balle SE");
					break;
				case 5:

					boss.tir("balle SO");
					boss.tir("balle S");
					boss.tir("balle N");
					boss.tir("balle NO");
					boss.tir("balle NE");
					boss.tir("balle E");
					boss.tir("balle SE");
					break;
				case 6:
					boss.tir("balle SO");
					boss.tir("balle S");
					boss.tir("balle N");
					boss.tir("balle NE");
					boss.tir("balle S");
					boss.tir("balle E");
					boss.tir("balle SE");
					break;
				case 7:
					boss.tir("balle SO");
					boss.tir("balle S");
					boss.tir("balle NE");
					boss.tir("balle NO");
					boss.tir("balle S");
					boss.tir("balle E");
					boss.tir("balle SE");
					break;
				case 8:
					boss.tir("balle SO");
					boss.tir("balle NO");
					boss.tir("balle N");
					boss.tir("balle NE");
					boss.tir("balle S");
					boss.tir("balle E");
					boss.tir("balle SE");
					break;
				case 9:
					boss.tir("balle S");
					boss.tir("balle NO");
					boss.tir("balle N");
					boss.tir("balle NE");
					boss.tir("balle S");
					boss.tir("balle E");
					boss.tir("balle SE");
					break;

				default: break;
				};
			}

		}

		public void update() throws FileNotFoundException {
			//Le temps d'une frame en JavaFX (On le met ici pour pouvoir manipuler les objets selon le temps)
			setTemps(getTemps() + NiveauInterface.TEMPS_1_FRAME);
			setTempsTotal(getTempsTotal() + NiveauInterface.TEMPS_1_FRAME);

			//Si ça fait 100 millisecondes que le joueur a isTiré()
			if(getTempsTotal() - getTpsDébTir() > NiveauInterface.COOLDOWN_TIR) setCooldownTir(false);

			//Pour mieux calculer le temps
			if(getTemps() > 2)
				setTemps( 0);

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
					//Si collision avec un ennemi
					Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().contains("ennemi") || e.getType().contains("boss") || e.getType().contains("balle")).forEach(ennemi -> {
						if (Manipulation.touche(s, ennemi)){
							Manipulation.CollisionJoueur(this, s, false);
						}

						if(Manipulation.touche(getGrazeBox(), ennemi)) {
							if(!isCooldownTouché()) Manipulation.grazeUp(this);
						}
					});

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
					if(getTemps()<1)
						s.bougerBas(1);
					else s.bougerHaut(1);

					if(Math.random() > 0.99)
						s.tir("ennemi balle1");
					break;

				case "ennemi2g": //Ennemi 2ème horde colonne de gauche
					s.bougerDroite(1);
					if(getTemps()<1)
						s.bougerBas(1);
					else s.bougerHaut(1);

					if(Math.random() > 0.99)
						s.tir("ennemi balle1");
					break;

				case "ennemi2d": //Ennemi 2ème horde colonne de droite
					s.bougerGauche(1);
					if(getTemps()<1)
						s.bougerBas(1);
					else s.bougerHaut(1);

					if(Math.random() > 0.99)
						s.tir("ennemi balle1");
					break;

				case "ennemi3": //Ennemi 3ème horde
					s.bougerBas(1);

					if(Math.random() > 0.99) {
						if(Math.random() > 0.5)
							s.tir("ennemi balle3g");
						else s.tir("ennemi balle3d");
					}


					break;
				case "ennemi3h": //Ennemi 3ème horde
					s.bougerDroite(1);

					if(Math.random() > 0.99)
						s.tir("ennemi balle1");
					break; 

				case "ennemi balle1": //Balle d'ennemi de 1ère horde
					if(!vsMINIBOSS)
						s.bougerBas(4); //...elle se dirige vers le bas
					else
						s.bougerBas(8); //Quand on est contre le boss en même temps les balles ralentissent
					break;

				case "ennemi balle3d": //Balle d'ennemi de 3ème horde
					//          if(!vsMINIBOSS)
					//          s.bougerBas(4);
					//  else

					s.bougerDroite(4);
					break;
				case "ennemi balle3g": //Balle d'ennemi de 3ème horde
					//          if(!vsMINIBOSS)
					//          s.bougerBas(4);
					//  else
					s.bougerGauche(4);
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


					//N: Nord, O: ouest...
				case "balle 1":
					if(getTemps()<1)
						s.bougerBas(0.5);
					else s.bougerDroite(0.75);
					break;


				case "balle 2":
					if(getTemps()<1)
						s.bougerBas(0.5);
					else s.bougerGauche(0.75);
					break;
				case "balle 3":
					s.bougerBas(0.1);
					break;

				case "balle NO":
					if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40)) {
						s.bougerDroite(0.25);
						s.bougerHaut(0.25);
					}else {
						s.bougerDroite(0.125);
						s.bougerHaut(0.125);
					}
					break;
				case "balle N":
					if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40)) {
						s.bougerHaut(0.25);

					}else {
						s.bougerHaut(0.125);
					}
					break;
				case "balle OS":
					if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40)) {
						s.bougerDroite(0.25);
						s.bougerBas(0.25);
					}else {
						s.bougerDroite(0.125);
						s.bougerBas(0.125);
					}
					break;
				case "balle O":
					if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40)) {
						s.bougerDroite(0.25);
					}else {
						s.bougerDroite(0.125);
					}
					break;
				case "balle S":
					if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40))
						s.bougerBas(0.25);
					else
						s.bougerBas(0.125);
					break;

				case "balle SE":
					if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40)) {
						s.bougerBas(0.25);
						s.bougerGauche(0.25);
					}else {
						s.bougerBas(0.125);
						s.bougerGauche(0.125);
					}
					break;

				case "balle E":
					if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40))
						s.bougerGauche(0.25);
					else
						s.bougerGauche(0.125);
					break;

				case "balle NE":
					if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40)) {
						s.bougerHaut(0.25);
						s.bougerGauche(0.25);
					}else {
						s.bougerHaut(0.125);
						s.bougerGauche(0.125);
					}

					break;

				case "boss":
					try {
						bossTir();
					} catch (FileNotFoundException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					break;

				case "raffale une" :
					s.bougerBas(0.5);
					s.relocate(s.getX(), s.getY() + 40);
					break;
				case "raffale deux":
					if(getTemps() < 1)
						s.bougerBas(0.5);
					else if(1<getTemps()&&getTemps()<1.33)
						s.bougerGauche(1);
					if (1.33<getTemps()&& getTemps()<1.66) {
						s.bougerHaut(1);
					}
					else if(1.66<getTemps()&&getTemps()<2)
						s.bougerDroite(1);

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

							} catch (FileNotFoundException e2) {
								Manipulation.erreur("Le sprite du bonus de points n'a pas été trouvé !", e2);
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
									}else { //Si y'a qu'un boss
										vsMINIBOSS = false;
									}

									if(Math.random() > 0.75)
										try {
											new BonusVie(miniboss.getTranslateX()+10, miniboss.getTranslateY() + 7, 25, 25, this);
										} catch (FileNotFoundException e1) {
											Manipulation.erreur("Le sprite du bonus de vie n'a pas pu être trouvé !", e1);
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
									bossBattu = true; //Mortz

								}
							});


						}
					}

				}});
			niveau();

			if(isNiveauTerminé()) fin();

		}
		private void premiereHorde() throws FileNotFoundException {
			//Toutes les 0.5 secondes un ennemi apparaît
			if(Manipulation.deuxFoisParSecondes(getTemps())) {
				new Ennemi(0, 120, 40, 40, "Images/Niveau1/ennemi.png", "ennemi1", this);
			}
		}
		private void deuxiemeHorde() throws FileNotFoundException {
			//Toutes les 0.5 secondes 2 ennemis apparaîssent, un à gauche et un à droite
			if(Manipulation.deuxFoisParSecondes(getTemps())) {
				new Ennemi(20, -10, 40, 40, "Images/Niveau1/ennemi.png", "ennemi2g", this); //2g car 2ème horde et est à gauche
				new Ennemi(NiveauInterface.LARGEUR_DECOR - 60, -10, 40, 40, "Images/Niveau1/ennemi.png", "ennemi2d", this);
			}
		}
		private void troisiemeHorde() throws FileNotFoundException {
			//Toutes les 0.5 secondes 2 ennemis apparaîssent, un à gauche et un à droite
			if(Manipulation.deuxFoisParSecondes(getTemps())) {
				new Ennemi(0, 120, 40, 40, "Images/Niveau1/ennemi.png", "ennemi3h", this);
				new Ennemi(250,0 , 40, 40, "Images/Niveau1/ennemi.png", "ennemi3", this);
			}
		}

		private void quatriemeVage() throws FileNotFoundException{
			if(Manipulation.deuxFoisParSecondes(getTemps())) {

				new Ennemi(250, 120, 40, 40, "Images/Niveau1/ennemi.png", "ennemi3", this);
				new Ennemi(20, -10, 40, 40, "Images/Niveau1/ennemi.png", "ennemi2g", this); //2g car 2ème horde et est à gauche
				new Ennemi(NiveauInterface.LARGEUR_DECOR - 60, -10, 40, 40, "Images/Niveau1/ennemi.png", "ennemi2d", this);
			}
		}
	}