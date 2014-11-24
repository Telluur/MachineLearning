
public class MachineLearning {

    public static void main(String args[]){
    	new MachineLearning();
    }
    
    public MachineLearning(){
    	InputReader inputReader = new InputReader();
    	System.out.println(AssignChances.assignChance(ParseText.parseString(inputReader.maleTrain)));
    	
    	
    }
}
