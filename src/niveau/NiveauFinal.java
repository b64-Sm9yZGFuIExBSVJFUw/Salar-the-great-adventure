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
import personnage.BOSSFINAL;
import personnage.BarreDeVie;
import personnage.Joueur;
import texte.Texte;

public class NiveauFinal extends Niveau {
	//===================== TIMING DU NIVEAU EN SECONDES ===================\\
	private static final double CREATION_BOSS = 0.2; //Le boss SE cr�e
	private static final double BOSS_CREE = 1; //Le boss a �t� cr�e
	private static final double FIN_DESCENTE_BOSS = 8; //Le boss arr�te de descendre
	private static final double TEMPS_TRANSITION = 3; //Temps de la transition

	//===================== TIMING DU BOSS EN SECONDES [PHASE I] ===================\\
	private static final double DIALOGUE_1 = 0; //Dialogue 1
	private static final double DIALOGUE_2 = 3; //Dialogue 2
	private static final double DEBUT = 6; //Commencement
	private static final double FIN_DEBUT = 9; //Fin de la premi�re attaque
	private static final double TRANSITION_DEBUT = 10; //Transition
	private static final double TIR_PARTOUT = 11; //Il tire de partout
	private static final double FIN_TIR = 19.5; //Fin de la deuxi�me attaque
	private static final double TRANSITION_TIR = 20; //Transition
	private static final double BALLES_GAUCHE_DROITE = 21.5; //Balles al�atoires sortant de gauche/droite
	private static final double FIN_BALLES = 25.5; //Fin de l'attaque
	private static final double TRANSITION_BALLES = 25.5; //Transition
	private static final double TIR_PARTOUT2 = 26.5; //Tire de partout une fois de plus 
	private static final double FIN_TIR2 = 31; //Tire de partout une fois de plus 
	private static final double TRANSITION_TIR2 = 30.5; //Transition
	private static final double SPIRALE = 31.25; //Attaque en spirale avec balles al�atoires gauche & droite
	private static final double FIN_SPIRALE = 39; //Fin de l'attaque
	private static final double MUR = 40; //Des murs de balles verticaux
	private static final double FIN_MUR = 42; //Fin de l'attaque
	private static final double TRANSITION_MUR = 41; //Transition
	private static final double TUNNEL = 42; //Tunnel
	private static final double TUNNEL_LENT = 52; //Tunnel mais ralenti
	private static final double FIN_TUNNEL = 55; //Fin du tunnel
	private static final double TRANSITION_TUNNEL = 55; //Transition
	private static final double BALLES_PARTOUT = 56.5; //Des balles al�atoires sortant de partout
	private static final double PLUS_PARTOUT = 67; //En plus le boss va tirer dans toutes les directions
	private static final double FIN_PARTOUT = 74.5; //Fin de l'attaque
	private static final double TRANSITION_PARTOUT = 76; //Transition
	private static final double GAUCHE_DROITE = 77; //Mur de balles o� le joueur devra faire Gauche/Droite rapidement
	private static final double FIN_GAUCHE_DROITE = 86; //Fin de l'attaque
	private static final double TUNNEL_LENT2 = 87; //Tunnel mais ralenti
	private static final double FIN_TUNNEL2 = 95; //Tunnel mais ralenti
	private static final double TRANSITION_TUNNEL2 = 96.5; //Transition
	private static final double RIEN = 98; //Il ne se passe rien...
	private static final double DEBUT2 = 102; //Balles tombent de haut plus vite
	private static final double FIN_DEBUT2 = 105; //Fin de l'attaque
	private static final double TRANSITION_DEBUT2 = 106.5; //Transition
	private static final double TUNNEL3 = 107.5; //Tunnel
	private static final double FIN_TUNNEL3 = 117; //Fin tunnel
	private static final double TRANSITION_TUNNEL3 = 116; //Transition
	private static final double BALLES_PARTOUT2 = 118; //Balles al�atoires de tous les c�t�s
	private static final double FIN_PARTOUT2 = 124.5; //Balles tombent de haut plus vite
	private static final double TIR_PARTOUT3 = 128; //Tire de partout
	private static final double FIN_PARTOUT3 = 136.5; //Fin de l'attaque
	private static final double TRANSITION_PARTOUT2 = 137.5; //Transition
	private static final double TUNNEL4 = 138; //Tunnel
	private static final double TUNNEL_RAPIDE = 148.25; //Tunnel plus rapide
	private static final double TUNNEL_LENT3 = 159; //Tunnel plus lent
	private static final double FIN_TUNNEL4 = 167; //Fin du tunnel
	private static final double TRANSITION_TUNNEL4 = 168.25; //Transition
	private static final double GAUCHE_DROITE2 = 169.25; //Gauche & Droite rapide
	private static final double FIN_GAUCHE_DROITE2 = 183; //Fin de l'attaque
	private static final double DIALOGUE_3 = 184; //Dialogue 3
	private static final double DIALOGUE_4 = 187; //Dialogue 4
	private static final double DIALOGUE_5 = 191; //Dialogue 5
	private static final double TRANSITION_DIALOGUE = 193.5;
	private static final double DEBUT3 = 194.5; //Balles tombent de haut tr�s vite
	private static final double FIN_DEBUT3 = 198.5; //Fin de l'attaque
	private static final double BALLES_PARTOUT3 = 199.5; //Balles al�atoires de tous les c�t�s
	private static final double TREMBLE = 205; //Zerod tremble
	private static final double STOP_TIR = 206.5; //Il ne tire plus
	private static final double STOP = 207; //Il ne tire plus et tremble �norm�ment
	private static final double FIN = 210; //Fin
	private static final double DIALOGUE_FIN = 212; //Dialogue apr�s l'armure cass�e de Zerod

	//===================== TIMING DU BOSS EN SECONDES [PHASE II] ===================\\
	private static final double APPARITION_NOM_NIVEAU = 0.1; //Le nom du niveau s'affiche
	private static final double NOM_NIVEAU_EN_APPARITION = 2; //Le nom du niveau appara�t
	private static final double NOM_NIVEAU_EN_DISPARITION = 4; //Le nom du niveau dispara�t
	private static final double NOM_NIVEAU_EN_DISPARITION2 = 6; //Le nom du niveau dispara�t plus vite
	private static final double FIN_NOM_NIVEAU = 7.5; //Le nom du niveau a disparu
	private static final double DIALOGUE_6 = 7.75; //Zerod parle
	private static final double FIN_DIALOGUE = 9; //Zerod parle plus
	private static final double COMMENCE = 9.5; //Zerod attaque
	private static final double RALENTI = 13.75; //Le tunnel ralentie
	private static final double FIN_RALENTI = 14.75; //Fin du ralenti
	private static final double FIN_COMMENCE = 19.25; //Le tunnel termine
	private static final double MUR_BALLE = 19.5; //Le tunnel termine
	private static final double FIN_MUR_BALLE = 28; //L'attaque finie
	private static final double RECOMMENCE = 30; //Le tunnel revient
	private static final double RERALENTI = 34; //Le tunnel reralentie
	private static final double FIN_RERALENTI = 35; //Le tunnel rerepart
	private static final double FIN_RECOMMENCE = 39; //Fin du tunnel
	private static final double ECLAIRS = 40; //Eclairs & balles al�atoires partout
	private static final double FIN_ECLAIRS = 48; //Fin
	private static final double BALLES_TOMBENT = 50.75; //Balles al�atoires de haut vitesse al�atoire 
	private static final double FIN_BALLES_TOMBENT = 56.25; //Fin
	private static final double FONCE = 58.25; //Balles qui foncent
	private static final double FIN_FONCE = 66; //Fin et balles al�atoires de bas vitesse al�atoire
	private static final double BALLES_ALEATOIRES_HAUT_BAS = 73.75; //Plus balles al�atoires de haut
	private static final double GROSSES = 81.5; //Des grosses appara�ssent
	private static final double TREMBLES = 89; //Et tremblent
	private static final double FIN_TREMBLES = 95; //Et tremblent
	private static final double DIALOGUE_7 = 97; //Zerod parle
	private static final double FIN_DIALOGUE_7 = 98; //Fin dialogue
	private static final double RETOUR = 99; //Retour en force w/Mur de balles
	private static final double FIN_RETOUR = 118.5; //Fin du mur de balles
	private static final double RETOUR_TUNNEL = 119.5; //Tunnel rapide
	private static final double PART = 137; //Zerod part
	private static final double ECLAIR = 139; //Que des �clairs
	private static final double PLUS_BALLE = 147.5; //Plus balles al�atoires de haut avec vitesse al�atoire
	private static final double FIN_ECLAIR = 154.5; //Fin
	private static final double HORIZONTAUX = 155.5; //Eclairs horizontaux
	private static final double PLUS_ALEATOIRE = 161; //Plus des balles al�atoires de partout
	private static final double FIN_ALEATOIRE = 169; //Fin des balles al�atoires de partout
	private static final double ALEATOIRES = 186; //Balles al�atoires de haut & mur de balles
	private static final double MURS = 200.5; //Murs de balles venant de haut et bas
	private static final double MUR_PARTOUT = 217; //Murs de balles venant de partout
	private static final double FIN_MURS = 233; //Fin
	private static final double REVIENT = 234; //Zerod redescend 
	private static final double RETOUR2 = 235; //Tunnel
	private static final double FIN_RETOUR2 = 253.5; //Fin tunnel
	private static final double DIALOGUE_8 = 255.5; //Zerod parle
	private static final double DERNIER_TUNNEL = 256.5; //Retour du tunnel
	private static final double FIN_DERNIER_TUNNEL = 279; //Fin
	private static final double DIALOGUE_9 = 281; //Dialogue
	private static final double DIALOGUE_10 = 284; //Dialogue
	private static final double DIALOGUE_11 = 287; //Dialogue
	private static final double DIALOGUE_12 = 290; //Dialogue
	private static final double DIALOGUE_13 = 293; //Dialogue
	private static final double DERNIERE_ATTAQUE = 296; //Fin, derni�re attaque

	private static final int VIE_BOSS = 565; //Vie du boss
	public static double DEGAT_BALLE_JOUEUR = 1; //Les d�g�ts que va affliger le joueur (Phase II)

	//Il faut combien de points pour avoir le rang ?
	private static final int RANG_SALAR = 24280;
	private static final int RANG_S = 24000;
	private static final int RANG_A = 20000;
	private static final int RANG_B = 15000;

	// ================================== Manipulations pour charger le son  ==================================
	private static Media son = new Media(new File("Sons/niveauFINAL/debut.mp3").toURI().toString());
	private static MediaPlayer mediaPlayer = new MediaPlayer(son);

	// ================================== Objets qui vont permettre l'affichage: Tout ce qu'on ajoute dedans sera affich� ==================================
	private static ImageView imageFond = new ImageView();

	// ================================== Variables qui g�rent le boss ==================================
	private static BOSSFINAL boss = null; //Le BOSS
	private static boolean phaseI = false; //Actuellement contre la phase I?
	private static boolean phaseII = false; //Actuellement contre la phase II? (Derni�re)
	private static boolean commenc� = false; //Le combat a-t-il commenc� ? (Contre la phase II)
	private boolean bossVersDroite = false; //Le boss bouge-t-il vers la droite ? (Pour g�rer son mouvement)
	private static String nomBOSS = "ZEROD";
	private double tpsD�butBoss = 0; //A quel moment le combat contre le boss commence ?
	private boolean lockFinDialogue = false;
	private double vieActuelle = VIE_BOSS; //Vie du boss
	private boolean mort = false; //Mort du boss
	private boolean bossPr�sent = false; //Le boss est-t-il ici ?
	private boolean finDialogue = false; //Le dialogue entre les deux phases est-t-il termin� ?

	// ================================== Variables qui g�rent l'attaque des �clairs ==================================
	private int tiers_�cran = 200; //Le tiers d'un �cran en largeur [L'�cran du jeu fait 600px]
	private int tiers_�cran2 = 266; //Le tiers d'un �cran en hauteur [L'�cran du jeu fait 800px]
	private Rectangle avertissement = new Rectangle(tiers_�cran,NiveauInterface.HAUTEUR_ECRAN+10); //La zone rouge qui averti l'emplacement de l'�clair
	private Rectangle avertissement2 = new Rectangle(NiveauInterface.LARGEUR_DECOR-15, tiers_�cran2); //La zone rouge qui averti l'emplacement de l'�clair
	private int endroit; //L'endroit al�atoire o� va appara�tre l'�clair

	// ================================== Variables qui g�rent la transition ==================================
	private boolean enTransition = false; //En transition de la fin du dialogue vers le combat
	private double tpsD�butTransition = 999;

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

		//Le boss perd de la vie
		if(phaseI && (getTempsTotal() - tpsD�butBoss)>DEBUT)
			boss.perdreVie(0.0125);

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

				//N: Nord, O: ouest...
			case "balle N":
				if((getTempsTotal() - tpsD�butBoss) > PLUS_PARTOUT) //Quand il tire en spirale
					s.bougerHaut(0.3);
				else
					s.bougerHaut(0.15);
				break;


			case "balle NO":
				if((getTempsTotal() - tpsD�butBoss) > PLUS_PARTOUT) { //Quand il tire en spirale
					s.bougerHaut(0.3);
					s.bougerDroite(0.3);	
				}else {
					s.bougerHaut(0.15);
					s.bougerDroite(0.15);	
				}
				break;

			case "balle O":
				if((getTempsTotal() - tpsD�butBoss) > PLUS_PARTOUT) //Quand il tire en spirale
					s.bougerDroite(0.3);
				else
					s.bougerDroite(0.15);
				break;

			case "balle OS":
				s.bougerDroite(0.3);
				s.bougerBas(0.3);
				break;

			case "balle S":
				if((getTempsTotal() - tpsD�butBoss) < SPIRALE+1 || (getTempsTotal() - tpsD�butBoss) > TIR_PARTOUT3) { //Quand il tire partout
					s.bougerBas(3);
				}else if((getTempsTotal() - tpsD�butBoss) > PLUS_PARTOUT){ //Quand il tire en spirale
					s.bougerBas(0.3);
				}else { //Quand il tire dans toutes les directions
					s.bougerBas(0.15);
				}
				break;

			case "balle SE":
				s.bougerBas(0.3);
				s.bougerGauche(0.3);
				break;

			case "balle E":
				if((getTempsTotal() - tpsD�butBoss) > PLUS_PARTOUT) //Quand il tire en spirale
					s.bougerGauche(0.3);
				else
					s.bougerGauche(0.15);

				break;

			case "balle NE":
				if((getTempsTotal() - tpsD�butBoss) > PLUS_PARTOUT) { //Quand il tire en spirale
					s.bougerHaut(0.3);
					s.bougerGauche(0.3);
				}else {
					s.bougerHaut(0.15);
					s.bougerGauche(0.15);
				}
				break;

			case "balle partout gg":
				s.bougerBas(3);
				s.bougerGauche(3);
				break;

			case "balle partout g":
				s.bougerBas(3);
				s.bougerGauche(5);
				break;

			case "balle partout d":
				s.bougerBas(3);
				s.bougerDroite(5);
				break;

			case "balle partout dd":
				s.bougerBas(3);
				s.bougerDroite(3);
				break;

				//Mur de balles 
			case "mur balle":
				if((getTempsTotal() - tpsD�butBoss) > RETOUR && (getTempsTotal() - tpsD�butBoss) < RETOUR_TUNNEL)
					s.bougerBas(0.075); //Retour du boss
				else if ((getTempsTotal() - tpsD�butBoss) > MURS && (getTempsTotal() - tpsD�butBoss) < MUR_PARTOUT)
					s.bougerBas(0.125);  //Murs de balles de bas et de haut
				else if ((getTempsTotal() - tpsD�butBoss) > MUR_PARTOUT)//Partout
					s.bougerBas(0.25); 
				else //D�but du boss
					s.bougerBas(0.2); 
				break;

				//Mur de balles de bas
			case "mur balle bas":
				if((getTempsTotal() - tpsD�butBoss) > MUR_PARTOUT) //Murs de balles partout
					s.bougerHaut(0.2); 
				else //Que le mur de balle venant de haut et bas
					s.bougerHaut(0.125); 
				break;

				//Balle des tunnel
			case "balle tunnel":
				if(((getTempsTotal() - tpsD�butBoss) < TUNNEL_LENT) || (((getTempsTotal() - tpsD�butBoss) > TUNNEL3) &&((getTempsTotal() - tpsD�butBoss) < TUNNEL_RAPIDE)))
					s.bougerBas(0.075); //Vitesse normale
				else if(((getTempsTotal() - tpsD�butBoss) > TUNNEL_LENT && ((getTempsTotal() - tpsD�butBoss) < TUNNEL_RAPIDE)) || ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL2) || ((getTempsTotal() - tpsD�butBoss) > TUNNEL_LENT3)) //Si tunnel au ralenti
					s.bougerBas(0.2); 
				else //Tunnel rapide (Ou tunnel en phase II)
					s.bougerBas(0.06); 
				break;

			case "balle tunnel II":
				if((((getTempsTotal() - tpsD�butBoss) > RALENTI) && ((getTempsTotal() - tpsD�butBoss) < FIN_RALENTI)) || (((getTempsTotal() - tpsD�butBoss) > RERALENTI) && ((getTempsTotal() - tpsD�butBoss) < FIN_RERALENTI)))
					s.bougerBas(0.2); //Ralenti
				else if((getTempsTotal() - tpsD�butBoss) > RETOUR_TUNNEL) //Rapide
					s.bougerBas(0.07);
				else //Tunnel normal
					s.bougerBas(0.075); 
				break;

			case "balle al�atoire bas": //Balles al�atoire allant en bas
				if((getTempsTotal() - tpsD�butBoss) < BALLES_PARTOUT) //Pour l'attaque du d�but
					s.bougerBas(1);
				else if((getTempsTotal() - tpsD�butBoss) < DEBUT2 || (((getTempsTotal() - tpsD�butBoss) > BALLES_PARTOUT2) && ((getTempsTotal() - tpsD�butBoss) < TIR_PARTOUT3)) || (getTempsTotal() - tpsD�butBoss) > BALLES_PARTOUT3) //Pour les balles al�atoires de partout
					s.bougerBas(3.5);
				else if ((getTempsTotal() - tpsD�butBoss) > DEBUT2 && (getTempsTotal() - tpsD�butBoss) < TRANSITION_DEBUT2)
					s.bougerBas(0.75); //Pour l'attaque du d�but acc�l�r�e
				else
					s.bougerBas(0.5); //Pour l'attaque du d�but tr�s vite
				break;


			case "balle al�atoire bas II":
				s.bougerBas(4);
				break;

			case "balle al�atoire haut": //Balles al�atoire allant en haut
				if(!commenc�)
					s.bougerHaut(3.5);
				else
					s.bougerHaut(4);
				break;

			case "balle al�atoire gauche": //Balles al�atoire allant � gauche
				if(!commenc�)
					s.bougerGauche(3.5);
				else
					s.bougerGauche(4);
				break;

			case "balle al�atoire droite": //Balles al�atoire allant � droite
				if(!commenc�)
					s.bougerDroite(3.5);
				else
					s.bougerDroite(4);
				break;

			case "balle vise gauche": //Balles viseuses allant � gauche
				s.bougerGauche(0.75);
				break;

			case "balle vise droite": //Balles viseuses allant � droite
				s.bougerDroite(0.75);
				break;

			case "balle vise haut": //Balles viseuses allant en haut
				s.bougerHaut(0.75);
				break;

			case "balle vise bas": //Balles viseuses allant en bas
				s.bougerBas(0.75);
				break;

			case "balle �clair": //Eclair
				s.bougerBas(0.01); //Tr�s vite [Flash]
				break;

			case "balle �clair2": //Eclair horizontal
				s.bougerGauche(0.01); //Tr�s vite [Flash]
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

			case "balle gauche droite": //Gauche/Droite rapide
				s.bougerBas(0.07);
				break;

			case "balle al�atoire bas vit1":
				if((getTempsTotal() - tpsD�butBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerBas(2.5);
				break;

			case "balle al�atoire bas vit2":
				if((getTempsTotal() - tpsD�butBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerBas(2);
				break;

			case "balle al�atoire bas vit3":
				if((getTempsTotal() - tpsD�butBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerBas(1.5);
				break;

			case "balle al�atoire bas vit4":
				if((getTempsTotal() - tpsD�butBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				if((getTempsTotal() - tpsD�butBoss)<BALLES_ALEATOIRES_HAUT_BAS)
					s.bougerBas(1);
				else //Comme y'a celles du bas aussi c'est + difficile
					s.bougerBas(1.25);
				break;

			case "balle al�atoire haut vit1":
				if((getTempsTotal() - tpsD�butBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerHaut(1);
				break;

			case "balle al�atoire haut vit2":
				if((getTempsTotal() - tpsD�butBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerHaut(1.5);
				break;

			case "balle al�atoire haut vit3":
				if((getTempsTotal() - tpsD�butBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerHaut(2);
				break;

			case "balle al�atoire haut vit4":
				if((getTempsTotal() - tpsD�butBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerHaut(2.5);
				break;

				//Pour les vies
			case "bonus":
				s.bougerBas(2);
				break;


			case "boss":
				if(phaseI) {
					//Il flotte
					if(getTemps() > 0 && getTemps() < 1)
						s.bougerHaut(1);
					else
						s.bougerBas(1);

					//Le boss perd de la vie automatiquement durant la phase I
					if(((getTempsTotal() - tpsD�butBoss)>DEBUT) && (getTempsTotal() - tpsD�butBoss)<FIN)
						boss.perdreVie(0.03);

					//Quand il tire en tunnel il tire avec une autre fr�quence (Donc l'expression bool�enne est "Quand il n'attaque pas en tunnel")
					if(((getTempsTotal() - tpsD�butBoss) < TUNNEL) || (((getTempsTotal() - tpsD�butBoss) > FIN_TUNNEL) && ((getTempsTotal() - tpsD�butBoss) < TUNNEL_LENT2)) || (((getTempsTotal() - tpsD�butBoss) > FIN_TUNNEL2) && ((getTempsTotal() - tpsD�butBoss) < TUNNEL3)) ||
							(((getTempsTotal() - tpsD�butBoss) > BALLES_PARTOUT2) && ((getTempsTotal() - tpsD�butBoss) < TUNNEL4)) || ((getTempsTotal() - tpsD�butBoss) > GAUCHE_DROITE2)) {
						try {
							bossTir();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					//Il bouge quand il tire partout
					if(((getTempsTotal()-tpsD�butBoss) > TIR_PARTOUT && (getTempsTotal()-tpsD�butBoss) < TRANSITION_TIR) || ((getTempsTotal()-tpsD�butBoss) > TIR_PARTOUT2 && (getTempsTotal()-tpsD�butBoss) < TRANSITION_TIR2) ||
							((getTempsTotal()-tpsD�butBoss) > SPIRALE && (getTempsTotal()-tpsD�butBoss) < FIN_SPIRALE) || ((getTempsTotal()-tpsD�butBoss) > PLUS_PARTOUT && (getTempsTotal()-tpsD�butBoss) < GAUCHE_DROITE) ||
							((getTempsTotal()-tpsD�butBoss) > TIR_PARTOUT3 && (getTempsTotal()-tpsD�butBoss) < FIN_PARTOUT3)){
						//Si contre un mur
						if(s.getTranslateX() <= 15 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 135) 
							bossVersDroite = !bossVersDroite; 

						if(bossVersDroite) {
							if((getTempsTotal() - tpsD�butBoss) < BALLES_PARTOUT || (getTempsTotal() - tpsD�butBoss) > TIR_PARTOUT3)
								s.bougerDroite(0.02);
							else
								s.bougerDroite(0.1); //Quand il tire dans toutes les directions avec les balles dans toutes les directions
						}else {
							if((getTempsTotal() - tpsD�butBoss) < BALLES_PARTOUT || (getTempsTotal() - tpsD�butBoss) > TIR_PARTOUT3)
								s.bougerGauche(0.02);
							else
								s.bougerGauche(0.1); //Quand il tire dans toutes les directions avec les balles dans toutes les directions
						}
					}

					//Bouge gauche/droite de mani�re al�atoire pour le tunnel (Expression bool�enne: "Quand il attaque en tunnel")
					if((((getTempsTotal() - tpsD�butBoss) > TUNNEL) && ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL)) || (((getTempsTotal() - tpsD�butBoss) > TUNNEL_LENT2) && ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL2)) ||
							(((getTempsTotal() - tpsD�butBoss) > TUNNEL3) && ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL3)) || (((getTempsTotal() - tpsD�butBoss) > TUNNEL4) && ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL4))) {
						//Si contre un mur
						if(s.getTranslateX() <= 10 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 130) 
							bossVersDroite = !bossVersDroite; 

						if(bossVersDroite) {
							if(((getTempsTotal() - tpsD�butBoss) < TUNNEL_LENT) || (((getTempsTotal() - tpsD�butBoss) > TUNNEL3) && ((getTempsTotal() - tpsD�butBoss) < TUNNEL_LENT3))) //Vitesse normale & rapide
								s.bougerDroite(0.1);
							else if(((getTempsTotal() - tpsD�butBoss) > TUNNEL_LENT && ((getTempsTotal() - tpsD�butBoss) < TUNNEL_RAPIDE)) || ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL2) || ((getTempsTotal() - tpsD�butBoss) > TUNNEL_LENT3)) //Si tunnel au ralenti
								s.bougerDroite(0.25);	
						}else {
							if(((getTempsTotal() - tpsD�butBoss) < TUNNEL_LENT) || (((getTempsTotal() - tpsD�butBoss) > TUNNEL3) && ((getTempsTotal() - tpsD�butBoss) < TUNNEL_LENT3))) //Vitesse normale & rapide
								s.bougerGauche(0.1);
							else if(((getTempsTotal() - tpsD�butBoss) > TUNNEL_LENT && ((getTempsTotal() - tpsD�butBoss) < TUNNEL_RAPIDE)) || ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL2) || ((getTempsTotal() - tpsD�butBoss) > TUNNEL_LENT3)) //Si tunnel au ralenti
								s.bougerGauche(0.25);
						}

						if(((getTempsTotal() - tpsD�butBoss) < TUNNEL_LENT) || (((getTempsTotal() - tpsD�butBoss) > TUNNEL3) && ((getTempsTotal() - tpsD�butBoss) < TUNNEL_LENT3))) { //Vitesse normale & rapide
							//Al�atoire toute les 0.125 secondes
							if(Manipulation.huitFoisParSecondes(getTemps())){
								if(Math.random() >= 0.5)
									bossVersDroite = false;
								else
									bossVersDroite = true;

								//Attaque tunnel, 8 fois/s
								try {
									bossTir();
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}else if(((getTempsTotal() - tpsD�butBoss) > TUNNEL_LENT) || ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL2) || ((getTempsTotal() - tpsD�butBoss) > TUNNEL_LENT3)) { //Si tunnel au ralenti
							//Al�atoire toute les 0.25 secondes
							if(Manipulation.quatreFoisParSecondes(getTemps())){
								if(Math.random() >= 0.5)
									bossVersDroite = false;
								else
									bossVersDroite = true;

								//Attaque tunnel, 4 fois/s
								try {
									bossTir();
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
				}else if(commenc� && !mort){ //Phase II (D�but du combat)
					//Si contre un mur
					if(s.getTranslateX() <= 10 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 130) 
						bossVersDroite = !bossVersDroite; 

					//Flotte
					if(getTemps() > 0 && getTemps() < 1)
						s.bougerHaut(0.75);
					else
						s.bougerBas(0.75);

					//Tunnel
					if((((getTempsTotal() - tpsD�butBoss) > COMMENCE) && ((getTempsTotal() - tpsD�butBoss) < FIN_COMMENCE)) || (((getTempsTotal() - tpsD�butBoss) > RECOMMENCE) && ((getTempsTotal() - tpsD�butBoss) < FIN_RECOMMENCE)) || 
							(((getTempsTotal() - tpsD�butBoss) > RETOUR_TUNNEL) && ((getTempsTotal() - tpsD�butBoss) < PART)) || (((getTempsTotal() - tpsD�butBoss) > RETOUR2) && ((getTempsTotal() - tpsD�butBoss) < FIN_RETOUR2)) || 
							(((getTempsTotal() - tpsD�butBoss) > DERNIER_TUNNEL) && (getTempsTotal() - tpsD�butBoss) < FIN_DERNIER_TUNNEL)){
						if(bossVersDroite) {
							if(((getTempsTotal() - tpsD�butBoss) > RALENTI && (getTempsTotal() - tpsD�butBoss) < FIN_RALENTI) || ((getTempsTotal() - tpsD�butBoss) > RERALENTI && (getTempsTotal() - tpsD�butBoss) < FIN_RERALENTI))
								s.bougerDroite(0.25);
							else
								s.bougerDroite(0.1);	
						}else {
							if(((getTempsTotal() - tpsD�butBoss) > RALENTI && (getTempsTotal() - tpsD�butBoss) < FIN_RALENTI) || ((getTempsTotal() - tpsD�butBoss) > RERALENTI && (getTempsTotal() - tpsD�butBoss) < FIN_RERALENTI))
								s.bougerGauche(0.25);
							else
								s.bougerGauche(0.1);	
						}

						//Tunnel ralenti
						if(((getTempsTotal() - tpsD�butBoss) > RALENTI && (getTempsTotal() - tpsD�butBoss) < FIN_RALENTI) || ((getTempsTotal() - tpsD�butBoss) > RERALENTI && (getTempsTotal() - tpsD�butBoss) < FIN_RERALENTI)) {
							//Al�atoire toute les 0.25 secondes
							if(Manipulation.quatreFoisParSecondes(getTemps())){
								if(Math.random() >= 0.5)
									bossVersDroite = false;
								else
									bossVersDroite = true;

								//Attaque tunnel, 4 fois/s
								try {
									bossTir();
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}else { //Pas ralenti
							//Al�atoire toute les 0.125 secondes
							if(Manipulation.huitFoisParSecondes(getTemps())){
								if(Math.random() >= 0.5)
									bossVersDroite = false;
								else
									bossVersDroite = true;

								//Attaque tunnel, 8 fois/s
								try {
									bossTir();
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					}
					//Il tire pas � la m�me vitesse quand il ne tire pas en tunnel (Condition: PAS en tunnel)
					if(((((getTempsTotal() - tpsD�butBoss) > FIN_COMMENCE) && ((getTempsTotal() - tpsD�butBoss) < RECOMMENCE)) || ((((getTempsTotal() - tpsD�butBoss) > ECLAIRS)) && ((getTempsTotal() - tpsD�butBoss) < RETOUR_TUNNEL)) || 
							((getTempsTotal() - tpsD�butBoss) > DERNIERE_ATTAQUE)) && commenc�) {
						try {
							bossTir();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				break;

			case "joueur bullet": //Balle du joueur
				s.bougerHaut(1);

				//Si collision avec le boss (Combat phase II commenc� et le boss pouvait �tre touch�)
				if(commenc� && bossPr�sent) {
					Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("boss")).forEach(bosss -> {
						if (Manipulation.touche(s, bosss)){
							s.meurs();
							boss.perdreVie(DEGAT_BALLE_JOUEUR); //Il perd de la vie !
							vieActuelle-=DEGAT_BALLE_JOUEUR; //On enregistre la vie du boss
							Manipulation.scoreUp(this);

							//Mort du boss
							if(boss.getVieBossActuelle() <= 0) {

								//Si c'est la phaseII, la mort du boss est la vraie
								if(phaseII) mort = true;

								setEnDialogue(true); //Dialogue de fin
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
			}
		});

		//(Phase II) Le boss dispara�t mais des balles apparaissent [Toutes les attaques sont dans bossTir pour que ce soit + compr�hensible]
		//Condition: "BOSS PAS PRESENT"
		if(!bossPr�sent && commenc�) {
			bossTir();
		}

		niveau();

		if(isNiveauTermin�()) fin();
	}

	//Il s'affiche � la phase II
	public void texteNiveau() throws FileNotFoundException {
		//On ne fait pas Manipulation.ajouterTexte car la il a un effet d'opacit�
		Texte niv = new Texte("NIVEAU FINAL - FIN", "nivFinal");
		niv.setX(-30);
		niv.setY(NiveauInterface.HAUTEUR_ECRAN/2);
		niv.setFont(Font.loadFont(new FileInputStream("Polices/NIVEAU.ttf"), 30)); //Charge la police et la change
		niv.setFill(Paint.valueOf("RED")); //Couleur
		niv.setOpacity(0); //Il va appara�tre
		Manipulation.ajouterJeu(getRoot(), niv);
	}

	public void niveau() throws FileNotFoundException {
		// ============================ BOSS (DIALOGUE) ============================= \\
		if(Manipulation.siTpsA(getTempsTotal(), CREATION_BOSS, true))
			//Le mini-boss appara�t
			boss = new BOSSFINAL((NiveauInterface.LARGEUR_DECOR/2)-70, -150, 100, 150, "boss", VIE_BOSS, "Images/NiveauFinal/boss.png", new BarreDeVie(10,40,VIE_BOSS,10, "vie", this), this);

		//La musique se joue (On le voit appara�tre
		if(Manipulation.siTpsA(getTempsTotal(), BOSS_CREE, true)) 
			if(Main.son()) mediaPlayer.play();

		//Il descend
		if(getTempsTotal() > BOSS_CREE && getTempsTotal() < FIN_DESCENTE_BOSS)
			boss.bougerBas(1);

		if(Manipulation.siTpsA(getTempsTotal(), FIN_DESCENTE_BOSS, true)) {
			//On ne fait pas Manipulation.ajouterTexte car on doit r�cup�rer ce nom pour pas qu'il soit supprim� lors de updateScore()
			Texte niv = new Texte(nomBOSS, "nomboss");
			niv.setX(NiveauInterface.X_NOM_BOSS);
			niv.setY(NiveauInterface.Y_NOM_BOSS);
			niv.setFont(Font.loadFont(new FileInputStream("Polices/BOSS.ttf"), 30)); //Charge la police et la change
			niv.setFill(Paint.valueOf("WHITE")); //Couleur
			Manipulation.ajouterJeu(getRoot(), niv);

			setEnDialogue(true); 
			dialogue();
		}

		//Fin du dialogue, transition vers le combat
		if(enTransition && ((getTempsTotal() - tpsD�butTransition)<TEMPS_TRANSITION))
			transition();

		//D�but du boss
		if(enTransition && (Manipulation.siTpsA(getTempsTotal() - tpsD�butTransition, TEMPS_TRANSITION, false)) && !phaseII) {
			enTransition = false;
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_1.gif")));

			mediaPlayer.stop();

			//Musique du boss
			mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveauFINAL/phaseI.mp3").toURI().toString()));
			if(Main.son()) mediaPlayer.play();

			tpsD�butBoss =  getTempsTotal(); //Moment de d�but du combat (phase I)
			phaseI = true;
			lockFinDialogue = false; //Pour le dialogue vers la phase II
			
			setTempsTotal(getTempsTotal() + 205);
		}

		// ============================ BOSS (PHASE I) ============================= \\
		if(phaseI) {
			//Dialogue
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_1, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Nul besoin de tirer mon armure est,", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_2, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "INDESTRUCTIBLE. BAHAHAHA !!!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}
			//Le dialogue s'efface
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DEBUT, false)) {
				boss();
			}

			//Transition (Tremble)
			if((((getTempsTotal() - tpsD�butBoss) > TRANSITION_DEBUT) && ((getTempsTotal() - tpsD�butBoss) < TIR_PARTOUT)) || (((getTempsTotal() - tpsD�butBoss) > TRANSITION_TIR) && ((getTempsTotal() - tpsD�butBoss) < BALLES_GAUCHE_DROITE)) ||
					((getTempsTotal() - tpsD�butBoss) > TRANSITION_BALLES) && ((getTempsTotal() - tpsD�butBoss) < TIR_PARTOUT2) || (((getTempsTotal() - tpsD�butBoss) > TRANSITION_TIR2) && ((getTempsTotal() - tpsD�butBoss) < SPIRALE)) ||
					(((getTempsTotal() - tpsD�butBoss) > TRANSITION_MUR) && ((getTempsTotal() - tpsD�butBoss) < TUNNEL) || ((getTempsTotal() - tpsD�butBoss) > TRANSITION_TUNNEL) && ((getTempsTotal() - tpsD�butBoss) < BALLES_PARTOUT)) ||
					((getTempsTotal() - tpsD�butBoss) > TRANSITION_PARTOUT) && ((getTempsTotal() - tpsD�butBoss) < GAUCHE_DROITE) || ((getTempsTotal() - tpsD�butBoss) > TRANSITION_TUNNEL2) && ((getTempsTotal() - tpsD�butBoss) < RIEN) ||
					((getTempsTotal() - tpsD�butBoss) > TRANSITION_DEBUT2) && ((getTempsTotal() - tpsD�butBoss) < TUNNEL3) || ((getTempsTotal() - tpsD�butBoss) > TRANSITION_TUNNEL3) && ((getTempsTotal() - tpsD�butBoss) < BALLES_PARTOUT2) ||
					((getTempsTotal() - tpsD�butBoss) > TRANSITION_PARTOUT2) && ((getTempsTotal() - tpsD�butBoss) < TUNNEL4) || ((getTempsTotal() - tpsD�butBoss) > TRANSITION_TUNNEL4) && ((getTempsTotal() - tpsD�butBoss) < GAUCHE_DROITE2) ||
					((getTempsTotal() - tpsD�butBoss) > TRANSITION_DIALOGUE) && ((getTempsTotal() - tpsD�butBoss) < DEBUT3) || ((getTempsTotal() - tpsD�butBoss) > TREMBLE) && ((getTempsTotal() - tpsD�butBoss) < FIN)){
				transition();
			}

			//Passage au fond 2 (Acc�l�re)
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TIR_PARTOUT, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TIR_PARTOUT2, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, GAUCHE_DROITE, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TIR_PARTOUT3, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, GAUCHE_DROITE2, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_2.gif")));

			//On replace le boss au milieu
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TRANSITION_TIR, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TRANSITION_TIR2, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_SPIRALE, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_TUNNEL, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_TUNNEL2, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_TUNNEL3, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_PARTOUT3, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_TUNNEL4, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TRANSITION_PARTOUT, false)) {
				boss.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);
				boss.updateImagePos();
			}

			//Passage au fond 3 (Calme)
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, BALLES_GAUCHE_DROITE, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, BALLES_PARTOUT, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TUNNEL_LENT2, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, BALLES_PARTOUT2, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_3.gif")));

			//On efface toutes les balles
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_TIR2, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_MUR, false)) {
				Manipulation.sprites(root).stream().filter(e -> e.getType().contains("balle")).forEach(balle -> {
					balle.meurs();
				});
			}

			//Passage au fond 4 (Rapide)
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TUNNEL, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TUNNEL3, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TUNNEL4, false)) 
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_4.gif")));

			//Passage au fond 1 (D�but)
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, RIEN, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_1.gif")));

			//Dialogue 2
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_3, false)) {
				//Affichage des personnages
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Col�re.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/debut.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Argh....", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_4, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Mon armure ne tiens plus...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_5, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai mis trop de puissance en vain?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}
			//Le dialogue s'efface
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, TRANSITION_DIALOGUE, false)) {
				boss();
			}

			//Plus de fond (Fin de la phase I)
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, STOP, false)) {
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/Rien.png")));

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

				//Changement de musique
				mediaPlayer.stop();

				//Musique du dialogue
				mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveauFINAL/dialogue.mp3").toURI().toString()));
				mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //R�p�te a l'infini
				
				//Animation de l'armure de Zerod qui se brise
				Manipulation.supprimer(this, boss.getImg()); //On supprime l'ancienne image
				boss.setImg("Images/NiveauFinal/boss.gif"); //On la change
			}

			// ========================================== BOSS (ENTRE DEUX PHASES) ======================================================= \\
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_FIN, false)) {
				//Changement de phase
				phaseI = false;
				phaseII = true;

				tpsD�butTransition = 999; //Pour la transition de d�but

				if(Main.son()) mediaPlayer.play(); //La musique se lance
				
				//Image de Zerod en phase II
				Manipulation.supprimer(this, boss.getImg()); //On supprime l'ancienne image
				boss.setImg("Images/NiveauFinal/boss2.png"); //On la change
				
				//Dialogue
				setEnDialogue(true); 
				dialogue();
			}
		}

		// ========================================== BOSS (PHASE II) ======================================================= \\
		if(phaseII && !mort && finDialogue){ 
			//Fin du dialogue, transition vers le combat
			if(enTransition && ((getTempsTotal() - tpsD�butTransition)<TEMPS_TRANSITION))
				transition();

			//D�but de la phase II
			if(enTransition && (Manipulation.siTpsA(getTempsTotal() - tpsD�butTransition, TEMPS_TRANSITION, false)) && phaseII) {
				//On le remet
				nomBOSS = "ZEROD, LE GRAND";
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

				//Change le fond
				enTransition = false;
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseII.gif")));

				//Il reg�n�re (Pette animation)
				for(int i =0; i<NiveauInterface.VIE_BOSS-22;++i) {
					boss.setVieBossActuelle(boss.getVieBossActuelle()+1); //La vie monte
					boss.getBarreVie().descente(boss); //Animation vie monte
					boss.getBarreVie().updateCouleur(boss); //Couleur change
				}
				tpsD�butBoss =  getTempsTotal(); //Moment de d�but du combat (Phase II)
				if(Main.son()) mediaPlayer.play();
			}

			//0.1 secondes apr�s le d�but de la phase on affiche enfin le nom du niveau
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, APPARITION_NOM_NIVEAU, true))
				texteNiveau();


			//De l'affichage � la disparition
			if((getTempsTotal() - tpsD�butBoss) > APPARITION_NOM_NIVEAU && (getTempsTotal() - tpsD�butBoss) < FIN_NOM_NIVEAU) {
				Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("nivFinal")).forEach(texte -> {

					if ((getTempsTotal() - tpsD�butBoss) > APPARITION_NOM_NIVEAU && getTempsTotal() - tpsD�butBoss < NOM_NIVEAU_EN_APPARITION) //D�but � 2 seconde il monte en opacit� [2 � 4 il stagne au max]
						texte.setOpacity(tpsD�butBoss/2);
					else if ((getTempsTotal() - tpsD�butBoss) > NOM_NIVEAU_EN_DISPARITION && (getTempsTotal() - tpsD�butBoss) < NOM_NIVEAU_EN_DISPARITION2) //4s � 7s il redescend en opacit�
						texte.setOpacity(4/(tpsD�butBoss*1.25));
					else if ((getTempsTotal() - tpsD�butBoss) > NOM_NIVEAU_EN_DISPARITION2 && (getTempsTotal() - tpsD�butBoss) < FIN_NOM_NIVEAU) //7s � 9s il redescend encore plus
						texte.setOpacity(4/(tpsD�butBoss*3.5));

					texte.updatePos(this, texte.getTranslateX() + 0.25, NiveauInterface.HAUTEUR_ECRAN/4);
				});
			}

			//Dialogue de d�but
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_6, false)) {
				//Affichage des personnages
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Col�re.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C  '  e  s  t    l  a    f  i  n.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			//Suppression du dialogue
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DERNIER_TUNNEL, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DERNIERE_ATTAQUE, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_DIALOGUE_7, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_DIALOGUE, false)) {
				commenc�=true;
				bossPr�sent = true;
				setEnDialogue(false);
				//Pour effacer tout ce qui est dialogue
				Manipulation.toutEffacer(this);

				imageFond = new ImageView(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseII.gif")));
				root.getChildren().add(imageFond);

				Manipulation.ajouterImage(getRoot(),"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

				//On r�affiche les personnages
				Manipulation.ajouterJeu(getRoot(), joueur.getImg());
				Manipulation.ajouterJeu(getRoot(), boss.getImg());

				//On enl�ve puis remet la barre de vie 
				Manipulation.supprimer(this, boss.getBarreVie());
				Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

				Manipulation.updateScore(this); //On remet le score

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

				boss.setVieBossActuelle(vieActuelle); //La vie qu'il avait
				
				//setTempsTotal(getTempsTotal()+70);
			}

			//Replace au milieu le boss
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_COMMENCE, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_RECOMMENCE, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, REVIENT-1, false)) {
				boss.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);

				//L'image s'affiche pas quand Zerod revient
				if((getTempsTotal() - tpsD�butBoss) < REVIENT-1)
					boss.updateImagePos();
			}

			//Le boss part
			if(((getTempsTotal() - tpsD�butBoss) > FIN_ECLAIRS && (getTempsTotal() - tpsD�butBoss) < BALLES_TOMBENT) || (getTempsTotal() - tpsD�butBoss) > PART && (getTempsTotal() - tpsD�butBoss) < PART+TEMPS_TRANSITION) {
				boss.bougerHaut(0.35);
				bossPr�sent = false; //Le boss n'est plus l�
			}

			//Le boss, barre de vie et nom disparaissent
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, BALLES_TOMBENT, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, PART+TEMPS_TRANSITION, false)) {
				boss.getImg().meurs(); //Le boss dispara�t (Sinon on voit ses pieds)
				Manipulation.supprimer(this, boss.getBarreVie()); //La barre de vie dispara�t

				//Le nom du boss dispara�t
				Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("nomboss")).forEach(texte -> {
					Manipulation.supprimer(this, texte);
				});
			}

			if(Manipulation.siTpsA(getTempsTotal(), ECLAIRS, true) || Manipulation.siTpsA(getTempsTotal(), ECLAIR, true) || Manipulation.siTpsA(getTempsTotal(), HORIZONTAUX, true)) {
				setTemps(0); //Pour bien commencer les �clairs (Ils sont en fonction du temps)
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_ECLAIRS, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, PART, false)) {
				//Suppression du nom du boss
				Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("nomboss")).forEach(texte -> {
					Manipulation.supprimer(this, texte);
				});
			}

			//Dialogue
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_7, false)) {
				//Affichage des personnages
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Col�re.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Disparait en un claquement de doigt !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			//Le boss revient
			if(((getTempsTotal() - tpsD�butBoss) > DIALOGUE_7 && (getTempsTotal() - tpsD�butBoss) < RETOUR) || (getTempsTotal() - tpsD�butBoss) > REVIENT && (getTempsTotal() - tpsD�butBoss) < RETOUR2) {
				boss.bougerBas(0.2);
				bossPr�sent = true;

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

			//Quand le boss revient
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_7, true) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, REVIENT, true)) {
				
				//Si c'est quand Zerod revient apr�s la 7�me dialogue, il a une aura autour de lui, son image change
				if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_7, false)) {
					Manipulation.supprimer(this, boss.getImg()); //On supprime l'ancienne image de Zerod
					boss.setImg("Images/NiveauFinal/zerod.gif"); //Zerod avec maintenant une aura bleue
				}else { //Sinon c'est quand il revient, toujours avec l'aura bleue donc pas besoin de changer l'image
					Manipulation.ajouterJeu(root, boss.getImg());
				}
				
				//Dans tous les cas, sa barre de vie revient
				Manipulation.ajouterJeu(root, boss.getBarreVie());
			}

			//transition() fait trembler Zerod
			if(((getTempsTotal() - tpsD�butBoss) > RETOUR && (getTempsTotal() - tpsD�butBoss) < PART) || ((getTempsTotal() - tpsD�butBoss) > REVIENT)) 
				transition();

			//Dialogue
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_8, false)) {
				//Affichage des personnages
				setPeutTirer(false); //Le joueur ne peut pas tirer
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Col�re.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "AHHHHHHHHHHHHHHHH !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			//Dialogue
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_9, false)) {
				//Affichage des personnages
				setPeutTirer(false); //Le joueur ne peut pas tirer
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Col�re.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ahhh....", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_10, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Aurais-je... Perdu ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_11, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Non... Rien n'est fini Salar...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_12, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C'est fini, Zerod. Arrete.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, DIALOGUE_13, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Hmhmhm... MEURS.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				
				//Zerod perd son aura bleue
				Manipulation.supprimer(this, boss.getImg()); //On supprime l'ancienne image de Zerod
				boss.setImg("Images/NiveauFinal/boss2.png"); //Nouvelle, celle d'avant, sans aura
				setPeutTirer(true); //Le joueur peut tirer
			}
		}
	}

	/**
	 * Des balles apparaissent al�atoirement des quatre c�t�s
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void ballesAl�atoiresPartout() throws FileNotFoundException {
		if(!commenc�) { //Phase I
			if(Math.random()>0.975) {
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), -10, 20, 20, "Images/NiveauFinal/balle_b.png", "balle al�atoire bas", this); //Sors du haut va vers le bas
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), NiveauInterface.HAUTEUR_ECRAN, 20, 20, "Images/NiveauFinal/balle_h.png", "balle al�atoire haut", this); //Sors du bas va vers le haut
				new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20,"Images/NiveauFinal/balle_d.png", "balle al�atoire droite", this); //Sors de la gauche va vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-100, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/NiveauFinal/balle_g.png", "balle al�atoire gauche", this); //Sors de la droite va vers la gauche
			}
		}else{
			double probabilit�Tir = 0.985; //Phase II, pas la derni�re attaque

			if((getTempsTotal() - tpsD�butBoss) > DERNIERE_ATTAQUE) //Derni�re attaque
				probabilit�Tir = 0.975;

			if(Math.random()>probabilit�Tir) {
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), -10, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire bas II", this); //Sors du haut va vers le bas
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), NiveauInterface.HAUTEUR_ECRAN, 20, 20,"Images/NiveauFinal/balle2.png", "balle al�atoire haut", this); //Sors du bas va vers le haut
				new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire droite", this); //Sors de la gauche va vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-100, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire gauche", this); //Sors de la droite va vers la gauche
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
		if(!commenc�) {
			if(Math.random() > 0.9) //Quand il y a seulement elles
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/NiveauFinal/balle_b.png", "balle al�atoire bas", this); //X al�atoire
		}else { //Phase II
			double probabilit�Tir = 0.975; //Avec les balles al�atoires partant du bas

			//Pas la m�me probabilit�e
			if((getTempsTotal() - tpsD�butBoss) < BALLES_ALEATOIRES_HAUT_BAS) //Le d�but
				probabilit�Tir = 0.9;
			else if((getTempsTotal() - tpsD�butBoss) > PLUS_BALLE) //Avec les �clairs
				probabilit�Tir = 0.95;


			if(Math.random() > probabilit�Tir) { //Probabilit� d'un tir
				double rd = Math.random();

				//Pas la m�me vitesse des balles
				if(rd < 0.25) {
					if((getTempsTotal() - tpsD�butBoss) < GROSSES)
						new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire bas vit1", this); //X al�atoire
					else //Une grosse balle
						new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 50, 50, "Images/NiveauFinal/balle2.png", "balle al�atoire bas vit1", this); //X al�atoire
				}
				else if(rd < 0.5)
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire bas vit2", this); //X al�atoire
				else if(rd < 0.75)
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire bas vit3", this); //X al�atoire
				else
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire bas vit4", this); //X al�atoire
			}
		}
	}

	/**
	 * Des balles apparaissent al�atoirement de la gauche et de la droite
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void ballesAl�atoiresGaucheDroite() throws FileNotFoundException {
		//Part de la droite
		if(Math.random() > 0.975)
			new BossBalle(NiveauInterface.LARGEUR_DECOR-35, Math.random() * NiveauInterface.LARGEUR_ECRAN, "Images/NiveauFinal/balle_g.png", "balle E", this); //X al�atoire

		//Part de la gauche
		if(Math.random() > 0.975)
			new BossBalle(5, Math.random() * NiveauInterface.LARGEUR_ECRAN, "Images/NiveauFinal/balle_d.png", "balle O", this); //X al�atoire
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
				endroit = 2; //2: Tout � droite

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

	/**
	 * Le boss tire cinq balles deux allant � gauche, deux � droite et une toute droite
	 * 
	 * @throws FileNotFoundException
	 * @author Jordan
	 */
	private void tirPartout() throws FileNotFoundException {
		if(Manipulation.huitFoisParSecondes(getTemps())) {
			new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY()+150, 20, 20, "Images/NiveauFinal/balle_b.png", "balle partout gg", this); 
			new EnnemiBalle(boss.getTranslateX()+25, boss.getTranslateY()+175, 20, 20, "Images/NiveauFinal/balle_b.png", "balle partout g", this); 
			new EnnemiBalle(boss.getTranslateX()+50, boss.getTranslateY()+200, 20, 20, "Images/NiveauFinal/balle_b.png", "balle S", this); 
			new EnnemiBalle(boss.getTranslateX()+75, boss.getTranslateY()+175, 20, 20, "Images/NiveauFinal/balle_b.png", "balle partout d", this); 
			new EnnemiBalle(boss.getTranslateX()+100, boss.getTranslateY()+150, 20, 20, "Images/NiveauFinal/balle_b.png", "balle partout dd", this); 
		}
	}

	@Override
	public void bossTir() throws FileNotFoundException {
		if(phaseI) {
			// ====================== BALLES ALEATOIRES DU HAUT ===================== \\
			if(((getTempsTotal() - tpsD�butBoss)>DEBUT && (getTempsTotal() - tpsD�butBoss)<FIN_DEBUT) || ((getTempsTotal() - tpsD�butBoss)>DEBUT2 && (getTempsTotal() - tpsD�butBoss)<FIN_DEBUT2) ||
					((getTempsTotal() - tpsD�butBoss)>DEBUT3 && (getTempsTotal() - tpsD�butBoss)<FIN_DEBUT3)) {
				ballesAl�atoiresHaut();
			}

			// ====================== TIRE DE PARTOUT ===================== \\
			if(((getTempsTotal() - tpsD�butBoss) > TIR_PARTOUT && (getTempsTotal() - tpsD�butBoss) < FIN_TIR) ||
					((getTempsTotal() - tpsD�butBoss) > TIR_PARTOUT2 && (getTempsTotal() - tpsD�butBoss) < FIN_TIR2) ||
					((getTempsTotal() - tpsD�butBoss) > TIR_PARTOUT3 && (getTempsTotal() - tpsD�butBoss) < FIN_PARTOUT3)){
				tirPartout();
			}

			// ====================== BALLES ALEATOIRES GAUCHE/DROITE ===================== \\
			if((getTempsTotal() - tpsD�butBoss) > BALLES_GAUCHE_DROITE && (getTempsTotal() - tpsD�butBoss) < FIN_BALLES) {
				ballesAl�atoiresGaucheDroite();
			}

			// ====================== SPIRALE ===================== \\
			if((getTempsTotal() - tpsD�butBoss) > SPIRALE && (getTempsTotal() - tpsD�butBoss) < FIN_SPIRALE) {
				if(Manipulation.siTpsA(getTemps(), 0.2, false) || Manipulation.siTpsA(getTemps(), 0.8, false) || Manipulation.siTpsA(getTemps(), 1.4, false)) {
					boss.tir("balle NO");
					boss.tir("balle NE");
					boss.tir("balle S");
				}

				if(Manipulation.siTpsA(getTemps(), 0.4, false) || Manipulation.siTpsA(getTemps(), 1, false) || Manipulation.siTpsA(getTemps(), 1.6, false)){
					boss.tir("balle N");
					boss.tir("balle E");
					boss.tir("balle OS");
				}

				if(Manipulation.siTpsA(getTemps(), 0.6, false) || Manipulation.siTpsA(getTemps(), 1.2, false) || Manipulation.siTpsA(getTemps(), 1.8, false)){
					boss.tir("balle NE");
					boss.tir("balle SE");
					boss.tir("balle O");
				}

				ballesAl�atoiresGaucheDroite();
			}

			// ====================== MUR BALLES VERTICAL ===================== \\
			if(((getTempsTotal() - tpsD�butBoss) > MUR) && ((getTempsTotal() - tpsD�butBoss) < FIN_MUR)) {
				//Pas le m�me �cart sinon �a sera toujours le m�me mur avec les m�mes trous 
				int ecartBalles = (int) (Math.random() * 100)+100; //+90 �vite le ecartBalles = 0

				if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false)) {
					//Cr�ation de la ligne de balles (De gauche � droite)
					for(int i = -ecartBalles+50; i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2; i+=ecartBalles) {
						new BossBalle(10, i, "Images/NiveauFinal/balle_d.png", "balle mur vertical g", this); 
						new BossBalle(-20, i, "Images/NiveauFinal/balle_d.png", "balle mur vertical g", this); 
					}
				}

				if(Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
					//Et la ligne de droite � gauche
					for(int i = (-ecartBalles); i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2+10; i+=ecartBalles) {
						new BossBalle(NiveauInterface.LARGEUR_DECOR-70, i,"Images/NiveauFinal/balle_g.png", "balle mur vertical d", this); 
						new BossBalle(NiveauInterface.LARGEUR_DECOR-40, i, "Images/NiveauFinal/balle_g.png", "balle mur vertical d", this);
					}
				}
			}

			// ====================== TUNNEL ===================== \\
			if((((getTempsTotal() - tpsD�butBoss) > TUNNEL) && ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL)) || (((getTempsTotal() - tpsD�butBoss) > TUNNEL_LENT2) && ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL2)) ||
					(((getTempsTotal() - tpsD�butBoss) > TUNNEL3) && ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL3)) || (((getTempsTotal() - tpsD�butBoss) > TUNNEL4) && ((getTempsTotal() - tpsD�butBoss) < FIN_TUNNEL4))) {
				//L'�cart entre les balles en largeur afin que cela ait une apparance de mur
				int ecartMur = NiveauInterface.TAILLE_BALLE_BOSS; 

				//Mettre cette limite sert � faire en sorte que pour le tunnel, le mur de droite ne soit pas sur le tableau
				int limiteBalle = NiveauInterface.LARGEUR_DECOR - 192;

				double toutAGauche = boss.getTranslateX()-50; //La balle la plus � gauche du BOSS
				double toutADroite;

				//Si le boss n'est pas proche du tableau qui va faire spawn des balles sur le tableau..
				if(boss.getTranslateX() <= limiteBalle)
					toutADroite = boss.getTranslateX()+120; //..tout se passe normal
				else
					toutADroite = NiveauInterface.LARGEUR_DECOR-35; //Sinon la limite c'est la fin du d�cor

				//Cr�ation des balles tout � gauche et droite
				new BossBalle(toutAGauche, boss.getTranslateY()+120, "Images/NiveauFinal/balle_b.png", "balle tunnel", this);

				if(boss.getTranslateX() <= limiteBalle) //Si le boss pas trop proche du tableau
					new BossBalle(toutADroite, boss.getTranslateY()+120, "Images/NiveauFinal/balle_b.png", "balle tunnel", this);

				//Cr�ation d'un mur de balles du milieu � la toute gauche [1er tunnel gauche]
				for(int i=0;i<toutAGauche;i+=ecartMur) {
					new BossBalle(i, boss.getTranslateY()+120, "Images/NiveauFinal/balle_b.png", "balle tunnel", this);
				}

				//De m�me pour le mur de balles du milieu � la toute droite [2�me tunnel gauche] si le boss n'est pas proche du tableau
				if(boss.getTranslateX() <= limiteBalle) {
					for(int i=NiveauInterface.LARGEUR_DECOR-35; i>toutADroite;i-=ecartMur) {
						new BossBalle(i, boss.getTranslateY()+120, "Images/NiveauFinal/balle_b.png", "balle tunnel", this);
					}
				}
			}

			// ====================== BALLES ALEATOIRES DE PARTOUT ===================== \\
			if((((getTempsTotal() - tpsD�butBoss) > BALLES_PARTOUT) && ((getTempsTotal() - tpsD�butBoss) < FIN_PARTOUT)) || (((getTempsTotal() - tpsD�butBoss) > BALLES_PARTOUT2) && ((getTempsTotal() - tpsD�butBoss) < FIN_PARTOUT2)) ||
					(((getTempsTotal() - tpsD�butBoss) > BALLES_PARTOUT3) && ((getTempsTotal() - tpsD�butBoss) < STOP_TIR))){
				ballesAl�atoiresPartout();

				if(((getTempsTotal() - tpsD�butBoss) > PLUS_PARTOUT) && ((getTempsTotal() - tpsD�butBoss) < BALLES_PARTOUT2)) {
					if(Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false)){
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

			// ====================== GAUCHE/DROITE RAPIDE ===================== \\
			if((((getTempsTotal() - tpsD�butBoss) > GAUCHE_DROITE) && ((getTempsTotal() - tpsD�butBoss) < FIN_GAUCHE_DROITE)) || (((getTempsTotal() - tpsD�butBoss) > GAUCHE_DROITE2) && ((getTempsTotal() - tpsD�butBoss) < FIN_GAUCHE_DROITE2))) {
				int ecartBalles = 100;

				if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.25, false) || Manipulation.siTpsA(getTemps(), 1.75, false)){
					//Cr�ation de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, "Images/NiveauFinal/balle_b.png", "balle gauche droite", this); //X al�atoire
					}
				}

				if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1, false) || Manipulation.siTpsA(getTemps(), 1.5, false) || Manipulation.siTpsA(getTemps(), 1.98, false)){
					//Cr�ation de la ligne de balles 
					for(int i = 50; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, "Images/NiveauFinal/balle_b.png", "balle gauche droite", this); //X al�atoire
					}
				}
			}
		}else if(phaseII && commenc� && !mort) { //PHASE II
			// ====================== TUNNEL ===================== \\
			if((((getTempsTotal() - tpsD�butBoss) > COMMENCE) && ((getTempsTotal() - tpsD�butBoss) < FIN_COMMENCE)) || (((getTempsTotal() - tpsD�butBoss) > RECOMMENCE) && ((getTempsTotal() - tpsD�butBoss) < FIN_RECOMMENCE)) || 
					(((getTempsTotal() - tpsD�butBoss) > RETOUR_TUNNEL) && ((getTempsTotal() - tpsD�butBoss) < PART)) || (((getTempsTotal() - tpsD�butBoss) > RETOUR2) && ((getTempsTotal() - tpsD�butBoss) < FIN_RETOUR2)) || 
					(((getTempsTotal() - tpsD�butBoss) > DERNIER_TUNNEL) && (getTempsTotal() - tpsD�butBoss) < FIN_DERNIER_TUNNEL)){
				//L'�cart entre les balles en largeur afin que cela ait une apparance de mur
				int ecartMur = NiveauInterface.TAILLE_BALLE_BOSS; 

				//Mettre cette limite sert � faire en sorte que pour le tunnel, le mur de droite ne soit pas sur le tableau
				int limiteBalle = NiveauInterface.LARGEUR_DECOR - 192;

				double toutAGauche = boss.getTranslateX()-50; //La balle la plus � gauche du BOSS
				double toutADroite;

				//Si le boss n'est pas proche du tableau qui va faire spawn des balles sur le tableau..
				if(boss.getTranslateX() <= limiteBalle)
					toutADroite = boss.getTranslateX()+120; //..tout se passe normal
				else
					toutADroite = NiveauInterface.LARGEUR_DECOR-35; //Sinon la limite c'est la fin du d�cor

				//Cr�ation des balles tout � gauche et droite
				new BossBalle(toutAGauche, boss.getTranslateY()+120, "Images/NiveauFinal/balle2.png", "balle tunnel II", this);

				if(boss.getTranslateX() <= limiteBalle) //Si le boss pas trop proche du tableau
					new BossBalle(toutADroite, boss.getTranslateY()+120, "Images/NiveauFinal/balle2.png", "balle tunnel II", this);

				//Cr�ation d'un mur de balles du milieu � la toute gauche [1er tunnel gauche]
				for(int i=0;i<toutAGauche;i+=ecartMur) {
					new BossBalle(i, boss.getTranslateY()+120,"Images/NiveauFinal/balle2.png", "balle tunnel II", this);
				}

				//De m�me pour le mur de balles du milieu � la toute droite [2�me tunnel gauche] si le boss n'est pas proche du tableau
				if(boss.getTranslateX() <= limiteBalle) {
					for(int i=NiveauInterface.LARGEUR_DECOR-35; i>toutADroite;i-=ecartMur) {
						new BossBalle(i, boss.getTranslateY()+120,  "Images/NiveauFinal/balle2.png", "balle tunnel II", this);
					}
				}
			}

			// ====================== MUR VERTICAUX ET HORIZONTAUX DE BALLES ===================== \\
			if(((getTempsTotal() - tpsD�butBoss) > MUR_BALLE) && ((getTempsTotal() - tpsD�butBoss) < FIN_MUR_BALLE)) {
				//Pas le m�me �cart sinon �a sera toujours le m�me mur avec les m�mes trous 
				int ecartBalles = (int) (Math.random() * 100)+100; //+100 �vite le ecartBalles = 0

				if(Manipulation.siTpsA(getTemps(), 0.98, false)) {
					//Cr�ation de la ligne de balles (De gauche � droite)
					for(int i = -ecartBalles+50; i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2; i+=ecartBalles) {
						new BossBalle(10, i, "Images/NiveauFinal/balle2.png", "balle mur vertical g", this); 
						new BossBalle(-20, i, "Images/NiveauFinal/balle2.png", "balle mur vertical g", this); 
					}
				}

				if(Manipulation.siTpsA(getTemps(), 1.98, false)) {
					//Et la ligne de droite � gauche
					for(int i = (-ecartBalles); i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2+10; i+=ecartBalles) {
						new BossBalle(NiveauInterface.LARGEUR_DECOR-70, i, "Images/NiveauFinal/balle2.png", "balle mur vertical d", this); 
						new BossBalle(NiveauInterface.LARGEUR_DECOR-40, i, "Images/NiveauFinal/balle2.png", "balle mur vertical d", this);
					}
				}

				//Plus le mur tombant de haut
				if(Manipulation.siTpsA(getTemps(), 1, false)){
					//Cr�ation de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, "Images/NiveauFinal/balle2.png", "mur balle", this); 
						new BossBalle(i, -40, "Images/NiveauFinal/balle2.png", "mur balle", this);
					}
				}
			}

			// ====================== ECLAIRS & BALLES ALEATOIRES PARTOUT ===================== \\
			if((((getTempsTotal() - tpsD�butBoss) > ECLAIRS) && ((getTempsTotal() - tpsD�butBoss) < FIN_ECLAIRS)) || (((getTempsTotal() - tpsD�butBoss) > ECLAIR) && ((getTempsTotal() - tpsD�butBoss) < FIN_ECLAIR))) {
				�clairs();

				//Balles al�atoires avec seulement dans ces temps l�
				if(((getTempsTotal() - tpsD�butBoss) > ECLAIRS) && ((getTempsTotal() - tpsD�butBoss) < FIN_ECLAIRS))
					ballesAl�atoiresPartout();

				if(((getTempsTotal() - tpsD�butBoss) > PLUS_BALLE) && ((getTempsTotal() - tpsD�butBoss) < FIN_ECLAIR))
					ballesAl�atoiresHaut();
			}

			//Suppression de l'avertissement de l'�clair pour pas qu'il reste
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, BALLES_TOMBENT, false)) 
				Manipulation.supprimer(this, avertissement);


			// ====================== BALLES ALEATOIRES DE HAUT VITESSE ALEATOIRE ===================== \\
			if(((getTempsTotal() - tpsD�butBoss) > BALLES_TOMBENT) && ((getTempsTotal() - tpsD�butBoss) < FIN_BALLES_TOMBENT))
				ballesAl�atoiresHaut();

			// ====================== BALLES FONCENT ===================== \\
			if(((getTempsTotal() - tpsD�butBoss) > FONCE) && ((getTempsTotal() - tpsD�butBoss) < FIN_FONCE)) {
				if(Manipulation.quatreFoisParSecondes(getTemps()))
					new BossBalle(joueur.getTranslateX(), -10,"Images/NiveauFinal/balle2.png", "balle fonce", this);
			}

			// ====================== BALLES ALEATOIRES DE BAS VITESSE ALEATOIRE ===================== \\
			if(((getTempsTotal() - tpsD�butBoss) > FIN_FONCE) && ((getTempsTotal() - tpsD�butBoss) < FIN_TREMBLES)) {
				double probabilit�Tir = 0.95;

				//Plus dur
				if(((getTempsTotal() - tpsD�butBoss) > BALLES_ALEATOIRES_HAUT_BAS))
					probabilit�Tir = 0.975;

				if(Math.random() > probabilit�Tir) { //Probabilit� d'un tir
					double rd = Math.random();

					//Pas la m�me vitesse des balles
					if(rd < 0.25) {
						if((getTempsTotal() - tpsD�butBoss) < GROSSES)
							new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN+15, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire haut vit1", this); //X al�atoire
						else //Une grosse balle
							new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN+15, 50, 50,"Images/NiveauFinal/balle2.png", "balle al�atoire haut vit1", this); //X al�atoire
					}else if(rd < 0.5)
						new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN+15, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire haut vit2", this); //X al�atoire
					else if(rd < 0.75)
						new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN+15, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire haut vit3", this); //X al�atoire
					else
						new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN+15, 20, 20, "Images/NiveauFinal/balle2.png", "balle al�atoire haut vit4", this); //X al�atoire
				}

				if((getTempsTotal() - tpsD�butBoss) > BALLES_ALEATOIRES_HAUT_BAS){ //Plus celles du haut
					ballesAl�atoiresHaut();
				}
			}

			// ====================== MUR BALLES ===================== \\
			if((getTempsTotal() - tpsD�butBoss)>RETOUR && (getTempsTotal() - tpsD�butBoss)<FIN_RETOUR) {
				if(Manipulation.deuxFoisParSecondes(getTemps())) {
					//Pas le m�me �cart sinon �a sera toujours le m�me mur avec les m�mes trous 
					int ecartBalles = (int) (Math.random() * 80)+80; //+70 �vite le ecartBalles = 0
					//Cr�ation de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, "Images/NiveauFinal/balle2.png", "mur balle", this);
						new BossBalle(i, -40, "Images/NiveauFinal/balle2.png", "mur balle", this);
					}
				}
			}

			//Suppression de l'avertissement de l'�clair pour pas qu'il reste
			if(Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, FIN_ECLAIR, false) || Manipulation.siTpsA(getTempsTotal() - tpsD�butBoss, ALEATOIRES, false)) {
				Manipulation.supprimer(this, avertissement);
				Manipulation.supprimer(this, avertissement2);
			}

			// ====================== ECLAIRS HORIZONTAUX ===================== \\
			if((getTempsTotal() - tpsD�butBoss)>HORIZONTAUX && (getTempsTotal() - tpsD�butBoss)<ALEATOIRES) {
				�clairsHorizontaux();

				//Plus des balles al�atoires de haut
				if((getTempsTotal() - tpsD�butBoss)>PLUS_ALEATOIRE && (getTempsTotal() - tpsD�butBoss)<FIN_ALEATOIRE)
					ballesAl�atoiresPartout();
			}

			// ====================== BALLES ALEATOIRES HAUT + MUR DE BALLES ===================== \\
			if((getTempsTotal() - tpsD�butBoss)>ALEATOIRES && (getTempsTotal() - tpsD�butBoss)<MURS) {
				ballesAl�atoiresPartout();

				//Mur de balles
				int ecartBalles = (int) (Math.random() * 150)+150; 

				//Et le mur tombant de haut
				if(Manipulation.siTpsA(getTemps(), 1, false)){
					//Cr�ation de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, NiveauInterface.HAUTEUR_ECRAN+10, "Images/NiveauFinal/balle2.png", "mur balle", this); 
						new BossBalle(i, NiveauInterface.HAUTEUR_ECRAN+40, "Images/NiveauFinal/balle2.png", "mur balle", this);
					}
				}
			}

			// ====================== MURS DE BALLES VENANT DE [HAUT ET DE BAS]/[PARTOUT] ===================== \\
			if((getTempsTotal() - tpsD�butBoss)>MURS && (getTempsTotal() - tpsD�butBoss)<FIN_MURS) {
				int ecartBalles = (int) (Math.random() * 80)+80; //+90 �vite le ecartBalles = 0

				//Et le mur tombant de bas
				if(Manipulation.siTpsA(getTemps(), 1, false)){
					//Cr�ation de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, NiveauInterface.HAUTEUR_ECRAN+10, "Images/NiveauFinal/balle2.png", "mur balle bas", this); 
						new BossBalle(i, NiveauInterface.HAUTEUR_ECRAN+40, "Images/NiveauFinal/balle2.png", "mur balle bas", this);
					}
				}

				//Murs de balles partout
				if((getTempsTotal() - tpsD�butBoss) > MUR_PARTOUT) {
					ecartBalles = (int) (Math.random() * 100)+100; //+90 �vite le ecartBalles = 0
					if(Manipulation.siTpsA(getTemps(), 0.98, false)) {
						//Cr�ation de la ligne de balles (De gauche � droite)
						for(int i = -ecartBalles+50; i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2; i+=ecartBalles) {
							new BossBalle(10, i, "Images/NiveauFinal/balle2.png", "balle mur vertical g", this); 
							new BossBalle(-20, i,"Images/NiveauFinal/balle2.png", "balle mur vertical g", this); 
						}
					}

					ecartBalles = (int) (Math.random() * 100)+100; //+90 �vite le ecartBalles = 0
					if(Manipulation.siTpsA(getTemps(), 1.98, false)) {
						//Et la ligne de droite � gauche
						for(int i = (-ecartBalles); i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2+10; i+=ecartBalles) {
							new BossBalle(NiveauInterface.LARGEUR_DECOR-70, i, "Images/NiveauFinal/balle2.png", "balle mur vertical d", this); 
							new BossBalle(NiveauInterface.LARGEUR_DECOR-40, i, "Images/NiveauFinal/balle2.png", "balle mur vertical d", this);
						}
					}
				}
			}

			// ====================== DERNIERE ATTAQUE (BALLES PARTOUT) ===================== \\
			if((getTempsTotal() - tpsD�butBoss) > DERNIERE_ATTAQUE)
				ballesAl�atoiresPartout();
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
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_DIFFICULTE, "DIFFICULTE -- MAX", "Polices/SCORE.ttf", 40, "RED");
			else
				Manipulation.ajouterTexte(this, NiveauInterface.POSITION_X_STATS, NiveauInterface.POSITION_Y_STATS_DIFFICULTE, "DIFFICULTE -- " + Main.getDifficult�().toString(), "Polices/SCORE.ttf", 40, "YELLOW");
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
	public void start(Stage NIVFINAL) throws Exception {
		Scene niveau_FINAL = new Scene(createNiveau(NIVFINAL)); //Cr�ation du niveau

		NIVFINAL.setScene(niveau_FINAL); //La sc�ne du niveau devient la sc�ne principale
		NIVFINAL.setTitle("NIVEAU FINAL"); //Nom de la fen�tre
		NIVFINAL.setResizable(false); //On ne peut pas redimensionner la fen�tre

		//On remet l'ic�ne du jeu comme elle a chang�e car c'est une autre f�netre
		NIVFINAL.getIcons().add(new Image("file:Images/Icone.png"));

		Manipulation.updateScore(this); //Met � jour le score pour le d�but du niveau

		NIVFINAL.show(); //Affichage du niveau

		/*On g�re les boutons tap�s (Contr�les du jeu) 
		 *[Quand on APPUIE sur la touche]
		 */
		niveau_FINAL.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(!isEnDialogue()) { //Quand c'est en dialogue, le joueur ne joue pas
					switch (event.getCode()) { //R�cup�ration de quelle touche appuy�e
					case UP:    setBougeHaut(true); break;
					case DOWN:  setBougeBas(true); break;
					case LEFT:  setBougeGauche(true); break;
					case RIGHT: setBougeDroite(true); break;
					case SPACE: if(commenc� && isPeutTirer()) setTir�(true); break; //Lors de la phase I, le joueur ne tire pas
					case SHIFT: setFocus(true); break;
					default:
						break;
					}
				}if(isPeutQuitter()) { //A la fin du niveau
					switch(event.getCode()) {
					case SPACE:
						NIVFINAL.close();
						mediaPlayer.stop();
						
						//La suite
						try {
							Main.sauvegarde(7);
						} catch (IOException e) {
							System.err.println("Erreur lors de la sauvegarde de l'�tat 7!");
						}
						
						
						Alert alerte = new Alert(AlertType.INFORMATION); 
						alerte.setTitle("ZEROD ABATTU !"); //Le titre de la f�netre
						alerte.setHeaderText("..."); //Au dessus du texte
						alerte.setContentText("?"); //Le texte

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
		niveau_FINAL.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(!isEnDialogue()) { //Quand c'est en dialogue, le joueur ne joue pas
					switch (event.getCode()) { //R�cup�ration de quelle touche appuy�e
					case UP:    setBougeHaut(false); break;
					case DOWN:  setBougeBas(false); break;
					case LEFT:  setBougeGauche(false); break;
					case RIGHT: setBougeDroite(false); break;
					case SPACE: if(commenc�) setTir�(false); break; //Lors de la phase I, le joueur ne tire pas
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
		root.setPrefSize(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); //Met � jour les dimensions du jeu

		// ========================= Image de fond =========================
		try {
			imageFond = new ImageView(new Image(new FileInputStream("Fonds/Niveau_FINAL/bg_debut.gif")));
			imageFond.setX(-15); 
			imageFond.setY(0);
			imageFond.setFitHeight(NiveauInterface.HAUTEUR_ECRAN+40); //Change la taille
			imageFond.setFitWidth(NiveauInterface.LARGEUR_ECRAN); 
			root.getChildren().add(imageFond);
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image du fond du niveau n'a pas �t� trouv�e !", e);	
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

	@Override
	public void miniBOSSTir(BOSS b) throws FileNotFoundException {}

	@Override
	public void boss() throws FileNotFoundException {
		//Pour effacer tout ce qui est dialogue
		Manipulation.toutEffacer(this);

		//Mise � jour du fond
		imageFond = new ImageView(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_1.gif")));
		root.getChildren().add(imageFond);

		Manipulation.ajouterImage(getRoot(),"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

		//On r�affiche les personnages
		Manipulation.ajouterJeu(getRoot(), joueur.getImg());
		Manipulation.ajouterJeu(getRoot(), boss.getImg());

		//On enl�ve puis remet la barre de vie 
		Manipulation.supprimer(this, boss.getBarreVie());
		Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

		Manipulation.updateScore(this); //On remet le score

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

	@Override
	public void dialogue() throws FileNotFoundException {
		super.dialogue();
		//Dialogue avant le d�but du boss
		if(!phaseI && !phaseII) {
			switch(getNbEspaceAppuy�s()) {
			case 0:
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Col�re.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/debut.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Te voici enfin.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1: 
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C'est donc toi derriere tout ca.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Totalement.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu te demandes... Pourquoi ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 4:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourquoi ? Cette guerre.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 5:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Depeches de t'expliquer.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 6:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Hmhmhm... Nous les humains...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 7:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Sommes toujours egoistes.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 8:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Toi et moi nous le savons...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 9:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "La soif de puissance a toujours...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 10:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ete d'actualite.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 11:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Que racontes-tu ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 12:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Chaque personne veut toujours...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 13:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "etre plus fort.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 14:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Et cela a n'importe quel prix.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 15:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Vous les totzuzens...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 16:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Vous etes bien plus puissants que nous,", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 17:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "simples humains.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 18:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Et alors ? Est-ce qu'on vous...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 19:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...a deja attaque ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 20:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Non, mais vous le pouvez n'importe quand.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 21:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourquoi on le ferait ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 22:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Une simple querelle pourrait declencher...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 23:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Une embrouille et donc un meurtre.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 24:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Vu votre puissance, vous tuez sans..", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 25:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "vous en rendre compte.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 26:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ces gens la sont juste fous.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 27:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Nous sommes loins d'etre comme ca.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 28:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Peu importe. Nous vivons dans la crainte.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 29:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je comprends. Mais lancer une guerre...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 30:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Etait-elle reellement la solution ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 31:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tuer des MILLIERS d'innocents...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 32:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "ETAIT-ELLE LA SOLUTION ????", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 33:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Oui. Car nous allons dominer le monde.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 34:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je vois. T'es donc fou.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 35:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Vois moi comme tu veux...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 36:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Mais ce que je vois chez toi..", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 37:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C'EST TA MORT IMMINENTE.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;
			}

			if(getNbEspaceAppuy�s() > 37) { //Fin du dialogue
				enTransition = true;
				setEnDialogue(false); 
			}
		}else if(phaseII && !mort) { //Dialogue entre les deux phases
			switch(getNbEspaceAppuy�s()) {
			case 0:
				//On le remet � 9999 comme �a le temps ne "s'�coule pas"
				tpsD�butBoss = 9999;

				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Col�re.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "????!!!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Qu...Qu'est-ce que...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "CA VEUT DIRE ??!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Hmhmhmhmhm...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 4:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Alors toi aussi...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 5:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu es un des notres ?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 6:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "En effet.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 7:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ne t'excite pas, Salar...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 8:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "La raison est logique.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 9:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "QUE RACONTES-TU ?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 10:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "EN QUOI CELA EST LOGIQUE,", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 11:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "D'EXTERMINER TA PROPRE ESPECE ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 12:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai ete deteste.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 13:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai ete rejete.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 14:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ma famille ma mis a la porte.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 15:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ma famille m'a tue.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 16:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Mon pere a tue mon frere...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 17:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ma mere a tuee mes deux soeurs...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 18:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai ete detruit.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 19:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "PAR MA PROPRE RACE.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 20:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ta famille devait etre exterminee.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 21:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pas NOUS, Zerod.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 22:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "SI. VOUS ETES TOUS LES MEMES.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 23:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "VOUS ETES ASSOIFES DE PUISSANCE.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 24:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "CETTE DECHIRURE ENTRE LES HUMAINS...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 25:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "N'A CREE QUE DE LA HAINE.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 26:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "JE SERAIS CELUI...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 27:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "QUI RETABLIRA UNE SEULE ESPECE !!!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 28:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "???", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 29:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "VOUS les TOTSUZENS...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 30:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Allez disparaitre.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 31:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Il ne restera que les humains normaux.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 32:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Dont moi le chef, possedant des pouvoirs.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 33:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu souhaites le chaos, Zerod.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 34:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Peut-etre que c'est le chaos apres tout,", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 35:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "ce que je recherche.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 36:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Mais cette race. Des totsuzens...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 37:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ont tues mon frere et mes soeurs.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 38:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je vois. La haine t'aveugle.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 39:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ils ont tues ton frere et tes soeurs..", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 40:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Toi. Tu as tue ma famille.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 41:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "ET ALORS ??? TU LES REJOINDRAS.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 42:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Zerod...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 43:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ton plan s'acheve ici.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 44:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Certes, tu as tue tous les totsuzens...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 45:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Mais tu ne me tueras pas moi,", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 46:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "LE DERNIER TOTSUZEN!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 47:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "HAHAHAHAHAHAHAHAHA", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 48:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "A  P  P  R  O  C  H  E  !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit

				//Fin de la musique
				mediaPlayer.stop();

				//On charge la musique du boss
				mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveauFINAL/phaseII.mp3").toURI().toString()));
				break;
			}

			if(getNbEspaceAppuy�s() > 48) { //Fin du dialogue
				enTransition = true;
				setEnDialogue(false); 
				finDialogue = true; //Fin du dialogue, le combat commence

			}
		}else { //Mort de Zerod
			switch(getNbEspaceAppuy�s()) {
			case 0:
				//Toutes les balles ennemies meurent
				Manipulation.sprites(root).stream().filter(e -> e.getType().contains("balle")).forEach(balle -> {
					balle.meurs();
				});

				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Col�re.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C'est fini... Zerod.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Argh... C.. Comment est-ce possible ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Zerod... Ton idee etait mauvaise.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai tente de te raisonner...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 4:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je n'y suis pas parvenu.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 5:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "ET DONC ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 6:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Laisse moi... Retablir la paix.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 7:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "LA PAIX N'EXISTERA JAMAIS DANS UN...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 8:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "DANS UN MONDE SI DESEQUILIBRE !!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 9:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Si. C'est toi l'origine du desequilibre.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 10:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Avant toi... Il n'y avait pas de problemes.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 11:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 12:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu as agis seulement par vengeance.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 13:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu n'avais qu'a tuer seulement ta famille.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 14:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Et NON NOTRE ESPECE !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 15:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tss. Hahahaha !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 16:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Peu importe. J'ai atteint mon objectif.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 17:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Te voila maintenant le seul Totsuzen.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 18:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai plus rien a accomplir.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 19:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Enflure...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 20:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "La paix... N'existera jamais...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 21:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, " ", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Zerod tombe raide.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 22:
				Manipulation.effacerDialogue(this); //"Efface" le texte pr�c�dent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tss.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;
			}

			if(getNbEspaceAppuy�s() > 22) { //Fin du dialogue et du niveau.
				setEnDialogue(false); 
				setNiveauTermin�(true);
				setTpsD�bScore(getTempsTotal());
			}
		}
	}

	/**
	 * La transition o� Zerod tremble
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void transition() throws FileNotFoundException {
		//Retire le dialogue et affiche le combat de la phase I 
		if(!phaseI && !phaseII && !lockFinDialogue) {
			//Pour effacer tout ce qui est dialogue
			setEnDialogue(false);
			Manipulation.toutEffacer(this);

			imageFond = new ImageView(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_debut_transition.gif")));
			root.getChildren().add(imageFond);

			Manipulation.ajouterImage(getRoot(),"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

			//On r�affiche les personnages
			Manipulation.ajouterJeu(getRoot(), joueur.getImg());
			Manipulation.ajouterJeu(getRoot(), boss.getImg());

			//On enl�ve puis remet la barre de vie 
			Manipulation.supprimer(this, boss.getBarreVie());
			Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

			Manipulation.updateScore(this); //On remet le score

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

			mediaPlayer.stop();

			//Son de la transition
			mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveauFINAL/transition.mp3").toURI().toString()));
			if(Main.son()) mediaPlayer.play();

			tpsD�butTransition = getTempsTotal(); //D�but de la transition pour pouvoir l'arr�ter � temps dans niveau()

			lockFinDialogue = true;
		}if(phaseII && !lockFinDialogue) { //Retire le dialogue et affiche le combat de la phase II
			//Pour effacer tout ce qui est dialogue
			setEnDialogue(false);
			Manipulation.toutEffacer(this);

			imageFond = new ImageView(new Image(new FileInputStream("Fonds/Niveau_FINAL/Rien.png")));
			imageFond.setX(-15); 
			imageFond.setY(0);
			imageFond.setFitHeight(NiveauInterface.HAUTEUR_ECRAN+40); //Change la taille
			imageFond.setFitWidth(NiveauInterface.LARGEUR_ECRAN-200); 
			root.getChildren().add(imageFond);

			Manipulation.ajouterImage(getRoot(),"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

			//On r�affiche les personnages
			Manipulation.ajouterJeu(getRoot(), joueur.getImg());
			Manipulation.ajouterJeu(getRoot(), boss.getImg());

			//On enl�ve puis remet la barre de vie 
			Manipulation.supprimer(this, boss.getBarreVie());
			Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

			Manipulation.updateScore(this); //On remet le score

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

			tpsD�butTransition = getTempsTotal(); //D�but de la transition pour pouvoir l'arr�ter � temps dans niveau()

			lockFinDialogue = true;
		}if(commenc�) { //Zerod tremble (En mode enerv�)
			double nb = Math.random();


			if(nb < 0.25) { //Droite
				boss.setTranslateX(boss.getTranslateX()+5);
				boss.updateImagePos();
				boss.setTranslateX(boss.getTranslateX()-5);
			}else if(nb < 0.5) { //Bas
				boss.setTranslateY(boss.getTranslateY()+5);
				boss.updateImagePos();
				boss.setTranslateY(boss.getTranslateY()-5);
			}else if (nb < 0.75) { //Gauche
				boss.setTranslateX(boss.getTranslateX()-5);
				boss.updateImagePos();
				boss.setTranslateX(boss.getTranslateX()+5);
			}else if (nb > 0.75) { //Haut
				boss.setTranslateY(boss.getTranslateY()-5);
				boss.updateImagePos();
				boss.setTranslateY(boss.getTranslateY()+5);
			}
		}
		double nb = Math.random();
		//Transition normale
		if((getTempsTotal() - tpsD�butBoss) < STOP){
			//Zerod tremble (Change la position puis remet o� il �tait initialement pour le prochain)

			if(nb < 0.25) { //Droite
				boss.setTranslateX(boss.getTranslateX()+5);
				boss.updateImagePos();
				boss.setTranslateX(boss.getTranslateX()-5);
			}else if(nb < 0.5) { //Bas
				boss.setTranslateY(boss.getTranslateY()+5);
				boss.updateImagePos();
				boss.setTranslateY(boss.getTranslateY()-5);
			}else if (nb < 0.75) { //Gauche
				boss.setTranslateX(boss.getTranslateX()-5);
				boss.updateImagePos();
				boss.setTranslateX(boss.getTranslateX()+5);
			}else if (nb > 0.75) { //Haut
				boss.setTranslateY(boss.getTranslateY()-5);
				boss.updateImagePos();
				boss.setTranslateY(boss.getTranslateY()+5);
			}
		}else { //Zerod tremble beaucoup (� la fin)
			if(nb < 0.25) { //Droite
				boss.setTranslateX(boss.getTranslateX()+20);
				boss.updateImagePos();
				boss.setTranslateX(boss.getTranslateX()-20);
			}else if(nb < 0.5) { //Bas
				boss.setTranslateY(boss.getTranslateY()+20);
				boss.updateImagePos();
				boss.setTranslateY(boss.getTranslateY()-20);
			}else if (nb < 0.75) { //Gauche
				boss.setTranslateX(boss.getTranslateX()-20);
				boss.updateImagePos();
				boss.setTranslateX(boss.getTranslateX()+20);
			}else if (nb > 0.75) { //Haut
				boss.setTranslateY(boss.getTranslateY()-20);
				boss.updateImagePos();
				boss.setTranslateY(boss.getTranslateY()+20);
			}
		}
	}

	/**
	 * Les �clairs mais horizontaux
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void �clairsHorizontaux() throws FileNotFoundException{
		double random = Math.random();

		if((Manipulation.siTpsA(getTemps(), 0.05, false)) || (Manipulation.siTpsA(getTemps(), 1.05, false))) {
			avertissement2.setFill(Color.RED); //Avertissement rouge
			avertissement2.setOpacity(0); //Au d�but invisible
			Manipulation.ajouterJeu(root, avertissement2);

			//Quel endroit ? (33% chacun)
			if(random < 0.33)
				endroit = 0; //0: Tout � gauche
			else if(random > 0.34 && random < 0.67)
				endroit = 1; //1: Au milieu
			else
				endroit = 2; //2: Tout � droite

			//On met � l'endroit choisi au hasard
			if(endroit==0)
				avertissement2.relocate(0, 0);
			else if (endroit==1)
				avertissement2.relocate(0, tiers_�cran2);
			else
				avertissement2.relocate(0, tiers_�cran2*2);
		}

		// ======= APPARITION DE L'AVERTISSEMENT PROGRESSIF ======= \\
		if((Manipulation.siTpsA(getTemps(), 0.3, false)) || (Manipulation.siTpsA(getTemps(), 1.3, false)))
			avertissement2.setOpacity(0);

		if((Manipulation.siTpsA(getTemps(), 0.4, false)) || (Manipulation.siTpsA(getTemps(), 1.4, false)))
			avertissement2.setOpacity(0.15);

		if((Manipulation.siTpsA(getTemps(), 0.5, false)) || (Manipulation.siTpsA(getTemps(), 1.5, false)))
			avertissement2.setOpacity(0.25);

		if((Manipulation.siTpsA(getTemps(), 0.6, false)) || (Manipulation.siTpsA(getTemps(), 1.6, false)))
			avertissement2.setOpacity(0.35);

		if((Manipulation.siTpsA(getTemps(), 0.7, false)) || (Manipulation.siTpsA(getTemps(), 1.7, false)))
			avertissement2.setOpacity(0.5);

		if((Manipulation.siTpsA(getTemps(), 0.8, false)) || (Manipulation.siTpsA(getTemps(), 1.8, false)))
			avertissement2.setOpacity(0.75);

		//Fin de l'avertissement
		if((Manipulation.siTpsA(getTemps(), 0.9, false)) || (Manipulation.siTpsA(getTemps(), 1.9, false)))
			Manipulation.supprimer(this, avertissement2);

		//L'�clair apparait!
		if((Manipulation.siTpsA(getTemps(), 0.91, false)) || (Manipulation.siTpsA(getTemps(), 1.91, false)))
			new EnnemiBalle(0,endroit*tiers_�cran2, NiveauInterface.LARGEUR_ECRAN, tiers_�cran2, "Images/�clair2.png", "balle �clair2", this); //Va vers le joueur par la droite
	}
}
