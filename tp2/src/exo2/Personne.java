package exo2;

import java.io.Serializable;

/**
 * @author jf
 *
 */
public class Personne implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int porteMonnaie;
	public Personne() {
		this.porteMonnaie = 0;
	}
	public int getPorteMonnaie() {
		return porteMonnaie;
	}
	public void setPorteMonnaie(int porteMonnaie) {
		this.porteMonnaie = porteMonnaie;
	}
	@Override
	public String toString() {
		return "Personne [porteMonnaie=" + porteMonnaie + "]";
	}
}
