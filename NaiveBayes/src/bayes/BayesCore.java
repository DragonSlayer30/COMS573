package bayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import util.Document;
import util.FileUtil;
import util.WordDetails;

public class BayesCore {
	
	String training_data_file;
	String training_label_file;
	String vocabulary_file;
	String map_file;
	String testing_label_file;
	String testing_data_file;
	boolean debug = false;
	int totalWordCount = 0; 
	
	FileUtil fileUtil = new FileUtil();
	
	Document newsDocument;
	
	String[] document_id_map = new String[20]; 
	HashMap<String, HashSet<String>> document_word_list = new HashMap<String, HashSet<String>>(); 
	HashMap<String, HashSet<String>> class_word_list = new HashMap<String, HashSet<String>>();
 	int[] document_to_Class;
 	double[] document_prior;
 	int[] type_document_count;
	
	public BayesCore(String vocabulary, String map, String label, String data, String test_label, String test_data, boolean print) {
		map_file = map;
		testing_data_file = test_data;
		testing_label_file = test_label;
		training_data_file = data;
		training_label_file = label;
		vocabulary_file = vocabulary;
		debug = print;
	}
	
	public void startMagic() {
		
		// Setting up document template
		ArrayList<String> all_words_list = fileUtil.readFile(vocabulary_file);
		if(debug) System.out.println("Total Words Count : " + all_words_list.size());
		int length = all_words_list.size();
		newsDocument = new Document(all_words_list);
		if(debug) System.out.println("Total words in Document : " + newsDocument.getWords().length);
		/*
		for(int i = 0; i < length; i++) {
			if(debug) System.out.println("Word id " + i + " : " + newsDocument.getWords()[i].getWordName());
		}
		*/
		all_words_list.clear();
		
		// Setting up document id map
		ArrayList<String> idDocMap = fileUtil.readFile(map_file);
		length = idDocMap.size();
		for(int i = 0; i < length; i++) {
			document_id_map[i] = idDocMap.get(i).split(",")[1];
		}
		idDocMap.clear();
		/*
		for(int i = 0; i < length; i++) {
			if(debug) System.out.println(document_id_map[i]);
		}
		*/
		
		// Setting up train Data
		ArrayList<String> train_label = fileUtil.readFile(training_label_file);
		length = train_label.size();
		document_to_Class = new int[length];
		type_document_count = new int[20];
		for(int i = 0; i < length; i++) {
			document_to_Class[i] = Integer.parseInt(train_label.get(i));
			type_document_count[document_to_Class[i] - 1] = type_document_count[document_to_Class[i] - 1] + 1;
		}
		document_prior = new double[20];
		double prior_Sum_check = 0.0;
		for(int i = 0; i < 20; i++) {
			document_prior[i] = (double)type_document_count[i]/length;
			prior_Sum_check = prior_Sum_check + document_prior[i];
			//if(debug)  System.out.println("Prior for document type " + (i + 1) + " " + document_prior[i]);
		}
		//if(debug) System.out.println("Prior Sum Check : " + prior_Sum_check);
		train_label.clear();
		
		// Feeding train data
		ArrayList<String> train_data = fileUtil.readFile(training_data_file);
		length = train_data.size();
		if(debug) System.out.println("Total Train Data : " + length);
		for(int i = 0; i < length; i++) {
			String[] trainLine = train_data.get(i).split(",");
			int id = Integer.parseInt(trainLine[1]);
			int docNum = Integer.parseInt(trainLine[0]) - 1;
			int count = Integer.parseInt(trainLine[2]);
			newsDocument.getWords()[id - 1].setClassTypeCount(document_to_Class[docNum], count);
			if(document_word_list.get(trainLine[0]) == null) {
				document_word_list.put(trainLine[0], new HashSet<String>());
				document_word_list.get(trainLine[0]).add(trainLine[1]);
			}
			else document_word_list.get(trainLine[0]).add(trainLine[1]);
			if(class_word_list.get(document_to_Class[docNum] + "") == null) {
				class_word_list.put(document_to_Class[docNum] + "", new HashSet<String>());
				class_word_list.get(document_to_Class[docNum] + "").add(trainLine[1]);
			}
			else class_word_list.get(document_to_Class[docNum] + "").add(trainLine[1]);
			//if(debug) System.out.println("train line : " + newsDocument.getWords()[id].getWordName() + " " + i);
		}
		train_data.clear();
		
		length = newsDocument.getWords().length;
		// Calculate Priors
		for(int i = 0; i < length; i++) {
			newsDocument.getWords()[i].calculatePriors(); 
			double sumCheck = 0.0;
			if(i == 0) {
				for(int j = 0; j < 20; j++) {
					sumCheck = sumCheck + newsDocument.getWords()[i].getPrior_Class(j + 1);
					if(debug) System.out.println(document_id_map[j] + " " + newsDocument.getWords()[i].getWordName() + " Prior : " + newsDocument.getWords()[i].getPrior_Class(j + 1));
				}
				if(debug) System.out.println("Total Prior Sum : " + sumCheck);
			}
			
		}
		
		// testing train data 
		int i = 0;
		for (String string : document_word_list.keySet()) {
			i = i + document_word_list.get(string).size();
			//if(debug) System.out.println("Total words in document " + string + " " + document_word_list.get(string).size());
		}
		//if(debug) System.out.println("Total words in all documents with repetetions : " + i);
		i = 0;
		for (String string : class_word_list.keySet()) {
			i = i + class_word_list.get(string).size();
			//if(debug) System.out.println("Total words in class " + string + " " + class_word_list.get(string).size());
		}
		//if(debug) System.out.println("Total words in all classes with repetetions : " + i);
		/* 
		double sumCheck = 0.0;
		i = 61187;
		for(int j = 0; j < 20; j++) {
			sumCheck = sumCheck + newsDocument.getWords()[i].getPrior_Class(j + 1);
			if(!debug) System.out.println(document_id_map[j] + " " + newsDocument.getWords()[i].getWordName() + "Total word Count "+ newsDocument.getWords()[i].getTotalCount() + " Calculation " + (double)newsDocument.getWords()[i].getClassTypeCount(j + 1)/newsDocument.getWords()[i].getTotalCount() + " Prior : " + newsDocument.getWords()[i].getPrior_Class(j + 1));
		}
		if(!debug) System.out.println("Total Prior Sum : " + sumCheck);
		*/
		/*
		WordDetails wordDetails = newsDocument.getWords()[0];
		if(debug) System.out.println("Word Id : " + wordDetails.getWordId() + " Name " + wordDetails.getWordName() + " Total Count : " + wordDetails.getTotalCount());
		for(i = 0; i < 20; i++) {
			if(debug) System.out.println("Word Count in Class type " + (i + 1) + " " + wordDetails.getClassTypeCount(i + 1) + " Priority : " + (wordDetails.getClassTypeCount(i + 1)/wordDetails.getTotalCount())); 
		}
		if(!debug) System.out.println("prior : " + ((double)wordDetails.getClassTypeCount(1)/wordDetails.getTotalCount())); 
		*/
		
	}
	
}
