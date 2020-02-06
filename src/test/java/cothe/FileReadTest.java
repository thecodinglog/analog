package cothe;

import org.junit.Test;

import java.io.File;

public class FileReadTest {
    //        String temp;
////        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\USER\\eclipse_git_juno_64bit\\workspace\\analog\\src\\main\\resources\\sample.txt"))) {
////            while ((temp = reader.readLine()) != null) {
////                preProcessor.addText(temp);
////            }
////        }
    @Test
    public void readFileList() {
        File targetDirectory = new File("C:\\logs");
        File[] files = targetDirectory.listFiles();
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }

    @Test
    public void notExistsDirectory() {
        File dir = new File("C:\\fff");
        dir.getName();
    }
}
