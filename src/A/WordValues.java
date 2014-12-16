package A;

public class WordValues {
    private int set1Occurrences;
    private int set2Occurrences;
    public WordValues(int set1Occurrences, int set2Occurrences) {
        this.set1Occurrences = set1Occurrences;
        this.set2Occurrences = set2Occurrences;
    }

    public void addSet1Occurrence(){
        set1Occurrences++;
    }

    public void addSet2Occurrence(){
        set2Occurrences++;
    }

    public int getSet2Occurrences() {
        return set2Occurrences;
    }

    public void setSet2Occurrences(int set2Occurrences) {
        this.set2Occurrences = set2Occurrences;
    }

    public int getSet1Occurrences() {
        return set1Occurrences;
    }

    public void setSet1Occurrences(int set1Occurrences) {
        this.set1Occurrences = set1Occurrences;
    }

    @Override
    public String toString() {
        return "set 1: " + set1Occurrences + " | set 2: " + set2Occurrences;
    }
}