import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Demander le nombre de joueurs
        System.out.println("Entrez le nombre de joueurs : ");

        // Ajouter une vérification de type pour s'assurer que l'entrée est un entier
        while (!scanner.hasNextInt()) {
            System.out.println("Veuillez entrer un nombre valide : ");
            scanner.next(); // Consommer l'entrée invalide
        }

        int numberOfPlayers = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne

        // Demander les noms des joueurs
        String[] playerNames = new String[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Entrez le nom du joueur " + (i + 1) + " : ");
            playerNames[i] = scanner.nextLine();
        }

        // Créer le jeu avec les joueurs
        Jeu jeu = new Jeu(playerNames);

        // Lancer la partie
        jeu.lancePartie();

        // Fermer le scanner
        scanner.close();
    }
}
