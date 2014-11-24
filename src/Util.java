import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {

    public static List<String[]> parseString(List<String> set) {
        List<String[]> returnSet = new ArrayList<String[]>();
        for (int i = 0; i < set.size(); i++) {

            String[] words = set.get(i).replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            returnSet.add(words);
        }
        return returnSet;
    }

    public static String determineType(String[] text, Chance maleChance, Chance femaleChance) {
        float chanceMale = 0;
        float chanceFemale = 0;

        final Map<String, Float> maleChanceReturnMap = maleChance.getReturnMap();
        final Map<String, Float> femaleChanceReturnMap = femaleChance.getReturnMap();
        final int maleChanceAmountWords = maleChance.getAmountWords();
        final int femaleChanceAmountWords = femaleChance.getAmountWords();

        for (int i = 0; i < text.length; i++) {

            if (!maleChanceReturnMap.containsKey(text[i]) || maleChanceReturnMap.get(text[i]) == 0) {
                chanceMale += Math.log((float)1 / maleChanceAmountWords);
            } else {
                chanceMale += Math.log(maleChanceReturnMap.get(text[i]));
            }
            if (!femaleChanceReturnMap.containsKey(text[i]) || femaleChanceReturnMap.get(text[i]) == 0) {
                chanceFemale += Math.log((float)1 / femaleChanceAmountWords);
            } else {
                chanceFemale += Math.log(femaleChanceReturnMap.get(text[i]));
            }
        }
        System.out.println("male: " + chanceMale + " female: " + chanceFemale);

        return chanceMale > chanceFemale ? "Male" : "Female";
    }
}
