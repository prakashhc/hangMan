import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Hangman {

    int currentNumWins = 0;
    int highestScore = 0;


    public Hangman(String[] arr) throws FileNotFoundException {

        String[] wordArr = arr;

        int numGuesses = 0;

        int score = 0;

        Scanner scan = new Scanner(System.in);

        //file gets the current word
        String currentWord = wordArr[(int) (Math.random() * wordArr.length)];

        StringBuilder dashedWord = new StringBuilder();  // will store the dashed version of the word (e.g. "a-pl-e")
        for (int i = 0; i < currentWord.length(); i++) {
            dashedWord.append("_");  // initially, all letters are hidden
        }

        //
        ArrayList<String> lettersGuessed = new ArrayList<>();

        System.out.println();
        System.out.print("Here is your unrevealed word: ");
        System.out.println(dashedWord);


        //Guesses continue while numGuesses is less than 6 (total number of guesses)
        while (numGuesses < 6) {

            System.out.println();
            System.out.println("Choose a letter.");
            String let = scan.nextLine();


            //if the letter chosen was already told, then you are prompted to guess again
            while (lettersGuessed.contains(let)) {
                System.out.println("Letter already guessed. Try again.");
                let = scan.nextLine();
            }

            //if the input was not equal to one (not a letter)
            while (let.length() != 1) {
                System.out.println("You must enter in a singular letter. Try again.");
                let = scan.nextLine();
            }


            lettersGuessed.add(let);

            char c = let.charAt(0);


            //if the letter is in the word, then it gets revealed
            if (currentWord.contains(let)) {
                for (int i = 0; i < currentWord.length(); i++) {
                    if (currentWord.charAt(i) == c) {
                        dashedWord.setCharAt(i, c);
                    }
                }
                System.out.println(dashedWord);
            } else {
                numGuesses++;

                System.out.print(dashedWord);
                System.out.println(" - You have " + (6 - numGuesses) + " guesses remaining.");
            }


            int numberUnderscore = 0;

            for (int i = 0; i < currentWord.length(); i++) {
                String currentLetter = String.valueOf(dashedWord.charAt(i));

                if (currentLetter.equals("_")) {
                    numberUnderscore++;
                }
            }


            if (numberUnderscore == 0) {

                score++;
                System.out.println("Correct! The word was " + currentWord);
                System.out.println("Your current number of wins is " + currentNumWins);
                System.out.println("Now moving onto the next word...");
                currentNumWins += 1;

                //Concept: remove word from array
                    /*
                    for(int i = 0; i < wordArr.length; i++){
                        if((wordArr[i].equals(currentWord))){
                            i--;
                        }
                        if(!(wordArr[i].equals(currentWord))){
                            newArr[i] = wordArr[i];
                        }

                    }

                     */

                new Hangman(wordArr);
                return;
            }

        }


        System.out.println("Sorry! You guessed incorrectly. The correct word was " + currentWord + ".");
        System.out.println("Your score for this round was " + currentNumWins);
        System.out.println();

        if (currentNumWins > highestScore) {
            highestScore = currentNumWins;
            System.out.println("Your highest score is " + highestScore);
        }

        System.out.println();
        System.out.println("Would you like to play again?");
        String userDecision = scan.nextLine().toLowerCase();
        if (userDecision.equals("yes")) {
            new Hangman(wordArr);
            return;
        } else if (userDecision.equals("no")) {
            return;
        }

    }


    /**
     * @throws FileNotFoundException
     * @returns file of words
     */
    public static String[] readFile(String fileName) throws FileNotFoundException {

        if (fileName == null) {
            throw new FileNotFoundException();
        }

        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        String[] s;
        if (scan.hasNextLine()) {
            String line = scan.nextLine().trim();
            s = line.split(" ");
        } else {
            scan.close();
            throw new FileNotFoundException();
        }

        return s;
    }


    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("Welcome to Hangman! A submission by Prakash Chatlani, Aadarsh Ganta, and Akshita Ramesh");
        Scanner scan = new Scanner(System.in);

        System.out.println();

        System.out.println("Do you know how to play?");
        String tutorial = scan.nextLine().toLowerCase(Locale.ROOT);
        if (tutorial.equals(("no"))) {
            System.out.println("You will be shown underscores (_) that represent letters. Your job is to guess a letter that could belong to the word. If you are correct, then the letters and their positioning will be revealed. Otherwise, you will be penalized. If you get more than 6 incorrect guesses, then you lose. Good luck!");
        }

        System.out.println();
        System.out.println("Please enter the file name of the list of words you would like to use.");
        String nameofFile = scan.nextLine();
        String[] wordArr = readFile(nameofFile);


        new Hangman(wordArr);
    }
}
