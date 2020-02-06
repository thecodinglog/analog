package cothe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class LogMessage {
    private final LocalDateTime localDateTime;
    private final String threadName;
    private final String logLevel;
    private final String body;

    @Override
    public String toString() {
        return "LogLine{" +
                "localDateTime=" + localDateTime +
                ", threadName='" + threadName + '\'' +
                ", logLevel='" + logLevel + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
