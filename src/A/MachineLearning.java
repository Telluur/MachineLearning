package A;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Part A main
 */
public class MachineLearning {
    public static final float SMOOTHING = 1;
    public static final float BLOG_SMOOTHING = 0.00001f;
    public static final float EMAIL_SMOOTHING = 0.001f;
    InputReader inputReader = new InputReader();

    public MachineLearning() {
        blog();
        email();
    }

    /**
     * Shows accuracy & statistics for the blogs.
     */
    private void blog() {
        List<String[]> maleTrain = Util.tokenizeString(inputReader.generateAndPopulateList(new File(InputReader.BLOG_LOC + "\\train\\M")));
        List<String[]> femaleTrain = Util.tokenizeString(inputReader.generateAndPopulateList(new File(InputReader.BLOG_LOC + "\\train\\F")));
        List<String[]> maleTest = Util.tokenizeString(inputReader.generateAndPopulateList(new File(InputReader.BLOG_LOC + "\\test\\M")));
        List<String[]> femaleTest = Util.tokenizeString(inputReader.generateAndPopulateList(new File(InputReader.BLOG_LOC + "\\test\\F")));

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

    /**
     * Shows accuracy & statistics for the spammails.
     */
    private void email() {
        //There are no separate train & test sets provided. We divide the material in 90% train and 10% test.
        List<String[]> ham = Util.tokenizeString(inputReader.generateAndPopulateList(new File(InputReader.EMAIL_LOC + "\\ham")));
        List<String[]> spam = Util.tokenizeString(inputReader.generateAndPopulateList(new File(InputReader.EMAIL_LOC + "\\spam")));
        int trainingHam = (int) (ham.size() * 90 / 100f);
        int trainingSpam = (int) (spam.size() * 90 / 100f);
        List<String[]> hamTrain = ham.subList(0, trainingHam);
        List<String[]> spamTrain = spam.subList(0, trainingSpam);
        List<String[]> hamTest = ham.subList(trainingHam, ham.size());
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

    /**
     * Prints (useful) statistics about the accuracy of the two testsets.
     *
     * @param dictionary dictionary with chances for the set1 and set2 respectively
     * @param set1       tokenized set
     * @param set2       tokenized set
     * @param set1Name   actual name of set1
     * @param set2Name   actual name of set2
     */
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
