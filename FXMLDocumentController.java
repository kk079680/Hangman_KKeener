/*
 * Kyle Keener
 * Document controller for FinalProjectGUI.fxml
 * 
 */
package finalprojectv4_kkeener;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.IOException;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.stream.IntStream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.Iterator;

/**
 *
 * @author SecurityStudent
 */
public class FXMLDocumentController implements Initializable { // Declare all variables here
    
    private WordsList words;
    private Random rand = new Random();
    private Scanner input;
    private double difficulty;
    private String randomWord;
    private int numOfGuesses;
//    private String[] previousGuesses = {};
    private List<String> prevGuesses = new ArrayList<>(1);
    private String wordStatus;
    private char guess;
    private String guessString;
    private String easySpace = "__________";
    private String normalSpace = "________";
    private String hardSpace = "______";
    private int guessPosition;
    private int guessPosition2;
    
// Save data variables
    private String saveRandomWord;
    private int saveNumOfGuesses;
    private String saveGuessString;
    private char saveGuess;
    private String saveWordStatus;
    private double saveDifficulty;
    private List<String> savedGuesses = new ArrayList<>(1);

    @FXML
    private Label difficulty_label;

    @FXML
    private Button save_btn;

    @FXML
    private Button guess_btn;

    @FXML
    private Label word_status;

    @FXML
    private Button load_btn;

    @FXML
    private Button give_up_btn;

    @FXML
    private Label gueses_remaining_txt;

    @FXML
    private Label letters_guessed_txt;

    @FXML
    private Slider difficulty_slider;

    @FXML
    private Button generate_game_btn;

    @FXML
    private TextField guess_txt;

    @FXML
    private Label word_status_label;

    public FXMLDocumentController() throws IOException { // constructor for Arraylist and wordlist
        this.prevGuesses = new ArrayList<>();
        this.words = new WordsList();
    }
    
    @FXML
    void onGenerate(ActionEvent event) throws IOException { // Generates game from difficulty slider input
        if (difficulty_slider.getValue() == 1){
            word_status.setText(easySpace);
            wordStatus = word_status.getText();
            
            difficulty_label.setText("Easy");
            numOfGuesses = 10;
            
            gueses_remaining_txt.setText("" + numOfGuesses);
            difficulty = difficulty_slider.getValue();
            randomWord = words.getList(difficulty);
            letters_guessed_txt.setText("");
            
        } else if (difficulty_slider.getValue() == 2){
            word_status.setText(normalSpace);
            wordStatus = word_status.getText();
            
            difficulty_label.setText("Normal");
            numOfGuesses = 7;
            
            gueses_remaining_txt.setText("" + numOfGuesses);
            difficulty = difficulty_slider.getValue();
            randomWord = words.getList(difficulty);
            letters_guessed_txt.setText("");            
            
        } else if (difficulty_slider.getValue() == 3){
            word_status.setText(hardSpace);
            wordStatus = word_status.getText();
            
            difficulty_label.setText("Hard");
            numOfGuesses = 4;
            
            gueses_remaining_txt.setText("" + numOfGuesses);
            difficulty = difficulty_slider.getValue();
            randomWord = words.getList(difficulty);
            letters_guessed_txt.setText("");            
        }
        
        word_status_label.setText("Current Word Status");
        //System.out.printf("The word is: %s%n", randomWord);
        difficulty_slider.disableProperty();
        generate_game_btn.disarm();
    }

    @FXML
    void onLoadGame(ActionEvent event) { // Load game response to save game function
        randomWord = saveRandomWord;
        numOfGuesses = saveNumOfGuesses;
        guessString = saveGuessString;
        guess = saveGuess;
        wordStatus = saveWordStatus;
        difficulty = saveDifficulty;
        
        for (int i = 0; i < savedGuesses.size(); i++){
            prevGuesses.add(savedGuesses.get(i));
            letters_guessed_txt.setText(Arrays.toString(prevGuesses.toArray()));
            savedGuesses.remove(i);
        }
        
        word_status_label.setText("");
        word_status.setText(wordStatus);
    }

    @FXML
    void onGiveUp(ActionEvent event) {
        word_status_label.setText("You lost! Your word was: ");
        word_status.setText(randomWord);
        guess_txt.clear();
        guess_txt.disableProperty();
    }

    @FXML
    void onGuess(ActionEvent event) {
        // Get letter guess
        // Ensure it is 1 character
        // Iterate through word
        // find index of word
        // replace wordStatus at the given position with the guessed letter
        // add guess to array
        // If guess is not found, clear, deduct number of guesses, and add to array
        StringBuilder buffer = new StringBuilder(wordStatus);
        randomWord = randomWord.toLowerCase();
        guessString = "";
        while(!randomWord.isEmpty()){
            guessString = guess_txt.getText();
            guessString = guessString.toLowerCase();
            if (guessString.length() > 1 || guessString.length() == 0 || guessString.equals(" ")){
                guess_txt.clear();
            } else {
                guess = guessString.charAt(0);
                prevGuesses.add(guessString);
                for (int i = 0; i < randomWord.length(); i++){
                    if(randomWord.charAt(i) == guess){
                        //System.out.printf("%s was found at %d%n", guess, i);
                        guessPosition = randomWord.indexOf(guess);
                        guessPosition2 = randomWord.lastIndexOf(guess);
                        for(int j = 0; j < wordStatus.length() - 1; j++){
                            buffer.setCharAt(guessPosition, guess);
                            buffer.setCharAt(guessPosition2, guess);
                            word_status.setText(buffer.toString());
                            wordStatus = word_status.getText();
                            guess_txt.clear();
                                if(wordStatus.equals(randomWord)){
                                    word_status_label.setText("You Won!");
                                    break;
                                }
                            //System.out.printf("You entered: %s%n", guessString);
                        }
                        
                    } else if (randomWord.indexOf(guess) < 0){
                        //System.out.printf("Position is %d%n", randomWord.indexOf(guess));
                        guess_txt.clear();
                        numOfGuesses = numOfGuesses - 1;
                        gueses_remaining_txt.setText("" + numOfGuesses);
                        if(numOfGuesses == 0){
                            word_status_label.setText("You Lost!  Your word was:");
                            word_status.setText(randomWord);
                        }
                        //System.out.printf("You guessed %s%n", guess);
                        break;
                    }
                }
            }
            break;
        }
        for (String prevGuess : prevGuesses) {
            letters_guessed_txt.setText(Arrays.toString(prevGuesses.toArray()));
        }
        
    }

    @FXML
    void onSave(ActionEvent event) { // Save event for gathering save data
        this.saveRandomWord = randomWord;
        this.saveNumOfGuesses = numOfGuesses;
        this.saveGuessString = guessString;
        this.saveGuess = guess;
        this.saveWordStatus = wordStatus;
        this.saveDifficulty = difficulty;
        
        for (int i = 0; i < prevGuesses.size(); i++){
            savedGuesses.add(prevGuesses.get(i));
            prevGuesses.remove(i);
        }
        
        letters_guessed_txt.setText(""); //Reset playing field
        word_status_label.setText("");
        word_status.setText("");
        guess_txt.clear();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { // Initialize difficulty values
        
        difficulty_slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
            if (difficulty_slider.getValue() == 1.0){
                difficulty_label.setText("Easy");
            } else if (difficulty_slider.getValue() == 2.0){
                difficulty_label.setText("Normal");
            } else if (difficulty_slider.getValue() == 3.0){
                difficulty_label.setText("Hard");
            }
        });
    
        word_status_label.setText("");
    }    
    
} // end FXML controller
