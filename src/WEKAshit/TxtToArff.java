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

import sun.font.CreatedFontTracker;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.bayes.BayesianLogisticRegression;
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

public class TxtToArff {


	public static String corpusFolderPath = System.getProperty("user.dir");
	public static String trainPath = corpusFolderPath + "\\train";
	public static String testPath = corpusFolderPath + "\\test";


	public static void main(String[] args) throws Exception {
		TxtToArff txttoarff = new TxtToArff();
		Instances train = txttoarff.createArrf(trainPath);
		Instances test = txttoarff.createArrf(testPath);
		StringToWordVector stw = txttoarff.createSTW(train);
		train = txttoarff.filterData(train, stw);
		test = txttoarff.filterData(test, stw);
		txttoarff.NBC(train, test);
		Instances spamham = txttoarff.createArrf(corpusFolderPath + "\\spammail");
		Instances[] spamhamarray = txttoarff.splitSet(spamham, 90);
		Instances train2 = spamhamarray[0];
		Instances test2 = spamhamarray[1];
		StringToWordVector stw2 = txttoarff.createSTW(train2);
		train2 = txttoarff.filterData(train2, stw2);
		test2 = txttoarff.filterData(test2, stw2);
		txttoarff.LR(train2, test2);
	}
	

	private void writeARFF(Instances data, String fileName) {
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
	
	private Instances[] splitSet(Instances data, int percent) {
		int trainSize = (int) Math.round(data.numInstances() * percent
			    / 100);
			int testSize = data.numInstances() - trainSize;
			Instances train = new Instances(data, 0, trainSize);
			Instances test = new Instances(data, trainSize, testSize);
			Instances[] result = new Instances[]{train,test};
			return result;
	}
	
	private Instances createArrf(String path) {
		Instances train = null;
		TextDirectoryLoader test = new TextDirectoryLoader();
		try {
			test.setDirectory(new File(path));
			train = test.getDataSet();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return train;
	}
	
	private StringToWordVector createSTW(Instances data) {
		 StringToWordVector stwfilter = new StringToWordVector();
		 String[] options = new String[7];
		 options[6] = "-C";
		 options[0] = "-I";
		 options[1] = "-R 1,2,3";
		 options[2] = "-O";
		 options[3] = "-T";
		 options[4] = "-N 0";
		 options[5] = "-M 1";
		
		 try {
			stwfilter.setInputFormat(data);
			stwfilter.setOptions(options);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return stwfilter;
		 
	}
	
	private Instances filterData (Instances data, Filter filter) {
		Instances dataFiltered = null;
		try {
			dataFiltered = Filter.useFilter(data, filter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataFiltered;
	}
	
	private void NBC (Instances trainData, Instances testData) {
		Classifier cModel = (Classifier)new NaiveBayes();
		 try {
			cModel.buildClassifier(trainData);
			Evaluation eTest = new Evaluation(trainData);
			 eTest.evaluateModel(cModel, testData);
			System.out.println(eTest.toSummaryString());
			System.out.println(eTest.toClassDetailsString());
			System.out.println(eTest.toMatrixString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	private void LR(Instances trainData, Instances testData) {
		Classifier cModel = (Classifier)new BayesianLogisticRegression();
		 try {
			cModel.buildClassifier(trainData);
			Evaluation eTest = new Evaluation(trainData);
			 eTest.evaluateModel(cModel, testData);
			System.out.println(eTest.toSummaryString());
			System.out.println(eTest.toClassDetailsString());
			System.out.println(eTest.toMatrixString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	private void J48(Instances trainData, Instances testData) {
		Classifier cModel = (Classifier)new J48();
		 try {
			cModel.buildClassifier(trainData);
			Evaluation eTest = new Evaluation(trainData);
			 eTest.evaluateModel(cModel, testData);
			System.out.println(eTest.toSummaryString());
			System.out.println(eTest.toClassDetailsString());
			System.out.println(eTest.toMatrixString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	
}