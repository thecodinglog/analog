package cothe;

import lombok.RequiredArgsConstructor;

import java.util.Queue;

@RequiredArgsConstructor
public class InputQueue implements Runnable {
    private final Queue<Integer> queue;

    public void run() {
        int i = 0;
        for (i = 0; i < QueueTest.MAX_CNT; i++) {
//            System.out.println("in|" + i);
            synchronized (queue) {
                queue.add(i);
            }
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException ignore) {
//            }
        }
        System.out.println("input finished " + i);
    }
}
