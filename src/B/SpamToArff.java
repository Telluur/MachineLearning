package B;

import weka.core.Instances;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author Derk Snijders
 * @date 1-12-2014
 */

public class SpamToArff {
    public static String corpusFolderPath = System.getProperty("user.dir");
    public static String trainPath = corpusFolderPath + "\\spammail";
    public static String testPath = corpusFolderPath + "\\test";

    public SpamToArff() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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


}