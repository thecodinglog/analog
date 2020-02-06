package cothe.preprocess;

import cothe.LogMessage;

import java.time.LocalDateTime;

public class DefaultLogMessageParser implements LogMessageParser {
    @Override
    public LogMessage parse(String line) {
        return new LogMessage(LocalDateTime.now(), "Thread-1", "DEBUG", line);
    }
}
