import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MachineLearning {

    public MachineLearning() {
        InputReader inputReader = new InputReader();

        //Read and tokenize all train files
        List<String[]> maleTokens = Util.parseString(inputReader.populateList(new File(InputReader.TRAIN_LOC + "\\M"), new ArrayList<String>()));
        List<String[]> femaleTokens = Util.parseString(inputReader.populateList(new File(InputReader.TRAIN_LOC + "\\F"), new ArrayList<String>()));

        //Create chances
        Chance maleChance = new Chance(maleTokens);
        Chance femaleChance = new Chance(femaleTokens);

        //Read and tokenize test files
        List<String[]> maleTest = Util.parseString(inputReader.populateList(new File(InputReader.TEST_LOC + "\\M"), new ArrayList<String>()));
        List<String[]> femaleTest = Util.parseString(inputReader.populateList(new File(InputReader.TEST_LOC + "\\F"), new ArrayList<String>()));


        System.out.println("\nPredicting the 'TRAIN' set.");
        int maleTrainHits = testSet(maleTokens, maleChance, femaleChance, "Male");
        int femaTrainHits = testSet(femaleTokens, maleChance, femaleChance, "Female");
        System.out.println("Accuracy: "
                + (maleTrainHits + femaTrainHits)
                + "/"
                + (maleTokens.size() + femaleTokens.size())
                + " correct predictions. ("
                + String.format("%.02f", (float) (((maleTrainHits + femaTrainHits) * 100f) / (maleTokens.size() + femaleTokens.size())))
                + "%)");

        System.out.println("\nPredicting the 'TEST' set.");
        int maleTestHits = testSet(maleTest, maleChance, femaleChance, "Male");
        int femaTestHits = testSet(femaleTest, maleChance, femaleChance, "Female");
        System.out.println("Accuracy: "
                + (maleTestHits + femaTestHits)
                + "/"
                + (maleTest.size() + femaleTest.size())
                + " correct predictions. ("
                + String.format("%.02f", (float) (((maleTestHits + femaTestHits) * 100f) / (maleTest.size() + femaleTest.size())))
                + "%)");
    }

    private int testSet(List<String[]> dataset, Chance maleChance, Chance femaleChance, String expected) {
        int hits = 0;
        for (String[] entry : dataset) {
            String result = Util.determineType(entry, maleChance, femaleChance);
            if (result.equals(expected)) {
                hits++;
            }
        }
        int size = dataset.size();
        float percent = (hits * 100f) / size;
        System.out.println("Results of '" + expected + "': " + hits + "/" + size + " correct predictions. (" + String.format("%.02f", percent) + "%)");

        return hits;
    }

    public static void main(String args[]) {
        new MachineLearning();
    }

}
