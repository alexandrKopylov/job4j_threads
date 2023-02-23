package ru.job4j.concurrent.wait;


public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(6);
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        Thread prod = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        );
        prod.start();
        prod.join();
        while (consumer.getState() != Thread.State.WAITING) {
            Thread.sleep(500);
        }
        consumer.interrupt();

    }
}