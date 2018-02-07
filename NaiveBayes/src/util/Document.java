package util;

import java.util.ArrayList;

public class Document {
	
	WordDetails[] words;
	int totalCoount = 0;
	
	public Document(ArrayList<String> allWords) {
		WordDetails[] wordsTemp = new WordDetails[allWords.size()];
		int index = 0;
		for (String string : allWords) {
			wordsTemp[index] = new WordDetails(index + 1, string);
			index++;
		}
		setWords(wordsTemp);
	}
	
	public WordDetails[] getWords() {
		return words;
	}
	
	public void setWords(WordDetails[] words) {
		this.words = words;
	}
	
	public int getTotalCoount() {
		return totalCoount;
	}
	
	public void setTotalCoount(int totalCoount) {
		this.totalCoount = totalCoount;
	}
	
	public void increaseTotalCount(int increment) {
		totalCoount = totalCoount + increment;
	}
	
	public void decreaseTotalCount(int decrement) {
		totalCoount = totalCoount + decrement;
	}
}
