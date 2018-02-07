package util;

public class Document {
	
	WordDetails[] words;
	int totalCoount = 0;
	
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
