/**
 * Classe de test des methodes de la classe Manche
 */

import org.junit.Test;

public class ZmancheTests {
	
	private String[] arguments;
	private Jeu jeu;
	private Manche manche;
	
	/**
	 * Constructeur
	 */
    @Test
	public void MancheTests() {
		arguments = new String[] {"luffy", "solo", "goku"};
		jeu = new Jeu(arguments);
		manche = new Manche(jeu.getCartes().get(0), jeu.getCartes().get(1), jeu.getJoueurs());
	}
	
    @Test
	public void lanceTests() {
		testTestNom();
		testTestSequence();
	}
	/**
	 * Methode de test de la methode testSequence
	 */
    @Test
	public void testTestSequence() {
		Carte CarteDepart = new Carte();
		Carte CarteArrivee = new Carte();
		//On initialise les cartes d'arrivées et de départ
		CarteDepart.getPodiumBleu().empileAnimal(new Animal("OURS"));
		CarteDepart.getPodiumBleu().empileAnimal(new Animal("LION"));
		CarteDepart.getPodiumBleu().empileAnimal(new Animal("ELEPHANT"));
		CarteArrivee.getPodiumRouge().empileAnimal(new Animal("ELEPHANT"));
		CarteArrivee.getPodiumRouge().empileAnimal(new Animal("LION"));
		CarteArrivee.getPodiumRouge().empileAnimal(new Animal("OURS"));
		
		manche.setCarteArrivee(CarteArrivee);
		manche.setCarteDepart(CarteDepart);
		
		System.out.println(CarteDepart);
		System.out.println(CarteArrivee);
		//Une sequence solution
		assert(manche.testSequence("KIKIKI"));
		
		manche.setCarteArrivee(CarteArrivee);
		manche.setCarteDepart(CarteDepart);
		
		//Une sequence non solution
		assert(manche.testSequence("KILOSO")==false);
		
		manche.setCarteArrivee(CarteArrivee);
		manche.setCarteDepart(CarteDepart);
		
		//Une sequence ayant un nombre de caractères impair
		assert(manche.testSequence("KILOK")==false);
		
		//Une sequence comprenant un ordre inconnu
		assert(manche.testSequence("KIKA")==false);
		
		
	}
	
	/**
	 * Methode de test de la methode testNom
	 */
    @Test
	public void testTestNom() {
		//un joueur existant et pouvant jouer
		assert manche.testNom("luffy");
		
		//un joueur existant ayant déjà joué
		jeu.getJoueurs().get(0).setPeutJouer(false);
		assert manche.testNom(jeu.getJoueurs().get(0).getNom()) == false;
		
		//un joueur n'existant pas
		assert manche.testNom("zoro")==false;
	}
}
