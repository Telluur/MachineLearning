import java.util.ArrayList;
import java.util.List;


public class ParseText {
	public static List<String[]> parseString(List<String> set) {
		List<String[]> returnset = new ArrayList<String[]>();
		for (int i = 0; i < set.size(); i++) {
			
			String[] words = set.get(i).replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
			returnset.add(words);
		}
		return returnset;
		
	}
	public void main (String[] args) {
		List<String> test = new ArrayList<String>();
		test.add("hoi Werkt, dit??? xD\t \r \n nnooo sdfsdfd;sa da[]?");
		List<String[]> tester = parseString(test);
		for (int i = 0; i < 7; i++) {
			System.out.println(tester.get(0)[i]);
		}
	}
}
