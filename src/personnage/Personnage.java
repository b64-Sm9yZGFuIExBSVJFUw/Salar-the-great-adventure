package personnage;

import niveau.Niveau;
import sprite.Sprite;

/**
 * Superclasse repr�sentant un personnage
 * 
 * @author Jordan LAIRES
 *
 */
public abstract class Personnage extends Sprite{

	/*
	 * @brief Constructeur d'un personnage
	 * @param [x]: Coordonn�es de x
	 * @param [y]: Coordonn�es de y
	 * @param [w]: Largeur de l'entit�
	 * @param [h]: Hauteur de l'entit�
	 * @param [imageURL]: Lien de l'image
	 * @param [t]: Type de sprite
	 * @param [n]: Le niveau o� il doit �tre affich�
	 */
	Personnage(double x, double y, int w, int h, String imageURL, String t, Niveau n){
		super(x, y, w, h, imageURL, t, n); //Constructeur de sprite
	}

	//Tir du personnage qui renvoie une exception si l'image de la balle n'est pas trouv�e
	public abstract void tir(String type);
}
