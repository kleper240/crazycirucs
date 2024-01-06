/**
 * Classe représentant les cartes dans le jeu,
 * qui représentent les combinaisons initiales et finales au fil des manches
 */
public class Carte {
	//les podiums sur les cartes
	private Podium podiumBleu = new Podium("BLEU");
	private Podium podiumRouge = new Podium("ROUGE");

	/**
	 * Constructeur
	 */
	public Carte() {

	}

	//getters et setters
	
	public Podium getPodiumRouge() {
		return podiumRouge;
	}

	public void setPodiumRouge(Podium podiumRouge) {
		this.podiumRouge = podiumRouge;
	}

	public Podium getPodiumBleu() {
		return podiumBleu;
	}

	public void setPodiumBleu(Podium podiumBleu) {
		this.podiumBleu = podiumBleu;
	}

	public String toString() {
		String s = podiumBleu.toString() + podiumRouge.toString();
		return s;
	}
}
