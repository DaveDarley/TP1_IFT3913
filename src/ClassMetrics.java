import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ClassMetrics {


    static BufferedReader readFiles(String chemin){
        FileReader fileToParse = null;
        BufferedReader texte  = null;
        try {
            fileToParse = new FileReader(chemin);
            texte = new BufferedReader(fileToParse);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return texte;
    }

    /**
     * Fonction retournant le nb de ligne de code d'un fichier.
     * Les lignes vides ne sont pas prises en compte lors du calcul
     * Ligne code = nb ligne total - ligne vide
     * @param text
     * @return
     */

    static int get_classe_LOC(BufferedReader text){

        int nbLineOfFile = 0;
        int nbEmptyLine = 0;
        String line = null;
        
        try {
            while((line=text.readLine()) != null){
                nbLineOfFile++;
                nbEmptyLine += isLineEmpty(line);    
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // test
        System.out.println("nb de lignes Total: "+ nbLineOfFile);
        System.out.println("nb de lignes vide: "+nbEmptyLine);

        return nbLineOfFile - nbEmptyLine;
    }

    /**
     * Fonction retournant le nombre de lignes
     * de commentaires d'un fichier texte.
     * Je compte le nb de ligne total, nb de ligne vide
     * et le nb de ligne qui contient que du texte.
     * Apres ces 3 types de ligne, on a que des commentaires:
     * que ce soit //, /** ./ ou /* ./ 
     * @param texte
     * @return
     */

    static int get_classe_CLOC(BufferedReader texte){

        int nbLineWithComments = 0;
        String line = null;

        //System.out.println("Test Cloc:");

        try {
            while((line=texte.readLine()) != null){
                
                line = line.trim();
                if( (isLineEmpty(line) == 0) && parseLine(line) == 2){ 
                    nbLineWithComments++;
                    //System.out.println(line);

                    if(line.length()>2 && (line.charAt(line.length()-1)!='/') && 
                      (!line.contains("/**")) ){

                    }else{
                        while((line=texte.readLine()) != null && !(line.contains("*/")) ){
                            if(isLineEmpty(line)==0){ 
                                nbLineWithComments++;
                               // System.out.println(line);
                            }
                        }
                        nbLineWithComments++;
                        //System.out.println(line);
                    }
                }else{

                    if(isLineEmpty(line) == 0 && parseLine(line) != 0){  
                        //System.out.println(line);
                        nbLineWithComments+= parseLine(line);
                    }
                }   
            }
           
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("nb lignes de commentaires: "+nbLineWithComments);
        return nbLineWithComments;
    }


    static double get_classe_DC(int classe_CLOC, int classe_LOC) {
        return (double) classe_CLOC / (double) classe_LOC;
    }


    /**
     * Fonction qui decrit si la ligne passee en parametre
     * est vide ou pas
     * @param line
     * @return
     */
    static int isLineEmpty(String line){
    
        String lineToParse = line.trim(); // enleve whitespace si y en a en debut de ligne
        if(lineToParse.length() == 0){ // ligne vide
            return 1;
        }
        
        return 0;
    }

    /**
     * 
     * @param line
     * @return
     */
    /* Idee:
    Si la ligne commence par une lettre de A-Z et ca contient // ou /* alors
    cette ligne a et des commentaires et du texte; on le considere pr le cloc.
    Si la ligne commence par une lettre de A-Z et ne contient pas de // ou /* alors
    c'est une ligne de texte normal.
    Si la ligne commence par /* alors j'ai 2 cas:
    - 
    -
    */
    static int parseLine(String line){

        String lineToParse = line.trim(); // enleve whitespace si y en a en debut de ligne
        int answer = 0;

        if(lineToParse.length() != 0 && lineToParse.charAt(0) != '/'){ // ma ligne commence par une lettre (A-Z)
            
            if( (lineToParse.contains("//")) || (lineToParse.contains("/*"))  ){ // cette ligne contient et du texte et des commentaires 
                answer = 1;
            }else{ // ligne texte
                answer = 0;
            }

        }else {
            // Alors "peut-etre" bloc commentaire ou juste une ligne de commentaire

            if(lineToParse.length() != 0 && lineToParse.charAt(0) == '/' && lineToParse.charAt(1) == '/'){
                answer = 1;
            }

            else if(lineToParse.length() != 0 && lineToParse.charAt(0) == '/' && lineToParse.charAt(1) == '*'){
                answer = 2;
            }
        }

        return answer;
    }  
}



// je suppose si quelqu'un m'ecrit un if direct comme ca apres un commentaire
// c du mauvais code
/*



*/ //if() // pas encore pris en compte

