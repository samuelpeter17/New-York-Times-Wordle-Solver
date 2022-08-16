import java.util.*;
import java.io.*;

public class WordleSolver {
    public static HashSet<String> getWords() throws IOException { //function to get all possible words and create a set of words
        BufferedReader br = new BufferedReader(new FileReader("Wordle/WordList.txt"));
        String st = br.readLine();
        HashSet<String> words = new HashSet<String>();
        while (st != null) {
            words.add(st.trim());
            st = br.readLine();
        }
        words.remove("");
        return words;
    }

    public static HashSet<String> remove(HashSet<String> words, String letters) {
        //get letters to remove and remove words with those letters
        letters = letters.replaceAll(" ", "");

        ArrayList<String> wordstoremove = new ArrayList<String>();
        for (String word : words) {
            for (int i = 0; i < letters.length(); i++) {
                char x = letters.charAt(i);
                System.out.println("analyzing " + word + " for " + x);
                if (word.contains(Character.toString(x))) {
                    wordstoremove.add(word);
                    System.out.println("break");
                    break;
                }
            }
        }
        return remover(words, wordstoremove);
    }

    public static HashSet<String> remover(HashSet<String> words, ArrayList<String> wordstoremove) {  //helper function to remove words
        for (int i = 0; i < wordstoremove.size(); i++) {
            words.remove(wordstoremove.get(i));
            System.out.println(wordstoremove.get(i) + " was removed");
        }
        return  words;
    }

    public static HashSet<String> issomewhere(HashSet<String> words) {  //function to respond to yellow tiles
        Scanner sc = new Scanner(System.in);
        ArrayList<String> wordstoremove = new ArrayList<String>();
        for(int i=1;i<6;i++){
            System.out.println("Enter incorrect letter for space "+i+" else enter '.'(fullstop/period)");
            char cl = sc.next().charAt(0);
            if(!(cl=='.')) {
                for (String word : words) {
                    // if the word contains a correct/sontained letter in the incorrect position(yellow tile) remove it
                    if ((word.charAt(i-1) == cl)||(!(word.contains(Character.toString(cl))))){
                        wordstoremove.add(word);
                    }
                }
            }
        }
        if(!wordstoremove.isEmpty()){
            return remover(words, wordstoremove);
        }else{
            return words;
        }
    }

    public static HashSet<String> correct(HashSet<String> words){
        // function that only keeps words with a correct letter in the correct position of the word in the set of words
        Scanner sc = new Scanner(System.in);
        ArrayList<String> wordstoremove = new ArrayList<String>();
        for(int i=1;i<6;i++){
            System.out.println("Enter letter for space "+i+" if correct, else enter '.'(fullstop/period)");
            char cl = sc.next().charAt(0);
            if(!(cl=='.')) {
                for (String word : words) {
                    if (!(word.charAt(i-1) == cl)) {
                        // remove words without correct letter in the correct position
                        wordstoremove.add(word);
                    }
                }
            }
        }
        if(!wordstoremove.isEmpty()){
            return remover(words, wordstoremove);
        }else{
            return words;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        HashSet<String> words = getWords();
        System.out.println(words);
        String control = "n";
        String toremove = "";
        String somewhere = "";
        while (!control.equals("y")) {
            System.out.println("Type letters to remove: ");
            toremove = sc.nextLine().trim();
            words = remove(words, toremove);
            System.out.println("Do you have any letters in incorrect places? (type 'y' or 'n')");
            String c1 = sc.nextLine();
            if (c1.equals("y")) {
                words = issomewhere(words);
            }
            System.out.println("Do you have any letters in correct places? (type 'y' or 'n')");
            String c2 = sc.nextLine();
            if(c2.equals("y")){
                words = correct(words);
            }
            System.out.println(words);
            System.out.println("quit?: (type 'y' or 'n')");
            control = sc.nextLine();
        }
    }
}
