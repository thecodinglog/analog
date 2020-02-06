package cothe.distributor;

import cothe.LogMessage;
import cothe.LogMessageBundle;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Queue;

/**
 * Queue 에 있는 LogMessage 를 관련 있는 것들 끼리 묶는다.
 */
@RequiredArgsConstructor
public class LogMessageDistributor implements Runnable {
    private final Queue<LogMessage> queue;
    private final Map<String, LogMessageBundle> logMessageBundleMap;

    @Override
    public void run() {
        // 지속적으로 queue 에 처리할 LogMessage 가 있는지 확인하는 루프
        while (!Thread.currentThread().isInterrupted()) {
            // 모든 공유된 queue에 대해서 동기화 처리를 해야함
            // 만약 ConcurrentLinkedQueue 같이 ThreadSafe 한 구상체를 사용하고 있으면
            // synchronized 블록이 없어도 상관없음. 하지면 여기서는 ThreadSafe 한 구상체가 넘어온다는 보장이 없으므로
            // 동기화 처리를 해야함
            synchronized (queue) {
                // queue에 처리할 것이 없을때 까지 한번 모두 처리함
                // 그렇지 않으면 Thread.sleep() 때문에 처리 지연됨
                while (!queue.isEmpty()) {
                    LogMessage logMessage = queue.poll();
                    synchronized (logMessageBundleMap) {
                        if (!logMessageBundleMap.containsKey(logMessage.getThreadName())) {
                            logMessageBundleMap.put(logMessage.getThreadName(), new LogMessageBundle());
                        }
                        logMessageBundleMap.get(logMessage.getThreadName()).addMessage(logMessage);
                    }
                    System.out.println(logMessage.toString());
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " terminated.");
    }
}
