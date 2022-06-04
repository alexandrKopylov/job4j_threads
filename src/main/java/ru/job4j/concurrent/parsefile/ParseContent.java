package ru.job4j.concurrent.parsefile;

import java.io.*;
import java.util.function.Predicate;

public class ParseContent {

    public synchronized String content(Predicate<Character> filter, File file) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}