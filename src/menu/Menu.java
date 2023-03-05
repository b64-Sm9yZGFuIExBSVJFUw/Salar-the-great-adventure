package menu;

/**
 * @brief Pour les menus
 * 
 * @author Jordan LAIRES
 */
public final class Menu {
	
	private Menu() {
		
	}
	
	//Résolution
	public static final double LARGEUR_ECRAN = 840;
	public static final double HAUTEUR_ECRAN = 470;

	//Bouton retour & options
	public static final double POSITION_X_BOUTON_RETOUR_OPTIONS = Menu.LARGEUR_ECRAN - 120; //Position en X du bouton retour & options
	public static final double POSITION_X_IMAGE_SON = Menu.LARGEUR_ECRAN - 250; //Position en X de l'image du son
	public static final int POSITION_Y_BOUTON_RETOUR_SON_OPTIONS = 40; //Même en Y en retour, son, options

	//Difficultées
	public static final int FACILE = 8;
	public static final int NORMAL = 4;
	public static final int DIFFICILE = 2;
	public static final int FRAGILE_COMME_DU_VERRE = 1;

	//Taille des "carrés" des boutons (100x100)
	public static final int TAILLE_BOUTON = 100; 
	
	//Images
	public static final String PATH_ICONE = "Images/Icone.png"; //Icône du jeu
	public static final String PATH_BACKGROUND_MENU = "Fonds/bg_menu.gif"; //Le fond du menu
	public static final String PATH_CINEMATIQUE_DEBUT = "Fonds/intro.gif"; //Cinématique de début
	public static final String PATH_GENERIQUE = "Fonds/Credits.gif"; //Générique de fin
	public static final String PATH_TITRE = "Images/TITRE.png"; //Le titre du jeu
	public static final String PATH_SALAR = "Images/Menu/Salar.gif"; //Salar en gif
	
	//Positions
	public static final int POSITION_TITRE = 20; //X et Y ont la même valeur
	public static final int POSITION_Y_SALAR = 120; //Position en Y de Salar
	public static final int POSITION_X_FIN = 150; //Position en X du bouton de FIN (Accessible après avoir fini les 6 niveaux)
	public static final int POSITION_Y_FIN = 350; //En Y
	
	//Tailles
	public static final int LARGEUR_TITRE = 554; //Largeur du titre
	public static final int HAUTEUR_TITRE = 140; //Hauteur du titre
	public static final int LARGEUR_SALAR = 425; //Largeur de Salar
	public static final int HAUTEUR_SALAR = 350; //Hauteur de Salar
}
