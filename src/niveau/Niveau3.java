
package niveau;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

import appli.Main;
import bonus.Bonus;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import manipulation.Manipulation;
import personnage.BOSS;
import personnage.Joueur;

public class Niveau3 extends Niveau{

	@Override
	public void fin() {
		// TODO Auto-generated method stub

	}

	@Override
	public Parent createNiveau(Stage niveau) throws IOException { 


		root.setPrefSize(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); //Met à jour les dimensions du jeu
		Manipulation.ajouterImage(root,"Fonds/Niveau_III/test.gif", -15, 0, NiveauInterface.LARGEUR_DECOR, NiveauInterface.HAUTEUR_ECRAN+25); //Image de fond
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

				//Si le joueur tire et que le  temps d'attente après le tir d'une balle est fini
				if (isTiré() && !isCooldownTir()) {
					joueur.tir("joueur bullet"); //Il tire
					setTempsTotal(getTpsDébTir()); //Le temps du début est enregistré pour calculer le cooldown
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
	public void start(Stage NIV3) throws Exception {
		Scene niveau_III = new Scene(createNiveau(NIV3)); //Création du niveau

		NIV3.setScene(niveau_III); //La scène du niveau devient la scène principale
		NIV3.setTitle("NIVEAU III"); //Nom de la fenêtre
		NIV3.setResizable(false); //On ne peut pas redimensionner la fenêtre

		//On remet l'icône du jeu comme elle a changée car c'est une autre fênetre
		NIV3.getIcons().add(new Image("file:Images/Icone.png"));

		Manipulation.updateScore(this); //Met à jour le score pour le début du niveau

		NIV3.show(); //Affichage du niveau

		/*On gère les boutons tapés (Contrôles du jeu) 
		 *[Quand on APPUIE sur la touche]
		 */
		niveau_III.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
						NIV3.close();

						Alert alerte = new Alert(AlertType.INFORMATION); 
						alerte.setTitle("NIVEAU III TERMINE !"); //Le titre de la fênetre
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
		niveau_III.setOnKeyReleased(new EventHandler<KeyEvent>() {
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
		// TODO Auto-generated method stub

	}

	@Override
	public void dialogue() throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void texteNiveau() throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void niveau() throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void miniBOSSTir(BOSS b) throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void bossTir() throws FileNotFoundException {
		// TODO Auto-generated method stub

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

				/*		case "bonus": //Bonus
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
							Manipulation.erreur("ERREUR 0x11", "Le sprite de la balle du miniboss n'a pas été trouvé !", "Veuillez vérifier l'existance de l'image \"Images/ennemi balle.png\" dans les fichiers du jeu.");
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
							Manipulation.erreur("ERREUR 0x12", "Le sprite de la balle du miniboss n'a pas été trouvé !", "Veuillez vérifier l'existance de l'image \"Images/ennemi balle.png\" dans les fichiers du jeu.");
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
						Manipulation.erreur("ERREUR 0x13", "Le sprite de la balle du miniboss n'a pas été trouvé !", "Veuillez vérifier l'existance de l'image \"Images/ennemi balle.png\" dans les fichiers du jeu.");
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
						Manipulation.erreur("ERREUR 0x14", "Le sprite de la balle du miniboss n'a pas été trouvé !", "Veuillez vérifier l'existance de l'image \"Images/ennemi balle.png\" dans les fichiers du jeu.");
					}
				}
				break; 

				//N: Nord, O: ouest...
			case "balle N":
				//Si c'est pas le boss OU le boss mais n'attaque pas en spirale: Les balles sont plus lentes (Car le boss aussi fait cette attaque)
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40)) 
					s.bougerHaut(0.25); 
				else
					s.bougerHaut(0.125);
				break;


			case "balle NO":
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40)) {
					s.bougerHaut(0.25);
					s.bougerDroite(0.25);
				}else {
					s.bougerHaut(0.125);
					s.bougerDroite(0.125);
				}

				break;

			case "balle O":
				if(!vsBOSS || ((getTempsTotal() - tpsDébutBoss)<40))
					s.bougerDroite(0.25);
				else
					s.bougerDroite(0.125);
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

				break;  */

				/*	case "boss":
				if(vsBOSS) {
					//Il flotte
					if((getTemps() > 0 && getTemps() < 0.5) || (getTemps() > 1 && getTemps() < 1.5))
						s.bougerHaut(1);
					else
						s.bougerBas(1);


					//Attaque étoile et Balles aléatoires + spirale
					if(((getTempsTotal() - tpsDébutBoss)>18 && (getTempsTotal() - tpsDébutBoss)<30) || (((getTempsTotal() - tpsDébutBoss)>58 && (getTempsTotal() - tpsDébutBoss)<76))){
						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 120) 
							bossVersDroite = !bossVersDroite; //Même X que le miniboss1

						if(bossVersDroite)
							s.bougerDroite(0.25);
						else
							s.bougerGauche(0.25);

					} else if (Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, 30, false)) { //Place le boss au milieu pour prochaine attaque (Balles rebondissantes)
						s.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);
						s.updateImagePos(); //Mise à jour de la position de l'image
					} else if ((getTempsTotal() - tpsDébutBoss)>30 && (getTempsTotal() - tpsDébutBoss)<43) { //Attaque balles rebondies
						if(s.getTranslateY() <= 50 || s.getTranslateY() >= 250)
							bossVersBas = !bossVersBas;

						if(bossVersBas)
							s.bougerBas(0.5);
						else
							s.bougerHaut(0.5);
					}else if (Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, 58, false)) { //Replace le boss vers le haut pour prochaine attaque (Spirale)
						//On le remet à sa place
						s.setTranslateY(50);
						s.updateImagePos();
					}else if (Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, 76.2, false)) { //Place le boss au milieu pour prochaine attaque (Mur de balles)
						s.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);
						s.updateImagePos(); //Mise à jour de la position de l'image
					}else if (Manipulation.siTpsA(getTempsTotal() - tpsDébutBoss, 100.8, false)) { //Place le boss au milieu pour prochaine attaque (Mur de balles)
						s.setTranslateY(50);
						s.updateImagePos(); //Mise à jour de la position de l'image
					}else if((getTempsTotal() - tpsDébutBoss)>101) { //Attaque tunnel
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
						if((getTempsTotal() - tpsDébutBoss)<101) { //Si c'est pas le tunnel
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

					} catch (FileNotFoundException e2) {
						Manipulation.erreur("ERREUR 0x15", "Le sprite du bonus de points n'a pas été trouvé !", "Veuillez vérifier l'existance de l'image \"Images/Bonus/BonusPoints.png\" dans les fichiers du jeu.");
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
						miniBOSS.perdreVie(DEGAT_BALLE_JOUEUR); //Il perd de la vie !
						Manipulation.scoreUp(this);

						//Mort du miniboss
						if(miniBOSS.getVieBoss() <= 0) {

							if(getTempsTotal() > APPARITION_MINIBOSS2) { //Si miniboss II
								if(miniBOSS2.getVieBoss() <= 0) vsMINIBOSS = false; //Si les deux boss sont morts, vsMINIBOSS = false;
							}else { //Si y'a qu'un boss
								vsMINIBOSS = false;
							}

							if(Math.random() > 0.75)
								try {
									new BonusVie(miniboss.getTranslateX()+10, miniboss.getTranslateY() + 7, 25, 25, this);
								} catch (FileNotFoundException e1) {
									Manipulation.erreur("ERREUR 0x19", "Le sprite du bonus de vie n'a pas pu être trouvé !", "Veuillez vérifier l'existance de l'image \"Images/Bonus/BonusVie.png\" dans les fichiers du jeu.");
								}

						}
					}	
				});

				//Dès qu'on peut attaquer (Finis de descendre)
				if((getTempsTotal() > APPARITION_MINIBOSS2  + TEMPS_MINIBOSS_DESCENTE && getTempsTotal() < APPARITION_DERNIERS_MINIBOSS) || (getTempsTotal() > APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE)) {
					Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("miniboss2")).forEach(miniboss -> {
						if (Manipulation.touche(s, miniboss)){
							s.meurs();
							miniBOSS2.perdreVie(DEGAT_BALLE_JOUEUR); //Il perd de la vie !
							Manipulation.scoreUp(this);

							//Mort du miniboss
							if(miniBOSS2.getVieBoss() <= 0) {
								if(miniBOSS.getVieBoss() <= 0) vsMINIBOSS = false; //Si les deux boss sont morts

								if(Math.random() > 0.75)
									try {
										new BonusVie(miniboss.getTranslateX()+10, miniboss.getTranslateY() + 7, 25, 25, this);
									} catch (FileNotFoundException e1) {
										Manipulation.erreur("ERREUR 0x1C", "Le sprite du bonus de vie n'a pas pu être trouvé !", "Veuillez vérifier l'existance de l'image \"Images/Bonus/BonusVie.png\" dans les fichiers du jeu.");
									}
							}
						}	
					});
				}

				if(getTempsTotal() > APPARITION_DERNIERS_MINIBOSS + TEMPS_MINIBOSS_DESCENTE) {
					Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("miniboss3")).forEach(miniboss -> {
						if (Manipulation.touche(s, miniboss)){
							s.meurs();
							miniBOSS3.perdreVie(DEGAT_BALLE_JOUEUR); //Il perd de la vie !
							Manipulation.scoreUp(this);

							//Mort du miniboss
							if(miniBOSS3.getVieBoss() <= 0) {

								if(Math.random() > 0.75)
									try {
										new BonusVie(miniboss.getTranslateX()+10, miniboss.getTranslateY() + 7, 25, 25, this);
									} catch (FileNotFoundException e1) {
										Manipulation.erreur("ERREUR 0x1F", "Le sprite du bonus de vie n'a pas pu être trouvé !", "Veuillez vérifier l'existance de l'image \"Images/Bonus/BonusVie.png\" dans les fichiers du jeu.");
									}
							}
						}	
					});

					Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("miniboss4")).forEach(miniboss -> {
						if (Manipulation.touche(s, miniboss)){
							s.meurs();
							miniBOSS4.perdreVie(DEGAT_BALLE_JOUEUR); //Il perd de la vie !
							Manipulation.scoreUp(this);

							//Mort du miniboss
							if(miniBOSS4.getVieBoss() <= 0) {

								if(Math.random() > 0.75)
									try {
										new BonusVie(miniboss.getTranslateX()+10, miniboss.getTranslateY() + 7, 25, 25, this);
									} catch (FileNotFoundException e1) {
										Manipulation.erreur("ERREUR 0x22", "Le sprite du bonus de vie n'a pas pu être trouvé !", "Veuillez vérifier l'existance de l'image \"Images/Bonus/BonusVie.png\" dans les fichiers du jeu.");
									}
							}
						}	
					}); */

				//Le boss
				/*			if(vsBOSS) {
						//Si la balle touche le boss
						Manipulation.sprites(getRoot()).stream().filter(e -> e.getType().equals("boss")).forEach(bosss -> {
							if (Manipulation.touche(s, bosss)){
								s.meurs();
								boss.perdreVie(DEGAT_BALLE_JOUEUR); //Il perd de la vie !
								Manipulation.scoreUp(this);

								//Mort du boss
								if(boss.getVieBoss() <= 0) {
									bossBattu = true; //Mort
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
			}*/
			}});

		niveau();

		if(isNiveauTerminé()) fin();
	}
}

