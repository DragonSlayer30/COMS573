package util;

public class WordDetails {
	
	int wordId = 0;
	int[] classTypeCount = new int[20];
	int totalCount = 0;
	
	String wordName;

	public WordDetails(int id, String name) {
		setWordId(id);
		setWordName(name);
	}
	
	public int getWordId() {
		return wordId;
	}

	public void setWordId(int wordId) {
		this.wordId = wordId;
	}

	public int getClassTypeCount(int documentId) {
		return classTypeCount[documentId - 1];
	}

	public void setClassTypeCount(int classId, int classTypeCount) {
		this.classTypeCount[classId - 1] = classTypeCount;
		incrementClassCount(classId, classTypeCount);
		totalCount = totalCount + classTypeCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	
	public void incrementClassCount(int classType, int increment) {
		classTypeCount[classType - 1] = classTypeCount[classType - 1] + increment;
	}
	
	public void decrementClassCount(int classType, int decrement) {
		classTypeCount[classType - 1] = classTypeCount[classType - 1] + decrement;
	}
}
