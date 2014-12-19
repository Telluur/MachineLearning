import A.MachineLearning;
import D.GUIController;

/**
 * Created by Rick on 19-12-2014.
 */
public class Results {
    public Results() {
        //Part A
        System.out.println("\n~ Part A ~\n");
        new MachineLearning();

        /*/Part B
        new SpamToArff();
        new TxtToArff();
        */

        //Part D
        System.out.println("\n~ Part D ~\n");
        System.out.println("Opening GUI....");
        new GUIController();
    }

    public static void main(String[] args) {
        new Results();
    }
}
