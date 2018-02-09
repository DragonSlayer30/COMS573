package util;

public class WordToCount {
	
	int word_id;
	int count;
	
	public WordToCount(int key, int value) {
		word_id = key;
		count = value;
	}

	public int getWord_id() {
		return word_id;
	}

	public void setWord_id(int word_id) {
		this.word_id = word_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
