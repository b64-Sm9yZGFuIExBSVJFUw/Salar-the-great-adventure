package niveau;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import appli.Difficult�;
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
	private static final double CHANGEMENT_FOND_NIVEAU = 2.3; //Le fond change au d�but
	private static final double APPARITION_NOM_NIVEAU = 2.7; //Le nom du niveau s'affiche
	private static final int NOM_NIVEAU_EN_APPARITION = 4; //Augmentation de son opacit�
	private static final int NOM_NIVEAU_EN_DISPARITION = 6; //Descente de son opacit�
	private static final int NOM_NIVEAU_EN_DISPARITION2 = 9; //Pareil mais plus vite
	private static final int FIN_NOM_NIVEAU = 11; //Il dispara�t
	private static final double APPARITION_1ERS_ENNEMIS = 15; //Premiers ennemis
	private static final double DISPARITION_1ERS_ENNEMIS = 25; //Ils disparaissent
	private static final double APPARITION_2EMES_ENNEMIS = 26; //Deuxi�mes ennemis
	private static final double DISPARITION_2EMES_ENNEMIS = 36; //Ils disparaissent
	private static final double APPARITION_MINIBOSS = 37.8; //Il appara�t
	private static final double MINIBOSS_ATTAQUE = 39; //Il commence a attaquer
	private static final double MINIBOSS_PREPARATION = 50.2; //Pause, et deuxi�me attaque
	private static final double MINIBOSS_2EME_ATTAQUE = 52; //Il devient s�rieux
	private static final double MINIBOSS_PART = 62; //Il part
	private static final double MINIBOSS_PLUS_VISIBLE = 66; //Miniboss parti
	private static final double APPARITION_ENNEMIS_APRES_MINIBOSS = 65.5; //Les ennemis apparaissent
	private static final double APPARITION_ENNEMIS2_APRES_MINIBOSS = 76; //Ceux du d�but viennent en +
	private static final double BALLES_ALEATOIRES_PARTOUT = 88.5; //Des balles sortent de partout al�atoirement
	private static final double APPARITION_ENNEMIS_APRES_BALLES = 101.5; //Des ennemis apr�s
	private static final double APPARITION_MINIBOSS2 = 114.5; //Le miniboss revient !
	private static final double MINIBOSS2_ATTAQUE = 118.5; //Il r�attaque
	private static final double MINIBOSS2_PREPARATION = 129; //Pause, et apr�s deuxi�me attaque
	private static final double MINIBOSS2_2EME_ATTAQUE = 131; //La 2�me attaque
	private static final double MINIBOSS2_PART = 142;
	private static final double BALLES_ALEATOIRES = 144; //Miniboss parti, des balles al�atoires sortant du haut
	private static final double BALLES_VISEUSES = 155; //Balles qui "visent" le joueur
	private static final double BALLES_VISEUSES_ET_ALEATOIRES = 168; //En plus des balles qui visent, les balles al�atoires sortant du haut reviennent
	private static final double ECLAIRS = 180; //Des �clairs
	private static final double IMAGE_FOND_SOMBRE = 194.5; //L'image du fond (feu) devient + sombre
	private static final double AUTRE_IMAGE_FOND = 207; //L'image du fond change, c'est celle du boss mais sombre
	private static final double APPARITION_VIES = 215; //Les 4 vies qui apparaissent avant l'apparition du BOSS
	public static final double APPARITION_BOSS = 217; //Le boss appara�t, fin du niveau
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
	private static final double FIN_DEBUT = 22.5; //Fin de la premi�re attaque
	private static final double MUR_BALLES = 22.75; //Mur de balles
	private static final double FIN_MUR_BALLES = 32.5; //Fin de l'attaque
	private static final double ECLAIRS_BOSS = 35; //Attaque des �clairs
	private static final double FIN_ECLAIRS_BOSS = 47; //Fin de l'attaque
	private static final double TUNNEL = 47.5; //Attaque du tunnel
	private static final double TUNNEL_BALLES = 58.25; //Attaque du tunnel PLUS des balles
	private static final double FIN_TUNNEL = 71.5; //Fin de l'attaque du tunnel (Et aussi du coup avec les balles ajout�es)
	private static final double BOSS_BALLES_ALEATOIRES = 72; //Balles al�atoires tombant de haut
	private static final double BOSS_ALEATOIRES_PLUS_PARTOUT = 84.25; //Balles al�atoires de tout les c�t�s
	private static final double FIN_BOSS_ALEATOIRES_PLUS_PARTOUT = 94; //Fin de l'attaque des balles al�atoires
	private static final double BALLE_FONCE = 96; //De haut vers le bas, une balle qui vise le joueur a toute vitesse
	private static final double VISEUSES = 108.25; //Balles de tout les c�t�s qui visent le joueur
	private static final double MUR_BALLES2 = 121.5; //Mur de balles mais plus rapide
	private static final double BALLES_TOMBENT = 145; //Balles al�atoires sortant du haut mais bougent sur les c�t�s
	private static final double BALLES_TOMBENT_PARTOUT = 157.5; //Pareil mais du coup des balles al�atoires sortant de partout
	private static final double FIN_BALLES_TOMBENT_PARTOUT = 167.5; //Fin de l'attaque des balles al�atoires de partout (+ bougeant sur les c�t�s)
	private static final double MUR_BALLES_V = 169; //Mur de balles vertical (Sortant de gauche & droite au lieu de haut)
	private static final double MUR_BALLES_PARTOUT = 181.5;//Mur de balles de partout (Original + Vertical)
	private static final double ECLAIRS_BALLES = 192; //Eclairs + Balles al�atoires de haut qui bougent
	private static final double DERNIER = 207; //Derni�re attaque: Mur de balles doucements
	
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

	// ================================== Objets qui vont permettre l'affichage: Tout ce qu'on ajoute dedans sera affich� ==================================
	private static ImageView imageFond = new ImageView();

	// ================================== Variables qui g�rent les miniboss ==================================
	private static BOSS miniBOSS = null; //Le mini-boss
	private static boolean miniBossVersDroite = false; //Va-t-il vers la droite ?

	// ================================== Variables qui g�rent le boss ==================================
	private static BOSS5 boss = null; //Le BOSS
	private static boolean vsBOSS = false; //Actuellement contre un boss ?
	private static boolean bossBattu = false; //BOSS battu ?
	private static boolean bossCommenc� = false; //Le combat a commenc� ?
	private static boolean vsMINIBOSS = false; //Actuellement contre la 2�me phase du miniboss ? (1�re inutile)
	private boolean bossVersDroite = false; //Le boss bouge-t-il vers la droite ? (Pour g�rer son mouvement)
	private static String nomBOSS = "GENESIS";
	private double tpsD�butBoss = 0; //A quel moment le combat contre le boss commence ?

	// ================================== Variables qui g�rent l'attaque des �clairs ==================================
	private int tiers_�cran = 200; //Le tiers d'un �cran [L'�cran du jeu fait 600px]
	private Rectangle avertissement = new Rectangle(tiers_�cran,NiveauInterface.HAUTEUR_ECRAN+10); //La zone rouge qui averti l'emplacement de l'�clair
	private int endroit; //L'endroit al�atoire o� va appara�tre l'�clair

	public void update() throws FileNotFoundException {
		//Le getTemps() d'une frame en JavaFX (On le met ici pour pouvoir manipuler les objets selon le getTemps())
		setTemps(getTemps() + NiveauInterface.TEMPS_1_FRAME);
		setTempsTotal(getTempsTotal() + NiveauInterface.TEMPS_1_FRAME);

		//Si �a fait 100 millisecondes que le joueur a tir�
		if(getTempsTotal() - getTpsD�bTir() > NiveauInterface.COOLDOWN_TIR) setCooldownTir(false);

		//Pour mieux calculer le getTemps()
		if(getTemps() > 2)
			setTemps(0);

		//Si cooldown �coul�
		if (getTempsTotal() - getTpsD�bTouch�() > NiveauInterface.COOLDOWN_REGENERATION) {
			setCooldownTouch�(false);
			joueur.misAJour(false); //A mettre a jour
		}

		//Si le cooldown est fini, et que le sprite n'a pas �t� mis � jour et que il n'est pas en focus et qu'il ne tire pas (Sinon il se met constamment en pose normale)
		if(!isCooldownTouch�() && !joueur.aEteMAJ() && !isFocus() && !isTir�()) { 
			joueur.getImg().setImage(new Image(new FileInputStream("Images/Joueur/joueur.png")));
			joueur.misAJour(true);
		}

		//Pour chacun des sprite pr�sent sur le jeu on va les mettre � jour
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
							if(!isCooldownTouch�()) Manipulation.grazeUp(this);
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

				//Ceux du d�but (Car apr�s le miniboss ils reviennent mais n'attaque pas dans le c�t�s, seulement tout droit mais reviennent en m�me temps ds balles al�atoires)
				if(getTempsTotal() < APPARITION_MINIBOSS || getTempsTotal() > APPARITION_ENNEMIS_APRES_BALLES)
					tirSpirale((Ennemi)s);
				else {
					//Le fait que ce soit miniboss ou pas ne change rien, c'est juste la trajectoire de la balle qui nous importe
					if(Math.random() > 0.99)
						s.tir("balle al�atoire bas");
				}
				break;

			case "ennemi1d":
				s.bougerGauche(0.5);

				//Ceux du d�but (Car apr�s le miniboss ils reviennent mais n'attaque pas dans le c�t�s, seulement tout droit mais reviennent en m�me temps ds balles al�atoires)
				if(getTempsTotal() < APPARITION_MINIBOSS || getTempsTotal() > APPARITION_ENNEMIS_APRES_BALLES)
					tirSpirale((Ennemi)s);
				else {
					//Le fait que ce soit miniboss ou pas ne change rien, c'est juste la trajectoire de la balle qui nous importe
					if(Math.random() > 0.99)
						s.tir("balle al�atoire bas");
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
						//Si pas attaque tunnel il va attaquer pas � la m�me vitesse que l'attaque du tunnel
						if(getTempsTotal() < MINIBOSS_2EME_ATTAQUE) miniBOSSTir((BOSS)s); 
					} catch (FileNotFoundException e1) {
						System.err.println("Sprite balle");
					}

					//Replace le miniboss au milieu du d�cor [Apr�s l'attaque des murs (PHASE I) ET Apr�s l'attaque du tunnel (PHASE II)]
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

						//Al�atoire toute les 0.125 secondes
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

					//Attaque spirale (PHASE II), derni�re attaque du miniboss
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
				if((getTempsTotal() - tpsD�butBoss) < MUR_BALLES_PARTOUT)
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

			case "balle al�atoire bas": //Balles al�atoire allant en bas
				if(vsBOSS) { //Si c'est contre le BOSS
					s.bougerBas(3);
					//Si c'est le moment o� les balles bougent aussi sur les c�t�s
					if((getTempsTotal() - tpsD�butBoss)>BALLES_TOMBENT) { //2 fois par seconde, 50% de chance que la balle va de l'autre c�t�		
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

			case "balle al�atoire haut": //Balles al�atoire allant en haut
				if(vsBOSS) { //Si c'est contre le BOSS
					s.bougerHaut(3);
					//Si c'est le moment o� les balles bougent aussi sur les c�t�s
					if((getTempsTotal() - tpsD�butBoss)>BALLES_TOMBENT) { //2 fois par seconde, 50% de chance que la balle va de l'autre c�t�
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

			case "balle al�atoire gauche": //Balles al�atoire allant � gauche
				if(vsBOSS) { //Si c'est contre le BOSS
					s.bougerGauche(3);
					//Si c'est le moment o� les balles bougent aussi sur les c�t�s
					if((getTempsTotal() - tpsD�butBoss)>BALLES_TOMBENT) { //2 fois par seconde, 50% de chance que la balle va de l'autre c�t�
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

			case "balle al�atoire droite": //Balles al�atoire allant � droite
				if(vsBOSS) { //Si c'est contre le BOSS
					s.bougerDroite(3);
					//Si c'est le moment o� les balles bougent aussi sur les c�t�s
					if((getTempsTotal() - tpsD�butBoss)>BALLES_TOMBENT) { //2 fois par seconde, 50% de chance que la balle va de l'autre c�t�
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

			case "balle vise gauche": //Balles viseuses allant � gauche
				if(vsBOSS)
					s.bougerGauche(1);
				else
					s.bougerGauche(1);
				break;

			case "balle vise droite": //Balles viseuses allant � droite
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

			case "balle �clair": //Eclair
				s.bougerBas(0.01); //Tr�s vite [Flash]
				break;

			case "balle fonce": //Les balles qui visent le joueur du haut vers le bas
				s.bougerBas(0.05); //A une grande vitesse
				break;

			case "balle mur vertical g": //Allant � droite (Part de la Gauche)
				s.bougerDroite(0.2);
				break;

			case "balle mur vertical d": //Allant � gauche (Part de la Droite)
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
					if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_1, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai une question. Tu es un totzusen toi.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_2, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Et ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_3, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourquoi ? Pourquoi tu nous a trahis ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_4, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ce ne sont pas tes affaires.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_5, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "En plus tu es l'un des chefs !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_6, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "D'UNE ORGANISATION QUI NOUS TUENT!!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_7, false)) {
						Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je ne suis PLUS un totsuzen.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
					}


					//================ DEBUT DU BOSS =================\\
					if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DEBUT, false)) {
						//Pour effacer tout ce qui est dialogue
						Manipulation.toutEffacer(this);
						Manipulation.ajouterImage(getRoot(), "Fonds/NIVEAU_V/bg_boss.gif", 0, 0, NiveauInterface.LARGEUR_DECOR, NiveauInterface.LARGEUR_ECRAN);
						Manipulation.ajouterImage(getRoot(),"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

						//On r�affiche les personnages
						Manipulation.ajouterJeu(getRoot(), joueur.getImg());
						Manipulation.ajouterJeu(getRoot(), boss.getImg());

						//On enl�ve puis remet la barre de vie 
						Manipulation.supprimer(this, boss.getBarreVie());
						Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

						Manipulation.updateScore(this); //On remet le score

						setEnDialogue(false);
						setNbEspaceAppuy�s(0); //Pendant le dialogue le joueur pouvait taper "espace" ce qui les comptaient

						//On ne fait pas Manipulation.ajouterTexte car on doit r�cup�rer ce nom pour pas qu'il soit supprim� lors de updateScore()
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

					//On met bossTir ici car lors de l'attaque du tunnel, le boss n'attaque pas � la m�me fr�quence
					if((getTempsTotal() - tpsD�butBoss)<TUNNEL || (getTempsTotal() - tpsD�butBoss)>FIN_TUNNEL) {
						try {
							bossTir();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					//Pour l'�clair
					if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, ECLAIRS_BOSS, false))
						setTemps(0);

					//Fin de l'�clair
					if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TUNNEL, false))
						Manipulation.supprimer(this, avertissement); //Disparition de l'avertissement

					//Le boss va bouger
					if((getTempsTotal() - tpsD�butBoss)>TUNNEL && (getTempsTotal() - tpsD�butBoss)<FIN_TUNNEL) {
						//Si contre un mur
						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120) 
							bossVersDroite = !bossVersDroite; 

						if(bossVersDroite)
							s.bougerDroite(0.1);
						else
							s.bougerGauche(0.1);

						//Al�atoire toute les 0.125 secondes
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

					//Le boss arr�te de bouger (on le replace) [Pour l'attaque balles al�atoires et deuxi�me murs de balles]
					if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_TUNNEL, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, MUR_BALLES2, false)) {
						s.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);
						s.updateImagePos(); //Mise � jour de la position de l'image
					}

					//Lorsque le boss tire dans toutes les directions, il va bouger
					if((getTempsTotal() - tpsD�butBoss) > BOSS_ALEATOIRES_PLUS_PARTOUT && (getTempsTotal() - tpsD�butBoss) < MUR_BALLES2) {
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
							Manipulation.erreur("Le sprite du bonus de points n'a pas �t� trouv� !", e);
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

		if(isNiveauTermin�()) fin();
	}

	public void texteNiveau() throws FileNotFoundException {
		//On ne fait pas Manipulation.ajouterTexte car la il a un effet d'opacit�
		Texte niv = new Texte("NIVEAU V - FACE A FACE", "niv6");
		niv.setX(-10);
		niv.setY(NiveauInterface.HAUTEUR_ECRAN/2);
		niv.setFont(Font.loadFont(new FileInputStream("Polices/NIVEAU.ttf"), 30)); //Charge la police et la change
		niv.setFill(Paint.valueOf("RED")); //Couleur
		niv.setOpacity(0); //Il va appara�tre
		Manipulation.ajouterJeu(root, niv);
	}

	public void niveau() throws FileNotFoundException {
		if(Manipulation.siTpsA(getTempsTotal(), CHANGEMENT_FOND_NIVEAU, true)) 
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5.gif"))); //Changement du fond, fin de l'acc�l�ration

		// ============================ LE TEXTE DU DEBUT ============================= \\
		//Affiche le nom du niveau & Le fond change
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_NOM_NIVEAU, true))
			texteNiveau();

		//De l'affichage � la disparition
		if(getTempsTotal() > APPARITION_NOM_NIVEAU && getTempsTotal() < FIN_NOM_NIVEAU) {
			Manipulation.textes(root).stream().filter(e -> e.getID().equals("niv6")).forEach(texte -> {

				if (getTempsTotal() > APPARITION_NOM_NIVEAU && getTempsTotal() < NOM_NIVEAU_EN_APPARITION) //D�but � 2 seconde il monte en opacit� [2 � 4 il stagne au max]
					texte.setOpacity(getTempsTotal()/2);
				else if (getTempsTotal() > NOM_NIVEAU_EN_DISPARITION && getTempsTotal() < NOM_NIVEAU_EN_DISPARITION2) //4s � 7s il redescend en opacit�
					texte.setOpacity(4/(getTempsTotal()*1.25));
				else if (getTempsTotal() > NOM_NIVEAU_EN_DISPARITION2 && getTempsTotal() < FIN_NOM_NIVEAU) //7s � 9s il redescend encore plus
					texte.setOpacity(4/(getTempsTotal()*3.5));

				texte.updatePos(this, texte.getTranslateX() + 0.25, NiveauInterface.HAUTEUR_ECRAN/4);
			});
		}

		//Apr�s son mouvement on le supprime
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
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_2.gif"))); //Changement du fond, fin de l'acc�l�ration

			if(Manipulation.siTpsA(getTempsTotal(), APPARITION_2EMES_ENNEMIS+6, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_3.gif"))); //Changement du fond, fin de l'acc�l�ration

			if(Manipulation.siTpsA(getTempsTotal(), APPARITION_2EMES_ENNEMIS+9, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_4.gif"))); //Changement du fond, fin de l'acc�l�ration

			deuxiemesEnnemis();
		}

		// ============================ MINIBOSS (PHASE I) ============================= \\
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_MINIBOSS, false)) 
			//Le mini-boss appara�t
			miniBOSS = new MINIBOSS5((NiveauInterface.LARGEUR_DECOR/2)-70, -100, 100, 200, "miniboss", VIE_MINIBOSS, "Images/Niveau5/boss.png", new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this), this);
		
		//Il descend
		if(getTempsTotal() > APPARITION_MINIBOSS && getTempsTotal() < MINIBOSS_ATTAQUE)
			miniBOSS.bougerBas(0.25);

		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS_ATTAQUE, false)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_debut.gif"))); //Changement du fond, acc�l�ration au MAX
			vsMINIBOSS = true;
		}

		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS_PREPARATION, false))
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5.gif"))); //Fond normal

		if(Manipulation.siTpsA(getTempsTotal(),  MINIBOSS_2EME_ATTAQUE, false)) 
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_debut.gif"))); //Puis �a repart

		// ============================ FIN MINIBOSS (PHASE I) / APPARITION ENNEMIS ============================= \\
		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS_PART, false)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_3.gif"))); //Changement du fond, fin de l'acc�l�ration, c'est vitesse normale
			//Il l�che une vie
			new BonusVie(miniBOSS.getTranslateX()+10, miniBOSS.getTranslateY() + 7, 25, 25, this);
		}

		if(getTempsTotal() > APPARITION_ENNEMIS_APRES_MINIBOSS && getTempsTotal() < BALLES_ALEATOIRES_PARTOUT-2) {
			troisiemesEnnemis();

			if(getTempsTotal() > APPARITION_ENNEMIS2_APRES_MINIBOSS) { //+ Les premiers
				premiersEnnemis();
				vsMINIBOSS = false; //Il sera d�j� parti
			}
		}

		// ============================ BALLES ALEATOIRES DE TOUS LES COTES ============================= \\
		if(getTempsTotal() > BALLES_ALEATOIRES_PARTOUT && getTempsTotal() < APPARITION_ENNEMIS_APRES_BALLES-1)
			ballesAl�atoiresPartout();

		// ============================ MEMES ENNEMIS QUE LE DEBUT MAIS TIRENT PLUS DOUCEMENT MAIS + DE BALLES ============================= \\
		if(getTempsTotal() > APPARITION_ENNEMIS_APRES_BALLES && getTempsTotal() < APPARITION_MINIBOSS2 - 2) {
			vsMINIBOSS = false; //La phase I sera d�j� termin�e
			premiersEnnemis();
		}

		// ============================ MINIBOSS (PHASE II) ============================= \\
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_MINIBOSS2, true))
			//Le mini-boss r�appara�t (Il �tait mort, on le recr�e)
			miniBOSS = new MINIBOSS5((NiveauInterface.LARGEUR_DECOR/2)-70, -10, 100, 100, "miniboss", VIE_MINIBOSS, "Images/Niveau5/boss.png", new BarreDeVie(10,40,VIE_MINIBOSS,10, "vie", this), this);

		if(getTempsTotal() > APPARITION_MINIBOSS2 && getTempsTotal() < MINIBOSS2_ATTAQUE)
			miniBOSS.bougerBas(0.5);

		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS2_ATTAQUE, true)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_debut.gif"))); //Changement du fond, acc�l�ration au MAX
			vsMINIBOSS = true;
		}

		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS2_PREPARATION, true))
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5.gif"))); //Fond normal

		if(Manipulation.siTpsA(getTempsTotal(),  MINIBOSS2_2EME_ATTAQUE, true)) 
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_debut.gif"))); //Puis �a repart

		if(Manipulation.siTpsA(getTempsTotal(), MINIBOSS2_PART, true)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5.gif"))); //Le fond redevient lent
			//Il l�che une vie
			new BonusVie(miniBOSS.getTranslateX()+10, miniBOSS.getTranslateY() + 7, 25, 25, this);
		}

		// ============================ BALLES ALEATOIRES DU HAUT VERS LE BAS ============================= \\
		if(getTempsTotal() > BALLES_ALEATOIRES && getTempsTotal() < BALLES_VISEUSES - 3)
			ballesAl�atoiresHaut();

		// ============================ BALLES VISEUSES ============================= \\
		if(getTempsTotal() > BALLES_VISEUSES && getTempsTotal() < ECLAIRS-2) {
			ballesViseuses();

			//Elles se rajoutent
			if(getTempsTotal() > BALLES_VISEUSES_ET_ALEATOIRES)
				ballesAl�atoiresHaut();
		}

		// ============================ ECLAIRS ============================= \\
		if(Manipulation.siTpsA(getTempsTotal(), ECLAIRS, true))
			setTemps(0); //Pour bien commencer les �clairs (Ils sont en fonction du temps)

		if(getTempsTotal() > ECLAIRS && getTempsTotal() < APPARITION_VIES)
			�clairs();

		//Le fond devient sombre, on approche du boss
		if(Manipulation.siTpsA(getTempsTotal(), IMAGE_FOND_SOMBRE, false))
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_Niveau5_sombre.gif")));

		//Puis les balles al�atoires sortant du haut s'ajoutent
		if(getTempsTotal() > IMAGE_FOND_SOMBRE && getTempsTotal() <  AUTRE_IMAGE_FOND-1.5)
			ballesAl�atoiresHaut();

		//On met le fond du boss mais sombre, on est tr�s bient�t contre lui
		if(Manipulation.siTpsA(getTempsTotal(), AUTRE_IMAGE_FOND, false))
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_V/bg_avant_boss.gif")));

		//Les balles al�atoires partent et les balles viseuses apparaissent
		if(getTempsTotal() > AUTRE_IMAGE_FOND && getTempsTotal() < APPARITION_BOSS-1)
			ballesViseuses();

		// ============================ BOSS ============================= \\
		//Les quatre vies tombent
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION_VIES, false)) {
			//   **
			// *    *
			// *    * 8 vies tombent comme �a
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
	 * Les premiers ennemis (Deux lign�es une allant � gauche une autre � droite)
	 * 
	 * @author Jordan
	 */
	private void premiersEnnemis() {

		if(Manipulation.quatreFoisParSecondes(getTemps())) {
			//Lign�e vers droite
			new Ennemi(-10, 30, 40, 40, "Images/Niveau5/ennemi.png", "ennemi1g", this);
			//Lign�e vers gauche
			new Ennemi(NiveauInterface.LARGEUR_DECOR-50, 60, 40, 40, "Images/Niveau5/ennemi.png", "ennemi1d", this);
		}
	}

	/**
	 * Les deuxi�me ennemis (Tombants de haut al�atoirement)
	 * 
	 * @author Jordan
	 */
	private void deuxiemesEnnemis() {
		if(Manipulation.siTpsA(getTemps(), 0.66, true) || Manipulation.siTpsA(getTemps(), 1.32, true) || Manipulation.siTpsA(getTemps(), 1.98, true)) {
			new Ennemi(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -30, 40, 40, "Images/Niveau5/ennemi.png", "ennemi2", this);
		}
	}

	/**
	 * Les troisi�mes ennemis (Tombants de haut [Gauche & Droite])
	 * 
	 * @author Jordan
	 */
	private void troisiemesEnnemis() {
		if(Manipulation.quatreFoisParSecondes(getTemps())) {
			new Ennemi(10, -30, 40, 40, "Images/Niveau5/ennemi.png", "ennemi3g", this); //g comme � gauche
			new Ennemi(NiveauInterface.LARGEUR_DECOR-65, -30, 40, 40, "Images/Niveau5/ennemi.png", "ennemi3d", this);
		}
	}

	/**
	 * Des balles apparaissent al�atoirement des quatre c�t�s
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void ballesAl�atoiresPartout() throws FileNotFoundException {
		if(!vsBOSS) {
			if(Math.random()>0.98) {
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/ennemi balle.png", "balle al�atoire bas", this); //Sors du haut va vers le bas
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN, 20, 20, "Images/ennemi balle.png", "balle al�atoire haut", this); //Sors du bas va vers le haut
				new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/ennemi balle.png", "balle al�atoire droite", this); //Sors de la gauche va vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-55, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/ennemi balle.png", "balle al�atoire gauche", this); //Sors de la droite va vers la gauche
			}
		}else {
			//Le premier "balles al�atoires de partout" c'est les grosses flammes
			if((getTempsTotal() - tpsD�butBoss)<BALLES_TOMBENT) {
				if(Math.random()>0.985) {
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), -10, 50, 50, PATH_BALLES_FEU, "balle al�atoire bas", this); //Sors du haut va vers le bas
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), NiveauInterface.HAUTEUR_ECRAN, 50, 50, PATH_BALLES_FEU, "balle al�atoire haut", this); //Sors du bas va vers le haut
					new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 50, 50, PATH_BALLES_FEU, "balle al�atoire droite", this); //Sors de la gauche va vers la droite
					new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-100, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 50, 50, PATH_BALLES_FEU, "balle al�atoire gauche", this); //Sors de la droite va vers la gauche
				}
			}else { //Le deuxi�me c'est les petites o� elles peuvent bouger sur les c�t�s
				if(Math.random()>0.975) {
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), -10, 20, 20, PATH_BALLES_FEU, "balle al�atoire bas", this); //Sors du haut va vers le bas
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), NiveauInterface.HAUTEUR_ECRAN, 20, 20, PATH_BALLES_FEU, "balle al�atoire haut", this); //Sors du bas va vers le haut
					new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, PATH_BALLES_FEU, "balle al�atoire droite", this); //Sors de la gauche va vers la droite
					new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-100, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, PATH_BALLES_FEU, "balle al�atoire gauche", this); //Sors de la droite va vers la gauche
				}
			}
		}
	}

	/**
	 * Des balles apparaissent al�atoirement du haut
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void ballesAl�atoiresHaut() throws FileNotFoundException{
		if(!vsBOSS) {
			if(Math.random() > 0.95)
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/ennemi balle.png", "balle al�atoire bas", this); //X al�atoire
		}else { //Si c'est contre le boss elles apparaissent + souvent
			if(Math.random() > 0.9)
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, PATH_BALLES_FEU, "balle al�atoire bas", this); //X al�atoire
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
				//joueur.get..... car on r�cup�re l'emplacement du joueur
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
	 * Avertissement pour dire o� va appara�tre l'�clair et il appara�t apr�s tr�s rapidement
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void �clairs() throws FileNotFoundException{
		double random = Math.random();

		if((Manipulation.siTpsA(getTemps(), 0.05, false)) || (Manipulation.siTpsA(getTemps(), 1.05, false))) {
			avertissement.setFill(Color.RED); //Avertissement rouge
			avertissement.setOpacity(0); //Au d�but invisible
			Manipulation.ajouterJeu(root, avertissement);

			//Quel endroit ? (33% chacun)
			if(random < 0.33)
				endroit = 0; //0: Tout � gauche
			else if(random > 0.34 && random < 0.67)
				endroit = 1; //1: Au milieu
			else
				endroit = 2; //2: Tout � d*roite

			//On met � l'endroit choisi au hasard
			if(endroit==0)
				avertissement.relocate(0, 0);
			else if (endroit==1)
				avertissement.relocate(tiers_�cran, 0);
			else
				avertissement.relocate((tiers_�cran*2)-15, 0);

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

		//L'�clair apparait!
		if((Manipulation.siTpsA(getTemps(), 0.91, false)) || (Manipulation.siTpsA(getTemps(), 1.91, false)))
			new EnnemiBalle(endroit*tiers_�cran, avertissement.getTranslateY(), tiers_�cran, NiveauInterface.HAUTEUR_ECRAN, "Images/�clair.png", "balle �clair", this); //Va vers le joueur par la droite
	}

	@Override
	public void bossTir() throws FileNotFoundException {
		// ====================== BALLES ALEATOIRES DU HAUT ===================== \\
		if((getTempsTotal() - tpsD�butBoss)>DEBUT && (getTempsTotal() - tpsD�butBoss)<FIN_DEBUT) {
			ballesAl�atoiresHaut();
		}

		// ====================== MUR DE BALLES (Plus serr� que quand c'�tait le miniboss) ===================== \\
		if((getTempsTotal() - tpsD�butBoss)>MUR_BALLES && (getTempsTotal() - tpsD�butBoss)<FIN_MUR_BALLES) {
			if(Manipulation.deuxFoisParSecondes(getTemps())){
				//Pas le m�me �cart sinon �a sera toujours le m�me mur avec les m�mes trous 
				int ecartBalles = (int) (Math.random() * 70)+70; //+70 �vite le ecartBalles = 0

				//Cr�ation de la ligne de balles
				for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
					new BossBalle(i, -10, PATH_BALLES_FEU, "miniboss balle descente", this); //miniboss car elles sont + lentes 
					new BossBalle(i, -40, PATH_BALLES_FEU, "miniboss balle descente", this);
				}
			}
		}

		// ====================== ECLAIRS ===================== \\
		if((getTempsTotal() - tpsD�butBoss)>ECLAIRS_BOSS && (getTempsTotal() - tpsD�butBoss)<FIN_ECLAIRS_BOSS) {
			�clairs();
		}

		// ====================== TUNNEL ===================== \\
		if((getTempsTotal() - tpsD�butBoss)>TUNNEL && (getTempsTotal() - tpsD�butBoss)<FIN_TUNNEL) {
			//Par d�faut voil� la largeur du tunnel
			double maxGauche = 50;
			double maxDroite = 120;

			//Avec les balles c'est plus facile
			if((getTempsTotal() - tpsD�butBoss)>TUNNEL_BALLES) {
				maxGauche = 100;
				maxDroite = 170;
			}

			//L'�cart entre les balles en largeur afin que cela ait une apparance de mur
			int ecartMur = NiveauInterface.TAILLE_BALLE_BOSS; 

			//Mettre cette limite sert � faire en sorte que pour le tunnel, le mur de droite ne soit pas sur le tableau
			int limiteBalle = NiveauInterface.LARGEUR_DECOR - 192;

			double toutAGauche = boss.getTranslateX()-maxGauche; //La balle la plus � gauche du BOSS
			double toutADroite;

			//Si le boss n'est pas proche du tableau qui va faire spawn des balles sur le tableau..
			if(boss.getTranslateX() <= limiteBalle)
				toutADroite = boss.getTranslateX()+maxDroite; //..tout se passe normal
			else
				toutADroite = NiveauInterface.LARGEUR_DECOR-35; //Sinon la limite c'est la fin du d�cor

			//Cr�ation des balles tout � gauche et droite
			new BossBalle(toutAGauche, boss.getTranslateY()+maxDroite, PATH_BALLES_FEU, "balle tunnel", this);

			if(boss.getTranslateX() <= limiteBalle) //Si le boss pas trop proche du tableau
				new BossBalle(toutADroite, boss.getTranslateY()+maxDroite, PATH_BALLES_FEU, "balle tunnel", this);

			//Cr�ation d'un mur de balles du milieu � la toute gauche [1er tunnel gauche]
			for(int i=0;i<toutAGauche;i+=ecartMur) {
				new BossBalle(i, boss.getTranslateY()+maxDroite, PATH_BALLES_FEU, "balle tunnel", this);
			}

			//De m�me pour le mur de balles du milieu � la toute droite [2�me tunnel gauche] si le boss n'est pas proche du tableau
			if(boss.getTranslateX() <= limiteBalle) {
				for(int i=NiveauInterface.LARGEUR_DECOR-35; i>toutADroite;i-=ecartMur) {
					new BossBalle(i, boss.getTranslateY()+maxDroite,  PATH_BALLES_FEU, "balle tunnel", this);
				}
			}

			// + balles al�atoires
			if((getTempsTotal() - tpsD�butBoss) > TUNNEL_BALLES) {
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
		if((getTempsTotal() - tpsD�butBoss)>BOSS_BALLES_ALEATOIRES && (getTempsTotal() - tpsD�butBoss)<FIN_BOSS_ALEATOIRES_PLUS_PARTOUT) {
			ballesAl�atoiresPartout();

			if((getTempsTotal() - tpsD�butBoss)>BOSS_ALEATOIRES_PLUS_PARTOUT){
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
		if((getTempsTotal() - tpsD�butBoss)>BALLE_FONCE && (getTempsTotal() - tpsD�butBoss)<VISEUSES) {
			if(Manipulation.quatreFoisParSecondes(getTemps()))
				new BossBalle(joueur.getTranslateX(), -10, PATH_BALLES_FEU, "balle fonce", this);
		}

		// ====================== BALLES VISEUSES ===================== \\
		if((getTempsTotal() - tpsD�butBoss)>VISEUSES && (getTempsTotal() - tpsD�butBoss)<MUR_BALLES2) {
			ballesViseuses();
		}

		// ====================== MUR DE BALLES (PLUS RAPIDE) ===================== \\
		if((getTempsTotal() - tpsD�butBoss)>MUR_BALLES2 && (getTempsTotal() - tpsD�butBoss)<BALLES_TOMBENT) {
			if(Manipulation.deuxFoisParSecondes(getTemps())) {
				//Pas le m�me �cart sinon �a sera toujours le m�me mur avec les m�mes trous 
				int ecartBalles = (int) (Math.random() * 80)+80; //+70 �vite le ecartBalles = 0
				//Cr�ation de la ligne de balles
				for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
					new BossBalle(i, -10, PATH_BALLES_FEU, "boss balle descente", this);
					new BossBalle(i, -40, PATH_BALLES_FEU, "boss balle descente", this);
				}
			}
		}

		// ====================== BALLES ALEATOIRES DU HAUT + MOUVEMENT SUR LES COTES ===================== \\
		if((getTempsTotal() - tpsD�butBoss)>BALLES_TOMBENT && (getTempsTotal() - tpsD�butBoss)<BALLES_TOMBENT_PARTOUT){
			ballesAl�atoiresHaut();
		}

		// ====================== BALLES ALEATOIRES DE PARTOUT + MOUVEMENT SUR LES COTES ===================== \\
		if((getTempsTotal() - tpsD�butBoss)>BALLES_TOMBENT_PARTOUT && (getTempsTotal() - tpsD�butBoss) < FIN_BALLES_TOMBENT_PARTOUT){
			ballesAl�atoiresPartout();
		}

		// ====================== MUR DE BALLES VERTICAL ===================== \\
		if((getTempsTotal() - tpsD�butBoss)>MUR_BALLES_V && (getTempsTotal() - tpsD�butBoss)<ECLAIRS_BALLES) {
			//Pas le m�me �cart sinon �a sera toujours le m�me mur avec les m�mes trous 
			int ecartBalles = (int) (Math.random() * 100)+100; //+90 �vite le ecartBalles = 0

			if(Manipulation.siTpsA(getTemps(), 0.98, false)) {
				//Cr�ation de la ligne de balles (De gauche � droite)
				for(int i = -ecartBalles+50; i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2; i+=ecartBalles) {
					new BossBalle(10, i, PATH_BALLES_FEU, "balle mur vertical g", this); 
					new BossBalle(-20, i, PATH_BALLES_FEU, "balle mur vertical g", this); 
				}
			}

			if(Manipulation.siTpsA(getTemps(), 1.98, false)) {
				//Et la ligne de droite � gauche
				for(int i = (-ecartBalles); i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2+10; i+=ecartBalles) {
					new BossBalle(NiveauInterface.LARGEUR_DECOR-70, i, PATH_BALLES_FEU, "balle mur vertical d", this); 
					new BossBalle(NiveauInterface.LARGEUR_DECOR-40, i, PATH_BALLES_FEU, "balle mur vertical d", this);
				}
			}

			// PLUS MUR DE BALLES NORMAL (CE QUI FAIT PARTOUT)
			if((getTempsTotal() - tpsD�butBoss)>MUR_BALLES_PARTOUT) {
				if(Manipulation.siTpsA(getTemps(), 1, false)){
					//Cr�ation de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, PATH_BALLES_FEU, "boss balle descente", this); 
						new BossBalle(i, -40, PATH_BALLES_FEU, "boss balle descente", this);
					}
				}
			}
		}

		// ====================== ECLAIR + BALLES ALEATOIRES ===================== \\
		if((getTempsTotal() - tpsD�butBoss)>ECLAIRS_BALLES && (getTempsTotal() - tpsD�butBoss)<DERNIER) {
			�clairs();
			ballesAl�atoiresHaut();
		}

		if((getTempsTotal() - tpsD�butBoss)>DERNIER) {
			if(Manipulation.siTpsA(getTemps(), 0.66, false) || Manipulation.siTpsA(getTemps(), 1.32, false) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
				//Pas le m�me �cart sinon �a sera toujours le m�me mur avec les m�mes trous 
				int ecartBalles = (int) (Math.random() * 70)+70; //+70 �vite le ecartBalles = 0
				//Cr�ation de la ligne de balles
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
		int difficult� = 0;

		switch(Main.getDifficult�()) {
		case FACILE:
			difficult� = 2;
			break;

		case NORMAL:
			difficult� = 4;
			break;

		case DIFFICILE:
			difficult� = 6;
			break;

		case FRAGILE_COMME_DU_VERRE:
			difficult� = 20;
			break;
		}

		//Le score final
		double scoreFinal = (getScore() * (double)(Main.getVies()/2)+1 * joueur.getPuissance()) * difficult�;

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

		if(Manipulation.siTpsA((getTempsTotal()-getTpsD�bScore()), 2, false)){
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_SCORE, "SCORE -- " + getScore(), "Polices/SCORE.ttf", 40, "YELLOW");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsD�bScore()), 4, false)){
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_VIES, "VIES RESTANTES -- " + Main.getVies(), "Polices/SCORE.ttf", 40, "YELLOW");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsD�bScore()), 6, false)){
			if(Main.getDifficult�() == Difficult�.FRAGILE_COMME_DU_VERRE)
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, 400, "DIFFICULTE -- MAX", "Polices/SCORE.ttf", 40, "RED");
			else
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, 400, "DIFFICULTE -- " + Main.getDifficult�().toString(), "Polices/SCORE.ttf", 40, "YELLOW");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsD�bScore()), 8, false)){
			if(joueur.getPuissance() < NiveauInterface.PUISSANCE_MAX)
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_PUISSANCE, "PUISSANCE -- " + joueur.getPuissance(), "Polices/SCORE.ttf", 40, "YELLOW");
			else
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_PUISSANCE, "PUISSANCE -- MAX", "Polices/SCORE.ttf", 40, "RED");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsD�bScore()), 12, false)){
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_SCORE_FINAL, "SCORE FINAL -- " + scoreFinal, "Polices/SCORE.ttf", 40, "RED");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsD�bScore()), 13, false)){
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_RANG, "RANG -- " + rang, "Polices/SCORE.ttf", 60, "PURPLE");
		}else if(Manipulation.siTpsA((getTempsTotal()-getTpsD�bScore()), 16, false)){
			Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS+10, NiveauInterface.POSITION_Y_STATS_QUITTER, "APPUYEZ SUR ESPACE POUR QUITTER", "Polices/SCORE.ttf", 30, "ORANGE");
			setPeutQuitter(true);
		}
	}

	@Override
	public void start(Stage NIV5) throws Exception {
		Scene niveau_V = new Scene(createNiveau(NIV5)); //Cr�ation du niveau

		NIV5.setScene(niveau_V); //La sc�ne du niveau devient la sc�ne principale
		NIV5.setTitle("NIVEAU V"); //Nom de la fen�tre
		NIV5.setResizable(false); //On ne peut pas redimensionner la fen�tre

		//On remet l'ic�ne du jeu comme elle a chang�e car c'est une autre f�netre
		NIV5.getIcons().add(new Image("file:Images/Icone.png"));

		Manipulation.updateScore(this); //Met � jour le score pour le d�but du niveau

		NIV5.show(); //Affichage du niveau

		/*On g�re les boutons tap�s (Contr�les du jeu) 
		 *[Quand on APPUIE sur la touche]
		 */
		niveau_V.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(!isEnDialogue()) { //Quand c'est en dialogue, le joueur ne joue pas
					switch (event.getCode()) { //R�cup�ration de quelle touche appuy�e
					case UP:    setBougeHaut(true); break;
					case DOWN:  setBougeBas(true); break;
					case LEFT:  setBougeGauche(true); break;
					case RIGHT: setBougeDroite(true); break;
					case SPACE: if(isPeutTirer()) setTir�(true); break;
					case SHIFT: setFocus(true); break;
					default:
						break;
					}
				}if(isPeutQuitter()) { //A la fin du niveau
					switch(event.getCode()) {
					case SPACE:
						NIV5.close();
						mediaPlayer.stop();
						
						//Niveau FINAL d�bloqu�
						try {
							Main.sauvegarde(6);
						} catch (IOException e) {
							System.err.println("Erreur lors de la sauvegarde de l'�tat 6 !");
						}
						
						Alert alerte = new Alert(AlertType.INFORMATION); 
						alerte.setTitle("NIVEAU V TERMINE !"); //Le titre de la f�netre
						alerte.setHeaderText("Vous avez r�ussi le niveau V ! ZEROD VOUS ATTEND."); //Au dessus du texte
						alerte.setContentText("SAUVEGARDE REUSSIE. REDEMARREZ LE JEU."); //Le texte

						alerte.showAndWait(); //On la montre et elle est modale (On peut rien faire tant qu'elle n'est pas ferm�e
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
					switch (event.getCode()) { //R�cup�ration de quelle touche appuy�e
					case UP:    setBougeHaut(false); break;
					case DOWN:  setBougeBas(false); break;
					case LEFT:  setBougeGauche(false); break;
					case RIGHT: setBougeDroite(false); break;
					case SPACE: setTir�(false); break;
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
							setNbEspaceAppuy�s(getNbEspaceAppuy�s()+1);
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
		root.setPrefSize(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); //Met � jour les dimensions du jeu

		// ========================= Image de fond =========================
		try {
			imageFond = new ImageView(new Image(new FileInputStream("Fonds/Niveau_V/bg_Niveau5_debut.gif")));
			imageFond.setX(-15); 
			imageFond.setY(0);
			imageFond.setFitHeight(NiveauInterface.HAUTEUR_ECRAN+15); //Change la taille
			imageFond.setFitWidth(NiveauInterface.LARGEUR_ECRAN); 
			root.getChildren().add(imageFond);
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image n'a pas �t� trouv� !", e);	
		} 

		Manipulation.ajouterImage(root,"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

		//Le joueur et sa grazebox
		grazeBox = new Joueur(295,695,NiveauInterface.LARGEUR_JOUEUR,NiveauInterface.HAUTEUR_JOUEUR, "grazebox", "Images/Transparent.png", this);
		joueur = new Joueur(300,700,NiveauInterface.LARGEUR_GRAZEBOX,NiveauInterface.HAUTEUR_GRAZEBOX, "joueur", "Images/Joueur/joueur.png", this);

		AnimationTimer timer = new AnimationTimer() { //Le timer qui va g�rer l'animation, etc.

			/*
			 * @brief Li� au timer
			 *        Va g�rer tout ce que doit faire le joueur quand il appuie sur les touches
			 */
			public void handle(long arg0) {
				if (isBougeHaut()) { //Il a appuy� sur la fl�che du HAUT
					joueur.bougerHaut(1); 
					grazeBox.bougerHaut(1); //Sa hitbox va le suivre de partout
				}
				if (isBougeBas()) { //Fl�che du BAS 
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

				//Si le joueur tire et que le  getTemps() d'attente apr�s le tir d'une balle est fini
				if (isTir�() && !isCooldownTir()) {
					joueur.tir("joueur bullet"); //Il tire
					setTpsD�bTir(getTempsTotal());  //Le temps du d�but est enregistr� pour calculer le cooldown
					setCooldownTir(true); //Il passe en cooldown
				}

				//Joueur a perdu
				if(Main.getVies() == 0) {
					if(!isLockGameOver()) { //L'action doit se faire 1 fois
						setLockGameOver(true) ;
						niveau.close(); //Fermeture fen�tre
						mediaPlayer.stop(); //Fin de la musique du Niveau 1
						Main.gameOver(); 
					}
				}

				try {
					update();
				} catch (FileNotFoundException e) {
					Manipulation.erreur("Un(e) sprite/image n'a pas pu �tre trouv�e !", e);
				}
			}

		}; //Fin de la m�thode handle

		//On lance le timer
		timer.start();

		//On retourne donc l'objet qui va nous permettre d'afficher des choses
		return root;
	}

	/**
	 * Tir en spirale (3 balles � la fois toutes les 0.2s)
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
				//Pas le m�me �cart sinon �a sera toujours le m�me mur avec les m�mes trous 
				int ecartBalles = (int) (Math.random() * 75)+75; //+100 �vite le ecartBalles = 0

				//Cr�ation de la ligne de balles
				for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
					new BossBalle(i, -10, PATH_BALLES_FEU, "miniboss balle descente", this); //X al�atoire
					new BossBalle(i, -40, PATH_BALLES_FEU, "miniboss balle descente", this); //X al�atoire
				}
			}
		}
		//============================= TUNNEL ==============================\\
		else if(getTempsTotal() > MINIBOSS_2EME_ATTAQUE && getTempsTotal() < MINIBOSS2_PREPARATION) {
			//L'�cart entre les balles en largeur afin que cela ait une apparance de mur
			int ecartMur = NiveauInterface.TAILLE_BALLE_BOSS; 

			//Mettre cette limite sert � faire en sorte que pour le tunnel, le mur de droite ne soit pas sur le tableau
			int limiteBalle = NiveauInterface.LARGEUR_DECOR - 192;

			double toutAGauche = miniBOSS.getTranslateX()-50; //La balle la plus � gauche du BOSS
			double toutADroite;

			//Si le boss n'est pas proche du tableau qui va faire spawn des balles sur le tableau..
			if(miniBOSS.getTranslateX() <= limiteBalle)
				toutADroite = miniBOSS.getTranslateX()+120; //..tout se passe normal
			else
				toutADroite = NiveauInterface.LARGEUR_DECOR-35; //Sinon la limite c'est la fin du d�cor

			//Cr�ation des balles tout � gauche et droite
			new BossBalle(toutAGauche, miniBOSS.getTranslateY()+120, PATH_BALLES_FEU, "balle tunnel", this);

			if(miniBOSS.getTranslateX() <= limiteBalle) //Si le boss pas trop proche du tableau
				new BossBalle(toutADroite, miniBOSS.getTranslateY()+120, PATH_BALLES_FEU, "balle tunnel", this);

			//Cr�ation d'un mur de balles du milieu � la toute gauche [1er tunnel gauche]
			for(int i=0;i<toutAGauche;i+=ecartMur) {
				new BossBalle(i, miniBOSS.getTranslateY()+120, PATH_BALLES_FEU, "balle tunnel", this);
			}

			//De m�me pour le mur de balles du milieu � la toute droite [2�me tunnel gauche] si le boss n'est pas proche du tableau
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
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //R�p�te a l'infini

		vsBOSS = true;
		tpsD�butBoss = getTempsTotal(); //On enregistre le temps de d�but du combat

		//On ne fait pas Manipulation.ajouterTexte car on doit r�cup�rer ce nom pour pas qu'il soit supprim� lors de updateScore()
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
		//Dialogue avant le d�but du boss
		if(!bossCommenc�) {
			switch(getNbEspaceAppuy�s()) {
			case 0:
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Col�re.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/Niveau5/Dialogue/boss.png", NiveauInterface.LARGEUR_DECOR-395, 250, 410, 580);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu es tres coriace toi !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1: 
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu ne devrais pas me sous-estimer.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "hmhmhm... Tu es interessant.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ton periple s'arrete maintenant.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;
			}

			if(getNbEspaceAppuy�s() > 3) { //Fin du dialogue
				boss();	 
				bossCommenc� = true;
				setNbEspaceAppuy�s(0); //Remise � 0 pour le dialogue de fin
			}
		}

		//Dialogue apr�s le boss
		if(bossBattu) {
			switch(getNbEspaceAppuy�s()) {
			case 0:
				//Toutes les balles ennemies meurent
				Manipulation.sprites(root).stream().filter(e -> e.getType().contains("balle")).forEach(balle -> {
					balle.meurs();
				});

				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Col�re.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/Niveau5/Dialogue/boss.png", NiveauInterface.LARGEUR_DECOR-395, 250, 410, 580);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Arrgghh.... Je...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1: 
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Silence, traitre.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Finissons-en. Ou est Zerod ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 4:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "En face. Tu ne feras pas le poids.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 5:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...Ne cherche pas. Et rends-toi.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 6:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ahahah.. Tu m'avais dit la meme chose.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 7:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourtant, te voici vaincu.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 8:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 9:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Refuses-tu toujours de ne pas dire...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 10:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...Les raisons de ta trahison ?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 11:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...Je trouve aussi que ce monde...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 12:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "n'est pas equitable.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 13:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Nous sommes beaucoup plus puissants.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 14:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Leur crainte est comprehensible.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 15:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "En tant que sous-chef tu aurais pu...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 16:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...stopper tout ca.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;	

			case 17:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "La guerre avait deja commencee.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 18:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C'est vrai, pas de retour arriere...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 19:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "APRES LA MORT DE MON PERE !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 20:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Salar l'acheva.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;
			}

			if(getNbEspaceAppuy�s() > 20) { //Fin du dialogue
				setEnDialogue(false);
				setNiveauTermin�(true);
				setTpsD�bScore(getTempsTotal());
			}
		}
	}
}
