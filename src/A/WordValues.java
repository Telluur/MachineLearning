package A;

public class WordValues {
    private int set1Occurrences;
    private int set2Occurrences;

    public WordValues(int set1Occurrences, int set2Occurrences) {
        this.set1Occurrences = set1Occurrences;
        this.set2Occurrences = set2Occurrences;
    }

    public void addSet1Occurrence() {
        set1Occurrences++;
    }

    public void addSet2Occurrence() {
        set2Occurrences++;
    }

    public int getSet2Occurrences() {
        return set2Occurrences;
    }

    public int getSet1Occurrences() {
        return set1Occurrences;
    }

    @Override
    public String toString() {
        return "set1Occurrences=" + set1Occurrences + " | set2Occurrences=" + set2Occurrences;
    }
}