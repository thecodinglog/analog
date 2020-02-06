package cothe;

import cothe.distributor.LogMessageDistributor;
import cothe.extract.FileInputMessageExtractor;
import cothe.extract.MessageExtractor;
import cothe.extract.StandardInputMessageExtractor;
import cothe.preprocess.DefaultLogLineChunk;
import cothe.preprocess.DefaultLogMessageParser;
import cothe.preprocess.PreProcessor;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();

        Queue<LogMessage> logMessages = new ArrayDeque<>();
        Queue<String> logLines = new ArrayDeque<>();
        Map<String, LogMessageBundle> logMessageMap = new HashMap<>();

        PreProcessor preProcessor = new PreProcessor(logMessages, new DefaultLogMessageParser(), new DefaultLogLineChunk(), logLines);
//        MessageExtractor messageExtractor = new StandardInputMessageExtractor(logLines);
        MessageExtractor messageExtractor = new FileInputMessageExtractor("C://logs", logLines);
        LogMessageDistributor logMessageDistributor = new LogMessageDistributor(logMessages, logMessageMap);

        service.execute(messageExtractor);
        service.execute(preProcessor);
        service.execute(logMessageDistributor);

        //todo logMessageMap 에 존재하는 LogMessageBundle 에서 isCompleted 가 true 이면 상내 내용을 분석하는 Thread Worker 를
        // 구현해야 함함

       service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}
