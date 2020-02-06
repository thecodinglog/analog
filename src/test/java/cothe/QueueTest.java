package cothe;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;

public class QueueTest {
    public static Integer MAX_CNT = 1000;

    @Test
    public void queueTest() throws InterruptedException, ExecutionException {
        Queue<Integer> queue = new ArrayDeque<>(); //ArrayDeque 와 synchronized 를 조합해서 사용하는게 가장 빨랐음.
//        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        InputQueue inputQueue = new InputQueue(queue);
        OutputQueue outputQueue = new OutputQueue(queue);
        Instant start = Instant.now();

        ExecutorService executorService = Executors.newCachedThreadPool();

//        Future<?> input = executorService.submit(inputQueue);
//        Future<?> output = executorService.submit(outputQueue);
//
//        input.get();
//        output.get();

        executorService.execute(inputQueue);
        executorService.execute(outputQueue);

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ignore) {

        }
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(">>> " + timeElapsed);

    }


}
