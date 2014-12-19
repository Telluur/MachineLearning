package D;

import A.Util;
import A.WordChances;
import A.WordValues;
import D.GUI.LearnerForm;
import D.GUI.StartupForm;
import Utilities.InputReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUIController {
    private StartupForm startupForm;
    private LearnerForm learner;
    private Set set;
    private List<String[]> set1Train;
    private List<String[]> set2Train;
    private List<String> set1Test;
    private List<String> set2Test;
    private Map<String, WordChances> staticDictionary;
    private Map<String, WordValues> learnerDictionary;
    private List<String[]> queue = new ArrayList<String[]>();
    private int queuePosition = 0;


    public GUIController() {
        startupForm = new StartupForm("Interactive Learner", this);
    }

    public void startInteractiveLearner() {
        InputReader inputReader = new InputReader();
        if (set.equals(Set.HAM_SPAM)) {
            List<String> ham = inputReader.generateAndPopulateList(new File(InputReader.EMAIL_LOC + "\\ham"));
            List<String> spam = inputReader.generateAndPopulateList(new File(InputReader.EMAIL_LOC + "\\spam"));
            int trainingHam = (int) (ham.size() * 90 / 100f);
            int trainingSpam = (int) (spam.size() * 90 / 100f);
            set1Train = Util.tokenizeStringList(ham.subList(0, trainingHam));
            set2Train = Util.tokenizeStringList(spam.subList(0, trainingSpam));
            set1Test = ham.subList(trainingHam, ham.size());
            set2Test = spam.subList(trainingSpam, spam.size());
        } else if (set.equals(Set.MALE_FEMALE)) {
            set1Train = Util.tokenizeStringList(inputReader.generateAndPopulateList(new File(InputReader.BLOG_LOC + "\\train\\M")));
            set2Train = Util.tokenizeStringList(inputReader.generateAndPopulateList(new File(InputReader.BLOG_LOC + "\\train\\F")));
            set1Test = inputReader.generateAndPopulateList(new File(InputReader.BLOG_LOC + "\\test\\M"));
            set2Test = inputReader.generateAndPopulateList(new File(InputReader.BLOG_LOC + "\\test\\F"));
        } else {
            System.exit(1);
        }

        for(String entry : set1Test){
            queue.add(new String[]{entry, set.getSet1()});
        }
        for(String entry : set2Test){
            queue.add(new String[]{entry, set.getSet2()});
        }

        learnerDictionary = Util.createWordValuesDictionary(set1Train, set2Train);
        staticDictionary = Util.createWordChancesDictionary(learnerDictionary, 1);

        startupForm.setVisible(false);
        startupForm.dispose();
        learner = new LearnerForm("Interactive Learner [" + set.getDisplay() + "] [NBC]", this);
        learner.setBusy();
        determineMessage(queuePosition, true);
    }

    public String[] determineMessage(int position, boolean updateScreen) {
        if(position < queue.size()){
            String[] entry = queue.get(position);
            String message = entry[0];
            String actualSet = entry[1];
            String[] tokens = Util.tokenizeString(message);
            String staticResult = Util.determineType(tokens, staticDictionary, set.getSet1(), set.getSet2(), true);
            String learnerResult = Util.determineType(tokens, learnerDictionary, set.getSet1(), set.getSet2(), 1, true);
            if (updateScreen) learner.updateScreen(message, staticResult, learnerResult, actualSet);
            return new String[]{staticResult, learnerResult, actualSet};
        }else{
            learner.setDone();
            return null;
        }
    }

    public void selectSet1(){
        Util.addToWordValuesDictionary(learnerDictionary, Util.tokenizeString(queue.get(queuePosition)[0]), null);
        queuePosition++;
        determineMessage(queuePosition, true);
    }

    public void selectSet2(){
        Util.addToWordValuesDictionary(learnerDictionary, null, Util.tokenizeString(queue.get(queuePosition)[0]));
        queuePosition++;
        determineMessage(queuePosition, true);
    }


    public void showLearner() {
        while(true){
            String[] resultSet = determineMessage(queuePosition, false);
            if(resultSet == null) break; //no more entries to test.
            System.out.println(queuePosition + " " + resultSet[0] + " " + resultSet[1]);
            if(resultSet[0].equals(resultSet[1])){
                selectSet2();
            }else{
                determineMessage(queuePosition, true);
                break; //Difference between static and learner found.
            }
            queuePosition++;
        }
    }


    public void setSet(Set set) {
        this.set = set;
    }

    public Set getSet() {
        return set;
    }

    public static void main(String args[]) {
        new GUIController();
    }
}
