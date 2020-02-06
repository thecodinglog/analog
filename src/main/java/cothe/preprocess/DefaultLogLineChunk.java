package cothe.preprocess;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 실제로 LogMessage 는 한 줄 이지만 여러라인으로 입력된 경우 버퍼에 담고 완결확인 로직에 의해서 그 결과를 반환하는
 * 메소드를 제공한다.
 */
public class DefaultLogLineChunk implements LogLineChunk {
    private List<String> linesBuffer = new ArrayList<>();
    private Queue<String> completedLines = new ArrayDeque<>();

    @Override
    public void addLine(String line) {
        preCheckCompleteLine(line);
        linesBuffer.add(line);
        postCheckCompleteLine(line);
    }

    /**
     * 현재 linesBuffer 에 있는 데이터가 완결된 문자이면 completedLines 에 넣도록 한다.
     * 입력 받은 line 으로 앞선 데이터가 종결인지 판단해야 하는 경우 사용
     */
    private void preCheckCompleteLine(String line) {
        //todo 완결 확인 로직 샘플
        if (line.startsWith("[") && !line.contains("sql")) {
            flush();
        } else if (line.startsWith("[") && line.contains("sql") && linesBuffer.size() > 0) {
            flush();
        }
    }

    /**
     * 현재 linesBuffer 에 있는 데이터가 완결된 문자이면 completedLines 에 넣도록 한다.
     */
    private void postCheckCompleteLine(String line) {
        // todo 완결 확인 로직
        if (line.startsWith("[") && !line.contains("sql") && linesBuffer.size() > 0)
            flush();
    }

    private void flush() {
        if (linesBuffer.size() > 0) {
            completedLines.add(String.join("\n", linesBuffer));
            linesBuffer.clear();
        }
    }

    @Override
    public String getLine() {
        return completedLines.poll();
    }

    @Override
    public boolean hasLine() {
        return !completedLines.isEmpty();
    }
}
