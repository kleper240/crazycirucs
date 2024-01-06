/**
 * classe représentant les joueurs
 */
public class Joueur implements Comparable<Joueur> {
  private String nom;
  private int score = 0;
  private boolean peut_jouer = true;

  /**
   * Construceur créant le joueur
   * 
   * @param nom
   *          : nom du joueur
   */
  public Joueur(String nom) {
    this.nom = nom;
  }

  /**
   * augmente le score du joueur de 1
   */
  public void incrementeScore() {
    this.score++;
  }

  // getters et setters

  public boolean getPeutJouer() {
    return peut_jouer;
  }

  public void setPeutJouer(boolean peut_jouer) {
    this.peut_jouer = peut_jouer;
  }

  public String getNom() {
    return nom;
  }

  public int getScore() {
    return score;
  }

  @Override
  public int compareTo(Joueur joueur) {
    if (score > joueur.score)
      return -1;
    else if (score == joueur.score) {
      if (nom.compareTo(joueur.nom) < 1)
        return -1;
      else
        return 1;
    } else
      return 1;
  }

}
