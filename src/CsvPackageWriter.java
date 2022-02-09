import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CsvPackageWriter {
    
    /**
     * Fonction permettant d'entrer les informations necessaires
     * dans mon fichier "classes.csv"
     * source:https://www.youtube.com/watch?v=dHZaqMmQNO4
     * @param nomFichier
     * @param container
    */    
    static void toCsv(String nomFichier, List<String[]> container){
        String file = nomFichier+".csv";
        File csvFile = new File(file);
        try {
            PrintWriter out = new PrintWriter(csvFile);
            out.printf("%s, %s, %s, %s, %s\n",
            "chemin","paquet","paquet_LOC","paquet_CLOC","paquet_DC");

            for(int i = 0; i < container.size(); i++){
                for(int j = 0; j < container.get(i).length - 1; j++){
                    out.printf("%s, ",
                    container.get(i)[j]);
                }
                
                if(i == container.size() - 1){
                    out.printf("%s",
                    container.get(i)[container.get(i).length - 1]);
                }else{
                    out.printf("%s\n",
                    container.get(i)[container.get(i).length - 1]);
                }

            }
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } 
}
