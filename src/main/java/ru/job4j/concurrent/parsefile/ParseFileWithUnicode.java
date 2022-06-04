package ru.job4j.concurrent.parsefile;

import java.io.*;

public class ParseFileWithUnicode implements Parsing {
    private final File file;
    ParseContent parseContent = new ParseContent();

    public ParseFileWithUnicode(File file) {
        this.file = file;
    }

    @Override
    public synchronized String getContent() {
        return parseContent.content((character -> character > 0), file);
    }
}