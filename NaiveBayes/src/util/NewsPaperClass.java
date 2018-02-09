package util;

public class NewsPaperClass {
	
	int total_documents = 0;
	long total_word = 0;
	int[] word_count;
	double[] maximum_likelihood;
	double[] bayesian_estimator;
	
	public int getTotal_documents() {
		return total_documents;
	}
	public void setTotal_documents(int total_documents) {
		this.total_documents = total_documents;
	}
	public long getTotal_word() {
		return total_word;
	}
	public void setTotal_word(long total_word) {
		this.total_word = total_word;
	}
	public int[] getWord_count() {
		return word_count;
	}
	public void setWord_count(int[] word_count) {
		this.word_count = word_count;
	}
	public double[] get_Maximum_likelihood() {
		return maximum_likelihood;
	}
	public void setMaximum_likelihood(double[] maximum_likelihood) {
		this.maximum_likelihood = maximum_likelihood;
	}
	public double[] getBayesian_estimator() {
		return bayesian_estimator;
	}
	public void setBayesian_estimator(double[] bayesian_estimator) {
		this.bayesian_estimator = bayesian_estimator;
	}
	
}
