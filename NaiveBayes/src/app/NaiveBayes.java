package app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import bayes.BayesAlgo;

public class NaiveBayes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Boolean debug = true;
		/*
		BayesCore bayesCore = new BayesCore(args[0], args[1], args[2], args[3], args[4], args[5], debug);
		bayesCore.startMagic();
		*/
		/*
		try {
			System.setOut(new PrintStream(new FileOutputStream("output.txt")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		BayesAlgo bayesCore = new BayesAlgo(args[0], args[1], args[2], args[3], args[4], args[5], debug);
		bayesCore.startMagic();
		
	}

}
