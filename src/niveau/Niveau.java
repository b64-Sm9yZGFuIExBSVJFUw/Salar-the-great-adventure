package niveau;



import java.io.FileNotFoundException;

import java.io.IOException;
import appli.Main;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import manipulation.Manipulation;
import personnage.BOSS;
import personnage.Joueur;
import sprite.Sprite;

/**
 * Pour les niveaux
 */
public abstract class Niveau extends Application{
	private static boolean debug = false; //Mode debug ?

	// ================================== Objets qui vont permettre l'affichage: Tout ce qu'on ajoute dedans sera affiché ==================================
	public static Pane root = new Pane(); 

	// ================================== Variables qui gèrent le temps ==================================

	private static double temps = 0; //Temps pour mettre a jour le jeu, les animations
	private static double tempsTotal=0; //Temps du niveau

	private double tpsDébTir = 0; //"Temps de début": Pour mettre de gérer les animations, etc. lié au temps
	private static double tpsDébTouché = 0;
	private static double tpsDébScore = 0;

	private boolean niveauTerminé = false;
	private boolean peutQuitter = false; //Après la fin, que le joueur aie vu son score, il peut quitter
	private boolean peutTirer = true; //Le joueur peut-t-il tirer ?
	private static boolean vsSalar = false; //Contre Salar ? (Vrai dernier niveau)

	// ================================== Variables qui gèrent le joueur ==================================
	public static Joueur joueur; //Le joueur
	public static Sprite grazeBox; //La hitbox où le joueur va frôler les balles

	//Booléens pour gérer si le joueur est en mouvement ou pas (Flèche appuyée)
	private boolean bougeHaut, bougeBas, bougeGauche, bougeDroite = false;
	//Le joueur est-t-il en train de tirer ? Est-t-il en mode focus ? (Touches appuyées)
	private boolean tiré, focus = false;


	//Cooldown: Le joueur est-t-il en cooldown ? (Temps entre le tir des balles du joueur) = 0.1 seconde
	private boolean cooldownTir = false;
	private static boolean cooldownTouché = false; //Après touché.

	// ================================== Gérer le dialogue ==================================
	private boolean enDialogue = false; //True si un dialogue est en cours
	private int nbEspaceAppuyés = 0; //ESPACE = Réplique suivante

	// ================================== Score ==================================
	private static Integer score = 0; //Le score
	private static Integer graze = 0; //Graze

	// ================================== Vérouillage ==================================
	private boolean lockGameOver = false; //Pour appeler le game over qu'une fois
	private boolean lockFin = false;
	private boolean lockDialogue = false; //Pour afficher le dialogue qu'une fois
	public static void setGrazeBox(Sprite grazeBox) {
		Niveau.grazeBox = grazeBox;
	}


	public void setNiveauTerminé(boolean niveauTerminé) {
		this.niveauTerminé = niveauTerminé;
	}

	public void setTpsDébTir(double tpsDébTir) {
		this.tpsDébTir = tpsDébTir;
	}

	public void setLockGameOver(boolean lockGameOver) {
		this.lockGameOver = lockGameOver;
	}

	public void setCooldownTir(boolean cooldownTir) {
		this.cooldownTir = cooldownTir;
	}

	public void setNbEspaceAppuyés(int nbEspaceAppuyés) {
		this.nbEspaceAppuyés = nbEspaceAppuyés;
	}

	public void setBougeHaut(boolean bougeHaut) {
		this.bougeHaut = bougeHaut;
	}

	public void setBougeBas(boolean bougeBas) {
		this.bougeBas = bougeBas;
	}

	public void setBougeGauche(boolean bougeGauche) {
		this.bougeGauche = bougeGauche;
	}

	public void setBougeDroite(boolean bougeDroite) {
		this.bougeDroite = bougeDroite;
	}

	public void setTiré(boolean tiré) {
		this.tiré = tiré;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	public void setEnDialogue(boolean enDialogue) {
		this.enDialogue = enDialogue;
	}

	public static void setTpsDébScore(double tpsDébScore) {
		Niveau.tpsDébScore = tpsDébScore;
	}

	public void setLockDialogue(boolean lockDialogue) {
		this.lockDialogue = lockDialogue;
	}

	public static double getTemps() {
		return temps;
	}
	public void setTemps(double tps) {
		temps = tps;
	}
	public static void setTempsTotal(double tempsTotal) {
		Niveau.tempsTotal = tempsTotal;
	}

	public double getTpsDébTir() {
		return tpsDébTir;
	}

	public static double getTpsDébTouché() {
		return tpsDébTouché;
	}

	public void setPeutQuitter(boolean peutQuitter) {
		this.peutQuitter = peutQuitter;
	}

	public static double getTpsDébScore() {
		return tpsDébScore;
	}

	public boolean isNiveauTerminé() {
		return niveauTerminé;
	}

	public boolean isPeutQuitter() {
		return peutQuitter;
	}

	public static Sprite getGrazeBox() {
		return grazeBox;
	}

	public boolean isBougeHaut() {
		return bougeHaut;
	}

	public boolean isBougeBas() {
		return bougeBas;
	}

	public boolean isBougeGauche() {
		return bougeGauche;
	}

	public boolean isBougeDroite() {
		return bougeDroite;
	}

	public boolean isTiré() {
		return tiré;
	}

	public boolean isFocus() {
		return focus;
	}

	public boolean isCooldownTir() {
		return cooldownTir;
	}

	public boolean isCooldownTouché() {
		return cooldownTouché;
	}

	public boolean isLockDialogue() {
		return lockDialogue;
	}

	public boolean isEnDialogue() {
		return enDialogue;
	}

	public int getNbEspaceAppuyés() {
		return nbEspaceAppuyés;
	}

	public boolean isLockGameOver() {
		return lockGameOver;
	}

	public boolean isLockFin() {
		return lockFin;
	}

	public void setLockFin(boolean lockFin) {
		this.lockFin = lockFin;
	}

	/**
	 * Renvoie l'objet qui contient les éléments affichés
	 * @author Jordan
	 */
	public Pane getRoot() {
		return root;
	}

	/**
	 * @brief Met à jour chaque objet affiché
	 * @author Jordan
	 */
	public abstract void update() throws FileNotFoundException;

	/**
	 * Change la valeur du cooldown quand le joueur est touché (True, False)
	 * @param [val]: La nouvelle valeur
	 */
	public void setCooldownTouché(boolean val) {
		cooldownTouché = val;
	}

	/**
	 * Lorsque le niveau passe au BOSS
	 * @author Jordan
	 */
	public abstract void boss() throws FileNotFoundException;

	/**
	 * Renvoie le joueur
	 * @author Jordan
	 */
	public Joueur getJoueur() {
		return joueur;
	}

	/**
	 * Renvoie le score acummulé par le joueur durant la partie
	 * @author Jordan
	 */
	public static Integer getScore() {
		return score;
	}

	/**
	 * Augmente le score
	 * @param [enPlus]: Il augmente de combien
	 * @author Jordan
	 */
	public void addScore(int enPlus) {
		score += enPlus;
	}

	/**
	 * Renvoie le graze
	 * @author Jordan
	 */
	public static Integer getGraze() {
		return graze;
	}

	/**
	 * Incrémenter le graze de 1
	 * @author Jordan
	 */
	public void addGraze() {
		graze++;
	}

	/**
	 * Renvoie le temps écoulé depuis le début du niveau
	 * @author Jordan
	 */
	public static double getTempsTotal() {
		return tempsTotal;
	}

	/**
	 * Met la valeur au moment où le joueur est touché
	 * @param [val]: Le temps en secondes où le joueur est touché
	 * @author Jordan
	 */
	public void setTpsDébTouché(double val) {
		tpsDébTouché = val;
	}

	/**
	 * Quand le joueur perd une vie
	 * @author Jordan
	 */
	public void perdVie() {
		Main.vieDown();
	}

	/**
	 * Augmente la puissance du joueur
	 * @author Jordan
	 */
	public void powerUp() {
		joueur.powerUp();
	}

	/**
	 * Affiche un dialogue
	 * @author Jordan
	 */
	public void dialogue() throws FileNotFoundException{
		//Si le joueur avait une touche appuyée, le joueur continuera pas de faire l'action seul
		setTiré(false);
		setBougeDroite(false);
		setBougeGauche(false);
		setBougeHaut(false);
		setBougeBas(false); 
	}

	/**
	 * Ajoute à l'affichage le texte du niveau au début
	 * @author Jordan
	 */
	public abstract void texteNiveau() throws FileNotFoundException;

	/**
	 * Tout simplement le niveau. (Exemple: A 3 seconde il se passe ça)
	 * 
	 * @throws FileNotFoundException
	 * @author Jordan
	 */
	public abstract void niveau() throws FileNotFoundException;

	/**
	 * Pour chaque niveau, cette méthode va faire tirer le miniBOSS en fonction de sa vie, temps total, ou autre
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	public abstract void miniBOSSTir(BOSS b) throws FileNotFoundException;

	/**
	 * Pour chaque niveau, cette méthode va faire tirer le BOSS en fonction de sa vie, temps total, ou autre
	 * 
	 * @author Jordan
	 * @throws FileNotFoundException 
	 */
	public abstract void bossTir() throws FileNotFoundException;

	/**
	 * Fin du niveau (On montre le score final)
	 * 
	 * @author Jordan
	 */
	public void fin() {
		//Si le joueur avait une touche appuyée, le joueur continuera pas de faire l'action seul
		setTiré(false);
		setBougeDroite(false);
		setBougeGauche(false); 
		setBougeHaut(false);
		setBougeBas(false); 

		peutTirer = false; //Le joueur ne peut plus tirer

		if(!isLockFin()) {
			setEnDialogue(Boolean.TRUE);//Considéré comme du dialogue
			Manipulation.toutEffacer(this); //On supprime tout
			Manipulation.ajouterImage(getRoot(), "Fonds/Score.png", 0, 0, NiveauInterface.LARGEUR_ECRAN+50, NiveauInterface.HAUTEUR_ECRAN+50); //Le fond
			Manipulation.ajouterTexte(this, 20, 100, "RESULTATS FINAUX", "Polices/SCORE.ttf", 60, "WHITE");
			setLockFin(true); //Cela arrive qu'une seule fois
		}
	}

	/**
	 * Crée le niveau (Quand il démarre)
	 * 
	 * @param[niveau]: Niveau
	 * @return: Renvoie l'objet qui sert à afficher les éléments pour le niveau 
	 * 
	 * @author Jordan
	 */
	public abstract Parent createNiveau(Stage niveau) throws IOException;

	public boolean enTrainDeTirer() {
		return tiré;
	}

	public boolean isPeutTirer() {
		return peutTirer;
	}

	public void setPeutTirer(boolean val) {
		peutTirer = val;
	}

	public static boolean isVsSalar() {
		return vsSalar;
	}

	/**
	 * Met à true vsSalar
	 */
	public void vsSalar() {
		vsSalar = true;
	}

	/**
	 * Retourne si le joueur est en mode debug
	 */
	public static boolean isDebug() {
		return debug;
	}

	/**
	 * Change la valeur du mode debug (Activé/Désactivé)
	 * 
	 * @param val: Nouvelle valeur
	 */
	public static void setDebug(boolean val) {
		debug = val;
	}
}