package niveau;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import appli.Difficulté;
import appli.Main;
import balles.BossBalle;
import balles.EnnemiBalle;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import manipulation.Manipulation;
import personnage.BOSS;
import personnage.BOSS5;
import personnage.BarreDeVie;
import personnage.Ennemi;
import personnage.Joueur;
import personnage.MINIBOSS5;
import personnage.Personnage;
import texte.Texte;

public class Niveau5 extends Niveau {
	//===================== TIMING DU NIVEAU EN SECONDES ===================\\
	private static final double CHANGEMENT_FOND_NIVEAU = 2.3; //Le fond change au début
	private static final double APPARITION_NOM_NIVEAU = 2.7; //Le nom du niveau s'affiche
	private static final int NOM_NIVEAU_EN_APPARITION = 4; //Augmentation de son opacité
	private static final int NOM_NIVEAU_EN_DISPARITION = 6; //Descente de son opacité
	private static final int NOM_NIVEAU_EN_DISPARITION2 = 9; //Pareil mais plus vite
	private static final int FIN_NOM_NIVEAU = 11; //Il disparaît
	private static final double APPARITION_1ERS_ENNEMIS = 15; //Premiers ennemis
	private static final double DISPARITION_1ERS_ENNEMIS = 25; //Ils disparaissent
	private static final double APPARITION_2EMES_ENNEMIS = 26; //Deuxièmes ennemis
	private static final double DISPARITION_2EMES_ENNEMIS = 36; //Ils disparaissent
	private static final double APPARITION_MINIBOSS = 37.8; //Il apparaît
	private static final double MINIBOSS_ATTAQUE = 39; //Il commence a attaquer
	private static final double MINIBOSS_PREPARATION = 50.2; //Pause, et deuxième attaque
	private static final double MINIBOSS_2EME_ATTAQUE = 52; //Il devient sérieux
	private static final double MINIBOSS_PART = 62; //Il part
	private static final double MINIBOSS_PLUS_VISIBLE = 66; //Miniboss parti
	private static final double APPARITION_ENNEMIS_APRES_MINIBOSS = 65.5; //Les ennemis apparaissent
	private static final double APPARITION_ENNEMIS2_APRES_MINIBOSS = 76; //Ceux du début viennent en +
	private static final double BALLES_ALEATOIRES_PARTOUT = 88.5; //Des balles sortent de partout aléatoirement
	private static final double APPARITION_ENNEMIS_APRES_BALLES = 101.5; //Des ennemis après
	private static final double APPARITION_MINIBOSS2 = 114.5; //Le miniboss revient !
	private static final double MINIBOSS2_ATTAQUE = 118.5; //Il réattaque
	private static final double MINIBOSS2_PREPARATION = 129; //Pause, et après deuxième attaque
	private static final double MINIBOSS2_2EME_ATTAQUE = 131; //La 2ème attaque
	private static final double MINIBOSS2_PART = 142;
	private static final double BALLES_ALEATOIRES = 144; //Miniboss parti, des balles aléatoires sortant du haut
	private static final double BALLES_VISEUSES = 155; //Balles qui "visent" le joueur
	private static final double BALLES_VISEUSES_ET_ALEATOIRES = 168; //En plus des balles qui visent, les balles aléatoires sortant du haut reviennent
	private static final double ECLAIRS = 180; //Des éclairs
	private static final double IMAGE_FOND_SOMBRE = 194.5; //L'image du fond (feu) devient + sombre
	private static final double AUTRE_IMAGE_FOND = 207; //L'image du fond change, c'est celle du boss mais sombre
	private static final double APPARITION_VIES = 215; //Les 4 vies qui apparaissent avant l'apparition du BOSS
	public static final double APPARITION_BOSS = 217; //Le boss apparaît, fin du niveau
	private static final double TEMPS_DESCENTE_BOSS = 2;

	//===================== TIMING DU BOSS EN SECONDES ===================\\
	private static final double DIALOGUE_1 = 0.5; //Dialogue...
	private static final double DIALOGUE_2 = 1.5;
	private static final double DIALOGUE_3 = 2.5;
	private static final double DIALOGUE_4 = 4;
	private static final double DIALOGUE_5 = 6;
	private static final double DIALOGUE_6 = 8;
	private static final double DIALOGUE_7 = 10;
	private static final double DEBUT = 11.5; //Commencement
	private static final double FIN_DEBUT = 22.5; //Fin de la première attaque
	private static final double MUR_BALLES = 22.75; //Mur de balles
	private static final double FIN_MUR_BALLES = 32.5; //Fin de l'attaque
	private static final double ECLAIRS_BOSS = 35; //Attaque des éclairs
	private static final double FIN_ECLAIRS_BOSS = 47; //Fin de l'attaque
	private static final double TUNNEL = 47.5; //Attaque du tunnel
	private static final double TUNNEL_BALLES = 58.25; //Attaque du tunnel PLUS des balles
	private static final double FIN_TUNNEL = 71.5; //Fin de l'attaque du tunnel (Et aussi du coup avec les balles ajoutées)
	private static final double BOSS_BALLES_ALEATOIRES = 72; //Balles aléatoires tombant de haut
	private static final double BOSS_ALEATOIRES_PLUS_PARTOUT = 84.25; //Balles aléatoires de tout les côtés
	private static final double FIN_BOSS_ALEATOIRES_PLUS_PARTOUT = 94; //Fin de l'attaque des balles aléatoires
	private static final double BALLE_FONCE = 96; //De haut vers le bas, une balle qui vise le joueur a toute vitesse
	private static final double VISEUSES = 108.25; //Balles de tout les côtés qui visent le joueur
	private static final double MUR_BALLES2 = 121.5; //Mur de balles mais plus rapide
	private static final double BALLES_TOMBENT = 145; //Balles aléatoires sortant du haut mais bougent sur les côtés
	private static final double BALLES_TOMBENT_PARTOUT = 157.5; //Pareil mais du coup des balles aléatoires sortant de partout
	private static final double FIN_BALLES_TOMBENT_PARTOUT = 167.5; //Fin de l'attaque des balles aléatoires de partout (+ bougeant sur les côtés)
	private static final double MUR_BALLES_V = 169; //Mur de balles vertical (Sortant de gauche & droite au lieu de haut)
	private static final double MUR_BALLES_PARTOUT = 181.5;//Mur de balles de partout (Original + Vertical)
	private static final double ECLAIRS_BALLES = 192; //Eclairs + Balles aléatoires de haut qui bougent
	private static final double DERNIER = 207; //Dernière attaque: Mur de balles doucements
	
	private static final String PATH_BALLES_FEU = "Images/Niveau5/feu.png";
	private static final String PATH_MUSIQUE_NIVEAU = "Sons/niveau5/niveau5.mp3";

	private static final int VIE_MINIBOSS = 565;
	private static double DEGAT_BALLE_JOUEUR_VS_BOSS = 0.45;
	private static double DEGAT_BALLE_JOUEUR_VS_MINIBOSS = 1;

	//Il faut combien de points pour avoir le rang ?
	private static final int RANG_SALAR = 176762;
	private static final int RANG_S = 150000;
	private static final int RANG_A = 100000;
	private static final int RANG_B = 50000;

	// ================================== Manipulations pour charger le son  ==================================
	private static Media son = new Media(new File(PATH_MUSIQUE_NIVEAU).toURI().toString());
	private static MediaPlayer mediaPlayer = new MediaPlayer(son);

	// ================================== Objets qui vont permettre l'affichage: Tout ce qu'on ajoute dedans sera affiché ==================================
	private static ImageView imageFond = new ImageView();

	// ================================== Variables qui gèrent les miniboss ==================================
	private static BOSS miniBOSS = null; //Le mini-boss
	private static boolean miniBossVersDroite = false; //Va-t-il vers la droite ?

	// ================================== Variables qui gèrent le boss ==================================
	private static BOSS5 boss = null; //Le BOSS
	private static boolean vsBOSS = false; //Actuellement contre un boss ?
	private static boolean bossBattu = false; //BOSS battu ?
	private static boolean bossCommencé = false; //Le combat a commencé ?
	private static boolean vsMINIBOSS = false; //Actuellement contre la 2ème phase du miniboss ? (1ère inutile)
	private boolean bossVersDroite = false; //Le boss bouge-t-il vers la droite ? (Pour gérer son mouvement)
	private static String nomBOSS = "GENESIS";
	private double tpsDébutBoss = 0; //A quel moment le combat contre le boss commence ?

	// ================================== Variables qui gèrent l'attaque des éclairs ==================================
	private int tiers_écran = 200; //Le tiers d'un écran [L'écran du jeu fait 600px]
	private Rectangle avertissement = new Rectangle(tiers_écran,NiveauInterface.HAUTEUR_ECRAN+10); //La zone rouge qui averti l'emplacement de l'éclair
	private int endroit; //L'endroit aléatoire où va apparaître l'éclair

	public void update() throws FileNotFoundException {
		//Le getTemps() d'une frame en JavaFX (On le met ici pour pouvoir manipuler les objets selon le getTemps())
		setTemps(getTemps() + NiveauInterface.TEMPS_1_FRAME);
		setTempsTotal(getTempsTotal() + NiveauInterface.TEMPS_1_FRAME);

		//Si ça fait 100 millisecondes que le joueur a tiré
		if(getTempsTotal() - getTpsDébTir() > NiveauInterface.COOLDOWN_TIR) setCooldownTir(false);

		//Pour mieux calculer le getTemps()
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
		Manipulation.sprites(root).forEach(s -> {   
			switch(s.getType()) {
			case "joueur":
				if(!Niveau.isDebug()) { //Si pas en mode debug, le joueur n'est pas invincible
					//Si collision avec un ennemi
					Manipulation.sprites(root).stream().filter(e -> e.getType().contains("ennemi") || e.getType().contains("boss") || e.getType().contains("balle")).forEach(ennemi -> {
						if (Manipulation.touche(s, ennemi)){
							Manipulation.CollisionJoueur(this, s, false);
						}

						if(Manipulation.touche(grazeBox, ennemi)) {
							if(!isCooldownTouché()) Manipulation.grazeUp(this);
						}
					});
				}

				//Si collision bonus
				Manipulation.sprites(root).stream().filter(e -> e.getType().contains("bonus")).forEach(bonus -> {
					if (Manipulation.touche(s, bonus)){
						Bonus b = (Bonus)bonus;
						b.pris();
					}
				});

				break;

			case "bonus": //Bonus
				s.bougerBas(2); //Descend vers le bas
				break;

			case "ennemi1g":
				s.bougerDroite(0.5);

				//Ceux du début (Car après le miniboss ils reviennent mais n'attaque pas dans le côtés, seulement tout droit mais reviennent en même temps ds balles aléatoires)
				if(getTempsTotal() < APPARITION_MINIBOSS || getTempsTotal() > APPARITION_ENNEMIS_APRES_BALLES)
					tirSpirale((Ennemi)s);
				else {
					//Le fait que ce soit miniboss ou pas ne change rien, c'est juste la trajectoire de la balle qui nous importe
					if(Math.random() > 0.99)
						s.tir("balle aléatoire bas");
				}
				break;

			case "ennemi1d":
				s.bougerGauche(0.5);

				//Ceux du début (Car après le miniboss ils reviennent mais n'attaque pas dans le côtés, seulement tout droit mais reviennent en même temps ds balles aléatoires)
				if(getTempsTotal() < APPARITION_MINIBOSS || getTempsTotal() > APPARITION_ENNEMIS_APRES_BALLES)
					tirSpirale((Ennemi)s);
				else {
					//Le fait que ce soit miniboss ou pas ne change rien, c'est juste la trajectoire de la balle qui nous importe
					if(Math.random() > 0.99)
						s.tir("balle aléatoire bas");
				}
				break;

			case "ennemi2":
				s.bougerBas(0.5);
				tirSpirale((Ennemi)s);
				break;

			case "ennemi3g":
				s.bougerBas(1);

				if(Math.random() > 0.99)
					s.tir("ennemi3g balle");
				break;

			case "ennemi3d":
				s.bougerBas(1);
				if(Math.random() > 0.99)
					s.tir("ennemi3d balle");
				break;


			case "miniboss":
				if(vsMINIBOSS) {
					try {
						//Si pas attaque tunnel il va attaquer pas à la même vitesse que l'attaque du tunnel
						if(getTempsTotal() < MINIBOSS_2EME_ATTAQUE) miniBOSSTir((BOSS)s); 
					} catch (FileNotFoundException e1) {
						System.err.println("Sprite balle");
					}

					//Replace le miniboss au milieu du décor [Après l'attaque des murs (PHASE I) ET Après l'attaque du tunnel (PHASE II)]
					if((Manipulation.siTpsA(getTempsTotal(), MINIBOSS_PREPARATION, true)) || (Manipulation.siTpsA(getTempsTotal(), MINIBOSS2_PREPARATION, true))) {
						miniBOSS.setTranslateX((NiveauInterface.LARGEUR_DECOR/2)-70);
						miniBOSS.updateImagePos();
					}

					//Attaque tunnel (Phase I ou Phase II)
					if((getTempsTotal() > MINIBOSS_2EME_ATTAQUE && getTempsTotal() < MINIBOSS_PLUS_VISIBLE) || getTempsTotal() > MINIBOSS2_ATTAQUE && getTempsTotal() < MINIBOSS2_PREPARATION) {
						//Si contre un mur
						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120) 
							miniBossVersDroite = !miniBossVersDroite; 

						if(miniBossVersDroite)
							s.bougerDroite(0.125);
						else
							s.bougerGauche(0.125);

						//Aléatoire toute les 0.125 secondes
						if(Manipulation.huitFoisParSecondes(getTemps())){
							if(Math.random() >= 0.5)
								miniBossVersDroite = false;
							else
								miniBossVersDroite = true;

							//Attaque tunnel, 8 fois/s
							try {
								miniBOSSTir((BOSS)s);
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					}

					//Attaque spirale (PHASE II), dernière attaque du miniboss
					if((getTempsTotal() > MINIBOSS2_2EME_ATTAQUE && getTempsTotal() < MINIBOSS2_PART+3)) {
						//Si contre un mur
						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120) 
							miniBossVersDroite = !miniBossVersDroite; 

						if(miniBossVersDroite)
							s.bougerDroite(0.125);
						else
							s.bougerGauche(0.125);

						try {
							miniBOSSTir((BOSS)s);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					//Il s'enfuit
					if((getTempsTotal() > MINIBOSS_PART && getTempsTotal() < BALLES_ALEATOIRES_PARTOUT) || (getTempsTotal() > MINIBOSS2_PART && getTempsTotal() < MINIBOSS2_PART +3)) {
						s.bougerHaut(0.2);
					}
				}
				break;


				//N: Nord, O: ouest...
			case "balle N":
				//Si c'est pas le boss OU le boss mais n'attaque pas en spirale: Les balles sont plus lentes (Car le boss aussi fait cette attaque)
				if(getTempsTotal() < APPARITION_2EMES_ENNEMIS)
					s.bougerHaut(1); 
				else if (vsMINIBOSS)
					s.bougerHaut(0.125);
				else if(vsBOSS)
					s.bougerHaut(0.5);
				else
					//Si c'est les ennemis avant le miniboss 2
					s.bougerHaut(3);
				break;


			case "balle NO":
				if(getTempsTotal() < APPARITION_2EMES_ENNEMIS) {
					s.bougerHaut(1);
					s.bougerDroite(1);
				}else if (vsMINIBOSS) {
					s.bougerHaut(0.125);
					s.bougerDroite(0.125);
				}else if (vsBOSS){ 
					s.bougerHaut(0.5);
					s.bougerDroite(0.5);
				}else {//Si c'est les ennemis avant le miniboss 2
					s.bougerHaut(3);
					s.bougerDroite(3);
				}

				break;

			case "balle O":
				if(getTempsTotal() < APPARITION_2EMES_ENNEMIS)
					s.bougerDroite(1);
				else if (vsMINIBOSS)
					s.bougerDroite(0.125);
				else if(vsBOSS)
					s.bougerDroite(0.5);
				else//Si c'est les ennemis avant le miniboss 2
					s.bougerDroite(3);
				break;

			case "balle OS":
				if(getTempsTotal() < APPARITION_2EMES_ENNEMIS) {
					s.bougerDroite(1);
					s.bougerBas(1);
				}else if (vsMINIBOSS) {
					s.bougerDroite(0.125);
					s.bougerBas(0.125);
				}else if (vsBOSS) {
					s.bougerDroite(0.5);
					s.bougerBas(0.5);
				}else { //Si c'est les ennemis avant le miniboss 2
					s.bougerDroite(3);
					s.bougerBas(3);
				}
				break;

			case "balle S":
				if(getTempsTotal() < APPARITION_2EMES_ENNEMIS) 
					s.bougerBas(1);
				else if (vsMINIBOSS)
					s.bougerBas(0.125);
				else if(vsBOSS)
					s.bougerBas(0.5);
				else//Si c'est les ennemis avant le miniboss 2
					s.bougerBas(3);
				break;

			case "balle SE":
				if(getTempsTotal() < APPARITION_2EMES_ENNEMIS) {
					s.bougerBas(1);
					s.bougerGauche(1);
				}else if (vsMINIBOSS) {
					s.bougerBas(0.125);
					s.bougerGauche(0.125);
				}else if (vsBOSS) {
					s.bougerBas(0.5);
					s.bougerGauche(0.5);
				}else { //Si c'est les ennemis avant le miniboss 2
					s.bougerBas(3);
					s.bougerGauche(3);
				}
				break;

			case "balle E":
				if(getTempsTotal() < APPARITION_2EMES_ENNEMIS)
					s.bougerGauche(1);
				else if (vsMINIBOSS)
					s.bougerGauche(0.125);
				else if(vsBOSS)
					s.bougerGauche(0.5);
				else//Si c'est les ennemis avant le miniboss 2
					s.bougerGauche(3);
				break;

			case "balle NE":
				if(getTempsTotal() < APPARITION_2EMES_ENNEMIS) {
					s.bougerHaut(1);
					s.bougerGauche(1);
				}else if (vsMINIBOSS) {
					s.bougerHaut(0.125);
					s.bougerGauche(0.125);
				}else if (vsBOSS) {
					s.bougerHaut(0.5);
					s.bougerGauche(0.5);
				}else { //Si c'est les ennemis avant le miniboss 2
					s.bougerHaut(3);
					s.bougerGauche(3);
				}

				break;

				//Balles des mur de balles du miniboss
			case "miniboss balle descente":
				s.bougerBas(0.125);
				break;

				//Balles des mur de balles du boss
			case "boss balle descente":
				//Pas le dernier mur de balles ni mur de balles w/vertical
				if((getTempsTotal() - tpsDébutBoss) < MUR_BALLES_PARTOUT)
					s.bougerBas(0.1);
				else 
					s.bougerBas(0.25);
				break;

				//Balle des tunnel
			case "balle tunnel":
				s.bougerBas(0.1);
				break;

			case "ennemi3g balle":
				s.bougerDroite(1.5);
				break;

			case "ennemi3d balle":
				s.bougerGauche(1.5);
				break;

			case "balle aléatoire bas": //Balles aléatoire allant en bas
				if(vsBOSS) { //Si c'est contre le BOSS
					s.bougerBas(3);
					//Si c'est le moment où les balles bougent aussi sur les côtés
					if((getTempsTotal() - tpsDébutBoss)>BALLES_TOMBENT) { //2 fois par seconde, 50% de chance que la balle va de l'autre côté		
						if(Manipulation.huitFoisParSecondes(getTemps())) {
							if(Math.random()>0.5)
								s.bougerGauche(3);
							else
								s.bougerDroite(3);
						}
					}
				}
				else {
					s.bougerBas(3.5);
				}
				break;

			case "balle aléatoire haut": //Balles aléatoire allant en haut
				if(vsBOSS) { //Si c'est contre le BOSS
					s.bougerHaut(3);
					//Si c'est le moment où les balles bougent aussi sur les côtés
					if((getTempsTotal() - tpsDébutBoss)>BALLES_TOMBENT) { //2 fois par seconde, 50% de chance que la balle va de l'autre côté
						if(Manipulation.huitFoisParSecondes(getTemps())) {
							if(Math.random()>0.5)
								s.bougerGauche(3);
							else
								s.bougerDroite(3);
						}
					}
				}
				else {
					s.bougerHaut(3.5);
				}
				break;

			case "balle aléatoire gauche": //Balles aléatoire allant à gauche
				if(vsBOSS) { //Si c'est contre le BOSS
					s.bougerGauche(3);
					//Si c'est le moment où les balles bougent aussi sur les côtés
					if((getTempsTotal() - tpsDébutBoss)>BALLES_TOMBENT) { //2 fois par seconde, 50% de chance que la balle va de l'autre côté
						if(Manipulation.huitFoisParSecondes(getTemps())) {
							if(Math.random()>0.5)
								s.bougerBas(3);
							else
								s.bougerHaut(3);
						}
					}
				}
				else {
					s.bougerGauche(3.5);
				}
				break;

			case "balle aléatoire droite": //Balles aléatoire allant à droite
				if(vsBOSS) { //Si c'est contre le BOSS
					s.bougerDroite(3);
					//Si c'est le moment où les balles bougent aussi sur les côtés
					if((getTempsTotal() - tpsDébutBoss)>BALLES_TOMBENT) { //2 fois par seconde, 50% de chance que la balle va de l'autre côté
						if(Manipulation.huitFoisParSecondes(getTemps())) {
							if(Math.random()>0.5)
								s.bougerBas(3);
							else
								s.bougerHaut(3);
						}
					}
				}
				else {
					s.bougerDroite(3.5);
				}
				break;

			case "balle vise gauche": //Balles viseuses allant à gauche
				if(vsBOSS)
					s.bougerGauche(1);
				else
					s.bougerGauche(1);
				break;

			case "balle vise droite": //Balles viseuses allant à droite
				if(vsBOSS)
					s.bougerDroite(1);
				else
					s.bougerDroite(1);
				break;

			case "balle vise haut": //Balles viseuses allant en haut
				if(vsBOSS)
					s.bougerHaut(1);
				else
					s.bougerHaut(1);
				break;

			case "balle vise bas": //Balles viseuses allant en bas
				if(vsBOSS)
					s.bougerBas(1);
				else
					s.bougerBas(1);
				break;

			case "balle éclair": //Eclair
				s.bougerBas(0.01); //Très vite [Flash]
				break;

			case "balle fonce": //Les balles qui visent le joueur du haut vers le bas
				s.bougerBas(0.05); //A une grande vitesse
				break;

			case "balle mur vertical g": //Allant à droite (Part de la Gauche)
				s.bougerDroite(0.2);
				break;

			case "balle mur vertical d": //Allant à gauche (Part de la Droite)
				s.bougerGauche(0.2);
				break;

			case "boss":
				if(vsBOSS) {
					//Il flotte
					if(getTemps() > 0 && getTemps() < 1)
						s.bougerHaut(1);
					else
						s.bougerBas(1);

					//================ DIALOGUE DE DEBUT =================\\
					if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_1, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte précédent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai une question. Tu es un totzusen toi.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_2, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte précédent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Et ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_3, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte précédent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourquoi ? Pourquoi tu nous a trahis ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_4, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte précédent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ce ne sont pas tes affaires.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_5, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte précédent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "En plus tu es l'un des chefs !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_6, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte précédent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "D'UNE ORGANISATION QUI NOUS TUENT!!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_7, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte précédent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je ne suis PLUS un totsuzen.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}


					//================ DEBUT DU BOSS =================\\
					if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DEBUT, false)) {
						//Pour effacer tout ce qui est dialogue
						Manipulation.toutEffacer(this);
						Manipulation.ajouterImage(getRoot(), "Fonds/NIVEAU_V/bg_boss.gif", 0, 0, NiveauInterface.LARGEUR_DECOR, NiveauInterface.LARGEUR_ECRAN);
						Manipulation.ajouterImage(getRoot(),"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

						//On réaffiche les personnages
						Manipulation.ajouterJeu(getRoot(), joueur.getImg());
						Manipulation.ajouterJeu(getRoot(), boss.getImg());

						//On enlève puis remet la barre de vie 
						Manipulation.supprimer(this, boss.getBarreVie());
						Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

						Manipulation.updateScore(this); //On remet le score

						setEnDialogue(false);
						setNbEspaceAppuyés(0); //Pendant le dialogue le joueur pouvait taper "espace" ce qui les comptaient

						//On ne fait pas Manipulation.ajouterTexte car on doit récupérer ce nom pour pas qu'il soit supprimé lors de updateScore()
						Texte niv = new Texte(nomBOSS, "nomboss");
						niv.setX(NiveauInterface.X_NOM_BOSS);
						niv.setY(NiveauInterface.Y_NOM_BOSS);

						try {
							niv.setFont(Font.loadFont(new FileInputStream("Polices/BOSS.ttf"), 30));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} //Charge la police et la change

						niv.setFill(Paint.valueOf("WHITE")); //Couleur
						Manipulation.ajouterJeu(getRoot(), niv);
					}

					//On met bossTir ici car lors de l'attaque du tunnel, le boss n'attaque pas à la même fréquence
					if((getTempsTotal() - tpsDébutBoss)<TUNNEL || (getTempsTotal() - tpsDébutBoss)>FIN_TUNNEL) {
						try {
							bossTir();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					//Pour l'éclair
					if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ECLAIRS_BOSS, false))
						setTemps(0);

					//Fin de l'éclair
					if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TUNNEL, false))
						Manipulation.supprimer(this, avertissement); //Disparition de l'avertissement

					//Le boss va bouger
					if((getTempsTotal() - tpsDébutBoss)>TUNNEL && (getTempsTotal() - tpsDébutBoss)<FIN_TUNNEL) {
						//Si contre un mur
						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120) 
							bossVersDroite = !bossVersDroite; 

						if(bossVersDroite)
							s.bougerDroite(0.1);
						else
							s.bougerGauche(0.1);

						//Aléatoire toute les 0.125 secondes
						if(Manipulation.huitFoisParSecondes(getTemps())){
							if(Math.random() >= 0.5)
								bossVersDroite = false;
							else
								bossVersDroite = true;

							//Tire 8 fois/s
							try {
								bossTir();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}								
					}

					//Le boss arrête de bouger (on le replace) [Pour l'attaque balles aléatoires et deuxième murs de balles]
					if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_TUNNEL, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, MUR_BALLES2, false)) {
						s.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);
						s.updateImagePos(); //Mise à jour de la position de l'image
					}

					//Lorsque le boss tire dans toutes les directions, il va bouger
					if((getTempsTotal() - tpsDébutBoss) > BOSS_ALEATOIRES_PLUS_PARTOUT && (getTempsTotal() - tpsDébutBoss) < MUR_BALLES2) {
						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120) 
							bossVersDroite = !bossVersDroite; 

						if(bossVersDroite)
							s.bougerDroite(0.1);
						else
							s.bougerGauche(0.1);
					}
				}
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
				Manipulation.sprites(root).stream().filter(e -> e.getType().contains("ennemi") && !e.getType().contains("balle")).forEach(ennemi -> {
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

				//Si collision avec le boss
				if(vsBOSS) {
					Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("boss")).forEach(bosss -> {
						if (Manipulation.touche(s, bosss)){
							s.meurs();
							boss.perdreVie(DEGAT_BALLE_JOUEUR_VS_BOSS); //Il perd de la vie !
							Manipulation.scoreUp(this);

							//Mort du boss
							if(boss.getVieBossActuelle() <= 0) {
								bossBattu = true; //Mort
								setEnDialogue(true); //Dialogue de fin
								vsBOSS = false;
								try {
									dialogue();
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					});	
				}

				Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("miniboss")).forEach(bosss -> {
					if (Manipulation.touche(s, bosss)){
						s.meurs();
						miniBOSS.perdreVie(DEGAT_BALLE_JOUEUR_VS_MINIBOSS); //Il perd de la vie !
						Manipulation.scoreUp(this);
					}
				});	
			}
		});

		niveau();

		if(isNiveauTerminé()) fin();
	}

	public void texteNiveau() throws FileNotFoundException {
		//On ne fait pas Manipulation.ajouterTexte car la il a un effet d'opacité
		Texte niv = new Texte("NIVEAU V - FACE A FACE", "niv6");
		niv.setX(-10);
		niv.setY(NiveauInterface.HAUTEUR_ECRAN/2);
		niv.setFont(Font.loadFont(new FileInputStream("Polices/NIVEAU.ttf"), 30)); //Charge la police et la change
		niv.setFill(Paint.valueOf("RED")); //Couleur
		niv.setOpacity(0); //Il va apparaître
		Manipulation.ajouterJeu(root, niv);
	}

	public void niveau() throws FileNotFoundException {
		if(Manipulation.siTpsA(getTempsTotal(), CHANGEMENT_FOND_NIVEAU, true)) 
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5.gif"))); //Changement du fond, fin de l'accélération

		// ============================ LE TEXTE DU DEBUT ============================= \\
		//Affiche le nom du niveau & Le fond change
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_NOM_NIVEAU, true))
			texteNiveau();

		//De l'affichage à la disparition
		if(getTempsTotal() > APPARITION_NOM_NIVEAU && getTempsTotal() < FIN_NOM_NIVEAU) {
			Manipulation.textes(root).stream().filter(e -> e.getID().equals("niv6")).forEach(texte -> {

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
			Manipulation.textes(root).stream().filter(e -> e.getID().equals("niv6")).forEach(texte -> {
				Manipulation.supprimer(this, texte);
			});
		}

		// ============================ PREMIERS ENNEMIS ============================= \\
		if(getTempsTotal() > APPARITION_1ERS_ENNEMIS && getTempsTotal() < DISPARITION_1ERS_ENNEMIS) {
			premiersEnnemis();
		}

		// ============================ DEUXIEMES ENNEMIS ============================= \\
		if(getTempsTotal() > APPARITION_2EMES_ENNEMIS && getTempsTotal() < DISPARITION_2EMES_ENNEMIS) {
			if(Manipulation.siTpsA(getTempsTotal(), APPARITION_2EMES_ENNEMIS, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_2.gif"))); //Changement du fond, fin de l'accélération

			if(Manipulation.siTpsA(getTempsTotal(), APPARITION_2EMES_ENNEMIS+6, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_3.gif"))); //Changement du fond, fin de l'accélération

			if(Manipulation.siTpsA(getTempsTotal(), APPARITION_2EMES_ENNEMIS+9, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_4.gif"))); //Changement du fond, fin de l'accélération

			deuxiemesEnnemis();
		}

		// ============================ MINIBOSS (PHASE I) ============================= \\
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_MINIBOSS, false)) 
			//Le mini-boss apparaît
			miniBOSS = new MINIBOSS5((NiveauInterface.LARGEUR_DECOR/2)-70, -100, 100, 200, "miniboss", VIE_MINIBOSS, "Images/Niveau5/boss.png", new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this), this);
		
		//Il descend
		if(getTempsTotal() > APPARITION_MINIBOSS && getTempsTotal() < MINIBOSS_ATTAQUE)
			miniBOSS.bougerBas(0.25);

		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS_ATTAQUE, false)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_debut.gif"))); //Changement du fond, accélération au MAX
			vsMINIBOSS = true;
		}

		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS_PREPARATION, false))
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5.gif"))); //Fond normal

		if(Manipulation.siTpsA(getTempsTotal(),  MINIBOSS_2EME_ATTAQUE, false)) 
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_debut.gif"))); //Puis ça repart

		// ============================ FIN MINIBOSS (PHASE I) / APPARITION ENNEMIS ============================= \\
		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS_PART, false)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_3.gif"))); //Changement du fond, fin de l'accélération, c'est vitesse normale
			//Il lâche une vie
			new BonusVie(miniBOSS.getTranslateX()+10, miniBOSS.getTranslateY() + 7, 25, 25, this);
		}

		if(getTempsTotal() > APPARITION_ENNEMIS_APRES_MINIBOSS && getTempsTotal() < BALLES_ALEATOIRES_PARTOUT-2) {
			troisiemesEnnemis();

			if(getTempsTotal() > APPARITION_ENNEMIS2_APRES_MINIBOSS) { //+ Les premiers
				premiersEnnemis();
				vsMINIBOSS = false; //Il sera déjà parti
			}
		}

		// ============================ BALLES ALEATOIRES DE TOUS LES COTES ============================= \\
		if(getTempsTotal() > BALLES_ALEATOIRES_PARTOUT && getTempsTotal() < APPARITION_ENNEMIS_APRES_BALLES-1)
			ballesAléatoiresPartout();

		// ============================ MEMES ENNEMIS QUE LE DEBUT MAIS TIRENT PLUS DOUCEMENT MAIS + DE BALLES ============================= \\
		if(getTempsTotal() > APPARITION_ENNEMIS_APRES_BALLES && getTempsTotal() < APPARITION_MINIBOSS2 - 2) {
			vsMINIBOSS = false; //La phase I sera déjà terminée
			premiersEnnemis();
		}

		// ============================ MINIBOSS (PHASE II) ============================= \\
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_MINIBOSS2, true))
			//Le mini-boss réapparaît (Il était mort, on le recrée)
			miniBOSS = new MINIBOSS5((NiveauInterface.LARGEUR_DECOR/2)-70, -10, 100, 100, "miniboss", VIE_MINIBOSS, "Images/Niveau5/boss.png", new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this), this);

		if(getTempsTotal() > APPARITION_MINIBOSS2 && getTempsTotal() < MINIBOSS2_ATTAQUE)
			miniBOSS.bougerBas(0.5);

		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS2_ATTAQUE, true)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_debut.gif"))); //Changement du fond, accélération au MAX
			vsMINIBOSS = true;
		}

		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS2_PREPARATION, true))
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5.gif"))); //Fond normal

		if(Manipulation.siTpsA(getTempsTotal(),  MINIBOSS2_2EME_ATTAQUE, true)) 
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_debut.gif"))); //Puis ça repart

		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS2_PART, true)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5.gif"))); //Le fond redevient lent
			//Il lâche une vie
			new BonusVie(miniBOSS.getTranslateX()+10, miniBOSS.getTranslateY() + 7, 25, 25, this);
		}

		// ============================ BALLES ALEATOIRES DU HAUT VERS LE BAS ============================= \\
		if(getTempsTotal() > BALLES_ALEATOIRES && getTempsTotal() < BALLES_VISEUSES - 3)
			ballesAléatoiresHaut();

		// ============================ BALLES VISEUSES ============================= \\
		if(getTempsTotal() > BALLES_VISEUSES && getTempsTotal() < ECLAIRS-2) {
			ballesViseuses();

			//Elles se rajoutent
			if(getTempsTotal() > BALLES_VISEUSES_ET_ALEATOIRES)
				ballesAléatoiresHaut();
		}

		// ============================ ECLAIRS ============================= \\
		if(Manipulation.siTpsA(getTempsTotal(), ECLAIRS, true))
			setTemps(0); //Pour bien commencer les éclairs (Ils sont en fonction du temps)

		if(getTempsTotal() > ECLAIRS && getTempsTotal() < APPARITION_VIES)
			éclairs();

		//Le fond devient sombre, on approche du boss
		if(Manipulation.siTpsA(getTempsTotal(), IMAGE_FOND_SOMBRE, false))
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_sombre.gif")));

		//Puis les balles aléatoires sortant du haut s'ajoutent
		if(getTempsTotal() > IMAGE_FOND_SOMBRE && getTempsTotal() <  AUTRE_IMAGE_FOND-1.5)
			ballesAléatoiresHaut();

		//On met le fond du boss mais sombre, on est très bientôt contre lui
		if(Manipulation.siTpsA(getTempsTotal(), AUTRE_IMAGE_FOND, false))
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_avant_boss.gif")));

		//Les balles aléatoires partent et les balles viseuses apparaissent
		if(getTempsTotal() > AUTRE_IMAGE_FOND && getTempsTotal() < APPARITION_BOSS-1)
			ballesViseuses();

		// ============================ BOSS ============================= \\
		//Les quatre vies tombent
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_VIES, false)) {
			//   **
			// *    *
			// *    * 8 vies tombent comme ça
			//   **
			new BonusVie(NiveauInterface.LARGEUR_DECOR/2-10, -10, 25, 25, this);
			new BonusVie(NiveauInterface.LARGEUR_DECOR/2-30, -10, 25, 25, this);
			new BonusVie(NiveauInterface.LARGEUR_DECOR/2 - 50, -30, 25, 25, this);
			new BonusVie(NiveauInterface.LARGEUR_DECOR/2+10, -30, 25, 25, this);
			new BonusVie(NiveauInterface.LARGEUR_DECOR/2 - 50, -50, 25, 25, this);
			new BonusVie(NiveauInterface.LARGEUR_DECOR/2+10, -50, 25, 25, this);
			new BonusVie(NiveauInterface.LARGEUR_DECOR/2-10, -70, 25, 25, this);
			new BonusVie(NiveauInterface.LARGEUR_DECOR/2-30, -70, 25, 25, this);
		}

		//On met le fond du boss 
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_BOSS, false)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_boss.gif")));
			boss = new BOSS5((NiveauInterface.LARGEUR_DECOR)/2-75, -100, 100, 200, "boss", VIE_MINIBOSS, "Images/Niveau5/boss.png", new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this) ,this);
		}

		if(getTempsTotal() > APPARITION_BOSS && getTempsTotal() < APPARITION_BOSS + TEMPS_DESCENTE_BOSS) {
			boss.bougerBas(0.25);
		}

		//Le dialogue commence
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_BOSS + TEMPS_DESCENTE_BOSS, false)) {
			setEnDialogue(true); //Il est en dialogue, il ne joue pas
			dialogue(); //DIALOGUE AVANT LE BOSS
		}
	}

	/**
	 * Les premiers ennemis (Deux lignées une allant à gauche une autre à droite)
	 * 
	 * @author Jordan
	 */
	private void premiersEnnemis() {

		if(Manipulation.quatreFoisParSecondes(getTemps())) {
			//Lignée vers droite
			new Ennemi(-10, 30, 40, 40, "Images/Niveau5/ennemi.png", "ennemi1g", this);
			//Lignée vers gauche
			new Ennemi(NiveauInterface.LARGEUR_DECOR-50, 60, 40, 40, "Images/Niveau5/ennemi.png", "ennemi1d", this);
		}
	}

	/**
	 * Les deuxième ennemis (Tombants de haut aléatoirement)
	 * 
	 * @author Jordan
	 */
	private void deuxiemesEnnemis() {
		if(Manipulation.siTpsA(getTemps(), 0.66, true) || Manipulation.siTpsA(getTemps(), 1.32, true) || Manipulation.siTpsA(getTemps(), 1.98, true)) {
			new Ennemi(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -30, 40, 40, "Images/Niveau5/ennemi.png", "ennemi2", this);
		}
	}

	/**
	 * Les troisièmes ennemis (Tombants de haut [Gauche & Droite])
	 * 
	 * @author Jordan
	 */
	private void troisiemesEnnemis() {
		if(Manipulation.quatreFoisParSecondes(getTemps())) {
			new Ennemi(10, -30, 40, 40, "Images/Niveau5/ennemi.png", "ennemi3g", this); //g comme à gauche
			new Ennemi(NiveauInterface.LARGEUR_DECOR-65, -30, 40, 40, "Images/Niveau5/ennemi.png", "ennemi3d", this);
		}
	}

	/**
	 * Des balles apparaissent aléatoirement des quatre côtés
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void ballesAléatoiresPartout() throws FileNotFoundException {
		if(!vsBOSS) {
			if(Math.random()>0.98) {
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/ennemi balle.png", "balle aléatoire bas", this); //Sors du haut va vers le bas
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN, 20, 20, "Images/ennemi balle.png", "balle aléatoire haut", this); //Sors du bas va vers le haut
				new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/ennemi balle.png", "balle aléatoire droite", this); //Sors de la gauche va vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-55, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/ennemi balle.png", "balle aléatoire gauche", this); //Sors de la droite va vers la gauche
			}
		}else {
			//Le premier "balles aléatoires de partout" c'est les grosses flammes
			if((getTempsTotal() - tpsDébutBoss)<BALLES_TOMBENT) {
				if(Math.random()>0.985) {
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), -10, 50, 50, PATH_BALLES_FEU, "balle aléatoire bas", this); //Sors du haut va vers le bas
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), NiveauInterface.HAUTEUR_ECRAN, 50, 50, PATH_BALLES_FEU, "balle aléatoire haut", this); //Sors du bas va vers le haut
					new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 50, 50, PATH_BALLES_FEU, "balle aléatoire droite", this); //Sors de la gauche va vers la droite
					new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-100, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 50, 50, PATH_BALLES_FEU, "balle aléatoire gauche", this); //Sors de la droite va vers la gauche
				}
			}else { //Le deuxième c'est les petites où elles peuvent bouger sur les côtés
				if(Math.random()>0.975) {
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), -10, 20, 20, PATH_BALLES_FEU, "balle aléatoire bas", this); //Sors du haut va vers le bas
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), NiveauInterface.HAUTEUR_ECRAN, 20, 20, PATH_BALLES_FEU, "balle aléatoire haut", this); //Sors du bas va vers le haut
					new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, PATH_BALLES_FEU, "balle aléatoire droite", this); //Sors de la gauche va vers la droite
					new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-100, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, PATH_BALLES_FEU, "balle aléatoire gauche", this); //Sors de la droite va vers la gauche
				}
			}
		}
	}

	/**
	 * Des balles apparaissent aléatoirement du haut
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void ballesAléatoiresHaut() throws FileNotFoundException{
		if(!vsBOSS) {
			if(Math.random() > 0.95)
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/ennemi balle.png", "balle aléatoire bas", this); //X aléatoire
		}else { //Si c'est contre le boss elles apparaissent + souvent
			if(Math.random() > 0.9)
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, PATH_BALLES_FEU, "balle aléatoire bas", this); //X aléatoire
		}
	}

	/**
	 * Des balles foncent vers le joueur
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void ballesViseuses() throws FileNotFoundException{
		if(!vsBOSS) {
			//Chaque seconde des balles foncent
			if(Manipulation.siTpsA(getTemps(), 1, false)) {
				//joueur.get..... car on récupère l'emplacement du joueur
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-30, joueur.getTranslateY(), 20, 20, "Images/ennemi balle.png", "balle vise gauche", this); //Va vers le joueur par la gauche
				new EnnemiBalle(0, joueur.getTranslateY(), 20, 20, "Images/ennemi balle.png", "balle vise droite", this); //Va vers le joueur par la droite
				new EnnemiBalle(joueur.getTranslateX()+5, -10, 20, 20, "Images/ennemi balle.png", "balle vise bas", this); //Va vers le joueur par le haut
				new EnnemiBalle(joueur.getTranslateX()+5, NiveauInterface.HAUTEUR_ECRAN-10, 20, 20, "Images/ennemi balle.png", "balle vise haut", this); //Va vers le joueur par le bas
			}
		}else {//3 fois/s
			if(Manipulation.siTpsA(getTemps(), 0.66, false) || (Manipulation.siTpsA(getTemps(), 1.32,false)) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-30, joueur.getTranslateY(), 20, 20, PATH_BALLES_FEU, "balle vise gauche", this); //Va vers le joueur par la gauche
				new EnnemiBalle(0, joueur.getTranslateY(), 20, 20, PATH_BALLES_FEU, "balle vise droite", this); //Va vers le joueur par la droite
				new EnnemiBalle(joueur.getTranslateX()+5, -10, 20, 20, PATH_BALLES_FEU, "balle vise bas", this); //Va vers le joueur par le haut
				new EnnemiBalle(joueur.getTranslateX()+5, NiveauInterface.HAUTEUR_ECRAN-10, 20, 20, PATH_BALLES_FEU, "balle vise haut", this); //Va vers le joueur par le bas
			}
		}
	}

	/**
	 * Avertissement pour dire où va apparaître l'éclair et il apparaît après très rapidement
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void éclairs() throws FileNotFoundException{
		double random = Math.random();

		if((Manipulation.siTpsA(getTemps(), 0.05, false)) || (Manipulation.siTpsA(getTemps(), 1.05, false))) {
			avertissement.setFill(Color.RED); //Avertissement rouge
			avertissement.setOpacity(0); //Au début invisible
			Manipulation.ajouterJeu(root, avertissement);

			//Quel endroit ? (33% chacun)
			if(random < 0.33)
				endroit = 0; //0: Tout à gauche
			else if(random > 0.34 && random < 0.67)
				endroit = 1; //1: Au milieu
			else
				endroit = 2; //2: Tout à d*roite

			//On met à l'endroit choisi au hasard
			if(endroit==0)
				avertissement.relocate(0, 0);
			else if (endroit==1)
				avertissement.relocate(tiers_écran, 0);
			else
				avertissement.relocate((tiers_écran*2)-15, 0);

		}

		// ======= APPARITION DE L'AVERTISSEMENT PROGRESSIF ======= \\
		if((Manipulation.siTpsA(getTemps(), 0.3, false)) || (Manipulation.siTpsA(getTemps(), 1.3, false)))
			avertissement.setOpacity(0);

		if((Manipulation.siTpsA(getTemps(), 0.4, false)) || (Manipulation.siTpsA(getTemps(), 1.4, false)))
			avertissement.setOpacity(0.15);

		if((Manipulation.siTpsA(getTemps(), 0.5, false)) || (Manipulation.siTpsA(getTemps(), 1.5, false)))
			avertissement.setOpacity(0.25);

		if((Manipulation.siTpsA(getTemps(), 0.6, false)) || (Manipulation.siTpsA(getTemps(), 1.6, false)))
			avertissement.setOpacity(0.35);

		if((Manipulation.siTpsA(getTemps(), 0.7, false)) || (Manipulation.siTpsA(getTemps(), 1.7, false)))
			avertissement.setOpacity(0.5);

		if((Manipulation.siTpsA(getTemps(), 0.8, false)) || (Manipulation.siTpsA(getTemps(), 1.8, false)))
			avertissement.setOpacity(0.75);

		//Fin de l'avertissement
		if((Manipulation.siTpsA(getTemps(), 0.9, false)) || (Manipulation.siTpsA(getTemps(), 1.9, false)))
			Manipulation.supprimer(this, avertissement);

		//L'éclair apparait!
		if((Manipulation.siTpsA(getTemps(), 0.91, false)) || (Manipulation.siTpsA(getTemps(), 1.91, false)))
			new EnnemiBalle(endroit*tiers_écran, avertissement.getTranslateY(), tiers_écran, NiveauInterface.HAUTEUR_ECRAN, "Images/éclair.png", "balle éclair", this); //Va vers le joueur par la droite
	}

	@Override
	public void bossTir() throws FileNotFoundException {
		// ====================== BALLES ALEATOIRES DU HAUT ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>DEBUT && (getTempsTotal() - tpsDébutBoss)<FIN_DEBUT) {
			ballesAléatoiresHaut();
		}

		// ====================== MUR DE BALLES (Plus serré que quand c'était le miniboss) ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>MUR_BALLES && (getTempsTotal() - tpsDébutBoss)<FIN_MUR_BALLES) {
			if(Manipulation.deuxFoisParSecondes(getTemps())){
				//Pas le même écart sinon ça sera toujours le même mur avec les mêmes trous 
				int ecartBalles = (int) (Math.random() * 70)+70; //+70 évite le ecartBalles = 0

				//Création de la ligne de balles
				for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
					new BossBalle(i, -10, PATH_BALLES_FEU, "miniboss balle descente", this); //miniboss car elles sont + lentes 
					new BossBalle(i, -40, PATH_BALLES_FEU, "miniboss balle descente", this);
				}
			}
		}

		// ====================== ECLAIRS ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>ECLAIRS_BOSS && (getTempsTotal() - tpsDébutBoss)<FIN_ECLAIRS_BOSS) {
			éclairs();
		}

		// ====================== TUNNEL ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>TUNNEL && (getTempsTotal() - tpsDébutBoss)<FIN_TUNNEL) {
			//Par défaut voilà la largeur du tunnel
			double maxGauche = 50;
			double maxDroite = 120;

			//Avec les balles c'est plus facile
			if((getTempsTotal() - tpsDébutBoss)>TUNNEL_BALLES) {
				maxGauche = 100;
				maxDroite = 170;
			}

			//L'écart entre les balles en largeur afin que cela ait une apparance de mur
			int ecartMur = NiveauInterface.TAILLE_BALLE_BOSS; 

			//Mettre cette limite sert à faire en sorte que pour le tunnel, le mur de droite ne soit pas sur le tableau
			int limiteBalle = NiveauInterface.LARGEUR_DECOR - 192;

			double toutAGauche = boss.getTranslateX()-maxGauche; //La balle la plus à gauche du BOSS
			double toutADroite;

			//Si le boss n'est pas proche du tableau qui va faire spawn des balles sur le tableau..
			if(boss.getTranslateX() <= limiteBalle)
				toutADroite = boss.getTranslateX()+maxDroite; //..tout se passe normal
			else
				toutADroite = NiveauInterface.LARGEUR_DECOR-35; //Sinon la limite c'est la fin du décor

			//Création des balles tout à gauche et droite
			new BossBalle(toutAGauche, boss.getTranslateY()+maxDroite, PATH_BALLES_FEU, "balle tunnel", this);

			if(boss.getTranslateX() <= limiteBalle) //Si le boss pas trop proche du tableau
				new BossBalle(toutADroite, boss.getTranslateY()+maxDroite, PATH_BALLES_FEU, "balle tunnel", this);

			//Création d'un mur de balles du milieu à la toute gauche [1er tunnel gauche]
			for(int i=0;i<toutAGauche;i+=ecartMur) {
				new BossBalle(i, boss.getTranslateY()+maxDroite, PATH_BALLES_FEU, "balle tunnel", this);
			}

			//De même pour le mur de balles du milieu à la toute droite [2ème tunnel gauche] si le boss n'est pas proche du tableau
			if(boss.getTranslateX() <= limiteBalle) {
				for(int i=NiveauInterface.LARGEUR_DECOR-35; i>toutADroite;i-=ecartMur) {
					new BossBalle(i, boss.getTranslateY()+maxDroite,  PATH_BALLES_FEU, "balle tunnel", this);
				}
			}

			// + balles aléatoires
			if((getTempsTotal() - tpsDébutBoss) > TUNNEL_BALLES) {
				if(Manipulation.siTpsA(getTemps(), 1, false)) {
					boss.tir("balle NO");
					boss.tir("balle NE");
					boss.tir("balle S");
					boss.tir("balle N");
					boss.tir("balle E");
					boss.tir("balle OS");
					boss.tir("balle SE");
					boss.tir("balle O");
				}
			}
		}

		// ====================== BALLES ALEATOIRES DE PARTOUT ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>BOSS_BALLES_ALEATOIRES && (getTempsTotal() - tpsDébutBoss)<FIN_BOSS_ALEATOIRES_PLUS_PARTOUT) {
			ballesAléatoiresPartout();

			if((getTempsTotal() - tpsDébutBoss)>BOSS_ALEATOIRES_PLUS_PARTOUT){
				if(Manipulation.siTpsA(getTemps(), 1, false)) {
					boss.tir("balle NO");
					boss.tir("balle NE");
					boss.tir("balle S");
					boss.tir("balle N");
					boss.tir("balle E");
					boss.tir("balle OS");
					boss.tir("balle SE");
					boss.tir("balle O");
				}
			}
		}

		// ====================== BALLES FONCE ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>BALLE_FONCE && (getTempsTotal() - tpsDébutBoss)<VISEUSES) {
			if(Manipulation.quatreFoisParSecondes(getTemps()))
				new BossBalle(joueur.getTranslateX(), -10, PATH_BALLES_FEU, "balle fonce", this);
		}

		// ====================== BALLES VISEUSES ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>VISEUSES && (getTempsTotal() - tpsDébutBoss)<MUR_BALLES2) {
			ballesViseuses();
		}

		// ====================== MUR DE BALLES (PLUS RAPIDE) ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>MUR_BALLES2 && (getTempsTotal() - tpsDébutBoss)<BALLES_TOMBENT) {
			if(Manipulation.deuxFoisParSecondes(getTemps())) {
				//Pas le même écart sinon ça sera toujours le même mur avec les mêmes trous 
				int ecartBalles = (int) (Math.random() * 80)+80; //+70 évite le ecartBalles = 0
				//Création de la ligne de balles
				for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
					new BossBalle(i, -10, PATH_BALLES_FEU, "boss balle descente", this);
					new BossBalle(i, -40, PATH_BALLES_FEU, "boss balle descente", this);
				}
			}
		}

		// ====================== BALLES ALEATOIRES DU HAUT + MOUVEMENT SUR LES COTES ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>BALLES_TOMBENT && (getTempsTotal() - tpsDébutBoss)<BALLES_TOMBENT_PARTOUT){
			ballesAléatoiresHaut();
		}

		// ====================== BALLES ALEATOIRES DE PARTOUT + MOUVEMENT SUR LES COTES ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>BALLES_TOMBENT_PARTOUT && (getTempsTotal() - tpsDébutBoss) < FIN_BALLES_TOMBENT_PARTOUT){
			ballesAléatoiresPartout();
		}

		// ====================== MUR DE BALLES VERTICAL ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>MUR_BALLES_V && (getTempsTotal() - tpsDébutBoss)<ECLAIRS_BALLES) {
			//Pas le même écart sinon ça sera toujours le même mur avec les mêmes trous 
			int ecartBalles = (int) (Math.random() * 100)+100; //+90 évite le ecartBalles = 0

			if(Manipulation.siTpsA(getTemps(), 0.98, false)) {
				//Création de la ligne de balles (De gauche à droite)
				for(int i = -ecartBalles+50; i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2; i+=ecartBalles) {
					new BossBalle(10, i, PATH_BALLES_FEU, "balle mur vertical g", this); 
					new BossBalle(-20, i, PATH_BALLES_FEU, "balle mur vertical g", this); 
				}
			}

			if(Manipulation.siTpsA(getTemps(), 1.98, false)) {
				//Et la ligne de droite à gauche
				for(int i = (-ecartBalles); i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2+10; i+=ecartBalles) {
					new BossBalle(NiveauInterface.LARGEUR_DECOR-70, i, PATH_BALLES_FEU, "balle mur vertical d", this); 
					new BossBalle(NiveauInterface.LARGEUR_DECOR-40, i, PATH_BALLES_FEU, "balle mur vertical d", this);
				}
			}

			// PLUS MUR DE BALLES NORMAL (CE QUI FAIT PARTOUT)
			if((getTempsTotal() - tpsDébutBoss)>MUR_BALLES_PARTOUT) {
				if(Manipulation.siTpsA(getTemps(), 1, false)){
					//Création de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, PATH_BALLES_FEU, "boss balle descente", this); 
						new BossBalle(i, -40, PATH_BALLES_FEU, "boss balle descente", this);
					}
				}
			}
		}

		// ====================== ECLAIR + BALLES ALEATOIRES ===================== \\
		if((getTempsTotal() - tpsDébutBoss)>ECLAIRS_BALLES && (getTempsTotal() - tpsDébutBoss)<DERNIER) {
			éclairs();
			ballesAléatoiresHaut();
		}

		if((getTempsTotal() - tpsDébutBoss)>DERNIER) {
			if(Manipulation.siTpsA(getTemps(), 0.66, false) || Manipulation.siTpsA(getTemps(), 1.32, false) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
				//Pas le même écart sinon ça sera toujours le même mur avec les mêmes trous 
				int ecartBalles = (int) (Math.random() * 70)+70; //+70 évite le ecartBalles = 0
				//Création de la ligne de balles
				for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
					new BossBalle(i, -10, PATH_BALLES_FEU, "boss balle descente", this);
					new BossBalle(i, -40, PATH_BALLES_FEU, "boss balle descente", this);
				}
			}
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
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, 400, "DIFFICULTE -- MAX", "Polices/SCORE.ttf", 40, "RED");
			else
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, 400, "DIFFICULTE -- " + Main.getDifficulté().toString(), "Polices/SCORE.ttf", 40, "YELLOW");
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

	@Override
	public void start(Stage NIV5) throws Exception {
		Scene niveau_V = new Scene(createNiveau(NIV5)); //Création du niveau

		NIV5.setScene(niveau_V); //La scène du niveau devient la scène principale
		NIV5.setTitle("NIVEAU V"); //Nom de la fenêtre
		NIV5.setResizable(false); //On ne peut pas redimensionner la fenêtre

		//On remet l'icône du jeu comme elle a changée car c'est une autre fênetre
		NIV5.getIcons().add(new Image("file:Images/Icone.png"));

		Manipulation.updateScore(this); //Met à jour le score pour le début du niveau

		NIV5.show(); //Affichage du niveau

		/*On gère les boutons tapés (Contrôles du jeu) 
		 *[Quand on APPUIE sur la touche]
		 */
		niveau_V.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
						NIV5.close();
						mediaPlayer.stop();
						
						//Niveau FINAL débloqué
						try {
							Main.sauvegarde(6);
						} catch (IOException e) {
							System.err.println("Erreur lors de la sauvegarde de l'état 6 !");
						}
						
						Alert alerte = new Alert(AlertType.INFORMATION); 
						alerte.setTitle("NIVEAU V TERMINE !"); //Le titre de la fênetre
						alerte.setHeaderText("Vous avez réussi le niveau V ! ZEROD VOUS ATTEND."); //Au dessus du texte
						alerte.setContentText("SAUVEGARDE REUSSIE. REDEMARREZ LE JEU."); //Le texte

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
		niveau_V.setOnKeyReleased(new EventHandler<KeyEvent>() {
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
						Joueur g = (Joueur)grazeBox;
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
	public Parent createNiveau(Stage niveau) {
		if (Main.son()) mediaPlayer.play(); //Musique du niveau		
		root.setPrefSize(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); //Met à jour les dimensions du jeu

		// ========================= Image de fond =========================
		try {
			imageFond = new ImageView(new Image(new FileInputStream("Fonds/Niveau_V/bg_Niveau5_debut.gif")));
			imageFond.setX(-15); 
			imageFond.setY(0);
			imageFond.setFitHeight(NiveauInterface.HAUTEUR_ECRAN+15); //Change la taille
			imageFond.setFitWidth(NiveauInterface.LARGEUR_ECRAN); 
			root.getChildren().add(imageFond);
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image n'a pas été trouvé !", e);	
		} 

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
						setLockGameOver(true) ;
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
		return root;
	}

	/**
	 * Tir en spirale (3 balles à la fois toutes les 0.2s)
	 * 
	 * @param [e]: Celui qui tire
	 * @author Jordan
	 */
	private void tirSpirale(Personnage e) {
		if(Manipulation.siTpsA(getTemps(), 0.2, false) || Manipulation.siTpsA(getTemps(), 0.8, false) || Manipulation.siTpsA(getTemps(), 1.4, false)) {
			e.tir("balle NO");
			e.tir("balle NE");
			e.tir("balle S");
		}

		if(Manipulation.siTpsA(getTemps(), 0.4, false) || Manipulation.siTpsA(getTemps(), 1, false) || Manipulation.siTpsA(getTemps(), 1.6, false)){
			e.tir("balle N");
			e.tir("balle E");
			e.tir("balle OS");
		}

		if(Manipulation.siTpsA(getTemps(), 0.6, false) || Manipulation.siTpsA(getTemps(), 1.2, false) || Manipulation.siTpsA(getTemps(), 1.8, false)){
			e.tir("balle NE");
			e.tir("balle SE");
			e.tir("balle O");
		}

		//Si c'est le miniboss (PHASE II)
		if(vsMINIBOSS) { //Tir dans toutes les directions toutes les 0.125ms 
			if(Manipulation.huitFoisParSecondes(getTemps())){
				e.tir("balle NO");
				e.tir("balle NE");
				e.tir("balle S");
				e.tir("balle N");
				e.tir("balle E");
				e.tir("balle OS");
				e.tir("balle SE");
				e.tir("balle O");
			}
		}
	}

	@Override
	public void miniBOSSTir(BOSS b) throws FileNotFoundException {
		//============================== MUR DE BALLES ==========================
		if(getTempsTotal() > MINIBOSS_ATTAQUE && getTempsTotal() < MINIBOSS_PREPARATION - 1.5) {
			if(Manipulation.deuxFoisParSecondes(getTemps())){
				//Pas le même écart sinon ça sera toujours le même mur avec les mêmes trous 
				int ecartBalles = (int) (Math.random() * 75)+75; //+100 évite le ecartBalles = 0

				//Création de la ligne de balles
				for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
					new BossBalle(i, -10, PATH_BALLES_FEU, "miniboss balle descente", this); //X aléatoire
					new BossBalle(i, -40, PATH_BALLES_FEU, "miniboss balle descente", this); //X aléatoire
				}
			}
		}
		//============================= TUNNEL ==============================\\
		else if(getTempsTotal() > MINIBOSS_2EME_ATTAQUE && getTempsTotal() < MINIBOSS2_PREPARATION) {
			//L'écart entre les balles en largeur afin que cela ait une apparance de mur
			int ecartMur = NiveauInterface.TAILLE_BALLE_BOSS; 

			//Mettre cette limite sert à faire en sorte que pour le tunnel, le mur de droite ne soit pas sur le tableau
			int limiteBalle = NiveauInterface.LARGEUR_DECOR - 192;

			double toutAGauche = miniBOSS.getTranslateX()-50; //La balle la plus à gauche du BOSS
			double toutADroite;

			//Si le boss n'est pas proche du tableau qui va faire spawn des balles sur le tableau..
			if(miniBOSS.getTranslateX() <= limiteBalle)
				toutADroite = miniBOSS.getTranslateX()+120; //..tout se passe normal
			else
				toutADroite = NiveauInterface.LARGEUR_DECOR-35; //Sinon la limite c'est la fin du décor

			//Création des balles tout à gauche et droite
			new BossBalle(toutAGauche, miniBOSS.getTranslateY()+120, PATH_BALLES_FEU, "balle tunnel", this);

			if(miniBOSS.getTranslateX() <= limiteBalle) //Si le boss pas trop proche du tableau
				new BossBalle(toutADroite, miniBOSS.getTranslateY()+120, PATH_BALLES_FEU, "balle tunnel", this);

			//Création d'un mur de balles du milieu à la toute gauche [1er tunnel gauche]
			for(int i=0;i<toutAGauche;i+=ecartMur) {
				new BossBalle(i, miniBOSS.getTranslateY()+120, PATH_BALLES_FEU, "balle tunnel", this);
			}

			//De même pour le mur de balles du milieu à la toute droite [2ème tunnel gauche] si le boss n'est pas proche du tableau
			if(miniBOSS.getTranslateX() <= limiteBalle) {
				for(int i=NiveauInterface.LARGEUR_DECOR-35; i>toutADroite;i-=ecartMur) {
					new BossBalle(i, miniBOSS.getTranslateY()+120,  PATH_BALLES_FEU, "balle tunnel", this);
				}
			}

		}
		//============================= SPIRALE ==============================\\
		else if(getTempsTotal() > MINIBOSS2_2EME_ATTAQUE) {
			tirSpirale(miniBOSS);
		}
	}

	@Override
	public void boss() throws FileNotFoundException {
		mediaPlayer.stop(); //Fin de la musique du niveau 1

		//Musique du boss
		mediaPlayer = new MediaPlayer(new Media(new File("Sons/Niveau5/boss.mp3").toURI().toString()));
		mediaPlayer.play();
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //Répète a l'infini

		vsBOSS = true;
		tpsDébutBoss = getTempsTotal(); //On enregistre le temps de début du combat

		//On ne fait pas Manipulation.ajouterTexte car on doit récupérer ce nom pour pas qu'il soit supprimé lors de updateScore()
		Texte niv = new Texte(nomBOSS, "nomboss");
		niv.setX(NiveauInterface.X_NOM_BOSS);
		niv.setY(NiveauInterface.Y_NOM_BOSS);
		niv.setFont(Font.loadFont(new FileInputStream("Polices/BOSS.ttf"), 30)); //Charge la police et la change
		niv.setFill(Paint.valueOf("WHITE")); //Couleur
		Manipulation.ajouterJeu(getRoot(), niv);
	}

	@Override
	public void dialogue() throws FileNotFoundException {
		super.dialogue();
		//Dialogue avant le début du boss
		if(!bossCommencé) {
			switch(getNbEspaceAppuyés()) {
			case 0:
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/Niveau5/Dialogue/boss.png", NiveauInterface.LARGEUR_DECOR-395, 250, 410, 580);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu es tres coriace toi !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1: 
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu ne devrais pas me sous-estimer.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "hmhmhm... Tu es interessant.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ton periple s'arrete maintenant.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;
			}

			if(getNbEspaceAppuyés() > 3) { //Fin du dialogue
				boss();	 
				bossCommencé = true;
				setNbEspaceAppuyés(0); //Remise à 0 pour le dialogue de fin
			}
		}

		//Dialogue après le boss
		if(bossBattu) {
			switch(getNbEspaceAppuyés()) {
			case 0:
				//Toutes les balles ennemies meurent
				Manipulation.sprites(root).stream().filter(e -> e.getType().contains("balle")).forEach(balle -> {
					balle.meurs();
				});

				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/Niveau5/Dialogue/boss.png", NiveauInterface.LARGEUR_DECOR-395, 250, 410, 580);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Arrgghh.... Je...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1: 
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Silence, traitre.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Finissons-en. Ou est Zerod ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 4:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "En face. Tu ne feras pas le poids.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 5:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...Ne cherche pas. Et rends-toi.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 6:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ahahah.. Tu m'avais dit la meme chose.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 7:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourtant, te voici vaincu.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 8:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 9:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Refuses-tu toujours de ne pas dire...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 10:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...Les raisons de ta trahison ?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 11:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...Je trouve aussi que ce monde...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 12:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "n'est pas equitable.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 13:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Nous sommes beaucoup plus puissants.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 14:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Leur crainte est comprehensible.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 15:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "En tant que sous-chef tu aurais pu...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 16:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...stopper tout ca.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;	

			case 17:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "La guerre avait deja commencee.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 18:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C'est vrai, pas de retour arriere...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 19:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "APRES LA MORT DE MON PERE !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 20:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Salar l'acheva.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;
			}

			if(getNbEspaceAppuyés() > 20) { //Fin du dialogue
				setEnDialogue(false);
				setNiveauTerminé(true);
				setTpsDébScore(getTempsTotal());
			}
		}
	}
}
