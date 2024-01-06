/**
 * Classe de test des methodes de la classe Podium
 */

import org.junit.Test;

public class ZpodiumTests {
	
	/**
	 * Constructeur
	 */
    @Test
	public void lanceTests() {
		testEmpileDepileAnimal();
	}
	
	/**
	 * Methode de test des methodes empileAnimal et depileAnimal de la classe Podium
	 */
    @Test
	public void testEmpileDepileAnimal() {
		//On initialise le podium
		Podium podium = new Podium("BLEU");
		
		//On teste l'ajout d'un animal
		podium.empileAnimal(new Animal("OURS"));
		assert(podium.taille() == 1);
		assert(podium.getAnimal(0).getNom().equals("OURS"));
		
		//On teste le retrait d'un animal
		assert(podium.depileAnimal().getNom().equals("OURS"));
		assert(podium.estVide());
		
	}
}
