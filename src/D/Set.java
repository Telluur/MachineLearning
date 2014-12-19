package D;

public enum Set {
    HAM_SPAM("Ham","Spam","Ham/Spam"),
    MALE_FEMALE("Male","Female","Male/Female");

    private String set1;
    private String set2;
    private String display;

    Set(String set1, String set2, String display) {
        this.set1 = set1;
        this.set2 = set2;
        this.display = display;
    }

    public String getSet1() {
        return set1;
    }

    public String getSet2() {
        return set2;
    }

    public String getDisplay() {
        return display;
    }
}
