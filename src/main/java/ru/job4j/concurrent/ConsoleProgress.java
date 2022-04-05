package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {

        String[] process = {"\\", "|", "/"};
        while (!Thread.currentThread().isInterrupted()) {
            for (String str : process) {
                System.out.print("\r loading..." + str);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(3000);
        progress.interrupt();
    }
}
