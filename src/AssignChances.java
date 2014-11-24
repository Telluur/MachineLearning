import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AssignChances {
	public static int k = 1;
	public static Map<String, Float> assignChance(List<String[]> set) {
		Map<String,Float> returnMap = new HashMap<String, Float>();
		int amountWords = 0;
		for (int i = 0; i < set.size(); i++) {
				amountWords += set.get(i).length;
			for (int j = 0; j < set.get(i).length; j++) {
				if (returnMap.keySet().contains(set.get(i)[j])){
					returnMap.put(set.get(i)[j], returnMap.get(set.get(i)[j]) +1);
				} else {
					returnMap.put(set.get(i)[j], (float) 1);
				}
			}
		}
		Set<String> keySet = returnMap.keySet();
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()) {
			String word = it.next();
			returnMap.put(word, (returnMap.get(word)+k)/(amountWords*k));
		}
		return returnMap;
	}
}
