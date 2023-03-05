package niveau;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import appli.Main;
import balles.EnnemiBalle;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import manipulation.Manipulation;
import personnage.BOSS;
import personnage.BarreDeVie;
import personnage.Joueur;
import personnage.Salar;

/**
 * Vrai dernier niveau: Contre Salar.
 * 
 * @author Jordan
 */
public class vsSalar extends Niveau {
	private static final double POSITION_SALAR = 300;

	//===================== TIMING DU DIALOGUE EN SECONDES ===================\\
	private static final double DEBUT = 0.2; //Début du dialogue
	private static final double DIALOGUE_2 = 5; //"Pourquoi ?"
	private static final double DIALOGUE_3 = 7; //"Pourquoi je sens que je n'ai toujours rien accompli?"
	private static final double DIALOGUE_4 = 10; //"..."
	private static final double DIALOGUE_5 = 12; //"J'ai... J'ai i'mpression de ne pas être moi-même"
	private static final double DIALOGUE_6 = 15; //"?" *regarde le joueur
	private static final double DIALOGUE_7 = 17; //"Qui est-tu ?"
	private static final double DIALOGUE_8 = 19; //"... Je vois."
	private static final double DIALOGUE_9 = 22; //"Tu es derrière tout ça."
	private static final double DIALOGUE_10 = 25; //"...J'ai donc été contrôlé ?"
	private static final double DIALOGUE_11 = 28; //"Toutes mes actions[...]"
	private static final double DIALOGUE_12 = 31; //"Toi..."
	private static final double DIALOGUE_13 = 34; //"En réalité... C'était TOI[...]"
	private static final double DIALOGUE_14 = 37; //"Toutes mes actions[...]"
	private static final double DIALOGUE_15 = 40; //Suite
	private static final double DIALOGUE_16 = 43; 
	private static final double DIALOGUE_17 = 46;
	private static final double DIALOGUE_18 = 49;
	private static final double DIALOGUE_19 = 52; 
	private static final double DIALOGUE_20 = 55; 
	private static final double DIALOGUE_21 = 58;
	private static final double DIALOGUE_22 = 61; 
	private static final double DIALOGUE_23 = 64; 
	private static final double TRANSITION = 67; 

	//===================== TIMING DE LA TRANSITION EN SECONDES ===================\\
	private static final double SON_TRANSITION = 1; //La musique se lance
	private static final double BOUGE = 12; //La fenêtre arrête de bouger
	private static final double PREPARATION = 14; //Préparation du combat [Dans le code] (Fenêtre cachée)
	private static final double DEBUT_COMBAT = 16; //Début du combat

	//===================== TIMING DU COMBAT EN SECONDES ===================\\
	private static final double DESCENTE = 1; //Salar descend
	private static final double FIN_DESCENTE = 15; //Arrête de descendre
	private static final double APPARITION = 17; //Révélation de Salar (C'était une ombre avant)	
	private static final double COMMENCE = 25; //Attaque en balayage
	private static final double ATTAQUE2 = 35; //Attaque simillaire en horizontal
	private static final double FIN_ATTAQUE2 = 44; //Fin de l'attaque
	private static final double ATTAQUE3 = 45; //Attaque simillaire en vertical
	private static final double FIN_ATTAQUE3 = 49; //Fin de l'attaque et début de l'alerte
	private static final double FIN_ALERTE = 53.5; //Fin de l'alerte
	private static final double STATIQUE = 53.5; //Transition
	private static final double ENNEMI = 54; //Contre l'ennemi du niveau I corrompu
	private static final double STATIQUE2 = 67.5; //Transition II
	private static final double ENNEMI2 = 69; //Contre le miniboss du niveau I corrompu
	private static final double STATIQUE3 = 82; //Transition III
	private static final double COLERE = 82.5; //Pendant la transition, Salar parle
	private static final double RETOUR = 83; //Salar est de retour
	private static final double RETOUR2 = 91; //Attaque des balles a gauche et à droite
	private static final double FIN_RETOUR2 = 96.5; //Attaque des balles a gauche et à droite
	private static final double RETOUR3 = 97; //Murs de balles
	private static final double ALERTE2 = 103.75; //2ème alerte
	private static final double FIN_ALERTE2 = 110; //Fin de l'alerte 2
	private static final double STATIQUE4 = 110.5; //Transition IV
	private static final double ENNEMI3 = 112; //Contre le boss du niveau I corrompu
	private static final double FIN_ENNEMI3 = 123.75; //Fin de l'attaque
	private static final double STATIQUE5 = 124.75; //Transition V
	private static final double ENNEMI4 = 125.25; //Contre un ennemi du niveau VI corrompu
	private static final double STATIQUE6 = 139; //Transition VI
	private static final double BACK = 140; //Retour de Salar
	private static final double BACK2 = 147; //Retour de Salar
	private static final double BACK3 = 153; //Retour de Salar
	private static final double ALERTE3 = 157.5; //3ème alerte
	private static final double FIN_ALERTE3 = 165.5; //Fin de l'alerte 3
	private static final double STATIQUE7 = 165.75; //Transition VII
	private static final double ENNEMI5 = 166; //Contre le boss du niveau VI corrompu
	private static final double STATIQUE8 = 178.75; //Transition VIII
	private static final double ENNEMI6 = 179.5; //Contre le joueur en miroir
	private static final double STATIQUE9 = 194; //Transition IX
	private static final double ENNEMI7 = 194.4; //Contre Zerod corrompu (Au lieu de Salar)
	private static final double ENNEMI7_2 = 204; //Autre attaque
	private static final double DERNIERE_ALERTE = 218.75; //Dernière alerte
	private static final double FIN_DERNIERE_ALERTE = 222; //Dernière alerte
	private static final double DERNIER_STATIQUE = 222.5; //Dernière transition
	private static final double DIALOGUE = 225.25; //Dialogue avant la phase II
	private static final double DIALOGUE2 = 229; //Dialogue avant la phase II
	private static final double DIALOGUE3 = 233; //Dialogue avant la phase II
	private static final double DIALOGUE4 = 237; //Dialogue avant la phase II
	private static final double DIALOGUE5 = 243; //Dialogue avant la phase II
	private static final double FINAL = 246.5; //Phase II

	//===================== TIMING DU DIALOGUE DE FIN EN SECONDES ===================\\
	private static final double FIN1 = 1;
	private static final double FIN2 = 3;
	private static final double FIN3 = 5;
	private static final double FIN4 = 7;
	private static final double FIN5 = 9;
	private static final double FIN6 = 11;
	private static final double FIN7 = 13;
	private static final double FIN8 = 15;
	private static final double FIN9 = 17;
	private static final double FIN10 = 19;
	private static final double FIN11 = 21;
	private static final double FIN12 = 23;
	private static final double FIN13 = 25;
	private static final double TUER = 27;

	//===================== TIMING DE LA FIN EN SECONDES ===================\\
	private static final double FINI = 5; //Fin de la fenêtre

	private static final double VIE_JOUEUR = 700; //La vie du joueur
	private static final double VIE_BOSS = 700; //La vie de Salar
	private static double DEGAT_SALAR = 1;//17; //Les dégâts que fait Salar au joueur
	private static final double DEGAT_BALLE_JOUEUR = 20; //Les dégâts que fait le joueur à Salar (Phase II)
	private static Stage NIVEAU = null; //Le niveau pour manipuler la fenêtre

	// ================================== Manipulations pour charger le son  ==================================
	private static Media son = new Media(new File("Sons/niveau/réalité.mp3").toURI().toString());
	private static MediaPlayer mediaPlayer = new MediaPlayer(son);

	// ================================== Objets qui vont permettre l'affichage: Tout ce qu'on ajoute dedans sera affiché ==================================
	private static ImageView imageFond = new ImageView();

	private BarreDeVie vie = null; //Vie du joueur (C'est une barre de vie dans ce combat)
	// ================================== Variables qui gèrent le boss ==================================
	private static Salar boss = null; //Le BOSS
	private static ImageView salar = new ImageView();
	private static boolean combat = false; //Le boss est-t-il apparu ?
	private static boolean phaseI = false; //Phase I commencée ?
	private static boolean commencé = false; //La phase II a-t-elle commencée ?
	private boolean bossVersDroite = false; //Le boss bouge-t-il vers la droite ? (Pour gérer son mouvement)
	private double tpsDébutBoss = 9999; //A quel moment le combat contre le boss commence ?
	private boolean mort = false; //Mort du boss
	private Rectangle alerte = new Rectangle(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); //Rectangle rouge qui prend tout l'écran (L'alerte du boss)
	private boolean lockDialogue = false; //Pour afficher le dialogue de fin qu'une fois
	private static boolean fin = false; //Fin réel du combat

	// ================================== Variables qui gèrent la transition ==================================
	private double tpsDébutTransition = 999; //Pour la transition de début
	private double tpsDébutDialogue = 999; //Pour le dialogue de fin
	private double tpsDébutFin = 999; //Pour la fenêtre qui tremble à la fin

	@Override
	public void update() throws FileNotFoundException {
		//Le getTemps() d'une frame en JavaFX (On le met ici pour pouvoir manipuler les objets selon le getTemps())
		setTemps(getTemps() + NiveauInterface.TEMPS_1_FRAME);
		setTempsTotal(getTempsTotal() + NiveauInterface.TEMPS_1_FRAME);

		//Si ça fait 100 millisecondes que le joueur a tiré
		if(getTempsTotal() - getTpsDébTir() > NiveauInterface.COOLDOWN_TIR) setCooldownTir(false);

		//Pour mieux calculer le getTemps()
		if(getTemps() > 2)
			setTemps(0);

		//Quand le combat commence, le joueur apparaîtra
		if(combat) {
			//Si cooldown écoulé
			if (getTempsTotal() - getTpsDébTouché() > NiveauInterface.COOLDOWN_REGENERATION) {
				setCooldownTouché(false);
				joueur.misAJour(false); //A mettre a jour
			}
		}

		//Pour chacun des sprite présent sur le jeu on va les mettre à jour
		Manipulation.sprites(root).forEach(s -> {   
			switch(s.getType()) {
			case "joueur":
				if(phaseI && !mort) { //Dès que le combat commence
					if(!Niveau.isDebug()) { //Si pas en mode debug, le joueur n'est pas invincible
						//Si collision avec le boss
						Manipulation.sprites(root).stream().filter(e -> e.getType().contains("boss") || e.getType().contains("balle")).forEach(ennemi -> {
							if (Manipulation.touche(s, ennemi)) {
								vie.setWidth(vie.getWidth()-DEGAT_SALAR);
								updateCouleur();

								//Si le joueur est mort
								if(vie.getWidth()<0)
									System.exit(0);
							}
						});
					}
				}

				break;

			case "boss":
				if(phaseI && !mort) { //Phase I
					//Si contre un mur
					if(s.getTranslateX() <= 15 || s.getTranslateX() >= NiveauInterface.LARGEUR_ECRAN - 140) 
						bossVersDroite = !bossVersDroite; 

					// ========================= MOUVEMENT DES ENNEMIS (SAUF SALAR QUAND IL EST EN GRAND ET LE JOUEUR EN MIROIR) ==========================
					if((getTempsTotal() - tpsDébutBoss) < RETOUR || (((getTempsTotal() - tpsDébutBoss) > ENNEMI3) && (getTempsTotal() - tpsDébutBoss) < BACK) || (((getTempsTotal() - tpsDébutBoss) > ENNEMI5) && (getTempsTotal() - tpsDébutBoss) < ENNEMI6) || (getTempsTotal() - tpsDébutBoss) > FINAL) {
						//En rond
						if((getTemps() > 0 && getTemps() < 0.25) || (getTemps() > 1 && getTemps() < 1.25)) {
							s.bougerDroite(0.5);
							s.bougerBas(0.5);
						}else if ((getTemps() > 0.25 && getTemps() < 0.5) || (getTemps() > 1.25 && getTemps() < 1.5)) {
							s.bougerGauche(0.5);
							s.bougerBas(0.5);
						}else if ((getTemps() > 0.5  && getTemps() < 0.75) || (getTemps() > 1.5 && getTemps() < 1.75)) {
							s.bougerGauche(0.5);
							s.bougerHaut(0.5);
						}else if ((getTemps() > 0.75 && getTemps() < 1) || (getTemps() > 1.75 && getTemps() < 2)) {
							s.bougerDroite(0.5);
							s.bougerHaut(0.5);
						}
					}

					//Lorsque ce n'est pas Salar
					if(((getTempsTotal() - tpsDébutBoss) > ENNEMI && (getTempsTotal() - tpsDébutBoss) < RETOUR) || (((getTempsTotal() - tpsDébutBoss) > ENNEMI3) && (getTempsTotal() - tpsDébutBoss) < BACK)) {
						if(bossVersDroite) {
							if((getTempsTotal() - tpsDébutBoss) < ENNEMI4)
								s.bougerDroite(0.125);
							else
								s.bougerDroite(0.05); //Ennemi du niveau VI
						}else {
							if((getTempsTotal() - tpsDébutBoss) < ENNEMI4)
								s.bougerGauche(0.125);
							else
								s.bougerGauche(0.05); //Ennemi du niveau VI
						}
					}

					//Le boss tire
					try {
						bossTir();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;

				//Balle de l'attaque par balai (Toute gauche)
			case "balle balaie gg":
				s.bougerGauche(0.75);
				s.bougerBas(0.25);
				break;

				//Balle de l'attaque par balai (Gauche)
			case "balle balaie g":
				s.bougerGauche(1.5);
				s.bougerBas(0.25);
				break;

				//Balle de l'attaque par balai (Vers le bas)	
			case "balle balaie":	
				if((getTempsTotal() - tpsDébutBoss) > ATTAQUE3) //Balai vertical
					s.bougerBas(0.2);
				else //Balai du début
					s.bougerBas(0.25);
				break;

				//Balle de l'attaque par balai (Toute gauche)	
			case "balle balaie d":
				s.bougerDroite(1.5);
				s.bougerBas(0.25);
				break;

				//Balle de l'attaque par balai (Toute gauche)	
			case "balle balaie dd":
				s.bougerDroite(0.75);
				s.bougerBas(0.25);
				break;

				//Balle de l'attaque par balai horizontal (Vers la gauche)
			case "balle vers gauche":
				s.bougerGauche(0.2);
				break;

				//Balle de l'attaque par balai horizontal (Vers la droite)
			case "balle vers droite":
				s.bougerDroite(0.2);
				break;

				//Balle de l'attaque par balai vertical (Vers le haut)
			case "balle vers haut":
				s.bougerHaut(0.2);
				break;

				//N: Nord, O: ouest...
			case "balle N":
				s.bougerHaut(0.5); 
				break;


			case "balle NO":
				s.bougerHaut(0.5);
				s.bougerGauche(0.5);
				break;

			case "balle O":
				if((getTempsTotal() - tpsDébutBoss) < RETOUR2 || (getTempsTotal() - tpsDébutBoss) > ENNEMI3) //Spirale (Miniboss niveau I) & Partout (Boss niveau I) & Balles de tous les côtés (Retour de Salar II)
					s.bougerGauche(0.5);
				else
					s.bougerGauche(1); //Retour de Salar
				break;

			case "balle OS":
				s.bougerGauche(0.5);
				s.bougerBas(0.5);
				break;

			case "balle S":
				if((getTempsTotal() - tpsDébutBoss) < ENNEMI4 ||(getTempsTotal() - tpsDébutBoss) > BACK) //Partout (Boss niveau I) & Balles de tous les côtés (Retour Salar II)
					s.bougerBas(0.5);
				else
					s.bougerBas(2); //Balles lentes (Ennemi niveau VI)
				break;

			case "balle SE":
				s.bougerBas(0.5);
				s.bougerDroite(0.5);
				break;

			case "balle E":
				if((getTempsTotal() - tpsDébutBoss) < RETOUR2 || (getTempsTotal() - tpsDébutBoss) > ENNEMI3) //Spirale (Miniboss niveau I) & Partout (Boss niveau I) & Balles de tous les côtés (Retour de Salar II)
					s.bougerDroite(0.5);
				else
					s.bougerDroite(1);
				break;

			case "balle NE":
				s.bougerHaut(0.5);
				s.bougerDroite(0.5);
				break;

			case "balle aléatoire haut": //Balles aléatoires vers le haut (Contre le joueur en miroir)
				s.bougerHaut(2);
				break;

			case "balle aléatoire bas": //Balles aléatoires vers le bas (Contre le joueur en miroir)
				s.bougerBas(2);
				break;

			case "balle aléatoire gauche": //Balles aléatoires vers la gauche (Contre le joueur en miroir)
				s.bougerGauche(2);
				break;

			case "balle aléatoire droite": //Balles aléatoires vers la droite (Contre le joueur en miroir)
				s.bougerDroite(2);
				break;

			case "joueur bullet": //Balle du joueur
				s.bougerHaut(1);

				//Si collision avec le boss (Combat phase II commencé et le boss pouvait être touché)
				if(isPeutTirer() && !mort) {
					Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("boss")).forEach(bosss -> {
						if (Manipulation.touche(s, bosss)){
							s.meurs();
							boss.perdreVie(DEGAT_BALLE_JOUEUR); //Il perd de la vie !

							//Mort du boss
							if(boss.getVieBossActuelle() <= 0) {
								mort = true;

								//Les balles du joueur meurent
								Manipulation.sprites(root).stream().filter(e -> e.getType().contains("bullet")).forEach(balle -> {
									balle.meurs();
								});

								try {
									dialogue(); //Bloque le joueur
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					});
				}else if (mort) { //Si c'est le coup de grâce
					Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("boss")).forEach(bosss -> {
						if (Manipulation.touche(s, bosss)) {
							s.meurs(); //Il disparaît
							fin = true;
							mediaPlayer.play(); //Transition
							tpsDébutFin = getTempsTotal();

							//Fond noir
							try {
								imageFond = new ImageView(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/Rien.png")));
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} //Changement du fond (new Imageview pour ajouter une couche par dessus la boîte de dialogue)
						}
					});
				}
			}});

		niveau();
	}

	@Override
	public void texteNiveau() throws FileNotFoundException {
		//Pas d'affichage du texte pour le niveau
	}

	@Override
	public void niveau() throws FileNotFoundException {
		// ==================================================================== DIALOGUE DE DEBUT ====================================================================
		if(!commencé) {
			if(Manipulation.siTpsA(getTempsTotal(), DEBUT, false)) {
				setEnDialogue(true); //En dialogue

				//Dialogue
				root.getChildren().add(salar); //On met Salar
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit

				//Si le joueur avait déjà vu le dialogue, il est zappé
				if(!Main.niveauBloqué(8)) {
					salar.setX(salar.getX()-50);
					salar.setFitWidth(300); //Sa largeur a changée
					salar.setImage(new Image(new FileInputStream("Images/Niveau/Dialogue/salar_énervé.png")));
					setTempsTotal(TRANSITION-1); //Si le joueur avait déjà vu le dialogue, il est zappé
				}
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_2, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourquoi ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_3, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourquoi je sens que je n'ai toujours rien accompli ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_4, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_5, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "J'ai... J'ai l'impression de ne pas etre moi-meme...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_6, false)) {
				//Image de Salar a changée
				salar.setX(salar.getX()-50);
				salar.setFitWidth(300); //Sa largeur a changée
				salar.setImage(new Image(new FileInputStream("Images/Niveau/Dialogue/salar_regarde.png")));

				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_7, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Qui es-tu ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_8, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "... Je vois.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_9, false)) {
				//La musique se lance
				mediaPlayer.play();
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu es derriere tout ca.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_10, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "... J'ai donc ete controle ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if((getTempsTotal() > DIALOGUE_11) && getTempsTotal() < TRANSITION) {
				tremble(); //Salar devient fou
			}
			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_11, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Toutes mes actions... N'etaient pas de moi-meme ?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_12, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Toi...","Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_13, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "En realite... C'est TOI qui etait a l'origine de tout ca.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_14, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Toutes mes actions... Mes eliminations...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_15, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "TU etais en realite LA personne ayant fait tout ca.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_16, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Depuis quand peut-on se permettre de controler un...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_17, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "... UN HUMAIN POUR ASSOUVIR SES DESIRS DE MEURTRE ?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_18, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "DEPUIS QUAND ON M'UTILISE ??", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_19, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "POUR...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_20, false)) {
				//Il s'énerve
				salar.setImage(new Image(new FileInputStream("Images/Niveau/Dialogue/salar_énervé.png")));

				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "TUER ?!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_21, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je... JE VAIS T'ETRIPER !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_22, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				try { //InetAddress..... Affiche le nom du PC pour un effet de surprise
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, InetAddress.getLocalHost().getHostName() + "!!! TES CONNERIES S'ARRETENT.", "Polices/Dialogue.ttf", 15, "BLACK");
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal(), DIALOGUE_23, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "PEU IMPORTE QUI TU ES, TU NE ME CONTROLE PAS !!!", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			// ==================================================================== TRANSITION ====================================================================
			if(Manipulation.siTpsA(getTempsTotal(), TRANSITION, true)){
				mediaPlayer.stop();
				mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveau/transition.mp3").toURI().toString()));

				tpsDébutTransition = getTempsTotal();
			}

			//Début du combat
			if((getTempsTotal() > TRANSITION) && !combat) 
				boss();

			// ==================================================================== DEBUT DU COMBAT ====================================================================
			//La musique se lance
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, 0, false)) {
				//Le dialogue de début a été vu, le joueur ne le verra plus jamais
				if(Main.niveauBloqué(8))
					try {Main.sauvegarde(8);} catch (IOException e) {System.err.println("Erreur lors de la sauvegarde");} 

				mediaPlayer.play(); //La musique se lance
				combat = true; //Le combat commence
				setEnDialogue(false);

				//Le joueur 
				joueur = new Joueur(380,700,45,45, "joueur", "Images/NIVEAU/joueur.gif", this);
			}

			//Salar descend
			if((getTempsTotal() - tpsDébutBoss) > DESCENTE && (getTempsTotal() - tpsDébutBoss) < FIN_DESCENTE)
				boss.bougerBas(2);

			//Salar se révèle, l'ombre devient Salar
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, APPARITION, false)) {
				//On enregistre la position en hauteur de Salar
				double y = boss.getTranslateY();

				//Pour en créer un autre pour qu'il soit avec une autre image (setImg ne marche pas, il double Salar)
				boss.meurs();

				boss = new Salar((NiveauInterface.LARGEUR_ECRAN/2)-70, y, 100, 150, "boss", 99999, "Images/Niveau/boss.png", new BarreDeVie(10,40,9999,10, "vie", this), this);			
				Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre
			}

			// ==================================================================== DEBUT DU COMBAT ====================================================================
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, COMMENCE, true)) {
				phaseI = true; //Le combat commence
				//Le fond change
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU/1.gif")));

				//Initialisation de sa vie
				vie = new BarreDeVie(50, 700, VIE_JOUEUR, 10, "vieJoueur", this);
			}

			//Pour l'apparition de l'alerte qui se base sur le temps (Comme les avertissemetns des éclairs)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FIN_ATTAQUE3, false))
				setTemps(0);

			//Alerte
			if(((getTempsTotal() - tpsDébutBoss) > FIN_ATTAQUE3 && (getTempsTotal() - tpsDébutBoss) < FIN_ALERTE) || ((getTempsTotal() - tpsDébutBoss) > ALERTE2 && (getTempsTotal() - tpsDébutBoss) < FIN_ALERTE2) ||
					((getTempsTotal() - tpsDébutBoss) > ALERTE3 && (getTempsTotal() - tpsDébutBoss) < FIN_ALERTE3) || ((getTempsTotal() - tpsDébutBoss) > DERNIERE_ALERTE && (getTempsTotal() - tpsDébutBoss) < FIN_DERNIERE_ALERTE))
				alerte();

			//Statique (Transition)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, STATIQUE, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, STATIQUE2, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, STATIQUE3, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, STATIQUE4, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, STATIQUE5, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, STATIQUE6, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, STATIQUE7, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, STATIQUE8, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, STATIQUE9, false) ||
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DERNIER_STATIQUE, false)) {
				//Toutes les balles ennemies meurent
				Manipulation.sprites(root).stream().filter(e -> e.getType().contains("balle")).forEach(balle -> {
					balle.meurs();
				});

				Manipulation.supprimer(this, alerte); //Suppression de l'alerte
				Manipulation.supprimer(this, joueur.getImg()); //Aussi du joueur
				Manipulation.supprimer(this, boss.getImg()); //Du boss
				Manipulation.supprimer(this, vie); //Et de la vie du joueur pour qu'il y ait seulement le statique
				imageFond.setImage((new Image(new FileInputStream("Fonds/NIVEAU/statique.gif"))));
			}

			//Autre phase de combat (Contre un ennemi du niveau I)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI, true)) {
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU/2.gif"))); //Changement du fond
				Manipulation.ajouterJeu(root, vie);
				Manipulation.ajouterJeu(root, joueur.getImg());

				//L'ennemi du niveau I bugé apparaît (Il est compté toujours comme le boss et comme, setImg ne marche pas, il double le boss, on doit en recréer un)
				boss.meurs();

				boss = new Salar((NiveauInterface.LARGEUR_ECRAN/2)-70, 50, 144, 116, "boss", 99999, "Images/Niveau/2.gif", new BarreDeVie(10,40,9999,10, "vie", this), this);			
				Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre
			}

			//La fenêtre tremble 
			if(((getTempsTotal() - tpsDébutBoss) > ENNEMI && (getTempsTotal() - tpsDébutBoss) < STATIQUE3) || ((getTempsTotal() - tpsDébutBoss) > ENNEMI3 && (getTempsTotal() - tpsDébutBoss) < STATIQUE6) || 
					((getTempsTotal() - tpsDébutBoss) > ENNEMI5 && (getTempsTotal() - tpsDébutBoss) < STATIQUE9))
				fenêtreTremble();

			//Autre phase de combat (Contre un miniboss du niveau I)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI2, true)){
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU/3.gif"))); //Changement du fond
				Manipulation.ajouterJeu(root, vie);
				Manipulation.ajouterJeu(root, joueur.getImg());

				//Le miniboss du niveau I bugé apparaît (Il est compté toujours comme le boss et comme, setImg ne marche pas, il double le boss, on doit en recréer un)
				boss.meurs();

				boss = new Salar((NiveauInterface.LARGEUR_ECRAN/2)-70, 50, 140, 140, "boss", 99999, "Images/Niveau/3.gif", new BarreDeVie(10,40,9999,10, "vie", this), this);			
				Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre
			}

			//Salar parle pendant la 3ème transition
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, COLERE, true)) {
				setEnDialogue(true); //En dialogue

				//Dialogue
				root.getChildren().add(salar); //On met Salar
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "T U  N E  M E  C O N T R O L E  P A S ! !", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			//Salar est de retour
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, RETOUR, false)) {
				Manipulation.supprimer(this, salar); //On enlève Salar du dialogue
				Manipulation.supprimer(this, imageFond); //Ancienne version de l'image du fond (Statique)

				imageFond = new ImageView(new Image(new FileInputStream("Fonds/NIVEAU/4.gif"))); //Changement du fond (new Imageview pour ajouter une couche par dessus la boîte de dialogue)

				Manipulation.ajouterJeu(root, imageFond); //On ajoute la nouvelle version pour qu'elle soit SUR la boite de dialogue
				regénération(); //La vie du joueur se regénère
				Manipulation.ajouterJeu(root, vie);
				Manipulation.ajouterJeu(root, joueur.getImg());

				//Salar
				boss.meurs();

				boss = new Salar(0, 0, NiveauInterface.LARGEUR_ECRAN, 300, "boss", 99999, "Images/Niveau/boss2.gif", new BarreDeVie(10,40,9999,10, "vie", this), this);			
				Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre
				setEnDialogue(false); //Ce n'est plus en dialogue

				DEGAT_SALAR = 9; //Plus dur, Salar fait moins de dégâts
			}


			//Autre phase de combat (Contre le boss du niveau I)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI3, false)){
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU/5.png"))); //Changement du fond
				Manipulation.ajouterJeu(root, vie);
				Manipulation.ajouterJeu(root, joueur.getImg());

				//Le boss du niveau I bugé apparaît 
				boss.meurs();

				boss = new Salar((NiveauInterface.LARGEUR_ECRAN/2)-70, 50, 140, 140, "boss", 99999, "Images/Niveau/5.gif", new BarreDeVie(10,40,9999,10, "vie", this), this);			
				Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre
			}

			//Une fenêtre CMD s'ouvre
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI3, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI4, false) || 
					Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI5, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI6, false))
				piratage(true); //True car on va ouvrir CMD

			//Autre phase de combat (Contre un ennemi du niveau VI)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI4, false)){
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU/6.gif"))); //Changement du fond
				Manipulation.ajouterJeu(root, vie);
				Manipulation.ajouterJeu(root, joueur.getImg());

				//Un ennemi du niveau VI bugé apparaît 
				boss.meurs();

				boss = new Salar((NiveauInterface.LARGEUR_ECRAN/2)-70, 50, 140, 140, "boss", 99999, "Images/Niveau/6.gif", new BarreDeVie(10,40,9999,10, "vie", this), this);			
				Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre
			}

			//Salar est de retour
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, BACK, false)) {
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU/7.gif"))); //Changement du fond (new Imageview pour ajouter une couche par dessus la boîte de dialogue)
				regénération(); //La vie du joueur se regénère
				Manipulation.ajouterJeu(root, vie);
				Manipulation.ajouterJeu(root, joueur.getImg());

				//Salar
				boss.meurs();

				boss = new Salar(0, 0, NiveauInterface.LARGEUR_ECRAN, 300, "boss", 99999, "Images/Niveau/boss2.gif", new BarreDeVie(10,40,9999,10, "vie", this), this);			
				Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre

				DEGAT_SALAR = 3;
			}

			//Autre phase de combat (Contre le boss du niveau VI)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI5, false)){
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/Rien.png"))); //Changement du fond
				Manipulation.ajouterJeu(root, vie);
				Manipulation.ajouterJeu(root, joueur.getImg());

				//Le miniboss du niveau I bugé apparaît (Il est compté toujours comme le boss et comme, setImg ne marche pas, il double le boss, on doit en recréer un)
				boss.meurs();

				boss = new Salar((NiveauInterface.LARGEUR_ECRAN/2)-70, 50, 84, 140, "boss", 99999, "Images/Niveau/8.gif", new BarreDeVie(10,40,9999,10, "vie", this), this);			
				Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre
			}

			//Le lecteur CD s'ouvre
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI5, false) || Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI6, false))
				piratage(false); //False car on n'ouvre pas une fenêtre CMD mais le lecteur CD

			//Autre phase de combat (Contre le joueur)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI6, false)){
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU/MENU.gif"))); //Changement du fond
				Manipulation.ajouterJeu(root, vie);
				Manipulation.ajouterJeu(root, joueur.getImg());

				//Le miniboss du niveau I bugé apparaît (Il est compté toujours comme le boss et comme, setImg ne marche pas, il double le boss, on doit en recréer un)
				boss.meurs();

				boss = new Salar((NiveauInterface.LARGEUR_ECRAN/2)-70, 50, 48, 48, "boss", 99999, "Images/Niveau/joueur.gif", new BarreDeVie(10,40,9999,10, "vie", this), this);			
				Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre

			}

			//Autre phase de combat (Contre Zerod au lieu de Salar)
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, ENNEMI7, false)){
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU/8.gif"))); //Changement du fond
				Manipulation.ajouterJeu(root, vie);
				Manipulation.ajouterJeu(root, joueur.getImg());
				regénération(); //La vie du joueur se regénère

				//Le miniboss du niveau I bugé apparaît (Il est compté toujours comme le boss et comme, setImg ne marche pas, il double le boss, on doit en recréer un)
				boss.meurs();

				boss = new Salar(0, 0, NiveauInterface.LARGEUR_ECRAN, 300, "boss", 99999, "Images/Niveau/9a.gif", new BarreDeVie(10,40,9999,10, "vie", this), this);			
				Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre
			}

			//Dialogue entre les deux phases
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE, true)){
				imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/Rien.png"))); //Changement du fond
				setEnDialogue(true); //En dialogue

				//Dialogue
				root.getChildren().add(salar); //On met Salar
				salar.setImage(new Image(new FileInputStream("Images/Niveau/Dialogue/salar_blessé.png")));
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Uuuhh....", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE2, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Ca y est.....?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE3, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Apres avoir tue tout le monde....", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE4, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu comptes tuer le heros du jeu que tu joues ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
			}

			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, DIALOGUE5, false)) {
				effacerDialogue(); //"Efface" le texte précédent
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
				Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "je... je ne te laisserais pas faire...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				DEGAT_SALAR = 10; 
			}

			//Salar est de retour
			if(Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, FINAL, true)) {
				Manipulation.supprimer(this, salar); //On enlève Salar du dialogue

				imageFond = new ImageView(new Image(new FileInputStream("Fonds/NIVEAU/9.gif"))); //Changement du fond (new Imageview pour ajouter une couche par dessus la boîte de dialogue)

				Manipulation.ajouterJeu(root, imageFond); //On ajoute la nouvelle version pour qu'elle soit SUR la boite de dialogue
				regénération(); //La vie du joueur se regénère
				Manipulation.ajouterJeu(root, vie);
				Manipulation.ajouterJeu(root, joueur.getImg());

				//Salar
				boss.meurs();

				boss = new Salar((NiveauInterface.LARGEUR_ECRAN/2)-70, 100, 100, 150, "boss", VIE_BOSS, "Images/Niveau/boss.png", new BarreDeVie(10,40,VIE_BOSS,10, "vie", this), this);			
				setEnDialogue(false); //Ce n'est plus en dialogue
				setPeutTirer(true); //Phase II, le joueur peut tirer
			}

			if(mort) {
				if(!lockDialogue) {
					lockDialogue = true;
					mediaPlayer.stop(); //Fin de la musique
					mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveau/transition.mp3").toURI().toString())); //Transition
					setEnDialogue(true); //En dialogue
					tpsDébutDialogue = getTempsTotal();
				}

				//Dialogue de fin
				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN1, true)){
					Manipulation.supprimer(this, joueur.getImg()); //Aussi du joueur
					Manipulation.supprimer(this, boss.getImg()); //Du boss
					Manipulation.supprimer(this, vie); //Et de la vie du joueur pour qu'il y ait seulement le statique
					imageFond.setImage(new Image(new FileInputStream("Fonds/NIVEAU_FINAL/Rien.png"))); //Changement du fond

					//Dialogue
					effacerDialogue(); //"Efface" le texte précédent
					boss = new Salar(POSITION_SALAR, POSITION_SALAR, 200, 500, "boss", VIE_BOSS, "Images/Niveau/Dialogue/salar_très_blessé.png", new BarreDeVie(10,40,VIE_BOSS,10, "vie", this), this);		
					Manipulation.supprimer(this, boss.getBarreVie()); //Suppression de sa barre
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN2, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "...J'ai echoue...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN3, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Je... Je n'ai pas pu t'arreter....", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN4, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "TOI, tu n'es pas humain. Tu es un demon.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN5, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pourquoi... Pourquoi tous nous tuer ?", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN6, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "... Tu es donc sans pitie.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN7, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "TU es le danger de ce monde.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN8, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu m'as controle... Tu m'as utilise pour tuer...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN9, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Tu... Tu m'as pousse a tuer...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN10, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Puis, une fois que j'ai tente de t'arreter...", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN11, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "Pour proteger ce monde... Tu me tue a mon tour.", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN12, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					try {
						Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, InetAddress.getLocalHost().getHostName() + ", je ne te connais pas reellement...", "Polices/Dialogue.ttf", 15, "BLACK");
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, FIN13, true)) {
					effacerDialogue(); //"Efface" le texte précédent
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_NOM_DIALOGUE, "Salar", "Polices/Dialogue.ttf", 20, "BLACK"); //Qui parle ?
					Manipulation.ajouterTexte(this, NiveauInterface.X_TEXTE_DIALOGUE, NiveauInterface.Y_TEXTE_DIALOGUE, "M-Mais....", "Polices/Dialogue.ttf", 15, "BLACK"); //Ce qu'il dit
				}

				if(Manipulation.siTpsA(getTempsTotal() - tpsDébutDialogue, TUER, true)) {
					Manipulation.ajouterJeu(root, joueur.getImg()); //Le joueur apparaît
					setEnDialogue(false); //Plus en dialogue
				}

				//Salar mort
				if(fin) {
					fenêtreTremble();

					if(Manipulation.siTpsA(getTempsTotal() - tpsDébutFin, FINI, true)) {
						try {
							Main.sauvegarde(9); //9 = Jeu terminé, le générique de fin s'affichera au prochain démarrage
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

						System.exit(0);
					}
				}
			}
		}
	}

	@Override
	public void boss() throws FileNotFoundException {
		//La musique de transition se joue
		if(Manipulation.siTpsA(getTempsTotal() - tpsDébutTransition, SON_TRANSITION, false)) {
			Manipulation.supprimer(this, salar); //On enlève Salar du dialogue
			mediaPlayer.play();
			imageFond = new ImageView(new Image(new FileInputStream("Fonds/NIVEAU/transition.gif")));
			root.getChildren().add(imageFond);
		}

		//La fenêtre bouge
		if((getTempsTotal() - tpsDébutTransition) < BOUGE)
			fenêtreTremble();


		//Le boss est crée
		if(Manipulation.siTpsA(getTempsTotal() - tpsDébutTransition, PREPARATION, true)) {
			//Boss (99999 est sa barre de vie de toute façon on ne peut pas tirer)
			boss = new Salar((NiveauInterface.LARGEUR_ECRAN/2)-70, -150, 100, 150, "boss", 99999, "Images/Niveau/debut.png", new BarreDeVie(10,40,9999,10, "vie", this), this);			
			Manipulation.supprimer(this, boss.getBarreVie()); //On n'affiche pas sa barre de vie
		}

		//La fenêtre disparaît
		if(Manipulation.siTpsA(getTempsTotal() - tpsDébutTransition, DEBUT_COMBAT, false)) {
			mediaPlayer.stop(); //Fin
			mediaPlayer = new MediaPlayer(new Media(new File("Sons/niveau/musique.mp3").toURI().toString())); //Musique du boss
			tpsDébutBoss = getTempsTotal();
		}
	}

	@Override
	public void miniBOSSTir(BOSS b) throws FileNotFoundException {
		//Pas de mini-boss
	}

	@Override
	public void bossTir() throws FileNotFoundException {

		// ============================ BALLES QUI BALAIE A GAUCHE ET A DROITE ============================
		if((getTempsTotal() - tpsDébutBoss)>COMMENCE && (getTempsTotal() - tpsDébutBoss)<ATTAQUE2){
			//Balles sur les côtés pour restreindre le joueur
			if(Manipulation.huitFoisParSecondes(getTemps())) {
				new EnnemiBalle(0, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers la "toute" gauche
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN-100, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers la "toute" gauche
			}

			//Balayage
			if(Manipulation.siTpsA(getTemps(), 0.125, false) || Manipulation.siTpsA(getTemps(), 1.125, false))
				new EnnemiBalle(boss.getTranslateX(), 0, 100, 100, "Images/joueur balle.png", "balle balaie gg", this); //Vers la "toute" gauche

			if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 1.25, false))
				new EnnemiBalle(boss.getTranslateX()+25, 0, 100, 100, "Images/joueur balle.png", "balle balaie g", this); //Vers la gauche

			if(Manipulation.siTpsA(getTemps(), 0.375, false) || Manipulation.siTpsA(getTemps(), 1.375, false)) 
				new EnnemiBalle(boss.getTranslateX()+50, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers le bas

			if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false))
				new EnnemiBalle(boss.getTranslateX()+75, 0, 100, 100, "Images/joueur balle.png", "balle balaie d", this); //Vers la droite

			if(Manipulation.siTpsA(getTemps(), 0.625, false) || Manipulation.siTpsA(getTemps(), 1.625, false))
				new EnnemiBalle(boss.getTranslateX()+100, 0, 100, 100, "Images/joueur balle.png", "balle balaie dd", this); //Vers la "toute" droite

			if(Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.75, false))
				new EnnemiBalle(boss.getTranslateX()+75, 0, 100, 100, "Images/joueur balle.png", "balle balaie d", this); 

			if(Manipulation.siTpsA(getTemps(), 0.875, false) || Manipulation.siTpsA(getTemps(), 1.875, false))
				new EnnemiBalle(boss.getTranslateX()+50, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); 

			if(Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false))
				new EnnemiBalle(boss.getTranslateX()+25, 0, 100, 100, "Images/joueur balle.png", "balle balaie g", this); 
		}

		// ============================ BALLES QUI BALAIE EN BAS ET EN HAUT  ============================
		if((getTempsTotal() - tpsDébutBoss)>ATTAQUE2 && (getTempsTotal() - tpsDébutBoss)<FIN_ATTAQUE2){
			//Balayage
			if(Manipulation.siTpsA(getTemps(), 0.125, false) || Manipulation.siTpsA(getTemps(), 1.125, false))
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN, 0, 100, 100, "Images/joueur balle.png", "balle vers gauche", this); //Vers la "toute" gauche

			if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 1.25, false))
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN, 200, 100, 100, "Images/joueur balle.png", "balle vers gauche", this); //Vers la gauche

			if(Manipulation.siTpsA(getTemps(), 0.375, false) || Manipulation.siTpsA(getTemps(), 1.375, false)) 
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN, 400, 100, 100, "Images/joueur balle.png", "balle vers gauche", this); //Vers le bas

			if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false))
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN, 600, 100, 100, "Images/joueur balle.png", "balle vers gauche", this); //Vers la droite

			if(Manipulation.siTpsA(getTemps(), 0.625, false) || Manipulation.siTpsA(getTemps(), 1.625, false))
				new EnnemiBalle(0, 700, 100, 100, "Images/joueur balle.png", "balle vers droite", this); //Vers la "toute" droite

			if(Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.75, false))
				new EnnemiBalle(0, 500, 100, 100, "Images/joueur balle.png", "balle vers droite", this); 

			if(Manipulation.siTpsA(getTemps(), 0.875, false) || Manipulation.siTpsA(getTemps(), 1.875, false))
				new EnnemiBalle(0, 300, 100, 100, "Images/joueur balle.png", "balle vers droite", this); 

			if(Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false))
				new EnnemiBalle(0, 100, 100, 100, "Images/joueur balle.png", "balle vers droite", this); 
		}

		// ============================ BALLES QUI BALAIE VERTICALEMENT  ============================
		if((getTempsTotal() - tpsDébutBoss)>ATTAQUE3 && (getTempsTotal() - tpsDébutBoss)<FIN_ATTAQUE3){
			//Balayage
			if(Manipulation.siTpsA(getTemps(), 0.125, false) || Manipulation.siTpsA(getTemps(), 1.125, false)) {
				new EnnemiBalle(0, NiveauInterface.HAUTEUR_ECRAN, 100, 100, "Images/joueur balle.png", "balle vers haut", this); //Vers le haut
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN-100, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers le bas
			}

			if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 1.25, false)) {
				new EnnemiBalle(140, NiveauInterface.HAUTEUR_ECRAN, 100, 100, "Images/joueur balle.png", "balle vers haut", this); //Vers le haut
				new EnnemiBalle(560, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers le bas
			}

			if(Manipulation.siTpsA(getTemps(), 0.375, false) || Manipulation.siTpsA(getTemps(), 1.375, false))  {
				new EnnemiBalle(280, NiveauInterface.HAUTEUR_ECRAN, 100, 100, "Images/joueur balle.png", "balle vers haut", this); //Vers le haut
				new EnnemiBalle(420, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers le bas
			}

			if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false)) {
				new EnnemiBalle(420, NiveauInterface.HAUTEUR_ECRAN, 100, 100, "Images/joueur balle.png", "balle vers haut", this); //Vers le haut
				new EnnemiBalle(280, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers le bas
			}

			if(Manipulation.siTpsA(getTemps(), 0.625, false) || Manipulation.siTpsA(getTemps(), 1.625, false)) {
				new EnnemiBalle(560, NiveauInterface.HAUTEUR_ECRAN, 100, 100, "Images/joueur balle.png", "balle vers haut", this); //Vers le haut
				new EnnemiBalle(140, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers le bas
			}

			if(Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.75, false)) {
				new EnnemiBalle(420, NiveauInterface.HAUTEUR_ECRAN, 100, 100, "Images/joueur balle.png", "balle vers haut", this); //Vers le haut
				new EnnemiBalle(280, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers le bas
			}


			if(Manipulation.siTpsA(getTemps(), 0.875, false) || Manipulation.siTpsA(getTemps(), 1.875, false)) {
				new EnnemiBalle(280, NiveauInterface.HAUTEUR_ECRAN, 100, 100, "Images/joueur balle.png", "balle vers haut", this); //Vers le haut
				new EnnemiBalle(420, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers le bas
			}

			if(Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
				new EnnemiBalle(140, NiveauInterface.HAUTEUR_ECRAN, 100, 100, "Images/joueur balle.png", "balle vers haut", this); //Vers le haut
				new EnnemiBalle(560, 0, 100, 100, "Images/joueur balle.png", "balle balaie", this); //Vers le bas
			}
		}

		// ============================ ENNEMI DU NIVEAU I  ============================
		if((getTempsTotal() - tpsDébutBoss)>ENNEMI && (getTempsTotal() - tpsDébutBoss)<STATIQUE2)
			if(Manipulation.quatreFoisParSecondes(getTemps()))
				//Tire vers le bas
				new EnnemiBalle(boss.getTranslateX()+70, boss.getTranslateY()+110, 50, 50, "Images/Niveau/2 balle.png", "balle S", this);

		// ============================ MINIBOSS DU NIVEAU I  ============================
		if((getTempsTotal() - tpsDébutBoss)>ENNEMI2 && (getTempsTotal() - tpsDébutBoss)<STATIQUE3) {
			//Tire en spirale
			if(Manipulation.siTpsA(getTemps(), 0.125, false) || Manipulation.siTpsA(getTemps(), 1.125, false))
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Bonus/BonusPoints.png", "balle N", this);
			if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 1.25, false))
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Bonus/BonusPoints.png", "balle NO", this);
			if(Manipulation.siTpsA(getTemps(), 0.375, false) || Manipulation.siTpsA(getTemps(), 1.375, false))
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Bonus/BonusPoints.png", "balle O", this); 
			if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false))
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Bonus/BonusPoints.png", "balle OS", this);
			if(Manipulation.siTpsA(getTemps(), 0.625, false) || Manipulation.siTpsA(getTemps(), 1.625, false))
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Bonus/BonusPoints.png", "balle S", this); 
			if(Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.75, false))
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Bonus/BonusPoints.png", "balle SE", this);
			if(Manipulation.siTpsA(getTemps(), 0.875, false) || Manipulation.siTpsA(getTemps(), 1.875, false))
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Bonus/BonusPoints.png", "balle E", this);
			if(Manipulation.siTpsA(getTemps(), 0.999, false) || Manipulation.siTpsA(getTemps(), 1.999, false))
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Bonus/BonusPoints.png", "balle NE", this);
		}

		// ============================ RETOUR DE SALAR  ============================
		if((getTempsTotal() - tpsDébutBoss) > RETOUR && (getTempsTotal() - tpsDébutBoss) < RETOUR2)
			//Balles aléatoires vers le bas
			if(Math.random() > 0.8)
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_ECRAN-60) - 10), 300, 75, 75, "Images/joueur balle max.png", "balle balaie", this); //Vers le bas

		if((getTempsTotal() - tpsDébutBoss) > RETOUR2 && (getTempsTotal() - tpsDébutBoss) < FIN_RETOUR2) {
			//Balles à gauche et à droite
			if(Manipulation.siTpsA(getTemps(), 0.98, true) || Manipulation.siTpsA(getTemps(), 1.98, true)){
				new EnnemiBalle(0, 300, 250, 250, "Images/joueur balle max.png", "balle E", this); //Vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN, 550, 250, 250, "Images/joueur balle max.png", "balle O", this); //Vers la gauche
			}
		}

		if((getTempsTotal() - tpsDébutBoss) > RETOUR3 && (getTempsTotal() - tpsDébutBoss) < ALERTE2) {
			//Murs de balles
			if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.25, false) || Manipulation.siTpsA(getTemps(), 1.75, false)) {
				new EnnemiBalle(0, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); //Vers la droite
				new EnnemiBalle(100, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); //Vers la droite
				new EnnemiBalle(200, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); //Vers la droite
				new EnnemiBalle(300, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); //Vers la droite
			}else if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1, false) || Manipulation.siTpsA(getTemps(), 1.5, false) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
				new EnnemiBalle(400, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); //Vers la droite
				new EnnemiBalle(500, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); //Vers la droite
				new EnnemiBalle(600, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); //Vers la droite
				new EnnemiBalle(700, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); //Vers la droite
			}
		}

		// ============================ BOSS DU NIVEAU I  ============================
		if((getTempsTotal() - tpsDébutBoss)>ENNEMI3 && (getTempsTotal() - tpsDébutBoss)<FIN_ENNEMI3){
			//Balles dans toutes les directions
			if(Manipulation.deuxFoisParSecondes(getTemps())) {
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Niveau/5 balle.png", "balle N", this); //Vers le bas
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Niveau/5 balle.png", "balle NE", this); //Vers le bas
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Niveau/5 balle.png", "balle E", this); //Vers le bas
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Niveau/5 balle.png", "balle SE", this); //Vers le bas
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Niveau/5 balle.png", "balle S", this); //Vers le bas
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Niveau/5 balle.png", "balle OS", this); //Vers le bas
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Niveau/5 balle.png", "balle O", this); //Vers le bas
				new EnnemiBalle(boss.getTranslateX(), boss.getTranslateY(), 50, 50, "Images/Niveau/5 balle.png", "balle NO", this); //Vers le bas
			}
		}

		// ============================ ENNEMI DU NIVEAU VI  ============================
		if((getTempsTotal() - tpsDébutBoss)>ENNEMI4 && (getTempsTotal() - tpsDébutBoss)<STATIQUE6) {
			//Balles sur le côté pour restreindre le joueur+
			if(Manipulation.deuxFoisParSecondes(getTemps())) {
				new EnnemiBalle(0, 0, 50, 50, "Images/Niveau/2 balle.png", "balle balaie", this); //Vers la "toute" gauche
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN-50, 0, 50, 50, "Images/Bonus/BonusVie.png", "balle balaie", this); //Vers la "toute" gauche
			}

			//Balles vers le bas lentes
			if(Math.random() > 0.9)
				new EnnemiBalle(boss.getTranslateX()+40, boss.getTranslateY()+140, 20, 20, "Images/Bonus/BonusPuissance.png", "balle S", this); //Vers le bas
		}

		// ============================ RETOUR DE SALAR II  ============================
		if((getTempsTotal() - tpsDébutBoss)>BACK && (getTempsTotal() - tpsDébutBoss)<BACK2) {
			//Balles de tous les côtés
			if(Manipulation.siTpsA(getTemps(), 0.48, false) || Manipulation.siTpsA(getTemps(), 1.48, false)) {
				new EnnemiBalle(0, 300, 250, 250, "Images/joueur balle max.png", "balle E", this); //Vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN-250, 550, 250, 250, "Images/joueur balle.png", "balle O", this); //Vers la gauche

				//Mur de balles vers le bas
				new EnnemiBalle(0, 300, 100, 100, "Images/joueur balle.png", "balle S", this); 
				new EnnemiBalle(100, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); 
				new EnnemiBalle(200, 300, 100, 100, "Images/joueur balle.png", "balle S", this);
				new EnnemiBalle(300, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); 			
			}

			if(Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
				new EnnemiBalle(0, 550, 250, 250, "Images/joueur balle.png", "balle E", this); //Vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN-250, 300, 250, 250, "Images/joueur balle max.png", "balle O", this); //Vers la gauche

				//Mur de balles vers le bas
				new EnnemiBalle(400, 300, 100, 100, "Images/joueur balle max.png", "balle S", this); 
				new EnnemiBalle(500, 300, 100, 100, "Images/joueur balle.png", "balle S", this); 
				new EnnemiBalle(600, 300, 100, 100, "Images/joueur balle max.png", "balle S", this);
				new EnnemiBalle(700, 300, 100, 100, "Images/joueur balle.png", "balle S", this); 
			}
		}

		if((getTempsTotal() - tpsDébutBoss)>BACK2 && (getTempsTotal() - tpsDébutBoss)<BACK3) {
			//Balles vers la gauche & droite "rebondissantes"
			if(Manipulation.siTpsA(getTemps(), 0.48, false) || Manipulation.siTpsA(getTemps(), 1.48, false)) {
				new EnnemiBalle(0, 300, 250, 250, "Images/joueur balle max.png", "balle E", this); //Vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN-250, 550, 250, 250, "Images/joueur balle max.png", "balle O", this); //Vers la gauche		
			}

			if(Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
				new EnnemiBalle(0, 550, 250, 250, "Images/joueur balle.png", "balle E", this); //Vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN-250, 300, 250, 250, "Images/joueur balle max.png", "balle O", this); //Vers la gauche
			}
		}

		if((getTempsTotal() - tpsDébutBoss)>BACK3 && (getTempsTotal() - tpsDébutBoss)<ALERTE3) {
			//Rideau de balles
			if(Manipulation.siTpsA(getTemps(), 0.125, false) || Manipulation.siTpsA(getTemps(), 1.125, false))
				new EnnemiBalle(0, 300, 160, 160, "Images/joueur balle.png", "balle S", this);
			if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 1.25, false))
				new EnnemiBalle(160, 300, 160, 160, "Images/joueur balle max.png", "balle S", this);
			if(Manipulation.siTpsA(getTemps(), 0.375, false) || Manipulation.siTpsA(getTemps(), 1.375, false))
				new EnnemiBalle(320, 300, 160, 160, "Images/Joueur/joueur.png", "balle S", this); 
			if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false))
				new EnnemiBalle(480, 300, 160, 160, "Images/Joueur/joueur_tir.png", "balle S", this);
			if(Manipulation.siTpsA(getTemps(), 0.625, false) || Manipulation.siTpsA(getTemps(), 1.625, false))
				new EnnemiBalle(640, 300, 160, 160, "Images/Joueur/joueur_focus.png", "balle S", this); 
			if(Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.75, false))
				new EnnemiBalle(480, 300, 160, 160, "Images/Joueur/joueur_touché.png", "balle S", this);
			if(Manipulation.siTpsA(getTemps(), 0.875, false) || Manipulation.siTpsA(getTemps(), 1.875, false))
				new EnnemiBalle(320, 300, 160, 160, "Images/éclair.png", "balle S", this);
			if(Manipulation.siTpsA(getTemps(), 0.999, false) || Manipulation.siTpsA(getTemps(), 1.999, false))
				new EnnemiBalle(160, 300, 160, 160, "Images/TITRE.png", "balle S", this);
		}

		// ============================ BOSS DU NIVEAU VI  ============================
		if((getTempsTotal() - tpsDébutBoss)>ENNEMI5 && (getTempsTotal() - tpsDébutBoss)<STATIQUE8) {
			if(Manipulation.deuxFoisParSecondes(getTemps())){
				//Pas le même écart sinon ça sera toujours le même mur avec les mêmes trous 
				int ecartBalles = (int) (Math.random() * 200)+200; //+100 évite le ecartBalles = 0

				//Création de la ligne de balles
				for(int i = 0; i < NiveauInterface.LARGEUR_ECRAN - 35; i+=ecartBalles) {
					new EnnemiBalle(i, -10, 50,50, "Images/niveau5/feu.png", "balle S", this); 
					new EnnemiBalle(i, -40, 50,50, "Images/niveau5/feu.png", "balle S", this);
				}
			}
		}

		// ============================ JOUEUR EN MIROIR  ============================
		if((getTempsTotal() - tpsDébutBoss)>ENNEMI6 && (getTempsTotal() - tpsDébutBoss)<STATIQUE9) {
			//Balles aléatoires de partout
			if(Math.random()>0.98) {
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 50, 50, "Images/Niveau/balle.gif", "balle aléatoire bas", this); //Sors du haut va vers le bas
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN, 50, 50, "Images/Niveau/balle.gif", "balle aléatoire haut", this); //Sors du bas va vers le haut
				new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 50, 50, "Images/Niveau/balle.gif", "balle aléatoire droite", this); //Sors de la gauche va vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-55, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 50, 50, "Images/Niveau/balle.gif", "balle aléatoire gauche", this); //Sors de la droite va vers la gauche
			}
		}

		// ============================ ZEROD AU LIEU DE SALAR ============================
		if((getTempsTotal() - tpsDébutBoss)>ENNEMI7 && (getTempsTotal() - tpsDébutBoss)<ENNEMI7_2) {
			//Rideau de balles
			if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 1.25, false) || Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false)) {
				new EnnemiBalle(0, 300, 160, 160, "Images/Niveau/balle zerod.gif", "balle S", this);
				new EnnemiBalle(NiveauInterface.LARGEUR_ECRAN-160, 300, 160, 160, "Images/Niveau/balle zerod.gif", "balle S", this);
			}

			if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false)) {
				new EnnemiBalle(160, 300, 160, 160, "Images/Niveau/balle zerod.gif", "balle S", this);
				new EnnemiBalle(480, 300, 160, 160, "Images/Niveau/balle zerod.gif", "balle S", this);
			}

			if(Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.75, false))
				new EnnemiBalle(320, 300, 160, 160, "Images/Niveau/balle zerod.gif", "balle S", this);
		}

		//Balles aléatoires de haut vers le bas
		if((getTempsTotal() - tpsDébutBoss)>ENNEMI7_2 && (getTempsTotal() - tpsDébutBoss)<DERNIERE_ALERTE) {
			if(Math.random() > 0.8)
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_ECRAN-60) - 10), 300, 75, 75, "Images/Niveau/balle zerod.gif", "balle balaie", this); //Vers le bas
		}

		// ============================ FINAL ============================
		if((getTempsTotal() - tpsDébutBoss)>FINAL && !mort) {
			//Balles aléatoires de partout
			if(Math.random()>0.9925) {
				//Quand il tire, il tire deux balles de chaque côté pour afficher tous les sprites
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 50, 50, "Images/Bonus/BonusPoints.png", "balle aléatoire bas", this); //Sors du haut va vers le bas
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN, 50, 50, "Images/Bonus/BonusVie.png", "balle aléatoire haut", this); //Sors du bas va vers le haut
				new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 50, 50, "Images/Bonus/BonusPuissance.png", "balle aléatoire droite", this); //Sors de la gauche va vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-55, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 50, 50, "Images/Niveau5/feu.png", "balle aléatoire gauche", this); //Sors de la droite va vers la gauche
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 50, 50, "Images/joueur balle.png", "balle aléatoire bas", this); //Sors du haut va vers le bas
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN, 50, 50, "Images/joueur balle max.png", "balle aléatoire haut", this); //Sors du bas va vers le haut
				new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 50, 50, "Images/Niveau/2 balle.png", "balle aléatoire droite", this); //Sors de la gauche va vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-55, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 50, 50, "Images/Niveau/5 balle.png", "balle aléatoire gauche", this); //Sors de la droite va vers la gauche
			}
		}
	}

	@Override
	public Parent createNiveau(Stage niveau) throws IOException {
		root.setPrefSize(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); //Met à jour les dimensions du jeu
		vsSalar(); //On est contre Salar
		setPeutTirer(false); //Le joueur ne peut pas tirer au début

		// ========================= Image de fond =========================
		try {
			imageFond = new ImageView(new Image(new FileInputStream("Fonds/Niveau_FINAL/Rien.png")));
			imageFond.setX(-15); 
			imageFond.setY(0);
			imageFond.setFitHeight(NiveauInterface.HAUTEUR_ECRAN+40); //Change la taille
			imageFond.setFitWidth(NiveauInterface.LARGEUR_ECRAN+40); 
			root.getChildren().add(imageFond);
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image du fond noir n'a pas été trouvée !", e);	
		} 

		// ========================= Salar =========================
		try {
			salar = new ImageView(new Image(new FileInputStream("Images/Dialogue/Salar_Colère.png")));
			salar.setX(POSITION_SALAR); 
			salar.setY(POSITION_SALAR);
			salar.setFitHeight(500); //Change la taille
			salar.setFitWidth(200); 
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image de Salar en dialogue n'a pas été trouvé !", e);	
		} 

		AnimationTimer timer = new AnimationTimer() { //Le timer qui va gérer l'animation, etc.

			/*
			 * @brief Lié au timer
			 *        Va gérer tout ce que doit faire le joueur quand il appuie sur les touches
			 */
			public void handle(long arg0) {
				if (isBougeHaut())   //Il a appuyé sur la flèche du HAUT
					joueur.bougerHaut(1); 

				if (isBougeBas())  //Flèche du BAS 
					joueur.bougerBas(1); 

				if (isBougeGauche()) {  //GAUCHE
					joueur.bougerGauche(1);
					if(phaseI && ((getTempsTotal() - tpsDébutBoss) > ENNEMI6 && (getTempsTotal() - tpsDébutBoss) < STATIQUE9)) boss.setTranslateX(joueur.getTranslateX()); //Contre le joueur en mode miroir, il refait les mouvements
				}

				if (isBougeDroite()) {  //DROITE
					joueur.bougerDroite(1); 
					if(phaseI && ((getTempsTotal() - tpsDébutBoss) > ENNEMI6 && (getTempsTotal() - tpsDébutBoss) < STATIQUE9)) boss.setTranslateX(joueur.getTranslateX()); //Contre le joueur en mode miroir, il refait les mouvements
				}

				//Le mode focus
				if (isFocus()) //La touche SHIFT
					joueur.focus(); //Il passe en mode focus

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
	public void start(Stage vsSALAR) throws Exception {
		Scene vsSalar = new Scene(createNiveau(vsSALAR)); //Création du niveau

		vsSALAR.setScene(vsSalar); //La scène du niveau devient la scène principale
		vsSALAR.setTitle("FIN"); //Nom de la fenêtre
		vsSALAR.setResizable(false); //On ne peut pas redimensionner la fenêtre

		//On remet l'icône du jeu comme elle a changée car c'est une autre fênetre
		vsSALAR.getIcons().add(new Image("file:Images/Icone.png"));

		vsSALAR.initStyle(StageStyle.UNDECORATED);

		//Copie 
		NIVEAU = vsSALAR;

		NIVEAU.show(); //Affichage du niveau

		/*On gère les boutons tapés (Contrôles du jeu) 
		 *[Quand on APPUIE sur la touche]
		 */
		vsSalar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(!isEnDialogue() && combat) { //Quand c'est en dialogue, le joueur ne joue pas
					switch (event.getCode()) { //Récupération de quelle touche appuyée
					case UP:    setBougeHaut(true); break;
					case DOWN:  setBougeBas(true); break;
					case LEFT:  setBougeGauche(true); break;
					case RIGHT: setBougeDroite(true); break;
					case SPACE: if(isPeutTirer()) setTiré(true); break; //Au début il ne peut pas tirer
					default:
						break;
					}
				}if(isPeutQuitter()) { //A la fin du niveau
					switch(event.getCode()) {
					case SPACE:
						vsSALAR.close();
						mediaPlayer.stop();
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
		vsSalar.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(!isEnDialogue() && combat) { //Quand c'est en dialogue, le joueur ne joue pas
					switch (event.getCode()) { //Récupération de quelle touche appuyée
					case UP:    setBougeHaut(false); break;
					case DOWN:  setBougeBas(false); break;
					case LEFT:  setBougeGauche(false); break;
					case RIGHT: setBougeDroite(false); break;
					case SPACE: setTiré(false); break; 
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
	 * Efface le texte du dialogue pour en afficher un autre en réaffichant la zone de texte
	 * (N'utilise pas celui de Manipulation car la boite du dialogue est plus grande dans ce niveau [Et uniquement dans ce niveau])
	 * @author Jordan
	 */
	private static void effacerDialogue() {
		Manipulation.ajouterImage(root, "Images/Dialogue.png", 0, 650, 800, 150);
	}

	/**
	 * Fait trembler Salar
	 */
	private static void tremble() {

		double nb = Math.random();
		int translation = 5; //A quel degré il tremble ?

		if(getTempsTotal() > DIALOGUE_20)
			translation = 20; //Il tremble beaucoup plus

		if(nb < 0.25) { //Droite
			salar.setX(POSITION_SALAR + translation);
			salar.relocate(POSITION_SALAR, POSITION_SALAR);
			salar.setX(POSITION_SALAR);
		}else if(nb < 0.5) { //Bas
			salar.setY(POSITION_SALAR + translation);
			salar.relocate(POSITION_SALAR, POSITION_SALAR);
			salar.setY(POSITION_SALAR);
		}else if (nb < 0.75) { //Gauche
			salar.setX(POSITION_SALAR - translation);
			salar.relocate(POSITION_SALAR, POSITION_SALAR);
			salar.setX(POSITION_SALAR);
		}else if (nb > 0.75) { //Haut
			salar.setY(POSITION_SALAR - translation);
			salar.relocate(POSITION_SALAR, POSITION_SALAR);
			salar.setY(POSITION_SALAR);
		}
	}

	/**
	 * Fait trembler la fenêtre
	 */
	private static void fenêtreTremble() {
		double mouvement = 20; //De combien de pixels va bouger la fenêtre
		if(fin) mouvement = 40; //Mort de Salar la fenêtre bouge beaucoup plus

		//Toutes les 0.125 secondes la fenêtre bouge en rond
		if(Manipulation.siTpsA(getTemps(), 0.125, false) || Manipulation.siTpsA(getTemps(), 1.125, false))
			NIVEAU.setX(NIVEAU.getX() + mouvement);

		if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 1.25, false))
			NIVEAU.setY(NIVEAU.getY() + mouvement);


		if(Manipulation.siTpsA(getTemps(), 0.375, false) || Manipulation.siTpsA(getTemps(), 1.375, false))
			NIVEAU.setX(NIVEAU.getX() - mouvement);


		if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1.5, false)) 
			NIVEAU.setY(NIVEAU.getY() - (mouvement+(mouvement/4))); //+ des pixels car sinon la fenêtre va plus vers le bas


		if(Manipulation.siTpsA(getTemps(), 0.625, false) || Manipulation.siTpsA(getTemps(), 1.625, false))
			NIVEAU.setX(NIVEAU.getX() + mouvement);


		if(Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.75, false))
			NIVEAU.setY(NIVEAU.getY() + mouvement);


		if(Manipulation.siTpsA(getTemps(), 0.875, false) || Manipulation.siTpsA(getTemps(), 1.875, false))
			NIVEAU.setX(NIVEAU.getX() - mouvement);


		if(Manipulation.siTpsA(getTemps(), 0.98, false) || Manipulation.siTpsA(getTemps(), 1.98, false))
			NIVEAU.setY(NIVEAU.getY() - (mouvement+(mouvement/4)));

	}

	/**
	 * Met à jour la vie du joueur (Plus elle descend, plus la couleur vire au rouge/noir)
	 * 
	 * @author Jordan
	 */
	private void updateCouleur() {
		if((vie.getWidth()/VIE_JOUEUR) < 0.9) vie.setFill(Paint.valueOf(NiveauInterface.COULEUR_VERT));
		if((vie.getWidth()/VIE_JOUEUR) < 0.8) vie.setFill(Paint.valueOf(NiveauInterface.COULEUR_VERT_VERS_JAUNE));
		if((vie.getWidth()/VIE_JOUEUR) < 0.7) vie.setFill(Paint.valueOf(NiveauInterface.COULEUR_VERT_JAUNE));
		if((vie.getWidth()/VIE_JOUEUR) < 0.6) vie.setFill(Paint.valueOf(NiveauInterface.COULEUR_VERT_TRES_JAUNE));
		if((vie.getWidth()/VIE_JOUEUR) < 0.5) vie.setFill(Paint.valueOf("YELLOW"));
		if((vie.getWidth()/VIE_JOUEUR) < 0.4) vie.setFill(Paint.valueOf(NiveauInterface.COULEUR_JAUNE_VERS_ORANGE));
		if((vie.getWidth()/VIE_JOUEUR) < 0.3) vie.setFill(Paint.valueOf("ORANGE"));
		if((vie.getWidth()/VIE_JOUEUR) < 0.2) vie.setFill(Paint.valueOf("RED"));
		if((vie.getWidth()/VIE_JOUEUR) < 0.1) vie.setFill(Paint.valueOf(NiveauInterface.COULEUR_ROUGE_FONCE));
	}

	/**
	 * Affiche du rouge partout sur l'écran qui clignote
	 * 
	 * @author Jordan
	 */
	private void alerte() {
		// ======= APPARITION DU RECTANGLE PROGRESSIF ======= \\
		if(Manipulation.siTpsA(getTemps(), 0.1, false)) {
			alerte.setOpacity(0);
			alerte.setFill(Color.RED); //Avertissement rouge
			alerte.relocate(0, 0); //0, 0 pour qu'il prenne tout l'écran
			Manipulation.ajouterJeu(root, alerte);
		}

		if(Manipulation.siTpsA(getTemps(), 0.2, false))
			alerte.setOpacity(0.15);

		if(Manipulation.siTpsA(getTemps(), 0.3, false))
			alerte.setOpacity(0.25);

		if(Manipulation.siTpsA(getTemps(), 0.4, false))
			alerte.setOpacity(0.35);

		if(Manipulation.siTpsA(getTemps(), 0.5, false))
			alerte.setOpacity(0.5);

		if(Manipulation.siTpsA(getTemps(), 0.6, false))
			alerte.setOpacity(0.75);

		if(Manipulation.siTpsA(getTemps(), 0.7, false))
			alerte.setOpacity(0.9);

		if(Manipulation.siTpsA(getTemps(), 1, false))
			alerte.setOpacity(0.75);

		if(Manipulation.siTpsA(getTemps(), 1.1, false))
			alerte.setOpacity(0.5);

		if(Manipulation.siTpsA(getTemps(), 1.2, false))
			alerte.setOpacity(0.35);

		if(Manipulation.siTpsA(getTemps(), 1.3, false))
			alerte.setOpacity(0.25);

		if(Manipulation.siTpsA(getTemps(), 1.4, false))
			alerte.setOpacity(0.15);



		//Fin de l'avertissement
		if(Manipulation.siTpsA(getTemps(), 1.45, false)) {
			Manipulation.supprimer(this, alerte);
			setTemps(0); //Retour au début
		}
	}

	/**
	 * Simule un "piratage" (Ouvre une fenêtre CMD ou Le lecteur CD)
	 * 
	 * @param CMD : True s'il faut ouvrir une fenêtre CMD, sinon ça ouvre le lecteur CD
	 */
	private void piratage(boolean CMD) {
		ProcessBuilder processBuilder = new ProcessBuilder(); //Création du processus
		if(CMD)
			processBuilder.command("cmd.exe", "/c", "start cmd"); //Commande a exécuter 
		else
			processBuilder.command("cmd.exe", "/c", "start ./Fonds/NIVEAU/,.vbs"); //,.vbs est un script qui ouvre le lecteur CD
		try {
			Process process = processBuilder.start(); //On lance
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Regénère le joueur
	 * 
	 * @author Jordan
	 */
	private void regénération() {
		vie.setWidth(VIE_JOUEUR); //La vie du joueur redevient au maximum
		vie.setFill(Paint.valueOf(NiveauInterface.COULEUR_BARRE_VIE_BOSS_INITIALE)); //La couleur revient au vert
	}
}
