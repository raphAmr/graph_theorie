import java.util.ArrayList;
import java.util.List;




public class Sommet {
    int nom;
    int duree;
    int date_tot;
    int date_tard;
    int rang;
    boolean fin;
    int margeT;
    List<Integer> liste_predecesseceur = new ArrayList<Integer>();

    Sommet(int nom, int duree){
        this.nom = nom;
        this.duree  = duree;
    }
    Sommet(int nom, int duree, List<Integer> liste_predecesseceur ){
        this.nom = nom;
        this.duree  = duree;
        this.liste_predecesseceur=liste_predecesseceur;
        this.fin=true;
    }

    public int getNom() {
        return nom;
    }

    public int getDuree() {
        return duree;
    }

    public List<Integer> getListe_predecesseceur_copie() {
        List<Integer> liste_predecesseceur_copie = new ArrayList<Integer>();
        for (Integer integer : liste_predecesseceur) {
            liste_predecesseceur_copie.add(integer);
        }
        return liste_predecesseceur_copie;
    }

}
