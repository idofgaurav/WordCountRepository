package com.floow.challenge.wordcount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import com.floow.challenge.wordcount.database.DatabaseUtil;
import com.floow.challenge.wordcount.util.InputOutputUtil;

import java.util.Comparator;

/**
 * The Class WordCountChallenge.
 * 
 * @author Gaurav Singhal
 * 
 * This Challenge will read words from an input file, and count the number of occurrences of each word. 
 *
 */
public class WordCountChallenge {

	private static class WordInformation {
		String word;
		int count;

		WordInformation(String w) {
			word = w;
			count = 1;
		}
	}

	private static class WordCountCompare implements Comparator<WordInformation> {
		public int compare(WordInformation data1, WordInformation data2) {
			return data2.count - data1.count;
		}
	} 

	public static void main(String[] args) {

		System.out.println("\n\n  Please select an input file");
		System.out.print("Press enter to begin.");
		InputOutputUtil.getln(); 

		try {

			if (InputOutputUtil.readSelectedFile() == false) {
				System.out.println("No input file selected. ");
				System.exit(1);
			}

			// Create a TreeMap to hold the data. Read the file and record
			// data in the map about the words that are found in the file.

			TreeMap<String, WordInformation> words = new TreeMap<String, WordInformation>();
			String word = readNextAvailableWord();
			while (word != null) {
				word = word.toLowerCase(); 
				WordInformation data = words.get(word);
				if (data == null)
					words.put(word, new WordInformation(word));
				else
					data.count++;
				word = readNextAvailableWord();
			}

			System.out.println("Number of different words found in file:  " + words.size());
			System.out.println();
			if (words.size() == 0) {
				System.out.println("No words found in file.");
				System.out.println("Exiting without saving data.");
				System.exit(0);
			}

			// Copy the word data into an array list, and sort the list
			// into order of decreasing frequency.

			ArrayList<WordInformation> wordsByFrequency = new ArrayList<WordInformation>(words.values());
			Collections.sort(wordsByFrequency, new WordCountCompare());
			for (WordInformation data : wordsByFrequency){
				//DatabaseUtil.updateDatabaseForWordCount(data.word, data.count); // Storing the information into the database
				System.out.println("Word:" + data.word + " Number Of Occurences:" + data.count);
			}
				
			System.out.println("Thank You!");

		} catch (Exception e) {
			System.out.println("Sorry, an error has occurred.");
			System.out.println("Error Message:  " + e.getMessage());
			e.printStackTrace();
		}
		System.exit(0); 
	}
	
	
	private static String readNextAvailableWord() {
		char ch = InputOutputUtil.peek(); // Look at next character in file.
		while (ch != InputOutputUtil.END_OF_FILE && !Character.isLetter(ch)) {
			InputOutputUtil.getAnyChar(); // Read the character from the file.
			ch = InputOutputUtil.peek(); // Look at the next character if it is existing.
		}
		if (ch == InputOutputUtil.END_OF_FILE) // If Encountered end-of-file then return from here
			return null;
		
		String word = ""; 
		while (true) {
			word += InputOutputUtil.getAnyChar(); 
			ch = InputOutputUtil.peek(); 
			if (ch == '\'') {
				InputOutputUtil.getAnyChar(); 
				ch = InputOutputUtil.peek();
												
				if (Character.isLetter(ch)) {
					word += "\'" + InputOutputUtil.getAnyChar();
					ch = InputOutputUtil.peek(); 
				} else
					break;
			}
			if (!Character.isLetter(ch)) {
				// If the next character is not a letter, the word is
				// finished, so bread out of the loop.
				break;
			}
		}
		return word;
	}

} 


