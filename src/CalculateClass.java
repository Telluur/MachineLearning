import java.util.Map;


public class CalculateClass {
	public static String CalculateClass(String[] text, Map<String, Float> mankans, Map<String, Float> vrouwkans, int totalman, int totalvrouw) {
		float chanceMale = 1;
		float chanceFemale = 1;
		for (int i = 0; i < text.length; i++) {
			if (!mankans.containsKey(text[i]) || mankans.get(text[i]) == 0) {
				chanceMale *= 1/totalman;
			} else {
				chanceMale *= mankans.get(text[i]);
			}
			if (!vrouwkans.containsKey(text[i]) || vrouwkans.get(text[i]) == 0) {
				chanceFemale *= 1/totalvrouw;
			} else {
				chanceFemale *= vrouwkans.get(text[i]);
			}
		}
		String returnString = "";
		if (chanceMale > chanceFemale) {
			returnString = "Male";
		} else {
			returnString = "Female";
		}
		return returnString;
	}
}
