package cothe;

import java.util.ArrayList;
import java.util.List;

public class LogMessageBundle {
    private List<LogMessage> logMessages = new ArrayList<>();
    private boolean isCompleted;
    private int index = 0;


    synchronized public void addMessage(LogMessage logMessage) {
        logMessages.add(logMessage);
        isCompleted = checkCompleted();
    }

    synchronized public LogMessage getNextMessage() {
        LogMessage logMessage = logMessages.get(index);
        index++;
        return logMessage;
    }
    synchronized public boolean hasNext(){
        return logMessages.size() > index;
    }

    private boolean checkCompleted() {
        //todo 완성된 로그 파일인지 확인 로직 추가
        return false;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
