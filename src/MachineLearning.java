import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MachineLearning {

    public static void main(String args[]){
    	new MachineLearning();
    }
    
    public MachineLearning(){
    	InputReader inputReader = new InputReader();
    	AssignChances maleChances = new AssignChances();
    	AssignChances femaleChances = new AssignChances();
    	Map<String, Float> maleMap = maleChances.assignChance((ParseText.parseString(inputReader.maleTrain)));
    	Map<String, Float> femaleMap = femaleChances.assignChance((ParseText.parseString(inputReader.femaleTrain)));
    	List<String> test = new ArrayList<String>();
    	test.add("hey iets doe nu boe????");
    	System.out.println(CalculateClass.CalculateClass(ParseText.parseString(test).get(0), maleMap, femaleMap, maleChances.amountWords, femaleChances.amountWords));
    	
    	
    }
}
