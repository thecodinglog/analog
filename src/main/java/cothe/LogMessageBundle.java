package cothe;

import java.util.ArrayList;
import java.util.List;

public class LogMessageBundle {
    private List<LogMessage> logMessages = new ArrayList<>();
    private boolean isCompleted;

    public void addMessage(LogMessage logMessage) {
        logMessages.add(logMessage);
        isCompleted = checkCompleted();
    }

    private boolean checkCompleted() {
        //todo 완성된 로그 파일인지 확인 로직 추가
        return false;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
