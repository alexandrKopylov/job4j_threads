package ru.job4j.concurrent.parsefile;


import java.io.*;

public class SaveContent {
    public SaveContent(String content, File file) {
        this.content = content;
        this.file = file;
    }

    private final String content;
    private final File file;

    public synchronized void saveContent() {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                out.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}