package A;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MachineLearning {
    public static final float SMOOTHING = 1;
    public static final float BLOG_SMOOTHING = 0.00001f;
    public static final float EMAIL_SMOOTHING = 0.01f;
    InputReader inputReader = new InputReader();

    public MachineLearning() {
        blog();
        email();
    }

    private void blog() {
        List<String[]> maleTrain = Util.tokenizeString(inputReader.populateList(new File(InputReader.BLOG_LOC + "\\train\\M"), new ArrayList<String>()));
        List<String[]> femaleTrain = Util.tokenizeString(inputReader.populateList(new File(InputReader.BLOG_LOC + "\\train\\F"), new ArrayList<String>()));
        List<String[]> maleTest = Util.tokenizeString(inputReader.populateList(new File(InputReader.BLOG_LOC + "\\test\\M"), new ArrayList<String>()));
        List<String[]> femaleTest = Util.tokenizeString(inputReader.populateList(new File(InputReader.BLOG_LOC + "\\test\\F"), new ArrayList<String>()));

        Map<String, WordChances> dictionary = Util.calculateChances(Util.createDictionary(maleTrain, femaleTrain), SMOOTHING);
        Map<String, WordChances> dictionaryCustom = Util.calculateChances(Util.createDictionary(maleTrain, femaleTrain), BLOG_SMOOTHING);

        System.out.println("\n\n############################## Blogs ##############################");
        System.out.printf("####################### Smoothing: %f #######################\n", SMOOTHING);
        System.out.println("\nPredicting the 'TRAIN' set.");
        determineAccuracy(dictionary, maleTrain, femaleTrain, "male", "female");
        System.out.println("\nPredicting the 'TEST' set");
        determineAccuracy(dictionary, maleTest, femaleTest, "male", "female");
        System.out.println("\n\n############################## Blogs ##############################");
        System.out.printf("####################### Smoothing: %f #######################\n", BLOG_SMOOTHING);
        System.out.println("\nPredicting the 'TRAIN' set.");
        determineAccuracy(dictionaryCustom, maleTrain, femaleTrain, "male", "female");
        System.out.println("\nPredicting the 'TEST' set");
        determineAccuracy(dictionaryCustom, maleTest, femaleTest, "male", "female");
    }

    private void email(){
        //There are no seperate train & test sets provided. We divide the material provided in 90% train and 10% test.
        List<String[]> ham = Util.tokenizeString(inputReader.populateList(new File(InputReader.EMAIL_LOC + "\\ham"), new ArrayList<String>()));
        List<String[]> spam = Util.tokenizeString(inputReader.populateList(new File(InputReader.EMAIL_LOC + "\\spam"), new ArrayList<String>()));
        int trainingHam = (int) (ham.size() * 90 / 100f);
        int trainingSpam = (int) (spam.size() * 90 / 100f);
        List<String[]> hamTrain =  ham.subList(0, trainingHam);
        List<String[]> spamTrain =  spam.subList(0, trainingSpam);
        List<String[]> hamTest =  ham.subList(trainingHam, ham.size());
        List<String[]> spamTest = spam.subList(trainingSpam, spam.size());

        Map<String, WordChances> dictionary = Util.calculateChances(Util.createDictionary(hamTrain, spamTrain), SMOOTHING);
        Map<String, WordChances> dictionaryCustom = Util.calculateChances(Util.createDictionary(hamTrain, spamTrain), EMAIL_SMOOTHING);

        System.out.println("\n\n############################## Email ##############################");
        System.out.printf("####################### Smoothing: %f #######################\n", SMOOTHING);
        System.out.println("\nPredicting the 'TRAIN' set.");
        determineAccuracy(dictionary, hamTrain, spamTrain, "ham", "spam");
        System.out.println("\nPredicting the 'TEST' set");
        determineAccuracy(dictionary, hamTest, spamTest, "ham", "spam");
        System.out.println("\n\n############################## Email ##############################");
        System.out.printf("####################### Smoothing: %f #######################\n", EMAIL_SMOOTHING);
        System.out.println("\nPredicting the 'TRAIN' set.");
        determineAccuracy(dictionaryCustom, hamTrain, spamTrain, "ham", "spam");
        System.out.println("\nPredicting the 'TEST' set");
        determineAccuracy(dictionaryCustom, hamTest, spamTest, "ham", "spam");



    }

    private void determineAccuracy(Map<String, WordChances> dictionary, List<String[]> set1, List<String[]> set2, String set1Name, String set2Name) {
        int set1Hits = 0;
        int set2Hits = 0;

        for (String[] set1Entry : set1) {
            String result = Util.determineType(set1Entry, dictionary, set1Name, set2Name);
            if (result.equals(set1Name)) {
                set1Hits++;
            }
        }

        for (String[] set2Entry : set2) {
            String result = Util.determineType(set2Entry, dictionary, set1Name, set2Name);
            if (result.equals(set2Name)) {
                set2Hits++;
            }
        }
        int set1Size = set1.size();
        int set2Size = set2.size();
        float set1Accuracy = (set1Hits * 100f) / set1Size;
        float set2Accuracy = (set2Hits * 100f) / set2Size;
        System.out.println("Results of '" + set1Name + "': " + set1Hits + "/" + set1Size + " correct predictions.("
                + String.format("%.02f", set1Accuracy) + "%)");
        System.out.println("Results of '" + set2Name + "': " + set2Hits + "/" + set2Size + " correct predictions.("
                + String.format("%.02f", set2Accuracy) + "%)");
        System.out.println("Accuracy: " + (set1Hits + set2Hits) + "/" + (set1Size + set2Size) + " correct predictions.("
                + String.format("%.02f", (float) (((set1Hits + set2Hits) * 100f) / (set1Size + set2Size))) + "%)");
    }

    public static void main(String args[]) {
        new MachineLearning();
    }

}