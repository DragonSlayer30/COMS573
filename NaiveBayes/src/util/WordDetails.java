package util;

public class WordDetails {
	
	int wordId = 0;
	int[] classTypeCount = new int[20];
	double[] prior_Class = new double[20];
	int totalCount = 0;
	
	String wordName;

	public WordDetails(int id, String name) {
		setWordId(id);
		setWordName(name);
		setTotalCount(0);
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
		//this.classTypeCount[classId - 1] = classTypeCount;
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

	public double getPrior_Class(int id) {
		return prior_Class[id - 1];
	}

	public void setPrior_Class(int prior_Class_id, double prob ) {
		this.prior_Class[prior_Class_id - 1] = prob;
	}
	
	public void calculatePriors() {
		for(int i = 0; i < 20; i++) {
			if(totalCount != 0) prior_Class[i] = (double)classTypeCount[i]/totalCount; 
			else prior_Class[i] = 0.0; 
		}
	}
	
}
