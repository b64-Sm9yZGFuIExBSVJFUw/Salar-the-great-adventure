	package niveau;

/**
 * Contient toutes les valeurs importantes pour les niveaux
 * 
 * @author Jordan
 *
 */
public final class NiveauInterface {
	
	private NiveauInterface() {
	
	}
	
	//==================================== DIMENSIONS ====================================\\
	public static final int LARGEUR_DECOR = 600; //Largeur de la "zone de jeu"
	public static final int LARGEUR_ECRAN = 800; //Largeur totale du jeu
	public static final int HAUTEUR_ECRAN = 790; //Hauteur totale du jeu
	public static final int LARGEUR_JOUEUR = 27;
	public static final int HAUTEUR_JOUEUR = 50;
	public static final int LARGEUR_GRAZEBOX = 27;
	public static final int HAUTEUR_GRAZEBOX = 50;
	public static final int TAILLE_BALLE_BOSS = 20;

	//==================================== POSITIONS ====================================\\
	public static final double X_NOM_BOSS = 10;
	public static final double Y_NOM_BOSS = 80;
	public static final double Y_NOM_DIALOGUE = 700;
	public static final double X_TEXTE_DIALOGUE = 30;
	public static final double Y_TEXTE_DIALOGUE = 750;

	//==================================== VITESSES ====================================\\
	public static final double VITESSE_BOSS = 0.5;
	public static final int VITESSE_JOUEUR = 5;
	public static final int VITESSE_JOUEUR_VS_SALAR = 10; //Contre Salar (Vrai dernier niveau, le joueur va + vite)
	public static final int VITESSE_ENNEMI = 5;
	public static final int VITESSE_BONUS = 8;
	public static final int VITESSE_BALLE_JOUEUR = 5;
	public static final int VITESSE_BALLE_ENNEMIE = 10;
	public static final int VITESSE_BALLE_BOSS = 1;
	public static final double VIE_BOSS = 560;

	//==================================== SCORE ====================================\\
	public static final int BONUSPOINTS = 50;
	public static final int NBPOINTS_ENNEMI_TOUCHE = 10; //Nb de points qu'on gagne dès qu'on touche un ennemi
	public static final int NBPOINTS_GRAZE = 20; //Nb de points qu'on gagne dès qu'on frôle une balle
	public static final String PATH_SCORE = "Images/Score.png"; //Image du tableau de score

	//==================================== JOUEUR ====================================\\
	public static final int PUISSANCE_MAX = 4; //Puissance maximale de tir
	public static final double COOLDOWN_TIR = 0.1; //Temps entre chaque tir du joueur (Espace appuyé)
	public static final double COOLDOWN_REGENERATION = 2; //Temps d'invincibilité du joueur après qu'il ai été touché

	public static final String COULEUR_BARRE_VIE_BOSS_INITIALE = "#048302"; //Barre de vie du boss
	public static final double TEMPS_1_FRAME = 0.016; //Le temps d'une frame en JAVAFX
	
	//==================================== FIN DU NIVEAU ====================================\\
	public static final int POSITION_X_STATS = 20; //Position en X des statistiques finales (Score, Vie, Difficulté, etc.)
	public static final double POSITION_Y_STATS_SCORE = 200; //Position en X du score dans les statistiques finales
	public static final double POSITION_Y_STATS_VIES = 300; //Position en X des vies dans les statistiques finales
	public static final double POSITION_Y_STATS_DIFFICULTE = 400;  //Position en X de la difficulté dans les statistiques finales
	public static final double POSITION_Y_STATS_PUISSANCE = 500;  //Position en X de la puissance dans les statistiques finales
	public static final double POSITION_Y_STATS_SCORE_FINAL = 600;  //Position en X du score final dans les statistiques finales
	public static final double POSITION_Y_STATS_RANG = 700;  //Position en X du rang dans les statistiques finales
	public static final int POSITION_Y_STATS_QUITTER = 750;  //Position en X de la phrase qui dit qu'on peut quitter dans les stats finales
	
	//==================================== COULEURS POUR LES BARRES DE VIES ====================================\\
	public static final String COULEUR_VERT = "#059a00";
	public static final String COULEUR_VERT_VERS_JAUNE = "#84c200";
	public static final String COULEUR_VERT_JAUNE = "#c5e101";
	public static final String COULEUR_VERT_TRES_JAUNE = "#d3e900";
	public static final String COULEUR_JAUNE_VERS_ORANGE = "#fde500";
	public static final String COULEUR_ROUGE_FONCE = "#880015";
}
