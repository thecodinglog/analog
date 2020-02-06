package cothe.preprocess;

import cothe.LogMessage;

/**
 * 완전한 LogMessage 의 String 을 받아 LogMessage 객체로 Parse 하는 일을 한다.
 */
public interface LogMessageParser {
    LogMessage parse(String line);
}
