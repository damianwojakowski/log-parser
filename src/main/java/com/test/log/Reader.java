package com.test.log;

import java.io.IOException;

public interface Reader {
    public void loadFile(String pathToTestLogs) throws IOException;
    public boolean hasNextLine();
    public String getNextLine();
    public void closeStream() throws IOException;
}
