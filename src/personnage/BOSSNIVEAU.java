package personnage;

import java.io.FileNotFoundException;

import niveau.Niveau;

public class BOSSNIVEAU extends BOSS {

	public BOSSNIVEAU(double x, double y, int w, int h, String t, double vie, String imageURL, BarreDeVie BDV,
			Niveau n) throws FileNotFoundException {
		super(x, y, w, h, t, vie, imageURL, BDV, n);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void perdreVie(double nb) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tir(String type) {
		// TODO Auto-generated method stub

	}

}
