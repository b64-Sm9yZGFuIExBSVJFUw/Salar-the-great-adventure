package texte;

import javafx.scene.text.Text;
import niveau.Niveau;

/**
 * Un texte sp�cial (Nom du niveau par exemple)
 * 
 * @author Jordan LAIRES
 *
 */
public class Texte extends Text{
	private String id; //Pour le diff�rencier des autres affich�s
	
	/**
	 * Constructeur d'un texte
	 * @param [texte]: Le texte du texte
	 * @param [id�]: L'ID (Type) du texte pour le retrouver/reconna�tre
	 * @author Jordan
	 */
	public Texte(String texte, String id�) {
		super(texte); //Constructeur du type Text de JavaFX
		id = id�;
	}
	
	/**
	 * Renvoie l'id du texte
	 * @author Jordan
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Met � jour la position du texte apr�s changement de position
	 * @param [niv]: Niveau o� il est affich�
	 * @param [x]: Nouvelle position en X
	 * @param [y]: Nouvelle position en Y
	 * @author Jordan
	 */
	public void updatePos(Niveau niv, double x, double y) {
		//Changement de la position
		setTranslateX(x);
		setTranslateY(y);
		
		//R�affichage de l'image
		relocate(x ,y);
	}
}
