/**
* Name: Divneet Kaur
* CSE8B login : cs8bwi20im
* Date: 2020 February 27th
*Sources: Lecture notes, Introduction to Java Programming book, Lecture,piazza
*/
/**
*This file is used to make a prediction of the ratings of movie reviews
*based on some learning it did beforehand using some reviews that already had
*ratings associated with them.
*/
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.*;

/**
*This class is able to make a prediction of the ratings of movie reviews
based on some learning it did beforehand using some  reviews that already had
ratings associated with them.
*/
public class RatingPredictor {
  // declaring a hashmap in which you will store a string as your key and
  //an integer array as the value.
  private HashMap<String, int[]> wordFreqMap;
  //This will be the hashset that stores all of the stopwords read from
  //the stopwords.txt file.
  private HashSet<String> stopWords;
  private static char Quote  = '\''; //a static variable
  private static char Hyphen  = '-'; //a static variable
  private static String QuoteHyphen = "-|'"; //a static variable that is
  //used in split method later

  /**
  *public constructor that takes in no constructors and initializes the
  *instance variables (wordFreqMap and stopWords)
  */
  public RatingPredictor() {
    wordFreqMap = new HashMap<String, int[]>();
    stopWords = new HashSet<String>();
  }

  // User Defined Method to print the hashmap
  public void printHashMap() {
    System.out.print(wordFreqMap.keySet());
    for (int[] value : wordFreqMap.values()) {
      System.out.print(value[0] + " " + value[1] + "\n");
    }
  }

  // Methods to Clean The Data
  /**
  *public method that is used to clean the data.
  *This method takes in a String as a parameter.The String will contain the
  *entire contents of one review and this method should split it into words
  *and return an ArrayList of words.
  *@param sentence String that contains contents of one review.
  *@return ArrayList output that contains the splitted words from the
  *input String review into words.
  */
  public ArrayList<String> splitLine (String sentence) {
    //creating a new ArrayList that stores the splitted words
    ArrayList<String> output = new ArrayList<String>();
    //stores the splitted words in a string array
    String[] words = sentence.split(" ");
    //iterates over the string array and add each word to output ArrayList
    for (String word : words){
      output.add(word);
    }
    //returns the ArrayList
    return output;
  }

  /**
  *public method that is used to clean the data.
  *This methods takes in an ArrayList of words and splits the words in the
  *ArrayList at the hyphens and single quotes and return a modified ArrayList
  *of words.
  *@param words ArrayList that contains the splitted words.
  *@return ArrayList output that is a modified ArrayList of words which are
  *splitted at hyphens and single quotes
  */
  public ArrayList<String> splitAtHyphensAndQuotes (ArrayList<String> words) {
    //creating a new ArrayList that stores the splitted words
    ArrayList<String> output = new ArrayList<String>();
    for (String word : words){
      // Split based on - and '
      String[] splitWords = word.split(QuoteHyphen);
      for (String splitWord : splitWords) {
        output.add(splitWord);
      }
  }
    return output;
}

  public ArrayList<String> removePunctuation (ArrayList<String> words) {
    ArrayList<String> output = new ArrayList<String>();
    for (String word : words){
      // Punct includes !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
      word = word.replaceAll("\\p{Punct}","");
      output.add(word);
    }
    return output;
  }

  public ArrayList<String> removeWhiteSpaces (ArrayList<String> words) {
    ArrayList<String> output = new ArrayList<String>();
    for (String word : words){
      word = word.trim();
      output.add(word);
    }
    return output;
  }

  public ArrayList<String> removeEmptyWords (ArrayList<String> words) {
    ArrayList<String> output = new ArrayList<String>();
    for (String word : words) {
      if(!word.isEmpty()) {
        output.add(word);
      }
    }
    return output;
  }

  public ArrayList<String> removeSingleLetterWords (ArrayList<String> words) {
    ArrayList<String> output = new ArrayList<String>();
    for (String word : words) {
      if(word.length() == 1) {
          continue;
      } else {
          output.add(word);
      }
    }
    return output;
  }

  public ArrayList<String> toLowerCase (ArrayList<String> words) {
    ArrayList<String> output = new ArrayList<String>();
    for (String word : words) {
      word = word.toLowerCase();
      output.add(word);
    }
    return output;
  }

  public ArrayList<String> removeStopWords (ArrayList<String> arrList) {
    Scanner sc = null;
    Set<String> stopWord = new HashSet<>();

    try{
      File inputFile = new File("stopwords.txt");
      sc = new Scanner(inputFile);

      String line = "";
      while (sc.hasNext()){
          line = sc.nextLine();
          stopWord.add(line);
        }
    }
    catch (Exception e){
      // Exception prints line numbers and call stack
      e.printStackTrace();
    }
    finally{
      // Make sure to close the stream for scanner and print writer
      if (sc != null){
          sc.close();
      }
    }

    ArrayList<String> output = new ArrayList<String>();
    for (String word : arrList) {
      if(stopWord.contains(word)) {
          continue;
      } else {
          output.add(word);
      }
    }
    return output;
  }

  // Methods to help with the rating prediction task:
  public void createStopWordsSet (String inFile, String outFile) {
    // Create Hash Set from inFile
    Scanner sc = null;
    Set<String> stopWordSet = new HashSet<>();

    try{
      File inputFile = new File(inFile);
      sc = new Scanner(inputFile);

      String line = "";
      while (sc.hasNext()){
        line = sc.nextLine();
        stopWordSet.add(line);
      }
    }
    catch (Exception e){
      // Exception prints line numbers and call stack
      e.printStackTrace();
    }
    finally{
      // Make sure to close the stream for scanner and print writer
      if (sc != null){
          sc.close();
      }
    }

    // Read Hash Set and store to outFile
    PrintWriter pw = null;
    try{
      // Define a new PrintWriter to write output to a file
      File outputFile = new File(outFile);
      pw = new PrintWriter(outputFile);

      for (String stopWord : stopWordSet) {
          pw.println(stopWord);
      }
    }
    catch(final Exception e){
      // Exception prints line numbers and call stack
      e.printStackTrace();
    }
    finally{
      // Make sure to close the stream for scanner and print writer
      if(pw!=null){
          pw.close();
      }
    }
  }

  public void cleanData (String inFile, String outFile, boolean ratingIncluded) {
    // Create Hash Set from inFile
    Scanner sc = null;
    PrintWriter pw = null;
    ArrayList<String> al1 = new ArrayList<>();

    try{
      File inputFile = new File(inFile);
      sc = new Scanner(inputFile);

      File outputFile = new File(outFile);
      pw = new PrintWriter(outputFile);

      if (ratingIncluded == true) {
        String line = "";
        while (sc.hasNext()){
          line = sc.nextLine();
          String [] parsedLine = line.split(" ");
          String rating = parsedLine[0];
          String sentence = "";
          for (int i=1; i< parsedLine.length; i++) {
            sentence = sentence + parsedLine[i] + " ";
          }

          al1 = splitLine(sentence);
          al1 = splitAtHyphensAndQuotes(al1);
          al1 = removePunctuation(al1);
          al1 = removeWhiteSpaces(al1);
          al1 = removeEmptyWords(al1);
          al1 = removeSingleLetterWords(al1);
          al1 = toLowerCase(al1);
          al1 = removeStopWords(al1);
          pw.print(rating + " ");
          for (int i = 0; i < al1.size(); i++){
              pw.print(al1.get(i) + " ");
          }
          pw.print("\n");
          }
      } else {
          String line = "";
          while (sc.hasNext()){
              line = sc.nextLine();
              al1 = splitLine(line);
              al1 = splitAtHyphensAndQuotes(al1);
              al1 = removePunctuation(al1);
              al1 = removeWhiteSpaces(al1);
              al1 = removeEmptyWords(al1);
              al1 = removeSingleLetterWords(al1);
              al1 = toLowerCase(al1);
              al1 = removeStopWords(al1);
              for (int i = 0; i < al1.size(); i++){
                  pw.print(al1.get(i) + " ");
              }
              pw.print("\n");
          }
      }
    }
    catch (Exception e){
        // Exception prints line numbers and call stack
        e.printStackTrace();
    }
    finally{
        // Make sure to close the stream for scanner and print writer
        if (sc != null){
            sc.close();
        }
        if(pw!=null){
            pw.close();
        }
    }
  }

  public void updateHashMap(String inCleanFile) {
      Scanner sc = null;

      try{
          File inputFile = new File(inCleanFile);
          sc = new Scanner(inputFile);

          String line = "";
          while (sc.hasNext()){
              line = sc.nextLine();
              String [] parsedLine = line.split(" ");
              String rating = parsedLine[0];
              int ratingInt = Integer.parseInt(rating);

              // Loop over the words
              for (int i=1; i< parsedLine.length; i++) {
                  if (wordFreqMap.containsKey(parsedLine[i])) {
                      int[] valPresent = wordFreqMap.get(parsedLine[i]); // (jungle = [5,2])-> [9,3]
                      int prevRatingCount = valPresent[0]; // 5
                      int prevWordCount = valPresent[1]; // 2

                      wordFreqMap.get(parsedLine[i])[0] = prevRatingCount + ratingInt;
                      wordFreqMap.get(parsedLine[i])[1] = prevWordCount + 1;

                  } else {
                      int[] inputToHashMap = new int[2];
                      inputToHashMap[0] = ratingInt;
                      inputToHashMap[1] = 1;

                      wordFreqMap.put(parsedLine[i], inputToHashMap);
                  }
              }
          }
      }
      catch (Exception e){
          // Exception prints line numbers and call stack
          e.printStackTrace();
      }
      finally{
          // Make sure to close the stream for scanner and print writer
          if (sc != null){
              sc.close();
          }
      }
  }

  public void rateReviews (String inCleanFile, String outRatingsFile) {
      Scanner sc = null;
      PrintWriter pw = null;

      try{
          File inputFile = new File(inCleanFile);
          sc = new Scanner(inputFile);

          File outputFile = new File(outRatingsFile);
          pw = new PrintWriter(outputFile);

          String line = "";
          while (sc.hasNext()){
              line = sc.nextLine();
              String [] parsedLine = line.split(" ");

              // Loop over the words
              float sum = 0;
              int num_words = parsedLine.length;
              for (int i=0; i< parsedLine.length; i++) {
                  if (wordFreqMap.containsKey(parsedLine[i])) {
                      int[] valPresent = wordFreqMap.get(parsedLine[i]);
                      int rating = valPresent[0]; // 5
                      int word = valPresent[1]; // 2
                      float val = (float) rating / word;
                      sum += val;
                  } else {
                      sum += 2.0;
                  }
              }

              // Using this link : https://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
              int scale = (int) Math.pow(10, 1);
              double finalRating = (double) Math.round(sum * scale / num_words) / scale;
              pw.println(finalRating);
          }
      }
      catch (Exception e){
          // Exception prints line numbers and call stack
          e.printStackTrace();
      }
      finally{
          // Make sure to close the stream for scanner and print writer
          if (sc != null){
              sc.close();
          }
          if(pw!=null){
              pw.close();
          }
      }
  }

  public static void main(String[] args) {
      RatingPredictor rp = new RatingPredictor();

      // Test splitLine
      // System.out.print(rp.splitLine("i am sartaj!"));

      // Test splitAtHyphensAndQuotes
      ArrayList<String> input = new ArrayList<String>();
      input.add("The");
      input.add("Jungle-Book");
      input.add("It's");
      // System.out.print(rp.splitAtHyphensAndQuotes(input));

      // Test removePunctuation
      ArrayList<String> input2 = new ArrayList<String>();
      input2.add("The");
      input2.add("Jungle-Book");
      input2.add("movie!");
      // System.out.print(rp.removePunctuation(input2));

      // Test removeWhiteSpaces
      ArrayList<String> input3 = new ArrayList<String>();
      input3.add("  The");
      input3.add("Jungle  ");
      input3.add("movie");
      // System.out.print(rp.removeWhiteSpaces(input3));

      // Test removeEmptyWords
      ArrayList<String> input4 = new ArrayList<String>();
      input4.add("The");
      input4.add("");
      input4.add("");
      // System.out.print(rp.removeEmptyWords(input4));

      // Test removeSingleLetterWords
      ArrayList<String> input5 = new ArrayList<String>();
      input5.add("A");
      input5.add("B");
      input5.add("1");
      input5.add("Sartaj");
      // System.out.print(rp.removeSingleLetterWords(input5));

      // Test toLowerCase
      ArrayList<String> input6 = new ArrayList<String>();
      input6.add("The");
      input6.add("Jungle");
      input6.add("movie");
      // System.out.print(rp.toLowerCase(input6));

      // Test removeStopWords
      ArrayList<String> arrList = new ArrayList<String>();
      arrList.add("the");
      arrList.add("jungle");
      arrList.add("book");
      // System.out.print(rp.removeStopWords(arrList));

      // Create Stop Words Set
      rp.createStopWordsSet ("stopwords.txt", "uniqueStopwords.txt");

      // Clean Data for all 4 files
      rp.cleanData("rawReviewRatings.txt", "cleanReviewRatings.txt", true);
      rp.cleanData("rawReviews.txt", "cleanReviews.txt", false);
      rp.cleanData("rawReviewRatingsBig.txt", "cleanReviewRatingsBig.txt", true);
      rp.cleanData("rawReviewsBig.txt", "cleanReviewsBig.txt", false);

      // Update Hash Map for small file and rate reviews
      rp.updateHashMap("cleanReviewRatings.txt");
      rp.rateReviews("cleanReviews.txt","ratings.txt");

      // Update Hash Map for large file and rate big reviews
      rp.updateHashMap("cleanReviewRatingsBig.txt");
      rp.rateReviews("cleanReviewsBig.txt","ratingsBig.txt");
  }
}
