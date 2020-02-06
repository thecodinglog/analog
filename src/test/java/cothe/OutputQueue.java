package cothe;

import lombok.RequiredArgsConstructor;

import java.util.Queue;

@RequiredArgsConstructor
public class OutputQueue implements Runnable {
    private final Queue<Integer> queue;
    private boolean stop;

    public void run() {
        int lastNumber = 0;
        while (!stop) {
            synchronized (queue) {
                if (!queue.isEmpty()) {
                    Integer poll = queue.poll();
                    lastNumber = poll;
                    if(poll == QueueTest.MAX_CNT - 1)
                        stop = true;
//                    System.out.println("out|" + poll);
                }
            }
            //            try {
//                Thread.sleep(1);
//            } catch (InterruptedException ignore) {
//            }
        }
        System.out.println("output finished " + lastNumber);
    }
}
