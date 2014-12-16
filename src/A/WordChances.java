package A;

public class WordChances {
    private float set1Chance;
    private float set2Chance;

    public WordChances(float set1Chance, float set2Chance){
        this.set1Chance = set1Chance;
        this.set2Chance = set2Chance;
    }

    public void setSet1Chance(float chance){
       set1Chance = chance;
    }

    public void setSet2Chance(float chance){
        set2Chance = chance;
    }

    public float getSet1Chance(){
        return set1Chance;
    }

    public float getSet2Chance(){
        return set2Chance;
    }


    @Override
    public String toString() {
        return "set 1: " + set1Chance + " | set 2: " + set2Chance;
    }
}
