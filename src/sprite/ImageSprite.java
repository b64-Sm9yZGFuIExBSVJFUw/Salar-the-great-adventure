package sprite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import manipulation.Manipulation;
import niveau.Niveau;

/**
 * L'image d'un sprite/personnage/balle
 * 
 * @author Jordan LAIRES
 *
 */
public class ImageSprite extends ImageView{
	private Niveau n; //Où est affiché le sprite ?
	
	/**
	 * Constructeur d'une image d'un sprite
	 * @param [img]: L'image
	 * @param [n]: Le niveau où il doit être affiché
	 * @author Jordan
	 */
	public ImageSprite(Image img, Niveau n){
		super(img); 
		this.n = n;
	}
	
	/**
	 * Fait mourir l'image
	 * @author Jordan
	 */
	public void meurs() {
		Manipulation.supprimer(n, this);
	}
}