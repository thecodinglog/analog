package cothe.preprocess;

public interface LogLineChunk {
    void addLine(String line);

    String getLine();

    boolean hasLine();
}
