package cothe.preprocess;

import cothe.LogMessage;
import lombok.RequiredArgsConstructor;

import java.util.Queue;

/**
 * 공유 자원인 logLine Queue 에서 한 줄씩 가져와 logLineChunk 에 넣는다.
 * logLineChunk 는 멤버내에서만 사용하는 멤버 변수이고 다른 스레드와 공유되면 안된다.
 * 그러므로 PreProcessor 는 2개 이상 존재하면 안된다.
 * LogLineChunk 에 완전한 logLine 이 있으면 파싱해서 LogMessage 객체를 만든다.
 * 만들어진 객체는 sharedQueue 에 넣는다.
 *
 * @author Jeongjin Kim
 * @since 2020-02-04
 */

@RequiredArgsConstructor
public class PreProcessor implements Runnable {
    private final Queue<LogMessage> sharedQueue;
    private final LogMessageParser logMessageParser;
    private final LogLineChunk logLineChunk;
    private final Queue<String> logLine;

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (logLine) {
                while (!logLine.isEmpty()) {
                    logLineChunk.addLine(logLine.poll());
                }
            }

            while (logLineChunk.hasLine()) {
                synchronized (sharedQueue) {
                    sharedQueue.add(logMessageParser.parse(logLineChunk.getLine()));
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " terminated.");
    }
}
