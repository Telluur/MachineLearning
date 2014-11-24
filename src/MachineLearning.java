import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MachineLearning {

    public static void main(String args[]){
    	new MachineLearning();
    }
    
    public MachineLearning(){
    	InputReader inputReader = new InputReader();

        List<String> maleTrain = inputReader.populateList(new File(InputReader.TRAIN_LOC + "\\F"), new ArrayList<String>());
        List<String> femaleTrain = inputReader.populateList(new File(InputReader.TRAIN_LOC + "\\M"), new ArrayList<String>());

        List<String[]> maleTokens = Util.parseString(maleTrain);
        List<String[]> femaleTokens = Util.parseString(femaleTrain);

        Chance maleChance = new Chance(maleTokens);
        Chance femaleChance = new Chance(femaleTokens);


    	List<String> test = new ArrayList<String>();
    	test.add("hey iets doe nu boe????");
    	System.out.println(Util.determineType(Util.parseString(test).get(0), maleChance, femaleChance));
    	
    	
    }
}
