package A;

import java.util.*;

public class Util {

    /**
     * Tokenizes a list of text to an array of words
     *
     * @param set list containing the text
     * @return tokenized texts
     */
    public static List<String[]> tokenizeStringList(List<String> set) {
        List<String[]> returnSet = new ArrayList<String[]>();
        for (String string : set) {
            returnSet.add(tokenizeString(string));
        }
        return returnSet;
    }

    public static String[] tokenizeString(String string) {
        return string.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
    }


    /**
     * Creates a dictionary based on two tokenized sets.
     *
     * @param tokenSet1 tokenized Strings array for set1
     * @param tokenSet2 tokenized Strings array for set2
     * @return dictionary containing words with their respective number of occurrences in set1 and set2
     */
    public static Map<String, WordValues> createWordValuesDictionary(List<String[]> tokenSet1, List<String[]> tokenSet2) {
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

    public static Map<String, WordValues> addToWordValuesDictionary(Map<String, WordValues> dictionary, String[] set1Tokens, String[] set2Tokens) {
        if (set1Tokens != null) {
            for (String word : set1Tokens) {
                if (dictionary.containsKey(word)) { //dictionary already contains word.
                    WordValues values = dictionary.get(word);
                    values.addSet1Occurrence();
                    dictionary.put(word, values);
                } else { //dictionary does not contain word
                    dictionary.put(word, new WordValues(1, 0));
                }
            }
        }
        if (set2Tokens != null) {
            for (String word : set2Tokens) {
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

    /**
     * Calculates the chances for a dictionary
     *
     * @param dictionary dictionary containing words and their respective number of occurrences in the train set.
     * @param smoothing  Laplacian smoothing factor
     * @return word dictionary containing chances based on the number of occurrences in the train set.
     */
    public static Map<String, WordChances> createWordChancesDictionary(Map<String, WordValues> dictionary, float smoothing) {
        int set1Words = 0;
        int set2Words = 0;
        int dictionarySize = dictionary.size();
        for (WordValues entry : dictionary.values()) {
            set1Words = set1Words + entry.getSet1Occurrences();
            set2Words = set2Words + entry.getSet2Occurrences();
        }
        Map<String, WordValues> dictionaryCopy = new TreeMap<String, WordValues>(dictionary);
        Map<String, WordChances> chancesMap = new TreeMap<String, WordChances>();
        Iterator it = dictionaryCopy.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String entryString = (String) entry.getKey();
            //Laplacian smoothing
            float set1Chance = (((WordValues) entry.getValue()).getSet1Occurrences() + smoothing) / (set1Words + smoothing * dictionarySize);
            float set2Chance = (((WordValues) entry.getValue()).getSet2Occurrences() + smoothing) / (set2Words + smoothing * dictionarySize);
            chancesMap.put(entryString, new WordChances(set1Chance, set2Chance));
            it.remove(); // avoids a ConcurrentModificationException
        }
        return chancesMap;
    }

    /**
     * Determines based on the dictionary how to classify the text
     *
     * @param text       to be classified tokenized string
     * @param dictionary contains words and their respective chance values for set1Name and set2Name
     * @param set1Name   actual name of first set
     * @param set2Name   actual name of second set
     * @return the name of the classified set
     */
    public static String determineType(String[] text, Map<String, WordChances> dictionary, String set1Name, String set2Name, boolean printChances) {
        float set1Result = 0;
        float set2Result = 0;
        for (String word : text) {
            if (dictionary.containsKey(word)) {
                set1Result += Math.log(dictionary.get(word).getSet1Chance());
                set2Result += Math.log(dictionary.get(word).getSet2Chance());
            }
        }
        if (printChances)
            System.out.println("Static | " + set1Name + "=" + set1Result + " " + set2Name + "=" + set2Result);
        return set1Result > set2Result ? set1Name : set2Name;
    }

    public static String determineType(String[] text, Map<String, WordValues> dictionary, String set1Name, String set2Name, float smoothing, boolean printChances) {
        int set1Words = 0;
        int set2Words = 0;
        int dictionarySize = dictionary.size();
        for (WordValues entry : dictionary.values()) {
            set1Words = set1Words + entry.getSet1Occurrences();
            set2Words = set2Words + entry.getSet2Occurrences();
        }
        float set1Result = 0;
        float set2Result = 0;
        for (String word : text) {
            int set1Occurrences = 0;
            int set2Occurrences = 0;
            if (dictionary.containsKey(word)) {
                set1Occurrences = dictionary.get(word).getSet1Occurrences();
                set2Occurrences = dictionary.get(word).getSet2Occurrences();
            }
            set1Result += Math.log((set1Occurrences + smoothing) / (set1Words + smoothing * dictionarySize));
            set2Result += Math.log((set2Occurrences + smoothing) / (set2Words + smoothing * dictionarySize));
        }
        if (printChances)
            System.out.println("Learning | " + set1Name + "=" + set1Result + " " + set2Name + "=" + set2Result);
        return set1Result > set2Result ? set1Name : set2Name;
    }
}
