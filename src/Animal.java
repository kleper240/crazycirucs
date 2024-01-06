
import java.util.Objects;

/**
 * Classe représentant les animaux du jeu
 */
public class Animal {
	
	private String nom;
	
	/**
	 * Constructeur créant l'animal
	 * @param nom : le nom de l'animal (son espèce)
	 */
	public Animal(String nom) {
		this.nom = nom;
	}
	
	//getters et setters
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		return Objects.equals(nom, other.nom);
	}
	
}
