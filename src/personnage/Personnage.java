package personnage;

import niveau.Niveau;
import sprite.Sprite;

/**
 * Superclasse représentant un personnage
 * 
 * @author Jordan LAIRES
 *
 */
public abstract class Personnage extends Sprite{

	/*
	 * @brief Constructeur d'un personnage
	 * @param [x]: Coordonnées de x
	 * @param [y]: Coordonnées de y
	 * @param [w]: Largeur de l'entité
	 * @param [h]: Hauteur de l'entité
	 * @param [imageURL]: Lien de l'image
	 * @param [t]: Type de sprite
	 * @param [n]: Le niveau où il doit être affiché
	 */
	Personnage(double x, double y, int w, int h, String imageURL, String t, Niveau n){
		super(x, y, w, h, imageURL, t, n); //Constructeur de sprite
	}

	//Tir du personnage qui renvoie une exception si l'image de la balle n'est pas trouvée
	public abstract void tir(String type);
}
