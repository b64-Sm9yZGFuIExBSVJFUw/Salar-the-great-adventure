package niveau;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import appli.Difficult�;
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
import personnage.BOSSSECRET;
import personnage.BarreDeVie;
import personnage.Joueur;
import texte.Texte;

/**
 * Le niveau secret contre Jordan
 * 
 * @author Jordan
 * 
 *  */
public class Secret extends Niveau {
	//===================== TIMING DU NIVEAU EN SECONDES ===================\\
	private static final double APPARITION = 0.1; //Le boss appara�t
	private static final double FIN_DESCENTE = 4; //Le boss arr�te de descendre
	private static final double GAUCHE_DROITE = 5; //L'attaque o� il faut faire gauche/droite rapidement
	private static final double FIN_GAUCHE_DROITE = 9.5; //Fin de l'attaque pr�c�dente
	private static final double PARTOUT = 10.5; //Tire partout
	private static final double RALENTI = 13; //Ralenti
	private static final double FIN_RALENTI = 15.5; //Fin ralenti
	private static final double FIN_PARTOUT = 20; //Fin de l'attaque partout
	private static final double TUNNEL = 21; //Attaque tunnel
	private static final double FIN_TUNNEL = 30.5; //Fin attaque tunnel
	private static final double ALEATOIRE = 31; //Balles al�atoires
	private static final double ECLAIRS = 41; //Les �clairs se rajoutent
	private static final double FIN_ALEATOIRE = 48; //Fin des balles al�atoires + �clairs
	private static final double MURS = 50.75; //Murs de balles
	private static final double FIN_MURS = 59; //Fin des murs de balles
	private static final double PARTOUT2 = 60.5; //Il re-tire partout
	private static final double RALENTI2 = 63.25; //Ralenti 2
	private static final double FIN_RALENTI2 = 65.75; //Fin ralenti 2
	private static final double FIN_PARTOUT2 = 70; //Fin de l'attaque partout 2
	private static final double TUNNEL2 = 71; //Attaque tunnel
	private static final double FIN_TUNNEL2 = 80.5; //Fin de l'attaque tunnel
	private static final double ECLAIRS2 = 81; //Que les �clairs
	private static final double PETIT_TUNNEL = 85; //Fin des �clairs, un tunnel appara�t 
	private static final double FIN_PETIT_TUNNEL = 85.75; //Fin du tunnel, reprise des �clairs
	private static final double MURS2 = 90; //Fin des �clairs, mur de balles
	private static final double RALENTI_MURS2 = 92.25; //Ralenti
	private static final double FIN_RALENTI_MURS2 = 93; //Fin du ralenti
	private static final double RALENTI2_MURS2 = 98; //Ralenti 2
	private static final double FIN_RALENTI2_MURS2 = 98.75; //Fin du ralenti 2
	private static final double FIN_MURS2 = 99; //Fin des murs de balles
	private static final double PETIT_TUNNEL2 = 99.25; //Un autre petit tunnel
	private static final double FIN_PETIT_TUNNEL2 = 99.5; //Un autre petit tunnel
	private static final double GAUCHE_DROITE2 = 100; //L'attaque o� il faut faire gauche/droite rapidement
	private static final double FIN_GAUCHE_DROITE2 = 110; //Fin de l'attaque pr�c�dente
	private static final double TUNNEL3 = 111.5; //Tunnel 3
	private static final double RAPIDE = 123.5; //Acc�l�ration du tunnel
	private static final double LENT = 132; //D�c�l�ration du tunnel
	private static final double ACCELERATION = 137.75; //Petite acc�l�ration
	private static final double FIN_ACCELERATION = 139; //Fin de l'acc�l�ration
	private static final double FIN = 143.5; //Fin, mort du boss
	private static final double RESULTAT = 145; //Affichage des r�sultats

	private static Media son = new Media(new File("Sons/secret/secret.mp3").toURI().toString());
	private static MediaPlayer mediaPlayer = new MediaPlayer(son);

	private static ImageView imageFond = new ImageView();

	//Il faut combien de points pour avoir le rang ?
	private static final int RANG_SALAR = 2692000;
	private static final int RANG_S = 2000000;
	private static final int RANG_A = 1500000;
	private static final int RANG_B = 1000000;

	// ================================== Variables qui g�rent le boss ==================================
	private static BOSSSECRET boss = null;	//Le boss
	private boolean bossVersDroite = false; //Le boss va-t'il � droite ? (Pour le mouvement)
	private static final double  DEGAT_JOUEUR = 0.055; //Il faut survivre, ce que le boss perd toutes les 0.016 secondes
	private String nomBOSS = "Jordan";

	// ================================== Variables qui g�rent l'attaque des �clairs ==================================
	private int tiers_�cran = 200; //Le tiers d'un �cran en largeur [L'�cran du jeu fait 600px]
	private Rectangle avertissement = new Rectangle(tiers_�cran,NiveauInterface.HAUTEUR_ECRAN+10); //La zone rouge qui averti l'emplacement de l'�clair
	private int endroit; //L'endroit al�atoire o� va appara�tre l'�clair

	@Override
	public void update() throws FileNotFoundException {
		//Le temps d'une frame en JavaFX (On le met ici pour pouvoir manipuler les objets selon le temps)
		setTemps(getTemps() + NiveauInterface.TEMPS_1_FRAME);
		setTempsTotal(getTempsTotal() + NiveauInterface.TEMPS_1_FRAME);

		//Pour mieux calculer le temps
		if(getTemps() > 2)
			setTemps( 0);

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
							if(!isCooldownTouch�()) Manipulation.grazeUp(this);
						}
					});
				}


				break;

			case "boss":
				//Il faut survivre 
				boss.perdreVie(DEGAT_JOUEUR);

				//Il flotte
				if((getTemps()> 0 && getTemps() < 0.5) || (getTemps() > 1 && getTemps() < 1.5))
					s.bougerHaut(2.5);
				else
					s.bougerBas(2.5);

				//Le combat 
				if(getTempsTotal() > GAUCHE_DROITE && getTempsTotal() < FIN) {
					if((getTempsTotal() < TUNNEL || getTempsTotal() > FIN_TUNNEL) && (getTempsTotal() < TUNNEL2 || getTempsTotal() > FIN_TUNNEL2) && (getTempsTotal() < PETIT_TUNNEL || 
							getTempsTotal() > FIN_PETIT_TUNNEL) && (getTempsTotal() < PETIT_TUNNEL2 || getTempsTotal() > FIN_PETIT_TUNNEL2) && (getTempsTotal() < TUNNEL3)) { //Pas tunnel
						try {
							bossTir();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else { //Tunnel
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

					//Le boss bouge
					if((getTempsTotal() > PARTOUT && getTempsTotal() < ALEATOIRE) || (getTempsTotal() > PARTOUT2 && getTempsTotal() < ECLAIRS2) || (getTempsTotal() > PETIT_TUNNEL && getTempsTotal() < FIN_PETIT_TUNNEL) ||
							(getTempsTotal() > PETIT_TUNNEL2 && getTempsTotal() < FIN_PETIT_TUNNEL2) || (getTempsTotal() > TUNNEL3)) {
						if(s.getTranslateX() <= 0 || s.getTranslateX() >= NiveauInterface.LARGEUR_DECOR - 150)
							bossVersDroite = !bossVersDroite;

						if(bossVersDroite) {
							if((getTempsTotal() > RALENTI && getTempsTotal() < FIN_RALENTI) || (getTempsTotal() > TUNNEL && getTempsTotal() < FIN_TUNNEL) || (getTempsTotal() > RALENTI2 && getTempsTotal() < FIN_RALENTI2) ||
									(getTempsTotal() > TUNNEL2 && getTempsTotal() < FIN_TUNNEL2) || (getTempsTotal() > PETIT_TUNNEL && getTempsTotal() < FIN_PETIT_TUNNEL) || (getTempsTotal() > PETIT_TUNNEL2 && getTempsTotal() < FIN_PETIT_TUNNEL2)||
									(getTempsTotal() > TUNNEL3)) //En ralenti & Tunnel
								s.bougerDroite(0.1);
							else if(getTempsTotal() > LENT)
								s.bougerDroite(0.2);
							else
								s.bougerDroite(0.05);
						}else {
							if((getTempsTotal() > RALENTI && getTempsTotal() < FIN_RALENTI) || (getTempsTotal() > TUNNEL && getTempsTotal() < FIN_TUNNEL) || (getTempsTotal() > RALENTI2 && getTempsTotal() < FIN_RALENTI2) ||
									(getTempsTotal() > TUNNEL2 && getTempsTotal() < FIN_TUNNEL2) || (getTempsTotal() > PETIT_TUNNEL && getTempsTotal() < FIN_PETIT_TUNNEL) || (getTempsTotal() > PETIT_TUNNEL2 && getTempsTotal() < FIN_PETIT_TUNNEL2) ||
									(getTempsTotal() > TUNNEL3)) //En ralenti & Tunnel
								s.bougerGauche(0.1);
							else if(getTempsTotal() > LENT)
								s.bougerGauche(0.2);
							else
								s.bougerGauche(0.05);
						}
					}
				}
				break;

				//Balles o� il faut bouger "gauche/droite" rapide
			case "balle gauche droite":
				s.bougerBas(0.05);
				break;

				//Les balles quand le boss tire de partout
			case "balle partout gg":
				if((getTempsTotal() > RALENTI && getTempsTotal() < FIN_RALENTI) || (getTempsTotal() > RALENTI2 && getTempsTotal() < FIN_RALENTI2)) { //En ralenti
					s.bougerBas(0.2);
					s.bougerGauche(0.2);
				}else {
					s.bougerBas(0.1);
					s.bougerGauche(0.1);
				}
				break;

			case "balle partout g":
				if((getTempsTotal() > RALENTI && getTempsTotal() < FIN_RALENTI) || (getTempsTotal() > RALENTI2 && getTempsTotal() < FIN_RALENTI2)) { //En ralenti
					s.bougerBas(0.2);
					s.bougerGauche(0.4);
				}else {
					s.bougerBas(0.1);
					s.bougerGauche(0.2);
				}
				break;

			case "balle partout d":
				if((getTempsTotal() > RALENTI && getTempsTotal() < FIN_RALENTI) || (getTempsTotal() > RALENTI2 && getTempsTotal() < FIN_RALENTI2)) { //En ralenti
					s.bougerBas(0.2);
					s.bougerDroite(0.4);
				}else {
					s.bougerBas(0.1);
					s.bougerDroite(0.2);
				}
				break;

			case "balle partout dd":
				if((getTempsTotal() > RALENTI && getTempsTotal() < FIN_RALENTI) || (getTempsTotal() > RALENTI2 && getTempsTotal() < FIN_RALENTI2)) { //En ralenti
					s.bougerBas(0.2);
					s.bougerDroite(0.2);
				}else {
					s.bougerBas(0.1);
					s.bougerDroite(0.1);
				}
				break;

				//Balle allant au sud
			case "balle S":
				if((getTempsTotal() > RALENTI && getTempsTotal() < FIN_RALENTI) || (getTempsTotal() > RALENTI2 && getTempsTotal() < FIN_RALENTI2)) //En ralenti
					s.bougerBas(0.2);
				else 
					s.bougerBas(0.1);
				break;

				//Balle du tunnel
			case "balle tunnel":
				if(getTempsTotal() < LENT)
					s.bougerBas(0.075); //Tunnel pas lent
				else if(getTempsTotal() > RAPIDE && getTempsTotal() < LENT)
					s.bougerBas(0.05); //Tunnel rapide
				else if(getTempsTotal() > ACCELERATION && getTempsTotal() < FIN_ACCELERATION) 
					s.bougerBas(0.1); //Acc�l�ration (En m�me temps que la lenteur
				else
					s.bougerBas(0.125); //Tunnel lent
				break;

				//Balle al�atoire vers le bas
			case "balle al�atoire bas":
				s.bougerBas(3);
				break;

				//Balle al�atoire vers la gauche
			case "balle al�atoire gauche":
				s.bougerGauche(3);
				break;

				//Balle al�atoire vers la droite
			case "balle al�atoire droite":
				s.bougerDroite(3);
				break;

				//Balle al�atoire vers le haut
			case "balle al�atoire haut":
				s.bougerHaut(3);
				break;

				//�clair
			case "balle �clair":
				s.bougerBas(0.01);
				break;

				//Mur de balles
			case "mur balle":
				if((getTempsTotal() > RALENTI_MURS2 && getTempsTotal() < FIN_RALENTI_MURS2) || (getTempsTotal() > RALENTI2_MURS2 && getTempsTotal() < FIN_RALENTI2_MURS2)) //En ralenti
					s.bougerBas(0.2);
				else 
					s.bougerBas(0.1);
				break;
			}
		});

		niveau();
	}

	@Override
	public void boss() throws FileNotFoundException {
		//Cr�ation du boss
		boss = new BOSSSECRET((NiveauInterface.LARGEUR_DECOR/2)-70, -150, 90, 150, "boss", NiveauInterface.VIE_BOSS, "Images/Secret/boss.gif", new BarreDeVie(10,40,NiveauInterface.VIE_BOSS,10, "vie", this), this);

		//Le nom du boss
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
	public void texteNiveau() throws FileNotFoundException {} //On n'affiche pas le nom du niveau

	@Override
	public void niveau() throws FileNotFoundException {
		//Cr�ation du boss
		if(Manipulation.siTpsA(getTempsTotal(), APPARITION, true))
			boss();

		//Le boss descend
		if(getTempsTotal() > APPARITION && getTempsTotal() < FIN_DESCENTE)
			boss.bougerBas(0.6);

		//Changement de fond
		if(Manipulation.siTpsA(getTempsTotal(), PARTOUT, true) || Manipulation.siTpsA(getTempsTotal(), PARTOUT2, true))
			imageFond.setImage(new Image(new FileInputStream("Fonds/SECRET/2.gif")));

		//Changement de fond & sprite
		if(Manipulation.siTpsA(getTempsTotal(), TUNNEL, false) || Manipulation.siTpsA(getTempsTotal(), TUNNEL2, false) || Manipulation.siTpsA(getTempsTotal(), TUNNEL3, false)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/SECRET/3.gif")));
			Manipulation.supprimer(this, boss.getImg());
			boss.setImg("Images/Secret/boss3.gif");
		}

		//On replace le boss au milieu et on change le fond
		if(Manipulation.siTpsA(getTempsTotal(), ALEATOIRE, true)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/SECRET/4.gif")));
			boss.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);
			boss.updateImagePos();
		}

		//On met le temps � 0 pour les �clairs et on change le fond
		if(Manipulation.siTpsA(getTempsTotal(), ECLAIRS, true)) {
			imageFond.setImage(new Image(new FileInputStream("Fonds/SECRET/5.gif")));
			setTemps(0);
		}

		//On enl�ve l'avertissement des �clairs du jeu apr�s la fin des �clairs
		if(Manipulation.siTpsA(getTempsTotal(), FIN_ALEATOIRE, false) || Manipulation.siTpsA(getTempsTotal(), PETIT_TUNNEL, false) ||
				Manipulation.siTpsA(getTempsTotal(), MURS2, false))
			Manipulation.supprimer(this, avertissement);

		//Changement de fond
		if(Manipulation.siTpsA(getTempsTotal(), MURS, true) || Manipulation.siTpsA(getTempsTotal(), MURS2, true))
			imageFond.setImage(new Image(new FileInputStream("Fonds/SECRET/6.gif")));

		//On replace le boss au milieu et on change le fond et on met le temps � 0 pour les �clairs
		if(Manipulation.siTpsA(getTempsTotal(), ECLAIRS2, true)) {
			setTemps(0);
			imageFond.setImage(new Image(new FileInputStream("Fonds/SECRET/7.gif")));
			boss.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);
			boss.updateImagePos();
		}

		//On replace le boss au milieu et on met le temps � 0 pour le retour des �clairs
		if(Manipulation.siTpsA(getTempsTotal(), FIN_PETIT_TUNNEL, true)) {
			boss.setTranslateX((NiveauInterface.LARGEUR_DECOR)/2-75);
			boss.updateImagePos();
			setTemps(0);
		}

		//Changement de fond
		if(Manipulation.siTpsA(getTempsTotal(), GAUCHE_DROITE2, true)) 
			imageFond.setImage(new Image(new FileInputStream("Fonds/SECRET/1.gif")));	

		//La mort du boss, fin de la survie
		if(Manipulation.siTpsA(getTempsTotal(), FIN, false)) {
			boss.meurs(); //Le boss meurt

			//Suppression du nom du boss
			Manipulation.textes(getRoot()).stream().filter(e -> e.getID().equals("nomboss")).forEach(texte -> {
				Manipulation.supprimer(this, texte);
			});
		}

		//Fin du niveau
		if(Manipulation.siTpsA(getTempsTotal(), RESULTAT, false)) {
			//Toutes les balles ennemies meurent
			Manipulation.sprites(root).stream().filter(e -> e.getType().contains("balle")).forEach(balle -> {
				balle.meurs();
			});

			fin();
		}
	}

	@Override
	public void miniBOSSTir(BOSS b) throws FileNotFoundException {} //Pas de miniboss

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

	@Override
	public void bossTir() throws FileNotFoundException {
		//===================== GAUCHE / DROITE =====================\\
		if((getTempsTotal() > GAUCHE_DROITE && getTempsTotal() < FIN_GAUCHE_DROITE) || (getTempsTotal() > GAUCHE_DROITE2 && getTempsTotal() < FIN_GAUCHE_DROITE2)){
			int ecartBalles = 100;

			if(Manipulation.siTpsA(getTemps(), 0.25, false) || Manipulation.siTpsA(getTemps(), 0.75, false) || Manipulation.siTpsA(getTemps(), 1.25, false) || Manipulation.siTpsA(getTemps(), 1.75, false)){
				//Cr�ation de la ligne de balles
				for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
					new BossBalle(i, -10, "Images/Secret/balle.gif", "balle gauche droite", this); //X al�atoire
				}
			}

			if(Manipulation.siTpsA(getTemps(), 0.5, false) || Manipulation.siTpsA(getTemps(), 1, false) || Manipulation.siTpsA(getTemps(), 1.5, false) || Manipulation.siTpsA(getTemps(), 1.98, false)){
				//Cr�ation de la ligne de balles 
				for(int i = 50; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
					new BossBalle(i, -10, "Images/Secret/balle.gif", "balle gauche droite", this); //X al�atoire
				}
			}
		}

		//===================== PARTOUT =====================\\
		if((getTempsTotal() > PARTOUT && getTempsTotal() < FIN_PARTOUT) || (getTempsTotal() > PARTOUT2 && getTempsTotal() < FIN_PARTOUT2)) {
			if((getTempsTotal() > RALENTI && getTempsTotal() < FIN_RALENTI) || (getTempsTotal() > RALENTI2 && getTempsTotal() < FIN_RALENTI2)){
				if(Manipulation.quatreFoisParSecondes(getTemps())) { //En ralenti
					new BossBalle(boss.getTranslateX(), boss.getTranslateY()+150, "Images/Secret/balle.gif", "balle partout gg", this); 
					new BossBalle(boss.getTranslateX()+25, boss.getTranslateY()+175, "Images/Secret/balle.gif", "balle partout g", this); 
					new BossBalle(boss.getTranslateX()+50, boss.getTranslateY()+200, "Images/Secret/balle.gif", "balle S", this); 
					new BossBalle(boss.getTranslateX()+75, boss.getTranslateY()+175, "Images/Secret/balle.gif", "balle partout d", this); 
					new BossBalle(boss.getTranslateX()+100, boss.getTranslateY()+150, "Images/Secret/balle.gif", "balle partout dd", this); 
				}
			}else{
				if(Manipulation.huitFoisParSecondes(getTemps())) {
					new BossBalle(boss.getTranslateX(), boss.getTranslateY()+150, "Images/Secret/balle.gif", "balle partout gg", this); 
					new BossBalle(boss.getTranslateX()+25, boss.getTranslateY()+175, "Images/Secret/balle.gif", "balle partout g", this); 
					new BossBalle(boss.getTranslateX()+50, boss.getTranslateY()+200, "Images/Secret/balle.gif", "balle S", this); 
					new BossBalle(boss.getTranslateX()+75, boss.getTranslateY()+175, "Images/Secret/balle.gif", "balle partout d", this); 
					new BossBalle(boss.getTranslateX()+100, boss.getTranslateY()+150, "Images/Secret/balle.gif", "balle partout dd", this); 
				}
			}
		}


		//===================== TUNNEL =====================\\
		if((getTempsTotal() > TUNNEL && getTempsTotal() < FIN_TUNNEL) || (getTempsTotal() > TUNNEL2 && getTempsTotal() < FIN_TUNNEL2) || (getTempsTotal() > PETIT_TUNNEL && getTempsTotal() < FIN_PETIT_TUNNEL) ||
				(getTempsTotal() > PETIT_TUNNEL2 && getTempsTotal() < FIN_PETIT_TUNNEL2) || (getTempsTotal() > TUNNEL3)) {
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
			new BossBalle(toutAGauche, boss.getTranslateY()+120, "Images/Secret/balle.png", "balle tunnel", this);

			if(boss.getTranslateX() <= limiteBalle) //Si le boss pas trop proche du tableau
				new BossBalle(toutADroite, boss.getTranslateY()+120, "Images/Secret/balle.png", "balle tunnel", this);

			//Cr�ation d'un mur de balles du milieu � la toute gauche [1er tunnel gauche]
			for(int i=0;i<toutAGauche;i+=ecartMur) {
				new BossBalle(i, boss.getTranslateY()+120, "Images/Secret/balle.png", "balle tunnel", this);
			}

			//De m�me pour le mur de balles du milieu � la toute droite [2�me tunnel gauche] si le boss n'est pas proche du tableau
			if(boss.getTranslateX() <= limiteBalle) {
				for(int i=NiveauInterface.LARGEUR_DECOR-35; i>toutADroite;i-=ecartMur) {
					new BossBalle(i, boss.getTranslateY()+120,  "Images/Secret/balle.png", "balle tunnel", this);
				}
			}
		}

		//===================== BALLES ALEATOIRES DE PARTOUT =====================\\
		if(getTempsTotal() > ALEATOIRE && getTempsTotal() < FIN_ALEATOIRE) {
			if(Math.random()>0.98) {
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), -10, 20, 20, "Images/Secret/balle.gif", "balle al�atoire bas", this); //Sors du haut va vers le bas
				new EnnemiBalle(Math.random() * ((NiveauInterface.LARGEUR_DECOR-30) - 10), NiveauInterface.HAUTEUR_ECRAN, 20, 20, "Images/Secret/balle.gif", "balle al�atoire haut", this); //Sors du bas va vers le haut
				new EnnemiBalle(0, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/Secret/balle.gif", "balle al�atoire droite", this); //Sors de la gauche va vers la droite
				new EnnemiBalle(NiveauInterface.LARGEUR_DECOR-55, Math.random() * NiveauInterface.HAUTEUR_ECRAN - 10, 20, 20, "Images/Secret/balle.gif", "balle al�atoire gauche", this); //Sors de la droite va vers la gauche
			}

			if(getTempsTotal() > ECLAIRS) {
				�clairs();
			}
		}

		//===================== MURS DE BALLES =====================\\
		if((getTempsTotal() > MURS && getTempsTotal() < FIN_MURS) || (getTempsTotal() > MURS2 && getTempsTotal() < FIN_MURS2)) {
			if((getTempsTotal() > RALENTI_MURS2 && getTempsTotal() < FIN_RALENTI_MURS2) || (getTempsTotal() > RALENTI2_MURS2 && getTempsTotal() < FIN_RALENTI2_MURS2)){ //En ralenti
				if(Manipulation.siTpsA(getTemps(), 0.98, false)){ //Une fois par seconde
					//Pas le m�me �cart sinon �a sera toujours le m�me mur avec les m�mes trous 
					int ecartBalles = (int) (Math.random() * 70)+70; //+70 �vite le ecartBalles = 0

					//Cr�ation de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, "Images/Secret/balle.gif", "mur balle", this); 
						new BossBalle(i, -40, "Images/Secret/balle.gif", "mur balle", this);
					}
				}
			}else {
				if(Manipulation.deuxFoisParSecondes(getTemps())){ //Deux fois par seconde
					//Pas le m�me �cart sinon �a sera toujours le m�me mur avec les m�mes trous 
					int ecartBalles = (int) (Math.random() * 70)+70; //+70 �vite le ecartBalles = 0

					//Cr�ation de la ligne de balles
					for(int i = 0; i < NiveauInterface.LARGEUR_DECOR - 35; i+=ecartBalles) {
						new BossBalle(i, -10, "Images/Secret/balle.gif", "mur balle", this); 
						new BossBalle(i, -40, "Images/Secret/balle.gif", "mur balle", this);
					}
				}
			}

		}

		//===================== ECLAIRS =====================\\
		if((getTempsTotal() > ECLAIRS2 && getTempsTotal() < PETIT_TUNNEL) || (getTempsTotal() > FIN_PETIT_TUNNEL && getTempsTotal() < MURS2)) {
			�clairs();
		}

	}

	@Override
	public Parent createNiveau(Stage niveau) throws IOException {
		if (Main.son()) mediaPlayer.play();

		getRoot().setPrefSize(NiveauInterface.LARGEUR_ECRAN, NiveauInterface.HAUTEUR_ECRAN); //Met � jour les dimensions du jeu

		// ========================= Image de fond =========================
		try {
			imageFond = new ImageView(new Image(new FileInputStream("Fonds/SECRET/1.gif")));
			imageFond.setX(-15); 
			imageFond.setY(0);
			imageFond.setFitHeight(NiveauInterface.HAUTEUR_ECRAN+40); //Change la taille
			imageFond.setFitWidth(NiveauInterface.LARGEUR_DECOR); 
			root.getChildren().add(imageFond);
		} catch (FileNotFoundException e) {
			Manipulation.erreur("L'image de fond du niveau n'a pas �t� trouv�e !", e);	
		} 

		Manipulation.ajouterImage(getRoot(),"Images/Score.png", NiveauInterface.LARGEUR_DECOR-15, 0, (NiveauInterface.LARGEUR_ECRAN - NiveauInterface.LARGEUR_DECOR)+25,  NiveauInterface.HAUTEUR_ECRAN+10); //Tableau de scores

		//Le joueur et sa grazebox
		Niveau.grazeBox =  new Joueur(295,695,NiveauInterface.LARGEUR_JOUEUR+10,NiveauInterface.HAUTEUR_JOUEUR+10, "grazebox", "Images/Transparent.png", this);
		joueur = new Joueur(300,700,NiveauInterface.LARGEUR_JOUEUR, NiveauInterface.HAUTEUR_JOUEUR , "joueur", "Images/Joueur/joueur.png", this);

		AnimationTimer timer = new AnimationTimer() { //Le timer qui va g�rer l'animation, etc.

			/*
			 * @brief Li� au timer
			 *        Va g�rer tout ce que doit faire le joueur quand il appuie sur les touches
			 */
			public void handle(long arg0) {
				if (isBougeHaut()) { //Il a appuy� sur la fl�che du HAUT
					joueur.bougerHaut(1); 
					getGrazeBox().bougerHaut(1); //Sa hitbox va le suivre de partout
				}
				if (isBougeBas()) { //Fl�che du BAS 
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

				//Joueur a perdu
				if(Main.getVies() == 0) {
					if(!isLockGameOver()) { //L'action doit se faire 1 fois
						setLockGameOver(true);
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
		return getRoot();
	}

	@Override
	public void start(Stage SECRET) throws Exception {
		Scene secret = new Scene(createNiveau(SECRET)); //Cr�ation du niveau

		SECRET.setScene(secret); //La sc�ne du niveau devient la sc�ne principale
		SECRET.setTitle("? ? ?"); //Nom de la fen�tre
		SECRET.setResizable(false); //On ne peut pas redimensionner la fen�tre

		//On remet l'ic�ne du jeu comme elle a chang�e car c'est une autre f�netre
		SECRET.getIcons().add(new Image("file:Images/Icone.png"));

		Manipulation.updateScore(this); //Met � jour le score pour le d�but du niveau

		SECRET.show(); //Affichage du niveau

		/*On g�re les boutons tap�s (Contr�les du jeu) 
		 *[Quand on APPUIE sur la touche]
		 */
		secret.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				switch (event.getCode()) { //R�cup�ration de quelle touche appuy�e
				case UP:    setBougeHaut(true); break;
				case DOWN:  setBougeBas(true); break;
				case LEFT:  setBougeGauche(true); break;
				case RIGHT: setBougeDroite(true); break;
				case SHIFT: setFocus(true); break;
				default:
					break;
				}
				if(isPeutQuitter()) { //A la fin du niveau
					switch(event.getCode()) {
					case SPACE:
						SECRET.close();
						mediaPlayer.stop();
						Alert alerte = new Alert(AlertType.INFORMATION); 
						alerte.setTitle("JORDAN"); //Le titre de la f�netre
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
		secret.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				switch (event.getCode()) { //R�cup�ration de quelle touche appuy�e
				case UP:    setBougeHaut(false); break;
				case DOWN:  setBougeBas(false); break;
				case LEFT:  setBougeGauche(false); break;
				case RIGHT: setBougeDroite(false); break;
				case SHIFT:
					setFocus(false); 
					Joueur g = (Joueur)grazeBox;
					joueur.finFocus();
					g.finFocus();
					break;
				default:
					break;
				}

			}	
		});
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
}
