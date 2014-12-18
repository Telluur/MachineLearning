package WEKAshit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Scanner;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesSimple;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * @author Derk Snijders
 * @date 1-12-2014
 */

public class spamtoarrf {


	public static String corpusFolderPath = System.getProperty("user.dir");
	public static String trainPath = corpusFolderPath + "\\spammail";
	public static String testPath = corpusFolderPath + "\\test";


	public static void main(String[] args) throws Exception {
		TextDirectoryLoader test = new TextDirectoryLoader();
		test.setDirectory(new File(trainPath));
		Instances train = test.getDataSet();  
		writeARFF(train, "testtrain.arff");
	
		 StringToWordVector stwfilter = new StringToWordVector();
		 String[] options = new String[7];
		 options[6] = "-C";
		 options[0] = "-I";
		 options[1] = "-R 1,2,3";
		 options[2] = "-O";
		 options[3] = "-T";
		 options[4] = "-N 0";
		 options[5] = "-M 1";
		 System.out.println(train.numInstances());
		 System.out.println(train.numInstances());
		stwfilter.setOptions(options);
		 stwfilter.setInputFormat(train);
		Instances dataFiltered = Filter.useFilter(train, stwfilter);
		writeARFF(dataFiltered, "spammail_generated_self.arff");
		 

	}

	private static void writeARFF(Instances data, String fileName) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			writer.print(data);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}