package util;

import java.util.ArrayList;

public class Document {
	
	int unique_words = 0;
	ArrayList<WordToCount> word_map = new ArrayList<WordToCount>();
	int total_words = 0;
	int class_id = -1;
	
	public int getUnique_words() {
		return unique_words;
	}
	public void setUnique_words(int unique_words) {
		this.unique_words = unique_words;
	}
	public ArrayList<WordToCount> getWord_map() {
		return word_map;
	}
	public void setWord_map(ArrayList<WordToCount> word_map) {
		this.word_map = word_map;
	}
	public int getTotal_words() {
		return total_words;
	}
	public void setTotal_words(int total_words) {
		this.total_words = total_words;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	
	
}
