package personnage;

import manipulation.Manipulation;

import niveau.Niveau;
import niveau.NiveauInterface;

/**
 * Superclasse des boss
 * 
 * @author Jordan LAIRES
 *
 */
public abstract class BOSS extends Personnage {
	private double vieBoss; //Vie du boss (de départ, points de vie)
	private double vieBossActuelle; //Vie actuelle (De départ - les dégâts)
	private BarreDeVie barreVie; 
	
	/**
	 * Constructeur du BOSS qui peut renvoyer une exception si l'image du BOSS n'est pas trouvée
	 * @param x : Coordonnées de x
	 * @param y : Coordonnées de y
	 * @param w : Largeur de l'entité
	 * @param h : Hauteur de l'entité
	 * @param vie : Vie du boss
	 * @param t : Type de sprite
	 * @param imageURL : Image de l'entité
	 * @param n : Le niveau où il doit être affiché
	 */
	public BOSS(double x, double y, int w, int h, String t, double vie, String imageURL, BarreDeVie bdv, Niveau n){
		super(x, y, w, h, imageURL, t, n); //Constructeur de personnage
		vieBoss = vie;
		vieBossActuelle = vieBoss;
		barreVie = bdv;
	}
	
	/**
	 * Lorsque le BOSS est touché par une balle du joueur
	 */
	public void perdreVie(double nb) {
		vieBossActuelle -= nb; //Il perd de la vie
		barreVie.descente(this); //La barre de vie descend
		barreVie.updateCouleur(this); //La couleur change 
		
		
		//Si le boss meurt
		if(vieBossActuelle <= 0)
			meurs(); //On le fait mourir
	}
	
	/**
	 * Setter vie actuelle du boss
	 * @param vie
	 * @author Jordan
	 */
	public void setVieBossActuelle(double vie) {
		vieBossActuelle = vie;
	}
	/**
	 * Renvoie la vie actuelle du boss
	 */
	public double getVieBossActuelle() {
		return vieBossActuelle;
	}
	
	/**
	 * Renvoie la vie actuelle du boss en pourcentage
	 */
	public double getPourcentageVieBoss() {
		return vieBossActuelle/vieBoss;
	}
	
	@Override
	public void bougerGauche(double diviseur) {
		setTranslateX(getTranslateX() - NiveauInterface.VITESSE_BOSS/diviseur); //Il bouge en X à sa vitesse
		updateImagePos();
	}
	
	@Override
	public void bougerDroite(double diviseur) {
		setTranslateX(getTranslateX() + NiveauInterface.VITESSE_BOSS/diviseur);
		updateImagePos();
	}

	@Override
	public void bougerBas(double diviseur) {
		setTranslateY(getTranslateY() + NiveauInterface.VITESSE_BOSS/diviseur);
		updateImagePos();
	}

	@Override
	public void bougerHaut(double diviseur) {
		setTranslateY(getTranslateY() - NiveauInterface.VITESSE_BOSS/diviseur);
		updateImagePos();
		
		if(dehors())
			meurs();
	}
	
	@Override
	//Override car la barre de vie ici est aussi supprimée
	public void meurs() {
		super.meurs();
		Manipulation.supprimer(getNiveau(), barreVie);
	}
	
	@Override
	public abstract void tir(String type);
	
	public BarreDeVie getBarreVie() {
		return barreVie;
	}
}
