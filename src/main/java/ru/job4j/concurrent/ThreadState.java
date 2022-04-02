package ru.job4j.concurrent;


public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        boolean bool = true;
        while (bool) {
            if (first.getState() == Thread.State.TERMINATED
                    &&  second.getState() == Thread.State.TERMINATED) {
                bool = false;
            }
        }
        System.out.println("Work finish");
    }
}