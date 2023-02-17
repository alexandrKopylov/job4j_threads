package ru.job4j.concurrent;


public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        Thread threadrrrr = new Thread(
                () -> {
                    int count = 0;
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(count++);
                    }

                    System.out.println("stop");
                }
        );
        threadrrrr.start();
        Thread.sleep(1000);


        threadrrrr.interrupt();
    }
}