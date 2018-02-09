package bayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import util.Document;
import util.Document_skeleton;
import util.FileUtil;
import util.NewsPaperClass;
import util.WordToCount;

public class BayesAlgo {

	String training_data_file;
	String training_label_file;
	String vocabulary_file;
	String map_file;
	String testing_label_file;
	String testing_data_file;
	boolean debug = false;
	int totalWordCount = 0; 
	int vocabulary_count = 0;
	long total_word_with_repetitions = 0;
	
	FileUtil fileUtil = new FileUtil();

	String[] class_id_map = new String[20]; 
	
	int[] document_id__to_class_map;
	int[] class_to_document_count = new int[20];
	double[] class_prior = new double[20];
	ArrayList<Document_skeleton> allDocuments = new ArrayList<Document_skeleton>();
	NewsPaperClass[] news_paper = new NewsPaperClass[20];

	public BayesAlgo(String vocabulary, String map, String label, String data, String test_label, String test_data, boolean print) {
		map_file = map;
		testing_data_file = test_data;
		testing_label_file = test_label;
		training_data_file = data;
		training_label_file = label;
		vocabulary_file = vocabulary;
		debug = print;
	}

	public void startMagic() { 
		
		// Setting up class id to name map
		ArrayList<String> idDocMap = fileUtil.readFile(map_file);
		int length = idDocMap.size();
		for(int i = 0; i < length; i++) {
			class_id_map[i] = idDocMap.get(i).split(",")[1];
		}
		idDocMap.clear();
		/*
		for (int i = 0; i < 20 ; i++) {
			if(debug) System.out.println("id " + i + " " + document_id_map[i]);
		}
		*/
		
		// setting up document id to class map
		ArrayList<String> train_label = fileUtil.readFile(training_label_file);
		length = train_label.size();
		document_id__to_class_map = new int[length];
		for(int i = 0; i < length; i++) {
			document_id__to_class_map[i] = Integer.parseInt(train_label.get(i));
			class_to_document_count[document_id__to_class_map[i] - 1] = class_to_document_count[document_id__to_class_map[i] - 1] + 1;
		}
		//if(debug) System.out.println("Total documents from file : " + length);
		
		// Feeding train data
		ArrayList<String> train_data = fileUtil.readFile(training_data_file);
		length = train_data.size();
		for(int i = 0; i < length; i++) {
			String[] trainLine = train_data.get(i).split(",");
			int id = Integer.parseInt(trainLine[1]);
			int docNum = Integer.parseInt(trainLine[0]);
			int count = Integer.parseInt(trainLine[2]);
			WordToCount wordToCount = new WordToCount(id, count);
			if(docNum > allDocuments.size()) {
				Document_skeleton document_skeleton = new Document_skeleton();
				document_skeleton.setClass_id(document_id__to_class_map[docNum - 1]);
				document_skeleton.getWord_map().add(wordToCount);
				allDocuments.add(document_skeleton);
			}
			else {
				allDocuments.get(docNum - 1).getWord_map().add(wordToCount);
			}
		}
		//if(debug) System.out.println("Total documents : " + allDocuments.size());
		int total_documents = allDocuments.size();
		// prior for classes 
		for(int i = 0; i < 20; i++) {
			class_prior[i] = (double)class_to_document_count[i]/total_documents;
			//if(debug) System.out.println("Prior for class " + i + " " + (double)class_to_document_count[i]/allDocuments.size()); 
		}
		
		// process documents
		for(int i = 0; i < total_documents; i++) {
			int total_in_doc = 0;
			Document_skeleton skeleton = allDocuments.get(i);
			for(WordToCount wc : skeleton.getWord_map()) {
				total_in_doc = total_in_doc + wc.getCount();
			}
			skeleton.setTotal_words(total_in_doc);
			skeleton.setUnique_words(skeleton.getWord_map().size());
		}
		
		total_word_with_repetitions = 0;
		for(int i = 0; i < total_documents; i++) {
			total_word_with_repetitions = total_word_with_repetitions + allDocuments.get(i).getTotal_words();
		}
		//if(debug) System.out.println("Total words with rep : " + total_word_with_repetitions); 
		
		
		
		// process class type files
		ArrayList<String> all_words_list = fileUtil.readFile(vocabulary_file);
		vocabulary_count = all_words_list.size();
		for(int i = 0; i < 20; i++) {
			news_paper[i] = new NewsPaperClass();
			news_paper[i].setWord_count(new int[vocabulary_count]);
			news_paper[i].setMaximum_likelihood(new double[vocabulary_count]);
			news_paper[i].setBayesian_estimator(new double[vocabulary_count]);
			news_paper[i].setTotal_documents(class_to_document_count[i]);
		}
		for(int i = 0; i < total_documents; i++) {
			Document_skeleton skeleton = allDocuments.get(i);
			int class_id = skeleton.getClass_id();
			NewsPaperClass paperClass = news_paper[class_id - 1];
			paperClass.setTotal_word(paperClass.getTotal_word() + skeleton.getTotal_words());
			for(WordToCount wc : skeleton.getWord_map()) {
				int count = wc.getCount() + paperClass.getWord_count()[wc.getWord_id() - 1];
				paperClass.getWord_count()[wc.getWord_id() - 1] = count;
			}
		}
		/*
		long verify_total_word_count = 0;
		for(int i = 0; i < 20; i++) {
			if(debug) System.out.println("Total words in " + (i + 1) + " " + news_paper[i].getTotal_word());
			verify_total_word_count = verify_total_word_count + news_paper[i].getTotal_word();
		}
		if(debug) System.out.println("Total count in all documents : " + verify_total_word_count);
		*/
		maximumLikelihoodEstimate();
		bayesian_estimator();
	}
	
	public void maximumLikelihoodEstimate() {
		for(int i = 0; i < 20; i++) {
			NewsPaperClass paperClass = news_paper[i];
			long total_words = paperClass.getTotal_word();
			int[] wordCount = paperClass.getWord_count();
			double[] mle = paperClass.getMaximum_likelihood();
			for(int j = 0; j < vocabulary_count; j++) {
				mle[j] = (double)wordCount[j]/total_words;
				//if(debug) System.out.println("MLE for " + (j) + " for " + (i + 1) + " " + mle[j]);
			}
		}
	}
	
	public void bayesian_estimator() {
		for(int i = 0; i < 20; i++) {
			NewsPaperClass paperClass = news_paper[i];
			long total_words = paperClass.getTotal_word();
			int[] wordCount = paperClass.getWord_count();
			double[] byestimator = paperClass.getBayesian_estimator();
			for(int j = 0; j < vocabulary_count; j++) {
				byestimator[j] = (double)(wordCount[j] + 1)/(total_words + vocabulary_count);
				if(debug) System.out.println("BYE for " + (j) + " for " + (i + 1) + " " + byestimator[j]);
			}
		}
	}
}
