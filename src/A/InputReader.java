package A;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
    final static public String BASE_LOC = System.getProperty("user.dir");
    final static public String BLOG_LOC = BASE_LOC + "\\blog";
    final static public String EMAIL_LOC = BASE_LOC + "\\spammail";

    public static void main(String args[]) {
        new InputReader();
    }

    /**
     * Initilizes class and prints out the user.dir directory
     */
    public InputReader() {
        System.out.println("user.dir: " + BASE_LOC);
    }

    /**
     * Helper method to retrieve all the file locations under the specified folder
     *
     * @param folder path to folder
     * @return list containing locations of text files
     */
    @SuppressWarnings("ConstantConditions")
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

    /**
     * Reads in files from a folder recursively and generates a list
     * Every list entry contains the contents of 1 file in the scanned folder
     *
     * @param folder location to be scanned for files
     * @return the contents of the read files
     */
    public List<String> generateAndPopulateList(final File folder) {
        List<String> returnList = new ArrayList<String>();
        for (final String location : listFiles(folder))
            try {
                String content = readFile(location, StandardCharsets.UTF_8);
                returnList.add(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return returnList;
    }

    /**
     * Lazy implementation for reading in files.
     * Beware that the contents of the file must fit in the max allocation size of a byte array.
     *
     * @param path     location to the file
     * @param encoding encoding
     * @return string containing file contents
     * @throws IOException
     */
    private static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
