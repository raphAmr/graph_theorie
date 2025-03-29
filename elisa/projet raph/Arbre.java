import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Cette classe contient des fonctions permettant l'initialisation de l'abre et le parcours en profondeur
public class Arbre {

    public Map<Integer, List<Integer>> critique;

    public Arbre() {
        this.critique = new HashMap<Integer, List<Integer>>();
    }

    // fonction permettant de créer un noeud de l'arbre
    public void ajouterNoeud(int noeud) {
        if (!critique.containsKey(noeud)) {
            critique.put(noeud, new ArrayList<Integer>());
        }
    }

    // fonction permettant de créer les lien entre les noeuds
    public void addEdge(int src, int dest) {
        ajouterNoeud(src);
        ajouterNoeud(dest);
        if (!critique.get(dest).contains(src)) {
            critique.get(dest).add(src);
        }
    }

    // Méthode de parcours en profondeur récursive
    public void dfs(int noeud, List<Integer> path, List<List<Integer>> paths,int fin) {
        // Si le noeud courant est le noeud d'arrivée, on ajoute le chemin à la liste des chemins
        if (noeud == fin) {
            paths.add(new ArrayList<Integer>(path));
            return;
        }

        // Pour chaque noeud prédécesseur, on continue le parcours en profondeur
        for (int pred : critique.get(noeud)) {
            path.add(pred);
            dfs(pred, path, paths,fin);
            path.remove(path.size() - 1);
        }
    }

    // Fonction permettant d'afficher les chemins critiques
    public void afficher_chemins(int fin){
        List<List<Integer>> paths = new ArrayList<List<Integer>>();
        List<Integer> path = new ArrayList<Integer>();
        path.add(0); // Noeud de départ
        this.dfs(0, path, paths,fin);
        // Affichage des chemins
        for (List<Integer> chemin : paths) {
            for (int i = 0; i< chemin.size(); i++) {
                if (chemin.get(i) == 0) {
                    System.out.print("Début");
                } else if (chemin.get(i) == fin) {
                    System.out.print("Fin");
                } else {
                    System.out.print(chemin.get(i));
                }
                if (i != chemin.size()-1) {
                    System.out.print("->");
                }
            }
            System.out.println(" ");
        }
    }

}