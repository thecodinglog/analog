package cothe;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Lex {
    private final LogMessageBundle logMessageBundle;

    private void lex() {
        while (logMessageBundle.hasNext()) {
            LogMessage nextMessage = logMessageBundle.getNextMessage();


        }
    }


}
