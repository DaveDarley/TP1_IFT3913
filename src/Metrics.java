import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class Metrics {
    
    static List <String[]>classInfos = new ArrayList<>();
    static List<String[]> packageInfos = new ArrayList<>();

    // contient la liste des dossier se trouvant sur un chemin donne
    static ArrayList<String> repositories = new ArrayList<>();

    static CsvPackageWriter csvPackageWriter;
    static CsvClassWriter csvClassWriter;


    /**
     * Fonction recuperant tous les dossiers d'un chemin donne.
     * Le chemin de chaque dossier est ensuite stocke dans un
     * arrayList "repositories"
     * @param chemin
     * @return 
     */
    static void parcoursRepertoire(String chemin){

        Path path = Paths.get(chemin);
        try(Stream<Path> subPaths = Files.walk(path) ){
            // Je recupere tous les DOSSIERS de mon repertoire
            subPaths.filter(Files::isDirectory).forEach(a -> repositories.add(a.toString()));
        } catch (IOException e){
           e.printStackTrace();
        }

       for(int i=0; i<repositories.size(); i++){
           metrics(repositories.get(i));
       }
       CsvClassWriter.toCsv("classes", classInfos);
       CsvPackageWriter.toCsv("paquets", packageInfos);
    }


    /**
     * Fonction recuperant tous les fichiers d'un dossier
     * specifique, calcul le loc,cloc et dc de chaque fichier
     * se trouvant dans le dossier. Les resultats du calcul sont
     * ensuite stocké dans mes ArrayList: classInfos et packageInfos
     * @param folder
     */

     // Src: https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
     // https://stackoverflow.com/questions/11373130/how-to-get-foldername-and-filename-from-the-directorypath
    static void metrics(String folder){

        int locPaquet , clocPaquet;
        locPaquet = clocPaquet = 0;
        double dcPaquet; 
        dcPaquet = 0; 

        int nbFichier = 0;

        File myFolder = new File(folder);
        File[] listOfFiles = myFolder.listFiles(); // list de fichiers dans mon dossier

        for(File file : listOfFiles){
            if(file.isFile()){

                String path = file.getPath();
                if(path.substring(path.length()-5).equals(".java")){
                
                    nbFichier++;
                    /*
                    Prob: j'utilise le meme bufferedRead alors qd je 
                    fs une 1ere lecture de mon doc qd je cherche le loc, qd je 
                    vais faire une deuxieme lecture du doc pr le cloc, mon
                    bufferedReader est deja a la fin du document. Et donc line == null
                    alors ca lit rien.
                    Solution non efficace: creer 2 bufferedReader (a voir comment ameliorer)
                    */

                    BufferedReader texte1 =  ClassMetrics.readFiles(path);
                    BufferedReader texte2 =  ClassMetrics.readFiles(path);

                    String name = file.getName();
                    int loc = ClassMetrics.get_classe_LOC(texte1);
                    int cloc = ClassMetrics.get_classe_CLOC(texte2);
                    double dc = ClassMetrics.get_classe_DC(cloc,loc);

                    locPaquet += loc; clocPaquet += cloc; 
                    // infos entrees de la facon qu'il vont etre mis dans le fichier csv
                    classInfos.add(new String[]
                    {path,name,loc+"",cloc+"",dc+""});
                }
            }
        }
        
        if(nbFichier != 0){ // mon paquet a au moins 1 fichier java
            dcPaquet = (double) clocPaquet/ (double) locPaquet;
            String name = myFolder.getName();

            packageInfos.add(new String[]
            {folder,name,locPaquet+"",clocPaquet+"",dcPaquet+""});
        }
    }    
}
