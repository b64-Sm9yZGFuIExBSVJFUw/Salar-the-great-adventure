package personnage;
import java.io.FileNotFoundException;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import manipulation.Manipulation;
import niveau.Niveau;
import niveau.NiveauInterface;

public class BarreDeVie extends Rectangle{
	/**
	 * Constructeur de la barre de vie
	 * @param x : Coordonnées en x
	 * @param y : Coordonnées en y
	 * @param w : Largeur de la barre
	 * @param h : Hauteur de la barre
	 * @param c : Couleur de la barre
	 * @param t : Type de sprite
	 * @param n : Le niveau où il doit être affiché
	 * @author Jordan
	 */
	public BarreDeVie(double x, double y, double w, int h, String t, Niveau n) throws FileNotFoundException {
		super(w,h); //Constructeur d'un rectangle
		setTranslateX(x); //On met a jour les coordonnées
		setTranslateY(y); //Pareil pour Y
		
		this.setFill(Paint.valueOf(NiveauInterface.COULEUR_BARRE_VIE_BOSS_INITIALE)); //On met la couleur
		Manipulation.ajouterJeu(n.getRoot(), this); //On ajoute au jeu
	}
	
	/**
	 * Met à jour la vie du boss (Plus elle descend, plus la couleur vire au rouge/noir)
	 * @param [b]: Le BOSS qui va voir sa barre de vie augmenter
	 * @author Jordan
	 */
	public void updateCouleur(BOSS b) {
		if(b.getPourcentageVieBoss() < 0.9) setFill(Paint.valueOf(NiveauInterface.COULEUR_VERT));
		if(b.getPourcentageVieBoss() < 0.8) setFill(Paint.valueOf(NiveauInterface.COULEUR_VERT_VERS_JAUNE));
		if(b.getPourcentageVieBoss() < 0.7) setFill(Paint.valueOf(NiveauInterface.COULEUR_VERT_JAUNE));
		if(b.getPourcentageVieBoss() < 0.6) setFill(Paint.valueOf(NiveauInterface.COULEUR_VERT_TRES_JAUNE));
		if(b.getPourcentageVieBoss() < 0.5) setFill(Paint.valueOf("YELLOW"));
		if(b.getPourcentageVieBoss() < 0.4) setFill(Paint.valueOf(NiveauInterface.COULEUR_JAUNE_VERS_ORANGE));
		if(b.getPourcentageVieBoss() < 0.3) setFill(Paint.valueOf("ORANGE"));
		if(b.getPourcentageVieBoss() < 0.2) setFill(Paint.valueOf("RED"));
		if(b.getPourcentageVieBoss() < 0.1) setFill(Paint.valueOf(NiveauInterface.COULEUR_ROUGE_FONCE));
	}
	
	/**
	 * @brief Baisse la largeur de la barre de vie (Quand le boss perd sa vie)
	 * @param [b]: Le BOSS qui perd sa vie
	 * @author Jordan
	 */
	public void descente(BOSS b) {
		setWidth(b.getVieBossActuelle());
	}
}