import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputReader {


    final static private String BASE_LOC = System.getProperty("user.dir");
    final static private String TRAIN_LOC = BASE_LOC + "\\train";
    //final static private String TEST_LOC = BASE_LOC + "\\test";

    private List<String> maleTrain = new ArrayList<String>();
    private List<String> femaleTrain = new ArrayList<String>();

    public static void main(String args[]) {
        new InputReader();
    }

    public InputReader() {
        System.out.println("This location should contain test and train folders\n" + BASE_LOC);
        populateList(new File(TRAIN_LOC + "\\F"), femaleTrain);
        populateList(new File(TRAIN_LOC + "\\M"), maleTrain);
    }


    private List<String> listFiles(final File folder) {
        List<String> locationStrings = new ArrayList<String>();
        for (final File file : folder.listFiles()) {
            if (file.isDirectory()) {
                listFiles(file);
            } else {
                locationStrings.add(file.getAbsolutePath());
            }
        }
        return locationStrings;
    }

    private void populateList(final File folder, List<String> list){
        for(final String location : listFiles(folder))
            try {
                String content = readFile(location, StandardCharsets.UTF_8);
                list.add(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    private static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
