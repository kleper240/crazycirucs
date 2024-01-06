
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Classe représentant les podiums et maniant les déplacements d'animaux
 */
public class Podium {
  private String couleur;
  private List<Animal> pile = new ArrayList<>();

  /**
   * Constructeur créant le podium
   * @param couleur : la couleur du podium
   */
  public Podium(String couleur) {
    this.couleur = couleur;
  }
  
  /**
   * Constructeur de copie
   * @param podiumBleu
   */
  public Podium(Podium podium) {
    this.couleur = podium.couleur;
    pile.addAll(podium.pile);
  }

  /**
   * ajoute un animal au podium
   * @param animal : l'animal à ajouter
   */
  public void empileAnimal(Animal animal) {
    pile.add(animal);
  }
  
  /**
   * retire l'animal au sommet du podium
   * @return l'animal retiré
   */
  public Animal depileAnimal() {
    Animal animal_retire = pile.get(pile.size()-1);
    pile.remove(animal_retire);
    return animal_retire;
  }
  
  /**
   * renvoie un animal à partir d'une position
   * @param position : la position de l'animal (0 pour le premier animal)
   * @return l'animal
   */
  public Animal getAnimal(int position) {
    return pile.get(position);
  }
  
  /**
   * retire un animal du podium à partir d'une position
   * @param position : la position de l'animal à retirer
   */
  public void supprimeAnimal(int position) {
    pile.remove(position);
  }

  /**
   * @return le nombre d'animaux dans le podium
   */
  public int taille() {
    return pile.size();
  }
  
  /**
   * @return true si le podium est vide, false sinon
   */
  public boolean estVide() {
    return pile.isEmpty();
  }
  
  /////////////////////////////////////////////////////////////////////////////
  
  public String getCouleur() {
    return couleur;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(pile);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Podium other = (Podium) obj;
    return Objects.equals(pile, other.pile);
  }
  
  public String toString() {
    String s = couleur + " = [";
    for (Animal animal : pile)
      s += " " + animal.getNom();
    s += "]\n";
    return s;
  }
}
