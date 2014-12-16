package A;

import java.util.*;

public class Util {

    public static List<String[]> tokenizeString(List<String> set) {
        List<String[]> returnSet = new ArrayList<String[]>();
        for (String word : set) {

            String[] words = word.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            returnSet.add(words);
        }
        return returnSet;
    }

    public static Map<String, WordValues> createDictionary(List<String[]> tokenSet1, List<String[]> tokenSet2) {
        Map<String, WordValues> dictionary = new TreeMap<String, WordValues>();
        for (String[] tokens : tokenSet1) {
            for (String word : tokens) {
                if (dictionary.containsKey(word)) { //dictionary already contains word.
                    WordValues values = dictionary.get(word);
                    values.addSet1Occurrence();
                    dictionary.put(word, values);
                } else { //dictionary does not contain word
                    dictionary.put(word, new WordValues(1, 0));
                }
            }
        }

        for (String[] tokens : tokenSet2) {
            for (String word : tokens) {
                if (dictionary.containsKey(word)) { //dictionary already contains word.
                    WordValues values = dictionary.get(word);
                    values.addSet2Occurrence();
                    dictionary.put(word, values);
                } else { //dictionary does not contain word
                    dictionary.put(word, new WordValues(0, 1));
                }
            }
        }
        return dictionary;
    }

    public static Map<String, WordChances> calculateChances(Map<String, WordValues> dictionary, float smoothing) {
        //get values for chance calculations
        int set1Words = 0;
        int set2Words = 0;
        int dictionarySize = dictionary.size();
        for (WordValues entry : dictionary.values()) {
            set1Words = set1Words + entry.getSet1Occurrences();
            set2Words = set2Words + entry.getSet2Occurrences();
        }

        Map<String, WordValues> dictionaryCopy = new TreeMap<String, WordValues>(dictionary);
        Map<String, WordChances> chancesMap = new TreeMap<String, WordChances>();

        //calculate chances
        Iterator it = dictionaryCopy.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();

            String entryString = (String) entry.getKey();
            float set1Chance = (((WordValues) entry.getValue()).getSet1Occurrences() + smoothing) / (set1Words + smoothing * dictionarySize);
            float set2Chance = (((WordValues) entry.getValue()).getSet2Occurrences() + smoothing) / (set2Words + smoothing * dictionarySize);
            chancesMap.put(entryString, new WordChances(set1Chance, set2Chance));

            it.remove(); // avoids a ConcurrentModificationException
        }


        return chancesMap;
    }

    public static String determineType(String[] text, Map<String, WordChances> dictionary, String set1Name, String set2Name) {
        float set1Result = 0;
        float set2Result = 0;

        for (String word : text) {
            if (dictionary.containsKey(word)) {
                set1Result += Math.log(dictionary.get(word).getSet1Chance());
                set2Result += Math.log(dictionary.get(word).getSet2Chance());
            }
        }
        return set1Result > set2Result ? set1Name : set2Name;
    }
}
