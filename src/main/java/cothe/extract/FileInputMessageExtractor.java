package cothe.extract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.UUID;

public class FileInputMessageExtractor implements MessageExtractor {
    private final File targetDirectory;
    private final Queue<String> logLines;

    public FileInputMessageExtractor(String targetDirectoryPath, Queue<String> logLines) {
        this.targetDirectory = new File(targetDirectoryPath);
        this.logLines = logLines;

        File[] files = targetDirectory.listFiles();
        if(files == null){
            throw new RuntimeException("It's not directory or exists");
        }



        if(targetDirectory.isFile()){
            throw new RuntimeException("It's not directory");
        }
    }

    @Override
    public void run() {
        String temp;
        // 지속적으로 읽어서 처리할 파일이 있는지 확인하는 루프
        while (!Thread.currentThread().isInterrupted()) {
            File[] files = targetDirectory.listFiles();
            if(files == null)
                throw new RuntimeException("No target directory");

            for (File file : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                    while ((temp = reader.readLine()) != null) {
                        synchronized (logLines) {
                            logLines.add(temp);
                        }
                    }
                } catch (IOException e) {
                    try {
                        Files.move(file.toPath(), Paths.get(targetDirectory + "_error/" + file.getName() + UUID.randomUUID()));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                try {
                    Files.move(file.toPath(), Paths.get(targetDirectory + "_back/" + file.getName() + UUID.randomUUID()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(Thread.currentThread().getName() + " terminated.");
    }
}
