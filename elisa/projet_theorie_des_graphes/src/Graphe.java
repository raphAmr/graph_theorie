import java.util.ArrayList;
import java.util.List;

public class Graphe {
    int rang_max;
    int date_plus_tot;
    int date_plus_tard;
    int nom;
    List<Sommet> liste_Sommets= new ArrayList<Sommet>();
    List<Sommet> tache_crit= new ArrayList<Sommet>();
    Arbre critique = new Arbre();

    // Constructeur
    Graphe(int num){
        this.nom=num;
    }

    // creation de la matrice de valeurs
    public String[][] matrice_valeur(){
        // Creation du tableau rempli de *
        int nbSommet= liste_Sommets.size();
        String[][] tab = new String[nbSommet][nbSommet];
        for(int i = 0; i < nbSommet; i++) {
            for(int j = 0; j < nbSommet; j++) {
                tab[i][j] = "*";
            }
        }
        // remplir le tableau avec les valeurs
        List<Integer> liste = new ArrayList<Integer>();
        for (Sommet sommet: liste_Sommets ) {
            int colonne= sommet.nom -1;
            // ajout des durées dans le tableau grace aux listes de predecesseur
            for (Integer predecesseur: sommet.liste_predecesseceur ) {
                liste.add(predecesseur);
                tab[predecesseur-1][colonne] = Integer.toString(liste_Sommets.get(predecesseur-1).duree);
            }
        }
        // Ajout de l'information sur les sommets terminaux
        for (Sommet sommet: liste_Sommets ) {
            int ligne= sommet.nom -1;
            for(int j = 0; j < nbSommet; j++) {
                if(tab[ligne][j] != "*"){
                    sommet.fin=false;
                }
            }
        }
        return tab;
    }

    // Fonction d'affichage de la matrice de valeurs
    public void afficherMatrice(String[][] tab){
        int nbSommet= liste_Sommets.size();
        // Affichage des sommets
        for(int i=1; i< nbSommet+1;i++){
            System.out.print("\t"+(i));
        }
        System.out.println();
        // Affichage de la matrice
        for(int i=0; i< nbSommet;i++){
            System.out.print((i+1)+"\t");
            for(int j=0; j< nbSommet; j++){
                System.out.print(tab[i][j]+"\t");
            }
            System.out.println();
        }

        System.out.println("\n----------------------------------------Matrice de valeurs associés au tableau de contraintes----------------------------------------\n");

    }

    // Verification de la présence de cycle par algorythme de suppression des points d'entrées
    public boolean presence_cycle(){
        System.out.println("\n--------------------Détéction de circuit avec la méthode de suppression des points d'entrées--------------------\n");

        // Copie en profondeur de la liste : liste_Sommets
        List<Sommet> liste_Sommets_copie = new ArrayList<Sommet>();
        for (Sommet s : liste_Sommets) {
            Sommet copie = new Sommet(s.getNom(), s.getDuree(), s.getListe_predecesseceur_copie());
            copie.rang=s.rang;
            liste_Sommets_copie.add(copie);
        }

        // Initialisation d'une liste vide pour y stocker les sommets a retirer
        List<Sommet> liste_Sommets_retire = new ArrayList<Sommet>();
        boolean verif_cycle=false;
        boolean verif_pas_cycle=false;

        // Algorythme de suppression des entrées
        while(!verif_cycle && !verif_pas_cycle){
            // recupération des sommets sans prédecesseur à stocker dans liste_Sommets_retire
            for (Sommet sommet : liste_Sommets_copie) {
                if(sommet.liste_predecesseceur.isEmpty()||sommet.liste_predecesseceur.contains(0)){
                    liste_Sommets_retire.add(sommet);
                }
            }

            // Pour chaque point d'entrée retirer leur présence dans la liste prédecesseur des autres sommets
            System.out.print("Point d'entrées : ");
            for (Sommet sommet: liste_Sommets_retire) {
                System.out.print(sommet.nom + " ");
                for (Sommet s: liste_Sommets_copie) {
                    if (s.liste_predecesseceur.contains(sommet.nom)) {
                        s.liste_predecesseceur.remove((Integer) (sommet.nom));
                    }
                }
            }

            // Suppression des points d'entrées
            System.out.println("\nSuppression des points d'entées");
            liste_Sommets_copie.removeAll(liste_Sommets_retire);

            // Affichage des sommets restants
            System.out.print("Sommets restants : ");
            for (Sommet sommet: liste_Sommets_copie) {
                System.out.print(sommet.nom + " ");
            }
            System.out.println("\n");

            // Vérifications pour la sortie de la boucle
            if(liste_Sommets_retire.size()==0){
                verif_cycle = true ;
                System.out.println("Présence de circuit");
                System.out.println(" ");

            }
            if (liste_Sommets_copie.isEmpty()){
                verif_pas_cycle = true;
                System.out.println("Abscence de circuit");
                System.out.println(" ");
            }

            // Remise à 0 de la liste sommet retire
            liste_Sommets_retire.removeAll(liste_Sommets_retire);


        }
        System.out.println("\n----------------------------------------Fin détection de circuit----------------------------------------\n");
        return verif_cycle;
    }

    // Verification de la presence d'arcs négatifs
    public boolean presenceArcNegatif(){
        // return false si il contient des valeurs négatives
        // return true sinon

        System.out.println("\n----------------------------------------Détéction d'arcs négatifs----------------------------------------\n");

        for (Sommet sommet : liste_Sommets) {
            if(sommet.getDuree() < 0){
                System.out.println("Présence d'arcs négatifs\n");
                System.out.println("\n----------------------------------------Fin détéction d'arcs négatifs----------------------------------------\n");
                return false;
            }
        }
        System.out.println("Abscence d'arcs négatifs\n");
        System.out.println("\n----------------------------------------Fin détéction d'arcs négatifs----------------------------------------\n");
        return true;
    }

    // Calcul des rangs avec l'algorythme de suppression des points d'entrées
    public void calcul_rangs(){

        System.out.println("\n--------------------Calcul de rang avec la méthode de suppression des points d'entrées--------------------\n");

        // Copie en profondeur de la liste : liste_Sommets
        List<Sommet> liste_Sommets_copie = new ArrayList<Sommet>();
        for (Sommet s : liste_Sommets) {
            Sommet copie = new Sommet(s.getNom(), s.getDuree(), s.getListe_predecesseceur_copie());
            copie.rang=s.rang;
            liste_Sommets_copie.add(copie);
        }
        // Initialisation d'une liste vide pour y stocker les sommets a retirer
        List<Sommet> liste_Sommets_retire = new ArrayList<Sommet>();
        int rang = 1;

        // Algorythme de suppression des entrées
        while(!liste_Sommets_copie.isEmpty()){
            for (Sommet sommet : liste_Sommets_copie) {
                // recupération des sommets sans prédecesseur à stocker dans liste_Sommets_retire
                if(sommet.liste_predecesseceur.isEmpty()||sommet.liste_predecesseceur.contains(0)){
                    liste_Sommets_retire.add(sommet);
                    for (Sommet s: liste_Sommets) {
                        if(s.nom==sommet.nom){
                            s.rang=rang;
                        }
                    }
                }
            }
            // Pour chaque point d'entrée retirer leur présence dans la liste prédecesseur des autres sommets
            System.out.println("Rang : " + rang );
            System.out.print("Point d'entrées : ");
            for (Sommet sommet: liste_Sommets_retire) {
                System.out.print(sommet.nom + " ");
                for (Sommet s: liste_Sommets_copie) {
                        if (s.liste_predecesseceur.contains(sommet.nom)) {
                            s.liste_predecesseceur.remove((Integer) (sommet.nom));
                        }
                }
            }

            // Suppression des points d'entrées
            System.out.println("\nSuppression des points d'entées");
            liste_Sommets_copie.removeAll(liste_Sommets_retire);

            // Remise à 0 de la liste sommet retire
            liste_Sommets_retire.removeAll(liste_Sommets_retire);

            // Affichage des sommet restants
            System.out.print("Sommets restants : ");
            for (Sommet sommet: liste_Sommets_copie) {
                System.out.print(sommet.nom + " ");
            }
            System.out.println("\n");
            rang++;

        }
        this.rang_max=rang-1;
        System.out.println("\n--------------------Fin de calcul de rang--------------------\n");
    }

    // Fonction d'affichage des rangs
    public void afficher_rangs(){
        // Affichage des rangs
        System.out.println("\n\n----------------------------------------Les rangs----------------------------------------\n");
        for (Sommet s :liste_Sommets) {
            System.out.println("Sommet : "+ s.nom + " Rang : "+s.rang);
        }
        System.out.println("\n\n----------------------------------------Les rangs----------------------------------------\n");
    }

    // Fonction de calcul du calandrier de dates au plus tôt
    public void calendrier_plus_tot() {
        int max=0;
        int nouv;
        int fin=0 ;
        for (int i=0;i<this.rang_max+1;i++){
            for (Sommet sommet: liste_Sommets) {

                if(sommet.rang==i){
                    max=0;
                    if(sommet.liste_predecesseceur.isEmpty()){
                        sommet.date_tot=0;
                    }
                    else {
                        for (int integer:sommet.liste_predecesseceur) {
                            nouv=liste_Sommets.get(integer-1).date_tot+liste_Sommets.get(integer-1).duree;
                            if(max<nouv){
                                max=nouv;

                            }
                        }
                        sommet.date_tot=max;
                    }
                    if (sommet.fin){
                        nouv=sommet.date_tot+sommet.duree;
                        if(fin<nouv){
                            fin=nouv;
                        }
                    }
                }
            }
        }
        this.date_plus_tot=fin;
        this.date_plus_tard=fin;
        for (Sommet s:liste_Sommets) {
            s.date_tard=fin;
        }
    }

    // Fonction de calcul du calandrier de dates au plus tard
    public void calendrier_plus_tard() {
        int max=0;
        int nouv;
        int fin=0 ;
        for (int i=this.rang_max;i>0;i--){
            for (Sommet sommet: liste_Sommets) {

                if(sommet.rang==i){

                    if (sommet.fin){

                        sommet.date_tard=this.date_plus_tard-sommet.duree;
                    }
                    if(!sommet.liste_predecesseceur.isEmpty()){
                        for (int integer:sommet.liste_predecesseceur) {

                            nouv=sommet.date_tard-liste_Sommets.get(integer-1).duree;
                            if(liste_Sommets.get(integer-1).date_tard>nouv){
                                liste_Sommets.get(integer-1).date_tard=nouv;
                            }
                        }
                    }

                }
            }
        }

    }

    // Calcul des marges totales
    public void marges_totales(){
        for (Sommet s :liste_Sommets) {
            s.margeT=s.date_tard-s.date_tot;
        }
    }

    // Affichage du calendrier
    public void calendrier(){

        System.out.println("\n----------------------------------------calendrier----------------------------------------\n");

        System.out.print("\t\t\tDébut\t\t");
        for (Sommet s :liste_Sommets) {
            System.out.print( s.nom + "\t\t");

        }
        System.out.print("Fin\nDurée\t\t\t\t\t");
        for (Sommet s :liste_Sommets) {
            System.out.print( s.duree + "\t\t");

        }
        System.out.print("\nDate tot\t\t0\t\t");
        for (Sommet s :liste_Sommets) {
            System.out.print( s.date_tot + "\t\t");

        }
        System.out.print(this.date_plus_tot+"\nDate tard\t\t0\t\t");
        for (Sommet s :liste_Sommets) {
            System.out.print( s.date_tard + "\t\t");
        }
        System.out.print(this.date_plus_tard+"\nMarge Totale\t0\t\t");
        for (Sommet s :liste_Sommets) {
            System.out.print( s.margeT + "\t\t");
        }
        System.out.println("0");

        System.out.println("\n----------------------------------------calendrier----------------------------------------\n");
    }

    // Detrermination des taches critiques
    public void tache_critique(){
        for (Sommet som : liste_Sommets){
            if(som.margeT == 0){
                tache_crit.add(som);
            }
        }

    }

    // Affichage des chemins critiques
    public void afficher_crit() {
        System.out.println("\n----------------------------------------Chemins critiques----------------------------------------\n");
        List<Sommet> fin = new ArrayList<>();
        // Nombre de sommet terminaux critiques
        int cpt=0;
        for (Sommet sommet:tache_crit) {
            if (sommet.rang==rang_max){
                fin.add(sommet);
                // création des noeuds et des liens entre les noeuds
                critique.addEdge(liste_Sommets.size()+1,sommet.nom);
                cpt++;
            }
        }

        // initialisation d'un tableau permettant de conserver pour chaque rang quels sont les sommmets
        List<Sommet>[][] tache_critique = new ArrayList[rang_max-1][cpt];

        for (Sommet sommet:liste_Sommets) {
            if (sommet.rang==1){
                // création des noeuds et des liens entre les noeuds
                critique.addEdge(sommet.nom,0);
            }
        }
        //initialisation mémoire du tableau
        for(int i=0 ; i<cpt;i++){
            for(int j=0 ; j<rang_max;j++){
                tache_critique[i][j] = new ArrayList<Sommet>();
            }
        }
        // Ajout des taches finales dans le tableau (première colonne)
        for(int j=0 ; j<cpt;j++){
            tache_critique[j][0].add(fin.get(j));
        }

        // Affichage des tâches critiques
        System.out.print("Liste des tâches critiques : ");
        for (Sommet sommet :tache_crit) {
            System.out.print(sommet.nom + " ");
        }

        System.out.println(" ");
        // Remlpire toutes les cases du tableau
        for (int i=0; i<cpt; i++){
            for(int colonne=1;colonne<rang_max;colonne ++){
                for (int h=0; h<tache_critique[i][colonne-1].size();h++){
                   for (Integer integer : tache_critique[i][colonne-1].get(h).liste_predecesseceur) {
                        for (Sommet sommet :liste_Sommets) {
                            if (sommet.nom==integer && this.tache_crit.contains(sommet)){
                                // pour chaque sommet ajouter les liens aves les predecesseur critiques
                                critique.addEdge(tache_critique[i][colonne-1].get(h).nom,sommet.nom);
                                tache_critique[i][colonne].add(sommet);
                            }
                        }
                    }
                }
            }

        }
        //Affichage des chemins critiques
        System.out.println("\nLes chemins critiques :");

        critique.afficher_chemins(liste_Sommets.size()+1);

        System.out.println("\n----------------------------------------Fin chemins critiques----------------------------------------\n");
    }



}
