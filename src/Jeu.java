import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe principale du jeu, gérant les animaux, les cartes, les manches et les scores
 */
public class Jeu {

  // les différentes listes représentant chaque entité du jeu
  private List<Joueur> joueurs = new ArrayList<>();
  private List<Carte> cartes = new ArrayList<>();
  private List<Animal> animaux = new ArrayList<>();

  // liste temporaire servant à créer la liste des cartes
  private List<Animal> liste_finale = new ArrayList<>();

  /**
   * Constructeur
   * 
   * @param joueurs
   *          : la liste des joueurs
   */
  public Jeu(String[] joueurs) {

    if (joueurs.length < 2) {
      System.out.println("Il faut au moins 2 joueurs!");
      return;
    }

    // initialise les joueurs
    for (int i = 0; i < joueurs.length; i++) {
      this.joueurs.add(new Joueur(joueurs[i]));
    }

    // initialise les animaux
    animaux.add(new Animal("OURS"));
    animaux.add(new Animal("LION"));
    animaux.add(new Animal("ELEPHANT"));

    // initialise les cartes
    List<Animal> animaux_restants = new ArrayList<>(animaux);
    if (true) { // TBD
      Carte carteDepart = new Carte();
      carteDepart.getPodiumBleu().empileAnimal(new Animal("LION"));
      carteDepart.getPodiumBleu().empileAnimal(new Animal("OURS"));
      carteDepart.getPodiumBleu().empileAnimal(new Animal("ELEPHANT"));

      Carte carteArrivee = new Carte();
      carteArrivee.getPodiumRouge().empileAnimal(new Animal("OURS"));
      carteArrivee.getPodiumRouge().empileAnimal(new Animal("LION"));
      carteArrivee.getPodiumRouge().empileAnimal(new Animal("ELEPHANT"));

      cartes.add(carteDepart);
      cartes.add(carteArrivee);

    } 
    else {
      creerCombinaisons(animaux_restants);
    }

    // affiche le nombre de cartes (et donc le nombre de tours restants)
    System.out.println("Nombre de cartes = " + cartes.size());

  }

  /**
   * lance une partie
   */
  public void lancePartie() {
    // on initialise la carte de départ de la première manche
    Carte carteDepart = cartes.get(0);
    cartes.remove(carteDepart);

    // tant que le paquet de cartes n'est pas vide, on fait une nouvelle manche
    while (!cartes.isEmpty()) {
      // On affiche le nombre de cartes restants, ce qui correspond au nombre de tours
      System.out.println("nombre de manches restantes = " + cartes.size());

      // A chaque nouvelle manche on tire au hasard une carte d'Arrivée dans la liste des cartes
      double a = Math.random() * cartes.size();
      int b = (int) a;
      Carte carteArrivee = cartes.get(b);

      // Une fois la carte piochée on la retire de la liste des cartes
      cartes.remove(carteArrivee);

      // On lance la manche
      Manche manche = new Manche(carteDepart, carteArrivee, joueurs);
      manche.lanceManche();

      // La carte de départ de la prochaine manche est la carte d'arrivée de l'ancienne manche
      carteDepart = carteArrivee;
    }

    // Une fois que la partie est finie, on affiche les scores
    afficheScore();
  }

  /**
   * affiche le score en fin de partie
   */
  public void afficheScore() {
    // on trie la liste des joueurs en fonction de leurs scores (et par ordre alphabétique si egalité il y a)
    Collections.sort(joueurs);

    // On affiche un à un chaque joueur ainsi que son rang
    int rang = 1;
    int score = joueurs.get(0).getScore();
    for (Joueur joueur : joueurs) {
      if (joueur.getScore() < score) {
        // Si le score du joueur à afficher est inférieur au score du joueur précédent,
        // il perd une place au classement
        rang++;
        System.out.println(rang + " : " + joueur.getNom() + "(" + joueur.getScore() + ")");
      } else {
        // Si le score est égal au score du joueur précédent
        // Les deux joueurs ont le même rang
        System.out.println(rang + " : " + joueur.getNom() + "(" + joueur.getScore() + ")");
        rang++;
      }
      score = joueur.getScore();

    }

  }
  ///

  /**
   * fonction créant toutes les combinaisons possibles à partir d'une liste d'animaux
   * 
   * @param animaux_restants
   *          : Une liste des animaux restants à ajouter pour compléter une combinaison
   */
  public void creerCombinaisons(List<Animal> animaux_restants) {
    if (animaux_restants.size() == 1) {
      // Il reste 1 seul animal à ajouter => la combinaison est complète
      liste_finale.add(animaux_restants.get(0));
      // et on créé les cartes correspondant à la combinaison
      creerCartes(liste_finale);
      liste_finale.remove(animaux_restants.get(0));
      return;
    }

    // on ajoute un animal à partir de la liste des animaux restants
    for (Animal animal : animaux_restants) {
      liste_finale.add(animal);

      // crée une copie de la liste d'animaux restants dans laquelle on retire l'animal précédemment ajouté
      List<Animal> animaux_restants2 = new ArrayList<>(animaux_restants);
      animaux_restants2.remove(animal);

      // rappelle récursivement la méthode avec la nouvelle liste des animaux restants
      creerCombinaisons(animaux_restants2);

      // Une fois que la combinaison a été complétée et la carte créée,
      // on supprime le dernier élément pour créer une nouvelle combinaison
      liste_finale.remove(animal);
    }
  }

  /**
   * fonction créant toutes les cartes possibles à partir d'une combainaison d'animaux
   * 
   * @param animaux
   *          : liste d'animaux
   */
  public void creerCartes(List<Animal> animaux) {

    // à chaque itération de la boucle, une nouvelle carte est créée:
    // Le nombre d'animaux sur le podium bleu diminue de 1 à chaque carte,
    // et le nombre d'animaux sur le podium rouge augmente donc de 1
    for (int taille_podium_bleu = animaux.size(); taille_podium_bleu >= 0; taille_podium_bleu--) {

      // On créé une carte vide
      Carte carte = new Carte();
      cartes.add(carte);

      // On ajoute d'abord autant d'animaux sur le podium bleu qu'il en faut pour la taille qu'on a défini
      // Puis on ajoute les animaux restants sur le podium rouge
      for (Animal animal : animaux) {
        if (carte.getPodiumBleu().taille() < taille_podium_bleu)
          carte.getPodiumBleu().empileAnimal(animal);
        else
          carte.getPodiumRouge().empileAnimal(animal);
      }
    }
  }

  // getters et setters

  public List<Carte> getCartes() {
    return cartes;
  }

  public List<Joueur> getJoueurs() {
    return joueurs;
  }
}
