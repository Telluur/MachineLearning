import java.io.Console;
import java.util.Map;


public class CalculateClass {
    public static String CalculateClass(String[] text, Chance maleChance, Chance femaleChance) {
        float chanceMale = 0;
        float chanceFemale = 0;

        final Map<String, Float> maleChanceReturnMap = maleChance.getReturnMap();
        final Map<String, Float> femaleChanceReturnMap = femaleChance.getReturnMap();
        final int maleChanceAmountWords = maleChance.getAmountWords();
        final int femaleChanceAmountWords = femaleChance.getAmountWords();

        for (int i = 0; i < text.length; i++) {

            if (!maleChanceReturnMap.containsKey(text[i]) || maleChanceReturnMap.get(text[i]) == 0) {
                chanceMale += Math.log(1 / maleChanceAmountWords);
            } else {
                //chanceMale += Math.log(maleChanceReturnMap.get(text[i]));
            }
            if (!femaleChanceReturnMap.containsKey(text[i]) || femaleChanceReturnMap.get(text[i]) == 0) {
                chanceFemale += Math.log(1 / femaleChanceAmountWords);
            } else {
                //chanceFemale += Math.log(femaleChanceReturnMap.get(text[i]));
            }
        }
        System.out.println("male: " + chanceMale + " female: " + chanceFemale);

        return chanceMale > chanceFemale ? "Male" : "Female";
    }
}
