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
	private static final double CREATION_BOSS = 0.2; //Le boss SE crée
	private static final double BOSS_CREE = 1; //Le boss a été crée
	private static final double FIN_DESCENTE_BOSS = 8; //Le boss arrête de descendre
	private static final double TEMPS_TRANSITION = 3; //Temps de la transition

	//===================== TIMING DU BOSS EN SECONDES [PHASE I] ===================\\
	private static final double DIALOGUE_1 = 0; //Dialogue 1
	private static final double DIALOGUE_2 = 3; //Dialogue 2
	private static final double DEBUT = 6; //Commencement
	private static final double FIN_DEBUT = 9; //Fin de la première attaque
	private static final double TRANSITION_DEBUT = 10; //Transition
	private static final double TIR_PARTOUT = 11; //Il tire de partout
	private static final double FIN_TIR = 19.5; //Fin de la deuxième attaque
	private static final double TRANSITION_TIR = 20; //Transition
	private static final double BALLES_GAUCHE_DROITE = 21.5; //Balles aléatoires sortant de gauche/droite
	private static final double FIN_BALLES = 25.5; //Fin de l'attaque
	private static final double TRANSITION_BALLES = 25.5; //Transition
	private static final double TIR_PARTOUT2 = 26.5; //Tire de partout une fois de plus 
	private static final double FIN_TIR2 = 31; //Tire de partout une fois de plus 
	private static final double TRANSITION_TIR2 = 30.5; //Transition
	private static final double SPIRALE = 31.25; //Attaque en spirale avec balles aléatoires gauche & droite
	private static final double FIN_SPIRALE = 39; //Fin de l'attaque
	private static final double MUR = 40; //Des murs de balles verticaux
	private static final double FIN_MUR = 42; //Fin de l'attaque
	private static final double TRANSITION_MUR = 41; //Transition
	private static final double TUNNEL = 42; //Tunnel
	private static final double TUNNEL_LENT = 52; //Tunnel mais ralenti
	private static final double FIN_TUNNEL = 55; //Fin du tunnel
	private static final double TRANSITION_TUNNEL = 55; //Transition
	private static final double BALLES_PARTOUT = 56.5; //Des balles aléatoires sortant de partout
	private static final double PLUS_PARTOUT = 67; //En plus le boss va tirer dans toutes les directions
	private static final double FIN_PARTOUT = 74.5; //Fin de l'attaque
	private static final double TRANSITION_PARTOUT = 76; //Transition
	private static final double GAUCHE_DROITE = 77; //Mur de balles où le joueur devra faire Gauche/Droite rapidement
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
	private static final double BALLES_PARTOUT2 = 118; //Balles aléatoires de tous les côtés
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
	private static final double DEBUT3 = 194.5; //Balles tombent de haut très vite
	private static final double FIN_DEBUT3 = 198.5; //Fin de l'attaque
	private static final double BALLES_PARTOUT3 = 199.5; //Balles aléatoires de tous les côtés
	private static final double TREMBLE = 205; //Zerod tremble
	private static final double STOP_TIR = 206.5; //Il ne tire plus
	private static final double STOP = 207; //Il ne tire plus et tremble énormément
	private static final double FIN = 210; //Fin
	private static final double DIALOGUE_FIN = 212; //Dialogue après l'armure cassée de Zerod

	//===================== TIMING DU BOSS EN SECONDES [PHASE II] ===================\\
	private static final double APPARITION_NOM_NIVEAU = 0.1; //Le nom du niveau s'affiche
	private static final double NOM_NIVEAU_EN_APPARITION = 2; //Le nom du niveau apparaît
	private static final double NOM_NIVEAU_EN_DISPARITION = 4; //Le nom du niveau disparaît
	private static final double NOM_NIVEAU_EN_DISPARITION2 = 6; //Le nom du niveau disparaît plus vite
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
	private static final double ECLAIRS = 40; //Eclairs & balles aléatoires partout
	private static final double FIN_ECLAIRS = 48; //Fin
	private static final double BALLES_TOMBENT = 50.75; //Balles aléatoires de haut vitesse aléatoire 
	private static final double FIN_BALLES_TOMBENT = 56.25; //Fin
	private static final double FONCE = 58.25; //Balles qui foncent
	private static final double FIN_FONCE = 66; //Fin et balles aléatoires de bas vitesse aléatoire
	private static final double BALLES_ALEATOIRES_HAUT_BAS = 73.75; //Plus balles aléatoires de haut
	private static final double GROSSES = 81.5; //Des grosses apparaîssent
	private static final double TREMBLES = 89; //Et tremblent
	private static final double FIN_TREMBLES = 95; //Et tremblent
	private static final double DIALOGUE_7 = 97; //Zerod parle
	private static final double FIN_DIALOGUE_7 = 98; //Fin dialogue
	private static final double RETOUR = 99; //Retour en force w/Mur de balles
	private static final double FIN_RETOUR = 118.5; //Fin du mur de balles
	private static final double RETOUR_TUNNEL = 119.5; //Tunnel rapide
	private static final double PART = 137; //Zerod part
	private static final double ECLAIR = 139; //Que des éclairs
	private static final double PLUS_BALLE = 147.5; //Plus balles aléatoires de haut avec vitesse aléatoire
	private static final double FIN_ECLAIR = 154.5; //Fin
	private static final double HORIZONTAUX = 155.5; //Eclairs horizontaux
	private static final double PLUS_ALEATOIRE = 161; //Plus des balles aléatoires de partout
	private static final double FIN_ALEATOIRE = 169; //Fin des balles aléatoires de partout
	private static final double ALEATOIRES = 186; //Balles aléatoires de haut & mur de balles
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
	private static final double DERNIERE_ATTAQUE = 296; //Fin, dernière attaque

	private static final int VIE_BOSS = 565; //Vie du boss
	public static double DEGAT_BALLE_JOUEUR = 1; //Les dégâts que va affliger le joueur (Phase II)

	//Il faut combien de points pour avoir le rang ?
	private static final int RANG_SALAR = 24280;
	private static final int RANG_S = 24000;
	private static final int RANG_A = 20000;
	private static final int RANG_B = 15000;

	// ================================== Manipulations pour charger le son  ==================================
	private static Media son = new Media(new File("Sons/niveauFINAL/debut.mp3").toURI().toString());
	private static MediaPlayer mediaPlayer = new MediaPlayer(son);

	// ================================== Objets qui vont permettre l'affichage: Tout ce qu'on ajoute dedans sera affiché ==================================
	private static ImageView imageFond = new ImageView();

	// ================================== Variables qui gèrent le boss ==================================
	private static BOSSFINAL boss = null; //Le BOSS
	private static boolean phaseI = false; //Actuellement contre la phase I?
	private static boolean phaseII = false; //Actuellement contre la phase II? (Dernière)
	private static boolean commencé = false; //Le combat a-t-il commencé ? (Contre la phase II)
	private boolean bossVersDroite = false; //Le boss bouge-t-il vers la droite ? (Pour gérer son mouvement)
	private static String nomBOSS = "ZEROD";
	private double tpsDébutBoss = 0; //A quel moment le combat contre le boss commence ?
	private boolean lockFinDialogue = false;
	private double vieActuelle = VIE_BOSS; //Vie du boss
	private boolean mort = false; //Mort du boss
	private boolean bossPrésent = false; //Le boss est-t-il ici ?
	private boolean finDialogue = false; //Le dialogue entre les deux phases est-t-il terminé ?

	// ================================== Variables qui gèrent l'attaque des éclairs ==================================
	private int tiers_écran = 200; //Le tiers d'un écran en largeur [L'écran du jeu fait 600px]
	private int tiers_écran2 = 266; //Le tiers d'un écran en hauteur [L'écran du jeu fait 800px]
	private Rectangle avertissement = new Rectangle(tiers_écran,NiveauInterface.HAUTEUR_ECRAN+10); //La zone rouge qui averti l'emplacement de l'éclair
	private Rectangle avertissement2 = new Rectangle(NiveauInterface.LARGEUR_DECOR-15, tiers_écran2); //La zone rouge qui averti l'emplacement de l'éclair
	private int endroit; //L'endroit aléatoire où va apparaître l'éclair

	// ================================== Variables qui gèrent la transition ==================================
	private boolean enTransition = false; //En transition de la fin du dialogue vers le combat
	private double tpsDébutTransition = 999;

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

		//Le boss perd de la vie
		if(phaseI && (getTempsTotal() - tpsDébutBoss)>DEBUT)
			boss.perdreVie(0.0125);

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

				//N: Nord, O: ouest...
			case "balle N":
				if((getTempsTotal() - tpsDébutBoss) > PLUS_PARTOUT) //Quand il tire en spirale
					s.bougerHaut(0.3);
				else
					s.bougerHaut(0.15);
				break;


			case "balle NO":
				if((getTempsTotal() - tpsDébutBoss) > PLUS_PARTOUT) { //Quand il tire en spirale
					s.bougerHaut(0.3);
					s.bougerDroite(0.3);	
				}else {
					s.bougerHaut(0.15);
					s.bougerDroite(0.15);	
				}
				break;

			case "balle O":
				if((getTempsTotal() - tpsDébutBoss) > PLUS_PARTOUT) //Quand il tire en spirale
					s.bougerDroite(0.3);
				else
					s.bougerDroite(0.15);
				break;

			case "balle OS":
				s.bougerDroite(0.3);
				s.bougerBas(0.3);
				break;

			case "balle S":
				if((getTempsTotal() - tpsDébutBoss) < SPIRALE+1 || (getTempsTotal() - tpsDébutBoss) > TIR_PARTOUT3) { //Quand il tire partout
					s.bougerBas(3);
				}else if((getTempsTotal() - tpsDébutBoss) > PLUS_PARTOUT){ //Quand il tire en spirale
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
				if((getTempsTotal() - tpsDébutBoss) > PLUS_PARTOUT) //Quand il tire en spirale
					s.bougerGauche(0.3);
				else
					s.bougerGauche(0.15);

				break;

			case "balle NE":
				if((getTempsTotal() - tpsDébutBoss) > PLUS_PARTOUT) { //Quand il tire en spirale
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
				if((getTempsTotal() - tpsDébutBoss) > RETOUR && (getTempsTotal() - tpsDébutBoss) < RETOUR_TUNNEL)
					s.bougerBas(0.075); //Retour du boss
				else if ((getTempsTotal() - tpsDébutBoss) > MURS && (getTempsTotal() - tpsDébutBoss) < MUR_PARTOUT)
					s.bougerBas(0.125);  //Murs de balles de bas et de haut
				else if ((getTempsTotal() - tpsDébutBoss) > MUR_PARTOUT)//Partout
					s.bougerBas(0.25); 
				else //Début du boss
					s.bougerBas(0.2); 
				break;

				//Mur de balles de bas
			case "mur balle bas":
				if((getTempsTotal() - tpsDébutBoss) > MUR_PARTOUT) //Murs de balles partout
					s.bougerHaut(0.2); 
				else //Que le mur de balle venant de haut et bas
					s.bougerHaut(0.125); 
				break;

				//Balle des tunnel
			case "balle tunnel":
				if(((getTempsTotal() - tpsDébutBoss) < TUNNEL_LENT) || (((getTempsTotal() - tpsDébutBoss) > TUNNEL3) &&((getTempsTotal() - tpsDébutBoss) < TUNNEL_RAPIDE)))
					s.bougerBas(0.075); //Vitesse normale
				else if(((getTempsTotal() - tpsDébutBoss) > TUNNEL_LENT && ((getTempsTotal() - tpsDébutBoss) < TUNNEL_RAPIDE)) || ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL2) || ((getTempsTotal() - tpsDébutBoss) > TUNNEL_LENT3)) //Si tunnel au ralenti
					s.bougerBas(0.2); 
				else //Tunnel rapide (Ou tunnel en phase II)
					s.bougerBas(0.06); 
				break;

			case "balle tunnel II":
				if((((getTempsTotal() - tpsDébutBoss) > RALENTI) && ((getTempsTotal() - tpsDébutBoss) < FIN_RALENTI)) || (((getTempsTotal() - tpsDébutBoss) > RERALENTI) && ((getTempsTotal() - tpsDébutBoss) < FIN_RERALENTI)))
					s.bougerBas(0.2); //Ralenti
				else if((getTempsTotal() - tpsDébutBoss) > RETOUR_TUNNEL) //Rapide
					s.bougerBas(0.07);
				else //Tunnel normal
					s.bougerBas(0.075); 
				break;

			case "balle aléatoire bas": //Balles aléatoire allant en bas
				if((getTempsTotal() - tpsDébutBoss) < BALLES_PARTOUT) //Pour l'attaque du début
					s.bougerBas(1);
				else if((getTempsTotal() - tpsDébutBoss) < DEBUT2 || (((getTempsTotal() - tpsDébutBoss) > BALLES_PARTOUT2) && ((getTempsTotal() - tpsDébutBoss) < TIR_PARTOUT3)) || (getTempsTotal() - tpsDébutBoss) > BALLES_PARTOUT3) //Pour les balles aléatoires de partout
					s.bougerBas(3.5);
				else if ((getTempsTotal() - tpsDébutBoss) > DEBUT2 && (getTempsTotal() - tpsDébutBoss) < TRANSITION_DEBUT2)
					s.bougerBas(0.75); //Pour l'attaque du début accélérée
				else
					s.bougerBas(0.5); //Pour l'attaque du début très vite
				break;


			case "balle aléatoire bas II":
				s.bougerBas(4);
				break;

			case "balle aléatoire haut": //Balles aléatoire allant en haut
				if(!commencé)
					s.bougerHaut(3.5);
				else
					s.bougerHaut(4);
				break;

			case "balle aléatoire gauche": //Balles aléatoire allant à gauche
				if(!commencé)
					s.bougerGauche(3.5);
				else
					s.bougerGauche(4);
				break;

			case "balle aléatoire droite": //Balles aléatoire allant à droite
				if(!commencé)
					s.bougerDroite(3.5);
				else
					s.bougerDroite(4);
				break;

			case "balle vise gauche": //Balles viseuses allant à gauche
				s.bougerGauche(0.75);
				break;

			case "balle vise droite": //Balles viseuses allant à droite
				s.bougerDroite(0.75);
				break;

			case "balle vise haut": //Balles viseuses allant en haut
				s.bougerHaut(0.75);
				break;

			case "balle vise bas": //Balles viseuses allant en bas
				s.bougerBas(0.75);
				break;

			case "balle éclair": //Eclair
				s.bougerBas(0.01); //Très vite [Flash]
				break;

			case "balle éclair2": //Eclair horizontal
				s.bougerGauche(0.01); //Très vite [Flash]
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

			case "balle gauche droite": //Gauche/Droite rapide
				s.bougerBas(0.07);
				break;

			case "balle aléatoire bas vit1":
				if((getTempsTotal() - tpsDébutBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerBas(2.5);
				break;

			case "balle aléatoire bas vit2":
				if((getTempsTotal() - tpsDébutBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerBas(2);
				break;

			case "balle aléatoire bas vit3":
				if((getTempsTotal() - tpsDébutBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerBas(1.5);
				break;

			case "balle aléatoire bas vit4":
				if((getTempsTotal() - tpsDébutBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				if((getTempsTotal() - tpsDébutBoss)<BALLES_ALEATOIRES_HAUT_BAS)
					s.bougerBas(1);
				else //Comme y'a celles du bas aussi c'est + difficile
					s.bougerBas(1.25);
				break;

			case "balle aléatoire haut vit1":
				if((getTempsTotal() - tpsDébutBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerHaut(1);
				break;

			case "balle aléatoire haut vit2":
				if((getTempsTotal() - tpsDébutBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerHaut(1.5);
				break;

			case "balle aléatoire haut vit3":
				if((getTempsTotal() - tpsDébutBoss) > TREMBLES) {
					if(Manipulation.huitFoisParSecondes(getTemps())) {
						if(Math.random()>0.5)
							s.bougerGauche(3);
						else
							s.bougerDroite(3);
					}
				}

				s.bougerHaut(2);
				break;

			case "balle aléatoire haut vit4":
				if((getTempsTotal() - tpsDébutBoss) > TREMBLES) {
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
					if(((getTempsTotal() - tpsDébutBoss)>DEBUT) && (getTempsTotal() - tpsDébutBoss)<FIN)
						boss.perdreVie(0.03);

					//Quand il tire en tunnel il tire avec une autre fréquence (Donc l'expression booléenne est "Quand il n'attaque pas en tunnel")
					if(((getTempsTotal() - tpsDébutBoss) < TUNNEL) || (((getTempsTotal() - tpsDébutBoss) > FIN_TUNNEL) && ((getTempsTotal() - tpsDébutBoss) < TUNNEL_LENT2)) || (((getTempsTotal() - tpsDébutBoss) > FIN_TUNNEL2) && ((getTempsTotal() - tpsDébutBoss) < TUNNEL3)) ||
							(((getTempsTotal() - tpsDébutBoss) > BALLES_PARTOUT2) && ((getTempsTotal() - tpsDébutBoss) < TUNNEL4)) || ((getTempsTotal() - tpsDébutBoss) > GAUCHE_DROITE2)) {
						try {
							bossTir();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					//Il bouge quand il tire partout
					if(((getTempsTotal()-tpsDébutBoss) > TIR_PARTOUT && (getTempsTotal()-tpsDébutBoss) < TRANSITION_TIR) || ((getTempsTotal()-tpsDébutBoss) > TIR_PARTOUT2 && (getTempsTotal()-tpsDébutBoss) < TRANSITION_TIR2) ||
							((getTempsTotal()-tpsDébutBoss) > SPIRALE && (getTempsTotal()-tpsDébutBoss) < FIN_SPIRALE) || ((getTempsTotal()-tpsDébutBoss) > PLUS_PARTOUT && (getTempsTotal()-tpsDébutBoss) < GAUCHE_DROITE) ||
							((getTempsTotal()-tpsDébutBoss) > TIR_PARTOUT3 && (getTempsTotal()-tpsDébutBoss) < FIN_PARTOUT3)){
						//Si contre un mur
						if(s.getTranslateX() <= 15 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 135) 
							bossVersDroite = !bossVersDroite; 

						if(bossVersDroite) {
							if((getTempsTotal() - tpsDébutBoss) < BALLES_PARTOUT || (getTempsTotal() - tpsDébutBoss) > TIR_PARTOUT3)
								s.bougerDroite(0.02);
							else
								s.bougerDroite(0.1); //Quand il tire dans toutes les directions avec les balles dans toutes les directions
						}else {
							if((getTempsTotal() - tpsDébutBoss) < BALLES_PARTOUT || (getTempsTotal() - tpsDébutBoss) > TIR_PARTOUT3)
								s.bougerGauche(0.02);
							else
								s.bougerGauche(0.1); //Quand il tire dans toutes les directions avec les balles dans toutes les directions
						}
					}

					//Bouge gauche/droite de manière aléatoire pour le tunnel (Expression booléenne: "Quand il attaque en tunnel")
					if((((getTempsTotal() - tpsDébutBoss) > TUNNEL) && ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL)) || (((getTempsTotal() - tpsDébutBoss) > TUNNEL_LENT2) && ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL2)) ||
							(((getTempsTotal() - tpsDébutBoss) > TUNNEL3) && ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL3)) || (((getTempsTotal() - tpsDébutBoss) > TUNNEL4) && ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL4))) {
						//Si contre un mur
						if(s.getTranslateX() <= 10 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 130) 
							bossVersDroite = !bossVersDroite; 

						if(bossVersDroite) {
							if(((getTempsTotal() - tpsDébutBoss) < TUNNEL_LENT) || (((getTempsTotal() - tpsDébutBoss) > TUNNEL3) && ((getTempsTotal() - tpsDébutBoss) < TUNNEL_LENT3))) //Vitesse normale & rapide
								s.bougerDroite(0.1);
							else if(((getTempsTotal() - tpsDébutBoss) > TUNNEL_LENT && ((getTempsTotal() - tpsDébutBoss) < TUNNEL_RAPIDE)) || ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL2) || ((getTempsTotal() - tpsDébutBoss) > TUNNEL_LENT3)) //Si tunnel au ralenti
								s.bougerDroite(0.25);	
						}else {
							if(((getTempsTotal() - tpsDébutBoss) < TUNNEL_LENT) || (((getTempsTotal() - tpsDébutBoss) > TUNNEL3) && ((getTempsTotal() - tpsDébutBoss) < TUNNEL_LENT3))) //Vitesse normale & rapide
								s.bougerGauche(0.1);
							else if(((getTempsTotal() - tpsDébutBoss) > TUNNEL_LENT && ((getTempsTotal() - tpsDébutBoss) < TUNNEL_RAPIDE)) || ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL2) || ((getTempsTotal() - tpsDébutBoss) > TUNNEL_LENT3)) //Si tunnel au ralenti
								s.bougerGauche(0.25);
						}

						if(((getTempsTotal() - tpsDébutBoss) < TUNNEL_LENT) || (((getTempsTotal() - tpsDébutBoss) > TUNNEL3) && ((getTempsTotal() - tpsDébutBoss) < TUNNEL_LENT3))) { //Vitesse normale & rapide
							//Aléatoire toute les 0.125 secondes
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
						}else if(((getTempsTotal() - tpsDébutBoss) > TUNNEL_LENT) || ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL2) || ((getTempsTotal() - tpsDébutBoss) > TUNNEL_LENT3)) { //Si tunnel au ralenti
							//Aléatoire toute les 0.25 secondes
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
				}else if(commencé && !mort){ //Phase II (Début du combat)
					//Si contre un mur
					if(s.getTranslateX() <= 10 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 130) 
						bossVersDroite = !bossVersDroite; 

					//Flotte
					if(getTemps() > 0 && getTemps() < 1)
						s.bougerHaut(0.75);
					else
						s.bougerBas(0.75);

					//Tunnel
					if((((getTempsTotal() - tpsDébutBoss) > COMMENCE) && ((getTempsTotal() - tpsDébutBoss) < FIN_COMMENCE)) || (((getTempsTotal() - tpsDébutBoss) > RECOMMENCE) && ((getTempsTotal() - tpsDébutBoss) < FIN_RECOMMENCE)) || 
							(((getTempsTotal() - tpsDébutBoss) > RETOUR_TUNNEL) && ((getTempsTotal() - tpsDébutBoss) < PART)) || (((getTempsTotal() - tpsDébutBoss) > RETOUR2) && ((getTempsTotal() - tpsDébutBoss) < FIN_RETOUR2)) || 
							(((getTempsTotal() - tpsDébutBoss) > DERNIER_TUNNEL) && (getTempsTotal() - tpsDébutBoss) < FIN_DERNIER_TUNNEL)){
						if(bossVersDroite) {
							if(((getTempsTotal() - tpsDébutBoss) > RALENTI && (getTempsTotal() - tpsDébutBoss) < FIN_RALENTI) || ((getTempsTotal() - tpsDébutBoss) > RERALENTI && (getTempsTotal() - tpsDébutBoss) < FIN_RERALENTI))
								s.bougerDroite(0.25);
							else
								s.bougerDroite(0.1);	
						}else {
							if(((getTempsTotal() - tpsDébutBoss) > RALENTI && (getTempsTotal() - tpsDébutBoss) < FIN_RALENTI) || ((getTempsTotal() - tpsDébutBoss) > RERALENTI && (getTempsTotal() - tpsDébutBoss) < FIN_RERALENTI))
								s.bougerGauche(0.25);
							else
								s.bougerGauche(0.1);	
						}

						//Tunnel ralenti
						if(((getTempsTotal() - tpsDébutBoss) > RALENTI && (getTempsTotal() - tpsDébutBoss) < FIN_RALENTI) || ((getTempsTotal() - tpsDébutBoss) > RERALENTI && (getTempsTotal() - tpsDébutBoss) < FIN_RERALENTI)) {
							//Aléatoire toute les 0.25 secondes
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
							//Aléatoire toute les 0.125 secondes
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
					//Il tire pas à la même vitesse quand il ne tire pas en tunnel (Condition: PAS en tunnel)
					if(((((getTempsTotal() - tpsDébutBoss) > FIN_COMMENCE) && ((getTempsTotal() - tpsDébutBoss) < RECOMMENCE)) || ((((getTempsTotal() - tpsDébutBoss) > ECLAIRS)) && ((getTempsTotal() - tpsDébutBoss) < RETOUR_TUNNEL)) || 
							((getTempsTotal() - tpsDébutBoss) > DERNIERE_ATTAQUE)) && commencé) {
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

				//Si collision avec le boss (Combat phase II commencé et le boss pouvait être touché)
				if(commencé && bossPrésent) {
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

		//(Phase II) Le boss disparaît mais des balles apparaissent [Toutes les attaques sont dans bossTir pour que ce soit + compréhensible]
		//Condition: "BOSS PAS PRESENT"
		if(!bossPrésent && commencé) {
			bossTir();
		}

		niveau();

		if(isNiveauTerminé()) fin();
	}

	//Il s'affiche à la phase II
	public void texteNiveau() throws FileNotFoundException {
		//On ne fait pas Manipulation.ajouterTexte car la il a un effet d'opacité
		Texte niv = new Texte("NIVEAU FINAL - FIN", "nivFinal");
		niv.setX(-30);
		niv.setY(NiveauInterface.HAUTEUR_ECRAN/2);
		niv.setFont(Font.loadFont(new FileInputStream("Polices/NIVEAU.ttf"), 30)); //Charge la police et la change
		niv.setFill(Paint.valueOf("RED")); //Couleur
		niv.setOpacity(0); //Il va apparaître
		Manipulation.ajouterJeu(getRoot(), niv);
	}

	public void niveau() throws FileNotFoundException {
		// ============================ BOSS (DIALOGUE) ============================= \\
		if(Manipulation.siTpsA(getTempsTotal(), CREATION_BOSS, true))
			//Le mini-boss apparaît
			boss = new BOSSFINAL((NiveauInterface.LARGEUR_DECOR/2)-70, -150, 100, 150, "boss", VIE_BOSS, "Images/NiveauFinal/boss.png", new BarreDeVie(10,40,VIE_BOSS,10, "vie", this), this);

		//La musique se joue (On le voit apparaître
		if(Manipulation.siTpsA(getTempsTotal(), BOSS_CREE, true)) 
			if(Main.son()) mediaPlayer.play();

		//Il descend
		if(getTempsTotal() > BOSS_CREE && getTempsTotal() < FIN_DESCENTE_BOSS)
			boss.bougerBas(1);

		if(Manipulation.siTpsA(getTempsTotal(), FIN_DESCENTE_BOSS, true)) {
			//On ne fait pas Manipulation.ajouterTexte car on doit récupérer ce nom pour pas qu'il soit supprimé lors de updateScore()
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
		if(enTransition && ((getTempsTotal() - tpsDébutTransition)<TEMPS_TRANSITION))
			transition();

		//Début du boss
		if(enTransition && (Manipulation.siTpsA(getTempsTotal() - tpsDébutTransition, TEMPS_TRANSITION, false)) && !phaseII) {
			enTransition = false;
			imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_1.gif")));

			mediaPlayer.stop();

			//Musique du boss
			mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveauFINAL/phaseI.mp3").toURI().toString()));
			if(Main.son()) mediaPlayer.play();

			tpsDébutBoss =  getTempsTotal(); //Moment de début du combat (phase I)
			phaseI = true;
			lockFinDialogue = false; //Pour le dialogue vers la phase II
			
			setTempsTotal(getTempsTotal() + 205);
		}

		// ============================ BOSS (PHASE I) ============================= \\
		if(phaseI) {
			//Dialogue
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_1, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Nul besoin de tirer mon armure est,", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_2, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "INDESTRUCTIBLE. BAHAHAHA !!!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}
			//Le dialogue s'efface
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DEBUT, false)) {
				boss();
			}

			//Transition (Tremble)
			if((((getTempsTotal() - tpsDébutBoss) > TRANSITION_DEBUT) && ((getTempsTotal() - tpsDébutBoss) < TIR_PARTOUT)) || (((getTempsTotal() - tpsDébutBoss) > TRANSITION_TIR) && ((getTempsTotal() - tpsDébutBoss) < BALLES_GAUCHE_DROITE)) ||
					((getTempsTotal() - tpsDébutBoss) > TRANSITION_BALLES) && ((getTempsTotal() - tpsDébutBoss) < TIR_PARTOUT2) || (((getTempsTotal() - tpsDébutBoss) > TRANSITION_TIR2) && ((getTempsTotal() - tpsDébutBoss) < SPIRALE)) ||
					(((getTempsTotal() - tpsDébutBoss) > TRANSITION_MUR) && ((getTempsTotal() - tpsDébutBoss) < TUNNEL) || ((getTempsTotal() - tpsDébutBoss) > TRANSITION_TUNNEL) && ((getTempsTotal() - tpsDébutBoss) < BALLES_PARTOUT)) ||
					((getTempsTotal() - tpsDébutBoss) > TRANSITION_PARTOUT) && ((getTempsTotal() - tpsDébutBoss) < GAUCHE_DROITE) || ((getTempsTotal() - tpsDébutBoss) > TRANSITION_TUNNEL2) && ((getTempsTotal() - tpsDébutBoss) < RIEN) ||
					((getTempsTotal() - tpsDébutBoss) > TRANSITION_DEBUT2) && ((getTempsTotal() - tpsDébutBoss) < TUNNEL3) || ((getTempsTotal() - tpsDébutBoss) > TRANSITION_TUNNEL3) && ((getTempsTotal() - tpsDébutBoss) < BALLES_PARTOUT2) ||
					((getTempsTotal() - tpsDébutBoss) > TRANSITION_PARTOUT2) && ((getTempsTotal() - tpsDébutBoss) < TUNNEL4) || ((getTempsTotal() - tpsDébutBoss) > TRANSITION_TUNNEL4) && ((getTempsTotal() - tpsDébutBoss) < GAUCHE_DROITE2) ||
					((getTempsTotal() - tpsDébutBoss) > TRANSITION_DIALOGUE) && ((getTempsTotal() - tpsDébutBoss) < DEBUT3) || ((getTempsTotal() - tpsDébutBoss) > TREMBLE) && ((getTempsTotal() - tpsDébutBoss) < FIN)){
				transition();
			}

			//Passage au fond 2 (Accélère)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TIR_PARTOUT, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TIR_PARTOUT2, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, GAUCHE_DROITE, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TIR_PARTOUT3, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, GAUCHE_DROITE2, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_2.gif")));

			//On replace le boss au milieu
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TRANSITION_TIR, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TRANSITION_TIR2, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_SPIRALE, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_TUNNEL, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_TUNNEL2, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_TUNNEL3, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_PARTOUT3, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_TUNNEL4, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TRANSITION_PARTOUT, false)) {
				boss.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);
				boss.updateImagePos();
			}

			//Passage au fond 3 (Calme)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, BALLES_GAUCHE_DROITE, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, BALLES_PARTOUT, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TUNNEL_LENT2, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, BALLES_PARTOUT2, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_3.gif")));

			//On efface toutes les balles
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_TIR2, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_MUR, false)) {
				Manipulation.sprites(root).stream().filter(e -> e.getType().contains("balle")).forEach(balle -> {
					balle.meurs();
				});
			}

			//Passage au fond 4 (Rapide)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TUNNEL, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TUNNEL3, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TUNNEL4, false)) 
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_4.gif")));

			//Passage au fond 1 (Début)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, RIEN, false))
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_1.gif")));

			//Dialogue 2
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_3, false)) {
				//Affichage des personnages
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/debut.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Argh....", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_4, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Mon armure ne tiens plus...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_5, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai mis trop de puissance en vain?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}
			//Le dialogue s'efface
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, TRANSITION_DIALOGUE, false)) {
				boss();
			}

			//Plus de fond (Fin de la phase I)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, STOP, false)) {
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/Rien.png")));

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

				//Changement de musique
				mediaPlayer.stop();

				//Musique du dialogue
				mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveauFINAL/dialogue.mp3").toURI().toString()));
				mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //Répète a l'infini
				
				//Animation de l'armure de Zerod qui se brise
				Manipulation.supprimer(this, boss.getImg()); //On supprime l'ancienne image
				boss.setImg("Images/NiveauFinal/boss.gif"); //On la change
			}

			// ========================================== BOSS (ENTRE DEUX PHASES) ======================================================= \\
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_FIN, false)) {
				//Changement de phase
				phaseI = false;
				phaseII = true;

				tpsDébutTransition = 999; //Pour la transition de début

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
			if(enTransition && ((getTempsTotal() - tpsDébutTransition)<TEMPS_TRANSITION))
				transition();

			//Début de la phase II
			if(enTransition && (Manipulation.siTpsA(getTempsTotal() - tpsDébutTransition, TEMPS_TRANSITION, false)) && phaseII) {
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

				//Il regénère (Pette animation)
				for(int i =0; i<NiveauInterface.VIE_BOSS-22;++i) {
					boss.setVieBossActuelle(boss.getVieBossActuelle()+1); //La vie monte
					boss.getBarreVie().descente(boss); //Animation vie monte
					boss.getBarreVie().updateCouleur(boss); //Couleur change
				}
				tpsDébutBoss =  getTempsTotal(); //Moment de début du combat (Phase II)
				if(Main.son()) mediaPlayer.play();
			}

			//0.1 secondes après le début de la phase on affiche enfin le nom du niveau
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, APPARITION_NOM_NIVEAU, true))
				texteNiveau();


			//De l'affichage à la disparition
			if((getTempsTotal() - tpsDébutBoss) > APPARITION_NOM_NIVEAU && (getTempsTotal() - tpsDébutBoss) < FIN_NOM_NIVEAU) {
				Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("nivFinal")).forEach(texte -> {

					if ((getTempsTotal() - tpsDébutBoss) > APPARITION_NOM_NIVEAU && getTempsTotal() - tpsDébutBoss < NOM_NIVEAU_EN_APPARITION) //Début à 2 seconde il monte en opacité [2 à 4 il stagne au max]
						texte.setOpacity(tpsDébutBoss/2);
					else if ((getTempsTotal() - tpsDébutBoss) > NOM_NIVEAU_EN_DISPARITION && (getTempsTotal() - tpsDébutBoss) < NOM_NIVEAU_EN_DISPARITION2) //4s à 7s il redescend en opacité
						texte.setOpacity(4/(tpsDébutBoss*1.25));
					else if ((getTempsTotal() - tpsDébutBoss) > NOM_NIVEAU_EN_DISPARITION2 && (getTempsTotal() - tpsDébutBoss) < FIN_NOM_NIVEAU) //7s à 9s il redescend encore plus
						texte.setOpacity(4/(tpsDébutBoss*3.5));

					texte.updatePos(this, texte.getTranslateX() + 0.25, NiveauInterface.HAUTEUR_ECRAN/4);
				});
			}

			//Dialogue de début
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_6, false)) {
				//Affichage des personnages
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C  '  e  s  t    l  a    f  i  n.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			//Suppression du dialogue
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DERNIER_TUNNEL, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DERNIERE_ATTAQUE, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_DIALOGUE_7, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_DIALOGUE, false)) {
				commencé=true;
				bossPrésent = true;
				setEnDialogue(false);
				//Pour effacer tout ce qui est dialogue
				Manipulation.toutEffacer(this);

				imageFond = new ImageView(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseII.gif")));
				root.getChildren().add(imageFond);

				Manipulation.ajouterImage(getRoot(),"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

				//On réaffiche les personnages
				Manipulation.ajouterJeu(getRoot(), joueur.getImg());
				Manipulation.ajouterJeu(getRoot(), boss.getImg());

				//On enlève puis remet la barre de vie 
				Manipulation.supprimer(this, boss.getBarreVie());
				Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

				Manipulation.updateScore(this); //On remet le score

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

				boss.setVieBossActuelle(vieActuelle); //La vie qu'il avait
				
				//setTempsTotal(getTempsTotal()+70);
			}

			//Replace au milieu le boss
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_COMMENCE, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_RECOMMENCE, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, REVIENT-1, false)) {
				boss.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);

				//L'image s'affiche pas quand Zerod revient
				if((getTempsTotal() - tpsDébutBoss) < REVIENT-1)
					boss.updateImagePos();
			}

			//Le boss part
			if(((getTempsTotal() - tpsDébutBoss) > FIN_ECLAIRS && (getTempsTotal() - tpsDébutBoss) < BALLES_TOMBENT) || (getTempsTotal() - tpsDébutBoss) > PART && (getTempsTotal() - tpsDébutBoss) < PART+TEMPS_TRANSITION) {
				boss.bougerHaut(0.35);
				bossPrésent = false; //Le boss n'est plus là
			}

			//Le boss, barre de vie et nom disparaissent
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, BALLES_TOMBENT, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, PART+TEMPS_TRANSITION, false)) {
				boss.getImg().meurs(); //Le boss disparaît (Sinon on voit ses pieds)
				Manipulation.supprimer(this, boss.getBarreVie()); //La barre de vie disparaît

				//Le nom du boss disparaît
				Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("nomboss")).forEach(texte -> {
					Manipulation.supprimer(this, texte);
				});
			}

			if(Manipulation.siTpsA(getTempsTotal(), ECLAIRS, true) || Manipulation.siTpsA(getTempsTotal(), ECLAIR, true) || Manipulation.siTpsA(getTempsTotal(), HORIZONTAUX, true)) {
				setTemps(0); //Pour bien commencer les éclairs (Ils sont en fonction du temps)
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_ECLAIRS, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, PART, false)) {
				//Suppression du nom du boss
				Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("nomboss")).forEach(texte -> {
					Manipulation.supprimer(this, texte);
				});
			}

			//Dialogue
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_7, false)) {
				//Affichage des personnages
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Disparait en un claquement de doigt !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			//Le boss revient
			if(((getTempsTotal() - tpsDébutBoss) > DIALOGUE_7 && (getTempsTotal() - tpsDébutBoss) < RETOUR) || (getTempsTotal() - tpsDébutBoss) > REVIENT && (getTempsTotal() - tpsDébutBoss) < RETOUR2) {
				boss.bougerBas(0.2);
				bossPrésent = true;

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

			//Quand le boss revient
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_7, true) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, REVIENT, true)) {
				
				//Si c'est quand Zerod revient après la 7ème dialogue, il a une aura autour de lui, son image change
				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_7, false)) {
					Manipulation.supprimer(this, boss.getImg()); //On supprime l'ancienne image de Zerod
					boss.setImg("Images/NiveauFinal/zerod.gif"); //Zerod avec maintenant une aura bleue
				}else { //Sinon c'est quand il revient, toujours avec l'aura bleue donc pas besoin de changer l'image
					Manipulation.ajouterJeu(root, boss.getImg());
				}
				
				//Dans tous les cas, sa barre de vie revient
				Manipulation.ajouterJeu(root, boss.getBarreVie());
			}

			//transition() fait trembler Zerod
			if(((getTempsTotal() - tpsDébutBoss) > RETOUR && (getTempsTotal() - tpsDébutBoss) < PART) || ((getTempsTotal() - tpsDébutBoss) > REVIENT)) 
				transition();

			//Dialogue
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_8, false)) {
				//Affichage des personnages
				setPeutTirer(false); //Le joueur ne peut pas tirer
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "AHHHHHHHHHHHHHHHH !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			//Dialogue
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_9, false)) {
				//Affichage des personnages
				setPeutTirer(false); //Le joueur ne peut pas tirer
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ahhh....", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_10, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Aurais-je... Perdu ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_11, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Non... Rien n'est fini Salar...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_12, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C'est fini, Zerod. Arrete.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE_13, false)) {
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
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
	 * Des balles apparaissent aléatoirement des quatre côtés
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void ballesAléatoiresPartout() throws FileNotFoundException {
		if(!commencé) { //Phase I
			if(Math.random()>0.975) {
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), -10, 20, 20, "Images/NiveauFinal/balle_b.png", "balle aléatoire bas", this); //Sors du haut va vers le bas
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), NiveauInterface.HAUTEUR_ECRAN, 20, 20, "Images/NiveauFinal/balle_h.png", "balle aléatoire haut", this); //Sors du bas va vers le haut
				new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20,"Images/NiveauFinal/balle_d.png", "balle aléatoire droite", this); //Sors de la gauche va vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-100, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/NiveauFinal/balle_g.png", "balle aléatoire gauche", this); //Sors de la droite va vers la gauche
			}
		}else{
			double probabilitéTir = 0.985; //Phase II, pas la dernière attaque

			if((getTempsTotal() - tpsDébutBoss) > DERNIERE_ATTAQUE) //Dernière attaque
				probabilitéTir = 0.975;

			if(Math.random()>probabilitéTir) {
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), -10, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire bas II", this); //Sors du haut va vers le bas
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-60) - 10), NiveauInterface.HAUTEUR_ECRAN, 20, 20,"Images/NiveauFinal/balle2.png", "balle aléatoire haut", this); //Sors du bas va vers le haut
				new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire droite", this); //Sors de la gauche va vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-100, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire gauche", this); //Sors de la droite va vers la gauche
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
		if(!commencé) {
			if(Math.random() > 0.9) //Quand il y a seulement elles
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/NiveauFinal/balle_b.png", "balle aléatoire bas", this); //X aléatoire
		}else { //Phase II
			double probabilitéTir = 0.975; //Avec les balles aléatoires partant du bas

			//Pas la même probabilitée
			if((getTempsTotal() - tpsDébutBoss) < BALLES_ALEATOIRES_HAUT_BAS) //Le début
				probabilitéTir = 0.9;
			else if((getTempsTotal() - tpsDébutBoss) > PLUS_BALLE) //Avec les éclairs
				probabilitéTir = 0.95;


			if(Math.random() > probabilitéTir) { //Probabilité d'un tir
				double rd = Math.random();

				//Pas la même vitesse des balles
				if(rd < 0.25) {
					if((getTempsTotal() - tpsDébutBoss) < GROSSES)
						new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire bas vit1", this); //X aléatoire
					else //Une grosse balle
						new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 50, 50, "Images/NiveauFinal/balle2.png", "balle aléatoire bas vit1", this); //X aléatoire
				}
				else if(rd < 0.5)
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire bas vit2", this); //X aléatoire
				else if(rd < 0.75)
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire bas vit3", this); //X aléatoire
				else
					new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire bas vit4", this); //X aléatoire
			}
		}
	}

	/**
	 * Des balles apparaissent aléatoirement de la gauche et de la droite
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void ballesAléatoiresGaucheDroite() throws FileNotFoundException {
		//Part de la droite
		if(Math.random() > 0.975)
			new BossBalle(NiveauInterface.LARGEUR_DECOR-35, Math.random() * NiveauInterface.LARGEUR_ECRAN, "Images/NiveauFinal/balle_g.png", "balle E", this); //X aléatoire

		//Part de la gauche
		if(Math.random() > 0.975)
			new BossBalle(5, Math.random() * NiveauInterface.LARGEUR_ECRAN, "Images/NiveauFinal/balle_d.png", "balle O", this); //X aléatoire
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
				endroit = 2; //2: Tout à droite

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

	/**
	 * Le boss tire cinq balles deux allant à gauche, deux à droite et une toute droite
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
			if(((getTempsTotal() - tpsDébutBoss)>DEBUT && (getTempsTotal() - tpsDébutBoss)<FIN_DEBUT) || ((getTempsTotal() - tpsDébutBoss)>DEBUT2 && (getTempsTotal() - tpsDébutBoss)<FIN_DEBUT2) ||
					((getTempsTotal() - tpsDébutBoss)>DEBUT3 && (getTempsTotal() - tpsDébutBoss)<FIN_DEBUT3)) {
				ballesAléatoiresHaut();
			}

			// ====================== TIRE DE PARTOUT ===================== \\
			if(((getTempsTotal() - tpsDébutBoss) > TIR_PARTOUT && (getTempsTotal() - tpsDébutBoss) < FIN_TIR) ||
					((getTempsTotal() - tpsDébutBoss) > TIR_PARTOUT2 && (getTempsTotal() - tpsDébutBoss) < FIN_TIR2) ||
					((getTempsTotal() - tpsDébutBoss) > TIR_PARTOUT3 && (getTempsTotal() - tpsDébutBoss) < FIN_PARTOUT3)){
				tirPartout();
			}

			// ====================== BALLES ALEATOIRES GAUCHE/DROITE ===================== \\
			if((getTempsTotal() - tpsDébutBoss) > BALLES_GAUCHE_DROITE && (getTempsTotal() - tpsDébutBoss) < FIN_BALLES) {
				ballesAléatoiresGaucheDroite();
			}

			// ====================== SPIRALE ===================== \\
			if((getTempsTotal() - tpsDébutBoss) > SPIRALE && (getTempsTotal() - tpsDébutBoss) < FIN_SPIRALE) {
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

				ballesAléatoiresGaucheDroite();
			}

			// ====================== MUR BALLES VERTICAL ===================== \\
			if(((getTempsTotal() - tpsDébutBoss) > MUR) && ((getTempsTotal() - tpsDébutBoss) < FIN_MUR)) {
				//Pas le même écart sinon ça sera toujours le même mur avec les mêmes trous 
				int ecartBalles = (int) (Math.random() * 100)+100; //+90 évite le ecartBalles = 0

				if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false)) {
					//Création de la ligne de balles (De gauche à droite)
					for(int i = -ecartBalles+50; i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2; i+=ecartBalles) {
						new BossBalle(10, i, "Images/NiveauFinal/balle_d.png", "balle mur vertical g", this); 
						new BossBalle(-20, i, "Images/NiveauFinal/balle_d.png", "balle mur vertical g", this); 
					}
				}

				if(Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
					//Et la ligne de droite à gauche
					for(int i = (-ecartBalles); i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2+10; i+=ecartBalles) {
						new BossBalle(NiveauInterface.LARGEUR_DECOR-70, i,"Images/NiveauFinal/balle_g.png", "balle mur vertical d", this); 
						new BossBalle(NiveauInterface.LARGEUR_DECOR-40, i, "Images/NiveauFinal/balle_g.png", "balle mur vertical d", this);
					}
				}
			}

			// ====================== TUNNEL ===================== \\
			if((((getTempsTotal() - tpsDébutBoss) > TUNNEL) && ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL)) || (((getTempsTotal() - tpsDébutBoss) > TUNNEL_LENT2) && ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL2)) ||
					(((getTempsTotal() - tpsDébutBoss) > TUNNEL3) && ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL3)) || (((getTempsTotal() - tpsDébutBoss) > TUNNEL4) && ((getTempsTotal() - tpsDébutBoss) < FIN_TUNNEL4))) {
				//L'écart entre les balles en largeur afin que cela ait une apparance de mur
				int ecartMur = NiveauInterface.TAILLE_BALLE_BOSS; 

				//Mettre cette limite sert à faire en sorte que pour le tunnel, le mur de droite ne soit pas sur le tableau
				int limiteBalle = NiveauInterface.LARGEUR_DECOR - 192;

				double toutAGauche = boss.getTranslateX()-50; //La balle la plus à gauche du BOSS
				double toutADroite;

				//Si le boss n'est pas proche du tableau qui va faire spawn des balles sur le tableau..
				if(boss.getTranslateX() <= limiteBalle)
					toutADroite = boss.getTranslateX()+120; //..tout se passe normal
				else
					toutADroite = NiveauInterface.LARGEUR_DECOR-35; //Sinon la limite c'est la fin du décor

				//Création des balles tout à gauche et droite
				new BossBalle(toutAGauche, boss.getTranslateY()+120, "Images/NiveauFinal/balle_b.png", "balle tunnel", this);

				if(boss.getTranslateX() <= limiteBalle) //Si le boss pas trop proche du tableau
					new BossBalle(toutADroite, boss.getTranslateY()+120, "Images/NiveauFinal/balle_b.png", "balle tunnel", this);

				//Création d'un mur de balles du milieu à la toute gauche [1er tunnel gauche]
				for(int i=0;i<toutAGauche;i+=ecartMur) {
					new BossBalle(i, boss.getTranslateY()+120, "Images/NiveauFinal/balle_b.png", "balle tunnel", this);
				}

				//De même pour le mur de balles du milieu à la toute droite [2ème tunnel gauche] si le boss n'est pas proche du tableau
				if(boss.getTranslateX() <= limiteBalle) {
					for(int i=NiveauInterface.LARGEUR_DECOR-35; i>toutADroite;i-=ecartMur) {
						new BossBalle(i, boss.getTranslateY()+120, "Images/NiveauFinal/balle_b.png", "balle tunnel", this);
					}
				}
			}

			// ====================== BALLES ALEATOIRES DE PARTOUT ===================== \\
			if((((getTempsTotal() - tpsDébutBoss) > BALLES_PARTOUT) && ((getTempsTotal() - tpsDébutBoss) < FIN_PARTOUT)) || (((getTempsTotal() - tpsDébutBoss) > BALLES_PARTOUT2) && ((getTempsTotal() - tpsDébutBoss) < FIN_PARTOUT2)) ||
					(((getTempsTotal() - tpsDébutBoss) > BALLES_PARTOUT3) && ((getTempsTotal() - tpsDébutBoss) < STOP_TIR))){
				ballesAléatoiresPartout();

				if(((getTempsTotal() - tpsDébutBoss) > PLUS_PARTOUT) && ((getTempsTotal() - tpsDébutBoss) < BALLES_PARTOUT2)) {
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
			if((((getTempsTotal() - tpsDébutBoss) > GAUCHE_DROITE) && ((getTempsTotal() - tpsDébutBoss) < FIN_GAUCHE_DROITE)) || (((getTempsTotal() - tpsDébutBoss) > GAUCHE_DROITE2) && ((getTempsTotal() - tpsDébutBoss) < FIN_GAUCHE_DROITE2))) {
				int ecartBalles = 100;

				if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.25, false) || Manipulation.siTpsA(getTemps(), 1.75, false)){
					//Création de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, "Images/NiveauFinal/balle_b.png", "balle gauche droite", this); //X aléatoire
					}
				}

				if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1, false) || Manipulation.siTpsA(getTemps(), 1.5, false) || Manipulation.siTpsA(getTemps(), 1.98, false)){
					//Création de la ligne de balles 
					for(int i = 50; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, "Images/NiveauFinal/balle_b.png", "balle gauche droite", this); //X aléatoire
					}
				}
			}
		}else if(phaseII && commencé && !mort) { //PHASE II
			// ====================== TUNNEL ===================== \\
			if((((getTempsTotal() - tpsDébutBoss) > COMMENCE) && ((getTempsTotal() - tpsDébutBoss) < FIN_COMMENCE)) || (((getTempsTotal() - tpsDébutBoss) > RECOMMENCE) && ((getTempsTotal() - tpsDébutBoss) < FIN_RECOMMENCE)) || 
					(((getTempsTotal() - tpsDébutBoss) > RETOUR_TUNNEL) && ((getTempsTotal() - tpsDébutBoss) < PART)) || (((getTempsTotal() - tpsDébutBoss) > RETOUR2) && ((getTempsTotal() - tpsDébutBoss) < FIN_RETOUR2)) || 
					(((getTempsTotal() - tpsDébutBoss) > DERNIER_TUNNEL) && (getTempsTotal() - tpsDébutBoss) < FIN_DERNIER_TUNNEL)){
				//L'écart entre les balles en largeur afin que cela ait une apparance de mur
				int ecartMur = NiveauInterface.TAILLE_BALLE_BOSS; 

				//Mettre cette limite sert à faire en sorte que pour le tunnel, le mur de droite ne soit pas sur le tableau
				int limiteBalle = NiveauInterface.LARGEUR_DECOR - 192;

				double toutAGauche = boss.getTranslateX()-50; //La balle la plus à gauche du BOSS
				double toutADroite;

				//Si le boss n'est pas proche du tableau qui va faire spawn des balles sur le tableau..
				if(boss.getTranslateX() <= limiteBalle)
					toutADroite = boss.getTranslateX()+120; //..tout se passe normal
				else
					toutADroite = NiveauInterface.LARGEUR_DECOR-35; //Sinon la limite c'est la fin du décor

				//Création des balles tout à gauche et droite
				new BossBalle(toutAGauche, boss.getTranslateY()+120, "Images/NiveauFinal/balle2.png", "balle tunnel II", this);

				if(boss.getTranslateX() <= limiteBalle) //Si le boss pas trop proche du tableau
					new BossBalle(toutADroite, boss.getTranslateY()+120, "Images/NiveauFinal/balle2.png", "balle tunnel II", this);

				//Création d'un mur de balles du milieu à la toute gauche [1er tunnel gauche]
				for(int i=0;i<toutAGauche;i+=ecartMur) {
					new BossBalle(i, boss.getTranslateY()+120,"Images/NiveauFinal/balle2.png", "balle tunnel II", this);
				}

				//De même pour le mur de balles du milieu à la toute droite [2ème tunnel gauche] si le boss n'est pas proche du tableau
				if(boss.getTranslateX() <= limiteBalle) {
					for(int i=NiveauInterface.LARGEUR_DECOR-35; i>toutADroite;i-=ecartMur) {
						new BossBalle(i, boss.getTranslateY()+120,  "Images/NiveauFinal/balle2.png", "balle tunnel II", this);
					}
				}
			}

			// ====================== MUR VERTICAUX ET HORIZONTAUX DE BALLES ===================== \\
			if(((getTempsTotal() - tpsDébutBoss) > MUR_BALLE) && ((getTempsTotal() - tpsDébutBoss) < FIN_MUR_BALLE)) {
				//Pas le même écart sinon ça sera toujours le même mur avec les mêmes trous 
				int ecartBalles = (int) (Math.random() * 100)+100; //+100 évite le ecartBalles = 0

				if(Manipulation.siTpsA(getTemps(), 0.98, false)) {
					//Création de la ligne de balles (De gauche à droite)
					for(int i = -ecartBalles+50; i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2; i+=ecartBalles) {
						new BossBalle(10, i, "Images/NiveauFinal/balle2.png", "balle mur vertical g", this); 
						new BossBalle(-20, i, "Images/NiveauFinal/balle2.png", "balle mur vertical g", this); 
					}
				}

				if(Manipulation.siTpsA(getTemps(), 1.98, false)) {
					//Et la ligne de droite à gauche
					for(int i = (-ecartBalles); i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2+10; i+=ecartBalles) {
						new BossBalle(NiveauInterface.LARGEUR_DECOR-70, i, "Images/NiveauFinal/balle2.png", "balle mur vertical d", this); 
						new BossBalle(NiveauInterface.LARGEUR_DECOR-40, i, "Images/NiveauFinal/balle2.png", "balle mur vertical d", this);
					}
				}

				//Plus le mur tombant de haut
				if(Manipulation.siTpsA(getTemps(), 1, false)){
					//Création de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, "Images/NiveauFinal/balle2.png", "mur balle", this); 
						new BossBalle(i, -40, "Images/NiveauFinal/balle2.png", "mur balle", this);
					}
				}
			}

			// ====================== ECLAIRS & BALLES ALEATOIRES PARTOUT ===================== \\
			if((((getTempsTotal() - tpsDébutBoss) > ECLAIRS) && ((getTempsTotal() - tpsDébutBoss) < FIN_ECLAIRS)) || (((getTempsTotal() - tpsDébutBoss) > ECLAIR) && ((getTempsTotal() - tpsDébutBoss) < FIN_ECLAIR))) {
				éclairs();

				//Balles aléatoires avec seulement dans ces temps là
				if(((getTempsTotal() - tpsDébutBoss) > ECLAIRS) && ((getTempsTotal() - tpsDébutBoss) < FIN_ECLAIRS))
					ballesAléatoiresPartout();

				if(((getTempsTotal() - tpsDébutBoss) > PLUS_BALLE) && ((getTempsTotal() - tpsDébutBoss) < FIN_ECLAIR))
					ballesAléatoiresHaut();
			}

			//Suppression de l'avertissement de l'éclair pour pas qu'il reste
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, BALLES_TOMBENT, false)) 
				Manipulation.supprimer(this, avertissement);


			// ====================== BALLES ALEATOIRES DE HAUT VITESSE ALEATOIRE ===================== \\
			if(((getTempsTotal() - tpsDébutBoss) > BALLES_TOMBENT) && ((getTempsTotal() - tpsDébutBoss) < FIN_BALLES_TOMBENT))
				ballesAléatoiresHaut();

			// ====================== BALLES FONCENT ===================== \\
			if(((getTempsTotal() - tpsDébutBoss) > FONCE) && ((getTempsTotal() - tpsDébutBoss) < FIN_FONCE)) {
				if(Manipulation.quatreFoisParSecondes(getTemps()))
					new BossBalle(joueur.getTranslateX(), -10,"Images/NiveauFinal/balle2.png", "balle fonce", this);
			}

			// ====================== BALLES ALEATOIRES DE BAS VITESSE ALEATOIRE ===================== \\
			if(((getTempsTotal() - tpsDébutBoss) > FIN_FONCE) && ((getTempsTotal() - tpsDébutBoss) < FIN_TREMBLES)) {
				double probabilitéTir = 0.95;

				//Plus dur
				if(((getTempsTotal() - tpsDébutBoss) > BALLES_ALEATOIRES_HAUT_BAS))
					probabilitéTir = 0.975;

				if(Math.random() > probabilitéTir) { //Probabilité d'un tir
					double rd = Math.random();

					//Pas la même vitesse des balles
					if(rd < 0.25) {
						if((getTempsTotal() - tpsDébutBoss) < GROSSES)
							new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN+15, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire haut vit1", this); //X aléatoire
						else //Une grosse balle
							new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN+15, 50, 50,"Images/NiveauFinal/balle2.png", "balle aléatoire haut vit1", this); //X aléatoire
					}else if(rd < 0.5)
						new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN+15, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire haut vit2", this); //X aléatoire
					else if(rd < 0.75)
						new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN+15, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire haut vit3", this); //X aléatoire
					else
						new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN+15, 20, 20, "Images/NiveauFinal/balle2.png", "balle aléatoire haut vit4", this); //X aléatoire
				}

				if((getTempsTotal() - tpsDébutBoss) > BALLES_ALEATOIRES_HAUT_BAS){ //Plus celles du haut
					ballesAléatoiresHaut();
				}
			}

			// ====================== MUR BALLES ===================== \\
			if((getTempsTotal() - tpsDébutBoss)>RETOUR && (getTempsTotal() - tpsDébutBoss)<FIN_RETOUR) {
				if(Manipulation.deuxFoisParSecondes(getTemps())) {
					//Pas le même écart sinon ça sera toujours le même mur avec les mêmes trous 
					int ecartBalles = (int) (Math.random() * 80)+80; //+70 évite le ecartBalles = 0
					//Création de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, "Images/NiveauFinal/balle2.png", "mur balle", this);
						new BossBalle(i, -40, "Images/NiveauFinal/balle2.png", "mur balle", this);
					}
				}
			}

			//Suppression de l'avertissement de l'éclair pour pas qu'il reste
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_ECLAIR, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ALEATOIRES, false)) {
				Manipulation.supprimer(this, avertissement);
				Manipulation.supprimer(this, avertissement2);
			}

			// ====================== ECLAIRS HORIZONTAUX ===================== \\
			if((getTempsTotal() - tpsDébutBoss)>HORIZONTAUX && (getTempsTotal() - tpsDébutBoss)<ALEATOIRES) {
				éclairsHorizontaux();

				//Plus des balles aléatoires de haut
				if((getTempsTotal() - tpsDébutBoss)>PLUS_ALEATOIRE && (getTempsTotal() - tpsDébutBoss)<FIN_ALEATOIRE)
					ballesAléatoiresPartout();
			}

			// ====================== BALLES ALEATOIRES HAUT + MUR DE BALLES ===================== \\
			if((getTempsTotal() - tpsDébutBoss)>ALEATOIRES && (getTempsTotal() - tpsDébutBoss)<MURS) {
				ballesAléatoiresPartout();

				//Mur de balles
				int ecartBalles = (int) (Math.random() * 150)+150; 

				//Et le mur tombant de haut
				if(Manipulation.siTpsA(getTemps(), 1, false)){
					//Création de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, NiveauInterface.HAUTEUR_ECRAN+10, "Images/NiveauFinal/balle2.png", "mur balle", this); 
						new BossBalle(i, NiveauInterface.HAUTEUR_ECRAN+40, "Images/NiveauFinal/balle2.png", "mur balle", this);
					}
				}
			}

			// ====================== MURS DE BALLES VENANT DE [HAUT ET DE BAS]/[PARTOUT] ===================== \\
			if((getTempsTotal() - tpsDébutBoss)>MURS && (getTempsTotal() - tpsDébutBoss)<FIN_MURS) {
				int ecartBalles = (int) (Math.random() * 80)+80; //+90 évite le ecartBalles = 0

				//Et le mur tombant de bas
				if(Manipulation.siTpsA(getTemps(), 1, false)){
					//Création de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, NiveauInterface.HAUTEUR_ECRAN+10, "Images/NiveauFinal/balle2.png", "mur balle bas", this); 
						new BossBalle(i, NiveauInterface.HAUTEUR_ECRAN+40, "Images/NiveauFinal/balle2.png", "mur balle bas", this);
					}
				}

				//Murs de balles partout
				if((getTempsTotal() - tpsDébutBoss) > MUR_PARTOUT) {
					ecartBalles = (int) (Math.random() * 100)+100; //+90 évite le ecartBalles = 0
					if(Manipulation.siTpsA(getTemps(), 0.98, false)) {
						//Création de la ligne de balles (De gauche à droite)
						for(int i = -ecartBalles+50; i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2; i+=ecartBalles) {
							new BossBalle(10, i, "Images/NiveauFinal/balle2.png", "balle mur vertical g", this); 
							new BossBalle(-20, i,"Images/NiveauFinal/balle2.png", "balle mur vertical g", this); 
						}
					}

					ecartBalles = (int) (Math.random() * 100)+100; //+90 évite le ecartBalles = 0
					if(Manipulation.siTpsA(getTemps(), 1.98, false)) {
						//Et la ligne de droite à gauche
						for(int i = (-ecartBalles); i < NiveauInterface.HAUTEUR_ECRAN + ecartBalles*2+10; i+=ecartBalles) {
							new BossBalle(NiveauInterface.LARGEUR_DECOR-70, i, "Images/NiveauFinal/balle2.png", "balle mur vertical d", this); 
							new BossBalle(NiveauInterface.LARGEUR_DECOR-40, i, "Images/NiveauFinal/balle2.png", "balle mur vertical d", this);
						}
					}
				}
			}

			// ====================== DERNIERE ATTAQUE (BALLES PARTOUT) ===================== \\
			if((getTempsTotal() - tpsDébutBoss) > DERNIERE_ATTAQUE)
				ballesAléatoiresPartout();
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

	@Override
	public void start(Stage NIVFINAL) throws Exception {
		Scene niveau_FINAL = new Scene(createNiveau(NIVFINAL)); //Création du niveau

		NIVFINAL.setScene(niveau_FINAL); //La scène du niveau devient la scène principale
		NIVFINAL.setTitle("NIVEAU FINAL"); //Nom de la fenêtre
		NIVFINAL.setResizable(false); //On ne peut pas redimensionner la fenêtre

		//On remet l'icône du jeu comme elle a changée car c'est une autre fênetre
		NIVFINAL.getIcons().add(new Image("file:Images/Icone.png"));

		Manipulation.updateScore(this); //Met à jour le score pour le début du niveau

		NIVFINAL.show(); //Affichage du niveau

		/*On gère les boutons tapés (Contrôles du jeu) 
		 *[Quand on APPUIE sur la touche]
		 */
		niveau_FINAL.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(!isEnDialogue()) { //Quand c'est en dialogue, le joueur ne joue pas
					switch (event.getCode()) { //Récupération de quelle touche appuyée
					case UP:    setBougeHaut(true); break;
					case DOWN:  setBougeBas(true); break;
					case LEFT:  setBougeGauche(true); break;
					case RIGHT: setBougeDroite(true); break;
					case SPACE: if(commencé && isPeutTirer()) setTiré(true); break; //Lors de la phase I, le joueur ne tire pas
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
							System.err.println("Erreur lors de la sauvegarde de l'état 7!");
						}
						
						
						Alert alerte = new Alert(AlertType.INFORMATION); 
						alerte.setTitle("ZEROD ABATTU !"); //Le titre de la fênetre
						alerte.setHeaderText("..."); //Au dessus du texte
						alerte.setContentText("?"); //Le texte

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
		niveau_FINAL.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(!isEnDialogue()) { //Quand c'est en dialogue, le joueur ne joue pas
					switch (event.getCode()) { //Récupération de quelle touche appuyée
					case UP:    setBougeHaut(false); break;
					case DOWN:  setBougeBas(false); break;
					case LEFT:  setBougeGauche(false); break;
					case RIGHT: setBougeDroite(false); break;
					case SPACE: if(commencé) setTiré(false); break; //Lors de la phase I, le joueur ne tire pas
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
		root.setPrefSize(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); //Met à jour les dimensions du jeu

		// ========================= Image de fond =========================
		try {
			imageFond = new ImageView(new Image(new FileInputStream("Fonds/Niveau_FINAL/bg_debut.gif")));
			imageFond.setX(-15); 
			imageFond.setY(0);
			imageFond.setFitHeight(NiveauInterface.HAUTEUR_ECRAN+40); //Change la taille
			imageFond.setFitWidth(NiveauInterface.LARGEUR_ECRAN); 
			root.getChildren().add(imageFond);
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image du fond du niveau n'a pas été trouvée !", e);	
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

	@Override
	public void miniBOSSTir(BOSS b) throws FileNotFoundException {}

	@Override
	public void boss() throws FileNotFoundException {
		//Pour effacer tout ce qui est dialogue
		Manipulation.toutEffacer(this);

		//Mise à jour du fond
		imageFond = new ImageView(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/bg_phaseI_1.gif")));
		root.getChildren().add(imageFond);

		Manipulation.ajouterImage(getRoot(),"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25, NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

		//On réaffiche les personnages
		Manipulation.ajouterJeu(getRoot(), joueur.getImg());
		Manipulation.ajouterJeu(getRoot(), boss.getImg());

		//On enlève puis remet la barre de vie 
		Manipulation.supprimer(this, boss.getBarreVie());
		Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

		Manipulation.updateScore(this); //On remet le score

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

	@Override
	public void dialogue() throws FileNotFoundException {
		super.dialogue();
		//Dialogue avant le début du boss
		if(!phaseI && !phaseII) {
			switch(getNbEspaceAppuyés()) {
			case 0:
				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/debut.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Te voici enfin.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1: 
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C'est donc toi derriere tout ca.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Totalement.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu te demandes... Pourquoi ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 4:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourquoi ? Cette guerre.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 5:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Depeches de t'expliquer.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 6:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Hmhmhm... Nous les humains...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 7:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Sommes toujours egoistes.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 8:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Toi et moi nous le savons...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 9:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "La soif de puissance a toujours...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 10:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ete d'actualite.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 11:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Que racontes-tu ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 12:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Chaque personne veut toujours...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 13:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "etre plus fort.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 14:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Et cela a n'importe quel prix.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 15:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Vous les totzuzens...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 16:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Vous etes bien plus puissants que nous,", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 17:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "simples humains.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 18:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Et alors ? Est-ce qu'on vous...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 19:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...a deja attaque ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 20:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Non, mais vous le pouvez n'importe quand.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 21:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourquoi on le ferait ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 22:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Une simple querelle pourrait declencher...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 23:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Une embrouille et donc un meurtre.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 24:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Vu votre puissance, vous tuez sans..", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 25:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "vous en rendre compte.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 26:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ces gens la sont juste fous.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 27:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Nous sommes loins d'etre comme ca.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 28:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Peu importe. Nous vivons dans la crainte.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 29:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je comprends. Mais lancer une guerre...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 30:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Etait-elle reellement la solution ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 31:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tuer des MILLIERS d'innocents...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 32:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "ETAIT-ELLE LA SOLUTION ????", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 33:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Oui. Car nous allons dominer le monde.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 34:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je vois. T'es donc fou.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 35:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Vois moi comme tu veux...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 36:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Mais ce que je vois chez toi..", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 37:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C'EST TA MORT IMMINENTE.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;
			}

			if(getNbEspaceAppuyés() > 37) { //Fin du dialogue
				enTransition = true;
				setEnDialogue(false); 
			}
		}else if(phaseII && !mort) { //Dialogue entre les deux phases
			switch(getNbEspaceAppuyés()) {
			case 0:
				//On le remet à 9999 comme ça le temps ne "s'écoule pas"
				tpsDébutBoss = 9999;

				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "????!!!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Qu...Qu'est-ce que...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "CA VEUT DIRE ??!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Hmhmhmhmhm...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 4:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Alors toi aussi...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 5:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu es un des notres ?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 6:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "En effet.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 7:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ne t'excite pas, Salar...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 8:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "La raison est logique.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 9:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "QUE RACONTES-TU ?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 10:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "EN QUOI CELA EST LOGIQUE,", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 11:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "D'EXTERMINER TA PROPRE ESPECE ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 12:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai ete deteste.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 13:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai ete rejete.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 14:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ma famille ma mis a la porte.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 15:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ma famille m'a tue.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 16:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Mon pere a tue mon frere...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 17:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ma mere a tuee mes deux soeurs...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 18:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai ete detruit.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 19:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "PAR MA PROPRE RACE.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 20:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ta famille devait etre exterminee.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 21:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pas NOUS, Zerod.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 22:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "SI. VOUS ETES TOUS LES MEMES.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 23:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "VOUS ETES ASSOIFES DE PUISSANCE.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 24:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "CETTE DECHIRURE ENTRE LES HUMAINS...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 25:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "N'A CREE QUE DE LA HAINE.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 26:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "JE SERAIS CELUI...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 27:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "QUI RETABLIRA UNE SEULE ESPECE !!!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 28:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "???", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 29:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "VOUS les TOTSUZENS...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 30:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Allez disparaitre.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 31:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Il ne restera que les humains normaux.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 32:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Dont moi le chef, possedant des pouvoirs.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 33:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu souhaites le chaos, Zerod.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 34:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Peut-etre que c'est le chaos apres tout,", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 35:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "ce que je recherche.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 36:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Mais cette race. Des totsuzens...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 37:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ont tues mon frere et mes soeurs.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 38:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je vois. La haine t'aveugle.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 39:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ils ont tues ton frere et tes soeurs..", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 40:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Toi. Tu as tue ma famille.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 41:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "ET ALORS ??? TU LES REJOINDRAS.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 42:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Zerod...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 43:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ton plan s'acheve ici.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 44:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Certes, tu as tue tous les totsuzens...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 45:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Mais tu ne me tueras pas moi,", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 46:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "LE DERNIER TOTSUZEN!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 47:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "HAHAHAHAHAHAHAHAHA", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 48:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "A  P  P  R  O  C  H  E  !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit

				//Fin de la musique
				mediaPlayer.stop();

				//On charge la musique du boss
				mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveauFINAL/phaseII.mp3").toURI().toString()));
				break;
			}

			if(getNbEspaceAppuyés() > 48) { //Fin du dialogue
				enTransition = true;
				setEnDialogue(false); 
				finDialogue = true; //Fin du dialogue, le combat commence

			}
		}else { //Mort de Zerod
			switch(getNbEspaceAppuyés()) {
			case 0:
				//Toutes les balles ennemies meurent
				Manipulation.sprites(root).stream().filter(e -> e.getType().contains("balle")).forEach(balle -> {
					balle.meurs();
				});

				Manipulation.ajouterImage(root, "Images/Dialogue/Salar_Colère.png", 0, 300, 200, 500); //Salar
				Manipulation.ajouterImage(root, "Images/NiveauFinal/Dialogue/zerod.png", NiveauInterface.LARGEUR_DECOR-265, 250, 250, 530);
				Manipulation.effacerDialogue(this); //Affiche la zone de texte
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "C'est fini... Zerod.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 1:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Argh... C.. Comment est-ce possible ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 2:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Zerod... Ton idee etait mauvaise.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 3:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai tente de te raisonner...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 4:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je n'y suis pas parvenu.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 5:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "ET DONC ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 6:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Laisse moi... Retablir la paix.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 7:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "LA PAIX N'EXISTERA JAMAIS DANS UN...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 8:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "DANS UN MONDE SI DESEQUILIBRE !!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 9:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Si. C'est toi l'origine du desequilibre.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 10:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Avant toi... Il n'y avait pas de problemes.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 11:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 12:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu as agis seulement par vengeance.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 13:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu n'avais qu'a tuer seulement ta famille.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 14:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Et NON NOTRE ESPECE !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 15:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tss. Hahahaha !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 16:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Peu importe. J'ai atteint mon objectif.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 17:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Te voila maintenant le seul Totsuzen.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 18:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai plus rien a accomplir.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 19:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Enflure...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 20:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, nomBOSS, "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "La paix... N'existera jamais...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 21:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, " ", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Zerod tombe raide.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;

			case 22:
				Manipulation.effacerDialogue(this); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tss.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				break;
			}

			if(getNbEspaceAppuyés() > 22) { //Fin du dialogue et du niveau.
				setEnDialogue(false); 
				setNiveauTerminé(true);
				setTpsDébScore(getTempsTotal());
			}
		}
	}

	/**
	 * La transition où Zerod tremble
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

			//On réaffiche les personnages
			Manipulation.ajouterJeu(getRoot(), joueur.getImg());
			Manipulation.ajouterJeu(getRoot(), boss.getImg());

			//On enlève puis remet la barre de vie 
			Manipulation.supprimer(this, boss.getBarreVie());
			Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

			Manipulation.updateScore(this); //On remet le score

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

			mediaPlayer.stop();

			//Son de la transition
			mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveauFINAL/transition.mp3").toURI().toString()));
			if(Main.son()) mediaPlayer.play();

			tpsDébutTransition = getTempsTotal(); //Début de la transition pour pouvoir l'arrêter à temps dans niveau()

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

			//On réaffiche les personnages
			Manipulation.ajouterJeu(getRoot(), joueur.getImg());
			Manipulation.ajouterJeu(getRoot(), boss.getImg());

			//On enlève puis remet la barre de vie 
			Manipulation.supprimer(this, boss.getBarreVie());
			Manipulation.ajouterJeu(getRoot(), boss.getBarreVie());

			Manipulation.updateScore(this); //On remet le score

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

			tpsDébutTransition = getTempsTotal(); //Début de la transition pour pouvoir l'arrêter à temps dans niveau()

			lockFinDialogue = true;
		}if(commencé) { //Zerod tremble (En mode enervé)
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
		if((getTempsTotal() - tpsDébutBoss) < STOP){
			//Zerod tremble (Change la position puis remet où il était initialement pour le prochain)

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
		}else { //Zerod tremble beaucoup (à la fin)
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
	 * Les éclairs mais horizontaux
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	private void éclairsHorizontaux() throws FileNotFoundException{
		double random = Math.random();

		if((Manipulation.siTpsA(getTemps(), 0.05, false)) || (Manipulation.siTpsA(getTemps(), 1.05, false))) {
			avertissement2.setFill(Color.RED); //Avertissement rouge
			avertissement2.setOpacity(0); //Au début invisible
			Manipulation.ajouterJeu(root, avertissement2);

			//Quel endroit ? (33% chacun)
			if(random < 0.33)
				endroit = 0; //0: Tout à gauche
			else if(random > 0.34 && random < 0.67)
				endroit = 1; //1: Au milieu
			else
				endroit = 2; //2: Tout à droite

			//On met à l'endroit choisi au hasard
			if(endroit==0)
				avertissement2.relocate(0, 0);
			else if (endroit==1)
				avertissement2.relocate(0, tiers_écran2);
			else
				avertissement2.relocate(0, tiers_écran2*2);
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

		//L'éclair apparait!
		if((Manipulation.siTpsA(getTemps(), 0.91, false)) || (Manipulation.siTpsA(getTemps(), 1.91, false)))
			new EnnemiBalle(0,endroit*tiers_écran2, NiveauInterface.LARGEUR_ECRAN, tiers_écran2, "Images/éclair2.png", "balle éclair2", this); //Va vers le joueur par la droite
	}
}
