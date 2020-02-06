package cothe.extract;

import lombok.RequiredArgsConstructor;

import java.util.Queue;
import java.util.Scanner;

@RequiredArgsConstructor
public class StandardInputMessageExtractor implements MessageExtractor {
    private final Queue<String> logLines;
    private final Scanner scan = new Scanner(System.in);

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            String s = scan.nextLine();
            synchronized (logLines) {
                logLines.add(s);
            }
        }
        System.out.println(Thread.currentThread().getName() + " terminated.");
    }
}
