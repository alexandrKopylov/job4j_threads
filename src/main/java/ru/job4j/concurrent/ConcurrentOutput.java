package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();

        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();

        Thread third = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        third.start();

        System.out.println(Thread.currentThread().getName());
    }
}