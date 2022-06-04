package ru.job4j.concurrent.parsefile;

import java.io.File;

public class ParseWithoutUnicode implements Parsing {
    private final File file;
    ParseContent parseContent = new ParseContent();

    public ParseWithoutUnicode(File file) {
        this.file = file;
    }

    @Override
    public synchronized String getContent() {
        return parseContent.content((character -> character > 0x80), file);
    }
}