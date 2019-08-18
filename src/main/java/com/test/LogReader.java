package com.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class LogReader {
    private FileInputStream inputStream;
    private Scanner scanner;
    private static final String charset = "UTF-8";

    public void loadFile(String pathToTestLogs) throws IOException {
        try {
            inputStream = new FileInputStream(pathToTestLogs);
            scanner = new Scanner(inputStream, charset);
        } catch (IOException e) {
            e.printStackTrace();

            if (inputStream != null) {
                inputStream.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public String getNextLine() {
        return scanner.nextLine();
    }

    public void closeStream() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        if (scanner != null) {
            scanner.close();
        }
    }
}
