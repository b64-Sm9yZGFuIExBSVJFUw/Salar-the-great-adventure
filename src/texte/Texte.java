package texte;

import javafx.scene.text.Text;
import niveau.Niveau;

/**
 * Un texte spécial (Nom du niveau par exemple)
 * 
 * @author Jordan LAIRES
 *
 */
public class Texte extends Text{
	private String id; //Pour le différencier des autres affichés
	
	/**
	 * Constructeur d'un texte
	 * @param [texte]: Le texte du texte
	 * @param [idé]: L'ID (Type) du texte pour le retrouver/reconnaître
	 * @author Jordan
	 */
	public Texte(String texte, String idé) {
		super(texte); //Constructeur du type Text de JavaFX
		id = idé;
	}
	
	/**
	 * Renvoie l'id du texte
	 * @author Jordan
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Met à jour la position du texte après changement de position
	 * @param [niv]: Niveau où il est affiché
	 * @param [x]: Nouvelle position en X
	 * @param [y]: Nouvelle position en Y
	 * @author Jordan
	 */
	public void updatePos(Niveau niv, double x, double y) {
		//Changement de la position
		setTranslateX(x);
		setTranslateY(y);
		
		//Réaffichage de l'image
		relocate(x ,y);
	}
}
