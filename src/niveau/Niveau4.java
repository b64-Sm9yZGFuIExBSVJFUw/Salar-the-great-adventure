package niveau;

import java.io.File;import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import appli.Difficulté;
import appli.Main;
import balles.BossBalle;
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
import personnage.BOSS;
import personnage.BOSS4;
import personnage.BarreDeVie;


import personnage.Ennemi;
import personnage.Joueur;
import sprite.Sprite;
import texte.Texte;

/**
 * Code source du niveau 4 du jeu dans lequel Salar essaye de s'évader de prison
 * dépourvu de pouvoirs.
 * 
 * @author Ephraim
 * 
 */
public class Niveau4 extends Niveau {

	// TIMING DU NIVEAU
	private static final double APPARITION_DIALOGUE = 2;
	private static final double APPARITION_DIALOGUE_1 = 3.7;
	private static final double APPARITION_DIALOGUE_2 = 5.5;
	private static final double FIN_DIALOGUE = 6.9;
	private static final double APPARITION_NOM_NIVEAU = 7;
	private static final int NOM_NIVEAU_EN_APPARITION = 9; // Augmentation de son opacité
	private static final int NOM_NIVEAU_EN_DISPARITION = 11; // Descente de son opacité
	private static final int NOM_NIVEAU_EN_DISPARITION2 = 13; // Pareil mais plus vite
	private static final int FIN_NOM_NIVEAU = 15; // Il disparaÃ®t

	private static final int HORDE1 = 17;
	private static final int FIN_HORDE1 = 37;
	private static final int PLUIE_BALLES1 = 38;
	private static final int FIN_PLUIE_BALLES1 = 48;
	private static final int SECONDE_HORDE = 50;
	private static final int FIN_HORDE2 = 70;
	private static final int PLUIE_BALLES2 = 71;
	private static final int FIN_PLUIE_BALLES2 = 81;
	private static final int HORDE3 = 82;
	private static final int FIN_HORDE3 = 110;
	private static final int PLUIE_BALLE_3 = 111;
	private static final int FIN_PLUIE_BALLES3 = 114;
	private static final int ARRIVEE_BOSS = 115;

	// Manipulations pour charger le son
	private static final String MUSIQUE_NIVEAU4 = "Sons/niveau4/niveau4.mp3"; // Le lien de la musique
	private static Media son = new Media(new File(MUSIQUE_NIVEAU4).toURI().toString());
	private static MediaPlayer mediaPlayer = new MediaPlayer(son);

	// Variables qui gÃ¨rent le boss
	private static BOSS gardien = null; // Le BOSS
	private double pourcentageVieBoss = 0; // Pour le combat du boss
	private static boolean vsBOSS = false; // Actuellement contre le boss ?
	private boolean bossVersDroite = false; // Le boss bouge-t-il vers la droite ? (Pour gérer son mouvement)
	private boolean debutNiveau = false; // Le boss bouge-t-il vers la droite ? (Pour gérer son mouvement)
	private double tpsDebutBoss = 0;
	private static final String NOM_BOSS = "Chef des Gardiens";

	private static final String PATH_BACKGROUND = "Fonds/NIVEAU_IV/bg_niveau4.gif";
	private static final String PATH_ICONE = "file:Images/Icone.png";
	private static final String PATH_GARDIEN_PRISON = "Images/Niveau4/garde_prison.png";
	private static final String PATH_JOUEUR = "Images/Joueur/joueur.png";

	private static final String PATH_SCORE = "Images/Score.png";
	private static final String PATH_GRAZEBOX = "Images/Transparent.png";
	private static final String PATH_BOSS = "Images/Niveau4/boss-niv4.png";
	private static final String PATH_ENNEMI_BALLE = "Images/Niveau4/balle_matraque.png";

	private static final String POLICE_NIVEAU = "Polices/NIVEAU.ttf";
	private static final String POLICE_DIALOGUE = "Polices/Dialogue.ttf";
	private static final String POLICE_SCORE = "Polices/SCORE.ttf";

	private static final double RANG_SALAR = 0;
	private static final double RANG_S = 0;
	private static final double RANG_A = 0;
	private static final double RANG_B = 0;

	/**
	 * Création du niveau 4
	 * 
	 * @return Renvoie l'objet oÃ¹ l'on va tout ajouter pour qu'il affiche
	 * @author Ephraim
	 */
	public Parent createNiveau(Stage niveau) throws IOException {
		root.setPrefSize(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); // Met Ã  jour les dimensions du
																						// jeu
		Manipulation.ajouterImage(root, PATH_BACKGROUND, -15, 0, NiveauInterface.LARGEUR_DECOR,
				NiveauInterface.HAUTEUR_ECRAN + 10); // Image
		// de
		// fond
		Manipulation.ajouterImage(root, PATH_SCORE, NiveauInterface.LARGEUR_DECOR - 15, 0,
				(NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR) + 25,
				NiveauInterface.HAUTEUR_ECRAN + 10); // Tableau de scores

		// Le joueur et sa grazebox
		// Copier Transparent.png dans un dossier niveau 4
		grazeBox = new Joueur(295, 695, 50, 50, "grazebox", PATH_GRAZEBOX, this);
		joueur = new Joueur(300, 700, NiveauInterface.LARGEUR_JOUEUR, NiveauInterface.HAUTEUR_JOUEUR, "joueur",
				PATH_JOUEUR, this);

		AnimationTimer timer = new AnimationTimer() { // Le timer qui va gérer l'animation, etc.
			/*
			 * @brief Lié au timer Va gérer tout ce que doit faire le joueur quand il appuie
			 * sur les touches
			 */
			public void handle(long arg0) {
				if (isBougeHaut()) { // Il a appuyé sur la flÃ¨che du HAUT
					joueur.bougerHaut(1);
					grazeBox.bougerHaut(1); // Sa hitbox va le suivre de partout
				}
				if (isBougeBas()) { // FlÃ¨che du BAS
					joueur.bougerBas(1);
					grazeBox.bougerBas(1);
				}
				if (isBougeGauche()) { // GAUCHE
					joueur.bougerGauche(1);
					grazeBox.bougerGauche(1);
				}
				if (isBougeDroite()) { // DROITE
					joueur.bougerDroite(1);
					grazeBox.bougerDroite(1);
				}

				// Le mode focus
				if (isFocus()) { // La touche SHIFT
					Joueur g = (Joueur) grazeBox;

					joueur.focus(); // Il passe en mode focus
					g.focus();

				}

				try {
					update();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					// System.err.println("Une erreur est survenue lors de la mise Ã  jour du niveau
					// (Update)! Une image n'a pas été trouvée !");
				}
			}

		}; // Fin de la méthode handle

		// On lance le timer
		timer.start();

		// On retourne donc l'objet qui va nous permettre d'afficher des choses
		return root;
	}

	/**
	 * Mise Ã  jour du jeu, l'affichage, (Les balles bougent etc.) Renvoie une
	 * exception si une image d'un sprite n'est pas trouvé
	 * 
	 * @author Ephraim
	 */
	public void update() throws FileNotFoundException {
		// Le temps d'une frame en JavaFX (On le met ici pour pouvoir manipuler les
		// objets selon le temps)
		setTemps(getTemps() + NiveauInterface.TEMPS_1_FRAME);
		setTempsTotal(getTempsTotal() + NiveauInterface.TEMPS_1_FRAME);

		if (getTempsTotal() - getTpsDébTouché() > 2) {
			setCooldownTouché(false);
			joueur.misAJour(false); // A mettre a jour
		}
		// Si le cooldown est fini, et que le sprite n'a pas été mis Ã  jour et que il
		// n'est pas en focus (Sinon il se met constamment en rouge)
		if (!isCooldownTouché() && !joueur.aEteMAJ() && !isFocus()) {
			joueur.getImg().setImage(new Image(new FileInputStream(PATH_JOUEUR)));
			joueur.misAJour(true);
		}

		if (vsBOSS() && (getTempsTotal() - tpsDebutBoss) > ARRIVEE_BOSS)
			gardien.perdreVie(0.125);

		// Pour chacun des sprite présent sur le jeu on va les mettre Ã  jour
		Manipulation.sprites(root).forEach(s -> {
			switch (s.getType()) {
			case "ennemi balle": // Si c'est une balle ennemie...
				s.bougerBas(1); // ...elle se dirige vers le bas
				break;

			case "balle":
				s.bougerBas(0.5);
				break;

			case "boss balle descente": // Si balle du boss
				// Si la balle sort du décor vers la droite (Va sur le score)
				gestionBossBalleDescente(s);

				break;

			case "joueur":
				gestionCollisionJoueur(s);
				break;

			case "ennemi": // Si c'est l'ennemi on va devoir le faire tirer
				s.bougerDroite(1);

				if (getTemps() > 2 && Math.random() < 0.3) { // Il TENTE de tirer toutes les 2 secondes
					// 30% que l'ennemi tire (Il TENTE)
					s.tir("ennemi balle"); // Il tire
				}
				break;

			case "BOSS":
				gestionBoss(s);
				break;

			}
		});

		if (getTemps() > 2)
			setTemps(0); // Pour mieux gérer le temps

		niveau();
	}

	private void gestionBossBalleDescente(Sprite s) {
		if (s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR + 25) {
			s.meurs();
		}

		try {
			bossTir();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		s.bougerBas(1); // La balle du BOSS va vers le bas
	}

	private void gestionBoss(Sprite s) {
		BOSS b = (BOSS) s; // Cast de SPRITE en BOSS

		// Si le boss est contre le bord du décor
		if (s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120)
			bossVersDroite = !bossVersDroite; // Alors son sens de mouvement s'inverse

		if (bossVersDroite)
			s.bougerDroite(1);
		else
			s.bougerGauche(1);

		if (getTemps() > 1) // Chaque seconde, il va changer de mouvement vertical pour un effet de survol
			b.bougerBas(1);
		else
			b.bougerHaut(1);

		if (pourcentageVieBoss > 0.66) { // Attaque tunnel
			if (getTemps() > 1)
				s.tir(null); // Les types sont déjÃ  dans BOSS.java
		} else if (pourcentageVieBoss > 0.33) { // Attaque plateformes
			if (getTemps() > 2)
				s.tir(null); // Les types sont déjÃ  dans BOSS.java
		} else { // Attaque pixels
			if (getTemps() > 1.5)
				s.tir(null); // Les types sont déjÃ  dans BOSS.java
		}
	}

	private void gestionCollisionJoueur(Sprite s) {
		if (!Niveau.isDebug()) { // Si pas en mode debug, le joueur n'est pas invincible
			// Si collision avec l'ennemi
			Manipulation.sprites(root).stream().filter(e -> e.getType().contains("ennemi")
					|| e.getType().contains("balle") || e.getType().contains("BOSS")).forEach(ennemi -> {
						if (Manipulation.touche(s, ennemi)) {
							Manipulation.CollisionJoueur(this, ennemi, true);
						}

						if (Manipulation.touche(grazeBox, ennemi) && !isCooldownTouché()) {
							Manipulation.grazeUp(this);
						}
					});
		}

		// Si collision avec le BOSS
		Manipulation.sprites(root).stream().filter(e -> e.getType().equals("BOSS")).forEach(ennemi -> {
			if (Manipulation.touche(s, ennemi)) {
				Manipulation.CollisionJoueur(this, ennemi, true);
			}

			if (Manipulation.touche(grazeBox, ennemi) && !isCooldownTouché()) {
				Manipulation.grazeUp(this);
			}

		});
	}

	/**
	 * Création du boss, renvoie une exception si son image de sprite n'est pas
	 * trouvée
	 * 
	 * @author Ephraim
	 */
	@Override
	public void boss() throws FileNotFoundException {
		tpsDebutBoss = getTempsTotal();
		// On supprime tout ce qui a été affiché (Pour enlever l'ancien background)
		Manipulation.toutEffacer(this);

		// Changement de l'image de fond
		Manipulation.ajouterImage(root, PATH_BACKGROUND, 0, 0, NiveauInterface.LARGEUR_DECOR,
				NiveauInterface.HAUTEUR_ECRAN + 10);
		Manipulation.ajouterImage(root, PATH_SCORE, NiveauInterface.LARGEUR_DECOR - 15, 0,
				(NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR) + 25,
				NiveauInterface.HAUTEUR_ECRAN + 10);

		Manipulation.ajouterJeu(root, joueur.getImg());

		Manipulation.updateScore(this);

		mediaPlayer.stop();

		gardien = new BOSS4(200, 200, 100, 100, "BOSS", NiveauInterface.VIE_BOSS, PATH_BOSS,
				new BarreDeVie(10, 40, 560, 10, "vie", this), this);

		vsBOSS = true; // On est contre le boss
		// Affichage du texte du nom du boss
		Manipulation.ajouterTexte(this, 40, 80, NOM_BOSS, "Polices/BOSS.ttf", 30, "WHITE");

		mediaPlayer.play(); // La musique du boss se joue
	}

	/**
	 * Getter joueur
	 * 
	 * @author Ephraim
	 */
	@Override
	public Joueur getJoueur() {
		return (Joueur) joueur;
	}

	/**
	 * Retourne le nombre de vies du joueur
	 * 
	 * @author Ephraim
	 */
	public Integer getVies() {
		return Main.getVies();
	}

	/**
	 * Getter si le joueur est contre le boss ou non
	 * 
	 * @author Ephraim
	 */
	public boolean vsBOSS() {
		return vsBOSS;
	}

	/**
	 * Fait perdre une vie au joueur
	 * 
	 * @author Ephraim
	 */
	@Override
	public void perdVie() {

		Main.vieDown();
	}

	/**
	 * @brief L'équivalent du MAIN
	 * 
	 * @param [JEU]:
	 *            Tout simplement le JEU en lui-mÃªme
	 */
	public void start(Stage niveau4) throws Exception {

		Scene sceneNiveau4 = new Scene(createNiveau(niveau4)); // Création du niveau

		niveau4.setScene(sceneNiveau4); // La scÃ¨ne du niveau devient la scÃ¨ne principale
		niveau4.setTitle("NIVEAU IV"); // Nom de la fenÃªtre
		niveau4.setResizable(false); // On ne peut pas redimensionner la fenÃªtre

		// On remet l'icÃ´ne du jeu comme elle a changée car c'est une autre fÃªnetre
		niveau4.getIcons().add(new Image(PATH_ICONE));

		Manipulation.updateScore(this); // Met Ã  jour le score pour le début du niveau
		niveau4.show(); // Affichage du niveau
		if (Main.son()) {
			mediaPlayer.play(); // Musique
			mediaPlayer.setCycleCount(2);
		}

		/*
		 * On gÃ¨re les boutons tapés (ContrÃ´les du jeu) [Quand on APPUIE sur la touche]
		 */
		sceneNiveau4.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (!isEnDialogue()) {
					switch (event.getCode()) { // Récupération de quelle touche appuyée
					case UP:
						setBougeHaut(true);
						break;
					case DOWN:
						setBougeBas(true);
						break;
					case LEFT:
						setBougeGauche(true);
						break;
					case RIGHT:
						setBougeDroite(true);
						break;
					case SPACE:
						setTiré(false);
						break;
					case SHIFT:
						setFocus(true);
						break;
					default:
						break;
					}
				}
				if (isPeutQuitter()) {
					switch (event.getCode()) {
					case SPACE:
						niveau4.close();
						mediaPlayer.stop();
						
						// Niveau V débloqué
						try {
							Main.sauvegarde(5);
						} catch (IOException e) {
							System.err.println("Erreur lors de la sauvegarde de l'état 5!");
						}
						
						Alert alerte = new Alert(AlertType.INFORMATION);
						alerte.setTitle("NIVEAU IV TERMINE");
						alerte.setHeaderText("Vous avez réussi le niveau IV ! Niveau V débloqué !");
						alerte.setContentText("Sauvegarde réussie. Veuillez redémarrer le jeu.");
						alerte.showAndWait();
						System.exit(0);
						break;

					default:
						break;
					}
				}
			}
		});

		// [Quand on RELACHE la touche]
		sceneNiveau4.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if (!isEnDialogue()) {
					switch (event.getCode()) { // Récupération de quelle touche appuyée
					case UP:
						setBougeHaut(false);
						break;
					case DOWN:
						setBougeBas(false);
						break;
					case LEFT:
						setBougeGauche(false);
						break;
					case RIGHT:
						setBougeDroite(false);
						break;
					case SPACE:
						setTiré(false);
						break;
					case SHIFT:
						setFocus(false);
						Joueur j = joueur;
						Joueur g = (Joueur) grazeBox;
						j.finFocus();
						g.finFocus();
						break;
					default:
						break;
					}
				} else {
					switch (event.getCode()) {
					case SPACE:
						try {
							setNbEspaceAppuyés(getNbEspaceAppuyés() + 1);
							dialogue();
						} catch (FileNotFoundException e) {

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
	public void dialogue() throws FileNotFoundException {
		if (debutNiveau) {
			super.dialogue();

			if (Manipulation.siTpsA(getTempsTotal(), APPARITION_DIALOGUE, false)) {
				Manipulation.ajouterImage(getRoot(), "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); // Salar
				Manipulation.ajouterImage(getRoot(), PATH_BOSS, NiveauInterface.LARGEUR_DECOR - 420, 200, 300, 400);

				Manipulation.effacerDialogue(this); // Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE,
						NOM_BOSS, POLICE_DIALOGUE, 20, "BLACK"); // Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE,
						"Jamais tu sortiras d'ici vivant Salar !!", POLICE_DIALOGUE, 15, "BLACK"); // Ce qu'il dit
			}

			if (Manipulation.siTpsA(getTempsTotal(), APPARITION_DIALOGUE_1, false)) {
				Manipulation.effacerDialogue(this); // "Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE,
						"Salar", POLICE_DIALOGUE, 20, "BLACK"); // Qui parle ?

				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE,
						"N'en soit pas si sur, meme sans pouvoirs, \nje peux toujours te mettre une raclée !",
						POLICE_DIALOGUE, 15, "BLACK"); // Ce qu'il dit
			}

			if (Manipulation.siTpsA(getTempsTotal(), APPARITION_DIALOGUE_2, false)) {
				Manipulation.effacerDialogue(this);
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE,
						NOM_BOSS, POLICE_DIALOGUE, 20, "BLACK"); // Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE,
						"C'est ce qu'on va voir. GARDES A L'ATTAQUE", POLICE_DIALOGUE, 15, "BLACK"); // Ce qu'il dit
			}

			if (Manipulation.siTpsA(getTempsTotal(), FIN_DIALOGUE, false)) {
				Manipulation.ajouterImage(root, PATH_BACKGROUND, -15, 0, NiveauInterface.LARGEUR_DECOR,
						NiveauInterface.HAUTEUR_ECRAN + 10); // Remet l'image de fond pour écraser
				Manipulation.supprimer(this, joueur.getImg());
				Manipulation.ajouterJeu(root, joueur.getImg());
				setEnDialogue(false);
			}
		}
	}

	@Override
	public void texteNiveau() throws FileNotFoundException {
		int x = -10;
		int y = NiveauInterface.HAUTEUR_ECRAN / 2;
		int tailleFond = 30;
		String couleurDecor = "GREY";
		String texte = "NIVEAU IV - OPERATION EVASION";
		Texte t = new Texte(texte, "niv4");
		t.setX(x);
		t.setY(y);
		t.setFont(Font.loadFont(new FileInputStream(POLICE_NIVEAU), tailleFond));
		t.setFill(Paint.valueOf(couleurDecor)); // Gris pour que ce soit plus adapté au thÃ¨me carceral
		t.setOpacity(0);
		Manipulation.ajouterJeu(getRoot(), t);
	}

	@Override
	public void bossTir() throws FileNotFoundException {
		if (Math.random() < 0.8) {
			new BossBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR - 20) - 10), -10, PATH_ENNEMI_BALLE,
					"boss balle descente", this);
			gardien.tir("balle rebondissante");
		}
	}

	@Override
	public void niveau() throws FileNotFoundException {
		if (Manipulation.siTpsA(getTempsTotal(), APPARITION_DIALOGUE, true))
			debutNiveau = true;

		if (getTempsTotal() > APPARITION_DIALOGUE && getTempsTotal() < APPARITION_NOM_NIVEAU)
			dialogue();

		if (Manipulation.siTpsA(getTempsTotal(), APPARITION_NOM_NIVEAU, true)) {
			texteNiveau();
		}

		if (getTempsTotal() > APPARITION_NOM_NIVEAU && getTempsTotal() < FIN_NOM_NIVEAU) {
			Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("niv4")).forEach(texte -> {

				if (getTempsTotal() > APPARITION_NOM_NIVEAU && getTempsTotal() < NOM_NIVEAU_EN_APPARITION)
					texte.setOpacity(getTempsTotal() / 2);
				else if (getTempsTotal() > NOM_NIVEAU_EN_DISPARITION && getTempsTotal() < NOM_NIVEAU_EN_DISPARITION2)
					texte.setOpacity(4 / getTempsTotal() * 1.25);
				else if (getTempsTotal() > APPARITION_NOM_NIVEAU && getTempsTotal() < FIN_NOM_NIVEAU)
					texte.setOpacity(4 / getTempsTotal() * 3.5);

				texte.updatePos(this, texte.getTranslateX() + 0.25, NiveauInterface.HAUTEUR_ECRAN / 4);
			});
		}

		if (Manipulation.siTpsA(getTempsTotal(), FIN_NOM_NIVEAU, false)) {
			Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("niv4")).forEach(texte -> {
				Manipulation.supprimer(this, texte);
			});
		}

		if ((getTempsTotal() > HORDE1) && (getTempsTotal() < FIN_HORDE1)) {
			premiereHorde();
		}

		if ((getTempsTotal() > PLUIE_BALLES1) && (getTempsTotal() < FIN_PLUIE_BALLES1)) {
			balles();
		}

		if ((getTempsTotal() > SECONDE_HORDE) && (getTempsTotal() < FIN_HORDE2)) {
			secondeHorde();
		}

		if ((getTempsTotal() > PLUIE_BALLES2) && (getTempsTotal() < FIN_PLUIE_BALLES2)) {
			balles();
		}

		if ((getTempsTotal() > HORDE3) && (getTempsTotal() < FIN_HORDE3)) {
			troisiemeHorde();
		}

		if ((getTempsTotal() > PLUIE_BALLE_3) && (getTempsTotal() < FIN_PLUIE_BALLES3)) {
			balles();
		}

		if (Manipulation.siTpsA(getTempsTotal(), ARRIVEE_BOSS, false)) {
			boss();
		}

	}

	private void balles() throws FileNotFoundException {

		if (Math.random() > 0.95) {
			new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR - 20) - 10), -10, 20, 20, PATH_ENNEMI_BALLE,
					"balle", this);
		}
	}

	@Override
	public void miniBOSSTir(BOSS b) throws FileNotFoundException {
		throw new UnsupportedOperationException("Ce niveau ne comporte pas de miniboss.");
	}

	@Override
	public void fin() {
		super.fin();

		int difficulteNiveau = 0;

		switch (Main.getDifficulté()) {
		case FACILE:
			difficulteNiveau = 2;
			break;

		case NORMAL:
			difficulteNiveau = 4;
			break;

		case DIFFICILE:
			difficulteNiveau = 6;
			break;

		case FRAGILE_COMME_DU_VERRE:
			difficulteNiveau = 20;
			break;
		default:
			break;
		}

		double scoreFinal = (getScore() * (double) Main.getVies() / 2)
				+ 1 * getJoueur().getPuissance() * difficulteNiveau;

		String rang = calculRang(scoreFinal);

		if (Manipulation.siTpsA((getTempsTotal() - getTpsDébScore()), 2, false)) {
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_SCORE,
					"SCORE -- " + getScore(), POLICE_SCORE, 40, "YELLOW");
		} else if (Manipulation.siTpsA((getTempsTotal() - getTpsDébScore()), 4, false)) {
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_VIES,
					"VIES RESTANTES -- " + Main.getVies(), POLICE_SCORE, 40, "YELLOW");
		} else if (Manipulation.siTpsA((getTempsTotal() - getTpsDébScore()), 6, false)) {
			if (Main.getDifficulté() == Difficulté.FRAGILE_COMME_DU_VERRE)
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS,
						NiveauInterface.POSITION_Y_STATS_DIFFICULTE, "DIFFICULTE -- MAX", POLICE_SCORE, 40, "RED");
			else
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS,
						NiveauInterface.POSITION_Y_STATS_DIFFICULTE, "DIFFICULTE -- " + Main.getDifficulté().toString(),
						POLICE_SCORE, 40, "YELLOW");
		} else if (Manipulation.siTpsA((getTempsTotal() - getTpsDébScore()), 8, false)) {
			if (joueur.getPuissance() < NiveauInterface.PUISSANCE_MAX)
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS,
						NiveauInterface.POSITION_Y_STATS_PUISSANCE, "PUISSANCE -- " + joueur.getPuissance(),
						POLICE_SCORE, 40, "YELLOW");
			else
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS,
						NiveauInterface.POSITION_Y_STATS_PUISSANCE, "PUISSANCE -- MAX", POLICE_SCORE, 40, "RED");
		} else if (Manipulation.siTpsA((getTempsTotal() - getTpsDébScore()), 12, false)) {
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS,
					NiveauInterface.POSITION_Y_STATS_SCORE_FINAL, "SCORE FINAL -- " + scoreFinal, POLICE_SCORE, 40,
					"RED");
		} else if (Manipulation.siTpsA((getTempsTotal() - getTpsDébScore()), 13, false)) {
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_RANG,
					"RANG -- " + rang, POLICE_SCORE, 60, "PURPLE");
		} else if (Manipulation.siTpsA((getTempsTotal() - getTpsDébScore()), 16, false)) {
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS + 10,
					NiveauInterface.POSITION_Y_STATS_QUITTER, "APPUYEZ SUR ESPACE POUR QUITTER", POLICE_SCORE, 30,
					"ORANGE");
			setPeutQuitter(true);
		}
	}

	private String calculRang(double scoreFinal) {
		String rang;
		if (scoreFinal > RANG_SALAR)
			rang = "SALAR";
		else if (scoreFinal > RANG_S)
			rang = "S";
		else if (scoreFinal > RANG_A)
			rang = "A";
		else if (scoreFinal > RANG_B)
			rang = "B";
		else
			rang = "C";
		return rang;
	}

	private void premiereHorde() throws FileNotFoundException {
		if (Manipulation.deuxFoisParSecondes(getTemps())) {
			new Ennemi(-10, 30, 40, 40, PATH_GARDIEN_PRISON, "ennemi", this);
		}
	}

	private void secondeHorde() throws FileNotFoundException {
		if (Manipulation.huitFoisParSecondes(getTemps())) {
			new Ennemi(20, 25, 40, 40, PATH_GARDIEN_PRISON, "ennemi", this);
		}
	}

	private void troisiemeHorde() throws FileNotFoundException {
		if (Manipulation.huitFoisParSecondes(getTemps())) {
			new Ennemi(35, 45, 40, 40, PATH_GARDIEN_PRISON, "ennemi", this);
		}
	}


}