package bayes;

import java.util.ArrayList;

import util.Document;
import util.FileUtil;

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
		
		ArrayList<String> all_words_list = fileUtil.readFile(vocabulary_file);
		if(debug) System.out.println("Total Words Count : " + all_words_list.size());
		
		
	}
	
}
