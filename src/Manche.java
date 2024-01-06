
import java.util.List;
import java.util.Scanner;

/**
 * Classe gérant la totalité d'une manche
 */
public class Manche {
  // la liste des joueurs
  private List<Joueur> joueurs;
  // le joueur venant de faire une proposition
  private Joueur joueur_en_cours;
  // deux podiums que la classe va manipuler pour vérifier les propositions
  private Podium podiumBleu = new Podium("BLEU");
  private Podium podiumRouge = new Podium("ROUGE");
  // les cartes de Départ et d'Arrivée de la manche
  private Carte carteDepart, carteArrivee;

  /**
   * Le constructeur initialisant la manche
   * 
   * @param carteDepart
   *          : la carte de Départ de la manche
   * @param carteArrivee
   *          : le carte d'Arrivée de la manche
   * @param joueurs
   *          : la liste des joueurs
   */
  public Manche(Carte carteDepart, Carte carteArrivee, List<Joueur> joueurs) {
    this.joueurs = joueurs;
    for (Joueur joueur : joueurs) {
      joueur.setPeutJouer(true);
    }
    this.carteDepart = carteDepart;
    this.carteArrivee = carteArrivee;
  }

  /**
   * lance la manche
   */
  public void lanceManche() {
	// On affiche la situation de jeu
    afficheSituation();

    // On initialise la variable dernierJoueur pour plus tard
    Joueur dernierJoueur = joueurs.get(0);

    // Compteur du nombre de joueurs pouvant encore proposer une séquence pour cette manche
    int nb_joueurs_pouvant_jouer = joueurs.size();

    // à chaque fois qu'un joueur se trompe, le compteur de joueurs pouvant jouer diminue de 1
    // le jeu continue à attendre des propositions tant qu'il reste au moins 2 joueurs pouvant jouer
    Scanner scanner = new Scanner(System.in);
    while (nb_joueurs_pouvant_jouer > 1) {
      // On récupère dans la proposition le nom du joueur
      String nom = scanner.next();

      // On teste si ce nom est valide
      if (testNom(nom)) {
        // Si il est valide, alors on récupère la séquence proposée
        String sequence = scanner.next();
        if (testSequence(sequence)) {
          // Si la séquence est valide alors le joueur a remporté le tour et la manche est finie
          joueur_en_cours.incrementeScore();
          scanner.close();
          return;

        } else {
          // Sinon il ne peut plus proposer de sequence pour ce tour
          joueur_en_cours.setPeutJouer(false);
        }
      }

      // Ignore la suite de la commande
      scanner.nextLine();
    } // while
    scanner.close();

    // On recalcule le nombre de joueurs pouvant jouer
    nb_joueurs_pouvant_jouer = 0;
    for (Joueur joueur : joueurs) {
      if (joueur.getPeutJouer()) {
        nb_joueurs_pouvant_jouer++;
        // On note le joueur qui peut jouer car si c'est le seul, il remporte le tour
        dernierJoueur = joueur;
      }
    }

    // Il ne reste qu'un seul joueur ==> il remporte le tour
    dernierJoueur.incrementeScore();
    System.out.println("Le joueur " + dernierJoueur.getNom()
        + " est le dernier joueur a ne pas avoir proposé de séquence, il remporte le tour.");
  }

  /**
   * Affiche la situation du jeu
   */
  private void afficheSituation() {
    for (int position = 2; position >= 0; position--) {
      String PodiumBleuDepart = centrer(carteDepart.getPodiumBleu(), position, 8);
      String PodiumRougeDepart = centrer(carteDepart.getPodiumRouge(), position, 8);
      String PodiumBleuArrivee = centrer(carteArrivee.getPodiumBleu(), position, 8);
      String PodiumRougeArrivee = centrer(carteArrivee.getPodiumRouge(), position, 8);
      System.out.println("   " + PodiumBleuDepart + "    " + PodiumRougeDepart + "         " + PodiumBleuArrivee
          + "    " + PodiumRougeArrivee);
    }

    System.out.println("     ----        ----     ==>     ----        ----\n"
        + "     BLEU        ROUGE            BLEU        ROUGE \n"
        + "   -------------------------------------------------\n" + "   KI : BLEU --> ROUGE      NI : BLEU ˆ\n"
        + "   LO : BLEU <-- ROUGE      MA : ROUGE ˆ\n" + "   SO : BLEU <-> ROUGE");
  }

  /**
   * Centre le nom d'un animal dans une chaine
   * 
   * @param podium
   *          : le podium de l'animal
   * @param position:
   *          la position de l'animal sur le podium (0 pour le premier)
   * @param longueur
   *          : la longueur de la chaine dans laquelle on veut centrer le mot
   * @return une chaine avec le nom de l'animal centré
   */
  public String centrer(Podium podium, int position, int longueur) {
    // On recupère le nom de l'animal
    String animal = NomAnimal(podium, position);

    // On cree la chaine
    String chaine_finale = String.format("%" + longueur + "s%s%" + longueur + "s", "", animal, "");
    float milieu = (chaine_finale.length() / 2);
    float debut = milieu - (longueur / 2);
    float fin = debut + longueur;
    return chaine_finale.substring((int) debut, (int) fin);
  }

  /**
   * retourne le nom d'un animal à partir de son podium et de sa position
   * 
   * @param podium
   *          : le podium fourni
   * @param position
   *          : la position de l'animal cherché dans le podium fourni
   * @return
   */
  public String NomAnimal(Podium podium, int position) {
    if (podium.taille() > position) {
      return podium.getAnimal(position).getNom();
    } else
      return "";
  }

  /**
   * Methode testant si le nom du joueur est valide
   * 
   * @param nom
   *          : le nom du joueur fourni
   * @return true si le joueur existe et peut jouer, false sinon
   */
  public boolean testNom(String nom) {

    // On teste si le nom fournit est le nom d'un vrai joueur
    boolean joueur_existe = false;
    for (Joueur joueur : joueurs) {
      if (joueur.getNom().equals(nom)) {
        joueur_existe = true;
        // On initialise l'attribut joueur_en_cours, ce qui permettra de manipuler le joueur plus tard
        joueur_en_cours = joueur;
      }
    }
    if (joueur_existe == false) {
      System.out.println("Erreur : Joueur non reconnu");
      return false;
    }

    // Si le joueur existe, on teste si il n'a pas déjà proposé de séquence pour cette manche
    if (joueur_en_cours.getPeutJouer() == false) {
      System.out.println("Le joueur " + nom + " ne peut plus proposer de sequence pour ce tour");
      return false;
    }

    // Le joueur existe et peut jouer, alors on retourne true
    return true;
  }

  /**
   * Methode testant si une sequence est solution de la manche en cours
   * 
   * @param sequence
   *          : la sequence fournie
   * @return true si la séquence est solution, false sinon
   */
  public boolean testSequence(String sequence) {
    int taille = sequence.length();
    // On teste d'abord si la séquence a le bon nombre de caractères (un nombre pair)
    if (taille % 2 == 1) {
      System.out.println("Sequence invalide (ordre inconnu)");
      return false;
    }

    // On initialise ensuite les podiums tels qu'ils sont sur la carte de départ
    podiumBleu = new Podium(carteDepart.getPodiumBleu());
    podiumRouge = new Podium(carteDepart.getPodiumRouge());

    // Et on lance l'éxécution de la séquence
    sequence = sequence.toUpperCase();

    for (int idx = 0; idx < taille; idx += 2) {
      // On récupère 2 caractères qui constituent un nouvel ordre
      String ordre = sequence.substring(idx, idx + 2);
      // Si l'ordre est connu, on l'applique
      if (ordre.equals("KI")) {
        if (!podiumBleu.estVide())
          podiumRouge.empileAnimal(podiumBleu.depileAnimal());
      } else if (ordre.equals("LO")) {
        if (!podiumRouge.estVide())
          podiumBleu.empileAnimal(podiumRouge.depileAnimal());
      } else if (ordre.equals("SO")) {
        if (!podiumRouge.estVide() && !podiumBleu.estVide()) {
          Animal animalrouge = podiumRouge.depileAnimal();
          podiumRouge.empileAnimal(podiumBleu.depileAnimal());
          podiumBleu.empileAnimal(animalrouge);
        }
      } else if (ordre.equals("NI")) {
        if (!podiumBleu.estVide()) {
          podiumBleu.empileAnimal(podiumBleu.getAnimal(0));
          podiumBleu.supprimeAnimal(0);
        }
      } else if (ordre.equals("MA")) {
        if (!podiumRouge.estVide()) {
          podiumRouge.empileAnimal(podiumRouge.getAnimal(0));
          podiumRouge.supprimeAnimal(0);
        }
      }
      // Si l'ordre est inconnu alors la séquence est invalidée
      else {
        System.out.println("Sequence invalide (ordre inconnu)");
        return false;
      }
    }

    // Une fois que la séquence s'est éxécutée, on vérifie que les nouveaux podiums
    // correspondent à la carte d'Arrivée
    if (podiumBleu.equals(carteArrivee.getPodiumBleu()) && podiumRouge.equals(carteArrivee.getPodiumRouge())) {
      System.out
          .println("La sequence proposee est solution, le joueur " + joueur_en_cours.getNom() + " remporte le tour");
      return true;

    } else {
      System.out.println("Séquence invalide");
      return false;
    }
  }

  // getters et setters

  public void setCarteDepart(Carte carteDepart) {
    this.carteDepart = carteDepart;
  }

  public void setCarteArrivee(Carte carteArrivee) {
    this.carteArrivee = carteArrivee;
  }

  public Carte getCarteDepart() {
    return carteDepart;
  }

  public Carte getCarteArrivee() {
    return carteArrivee;
  }
}
