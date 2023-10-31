package finalprojectv4_kkeener;

import java.util.Arrays;
import java.util.List;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class WordsList {
    
    Scanner input;
    Random rand = new Random();
//Declare 3 arrays
    String[] easyWords = {};
    String[] normalWords = {};
    String[] hardWords = {};
    String randomWord;
//Declare 3 lists    
    List<String> easyWordsList = new ArrayList<>(Arrays.asList(easyWords));
    List<String> normalWordsList = new ArrayList<>(Arrays.asList(normalWords));    
    List<String> hardWordsList = new ArrayList<>(Arrays.asList(hardWords));      
    
    public WordsList() throws IOException {
        this.input = new Scanner(Paths.get("wordsList.txt"));
        
        while(input.hasNext()){ //While wordslist has input, sort words
            
            String word = input.next();
            int wordLength = word.length();
            
            if (wordLength == 10){
                easyWordsList.add(word);
            } else if (wordLength == 8){
                normalWordsList.add(word);
            } else if (wordLength == 6){
                hardWordsList.add(word);
            }
        }
    }
    
    public String getList(double difficulty){ // get difficulty from FXML controller
        if (difficulty == 1){
            randomWord = easyWordsList.get(rand.nextInt(easyWordsList.size()));
            return randomWord;
        } else if (difficulty == 2){
            randomWord = normalWordsList.get(rand.nextInt(normalWordsList.size()));
            return randomWord;
        } else if (difficulty == 3){
            randomWord = hardWordsList.get(rand.nextInt(hardWordsList.size()));
            return randomWord;
        } else {
            return null;
        }
    }

  //  String get(int nextInt, List<String> wordList) {
        

        
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  //  }
       
    
}
