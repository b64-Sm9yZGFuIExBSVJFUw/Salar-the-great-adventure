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
	private double vieBoss; //Vie du boss (de d�part, points de vie)
	private double vieBossActuelle; //Vie actuelle (De d�part - les d�g�ts)
	private BarreDeVie barreVie; 
	
	/**
	 * Constructeur du BOSS qui peut renvoyer une exception si l'image du BOSS n'est pas trouv�e
	 * @param x : Coordonn�es de x
	 * @param y : Coordonn�es de y
	 * @param w : Largeur de l'entit�
	 * @param h : Hauteur de l'entit�
	 * @param vie : Vie du boss
	 * @param t : Type de sprite
	 * @param imageURL : Image de l'entit�
	 * @param n : Le niveau o� il doit �tre affich�
	 */
	public BOSS(double x, double y, int w, int h, String t, double vie, String imageURL, BarreDeVie bdv, Niveau n){
		super(x, y, w, h, imageURL, t, n); //Constructeur de personnage
		vieBoss = vie;
		vieBossActuelle = vieBoss;
		barreVie = bdv;
	}
	
	/**
	 * Lorsque le BOSS est touch� par une balle du joueur
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
		setTranslateX(getTranslateX() - NiveauInterface.VITESSE_BOSS/diviseur); //Il bouge en X � sa vitesse
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
	//Override car la barre de vie ici est aussi supprim�e
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
