import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // Ouverture du scaner
        Scanner sc = new Scanner(System.in);
        boolean working = true;

        // Boucle de travail
        while (working){
            // Récupération du numero du graph
            System.out.println("\nVeuillez entrer le numéro du tableau ou 0 pour mettre fin au programme : ");
            int num_tab = sc.nextInt();
            if (num_tab<13 && num_tab >0) {
                // Erengistration mémoire du graph
                Graphe graphe = choose_graphe(num_tab);

                // Affichage de la matrice des valeurs
                System.out.println("\n----------------------------------------Matrice de valeurs associés au tableau de contraintes n° " + num_tab + "---------------------------------------- \n");
                graphe.afficherMatrice(graphe.matrice_valeur());
                System.out.println("\n");
                // Verification du graph d'ordanancement 1. Arc négatif
                if (graphe.presenceArcNegatif()) {
                    // 2. présence de cycle
                    if (!graphe.presence_cycle()) {
                        System.out.println("\n\nLe graph est un graph d'ordanancement nous pouvons donc poursuivre");

                        // Calcul de rang
                        graphe.calcul_rangs();
                        graphe.afficher_rangs();

                        // Calendrier
                        graphe.calendrier_plus_tot();
                        graphe.calendrier_plus_tard();
                        graphe.marges_totales();
                        graphe.calendrier();

                        // Chemins critiques
                        graphe.tache_critique();
                        graphe.afficher_crit();
                    } else {
                        System.out.println("\n\nLe graph n'est pas un graph d'ordanancement nous ne pouvons pas poursuivre les opérations\n");
                    }
                }
                else {
                    System.out.println("\n\nLe graph n'est pas un graph d'ordanancement nous ne pouvons pas poursuivre les opérations\n");
                }
            } else if (num_tab == 0) {
                System.out.println("Fin du programme");
                working = false;
            } else{
                System.out.println("Vous avez entré un faux numero");
            }
        }
    }


    //Foncion qui prend le chemin d'acces a un fichier et qui retourne le fichier en question
    public static BufferedReader lecture(String path) throws FileNotFoundException {
        // Création d’un fileReader pour lire le fichier
        FileReader fileReader = new FileReader(path);
        // Création d’un bufferedReader qui utilise le fileReader
        return new BufferedReader(fileReader);
    }


    //Fonction qui prend un numero de tableau et qui enregistre dans une classe les données du tableau choisi
    public static Graphe choose_graphe(int num) throws IOException {
        BufferedReader read = lecture("src/data/table " + num + ".txt");
        Graphe gr = new Graphe(num);
        try {
            String line = read.readLine();

            while (line != null) {
                String[] mots = line.split(" ");
                gr.liste_Sommets.add(new Sommet(Integer.parseInt(mots[0]), Integer.parseInt(mots[1]), new ArrayList<>() {
                    {
                        for (int i = 2; i < mots.length; i++) {
                            add(Integer.parseInt(mots[i]));
                        }
                    }
                }));
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        read.close();
        return gr;
    }

}