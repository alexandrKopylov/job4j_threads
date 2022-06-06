package ru.job4j.concurrent.parsefile;

import java.io.File;

public class Parser {

    private Parsing strategy;
    private final File file;

    public Parser(File file) {
        this.file = file;
    }

    public void setStrategy(Parsing strategy) {
        this.strategy = strategy;
    }

    public String runStrategy() {
        return strategy.getContent();
    }

    public static void main(String[] args) {
        Parser parser = new Parser(new File("src/main/java/ru/job4j/concurrent/parsefile/content.txt"));
        parser.setStrategy(new ParseFileWithUnicode(parser.file));
        String content = parser.runStrategy();
        System.out.println(content);

        parser.setStrategy(new ParseWithoutUnicode(parser.file));
        content = parser.runStrategy();
        System.out.println(content);

        String pathFile = "src/main/java/ru/job4j/concurrent/parsefile/Out.txt";
        SaveContent saveContent = new SaveContent("Java SE", new File(pathFile));
        saveContent.saveContent();
    }
}
