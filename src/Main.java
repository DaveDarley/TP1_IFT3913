import java.io.*;


public class Main {

    static Metrics metriques;
    //------------------- PARTIE 3 -------------------//

    /**
     * Fonction qui prend en paramètre un chemin vers une classe java
     * et calcul  la somme pondérée des complexités cyclomatiques de McCabe
     * de toutes les méthodes de la classe
     * @param Chemin
     * @return
     */
    private static int WMC(String Chemin) {
        return 0;
    }


    /**
     * Fonction qui prend en paramètre un chemin vers un paquet java
     * et calcul  la somme des WMC de toutes les classes d’un paquet
     * et les WCP de ses sous-paquets
     * @param chemin
     * @return
     */
    private static int WCP(String chemin) {
        return 0;
    }


    /**
     * Fonction qui prend en paramètre la densité de commentaires
     * et la somme des complexités cyclomatique de McCabe d'une classe java
     * et retourne le degré selon lequel cette classe est bien commentée
     * @param classe_DC
     * @param WMC
     * @return
     */
    private static double get_classe_BC(double classe_DC, int WMC) {
        return classe_DC / (double) WMC;
    }


    /**
     * Fonction qui prend en paramètre la densité de commentaires
     * et la somme des complexités cyclomatique de McCabe d'un paquet java
     * et retourne le degré selon lequel ce paquet est bien commenté
     * @param paquet_DC
     * @param WCP
     * @return
     */
    private static double get_paquet_BC(double paquet_DC, int WCP) {
        return paquet_DC / (double) WCP;
    }


    /**
     * Main dans lequel nous allons calculer les différentes métriques
     * et ou nous allons créer les fichiers csv associé aux métriques des classes
     * et des paquets
     * @param args
     * @throws IOException
     */
    public static void main(String[] args){
        Metrics.parcoursRepertoire("/Users/davejoseph/Desktop/TestIFT3913");
    }
}
