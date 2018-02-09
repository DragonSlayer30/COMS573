package app;

import bayes.BayesAlgo;
import bayes.BayesCore;

public class NaiveBayes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Boolean debug = true;
		/*
		BayesCore bayesCore = new BayesCore(args[0], args[1], args[2], args[3], args[4], args[5], debug);
		bayesCore.startMagic();
		*/
		BayesAlgo bayesCore = new BayesAlgo(args[0], args[1], args[2], args[3], args[4], args[5], debug);
		bayesCore.startMagic();
		
	}

}
